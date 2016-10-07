var paused = false;

$(function() {
    $("[name='pause-button']").bootstrapSwitch({
	onSwitchChange: function() {
	    paused = !paused;
	    if (paused) {
		$('#barChart').show();
		$('#mobile-optimized-barchart').hide();
		if (updatePlot && isMobile && isMobile.any && lastData && lastData.counts) {
		    updatePlot(lastData.counts);
		}
	    } else {
		$('#barChart').hide();
		$('#mobile-optimized-barchart').show();
	    }
	}
    });

    if (isMobile && isMobile.any) {
	$('#mobile-optimized-barchart').show();
	$('#barChart').hide();
    }
})
