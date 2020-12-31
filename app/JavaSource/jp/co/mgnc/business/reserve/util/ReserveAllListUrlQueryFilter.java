package jp.co.mgnc.business.reserve.util;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

public class ReserveAllListUrlQueryFilter {
	private static int oldyear;	//前回値保存用(年)
	private static int oldmonth;	//〃(月)

	public static int getOldyear() {
		return oldyear;
	}
	public static int getOldmonth() {
		return oldmonth;
	}

	private int year;		//URLから取り込む年
	private int month;		//〃月
	private int day=1;		//TODO:1で初期化(暫定)

	public int getYear() {
		return year;
	}
	public int getMonth() {
		return month;
	}
	public int getDay() {
		return day;
	}
	public void setYear(int year) {
		this.year = year;
		oldyear = this.year;
	}
	public void setMonth(int month) {
		this.month = month;
		oldmonth = this.month;
	}

	public void validate(HttpServletRequest request) throws UnsupportedEncodingException, NullPointerException, IllegalArgumentException {

		request.setCharacterEncoding("utf-8");

		//フォーマット区切り文字の"/"で区切る
		String[] datelist  = request.getParameter("date").split("/");

		//分けた文字列を数値化
		year = Integer.parseInt(datelist[0]);
		month = Integer.parseInt(datelist[1]);

		//範囲外の年月が指定された
		if(((year < 0) || (year > 9999)) || ((month < 1) || (month > 12))) {
			//フォーマット例外をスローする
			throw new IllegalArgumentException();
		}

	}

}
