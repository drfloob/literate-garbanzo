var socket = io('http://' + document.domain + ':' + location.port);

var paused = false;
var maxNodesToProcess = 5000;
var displayedSize = 0;
var minDisplayedSizeForDemo = 25;

socket.on('connect', function() {
    resetZoom();
    console.log("connected");
});

socket.on('components', function(msg) {
    if (paused && displayedSize >= minDisplayedSizeForDemo)
	return;
    var newData = msg.data.new_val;

    // Note: summing the counts via reduce may be more efficient
    var newDataSize = _.size(newData.clusters);
    
    if (newDataSize > maxNodesToProcess) {
	console.log("Nope! too much to do", newDataSize);
	return;
    }

    
    displayedSize = newDataSize;
    updatePulseGraph(newData.clusters);
    updatePlot(newData.counts);
});


socket.on('error', function(err) {
    console.log("error returned: ", err);
});

function togglePause() {
    paused = !paused;
}
