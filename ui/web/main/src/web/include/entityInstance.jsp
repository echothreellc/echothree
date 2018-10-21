<%@ include file="taglibs.jsp" %>

<c:if test='${entityInstance.entityTime != null}'>
    Created: <c:out value="${entityInstance.entityTime.createdTime}" /><br />
    <c:if test='${entityInstance.entityTime.modifiedTime != null}'>
        Modified: <c:out value="${entityInstance.entityTime.modifiedTime}" /><br />
    </c:if>
    <c:if test='${entityInstance.entityTime.deletedTime != null}'>
        Deleted: <c:out value="${entityInstance.entityTime.deletedTime}" /><br />
    </c:if>
</c:if>
<et:checkSecurityRoles securityRoles="Event.List" />
<et:hasSecurityRole securityRole="Event.List">
    <c:url var="eventsUrl" value="/action/Core/Event/Main">
        <c:param name="EntityRef" value="${entityInstance.entityRef}" />
    </c:url>
    <a href="${eventsUrl}">Events</a>
</et:hasSecurityRole>
