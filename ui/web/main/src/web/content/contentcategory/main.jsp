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
        <title>Categories</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Content/Main" />">Content</a> &gt;&gt;
                <a href="<c:url value="/action/Content/ContentCollection/Main" />">Collections</a> &gt;&gt;
                <c:url var="contentCatalogsUrl" value="/action/Content/ContentCatalog/Main">
                    <c:param name="ContentCollectionName" value="${parentContentCategory.contentCatalog.contentCollection.contentCollectionName}" />
                </c:url>
                <a href="${contentCatalogsUrl}">Catalogs</a> &gt;&gt;
                Categories
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="Event.List:ContentCategory.Review" />
            <c:url var="addUrl" value="/action/Content/ContentCategory/Add">
                <c:param name="ContentCollectionName" value="${parentContentCategory.contentCatalog.contentCollection.contentCollectionName}" />
                <c:param name="ContentCatalogName" value="${parentContentCategory.contentCatalog.contentCatalogName}" />
                <c:param name="ParentContentCategoryName" value="${parentContentCategory.contentCategoryName}" />
            </c:url>
            <p><a href="${addUrl}">Add Category.</a></p>
            <et:hasSecurityRole securityRole="ContentCategory.Review" var="includeReviewUrl" />
            <c:choose>
                <c:when test="${contentCategoryCount == null || contentCategoryCount < 21}">
                    <display:table name="contentCategories.list" id="contentCategory" class="displaytag">
                        <display:column titleKey="columnTitle.name">
                            <c:choose>
                                <c:when test="${includeReviewUrl}">
                                    <c:url var="reviewUrl" value="/action/Content/ContentCategory/Review">
                                        <c:param name="ContentCollectionName" value="${contentCategory.contentCatalog.contentCollection.contentCollectionName}" />
                                        <c:param name="ContentCatalogName" value="${contentCategory.contentCatalog.contentCatalogName}" />
                                        <c:param name="ContentCategoryName" value="${contentCategory.contentCategoryName}" />
                                    </c:url>
                                    <a href="${reviewUrl}"><c:out value="${contentCategory.contentCategoryName}" /></a>
                                </c:when>
                                <c:otherwise>
                                    <c:out value="${contentCategory.contentCategoryName}" />
                                </c:otherwise>
                            </c:choose>
                        </display:column>
                        <display:column titleKey="columnTitle.description">
                            <c:out value="${contentCategory.description}" />
                        </display:column>
                        <display:column titleKey="columnTitle.defaultOfferUse">
                            <c:if test="${contentCategory.defaultOfferUse != null}">
                                <c:url var="reviewUrl" value="/action/Advertising/OfferUse/Review">
                                    <c:param name="OfferName" value="${contentCategory.defaultOfferUse.offer.offerName}" />
                                    <c:param name="UseName" value="${contentCategory.defaultOfferUse.use.useName}" />
                                </c:url>
                                <a href="${reviewUrl}"><c:out value="${contentCategory.defaultOfferUse.offer.description}:${contentCategory.defaultOfferUse.use.description}" /></a>
                            </c:if>
                        </display:column>
                        <display:column titleKey="columnTitle.itemSelector">
                            <c:if test="${contentCategory.contentCategoryItemSelector != null}">
                                <c:url var="reviewUrl" value="/action/Selector/Selector/Review">
                                    <c:param name="SelectorKindName" value="${contentCategory.contentCategoryItemSelector.selectorType.selectorKind.selectorKindName}" />
                                    <c:param name="SelectorTypeName" value="${contentCategory.contentCategoryItemSelector.selectorType.selectorTypeName}" />
                                    <c:param name="SelectorName" value="${contentCategory.contentCategoryItemSelector.selectorName}" />
                                </c:url>
                                <a href="${reviewUrl}"><c:out value="${contentCategory.contentCategoryItemSelector.description}" /></a>
                            </c:if>
                        </display:column>
                        <display:column property="sortOrder" titleKey="columnTitle.sortOrder" />
                        <display:column titleKey="columnTitle.default">
                            <c:choose>
                                <c:when test="${contentCategory.isDefault}">
                                    Default
                                </c:when>
                                <c:otherwise>
                                    <c:url var="setDefaultContentCategoryUrl" value="/action/Content/ContentCategory/SetDefault">
                                        <c:param name="ContentCollectionName" value="${contentCategory.contentCatalog.contentCollection.contentCollectionName}" />
                                        <c:param name="ContentCatalogName" value="${contentCategory.contentCatalog.contentCatalogName}" />
                                        <c:param name="ContentCategoryName" value="${contentCategory.contentCategoryName}" />
                                        <c:param name="ParentContentCategoryName" value="${contentCategory.parentContentCategory.contentCategoryName}" />
                                    </c:url>
                                    <a href="${setDefaultContentCategoryUrl}">Set Default</a>
                                </c:otherwise>
                            </c:choose>
                        </display:column>
                        <display:column>
                            <c:url var="contentCategoriesUrl" value="/action/Content/ContentCategory/Main">
                                <c:param name="ContentCollectionName" value="${contentCategory.contentCatalog.contentCollection.contentCollectionName}" />
                                <c:param name="ContentCatalogName" value="${contentCategory.contentCatalog.contentCatalogName}" />
                                <c:param name="ParentContentCategoryName" value="${contentCategory.contentCategoryName}" />
                            </c:url>
                            <a href="${contentCategoriesUrl}">Categories</a>
                            <c:url var="contentCategoryItemsUrl" value="/action/Content/ContentCategoryItem/Main">
                                <c:param name="ContentCollectionName" value="${contentCategory.contentCatalog.contentCollection.contentCollectionName}" />
                                <c:param name="ContentCatalogName" value="${contentCategory.contentCatalog.contentCatalogName}" />
                                <c:param name="ContentCategoryName" value="${contentCategory.contentCategoryName}" />
                            </c:url>
                            <a href="${contentCategoryItemsUrl}">Category Items</a><br />
                            <c:url var="editContentCategoryUrl" value="/action/Content/ContentCategory/Edit">
                                <c:param name="ContentCollectionName" value="${contentCategory.contentCatalog.contentCollection.contentCollectionName}" />
                                <c:param name="ContentCatalogName" value="${contentCategory.contentCatalog.contentCatalogName}" />
                                <c:param name="OriginalContentCategoryName" value="${contentCategory.contentCategoryName}" />
                                <c:param name="ParentContentCategoryName" value="${contentCategory.parentContentCategory.contentCategoryName}" />
                            </c:url>
                            <a href="${editContentCategoryUrl}">Edit</a>
                            <c:url var="contentCategoryDescriptionsUrl" value="/action/Content/ContentCategory/Description">
                                <c:param name="ContentCollectionName" value="${contentCategory.contentCatalog.contentCollection.contentCollectionName}" />
                                <c:param name="ContentCatalogName" value="${contentCategory.contentCatalog.contentCatalogName}" />
                                <c:param name="ContentCategoryName" value="${contentCategory.contentCategoryName}" />
                                <c:param name="ParentContentCategoryName" value="${contentCategory.parentContentCategory.contentCategoryName}" />
                            </c:url>
                            <a href="${contentCategoryDescriptionsUrl}">Descriptions</a>
                            <c:url var="deleteContentCategoryUrl" value="/action/Content/ContentCategory/Delete">
                                <c:param name="ContentCollectionName" value="${contentCategory.contentCatalog.contentCollection.contentCollectionName}" />
                                <c:param name="ContentCatalogName" value="${contentCategory.contentCatalog.contentCatalogName}" />
                                <c:param name="ContentCategoryName" value="${contentCategory.contentCategoryName}" />
                                <c:param name="ParentContentCategoryName" value="${contentCategory.parentContentCategory.contentCategoryName}" />
                            </c:url>
                            <a href="${deleteContentCategoryUrl}">Delete</a>
                        </display:column>
                        <et:hasSecurityRole securityRole="Event.List">
                            <display:column>
                                <c:url var="eventsUrl" value="/action/Core/Event/Main">
                                    <c:param name="EntityRef" value="${contentCategory.entityInstance.entityRef}" />
                                </c:url>
                                <a href="${eventsUrl}">Events</a>
                            </display:column>
                        </et:hasSecurityRole>
                    </display:table>
                    <c:if test="${contentCategories.size > 20}">
                        <c:url var="resultsUrl" value="/action/Content/ContentCategory/Main">
                            <c:param name="ContentCollectionName" value="${parentContentCategory.contentCatalog.contentCollection.contentCollectionName}" />
                            <c:param name="ContentCatalogName" value="${parentContentCategory.contentCatalog.contentCatalogName}" />
                            <c:param name="ParentContentCategoryName" value="${parentContentCategory.contentCategoryName}" />
                        </c:url>
                        <a href="${resultsUrl}">Paged Results</a>
                    </c:if>
                </c:when>
                <c:otherwise>
                    <display:table name="contentCategories.list" id="contentCategory" class="displaytag" partialList="true" pagesize="20" size="contentCategoryCount" requestURI="/action/Content/ContentCategory/Main">
                        <display:column titleKey="columnTitle.name">
                            <c:choose>
                                <c:when test="${includeReviewUrl}">
                                    <c:url var="reviewUrl" value="/action/Content/ContentCategory/Review">
                                        <c:param name="ContentCollectionName" value="${contentCategory.contentCatalog.contentCollection.contentCollectionName}" />
                                        <c:param name="ContentCatalogName" value="${contentCategory.contentCatalog.contentCatalogName}" />
                                        <c:param name="ContentCategoryName" value="${contentCategory.contentCategoryName}" />
                                        <c:param name="ParentContentCategoryName" value="${contentCategory.parentContentCategory.contentCategoryName}" />
                                    </c:url>
                                    <a href="${reviewUrl}"><c:out value="${contentCategory.contentCategoryName}" /></a>
                                </c:when>
                                <c:otherwise>
                                    <c:out value="${contentCategory.contentCategoryName}" />
                                </c:otherwise>
                            </c:choose>
                        </display:column>
                        <display:column titleKey="columnTitle.description">
                            <c:out value="${contentCategory.description}" />
                        </display:column>
                        <display:column titleKey="columnTitle.defaultOfferUse">
                            <c:if test="${contentCategory.defaultOfferUse != null}">
                                <c:url var="reviewUrl" value="/action/Advertising/OfferUse/Review">
                                    <c:param name="OfferName" value="${contentCategory.defaultOfferUse.offer.offerName}" />
                                    <c:param name="UseName" value="${contentCategory.defaultOfferUse.use.useName}" />
                                </c:url>
                                <a href="${reviewUrl}"><c:out value="${contentCategory.defaultOfferUse.offer.description}:${contentCategory.defaultOfferUse.use.description}" /></a>
                            </c:if>
                        </display:column>
                        <display:column titleKey="columnTitle.itemSelector">
                            <c:if test="${contentCategory.contentCategoryItemSelector != null}">
                                <c:url var="reviewUrl" value="/action/Selector/Selector/Review">
                                    <c:param name="SelectorKindName" value="${contentCategory.contentCategoryItemSelector.selectorType.selectorKind.selectorKindName}" />
                                    <c:param name="SelectorTypeName" value="${contentCategory.contentCategoryItemSelector.selectorType.selectorTypeName}" />
                                    <c:param name="SelectorName" value="${contentCategory.contentCategoryItemSelector.selectorName}" />
                                </c:url>
                                <a href="${reviewUrl}"><c:out value="${contentCategory.contentCategoryItemSelector.description}" /></a>
                            </c:if>
                        </display:column>
                        <display:column property="sortOrder" titleKey="columnTitle.sortOrder" />
                        <display:column titleKey="columnTitle.default">
                            <c:choose>
                                <c:when test="${contentCategory.isDefault}">
                                    Default
                                </c:when>
                                <c:otherwise>
                                    <c:url var="setDefaultContentCategoryUrl" value="/action/Content/ContentCategory/SetDefault">
                                        <c:param name="ContentCollectionName" value="${contentCategory.contentCatalog.contentCollection.contentCollectionName}" />
                                        <c:param name="ContentCatalogName" value="${contentCategory.contentCatalog.contentCatalogName}" />
                                        <c:param name="ContentCategoryName" value="${contentCategory.contentCategoryName}" />
                                        <c:param name="ParentContentCategoryName" value="${contentCategory.parentContentCategory.contentCategoryName}" />
                                    </c:url>
                                    <a href="${setDefaultContentCategoryUrl}">Set Default</a>
                                </c:otherwise>
                            </c:choose>
                        </display:column>
                        <display:column>
                            <c:url var="contentCategoriesUrl" value="/action/Content/ContentCategory/Main">
                                <c:param name="ContentCollectionName" value="${contentCategory.contentCatalog.contentCollection.contentCollectionName}" />
                                <c:param name="ContentCatalogName" value="${contentCategory.contentCatalog.contentCatalogName}" />
                                <c:param name="ParentContentCategoryName" value="${contentCategory.contentCategoryName}" />
                            </c:url>
                            <a href="${contentCategoriesUrl}">Categories</a>
                            <c:url var="contentCategoryItemsUrl" value="/action/Content/ContentCategoryItem/Main">
                                <c:param name="ContentCollectionName" value="${contentCategory.contentCatalog.contentCollection.contentCollectionName}" />
                                <c:param name="ContentCatalogName" value="${contentCategory.contentCatalog.contentCatalogName}" />
                                <c:param name="ContentCategoryName" value="${contentCategory.contentCategoryName}" />
                            </c:url>
                            <a href="${contentCategoryItemsUrl}">Category Items</a><br />
                            <c:url var="editContentCategoryUrl" value="/action/Content/ContentCategory/Edit">
                                <c:param name="ContentCollectionName" value="${contentCategory.contentCatalog.contentCollection.contentCollectionName}" />
                                <c:param name="ContentCatalogName" value="${contentCategory.contentCatalog.contentCatalogName}" />
                                <c:param name="OriginalContentCategoryName" value="${contentCategory.contentCategoryName}" />
                                <c:param name="ParentContentCategoryName" value="${contentCategory.parentContentCategory.contentCategoryName}" />
                            </c:url>
                            <a href="${editContentCategoryUrl}">Edit</a>
                            <c:url var="contentCategoryDescriptionsUrl" value="/action/Content/ContentCategory/Description">
                                <c:param name="ContentCollectionName" value="${contentCategory.contentCatalog.contentCollection.contentCollectionName}" />
                                <c:param name="ContentCatalogName" value="${contentCategory.contentCatalog.contentCatalogName}" />
                                <c:param name="ContentCategoryName" value="${contentCategory.contentCategoryName}" />
                                <c:param name="ParentContentCategoryName" value="${contentCategory.parentContentCategory.contentCategoryName}" />
                            </c:url>
                            <a href="${contentCategoryDescriptionsUrl}">Descriptions</a>
                            <c:url var="deleteContentCategoryUrl" value="/action/Content/ContentCategory/Delete">
                                <c:param name="ContentCollectionName" value="${contentCategory.contentCatalog.contentCollection.contentCollectionName}" />
                                <c:param name="ContentCatalogName" value="${contentCategory.contentCatalog.contentCatalogName}" />
                                <c:param name="ContentCategoryName" value="${contentCategory.contentCategoryName}" />
                                <c:param name="ParentContentCategoryName" value="${contentCategory.parentContentCategory.contentCategoryName}" />
                            </c:url>
                            <a href="${deleteContentCategoryUrl}">Delete</a>
                        </display:column>
                        <et:hasSecurityRole securityRole="Event.List">
                            <display:column>
                                <c:url var="eventsUrl" value="/action/Core/Event/Main">
                                    <c:param name="EntityRef" value="${contentCategory.entityInstance.entityRef}" />
                                </c:url>
                                <a href="${eventsUrl}">Events</a>
                            </display:column>
                        </et:hasSecurityRole>
                    </display:table>
                    <c:url var="resultsUrl" value="/action/Content/ContentCategory/Main">
                        <c:param name="ContentCollectionName" value="${parentContentCategory.contentCatalog.contentCollection.contentCollectionName}" />
                        <c:param name="ContentCatalogName" value="${parentContentCategory.contentCatalog.contentCatalogName}" />
                        <c:param name="ParentContentCategoryName" value="${parentContentCategory.contentCategoryName}" />
                        <c:param name="Results" value="Complete" />
                    </c:url>
                    <a href="${resultsUrl}">All Results</a>
                </c:otherwise>
            </c:choose>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
