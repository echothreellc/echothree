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
        <title><fmt:message key="pageTitle.divisions" /></title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Accounting/Main" />"><fmt:message key="navigation.accounting" /></a> &gt;&gt;
                <a href="<c:url value="/action/Accounting/Company/Main" />"><fmt:message key="navigation.companies" /></a> &gt;&gt;
                <fmt:message key="navigation.divisions" />
            </h2>
        </div>
        <div id="Content">
            <c:url var="addUrl" value="/action/Accounting/Division/Add">
                <c:param name="CompanyName" value="${company.companyName}" />
            </c:url>
            <p><a href="${addUrl}">Add Division.</a></p>
            <et:checkSecurityRoles securityRoles="Event.List" />
            <display:table name="divisions" id="division" class="displaytag">
                <display:column titleKey="columnTitle.name">
                    <c:url var="reviewUrl" value="/action/Accounting/Division/Review">
                        <c:param name="CompanyName" value="${division.company.companyName}" />
                        <c:param name="DivisionName" value="${division.divisionName}" />
                    </c:url>
                    <a href="${reviewUrl}"><c:out value="${division.divisionName}" /></a>
                </display:column>
                <display:column titleKey="columnTitle.description">
                    <c:out value="${division.partyGroup.name}" />
                </display:column>
                <display:column property="sortOrder" titleKey="columnTitle.sortOrder" />
                <display:column titleKey="columnTitle.default">
                    <c:choose>
                        <c:when test="${division.isDefault}">
                            Default
                        </c:when>
                        <c:otherwise>
                            <c:url var="setDefaultUrl" value="/action/Accounting/Division/SetDefault">
                                <c:param name="CompanyName" value="${division.company.companyName}" />
                                <c:param name="DivisionName" value="${division.divisionName}" />
                            </c:url>
                            <a href="${setDefaultUrl}">Set Default</a>
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column>
                    <c:url var="divisionContactMechanismsUrl" value="/action/Accounting/DivisionContactMechanism/Main">
                        <c:param name="CompanyName" value="${division.company.companyName}" />
                        <c:param name="DivisionName" value="${division.divisionName}" />
                    </c:url>
                    <a href="${divisionContactMechanismsUrl}">Contact Mechanisms</a>
                    <c:url var="departmentsUrl" value="/action/Accounting/Department/Main">
                        <c:param name="CompanyName" value="${division.company.companyName}" />
                        <c:param name="DivisionName" value="${division.divisionName}" />
                    </c:url>
                    <a href="${departmentsUrl}"><fmt:message key="navigation.departments" /></a><br />
                    <c:url var="editUrl" value="/action/Accounting/Division/Edit">
                        <c:param name="CompanyName" value="${division.company.companyName}" />
                        <c:param name="OriginalDivisionName" value="${division.divisionName}" />
                    </c:url>
                    <a href="${editUrl}">Edit</a>
                    <c:url var="deleteUrl" value="/action/Accounting/Division/Delete">
                        <c:param name="CompanyName" value="${division.company.companyName}" />
                        <c:param name="DivisionName" value="${division.divisionName}" />
                    </c:url>
                    <a href="${deleteUrl}">Delete</a>
                </display:column>
                <et:hasSecurityRole securityRole="Event.List">
                    <display:column>
                        <c:url var="eventsUrl" value="/action/Core/Event/Main">
                            <c:param name="EntityRef" value="${division.entityInstance.entityRef}" />
                        </c:url>
                        <a href="${eventsUrl}">Events</a>
                    </display:column>
                </et:hasSecurityRole>
            </display:table>
        </div>
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
