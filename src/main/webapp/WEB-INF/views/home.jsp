<%@ include file="/WEB-INF/views/includes/taglibs.jsp"%>

<!DOCTYPE HTML>
<html>
<head>
    <title>Real-Time Wikipedia edits</title>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
    
    <link rel="stylesheet" href="<c:url value='resources/css/layout-default-latest.css'/>" type="text/css">
    <link rel="stylesheet" href="<c:url value='resources/css/bootstrap.min.css'/>" type="text/css">
    <link rel="stylesheet" href="<c:url value='resources/css/jquery-jvectormap-1.2.2.css'/>" type="text/css">
    <link rel="stylesheet" href="<c:url value='resources/css/rickshaw.min.css'/>" type="text/css">
    <link rel="stylesheet" href="<c:url value='resources/css/main.css'/>" type="text/css">
    
    <script src="<c:url value='resources/js/lib/jquery-1.10.2.min.js'/>"></script>
    <script src="<c:url value='resources/js/lib/jquery-ui-1.10.3.custom.min.js'/>"></script>
    <script src="<c:url value='resources/js/lib/jquery.layout-latest.min.js'/>"></script>
    <script src="<c:url value='resources/js/lib/jquery.atmosphere-min.js'/>"></script>
    <script src="<c:url value='resources/js/lib/bootstrap.min.js'/>"></script>
    <script src="<c:url value='resources/js/lib/jquery-jvectormap-1.2.2.min.js'/>"></script>
    <script src="<c:url value='resources/js/jvectormaps-maps/jquery-jvectormap-world-mill-en.js'/>"></script>
    <script src="<c:url value='resources/js/lib/raphael.2.1.0.min.js'/>"></script>
    <script src="<c:url value='resources/js/lib/justgage.1.0.1.min.js'/>"></script>
    <script src="<c:url value='resources/js/lib/rickshaw/vendor/d3.v2.js'/>"></script>
    <script src="<c:url value='resources/js/lib/rickshaw/rickshaw.min.js'/>"></script>
    
    <script src="<c:url value='resources/js/modules/async_http_poll.js'/>"></script>
    <script src="<c:url value='resources/js/modules/graph.js'/>"></script>
    <script src="<c:url value='resources/js/modules/timed_counter.js'/>"></script>
    <script src="<c:url value='resources/js/modules/timed_reset_counter.js'/>"></script>
    
    <script src="<c:url value='resources/js/multi_pane_layout_wrapper.js'/>"></script>
</head>
<body>

<div class="ui-layout-north">
    <nav id="title-bar" class="navbar navbar-default" role="navigation">
        <div class="navbar-header">
            <a class="navbar-brand" href="#">Real-Time Wikipedia edits</a>
        </div>
        <p class="navbar-text">Monitor edits to Wikipedia English by anonymous users</p>
        <ul class="nav navbar-nav navbar-right">
            <li><a href="#" data-toggle="modal" data-target="#helpModal">Help</a></li>
			<li><a href="#" data-toggle="modal" data-target="#aboutModal">About</a></li>
        </ul>
        
    </nav>
</div>

<div class="ui-layout-center">

    <div class="ui-layout-center-center">
        <div id="editsGraph" class="ui-layout-graph-pane"></div>
        <div id="editSizeGraph" class="ui-layout-graph-pane"></div>
    </div>
    
    <div class="ui-layout-center-east">
    
        <div class="ui-layout-center-center-north">
            <div id="world-map"></div>
        </div>
        
        <div class="ui-layout-center-center-west">
            <div id="avgEditsPerMinGauge" style="width:190px;height:150px;"></div>
            <div id="edit_stats">
                <table class="table table-condensed">
                <tr>
                    <td>Latest edit</td>
                    <td><span id="latest_edit_timer">0</span> seconds ago</td>
                </tr>
                <tr>
                    <td>Edits in the current minute</td>
                    <td id="last_min_edits_counter">0</td>
                </tr>
                <tr>
                    <td></td>
                    <td></td>
                </tr>
            </table>
            </div>
        </div>
        
        <div class="ui-layout-center-center-center">
            <table class="table table-striped table-condensed">
                <tr id="edit-event-table-heading">
                    <th>Page</th>
                    <th>Date</th>
                    <th>Edit size</th>
                    <th>Summary of Edit</th>
                    <th>Location</th>
                </tr>
            </table>
        </div>
        
    </div>
    
