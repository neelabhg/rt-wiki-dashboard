var TimedResetCounter = function (element, interval) {
    
    var count;
    
    var init = (function() {
        
        count = 0;
        element.text(0);
        
        setInterval(function() {
            count = 0;
            element.text(0);
        }, interval);
        
    })();
    
    var increment = function() {
        count++;
        element.text(count);
    };
    
    return {
        increment: increment
    };
};
