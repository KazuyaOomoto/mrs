package jp.co.mgnc.business.reserve.flowbean;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ReserveCurrentListFlowBean {
	private LocalDate reserveDate;
	private String date;
	private int timeCd;
	private String timeName;
	private int roomCd;
	private String roomName;
	private String purpose;
	private String tel;
	private final String format = "uuuu/MM/dd";

	public void setReserveDate(LocalDate reserveDate) {
		this.reserveDate = reserveDate;
		this.date = DateToStr();
	}
	public LocalDate getReserveDate() {
		return reserveDate;
	}
	private String DateToStr() {
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
		return reserveDate.format(dateTimeFormatter);
	}
	public String getDate() {
		return date;
	}
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
	}
	public int getRoomCd() {
		return roomCd;
	}
	public void setRoomCd(int roomCd) {
		this.roomCd = roomCd;
	}
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getRoomName() {
		return roomName;
	}
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

}
