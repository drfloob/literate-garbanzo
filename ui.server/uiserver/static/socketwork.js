var socket = io('http://' + document.domain + ':' + location.port);

var timeoutMS = 700;
var timeoutHandle;
var paused = false;
var maxNodesToProcess = 1500;

socket.on('connect', function() {
    console.log("connected");
    // socket.emit('my event', {data: 'I\'m connected!'});    
    getCC();
});

socket.on('response', function(msg){
    console.log('response: ', msg);
});

socket.on('components', function(msg) {
    var newData = JSON.parse(msg.data);
    console.log('parsed', newData);
    var newDataSize = _.size(newData);
    if (newDataSize > maxNodesToProcess) {
	console.log("Nope! too much to do", newDataSize);
	return;
    }
    filteredKeys = _.filter(_.keys(newData), function(k) { return newData[k].length > 2; });
    filteredData = _.pick(newData, filteredKeys);
    console.log('filtered', filteredData);
    updatePulseGraph(filteredData);
    if (!paused)
	timeoutHandle = setTimeout(getCC, timeoutMS);
    updatePlot(newData);
});



socket.on('error', function(err) {
    console.log("error returned: ", err);
});

function sendMsg() {
    socket.emit('my event', {data: document.getElementById('input').value});
    return false;
}

function getCC() {
    console.log("in getCC");
    socket.emit('get cc');
    // socket.emit('my event', {data: 'test'});    
}


function togglePause() {
    if (paused) {
	paused = false;
	getCC();
    } else {
	paused = true;
	clearTimeout(timeoutHandle);
    }
}

function dummyPrint(newData) {
    if (_.has(newData, 'length'))
	return;
    document.getElementById("dummy").innerText = JSON.stringify(newData, null, 2);
}
