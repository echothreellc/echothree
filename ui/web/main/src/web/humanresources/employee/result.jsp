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
        <title><fmt:message key="pageTitle.employeeResults" /></title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/HumanResources/Main" />"><fmt:message key="navigation.humanResources" /></a> &gt;&gt;
                <a href="<c:url value="/action/HumanResources/Employee/Main" />"><fmt:message key="navigation.employees" /></a> &gt;&gt;
                <fmt:message key="navigation.results" />
            </h2>
        </div>
        <div id="Content">
            <c:url var="addUrl" value="/action/HumanResources/Employee/Add">
                <c:if test='${param.FirstName != null}'>
                    <c:param name="FirstName" value="${param.FirstName}" />
                </c:if>
                <c:if test='${param.MiddleName != null}'>
                    <c:param name="MiddleName" value="${param.MiddleName}" />
                </c:if>
                <c:if test='${param.LastName != null}'>
                    <c:param name="LastName" value="${param.LastName}" />
                </c:if>
            </c:url>
            <p><a href="${addUrl}">Add Employee.</a></p>
            <et:checkSecurityRoles securityRoles="EmployeeStatus.Choices:EmployeeAvailability.Choices:Event.List" />
            <et:hasSecurityRole securityRole="EmployeeStatus.Choices" var="includeEditableEmployeeStatus" />
            <et:hasSecurityRole securityRole="EmployeeAvailability.Choices" var="includeEditableEmployeeAvailability" />
            <et:containsExecutionError key="UnknownUserVisitSearch">
                <c:set var="unknownUserVisitSearch" value="true" />
            </et:containsExecutionError>
            <c:choose>
                <c:when test="${unknownUserVisitSearch}">
                    <p>Your search results are no longer available, please perform your search again.</p>
                </c:when>
                <c:otherwise>
                    <et:hasSecurityRole securityRole="CustomerStatus.Choices" var="includeEditableCustomerStatus">
                        <c:url var="returnUrl" scope="request" value="Result" />
                    </et:hasSecurityRole>
                    <c:choose>
                        <c:when test="${employeeResultCount == null || employeeResultCount < 21}">
                            <display:table name="employeeResults.list" id="employeeResult" class="displaytag" export="true" sort="list" requestURI="/action/HumanResources/Employee/Result">
                                <display:setProperty name="export.csv.filename" value="Employees.csv" />
                                <display:setProperty name="export.excel.filename" value="Employees.xls" />
                                <display:setProperty name="export.pdf.filename" value="Employees.pdf" />
                                <display:setProperty name="export.rtf.filename" value="Employees.rtf" />
                                <display:setProperty name="export.xml.filename" value="Employees.xml" />
                                <display:column titleKey="columnTitle.name" media="html" sortable="true" sortProperty="employee.employeeName">
                                    <c:url var="reviewUrl" value="/action/HumanResources/Employee/Review">
                                        <c:param name="EmployeeName" value="${employeeResult.employee.employeeName}" />
                                    </c:url>
                                    <a href="${reviewUrl}">${employeeResult.employee.employeeName}</a>
                                </display:column>
                                <display:column property="employee.employeeName" titleKey="columnTitle.name" media="csv excel pdf rtf xml" />
                                <display:column titleKey="columnTitle.firstName" media="html" sortable="true" sortProperty="employee.person.firstName">
                                    <c:out value="${employeeResult.employee.person.firstName}" />
                                </display:column>
                                <display:column property="employee.person.firstName" titleKey="columnTitle.firstName" media="csv excel pdf rtf xml" />
                                <display:column titleKey="columnTitle.lastName" media="html" sortable="true" sortProperty="employee.person.lastName">
                                    <c:out value="${employeeResult.employee.person.lastName}" />
                                </display:column>
                                <display:column property="employee.person.lastName" titleKey="columnTitle.lastName" media="csv excel pdf rtf xml" />
                                <display:column titleKey="columnTitle.username" media="html" sortable="true" sortProperty="employee.userLogin.username">
                                    <c:out value="${employeeResult.employee.userLogin.username}" />
                                </display:column>
                                <display:column property="employee.userLogin.username" titleKey="columnTitle.username" media="csv excel pdf rtf xml" />
                                <display:column titleKey="columnTitle.status" media="html" sortable="true" sortProperty="employee.employeeStatus.workflowStep.description">
                                    <c:choose>
                                        <c:when test="${includeEditableEmployeeStatus}">
                                            <c:url var="statusUrl" value="/action/HumanResources/Employee/Status">
                                                <c:param name="EmployeeName" value="${employeeResult.employee.employeeName}" />
                                                <c:param name="ReturnUrl" value="Result" />
                                            </c:url>
                                            <a href="${statusUrl}"><c:out value="${employeeResult.employee.employeeStatus.workflowStep.description}" /></a>
                                        </c:when>
                                        <c:otherwise>
                                            <c:out value="${employeeResult.employee.employeeStatus.workflowStep.description}" />
                                        </c:otherwise>
                                    </c:choose>
                                </display:column>
                                <display:column property="employee.employeeStatus.workflowStep.description" titleKey="columnTitle.status" media="csv excel pdf rtf xml" />
                                <display:column titleKey="columnTitle.availability" media="html" sortable="true" sortProperty="employee.employeeAvailability.workflowStep.description">
                                    <c:choose>
                                        <c:when test="${includeEditableEmployeeAvailability}">
                                            <c:url var="availabilityUrl" value="/action/HumanResources/Employee/Availability">
                                                <c:param name="EmployeeName" value="${employeeResult.employee.employeeName}" />
                                                <c:param name="ReturnUrl" value="Result" />
                                            </c:url>
                                            <a href="${availabilityUrl}"><c:out value="${employeeResult.employee.employeeAvailability.workflowStep.description}" /></a>
                                        </c:when>
                                        <c:otherwise>
                                            <c:out value="${employeeResult.employee.employeeAvailability.workflowStep.description}" />
                                        </c:otherwise>
                                    </c:choose>
                                </display:column>
                                <display:column property="employee.employeeAvailability.workflowStep.description" titleKey="columnTitle.availability" media="csv excel pdf rtf xml" />
                                <display:column titleKey="columnTitle.login" media="html">
                                    <c:choose>
                                        <c:when test="${userSession.party.partyName == employee.partyName}">
                                            <c:url var="changePasswordUrl" value="/action/Employee/Password" />
                                        </c:when>
                                        <c:otherwise>
                                            <c:url var="changePasswordUrl" value="/action/HumanResources/Employee/Password">
                                                <c:param name="EmployeeName" value="${employeeResult.employee.employeeName}" />
                                                <c:param name="ReturnUrl" value="Result" />
                                            </c:url>
                                        </c:otherwise>
                                    </c:choose>
                                    <a href="${changePasswordUrl}">Change Password</a><br />
                                    <c:if test="${employeeResult.employee.userLogin.failureCount > partyType.partyTypeLockoutPolicy.lockoutFailureCount}">
                                        <c:url var="resetLockoutUrl" value="/action/HumanResources/Employee/ResetLockout">
                                            <c:param name="PartyName" value="${employeeResult.employee.partyName}" />
                                            <c:param name="ReturnUrl" value="Result" />
                                        </c:url>
                                        <a href="${resetLockoutUrl}">Reset Lockout</a>
                                    </c:if>
                                </display:column>
                                <display:column media="html">
                                    <c:url var="employeeContactMechanismsUrl" value="/action/HumanResources/EmployeeContactMechanism/Main">
                                        <c:param name="EmployeeName" value="${employeeResult.employee.employeeName}" />
                                    </c:url>
                                    <a href="${employeeContactMechanismsUrl}">Contact Mechanisms</a>
                                    <c:url var="employeeDocumentsUrl" value="/action/HumanResources/EmployeeDocument/Main">
                                        <c:param name="EmployeeName" value="${employeeResult.employee.employeeName}" />
                                    </c:url>
                                    <a href="${employeeDocumentsUrl}">Documents</a>
                                    <c:url var="employeePrinterGroupUsesUrl" value="/action/HumanResources/EmployeePrinterGroupUse/Main">
                                        <c:param name="EmployeeName" value="${employeeResult.employee.employeeName}" />
                                    </c:url>
                                    <a href="${employeePrinterGroupUsesUrl}">Printer Group Uses</a>
                                    <c:url var="employeeScaleUsesUrl" value="/action/HumanResources/EmployeeScaleUse/Main">
                                        <c:param name="EmployeeName" value="${employeeResult.employee.employeeName}" />
                                    </c:url>
                                    <a href="${employeeScaleUsesUrl}">Scale Uses</a><br />
                                    <c:url var="companiesUrl" value="/action/HumanResources/Company/Main">
                                        <c:param name="PartyName" value="${employeeResult.employee.partyName}" />
                                    </c:url>
                                    <a href="${companiesUrl}">Companies</a>
                                    <c:url var="employmentsUrl" value="/action/HumanResources/Employment/Main">
                                        <c:param name="PartyName" value="${employeeResult.employee.partyName}" />
                                    </c:url>
                                    <a href="${employmentsUrl}">Employment</a>
                                    <c:url var="leavesUrl" value="/action/HumanResources/Leave/Main">
                                        <c:param name="PartyName" value="${employeeResult.employee.partyName}" />
                                    </c:url>
                                    <a href="${leavesUrl}">Leave</a><br />
                                    <c:url var="partyResponsibilitiesUrl" value="/action/HumanResources/PartyResponsibility/Main">
                                        <c:param name="PartyName" value="${employeeResult.employee.partyName}" />
                                    </c:url>
                                    <a href="${partyResponsibilitiesUrl}">Responsibilities</a>
                                    <c:url var="partySkillsUrl" value="/action/HumanResources/PartySkill/Main">
                                        <c:param name="PartyName" value="${employeeResult.employee.partyName}" />
                                    </c:url>
                                    <a href="${partySkillsUrl}">Skills</a>
                                    <c:url var="partyTrainingClassesUrl" value="/action/HumanResources/PartyTrainingClass/Main">
                                        <c:param name="PartyName" value="${employeeResult.employee.partyName}" />
                                    </c:url>
                                    <a href="${partyTrainingClassesUrl}">Training Classes</a><br />
                                    <c:url var="editUrl" value="/action/HumanResources/Employee/Edit">
                                        <c:param name="EmployeeName" value="${employeeResult.employee.employeeName}" />
                                    </c:url>
                                    <a href="${editUrl}">Edit</a>
                                </display:column>
                                <et:hasSecurityRole securityRole="Event.List">
                                    <display:column media="html">
                                        <c:url var="actionsUrl" value="/action/Core/Event/Main">
                                            <c:param name="CreatedByEntityRef" value="${employeeResult.employee.entityInstance.entityRef}" />
                                        </c:url>
                                        <a href="${actionsUrl}">Actions</a>
                                    </display:column>
                                    <display:column>
                                        <c:url var="eventsUrl" value="/action/Core/Event/Main">
                                            <c:param name="EntityRef" value="${employeeResult.employee.entityInstance.entityRef}" />
                                        </c:url>
                                        <a href="${eventsUrl}">Events</a>
                                    </display:column>
                                </et:hasSecurityRole>
                            </display:table>
                            <c:if test="${employeeResults.size > 20}">
                                <c:url var="resultsUrl" value="/action/HumanResources/Employee/Result">
                                    <c:if test='${param.FirstName != null}'>
                                        <c:param name="FirstName" value="${param.FirstName}" />
                                    </c:if>
                                    <c:if test='${param.MiddleName != null}'>
                                        <c:param name="MiddleName" value="${param.MiddleName}" />
                                    </c:if>
                                    <c:if test='${param.LastName != null}'>
                                        <c:param name="LastName" value="${param.LastName}" />
                                    </c:if>
                                </c:url>
                                <a href="${resultsUrl}">Paged Results</a>
                            </c:if>
                        </c:when>
                        <c:otherwise>
                            <display:table name="employeeResults.list" id="employeeResult" class="displaytag" partialList="true" pagesize="20" size="employeeResultCount" requestURI="/action/HumanResources/Employee/Result">
                                <display:column titleKey="columnTitle.name">
                                    <c:url var="reviewUrl" value="/action/HumanResources/Employee/Review">
                                        <c:param name="EmployeeName" value="${employeeResult.employee.employeeName}" />
                                    </c:url>
                                    <a href="${reviewUrl}">${employeeResult.employee.employeeName}</a>
                                </display:column>
                                <display:column titleKey="columnTitle.firstName">
                                    <c:out value="${employeeResult.employee.person.firstName}" />
                                </display:column>
                                <display:column titleKey="columnTitle.lastName">
                                    <c:out value="${employeeResult.employee.person.lastName}" />
                                </display:column>
                                <display:column titleKey="columnTitle.username">
                                    <c:out value="${employeeResult.employee.userLogin.username}" />
                                </display:column>
                                <display:column titleKey="columnTitle.status">
                                    <c:choose>
                                        <c:when test="${includeEditableEmployeeStatus}">
                                            <c:url var="statusUrl" value="/action/HumanResources/Employee/Status">
                                                <c:param name="EmployeeName" value="${employeeResult.employee.employeeName}" />
                                                <c:param name="ReturnUrl" value="Result" />
                                            </c:url>
                                            <a href="${statusUrl}"><c:out value="${employeeResult.employee.employeeStatus.workflowStep.description}" /></a>
                                        </c:when>
                                        <c:otherwise>
                                            <c:out value="${employeeResult.employee.employeeStatus.workflowStep.description}" />
                                        </c:otherwise>
                                    </c:choose>
                                </display:column>
                                <display:column titleKey="columnTitle.availability">
                                    <c:choose>
                                        <c:when test="${includeEditableEmployeeAvailability}">
                                            <c:url var="availabilityUrl" value="/action/HumanResources/Employee/Availability">
                                                <c:param name="EmployeeName" value="${employeeResult.employee.employeeName}" />
                                                <c:param name="ReturnUrl" value="Result" />
                                            </c:url>
                                            <a href="${availabilityUrl}"><c:out value="${employeeResult.employee.employeeAvailability.workflowStep.description}" /></a>
                                        </c:when>
                                        <c:otherwise>
                                            <c:out value="${employeeResult.employee.employeeAvailability.workflowStep.description}" />
                                        </c:otherwise>
                                    </c:choose>
                                </display:column>
                                <display:column titleKey="columnTitle.login">
                                    <c:choose>
                                        <c:when test="${userSession.party.partyName == employee.partyName}">
                                            <c:url var="changePasswordUrl" value="/action/Employee/Password" />
                                        </c:when>
                                        <c:otherwise>
                                            <c:url var="changePasswordUrl" value="/action/HumanResources/Employee/Password">
                                                <c:param name="EmployeeName" value="${employeeResult.employee.employeeName}" />
                                                <c:param name="ReturnUrl" value="Result" />
                                            </c:url>
                                        </c:otherwise>
                                    </c:choose>
                                    <a href="${changePasswordUrl}">Change Password</a><br />
                                    <c:if test="${employeeResult.employee.userLogin.failureCount > partyType.partyTypeLockoutPolicy.lockoutFailureCount}">
                                        <c:url var="resetLockoutUrl" value="/action/HumanResources/Employee/ResetLockout">
                                            <c:param name="PartyName" value="${employeeResult.employee.partyName}" />
                                            <c:param name="ReturnUrl" value="Result" />
                                        </c:url>
                                        <a href="${resetLockoutUrl}">Reset Lockout</a>
                                    </c:if>
                                </display:column>
                                <display:column>
                                    <c:url var="employeeContactMechanismsUrl" value="/action/HumanResources/EmployeeContactMechanism/Main">
                                        <c:param name="EmployeeName" value="${employeeResult.employee.employeeName}" />
                                    </c:url>
                                    <a href="${employeeContactMechanismsUrl}">Contact Mechanisms</a>
                                    <c:url var="employeeDocumentsUrl" value="/action/HumanResources/EmployeeDocument/Main">
                                        <c:param name="EmployeeName" value="${employeeResult.employee.employeeName}" />
                                    </c:url>
                                    <a href="${employeeDocumentsUrl}">Documents</a>
                                    <c:url var="employeePrinterGroupUsesUrl" value="/action/HumanResources/EmployeePrinterGroupUse/Main">
                                        <c:param name="EmployeeName" value="${employeeResult.employee.employeeName}" />
                                    </c:url>
                                    <a href="${employeePrinterGroupUsesUrl}">Printer Group Uses</a>
                                    <c:url var="employeeScaleUsesUrl" value="/action/HumanResources/EmployeeScaleUse/Main">
                                        <c:param name="EmployeeName" value="${employeeResult.employee.employeeName}" />
                                    </c:url>
                                    <a href="${employeeScaleUsesUrl}">Scale Uses</a><br />
                                    <c:url var="companiesUrl" value="/action/HumanResources/Company/Main">
                                        <c:param name="PartyName" value="${employeeResult.employee.partyName}" />
                                    </c:url>
                                    <a href="${companiesUrl}">Companies</a>
                                    <c:url var="employmentsUrl" value="/action/HumanResources/Employment/Main">
                                        <c:param name="PartyName" value="${employeeResult.employee.partyName}" />
                                    </c:url>
                                    <a href="${employmentsUrl}">Employment</a>
                                    <c:url var="leavesUrl" value="/action/HumanResources/Leave/Main">
                                        <c:param name="PartyName" value="${employeeResult.employee.partyName}" />
                                    </c:url>
                                    <a href="${leavesUrl}">Leave</a><br />
                                    <c:url var="partyResponsibilitiesUrl" value="/action/HumanResources/PartyResponsibility/Main">
                                        <c:param name="PartyName" value="${employeeResult.employee.partyName}" />
                                    </c:url>
                                    <a href="${partyResponsibilitiesUrl}">Responsibilities</a>
                                    <c:url var="partySkillsUrl" value="/action/HumanResources/PartySkill/Main">
                                        <c:param name="PartyName" value="${employeeResult.employee.partyName}" />
                                    </c:url>
                                    <a href="${partySkillsUrl}">Skills</a>
                                    <c:url var="partyTrainingClassesUrl" value="/action/HumanResources/PartyTrainingClass/Main">
                                        <c:param name="PartyName" value="${employeeResult.employee.partyName}" />
                                    </c:url>
                                    <a href="${partyTrainingClassesUrl}">Training Classes</a><br />
                                    <c:url var="editUrl" value="/action/HumanResources/Employee/Edit">
                                        <c:param name="EmployeeName" value="${employeeResult.employee.employeeName}" />
                                    </c:url>
                                    <a href="${editUrl}">Edit</a>
                                </display:column>
                                <et:hasSecurityRole securityRole="Event.List">
                                    <display:column media="html">
                                        <c:url var="actionsUrl" value="/action/Core/Event/Main">
                                            <c:param name="CreatedByEntityRef" value="${employeeResult.employee.entityInstance.entityRef}" />
                                        </c:url>
                                        <a href="${actionsUrl}">Actions</a>
                                    </display:column>
                                    <display:column>
                                        <c:url var="eventsUrl" value="/action/Core/Event/Main">
                                            <c:param name="EntityRef" value="${employeeResult.employee.entityInstance.entityRef}" />
                                        </c:url>
                                        <a href="${eventsUrl}">Events</a>
                                    </display:column>
                                </et:hasSecurityRole>
                            </display:table>
                            <c:url var="resultsUrl" value="/action/HumanResources/Employee/Result">
                                <c:if test='${param.FirstName != null}'>
                                    <c:param name="FirstName" value="${param.FirstName}" />
                                </c:if>
                                <c:if test='${param.MiddleName != null}'>
                                    <c:param name="MiddleName" value="${param.MiddleName}" />
                                </c:if>
                                <c:if test='${param.LastName != null}'>
                                    <c:param name="LastName" value="${param.LastName}" />
                                </c:if>
                                <c:param name="Results" value="Complete" />
                            </c:url>
                            <a href="${resultsUrl}">All Results</a>
                        </c:otherwise>
                    </c:choose>
                </c:otherwise>
            </c:choose>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
