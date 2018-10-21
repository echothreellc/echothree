<%@ include file="taglibs.jsp" %>

<div id="UserSession">
    <p>
        <c:choose>
            <c:when test="${userSession == null}">
                <c:url var="loginUrl" value="/action/Employee/Login" />
                <a href="${loginUrl}">Login</a>
            </c:when>
            <c:otherwise>
                <c:out value="User: ${userSession.party.person.firstName} ${userSession.party.person.lastName}, Company: ${userSession.partyRelationship.fromParty.partyGroup.name}," />

                <c:url var="availabilityUrl" value="/action/Employee/Availability" />
                Availability: <a href="${availabilityUrl}"><c:out value="${employeeAvailability.workflowStep.description}" /></a>,

                <c:choose>
                    <c:when test="${userSession.party.profile == null}">
                        <c:url var="profileUrl" value="/action/Employee/ProfileAdd" />
                    </c:when>
                    <c:otherwise>
                        <c:url var="profileUrl" value="/action/Employee/ProfileEdit" />
                    </c:otherwise>
                </c:choose>
                <a href="${profileUrl}">Profile</a>,

                <c:url var="printerGroupsUrl" value="/action/Employee/EmployeePrinterGroupUse/Main" />
                <a href="${printerGroupsUrl}">Printer Groups</a>,

                <c:url var="scalesUrl" value="/action/Employee/EmployeeScaleUse/Main" />
                <a href="${scalesUrl}">Scales</a>,

                <c:url var="passwordUrl" value="/action/Employee/Password" />
                <a href="${passwordUrl}">Password</a>,

                <c:url var="logoutUrl" value="/action/Employee/Logout" />
                <a href="${logoutUrl}">Logout</a>
            </c:otherwise>
        </c:choose>
    </p>
</div>
<script type="text/javascript">
    startIdle('<c:url value="/action/Employee/Idle" />');
</script>
