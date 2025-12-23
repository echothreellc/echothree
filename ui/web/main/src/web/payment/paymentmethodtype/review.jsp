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
        <title>Review (<c:out value="${paymentMethodType.paymentMethodTypeName}" />)</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Payment/Main" />">Payment</a> &gt;&gt;
                Payment Method Types &gt;&gt;
                Review (<c:out value="${paymentMethodType.paymentMethodTypeName}" />)
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="Workflow.Review" />
            <et:hasSecurityRole securityRole="Workflow.Review" var="includeWorkflowReviewUrl" />
            <p><font size="+2"><b><c:out value="${paymentMethodType.description}" /></b></font></p>
            <br />
            Payment Method Type Name: ${paymentMethodType.paymentMethodTypeName}<br />
            <br />

            <c:if test='${paymentMethodType.paymentMethodTypePartyTypes.size > 0}'>
                <display:table name="paymentMethodType.paymentMethodTypePartyTypes.list" id="paymentMethodTypePartyType" class="displaytag">
                    <display:column titleKey="columnTitle.partyType">
                        <c:url var="reviewUrl" value="/action/Configuration/PartyType/Review">
                            <c:param name="PartyTypeName" value="${paymentMethodTypePartyType.partyType.partyTypeName}" />
                        </c:url>
                        <a href="${reviewUrl}"><c:out value="${paymentMethodTypePartyType.partyType.description}" /></a>
                    </display:column>
                    <display:column titleKey="columnTitle.partyPaymentMethodWorkflow">
                        <c:choose>
                            <c:when test="${includeWorkflowReviewUrl}">
                                <c:url var="reviewUrl" value="/action/Configuration/Workflow/Review">
                                    <c:param name="WorkflowName" value="${paymentMethodTypePartyType.partyPaymentMethodWorkflow.workflowName}" />
                                </c:url>
                                <a href="${reviewUrl}"><c:out value="${paymentMethodTypePartyType.partyPaymentMethodWorkflow.description}" /></a>
                            </c:when>
                            <c:otherwise>
                                <c:out value="${paymentMethodTypePartyType.partyPaymentMethodWorkflow.description}" />
                            </c:otherwise>
                        </c:choose>
                    </display:column>
                    <display:column titleKey="columnTitle.partyContactMechanismWorkflow">
                        <c:choose>
                            <c:when test="${includeWorkflowReviewUrl}">
                                <c:url var="reviewUrl" value="/action/Configuration/Workflow/Review">
                                    <c:param name="WorkflowName" value="${paymentMethodTypePartyType.partyContactMechanismWorkflow.workflowName}" />
                                </c:url>
                                <a href="${reviewUrl}"><c:out value="${paymentMethodTypePartyType.partyContactMechanismWorkflow.description}" /></a>
                            </c:when>
                            <c:otherwise>
                                <c:out value="${paymentMethodTypePartyType.partyContactMechanismWorkflow.description}" />
                            </c:otherwise>
                        </c:choose>
                    </display:column>
                </display:table>
                <br />
            </c:if>

            <br />
            <br />
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
