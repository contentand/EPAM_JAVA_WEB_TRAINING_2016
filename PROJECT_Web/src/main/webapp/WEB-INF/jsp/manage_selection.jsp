<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="att" uri="http://daniilyurov.com/jsp/tlds/att" %>

<%@include file="shared/locale.jsp" %>

<html>
<head>
    <title>University</title>
    <link href="${pageContext.request.contextPath}/resource/css/style.css" rel="stylesheet">
</head>
<body>
<jsp:include page="shared/header.jsp"/>

<div id="content">
    <h2>${faculty.localName}</h2>

    <c:if test="${faculty.registrationStatus == 'OVER_UNSET'}">
        <p><fmt:message key="page.consider_everyone_to_select" bundle="${bd}"/></p>
    </c:if>

    <c:if test="${faculty.registrationStatus == 'OVER_UNSET'
              and faculty.numberOfStudentsToAddUnderConsideration == 0}">

        <form method="POST" action="${pageContext.request.contextPath}/faculty/${faculty.id}/select-best">
            <input type="hidden" name="afterProcessDestinationPath"
                   value="${pageContext.request.contextPath}/faculty/${faculty.id}/selection/last">
            <input type="hidden" name="csrfToken" value="${sessionScope.csrfTokenServer}">
            <input class="small-btn" type="submit" value="<fmt:message key="page.select_the_best" bundle="${bd}"/>">
        </form>
    </c:if>

    <att:applicant-table
            headName="page.select_the_best"
            applicants="${applicantsUnderConsideration}"
            buttonPresent="false"/>


    <att:applicant-table
            headName="page.select_the_best"
            applicants="${unconsideredApplicants}"
            buttonPresent="true"/>

</div>
</body>
</html>