</div>

<div class="ui-layout-south">
    <div class="col-lg-2">
        <button id="connect-button" type="button" class="btn btn-success btn-mini">Connect</button>
        <button id="disconnect-button" type="button" class="btn btn-danger btn-mini">Disconnect</button>
    </div>
    <div id="connection_stats" class="row">
        <span id="async_connection_status"></span>
        <span>| Last message received: <span id="last_msg_received_timer">0</span> second(s) ago</span>
    </div>
</div>

<!-- Modal -->
<div class="modal fade" id="aboutModal" tabindex="-1" role="dialog" aria-labelledby="aboutModalLabel" aria-hidden="true">
    <div class="modal-dialog">
	    <div class="modal-content">
			<div class="modal-header">
			    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
			    <h4 class="modal-title" id="aboutModalLabel">About</h4>
			</div>
			<div class="modal-body">
			    
			    <p>
				    The Wikimedia Foundation publishes a lot of open data, available <a href="http://meta.wikimedia.org/wiki/Research:Data">here</a>.
				    They also publish live Recent changes feeds through <abbr title="Internet Relay Chat">IRC</abbr> which show edits on Wikimedia wikis automatically as they happen.
				    <br />
				    Here, those edits made on Wikipedia English by anonymous (unregistered) users are shown.
				    <br />
				    Only anonymous edits are shown because the location of an edit is identified from the IP address of the user.
				    The IP address is only included in the IRC feed if the user is unregistered; if the user is registered, the username is shown instead.
				    <br />
				    Visit the <a href="https://github.com/neelabhg/rt-wiki-dashboard">project page on GitHub</a> to look at the implementation details along with the source code.
				    <br />
                    Also see <a href="http://hatnote.com/">Hatnote</a>, another great Wikipedia Recent Changes Map.
			    </p>
                <small>
                    &copy; 2013 <a href="http://neelabhgupta.com/">Neelabh Gupta</a>
                    <br />
                    This project includes GeoLite2 data created by MaxMind, available from
                    <a href="http://www.maxmind.com">http://www.maxmind.com</a>.
                </small>
                
			</div>
			<div class="modal-footer">
			    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
			</div>
	    </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<!-- Modal -->
