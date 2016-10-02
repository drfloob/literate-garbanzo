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
    xaxis: {range: [0,1500]},
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
    var counts = _.reduce(data, function(agg, one) {
	var len = _.size(_.filter(one, function(e) {return e != null && e != "" && (!_.isNumber(e) || !isNaN(e))}))
	var idx = 0;
	if (len == 2)
	    idx = 0;
	else if (len <= 4)
	    idx = 1;
	else if (len <= 9)
	    idx = 2;
	else
	    idx = 3;
	
	agg[idx]++;
	return agg;
    }, [0,0,0,0]);
    // console.log('counts', counts);
    Plotly.newPlot('barChart', makePlotData(counts), plotlyLayout);
}
