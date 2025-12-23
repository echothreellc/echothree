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
        <title>Collections</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Content/Main" />">Content</a> &gt;&gt;
                Collections
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="Event.List:ContentCollection.Create:ContentCollection.Edit:ContentCollection.Delete:ContentCollection.Review:ContentCollection.Description:ContentSection.List:ContentCatalog.List:ContentForum.List" />
            <et:hasSecurityRole securityRole="ContentCollection.Create">
                <p><a href="<c:url value="/action/Content/ContentCollection/Add" />">Add Collection.</a></p>
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRole="ContentCollection.Review" var="includeReviewUrl" />
            <display:table name="contentCollections" id="contentCollection" class="displaytag">
                <display:column titleKey="columnTitle.name">
                    <c:choose>
                        <c:when test="${includeReviewUrl}">
                            <c:url var="reviewUrl" value="/action/Content/ContentCollection/Review">
                                <c:param name="ContentCollectionName" value="${contentCollection.contentCollectionName}" />
                            </c:url>
                            <a href="${reviewUrl}"><c:out value="${contentCollection.contentCollectionName}" /></a>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${contentCollection.contentCollectionName}" />
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column titleKey="columnTitle.description">
                    <c:out value="${contentCollection.description}" />
                </display:column>
                <display:column titleKey="columnTitle.defaultOfferUse">
                    <c:url var="reviewUrl" value="/action/Advertising/OfferUse/Review">
                        <c:param name="OfferName" value="${contentCollection.defaultOfferUse.offer.offerName}" />
                        <c:param name="UseName" value="${contentCollection.defaultOfferUse.use.useName}" />
                    </c:url>
                    <a href="${reviewUrl}"><c:out value="${contentCollection.defaultOfferUse.offer.description}:${contentCollection.defaultOfferUse.use.description}" /></a>
                </display:column>
                <et:hasSecurityRole securityRoles="ContentSection.List:ContentCatalog.List:ContentForum.List:ContentCollection.Edit:ContentCollection.Delete:ContentCollection.Description">
                    <display:column>
                        <et:hasSecurityRole securityRoles="ContentSection.List:ContentCatalog.List:ContentForum.List">
                            <et:hasSecurityRole securityRole="ContentSection.List">
                                <c:url var="contentSectionsUrl" value="/action/Content/ContentSection/Main">
                                    <c:param name="ContentCollectionName" value="${contentCollection.contentCollectionName}" />
                                    <c:param name="ParentContentSectionName" value="ROOT" />
                                </c:url>
                                <a href="${contentSectionsUrl}">Sections</a>
                            </et:hasSecurityRole>
                            <et:hasSecurityRole securityRole="ContentCatalog.List">
                                <c:url var="contentCatalogsUrl" value="/action/Content/ContentCatalog/Main">
                                    <c:param name="ContentCollectionName" value="${contentCollection.contentCollectionName}" />
                                </c:url>
                                <a href="${contentCatalogsUrl}">Catalogs</a>
                            </et:hasSecurityRole>
                            <et:hasSecurityRole securityRole="ContentForum.List">
                                <c:url var="contentForumsUrl" value="/action/Content/ContentForum/Main">
                                    <c:param name="ContentCollectionName" value="${contentCollection.contentCollectionName}" />
                                </c:url>
                                <a href="${contentForumsUrl}">Forums</a>
                            </et:hasSecurityRole>
                            <br />
                        </et:hasSecurityRole>
                        <et:hasSecurityRole securityRoles="ContentCollection.Edit:ContentCollection.Delete:ContentCollection.Description">
                            <et:hasSecurityRole securityRole="ContentCollection.Edit">
                                <c:url var="editContentCollectionUrl" value="/action/Content/ContentCollection/Edit">
                                    <c:param name="OriginalContentCollectionName" value="${contentCollection.contentCollectionName}" />
                                </c:url>
                            <a href="${editContentCollectionUrl}">Edit</a>
                            </et:hasSecurityRole>
                            <et:hasSecurityRole securityRole="ContentCollection.Description">
                                <c:url var="contentCollectionDescriptionsUrl" value="/action/Content/ContentCollection/Description">
                                    <c:param name="ContentCollectionName" value="${contentCollection.contentCollectionName}" />
                                </c:url>
                                <a href="${contentCollectionDescriptionsUrl}">Descriptions</a>
                            </et:hasSecurityRole>
                            <et:hasSecurityRole securityRole="ContentCollection.Delete">
                                <c:url var="deleteContentCollectionUrl" value="/action/Content/ContentCollection/Delete">
                                    <c:param name="ContentCollectionName" value="${contentCollection.contentCollectionName}" />
                                </c:url>
                                <a href="${deleteContentCollectionUrl}">Delete</a>
                            </et:hasSecurityRole>
                        </et:hasSecurityRole>
                    </display:column>
                </et:hasSecurityRole>
                <et:hasSecurityRole securityRole="Event.List">
                    <display:column>
                        <c:url var="eventsUrl" value="/action/Core/Event/Main">
                            <c:param name="EntityRef" value="${contentCollection.entityInstance.entityRef}" />
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