<div class="modal fade" id="helpModal" tabindex="-1" role="dialog" aria-labelledby="helpModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="helpModalLabel">Help</h4>
            </div>
            <div class="modal-body">
                    Table columns:
                    <ul>
                        <li>Page: The title of the Wikipedia page edited. Clicking on it will take you to the diff page,
                            which shows the page before and after the edit and highlights the difference.</li>
                        <li>Edit-size: The net number of characters added (will be negative for deletions).</li>
                    </ul>
                    Graphs:
                    <ul>
                        <li>Number of edits: This graph shows the total number of edits in 10 second intervals.</li>
                        <li>Size of edits: This graph shows the total size of edits in 10 second intervals.</li>
                    </ul>
                    Warning: The graphs' y-axis scales adapt to incoming data - the scales may change!
                    <br />
                    Note: Click the bar at the very bottom to view connection details.
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<script type="text/javascript">
$(function() {
    var mapObject;  // the real-time purchase map
    var gauge;  // the transactions-per-minute gauge
    
    var layout_panes;
    
    var edits_graph;
    var editSize_graph;
    
    var markerIdx = 0;
    
    var last_msg_received_timer;
    var latest_edit_timer;
    var last_min_edits_counter;
    
    var DEBUG = true;  //flag to enable/disable logging to the javascript console
    
    var asyncHttpPollObject;
    
    var websocketUrl = "${fn:replace(r.requestURL, r.requestURI, '')}${r.contextPath}/async";
    
    function onMessageReceived(data) {
    	last_msg_received_timer.reset();
        var json;
        try {
            json = $.parseJSON(data);
        } catch(err) {
            console.log("JSON parse error: " + err);
            console.log("When trying to parse: " + data);
        }
        if (json['type']) {   
            if (json['type'] == 'new_edit') {
                handleWikiEditEvent(json);
            }
            else if (json['type'] == 'statistic_update') {
            	handleStats(json);
            }
            else {
                console.log("Unknown JSON object of type: " + json['type']);
            }
        } else {
            console.log("Unknown JSON object: No 'type' property found.");
            console.log(json);
        }
    }
    
    function onStateChanged(response) {
        $('#async_connection_status').text('Connection status: ' + response.state + ' | Connected using: ' + response.transport);
    }
    
    $('#connect-button').click(function() {
    	asyncHttpPollObject.connect();
    });
    $('#disconnect-button').click(function() {
    	asyncHttpPollObject.disconnect();
    });
    
    function handleWikiEditEvent(wikiEditEventJson) {
    	
        latest_edit_timer.reset();
        last_min_edits_counter.increment();
        
        var page_title_url = $('<a>', {
            href: wikiEditEventJson.new_edit.diff_url,
            text: wikiEditEventJson.new_edit.page_title,
            target: '_blank'
        });
        
        var tr = $('<tr>').append(
            $('<td>').html(page_title_url),
            $('<td>').text(wikiEditEventJson.new_edit.edit_date),
            $('<td>').text(wikiEditEventJson.new_edit.edit_size),
            $('<td>').text(wikiEditEventJson.new_edit.edit_summary),
            $('<td>').text(wikiEditEventJson.new_edit.anonUserLocation.city + ", " + wikiEditEventJson.new_edit.anonUserLocation.country)
        );
        
        $(tr).hide();
        $("#edit-event-table-heading").after(tr);
        $(tr).fadeIn();
        
		mapObject.addMarker(
		    markerIdx,
		    {latLng: [wikiEditEventJson.new_edit.anonUserLocation.latitude, wikiEditEventJson.new_edit.anonUserLocation.longitude],
		    name:"\"" + wikiEditEventJson.new_edit.page_title + "\"" + " edited in " + wikiEditEventJson.new_edit.anonUserLocation.city + ", " + wikiEditEventJson.new_edit.anonUserLocation.country}
		);
        
	    gauge.refresh(wikiEditEventJson.recent_changes_stats.changes_per_minute);
    }
    
    function handleStats(statsJson) {
        edits_graph.add_data_point([statsJson.time, statsJson.num_edits]);
        editSize_graph.add_data_point([statsJson.time, statsJson.edit_size]);
    }
    
    var resize_elements = function() {
        $("#world-map").height(layout_panes.innerEastLayout.state.north.size);
        mapObject.setSize();
        edits_graph.resize();
        editSize_graph.resize();
    };
    
    var init_graphs = function() {
    	
    	edits_graph = new Graph('editsGraph', "Number of edits", 'line', 'steelblue', ${numGraphDataPoints});
        editSize_graph = new Graph('editSizeGraph', "Size of edits", 'bar', 'DarkOliveGreen', ${numGraphDataPoints});
        
    	<c:if test="${not empty statsCache}">
	    	var statsJson;
	        <c:forEach var="stats_json" items="${statsCache}">
	            statsJson = ${stats_json};
	            edits_graph.add_data_point([statsJson.time, statsJson.num_edits]);
	            editSize_graph.add_data_point([statsJson.time, statsJson.edit_size]);
	        </c:forEach>
    	</c:if>
    };
    
    var init = (function() {
    	// enable/disable logging depending on debug flag and availability of javascript console in browser
        if (!window.console || !DEBUG) {
            console = {log: function() {}};
        }
        
        layout_panes = create_layout(resize_elements);
        
        last_msg_received_timer = new TimedCounter($('#last_msg_received_timer'), 1000);
        latest_edit_timer = new TimedCounter($('#latest_edit_timer'), 1000);
        last_min_edits_counter = new TimedResetCounter($('#last_min_edits_counter'), 60000);
        
        gauge = new JustGage({
            id: "avgEditsPerMinGauge",
            value: 0,
            min: 0,
            max: 100,
            title: "Avg edits per min",
            relativeGaugeSize: true
        });
        
        $('#world-map').vectorMap({
            markerStyle: {
                initial: {
                    fill: 'yellow',
                    stroke: '#383f47',
                    r: '10'
                }
            }
        });
        mapObject = $('#world-map').vectorMap('get', 'mapObject');
        
        init_graphs();
        asyncHttpPollObject = AsyncHttpPoll(onMessageReceived, onStateChanged, websocketUrl);
        asyncHttpPollObject.connect();
        resize_elements();
    })();
});
</script>
</body>
</html>