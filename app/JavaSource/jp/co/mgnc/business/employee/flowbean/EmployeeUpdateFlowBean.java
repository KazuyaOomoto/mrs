package jp.co.mgnc.business.employee.flowbean;

import jp.co.mgnc.business.employee.dto.Employee;

/**
 * 従業員情報を更新する際に必要なデータを保持するクラスです。
 * @author Oomoto Kazuya
 * @version 1.0
 * <p>Employeeクラスを継承し、メンバに</p>
 * <li>
 * <ul>新パスワード</ul>
 * <ul>新連絡先</ul>
 * </li>
 * <p>を付け足したもので対応します。</p>
 */
public class EmployeeUpdateFlowBean extends Employee {
	private String  new_password;
	private String new_default_tel;
	public String getNew_password() {
		return new_password;
	}
	public void setNew_password(String new_password) {
		this.new_password = new_password;
	}
	public String getNew_default_tel() {
		return new_default_tel;
	}
	public void setNew_default_tel(String new_default_tel) {
		this.new_default_tel = new_default_tel;
	}
	
}
