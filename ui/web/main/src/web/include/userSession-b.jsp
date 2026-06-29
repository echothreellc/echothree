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
    <nav class="navbar navbar-expand-lg bg-dark border-bottom border-body" data-bs-theme="dark">
        <div class="container-fluid">
            <a class="navbar-brand" href="<c:url value="/action/Portal" />"><c:out value="${userSession.partyRelationship.fromParty.partyGroup.name}" /></a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                            <c:out value="${userSession.party.person.firstName} ${userSession.party.person.lastName}" />
                        </a>
                        <div class="dropdown-menu" aria-labelledby="navbarDropdown" data-bs-theme="light">
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
                <html:form styleClass="d-flex flex-grow-1 justify-content-end my-2 my-lg-0" action="/Portal/Jump" method="POST">
                    <html:text styleClass="form-control w-auto bg-light text-dark border-secondary" styleId="target" property="target" size="40" maxlength="80" />
                </html:form>
            </div>
        </div>
    </nav>
</header>
<script type="text/javascript">
    $(document).keypress(function(e) {
        var originalEvent = e.originalEvent || e;
        var isAltGraph = originalEvent.getModifierState && originalEvent.getModifierState("AltGraph");
        var hasShortcutModifier = e.metaKey || (!isAltGraph && (e.altKey || e.ctrlKey));

        if(e.charCode === 96 && !hasShortcutModifier) {
            var tagName = document.activeElement.tagName;

            if(tagName !== 'INPUT' && tagName !== 'TEXTAREA') {
                document.forms["Jump"].elements["target"].focus();
                e.preventDefault();
            }
        }
    });
    
    startIdle('<c:url value="/action/Employee/Idle" />');
</script>
