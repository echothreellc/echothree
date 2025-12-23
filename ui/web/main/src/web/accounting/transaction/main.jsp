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
        <title>Transactions</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Accounting/Main" />"><fmt:message key="navigation.accounting" /></a> &gt;&gt;
                <a href="<c:url value="/action/Accounting/TransactionGroup/Main" />">Transaction Groups</a> &gt;&gt;
                Transactions
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="Event.List" />
            <display:table name="transactions" id="transaction" class="displaytag">
                <display:column titleKey="columnTitle.name">
                    <c:url var="reviewUrl" value="/action/Accounting/Transaction/Review">
                        <c:param name="TransactionName" value="${transaction.transactionName}" />
                    </c:url>
                    <a href="${reviewUrl}"><c:out value="${transaction.transactionName}" /></a>
                </display:column>
                <display:column titleKey="columnTitle.status">
                    <c:url var="statusUrl" value="/action/Accounting/Transaction/Status">
                        <c:param name="TransactionName" value="${transaction.transactionName}" />
                    </c:url>
                    <a href="${statusUrl}"><c:out value="${transaction.transactionStatus.workflowStep.description}" /></a>
                </display:column>
                <display:column>
                    <c:if test="${transaction.groupParty != null}">
                        <c:choose>
                            <c:when test="${transaction.groupParty.partyType.partyTypeName == 'COMPANY'}">
                                <c:url var="groupPartyUrl" value="/action/Accounting/Company/Review">
                                    <c:param name="PartyName" value="${transaction.groupParty.partyName}" />
                                </c:url>
                            </c:when>
                            <c:when test="${transaction.groupParty.partyType.partyTypeName == 'DIVISION'}">
                                <c:url var="groupPartyUrl" value="/action/Accounting/Division/Review">
                                    <c:param name="PartyName" value="${transaction.groupParty.partyName}" />
                                </c:url>
                            </c:when>
                            <c:when test="${transaction.groupParty.partyType.partyTypeName == 'DEPARTMENT'}">
                                <c:url var="groupPartyUrl" value="/action/Accounting/Department/Review">
                                    <c:param name="PartyName" value="${transaction.groupParty.partyName}" />
                                </c:url>
                            </c:when>
                        </c:choose>
                        <a href="${groupPartyUrl}"><c:out value="${transaction.groupParty.description}" /></a>
                    </c:if>
                </display:column>
                <display:column titleKey="columnTitle.transactionType">
                    <c:url var="transactionTypeUrl" value="/action/Accounting/TransactionType/Review">
                        <c:param name="TransactionTypeName" value="${transaction.transactionType.transactionTypeName}" />
                    </c:url>
                    <a href="${transactionTypeUrl}"><c:out value="${transaction.transactionType.description}" /></a>
                </display:column>
                <display:column titleKey="columnTitle.transactionTime">
                    <c:out value="${transaction.transactionTimes.map['TRANSACTION_TIME'].time}" />
                </display:column>
                <display:column titleKey="columnTitle.postedTime">
                    <c:out value="${transaction.transactionTimes.map['POSTED_TIME'].time}" />
                </display:column>
                <display:column titleKey="columnTitle.created">
                    <c:out value="${transaction.entityInstance.entityTime.createdTime}" />
                </display:column>
                <display:column titleKey="columnTitle.modified">
                    <c:out value="${transaction.entityInstance.entityTime.modifiedTime}" />
                </display:column>
                <et:hasSecurityRole securityRole="Event.List">
                    <display:column>
                        <c:url var="eventsUrl" value="/action/Core/Event/Main">
                            <c:param name="EntityRef" value="${transaction.entityInstance.entityRef}" />
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
