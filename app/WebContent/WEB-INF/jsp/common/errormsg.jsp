<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:if test="${!empty errMsgList}">
 <div id="errormsg">
   <ul>
   	<c:forEach items="${errMsgList}" var="errMsg">
     	<li><c:out value="${errMsg}" /></li>
   	</c:forEach>
   </ul>
 </div>
</c:if>