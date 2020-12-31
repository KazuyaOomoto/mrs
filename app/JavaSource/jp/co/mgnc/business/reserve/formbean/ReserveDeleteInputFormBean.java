package jp.co.mgnc.business.reserve.formbean;


import javax.servlet.http.HttpServletRequest;

public class ReserveDeleteInputFormBean {
	
	private int index;

	/**
	 * 現在の予約一覧画面から入力された行番号を取得するメソッドです。
	 * @param なし
	 * @return　時間帯コード
	 */
	public int getIndex() {
		return index;
	}
	
	/**
	 * 現在の予約一覧画面の入力値の妥当性をチェックするメソッドです。
	 * @param request
	 * @return　エラーリスト
	 */
	public void validate(HttpServletRequest request) throws Exception{

		request.setCharacterEncoding("utf-8");
		String param = request.getParameter("index");
		index = Integer.parseInt(param);
		
	}
}
