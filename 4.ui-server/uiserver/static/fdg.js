// var width = 960,
//     height = 500,
//     padding = 1.5, // separation between same-color circles
//     clusterPadding = 12, // separation between different-color circles
//     radius = 10,
//     color = d3.scale.category20();


// // temp

// width=0; height=0;
// var clusters = {'blort': {cluster: 1, user: 'blort', radius: radius}};
// var nodes = [clusters['blort']];

// var force = d3.layout.force()
//     .nodes(nodes)
//     .size([width, height])
//     .gravity(.03)
//     .charge(0)
//     .on("tick", tick)
//     .start();

// var svg = d3.select("body").append("svg")
//     .attr("width", width)
//     .attr("height", height);

// var circle = svg.selectAll("circle")
//     .data(nodes)
//     .enter().append("circle")
//     .attr("r", function(d) { return d.radius; })
//     .style("fill", function(d) { return color(d.cluster); })
//     .call(force.drag);
// var force, circle;

// function updated3(newData) {
//     console.log('updating d3');
//     var keys = _.keys(newData);
//     if (keys.length == 0)
// 	return;
//     clusters = _.map(keys, function(k) { return {cluster: k, radius: radius}; });
//     console.log('keys', keys);
//     nodes = _.chain(keys)
// 	.map(function(k) {
// 	    return _.map(newData[k], function(val) {
// 		return {cluster: k, user: val, radius: radius};
// 	    })
// 	})
// 	.flatten()
// 	.value();
//     console.log('nodes: ', nodes);
//     if (nodes.length == 0)
// 	return;
    
//     force = d3.layout.force()
// 	.nodes(nodes)
// 	.size([width, height])
// 	.gravity(0.3)
// 	.charge(0)
// 	.on("tick", tick)
// 	.start();

//     circle = svg.selectAll("circle")
// 	.data(nodes)
// 	.enter().append("circle")
// 	.attr("r", function(d) { return d.radius; })
// 	.transition()
// 	.duration(300)
//         .style("fill", function(d) { return color(d.cluster); })
// 	.call(force.drag);

// }

// function tick(e) {
//     if (!circle)
// 	return;
//     circle
//         .each(cluster(10 * e.alpha * e.alpha))
// 	    .each(collide(.5))
// 		.attr("cx", function(d) { return d.x; })
//         .attr("cy", function(d) { return d.y; });
// }

// // Move d to be adjacent to the cluster node.
// function cluster(alpha) {
//     return function(d) {
// 	var cluster = clusters[d.cluster];
// 	if (cluster === d) return;
// 	var x = d.x - cluster.x,
// 	    y = d.y - cluster.y,
// 	    l = Math.sqrt(x * x + y * y),
// 	    r = d.radius + cluster.radius;
// 	if (l != r) {
// 	    l = (l - r) / l * alpha;
// 	    d.x -= x *= l;
// 	    d.y -= y *= l;
// 	    cluster.x += x;
// 	    cluster.y += y;
// 	}
//     };
// }

// // Resolves collisions between d and all other circles.
// function collide(alpha) {
//     var quadtree = d3.geom.quadtree(nodes);
//     return function(d) {
// 	var r = radius,
// 	    nx1 = d.x - r,
// 	    nx2 = d.x + r,
// 	    ny1 = d.y - r,
// 	    ny2 = d.y + r;
// 	quadtree.visit(function(quad, x1, y1, x2, y2) {
// 	    if (quad.point && (quad.point !== d)) {
// 		var x = d.x - quad.point.x,
// 		    y = d.y - quad.point.y,
// 		    l = Math.sqrt(x * x + y * y),
// 		    r = d.radius + quad.point.radius + (d.cluster === quad.point.cluster ? padding : clusterPadding);
// 		if (l < r) {
// 		    l = (l - r) / l * alpha;
// 		    d.x -= x *= l;
// 		    d.y -= y *= l;
// 		    quad.point.x += x;
// 		    quad.point.y += y;
// 		}
// 	    }
// 	    return x1 > nx2 || x2 < nx1 || y1 > ny2 || y2 < ny1;
// 	});
//     };
// }
