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
        <title><fmt:message key="pageTitle.itemCategories" /></title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Item/Main" />"><fmt:message key="navigation.items" /></a> &gt;&gt;
                <fmt:message key="navigation.itemCategories" />
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="Event.List:ItemCategory.Create:ItemCategory.Edit:ItemCategory.Delete:ItemCategory.Review:ItemCategory.Description" />
            <et:hasSecurityRole securityRole="ItemCategory.Create">
                <p><a href="<c:url value="/action/Item/ItemCategory/Add" />">Add Item Category.</a></p>
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRole="ItemCategory.Review" var="includeReviewUrl" />
            <display:table name="itemCategories" id="itemCategory" class="displaytag" export="true" sort="list" requestURI="/action/Item/ItemCategory/Main">
                <display:setProperty name="export.csv.filename" value="ItemCategories.csv" />
                <display:setProperty name="export.excel.filename" value="ItemCategories.xls" />
                <display:setProperty name="export.pdf.filename" value="ItemCategories.pdf" />
                <display:setProperty name="export.rtf.filename" value="ItemCategories.rtf" />
                <display:setProperty name="export.xml.filename" value="ItemCategories.xml" />
                <display:column titleKey="columnTitle.name" media="html" sortable="true" sortProperty="itemCategoryName">
                    <c:choose>
                        <c:when test="${includeReviewUrl}">
                            <c:url var="reviewUrl" value="/action/Item/ItemCategory/Review">
                                <c:param name="ItemCategoryName" value="${itemCategory.itemCategoryName}" />
                            </c:url>
                            <a href="${reviewUrl}"><et:appearance appearance="${itemCategory.entityInstance.entityAppearance.appearance}"><c:out value="${itemCategory.itemCategoryName}" /></et:appearance></a>
                        </c:when>
                        <c:otherwise>
                            <et:appearance appearance="${itemCategory.entityInstance.entityAppearance.appearance}"><c:out value="${itemCategory.itemCategoryName}" /></et:appearance>
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column titleKey="columnTitle.description" media="html" sortable="true" sortProperty="description">
                    <et:appearance appearance="${itemCategory.entityInstance.entityAppearance.appearance}"><c:out value="${itemCategory.description}" /></et:appearance>
                </display:column>
                <display:column property="sortOrder" titleKey="columnTitle.sortOrder" media="html" sortable="true" sortProperty="sortOrder"/>
                <display:column titleKey="columnTitle.default" media="html" sortable="true" sortProperty="isDefault">
                    <c:choose>
                        <c:when test="${itemCategory.isDefault}">
                            Default
                        </c:when>
                        <c:otherwise>
                            <et:hasSecurityRole securityRole="ItemCategory.Edit">
                                <c:url var="setDefaultUrl" value="/action/Item/ItemCategory/SetDefault">
                                    <c:param name="ParentItemCategoryName" value="${itemCategory.parentItemCategory.itemCategoryName}" />
                                    <c:param name="ItemCategoryName" value="${itemCategory.itemCategoryName}" />
                                </c:url>
                                <a href="${setDefaultUrl}">Set Default</a>
                            </et:hasSecurityRole>
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <et:hasSecurityRole securityRoles="ItemCategory.Edit:ItemCategory.Description:ItemCategory.Delete">
                    <display:column media="html">
                        <et:hasSecurityRole securityRole="ItemCategory.Edit">
                            <c:url var="editUrl" value="/action/Item/ItemCategory/Edit">
                                <c:param name="OriginalItemCategoryName" value="${itemCategory.itemCategoryName}" />
                            </c:url>
                            <a href="${editUrl}">Edit</a>
                        </et:hasSecurityRole>
                        <et:hasSecurityRole securityRole="ItemCategory.Description">
                            <c:url var="descriptionsUrl" value="/action/Item/ItemCategory/Description">
                                <c:param name="ItemCategoryName" value="${itemCategory.itemCategoryName}" />
                            </c:url>
                            <a href="${descriptionsUrl}">Descriptions</a>
                        </et:hasSecurityRole>
                        <et:hasSecurityRole securityRole="ItemCategory.Delete">
                            <c:url var="deleteUrl" value="/action/Item/ItemCategory/Delete">
                                <c:param name="ItemCategoryName" value="${itemCategory.itemCategoryName}" />
                            </c:url>
                            <a href="${deleteUrl}">Delete</a>
                        </et:hasSecurityRole>
                    </display:column>
                </et:hasSecurityRole>
                <et:hasSecurityRole securityRole="Event.List">
                    <display:column media="html">
                        <c:url var="eventsUrl" value="/action/Core/Event/Main">
                            <c:param name="EntityRef" value="${itemCategory.entityInstance.entityRef}" />
                        </c:url>
                        <a href="${eventsUrl}">Events</a>
                    </display:column>
                </et:hasSecurityRole>
                <display:column property="itemCategoryName" titleKey="columnTitle.name" media="csv excel pdf rtf xml" />
                <display:column property="description" titleKey="columnTitle.description" media="csv excel pdf rtf xml" />
                <display:column property="sortOrder" titleKey="columnTitle.sortOrder" media="csv excel pdf rtf xml" />
                <display:column property="isDefault" titleKey="columnTitle.default" media="csv excel pdf rtf xml" />
            </display:table>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
