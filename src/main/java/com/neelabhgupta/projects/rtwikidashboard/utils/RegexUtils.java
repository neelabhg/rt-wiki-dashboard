package com.neelabhgupta.projects.rtwikidashboard.utils;

import java.util.regex.Pattern;

public class RegexUtils {
	
	private static Pattern wikiEditSummaryRegexPattern = null;
	
	private static void compileWikiEditSummaryRegexPattern() {
		String regexString = "\\[\\[(.*?)\\]\\].*(http://.*?)\\s+\\*\\s+(.*?)\\s+\\*\\s+\\((.*?)\\)\\s+(.*)";
		wikiEditSummaryRegexPattern = Pattern.compile(regexString);
	}
	
	public static Pattern getWikiEditSummaryRegexPattern() {
		if (wikiEditSummaryRegexPattern == null) {
			compileWikiEditSummaryRegexPattern();
		}
		return wikiEditSummaryRegexPattern;
	}

}
