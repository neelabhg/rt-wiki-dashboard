var Graph = function(graph_container_id, graph_title, graph_style, graph_color, max_data_points) {
	
	var series_data;
	var graph_handles;
	var page_elements;
	var num_data_points;
	
	var generate_divs = function() {
		
		page_elements.container = $('#' + graph_container_id);
		
		title_text = $('<h5>', {
			class: 'text-center text-muted',
			text: graph_title
		});
		
		page_elements.title = $('<div>', {
            id: graph_container_id + '_title',
            class: "graph_title",
            html: title_text
        });
		
		page_elements.legend = $('<div>', {
            id: graph_container_id + '_legend',
            class: "graph_legend",
        });
		
		page_elements.chart_container = $('<div>', {
            id: graph_container_id + '_chart_container',
            class: "graph_chart_container",
        });
		
		page_elements.chart = $('<div>', {
            id: graph_container_id + '_chart',
            class: "graph_chart",
        });
		
		page_elements.x_axis = $('<div>', {
            id: graph_container_id + '_x_axis',
            class: "graph_x_axis",
        });
		
		page_elements.y_axis = $('<div>', {
            id: graph_container_id + '_y_axis',
            class: "graph_y_axis",
        });
		
		page_elements.chart_container.append(
			page_elements.y_axis,
			page_elements.chart,
			page_elements.x_axis
		);
		
		page_elements.container.append(
			page_elements.title,
			page_elements.chart_container,
			page_elements.legend
		);
		
	};
	
	var init = (function() {
		series_data = [ [{x:0, y:0}] ];
		num_data_points = 0;
		page_elements = {};
		graph_handles = {};
		generate_divs();
		
		
		graph_handles.graph_obj = new Rickshaw.Graph({
			element: page_elements.chart[0],
			renderer: graph_style,
			series:
			[
				{
					color: graph_color,
					data: series_data[0],
					name: graph_container_id
				}
            ]
		});
		
		graph_handles.x_axis = new Rickshaw.Graph.Axis.X({
	        graph: graph_handles.graph_obj,
	        orientation: 'bottom',
	        element: page_elements.x_axis[0],
	        pixelsPerTick: 100,
	        tickFormat: function(n) { return (new Date(n)).toLocaleTimeString(); }
	    });
	    
		graph_handles.y_axis = new Rickshaw.Graph.Axis.Y({
	        graph: graph_handles.graph_obj,
	        orientation: 'left',
	        element: page_elements.y_axis[0]
	    });
	    
		/*graph_handles.legend = new Rickshaw.Graph.Legend({
	    	graph: graph_handles.graph_obj,
	        element: page_elements.legend[0]
	    });*/
		
	    graph_handles.graph_obj.render();
	    
	})();
	
	var resize = function() {
		var chart_container_extra_width = page_elements.chart_container.outerWidth(true) - page_elements.chart_container.width();
		var legend_width = 0; //page_elements.legend.outerWidth(true);
		// Subtracting 10 because outerWidth(true) is not including padding somehow
		var new_chart_container_width = page_elements.container.width() - legend_width - chart_container_extra_width - 10;
		var new_chart_container_height = page_elements.container.height() - page_elements.title.outerHeight(true);
		page_elements.chart_container.width(new_chart_container_width);
		page_elements.chart_container.height(new_chart_container_height);
		graph_handles.graph_obj.configure({
			width: new_chart_container_width - page_elements.y_axis.outerWidth(true),
			height: new_chart_container_height - page_elements.x_axis.outerHeight(true) - 20
		});
		graph_handles.graph_obj.render();
		
	};
	
	var add_data_point = function(data) {
		num_data_points++;
		if (num_data_points == 1 || series_data[0].length >= max_data_points) {
			for (var i = 0; i < series_data.length; i++) {
				series_data[i].shift();
            }
        }
		series_data[0].push({ x: data[0], y: data[1] });
		graph_handles.graph_obj.update();
	};
	
	return {
		add_data_point: add_data_point,
		resize: resize
	};
};
