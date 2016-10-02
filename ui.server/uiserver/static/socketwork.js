var socket = io('http://' + document.domain + ':' + location.port);

var paused = false;
var maxNodesToProcess = 2000;

socket.on('connect', function() {
    resetZoom();
    console.log("connected");
});

socket.on('components', function(msg) {
    if (paused)
	return;
    var newData = JSON.parse(msg.data);
    // console.log('parsed', newData);
    var newDataSize = _.size(newData);
    if (newDataSize > maxNodesToProcess) {
	console.log("Nope! too much to do", newDataSize);
	return;
    }
    filteredKeys = _.filter(_.keys(newData), function(k) { return newData[k].length > 2; });
    filteredData = _.pick(newData, filteredKeys);
    // console.log('filtered', filteredData);
    updatePulseGraph(filteredData);
    updatePlot(newData);
});


socket.on('error', function(err) {
    console.log("error returned: ", err);
});

function sendMsg() {
    socket.emit('my event', {data: document.getElementById('input').value});
    return false;
}


function togglePause() {
    paused = !paused;
}

function dummyPrint(newData) {
    if (_.has(newData, 'length'))
	return;
    document.getElementById("dummy").innerText = JSON.stringify(newData, null, 2);
}
