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
        <title>Pages Areas</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Content/Main" />">Content</a> &gt;&gt;
                <a href="<c:url value="/action/Content/ContentCollection/Main" />">Collections</a> &gt;&gt;
                <c:url var="contentSectionsUrl" value="/action/Content/ContentSection/Main">
                    <c:param name="ContentCollectionName" value="${contentCollectionName}" />
                    <c:param name="ParentContentSectionName" value="${parentContentSectionName}" />
                </c:url>
                <a href="${contentSectionsUrl}">Sections</a> &gt;&gt;
                <c:url var="contentPagesUrl" value="/action/Content/ContentPage/Main">
                    <c:param name="ContentCollectionName" value="${contentCollectionName}" />
                    <c:param name="ContentSectionName" value="${contentSectionName}" />
                    <c:param name="ParentContentSectionName" value="${parentContentSectionName}" />
                </c:url>
                <a href="${contentPagesUrl}">Pages</a> &gt;&gt;
                Page Areas
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="ContentPageArea.Create:ContentPageArea.Edit:ContentPageArea.Delete" />
            <et:hasSecurityRole securityRole="ContentPageArea.Create">
                <p>
                    <c:forEach var="contentPageLayoutArea" items="${contentPageLayoutAreas}">
                        <c:url var="addUrl" value="/action/Content/ContentPageArea/Add">
                            <c:param name="ContentCollectionName" value="${contentCollectionName}" />
                            <c:param name="ContentSectionName" value="${contentSectionName}" />
                            <c:param name="ContentPageName" value="${contentPageName}" />
                            <c:param name="SortOrder" value="${contentPageLayoutArea.sortOrder}" />
                            <c:param name="ParentContentSectionName" value="${parentContentSectionName}" />
                        </c:url>
                        <a href="${addUrl}">Add <c:out value="${contentPageLayoutArea.description}" />.</a><br />
                    </c:forEach>
                </p>
            </et:hasSecurityRole>
            <display:table name="contentPageAreas" id="contentPageArea" class="displaytag">
                <display:column titleKey="columnTitle.pageAreaDescription">
                    <c:out value="${contentPageArea.contentPageLayoutArea.description}" />
                </display:column>
                <display:column titleKey="columnTitle.language">
                    <c:out value="${contentPageArea.language.description}" />
                </display:column>
                <et:hasSecurityRole securityRoles="ContentPageArea.Edit:ContentPageArea.Delete">
                    <display:column>
                        <et:hasSecurityRole securityRole="ContentPageArea.Edit">
                            <c:url var="editUrl" value="/action/Content/ContentPageArea/Edit">
                                <c:param name="ContentCollectionName" value="${contentPageArea.contentPage.contentSection.contentCollection.contentCollectionName}" />
                                <c:param name="ContentSectionName" value="${contentPageArea.contentPage.contentSection.contentSectionName}" />
                                <c:param name="ContentPageName" value="${contentPageArea.contentPage.contentPageName}" />
                                <c:param name="SortOrder" value="${contentPageArea.contentPageLayoutArea.sortOrder}" />
                                <c:param name="LanguageIsoName" value="${contentPageArea.language.languageIsoName}" />
                                <c:param name="ParentContentSectionName" value="${contentPageArea.contentPage.contentSection.parentContentSection.contentSectionName}" />
                            </c:url>
                            <a href="${editUrl}">Edit</a>
                        </et:hasSecurityRole>
                        <et:hasSecurityRole securityRole="ContentPageArea.Delete">
                            <c:url var="deleteUrl" value="/action/Content/ContentPageArea/Delete">
                                <c:param name="ContentCollectionName" value="${contentPageArea.contentPage.contentSection.contentCollection.contentCollectionName}" />
                                <c:param name="ContentSectionName" value="${contentPageArea.contentPage.contentSection.contentSectionName}" />
                                <c:param name="ContentPageName" value="${contentPageArea.contentPage.contentPageName}" />
                                <c:param name="SortOrder" value="${contentPageArea.contentPageLayoutArea.sortOrder}" />
                                <c:param name="LanguageIsoName" value="${contentPageArea.language.languageIsoName}" />
                                <c:param name="ParentContentSectionName" value="${contentPageArea.contentPage.contentSection.parentContentSection.contentSectionName}" />
                            </c:url>
                            <a href="${deleteUrl}">Delete</a>
                        </et:hasSecurityRole>
                    </display:column>
                </et:hasSecurityRole>
            </display:table>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
