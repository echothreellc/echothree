<%@ include file="taglibs.jsp" %>

<h3>Documents</h3>
<c:url var="addDocumentUrl" value="/action/${commonUrl}/Add/Step1">
    <c:param name="PartyName" value="${party.partyName}" />
</c:url>
<p><a href="${addDocumentUrl}">Add Document.</a></p>
<c:choose>
    <c:when test='${partyDocuments.size == 0}'>
        <br />
    </c:when>
    <c:otherwise>
        <display:table name="partyDocuments.list" id="partyDocument" class="displaytag">
            <display:column titleKey="columnTitle.name">
                <c:url var="partyDocumentReviewUrl" value="/action/${commonUrl}/Review">
                    <c:param name="PartyName" value="${partyDocument.party.partyName}" />
                    <c:param name="DocumentName" value="${partyDocument.document.documentName}" />
                </c:url>
                <a href="${partyDocumentReviewUrl}"><c:out value="${partyDocument.document.documentName}" /></a>
            </display:column>
            <display:column titleKey="columnTitle.description">
                <c:out value="${partyDocument.document.description}" />
            </display:column>
            <display:column titleKey="columnTitle.documentType">
                <c:out value="${partyDocument.document.documentType.description}" />
            </display:column>
            <display:column>
                <c:choose>
                    <c:when test='${partyDocument.document.documentType.mimeTypeUsageType.mimeTypeUsageTypeName == "TEXT"}'>
                        <c:url var="viewUrl" value="/action/${commonUrl}/ClobView">
                            <c:param name="PartyName" value="${partyDocument.party.partyName}" />
                            <c:param name="DocumentName" value="${partyDocument.document.documentName}" />
                        </c:url>
                    </c:when>
                    <c:otherwise>
                        <c:url var="viewUrl" value="/action/${commonUrl}/BlobView">
                            <c:param name="DocumentName" value="${partyDocument.document.documentName}" />
                        </c:url>
                    </c:otherwise>
                </c:choose>
                <a href="${viewUrl}">View</a>
            </display:column>
            <display:column titleKey="columnTitle.default">
                <c:choose>
                    <c:when test="${partyDocument.isDefault}">
                        Default
                    </c:when>
                    <c:otherwise>
                        <c:url var="setDefaultUrl" value="/action/${commonUrl}/SetDefault">
                            <c:param name="PartyName" value="${partyDocument.party.partyName}" />
                            <c:param name="DocumentName" value="${partyDocument.document.documentName}" />
                        </c:url>
                        <a href="${setDefaultUrl}">Set Default</a>
                    </c:otherwise>
                </c:choose>
            </display:column>
            <display:column>
                <c:url var="editUrl" value="/action/${commonUrl}/Edit">
                    <c:param name="PartyName" value="${partyDocument.party.partyName}" />
                    <c:param name="DocumentName" value="${partyDocument.document.documentName}" />
                </c:url>
                <a href="${editUrl}">Edit</a>
                <c:url var="descriptionsUrl" value="/action/${commonUrl}/Description">
                    <c:param name="PartyName" value="${partyDocument.party.partyName}" />
                    <c:param name="DocumentName" value="${partyDocument.document.documentName}" />
                </c:url>
                <a href="${descriptionsUrl}">Descriptions</a>
                <c:url var="deleteUrl" value="/action/${commonUrl}/Delete">
                    <c:param name="PartyName" value="${partyDocument.party.partyName}" />
                    <c:param name="DocumentName" value="${partyDocument.document.documentName}" />
                </c:url>
                <a href="${deleteUrl}">Delete</a>
            </display:column>
            <et:hasSecurityRole securityRole="Event.List">
                <display:column>
                    <c:url var="eventsUrl" value="/action/Core/Event/Main">
                        <c:param name="EntityRef" value="${partyDocument.document.entityInstance.entityRef}" />
                    </c:url>
                    <a href="${eventsUrl}">Events</a>
                </display:column>
            </et:hasSecurityRole>
        </display:table>
    </c:otherwise>
</c:choose>
<br />
