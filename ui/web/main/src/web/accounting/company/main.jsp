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
        <title><fmt:message key="pageTitle.companies" /></title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Accounting/Main" />"><fmt:message key="navigation.accounting" /></a> &gt;&gt;
                <fmt:message key="navigation.companies" />
            </h2>
        </div>
        <div id="Content">
            <c:url var="addUrl" value="/action/Accounting/Company/Add" />
            <p><a href="${addUrl}">Add Company.</a></p>
            <et:checkSecurityRoles securityRoles="Event.List" />
            <display:table name="companies" id="company" class="displaytag">
                <display:column titleKey="columnTitle.name">
                    <c:url var="reviewUrl" value="/action/Accounting/Company/Review">
                        <c:param name="CompanyName" value="${company.companyName}" />
                    </c:url>
                    <a href="${reviewUrl}"><c:out value="${company.companyName}" /></a>
                </display:column>
                <display:column titleKey="columnTitle.description">
                    <c:out value="${company.partyGroup.name}" />
                </display:column>
                <display:column property="sortOrder" titleKey="columnTitle.sortOrder" />
                <display:column titleKey="columnTitle.default">
                    <c:choose>
                        <c:when test="${company.isDefault}">
                            Default
                        </c:when>
                        <c:otherwise>
                            <c:url var="setDefaultUrl" value="/action/Accounting/Company/SetDefault">
                                <c:param name="CompanyName" value="${company.companyName}" />
                            </c:url>
                            <a href="${setDefaultUrl}">Set Default</a>
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column>
                    <c:url var="companyDocumentsUrl" value="/action/Accounting/CompanyDocument/Main">
                        <c:param name="CompanyName" value="${company.companyName}" />
                    </c:url>
                    <a href="${companyDocumentsUrl}">Documents</a>
                    <c:url var="companyPrinterGroupUsesUrl" value="/action/Accounting/CompanyPrinterGroupUse/Main">
                        <c:param name="CompanyName" value="${company.companyName}" />
                    </c:url>
                    <a href="${companyPrinterGroupUsesUrl}">Printer Group Uses</a>
                    <c:url var="companyContactMechanismsUrl" value="/action/Accounting/CompanyContactMechanism/Main">
                        <c:param name="CompanyName" value="${company.companyName}" />
                    </c:url>
                    <a href="${companyContactMechanismsUrl}">Contact Mechanisms</a>
                    <c:url var="divisionsUrl" value="/action/Accounting/Division/Main">
                        <c:param name="CompanyName" value="${company.companyName}" />
                    </c:url>
                    <a href="${divisionsUrl}"><fmt:message key="navigation.divisions" /></a><br />
                    <c:url var="editUrl" value="/action/Accounting/Company/Edit">
                        <c:param name="OriginalCompanyName" value="${company.companyName}" />
                    </c:url>
                    <a href="${editUrl}">Edit</a>
                    <c:url var="deleteUrl" value="/action/Accounting/Company/Delete">
                        <c:param name="CompanyName" value="${company.companyName}" />
                    </c:url>
                    <a href="${deleteUrl}">Delete</a>
                </display:column>
                <et:hasSecurityRole securityRole="Event.List">
                    <display:column>
                        <c:url var="eventsUrl" value="/action/Core/Event/Main">
                            <c:param name="EntityRef" value="${company.entityInstance.entityRef}" />
                        </c:url>
                        <a href="${eventsUrl}">Events</a>
                    </display:column>
                </et:hasSecurityRole>
            </display:table>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
