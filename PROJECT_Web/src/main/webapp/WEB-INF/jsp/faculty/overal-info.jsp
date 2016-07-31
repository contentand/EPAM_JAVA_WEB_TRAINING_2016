<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="overall-info">
    <h3>${faculty.localName}</h3>
    <hr>
    <p>${faculty.localDescription}</p>
    <div class="requirements"><fmt:message key="page.requirements" bundle="${bd}"/>:</div>
    <hr>
    <c:forEach var="subject" items="${faculty.requiredSubjects}">
        <div class="subject">${subject}</div>
    </c:forEach>
    <hr>
    <div ><fmt:message key="page.nStudents" bundle="${bd}"/>: ${faculty.numberOfStudents},
        <fmt:message key="page.nGraduates" bundle="${bd}"/>: ${faculty.numberOfGraduates}</div>
</div>