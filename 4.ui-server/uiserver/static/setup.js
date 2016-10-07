var paused = false;

$(function() {
    $("[name='pause-button']").bootstrapSwitch({
	onSwitchChange: function() {paused = !paused;}
    });
})
