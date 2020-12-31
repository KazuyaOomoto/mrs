package jp.co.mgnc.business.reserve.formbean;

import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

public class ReserveDeleteLoginCheckFormBean {
	private String password;

	public String getPassword() {
		return password;
	}

	/**
	 * 予約削除確認画面の入力値の妥当性をチェックするメソッドです。
	 * @param request
	 * @return　エラーリスト
	 */
	public ArrayList<String> validate(HttpServletRequest request) {

		ArrayList<String> errMsg = new ArrayList<>();

		try {
			request.setCharacterEncoding("utf-8");
			password = request.getParameter("password"); 
			if("".equals(password)) {
				errMsg.add(MessageFormat.format(ResourceBundle.getBundle("message").getString("INPUT_ERR_VALIDATE"), "パスワード"));
			}
		}catch(NullPointerException | UnsupportedEncodingException e) {
			e.printStackTrace();
			errMsg.add(MessageFormat.format(ResourceBundle.getBundle("message").getString("INPUT_ERR_VALIDATE"), "パスワード"));
		}

		return errMsg;
	}
}
