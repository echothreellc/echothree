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
        <title>
            <fmt:message key="pageTitle.company">
                <fmt:param value="${company.companyName}" />
            </fmt:message>
        </title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Accounting/Main" />"><fmt:message key="navigation.accounting" /></a> &gt;&gt;
                <a href="<c:url value="/action/Accounting/Company/Main" />"><fmt:message key="navigation.companies" /></a> &gt;&gt;
                Review (<c:out value="${company.companyName}" />)
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="Event.List" />
            <p><font size="+2"><b><c:out value="${company.partyGroup.name}" /></b></font></p>
            <br />
            <fmt:message key="label.companyName" />: ${company.companyName}<br />
            <br />
            <h2>Accounts Payable Invoices</h2>
            <c:choose>
                <c:when test="${company.invoicesTo.size == 0}">
                    No AP invoices were found.<br />
                </c:when>
                <c:otherwise>
                    <display:table name="company.invoicesTo.list" id="invoice" class="displaytag">
                        <display:column titleKey="columnTitle.invoice">
                            <c:out value="${invoice.invoiceName}" />
                        </display:column>
                        <display:column titleKey="columnTitle.reference">
                            <c:out value="${invoice.reference}" />
                        </display:column>
                        <display:column titleKey="columnTitle.description">
                            <c:out value="${invoice.description}" />
                        </display:column>
                        <display:column titleKey="columnTitle.invoiceDate">
                            <c:out value="${invoice.invoiceTimes.map['INVOICED'].time}" />
                        </display:column>
                        <display:column titleKey="columnTitle.dueDate">
                            <c:out value="${invoice.invoiceTimes.map['DUE'].time}" />
                        </display:column>
                        <display:column titleKey="columnTitle.paidDate">
                            <c:out value="${invoice.invoiceTimes.map['PAID'].time}" />
                        </display:column>
                        <display:column titleKey="columnTitle.terms">
                            <c:out value="${invoice.term.description}" />
                        </display:column>
                        <display:column titleKey="columnTitle.status">
                            <c:out value="${invoice.invoiceStatus.workflowStep.description}" />
                        </display:column>
                    </display:table>
                    <c:if test='${company.invoicesToCount > 5}'>
                        <a href="TODO">More...</a> (<c:out value="${company.invoicesToCount}" /> total)<br />
                    </c:if>
                </c:otherwise>
            </c:choose>
            <br />
            <h2>Accounts Receivable Invoices</h2>
            <c:choose>
                <c:when test="${company.invoicesFrom.size == 0}">
                    No AR invoices were found.<br />
                </c:when>
                <c:otherwise>
                    <display:table name="company.invoicesFrom.list" id="invoice" class="displaytag">
                        <display:column titleKey="columnTitle.invoice">
                            <c:out value="${invoice.invoiceName}" />
                        </display:column>
                        <display:column titleKey="columnTitle.reference">
                            <c:out value="${invoice.reference}" />
                        </display:column>
                        <display:column titleKey="columnTitle.description">
                            <c:out value="${invoice.description}" />
                        </display:column>
                        <display:column titleKey="columnTitle.invoiceDate">
                            <c:out value="${invoice.invoiceTimes.map['INVOICED'].time}" />
                        </display:column>
                        <display:column titleKey="columnTitle.dueDate">
                            <c:out value="${invoice.invoiceTimes.map['DUE'].time}" />
                        </display:column>
                        <display:column titleKey="columnTitle.paidDate">
                            <c:out value="${invoice.invoiceTimes.map['PAID'].time}" />
                        </display:column>
                        <display:column titleKey="columnTitle.terms">
                            <c:out value="${invoice.term.description}" />
                        </display:column>
                        <display:column titleKey="columnTitle.status">
                            <c:out value="${invoice.invoiceStatus.workflowStep.description}" />
                        </display:column>
                    </display:table>
                    <c:if test='${company.invoicesFromCount > 5}'>
                        <a href="TODO">More...</a> (<c:out value="${company.invoicesFromCount}" /> total)<br />
                    </c:if>
                </c:otherwise>
            </c:choose>
            <br />
            <br />
            <br />
            <c:set var="commonUrl" scope="request" value="Accounting/CompanyContactMechanism" />
            <c:set var="party" scope="request" value="${company}" />
            <c:set var="partyContactMechanisms" scope="request" value="${company.partyContactMechanisms}" />
            <jsp:include page="../../include/partyContactMechanisms.jsp" />
            <c:set var="commonUrl" scope="request" value="Accounting/CompanyPrinterGroupUse" />
            <c:set var="partyPrinterGroupUses" scope="request" value="${company.partyPrinterGroupUses}" />
            <jsp:include page="../../include/partyPrinterGroupUses.jsp" />
            <c:set var="commonUrl" scope="request" value="Accounting/CompanyCarrier" />
            <c:set var="partyCarriers" scope="request" value="${company.partyCarriers}" />
            <jsp:include page="../../include/partyCarriers.jsp" />
            <c:set var="commonUrl" scope="request" value="Accounting/CompanyCarrierAccount" />
            <c:set var="partyCarrierAccounts" scope="request" value="${company.partyCarrierAccounts}" />
            <jsp:include page="../../include/partyCarrierAccounts.jsp" />
            <c:set var="commonUrl" scope="request" value="Accounting/CompanyDocument" />
            <c:set var="partyDocuments" scope="request" value="${company.partyDocuments}" />
            <jsp:include page="../../include/partyDocuments.jsp" />
            <c:set var="tagScopes" scope="request" value="${company.tagScopes}" />
            <c:set var="entityAttributeGroups" scope="request" value="${company.entityAttributeGroups}" />
            <c:set var="entityInstance" scope="request" value="${company.entityInstance}" />
            <c:url var="returnUrl" scope="request" value="/../action/Accounting/Company/Review">
                <c:param name="PartyName" value="${company.partyName}" />
            </c:url>
            <jsp:include page="../../include/tagScopes.jsp" />
            <jsp:include page="../../include/entityAttributeGroups.jsp" />
            <jsp:include page="../../include/entityInstance.jsp" />
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
