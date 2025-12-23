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
        <title>Transaction Types</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Accounting/Main" />"><fmt:message key="navigation.accounting" /></a> &gt;&gt;
                Transaction Types
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="Event.List:TransactionType.Create:TransactionType.Edit:TransactionType.Delete:TransactionType.Review:TransactionType.Description:TransactionGlAccountCategory.List:TransactionEntityRoleType.List" />
            <et:hasSecurityRole securityRoles="TransactionGlAccountCategory.List:TransactionEntityRoleType.List">
                <c:set var="linksInFirstRow" value="true" />
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRoles="TransactionType.Edit:TransactionType.Description:TransactionType.Delete">
                <c:set var="linksInSecondRow" value="true" />
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRole="TransactionType.Create">
                <p><a href="<c:url value="/action/Accounting/TransactionType/Add" />">Add Transaction Type.</a></p>
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRole="TransactionType.Review" var="includeReviewUrl" />
            <display:table name="transactionTypes" id="transactionType" class="displaytag">
                <display:column titleKey="columnTitle.name">
                    <c:choose>
                        <c:when test="${includeReviewUrl}">
                            <c:url var="reviewUrl" value="/action/Accounting/TransactionType/Review">
                                <c:param name="TransactionTypeName" value="${transactionType.transactionTypeName}" />
                            </c:url>
                            <a href="${reviewUrl}"><c:out value="${transactionType.transactionTypeName}" /></a>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${transactionType.transactionTypeName}" />
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column titleKey="columnTitle.description">
                    <c:out value="${transactionType.description}" />
                </display:column>
                <display:column property="sortOrder" titleKey="columnTitle.sortOrder" />
                <et:hasSecurityRole securityRoles="TransactionGlAccountCategory.List:TransactionEntityRoleType.List:TransactionType.Edit:TransactionType.Delete:TransactionType.Description">
                    <display:column>
                        <et:hasSecurityRole securityRole="TransactionGlAccountCategory.List">
                            <c:url var="glAccountCategoriesUrl" value="/action/Accounting/TransactionGlAccountCategory/Main">
                                <c:param name="TransactionTypeName" value="${transactionType.transactionTypeName}" />
                            </c:url>
                            <a href="${glAccountCategoriesUrl}">Gl Account Categories</a>
                        </et:hasSecurityRole>
                        <et:hasSecurityRole securityRole="TransactionEntityRoleType.List">
                            <c:url var="entityRoleTypesUrl" value="/action/Accounting/TransactionEntityRoleType/Main">
                                <c:param name="TransactionTypeName" value="${transactionType.transactionTypeName}" />
                            </c:url>
                            <a href="${entityRoleTypesUrl}">Entity Role Types</a>
                        </et:hasSecurityRole>
                        <c:if test="${linksInFirstRow && linksInSecondRow}">
                            <br />
                        </c:if>
                        <et:hasSecurityRole securityRole="TransactionType.Edit">
                            <c:url var="editUrl" value="/action/Accounting/TransactionType/Edit">
                                <c:param name="OriginalTransactionTypeName" value="${transactionType.transactionTypeName}" />
                            </c:url>
                            <a href="${editUrl}">Edit</a>
                        </et:hasSecurityRole>
                        <et:hasSecurityRole securityRole="TransactionType.Description">
                            <c:url var="descriptionsUrl" value="/action/Accounting/TransactionType/Description">
                                <c:param name="TransactionTypeName" value="${transactionType.transactionTypeName}" />
                            </c:url>
                            <a href="${descriptionsUrl}">Descriptions</a>
                        </et:hasSecurityRole>
                        <et:hasSecurityRole securityRole="TransactionType.Delete">
                            <c:url var="deleteUrl" value="/action/Accounting/TransactionType/Delete">
                                <c:param name="TransactionTypeName" value="${transactionType.transactionTypeName}" />
                            </c:url>
                            <a href="${deleteUrl}">Delete</a>
                        </et:hasSecurityRole>
                    </display:column>
                </et:hasSecurityRole>
                <et:hasSecurityRole securityRole="Event.List">
                    <display:column>
                        <c:url var="eventsUrl" value="/action/Core/Event/Main">
                            <c:param name="EntityRef" value="${transactionType.entityInstance.entityRef}" />
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
