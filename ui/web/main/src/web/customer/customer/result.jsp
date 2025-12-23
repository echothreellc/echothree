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
        <title><fmt:message key="pageTitle.customerResults" /></title>
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
                <jsp:param name="showAsLink" value="false"/>
            </jsp:include>
        <%@ include file="../../include/breadcrumb/breadcrumbs-end.jsp" %>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="CustomerStatus.Choices:Event.List" />
            <c:url var="addUrl" value="/action/Customer/Customer/Add">
                <c:if test='${param.FirstName != null}'>
                    <c:param name="FirstName" value="${param.FirstName}" />
                </c:if>
                <c:if test='${param.MiddleName != null}'>
                    <c:param name="MiddleName" value="${param.MiddleName}" />
                </c:if>
                <c:if test='${param.LastName != null}'>
                    <c:param name="LastName" value="${param.LastName}" />
                </c:if>
                <c:if test='${param.Name != null}'>
                    <c:param name="Name" value="${param.Name}" />
                </c:if>
            </c:url>
            <p><a href="${addUrl}">Add Customer</a></p>
            <et:containsExecutionError key="UnknownUserVisitSearch">
                <c:set var="unknownUserVisitSearch" value="true" />
            </et:containsExecutionError>
            <c:choose>
                <c:when test="${unknownUserVisitSearch}">
                    <p>Your search results are no longer available, please perform your search again.</p>
                </c:when>
                <c:otherwise>
                    <et:hasSecurityRole securityRole="CustomerStatus.Choices" var="includeEditableCustomerStatus">
                        <c:url var="returnUrl" scope="request" value="Result" />
                    </et:hasSecurityRole>
                    <c:choose>
                        <c:when test="${customerResultCount == null || customerResultCount < 21}">
                            <display:table name="customerResults.list" id="customerResult" class="displaytag" sort="list" requestURI="/action/Customer/Customer/Result">
                                <display:column titleKey="columnTitle.name" sortable="true" sortProperty="customer.customerName">
                                    <c:url var="reviewUrl" value="/action/Customer/Customer/Review">
                                        <c:param name="CustomerName" value="${customerResult.customer.customerName}" />
                                    </c:url>
                                    <a href="${reviewUrl}"><et:appearance appearance="${customerResult.customer.entityInstance.entityAppearance.appearance}">${customerResult.customer.customerName}</et:appearance></a>
                                </display:column>
                                <display:column titleKey="columnTitle.firstName" sortable="true" sortProperty="customer.person.firstName">
                                    <c:if test='${customerResult.customer.person.firstName != null}'>
                                        <c:url var="mainUrl" value="/action/Customer/Customer/Main">
                                            <c:param name="FirstName" value="${customerResult.customer.person.firstName}" />
                                        </c:url>
                                        <a href="${mainUrl}"><et:appearance appearance="${customerResult.customer.entityInstance.entityAppearance.appearance}"><c:out value="${customerResult.customer.person.firstName}" /></et:appearance></a>
                                    </c:if>
                                </display:column>
                                <display:column titleKey="columnTitle.lastName" sortable="true" sortProperty="customer.person.lastName">
                                    <c:if test='${customerResult.customer.person.lastName != null}'>
                                        <c:url var="mainUrl" value="/action/Customer/Customer/Main">
                                            <c:param name="LastName" value="${customerResult.customer.person.lastName}" />
                                        </c:url>
                                        <a href="${mainUrl}"><et:appearance appearance="${customerResult.customer.entityInstance.entityAppearance.appearance}"><c:out value="${customerResult.customer.person.lastName}" /></et:appearance></a>
                                    </c:if>
                                </display:column>
                                <display:column titleKey="columnTitle.companyName" sortable="true" sortProperty="customer.partyGroup.name">
                                    <et:appearance appearance="${customerResult.customer.entityInstance.entityAppearance.appearance}"><c:out value="${customerResult.customer.partyGroup.name}" /></et:appearance>
                                </display:column>
                                <display:column titleKey="columnTitle.type" sortable="true" sortProperty="customer.customerType.description">
                                    <et:appearance appearance="${customerResult.customer.customerType.entityInstance.entityAppearance.appearance}"><c:out value="${customerResult.customer.customerType.description}" /></et:appearance>
                                </display:column>
                                <display:column titleKey="columnTitle.status">
                                    <c:choose>
                                        <c:when test="${includeEditableCustomerStatus}">
                                            <c:url var="statusUrl" value="/action/Customer/Customer/CustomerStatus">
                                                <c:param name="CustomerName" value="${customerResult.customer.customerName}" />
                                                <c:param name="ReturnUrl" value="${returnUrl}" />
                                            </c:url>
                                            <a href="${statusUrl}"><et:appearance appearance="${customerResult.customer.customerStatus.workflowStep.entityInstance.entityAppearance.appearance}"><c:out value="${customerResult.customer.customerStatus.workflowStep.description}" /></et:appearance></a>
                                        </c:when>
                                        <c:otherwise>
                                            <et:appearance appearance="${customerResult.customer.customerStatus.workflowStep.entityInstance.entityAppearance.appearance}"><c:out value="${customerResult.customer.customerStatus.workflowStep.description}" /></et:appearance>
                                        </c:otherwise>
                                    </c:choose>
                                </display:column>
                                <display:column>
                                    <c:url var="customerDocumentsUrl" value="/action/Customer/CustomerDocument/Main">
                                        <c:param name="CustomerName" value="${customerResult.customer.customerName}" />
                                    </c:url>
                                    <a href="${customerDocumentsUrl}">Documents</a>
                                    <c:url var="customerContactMechanismsUrl" value="/action/Customer/CustomerContactMechanism/Main">
                                        <c:param name="CustomerName" value="${customerResult.customer.customerName}" />
                                    </c:url>
                                    <a href="${customerContactMechanismsUrl}">Contact Mechanisms</a>
                                    <c:url var="customerPaymentMethodsUrl" value="/action/Customer/CustomerPaymentMethod/Main">
                                        <c:param name="CustomerName" value="${customerResult.customer.customerName}" />
                                    </c:url>
                                    <a href="${customerPaymentMethodsUrl}">Payment Methods</a>
                                </display:column>
                                <et:hasSecurityRole securityRole="Event.List">
                                    <display:column>
                                        <c:url var="eventsUrl" value="/action/Core/Event/Main">
                                            <c:param name="EntityRef" value="${customerResult.customer.entityInstance.entityRef}" />
                                        </c:url>
                                        <a href="${eventsUrl}">Events</a>
                                    </display:column>
                                </et:hasSecurityRole>
                            </display:table>
                            <c:if test="${customerResults.size > 20}">
                                <c:url var="resultsUrl" value="/action/Customer/Customer/Result">
                                    <c:if test='${param.FirstName != null}'>
                                        <c:param name="FirstName" value="${param.FirstName}" />
                                    </c:if>
                                    <c:if test='${param.MiddleName != null}'>
                                        <c:param name="MiddleName" value="${param.MiddleName}" />
                                    </c:if>
                                    <c:if test='${param.LastName != null}'>
                                        <c:param name="LastName" value="${param.LastName}" />
                                    </c:if>
                                    <c:if test='${param.Name != null}'>
                                        <c:param name="Name" value="${param.Name}" />
                                    </c:if>
                                </c:url>
                                <a href="${resultsUrl}">Paged Results</a>
                            </c:if>
                        </c:when>
                        <c:otherwise>
                            <display:table name="customerResults.list" id="customerResult" class="displaytag" partialList="true" pagesize="20" size="customerResultCount" requestURI="/action/Customer/Customer/Result">
                                <display:column titleKey="columnTitle.name">
                                    <c:url var="reviewUrl" value="/action/Customer/Customer/Review">
                                        <c:param name="CustomerName" value="${customerResult.customer.customerName}" />
                                    </c:url>
                                    <a href="${reviewUrl}"><et:appearance appearance="${customerResult.customer.entityInstance.entityAppearance.appearance}">${customerResult.customer.customerName}</et:appearance></a>
                                </display:column>
                                <display:column titleKey="columnTitle.firstName">
                                    <c:if test='${customerResult.customer.person.firstName != null}'>
                                        <c:url var="mainUrl" value="/action/Customer/Customer/Main">
                                            <c:param name="FirstName" value="${customerResult.customer.person.firstName}" />
                                        </c:url>
                                        <a href="${mainUrl}"><et:appearance appearance="${customerResult.customer.entityInstance.entityAppearance.appearance}"><c:out value="${customerResult.customer.person.firstName}" /></et:appearance></a>
                                    </c:if>
                                </display:column>
                                <display:column titleKey="columnTitle.lastName">
                                    <c:if test='${customerResult.customer.person.lastName != null}'>
                                        <c:url var="mainUrl" value="/action/Customer/Customer/Main">
                                            <c:param name="LastName" value="${customerResult.customer.person.lastName}" />
                                        </c:url>
                                        <a href="${mainUrl}"><et:appearance appearance="${customerResult.customer.entityInstance.entityAppearance.appearance}"><c:out value="${customerResult.customer.person.lastName}" /></et:appearance></a>
                                    </c:if>
                                </display:column>
                                <display:column titleKey="columnTitle.companyName">
                                    <et:appearance appearance="${customerResult.customer.entityInstance.entityAppearance.appearance}"><c:out value="${customerResult.customer.partyGroup.name}" /></et:appearance>
                                </display:column>
                                <display:column titleKey="columnTitle.type">
                                    <et:appearance appearance="${customerResult.customer.customerType.entityInstance.entityAppearance.appearance}"><c:out value="${customerResult.customer.customerType.description}" /></et:appearance>
                                </display:column>
                                <display:column titleKey="columnTitle.status">
                                    <c:choose>
                                        <c:when test="${includeEditableCustomerStatus}">
                                            <c:url var="statusUrl" value="/action/Customer/Customer/CustomerStatus">
                                                <c:param name="CustomerName" value="${customerResult.customer.customerName}" />
                                                <c:param name="ReturnUrl" value="${returnUrl}" />
                                            </c:url>
                                            <a href="${statusUrl}"><et:appearance appearance="${customerResult.customer.customerStatus.workflowStep.entityInstance.entityAppearance.appearance}"><c:out value="${customerResult.customer.customerStatus.workflowStep.description}" /></et:appearance></a>
                                        </c:when>
                                        <c:otherwise>
                                            <et:appearance appearance="${customerResult.customer.customerStatus.workflowStep.entityInstance.entityAppearance.appearance}"><c:out value="${customerResult.customer.customerStatus.workflowStep.description}" /></et:appearance>
                                        </c:otherwise>
                                    </c:choose>
                                </display:column>
                                <display:column>
                                    <c:url var="customerDocumentsUrl" value="/action/Customer/CustomerDocument/Main">
                                        <c:param name="CustomerName" value="${customerResult.customer.customerName}" />
                                    </c:url>
                                    <a href="${customerDocumentsUrl}">Documents</a>
                                    <c:url var="customerContactMechanismsUrl" value="/action/Customer/CustomerContactMechanism/Main">
                                        <c:param name="CustomerName" value="${customerResult.customer.customerName}" />
                                    </c:url>
                                    <a href="${customerContactMechanismsUrl}">Contact Mechanisms</a>
                                    <c:url var="customerPaymentMethodsUrl" value="/action/Customer/CustomerPaymentMethod/Main">
                                        <c:param name="CustomerName" value="${customerResult.customer.customerName}" />
                                    </c:url>
                                    <a href="${customerPaymentMethodsUrl}">Payment Methods</a>
                                </display:column>
                                <et:hasSecurityRole securityRole="Event.List">
                                    <display:column>
                                        <c:url var="eventsUrl" value="/action/Core/Event/Main">
                                            <c:param name="EntityRef" value="${customerResult.customer.entityInstance.entityRef}" />
                                        </c:url>
                                        <a href="${eventsUrl}">Events</a>
                                    </display:column>
                                </et:hasSecurityRole>
                            </display:table>
                            <c:url var="resultsUrl" value="/action/Customer/Customer/Result">
                                <c:if test='${param.FirstName != null}'>
                                    <c:param name="FirstName" value="${param.FirstName}" />
                                </c:if>
                                <c:if test='${param.MiddleName != null}'>
                                    <c:param name="MiddleName" value="${param.MiddleName}" />
                                </c:if>
                                <c:if test='${param.LastName != null}'>
                                    <c:param name="LastName" value="${param.LastName}" />
                                </c:if>
                                <c:if test='${param.Name != null}'>
                                    <c:param name="Name" value="${param.Name}" />
                                </c:if>
                                <c:param name="Results" value="Complete" />
                            </c:url>
                            <a href="${resultsUrl}">All Results</a>
                        </c:otherwise>
                    </c:choose>
                </c:otherwise>
            </c:choose>
        </div>
    <%@ include file="../../include/body-end-b.jsp" %>
</html>
