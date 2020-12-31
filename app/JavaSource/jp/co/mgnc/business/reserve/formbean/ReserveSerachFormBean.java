package jp.co.mgnc.business.reserve.formbean;

import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

/**
 * 予約検索画面の入力値を保持するFormBeanクラスです。
 * @author KO
 * @version 1.0
 */
public class ReserveSerachFormBean {
	
	private int timeCd;
	private int roomCd;
	private LocalDate date;

	/**
	 * 予約検索画面の入力値の妥当性をチェックするメソッドです。
	 * @param request
	 * @return　エラーリスト
	 */
	public ArrayList<String> validate(HttpServletRequest request) {

		ArrayList<String> errMsg = new ArrayList<>();
		String inputDate = null;

		try {
			request.setCharacterEncoding("utf-8");

			//日付をリクエストから取得
			inputDate = request.getParameter("date");
		
			//会議室コードは1で固定(暫定)
			roomCd = 1;

			//時間帯コードをリクエストから取得し、整数値に変換
			timeCd = Integer.parseInt(request.getParameter("time"));
		}
		//整数値でないもしくは無効(null)の場合
		catch (NumberFormatException | NullPointerException | UnsupportedEncodingException e) {
			e.printStackTrace();
			errMsg.add(MessageFormat.format(ResourceBundle.getBundle("message").getString("INPUT_ERR_VALIDATE"), "時間帯"));
		}


		try {
			//uuuu/MM/ddで厳密な日付(例:2019/08/32とはならないよう)のフォーマットを定義
			DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("uuuu/MM/dd").withResolverStyle(ResolverStyle.STRICT);

			//取得した日付を上記フォーマットに変換
			date = LocalDate.parse(inputDate, dateTimeFormatter);

			//現在の日付を取得
			LocalDate currentDate = LocalDate.now();

			//指定した日付が現在の日付よりも前の日の場合
			if(date.isBefore(currentDate)) {
				errMsg.add(MessageFormat.format(ResourceBundle.getBundle("message").getString("INPUT_ERR_VALIDATE"), "予約日"));
			}
		}
		//日付のフォーマットエラーもしくは無効(null)である場合
		catch (DateTimeException | NullPointerException e) {
			errMsg.add(MessageFormat.format(ResourceBundle.getBundle("message").getString("INPUT_ERR_VALIDATE"), "予約日"));
		}

		return errMsg;
	}

	/**
	 * 予約検索画面から入力された時間帯コードを取得するメソッドです。
	 * @param なし
	 * @return　時間帯コード
	 */
	public int getTimeCd() {
		return timeCd;
	}
	
	/**
	 * 予約検索画面から入力された会議室コードを取得するメソッドです。
	 * @param なし
	 * @return　会議室コード
	 */
	public int getRoomCd() {
		return roomCd;
	}

	/**
	 * 予約検索画面から入力された日付を取得するメソッドです。
	 * @param なし
	 * @return　日付
	 */
	public LocalDate getDate() {
		return date;
	}

}
