<%@ include file="taglibs.jsp" %>

<h2><c:out value="${ratings.ratingType.description}" /> Ratings</h2>
<c:url var="addUrl" value="/action/${commonUrl}/RatingAdd">
    <c:param name="ItemName" value="${item.itemName}" />
    <c:param name="RatingTypeName" value="${ratings.ratingType.ratingTypeName}" />
</c:url>
<p><a href="${addUrl}">Add <c:out value="${ratings.ratingType.description}" /> Rating.</a></p>
<c:choose>
    <c:when test='${ratings.size == 0}'>
        <br />
    </c:when>
    <c:otherwise>
        <et:checkSecurityRoles securityRoles="Event.List" />
        <display:table name="ratings.list" id="rating" class="displaytag">
            <display:column titleKey="columnTitle.dateTime">
                <c:out value="${rating.entityInstance.entityTime.createdTime}" />
            </display:column>
            <display:column titleKey="columnTitle.rating">
                <c:out value="${rating.ratingTypeListItem.description}" />
            </display:column>
            <display:column titleKey="columnTitle.enteredBy">
                <c:set var="entityInstance" scope="request" value="${rating.ratedByEntityInstance}" />
                <jsp:include page="targetAsReviewLink.jsp" />
            </display:column>
            <display:column>
                <c:url var="editUrl" value="/action/${commonUrl}/RatingEdit">
                    <c:param name="ItemName" value="${item.itemName}" />
                    <c:param name="RatingName" value="${rating.ratingName}" />
                </c:url>
                <a href="${editUrl}">Edit</a>
                <c:url var="deleteUrl" value="/action/${commonUrl}/RatingDelete">
                    <c:param name="ItemName" value="${item.itemName}" />
                    <c:param name="RatingName" value="${rating.ratingName}" />
                </c:url>
                <a href="${deleteUrl}">Delete</a>
            </display:column>
            <et:hasSecurityRole securityRole="Event.List">
                <display:column>
                    <c:url var="eventsUrl" value="/action/Core/Event/Main">
                        <c:param name="EntityRef" value="${rating.entityInstance.entityRef}" />
                    </c:url>
                    <a href="${eventsUrl}">Events</a>
                </display:column>
            </et:hasSecurityRole>
        </display:table>
    </c:otherwise>
</c:choose>
<br />
