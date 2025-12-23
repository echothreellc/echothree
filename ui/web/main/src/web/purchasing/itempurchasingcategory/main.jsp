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
        <title><fmt:message key="pageTitle.itemPurchasingCategories" /></title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Purchasing/Main" />"><fmt:message key="navigation.purchasing" /></a> &gt;&gt;
                <fmt:message key="navigation.itemPurchasingCategories" />
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="Event.List:ItemPurchasingCategory.Create:ItemPurchasingCategory.Edit:ItemPurchasingCategory.Delete:ItemPurchasingCategory.Review:ItemPurchasingCategory.Description" />
            <et:hasSecurityRole securityRole="ItemPurchasingCategory.Create">
                <p><a href="<c:url value="/action/Purchasing/ItemPurchasingCategory/Add" />">Add Item Purchasing Category.</a></p>
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRole="ItemPurchasingCategory.Review" var="includeReviewUrl" />
            <display:table name="itemPurchasingCategories" id="itemPurchasingCategory" class="displaytag" export="true" sort="list" requestURI="/action/Purchasing/ItemPurchasingCategory/Main">
                <display:setProperty name="export.csv.filename" value="ItemPurchasingCategories.csv" />
                <display:setProperty name="export.excel.filename" value="ItemPurchasingCategories.xls" />
                <display:setProperty name="export.pdf.filename" value="ItemPurchasingCategories.pdf" />
                <display:setProperty name="export.rtf.filename" value="ItemPurchasingCategories.rtf" />
                <display:setProperty name="export.xml.filename" value="ItemPurchasingCategories.xml" />
                <display:column titleKey="columnTitle.name" media="html" sortable="true" sortProperty="itemPurchasingCategoryName">
                    <c:choose>
                        <c:when test="${includeReviewUrl}">
                            <c:url var="reviewUrl" value="/action/Purchasing/ItemPurchasingCategory/Review">
                                <c:param name="ItemPurchasingCategoryName" value="${itemPurchasingCategory.itemPurchasingCategoryName}" />
                            </c:url>
                            <a href="${reviewUrl}"><c:out value="${itemPurchasingCategory.itemPurchasingCategoryName}" /></a>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${itemPurchasingCategory.itemPurchasingCategoryName}" />
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column titleKey="columnTitle.description" media="html" sortable="true" sortProperty="description">
                    <c:out value="${itemPurchasingCategory.description}" />
                </display:column>
                <display:column property="sortOrder" titleKey="columnTitle.sortOrder" media="html" sortable="true" sortProperty="sortOrder"/>
                <display:column titleKey="columnTitle.default" media="html" sortable="true" sortProperty="isDefault">
                    <c:choose>
                        <c:when test="${itemPurchasingCategory.isDefault}">
                            Default
                        </c:when>
                        <c:otherwise>
                            <et:hasSecurityRole securityRole="ItemPurchasingCategory.Edit">
                                <c:url var="setDefaultUrl" value="/action/Purchasing/ItemPurchasingCategory/SetDefault">
                                    <c:param name="ParentItemPurchasingCategoryName" value="${itemPurchasingCategory.parentItemPurchasingCategory.itemPurchasingCategoryName}" />
                                    <c:param name="ItemPurchasingCategoryName" value="${itemPurchasingCategory.itemPurchasingCategoryName}" />
                                </c:url>
                                <a href="${setDefaultUrl}">Set Default</a>
                            </et:hasSecurityRole>
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <et:hasSecurityRole securityRoles="ItemPurchasingCategory.Edit:ItemPurchasingCategory.Description:ItemPurchasingCategory.Delete">
                    <display:column media="html">
                        <et:hasSecurityRole securityRole="ItemPurchasingCategory.Edit">
                            <c:url var="editUrl" value="/action/Purchasing/ItemPurchasingCategory/Edit">
                                <c:param name="OriginalItemPurchasingCategoryName" value="${itemPurchasingCategory.itemPurchasingCategoryName}" />
                            </c:url>
                            <a href="${editUrl}">Edit</a>
                        </et:hasSecurityRole>
                        <et:hasSecurityRole securityRole="ItemPurchasingCategory.Description">
                            <c:url var="descriptionsUrl" value="/action/Purchasing/ItemPurchasingCategory/Description">
                                <c:param name="ItemPurchasingCategoryName" value="${itemPurchasingCategory.itemPurchasingCategoryName}" />
                            </c:url>
                            <a href="${descriptionsUrl}">Descriptions</a>
                        </et:hasSecurityRole>
                        <et:hasSecurityRole securityRole="ItemPurchasingCategory.Delete">
                            <c:url var="deleteUrl" value="/action/Purchasing/ItemPurchasingCategory/Delete">
                                <c:param name="ItemPurchasingCategoryName" value="${itemPurchasingCategory.itemPurchasingCategoryName}" />
                            </c:url>
                            <a href="${deleteUrl}">Delete</a>
                        </et:hasSecurityRole>
                    </display:column>
                </et:hasSecurityRole>
                <et:hasSecurityRole securityRole="Event.List">
                    <display:column media="html">
                        <c:url var="eventsUrl" value="/action/Core/Event/Main">
                            <c:param name="EntityRef" value="${itemPurchasingCategory.entityInstance.entityRef}" />
                        </c:url>
                        <a href="${eventsUrl}">Events</a>
                    </display:column>
                </et:hasSecurityRole>
                <display:column property="itemPurchasingCategoryName" titleKey="columnTitle.name" media="csv excel pdf rtf xml" />
                <display:column property="description" titleKey="columnTitle.description" media="csv excel pdf rtf xml" />
                <display:column property="sortOrder" titleKey="columnTitle.sortOrder" media="csv excel pdf rtf xml" />
                <display:column property="isDefault" titleKey="columnTitle.default" media="csv excel pdf rtf xml" />
            </display:table>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
