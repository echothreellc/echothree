<%@ include file="../taglibs.jsp" %>
<c:choose>
    <c:when test="${param.showAsLink == 'true'}">
        <li class="breadcrumb-item">
            <c:url var="customerReviewUrl" value="/action/Customer/Customer/Review">
                <c:param name="CustomerName" value="${customer.customerName}" />
            </c:url>
            <a href="${customerReviewUrl}">
                <fmt:message key="navigation.customer">
                    <fmt:param value="${customer.customerName}" />
                </fmt:message>
            </a>
        </li>
    </c:when>
    <c:when test="${param.showAsLink == 'false'}">
        <li class="breadcrumb-item active" aria-current="page">
            <fmt:message key="navigation.customer">
                <fmt:param value="${customer.customerName}" />
            </fmt:message>
        </li>
    </c:when>
</c:choose>
