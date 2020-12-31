<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:choose>
	<c:when test="${empNo != null }">
		<a href="<c:url value='menu' />" id="header">
		<h1>会議室予約システム</h1>
		</a>
	</c:when>
	<c:otherwise>
		<a href="<c:url value='/login.jsp' />" id="header">
		<h1>会議室予約システム</h1>
		</a>
	</c:otherwise>
</c:choose>
