package com.neelabhgupta.projects.rtwikidashboard.utils;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import com.neelabhgupta.projects.rtwikidashboard.model.LocationInfo;

public class LocationUtils {
	
	
	private static final Logger logger = LoggerFactory.getLogger(LocationUtils.class);
	
	private static DatabaseReader dbReader;
	
	public static void loadDB(String maxMindDBFilename) {
		logger.info("Creating DatabaseReader from DB file: " + maxMindDBFilename);
		ClassPathResource cpr = new ClassPathResource(maxMindDBFilename);
		try {
			dbReader = new DatabaseReader.Builder(cpr.getFile()).build();
		} catch (IOException e) {
			logger.error("ERROR");
			e.printStackTrace();
		}
		logger.info("DatabaseReader created successfully");
	}

	public static DatabaseReader getDbReader() {
		return dbReader;
	}
	
	public static LocationInfo getLocationInfo(String ipAddr) {
		
		CityResponse rsp;
		
		try {
			rsp = dbReader.city(InetAddress.getByName(ipAddr));
			
			return new LocationInfo(
					rsp.getCity().getName(),
					rsp.getCountry().getName(),
					rsp.getLocation().getLatitude(),
					rsp.getLocation().getLongitude()
			);
		} catch (UnknownHostException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		} catch (GeoIp2Exception e) {
			logger.error(e.getMessage());
		}
		
		return null;
	}
}
