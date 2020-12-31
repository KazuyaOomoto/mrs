<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	//セッションキーが残っていたら全削除
	final String sessionkeys[] = {
			"EmployeeUpdateFlowBean",
			"ReserveAllListFlowBean",
			"ReserveCurrentListFlowBeanList",
			"ReserveCurrentListFlowBean",
			"ReserveInputFlowBean",
			"ReserveSearchFlowBean",
	};
	try{
		for(String key : sessionkeys){
			if(session.getAttribute(key) != null){
				session.removeAttribute(key);
			}
		}
	} catch(Exception e){
		response.sendRedirect(request.getContextPath() + "/error");
	}
%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>メインメニュー</title>
<link rel="stylesheet" type="text/css" href="<c:url value='/css/common.css' />">
</head>
<body>
<%@ include file="/WEB-INF/jsp/common/header.jsp"%>
<h2>メインメニュー </h2>
<div id="contents">
  <p><c:out value="${empName}" />さん、ログイン中</p>
  <div class="controlbox">
    <a href="<c:url value='/admin/search' />" id="search" class="button-blue">予約検索・登録</a>
    <a href="<c:url value='/admin/reserve-current-list' />" id="current_reserve_list" class="button-blue">現在の予約・予約削除</a>
    <a href="<c:url value='/admin/employee-update-open' />" id="employee_update_input" class="button-blue">利用者情報変更</a>
    <a href="<c:url value='/admin/reserve-all-list' />" id="all_reserve_list" class="button-blue">全予約一覧表示</a>
  </div>
</div>
<%@ include file="/WEB-INF/jsp/common/logout.jsp"%>
</body>
</html>
