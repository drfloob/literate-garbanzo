var colorHash = new ColorHash();
var nodeSize = 0.5;

var s = new sigma();
s.addRenderer({type: 'canvas', container: 'sigma_container'});
// s.graph.addNode({id: 0, label: "drfloob", size: nodeSize, color: colorHash.hex("drfloob")});
// s.refresh();

function resetZoom() {
    s.camera.goTo({x: 0, y: 0, ratio: 1})
}

function nodeClicked(node) {
    console.log('node', node);
    var neighborhood = s.graph.neighborhood(node.data.node.masterNode);
    console.log(_.map(neighborhood.nodes, function(n) { return n.id;}));
}

s.bind("clickNode", nodeClicked);

function updatePulseGraph(data) {
    s.graph.clear();
    _.each(data, function(val, key){
	var color = colorHash.hex(key);
	var color_xy = colorHash.rgb(key);
	var x = color_xy[0]/255;
	var y = color_xy[1]/255;
	var jitterScale = 20;
	
	// must establish first node for edge creation
	s.graph.addNode({
	    id: key,
	    size: nodeSize,
	    color: color,
	    x: x + Math.random()/jitterScale,
	    y: y + Math.random()/jitterScale,
	    masterNode: key
	});
	_.each(val, function(user, idx) {
	    if (user == key)
		return;
	    s.graph.addNode({
		id: user,
		size: nodeSize,
		color: color,
		x: x + Math.random()/jitterScale,
		y: y + Math.random()/jitterScale,
		masterNode: key
	    });
	    s.graph.addEdge({
		id: key + "_" + user,
		source: user,
		target: key
	    });
	});
    });
    s.refresh();
}
