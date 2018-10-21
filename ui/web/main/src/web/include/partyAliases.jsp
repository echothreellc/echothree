<%@ include file="taglibs.jsp" %>

<et:checkSecurityRoles securityRoles="${securityRoleGroupNamePrefix}Alias.Create:${securityRoleGroupNamePrefix}Alias.List:${securityRoleGroupNamePrefix}Alias.Edit:${securityRoleGroupNamePrefix}Alias.Delete:PartyAliasType.Review" />

<h2>Aliases</h2>
<et:hasSecurityRole securityRole="${securityRoleGroupNamePrefix}Alias.Create">
    <c:url var="addPartyAliasUrl" value="/action/${commonUrl}/Add">
        <c:param name="PartyName" value="${party.partyName}" />
    </c:url>
    <p><a href="${addPartyAliasUrl}">Add Alias.</a></p>
</et:hasSecurityRole>
<et:hasSecurityRole securityRole="${securityRoleGroupNamePrefix}Alias.List">
    <c:choose>
        <c:when test='${partyAliases.size == 0}'>
            <br />
        </c:when>
        <c:otherwise>
            <et:hasSecurityRole securityRole="PartyAliasType.Review" var="includePartyAliasTypeReviewUrl" />
            <display:table name="partyAliases.list" id="partyAlias" class="displaytag">
                <display:column titleKey="columnTitle.description">
                    <c:choose>
                        <c:when test='${includePartyAliasTypeReviewUrl}'>
                            <c:url var="partyAliasTypeReviewUrl" value="/action/Configuration/PartyAliasType/Review">
                                <c:param name="PartyName" value="${partyAlias.party.partyName}" />
                                <c:param name="PartyAliasTypeName" value="${partyAlias.partyAliasType.partyAliasTypeName}" />
                            </c:url>
                            <a href="${partyAliasTypeReviewUrl}"><c:out value="${partyAlias.partyAliasType.description}" /></a>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${partyAlias.partyAliasType.description}" />
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column titleKey="columnTitle.alias">
                    <c:out value="${partyAlias.alias}" />
                </display:column>
                <et:hasSecurityRole securityRoles="${securityRoleGroupNamePrefix}Alias.Edit:${securityRoleGroupNamePrefix}Alias.Delete">
                    <display:column>
                        <et:hasSecurityRole securityRoles="${securityRoleGroupNamePrefix}Alias.Edit">
                            <c:url var="editPartyAliasUrl" value="/action/${commonUrl}/Edit">
                                <c:param name="PartyName" value="${partyAlias.party.partyName}" />
                                <c:param name="PartyAliasTypeName" value="${partyAlias.partyAliasType.partyAliasTypeName}" />
                            </c:url>
                            <a href="${editPartyAliasUrl}">Edit</a>
                        </et:hasSecurityRole>
                        <et:hasSecurityRole securityRoles="${securityRoleGroupNamePrefix}Alias.Delete">
                            <c:url var="deletePartyAliasUrl" value="/action/${commonUrl}/Delete">
                                <c:param name="PartyName" value="${partyAlias.party.partyName}" />
                                <c:param name="PartyAliasTypeName" value="${partyAlias.partyAliasType.partyAliasTypeName}" />
                            </c:url>
                            <a href="${deletePartyAliasUrl}">Delete</a>
                        </et:hasSecurityRole>
                    </display:column>
                </et:hasSecurityRole>
            </display:table>
        </c:otherwise>
    </c:choose>
</et:hasSecurityRole>
<br />
