<%@ include file="taglibs.jsp" %>

<et:country options="CountryIncludeAliases" var="defaultCountry" />

<h3>Contact Mechanisms</h3>
<c:url var="addContactMechanismUrl" value="/action/${commonUrl}/ContactMechanismAdd/Step1">
    <c:param name="PartyName" value="${party.partyName}" />
</c:url>
<c:url var="addContactWebAddressUrl" value="/action/${commonUrl}/ContactMechanismAdd/ContactWebAddressAdd">
    <c:param name="PartyName" value="${party.partyName}" />
</c:url>
<c:url var="addContactEmailAddressUrl" value="/action/${commonUrl}/ContactMechanismAdd/ContactEmailAddressAdd">
    <c:param name="PartyName" value="${party.partyName}" />
</c:url>
<c:url var="addContactPostalAddressUrl" value="/action/${commonUrl}/ContactMechanismAdd/ContactPostalAddressAdd">
    <c:param name="PartyName" value="${party.partyName}" />
    <c:param name="CountryName" value="${defaultCountry.geoCodeAliases.map['COUNTRY_NAME'].alias}" />
</c:url>
<c:url var="addContactTelephoneUrl" value="/action/${commonUrl}/ContactMechanismAdd/ContactTelephoneAdd">
    <c:param name="PartyName" value="${party.partyName}" />
    <c:param name="CountryName" value="${defaultCountry.geoCodeAliases.map['COUNTRY_NAME'].alias}" />
