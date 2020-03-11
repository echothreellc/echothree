<%@ include file="taglibs.jsp" %>

<h3>Contact Lists</h3>
<c:url var="addUrl" value="/action/${commonUrl}/Add">
    <c:param name="PartyName" value="${party.partyName}" />
</c:url>
<p><a href="${addUrl}">Add Contact List.</a></p>
<c:choose>
    <c:when test='${partyContactLists.size == 0}'>
        <br />
    </c:when>
    <c:otherwise>
        <display:table name="partyContactLists.list" id="partyContactList" class="displaytag">
            <display:column>
                <c:url var="reviewUrl" value="/action/${commonUrl}/Review">
                    <c:param name="PartyName" value="${partyContactList.party.partyName}" />
                    <c:param name="ContactListName" value="${partyContactList.contactList.contactListName}" />
                </c:url>
                <a href="${reviewUrl}">Review</a>
            </display:column>
            <display:column titleKey="columnTitle.contactListGroup">
                <c:url var="reviewUrl" value="/action/ContactList/ContactListGroup/Review">
                    <c:param name="ContactListGroupName" value="${partyContactList.contactList.contactListGroup.contactListGroupName}" />
                </c:url>
                <a href="${reviewUrl}"><c:out value="${partyContactList.contactList.contactListGroup.description}" /></a>
            </display:column>
            <display:column titleKey="columnTitle.contactList">
                <c:url var="reviewUrl" value="/action/ContactList/ContactList/Review">
                    <c:param name="ContactListName" value="${partyContactList.contactList.contactListName}" />
                </c:url>
                <a href="${reviewUrl}"><c:out value="${partyContactList.contactList.description}" /></a>
            </display:column>
            <display:column titleKey="columnTitle.contactListFrequency">
                <c:if test='${partyContactList.contactList.contactListFrequency != null}'>
                    <c:url var="reviewUrl" value="/action/ContactList/ContactListFrequency/Review">
                        <c:param name="ContactListFrequencyName" value="${partyContactList.contactList.contactListFrequency.contactListFrequencyName}" />
                    </c:url>
                    <a href="${reviewUrl}"><c:out value="${partyContactList.contactList.contactListFrequency.description}" /></a>
                </c:if>
            </display:column>
            <display:column titleKey="columnTitle.preferredContactListContactMechanismPurpose">
                <c:if test='${partyContactList.preferredContactListContactMechanismPurpose != null}'>
                    <c:out value="${partyContactList.preferredContactListContactMechanismPurpose.contactMechanismPurpose.description}" />
                </c:if>
            </display:column>
            <display:column titleKey="columnTitle.partyContactListStatus">
                <c:url var="statusUrl" value="/action/${commonUrl}/PartyContactListStatus">
                    <c:param name="PartyName" value="${partyContactList.party.partyName}" />
                    <c:param name="ContactListName" value="${partyContactList.contactList.contactListName}" />
                </c:url>
                <a href="${statusUrl}"><c:out value="${partyContactList.partyContactListStatus.workflowStep.description}" /></a>
            </display:column>
            <display:column>
                <c:url var="editUrl" value="/action/${commonUrl}/Edit">
                    <c:param name="PartyName" value="${partyContactList.party.partyName}" />
                    <c:param name="ContactListName" value="${partyContactList.contactList.contactListName}" />
                </c:url>
                <a href="${editUrl}">Edit</a>
                <c:url var="deleteUrl" value="/action/${commonUrl}/Delete">
                    <c:param name="PartyName" value="${partyContactList.party.partyName}" />
                    <c:param name="ContactListName" value="${partyContactList.contactList.contactListName}" />
                </c:url>
                <a href="${deleteUrl}">Delete</a>
            </display:column>
        </display:table>
    </c:otherwise>
</c:choose>
<br />
