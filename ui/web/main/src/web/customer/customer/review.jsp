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

<html>
    <head>
        <title>
            <fmt:message key="pageTitle.customer">
                <fmt:param value="${customer.customerName}" />
            </fmt:message>
        </title>
        <html:base/>
        <%@ include file="../../include/environment-b.jsp" %>
    </head>
    <%@ include file="../../include/body-start-b.jsp" %>
        <%@ include file="../../include/breadcrumb/breadcrumbs-start.jsp" %>
            <jsp:include page="../../include/breadcrumb/portal.jsp">
                <jsp:param name="showAsLink" value="true"/>
            </jsp:include>
            <jsp:include page="../../include/breadcrumb/customers.jsp">
                <jsp:param name="showAsLink" value="true"/>
            </jsp:include>
            <jsp:include page="../../include/breadcrumb/customers-search.jsp">
                <jsp:param name="showAsLink" value="true"/>
            </jsp:include>
            <jsp:include page="../../include/breadcrumb/customers-results.jsp">
                <jsp:param name="showAsLink" value="true"/>
            </jsp:include>
            <jsp:include page="../../include/breadcrumb/customer.jsp">
                <jsp:param name="showAsLink" value="false"/>
            </jsp:include>
        <%@ include file="../../include/breadcrumb/breadcrumbs-end.jsp" %>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="CustomerStatus.Choices:CancellationPolicy.Review:ReturnPolicy.Review:WorkflowStep.Review:Event.List" />
            <et:hasSecurityRole securityRole="CancellationPolicy.Review" var="includeCancellationPolicyUrl" />
            <et:hasSecurityRole securityRole="ReturnPolicy.Review" var="includeReturnPolicyUrl" />
            <et:hasSecurityRole securityRole="WorkflowStep.Review" var="includeWorkflowStepUrl" />
            <c:set var="party" scope="request" value="${customer}" />
            <c:if test='${customer.person.firstName != null || customer.person.middleName != null || customer.person.lastName != null}'>
                <h1><et:appearance appearance="${customer.entityInstance.entityAppearance.appearance}"><c:out value="${customer.person.personalTitle.description}" /> <c:out value="${customer.person.firstName}" />
                <c:out value="${customer.person.middleName}" /> <c:out value="${customer.person.lastName}" />
                <c:out value="${customer.person.nameSuffix.description}" /></et:appearance></h1>
            </c:if>
            <c:if test='${customer.partyGroup.name != null}'>
                <h2><et:appearance appearance="${customer.entityInstance.entityAppearance.appearance}"><c:out value="${customer.partyGroup.name}" /></et:appearance></h2>
            </c:if>
            Customer Name: <et:appearance appearance="${customer.entityInstance.entityAppearance.appearance}">${customer.customerName}</et:appearance><br />
            Customer Type:
            <c:url var="customerTypeUrl" value="/action/Customer/CustomerType/Review">
                <c:param name="CustomerTypeName" value="${customer.customerType.customerTypeName}" />
            </c:url>
            <a href="${customerTypeUrl}"><et:appearance appearance="${customer.customerType.entityInstance.entityAppearance.appearance}"><c:out value="${customer.customerType.description}" /></et:appearance></a>
            <c:url var="editUrl" value="/action/Customer/Customer/CustomerEdit">
                <c:param name="CustomerName" value="${customer.customerName}" />
            </c:url>
            <a href="${editUrl}">Edit</a>
            <br />
            <c:url var="initialOfferUseUrl" value="/action/Advertising/OfferUse/Review">
                <c:param name="OfferName" value="${customer.initialOfferUse.offer.offerName}" />
                <c:param name="UseName" value="${customer.initialOfferUse.use.useName}" />
            </c:url>
            Initial Offer Use: <a href="${initialOfferUseUrl}"><c:out value="${customer.initialOfferUse.offer.description}" />:<c:out value="${customer.initialOfferUse.use.description}" /></a><br />
            Terms: <c:out value="${customer.partyTerm.term.description}" />,
            <c:choose>
                <c:when test="${customer.partyTerm.taxable}">
                    Taxable
                </c:when>
                <c:otherwise>
                    Tax Exempt
                </c:otherwise>
            </c:choose>
            <c:url var="editUrl" value="/action/Customer/Customer/PartyTermEdit">
                <c:param name="CustomerName" value="${customer.customerName}" />
                <c:param name="PartyName" value="${customer.partyName}" />
            </c:url>
            <a href="${editUrl}">Edit</a>
            <br />
            Cancellation Policy:
            <c:choose>
                <c:when test="${customer.cancellationPolicy != null}">
                    <c:choose>
                        <c:when test="${includeCancellationPolicyUrl}">
                            <c:url var="reviewUrl" value="/action/CancellationPolicy/CancellationPolicy/Review">
                                <c:param name="CancellationKindName" value="${customer.cancellationPolicy.cancellationKind.cancellationKindName}" />
                                <c:param name="CancellationPolicyName" value="${customer.cancellationPolicy.cancellationPolicyName}" />
                            </c:url>
                            <a href="${reviewUrl}"><c:out value="${customer.cancellationPolicy.description}" /></a>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${customer.cancellationPolicy.description}" />
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
                <c:when test="${customer.returnPolicy != null}">
                    <c:choose>
                        <c:when test="${includeReturnPolicyUrl}">
                            <c:url var="reviewUrl" value="/action/ReturnPolicy/ReturnPolicy/Review">
                                <c:param name="ReturnKindName" value="${customer.returnPolicy.returnKind.returnKindName}" />
                                <c:param name="ReturnPolicyName" value="${customer.returnPolicy.returnPolicyName}" />
                            </c:url>
                            <a href="${reviewUrl}"><c:out value="${customer.returnPolicy.description}" /></a>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${customer.returnPolicy.description}" />
                        </c:otherwise>
                    </c:choose>
                </c:when>
                <c:otherwise>
                    <i><fmt:message key="phrase.notSet" /></i>
                </c:otherwise>
            </c:choose>
            <br />
            <br />
            Status: <et:appearance appearance="${customer.customerStatus.workflowStep.entityInstance.entityAppearance.appearance}"><c:out value="${customer.customerStatus.workflowStep.description}" /></et:appearance>
            <et:hasSecurityRole securityRole="CustomerStatus.Choices">
                <c:url var="returnUrl" scope="request" value="Review">
                    <c:param name="CustomerName" value="${customer.customerName}" />
                </c:url>
                <c:url var="editUrl" value="/action/Customer/Customer/CustomerStatus">
                    <c:param name="CustomerName" value="${customer.customerName}" />
                    <c:param name="ReturnUrl" value="${returnUrl}" />
                </c:url>
                <a href="${editUrl}">Edit</a>
            </et:hasSecurityRole>
            <br />
            Credit Status:  <c:out value="${customer.customerCreditStatus.workflowStep.description}" />
            <c:if test='${customer.customerCreditStatus.workflowStep.hasDestinations}'>
                <c:url var="editUrl" value="/action/Customer/Customer/CustomerCreditStatus">
                    <c:param name="CustomerName" value="${customer.customerName}" />
                </c:url>
                <a href="${editUrl}">Edit</a>
            </c:if>
            <br />
            <br />
            Username:
            <c:choose>
                <c:when test="${customer.userLogin == null}">
                    <c:url var="createUserLoginUrl" value="/action/Customer/Customer/CustomerUserLoginAdd">
                        <c:param name="PartyName" value="${customer.partyName}" />
                    </c:url>
                    <a href="${createUserLoginUrl}">Create</a>
                </c:when>
                <c:otherwise>
                    <c:out value="${customer.userLogin.username}" />
                    <c:url var="editUserLoginUrl" value="/action/Customer/Customer/CustomerUserLoginEdit">
                        <c:param name="PartyName" value="${customer.partyName}" />
                    </c:url>
                    <a href="${editUserLoginUrl}">Edit</a>
                    <c:url var="deleteUserLoginUrl" value="/action/Customer/Customer/CustomerUserLoginDelete">
                        <c:param name="PartyName" value="${customer.partyName}" />
                    </c:url>
                    <a href="${deleteUserLoginUrl}">Delete</a>
                </c:otherwise>
            </c:choose>
            <br />
            <c:if test="${customer.recoveryAnswer != null}">
                Recovery Answer:
                <c:url var="reviewRecoveryAnswerUrl" value="/action/Customer/Customer/CustomerRecoveryAnswerReview">
                    <c:param name="PartyName" value="${customer.partyName}" />
                </c:url>
                <a href="${reviewRecoveryAnswerUrl}">Review</a>
                <c:url var="editRecoveryAnswerUrl" value="/action/Customer/Customer/CustomerRecoveryAnswerEdit">
                    <c:param name="PartyName" value="${customer.partyName}" />
                </c:url>
                <a href="${editRecoveryAnswerUrl}">Edit</a>
                <br />
            </c:if>
            Profile:
            <c:choose>
                <c:when test="${customer.profile == null}">
                    <c:url var="createProfileUrl" value="/action/Customer/Customer/CustomerProfileAdd">
                        <c:param name="CustomerName" value="${customer.customerName}" />
                        <c:param name="PartyName" value="${customer.partyName}" />
                    </c:url>
                    <a href="${createProfileUrl}">Create</a>
                </c:when>
                <c:otherwise>
                    <c:url var="reviewProfileUrl" value="/action/Customer/Customer/CustomerProfileReview">
                        <c:param name="CustomerName" value="${customer.customerName}" />
                    </c:url>
                    <a href="${reviewProfileUrl}">Review</a>
                    <c:url var="editProfileUrl" value="/action/Customer/Customer/CustomerProfileEdit">
                        <c:param name="CustomerName" value="${customer.customerName}" />
                        <c:param name="PartyName" value="${customer.partyName}" />
                    </c:url>
                    <a href="${editProfileUrl}">Edit</a>
                </c:otherwise>
            </c:choose>
            <br />
            <br />
            <br />
            <h3>Credit Limits</h3>
            <c:url var="addUrl" value="/action/Customer/Customer/PartyCreditLimitAdd">
                <c:param name="CustomerName" value="${customer.customerName}" />
                <c:param name="PartyName" value="${customer.partyName}" />
            </c:url>
            <p><a href="${addUrl}">Add Credit Limit.</a></p>
            <c:choose>
                <c:when test='${customer.partyCreditLimits.size == 0}'>
                    <br />
                </c:when>
                <c:otherwise>
                    <display:table name="customer.partyCreditLimits.list" id="partyCreditLimit" class="displaytag">
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

            <c:if test='${customer.billingAccounts.size > 0}'>
                <h3>Billing Accounts</h3>
                <display:table name="customer.billingAccounts.list" id="billingAccount" class="displaytag">
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
                <br />
            </c:if>

            <h3>Accounts Receivable Invoices</h3>
            <c:choose>
                <c:when test="${customer.invoicesTo.size == 0}">
                    No AR invoices were found.<br />
                    <br />
                </c:when>
                <c:otherwise>
                    <display:table name="customer.invoicesTo.list" id="invoice" class="displaytag">
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
                    <c:if test='${customer.invoicesToCount > 5}'>
                        <a href="TODO">More...</a> (<c:out value="${custoemr.invoicesToCount}" /> total)<br />
                    </c:if>
                </c:otherwise>
            </c:choose>
            <br />

            <c:set var="commonUrl" scope="request" value="Customer/CustomerPaymentMethod" />
            <c:set var="partyPaymentMethods" scope="request" value="${customer.partyPaymentMethods}" />
            <jsp:include page="../../include/partyPaymentMethods.jsp" />

            <c:if test='${customer.subscriptions.size > 0}'>
                <h3>Subscriptions</h3>
                <c:url var="addUrl" value="/action/Customer/SubscriptionAdd">
                    <c:param name="PartyName" value="${customer.partyName}" />
                </c:url>
                <a href="${addUrl}">Add Subscription.</a>
                <display:table name="customer.subscriptions.list" id="subscription" class="displaytag">
                    <display:column titleKey="columnTitle.name">
                        <c:url var="reviewUrl" value="/action/Subscription/Subscription/Review">
                            <c:param name="SubscriptionName" value="${subscription.subscriptionName}" />
                        </c:url>
                        <a href="${reviewUrl}"><c:out value="${subscription.subscriptionName}" /></a>
                    </display:column>
                    <display:column titleKey="columnTitle.type">
                        <c:url var="subscriptionTypeUrl" value="/action/Subscription/SubscriptionType/Review">
                            <c:param name="SubscriptionKindName" value="${subscription.subscriptionType.subscriptionKind.subscriptionKindName}" />
                            <c:param name="SubscriptionTypeName" value="${subscription.subscriptionType.subscriptionTypeName}" />
                        </c:url>
                        <a href="${subscriptionTypeUrl}"><c:out value="${subscription.subscriptionType.description}" /></a>
                    </display:column>
                    <display:column titleKey="columnTitle.startDateTime">
                        <c:out value="${subscription.startTime}" />
                    </display:column>
                    <display:column titleKey="columnTitle.endDateTime">
                        <c:out value="${subscription.endTime}" />
                    </display:column>
                    <display:column>
                        <c:url var="editUrl" value="/action/Customer/Customer/SubscriptionEdit">
                            <c:param name="PartyName" value="${customer.partyName}" />
                            <c:param name="SubscriptionName" value="${subscription.subscriptionName}" />
                        </c:url>
                        <a href="${editUrl}">Edit</a>
                        <c:url var="deleteUrl" value="/action/Customer/Customer/SubscriptionDelete">
                            <c:param name="PartyName" value="${customer.partyName}" />
                            <c:param name="SubscriptionName" value="${subscription.subscriptionName}" />
                        </c:url>
                        <a href="${deleteUrl}">Delete</a>
                    </display:column>
                    <et:hasSecurityRole securityRole="Event.List">
                        <display:column>
                            <c:url var="eventsUrl" value="/action/Core/Event/Main">
                                <c:param name="EntityRef" value="${subscription.entityInstance.entityRef}" />
                            </c:url>
                            <a href="${eventsUrl}">Events</a>
                        </display:column>
                    </et:hasSecurityRole>
                </display:table>
                <br />
            </c:if>

            <c:set var="commonUrl" scope="request" value="Customer/Customer" />
            <c:set var="comments" scope="request" value="${customer.comments.map['CUSTOMER_CUSTOMER_SERVICE']}" />
            <jsp:include page="../../include/partyComments.jsp" />

            <c:set var="comments" scope="request" value="${customer.comments.map['CUSTOMER_ORDER_ENTRY']}" />
            <jsp:include page="../../include/partyComments.jsp" />

            <c:set var="commonUrl" scope="request" value="Customer/CustomerContactMechanism" />
            <c:set var="partyContactMechanisms" scope="request" value="${customer.partyContactMechanisms}" />
            <jsp:include page="../../include/partyContactMechanisms.jsp" />
            
            <c:set var="commonUrl" scope="request" value="Customer/CustomerDocument" />
            <c:set var="partyDocuments" scope="request" value="${customer.partyDocuments}" />
            <jsp:include page="../../include/partyDocuments.jsp" />
            
            <c:if test='${customer.communicationEvents.size > 0}'>
                <h3>Communication Events</h3>
                <display:table name="customer.communicationEvents.list" id="communicationEvent" class="displaytag">
                    <display:column titleKey="columnTitle.name">
                        <c:url var="reviewUrl" value="/action/Communication/CommunicationEvent/Review">
                            <c:param name="CommunicationEventName" value="${communicationEvent.communicationEventName}" />
                        </c:url>
                        <a href="${reviewUrl}"><c:out value="${communicationEvent.communicationEventName}" /></a>
                    </display:column>
                    <display:column titleKey="columnTitle.type">
                        <c:out value="${communicationEvent.communicationEventType.description}" />
                    </display:column>
                    <display:column titleKey="columnTitle.source">
                        <c:url var="reviewUrl" value="/action/Configuration/CommunicationSource/Review">
                            <c:param name="CommunicationSourceName" value="${communicationEvent.communicationSource.communicationSourceName}" />
                        </c:url>
                        <a href="${reviewUrl}"><c:out value="${communicationEvent.communicationSource.description}" /></a>
                    </display:column>
                    <display:column titleKey="columnTitle.purpose">
                        <c:url var="reviewUrl" value="/action/Configuration/CommunicationEventPurpose/Review">
                            <c:param name="CommunicationEventPurposeName" value="${communicationEvent.communicationEventPurpose.communicationEventPurposeName}" />
                        </c:url>
                        <a href="${reviewUrl}"><c:out value="${communicationEvent.communicationEventPurpose.description}" /></a>
                    </display:column>
                    <display:column titleKey="columnTitle.description">
                        <c:out value="${communicationEvent.document.description}" />
                    </display:column>
                    <et:hasSecurityRole securityRole="Event.List">
                        <display:column>
                            <c:url var="eventsUrl" value="/action/Core/Event/Main">
                                <c:param name="EntityRef" value="${communicationEvent.entityInstance.entityRef}" />
                            </c:url>
                            <a href="${eventsUrl}">Events</a>
                        </display:column>
                    </et:hasSecurityRole>
                </display:table>
                <br />
            </c:if>

            <h3>Cancellation Policies</h3>
            <c:choose>
                <c:when test="${customer.partyCancellationPolicies.size == 0}">
                    No cancellation policies were found.<br />
                    <br />
                </c:when>
                <c:otherwise>
                    <display:table name="customer.partyCancellationPolicies.list" id="partyCancellationPolicy" class="displaytag">
                        <display:column titleKey="columnTitle.description">
                            <c:choose>
                                <c:when test="${includeCancellationPolicyUrl}">
                                    <c:url var="reviewUrl" value="/action/CancellationPolicy/CancellationPolicy/Review">
                                        <c:param name="CancellationKindName" value="${partyCancellationPolicy.cancellationPolicy.cancellationKind.cancellationKindName}" />
                                        <c:param name="CancellationPolicyName" value="${partyCancellationPolicy.cancellationPolicy.cancellationPolicyName}" />
                                    </c:url>
                                    <a href="${reviewUrl}"><c:out value="${partyCancellationPolicy.cancellationPolicy.description}" /></a>
                                </c:when>
                                <c:otherwise>
                                    <c:out value="${partyCancellationPolicy.cancellationPolicy.description}" />
                                </c:otherwise>
                            </c:choose>
                        </display:column>
                        <display:column titleKey="columnTitle.status">
                            <c:choose>
                                <c:when test="${includeWorkflowStepUrl}">
                                    <c:url var="reviewUrl" value="/action/Configuration/WorkflowStep/Review">
                                        <c:param name="WorkflowName" value="${partyCancellationPolicy.partyCancellationPolicyStatus.workflowStep.workflow.workflowName}" />
                                        <c:param name="WorkflowStepName" value="${partyCancellationPolicy.partyCancellationPolicyStatus.workflowStep.workflowStepName}" />
                                    </c:url>
                                    <a href="${reviewUrl}"><c:out value="${partyCancellationPolicy.partyCancellationPolicyStatus.workflowStep.description}" /></a>
                                </c:when>
                                <c:otherwise>
                                    <c:out value="${partyCancellationPolicy.partyCancellationPolicyStatus.workflowStep.description}" />
                                </c:otherwise>
                            </c:choose>
                        </display:column>
                    </display:table>
                </c:otherwise>
            </c:choose>
            <br />

            <h3>Return Policies</h3>
            <c:choose>
                <c:when test="${customer.partyReturnPolicies.size == 0}">
                    No return policies were found.<br />
                    <br />
                </c:when>
                <c:otherwise>
                    <display:table name="customer.partyReturnPolicies.list" id="partyReturnPolicy" class="displaytag">
                        <display:column titleKey="columnTitle.description">
                            <c:choose>
                                <c:when test="${includeReturnPolicyUrl}">
                                    <c:url var="reviewUrl" value="/action/ReturnPolicy/ReturnPolicy/Review">
                                        <c:param name="ReturnKindName" value="${partyReturnPolicy.returnPolicy.returnKind.returnKindName}" />
                                        <c:param name="ReturnPolicyName" value="${partyReturnPolicy.returnPolicy.returnPolicyName}" />
                                    </c:url>
                                    <a href="${reviewUrl}"><c:out value="${partyReturnPolicy.returnPolicy.description}" /></a>
                                </c:when>
                                <c:otherwise>
                                    <c:out value="${partyReturnPolicy.returnPolicy.description}" />
                                </c:otherwise>
                            </c:choose>
                        </display:column>
                        <display:column titleKey="columnTitle.status">
                            <c:choose>
                                <c:when test="${includeWorkflowStepUrl}">
                                    <c:url var="reviewUrl" value="/action/Configuration/WorkflowStep/Review">
                                        <c:param name="WorkflowName" value="${partyReturnPolicy.partyReturnPolicyStatus.workflowStep.workflow.workflowName}" />
                                        <c:param name="WorkflowStepName" value="${partyReturnPolicy.partyReturnPolicyStatus.workflowStep.workflowStepName}" />
                                    </c:url>
                                    <a href="${reviewUrl}"><c:out value="${partyReturnPolicy.partyReturnPolicyStatus.workflowStep.description}" /></a>
                                </c:when>
                                <c:otherwise>
                                    <c:out value="${partyReturnPolicy.partyReturnPolicyStatus.workflowStep.description}" />
                                </c:otherwise>
                            </c:choose>
                        </display:column>
                    </display:table>
                </c:otherwise>
            </c:choose>
            <br />

            <c:if test='${wishlists != null}'>
                <h3>Wishlists</h3>
                <display:table name="wishlists" id="wishlist" class="displaytag">
                    <display:column titleKey="columnTitle.name">
                        <c:url var="reviewUrl" value="/action/Customer/Customer/WishlistReview">
                            <c:param name="CustomerName" value="${customer.customerName}" />
                            <c:param name="WishlistName" value="${wishlist.wishlistName}" />
                        </c:url>
                        <a href="${reviewUrl}"><c:out value="${wishlist.wishlistName}" /></a>
                    </display:column>
                    <display:column titleKey="columnTitle.currency">
                        <c:url var="reviewUrl" value="/action/Accounting/Currency/Review">
                            <c:param name="CurrencyIsoName" value="${wishlist.currency.currencyIsoName}" />
                        </c:url>
                        <a href="${reviewUrl}"><c:out value="${wishlist.currency.description}" /></a>
                    </display:column>
                    <display:column titleKey="columnTitle.offerUse">
                        <c:url var="offerUseUrl" value="/action/Advertising/OfferUse/Review">
                            <c:param name="OfferName" value="${wishlist.offerUse.offer.offerName}" />
                            <c:param name="UseName" value="${wishlist.offerUse.use.useName}" />
                        </c:url>
                        <a href="${offerUseUrl}"><c:out value="${wishlist.offerUse.offer.offerName}" />:<c:out value="${wishlist.offerUse.use.useName}" /></a>
                    </display:column>
                    <display:column titleKey="columnTitle.wishlistType">
                        <c:url var="reviewUrl" value="/action/Wishlist/WishlistType/Review">
                            <c:param name="WishlistTypeName" value="${wishlist.wishlistType.wishlistTypeName}" />
                        </c:url>
                        <a href="${reviewUrl}"><c:out value="${wishlist.wishlistType.description}" /></a>
                    </display:column>
                    <et:hasSecurityRole securityRole="Event.List">
                        <display:column>
                            <c:url var="eventsUrl" value="/action/Core/Event/Main">
                                <c:param name="EntityRef" value="${wishlist.entityInstance.entityRef}" />
                            </c:url>
                            <a href="${eventsUrl}">Events</a>
                        </display:column>
                    </et:hasSecurityRole>
                </display:table>
                <br />
            </c:if>

            <c:set var="commonUrl" scope="request" value="Customer/CustomerContactList" />
            <c:set var="partyContactLists" scope="request" value="${customer.partyContactLists}" />
            <jsp:include page="../../include/partyContactLists.jsp" />

            <c:set var="commonUrl" scope="request" value="Customer/CustomerCarrier" />
            <c:set var="partyCarriers" scope="request" value="${customer.partyCarriers}" />
            <jsp:include page="../../include/partyCarriers.jsp" />

            <c:set var="commonUrl" scope="request" value="Customer/CustomerCarrierAccount" />
            <c:set var="partyCarrierAccounts" scope="request" value="${customer.partyCarrierAccounts}" />
            <jsp:include page="../../include/partyCarrierAccounts.jsp" />
            
            <c:set var="commonUrl" scope="request" value="Customer/CustomerAlias" />
            <c:set var="partyAliases" scope="request" value="${customer.partyAliases}" />
            <c:set var="securityRoleGroupNamePrefix" scope="request" value="Customer" />
            <jsp:include page="../../include/partyAliases.jsp" />

            <c:set var="tagScopes" scope="request" value="${customer.tagScopes}" />
            <c:set var="entityAttributeGroups" scope="request" value="${customer.entityAttributeGroups}" />
            <c:set var="entityInstance" scope="request" value="${customer.entityInstance}" />
            <c:url var="returnUrl" scope="request" value="/../action/Customer/Customer/Review">
                <c:param name="PartyName" value="${customer.partyName}" />
            </c:url>
            <jsp:include page="../../include/tagScopes.jsp" />
            <jsp:include page="../../include/entityAttributeGroups.jsp" />
            <jsp:include page="../../include/entityInstance.jsp" />
        </div>
    <%@ include file="../../include/body-end-b.jsp" %>
</html>
