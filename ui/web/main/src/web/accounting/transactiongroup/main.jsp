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
        <title>Transaction Groups</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Accounting/Main" />"><fmt:message key="navigation.accounting" /></a> &gt;&gt;
                Transaction Groups
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="Event.List" />
            <display:table name="transactionGroups" id="transactionGroup" class="displaytag">
                <display:column titleKey="columnTitle.name">
                    <c:url var="reviewUrl" value="/action/Accounting/TransactionGroup/Review">
                        <c:param name="TransactionGroupName" value="${transactionGroup.transactionGroupName}" />
                    </c:url>
                    <a href="${reviewUrl}"><c:out value="${transactionGroup.transactionGroupName}" /></a>
                </display:column>
                <display:column titleKey="columnTitle.status">
                    <c:url var="statusUrl" value="/action/Accounting/TransactionGroup/Status">
                        <c:param name="TransactionGroupName" value="${transactionGroup.transactionGroupName}" />
                    </c:url>
                    <a href="${statusUrl}"><c:out value="${transactionGroup.transactionGroupStatus.workflowStep.description}" /></a>
                </display:column>
                <display:column titleKey="columnTitle.created">
                    <c:out value="${transactionGroup.entityInstance.entityTime.createdTime}" />
                </display:column>
                <display:column titleKey="columnTitle.modified">
                    <c:out value="${transactionGroup.entityInstance.entityTime.modifiedTime}" />
                </display:column>
                <display:column titleKey="columnTitle.transactions">
                    <c:url var="transactionsUrl" value="/action/Accounting/Transaction/Main">
                        <c:param name="TransactionGroupName" value="${transactionGroup.transactionGroupName}" />
                    </c:url>
                    <a href="${transactionsUrl}">Transactions</a>
                </display:column>
                <et:hasSecurityRole securityRole="Event.List">
                    <display:column>
                        <c:url var="eventsUrl" value="/action/Core/Event/Main">
                            <c:param name="EntityRef" value="${transactionGroup.entityInstance.entityRef}" />
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
