function makePlotData(arr) {
    return [{
	type: 'bar',
	x: arr,
	y: ['2', '3-4', '5-9', '10+'],
	marker: {
	    color: '#8c5675'
	},
	orientation: 'h'
    }];
}

var plotData = makePlotData([0,0,0,0]);

var plotlyLayout = {
    xaxis: {range: [0,350]},
    font: {
	family: 'Consolas, Courier New, monospace',
	size: 18
    },
    margin: {
	l: 50,
	b: 40,
	t: 40,
	r: 30
    }
};

Plotly.newPlot('barChart', plotData, plotlyLayout);

function updatePlot(data) {
    // console.log('updatePlot: data: ', data);
    var counts = [0,0,0,0];
    _.each(data, function(k, v, list) {
	if (k <= 2)
	    counts[0] += parseInt(v);
	else if (k <= 4)
	    counts[1] += parseInt(v);
	else if (k <= 9)
	    counts[2] += parseInt(v);
	else
	    counts[3] += parseInt(v);
    });

    Plotly.newPlot('barChart', makePlotData(counts), plotlyLayout);
}