</c:url>
<p><a href="${addContactMechanismUrl}">Add Contact Mechanism.</a>
Add <a href="${addContactWebAddressUrl}">Web</a>,
<a href="${addContactEmailAddressUrl}">Email</a>,
<a href="${addContactPostalAddressUrl}"><c:out value="${defaultCountry.geoCodeAliases.map['ISO_2_LETTER'].alias}" /> Postal</a>,
<a href="${addContactTelephoneUrl}"><c:out value="${defaultCountry.geoCodeAliases.map['ISO_2_LETTER'].alias}" /> Telephone</a>.
</p>
<c:choose>
    <c:when test='${partyContactMechanisms.size == 0}'>
        <br />
    </c:when>
    <c:otherwise>
        <display:table name="partyContactMechanisms.list" id="partyContactMechanism" class="displaytag">
            <display:column titleKey="columnTitle.name">
                <c:url var="partyContactMechanismReviewUrl" value="/action/${commonUrl}/Review">
                    <c:param name="PartyName" value="${partyContactMechanism.party.partyName}" />
                    <c:param name="ContactMechanismName" value="${partyContactMechanism.contactMechanism.contactMechanismName}" />
                </c:url>
                <a href="${partyContactMechanismReviewUrl}"><c:out value="${partyContactMechanism.contactMechanism.contactMechanismName}" /></a>
            </display:column>
            <display:column titleKey="columnTitle.allowSolicitation">
                <c:if test='${partyContactMechanism.contactMechanism.contactMechanismType.contactMechanismTypeName != "WEB_ADDRESS" && partyContactMechanism.contactMechanism.contactMechanismType.contactMechanismTypeName != "INET_4"}'>
                    <c:choose>
                        <c:when test="${partyContactMechanism.contactMechanism.allowSolicitation}">
                            Yes
                        </c:when>
                        <c:otherwise>
                            No
                        </c:otherwise>
                    </c:choose>
                </c:if>
            </display:column>
            <display:column titleKey="columnTitle.valid">
                <c:choose>
                    <c:when test='${partyContactMechanism.contactMechanism.contactMechanismType.contactMechanismTypeName == "EMAIL_ADDRESS"}'>
                        <c:url var="emailAddressStatusUrl" value="/action/${commonUrl}/ContactEmailAddressStatus">
                            <c:param name="PartyName" value="${partyContactMechanism.party.partyName}" />
                            <c:param name="ContactMechanismName" value="${partyContactMechanism.contactMechanism.contactMechanismName}" />
                        </c:url>
                        <c:url var="emailAddressVerificationUrl" value="/action/${commonUrl}/ContactEmailAddressVerification">
                            <c:param name="PartyName" value="${partyContactMechanism.party.partyName}" />
                            <c:param name="ContactMechanismName" value="${partyContactMechanism.contactMechanism.contactMechanismName}" />
                        </c:url>
                        <a href="${emailAddressStatusUrl}"><c:out value="${partyContactMechanism.contactMechanism.contactEmailAddress.emailAddressStatus.workflowStep.description}" /></a><br/>
                        <a href="${emailAddressVerificationUrl}"><c:out value="${partyContactMechanism.contactMechanism.contactEmailAddress.emailAddressVerification.workflowStep.description}" /></a>
                    </c:when>
                    <c:when test='${partyContactMechanism.contactMechanism.contactMechanismType.contactMechanismTypeName == "POSTAL_ADDRESS"}'>
                        <c:url var="postalAddressStatusUrl" value="/action/${commonUrl}/ContactPostalAddressStatus">
                            <c:param name="PartyName" value="${partyContactMechanism.party.partyName}" />
                            <c:param name="ContactMechanismName" value="${partyContactMechanism.contactMechanism.contactMechanismName}" />
                        </c:url>
                        <a href="${postalAddressStatusUrl}"><c:out value="${partyContactMechanism.contactMechanism.contactPostalAddress.postalAddressStatus.workflowStep.description}" /></a>
                    </c:when>
                    <c:when test='${partyContactMechanism.contactMechanism.contactMechanismType.contactMechanismTypeName == "TELECOM_ADDRESS"}'>
                        <c:url var="telephoneStatusUrl" value="/action/${commonUrl}/ContactTelephoneStatus">
                            <c:param name="PartyName" value="${partyContactMechanism.party.partyName}" />
                            <c:param name="ContactMechanismName" value="${partyContactMechanism.contactMechanism.contactMechanismName}" />
                        </c:url>
                        <a href="${telephoneStatusUrl}"><c:out value="${partyContactMechanism.contactMechanism.contactTelephone.telephoneStatus.workflowStep.description}" /></a><br/>
                    </c:when>
                    <c:when test='${partyContactMechanism.contactMechanism.contactMechanismType.contactMechanismTypeName == "WEB_ADDRESS"}'>
                        <c:url var="webAddressStatusUrl" value="/action/${commonUrl}/ContactWebAddressStatus">
                            <c:param name="PartyName" value="${partyContactMechanism.party.partyName}" />
                            <c:param name="ContactMechanismName" value="${partyContactMechanism.contactMechanism.contactMechanismName}" />
                        </c:url>
                        <a href="${webAddressStatusUrl}"><c:out value="${partyContactMechanism.contactMechanism.contactWebAddress.webAddressStatus.workflowStep.description}" /></a><br/>
                    </c:when>
                </c:choose>
            </display:column>
            <display:column titleKey="columnTitle.type">
                <c:out value="${partyContactMechanism.contactMechanism.contactMechanismType.description}" />
            </display:column>
            <display:column>
                <c:if test="${partyContactMechanism.description != null}">
                    Description: <c:out value="${partyContactMechanism.description}" /><br />
                </c:if>
                <c:set var="partyContactMechanism" scope="request" value="${partyContactMechanism}" />
                <jsp:include page="partyContactMechanism.jsp" />
            </display:column>
            <display:column>
                <c:if test='${partyContactMechanism.contactMechanism.contactMechanismType.contactMechanismTypeName != "INET_4"}'>
                    <c:choose>
                        <c:when test='${partyContactMechanism.contactMechanism.contactMechanismType.contactMechanismTypeName == "EMAIL_ADDRESS"}'>
                            <c:url var="editUrl" value="/action/${commonUrl}/ContactEmailAddressEdit">
                                <c:param name="PartyName" value="${partyContactMechanism.party.partyName}" />
                                <c:param name="ContactMechanismName" value="${partyContactMechanism.contactMechanism.contactMechanismName}" />
                            </c:url>
                        </c:when>
                        <c:when test='${partyContactMechanism.contactMechanism.contactMechanismType.contactMechanismTypeName == "POSTAL_ADDRESS"}'>
                            <c:url var="editUrl" value="/action/${commonUrl}/ContactPostalAddressEdit">
                                <c:param name="PartyName" value="${partyContactMechanism.party.partyName}" />
                                <c:param name="ContactMechanismName" value="${partyContactMechanism.contactMechanism.contactMechanismName}" />
                            </c:url>
                        </c:when>
                        <c:when test='${partyContactMechanism.contactMechanism.contactMechanismType.contactMechanismTypeName == "TELECOM_ADDRESS"}'>
                            <c:url var="editUrl" value="/action/${commonUrl}/ContactTelephoneEdit">
                                <c:param name="PartyName" value="${partyContactMechanism.party.partyName}" />
                                <c:param name="ContactMechanismName" value="${partyContactMechanism.contactMechanism.contactMechanismName}" />
                            </c:url>
                        </c:when>
                        <c:when test='${partyContactMechanism.contactMechanism.contactMechanismType.contactMechanismTypeName == "WEB_ADDRESS"}'>
                            <c:url var="editUrl" value="/action/${commonUrl}/ContactWebAddressEdit">
                                <c:param name="PartyName" value="${partyContactMechanism.party.partyName}" />
                                <c:param name="ContactMechanismName" value="${partyContactMechanism.contactMechanism.contactMechanismName}" />
                            </c:url>
                        </c:when>
                    </c:choose>
                    <a href="${editUrl}">Edit</a>
                </c:if>
                <c:url var="deleteUrl" value="/action/${commonUrl}/Delete">
                    <c:param name="PartyName" value="${partyContactMechanism.party.partyName}" />
                    <c:param name="ContactMechanismName" value="${partyContactMechanism.contactMechanism.contactMechanismName}" />
                </c:url>
                <a href="${deleteUrl}">Delete</a>
            </display:column>
            <et:hasSecurityRole securityRole="Event.List">
                <display:column>
                    <c:url var="eventsUrl" value="/action/Core/Event/Main">
                        <c:param name="EntityRef" value="${partyContactMechanism.contactMechanism.entityInstance.entityRef}" />
                    </c:url>
                    <a href="${eventsUrl}">Events</a>
                </display:column>
            </et:hasSecurityRole>
        </display:table>
    </c:otherwise>
</c:choose>
<br />
