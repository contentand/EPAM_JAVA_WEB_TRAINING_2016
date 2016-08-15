<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@include file="shared/locale.jsp" %>

<html>
<head>
    <title><fmt:message key="page.forbidden" bundle="${bd}"/></title>
    <link href="${pageContext.request.contextPath}/resource/css/style.css" rel="stylesheet">
</head>
<body>
<jsp:include page="shared/header.jsp"/>

<div style="margin-top: 200px;
            font-size: 20px;
            text-align: center;">
    <fmt:message key="page.forbidden" bundle="${bd}"/>
</div>

</body>
</html>
