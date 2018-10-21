<%@ include file="taglibs.jsp" %>

<c:if test="${partySetupComplete == null}">
    <et:checkSecurityRoles securityRoles="Carrier.Review:Company.Review:Customer.Review:Department.Review:Division.Review:Employee.Review:Warehouse.Review:Vendor.Review" />
    <c:set var="partySetupComplete" value="true"/>
</c:if>

<c:set var="partyUrl" value="unset"/>
<c:choose>
    <c:when test="${party.partyType.partyTypeName == 'CARRIER'}">
        <et:hasSecurityRole securityRole="Carrier.Review">
            <c:url var="partyUrl" value="/action/Shipping/Carrier/Review">
                <c:param name="PartyName" value="${party.partyName}" />
            </c:url>
        </et:hasSecurityRole>
    </c:when>
    <c:when test="${party.partyType.partyTypeName == 'COMPANY'}">
        <et:hasSecurityRole securityRole="Carrier.Review">
            <c:url var="partyUrl" value="/action/Accounting/Company/Review">
                <c:param name="PartyName" value="${party.partyName}" />
            </c:url>
        </et:hasSecurityRole>
    </c:when>
    <c:when test="${party.partyType.partyTypeName == 'CUSTOMER'}">
        <et:hasSecurityRole securityRole="Customer.Review">
            <c:url var="partyUrl" value="/action/Customer/Customer/Review">
                <c:param name="PartyName" value="${party.partyName}" />
            </c:url>
        </et:hasSecurityRole>
    </c:when>
    <c:when test="${party.partyType.partyTypeName == 'DEPARTMENT'}">
        <et:hasSecurityRole securityRole="Department.Review">
            <c:url var="partyUrl" value="/action/Accounting/Department/Review">
                <c:param name="PartyName" value="${party.partyName}" />
            </c:url>
        </et:hasSecurityRole>
    </c:when>
    <c:when test="${party.partyType.partyTypeName == 'DIVISION'}">
        <et:hasSecurityRole securityRole="Division.Review">
            <c:url var="partyUrl" value="/action/Accounting/Division/Review">
                <c:param name="PartyName" value="${party.partyName}" />
            </c:url>
        </et:hasSecurityRole>
    </c:when>
    <c:when test="${party.partyType.partyTypeName == 'EMPLOYEE'}">
        <et:hasSecurityRole securityRole="Employee.Review">
            <c:url var="partyUrl" value="/action/HumanResources/Employee/Review">
                <c:param name="PartyName" value="${party.partyName}" />
            </c:url>
        </et:hasSecurityRole>
    </c:when>
    <c:when test="${party.partyType.partyTypeName == 'WAREHOUSE'}">
        <et:hasSecurityRole securityRole="Warehouse.Review">
            <c:url var="partyUrl" value="/action/Warehouse/Warehouse/Review">
                <c:param name="PartyName" value="${party.partyName}" />
            </c:url>
        </et:hasSecurityRole>
    </c:when>
    <c:when test="${party.partyType.partyTypeName == 'VENDOR'}">
        <et:hasSecurityRole securityRole="Vendor.Review">
            <c:url var="partyUrl" value="/action/Vendor/Vendor/Review">
                <c:param name="PartyName" value="${party.partyName}" />
            </c:url>
        </et:hasSecurityRole>
    </c:when>
</c:choose>

<c:choose>
    <c:when test="${partyUrl != 'unset'}">
        <c:choose>
            <c:when test="${party.description == null}">
                <a href="${partyUrl}"><c:out value="${party.partyName}" /></a>
            </c:when>
            <c:otherwise>
                <a href="${partyUrl}"><c:out value="${party.description}" /></a>
            </c:otherwise>
        </c:choose>
    </c:when>
    <c:otherwise>
        <c:choose>
            <c:when test="${party.description == null}">
                <c:out value="${party.partyName}" />
            </c:when>
            <c:otherwise>
                <c:out value="${party.description}" />
            </c:otherwise>
        </c:choose>
    </c:otherwise>
</c:choose>
