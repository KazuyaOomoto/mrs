<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title>予約削除確認画面</title>
<link rel="stylesheet" type="text/css" href="<c:url value='/css/common.css' />">
</head>
<body>
<%@ include file="/WEB-INF/jsp/common/header.jsp"%>
<h2>予約削除確認画面 </h2>
<div id="contents">
  <p>以下の予約を削除します。よろしければ、再度パスワードを入力し、「削除確定」ボタンを押してください。</p>
  <form action="<c:url value='/admin/reserve-delete' />" method="post">
    <table>
      <tr>
        <th>予約日</th>
        <td>${ReserveCurrentListFlowBean.date}</td>
      </tr>
      <tr>
        <th>時間帯</th>
        <td>${ReserveCurrentListFlowBean.timeName}</td>
      </tr>
      <tr>
        <th>従業員番号</th>
        <td>${empNo}</td>
      </tr>
      <tr>
        <th>連絡先</th>
        <td>${ReserveCurrentListFlowBean.tel}</td>
      </tr>
      <tr>
        <th>目的</th>
        <td>${ReserveCurrentListFlowBean.purpose}</td>
      </tr>
      <tr>
        <th>パスワード</th>
        <td>
          <input type="password" name="password" maxlength="8" class="width-middle">
        </td>
      </tr>
    </table>
    <div class="controlbox">
      <input type="submit" value="削除確定" class="button-red">
      <a href="<c:url value='/admin/reserve-current-list' />" class="button">戻る</a>
    </div>
	<c:choose>
		<c:when test="${DeleteReserveErr != null }" >
		<div id="errormsg">
		    <ul>
			<c:forEach items="${DeleteReserveErr}" var="err">
		      <li>${err}</li>
			</c:forEach>
			</ul>
	 	</div>
		</c:when>
	</c:choose>
  </form>
</div>
<%@ include file="/WEB-INF/jsp/common/logout.jsp"%>
</body>
</html>