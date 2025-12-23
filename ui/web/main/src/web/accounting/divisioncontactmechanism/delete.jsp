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
        <title>Division Contact Mechanism (<c:out value="${partyContactMechanism.contactMechanism.contactMechanismName}" />)</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Accounting/Main" />"><fmt:message key="navigation.accounting" /></a> &gt;&gt;
                <a href="<c:url value="/action/Accounting/Company/Main" />"><fmt:message key="navigation.companies" /></a> &gt;&gt;
                <c:url var="divisionsUrl" value="/action/Accounting/Division/Main">
                    <c:param name="CompanyName" value="${division.company.companyName}" />
                </c:url>
                <a href="${divisionsUrl}"><fmt:message key="navigation.divisions" /></a> &gt;&gt;
                <c:url var="reviewUrl" value="/action/Accounting/Division/Review">
                    <c:param name="CompanyName" value="${division.company.companyName}" />
                    <c:param name="DivisionName" value="${division.divisionName}" />
                </c:url>
                <a href="${reviewUrl}">Review (<c:out value="${division.divisionName}" />)</a> &gt;&gt;
                <c:url var="divisionContactMechanismsUrl" value="/action/Accounting/DivisionContactMechanism/Main">
                    <c:param name="CompanyName" value="${division.company.companyName}" />
                    <c:param name="DivisionName" value="${division.divisionName}" />
                </c:url>
                <a href="${divisionContactMechanismsUrl}">Contact Mechanisms</a> &gt;&gt;
                Delete <c:out value="${partyContactMechanism.contactMechanism.contactMechanismType.description}" />
            </h2>
        </div>
        <div id="Content">
            <c:choose>
                <c:when test="${commandResult.hasErrors}">
                    <et:executionErrors id="errorMessage">
                        <p class="executionErrors"><c:out value="${errorMessage}" /></p><br />
                    </et:executionErrors>
                </c:when>
                <c:otherwise>
                    <p>You are about to delete the <c:out value="${fn:toLowerCase(partyEntityType.entityType.description)}" />
                        <c:choose>
                            <c:when test="${partyContactMechanism.description == null}">
                                &quot;<c:out value="${partyContactMechanism.contactMechanism.contactMechanismName}" />.&quot;
                            </c:when>
                            <c:otherwise>
                                &quot;<c:out value="${partyContactMechanism.description}" />.&quot;
                            </c:otherwise>
                        </c:choose>
                    </p>
                    <html:form action="/Accounting/DivisionContactMechanism/Delete" method="POST">
                        <table>
                            <tr>
                                <td align=right><fmt:message key="label.confirmDelete" />:</td>
                                <td>
                                    <html:checkbox property="confirmDelete" /> (*)
                                    <et:validationErrors id="errorMessage" property="ConfirmDelete">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                        </table>
                        <html:submit value="Delete" onclick="onSubmitDisable(this);" />&nbsp;<html:cancel onclick="onSubmitDisable(this);" /><html:hidden property="submitButton" />
                        <html:hidden property="partyName" />
                        <html:hidden property="contactMechanismName" />
                    </html:form>
                </c:otherwise>
            </c:choose>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
