var colorHash = new ColorHash();
var nodeSize = 0.3;
var nodeOpacity = 0.42;

var s = new sigma();
s.addRenderer({
    type: 'canvas',
    container: 'sigma_container',
    settings: {
	minEdgeSize: 1,
	minNodeSize: 0.3,
	autoRescale: false
    }
});


function resetZoom() {
    s.camera.goTo({x: 0, y: 0, ratio: 1})
}

function nodeClicked(node) {
    console.log('node', node);
    var neighborhood = s.graph.neighborhood(node.data.node.masterNode);
    console.log("neighborhood.nodes", neighborhood.nodes);
    var namesArray = _.map(neighborhood.nodes, function(n) { return n.id;});
    console.log('namesArray', namesArray);
    alert(JSON.stringify(namesArray, null, 2));
}

s.bind("clickNode", nodeClicked);


function addNode(user, master) {
    var color = colorHash.rgb(master);
    var jitterScale = 15;
    var x = (color[0]/255 + Math.random()/jitterScale) * s.renderers[0].width;
    var y = (color[1]/255 + Math.random()/jitterScale) * s.renderers[0].height;
    var rgba="rgba("+color[0]+","+color[1]+","+color[2]+","+nodeOpacity+")";
    s.graph.addNode({
	id: user,
	size: nodeSize,
	color: rgba,
	x: x,
	y: y,
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
		size: 2
	    });
	});
    });
    s.refresh();
}
