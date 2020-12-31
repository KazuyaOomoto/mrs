package jp.co.mgnc.business.reserve.formbean;

import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

/**
 * 予約確認画面の入力値を保持するFormBeanクラスです。
 * @author KO
 * @version 1.0
 */
public class ReserveConfirmFormBean {
	
	private String tel;
	private String purpose;

	/**
	 * 予約確認画面の入力値の妥当性をチェックするメソッドです。
	 * @param request
	 * @return　エラーリスト
	 */
	public ArrayList<String> validate(HttpServletRequest request) {
		
		ArrayList<String> errMsg = new ArrayList<>();
		
		try {
			request.setCharacterEncoding("utf-8");

			//連絡先をリクエストから取得
			tel = request.getParameter("tel");

			//使用目的をリクエストから取得
			purpose = request.getParameter("purpose");

			//連絡先の桁数は仕様上最大11桁。int型は10桁までのため、long型に変換してその正当性を確認する
			Long.parseLong(tel);
		}
		//連絡先が無効(null)または数値でない場合
		catch(NumberFormatException | NullPointerException | UnsupportedEncodingException e) {
			//連絡先が不正としてエラーリストに追加
			errMsg.add(MessageFormat.format(ResourceBundle.getBundle("message").getString("INPUT_ERR_VALIDATE"), "連絡先"));
		}

		try {
			//空欄のまま来た場合
			if("".equals(purpose)) {
				//使用目的が不正としてエラーリストに追加
				errMsg.add(MessageFormat.format(ResourceBundle.getBundle("message").getString("INPUT_ERR_VALIDATE"), "利用目的"));
			}
		}
		//使用目的が無効(null)の場合
		catch(NullPointerException e) {
			//使用目的が不正としてエラーリストに追加
			errMsg.add(MessageFormat.format(ResourceBundle.getBundle("message").getString("INPUT_ERR_VALIDATE"), "利用目的"));
		}

		return errMsg;
	}
	
	/**
	 * 予約確認画面から入力された連絡先を取得するメソッドです。
	 * @param なし
	 * @return　連絡先
	 */
	public String getTel() {
		return tel;
	}
	
	/**
	 * 予約確認画面から入力された使用目的を取得するメソッドです。
	 * @param なし
	 * @return　使用目的
	 */
	public String getPurpose() {
		return purpose;
	}

}
