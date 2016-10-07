var paused = false;

$(function() {
    $("[name='pause-button']").bootstrapSwitch({
	onSwitchChange: function() {
	    paused = !paused;
	    if (updatePlot && isMobile && isMobile.any && lastData && lastData.counts) {
		if (paused) {
		    updatePlot(lastData.counts);
		    $('#barChart').show();
		    $('#mobile-optimized-barchart').hide();
		} else {
		    $('#barChart').hide();
		    $('#mobile-optimized-barchart').show();
		}
	    }
	}
    });

    if (isMobile && isMobile.any) {
	$('#mobile-optimized-barchart').show();
	$('#barChart').hide();
    }
})
