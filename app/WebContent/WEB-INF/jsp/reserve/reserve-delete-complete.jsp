<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title>予約削除完了画面</title>
<link rel="stylesheet" type="text/css" href="<c:url value='/css/common.css' />">
</head>
<body>
<%@ include file="/WEB-INF/jsp/common/header.jsp"%>
<h2>予約削除完了画面 </h2>
<div id="contents">
  <p>予約を削除しました。</p>
  <div class="controlbox">
    <a href="<c:url value='/admin/menu' />" class="button">メニューに戻る</a>
  </div>
</div>
<%@ include file="/WEB-INF/jsp/common/logout.jsp"%>
</body>
</html>