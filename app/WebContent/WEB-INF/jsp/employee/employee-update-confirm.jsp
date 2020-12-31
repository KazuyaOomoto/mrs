<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title>利用者情報変更確認画面</title>
<link rel="stylesheet" type="text/css" href="<c:url value='/css/common.css' />">
</head>
<body>
<body>
<%@ include file="/WEB-INF/jsp/common/header.jsp"%>
<h2>利用者情報変更確認画面 </h2>
<div id="contents">
  <p>以下の情報で変更します。よろしければ、「変更確定」ボタンを押してください。</p>
  <form action="<c:url value='/admin/employee-update' />" method="post">
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
      <c:if test="${ EmployeeUpdateFlowBean.new_password != null}" >
      <tr>
        <th>パスワード</th>
        <td>${New_Password}</td>
      </tr>
      </c:if>
      <c:if test="${ EmployeeUpdateFlowBean.new_default_tel != null}" >
      <tr>
        <th>連絡先</th>
        <td>${EmployeeUpdateFlowBean.new_default_tel}</td>
      </tr>
      </c:if>
    </table>
    <div class="controlbox">
      <input type="submit" value="変更確定" class="button-red">
      <a href="<c:url value='/admin/employee-update-open' />" class="button">戻る</a>
    </div>
  </form>
</div>
<%@ include file="/WEB-INF/jsp/common/logout.jsp"%>
</body>
</html>