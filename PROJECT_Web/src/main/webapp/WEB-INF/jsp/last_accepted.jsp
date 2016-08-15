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
    <h2>${facultyName}</h2>
    <att:applicant-table
            headName="page.selected_students"
            applicants="${studentsFromLastSelection}"
            buttonPresent="false"/>
</div>

</body>
</html>
