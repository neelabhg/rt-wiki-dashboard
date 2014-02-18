package com.neelabhgupta.projects.rtwikidashboard.model;

import java.util.Date;
import java.util.regex.Matcher;

import org.apache.commons.validator.routines.InetAddressValidator;
import org.json.JSONObject;

import com.neelabhgupta.projects.rtwikidashboard.utils.LocationUtils;
import com.neelabhgupta.projects.rtwikidashboard.utils.RegexUtils;

public class WikiEditSummary {
	
	private String pageTitle;
	private String diffUrl;
	private String user;
	private int editSize;
	private String editSummary;
	private Date editDate;
	
	private LocationInfo anonUserLocation;
	
	private String rawIrcMessageString;
	private boolean isValid;
	private boolean isAnon;
	private JSONObject json;
	
	public WikiEditSummary(String ircMessage, Date ircMessageReceivedDate) {
		
		rawIrcMessageString = ircMessage;
		editDate = ircMessageReceivedDate;
		
		Matcher matcher = RegexUtils.getWikiEditSummaryRegexPattern()
									.matcher(rawIrcMessageString);
		
		if (!matcher.find()) {
			isValid = false;
		} else {
			isValid = true;
			pageTitle = matcher.group(1);
			diffUrl = matcher.group(2);
			user = matcher.group(3);
			try {
				editSize = Integer.parseInt(matcher.group(4));
			} catch (NumberFormatException e) {
				isValid = false;
			}
			editSummary = matcher.group(5);
			
			// If the "user" is a valid IP Address, then the user is assumed to be anonymous
			isAnon = InetAddressValidator.getInstance().isValid(user);
			
			if (isAnon) {
				anonUserLocation = LocationUtils.getLocationInfo(user);
			}
		}
		
		generateJson();
	}
	
	private void generateJson() {
		if (isValid) {
			json = new JSONObject()
				.put("page_title", pageTitle)
				.put("diff_url", diffUrl)
				.put("user", user)
				.put("edit_size", editSize)
				.put("edit_summary", editSummary)
				.put("edit_date", editDate)
				.put("isAnon", isAnon);
			
			if (isAnon) {
				json.put("anonUserLocation", new JSONObject(anonUserLocation));
			}
			
		} else {
			json = new JSONObject()
				.put("raw_irc_message", rawIrcMessageString)
				.put("edit_date", editDate);
		}
	}
	
	public JSONObject toJSON() {
		return json;
	}
	
	public String toString() {
		return json.toString();
	}

	public boolean isValid() {
		return isValid;
	}

	public boolean isAnon() {
		return isAnon;
	}

	public int getEditSize() {
		return editSize;
	}

}
