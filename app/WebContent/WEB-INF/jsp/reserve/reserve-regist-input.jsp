<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title>予約登録画面</title>
<link rel="stylesheet" type="text/css" href="<c:url value='/css/common.css' />">
</head>
<body>
<%@ include file="/WEB-INF/jsp/common/header.jsp"%>
<h2>予約登録画面 </h2>
<div id="contents">
  <p>連絡先・利用目的を入力して、「確認」ボタンを押してください。</p>
  <form action="<c:url value='/admin/reserve-regist-confirm' />" method="post">
  	<input type="hidden" name="date" value="${ReserveInputFlowBean.date}" />
  	<input type="hidden" name="timename" value="${ReserveInputFlowBean.timeName}" />
  	<input type="hidden" name="time" value="${ReserveInputFlowBean.timeCd}" />
    <table>
      <tr>
        <th>予約日</th>
        <td>${ReserveInputFlowBean.date}</td>
      </tr>
      <tr>
        <th>時間帯</th>
        <td>${ReserveInputFlowBean.timeName}</td>
      </tr>
      <tr>
        <th>従業員番号</th>
        <td>${empNo}</td>
      </tr>
      <tr>
        <th>連絡先</th>
        <td><input type="text" name="tel" maxlength="11" class="width-middle"/>${tel}</td>
      </tr>
      <tr>
        <th>利用目的</th>
        <td><input type="text" name="purpose" maxlength="25" class="width-large"/></td>
      </tr>
    </table>
    <div class="controlbox">
      <input type="submit" value="確認" class="button-red">
      	<c:choose>
	 		<c:when test="${ReserveAllListFlowBean != null}" >
	 			<fmt:parseDate var="date" value="${ReserveAllListFlowBean.reserveDate}" type="DATE" pattern="yyyy-MM-dd" />
	 			<a href="reserve-all-list?date=<fmt:formatDate value="${date}" type="DATE" pattern="yyyy/MM" />" class="button">戻る</a>
	 		</c:when>
	 		<c:otherwise>
	 	     	<a href="<c:url value='/admin/search' />" class="button">戻る</a>
	 	    </c:otherwise>
 	    </c:choose>
    </div>
  </form>
	<c:choose>
		<c:when test="${ReserveRegistInputErr != null }" >
		<div id="errormsg">
		    <ul>
			    <c:forEach items="${ReserveRegistInputErr}" var="err">
			      <li>${err}</li>
				</c:forEach>
			</ul>
	 	</div>
		</c:when>
	</c:choose>
</div>
<%@ include file="/WEB-INF/jsp/common/logout.jsp"%>
</body>
</html>