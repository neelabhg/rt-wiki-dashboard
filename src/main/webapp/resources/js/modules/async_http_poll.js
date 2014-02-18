var AsyncHttpPoll = function(onMessageReceived_callback, onStateChanged_callback, websocketUrl) {
	
	var socket;
	var subSocket;
	var transport;
	var fallbackTransport;
	var request;
	
	var connect = function() {
		socket.unsubscribe();
		subSocket = socket.subscribe(request);
	};
	
	var disconnect = function() {
		socket.unsubscribe();
	};
	
	var send = function(data) {
		subSocket.push(data);
	};
	
	var onMessage = function(response) {
		onStateChanged_callback(response);
		onMessageReceived_callback(response.responseBody);
	};
	
	var onOpen = function(response) {
		console.log('Atmosphere onOpen: Atmosphere connected using ' + response.transport);
		transport = response.transport;
		onStateChanged_callback(response);
    };
    
    var onReconnect = function (request, response) {
		console.log("Atmosphere onReconnect: Reconnecting");
		onStateChanged_callback(response);
    };
    
    var onClose = function(response) {
		console.log('Atmosphere onClose executed');
		onStateChanged_callback(response);
	};
	
	var onError = function(response) {
		onStateChanged_callback(response);
		if (response.transport != fallbackTransport) {
			
			console.log('Atmosphere onError: Switching to ' + fallbackTransport);
			request.transport = fallbackTransport;
			connect();
			
		} else {
			console.log('Atmosphere onError: Sorry, could not connect. The server might be down.');
		}
	};
	
	var init = (function() {
		
		socket = $.atmosphere;
		transport = 'websocket';
		fallbackTransport = 'long-polling';
		
		if (onMessageReceived_callback == null) {
			onMessageReceived_callback = function() {};
		}
		
		if (onStateChanged_callback == null) {
			onStateChanged_callback = function() {};
		}
		
		request = {
			url: websocketUrl,
			contentType : "application/json",
			logLevel : 'debug',
			maxReconnectOnClose: 2,
			transport : transport,
			fallbackTransport: fallbackTransport,
			onMessage: onMessage,
			onOpen: onOpen,
			onReconnect: onReconnect,
			onClose: onClose,
			onError: onError
		};
	})();
	
	return {
		connect: connect,
		disconnect: disconnect,
		send: send
	};
};
