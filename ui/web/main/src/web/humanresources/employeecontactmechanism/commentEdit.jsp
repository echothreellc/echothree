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
        <title>Employee Contact Mechanism (<c:out value="${contactMechanism.contactMechanismName}" />)</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
        <%@ include file="commentTinyMce.jsp" %>
    </head>
    <body onLoad="pageLoaded()">
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
                <c:url var="employeeContactMechanismsUrl" value="/action/HumanResources/EmployeeContactMechanism/Main">
                    <c:param name="EmployeeName" value="${employee.employeeName}" />
                </c:url>
                <a href="${employeeContactMechanismsUrl}">Contact Mechanisms</a> &gt;&gt;
                <c:url var="reviewUrl" value="/action/HumanResources/EmployeeContactMechanism/Review">
                    <c:param name="PartyName" value="${employee.partyName}" />
                    <c:param name="ContactMechanismName" value="${contactMechanism.contactMechanismName}" />
                </c:url>
                <a href="${reviewUrl}">Employee Contact Mechanism (<c:out value="${contactMechanism.contactMechanismName}" />)</a> &gt;&gt;
                Edit Comment
            </h2>
        </div>
        <div id="Content">
            <c:choose>
                <c:when test="${commandResult.executionResult.hasLockErrors}">
                    <et:executionErrors id="errorMessage">
                        <p class="executionErrors"><c:out value="${errorMessage}" /></p><br />
                    </et:executionErrors>
                </c:when>
                <c:otherwise>
                    <et:executionErrors id="errorMessage">
                        <p class="executionErrors"><c:out value="${errorMessage}" /></p><br />
                    </et:executionErrors>
                    <html:form action="/HumanResources/EmployeeContactMechanism/CommentEdit" method="POST" focus="description">
                        <table>
                            <tr>
                                <td align=right><fmt:message key="label.language" />:</td>
                                <td>
                                    <html:select property="languageChoice">
                                        <html:optionsCollection property="languageChoices" />
                                    </html:select> (*)
                                    <et:validationErrors id="errorMessage" property="LanguageIsoName">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td align=right><fmt:message key="label.description" />:</td>
                                <td>
                                    <html:text property="description" size="60" maxlength="132" />
                                    <et:validationErrors id="errorMessage" property="Description">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td align=right><fmt:message key="label.mimeType" />:</td>
                                <td>
                                    <html:select onchange="mimeTypeChoiceChange()" property="mimeTypeChoice" styleId="mimeTypeChoices">
                                        <html:optionsCollection property="mimeTypeChoices" />
                                    </html:select> (*)
                                    <et:validationErrors id="errorMessage" property="MimeTypeName">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td align=right><fmt:message key="label.clobComment" />:</td>
                                <td>
                                    <html:textarea property="clobComment" cols="${employeeContactMechanismCommentEditorUse.preferredWidth}" rows="${employeeContactMechanismCommentEditorUse.preferredHeight}" styleId="clobCommentTA" /> (*)
                                    <et:validationErrors id="errorMessage" property="Clob">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <html:hidden property="partyName" />
                                    <html:hidden property="contactMechanismName" />
                                    <html:hidden property="commentName" />
                                </td>
                                <td> <html:submit onclick="onSubmitDisable(this);" />&nbsp;<html:cancel onclick="onSubmitDisable(this);" />&nbsp;<html:reset /><html:hidden property="submitButton" /></td>
                            </tr>
                        </table>
                    </html:form>
                </c:otherwise>
            </c:choose>
        </div>
        <jsp:include page="../../include/entityLock.jsp" />
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
