
<%-- SECTION FOR ADMINISTRATORS --%>

<c:if test="${sessionScope.authority == 'ADMINISTRATOR'}">

    <%-- Current faculty status regarding the student selection --%>
    <p><fmt:message key="page.status.${faculty.registrationStatus}" bundle="${bd}"/></p>

    <%-- Statistics if selection is currently in process or students have not been selected --%>
    <c:if test="${faculty.registrationStatus == 'IN_PROGRESS'
               or faculty.registrationStatus == 'OVER_UNSET'}">

        <table>
            <tr>
                <td><fmt:message key="page.numberToAddToList" bundle="${bd}"/> :</td>
                <td>${faculty.numberOfStudentsToAddUnderConsideration}</td>
            </tr>
        </table>
    </c:if>

<%--
    <c:if test="${faculty.registrationStatus == 'SCHEDULED'}">
        <form action="${pageContext.request.contextPath}/faculty/${faculty.id}/selection/cancel" method="POST">
            <input type="hidden" name="afterProcessDestinationPath" value="${sessionScope.currentPageLink}">
            <input type="hidden" name="csrfToken" value="${sessionScope.csrfTokenServer}">
            <input type="submit" class="small-btn" value="<fmt:message key="page.cancel_scheduled_selection"
                                                                       bundle="${bd}"/>">
        </form>
    </c:if>
--%>
    <c:if test="${faculty.registrationStatus == 'IN_PROGRESS'
               or faculty.registrationStatus == 'OVER_UNSET'}">
        <form action="${pageContext.request.contextPath}/faculty/${faculty.id}/selection/" method="GET">
            <input type="submit" class="small-btn" value="<fmt:message key="page.manage_selection" bundle="${bd}"/>">
        </form>
    </c:if>
<%--
    <c:if test="${faculty.registrationStatus == 'OVER_SET'}">
        <form action="${pageContext.request.contextPath}/faculty/${faculty.id}/selection/new" method="GET">
            <input type="submit" class="small-btn" value="<fmt:message key="page.create_new_selection" bundle="${bd}"/>">
        </form>
    </c:if>
--%>
<%--
    <form action="${pageContext.request.contextPath}/faculty/${faculty.id}" method="GET">
        <input type="submit" class="small-btn" value="<fmt:message key="page.edit_faculty"  bundle="${bd}"/>">
    </form>
--%>
</c:if>