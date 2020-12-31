package jp.co.mgnc.business.reserve.formbean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

public class ReserveAllListFormBean {

	private LocalDate date;
	private int time;
	private boolean month_input;
	
	public ArrayList<String> validate(HttpServletRequest request) {

		ArrayList<String> errMsg = new ArrayList<>();

		try {
			request.setCharacterEncoding("utf-8");
			
			/*フォームから設定されたパラメータ[date][time]を取得する*/
			String sdate = request.getParameter("date");
			/* 分割文字列を設定	*/
			String split_str = "/";
			try {
				/* "yyyy-MM"のフォーマットか厳密に確認 */
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
				format.setLenient(false);
				format.parse(sdate);
				
				/*"yyyy-MM"で確認=日付フォームからの入力となる */
				/*区切り文字を"-"に変更*/
				split_str = "-";	
				month_input = true;
			}catch(ParseException  e) {
				/* 処理なし	*/
			} finally {
				
				String[] dates = sdate.split(split_str);
				int year= Integer.parseInt(dates[0]);
				int month = Integer.parseInt(dates[1]);
				int dayOfMonth=1;
				if(!month_input) {
					dayOfMonth =  Integer.parseInt(dates[2]);
					time = Integer.parseInt(request.getParameter("time"));	
				}
				
				date = LocalDate.of(year, month, dayOfMonth);
			}
		}
		/*name,timeを解析した結果、数値ではなかった or requestが異常*/
		catch(Exception  e) {
			/*「指定した予約日・時間帯では予約できません。」をエラーメッセージに設定	*/
			errMsg.add(ResourceBundle.getBundle("message").getString("SEARCH_ERR_EXIST_RESERVE"));
		}
		return errMsg;
	}

	public LocalDate getDate() {
		return date;
	}

	public int getTime() {
		return time;
	}

	public boolean ismonth_input() {
		return month_input;
	}
	
}
