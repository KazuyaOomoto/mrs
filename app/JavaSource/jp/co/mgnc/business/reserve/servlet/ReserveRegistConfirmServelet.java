package jp.co.mgnc.business.reserve.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.co.mgnc.business.reserve.flowbean.ReserveInputFlowBean;
import jp.co.mgnc.business.reserve.formbean.ReserveConfirmFormBean;

@WebServlet("/admin/reserve-regist-confirm")
public class ReserveRegistConfirmServelet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ReserveRegistConfirmServelet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//reserve-regist-confirmのフォームのバリデーション
		ReserveConfirmFormBean formbean = new ReserveConfirmFormBean();
		ArrayList<String> errMsg = formbean.validate(request);
		
		//セッションからReserveInputFlowBeanを取得
		HttpSession session = request.getSession(false);
		ReserveInputFlowBean flowbean = (ReserveInputFlowBean)session.getAttribute("ReserveInputFlowBean");

		//バリデーションのエラーなしの場合は、reserve-regist-confirm.jspに遷移
		String path = "/WEB-INF/jsp/reserve/reserve-regist-confirm.jsp";
	
		//バリデーションのエラーなし
		if(errMsg.isEmpty()) {
			//ReserveInputFlowBeanに連絡先、使用目的を設定
			flowbean.setTel(formbean.getTel());
			flowbean.setPurpose(formbean.getPurpose());
		}
		//エラーあり
		else {
			//連絡先、使用目的をクリア、エラーメッセージを設定してreserve-regist-input.jspに遷移先を切り替え
			flowbean.setTel("");
			flowbean.setPurpose("");
			request.setAttribute("ReserveRegistInputErr", errMsg);
			path = "/WEB-INF/jsp/reserve/reserve-regist-input.jsp";
		}
		request.getRequestDispatcher(path).forward(request, response);
	}

}
