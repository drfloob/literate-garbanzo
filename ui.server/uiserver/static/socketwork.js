var socket = io('http://' + document.domain + ':' + location.port);

var timeoutMS = 700;
var timeoutHandle;
var paused = false;

socket.on('connect', function() {
    console.log("connected");
    // socket.emit('my event', {data: 'I\'m connected!'});    
    getCC();
});

socket.on('response', function(msg){
    console.log('response: ', msg);
});

socket.on('components', function(msg) {
    // console.log('components', msg);
    // console.log('value', msg.data);
    var newData = JSON.parse(msg.data);
    console.log('parsed', newData);
    // dummyPrint(newData);
    updatePulseGraph(newData);
    if (!paused)
	timeoutHandle = setTimeout(getCC, timeoutMS);
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
