var TimedCounter = function (element, interval) {
	
	var time_elapsed;
	var timer_obj;
	
	var init = (function() {
		time_elapsed = 0;
		element.text(0);
		timer_obj = setInterval(update, interval);
	})();
	
	var update = function() {
		element.text(time_elapsed);
		time_elapsed++;
	};
	
	var reset = function() {
		time_elapsed = 0;
		element.text(0);
		clearInterval(timer_obj);
		timer_obj = setInterval(update, interval);
	};
	
	return {
		reset: reset
	};
};
