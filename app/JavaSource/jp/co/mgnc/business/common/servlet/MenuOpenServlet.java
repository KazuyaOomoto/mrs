package jp.co.mgnc.business.common.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * メニューを表示する共通サーブレットクラスです。
 * @author Oomoto Kazuya
 * @version 1.0
 */
@WebServlet("/admin/menu")
public class MenuOpenServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//セッションチェック
		HttpSession session = request.getSession(false);
		if (session != null && session.getAttribute("empNo") != null) {
			
			request.getRequestDispatcher("/WEB-INF/jsp/menu.jsp").forward(request, response);
		
		} else {
			
			//　セッションにキーempNoが無ければ、ログイン画面に遷移する。
			request.getRequestDispatcher("/error").forward(request, response);
			
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
