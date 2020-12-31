package jp.co.mgnc.business.time.dto;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

public class Time {
	private int timeCd;
	private String timeName;
	private LocalTime timeLimit;
	private static final String timeseparator = "～" ;
	private static int ClosingTimeIndex = 1;
	private static int TimeEndIndex = 5;
	
	public int getTimeCd() {
		return timeCd;
	}
	public void setTimeCd(int timeCd) {
		this.timeCd = timeCd;
	}
	public String getTimeName() {
		return timeName;
	}
	public void setTimeName(String timeName) {
		this.timeName = timeName;
		setTimeLimit(timeName);
	}
	private void setTimeLimit(String timeName) {
		try {
			String[] name = timeName.split(timeseparator);
			String tmp = name[ClosingTimeIndex];
			tmp = tmp.substring(0, TimeEndIndex) + ":00";
			timeLimit = LocalTime.parse(tmp,DateTimeFormatter.ofPattern("HH:mm:ss").withResolverStyle(ResolverStyle.STRICT));
		} catch(DateTimeParseException e) {
			e.printStackTrace();
		}
		return;
	}
	public LocalTime getTimeLimit() {
		return timeLimit;
	}
}
