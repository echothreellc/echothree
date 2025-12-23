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
        <title><fmt:message key="pageTitle.itemAccountingCategories" /></title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Accounting/Main" />"><fmt:message key="navigation.accounting" /></a> &gt;&gt;
                <fmt:message key="navigation.itemAccountingCategories" />
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="Event.List:ItemAccountingCategory.Create:ItemAccountingCategory.Edit:ItemAccountingCategory.Delete:ItemAccountingCategory.Review:ItemAccountingCategory.Description" />
            <et:hasSecurityRole securityRole="ItemAccountingCategory.Create">
                <p><a href="<c:url value="/action/Accounting/ItemAccountingCategory/Add" />">Add Item Accounting Category.</a></p>
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRole="ItemAccountingCategory.Review" var="includeReviewUrl" />
            <display:table name="itemAccountingCategories" id="itemAccountingCategory" class="displaytag" export="true" sort="list" requestURI="/action/Accounting/ItemAccountingCategory/Main">
                <display:setProperty name="export.csv.filename" value="ItemAccountingCategories.csv" />
                <display:setProperty name="export.excel.filename" value="ItemAccountingCategories.xls" />
                <display:setProperty name="export.pdf.filename" value="ItemAccountingCategories.pdf" />
                <display:setProperty name="export.rtf.filename" value="ItemAccountingCategories.rtf" />
                <display:setProperty name="export.xml.filename" value="ItemAccountingCategories.xml" />
                <display:column titleKey="columnTitle.name" media="html" sortable="true" sortProperty="itemAccountingCategoryName">
                    <c:choose>
                        <c:when test="${includeReviewUrl}">
                            <c:url var="reviewUrl" value="/action/Accounting/ItemAccountingCategory/Review">
                                <c:param name="ItemAccountingCategoryName" value="${itemAccountingCategory.itemAccountingCategoryName}" />
                            </c:url>
                            <a href="${reviewUrl}"><c:out value="${itemAccountingCategory.itemAccountingCategoryName}" /></a>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${itemAccountingCategory.itemAccountingCategoryName}" />
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column titleKey="columnTitle.description" media="html" sortable="true" sortProperty="description">
                    <c:out value="${itemAccountingCategory.description}" />
                </display:column>
                <display:column property="sortOrder" titleKey="columnTitle.sortOrder" media="html" sortable="true" sortProperty="sortOrder"/>
                <display:column titleKey="columnTitle.default" media="html" sortable="true" sortProperty="isDefault">
                    <c:choose>
                        <c:when test="${itemAccountingCategory.isDefault}">
                            Default
                        </c:when>
                        <c:otherwise>
                            <et:hasSecurityRole securityRole="ItemAccountingCategory.Edit">
                                <c:url var="setDefaultUrl" value="/action/Accounting/ItemAccountingCategory/SetDefault">
                                    <c:param name="ItemAccountingCategoryName" value="${itemAccountingCategory.itemAccountingCategoryName}" />
                                </c:url>
                                <a href="${setDefaultUrl}">Set Default</a>
                            </et:hasSecurityRole>
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <et:hasSecurityRole securityRoles="ItemAccountingCategory.Edit:ItemAccountingCategory.Description:ItemAccountingCategory.Delete">
                    <display:column media="html">
                        <et:hasSecurityRole securityRole="ItemAccountingCategory.Edit">
                            <c:url var="editUrl" value="/action/Accounting/ItemAccountingCategory/Edit">
                                <c:param name="OriginalItemAccountingCategoryName" value="${itemAccountingCategory.itemAccountingCategoryName}" />
                            </c:url>
                            <a href="${editUrl}">Edit</a>
                        </et:hasSecurityRole>
                        <et:hasSecurityRole securityRole="ItemAccountingCategory.Description">
                            <c:url var="descriptionsUrl" value="/action/Accounting/ItemAccountingCategory/Description">
                                <c:param name="ItemAccountingCategoryName" value="${itemAccountingCategory.itemAccountingCategoryName}" />
                            </c:url>
                            <a href="${descriptionsUrl}">Descriptions</a>
                        </et:hasSecurityRole>
                        <et:hasSecurityRole securityRole="ItemAccountingCategory.Delete">
                            <c:url var="deleteUrl" value="/action/Accounting/ItemAccountingCategory/Delete">
                                <c:param name="ItemAccountingCategoryName" value="${itemAccountingCategory.itemAccountingCategoryName}" />
                            </c:url>
                            <a href="${deleteUrl}">Delete</a>
                        </et:hasSecurityRole>
                    </display:column>
                </et:hasSecurityRole>
                <et:hasSecurityRole securityRole="Event.List">
                    <display:column media="html">
                        <c:url var="eventsUrl" value="/action/Core/Event/Main">
                            <c:param name="EntityRef" value="${itemAccountingCategory.entityInstance.entityRef}" />
                        </c:url>
                        <a href="${eventsUrl}">Events</a>
                    </display:column>
                </et:hasSecurityRole>
                <display:column property="itemAccountingCategoryName" titleKey="columnTitle.name" media="csv excel pdf rtf xml" />
                <display:column property="description" titleKey="columnTitle.description" media="csv excel pdf rtf xml" />
                <display:column property="sortOrder" titleKey="columnTitle.sortOrder" media="csv excel pdf rtf xml" />
                <display:column property="isDefault" titleKey="columnTitle.default" media="csv excel pdf rtf xml" />
            </display:table>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
