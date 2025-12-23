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
        <title>Review (<c:out value="${vendor.vendorName}" />)</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Purchasing/Main" />">Purchasing</a> &gt;&gt;
                <a href="<c:url value="/action/Purchasing/Vendor/Main" />">Vendors</a> &gt;&gt;
                <et:countVendorResults searchTypeName="VENDOR_REVIEW" countVar="vendorResultsCount" commandResultVar="countVendorResultsCommandResult" logErrors="false" />
                <c:if test="${vendorResultsCount > 0}">
                    <a href="<c:url value="/action/Purchasing/Vendor/Result" />"><fmt:message key="navigation.results" /></a> &gt;&gt;
                </c:if>
                Review (<c:out value="${vendor.vendorName}" />)
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="Item.Review:VendorItem.Review:VendorItem.Create:VendorItemStatus.Choices:VendorStatus.Choices:CancellationPolicy.Review:ReturnPolicy.Review:Event.List" />
            <et:hasSecurityRole securityRole="Item.Review" var="includeItemUrl" />
            <et:hasSecurityRole securityRole="CancellationPolicy.Review" var="includeCancellationPolicyUrl" />
            <et:hasSecurityRole securityRole="ReturnPolicy.Review" var="includeReturnPolicyUrl" />
            <et:hasSecurityRole securityRole="VendorItemStatus.Choices" var="includeEditableVendorItemStatus" />
            <c:url var="returnUrl" scope="request" value="/../action/Purchasing/Vendor/Review">
                <c:param name="VendorName" value="${vendor.vendorName}" />
            </c:url>
            <c:set var="party" scope="request" value="${vendor}" />
            <c:if test='${vendor.partyGroup.name != null}'>
                <p><font size="+2"><c:out value="${vendor.partyGroup.name}" /></font></p>
            </c:if>
            <c:if test='${vendor.person.firstName != null || vendor.person.middleName != null || vendor.person.lastName != null}'>
                <p><font size="+1"><b><c:out value="${vendor.person.personalTitle.description}" /> <c:out value="${vendor.person.firstName}" />
                    <c:out value="${vendor.person.middleName}" /> <c:out value="${vendor.person.lastName}" />
                    <c:out value="${vendor.person.nameSuffix.description}" /></b></font></p>
            </c:if>
            <br />
            Vendor Name: ${vendor.vendorName}<br />
            Vendor Type:
            <c:url var="vendorTypeUrl" value="/action/Purchasing/VendorType/Review">
                <c:param name="VendorTypeName" value="${vendor.vendorType.vendorTypeName}" />
            </c:url>
            <a href="${vendorTypeUrl}"><c:out value="${vendor.vendorType.description}" /></a>
            <c:url var="editUrl" value="/action/Purchasing/Vendor/VendorEdit">
                <c:param name="OriginalVendorName" value="${vendor.vendorName}" />
            </c:url>
            <a href="${editUrl}">Edit</a>
            <br />
            Terms: <c:out value="${vendor.partyTerm.term.description}" />
            <c:url var="editUrl" value="/action/Purchasing/Vendor/PartyTermEdit">
                <c:param name="VendorName" value="${vendor.vendorName}" />
                <c:param name="PartyName" value="${vendor.partyName}" />
            </c:url>
            <a href="${editUrl}">Edit</a>
            <br />
            Cancellation Policy:
            <c:choose>
                <c:when test="${vendor.cancellationPolicy != null}">
                    <c:choose>
                        <c:when test="${includeCancellationPolicyUrl}">
                            <c:url var="reviewUrl" value="/action/CancellationPolicy/CancellationPolicy/Review">
                                <c:param name="CancellationKindName" value="${vendor.cancellationPolicy.cancellationKind.cancellationKindName}" />
                                <c:param name="CancellationPolicyName" value="${vendor.cancellationPolicy.cancellationPolicyName}" />
                            </c:url>
                            <a href="${reviewUrl}"><c:out value="${vendor.cancellationPolicy.description}" /></a>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${vendor.cancellationPolicy.description}" />
                        </c:otherwise>
                    </c:choose>
                </c:when>
                <c:otherwise>
                    <i><fmt:message key="phrase.notSet" /></i>
                </c:otherwise>
            </c:choose>
            <br />
            Return Policy:
            <c:choose>
                <c:when test="${vendor.returnPolicy != null}">
                    <c:choose>
                        <c:when test="${includeReturnPolicyUrl}">
                            <c:url var="reviewUrl" value="/action/ReturnPolicy/ReturnPolicy/Review">
                                <c:param name="ReturnKindName" value="${vendor.returnPolicy.returnKind.returnKindName}" />
                                <c:param name="ReturnPolicyName" value="${vendor.returnPolicy.returnPolicyName}" />
                            </c:url>
                            <a href="${reviewUrl}"><c:out value="${vendor.returnPolicy.description}" /></a>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${vendor.returnPolicy.description}" />
                        </c:otherwise>
                    </c:choose>
                </c:when>
                <c:otherwise>
                    <i><fmt:message key="phrase.notSet" /></i>
                </c:otherwise>
            </c:choose>
            <br />
            <br />
            Vendor Status: <c:out value="${vendor.vendorStatus.workflowStep.description}" />
            <et:hasSecurityRole securityRole="VendorStatus.Choices">
                <c:url var="editUrl" value="/action/Purchasing/Vendor/Status">
                    <c:param name="VendorName" value="${vendor.vendorName}" />
                    <c:param name="ReturnUrl" value="${returnUrl}" />
                </c:url>
                <a href="${editUrl}">Edit</a>
            </et:hasSecurityRole>
            <br />
            <br />
            <br />
            <h2>Credit Limits</h2>
            <c:url var="addUrl" value="/action/Customer/Customer/PartyCreditLimitAdd">
                <c:param name="CustomerName" value="${customer.customerName}" />
                <c:param name="PartyName" value="${customer.partyName}" />
            </c:url>
            <p><a href="${addUrl}">Add Credit Limit.</a></p>
            <c:choose>
                <c:when test="${vendor.partyCreditLimits.size == 0}">
                    No credit limits were found.<br />
                    <br />
                </c:when>
                <c:otherwise>
                    <display:table name="vendor.partyCreditLimits.list" id="partyCreditLimit" class="displaytag">
                        <display:column titleKey="columnTitle.currency">
                            <c:url var="reviewUrl" value="/action/Accounting/Currency/Review">
                                <c:param name="CurrencyIsoName" value="${partyCreditLimit.currency.currencyIsoName}" />
                            </c:url>
                            <a href="${reviewUrl}"><c:out value="${partyCreditLimit.currency.description}" /></a>
                        </display:column>
                        <display:column titleKey="columnTitle.creditLimit" class="amount">
                            <c:out value="${partyCreditLimit.creditLimit}" />
                        </display:column>
                        <display:column titleKey="columnTitle.potentialCreditLimit" class="amount">
                            <c:out value="${partyCreditLimit.potentialCreditLimit}" />
                        </display:column>
                        <display:column>
                            <c:url var="editUrl" value="/action/Customer/Customer/PartyCreditLimitEdit">
                                <c:param name="CustomerName" value="${customer.customerName}" />
                                <c:param name="PartyName" value="${customer.partyName}" />
                                <c:param name="CurrencyIsoName" value="${partyCreditLimit.currency.currencyIsoName}" />
                            </c:url>
                            <a href="${editUrl}">Edit</a>
                            <c:url var="deleteUrl" value="/action/Customer/Customer/PartyCreditLimitDelete">
                                <c:param name="CustomerName" value="${customer.customerName}" />
                                <c:param name="PartyName" value="${customer.partyName}" />
                                <c:param name="CurrencyIsoName" value="${partyCreditLimit.currency.currencyIsoName}" />
                            </c:url>
                            <a href="${deleteUrl}">Delete</a>
                        </display:column>
                    </display:table>
                </c:otherwise>
            </c:choose>
            <br />
            
            <h2>Billing Accounts</h2>
            <c:choose>
                <c:when test="${vendor.billingAccounts.size == 0}">
                    No billing accounts were found.<br />
                    <br />
                </c:when>
                <c:otherwise>
                    <display:table name="vendor.billingAccounts.list" id="billingAccount" class="displaytag">
                        <display:column titleKey="columnTitle.name">
                            <c:out value="${billingAccount.billingAccountName}" />
                        </display:column>
                        <display:column titleKey="columnTitle.currency">
                            <c:url var="reviewUrl" value="/action/Accounting/Currency/Review">
                                <c:param name="CurrencyIsoName" value="${billingAccount.currency.currencyIsoName}" />
                            </c:url>
                            <a href="${reviewUrl}"><c:out value="${billingAccount.currency.description}" /></a>
                        </display:column>
                        <display:column titleKey="columnTitle.reference">
                            <c:out value="${billingAccount.reference}" />
                        </display:column>
                        <display:column titleKey="columnTitle.description">
                            <c:out value="${billingAccount.description}" />
                        </display:column>
                    </display:table>
                </c:otherwise>
            </c:choose>
            <br />
            
            <h2>Vendor Items</h2>
            <et:hasSecurityRole securityRole="VendorItem.Create">
                <c:url var="addUrl" value="/action/Purchasing/VendorItem/Add">
                    <c:param name="VendorName" value="${vendor.vendorName}" />
                </c:url>
            </et:hasSecurityRole>
            <p><a href="${addUrl}">Add Vendor Item.</a></p>
            <c:choose>
                <c:when test="${vendor.vendorItems.size == 0}">
                    No vendor items were found.<br />
                    <br />
                </c:when>
                <c:otherwise>
                    <display:table name="vendor.vendorItems.list" id="vendorItem" class="displaytag">
                        <et:hasSecurityRole securityRole="VendorItem.Review">
                            <display:column>
                                <c:url var="reviewUrl" value="/action/Purchasing/VendorItem/Review">
                                    <c:param name="VendorName" value="${vendor.vendorName}" />
                                    <c:param name="VendorItemName" value="${vendorItem.vendorItemName}" />
                                </c:url>
                                <a href="${reviewUrl}">Review</a>
                            </display:column>
                        </et:hasSecurityRole>
                        <display:column titleKey="columnTitle.itemName">
                            <c:choose>
                                <c:when test="${includeItemUrl}">
                                    <c:url var="itemReviewUrl" value="/action/Item/Item/Review">
                                        <c:param name="ItemName" value="${vendorItem.item.itemName}" />
                                    </c:url>
                                    <a href="${itemReviewUrl}"><c:out value="${vendorItem.item.itemName}" /></a>
                                </c:when>
                                <c:otherwise>
                                    <c:out value="${vendorItem.item.itemName}" />
                                </c:otherwise>
                            </c:choose>
                        </display:column>
                        <display:column titleKey="columnTitle.vendorItemName">
                            <c:out value="${vendorItem.vendorItemName}" />
                        </display:column>
                        <display:column titleKey="columnTitle.description">
                            <c:out value="${vendorItem.description}" />
                        </display:column>
                        <display:column titleKey="columnTitle.priority">
                            <c:out value="${vendorItem.priority}" />
                        </display:column>
                        <display:column titleKey="columnTitle.cancellationPolicy">
                            <c:if test="${vendorItem.cancellationPolicy != null}">
                                <c:choose>
                                    <c:when test="${includeCancellationPolicyUrl}">
                                        <c:url var="reviewUrl" value="/action/CancellationPolicy/CancellationPolicy/Review">
                                            <c:param name="CancellationKindName" value="${vendorItem.cancellationPolicy.cancellationKind.cancellationKindName}" />
                                            <c:param name="CancellationPolicyName" value="${vendorItem.cancellationPolicy.cancellationPolicyName}" />
                                        </c:url>
                                        <a href="${reviewUrl}"><c:out value="${vendorItem.cancellationPolicy.description}" /></a>
                                    </c:when>
                                    <c:otherwise>
                                        <c:out value="${vendorItem.cancellationPolicy.description}" />
                                    </c:otherwise>
                                </c:choose>
                            </c:if>
                        </display:column>
                        <display:column titleKey="columnTitle.returnPolicy">
                            <c:if test="${vendorItem.returnPolicy != null}">
                                <c:choose>
                                    <c:when test="${includeReturnPolicyUrl}">
                                        <c:url var="reviewUrl" value="/action/ReturnPolicy/ReturnPolicy/Review">
                                            <c:param name="ReturnKindName" value="${vendorItem.returnPolicy.returnKind.returnKindName}" />
                                            <c:param name="ReturnPolicyName" value="${vendorItem.returnPolicy.returnPolicyName}" />
                                        </c:url>
                                        <a href="${reviewUrl}"><c:out value="${vendorItem.returnPolicy.description}" /></a>
                                    </c:when>
                                    <c:otherwise>
                                        <c:out value="${vendorItem.returnPolicy.description}" />
                                    </c:otherwise>
                                </c:choose>
                            </c:if>
                        </display:column>
                        <display:column titleKey="columnTitle.status">
                            <c:choose>
                                <c:when test="${includeEditableVendorItemStatus}">
                                    <c:url var="statusUrl" value="/action/Purchasing/VendorItem/Status">
                                        <c:param name="ReturnUrl" value="${returnUrl}" />
                                        <c:param name="VendorName" value="${vendor.vendorName}" />
                                        <c:param name="VendorItemName" value="${vendorItem.vendorItemName}" />
                                    </c:url>
                                    <a href="${statusUrl}"><c:out value="${vendorItem.vendorItemStatus.workflowStep.description}" /></a>
                                </c:when>
                                <c:otherwise>
                                    <c:out value="${vendorItem.vendorItemStatus.workflowStep.description}" />
                                </c:otherwise>
                            </c:choose>
                        </display:column>
                        <display:column>
                            <c:url var="vendorItemCostsUrl" value="/action/Purchasing/VendorItemCost/Main">
                                <c:param name="VendorName" value="${vendor.vendorName}" />
                                <c:param name="VendorItemName" value="${vendorItem.vendorItemName}" />
                            </c:url>
                            <a href="${vendorItemCostsUrl}">Costs</a><br />
                            <c:url var="editUrl" value="/action/Purchasing/VendorItem/Edit">
                                <c:param name="VendorName" value="${vendor.vendorName}" />
                                <c:param name="OriginalVendorItemName" value="${vendorItem.vendorItemName}" />
                            </c:url>
                            <a href="${editUrl}">Edit</a>
                            <c:url var="deleteUrl" value="/action/Purchasing/VendorItem/Delete">
                                <c:param name="VendorName" value="${vendor.vendorName}" />
                                <c:param name="VendorItemName" value="${vendorItem.vendorItemName}" />
                            </c:url>
                            <a href="${deleteUrl}">Delete</a>
                        </display:column>
                        <et:hasSecurityRole securityRole="Event.List">
                            <display:column>
                                <c:url var="eventsUrl" value="/action/Core/Event/Main">
                                    <c:param name="EntityRef" value="${vendorItem.entityInstance.entityRef}" />
                                </c:url>
                                <a href="${eventsUrl}">Events</a>
                            </display:column>
                        </et:hasSecurityRole>
                    </display:table>
                    <c:if test='${vendor.vendorItemsCount > 5}'>
                        <c:url var="vendorItemsUrl" value="/action/Purchasing/VendorItem/Main">
                            <c:param name="VendorName" value="${vendor.vendorName}" />
                        </c:url>
                        <a href="${vendorItemsUrl}">More...</a> (<c:out value="${vendor.vendorItemsCount}" /> total)<br />
                    </c:if>
                </c:otherwise>
            </c:choose>
            <br />
            
            <c:set var="commonUrl" scope="request" value="Purchasing/Vendor" />
            <c:set var="comments" scope="request" value="${vendor.comments.map['VENDOR_PURCHASING']}" />
            <jsp:include page="../../include/partyComments.jsp" />
            
            <h2>Accounts Payable Invoices</h2>
            <c:choose>
                <c:when test="${vendor.invoicesFrom.size == 0}">
                    No AP invoices were found.<br />
                    <br />
                </c:when>
                <c:otherwise>
                    <display:table name="vendor.invoicesFrom.list" id="invoice" class="displaytag">
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
                    <c:if test='${vendor.invoicesFromCount > 5}'>
                        <a href="TODO">More...</a> (<c:out value="${vendor.invoicesFromCount}" /> total)<br />
                    </c:if>
                </c:otherwise>
            </c:choose>
            <br />
            
            <c:set var="commonUrl" scope="request" value="Purchasing/VendorContactMechanism" />
            <c:set var="party" scope="request" value="${vendor}" />
            <c:set var="partyContactMechanisms" scope="request" value="${vendor.partyContactMechanisms}" />
            <jsp:include page="../../include/partyContactMechanisms.jsp" />
            
            <c:set var="commonUrl" scope="request" value="Purchasing/VendorDocument" />
            <c:set var="partyDocuments" scope="request" value="${vendor.partyDocuments}" />
            <jsp:include page="../../include/partyDocuments.jsp" />

            <c:set var="commonUrl" scope="request" value="Purchasing/Vendor" />
            <c:set var="partyContactLists" scope="request" value="${vendor.partyContactLists}" />
            <jsp:include page="../../include/partyContactLists.jsp" />

            <c:set var="commonUrl" scope="request" value="Purchasing/VendorCarrier" />
            <c:set var="partyCarriers" scope="request" value="${vendor.partyCarriers}" />
            <jsp:include page="../../include/partyCarriers.jsp" />

            <c:set var="commonUrl" scope="request" value="Purchasing/VendorCarrierAccount" />
            <c:set var="partyCarrierAccounts" scope="request" value="${vendor.partyCarrierAccounts}" />
            <jsp:include page="../../include/partyCarrierAccounts.jsp" />

            <c:set var="commonUrl" scope="request" value="Purchasing/VendorAlias" />
            <c:set var="partyAliases" scope="request" value="${vendor.partyAliases}" />
            <c:set var="securityRoleGroupNamePrefix" scope="request" value="Vendor" />
            <jsp:include page="../../include/partyAliases.jsp" />
            
            <c:set var="tagScopes" scope="request" value="${vendor.tagScopes}" />
            <c:set var="entityAttributeGroups" scope="request" value="${vendor.entityAttributeGroups}" />
            <c:set var="entityInstance" scope="request" value="${vendor.entityInstance}" />
            <jsp:include page="../../include/tagScopes.jsp" />
            <jsp:include page="../../include/entityAttributeGroups.jsp" />
            <jsp:include page="../../include/entityInstance.jsp" />
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
