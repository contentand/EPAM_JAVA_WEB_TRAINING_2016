<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:set var="ru" value="ru"/>
<c:set var="en" value="en"/>
<c:set var="de" value="de"/>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:bundle basename="localization" prefix="page.commons.">

    <c:set var="startWithEn" value="${fn:startsWith(sessionScope.locale, en)}"/>
    <c:choose>
        <c:when test="${not startWithEn}">
            <form action="${pageContext.request.contextPath}/language" method="POST">
                <input type="hidden" name="afterProcessDestinationPath" value="${sessionScope.currentPageLink}">
                <input type="hidden" name="csrfToken" value="${sessionScope.csrfTokenServer}">
                <input type="hidden" name="language" value="en">
                <input class="header-element" type="submit" value="English">
            </form>
        </c:when>
        <c:otherwise>
            <div class="header-element unselected-header-element">English</div>
        </c:otherwise>
    </c:choose>

    <c:set var="startWithRu" value="${fn:startsWith(sessionScope.locale, ru)}"/>
    <c:choose>
        <c:when test="${not startWithRu}">
            <form action="${pageContext.request.contextPath}/language" method="POST">
                <input type="hidden" name="afterProcessDestinationPath" value="${sessionScope.currentPageLink}">
                <input type="hidden" name="csrfToken" value="${sessionScope.csrfTokenServer}">
                <input type="hidden" name="language" value="ru">
                <input class="header-element" type="submit" value="Русский">
            </form>
        </c:when>
        <c:otherwise>
            <div class="header-element unselected-header-element">Русский</div>
        </c:otherwise>
    </c:choose>


    <c:set var="startWithDe" value="${fn:startsWith(sessionScope.locale, de)}"/>
    <c:choose>
        <c:when test="${not startWithDe}">
            <form action="${pageContext.request.contextPath}/language" method="POST">
                <input type="hidden" name="afterProcessDestinationPath" value="${sessionScope.currentPageLink}">
                <input type="hidden" name="csrfToken" value="${sessionScope.csrfTokenServer}">
                <input type="hidden" name="language" value="de">
                <input class="header-element"type="submit" value="Deutsch"/>
            </form>
        </c:when>
        <c:otherwise>
            <div class="header-element unselected-header-element">Deutsch</div>
        </c:otherwise>
    </c:choose>


</fmt:bundle>