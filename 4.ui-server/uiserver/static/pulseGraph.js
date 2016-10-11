var colorHash = new ColorHash();
var nodeSize = 9;
var edgeSize = 2;
var nodeOpacity = 0.42;
var jitterScale = 30;

var s = new sigma({settings: {
    autoRescale: false,
    autoResize: false,
    autoScale: false
}});

s.addRenderer({
    type: 'canvas',
    container: 'sigma_container'
});


function resetZoom() {
    sigma.misc.animation.camera(
	s.camera,
	{
	    ratio: 1,
	    angle: 0,
	    x: s.renderers[0].width/2,
	    y: s.renderers[0].height/2
	}, {duration: 400}
    );
}

function nodeClicked(node) {
    var neighborhood = s.graph.neighborhood(node.data.node.masterNode);
    var namesArray = _.map(neighborhood.nodes, function(n) { return n.id;});
    // console.log('namesArray', namesArray);
    // alert(JSON.stringify(namesArray, null, 2));
    
    $('#clusterModal .modal-body').html("<ul>" + _.map(namesArray, function(n) { return "<li>"+n+"</li>";}).join("") + "</ul>");
    $('#clusterModal').modal();
}

s.bind("clickNode", nodeClicked);

// from http://erlycoder.com/49/javascript-hash-functions-to-convert-string-into-integer-hash-
djb2Code = function(str){
    var hash = 5381;
    for (i = 0; i < str.length; i++) {
	char = str.charCodeAt(i);
	hash = ((hash << 5) + hash) + char; /* hash * 33 + c */
    }
    return hash;
}

clusterJitter = function(str) {
    return ((djb2Code(str) % 512 - 255) / 255) / jitterScale;
}

function makeUserCoords(user, center) {
    var r = (djb2Code("length-"+user) % 255 / 255) * jitterScale
    var phi = (djb2Code(user+"-angle!!!") % 255 / 255) * 2 * Math.PI;
    return {
	x: r*Math.cos(phi) + center.x,
	y: r*Math.sin(phi) + center.y
    }; 
}

function addNode(user, master) {
    var color = colorHash.rgb(master);
    var center = {
	x: color[0]/255 * s.renderers[0].width,
	y: color[1]/255 * s.renderers[0].height
    };
    var coords = makeUserCoords(user, center);
    var rgba="rgba("+color[0]+","+color[1]+","+color[2]+","+nodeOpacity+")";
    s.graph.addNode({
	id: user,
	size: nodeSize,
	color: rgba,
	x: coords.x,
	y: coords.y,
	masterNode: master
    });
}

function updatePulseGraph(data) {
    s.graph.clear();
    _.each(data, function(val, key){
	// must establish first node for edge creation
	addNode(key, key);
	_.each(val, function(user, idx) {
	    if (user == key)
		return;
	    addNode(user, key);
	    s.graph.addEdge({
		id: key + "_" + user,
		source: user,
		target: key,
		size: edgeSize
	    });
	});
    });
    s.refresh();
}
