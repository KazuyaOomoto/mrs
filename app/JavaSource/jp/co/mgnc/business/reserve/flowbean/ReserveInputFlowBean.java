package jp.co.mgnc.business.reserve.flowbean;

import java.time.LocalDate;

public class ReserveInputFlowBean extends ReserveSearchFlowBean {
	private String timeName;
	private String tel;
	private String purpose;

	public ReserveInputFlowBean(LocalDate date, int timeCd, int roomCd, String timeName) {
		super(date, timeCd, roomCd);
		this.timeName = timeName;
	}
	public String getTimeName() {
		return timeName;
	}
	public void setTimeName(String timeName) {
		this.timeName = timeName;
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
}
