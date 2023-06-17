<%@ include file="taglibs.jsp" %>

<h2>Entity Types</h2>
<et:checkSecurityRoles securityRoles="PartyEntityType.Create:PartyEntityType.Edit:PartyEntityType.Delete:ComponentVendor.Review:EntityType.Review" />
<!--
<et:hasSecurityRole securityRole="PartyEntityType.Create">
    <c:url var="addEntityTypeUrl" value="/action/${commonUrl}/Add">
        <c:param name="PartyName" value="${party.partyName}" />
    </c:url>
    <p><a href="${addEntityTypeUrl}">Add Entity Types.</a></p>
</et:hasSecurityRole>
-->
<c:choose>
    <c:when test='${partyEntityTypes.size == 0}'>
        <br />
    </c:when>
    <c:otherwise>
        <et:hasSecurityRole securityRole="ComponentVendor.Review" var="includeComponentVendorReviewUrl" />
        <et:hasSecurityRole securityRole="EntityType.Review" var="includeEntityTypeReviewUrl" />
        <display:table name="partyEntityTypes.list" id="partyEntityType" class="displaytag">
            <display:column titleKey="columnTitle.componentVendor">
                <c:choose>
                    <c:when test="${includeComponentVendorReviewUrl}">
                        <c:url var="reviewUrl" value="/action/Core/ComponentVendor/Review">
                            <c:param name="ComponentVendorName" value="${partyEntityType.entityType.componentVendor.componentVendorName}" />
                        </c:url>
                        <a href="${reviewUrl}"><c:out value="${partyEntityType.entityType.componentVendor.description}" /></a>
                    </c:when>
                    <c:otherwise>
                        <c:out value="${partyEntityType.entityType.componentVendor.description}" />
                    </c:otherwise>
                </c:choose>
            </display:column>
            <display:column titleKey="columnTitle.entityType">
                <c:choose>
                    <c:when test="${includeEntityTypeReviewUrl}">
                        <c:url var="reviewUrl" value="/action/Core/EntityType/Review">
                            <c:param name="ComponentVendorName" value="${partyEntityType.entityType.componentVendor.componentVendorName}" />
                            <c:param name="EntityTypeName" value="${partyEntityType.entityType.entityTypeName}" />
                        </c:url>
                        <a href="${reviewUrl}"><c:out value="${partyEntityType.entityType.description}" /></a>
                    </c:when>
                    <c:otherwise>
                        <c:out value="${partyEntityType.entityType.description}" />
                    </c:otherwise>
                </c:choose>
            </display:column>
            <display:column titleKey="columnTitle.confirmDelete">
                <c:choose>
                    <c:when test="${partyEntityType.confirmDelete}">
                        <fmt:message key="phrase.yes" />
                    </c:when>
                    <c:otherwise>
                        <fmt:message key="phrase.no" />
                    </c:otherwise>
                </c:choose>
            </display:column>
            <et:hasSecurityRole securityRoles="PartyEntityType.Edit:PartyEntityType.Delete">
                <display:column>
                    <et:hasSecurityRole securityRole="PartyEntityType.Edit">
                        <c:url var="editUrl" value="/action/${commonUrl}/Edit">
                            <c:param name="PartyName" value="${partyEntityType.party.partyName}" />
                            <c:param name="ComponentVendorName" value="${partyEntityType.entityType.componentVendor.componentVendorName}" />
                            <c:param name="EntityTypeName" value="${partyEntityType.entityType.entityTypeName}" />
                        </c:url>
                        <a href="${editUrl}">Edit</a>
                    </et:hasSecurityRole>
                    <et:hasSecurityRole securityRole="PartyEntityType.Delete">
                        <c:url var="deleteUrl" value="/action/${commonUrl}/Delete">
                            <c:param name="PartyName" value="${partyEntityType.party.partyName}" />
                            <c:param name="ComponentVendorName" value="${partyEntityType.entityType.componentVendor.componentVendorName}" />
                            <c:param name="EntityTypeName" value="${partyEntityType.entityType.entityTypeName}" />
                        </c:url>
                        <a href="${deleteUrl}">Delete</a>
                    </et:hasSecurityRole>
                </display:column>
            </et:hasSecurityRole>
        </display:table>
    </c:otherwise>
</c:choose>
<br />
