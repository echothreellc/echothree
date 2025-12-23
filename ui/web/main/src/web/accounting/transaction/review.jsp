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
        <title>Review (<c:out value="${transaction.transactionName}" />)</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Accounting/Main" />"><fmt:message key="navigation.accounting" /></a> &gt;&gt;
                <a href="<c:url value="/action/Accounting/TransactionGroup/Main" />">Transaction Groups</a> &gt;&gt;
                <c:url var="transactionsUrl" value="/action/Accounting/Transaction/Main">
                    <c:param name="TransactionGroupName" value="${transaction.transactionGroup.transactionGroupName}" />
                </c:url>
                <a href="${transactionsUrl}">Transactions</a> &gt;&gt;
                Review (<c:out value="${transaction.transactionName}" />)
            </h2>
        </div>
        <div id="Content">
            <p><font size="+2"><b><c:out value="${transaction.transactionName}" /></b></font></p>
            <br />
            <c:url var="transactionTypeUrl" value="/action/Accounting/TransactionType/Review">
                <c:param name="TransactionTypeName" value="${transactionGlEntry.transaction.transactionType.transactionTypeName}" />
            </c:url>
            Transaction Type: <a href="${transactionTypeUrl}"><c:out value="${transaction.transactionType.description}" /></a><br />
            <br />
            <c:if test="${transaction.transactionTimes.size > 0}">
                <h2>Times</h2>
                <display:table name="transaction.transactionTimes.list" id="transactionTime" class="displaytag">
                    <display:column titleKey="columnTitle.transactionTimeType">
                        <c:out value="${transactionTime.transactionTimeType.description}" />
                    </display:column>
                    <display:column titleKey="columnTitle.time">
                        <c:out value="${transactionTime.time}" />
                    </display:column>
                </display:table>
                <br />
            </c:if>
            <h2>Gl Entries</h2>
            <display:table name="transaction.transactionGlEntries.list" id="transactionGlEntry" class="displaytag">
                <display:column titleKey="columnTitle.sequence">
                    <c:out value="${transactionGlEntry.transactionGlEntrySequence}" />
                </display:column>
                <display:column>
                    <c:set var="party" scope="request" value="${transactionGlEntry.groupParty}" />
                    <jsp:include page="../../include/party.jsp" />
                </display:column>
                <display:column titleKey="columnTitle.description">
                    <c:url var="transactionGlAccountCategoryUrl" value="/action/Accounting/TransactionGlAccountCategory/Review">
                        <c:param name="TransactionTypeName" value="${transactionGlEntry.transaction.transactionType.transactionTypeName}" />
                        <c:param name="TransactionGlAccountCategoryName" value="${transactionGlEntry.transactionGlAccountCategory.transactionGlAccountCategoryName}" />
                    </c:url>
                    <a href="${transactionGlAccountCategoryUrl}"><c:out value="${transactionGlEntry.transactionGlAccountCategory.description}" /></a>
                </display:column>
                <display:column titleKey="columnTitle.glAccount">
                    <c:url var="glAccountUrl" value="/action/Accounting/GlAccount/Review">
                        <c:param name="GlAccountName" value="${transactionGlEntry.glAccount.glAccountName}" />
                    </c:url>
                    <a href="${glAccountUrl}"><c:out value="${transactionGlEntry.glAccount.description}" /></a>
                </display:column>
                <display:column titleKey="columnTitle.originalDebit" class="amount">
                    <c:if test="${transactionGlEntry.originalDebit != null}">
                        <c:out value="${transactionGlEntry.originalCurrency.symbol}" /><c:out value="${transactionGlEntry.originalDebit}" />
                    </c:if>
                </display:column>
                <display:column titleKey="columnTitle.originalCredit" class="amount">
                    <c:if test="${transactionGlEntry.originalCredit != null}">
                        <c:out value="${transactionGlEntry.originalCurrency.symbol}" /><c:out value="${transactionGlEntry.originalCredit}" />
                    </c:if>
                </display:column>
                <display:column titleKey="columnTitle.originalCurrency">
                    <c:url var="originalCurrencyUrl" value="/action/Accounting/Currency/Review">
                        <c:param name="CurrencyIsoName" value="${transactionGlEntry.originalCurrency.currencyIsoName}" />
                    </c:url>
                    <a href="${originalCurrencyUrl}"><c:out value="${transactionGlEntry.originalCurrency.currencyIsoName}" /></a>
                </display:column>
                <display:column titleKey="columnTitle.debit" class="amount">
                    <c:if test="${transactionGlEntry.debit != null}">
                        <c:out value="${transactionGlEntry.glAccount.currency.symbol}" /><c:out value="${transactionGlEntry.debit}" />
                    </c:if>
                </display:column>
                <display:column titleKey="columnTitle.credit" class="amount">
                    <c:if test="${transactionGlEntry.credit != null}">
                        <c:out value="${transactionGlEntry.glAccount.currency.symbol}" /><c:out value="${transactionGlEntry.credit}" />
                    </c:if>
                </display:column>
                <display:column titleKey="columnTitle.currency">
                    <c:url var="currencyUrl" value="/action/Accounting/Currency/Review">
                        <c:param name="CurrencyIsoName" value="${transactionGlEntry.glAccount.currency.currencyIsoName}" />
                    </c:url>
                    <a href="${currencyUrl}"><c:out value="${transactionGlEntry.glAccount.currency.currencyIsoName}" /></a>
                </display:column>
            </display:table>
            <br />
            <c:if test="${transaction.transactionEntityRoles.size > 0}">
                <h2>Entity Roles</h2>
                <display:table name="transaction.transactionEntityRoles.list" id="transactionEntityRole" class="displaytag">
                    <display:column titleKey="columnTitle.entityRoleType">
                        <c:url var="transactionEntityRoleTypeUrl" value="/action/Accounting/TransactionEntityRoleType/Review">
                            <c:param name="TransactionTypeName" value="${transactionEntityRole.transaction.transactionType.transactionTypeName}" />
                            <c:param name="TransactionEntityRoleTypeName" value="${transactionEntityRole.transactionEntityRoleType.transactionEntityRoleTypeName}" />
                        </c:url>
                        <a href="${transactionEntityRoleTypeUrl}"><c:out value="${transactionEntityRole.transactionEntityRoleType.description}" /></a>
                    </display:column>
                    <display:column titleKey="columnTitle.entity">
                        <c:set var="entityInstance" scope="request" value="${transactionEntityRole.entityInstance}" />
                        <jsp:include page="../../include/targetAsReviewLink.jsp" />
                    </display:column>
                </display:table>
                <br />
            </c:if>
            <br />
            <br />
            <c:set var="entityInstance" scope="request" value="${transaction.entityInstance}" />
            <jsp:include page="../../include/entityInstance.jsp" />
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
