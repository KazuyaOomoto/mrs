package jp.co.mgnc.business.reserve.dto;

import java.time.LocalDate;

/**@author ISA-22D
 * 会議室予約情報クラス
 */
public class Reserve {

	LocalDate reserveDate;
	private int timeCd;
	private int roomCd;
	private String empNo;
	private String tel;
	private String purpose;
	private int count;					// 予約数
	private String timeName;		// 時間帯
	private String roomName;	// 部屋名
	private String empName;		// 従業員名

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
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
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
	public String toString() {
		return "Reserve [reserveDate=" + reserveDate + ", timeCd=" + timeCd + ", roomCd=" + roomCd + ", empNo=" + empNo
				+ ", tel=" + tel + ", purpose=" + purpose + "]";
	}
	
	public void showReserveListConvName() {
		System.out.println( "Reserve [reserveDate=" + reserveDate + ", timeName=" + timeName + ", roomName=" + roomName + ", empName=" + empName
				+ ", tel=" + tel + ", purpose=" + purpose + "]");
	}
}
