<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.time.LocalTime" %>
<%@ page import="jp.co.mgnc.business.time.dao.TimeDAO" %>
<%@ page import="jp.co.mgnc.business.time.dto.Time" %>
<jsp:useBean id="date" class="java.util.Date"/>
<%
	try{
		//3. 時間帯情報を取得する。
		ArrayList<Time> timelist = new TimeDAO().getTimeAllList();
		int timesize = timelist.size();
		
		//4-2.4-1のリストから時間帯上限を取得する
		LocalTime[] timelimits = new LocalTime[timesize];
		for (int i = 0; i < timesize; i++) {
			timelimits[i] = timelist.get(i).getTimeLimit();
		}
		//ページスコープに全時間帯、時間帯上限を設定
		pageContext.setAttribute("timelist", timelist);
		pageContext.setAttribute("timelimits", timelimits);
	} catch(Exception e){
		response.sendRedirect(request.getContextPath() + "/error");
	}
%>
<c:set var="now" value="${date}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title>全予約一覧画面</title>
<link rel="stylesheet" type="text/css" href="<c:url value='/css/common.css' />">
</head>
<body>
<%@ include file="/WEB-INF/jsp/common/header.jsp"%>
<h2>全予約一覧画面 </h2>
<div id="contents">
	<fmt:parseDate var="CurrentMonth" value="${year}/${month}" type="DATE" pattern="yyyy/MM" />
	<p id="date">
	<fmt:formatDate value="${CurrentMonth}" pattern="yyyy/MM" />の予約一覧
	</p>
	
	<%-- エラーメッセージ(全予約リストがある場合) --%>
	<c:if test="${ReserveAllListFlowBeanList != null and ReserveAllListErr != null }" >
        <div id="errormsg">
            <ul>
            <c:forEach items="${ReserveAllListErr}" var="err">
                <li>${err}</li>
            </c:forEach>
            </ul>
        </div>
        <br/>
	</c:if>
	
	<div id="page" style="display: inline-flex">
		<div class="controlbox">
			<%-- 前月の処理 --%>
			<c:choose>
				<%-- 1月の場合 --%>
				<c:when test="${month == 1}" >
					<fmt:parseDate var="PreviousMonth" value="${year-1}/12" type="DATE" pattern="yyyy/MM" />
				</c:when>
				<%-- それ以外 --%>
				<c:otherwise>
					<fmt:parseDate var="PreviousMonth" value="${year}/${month-1}" type="DATE" pattern="yyyy/MM" />
				</c:otherwise>
			</c:choose>
			<a href="reserve-all-list?date=<fmt:formatDate value="${PreviousMonth}" type="DATE" pattern="yyyy/MM" />" class="button-blue">前月</a>
			 
			<%-- 次月の処理 --%>
			<c:choose>
				<%-- 12月の場合 --%>
				<c:when test="${month == 12}" >
					<fmt:parseDate var="NextMonth" value="${year+1}/1" type="DATE" pattern="yyyy/MM" />
				</c:when>
				<%-- それ以外 --%>
				<c:otherwise>
					<fmt:parseDate var="NextMonth" value="${year}/${month+1}" type="DATE" pattern="yyyy/MM" />
				</c:otherwise>
			</c:choose>
			<a href="reserve-all-list?date=<fmt:formatDate value="${NextMonth}" type="DATE" pattern="yyyy/MM" />" class="button-blue">次月</a>
			
			<a href="<c:url value='/admin/reserve-all-list' />" class="button-blue">今月</a>
			
		</div>
		
		<label for="month_input">日付入力：(yyyy-MM)</label>
		<form id="month_input" action="<c:url value='/admin/reserve-all-list' />" method="post">
			<input type="month" name="date" maxlength="7"/>
			<input type="submit" value="検索" />
		</form>
	</div>

	<c:choose>
		<%-- 全予約情報がある --%>
		<c:when test="${ReserveAllListFlowBeanList != null}" >
			<table>
				<%-- 表のヘッダを作成 --%>
				<tr align="center">
					<th> 予約日 </th>
					<%-- 時間帯名を出力 --%>
					<c:forEach items="${timelist}" var="time">
						<th> ${time.timeName} </th>
					</c:forEach>
				</tr>
				
				<%-- 日ごとの予約情報を取得 --%>
				<c:forEach items="${ReserveAllListFlowBeanList}" var="ReserveAllListFlowBeanInMonth" varStatus="dayoffset">
					<tr align="center">
						<fmt:parseDate var="dayofmonth" value="${year}/${month}/${dayoffset.count}" type="BOTH" dateStyle="LONG" pattern="yyyy/MM/dd" />
						<td><fmt:formatDate value="${dayofmonth}" pattern="yyyy/MM/dd(E)" /></td>
						
						<c:set var="length" value="${fn:length(ReserveAllListFlowBeanInMonth.value)}" />
						<c:set var="index" value="0" />
						
						<c:forEach begin="1" end="${fn:length(timelist)}" varStatus="times">
							<fmt:parseDate var="reserve" value="${year}/${month}/${dayoffset.count} ${timelimits[times.count-1]}" type="BOTH" dateStyle="LONG" pattern="yyyy/MM/dd HH:mm" />
							<td>
								<c:choose>
									<c:when test="${index < length and not empty ReserveAllListFlowBeanInMonth.value}" >
										<c:set var="tmp" value="${ReserveAllListFlowBeanInMonth.value[index]}" />
										<c:choose>
											<c:when test="${tmp.timeCd == times.index and tmp.empName != null}">
												${tmp.empName}<br />
												${tmp.department}${tmp.tel}<br />
												${tmp.purpose}<br/>
												<c:set var="index" value="${index + 1 }" />												
											</c:when>
											<c:otherwise>
												<form action="<c:url value='/admin/reserve-all-list' />" method="post">
													<c:choose>
														<c:when test="${ reserve ge now }" >
															<input type="hidden" name="date" value="${year}/${month}/${dayoffset.count}">
															<input type="hidden" name="time" value="${ times.index }">
															<input type="submit" value="予約" class="button-ral">
														</c:when>
														<%-- (予約できませんを表示) --%>
														<c:otherwise>
															<input type="submit" value="予約不可" disabled="disabled" class="button">
														</c:otherwise>
													</c:choose>
												</form>
											</c:otherwise>
										</c:choose>
									</c:when>
									<c:otherwise>
										<form action="<c:url value='/admin/reserve-all-list' />" method="post">
											<c:choose>
												<c:when test="${ reserve ge now }" >
													<input type="hidden" name="date" value="${year}/${month}/${dayoffset.count}">
													<input type="hidden" name="time" value="${ times.index }">
													<input type="submit" value="予約" class="button-ral">
												</c:when>
												<%-- (予約できませんを表示) --%>
												<c:otherwise>
													<input type="submit" value="予約不可" disabled="disabled" class="button">
												</c:otherwise>
											</c:choose>
										</form>
									</c:otherwise>
								</c:choose>
							</td>
						</c:forEach>
					</tr>
				</c:forEach>
			</table>
		</c:when>
		<%-- 全予約情報がない --%>
		<c:otherwise>
				<fmt:parseDate var="CurrentMonth" value="${year}/${month}" type="DATE" pattern="yyyy/MM" />
				<p>
				<fmt:formatDate value="${CurrentMonth}" pattern="yyyy/MM" />の予約はありません
				</p>
		</c:otherwise>
	</c:choose>
	
	<div class="controlbox">
		<a href="<c:url value='/admin/menu' />" class="button-blue">メニューに戻る</a>
	</div>
	
	<%-- エラーメッセージ(全予約リストがない場合) --%>
	<c:if test="${ReserveAllListFlowBeanList == null and ReserveAllListErr != null}" >
        <div id="errormsg">
            <ul>
            <c:forEach items="${ReserveAllListErr}" var="err">
                <li>${err}</li>
            </c:forEach>
            </ul>
        </div>
	</c:if>
</div>
<script>
</script>
<%@ include file="/WEB-INF/jsp/common/logout.jsp"%>
</body>
<script>
</script>
</html>
