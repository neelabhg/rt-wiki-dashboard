package com.neelabhgupta.projects.rtwikidashboard.model;

public class LocationInfo {
	
	private String city;
	private String country;
	private double latitude;
	private double longitude;
	
	public LocationInfo(String city, String country, double latitude, double longitude) {
		this.city = city;
		this.country = country;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public String getCity() {
		return city;
	}

	public String getCountry() {
		return country;
	}

	public double getLatitude() {
		return latitude;
	}

	public double getLongitude() {
		return longitude;
	}
}
