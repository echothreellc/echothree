<%@ include file="taglibs.jsp" %>

<h3>Payment Methods</h3>
<c:url var="addUrl" value="/action/${commonUrl}/Add/Step1">
    <c:param name="PartyName" value="${party.partyName}" />
</c:url>
<p><a href="${addUrl}">Add Payment Method.</a></p>
<c:choose>
    <c:when test='${party.partyPaymentMethods.size == 0}'>
        <br />
    </c:when>
    <c:otherwise>
        <display:table name="party.partyPaymentMethods.list" id="partyPaymentMethod" class="displaytag">
            <display:column titleKey="columnTitle.name">
                <c:url var="reviewUrl" value="/action/${commonUrl}/Review">
                    <c:param name="PartyName" value="${party.partyName}" />
                    <c:param name="PartyPaymentMethodName" value="${partyPaymentMethod.partyPaymentMethodName}" />
                </c:url>
                <a href="${reviewUrl}"><c:out value="${partyPaymentMethod.partyPaymentMethodName}" /></a>
            </display:column>
            <display:column titleKey="columnTitle.description">
                <c:out value="${partyPaymentMethod.description}" />
            </display:column>
            <display:column titleKey="columnTitle.paymentMethod">
                <c:out value="${partyPaymentMethod.paymentMethod.description}" />
            </display:column>
            <display:column titleKey="columnTitle.deleteWhenUnused">
                <c:choose>
                    <c:when test="${partyPaymentMethod.deleteWhenUnused}">
                        Yes
                    </c:when>
                    <c:otherwise>
                        No
                    </c:otherwise>
                </c:choose>
            </display:column>
            <display:column property="sortOrder" titleKey="columnTitle.sortOrder" />
            <display:column titleKey="columnTitle.default">
                <c:choose>
                    <c:when test="${partyPaymentMethod.isDefault}">
                        Default
                    </c:when>
                    <c:otherwise>
                        <c:url var="setDefaultUrl" value="/action/${commonUrl}/SetDefault">
                            <c:param name="PartyName" value="${party.partyName}" />
                            <c:param name="PartyPaymentMethodName" value="${partyPaymentMethod.partyPaymentMethodName}" />
                        </c:url>
                        <a href="${setDefaultUrl}">Set Default</a>
                    </c:otherwise>
                </c:choose>
            </display:column>
            <display:column>
                <c:url var="editUrl" value="/action/${commonUrl}/Edit">
                    <c:param name="PartyName" value="${party.partyName}" />
                    <c:param name="PartyPaymentMethodName" value="${partyPaymentMethod.partyPaymentMethodName}" />
                </c:url>
                <a href="${editUrl}">Edit</a>
                <c:url var="deleteUrl" value="/action/${commonUrl}/Delete">
                    <c:param name="PartyName" value="${party.partyName}" />
                    <c:param name="PartyPaymentMethodName" value="${partyPaymentMethod.partyPaymentMethodName}" />
                </c:url>
                <a href="${deleteUrl}">Delete</a>
            </display:column>
            <et:hasSecurityRole securityRole="Event.List">
                <display:column>
                    <c:url var="eventsUrl" value="/action/Core/Event/Main">
                        <c:param name="EntityRef" value="${partyPaymentMethod.entityInstance.entityRef}" />
                    </c:url>
                    <a href="${eventsUrl}">Events</a>
                </display:column>
            </et:hasSecurityRole>
        </display:table>
    </c:otherwise>
</c:choose>
<br />
