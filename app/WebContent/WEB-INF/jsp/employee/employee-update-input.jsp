<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title>利用者情報変更画面</title>
<link rel="stylesheet" type="text/css" href="<c:url value='/css/common.css' />">
</head>
<body>
<%@ include file="/WEB-INF/jsp/common/header.jsp"%>
<h2>利用者情報変更画面 </h2>
<div id="contents">
  <p>変更する箇所を修正して、「確認」ボタンを押してください。</p>
  <form action="<c:url value='/admin/employee-update-confirm' />" method="post">
    <table>
      <tr>
        <th>氏名</th>
        <td>${EmployeeUpdateFlowBean.empName}</td>
      </tr>
      <tr>
        <th>従業員番号</th>
        <td>${EmployeeUpdateFlowBean.empNo}</td>
      </tr>
      <tr>
        <th>所属部署</th>
        <td>${EmployeeUpdateFlowBean.department}</td>
      </tr>
      <tr>
        <th>パスワード</th>
        <td>現在のパスワード：<input name="password" type="password" class="width-middle" maxlength="8"><br />
        新しいパスワード：<input name="new-password" type="password" class="width-middle" maxlength="8"><br />
        新しいパスワード：<input name="new-password-confirm" type="password" class="width-middle" maxlength="8">（確認用）
        </td>
      </tr>
      <tr>
        <th>連絡先</th>
        <td><input type="text" name="tel" class="width-middle" maxlength="11" value="${EmployeeUpdateFlowBean.defaultTel}">
          <br />
          ※既定値は内線番号</td>
      </tr>
    </table>
    <div class="controlbox">
      <input type="submit" value="確認" class="button">
      <a href="<c:url value='/admin/menu' />" class="button">メニューに戻る</a>
    </div>
  </form>
	<c:choose>
		<c:when test="${EmployeeUpdateInputErr != null }" >
		<div id="errormsg">
		    <ul>
			<c:forEach items="${EmployeeUpdateInputErr}" var="err">
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