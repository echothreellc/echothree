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
        <title>Descriptions (<c:out value="${itemDescription.item.itemName}" />)</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
        <c:if test='${itemDescription.itemDescriptionType.mimeTypeUsageType.mimeTypeUsageTypeName == "TEXT"}'>
            <%@ include file="../../include/environment-prism.jsp" %>
        </c:if>
    </head>
    <body>
        <c:if test='${itemDescription.itemDescriptionType.mimeTypeUsageType.mimeTypeUsageTypeName == "TEXT"}'>
            <%@ include file="../../include/prism.jsp" %>
        </c:if>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Item/Main" />">Items</a> &gt;&gt;
                <a href="<c:url value="/action/Item/Item/Main" />">Search</a> &gt;&gt;
                <et:countItemResults searchTypeName="ITEM_MAINTENANCE" countVar="itemResultsCount" commandResultVar="countItemResultsCommandResult" logErrors="false" />
                <c:if test="${itemResultsCount > 0}">
                    <a href="<c:url value="/action/Item/Item/Result" />"><fmt:message key="navigation.results" /></a> &gt;&gt;
                </c:if>
                <c:url var="reviewUrl" value="/action/Item/Item/Review">
                    <c:param name="ItemName" value="${itemDescription.item.itemName}" />
                </c:url>
                <a href="${reviewUrl}">Review (<c:out value="${itemDescription.item.itemName}" />)</a> &gt;&gt;
                <c:url var="descriptionsUrl" value="/action/Item/Item/Description">
                    <c:param name="ItemName" value="${itemDescription.item.itemName}" />
                </c:url>
                <a href="${descriptionsUrl}">Descriptions</a> &gt;&gt;
                View
            </h2>
        </div>
        <div id="Content">
            <p><font size="+2"><b><c:out value="${itemDescription.item.description}" /></b></font></p>
            <p><font size="+1">${itemDescription.item.itemName}</font></p>
            <br />
            Item Description Type: <c:out value="${itemDescription.itemDescriptionType.description}" /><br />
            Language: <c:out value="${itemDescription.language.description}" /><br />
            Mime Type: <c:out value="${itemDescription.mimeType.description}" /> (<c:out value="${itemDescription.mimeType.mimeTypeName}" />)<br />
            <br />
            <table class="displaytag">
                <tbody>
                    <tr class="odd">
                        <td>
                            <c:choose>
                                <c:when test='${itemDescription.itemDescriptionType.mimeTypeUsageType.mimeTypeUsageTypeName == "TEXT"}'>
                                    <et:out value="${itemDescription.clobDescription}" mimeTypeName="${itemDescription.mimeType.mimeTypeName}" />
                                </c:when>
                                <c:when test='${itemDescription.itemDescriptionType.mimeTypeUsageType.mimeTypeUsageTypeName == "IMAGE"}'>
                                    <c:url var="blobUrl" value="/action/Item/Item/ItemBlobDescriptionView">
                                        <c:param name="ItemDescriptionTypeName" value="${itemDescription.itemDescriptionType.itemDescriptionTypeName}" />
                                        <c:param name="ItemName" value="${itemDescription.item.itemName}" />
                                        <c:param name="LanguageIsoName" value="${itemDescription.language.languageIsoName}" />
                                    </c:url>
                                    <img src="${blobUrl}" alt="${itemDescription.item.description}">
                                </c:when>
                                <c:otherwise>
                                    <c:out value='${itemDescription.itemDescriptionType.mimeTypeUsageType.description}' />
                                    (<c:out value='${itemDescription.itemDescriptionType.mimeTypeUsageType.mimeTypeUsageTypeName}' />)
                                    is not a supported Mime Type Usage Type.
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                </tbody>
            </table>
            <c:set var="tagScopes" scope="request" value="${itemDescription.tagScopes}" />
            <c:set var="entityInstance" scope="request" value="${itemDescription.entityInstance}" />
            <c:url var="returnUrl" scope="request" value="/../action/Item/Item/ItemDescriptionReview">
                <c:param name="ItemDescriptionTypeName" value="${itemDescription.itemDescriptionType.itemDescriptionTypeName}" />
                <c:param name="ItemName" value="${itemDescription.item.itemName}" />
                <c:param name="LanguageIsoName" value="${itemDescription.language.languageIsoName}" />
            </c:url>
            <jsp:include page="../../include/tagScopes.jsp" />
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
