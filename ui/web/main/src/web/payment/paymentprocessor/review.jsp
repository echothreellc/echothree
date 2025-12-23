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
        <title>Review (<c:out value="${paymentProcessor.paymentProcessorName}" />)</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Payment/Main" />">Payment</a> &gt;&gt;
                <a href="<c:url value="/action/Payment/PaymentProcessor/Main" />">Payment Processors</a> &gt;&gt;
                Review (<c:out value="${paymentProcessor.paymentProcessorName}" />)
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="PaymentProcessorType.Review:Event.List" />
            <et:hasSecurityRole securityRole="PaymentProcessorType.Review" var="includePaymentProcessorTypeReviewUrl" />
            <p><font size="+2"><b><c:out value="${paymentProcessor.description}" /></b></font></p>
            <br />
            Payment Processor Name: ${paymentProcessor.paymentProcessorName}<br />
            <br />
            Payment Processor Type:
            <c:choose>
                <c:when test="${includePaymentProcessorTypeReviewUrl}">
                    <c:url var="paymentProcessorTypeUrl" value="/action/Payment/PaymentProcessorType/Review">
                        <c:param name="PaymentProcessorTypeName" value="${paymentProcessor.paymentProcessorType.paymentProcessorTypeName}" />
                    </c:url>
                    <a href="${paymentProcessorTypeUrl}"><c:out value="${paymentProcessor.paymentProcessorType.description}" /></a>
                </c:when>
                <c:otherwise>
                    <c:out value="${paymentProcessor.paymentProcessorType.description}" />
                </c:otherwise>
            </c:choose>
            <br />
            <br />
            <br />
            <c:set var="comments" scope="request" value="${paymentProcessor.comments.map['PAYMENT_PROCESSOR']}" />
            <h2><c:out value="${comments.commentType.description}" /> Comments</h2>
            <c:url var="addUrl" value="/action/Payment/PaymentProcessor/CommentAdd">
                <c:param name="PaymentProcessorName" value="${paymentProcessor.paymentProcessorName}" />
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
                            <c:url var="editUrl" value="/action/Payment/PaymentProcessor/CommentEdit">
                                <c:param name="PaymentProcessorName" value="${paymentProcessor.paymentProcessorName}" />
                                <c:param name="CommentName" value="${comment.commentName}" />
                            </c:url>
                            <a href="${editUrl}">Edit</a>
                            <c:url var="deleteUrl" value="/action/Payment/PaymentProcessor/CommentDelete">
                                <c:param name="PaymentProcessorName" value="${paymentProcessor.paymentProcessorName}" />
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
            <c:set var="entityInstance" scope="request" value="${paymentProcessor.entityInstance}" />
            <jsp:include page="../../include/entityInstance.jsp" />
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
