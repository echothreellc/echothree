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
        <title>Payment Processors</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Payment/Main" />">Payment</a> &gt;&gt;
                Payment Processors
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="Event.List:PaymentProcessorType.Review:PaymentProcessor.Create:PaymentProcessor.Edit:PaymentProcessor.Delete:PaymentProcessor.Review:PaymentProcessor.Description" />
            <et:hasSecurityRole securityRole="PaymentProcessor.Create">
                <p><a href="<c:url value="/action/Payment/PaymentProcessor/Add/Step1" />">Add Payment Processor.</a></p>
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRole="PaymentProcessor.Review" var="includeReviewUrl" />
            <et:hasSecurityRole securityRole="PaymentProcessorType.Review" var="includePaymentProcessorTypeReviewUrl" />
            <display:table name="paymentProcessors" id="paymentProcessor" class="displaytag">
                <display:column titleKey="columnTitle.name">
                    <c:choose>
                        <c:when test="${includeReviewUrl}">
                            <c:url var="reviewUrl" value="/action/Payment/PaymentProcessor/Review">
                                <c:param name="PaymentProcessorName" value="${paymentProcessor.paymentProcessorName}" />
                            </c:url>
                            <a href="${reviewUrl}"><c:out value="${paymentProcessor.paymentProcessorName}" /></a>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${paymentProcessor.paymentProcessorName}" />
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column titleKey="columnTitle.description">
                    <c:out value="${paymentProcessor.description}" />
                </display:column>
                <display:column titleKey="columnTitle.paymentProcessorType">
                    <c:choose>
                        <c:when test="${includePaymentProcessorTypeReviewUrl}">
                            <c:url var="reviewUrl" value="/action/Payment/PaymentProcessorType/Review">
                                <c:param name="PaymentProcessorTypeName" value="${paymentProcessor.paymentProcessorType.paymentProcessorTypeName}" />
                            </c:url>
                            <a href="${reviewUrl}"><c:out value="${paymentProcessor.paymentProcessorType.description}" /></a>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${paymentProcessor.paymentProcessorType.description}" />
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column property="sortOrder" titleKey="columnTitle.sortOrder" />
                <display:column titleKey="columnTitle.default">
                    <c:choose>
                        <c:when test="${paymentProcessor.isDefault}">
                            Default
                        </c:when>
                        <c:otherwise>
                            <et:hasSecurityRole securityRole="PaymentProcessor.Edit">
                                <c:url var="setDefaultUrl" value="/action/Payment/PaymentProcessor/SetDefault">
                                    <c:param name="PaymentProcessorName" value="${paymentProcessor.paymentProcessorName}" />
                                </c:url>
                                <a href="${setDefaultUrl}">Set Default</a>
                            </et:hasSecurityRole>
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <et:hasSecurityRole securityRoles="PaymentProcessor.Edit:PaymentProcessor.Description:PaymentProcessor.Delete">
                    <display:column>
                        <et:hasSecurityRole securityRole="PaymentProcessor.Edit">
                            <c:url var="editUrl" value="/action/Payment/PaymentProcessor/Edit">
                                <c:param name="OriginalPaymentProcessorName" value="${paymentProcessor.paymentProcessorName}" />
                            </c:url>
                            <a href="${editUrl}">Edit</a>
                        </et:hasSecurityRole>
                        <et:hasSecurityRole securityRole="PaymentProcessor.Description">
                            <c:url var="descriptionsUrl" value="/action/Payment/PaymentProcessor/Description">
                                <c:param name="PaymentProcessorName" value="${paymentProcessor.paymentProcessorName}" />
                            </c:url>
                            <a href="${descriptionsUrl}">Descriptions</a>
                        </et:hasSecurityRole>
                        <et:hasSecurityRole securityRole="PaymentProcessor.Delete">
                            <c:url var="deleteUrl" value="/action/Payment/PaymentProcessor/Delete">
                                <c:param name="PaymentProcessorName" value="${paymentProcessor.paymentProcessorName}" />
                            </c:url>
                            <a href="${deleteUrl}">Delete</a>
                        </et:hasSecurityRole>
                    </display:column>
                </et:hasSecurityRole>
                <et:hasSecurityRole securityRole="Event.List">
                    <display:column>
                        <c:url var="eventsUrl" value="/action/Core/Event/Main">
                            <c:param name="EntityRef" value="${paymentProcessor.entityInstance.entityRef}" />
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
