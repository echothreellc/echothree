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
        <title>Customer Payment Method (<c:out value="${partyPaymentMethod.partyPaymentMethodName}" />)</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Customer/Main" />">Customers</a> &gt;&gt;
                <a href="<c:url value="/action/Customer/Customer/Main" />">Search</a> &gt;&gt;
                <et:countCustomerResults searchTypeName="ORDER_ENTRY" countVar="customerResultsCount" commandResultVar="countCustomerResultsCommandResult" logErrors="false" />
                <c:if test="${customerResultsCount > 0}">
                    <a href="<c:url value="/action/Customer/Customer/Result" />"><fmt:message key="navigation.results" /></a> &gt;&gt;
                </c:if>
                <c:url var="reviewUrl" value="/action/Customer/Customer/Review">
                    <c:param name="PartyName" value="${customer.partyName}" />
                </c:url>
                <a href="${reviewUrl}">Review (<c:out value="${customer.customerName}" />)</a> &gt;&gt;
                <c:url var="customerPaymentMethodsUrl" value="/action/Customer/CustomerPaymentMethod/Main">
                    <c:param name="PartyName" value="${customer.partyName}" />
                </c:url>
                <a href="${customerPaymentMethodsUrl}">Payment Methods</a> &gt;&gt;
                Customer Payment Method (<c:out value="${partyPaymentMethod.partyPaymentMethodName}" />)
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="Event.List" />
            <p><font size="+2"><b>
                <c:choose>
                    <c:when test="${partyPaymentMethod.description == null}">
                        ${partyPaymentMethod.partyPaymentMethodName}
                    </c:when>
                    <c:otherwise>
                        <c:out value="${partyPaymentMethod.description}" />
                    </c:otherwise>
                </c:choose>
            </b></font></p>
            <br />
            Customer Payment Method Name: ${partyPaymentMethod.partyPaymentMethodName}<br />
            <br />
            <c:url var="paymentMethodUrl" value="/action/Payment/PaymentMethod/Review">
                <c:param name="PaymentMethodName" value="${partyPaymentMethod.paymentMethod.paymentMethodName}" />
            </c:url>
            Payment Method: <a href="${paymentMethodUrl}"><c:out value="${partyPaymentMethod.paymentMethod.description}" /></a><br />
            <br />
            <c:if test='${partyPaymentMethod.partyPaymentMethodStatus != null}'>
                Status: <c:out value="${partyPaymentMethod.partyPaymentMethodStatus.workflowStep.description}" /><br />
                <br />
            </c:if>
            <c:choose>
                <c:when test="${partyPaymentMethod.paymentMethod.paymentMethodType.paymentMethodTypeName == 'CREDIT_CARD'}">
                    Card Number ${partyPaymentMethod.number}<br />
                    Expiration: ${partyPaymentMethod.expirationMonth}/${partyPaymentMethod.expirationYear}<br />
                    <br />
                    <c:if test="${partyPaymentMethod.firstName != null || partyPaymentMethod.middleName != null || partyPaymentMethod.lastName != null}">
                        <c:out value="${partyPaymentMethod.personalTitle.description}" />
                        <c:out value="${partyPaymentMethod.firstName}" />
                        <c:out value="${partyPaymentMethod.middleName}" />
                        <c:out value="${partyPaymentMethod.lastName}" />
                        <c:out value="${partyPaymentMethod.nameSuffix.description}" /><br />
                    </c:if>
                    <c:if test="${partyPaymentMethod.name != null}">
                        <c:out value="${partyPaymentMethod.name}" /><br />
                    </c:if>
                    <!-- TODO: Issuer Contact Mechanism -->
                    <!-- TODO: Billing Contact Mechanism -->
                    <br />
                </c:when>
            </c:choose>
            <br />
            <c:if test='${partyPaymentMethod.partyPaymentMethodContactMechanisms.size > 0}'>
                <h2>Contact Mechanisms</h2>
                <display:table name="partyPaymentMethod.partyPaymentMethodContactMechanisms.list" id="partyPaymentMethodContactMechanism" class="displaytag">
                    <display:column property="partyContactMechanismPurpose.partyContactMechanism.contactMechanism.contactMechanismName" titleKey="columnTitle.name" />
                    <display:column titleKey="columnTitle.status">
                        <c:out value="${partyPaymentMethodContactMechanism.partyPaymentMethodContactMechanismStatus.workflowStep.description}" />
                    </display:column>
                    <display:column titleKey="columnTitle.valid">
                        <c:choose>
                            <c:when test='${partyPaymentMethodContactMechanism.partyContactMechanismPurpose.partyContactMechanism.contactMechanism.contactMechanismType.contactMechanismTypeName == "EMAIL_ADDRESS"}'>
                                <c:out value="${partyPaymentMethodContactMechanism.partyContactMechanismPurpose.partyContactMechanism.contactMechanism.contactEmailAddress.emailAddressStatus.workflowStep.description}" /><br/>
                                <c:out value="${partyPaymentMethodContactMechanism.partyContactMechanismPurpose.partyContactMechanism.contactMechanism.contactEmailAddress.emailAddressVerification.workflowStep.description}" />
                            </c:when>
                            <c:when test='${partyPaymentMethodContactMechanism.partyContactMechanismPurpose.partyContactMechanism.contactMechanism.contactMechanismType.contactMechanismTypeName == "POSTAL_ADDRESS"}'>
                                <c:out value="${partyPaymentMethodContactMechanism.partyContactMechanismPurpose.partyContactMechanism.contactMechanism.contactPostalAddress.postalAddressStatus.workflowStep.description}" />
                            </c:when>
                            <c:when test='${partyPaymentMethodContactMechanism.partyContactMechanismPurpose.partyContactMechanism.contactMechanism.contactMechanismType.contactMechanismTypeName == "TELECOM_ADDRESS"}'>
                                <c:out value="${partyPaymentMethodContactMechanism.partyContactMechanismPurpose.partyContactMechanism.contactMechanism.contactTelephone.telephoneStatus.workflowStep.description}" /><br/>
                            </c:when>
                            <c:when test='${partyPaymentMethodContactMechanism.partyContactMechanismPurpose.partyContactMechanism.contactMechanism.contactMechanismType.contactMechanismTypeName == "WEB_ADDRESS"}'>
                                <c:out value="${partyPaymentMethodContactMechanism.partyContactMechanismPurpose.partyContactMechanism.contactMechanism.contactWebAddress.webAddressStatus.workflowStep.description}" /><br/>
                            </c:when>
                        </c:choose>
                    </display:column>
                    <display:column titleKey="columnTitle.type">
                        <c:out value="${partyPaymentMethodContactMechanism.partyContactMechanismPurpose.partyContactMechanism.contactMechanism.contactMechanismType.description}" />
                    </display:column>
                    <display:column>
                        <c:if test="${partyPaymentMethodContactMechanism.partyContactMechanismPurpose.partyContactMechanism.description != null}">
                            Description: <c:out value="${partyPaymentMethodContactMechanism.partyContactMechanismPurpose.partyContactMechanism.description}" /><br />
                        </c:if>
                        <c:choose>
                            <c:when test='${partyPaymentMethodContactMechanism.partyContactMechanismPurpose.partyContactMechanism.contactMechanism.contactMechanismType.contactMechanismTypeName == "EMAIL_ADDRESS"}'>
                                E-mail Address: <a href="mailto:<c:out value="${partyPaymentMethodContactMechanism.partyContactMechanismPurpose.partyContactMechanism.contactMechanism.contactEmailAddress.emailAddress}" />"><c:out value="${partyPaymentMethodContactMechanism.partyContactMechanismPurpose.partyContactMechanism.contactMechanism.contactEmailAddress.emailAddress}" /></a>
                            </c:when>
                            <c:when test='${partyPaymentMethodContactMechanism.partyContactMechanismPurpose.partyContactMechanism.contactMechanism.contactMechanismType.contactMechanismTypeName == "POSTAL_ADDRESS"}'>
                                <c:out value="${partyPaymentMethodContactMechanism.partyContactMechanismPurpose.partyContactMechanism.contactMechanism.contactPostalAddress.personalTitle.description}" />
                                <c:out value="${partyPaymentMethodContactMechanism.partyContactMechanismPurpose.partyContactMechanism.contactMechanism.contactPostalAddress.firstName}" />
                                <c:out value="${partyPaymentMethodContactMechanism.partyContactMechanismPurpose.partyContactMechanism.contactMechanism.contactPostalAddress.middleName}" />
                                <c:out value="${partyPaymentMethodContactMechanism.partyContactMechanismPurpose.partyContactMechanism.contactMechanism.contactPostalAddress.lastName}" />
                                <c:out value="${partyPaymentMethodContactMechanism.partyContactMechanismPurpose.partyContactMechanism.contactMechanism.contactPostalAddress.nameSuffix.description}" /><br />
                                <c:if test='${partyPaymentMethodContactMechanism.partyContactMechanismPurpose.partyContactMechanism.contactMechanism.contactPostalAddress.companyName != null}'>
                                    <c:out value="${partyPaymentMethodContactMechanism.partyContactMechanismPurpose.partyContactMechanism.contactMechanism.contactPostalAddress.companyName}" /><br />
                                </c:if>
                                <c:out value="${partyPaymentMethodContactMechanism.partyContactMechanismPurpose.partyContactMechanism.contactMechanism.contactPostalAddress.address1}" /><br />
                                <c:if test='${partyPaymentMethodContactMechanism.partyContactMechanismPurpose.partyContactMechanism.contactMechanism.contactPostalAddress.address2 != null}'>
                                    <c:out value="${partyPaymentMethodContactMechanism.partyContactMechanismPurpose.partyContactMechanism.contactMechanism.contactPostalAddress.address2}" /><br />
                                </c:if>
                                <c:if test='${partyPaymentMethodContactMechanism.partyContactMechanismPurpose.partyContactMechanism.contactMechanism.contactPostalAddress.address3 != null}'>
                                    <c:out value="${partyPaymentMethodContactMechanism.partyContactMechanismPurpose.partyContactMechanism.contactMechanism.contactPostalAddress.address3}" /><br />
                                </c:if>
                                <c:out value="${partyPaymentMethodContactMechanism.partyContactMechanismPurpose.partyContactMechanism.contactMechanism.contactPostalAddress.city}" />,
                                <c:out value="${partyPaymentMethodContactMechanism.partyContactMechanismPurpose.partyContactMechanism.contactMechanism.contactPostalAddress.state}" />
                                <c:out value="${partyPaymentMethodContactMechanism.partyContactMechanismPurpose.partyContactMechanism.contactMechanism.contactPostalAddress.postalCode}" /><br />
                                <!-- <c:out value="${partyPaymentMethodContactMechanism.partyContactMechanismPurpose.partyContactMechanism.contactMechanism.contactPostalAddress.cityGeoCode.description}" /><br /> -->
                                <!-- <c:out value="${partyPaymentMethodContactMechanism.partyContactMechanismPurpose.partyContactMechanism.contactMechanism.contactPostalAddress.countyGeoCode.description}" /><br /> -->
                                <!-- <c:out value="${partyPaymentMethodContactMechanism.partyContactMechanismPurpose.partyContactMechanism.contactMechanism.contactPostalAddress.stateGeoCode.description}" /><br /> -->
                                <!-- <c:out value="${partyPaymentMethodContactMechanism.partyContactMechanismPurpose.partyContactMechanism.contactMechanism.contactPostalAddress.postalCodeGeoCode.description}" /><br /> -->
                                Country: <c:out value="${partyPaymentMethodContactMechanism.partyContactMechanismPurpose.partyContactMechanism.contactMechanism.contactPostalAddress.countryGeoCode.description}" /><br />
                                Commercial:
                                <c:choose>
                                    <c:when test="${partyPaymentMethodContactMechanism.partyContactMechanismPurpose.partyContactMechanism.contactMechanism.contactPostalAddress.isCommercial}">
                                        <fmt:message key="phrase.yes" />
                                    </c:when>
                                    <c:otherwise>
                                        <fmt:message key="phrase.no" />
                                    </c:otherwise>
                                </c:choose>
                            </c:when>
                            <c:when test='${partyPaymentMethodContactMechanism.partyContactMechanismPurpose.partyContactMechanism.contactMechanism.contactMechanismType.contactMechanismTypeName == "TELECOM_ADDRESS"}'>
                                Country: <c:out value="${partyPaymentMethodContactMechanism.partyContactMechanismPurpose.partyContactMechanism.contactMechanism.contactTelephone.countryGeoCode.description}" /><br />
                                <c:if test='${partyPaymentMethodContactMechanism.partyContactMechanismPurpose.partyContactMechanism.contactMechanism.contactTelephone.areaCode != null}'>
                                    Area Code: <c:out value="${partyPaymentMethodContactMechanism.partyContactMechanismPurpose.partyContactMechanism.contactMechanism.contactTelephone.areaCode}" /><br />
                                </c:if>
                                Number: <c:out value="${partyPaymentMethodContactMechanism.partyContactMechanismPurpose.partyContactMechanism.contactMechanism.contactTelephone.telephoneNumber}" />
                                <c:if test='${partyPaymentMethodContactMechanism.partyContactMechanismPurpose.partyContactMechanism.contactMechanism.contactTelephone.telephoneExtension != null}'>
                                    <br />Extension <c:out value="${partyPaymentMethodContactMechanism.partyContactMechanismPurpose.partyContactMechanism.contactMechanism.contactTelephone.telephoneExtension}" />
                                </c:if>
                            </c:when>
                            <c:when test='${partyPaymentMethodContactMechanism.partyContactMechanismPurpose.partyContactMechanism.contactMechanism.contactMechanismType.contactMechanismTypeName == "WEB_ADDRESS"}'>
                                Web Address: <a target="_blank" href="${partyPaymentMethodContactMechanism.partyContactMechanismPurpose.partyContactMechanism.contactMechanism.contactWebAddress.url}"><c:out value="${partyPaymentMethodContactMechanism.partyContactMechanismPurpose.partyContactMechanism.contactMechanism.contactWebAddress.url}" /></a>
                            </c:when>
                            <c:when test='${partyPaymentMethodContactMechanism.partyContactMechanismPurpose.partyContactMechanism.contactMechanism.contactMechanismType.contactMechanismTypeName == "INET_4"}'>
                                IPv4 Address: <c:out value="${partyPaymentMethodContactMechanism.partyContactMechanismPurpose.partyContactMechanism.contactMechanism.contactInet4Address.inet4Address}" />
                            </c:when>
                        </c:choose>
                    </display:column>
                    <et:hasSecurityRole securityRole="Event.List">
                        <display:column>
                            <c:url var="eventsUrl" value="/action/Core/Event/Main">
                                <c:param name="EntityRef" value="${partyPaymentMethodContactMechanism.partyContactMechanismPurpose.partyContactMechanism.contactMechanism.entityInstance.entityRef}" />
                            </c:url>
                            <a href="${eventsUrl}">Events</a>
                        </display:column>
                    </et:hasSecurityRole>
                </display:table>
                <br />
            </c:if>
            <br />
            <c:set var="comments" scope="request" value="${partyPaymentMethod.comments.map['PARTY_PAYMENT_METHOD']}" />
            <h2><c:out value="${comments.commentType.description}" /> Comments</h2>
            <c:url var="addUrl" value="/action/Customer/CustomerPaymentMethod/CommentAdd">
                <c:param name="PartyName" value="${partyPaymentMethod.party.partyName}" />
                <c:param name="PartyPaymentMethodName" value="${partyPaymentMethod.partyPaymentMethodName}" />
            </c:url>
            <p><a href="${addUrl}">Add Comment.</a></p>
            <c:choose>
                <c:when test='${comments.size == 0}'>
                    <br />
                </c:when>
                <c:otherwise>
                    <display:table name="comments.list" id="comment" class="displaytag">
                        <display:column titleKey="columnTitle.dateTime">
                            <c:out value="${comment.entityInstance.entityTime.createdTime}" />
                        </display:column>
                        <display:column titleKey="columnTitle.comment">
                            <c:if test='${comment.description != null}'>
                                <b><c:out value="${comment.description}" /></b><br />
                            </c:if>
                            <et:out value="${comment.clob}" mimeTypeName="${comment.mimeType.mimeTypeName}" />
                        </display:column>
                        <display:column property="commentedByEntityInstance.description" titleKey="columnTitle.enteredBy" />
                        <display:column>
                            <c:url var="editUrl" value="/action/Customer/CustomerPaymentMethod/CommentEdit">
                                <c:param name="PartyName" value="${partyPaymentMethod.party.partyName}" />
                                <c:param name="PartyPaymentMethodName" value="${partyPaymentMethod.partyPaymentMethodName}" />
                                <c:param name="CommentName" value="${comment.commentName}" />
                            </c:url>
                            <a href="${editUrl}">Edit</a>
                            <c:url var="deleteUrl" value="/action/Customer/CustomerPaymentMethod/CommentDelete">
                                <c:param name="PartyName" value="${partyPaymentMethod.party.partyName}" />
                                <c:param name="PartyPaymentMethodName" value="${partyPaymentMethod.partyPaymentMethodName}" />
                                <c:param name="CommentName" value="${comment.commentName}" />
                            </c:url>
                            <a href="${deleteUrl}">Delete</a>
                        </display:column>
                        <et:hasSecurityRole securityRole="Event.List">
                            <display:column>
                                <c:url var="eventsUrl" value="/action/Core/Event/Main">
                                    <c:param name="EntityRef" value="${comment.entityInstance.entityRef}" />
                                </c:url>
                                <a href="${eventsUrl}">Events</a>
                            </display:column>
                        </et:hasSecurityRole>
                    </display:table>
                </c:otherwise>
            </c:choose>
            <br />
            <br />
            <br />
            Created: <c:out value="${partyPaymentMethod.entityInstance.entityTime.createdTime}" /><br />
            Modified: <c:out value="${partyPaymentMethod.entityInstance.entityTime.modifiedTime}" /><br />
            <c:url var="eventsUrl" value="/action/Core/Event/Main">
                <c:param name="EntityRef" value="${partyPaymentMethod.entityInstance.entityRef}" />
            </c:url>
            <a href="${eventsUrl}">Events</a>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
