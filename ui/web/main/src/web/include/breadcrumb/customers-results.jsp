<%@ include file="../taglibs.jsp" %>
<c:choose>
    <c:when test="${param.showAsLink == 'true'}">
        <et:countCustomerResults searchTypeName="ORDER_ENTRY" countVar="customerResultsCount" commandResultVar="countCustomerResultsCommandResult" logErrors="false" />
        <c:if test="${customerResultsCount > 0}">
            <li class="breadcrumb-item">
                <a href="<c:url value="/action/Customer/Customer/Result" />">
                    <fmt:message key="navigation.results" />
                </a>
            </li>
        </c:if>
    </c:when>
    <c:when test="${param.showAsLink == 'false'}">
        <li class="breadcrumb-item active" aria-current="page">
            <fmt:message key="navigation.results" />
        </li>
    </c:when>
</c:choose>
