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



    <form class="container" method="POST" action="${pageContext.request.contextPath}/user/new">

        <h3><fmt:message key="page.user_registration" bundle="${bd}"/></h3>
        <hr>

        <input type="hidden" name="csrfToken" value="${sessionScope.csrfTokenServer}">
        <input type="hidden" name="afterProcessDestinationPath" value="${sessionScope.currentPageLink}">

        <table>
            <tr>
                <td><fmt:message key="page.register_user.login" bundle="${bd}"/></td>
                <td><input type="text" value="${sessionScope.login}"
                           name="login" pattern="[A-Za-z0-9]{6,}"
                           title="<fmt:message key="page.register_user.login_pattern" bundle="${bd}"/>">
                </td>
                <td></td>
                <td><fmt:message key="page.register_user.cyrillic_first_name" bundle="${bd}"/></td>
                <td><input type="text" value="${sessionScope.cyrillicFirstName}" name="cyrillicFirstName"></td>
            </tr>
            <tr>
                <td><fmt:message key="page.register_user.password" bundle="${bd}"/></td>
                <td><input type="password" value="${sessionScope.password}" name="password"></td>
                <td></td>
                <td><fmt:message key="page.register_user.cyrillic_last_name" bundle="${bd}"/></td>
                <td><input type="text" value="${sessionScope.cyrillicLastName}" name="cyrillicLastName"></td>
            </tr>
            <tr>
                <td><fmt:message key="page.register_user.email" bundle="${bd}"/></td>
                <td><input type="email" value="${sessionScope.email}" name="email"></td>
                <td></td>
                <td><fmt:message key="page.register_user.latin_first_name" bundle="${bd}"/></td>
                <td><input type="text" value="${sessionScope.latinFirstName}" name="latinFirstName"></td>
            </tr>
            <tr>
                <td><fmt:message key="page.register_user.average_school_result" bundle="${bd}"/></td>
                <td><input type="number" value="${sessionScope.averageSchoolResult}" name="averageSchoolResult"></td>
                <td></td>
                <td><fmt:message key="page.register_user.latin_last_name" bundle="${bd}"/></td>
                <td><input type="text" value="${sessionScope.latinLastName}" name="latinLastName"></td>
            </tr>
        </table>
        <p>* - <fmt:message key="page.register_user.average_school_result_in_200_system" bundle="${bd}"/></p>

        <table style="width:100%">
            <tr>
                <td><fmt:message key="page.subjects" bundle="${bd}"/> : </td>
                <td style="text-align: right">
                    <select id="selectBox" onchange="chooseSubject(this);">
                        <option disabled selected><fmt:message key="page.add_subject" bundle="${bd}"/></option>
                        <c:forEach var="subject" items="${subjects}" varStatus="status">
                            <option value="<c:out value="${subject.key}"/>"><c:out value="${subject.value}"/></option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
        </table>

        <hr>
        <table id="subjects"></table>
        <hr>
        <input class="small-btn" type="submit"
               value="<fmt:message key="page.register_user.button.register" bundle="${bd}"/>">
    </form>


    <script>

        window.onload = function() {
            <c:forEach var="subject" items="${subjects}" varStatus="status">
            <c:set var="id" value="${subject.key}" scope="page"/>
            <c:if test="${not empty sessionScope[id]}">

            deleteOption("${id}");
            addSubject("${id}", "${subject.value}", ${sessionScope[id]})

            </c:if>
            <c:remove var="id"/>
            </c:forEach>
        }

        function deleteOption(value) {
            var selectBox = document.getElementById("selectBox")
            for (var i=0; i< selectBox.length; i++){
                if (selectBox.options[i].value == value )
                    selectBox.remove(i);
            }
        }

        function addSubject(subjectId, subjectName, result) {
            document.getElementById("subjects").appendChild(document
                    .getElementById("tmp_subject").content.cloneNode(true));
            document.getElementById("subjects").lastElementChild.childNodes.item(1)
                    .textContent = subjectName;
            document.getElementById("subjects").lastElementChild.childNodes.item(3)
                    .firstChild.setAttribute("name", subjectId);
            document.getElementById("subjects").lastElementChild.childNodes.item(3)
                    .firstChild.setAttribute("value", result);
        }

        function chooseSubject(selectBox) {
            var subjectId = selectBox.options[selectBox.selectedIndex].value;
            var subjectName = selectBox.options[selectBox.selectedIndex].textContent;
            selectBox.removeChild(selectBox.options[selectBox.selectedIndex]);
            selectBox.selectedIndex = 0;

            addSubject(subjectId, subjectName);


        }

        function removeSubject(subject) {
            console.log(subject.parentNode.parentNode.childNodes);

            console.log(subject.parentNode.parentNode.childNodes.item(3));

            var option = new Option();
            option.setAttribute("value", subject.parentNode.parentNode.childNodes.item(3).getAttribute("name"));
            option.text = subject.parentNode.parentNode.childNodes.item(1).textContent;
            document.getElementById("selectBox").appendChild(option);
            document.getElementById("subjects").removeChild(subject.parentNode.parentNode);
        }

    </script>





    <template id="tmp_subject">
        <tr class="subject">
            <td><div class="subject_name"></div></td>
            <td><input type="number"></td>
            <td><div class="delete" onclick="removeSubject(this);">X</div></td>
        </tr>
    </template>

</body>
</html>