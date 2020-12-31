<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title>現在の予約一覧</title>
<link rel="stylesheet" type="text/css" href="<c:url value='/css/common.css' />">
</head>
<body>
<%@ include file="/WEB-INF/jsp/common/header.jsp"%>
<h2>現在の予約一覧 </h2>
<div id="contents">
<c:choose>
<c:when test="${ReserveCurrentListFlowBeanList != null }" >
<p>${empName}さんの予約は次の通りです</p>
  <table>
    <tr>
      <th>予約日</th>
      <th>時間帯</th>
      <th>内容</th>
      <th>連絡先</th>
      <th>削除</th>
    </tr>
    <c:forEach items="${ReserveCurrentListFlowBeanList}" var="flowbean" varStatus="status">
    <tr>
      <td>${flowbean.date}</td>
      <td>${flowbean.timeName}</td>
      <td>${flowbean.purpose}</td>
      <td>${flowbean.tel}</td>
      <td><form action="<c:url value='/admin/reserve-delete-confirm' />" method="post">
          <input type="hidden" name="index" value="${status.index}" />
          <input type="submit" value="削除" class="button">
        </form></td>
    </tr>
    </c:forEach>
  </table>
</c:when>
<c:otherwise>
<p>${empName}さんの予約はありません。</p>
</c:otherwise>
</c:choose>
</div>
<div class="controlbox">
  <a href="<c:url value='/admin/menu' />" class="button">メニューに戻る</a>
</div>
<%@ include file="/WEB-INF/jsp/common/logout.jsp"%>
</body>
</html>