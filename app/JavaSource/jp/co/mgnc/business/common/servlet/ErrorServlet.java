package jp.co.mgnc.business.common.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 予期しないエラーが発生した場合に呼び出す共通サーブレットクラスです。ログアウト処理を行い、ログイン画面に遷移します。
 * @author Oomoto Kazuya
 * @version 1.0
 */
@WebServlet("/error")
public class ErrorServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//セッションがあれば、セッションを削除する。
		HttpSession session = request.getSession(false);
		if(session != null){
			session.invalidate();
		}
		
		//エラーメッセージを格納して、ログイン画面に遷移する。
		List<String> errMsgList = new ArrayList<String>();
		errMsgList.add(ResourceBundle.getBundle("message").getString("EXCEPTION_OTHERS"));
		request.setAttribute("errMsgList", errMsgList);
		request.getRequestDispatcher("/login.jsp").forward(request, response);
			
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
