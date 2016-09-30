var plotData = [{
    type: 'bar',
    x: [0,0,0,0],
    y: ['2', '3-4', '5-9', '10+'],
    orientation: 'h'
}];

Plotly.newPlot('barChart', plotData);

function updatePlot(data) {
    var counts = _.reduce(data, function(one, agg) {
	agg[one.length]++;
	return agg;
    }, [0,0,0,0]);
    Plotly.restyle('barChart', 'x', counts);
}
