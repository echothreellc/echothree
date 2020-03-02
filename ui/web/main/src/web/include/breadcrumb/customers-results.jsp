<%@ include file="../taglibs.jsp" %>
<c:choose>
    <c:when test="${param.showAsLink == 'true'}">
        <li class="breadcrumb-item"><a href="<c:url value="/action/Customer/Customer/Result" />"><fmt:message key="navigation.results" /></a></li>
    </c:when>
    <c:when test="${param.showAsLink == 'false'}">
        <li class="breadcrumb-item active" aria-current="page"><fmt:message key="navigation.results" /></li>
    </c:when>
</c:choose>
