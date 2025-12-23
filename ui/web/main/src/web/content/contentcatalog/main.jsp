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
        <title>Catalogs</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Content/Main" />">Content</a> &gt;&gt;
                <a href="<c:url value="/action/Content/ContentCollection/Main" />">Collections</a> &gt;&gt;
                Catalogs
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="Event.List:ContentCatalog.Review" />
            <c:url var="addContentCatalogUrl" value="/action/Content/ContentCatalog/Add">
                <c:param name="ContentCollectionName" value="${contentCollection.contentCollectionName}" />
            </c:url>
            <p><a href="${addContentCatalogUrl}">Add Catalog.</a></p>
            <et:hasSecurityRole securityRole="ContentCatalog.Review" var="includeReviewUrl" />
            <display:table name="contentCatalogs" id="contentCatalog" class="displaytag">
                <display:column titleKey="columnTitle.name">
                    <c:choose>
                        <c:when test="${includeReviewUrl}">
                            <c:url var="reviewUrl" value="/action/Content/ContentCatalog/Review">
                                <c:param name="ContentCollectionName" value="${contentCatalog.contentCollection.contentCollectionName}" />
                                <c:param name="ContentCatalogName" value="${contentCatalog.contentCatalogName}" />
                            </c:url>
                            <a href="${reviewUrl}"><c:out value="${contentCatalog.contentCatalogName}" /></a>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${contentCatalog.contentCatalogName}" />
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column titleKey="columnTitle.description">
                    <c:out value="${contentCatalog.description}" />
                </display:column>
                <display:column titleKey="columnTitle.defaultOfferUse">
                    <c:url var="reviewUrl" value="/action/Advertising/OfferUse/Review">
                        <c:param name="OfferName" value="${contentCatalog.defaultOfferUse.offer.offerName}" />
                        <c:param name="UseName" value="${contentCatalog.defaultOfferUse.use.useName}" />
                    </c:url>
                    <a href="${reviewUrl}"><c:out value="${contentCatalog.defaultOfferUse.offer.description}:${contentCatalog.defaultOfferUse.use.description}" /></a>
                </display:column>
                <display:column property="sortOrder" titleKey="columnTitle.sortOrder" />
                <display:column titleKey="columnTitle.default">
                    <c:choose>
                        <c:when test="${contentCatalog.isDefault}">
                            Default
                        </c:when>
                        <c:otherwise>
                            <c:url var="setDefaultContentCatalogUrl" value="/action/Content/ContentCatalog/SetDefault">
                                <c:param name="ContentCollectionName" value="${contentCatalog.contentCollection.contentCollectionName}" />
                                <c:param name="ContentCatalogName" value="${contentCatalog.contentCatalogName}" />
                            </c:url>
                            <a href="${setDefaultContentCatalogUrl}">Set Default</a>
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column>
                    <c:url var="contentCategoriesUrl" value="/action/Content/ContentCategory/Main">
                        <c:param name="ContentCollectionName" value="${contentCatalog.contentCollection.contentCollectionName}" />
                        <c:param name="ContentCatalogName" value="${contentCatalog.contentCatalogName}" />
                        <c:param name="ParentContentCategoryName" value="ROOT" />
                    </c:url>
                    <a href="${contentCategoriesUrl}">Categories</a>
                    <c:url var="contentCatalogItemsUrl" value="/action/Content/ContentCatalogItem/Main">
                        <c:param name="ContentCollectionName" value="${contentCatalog.contentCollection.contentCollectionName}" />
                        <c:param name="ContentCatalogName" value="${contentCatalog.contentCatalogName}" />
                    </c:url>
                    <a href="${contentCatalogItemsUrl}">Items</a><br />
                    <c:url var="editContentCatalogUrl" value="/action/Content/ContentCatalog/Edit">
                        <c:param name="ContentCollectionName" value="${contentCatalog.contentCollection.contentCollectionName}" />
                        <c:param name="OriginalContentCatalogName" value="${contentCatalog.contentCatalogName}" />
                    </c:url>
                    <a href="${editContentCatalogUrl}">Edit</a>
                    <c:url var="contentCatalogDescriptionsUrl" value="/action/Content/ContentCatalog/Description">
                        <c:param name="ContentCollectionName" value="${contentCatalog.contentCollection.contentCollectionName}" />
                        <c:param name="ContentCatalogName" value="${contentCatalog.contentCatalogName}" />
                    </c:url>
                    <a href="${contentCatalogDescriptionsUrl}">Descriptions</a>
                    <c:url var="deleteContentCatalogUrl" value="/action/Content/ContentCatalog/Delete">
                        <c:param name="ContentCollectionName" value="${contentCatalog.contentCollection.contentCollectionName}" />
                        <c:param name="ContentCatalogName" value="${contentCatalog.contentCatalogName}" />
                    </c:url>
                    <a href="${deleteContentCatalogUrl}">Delete</a>
                </display:column>
                <et:hasSecurityRole securityRole="Event.List">
                    <display:column>
                        <c:url var="eventsUrl" value="/action/Core/Event/Main">
                            <c:param name="EntityRef" value="${contentCatalog.entityInstance.entityRef}" />
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
