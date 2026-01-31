<%@ include file="taglibs.jsp" %>

<h2><c:out value="${comments.commentType.description}" /> Comments</h2>
<c:url var="addUrl" value="/action/${commonUrl}/CommentAdd">
    <c:param name="ItemName" value="${item.itemName}" />
    <c:param name="CommentTypeName" value="${comments.commentType.commentTypeName}" />
</c:url>
<p><a href="${addUrl}">Add <c:out value="${comments.commentType.description}" /> Comment.</a></p>
<c:choose>
    <c:when test='${comments.size == 0}'>
        <br />
    </c:when>
    <c:otherwise>
        <et:checkSecurityRoles securityRoles="Event.List" />
        <display:table name="comments.list" id="comment" class="displaytag">
            <display:column titleKey="columnTitle.dateTime">
                <c:out value="${comment.entityInstance.entityTime.createdTime}" />
            </display:column>
            <display:column titleKey="columnTitle.comment">
                <c:if test='${comment.description != null}'>
                    <b><c:out value="${comment.description}" /></b><br />
                </c:if>
                <et:out value="${comment.clob}" mimeTypeName="${comment.mimeType.mimeTypeName}" />
            </display:column>
            <display:column titleKey="columnTitle.enteredBy">
                <c:set var="entityInstance" scope="request" value="${comment.commentedByEntityInstance}" />
                <jsp:include page="targetAsReviewLink.jsp" />
            </display:column>
            <c:if test="${comments.commentType.workflowEntrance != null}">
                <display:column titleKey="columnTitle.status">
                    <c:url var="statusUrl" value="/action/${commonUrl}/CommentStatus">
                        <c:param name="ItemName" value="${item.itemName}" />
                        <c:param name="CommentName" value="${comment.commentName}" />
                    </c:url>
                    <a href="${statusUrl}"><c:out value="${comment.commentStatus.workflowStep.description}" /></a>
                </display:column>
            </c:if>
            <display:column>
                <c:url var="editUrl" value="/action/${commonUrl}/CommentEdit">
                    <c:param name="ItemName" value="${item.itemName}" />
                    <c:param name="CommentName" value="${comment.commentName}" />
                </c:url>
                <a href="${editUrl}">Edit</a>
                <c:url var="deleteUrl" value="/action/${commonUrl}/CommentDelete">
                    <c:param name="ItemName" value="${item.itemName}" />
                    <c:param name="CommentName" value="${comment.commentName}" />
                </c:url>
                <a href="${deleteUrl}">Delete</a>
            </display:column>
            <et:hasSecurityRole securityRole="Event.List">
                <display:column>
                    <c:url var="eventsUrl" value="/action/Core/Event/Main">
                        <c:param name="EntityRef" value="${comment.entityInstance.entityRef}" />
                    </c:url>
                    <a href="${eventsUrl}">Events</a>
                </display:column>
            </et:hasSecurityRole>
        </display:table>
    </c:otherwise>
</c:choose>
<br />
