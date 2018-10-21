<%@ include file="taglibs.jsp" %>

<h2>Printer Group Uses</h2>
<et:checkSecurityRoles securityRoles="PartyPrinterGroupUse.Create:PartyPrinterGroupUse.Edit:PartyPrinterGroupUse.Delete:PrinterGroupUseType.Review:PrinterGroup.Review" />
<et:hasSecurityRole securityRole="PartyPrinterGroupUse.Create">
    <c:url var="addPrinterGroupUseUrl" value="/action/${commonUrl}/Add">
        <c:param name="PartyName" value="${party.partyName}" />
    </c:url>
    <p><a href="${addPrinterGroupUseUrl}">Add Printer Group Use.</a></p>
</et:hasSecurityRole>
<c:choose>
    <c:when test='${partyPrinterGroupUses.size == 0}'>
        <br />
    </c:when>
    <c:otherwise>
        <et:hasSecurityRole securityRole="PrinterGroupUseType.Review" var="includePrinterGroupUseTypeReviewUrl" />
        <et:hasSecurityRole securityRole="PrinterGroup.Review" var="includePrinterGroupReviewUrl" />
        <display:table name="partyPrinterGroupUses.list" id="partyPrinterGroupUse" class="displaytag">
            <display:column titleKey="columnTitle.printerGroupUseType">
                <c:choose>
                    <c:when test="${includePrinterGroupUseTypeReviewUrl}">
                        <c:url var="printerGroupUseTypeUrl" value="/action/Configuration/PrinterGroupUseType/Review">
                            <c:param name="PrinterGroupUseTypeName" value="${partyPrinterGroupUse.printerGroupUseType.printerGroupUseTypeName}" />
                        </c:url>
                        <a href="${printerGroupUseTypeUrl}"><c:out value="${partyPrinterGroupUse.printerGroupUseType.description}" /></a>
                    </c:when>
                    <c:otherwise>
                        <c:out value="${partyPrinterGroupUse.printerGroupUseType.description}" />
                    </c:otherwise>
                </c:choose>
            </display:column>
            <display:column titleKey="columnTitle.printerGroup">
                <c:choose>
                    <c:when test="${includePrinterGroupReviewUrl}">
                        <c:url var="printerUrl" value="/action/Configuration/PrinterGroup/Review">
                            <c:param name="PrinterGroupName" value="${partyPrinterGroupUse.printerGroup.printerGroupName}" />
                        </c:url>
                        <a href="${printerUrl}"><c:out value="${partyPrinterGroupUse.printerGroup.description}" /></a>
                    </c:when>
                    <c:otherwise>
                        <c:out value="${partyPrinterGroupUse.printerGroup.description}" />
                    </c:otherwise>
                </c:choose>
            </display:column>
            <et:hasSecurityRole securityRoles="PartyPrinterGroupUse.Edit:PartyPrinterGroupUse.Delete">
                <display:column>
                    <et:hasSecurityRole securityRole="PartyPrinterGroupUse.Edit">
                        <c:url var="editUrl" value="/action/${commonUrl}/Edit">
                            <c:param name="PartyName" value="${partyPrinterGroupUse.party.partyName}" />
                            <c:param name="PrinterGroupUseTypeName" value="${partyPrinterGroupUse.printerGroupUseType.printerGroupUseTypeName}" />
                        </c:url>
                        <a href="${editUrl}">Edit</a>
                    </et:hasSecurityRole>
                    <et:hasSecurityRole securityRole="PartyPrinterGroupUse.Delete">
                        <c:url var="deleteUrl" value="/action/${commonUrl}/Delete">
                            <c:param name="PartyName" value="${partyPrinterGroupUse.party.partyName}" />
                            <c:param name="PrinterGroupUseTypeName" value="${partyPrinterGroupUse.printerGroupUseType.printerGroupUseTypeName}" />
                        </c:url>
                        <a href="${deleteUrl}">Delete</a>
                    </et:hasSecurityRole>
                </display:column>
            </et:hasSecurityRole>
        </display:table>
    </c:otherwise>
</c:choose>
<br />
