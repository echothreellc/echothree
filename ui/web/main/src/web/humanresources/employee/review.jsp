<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>

<!--                                                                                  -->
<!-- Copyright 2002-2026 Echo Three, LLC                                              -->
<!--                                                                                  -->
<!-- Licensed under the Apache License, Version 2.0 (the "License");                  -->
<!-- you may not use this file except in compliance with the License.                 -->
<!-- You may obtain a copy of the License at                                          -->
<!--                                                                                  -->
<!--     http://www.apache.org/licenses/LICENSE-2.0                                   -->
<!--                                                                                  -->
<!-- Unless required by applicable law or agreed to in writing, software              -->
<!-- distributed under the License is distributed on an "AS IS" BASIS,                -->
<!-- WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.         -->
<!-- See the License for the specific language governing permissions and              -->
<!-- limitations under the License.                                                   -->
<!--                                                                                  -->

<%@ include file="../../include/taglibs.jsp" %>

<html:html xhtml="true">
    <head>
        <title>
            <fmt:message key="pageTitle.employee">
                <fmt:param value="${employee.employeeName}" />
            </fmt:message>
        </title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/HumanResources/Main" />"><fmt:message key="navigation.humanResources" /></a> &gt;&gt;
                <a href="<c:url value="/action/HumanResources/Employee/Main" />"><fmt:message key="navigation.employees" /></a> &gt;&gt;
                <et:countEmployeeResults searchTypeName="HUMAN_RESOURCES" countVar="employeeResultsCount" commandResultVar="countEmployeeResultsCommandResult" logErrors="false" />
                <c:if test="${employeeResultsCount > 0}">
                    <a href="<c:url value="/action/HumanResources/Employee/Result" />"><fmt:message key="navigation.results" /></a> &gt;&gt;
                </c:if>
                Review (<c:out value="${employee.employeeName}" />)
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="EmployeeStatus.Choices:EmployeeAvailability.Choices:TrainingClass.Review:PartyTrainingClassStatus.Choices:PartyTrainingClass.Create:PartyTrainingClass.Edit:PartyTrainingClass.Delete:PartyTrainingClass.Review:Event.List" />
            <et:hasSecurityRole securityRole="PartyTrainingClassStatus.Choices" var="includePartyTrainingClassStatus" />
            <et:hasSecurityRole securityRole="PartyTrainingClass.Review" var="includeReviewUrl" />
            <et:hasSecurityRole securityRole="Employee.Review" var="includeEmployeeReviewUrl" />
            <et:hasSecurityRole securityRole="TrainingClass.Review" var="includeTrainingClassReviewUrl" />
            <c:url var="returnUrl" scope="request" value="Review">
                <c:param name="EmployeeName" value="${employee.employeeName}" />
            </c:url>
            <p><font size="+2"><b><c:out value="${employee.person.personalTitle.description}" /> <c:out value="${employee.person.firstName}" />
            <c:out value="${employee.person.middleName}" /> <c:out value="${employee.person.lastName}" />
            <c:out value="${employee.person.nameSuffix.description}" /></b></font></p>
            <br />
            <fmt:message key="label.employeeName" />: <c:out value="${employee.employeeName}" /><br />
            <fmt:message key="label.employeeType" />:
            <c:url var="employeeTypeUrl" value="/action/HumanResources/EmployeeType/Review">
                <c:param name="EmployeeTypeName" value="${employee.employeeType.employeeTypeName}" />
            </c:url>
            <a href="${employeeTypeUrl}"><c:out value="${employee.employeeType.description}" /></a>
            <c:url var="editUrl" value="/action/HumanResources/Employee/Edit">
                <c:param name="EmployeeName" value="${employee.employeeName}" />
            </c:url>
            <a href="${editUrl}">Edit</a>
            <br />
            <br />
            Status: <c:out value="${employee.employeeStatus.workflowStep.description}" />
            <et:hasSecurityRole securityRole="EmployeeStatus.Choices">
                <c:url var="editUrl" value="/action/HumanResources/Employee/Status">
                    <c:param name="EmployeeName" value="${employee.employeeName}" />
                    <c:param name="ReturnUrl" value="${returnUrl}" />
                </c:url>
                <a href="${editUrl}">Edit</a>
            </et:hasSecurityRole>
            <br />
            Availability: <c:out value="${employee.employeeAvailability.workflowStep.description}" />
            <et:hasSecurityRole securityRole="EmployeeAvailability.Choices">
                <c:url var="editUrl" value="/action/HumanResources/Employee/Availability">
                    <c:param name="EmployeeName" value="${employee.employeeName}" />
                    <c:param name="ReturnUrl" value="${returnUrl}" />
                </c:url>
                <a href="${editUrl}">Edit</a>
            </et:hasSecurityRole>
            <br />
            <br />
            Profile:
            <c:choose>
                <c:when test="${employee.profile == null}">
                    <c:url var="createProfileUrl" value="/action/HumanResources/Employee/EmployeeProfileAdd">
                        <c:param name="EmployeeName" value="${employee.employeeName}" />
                        <c:param name="PartyName" value="${employee.partyName}" />
                    </c:url>
                    <a href="${createProfileUrl}">Create</a>
                </c:when>
                <c:otherwise>
                    <c:url var="reviewProfileUrl" value="/action/HumanResources/Employee/EmployeeProfileReview">
                        <c:param name="EmployeeName" value="${employee.employeeName}" />
                    </c:url>
                    <a href="${reviewProfileUrl}">Review</a>
                    <c:url var="editProfileUrl" value="/action/HumanResources/Employee/EmployeeProfileEdit">
                        <c:param name="EmployeeName" value="${employee.employeeName}" />
                        <c:param name="PartyName" value="${employee.partyName}" />
                    </c:url>
                    <a href="${editProfileUrl}">Edit</a>
                </c:otherwise>
            </c:choose>
            <br />
            <br />
            <br />
            <h2>User Login</h2>
            Username: <c:out value="${employee.userLogin.username}" />
            <c:url var="editUserLoginUrl" value="/action/HumanResources/Employee/EmployeeUserLoginEdit">
                <c:param name="PartyName" value="${employee.partyName}" />
            </c:url>
            <a href="${editUserLoginUrl}">Edit</a><br />
            Last Login Time:
            <c:choose>
                <c:when test='${employee.userLogin.lastLoginTime == null}'>
                    <i><fmt:message key="phrase.notSet" /></i>
                </c:when>
                <c:otherwise>
                    <c:out value="${employee.userLogin.lastLoginTime}" />
                </c:otherwise>
            </c:choose>
            <br />
            Failure Count: <c:out value="${employee.userLogin.failureCount}" /><br />
            First Failure Time:
            <c:choose>
                <c:when test='${employee.userLogin.firstFailureTime == null}'>
                    <i><fmt:message key="phrase.notSet" /></i>
                </c:when>
                <c:otherwise>
                    <c:out value="${employee.userLogin.firstFailureTime}" />
                </c:otherwise>
            </c:choose>
            <br />
            Last Failure Time:
            <c:choose>
                <c:when test='${employee.userLogin.lastFailureTime == null}'>
                    <i><fmt:message key="phrase.notSet" /></i>
                </c:when>
                <c:otherwise>
                    <c:out value="${employee.userLogin.lastFailureTime}" />
                </c:otherwise>
            </c:choose>
            <br />
            Expired Count: <c:out value="${employee.userLogin.expiredCount}" /><br />
            Force Change:
            <c:choose>
                <c:when test="${employee.userLogin.forceChange}">
                    <fmt:message key="phrase.yes" />
                </c:when>
                <c:otherwise>
                    <fmt:message key="phrase.no" />
                </c:otherwise>
            </c:choose>
            <br />
            <c:choose>
                <c:when test="${userSession.party.partyName == employee.partyName}">
                    <c:url var="changePasswordUrl" value="/action/Employee/Password" />
                </c:when>
                <c:otherwise>
                    <c:url var="changePasswordUrl" value="/action/HumanResources/Employee/Password">
                        <c:param name="EmployeeName" value="${employee.employeeName}" />
                        <c:param name="ReturnUrl" value="${returnUrl}" />
                    </c:url>
                </c:otherwise>
            </c:choose>
            <a href="${changePasswordUrl}">Change Password</a><br />
            <c:if test="${employee.userLogin.failureCount > partyType.partyTypeLockoutPolicy.lockoutFailureCount}">
                <c:url var="resetLockoutUrl" value="/action/HumanResources/Employee/ResetLockout">
                    <c:param name="PartyName" value="${employee.partyName}" />
                    <c:param name="ReturnUrl" value="${returnUrl}" />
                </c:url>
                <a href="${resetLockoutUrl}">Reset Lockout</a><br />
            </c:if>
            <br /><br />
            <h2>Responsibilities</h2>
            <c:url var="addUrl" value="/action/HumanResources/PartyResponsibility/Add">
                <c:param name="PartyName" value="${employee.partyName}" />
            </c:url>
            <p><a href="${addUrl}">Add Responsibility.</a></p>
            <c:choose>
                <c:when test="${employee.partyResponsibilities.size == 0}">
                    No responsibilities were found.<br />
                </c:when>
                <c:otherwise>
                    <display:table name="employee.partyResponsibilities.list" id="partyResponsibility" class="displaytag">
                        <display:column titleKey="columnTitle.responsibility">
                            <c:out value="${partyResponsibility.responsibilityType.description}" />
                        </display:column>
                        <display:column>
                            <c:url var="deleteUrl" value="/action/HumanResources/PartyResponsibility/Delete">
                                <c:param name="PartyName" value="${partyResponsibility.party.partyName}" />
                                <c:param name="ResponsibilityTypeName" value="${partyResponsibility.responsibilityType.responsibilityTypeName}" />
                            </c:url>
                            <a href="${deleteUrl}">Delete</a>
                        </display:column>
                    </display:table>
                </c:otherwise>
            </c:choose>
            <br />
            <h2>Skills</h2>
            <c:url var="addUrl" value="/action/HumanResources/PartySkill/Add">
                <c:param name="PartyName" value="${employee.partyName}" />
            </c:url>
            <p><a href="${addUrl}">Add Skill.</a></p>
            <c:choose>
                <c:when test="${employee.partySkills.size == 0}">
                    No skills were found.<br />
                </c:when>
                <c:otherwise>
                    <display:table name="employee.partySkills.list" id="partySkill" class="displaytag">
                        <display:column titleKey="columnTitle.skill">
                            <c:out value="${partySkill.skillType.description}" />
                        </display:column>
                        <display:column>
                            <c:url var="deleteUrl" value="/action/HumanResources/PartySkill/Delete">
                                <c:param name="PartyName" value="${partySkill.party.partyName}" />
                                <c:param name="SkillTypeName" value="${partySkill.skillType.skillTypeName}" />
                            </c:url>
                            <a href="${deleteUrl}">Delete</a>
                        </display:column>
                    </display:table>
                </c:otherwise>
            </c:choose>
            <br />
            <h2>Training Classes</h2>
            <et:hasSecurityRole securityRole="PartyTrainingClass.Create">
                <c:url var="addUrl" value="/action/HumanResources/PartyTrainingClass/Add">
                    <c:param name="PartyName" value="${employee.partyName}" />
                </c:url>
                <p><a href="${addUrl}">Add Training Class.</a></p>
            </et:hasSecurityRole>
            <c:choose>
                <c:when test="${employee.partyTrainingClasses.size == 0}">
                    No training classes were found.<br />
                </c:when>
                <c:otherwise>
                    <display:table name="employee.partyTrainingClasses.list" id="partyTrainingClass" class="displaytag" export="true" sort="list" requestURI="/action/HumanResources/Employee/Review">
                        <display:setProperty name="export.csv.filename" value="PartyTrainingClasses.csv" />
                        <display:setProperty name="export.excel.filename" value="PartyTrainingClasses.xls" />
                        <display:setProperty name="export.pdf.filename" value="PartyTrainingClasses.pdf" />
                        <display:setProperty name="export.rtf.filename" value="PartyTrainingClasses.rtf" />
                        <display:setProperty name="export.xml.filename" value="PartyTrainingClasses.xml" />
                        <display:column titleKey="columnTitle.name" media="html" sortable="true" sortProperty="partyTrainingClassName">
                            <c:choose>
                                <c:when test="${includeReviewUrl}">
                                    <c:url var="reviewUrl" value="/action/HumanResources/PartyTrainingClass/Review">
                                        <c:param name="PartyTrainingClassName" value="${partyTrainingClass.partyTrainingClassName}" />
                                    </c:url>
                                    <a href="${reviewUrl}"><c:out value="${partyTrainingClass.partyTrainingClassName}" /></a>
                                </c:when>
                                <c:otherwise>
                                    <c:out value="${partyTrainingClass.partyTrainingClassName}" />
                                </c:otherwise>
                            </c:choose>
                        </display:column>
                        <display:column property="partyTrainingClassName" titleKey="columnTitle.name" media="csv excel pdf rtf xml" />
                        <display:column titleKey="columnTitle.trainingClass" media="html" sortable="true" sortProperty="trainingClass.description">
                            <c:choose>
                                <c:when test="${includeTrainingClassReviewUrl}">
                                    <c:url var="trainingClassReviewUrl" value="/action/HumanResources/TrainingClass/Review">
                                        <c:param name="TrainingClassName" value="${partyTrainingClass.trainingClass.trainingClassName}" />
                                    </c:url>
                                    <a href="${trainingClassReviewUrl}"><c:out value="${partyTrainingClass.trainingClass.description}" /></a>
                                </c:when>
                                <c:otherwise>
                                    <c:out value="${partyTrainingClass.trainingClass.description}" />
                                </c:otherwise>
                            </c:choose>
                        </display:column>
                        <display:column property="trainingClass.description" titleKey="columnTitle.trainingClass" media="csv excel pdf rtf xml" />
                        <display:column titleKey="columnTitle.completedTime" media="html" sortable="true" sortProperty="unformattedCompletedTime">
                            <c:out value="${partyTrainingClass.completedTime}" />
                        </display:column>
                        <display:column property="completedTime" titleKey="columnTitle.completedTime" media="csv excel pdf rtf xml" />
                        <display:column titleKey="columnTitle.validUntilTime" media="html" sortable="true" sortProperty="unformattedValidUntilTime">
                            <c:out value="${partyTrainingClass.validUntilTime}" />
                        </display:column>
                        <display:column property="validUntilTime" titleKey="columnTitle.validUntilTime" media="csv excel pdf rtf xml" />
                        <display:column titleKey="columnTitle.status" media="html" sortable="true" sortProperty="partyTrainingClassStatus.workflowStep.description">
                            <c:choose>
                                <c:when test="${includePartyTrainingClassStatus}">
                                    <c:url var="statusUrl" value="/action/HumanResources/PartyTrainingClass/Status">
                                        <c:param name="PartyName" value="${partyTrainingClass.party.partyName}" />
                                        <c:param name="PartyTrainingClassName" value="${partyTrainingClass.partyTrainingClassName}" />
                                    </c:url>
                                    <a href="${statusUrl}"><c:out value="${partyTrainingClass.partyTrainingClassStatus.workflowStep.description}" /></a>
                                </c:when>
                                <c:otherwise>
                                    <c:out value="${partyTrainingClass.partyTrainingClassStatus.workflowStep.description}" />
                                </c:otherwise>
                            </c:choose>
                        </display:column>
                        <display:column property="partyTrainingClassStatus.workflowStep.description" titleKey="columnTitle.status" media="csv excel pdf rtf xml" />
                        <et:hasSecurityRole securityRoles="PartyTrainingClass.Edit:TrainingClass.Delete">
                            <display:column media="html">
                                <et:hasSecurityRole securityRole="PartyTrainingClass.Edit">
                                    <c:url var="editUrl" value="/action/HumanResources/PartyTrainingClass/Edit">
                                        <c:param name="PartyName" value="${partyTrainingClass.party.partyName}" />
                                        <c:param name="PartyTrainingClassName" value="${partyTrainingClass.partyTrainingClassName}" />
                                    </c:url>
                                    <a href="${editUrl}">Edit</a>
                                </et:hasSecurityRole>
                                <et:hasSecurityRole securityRole="PartyTrainingClass.Delete">
                                    <c:url var="deleteUrl" value="/action/HumanResources/PartyTrainingClass/Delete">
                                        <c:param name="PartyName" value="${partyTrainingClass.party.partyName}" />
                                        <c:param name="PartyTrainingClassName" value="${partyTrainingClass.partyTrainingClassName}" />
                                    </c:url>
                                    <a href="${deleteUrl}">Delete</a>
                                </et:hasSecurityRole>
                            </display:column>
                        </et:hasSecurityRole>
                        <et:hasSecurityRole securityRole="Event.List">
                            <display:column media="html">
                                <c:url var="eventsUrl" value="/action/Core/Event/Main">
                                    <c:param name="EntityRef" value="${partyTrainingClass.entityInstance.entityRef}" />
                                </c:url>
                                <a href="${eventsUrl}">Events</a>
                            </display:column>
                        </et:hasSecurityRole>
                    </display:table>
                </c:otherwise>
            </c:choose>
            <br />
            <c:set var="commonUrl" scope="request" value="HumanResources/EmployeeContactMechanism" />
            <c:set var="party" scope="request" value="${employee}" />
            <c:set var="partyContactMechanisms" scope="request" value="${employee.partyContactMechanisms}" />
            <jsp:include page="../../include/partyContactMechanisms.jsp" />
            <c:set var="commonUrl" scope="request" value="HumanResources/EmployeeDocument" />
            <c:set var="partyDocuments" scope="request" value="${employee.partyDocuments}" />
            <jsp:include page="../../include/partyDocuments.jsp" />
            <c:set var="commonUrl" scope="request" value="HumanResources/EmployeePrinterGroupUse" />
            <c:set var="partyPrinterGroupUses" scope="request" value="${employee.partyPrinterGroupUses}" />
            <jsp:include page="../../include/partyPrinterGroupUses.jsp" />
            <c:set var="commonUrl" scope="request" value="HumanResources/EmployeeScaleUse" />
            <c:set var="partyScaleUses" scope="request" value="${employee.partyScaleUses}" />
            <jsp:include page="../../include/partyScaleUses.jsp" />
            <c:set var="commonUrl" scope="request" value="HumanResources/Employee" />
            <c:set var="partyContactLists" scope="request" value="${employee.partyContactLists}" />
            <jsp:include page="../../include/partyContactLists.jsp" />

            <c:set var="commonUrl" scope="request" value="HumanResources/EmployeeAlias" />
            <c:set var="partyAliases" scope="request" value="${employee.partyAliases}" />
            <c:set var="securityRoleGroupNamePrefix" scope="request" value="Employee" />
            <jsp:include page="../../include/partyAliases.jsp" />

            <c:set var="tagScopes" scope="request" value="${employee.tagScopes}" />
            <c:set var="entityAttributeGroups" scope="request" value="${employee.entityAttributeGroups}" />
            <c:set var="entityInstance" scope="request" value="${employee.entityInstance}" />
            <c:url var="returnUrl" scope="request" value="/../action/HumanResources/Employee/Review">
                <c:param name="PartyName" value="${employee.partyName}" />
            </c:url>
            <jsp:include page="../../include/tagScopes.jsp" />
            <jsp:include page="../../include/entityAttributeGroups.jsp" />
            <jsp:include page="../../include/entityInstance.jsp" />
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
