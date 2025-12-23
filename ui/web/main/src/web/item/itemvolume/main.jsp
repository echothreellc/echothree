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
        <title>Volumes (<c:out value="${item.itemName}" />)</title>
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
                Volumes
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="ItemVolumeType.Review" />
            <et:hasSecurityRole securityRole="ItemVolumeType.Review" var="includeItemVolumeTypeReviewUrl" />
            <c:url var="addUrl" value="/action/Item/ItemVolume/Add">
                <c:param name="ItemName" value="${item.itemName}" />
            </c:url>
            <p><a href="${addUrl}">Add Volume.</a></p>
            <display:table name="itemVolumes" id="itemVolume" class="displaytag">
                <display:column titleKey="columnTitle.unitOfMeasureType">
                    <c:out value="${itemVolume.unitOfMeasureType.description}" />
                </display:column>
                <display:column titleKey="columnTitle.type">
                    <c:choose>
                        <c:when test="${includeItemVolumeTypeReviewUrl}">
                            <c:url var="reviewUrl" value="/action/Item/ItemVolumeType/Review">
                                <c:param name="ItemVolumeTypeTypeName" value="${itemVolume.itemVolumeType.itemVolumeTypeName}" />
                            </c:url>
                            <a href="${reviewUrl}"><et:appearance appearance="${itemVolume.itemVolumeType.entityInstance.entityAppearance.appearance}"><c:out value="${itemVolume.itemVolumeType.description}" /></et:appearance></a>
                        </c:when>
                        <c:otherwise>
                            <et:appearance appearance="${itemVolume.itemVolumeType.entityInstance.entityAppearance.appearance}"><c:out value="${itemVolume.itemVolumeType.description}" /></et:appearance>
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column titleKey="columnTitle.volume">
                    H: <c:out value="${itemVolume.height}" />,
                    W: <c:out value="${itemVolume.width}" />,
                    D: <c:out value="${itemVolume.depth}" />
                </display:column>
                <display:column>
                    <c:url var="editUrl" value="/action/Item/ItemVolume/Edit">
                        <c:param name="ItemName" value="${itemVolume.item.itemName}" />
                        <c:param name="UnitOfMeasureTypeName" value="${itemVolume.unitOfMeasureType.unitOfMeasureTypeName}" />
                        <c:param name="ItemVolumeTypeName" value="${itemVolume.itemVolumeType.itemVolumeTypeName}" />
                    </c:url>
                    <a href="${editUrl}">Edit</a>
                    <c:url var="deleteUrl" value="/action/Item/ItemVolume/Delete">
                        <c:param name="ItemName" value="${itemVolume.item.itemName}" />
                        <c:param name="UnitOfMeasureTypeName" value="${itemVolume.unitOfMeasureType.unitOfMeasureTypeName}" />
                        <c:param name="ItemVolumeTypeName" value="${itemVolume.itemVolumeType.itemVolumeTypeName}" />
                    </c:url>
                    <a href="${deleteUrl}">Delete</a>
                </display:column>
            </display:table>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
