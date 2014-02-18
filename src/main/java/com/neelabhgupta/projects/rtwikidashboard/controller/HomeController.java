package com.neelabhgupta.projects.rtwikidashboard.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.collections.Buffer;
import org.atmosphere.cpr.AtmosphereResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.neelabhgupta.projects.rtwikidashboard.model.RecentChangesStats;
import com.neelabhgupta.projects.rtwikidashboard.utils.AtmosphereUtils;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	private final String BROADCAST_CHANNEL = "en_wikipedia_recent_changes";
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		String formattedDate = dateFormat.format(date);
		model.addAttribute("serverTime", formattedDate );
		
		model.addAttribute("numGraphDataPoints", RecentChangesStats.STATS_CACHE_SIZE);
		Buffer statsCache = RecentChangesStats.getStatsCache();
		if (statsCache.size() == RecentChangesStats.STATS_CACHE_SIZE) {
			model.addAttribute("statsCache", statsCache);
		}
		
		return "home";
	}
	
	@RequestMapping(value = "/async", method = RequestMethod.GET)
	@ResponseBody
	public void atmosphere_get(final AtmosphereResource event) {
		AtmosphereUtils.suspend(event);
		AtmosphereUtils.getBroadcaster(BROADCAST_CHANNEL).addAtmosphereResource(event);
	}
	
	@RequestMapping(value = "/async", method = RequestMethod.POST)
	@ResponseBody
	public void atmosphere_post(final AtmosphereResource event, @RequestBody String message) {
		logger.info("Received message to broadcast: {}", message);
	}
}
