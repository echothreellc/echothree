<%@ include file="taglibs.jsp" %>

<c:choose>
    <c:when test='${partyContactMechanism.contactMechanism.contactMechanismType.contactMechanismTypeName == "EMAIL_ADDRESS"}'>
        E-mail Address: <a href="mailto:<c:out value="${partyContactMechanism.contactMechanism.contactEmailAddress.emailAddress}" />"><c:out value="${partyContactMechanism.contactMechanism.contactEmailAddress.emailAddress}" /></a><br />
    </c:when>
    <c:when test='${partyContactMechanism.contactMechanism.contactMechanismType.contactMechanismTypeName == "POSTAL_ADDRESS"}'>
        <et:contactPostalAddress contactPostalAddress="${partyContactMechanism.contactMechanism.contactPostalAddress}" var="contactPostalAddressLines" />
        <c:forEach items="${contactPostalAddressLines.list}" var="contactPostalAddressLine">
            <c:out value="${contactPostalAddressLine}" /><br />
        </c:forEach>
        Commercial:
        <c:choose>
            <c:when test="${partyContactMechanism.contactMechanism.contactPostalAddress.isCommercial}">
                <fmt:message key="phrase.yes" />
            </c:when>
            <c:otherwise>
                <fmt:message key="phrase.no" />
            </c:otherwise>
        </c:choose>
        <br />
    </c:when>
    <c:when test='${partyContactMechanism.contactMechanism.contactMechanismType.contactMechanismTypeName == "TELECOM_ADDRESS"}'>
        Country: <c:out value="${partyContactMechanism.contactMechanism.contactTelephone.countryGeoCode.description}" /><br />
        <c:if test='${partyContactMechanism.contactMechanism.contactTelephone.areaCode != null}'>
            Area Code: <c:out value="${partyContactMechanism.contactMechanism.contactTelephone.areaCode}" /><br />
        </c:if>
        Number: <c:out value="${partyContactMechanism.contactMechanism.contactTelephone.telephoneNumber}" /><br />
        <c:if test='${partyContactMechanism.contactMechanism.contactTelephone.telephoneExtension != null}'>
            Extension <c:out value="${partyContactMechanism.contactMechanism.contactTelephone.telephoneExtension}" /><br />
        </c:if>
    </c:when>
    <c:when test='${partyContactMechanism.contactMechanism.contactMechanismType.contactMechanismTypeName == "WEB_ADDRESS"}'>
        Web Address: <a target="_blank" href="${partyContactMechanism.contactMechanism.contactWebAddress.url}"><c:out value="${partyContactMechanism.contactMechanism.contactWebAddress.url}" /></a><br />
    </c:when>
    <c:when test='${partyContactMechanism.contactMechanism.contactMechanismType.contactMechanismTypeName == "INET_4"}'>
        IPv4 Address: <c:out value="${partyContactMechanism.contactMechanism.contactInet4Address.inet4Address}" /><br />
    </c:when>
</c:choose>
<br />Used for:
<c:choose>
    <c:when test='${partyContactMechanism.partyContactMechanismPurposes.size != 0}'>
        <c:forEach items="${partyContactMechanism.partyContactMechanismPurposes.list}" var="partyContactMechanismPurpose">
            <c:out value="${partyContactMechanismPurpose.contactMechanismPurpose.description}" />
            <c:choose>
                <c:when test='${partyContactMechanismPurpose.isDefault}'>
                    Default
                </c:when>
                <c:otherwise>
                    <c:url var="setDefaultUrl" value="/action/${commonUrl}/PartyContactMechanismPurposeSetDefault">
                        <c:param name="PartyName" value="${partyContactMechanism.party.partyName}" />
                        <c:param name="ContactMechanismName" value="${partyContactMechanism.contactMechanism.contactMechanismName}" />
                        <c:param name="ContactMechanismPurposeName" value="${partyContactMechanismPurpose.contactMechanismPurpose.contactMechanismPurposeName}" />
                    </c:url>
                    <a href="${setDefaultUrl}">Set Default</a>
                </c:otherwise>
            </c:choose>
            <c:url var="deleteUrl" value="/action/${commonUrl}/PartyContactMechanismPurposeDelete">
                <c:param name="PartyName" value="${partyContactMechanism.party.partyName}" />
                <c:param name="ContactMechanismName" value="${partyContactMechanism.contactMechanism.contactMechanismName}" />
                <c:param name="ContactMechanismPurposeName" value="${partyContactMechanismPurpose.contactMechanismPurpose.contactMechanismPurposeName}" />
            </c:url>
            <a href="${deleteUrl}">Delete</a>,
        </c:forEach>
    </c:when>
    <c:otherwise>
        Nothing.
    </c:otherwise>
</c:choose>
<c:url var="addUrl" value="/action/${commonUrl}/PartyContactMechanismPurposeAdd">
    <c:param name="PartyName" value="${partyContactMechanism.party.partyName}" />
    <c:param name="ContactMechanismName" value="${partyContactMechanism.contactMechanism.contactMechanismName}" />
</c:url>
<a href="${addUrl}">Add</a>
<c:if test='${partyContactMechanism.contactMechanism.contactMechanismType.contactMechanismTypeName == "POSTAL_ADDRESS" || partyContactMechanism.partyContactMechanismRelationships.size != 0}'>
    <br />Related to:
    <c:choose>
        <c:when test='${partyContactMechanism.partyContactMechanismRelationships.size != 0}'>
            <c:forEach items="${partyContactMechanism.partyContactMechanismRelationships.list}" var="partyContactMechanismRelationship">
                <c:choose>
                    <c:when test='${partyContactMechanismRelationship.toPartyContactMechanism.description == null}'>
                        <c:out value="${partyContactMechanismRelationship.toPartyContactMechanism.contactMechanism.contactMechanismName}" />
                    </c:when>
                    <c:otherwise>
                        <c:out value="${partyContactMechanismRelationship.toPartyContactMechanism.description}" />
                    </c:otherwise>
                </c:choose>
                <c:url var="deleteUrl" value="/action/${commonUrl}/PartyContactMechanismRelationshipDelete">
                    <c:param name="PartyName" value="${partyContactMechanism.party.partyName}" />
                    <c:param name="FromContactMechanismName" value="${partyContactMechanismRelationship.fromPartyContactMechanism.contactMechanism.contactMechanismName}" />
                    <c:param name="ToContactMechanismName" value="${partyContactMechanismRelationship.toPartyContactMechanism.contactMechanism.contactMechanismName}" />
                </c:url>
                <a href="${deleteUrl}">Delete</a>,
            </c:forEach>
        </c:when>
        <c:otherwise>
            Nothing.
        </c:otherwise>
    </c:choose>
    <c:url var="addUrl" value="/action/${commonUrl}/PartyContactMechanismRelationshipAdd">
        <c:param name="PartyName" value="${partyContactMechanism.party.partyName}" />
        <c:param name="FromContactMechanismName" value="${partyContactMechanism.contactMechanism.contactMechanismName}" />
    </c:url>
    <a href="${addUrl}">Add</a>
</c:if>
<br />
