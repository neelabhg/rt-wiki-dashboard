package com.neelabhgupta.projects.rtwikidashboard.service;

import java.io.IOException;
import java.util.Date;

import org.json.JSONObject;
import org.pircbotx.Colors;
import org.pircbotx.PircBotX;
import org.pircbotx.exception.IrcException;
import org.pircbotx.exception.NickAlreadyInUseException;
import org.pircbotx.hooks.Listener;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.neelabhgupta.projects.rtwikidashboard.model.RecentChangesStats;
import com.neelabhgupta.projects.rtwikidashboard.model.WikiEditSummary;

@SuppressWarnings("rawtypes")
public class WikipediaChangesIrcReceiver extends ListenerAdapter implements Listener {
	
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(WikipediaChangesIrcReceiver.class);
	
	PircBotX bot;
	String ircChannel;
	BroadcastManager broadcastManager;
	
	public WikipediaChangesIrcReceiver(String ircChannel, BroadcastManager broadcastManager) {
		
		bot = new PircBotX();
		this.ircChannel = ircChannel;
		this.broadcastManager = broadcastManager;
		
		// Setup this bot
		/* Taken from https://code.google.com/p/pircbotx/source/browse/src/main/java/org/pircbotx/impl/PircBotXExample.java?name=1.9 */
		bot.setName("WikiChangesIRCListenerBot");
		bot.setLogin("LQ"); //login part of hostmask, eg name:login@host
        bot.setAutoNickChange(true); //Automatically change nick when the current one is in use
        bot.setCapEnabled(true); //Enable CAP features
        
        
        //bot.setVerbose(true); //Print everything, which is what you want to do 90% of the time
        
        
        //This class is a listener, so add it to the bots known listeners
        bot.getListenerManager().addListener(this);
		
        try {
			
			bot.connect("irc.wikimedia.org");
			bot.joinChannel(ircChannel);
			
		} catch (NickAlreadyInUseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (IrcException e) {
			e.printStackTrace();
		}
        
	}
	
	@Override
	public void onMessage(MessageEvent event) {
		String message = Colors.removeFormattingAndColors(event.getMessage());
		WikiEditSummary newEdit = new WikiEditSummary(message, new Date());
		
		// Only consider Anonymous changes
		if (newEdit.isValid() && newEdit.isAnon()) {
			RecentChangesStats.addRecentChange(newEdit);
			JSONObject broadcastJson = new JSONObject();
			JSONObject changesPerMinuteJSON = new JSONObject();
			changesPerMinuteJSON.put("changes_per_minute", RecentChangesStats.getChangesPerMinute());
			broadcastJson.put("recent_changes_stats", changesPerMinuteJSON);
			broadcastJson.put("new_edit", newEdit.toJSON());
			broadcastManager.broadcast("new_edit", broadcastJson);
		}
	}

}
