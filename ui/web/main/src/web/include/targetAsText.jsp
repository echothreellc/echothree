<%@ include file="taglibs.jsp" %>

<c:choose>
    <c:when test="${entityInstance.description == null}">
        <c:out value="${entityInstance.entityRef}" />
    </c:when>
    <c:otherwise>
        <c:out value="${entityInstance.description}" />
    </c:otherwise>
</c:choose>
