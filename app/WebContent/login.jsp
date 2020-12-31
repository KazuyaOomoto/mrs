<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>ログイン画面</title>
<link rel="stylesheet" type="text/css" href="<c:url value='/css/common.css' />">
</head>
<body>
<%@ include file="/WEB-INF/jsp/common/header.jsp"%>
<h2>ログイン画面 </h2>
<div id="contents">
  <p>従業員番号とパスワードを入力して、ログインしてください。</p>
  <form action="<c:url value='/login'/>" method="post">
    <table>
      <tr>
        <th>従業員番号</th>
        <td><input name="emp_no" type="text" class="width-middle" maxlength="8" value="<c:out value='${LoginFlowBean.empNo}'/>"></td>
      </tr>
      <tr>
        <th>パスワード</th>
        <td><input name="password" type="password" class="width-middle" maxlength="8"></td>
      </tr>
    </table>
    <div class="controlbox">
      <input type="submit" value="ログイン" id="login" class="button">
      <input type="reset" value="リセット" id="reset" class="button">
    </div>
	<%@ include file="/WEB-INF/jsp/common/errormsg.jsp"%>
  </form>
</div>
</body>
</html>
