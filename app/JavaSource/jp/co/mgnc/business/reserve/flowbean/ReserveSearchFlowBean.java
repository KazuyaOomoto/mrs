package jp.co.mgnc.business.reserve.flowbean;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ReserveSearchFlowBean {
	private LocalDate ldate;
	private int timeCd;
	private int roomCd;
	private String date;
	private final String format = "uuuu/MM/dd";
	
	public ReserveSearchFlowBean(LocalDate date, int timeCd, int roomCd) {
		this.timeCd = timeCd;
		this.roomCd = roomCd;
		ldate = date;
		this.date = DateToStr();
	}
	public int getTimeCd() {
		return timeCd;
	}
	public void setTimeCd(int timeCd) {
		this.timeCd = timeCd;
	}
	public int getRoomCd() {
		return roomCd;
	}
	public void setRoomCd(int roomCd) {
		this.roomCd = roomCd;
	}
	public LocalDate getLdate() {
		return ldate;
	}
	public void setLdate(LocalDate date) {
		ldate = date;
		this.date = DateToStr();
	}
	public String getDate() {
		return date;
	}
	private String DateToStr() {
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
		return ldate.format(dateTimeFormatter);
	}
	
}
