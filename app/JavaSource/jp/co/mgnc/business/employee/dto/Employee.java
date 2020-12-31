package jp.co.mgnc.business.employee.dto;

/**
 * 従業員情報を保持するDTOクラスです。
 * @author Oomoto Kazuya
 * @version 1.0
 */
public class Employee{

	private String empNo;
	private String defaultTel;
	private String department;
	private String empName;
	private String password;

	public String getEmpNo() {
		return this.empNo;
	}

	public void setEmpNo(String empNo) {
		this.empNo = empNo;
	}

	public String getDefaultTel() {
		return this.defaultTel;
	}

	public void setDefaultTel(String defaultTel) {
		this.defaultTel = defaultTel;
	}

	public String getDepartment() {
		return this.department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getEmpName() {
		return this.empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}