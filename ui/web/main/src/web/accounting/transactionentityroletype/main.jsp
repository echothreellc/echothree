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
        <title>Entity Role Types</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Accounting/Main" />"><fmt:message key="navigation.accounting" /></a> &gt;&gt;
                <a href="<c:url value="/action/Accounting/TransactionType/Main" />">Transaction Types</a> &gt;&gt;
                Entity Role Types
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="Event.List:TransactionEntityRoleType.Create:TransactionEntityRoleType.Edit:TransactionEntityRoleType.Delete:TransactionEntityRoleType.Review:TransactionEntityRoleType.Description:ComponentVendor.Review:EntityType.Review" />
            <et:hasSecurityRole securityRole="TransactionEntityRoleType.Create">
                <c:url var="addUrl" value="/action/Accounting/TransactionEntityRoleType/Create">
                    <c:param name="TransactionTypeName" value="${transactionType.transactionTypeName}" />
                </c:url>
                <p><a href="${addUrl}">Add Entity Role Type.</a></p>
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRole="TransactionEntityRoleType.Review" var="includeReviewUrl" />
            <et:hasSecurityRole securityRole="ComponentVendor.Review" var="includeComponentVendorReviewUrl" />
            <et:hasSecurityRole securityRole="EntityType.Review" var="includeEntityTypeReviewUrl" />
            <display:table name="transactionEntityRoleTypes" id="transactionEntityRoleType" class="displaytag">
                <display:column titleKey="columnTitle.name">
                    <c:choose>
                        <c:when test="${includeReviewUrl}">
                            <c:url var="reviewUrl" value="/action/Accounting/TransactionEntityRoleType/Review">
                                <c:param name="TransactionTypeName" value="${transactionEntityRoleType.transactionType.transactionTypeName}" />
                                <c:param name="TransactionEntityRoleTypeName" value="${transactionEntityRoleType.transactionEntityRoleTypeName}" />
                            </c:url>
                            <a href="${reviewUrl}"><c:out value="${transactionEntityRoleType.transactionEntityRoleTypeName}" /></a>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${transactionEntityRoleType.transactionEntityRoleTypeName}" />
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column titleKey="columnTitle.description">
                    <c:out value="${transactionEntityRoleType.description}" />
                </display:column>
                <display:column titleKey="columnTitle.componentVendor">
                    <c:choose>
                        <c:when test="${includeComponentVendorReviewUrl}">
                            <c:url var="reviewUrl" value="/action/Core/ComponentVendor/Review">
                                <c:param name="ComponentVendorName" value="${transactionEntityRoleType.entityType.componentVendor.componentVendorName}" />
                            </c:url>
                            <a href="${reviewUrl}"><c:out value="${transactionEntityRoleType.entityType.componentVendor.description}" /></a>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${transactionEntityRoleType.entityType.componentVendor.description}" />
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column titleKey="columnTitle.entityType">
                    <c:choose>
                        <c:when test="${includeEntityTypeReviewUrl}">
                            <c:url var="reviewUrl" value="/action/Core/EntityType/Review">
                                <c:param name="ComponentVendorName" value="${transactionEntityRoleType.entityType.componentVendor.componentVendorName}" />
                                <c:param name="EntityTypeName" value="${transactionEntityRoleType.entityType.entityTypeName}" />
                            </c:url>
                            <a href="${reviewUrl}"><c:out value="${transactionEntityRoleType.entityType.description}" /></a>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${transactionEntityRoleType.entityType.description}" />
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <et:hasSecurityRole securityRoles="TransactionEntityRoleType.Edit:TransactionEntityRoleType.Delete:TransactionEntityRoleType.Description">
                    <display:column>
                        <et:hasSecurityRole securityRole="TransactionEntityRoleType.Edit">
                            <c:url var="editUrl" value="/action/Accounting/TransactionEntityRoleType/Edit">
                                <c:param name="TransactionTypeName" value="${transactionEntityRoleType.transactionType.transactionTypeName}" />
                                <c:param name="OriginalTransactionEntityRoleTypeName" value="${transactionEntityRoleType.transactionEntityRoleTypeName}" />
                            </c:url>
                            <a href="${editUrl}">Edit</a>
                        </et:hasSecurityRole>
                        <et:hasSecurityRole securityRole="TransactionEntityRoleType.Description">
                            <c:url var="descriptionsUrl" value="/action/Accounting/TransactionEntityRoleType/Description">
                                <c:param name="TransactionTypeName" value="${transactionEntityRoleType.transactionType.transactionTypeName}" />
                                <c:param name="TransactionEntityRoleTypeName" value="${transactionEntityRoleType.transactionEntityRoleTypeName}" />
                            </c:url>
                            <a href="${descriptionsUrl}">Descriptions</a>
                        </et:hasSecurityRole>
                        <et:hasSecurityRole securityRole="TransactionEntityRoleType.Delete">
                            <c:url var="deleteUrl" value="/action/Accounting/TransactionEntityRoleType/Delete">
                                <c:param name="TransactionTypeName" value="${transactionEntityRoleType.transactionType.transactionTypeName}" />
                                <c:param name="TransactionEntityRoleTypeName" value="${transactionEntityRoleType.transactionEntityRoleTypeName}" />
                            </c:url>
                            <a href="${deleteUrl}">Delete</a>
                        </et:hasSecurityRole>
                    </display:column>
                </et:hasSecurityRole>
                <et:hasSecurityRole securityRole="Event.List">
                    <display:column>
                        <c:url var="eventsUrl" value="/action/Core/Event/Main">
                            <c:param name="EntityRef" value="${transactionEntityRoleType.entityInstance.entityRef}" />
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
