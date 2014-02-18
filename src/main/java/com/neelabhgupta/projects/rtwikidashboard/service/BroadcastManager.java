package com.neelabhgupta.projects.rtwikidashboard.service;

import org.atmosphere.cpr.Broadcaster;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;

import com.neelabhgupta.projects.rtwikidashboard.model.RecentChangesStats;
import com.neelabhgupta.projects.rtwikidashboard.utils.AtmosphereUtils;

public class BroadcastManager {

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(BroadcastManager.class);
	
	String broadcasterChannel;
	Broadcaster broadcaster;
	
	public BroadcastManager(String broadcasterChannel) {
		this.broadcasterChannel = broadcasterChannel;
		this.broadcaster = AtmosphereUtils.getBroadcaster(broadcasterChannel);
	}
	
	public void broadcast(String message_type, JSONObject broadcastJson) {
		broadcastJson.put("type", message_type);
		broadcaster.broadcast(broadcastJson);
	}
	
	@Scheduled(fixedDelay=10000)
	public void broadcastStats() {
		broadcast("statistic_update", RecentChangesStats.getIntervalEditStats());
	}
}
