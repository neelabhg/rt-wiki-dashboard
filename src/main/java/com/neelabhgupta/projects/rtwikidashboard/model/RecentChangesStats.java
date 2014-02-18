package com.neelabhgupta.projects.rtwikidashboard.model;

import java.util.Date;

import org.apache.commons.collections.Buffer;
import org.apache.commons.collections.BufferUtils;
import org.apache.commons.collections.buffer.CircularFifoBuffer;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RecentChangesStats {
	
	private static final Logger logger = LoggerFactory.getLogger(RecentChangesStats.class);
	private static Date serverStart;
	private static long numEvents;
	
	private static long num_edits_in_interval;
	private static long edit_size_in_interval;
	
	private static Buffer statsCache;;
	
	public static final int STATS_CACHE_SIZE = 40;
	
	public static void reset() {
		logger.info("Stats reset");
		serverStart = new Date();
		numEvents = 0;
		
		num_edits_in_interval = 0;
		edit_size_in_interval = 0;
		
		statsCache = BufferUtils.synchronizedBuffer(new CircularFifoBuffer(STATS_CACHE_SIZE));
	}
	
	public static void addRecentChange(WikiEditSummary event) {
		numEvents++;
		num_edits_in_interval++;
		edit_size_in_interval += Math.abs(event.getEditSize());
	}
	
	public static long getChangesPerMinute() {
		long minutes_elapsed = ((new Date().getTime()) - serverStart.getTime()) / (60 * 1000);
		return numEvents / minutes_elapsed;
	}
	
	@SuppressWarnings("unchecked")
	public static JSONObject getIntervalEditStats() {
		JSONObject json = new JSONObject();
		json.put("num_edits", num_edits_in_interval);
		json.put("edit_size", edit_size_in_interval);
		json.put("time", System.currentTimeMillis());
		num_edits_in_interval = 0;
		edit_size_in_interval = 0;
		statsCache.add(json);
		return json;
	}

	public static Buffer getStatsCache() {
		return statsCache;
	}
}
