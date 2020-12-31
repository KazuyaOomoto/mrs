package jp.co.mgnc.business.employee.formbean;

import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

/**
 * 従業員情報更新画面の入力値を保持するFormBeanクラスです。
 * @author KO
 * @version 1.0
 */
public class EmployeeUpdateFormBean {
	
	private String password;
	private String new_password;
	private String new_password_confirm;
	private String tel;

	/**
	 * 従業員情報更新画面の入力値の妥当性をチェックするメソッドです。
	 * @param request
	 * @return　エラーリスト
	 */
	public ArrayList<String> validate(HttpServletRequest request) {

		ArrayList<String> errMsg = new ArrayList<>();
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		//リクエストから各パラメータを取得
		password = request.getParameter("password");
		new_password = request.getParameter("new-password");
		new_password_confirm = request.getParameter("new-password-confirm");
		tel = request.getParameter("tel");
		
		//NULLチェック
		if(password == null) {
			errMsg.add(MessageFormat.format(ResourceBundle.getBundle("message").getString("INPUT_ERR_VALIDATE"), "現在のパスワード"));
		}
		if(new_password == null) {
			errMsg.add(MessageFormat.format(ResourceBundle.getBundle("message").getString("INPUT_ERR_VALIDATE"), "新しいパスワード"));
		}
		if(new_password_confirm == null) {
			errMsg.add(MessageFormat.format(ResourceBundle.getBundle("message").getString("INPUT_ERR_VALIDATE"), "新しいパスワード（確認用）"));
		}
		if(tel == null) {
			errMsg.add(MessageFormat.format(ResourceBundle.getBundle("message").getString("INPUT_ERR_VALIDATE"), "連絡先"));
		}
		if(!errMsg.isEmpty()) {
			return errMsg;
		}
		
		//新しいパスワードかつ連絡先が空の場合
		if("".equals(new_password) && "".equals(tel)) {
			//エラーメッセージINPUT_ERR_VALIDATEをエラーメッセージリストに追加する
			//・入力内容「新しいパスワード」に誤りがあります。
			//・入力内容「連絡先」に誤りがあります。
			errMsg.add(MessageFormat.format(ResourceBundle.getBundle("message").getString("INPUT_ERR_VALIDATE"), "新しいパスワード"));
			errMsg.add(MessageFormat.format(ResourceBundle.getBundle("message").getString("INPUT_ERR_VALIDATE"), "連絡先"));
			return errMsg;
		}

		//連絡先のみかをチェック(新しいパスワード以外が入力されている)
		if("".equals(new_password) && !("".equals(tel)) && (   !("".equals(password))  ||  !("".equals(new_password_confirm)) )) {
			errMsg.add(MessageFormat.format(ResourceBundle.getBundle("message").getString("INPUT_ERR_VALIDATE"), "新しいパスワード"));
			return errMsg;
		}
		
		//新しいパスワードが入力されている場合
		if(!("".equals(new_password))) {
			//現在のパスワードが空の場合
			if("".equals(password)) {
				//エラーメッセージINPUT_ERR_VALIDATEをエラーメッセージリストに追加する。
				//・入力内容「現在のパスワード」に誤りがあります。
				errMsg.add(MessageFormat.format(ResourceBundle.getBundle("message").getString("INPUT_ERR_VALIDATE"), "現在のパスワード"));
			}
			if("".equals(new_password_confirm)) {
				//エラーメッセージINPUT_ERR_VALIDATEをエラーメッセージリストに追加する。
				//・入力内容「新しいパスワード（確認用）」に誤りがあります。
				errMsg.add(MessageFormat.format(ResourceBundle.getBundle("message").getString("INPUT_ERR_VALIDATE"), "新しいパスワード（確認用）"));
			}
			if(!errMsg.isEmpty()) {
				return errMsg;
			}
			
			//新しいパスワードと新しいパスワード（確認用）が一致しない場合
			if(!(new_password.equals(new_password_confirm))) {
				//エラーメッセージCOMPARE_ERR_VALIDATEをエラーメッセージリストに追加する。
				//・「新しいパスワード」と「新しいパスワード（確認用）」が一致しません。
				errMsg.add(MessageFormat.format(ResourceBundle.getBundle("message").getString("COMPARE_ERR_VALIDATE"), "新しいパスワード","新しいパスワード（確認用）") );
				return errMsg;
			}

		}
		
		return errMsg;
	}

	/**
	 * 従業員情報更新画面から入力された現在のパスワードを取得するメソッドです。
	 * @param なし
	 * @return　現在のパスワード
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * 従業員情報更新画面から入力された新パスワードを取得するメソッドです。
	 * @param なし
	 * @return　新パスワード
	 */
	public String getNew_password() {
		return new_password;
	}

	/**
	 * 従業員情報更新画面から入力された新連絡先を取得するメソッドです。
	 * @param なし
	 * @return　新連絡先
	 */
	public String getTel() {
		return tel;
	}
	
	
}
