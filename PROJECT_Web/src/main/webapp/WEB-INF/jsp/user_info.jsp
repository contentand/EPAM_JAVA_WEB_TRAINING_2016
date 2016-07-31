<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@include file="shared/locale.jsp" %>

<html>
    <head>
        <title>User Info</title>
        <link href="${pageContext.request.contextPath}/resource/css/style.css" rel="stylesheet">
    </head>
    <body>
    <jsp:include page="shared/header.jsp"/>

    <form class="container">

        <h3><fmt:message key="page.user_info" bundle="${bd}"/></h3>
        <hr>

        <table>
            <tr>
                <td><fmt:message key="page.register_user.cyrillic_first_name" bundle="${bd}"/></td>
                <td><input disabled type="text" value="${sessionScope.user.cyrillicFirstName}"
                           name="cyrillicFirstName"></td>
            </tr>
            <tr>
                <td><fmt:message key="page.register_user.cyrillic_last_name" bundle="${bd}"/></td>
                <td><input disabled type="text" value="${sessionScope.user.cyrillicLastName}"
                           name="cyrillicLastName"></td>
            </tr>
            <tr>
                <td><fmt:message key="page.register_user.latin_first_name" bundle="${bd}"/></td>
                <td><input disabled type="text" value="${sessionScope.user.latinFirstName}"
                           name="latinFirstName"></td>
            </tr>
            <tr>
                <td><fmt:message key="page.register_user.latin_last_name" bundle="${bd}"/></td>
                <td><input disabled type="text" value="${sessionScope.user.latinLastName}"
                           name="latinLastName"></td>
            </tr>
            <tr>
                <td><fmt:message key="page.register_user.average_school_result" bundle="${bd}"/></td>
                <td><input disabled type="number" value="${sessionScope.user.averageSchoolResult}"
                           name="averageSchoolResult"></td>
            </tr>
        </table>
        <br>
        <div style="font-weight: bold"><fmt:message key="page.subjects" bundle="${bd}"/></div>
        <hr>
        <table id="subjects"></table>
    </form>


    <script>

        window.onload = function() {
            <c:forEach var="subject" items="${subjects}" varStatus="status">
            <c:set var="id" value="${subject.key}" scope="page"/>
            <c:if test="${not empty sessionScope[id]}">

            addSubject("${id}", "${subject.value}", ${sessionScope[id]})

            </c:if>
            <c:remove var="id"/>
            </c:forEach>
        }

        function addSubject(subjectId, subjectName, result) {
            document.getElementById("subjects").appendChild(document.getElementById("tmp_subject").content.cloneNode(true));
            document.getElementById("subjects").lastElementChild.childNodes.item(1).textContent = subjectName;
            document.getElementById("subjects").lastElementChild.childNodes.item(3).firstChild.setAttribute("name", subjectId);
            document.getElementById("subjects").lastElementChild.childNodes.item(3).firstChild.setAttribute("value", result);
        }

    </script>





    <template id="tmp_subject">
        <tr class="subject">
            <td><div class="subject_name"></div></td>
            <td><input disabled type="number"></td>
        </tr>
    </template>

    </body>
</html>