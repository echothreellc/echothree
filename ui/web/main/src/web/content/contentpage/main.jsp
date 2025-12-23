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
        <title>Pages</title>
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
                Pages
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="Event.List:ContentPage.Create:ContentPage.Edit:ContentPage.Delete:ContentPage.Review:ContentPage.Description:ContentPageArea.List" />
            <et:hasSecurityRole securityRole="ContentPage.Create">
                <c:url var="addUrl" value="/action/Content/ContentPage/Add">
                    <c:param name="ContentCollectionName" value="${contentCollectionName}" />
                    <c:param name="ParentContentSectionName" value="${parentContentSectionName}" />
                    <c:param name="ContentSectionName" value="${contentSectionName}" />
                </c:url>
                <p><a href="${addUrl}">Add Page.</a></p>
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRole="ContentPage.Review" var="includeReviewUrl" />
            <display:table name="contentPages" id="contentPage" class="displaytag">
                <display:column titleKey="columnTitle.name">
                    <c:choose>
                        <c:when test="${includeReviewUrl}">
                            <c:url var="reviewUrl" value="/action/Content/ContentPage/Review">
                                <c:param name="ContentCollectionName" value="${contentPage.contentSection.contentCollection.contentCollectionName}" />
                                <c:param name="ContentSectionName" value="${contentPage.contentSection.contentSectionName}" />
                                <c:param name="ContentPageName" value="${contentPage.contentPageName}" />
                                <c:param name="ParentContentSectionName" value="${contentPage.contentSection.parentContentSection.contentSectionName}" />
                            </c:url>
                            <a href="${reviewUrl}"><c:out value="${contentPage.contentPageName}" /></a>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${contentPage.contentPageName}" />
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column titleKey="columnTitle.description">
                    <c:out value="${contentPage.description}" />
                </display:column>
                <display:column property="sortOrder" titleKey="columnTitle.sortOrder" />
                <display:column titleKey="columnTitle.default">
                    <c:choose>
                        <c:when test="${contentPage.isDefault}">
                            Default
                        </c:when>
                        <c:otherwise>
                            <et:hasSecurityRole securityRole="ContentPage.Edit">
                                <c:url var="setDefaultContentPageUrl" value="/action/Content/ContentPage/SetDefault">
                                    <c:param name="ContentCollectionName" value="${contentPage.contentSection.contentCollection.contentCollectionName}" />
                                    <c:param name="ContentSectionName" value="${contentPage.contentSection.contentSectionName}" />
                                    <c:param name="ContentPageName" value="${contentPage.contentPageName}" />
                                    <c:param name="ParentContentSectionName" value="${contentPage.contentSection.parentContentSection.contentSectionName}" />
                                </c:url>
                                <a href="${setDefaultContentPageUrl}">Set Default</a>
                            </et:hasSecurityRole>
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <et:hasSecurityRole securityRoles="ContentPageArea.List:ContentPage.Edit:ContentPage.Delete:ContentPage.Description">
                    <display:column>
                        <et:hasSecurityRole securityRoles="ContentPageArea.List">
                            <c:url var="contentPageAreasUrl" value="/action/Content/ContentPageArea/Main">
                                <c:param name="ContentCollectionName" value="${contentPage.contentSection.contentCollection.contentCollectionName}" />
                                <c:param name="ContentSectionName" value="${contentPage.contentSection.contentSectionName}" />
                                <c:param name="ContentPageName" value="${contentPage.contentPageName}" />
                                <c:param name="ParentContentSectionName" value="${contentPage.contentSection.parentContentSection.contentSectionName}" />
                            </c:url>
                            <a href="${contentPageAreasUrl}">Page Areas</a>
                            <br />
                        </et:hasSecurityRole>
                        <et:hasSecurityRole securityRoles="ContentPage.Edit:ContentPage.Delete:ContentPage.Description">
                            <et:hasSecurityRole securityRole="ContentPage.Edit">
                                <c:url var="editContentPageUrl" value="/action/Content/ContentPage/Edit">
                                    <c:param name="ContentCollectionName" value="${contentPage.contentSection.contentCollection.contentCollectionName}" />
                                    <c:param name="ContentSectionName" value="${contentPage.contentSection.contentSectionName}" />
                                    <c:param name="OriginalContentPageName" value="${contentPage.contentPageName}" />
                                    <c:param name="ParentContentSectionName" value="${contentPage.contentSection.parentContentSection.contentSectionName}" />
                                </c:url>
                                <a href="${editContentPageUrl}">Edit</a>
                            </et:hasSecurityRole>
                            <et:hasSecurityRole securityRole="ContentPage.Description">
                                <c:url var="contentPageDescriptionsUrl" value="/action/Content/ContentPage/Description">
                                    <c:param name="ContentCollectionName" value="${contentPage.contentSection.contentCollection.contentCollectionName}" />
                                    <c:param name="ContentSectionName" value="${contentPage.contentSection.contentSectionName}" />
                                    <c:param name="ContentPageName" value="${contentPage.contentPageName}" />
                                    <c:param name="ParentContentSectionName" value="${contentPage.contentSection.parentContentSection.contentSectionName}" />
                                </c:url>
                                <a href="${contentPageDescriptionsUrl}">Descriptions</a>
                            </et:hasSecurityRole>
                            <et:hasSecurityRole securityRole="ContentPage.Delete">
                                <c:url var="deleteContentPageUrl" value="/action/Content/ContentPage/Delete">
                                    <c:param name="ContentCollectionName" value="${contentPage.contentSection.contentCollection.contentCollectionName}" />
                                    <c:param name="ContentSectionName" value="${contentPage.contentSection.contentSectionName}" />
                                    <c:param name="ContentPageName" value="${contentPage.contentPageName}" />
                                    <c:param name="ParentContentSectionName" value="${contentPage.contentSection.parentContentSection.contentSectionName}" />
                                </c:url>
                                <a href="${deleteContentPageUrl}">Delete</a>
                            </et:hasSecurityRole>
                        </et:hasSecurityRole>
                    </display:column>
                </et:hasSecurityRole>
                <et:hasSecurityRole securityRole="Event.List">
                    <display:column>
                        <c:url var="eventsUrl" value="/action/Core/Event/Main">
                            <c:param name="EntityRef" value="${contentPage.entityInstance.entityRef}" />
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
