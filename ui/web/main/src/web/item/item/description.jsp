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
        <title>Descriptions (<c:out value="${item.itemName}" />)</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
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
                    <c:param name="ItemName" value="${item.itemName}" />
                </c:url>
                <a href="${reviewUrl}">Review (<c:out value="${item.itemName}" />)</a> &gt;&gt;
                Descriptions
            </h2>
        </div>
        <div id="Content">
            <p><font size="+2"><b><c:out value="${item.description}" /></b></font></p>
            <p><font size="+1">${item.itemName}</font></p>
            <br />
            <et:checkSecurityRoles securityRoles="ItemDescription.Create:ItemImageType.Review" />
            <et:hasSecurityRole securityRole="ItemDescription.Create">
                <c:url var="addUrl" value="/action/Item/Item/DescriptionAdd/Step1">
                    <c:param name="ItemName" value="${item.itemName}" />
                </c:url>
                <p><a href="${addUrl}">Add Description.</a></p>
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRole="ItemImageType.Review" var="includeItemImageTypeReviewUrl" />
            <display:table name="itemDescriptions" id="itemDescription" class="displaytag">
                <display:column titleKey="columnTitle.type">
                    <c:out value="${itemDescription.itemDescriptionType.description}" />
                </display:column>
                <display:column titleKey="columnTitle.language">
                    <c:out value="${itemDescription.language.description}" />
                </display:column>
                <display:column titleKey="columnTitle.description">
                    <c:choose>
                        <c:when test="${itemDescription.mimeType == null}">
                            <c:out value="${itemDescription.stringDescription}" />
                        </c:when>
                        <c:otherwise>
                            <c:choose>
                                <c:when test='${itemDescription.itemDescriptionType.mimeTypeUsageType.mimeTypeUsageTypeName == "TEXT" || itemDescription.itemDescriptionType.mimeTypeUsageType.mimeTypeUsageTypeName == "IMAGE"}'>
                                    <c:url var="viewUrl" value="/action/Item/Item/ItemDescriptionReview">
                                        <c:param name="ItemDescriptionTypeName" value="${itemDescription.itemDescriptionType.itemDescriptionTypeName}" />
                                        <c:param name="ItemName" value="${itemDescription.item.itemName}" />
                                        <c:param name="LanguageIsoName" value="${itemDescription.language.languageIsoName}" />
                                    </c:url>
                                </c:when>
                                <c:otherwise>
                                    <c:url var="viewUrl" value="/action/Item/Item/ItemBlobDescriptionView">
                                        <c:param name="ItemDescriptionTypeName" value="${itemDescription.itemDescriptionType.itemDescriptionTypeName}" />
                                        <c:param name="ItemName" value="${itemDescription.item.itemName}" />
                                        <c:param name="LanguageIsoName" value="${itemDescription.language.languageIsoName}" />
                                    </c:url>
                                </c:otherwise>
                            </c:choose>
                            <a href="${viewUrl}">View</a>
                            <c:if test='${itemDescription.itemDescriptionType.mimeTypeUsageType.mimeTypeUsageTypeName == "IMAGE"}'>
                                <c:url var="downloadUrl" value="/action/Item/Item/ItemBlobDescriptionView">
                                    <c:param name="ItemDescriptionTypeName" value="${itemDescription.itemDescriptionType.itemDescriptionTypeName}" />
                                    <c:param name="ItemName" value="${itemDescription.item.itemName}" />
                                    <c:param name="LanguageIsoName" value="${itemDescription.language.languageIsoName}" />
                                    <c:param name="Disposition" value="attachment" />
                                </c:url>
                                <a href="${downloadUrl}">Download</a>
                            </c:if>
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column titleKey="columnTitle.mimeType">
                    <c:if test='${itemDescription.mimeType != null}'>
                        <c:out value="${itemDescription.mimeType.description}" /> (<c:out value="${itemDescription.mimeType.mimeTypeName}" />)
                    </c:if>
                </display:column>
                <display:column titleKey="columnTitle.itemImageDescription">
                    <c:if test='${itemDescription.itemDescriptionType.mimeTypeUsageType.mimeTypeUsageTypeName == "IMAGE"}'>
                        <c:choose>
                            <c:when test="${includeItemImageTypeReviewUrl}">
                                <c:url var="itemImageTypeUrl" value="/action/Item/ItemImageType/Review">
                                    <c:param name="ItemImageTypeName" value="${itemDescription.itemImageType.itemImageTypeName}" />
                                </c:url>
                                <a href="${itemImageTypeUrl}"><c:out value="${itemDescription.itemImageType.description}" /></a>
                            </c:when>
                            <c:otherwise>
                                <c:out value="${itemDescription.itemImageType.description}" />
                            </c:otherwise>
                        </c:choose>
                        <c:if test='${itemDescription.width != null && itemDescription.height != null}'>
                            <c:out value="${itemDescription.width}" /> x <c:out value="${itemDescription.height}" />
                        </c:if>
                        <c:if test='${itemDescription.scaledFromParent}'>
                            <i>(Scaled)</i>
                        </c:if>
                    </c:if>
                </display:column>
                <display:column>
                    <c:url var="editUrl" value="/action/Item/Item/DescriptionEdit">
                        <c:param name="ItemDescriptionTypeName" value="${itemDescription.itemDescriptionType.itemDescriptionTypeName}" />
                        <c:param name="ItemName" value="${itemDescription.item.itemName}" />
                        <c:param name="LanguageIsoName" value="${itemDescription.language.languageIsoName}" />
                    </c:url>
                    <a href="${editUrl}">Edit</a>
                    <c:url var="deleteUrl" value="/action/Item/Item/DescriptionDelete">
                        <c:param name="ItemDescriptionTypeName" value="${itemDescription.itemDescriptionType.itemDescriptionTypeName}" />
                        <c:param name="ItemName" value="${itemDescription.item.itemName}" />
                        <c:param name="LanguageIsoName" value="${itemDescription.language.languageIsoName}" />
                    </c:url>
                    <a href="${deleteUrl}">Delete</a>
                </display:column>
            </display:table>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
