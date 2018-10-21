<%@ include file="taglibs.jsp" %>

<h2>Carriers</h2>
<c:url var="addCarrierUrl" value="/action/${commonUrl}/Add">
    <c:param name="PartyName" value="${party.partyName}" />
</c:url>
<p><a href="${addCarrierUrl}">Add Carrier.</a></p>
<c:choose>
    <c:when test='${partyCarriers.size == 0}'>
        <br />
    </c:when>
    <c:otherwise>
        <display:table name="partyCarriers.list" id="partyCarrier" class="displaytag">
            <display:column titleKey="columnTitle.carrier">
                <c:url var="carrierUrl" value="/action/Shipping/Carrier/Review">
                    <c:param name="CarrierName" value="${partyCarrier.carrier.carrierName}" />
                </c:url>
                <a href="${carrierUrl}"><c:out value="${partyCarrier.carrier.partyGroup.name}" /></a>
            </display:column>
            <display:column>
                <c:url var="deleteUrl" value="/action/${commonUrl}/Delete">
                    <c:param name="PartyName" value="${partyCarrier.party.partyName}" />
                    <c:param name="CarrierName" value="${partyCarrier.carrier.carrierName}" />
                </c:url>
                <a href="${deleteUrl}">Delete</a>
            </display:column>
        </display:table>
    </c:otherwise>
</c:choose>
<br />
