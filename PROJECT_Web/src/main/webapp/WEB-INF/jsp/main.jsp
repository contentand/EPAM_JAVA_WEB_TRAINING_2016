<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@include file="shared/locale.jsp" %>

<html>
<head>
    <title>University</title>
    <link href="${pageContext.request.contextPath}/resource/css/style.css" rel="stylesheet">
</head>
<body>
    <jsp:include page="shared/header.jsp"/>

<%-- future feature
    <c:if test="${sessionScope.authority == 'ADMINISTRATOR'}">
        <a href="${pageContext.request.contextPath}/faculty/new">CREATE FACULTY!</a>
    </c:if>
--%>

    <c:forEach var="faculty" items="${sessionScope.faculties}">

        <div class="faculty">
            <%@include file="faculty/overal-info.jsp" %>
            <div class="faculty-image"></div>
            <div class="registration-info">
                <%@include file="faculty/applicant-action.jsp" %>
                <%@include file="faculty/administrator-action.jsp" %>
                <%--<%@include file="faculty/registration-details.jsp" %>--%>

                <c:if test="${faculty.registrationStatus == 'SCHEDULED' or faculty.registrationStatus == 'IN_PROGRESS'}">
                    <h3><fmt:message key="page.h_registration_details" bundle="${bd}"/></h3>
                    <div class="registration-details">
                        <table>
                            <tr>
                                <td><fmt:message key="page.registrationStart" bundle="${bd}"/> : </td>
                                <td>${faculty.registrationStart}</td>
                            </tr>
                            <tr>
                                <td><fmt:message key="page.registrationEnd" bundle="${bd}"/> : </td>
                                <td>${faculty.registrationEnd}</td>
                            </tr>
                            <tr>
                                <td><fmt:message key="page.studyStart" bundle="${bd}"/> : </td>
                                <td>${faculty.studyStart}</td>
                            </tr>
                            <tr>
                                <td><fmt:message key="page.duration" bundle="${bd}"/> : </td>
                                <td>${faculty.duration} <fmt:message key="page.months" bundle="${bd}"/></td>
                            </tr>
                            <tr>
                                <td><fmt:message key="page.maxStudents" bundle="${bd}"/> : </td>
                                <td>${faculty.maxStudents}</td>
                            </tr>
                            <c:if test="${faculty.registrationStatus == 'IN_PROGRESS'}">
                                <tr>
                                    <td><fmt:message key="page.numberOfAppliedStudents" bundle="${bd}"/> : </td>
                                    <td>${faculty.numberOfAppliedStudents}</td>
                                </tr>
                            </c:if>
                        </table>
                    </div>
                </c:if>
            </div>
        </div>
    </c:forEach>

</body>
</html>
