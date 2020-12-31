package jp.co.mgnc.business.common.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

/**
 * 日付に関連するユーティリティクラスです。
 * @author Oomoto Kazuya
 */
public class DateUtil {

	/**
	 * 日付書式がyyyy/MM/ddになっているかどうかチェックするメソッドです。
	 * <pre>
	 * 使用例：
	 * checkDateFormat("2016/02/29")　→　trueになります。
	 * checkDateFormat("2016/02/30")　→　falseになります。
	 * checkDateFormat("2016/02/31")　→　falseになります。
	 * checkDateFormat("2016-02-30")　→　falseになります。
	 * </pre>
	 * @param date　対象となる日付
	 * @return 正しい日付書式の場合はtrue、そうではないならばfalse
	 */
	public static boolean checkDateFormat(String date) {

		boolean ret;
	
		try {
			LocalDate.parse(date,DateTimeFormatter.ofPattern("uuuu/MM/dd").withResolverStyle(ResolverStyle.STRICT));
			ret = true;
		} catch (DateTimeParseException e) {
			ret = false;
		}
		
		return ret;
				
	}
	
	/**
	 * yyyy-MM-dd形式の日付をyyyy/MM/dd形式に変換します。
	 * <pre>
	 * convateDateFormat("2017-05-11")　→　2017/05/11を返却します。
	 * </pre>
	 * @param beforeDate yyyy-MM-dd形式の日付
	 * @return yyyy/MM/dd形式
	 */
	public static String convertDateFormat(String beforeDate) {

		return LocalDate.parse(beforeDate,DateTimeFormatter.ofPattern("uuuu-MM-dd")).format(DateTimeFormatter.ofPattern("uuuu/MM/dd"));
		
	}
	
	/**
	 * yyyy/MM/dd形式の日付と現在の日付を比較し、過去の日付かどうかチェックするメソッドです。
	 * <pre>
	 * 使用例：
	 * 今日の日付を2017/05/11とすると、
	 * checkPastDate("2017/05/10")　→　trueになります。
	 * checkPastDate("2017/05/11")　→　falseになります。
	 * checkPastDate("2017/05/12")　→　falseになります。
	 * </pre>
	　* @param targetDate yyyy-MM-dd形式の日付
	 * @return 過去の日付であればtrue、そうでなければfalse
	 */
	public static boolean checkPastDate(String targetDate){
		
		LocalDate target = LocalDate.parse(targetDate,DateTimeFormatter.ofPattern("uuuu/MM/dd"));
		LocalDate currentDate = LocalDate.now();
		return target.isBefore(currentDate);
		
	}
	
	/**
	 * yyyy/MM/dd形式の文字列からLocalDateオブジェクトを取得します。
	 * @param targetDate yyyy/MM/dd形式の日付
	 * @return LocalDateオブジェクト
	 */
	public static LocalDate convertToLocalDate(String targetDate) {

		return LocalDate.parse(targetDate,DateTimeFormatter.ofPattern("uuuu/MM/dd"));
		
	}
		
}
