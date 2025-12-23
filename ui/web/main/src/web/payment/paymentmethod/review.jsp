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
        <title>Review (<c:out value="${paymentMethod.paymentMethodName}" />)</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Payment/Main" />">Payment</a> &gt;&gt;
                <a href="<c:url value="/action/Payment/PaymentMethod/Main" />">Payment Methods</a> &gt;&gt;
                Review (<c:out value="${paymentMethod.paymentMethodName}" />)
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="PaymentMethodType.Review:PaymentProcessor.Review:Event.List" />
            <et:hasSecurityRole securityRole="PaymentMethodType.Review" var="includePaymentMethodTypeReviewUrl" />
            <et:hasSecurityRole securityRole="PaymentProcessor.Review" var="includePaymentProcessorReviewUrl" />
            <p><font size="+2"><b><c:out value="${paymentMethod.description}" /></b></font></p>
            <br />
            Payment Method Name: <c:out value="${paymentMethod.paymentMethodName}" /><br />
            <br />
            Payment Method Type:
            <c:choose>
                <c:when test="${includePaymentMethodTypeReviewUrl}">
                    <c:url var="paymentMethodTypeUrl" value="/action/Payment/PaymentMethodType/Review">
                        <c:param name="PaymentMethodTypeName" value="${paymentMethod.paymentMethodType.paymentMethodTypeName}" />
                    </c:url>
                    <a href="${paymentMethodTypeUrl}"><c:out value="${paymentMethod.paymentMethodType.description}" /></a>
                </c:when>
                <c:otherwise>
                    <c:out value="${paymentMethod.paymentMethodType.description}" />
                </c:otherwise>
            </c:choose>
            <br />
            <br />
            <c:if test='${paymentMethod.paymentProcessor != null}'>
                Payment Processor:
                <c:choose>
                    <c:when test="${includePaymentProcessorReviewUrl}">
                        <c:url var="paymentProcessorUrl" value="/action/Payment/PaymentProcessor/Review">
                            <c:param name="PaymentProcessorName" value="${paymentMethod.paymentProcessor.paymentProcessorName}" />
                        </c:url>
                        <a href="${paymentProcessorUrl}"><c:out value="${paymentMethod.paymentProcessor.description}" /></a>
                    </c:when>
                    <c:otherwise>
                        <c:out value="${paymentMethod.paymentProcessor.description}" />
                    </c:otherwise>
                </c:choose>
                <br />
                <br />
            </c:if>
            <c:choose>
                <c:when test="${paymentMethod.paymentMethodType.paymentMethodTypeName == 'CHECK'}">
                    Hold Days: ${paymentMethod.holdDays}
                    <br />
                </c:when>
                <c:when test="${paymentMethod.paymentMethodType.paymentMethodTypeName == 'CREDIT_CARD'}">
                    Request Name On Card:
                    <c:choose>
                        <c:when test="${paymentMethod.requestNameOnCard}">
                            <fmt:message key="phrase.yes" />
                        </c:when>
                        <c:otherwise>
                            <fmt:message key="phrase.no" />
                        </c:otherwise>
                    </c:choose>
                    <br />
                    Require Name On Card:
                    <c:choose>
                        <c:when test="${paymentMethod.requireNameOnCard}">
                            <fmt:message key="phrase.yes" />
                        </c:when>
                        <c:otherwise>
                            <fmt:message key="phrase.no" />
                        </c:otherwise>
                    </c:choose>
                    <br />
                    <br />
                    Check Card Number:
                    <c:choose>
                        <c:when test="${paymentMethod.checkCardNumber}">
                            <fmt:message key="phrase.yes" />
                        </c:when>
                        <c:otherwise>
                            <fmt:message key="phrase.no" />
                        </c:otherwise>
                    </c:choose>
                    <br />
                    <br />
                    Request Expiration Date:
                    <c:choose>
                        <c:when test="${paymentMethod.requestExpirationDate}">
                            <fmt:message key="phrase.yes" />
                        </c:when>
                        <c:otherwise>
                            <fmt:message key="phrase.no" />
                        </c:otherwise>
                    </c:choose>
                    <br />
                    Require Expiration Date:
                    <c:choose>
                        <c:when test="${paymentMethod.requireExpirationDate}">
                            <fmt:message key="phrase.yes" />
                        </c:when>
                        <c:otherwise>
                            <fmt:message key="phrase.no" />
                        </c:otherwise>
                    </c:choose>
                    <br />
                    Check Expiration Date:
                    <c:choose>
                        <c:when test="${paymentMethod.checkExpirationDate}">
                            <fmt:message key="phrase.yes" />
                        </c:when>
                        <c:otherwise>
                            <fmt:message key="phrase.no" />
                        </c:otherwise>
                    </c:choose>
                    <br />
                    <br />
                    Request Security Code:
                    <c:choose>
                        <c:when test="${paymentMethod.requestSecurityCode}">
                            <fmt:message key="phrase.yes" />
                        </c:when>
                        <c:otherwise>
                            <fmt:message key="phrase.no" />
                        </c:otherwise>
                    </c:choose>
                    <br />
                    Require Security Code:
                    <c:choose>
                        <c:when test="${paymentMethod.requireSecurityCode}">
                            <fmt:message key="phrase.yes" />
                        </c:when>
                        <c:otherwise>
                            <fmt:message key="phrase.no" />
                        </c:otherwise>
                    </c:choose>
                    <br />
                    <br />
                    Card Number Validation Pattern: <c:out value="${paymentMethod.cardNumberValidationPattern}" /><br />
                    Security Code Validation Pattern: <c:out value="${paymentMethod.securityCodeValidationPattern}" /><br />
                    <br />
                    Retain Credit Card:
                    <c:choose>
                        <c:when test="${paymentMethod.retainCreditCard}">
                            <fmt:message key="phrase.yes" />
                        </c:when>
                        <c:otherwise>
                            <fmt:message key="phrase.no" />
                        </c:otherwise>
                    </c:choose>
                    <br />
                    Retain Security Code:
                    <c:choose>
                        <c:when test="${paymentMethod.retainSecurityCode}">
                            <fmt:message key="phrase.yes" />
                        </c:when>
                        <c:otherwise>
                            <fmt:message key="phrase.no" />
                        </c:otherwise>
                    </c:choose>
                    <br />
                    <br />
                    Request Billing:
                    <c:choose>
                        <c:when test="${paymentMethod.requestBilling}">
                            <fmt:message key="phrase.yes" />
                        </c:when>
                        <c:otherwise>
                            <fmt:message key="phrase.no" />
                        </c:otherwise>
                    </c:choose>
                    <br />
                    Require Billing:
                    <c:choose>
                        <c:when test="${paymentMethod.requireBilling}">
                            <fmt:message key="phrase.yes" />
                        </c:when>
                        <c:otherwise>
                            <fmt:message key="phrase.no" />
                        </c:otherwise>
                    </c:choose>
                    <br />
                    <br />
                    Request Issuer:
                    <c:choose>
                        <c:when test="${paymentMethod.requestIssuer}">
                            <fmt:message key="phrase.yes" />
                        </c:when>
                        <c:otherwise>
                            <fmt:message key="phrase.no" />
                        </c:otherwise>
                    </c:choose>
                    <br />
                    Require Issuer:
                    <c:choose>
                        <c:when test="${paymentMethod.requireIssuer}">
                            <fmt:message key="phrase.yes" />
                        </c:when>
                        <c:otherwise>
                            <fmt:message key="phrase.no" />
                        </c:otherwise>
                    </c:choose>
                    <br />
                </c:when>
            </c:choose>
            <br />
            <br />
            <c:set var="comments" scope="request" value="${paymentMethod.comments.map['PAYMENT_METHOD']}" />
            <h2><c:out value="${comments.commentType.description}" /> Comments</h2>
            <c:url var="addUrl" value="/action/Payment/PaymentMethod/CommentAdd">
                <c:param name="PaymentMethodName" value="${paymentMethod.paymentMethodName}" />
            </c:url>
            <p><a href="${addUrl}">Add <c:out value="${comments.commentType.description}" /> Comment.</a></p>
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
                            <c:url var="editUrl" value="/action/Payment/PaymentMethod/CommentEdit">
                                <c:param name="PaymentMethodName" value="${paymentMethod.paymentMethodName}" />
                                <c:param name="CommentName" value="${comment.commentName}" />
                            </c:url>
                            <a href="${editUrl}">Edit</a>
                            <c:url var="deleteUrl" value="/action/Payment/PaymentMethod/CommentDelete">
                                <c:param name="PaymentMethodName" value="${paymentMethod.paymentMethodName}" />
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
            <c:set var="entityAttributeGroups" scope="request" value="${paymentMethod.entityAttributeGroups}" />
            <c:set var="entityInstance" scope="request" value="${paymentMethod.entityInstance}" />
            <c:url var="returnUrl" scope="request" value="/../action/Payment/PaymentMethod/Review">
                <c:param name="PaymentMethodName" value="${paymentMethod.paymentMethodName}" />
            </c:url>
            <jsp:include page="../../include/entityAttributeGroups.jsp" />
            <jsp:include page="../../include/entityInstance.jsp" />
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
