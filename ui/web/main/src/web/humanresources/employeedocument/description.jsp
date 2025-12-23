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
        <title><fmt:message key="pageTitle.employeeDocumentDescriptions" /></title>
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
                <c:url var="reviewUrl" value="/action/HumanResources/Employee/Review">
                    <c:param name="EmployeeName" value="${employee.employeeName}" />
                </c:url>
                <a href="${reviewUrl}">Review (<c:out value="${employee.employeeName}" />)</a> &gt;&gt;
                <c:url var="employeeDocumentsUrl" value="/action/HumanResources/EmployeeDocument/Main">
                    <c:param name="EmployeeName" value="${employee.employeeName}" />
                </c:url>
                <a href="${employeeDocumentsUrl}"><fmt:message key="navigation.employeeDocuments" /></a> &gt;&gt;
                <fmt:message key="navigation.employeeDocumentDescriptions" />
            </h2>
        </div>
        <div id="Content">
            <c:url var="addUrl" value="/action/HumanResources/EmployeeDocument/DescriptionAdd">
                <c:param name="PartyName" value="${employee.partyName}" />
                <c:param name="DocumentName" value="${document.documentName}" />
            </c:url>
            <p><a href="${addUrl}">Add Description.</a></p>
            <display:table name="documentDescriptions" id="documentDescription" class="displaytag">
                <display:column titleKey="columnTitle.language">
                    <c:out value="${documentDescription.language.description}" />
                </display:column>
                <display:column titleKey="columnTitle.description">
                    <c:out value="${documentDescription.description}" />
                </display:column>
                <display:column>
                    <c:url var="editUrl" value="/action/HumanResources/EmployeeDocument/DescriptionEdit">
                        <c:param name="PartyName" value="${employee.partyName}" />
                        <c:param name="DocumentName" value="${documentDescription.document.documentName}" />
                        <c:param name="LanguageIsoName" value="${documentDescription.language.languageIsoName}" />
                    </c:url>
                    <a href="${editUrl}">Edit</a>
                    <c:url var="deleteUrl" value="/action/HumanResources/EmployeeDocument/DescriptionDelete">
                        <c:param name="PartyName" value="${employee.partyName}" />
                        <c:param name="DocumentName" value="${documentDescription.document.documentName}" />
                        <c:param name="LanguageIsoName" value="${documentDescription.language.languageIsoName}" />
                    </c:url>
                    <a href="${deleteUrl}">Delete</a>
                </display:column>
           </display:table>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
