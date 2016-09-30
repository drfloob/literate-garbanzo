var plotData = [{
    type: 'bar',
    x: [0,0,0,0],
    y: ['2', '3-4', '5-9', '10+'],
    orientation: 'h'
}];

var plotlyLayout = {xaxis: {range: [0,1000]}};

Plotly.newPlot('barChart', plotData, layout);

function updatePlot(data) {
    console.log('updatePlot: data: ', data);
    if (_.size(_.keys(data)) > 1000) {
	alert("NOPE!")
	return;
    }
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
    console.log('counts', counts);
    Plotly.newPlot('barChart', [{
	type: 'bar',
	x: counts,
	y: ['2', '3-4', '5-9', '10+'],
	orientation: 'h'
    }], plotlyLayout);
}
