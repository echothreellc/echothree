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
        <title>Gl Accounts</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Accounting/Main" />"><fmt:message key="navigation.accounting" /></a> &gt;&gt;
                Gl Accounts
            </h2>
        </div>
        <div id="Content">
            <p><a href="<c:url value="/action/Accounting/GlAccount/Add" />">Add Gl Account.</a></p>
            <et:checkSecurityRoles securityRoles="Event.List" />
            <display:table name="glAccounts" id="glAccount" class="displaytag" sort="list" requestURI="/action/Accounting/GlAccount/Main">
                <display:column titleKey="columnTitle.name" sortable="true" sortProperty="glAccountName">
                    <c:url var="reviewUrl" value="/action/Accounting/GlAccount/Review">
                        <c:param name="GlAccountName" value="${glAccount.glAccountName}" />
                    </c:url>
                    <a href="${reviewUrl}"><c:out value="${glAccount.glAccountName}" /></a>
                </display:column>
                <display:column titleKey="columnTitle.description" sortable="true" sortProperty="description">
                    <c:out value="${glAccount.description}" />
                </display:column>
                <display:column titleKey="columnTitle.accountClass" sortable="true" sortProperty="glAccountClass.description">
                    <c:out value="${glAccount.glAccountClass.description}" />
                </display:column>
                <display:column titleKey="columnTitle.accountCategory" sortable="true" sortProperty="glAccountCategory.description">
                    <c:out value="${glAccount.glAccountCategory.description}" />
                </display:column>
                <display:column titleKey="columnTitle.resourceType" sortable="true" sortProperty="glResourceType.description">
                    <c:out value="${glAccount.glResourceType.description}" />
                </display:column>
                <display:column property="currency.currencyIsoName" titleKey="columnTitle.currency" sortable="true" sortProperty="currency.currencyIsoName" />
                <display:column>
                    <c:url var="editUrl" value="/action/Accounting/GlAccount/Edit">
                        <c:param name="OriginalGlAccountName" value="${glAccount.glAccountName}" />
                    </c:url>
                    <a href="${editUrl}">Edit</a>
                    <c:url var="descriptionsUrl" value="/action/Accounting/GlAccount/Description">
                        <c:param name="GlAccountName" value="${glAccount.glAccountName}" />
                    </c:url>
                    <a href="${descriptionsUrl}">Descriptions</a>
                    <c:url var="deleteUrl" value="/action/Accounting/GlAccount/Delete">
                        <c:param name="GlAccountName" value="${glAccount.glAccountName}" />
                    </c:url>
                    <a href="${deleteUrl}">Delete</a>
                </display:column>
                <et:hasSecurityRole securityRole="Event.List">
                    <display:column>
                        <c:url var="eventsUrl" value="/action/Core/Event/Main">
                            <c:param name="EntityRef" value="${glAccount.entityInstance.entityRef}" />
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
