function create_layout(resize_function) {
	
	var outerLayout;    // the main layout
    var centerLayout;   // the nested center layout containing main views
    var innerEastLayout;    // the right layout containing the map, gauge and purchases
    var innerCenterLayout;  // the layout containing the graphs
    
	outerLayout = $('body').layout({
        livePaneResizing: true,
        
        onhide: resize_function,
        onshow: resize_function,
        onopen: resize_function,
        onclose: resize_function,
        onresize: resize_function,
        
        north__resizable: false,
        north__spacing_open: 0,
        north__spacing_closed: 20,
        
        south__resizable: false,
        south__initClosed: true,
        //south__spacing_open: 0,
        //south__spacing_closed: 20,
    });
    
    centerLayout = outerLayout.panes.center.layout({
        livePaneResizing: true,
        
        onhide: resize_function,
        onshow: resize_function,
        onopen: resize_function,
        onclose: resize_function,
        onresize: resize_function,
        
        center__paneSelector: ".ui-layout-center-center",
        center__size: "50%",
        east__paneSelector: ".ui-layout-center-east",
        east__size: "50%"
    });
    
    innerEastLayout = centerLayout.panes.east.layout({
        livePaneResizing: true,
        
        onhide: resize_function,
        onshow: resize_function,
        onopen: resize_function,
        onclose: resize_function,
        onresize: resize_function,
        
        north__paneSelector: ".ui-layout-center-center-north",
        north__size: 300,
        
        west__paneSelector: ".ui-layout-center-center-west",
        west__size: 200,
        west__maxSize: 200,
        
        center__paneSelector: ".ui-layout-center-center-center"
    });
    
    innerCenterLayout = centerLayout.panes.center.layout({
        livePaneResizing: true,
        
        onhide: resize_function,
        onshow: resize_function,
        onopen: resize_function,
        onclose: resize_function,
        onresize: resize_function,
        
        west__paneSelector: "#editsGraph",
        west__size: "50%",
        
        center__paneSelector: "#editSizeGraph",
        center__size: "50%",
    });
    
    return {
    	outerLayout: outerLayout,
    	centerLayout: centerLayout,
    	innerEastLayout: innerEastLayout,
    	innerCenterLayout: innerCenterLayout
    };
};