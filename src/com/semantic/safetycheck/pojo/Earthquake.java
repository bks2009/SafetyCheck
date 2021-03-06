package com.semantic.safetycheck.pojo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

import org.eclipse.jetty.util.log.Log;

public class Earthquake {

	private String[] formatStrings = {"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
			"yyyy-MM-dd'T'HH:mm:ss'Z'"};
	private String id;
	private Float magnitude;
	private Date time;
	private Float latitude;
	private Float longitude;
	private String desc;

	public Earthquake() {
		super();
	}

	public Earthquake(String id, Float magnitude, String time, Float latitude,
			Float longitude, String desc) {
		super();
		if (id.indexOf("#") == -1) {
			this.id = id;
		} else {
			this.id = id.substring(id.indexOf("#"));
		}
		this.magnitude = magnitude;
		this.time = parseTime(time);
		this.latitude = latitude;
		this.longitude = longitude;
		this.desc = desc;
	}

	public Earthquake(Float magnitude, String time, Float latitude,
			Float longitude, String desc) {
		super();
		this.magnitude = magnitude;
		this.time = parseTime(time);
		this.latitude = latitude;
		this.longitude = longitude;
		this.desc = desc;
	}

	private Date parseTime(String time) {
		// 2015-11-12T00:22:32.520Z
		// 2016-08-22T04:48:42Z
		for (String formatString : formatStrings) {
			try {
				return new SimpleDateFormat(formatString).parse(time);
				// System.out.println(date.getTime());
			} catch (ParseException e) {
				Log.debug(e.getMessage());
				// e.printStackTrace();
			}
		}
		System.out.println("Couldn't parse:" + time);
		Log.warn("Couldn't parse:" + time);
		return null;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Float getMagnitude() {
		return magnitude;
	}
	public void setMagnitude(Float magnitude) {
		this.magnitude = magnitude;
	}
	public String getTimeAsFormat() {
		return new SimpleDateFormat(formatStrings[0]).format(time);
	}
	public Date getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = new Date(Long.parseLong(time));
		// this.time = parseTime(time);
	}
	public Float getLatitude() {
		return latitude;
	}
	public void setLatitude(Float latitude) {
		this.latitude = latitude;
	}
	public Float getLongitude() {
		return longitude;
	}
	public void setLongitude(Float longitude) {
		this.longitude = longitude;
	}

	public static void main(String[] args) {
		new Earthquake().parseTime("2015-11-12T00:22:32.520Z");
	}

	public static Comparator<Earthquake> EQTimeComparator = new Comparator<Earthquake>() {

		public int compare(Earthquake eq1, Earthquake eq2) {
			return eq2.getTime().compareTo(eq1.getTime());
		}
	};

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

}
