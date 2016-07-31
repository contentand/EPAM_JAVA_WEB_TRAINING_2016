<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${not empty sessionScope.msg_success}">
    <p class="msg_success">${sessionScope.msg_success}</p>
</c:if>
<c:if test="${not empty sessionScope.msg_error}">
    <p class="msg_error">${sessionScope.msg_error}</p>
</c:if>
<c:if test="${not empty sessionScope.msg}">
    <p class="msg">${sessionScope.msg}</p>
</c:if>



