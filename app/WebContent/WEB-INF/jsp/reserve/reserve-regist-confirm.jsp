<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title>予約確認画面</title>
<link rel="stylesheet" type="text/css" href="<c:url value='/css/common.css' />">
</head>
<body>
<%@ include file="/WEB-INF/jsp/common/header.jsp"%>
<h2>予約確認画面 </h2>
<div id="contents">
  <p>以下の予約を登録します。よろしければ、「登録確定」ボタンを押してください。</p>
  <form action="<c:url value='/admin/reserve-regist-complete' />" method="post">
  	<input type="hidden" name="date" value="${ReserveInputFlowBean.date}" />
  	<input type="hidden" name="time" value="${ReserveInputFlowBean.timeCd}" />
  	<input type="hidden" name="empNo" value="${empNo}" />
  	<input type="hidden" name="tel" value="${ReserveInputFlowBean.tel}" />
  	<input type="hidden" name="purpose" value="${ReserveInputFlowBean.purpose}" />
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
        <td>${ReserveInputFlowBean.tel}</td>
      </tr>
      <tr>
        <th>利用目的</th>
        <td>${ReserveInputFlowBean.purpose}</td>
      </tr>
    </table>
    <div class="controlbox">
     <input type="submit" value="登録確定" class="button-red">
	 <a href="<c:url value='/admin/reserve-regist-input'/>" class="button">戻る</a>
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