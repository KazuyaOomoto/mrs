package jp.co.mgnc.business.login.formbean;

import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

/**
 * ログイン画面の入力値を保持するFormBeanクラスです。
 * @author Oomoto Kazuya
 * @version 1.0
 */
public class LoginFormBean {

	private String emp_no;
	private String password;

	/**
	 * 入力チェックを行い、エラーメッセージを返却します。
	 * @param request
	 * @return
	 */
	public ArrayList<String> validate(HttpServletRequest request) {

		//エラーメッセージ用
		ArrayList<String> errMsg = new ArrayList<String>();

		//TODO:２：login.jspからHTMLフォームのデータを受け取る
		//リクエストを取得する。
		emp_no = request.getParameter("emp_no");
		password = request.getParameter("password");
		
		//必須チェックを行う。入力不備があれば、エラーメッセージをセットする。
		if ("".equals(emp_no) || "".equals(password)) {
			errMsg.add(ResourceBundle.getBundle("message").getString("LOGIN_ERR"));
		}

		return errMsg;

	}

	public String getEmp_no() {
		return emp_no;
	}

	public void setEmp_no(String emp_no) {
		this.emp_no = emp_no;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
