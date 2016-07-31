<%-- SECTION FOR USUAL NON-ADMIN VISITORS --%>
<c:if test="${sessionScope.authority != 'ADMINISTRATOR'}">
    <div class="registration-action">

        <%-- INFORMATION ABOUT THE STATUS OF THE LAST APPLICATION SENT TO THE FACULTY --%>
        <c:if test="${not empty faculty.latestStatusOfCurrentUser}">
            <div class="info">
                <fmt:message key="page.${faculty.latestStatusOfCurrentUser}" bundle="${bd}"/>
            </div>
            <hr>
        </c:if>
        <%-- ACTION BUTTON FOR STUDENTS OF THE FACULTY --%>
        <c:if test="${faculty.latestStatusOfCurrentUser == 'ACCEPTED'}">
            <form action="${pageContext.request.contextPath}/application/${faculty
                                           .applicationIdForCurrentSelection}/quit" method="POST">

                <input type="hidden" name="afterProcessDestinationPath" value="${sessionScope.currentPageLink}">
                <input type="hidden" name="csrfToken" value="${sessionScope.csrfTokenServer}">
                <input class="btn action-stop" type="submit"
                       value="<fmt:message key="page.quit_studies" bundle="${bd}"/>">
            </form>
        </c:if>

        <%-- INFORMATION IF REGISTRATION IS NOT CURRENTLY AVAILABLE --%>
        <c:if test="${faculty.registrationStatus == 'OVER_SET' or faculty.registrationStatus == 'OVER_UNSET'}">
            <div class="info"><fmt:message key="page.registration_over" bundle="${bd}"/></div>
        </c:if>

        <%-- ACTION BUTTON FOR CANDIDATES. AVAILABLE WHEN REGISTRATION IS ON --%>
        <c:if test="${faculty.registrationStatus == 'IN_PROGRESS'}">
            <c:choose>

                <%-- Cancel application for those applied or under consideration for current selection --%>
                <c:when test="${not empty faculty.applicationIdForCurrentSelection
                                     and (faculty.latestStatusOfCurrentUser == 'APPLIED' or
                                          faculty.latestStatusOfCurrentUser == 'UNDER_CONSIDERATION')}">

                    <form action="${pageContext.request.contextPath}/application/${faculty
                                           .applicationIdForCurrentSelection}/cancel" method="POST">
                        <input type="hidden" name="afterProcessDestinationPath" value="${sessionScope.currentPageLink}">
                        <input type="hidden" name="csrfToken" value="${sessionScope.csrfTokenServer}">
                        <input class="btn action-stop" type="submit"
                               value="<fmt:message key="page.cancel_application" bundle="${bd}"/>">
                    </form>
                </c:when>

                <%-- Apply for those who cancelled application for current selection --%>
                <c:when test="${not empty faculty.applicationIdForCurrentSelection
                                      and faculty.latestStatusOfCurrentUser == 'CANCELLED'}">

                    <form action="${pageContext.request.contextPath}/application/${faculty
                                           .applicationIdForCurrentSelection}/reapply" method="POST">
                        <input type="hidden" name="afterProcessDestinationPath" value="${sessionScope.currentPageLink}">
                        <input type="hidden" name="csrfToken" value="${sessionScope.csrfTokenServer}">
                        <input class="btn action-start" type="submit"
                               value="<fmt:message key="page.send_application_again" bundle="${bd}"/>">
                    </form>


                </c:when>

                <%-- Apply for those who previously quit studies --%>
                <c:when test="${faculty.latestStatusOfCurrentUser == 'QUIT'}">
                    <form action="${pageContext.request.contextPath}/application/new" method="POST">
                        <input type="hidden" name="afterProcessDestinationPath" value="${sessionScope.currentPageLink}">
                        <input type="hidden" name="csrfToken" value="${sessionScope.csrfTokenServer}">
                        <input type="hidden" name="faculty" value="${faculty.id}">
                        <input type="hidden" name="action" value="apply">
                        <input class="btn action-start" type="submit"
                               value="<fmt:message key="page.send_application_again" bundle="${bd}"/>">
                    </form>
                </c:when>

                <%-- Apply for those who were previously rejected or expelled --%>
                <c:when test="${faculty.latestStatusOfCurrentUser == 'EXPELLED'
                                or faculty.latestStatusOfCurrentUser == 'REJECTED'}">

                    <form action="${pageContext.request.contextPath}/application/new" method="POST">
                        <input type="hidden" name="afterProcessDestinationPath" value="${sessionScope.currentPageLink}">
                        <input type="hidden" name="csrfToken" value="${sessionScope.csrfTokenServer}">
                        <input type="hidden" name="faculty" value="${faculty.id}">
                        <input type="hidden" name="action" value="apply">
                        <input class="btn action-start" type="submit"
                               value="<fmt:message key="page.try_application_again" bundle="${bd}"/>">
                    </form>

                </c:when>

                <%-- Graduates cannot apply since they have alredy finished studies. --%>
                <c:when test="${faculty.latestStatusOfCurrentUser == 'GRADUATED'}">
                    <!-- No action -->
                </c:when>

                <%-- Apply for guests or those who cancelled application in previous selections. --%>
                <c:when test="${(empty faculty.latestStatusOfCurrentUser
                                and faculty.latestStatusOfCurrentUser == 'CANCELLED')
                                or (empty faculty.latestStatusOfCurrentUser and empty faculty.latestStatusOfCurrentUser)
                                or sessionScope.authority == 'GUEST'}">
                    <form action="${pageContext.request.contextPath}/application/new" method="POST">
                        <input type="hidden" name="afterProcessDestinationPath" value="${sessionScope.currentPageLink}">
                        <input type="hidden" name="csrfToken" value="${sessionScope.csrfTokenServer}">
                        <input type="hidden" name="faculty" value="${faculty.id}">
                        <input type="hidden" name="action" value="apply">
                        <input class="btn action-start" type="submit"
                               value="<fmt:message key="page.send_application" bundle="${bd}"/>">
                    </form>
                </c:when>
            </c:choose>
        </c:if>
    </div>
</c:if>