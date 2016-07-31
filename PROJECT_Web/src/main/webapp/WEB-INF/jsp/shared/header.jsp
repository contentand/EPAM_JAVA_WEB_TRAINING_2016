<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:bundle basename="localization" prefix="page.commons.">
    <div id="notifier" class="sparkling">
        <jsp:include page="feedback.jsp"/>
    </div>
    <header>
        <nav class="left-navigation">
            <c:if test="${not sessionScope.isMain}">
                <a class="header-element" href="${pageContext.request.contextPath}/"><fmt:message key="back"/></a>
            </c:if>
            <c:if test="${not empty sessionScope.userId
                            and not sessionScope.isUserInfoPage}">
                <a class="header-element" href="${pageContext.request.contextPath}/user">
                    <fmt:message key="edit_profile"/>
                </a>
            </c:if>
            <c:if test="${sessionScope.authority == 'GUEST'
                  and not sessionScope.isLoginPage}">

                <a class="header-element important-header-element" href="${pageContext.request.contextPath}/login">
                    <fmt:message key="login"/>
                </a>
            </c:if>
            <c:if test="${sessionScope.authority == 'GUEST'
                  and not sessionScope.isRegistrationPage}">

                <a class="header-element" href="${pageContext.request.contextPath}/user/new ">
                    <fmt:message key="register"/>
                </a>
            </c:if>

        </nav>
        <nav class="right-navigation">
            <jsp:include page="language.jsp"/>
            <c:if test="${not empty sessionScope.userId}">
                <form action="${pageContext.request.contextPath}/logout" method="POST">
                    <input type="hidden" name="afterProcessDestinationPath" value="${sessionScope.currentPageLink}">
                    <input type="hidden" name="csrfToken" value="${sessionScope.csrfTokenServer}">
                    <input class="header-element important-header-element"
                           type="submit" value="<fmt:message key="logout"/>">
                </form>
            </c:if>
        </nav>
    </header>
    <div style="clear:both"/>
    <div id="top-offset">1</div>
</fmt:bundle>
