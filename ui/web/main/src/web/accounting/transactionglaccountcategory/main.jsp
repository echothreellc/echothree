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
        <title>Gl Account Categories</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Accounting/Main" />"><fmt:message key="navigation.accounting" /></a> &gt;&gt;
                <a href="<c:url value="/action/Accounting/TransactionType/Main" />">Transaction Types</a> &gt;&gt;
                Gl Account Categories
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="Event.List:TransactionGlAccountCategory.Create:TransactionGlAccountCategory.Edit:TransactionGlAccountCategory.Delete:TransactionGlAccountCategory.Review:TransactionGlAccountCategory.Description:GlAccountCategory.Review:GlAccount.Review" />
            <et:hasSecurityRole securityRole="TransactionGlAccountCategory.Create">
                <c:url var="addUrl" value="/action/Accounting/TransactionGlAccountCategory/Create">
                    <c:param name="TransactionTypeName" value="${transactionType.transactionTypeName}" />
                </c:url>
                <p><a href="${addUrl}">Add Gl Account Category.</a></p>
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRole="TransactionGlAccountCategory.Review" var="includeReviewUrl" />
            <et:hasSecurityRole securityRole="GlAccountCategory.Review" var="includeGlAccountCategoryReviewUrl" />
            <et:hasSecurityRole securityRole="GlAccount.Review" var="includeGlAccountReviewUrl" />
            <display:table name="transactionGlAccountCategories" id="transactionGlAccountCategory" class="displaytag">
                <display:column titleKey="columnTitle.name">
                    <c:choose>
                        <c:when test="${includeReviewUrl}">
                            <c:url var="reviewUrl" value="/action/Accounting/TransactionGlAccountCategory/Review">
                                <c:param name="TransactionTypeName" value="${transactionGlAccountCategory.transactionType.transactionTypeName}" />
                                <c:param name="TransactionGlAccountCategoryName" value="${transactionGlAccountCategory.transactionGlAccountCategoryName}" />
                            </c:url>
                            <a href="${reviewUrl}"><c:out value="${transactionGlAccountCategory.transactionGlAccountCategoryName}" /></a>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${transactionGlAccountCategory.transactionGlAccountCategoryName}" />
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column titleKey="columnTitle.description">
                    <c:out value="${transactionGlAccountCategory.description}" />
                </display:column>
                <display:column titleKey="columnTitle.glAccountCategory">
                    <c:choose>
                        <c:when test="${includeReviewUrl}">
                            <c:url var="reviewUrl" value="/action/Accounting/GlAccountCategory/Review">
                                <c:param name="GlAccountCategoryName" value="${transactionGlAccountCategory.glAccountCategory.glAccountCategoryName}" />
                            </c:url>
                            <a href="${reviewUrl}"><c:out value="${transactionGlAccountCategory.glAccountCategory.description}" /></a>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${transactionGlAccountCategory.glAccountCategory.description}" />
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column titleKey="columnTitle.glAccountName">
                    <c:choose>
                        <c:when test="${includeGlAccountReviewUrl}">
                            <c:url var="reviewUrl" value="/action/Accounting/GlAccount/Review">
                                <c:param name="GlAccountName" value="${transactionGlAccountCategory.glAccount.glAccountName}" />
                            </c:url>
                            <a href="${reviewUrl}"><c:out value="${transactionGlAccountCategory.glAccount.glAccountName}" /></a>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${transactionGlAccountCategory.glAccount.glAccountName}" />
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column titleKey="columnTitle.glAccountDescription">
                    <c:out value="${transactionGlAccountCategory.glAccount.description}" />
                </display:column>
                <et:hasSecurityRole securityRoles="TransactionGlAccountCategory.Edit:TransactionGlAccountCategory.Delete:TransactionGlAccountCategory.Description">
                    <display:column>
                        <et:hasSecurityRole securityRole="TransactionGlAccountCategory.Edit">
                            <c:url var="editUrl" value="/action/Accounting/TransactionGlAccountCategory/Edit">
                                <c:param name="TransactionTypeName" value="${transactionGlAccountCategory.transactionType.transactionTypeName}" />
                                <c:param name="OriginalTransactionGlAccountCategoryName" value="${transactionGlAccountCategory.transactionGlAccountCategoryName}" />
                            </c:url>
                            <a href="${editUrl}">Edit</a>
                        </et:hasSecurityRole>
                        <et:hasSecurityRole securityRole="TransactionGlAccountCategory.Description">
                            <c:url var="descriptionsUrl" value="/action/Accounting/TransactionGlAccountCategory/Description">
                                <c:param name="TransactionTypeName" value="${transactionGlAccountCategory.transactionType.transactionTypeName}" />
                                <c:param name="TransactionGlAccountCategoryName" value="${transactionGlAccountCategory.transactionGlAccountCategoryName}" />
                            </c:url>
                            <a href="${descriptionsUrl}">Descriptions</a>
                        </et:hasSecurityRole>
                        <et:hasSecurityRole securityRole="TransactionGlAccountCategory.Delete">
                            <c:url var="deleteUrl" value="/action/Accounting/TransactionGlAccountCategory/Delete">
                                <c:param name="TransactionTypeName" value="${transactionGlAccountCategory.transactionType.transactionTypeName}" />
                                <c:param name="TransactionGlAccountCategoryName" value="${transactionGlAccountCategory.transactionGlAccountCategoryName}" />
                            </c:url>
                            <a href="${deleteUrl}">Delete</a>
                        </et:hasSecurityRole>
                    </display:column>
                </et:hasSecurityRole>
                <et:hasSecurityRole securityRole="Event.List">
                    <display:column>
                        <c:url var="eventsUrl" value="/action/Core/Event/Main">
                            <c:param name="EntityRef" value="${transactionGlAccountCategory.entityInstance.entityRef}" />
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
