<%@ include file="taglibs.jsp" %>

<h2>Scale Uses</h2>
<et:checkSecurityRoles securityRoles="PartyScaleUse.Create:PartyScaleUse.Edit:PartyScaleUse.Delete:ScaleUseType.Review:Scale.Review" />
<et:hasSecurityRole securityRole="PartyScaleUse.Create">
    <c:url var="addScaleUseUrl" value="/action/${commonUrl}/Add">
        <c:param name="PartyName" value="${party.partyName}" />
    </c:url>
    <p><a href="${addScaleUseUrl}">Add Scale Use.</a></p>
</et:hasSecurityRole>
<c:choose>
    <c:when test='${partyScaleUses.size == 0}'>
        <br />
    </c:when>
    <c:otherwise>
        <et:hasSecurityRole securityRole="ScaleUseType.Review" var="includeScaleUseTypeReviewUrl" />
        <et:hasSecurityRole securityRole="Scale.Review" var="includeScaleReviewUrl" />
        <display:table name="partyScaleUses.list" id="partyScaleUse" class="displaytag">
            <display:column titleKey="columnTitle.scaleUseType">
                <c:choose>
                    <c:when test="${includeScaleUseTypeReviewUrl}">
                        <c:url var="scaleUseTypeUrl" value="/action/Configuration/ScaleUseType/Review">
                            <c:param name="ScaleUseTypeName" value="${partyScaleUse.scaleUseType.scaleUseTypeName}" />
                        </c:url>
                        <a href="${scaleUseTypeUrl}"><c:out value="${partyScaleUse.scaleUseType.description}" /></a>
                    </c:when>
                    <c:otherwise>
                        <c:out value="${partyScaleUse.scaleUseType.description}" />
                    </c:otherwise>
                </c:choose>
            </display:column>
            <display:column titleKey="columnTitle.scale">
                <c:choose>
                    <c:when test="${includeScaleReviewUrl}">
                        <c:url var="scaleUrl" value="/action/Configuration/Scale/Review">
                            <c:param name="ScaleName" value="${partyScaleUse.scale.scaleName}" />
                        </c:url>
                        <a href="${scaleUrl}"><c:out value="${partyScaleUse.scale.description}" /></a>
                    </c:when>
                    <c:otherwise>
                        <c:out value="${partyScaleUse.scale.description}" />
                    </c:otherwise>
                </c:choose>
            </display:column>
            <et:hasSecurityRole securityRoles="PartyScaleUse.Edit:PartyScaleUse.Delete">
                <display:column>
                    <et:hasSecurityRole securityRole="PartyScaleUse.Edit">
                        <c:url var="editUrl" value="/action/${commonUrl}/Edit">
                            <c:param name="PartyName" value="${partyScaleUse.party.partyName}" />
                            <c:param name="ScaleUseTypeName" value="${partyScaleUse.scaleUseType.scaleUseTypeName}" />
                        </c:url>
                        <a href="${editUrl}">Edit</a>
                    </et:hasSecurityRole>
                    <et:hasSecurityRole securityRole="PartyScaleUse.Delete">
                        <c:url var="deleteUrl" value="/action/${commonUrl}/Delete">
                            <c:param name="PartyName" value="${partyScaleUse.party.partyName}" />
                            <c:param name="ScaleUseTypeName" value="${partyScaleUse.scaleUseType.scaleUseTypeName}" />
                        </c:url>
                        <a href="${deleteUrl}">Delete</a>
                    </et:hasSecurityRole>
                </display:column>
            </et:hasSecurityRole>
        </display:table>
    </c:otherwise>
</c:choose>
<br />
