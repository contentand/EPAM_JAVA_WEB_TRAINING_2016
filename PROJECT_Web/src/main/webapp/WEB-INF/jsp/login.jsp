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

    <form class="login" method="POST" action="${pageContext.request.contextPath}/login">
        <input type="hidden" name="afterProcessDestinationPath" value="${sessionScope.currentPageLink}">
        <input type="hidden" name="csrfToken" value="${sessionScope.csrfTokenServer}">

        <h3>Gaudeamus Igitur</h3>
        <hr>
        <table>
            <tr>
                <td><fmt:message key="page.login.login" bundle="${bd}"/></td>
                <td><input type="text" name="login"></td>
            </tr>
            <tr>
                <td><fmt:message key="page.login.password" bundle="${bd}"/></td>
                <td><input type="password" name="password"></td>
            </tr>
        </table>
        <br>
        <input class="small-btn" type="submit"
               value="<fmt:message key="page.login.button.login"
               bundle="${bd}"/>">
    </form>

</body>
</html>
