<%@ include file="../taglibs.jsp" %>
<c:choose>
    <c:when test="${param.showAsLink == 'true'}">
        <li class="breadcrumb-item">
            <a href="<c:url value="/action/Portal" />">
                <fmt:message key="navigation.portal" />
            </a>
        </li>
    </c:when>
    <c:when test="${param.showAsLink == 'false'}">
        <li class="breadcrumb-item active" aria-current="page">
            <fmt:message key="navigation.portal" />
        </li>
    </c:when>
</c:choose>
