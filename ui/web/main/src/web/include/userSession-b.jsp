<et:checkSecurityRoles securityRoles="PartyPrinterGroupUse.List:PartyScaleUse.List:PartyApplicationEditorUse.List:PartyEntityType.List" />
<c:choose>
    <c:when test="${userSession.party.profile == null}">
        <c:url var="profileUrl" value="/action/Employee/ProfileAdd" />
    </c:when>
    <c:otherwise>
        <c:url var="profileUrl" value="/action/Employee/ProfileEdit" />
    </c:otherwise>
</c:choose>
<header>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top">
        <a class="navbar-brand" href="#"><c:out value="${userSession.partyRelationship.fromParty.partyGroup.name}" /></a>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item dropdown active">
                    <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        <c:out value="${userSession.party.person.firstName} ${userSession.party.person.lastName}" />
                    </a>
                    <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                        <a class="dropdown-item" href="<c:url value="/action/Employee/Availability" />">Availability: <c:out value="${employeeAvailability.workflowStep.description}" /></a></a>
                        <a class="dropdown-item" href="<c:url value="/action/Employee/Password" />">Password</a>
                        <a class="dropdown-item" href="${profileUrl}">Profile</a>
                        <div class="dropdown-divider"></div>
                        <et:hasSecurityRole securityRole="PartyApplicationEditorUse.List">
                            <a class="dropdown-item" href="<c:url value="/action/Employee/EmployeeApplicationEditorUse/Main" />">Editors</a>
                        </et:hasSecurityRole>
                        <et:hasSecurityRole securityRole="PartyPrinterGroupUse.List">
                            <a class="dropdown-item" href="<c:url value="/action/Employee/EmployeePrinterGroupUse/Main" />">Printer Groups</a>
                        </et:hasSecurityRole>
                        <et:hasSecurityRole securityRole="PartyScaleUse.List">
                            <a class="dropdown-item" href="<c:url value="/action/Employee/EmployeeScaleUse/Main" />">Scales</a>
                        </et:hasSecurityRole>
                        <et:hasSecurityRole securityRole="PartyEntityType.List">
                            <a class="dropdown-item" href="<c:url value="/action/Employee/EmployeeEntityType/Main" />">Entity Types</a>
                        </et:hasSecurityRole>
                        <et:hasSecurityRole securityRoles="PartyPrinterGroupUse.List:PartyScaleUse.List:PartyApplicationEditorUse.List:PartyEntityType.List">
                            <div class="dropdown-divider"></div>
                        </et:hasSecurityRole>
                        <a class="dropdown-item" href="<c:url value="/action/Employee/Logout" />">Logout</a>
                        <div class="dropdown-divider"></div>
                        <a class="dropdown-item" href="<c:url value="/action/Portal/About" />">About</a>
                    </div>
                </li>
            </ul>
            <html:form styleClass="form-inline my-2 my-lg-0" action="/Portal/Jump" method="POST">
                <html:text styleClass="form-control mr-sm-2" styleId="target" property="target" size="40" maxlength="80" />
            </html:form>
      </div>
    </nav>
</header>
<script type="text/javascript">
    $(document).keypress(function(e) {
        if(e.charCode === 96) {
            var tagName = document.activeElement.tagName;

            if(tagName !== 'INPUT' && tagName !== 'TEXTAREA') {
                document.forms["Jump"].elements["target"].focus();
                e.preventDefault();
            }
        }
    });
    
    startIdle('<c:url value="/action/Employee/Idle" />');
</script>
