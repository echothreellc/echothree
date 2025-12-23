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
        <title>Sections</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Content/Main" />">Content</a> &gt;&gt;
                <a href="<c:url value="/action/Content/ContentCollection/Main" />">Collections</a> &gt;&gt;
                Sections
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="Event.List:ContentSection.Create:ContentSection.Edit:ContentSection.Delete:ContentSection.Review:ContentSection.Description:ContentSection.List:ContentPage.List" />
            <et:hasSecurityRole securityRole="ContentSection.Create">
                <c:url var="addUrl" value="/action/Content/ContentSection/Add">
                    <c:param name="ContentCollectionName" value="${contentCollectionName}" />
                    <c:param name="ParentContentSectionName" value="${parentContentSectionName}" />
                </c:url>
                <p><a href="${addUrl}">Add Section.</a></p>
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRole="ContentSection.Review" var="includeReviewUrl" />
            <display:table name="contentSections" id="contentSection" class="displaytag">
                <display:column titleKey="columnTitle.name">
                    <c:choose>
                        <c:when test="${includeReviewUrl}">
                            <c:url var="reviewUrl" value="/action/Content/ContentSection/Review">
                                <c:param name="ContentCollectionName" value="${contentSection.contentCollection.contentCollectionName}" />
                                <c:param name="ContentSectionName" value="${contentSection.contentSectionName}" />
                            </c:url>
                            <a href="${reviewUrl}"><c:out value="${contentSection.contentSectionName}" /></a>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${contentSection.contentSectionName}" />
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column titleKey="columnTitle.description">
                    <c:out value="${contentSection.description}" />
                </display:column>
                <display:column property="sortOrder" titleKey="columnTitle.sortOrder" />
                <display:column titleKey="columnTitle.default">
                    <c:choose>
                        <c:when test="${contentSection.isDefault}">
                            Default
                        </c:when>
                        <c:otherwise>
                            <et:hasSecurityRole securityRole="ContentSection.Edit">
                                <c:url var="setDefaultContentSectionUrl" value="/action/Content/ContentSection/SetDefault">
                                    <c:param name="ContentCollectionName" value="${contentSection.contentCollection.contentCollectionName}" />
                                    <c:param name="ContentSectionName" value="${contentSection.contentSectionName}" />
                                    <c:param name="ParentContentSectionName" value="${contentSection.parentContentSection.contentSectionName}" />
                                </c:url>
                                <a href="${setDefaultContentSectionUrl}">Set Default</a>
                            </et:hasSecurityRole>
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <et:hasSecurityRole securityRoles="ContentSection.List:ContentPage.List:ContentSection.Edit:ContentSection.Delete:ContentSection.Description">
                    <display:column>
                        <et:hasSecurityRole securityRoles="ContentSection.List:ContentPage.List">
                            <et:hasSecurityRole securityRole="ContentSection.List">
                                <c:url var="contentSectionsUrl" value="/action/Content/ContentSection/Main">
                                    <c:param name="ContentCollectionName" value="${contentSection.contentCollection.contentCollectionName}" />
                                    <c:param name="ParentContentSectionName" value="${contentSection.contentSectionName}" />
                                </c:url>
                                <a href="${contentSectionsUrl}">Sections</a>
                            </et:hasSecurityRole>
                            <et:hasSecurityRole securityRole="ContentPage.List">
                                <c:url var="contentPagesUrl" value="/action/Content/ContentPage/Main">
                                    <c:param name="ContentCollectionName" value="${contentSection.contentCollection.contentCollectionName}" />
                                    <c:param name="ContentSectionName" value="${contentSection.contentSectionName}" />
                                    <c:param name="ParentContentSectionName" value="${contentSection.parentContentSection.contentSectionName}" />
                                </c:url>
                                <a href="${contentPagesUrl}">Pages</a>
                            </et:hasSecurityRole>
                            <br />
                        </et:hasSecurityRole>
                        <et:hasSecurityRole securityRoles="ContentSection.Edit:ContentSection.Delete:ContentSection.Description">
                            <et:hasSecurityRole securityRole="ContentSection.Edit">
                                <c:url var="editContentSectionUrl" value="/action/Content/ContentSection/Edit">
                                    <c:param name="ContentCollectionName" value="${contentSection.contentCollection.contentCollectionName}" />
                                    <c:param name="OriginalContentSectionName" value="${contentSection.contentSectionName}" />
                                    <c:param name="ParentContentSectionName" value="${contentSection.parentContentSection.contentSectionName}" />
                                </c:url>
                                <a href="${editContentSectionUrl}">Edit</a>
                            </et:hasSecurityRole>
                            <et:hasSecurityRole securityRole="ContentSection.Description">
                                <c:url var="contentSectionDescriptionsUrl" value="/action/Content/ContentSection/Description">
                                    <c:param name="ContentCollectionName" value="${contentSection.contentCollection.contentCollectionName}" />
                                    <c:param name="ContentSectionName" value="${contentSection.contentSectionName}" />
                                    <c:param name="ParentContentSectionName" value="${contentSection.parentContentSection.contentSectionName}" />
                                </c:url>
                                <a href="${contentSectionDescriptionsUrl}">Descriptions</a>
                            </et:hasSecurityRole>
                            <et:hasSecurityRole securityRole="ContentSection.Delete">
                                <c:url var="deleteContentSectionUrl" value="/action/Content/ContentSection/Delete">
                                    <c:param name="ContentCollectionName" value="${contentSection.contentCollection.contentCollectionName}" />
                                    <c:param name="ContentSectionName" value="${contentSection.contentSectionName}" />
                                    <c:param name="ParentContentSectionName" value="${contentSection.parentContentSection.contentSectionName}" />
                                </c:url>
                                <a href="${deleteContentSectionUrl}">Delete</a>
                            </et:hasSecurityRole>
                        </et:hasSecurityRole>
                    </display:column>
                </et:hasSecurityRole>
                <et:hasSecurityRole securityRole="Event.List">
                    <display:column>
                        <c:url var="eventsUrl" value="/action/Core/Event/Main">
                            <c:param name="EntityRef" value="${contentSection.entityInstance.entityRef}" />
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
