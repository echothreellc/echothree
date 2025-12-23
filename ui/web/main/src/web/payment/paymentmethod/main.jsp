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
        <title>Payment Methods</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Payment/Main" />">Payment</a> &gt;&gt;
                Payment Methods
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="Event.List:PaymentMethodType.Review:PaymentProcessor.Review:PaymentMethod.Create:PaymentMethod.Edit:PaymentMethod.Delete:PaymentMethod.Review:PaymentMethod.Description" />
            <et:hasSecurityRole securityRole="PaymentMethod.Create">
                <p><a href="<c:url value="/action/Payment/PaymentMethod/Add/Step1" />">Add Payment Method.</a></p>
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRole="PaymentMethod.Review" var="includeReviewUrl" />
            <et:hasSecurityRole securityRole="PaymentMethodType.Review" var="includePaymentMethodTypeReviewUrl" />
            <et:hasSecurityRole securityRole="PaymentProcessor.Review" var="includePaymentProcessorReviewUrl" />
            <display:table name="paymentMethods" id="paymentMethod" class="displaytag">
                <display:column titleKey="columnTitle.name">
                    <c:choose>
                        <c:when test="${includeReviewUrl}">
                            <c:url var="reviewUrl" value="/action/Payment/PaymentMethod/Review">
                                <c:param name="PaymentMethodName" value="${paymentMethod.paymentMethodName}" />
                            </c:url>
                            <a href="${reviewUrl}"><c:out value="${paymentMethod.paymentMethodName}" /></a>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${paymentMethod.paymentMethodName}" />
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column titleKey="columnTitle.description">
                    <c:out value="${paymentMethod.description}" />
                </display:column>
                <display:column titleKey="columnTitle.paymentMethodType">
                    <c:choose>
                        <c:when test="${includePaymentMethodTypeReviewUrl}">
                            <c:url var="reviewUrl" value="/action/Payment/PaymentMethodType/Review">
                                <c:param name="PaymentMethodTypeName" value="${paymentMethod.paymentMethodType.paymentMethodTypeName}" />
                            </c:url>
                            <a href="${reviewUrl}"><c:out value="${paymentMethod.paymentMethodType.description}" /></a>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${paymentMethod.paymentMethodType.description}" />
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column titleKey="columnTitle.paymentProcessor">
                    <c:choose>
                        <c:when test="${includePaymentProcessorReviewUrl}">
                            <c:url var="reviewUrl" value="/action/Payment/PaymentProcessor/Review">
                                <c:param name="PaymentProcessorName" value="${paymentMethod.paymentProcessor.paymentProcessorName}" />
                            </c:url>
                            <a href="${reviewUrl}"><c:out value="${paymentMethod.paymentProcessor.description}" /></a>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${paymentMethod.paymentProcessor.description}" />
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column property="sortOrder" titleKey="columnTitle.sortOrder" />
                <display:column titleKey="columnTitle.default">
                    <c:choose>
                        <c:when test="${paymentMethod.isDefault}">
                            Default
                        </c:when>
                        <c:otherwise>
                            <et:hasSecurityRole securityRole="PaymentMethod.Edit">
                                <c:url var="setDefaultUrl" value="/action/Payment/PaymentMethod/SetDefault">
                                    <c:param name="PaymentMethodName" value="${paymentMethod.paymentMethodName}" />
                                </c:url>
                                <a href="${setDefaultUrl}">Set Default</a>
                            </et:hasSecurityRole>
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <et:hasSecurityRole securityRoles="PaymentMethod.Edit:PaymentMethod.Description:PaymentMethod.Delete">
                    <display:column>
                        <et:hasSecurityRole securityRole="PaymentMethod.Edit">
                            <c:url var="editUrl" value="/action/Payment/PaymentMethod/Edit">
                                <c:param name="OriginalPaymentMethodName" value="${paymentMethod.paymentMethodName}" />
                            </c:url>
                            <a href="${editUrl}">Edit</a>
                        </et:hasSecurityRole>
                        <et:hasSecurityRole securityRole="PaymentMethod.Description">
                            <c:url var="descriptionsUrl" value="/action/Payment/PaymentMethod/Description">
                                <c:param name="PaymentMethodName" value="${paymentMethod.paymentMethodName}" />
                            </c:url>
                            <a href="${descriptionsUrl}">Descriptions</a>
                        </et:hasSecurityRole>
                        <et:hasSecurityRole securityRole="PaymentMethod.Delete">
                            <c:url var="deleteUrl" value="/action/Payment/PaymentMethod/Delete">
                                <c:param name="PaymentMethodName" value="${paymentMethod.paymentMethodName}" />
                            </c:url>
                            <a href="${deleteUrl}">Delete</a>
                        </et:hasSecurityRole>
                    </display:column>
                </et:hasSecurityRole>
                <et:hasSecurityRole securityRole="Event.List">
                    <display:column>
                        <c:url var="eventsUrl" value="/action/Core/Event/Main">
                            <c:param name="EntityRef" value="${paymentMethod.entityInstance.entityRef}" />
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
