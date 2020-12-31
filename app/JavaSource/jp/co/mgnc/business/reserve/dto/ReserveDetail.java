package jp.co.mgnc.business.reserve.dto;

import java.time.LocalDate;

public class ReserveDetail  {

	private LocalDate reserveDate;
	private int timeCd;
	private int roomCd;
	private String empNo;
	private String tel;
	private String purpose;
	private String timeName;
	private String roomName;
	private String empName;
	private String department;

	public LocalDate getReserveDate() {
		return reserveDate;
	}
	public void setReserveDate(LocalDate reserveDate) {
		this.reserveDate = reserveDate;
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
	public String getEmpNo() {
		return empNo;
	}
	public void setEmpNo(String empNo) {
		this.empNo = empNo;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	public String getTimeName() {
		return timeName;
	}
	public void setTimeName(String timeName) {
		this.timeName = timeName;
	}
	public String getRoomName() {
		return roomName;
	}
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	
}
