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
            <fmt:message key="pageTitle.relatedItem">
                <fmt:param value="${relatedItem.relatedItemType.relatedItemTypeName}" />
                <fmt:param value="${relatedItem.fromItem.itemName}" />
                <fmt:param value="${relatedItem.toItem.itemName}" />
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
                    <c:param name="ItemName" value="${relatedItem.fromItem.itemName}" />
                </c:url>
                <a href="${reviewUrl}">
                    <fmt:message key="navigation.item">
                        <fmt:param value="${relatedItem.fromItem.itemName}" />
                    </fmt:message>
                </a> &gt;&gt;
                <c:url var="relatedItemsUrl" value="/action/Item/RelatedItem/Main">
                    <c:param name="ItemName" value="${relatedItem.fromItem.itemName}" />
                </c:url>
                <a href="${relatedItemsUrl}"><fmt:message key="navigation.relatedItems" /></a> &gt;&gt;
                <fmt:message key="navigation.relatedItems">
                    <fmt:param value="${relatedItem.relatedItemType.relatedItemTypeName}" />
                    <fmt:param value="${relatedItem.fromItem.itemName}" />
                    <fmt:param value="${relatedItem.toItem.itemName}" />
                </fmt:message>
            </h2>
        </div>
        <div id="Content">
            <p><font size="+2"><b><c:out value="${relatedItem.relatedItemType.relatedItemTypeName}" />, <c:out value="${relatedItem.fromItem.itemName}" />, <c:out value="${relatedItem.toItem.itemName}" /></b></font></p>
            <br />
            <br />
            <br />
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
