<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="jp.co.mgnc.business.time.dao.TimeDAO" %>
<%@ page import="jp.co.mgnc.business.time.dto.Time" %>
<%@ page import="java.util.ArrayList" %>
<%
	//ページスコープに全時間帯を設定
	ArrayList<Time> timelist = new TimeDAO().getTimeAllList();
	pageContext.setAttribute("timelist", timelist);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title>予約検索画面</title>
<link rel="stylesheet" type="text/css" href="<c:url value='/css/common.css' />">
<link rel="stylesheet" type="text/css" href="<c:url value='/js/datepicker/jquery-ui.css'  />">
<script src="<c:url value='/js/datepicker/jquery.js' />"></script>
<script src="<c:url value='/js/datepicker/jquery-ui.js'  />"></script>
<script src="<c:url value='/js/datepicker/datepicker-ja.js' />"></script>
</head>
<body>
<%@ include file="/WEB-INF/jsp/common/header.jsp"%>
<h2>予約検索画面 </h2>
<div id="contents">
  <p>予約日・時間帯を入力して、「検索」ボタンをクリックしてください。</p>
  <form action="<c:url value='/admin/search' />" method="post">
    <table>
      <tr>
        <th>予約日</th>
        <td><input type="text" name="date" class="width-middle" id="datepicker">
          <br/>
          例．2016/06/01</td>
      </tr>
      <tr>
        <th rowspan="${timelist.size()+1}">時間帯</th>
 		<c:forEach items="${timelist}" var="t">
 			<tr>
				<td><input type="radio" name="time" value="${t.timeCd}">
         			 ${t.timeName}</td>
         	</tr>
 		</c:forEach>
	  </tr>
    </table>
    <div class="controlbox">
      <input type="submit" value="検索" class="button">
      <a href="<c:url value='/admin/menu' />" class="button">メニューに戻る</a>
    </div>
   </form>
	<c:choose>
		<c:when test="${ReserveSearchErrMsg != null }" >
		<div id="errormsg">
		<c:forEach items="${ReserveSearchErrMsg}" var="err">
	    <ul>
	      <li>${err}</li>
	    </ul>
		</c:forEach>
	 	</div>
		</c:when>
		<c:otherwise>
		<c:if test="${ReserveSearchFlowBean != null }">
		  <div id="reserve_control">
		    <p>予約することができます。</p>
		    <form action="<c:url value='/admin/reserve-regist-input' />" method="post">
		      <input type="hidden" name="date" value="${ReserveSearchFlowBean.date}">
		      <input type="hidden" name="time" value="${ReserveSearchFlowBean.timeCd}">
		      <input type="submit" value="予約登録に進む" class="button-red">
		    </form>
		  </div>
		  </c:if>
		</c:otherwise>
	</c:choose>
</div>
<script>
$('#datepicker').datepicker(
	{ 
	minDate: new Date(), 
	firstDay: 1
	}
);
</script>

<%@ include file="/WEB-INF/jsp/common/logout.jsp"%>
</body>
</html>