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
        <title>
            <fmt:message key="pageTitle.relatedItems">
                <fmt:param value="${relatedItem.fromItem.itemName}" />
            </fmt:message>
        </title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Item/Main" />"><fmt:message key="navigation.items" /></a> &gt;&gt;
                <a href="<c:url value="/action/Item/Item/Main" />"><fmt:message key="navigation.search" /></a> &gt;&gt;
                <et:countItemResults searchTypeName="ITEM_MAINTENANCE" countVar="itemResultsCount" commandResultVar="countItemResultsCommandResult" logErrors="false" />
                <c:if test="${itemResultsCount > 0}">
                    <a href="<c:url value="/action/Item/Item/Result" />"><fmt:message key="navigation.results" /></a> &gt;&gt;
                </c:if>
                <c:url var="reviewUrl" value="/action/Item/Item/Review">
                    <c:param name="ItemName" value="${item.itemName}" />
                </c:url>
                <a href="${reviewUrl}">
                    <fmt:message key="navigation.item">
                        <fmt:param value="${item.itemName}" />
                    </fmt:message>
                </a> &gt;&gt;
                <fmt:message key="navigation.relatedItems" />
            </h2>
        </div>
        <div id="Content">
            <et:relatedItemTypes var="relatedItemTypes" />
            <c:forEach var="relatedItemType" items="${relatedItemTypes.list}">
                <h2><c:out value="${relatedItemType.description}" /></h2>
                <c:url var="addUrl" value="/action/Item/RelatedItem/Add">
                    <c:param name="RelatedItemTypeName" value="${relatedItemType.relatedItemTypeName}" />
                    <c:param name="FromItemName" value="${item.itemName}" />
                </c:url>
                <p><a href="${addUrl}">Add Related Item.</a></p>
                <c:set var="relatedItems" scope="request" value="${item.relatedItems.map[relatedItemType.relatedItemTypeName]}" />
                <c:choose>
                    <c:when test='${relatedItems.size == 0}'>
                        <br />
                    </c:when>
                    <c:otherwise>
                        <display:table name="relatedItems.list" id="relatedItem" class="displaytag">
                            <display:column>
                                <c:url var="reviewUrl" value="/action/Item/RelatedItem/Review">
                                    <c:param name="RelatedItemTypeName" value="${relatedItem.relatedItemType.relatedItemTypeName}" />
                                    <c:param name="FromItemName" value="${relatedItem.fromItem.itemName}" />
                                    <c:param name="ToItemName" value="${relatedItem.toItem.itemName}" />
                                </c:url>
                                <a href="${reviewUrl}">Review</a>
                            </display:column>
                            <display:column titleKey="columnTitle.name">
                                <c:url var="toItemReviewUrl" value="/action/Item/Item/Review">
                                    <c:param name="ItemName" value="${relatedItem.toItem.itemName}" />
                                </c:url>
                                <a href="${toItemReviewUrl}"><c:out value="${relatedItem.toItem.description}" /></a>
                            </display:column>
                            <display:column titleKey="columnTitle.sortOrder">
                                <c:out value="${relatedItem.sortOrder}" />
                            </display:column>
                            <display:column>
                                <c:url var="editUrl" value="/action/Item/RelatedItem/Edit">
                                    <c:param name="RelatedItemTypeName" value="${relatedItem.relatedItemType.relatedItemTypeName}" />
                                    <c:param name="FromItemName" value="${relatedItem.fromItem.itemName}" />
                                    <c:param name="ToItemName" value="${relatedItem.toItem.itemName}" />
                                </c:url>
                                <a href="${editUrl}">Edit</a>
                                <c:url var="deleteUrl" value="/action/Item/RelatedItem/Delete">
                                    <c:param name="RelatedItemTypeName" value="${relatedItem.relatedItemType.relatedItemTypeName}" />
                                    <c:param name="FromItemName" value="${relatedItem.fromItem.itemName}" />
                                    <c:param name="ToItemName" value="${relatedItem.toItem.itemName}" />
                                </c:url>
                                <a href="${deleteUrl}">Delete</a>
                            </display:column>
                        </display:table>
                    </c:otherwise>
                </c:choose>
                <br />
            </c:forEach>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
