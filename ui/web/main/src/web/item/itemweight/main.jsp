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
        <title>Weights (<c:out value="${item.itemName}" />)</title>
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
                Weights
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="ItemWeightType.Review" />
            <et:hasSecurityRole securityRole="ItemWeightType.Review" var="includeItemWeightTypeReviewUrl" />
            <c:url var="addUrl" value="/action/Item/ItemWeight/Add">
                <c:param name="ItemName" value="${item.itemName}" />
            </c:url>
            <p><a href="${addUrl}">Add Weight.</a></p>
            <display:table name="itemWeights" id="itemWeight" class="displaytag">
                <display:column titleKey="columnTitle.unitOfMeasureType">
                    <c:out value="${itemWeight.unitOfMeasureType.description}" />
                </display:column>
                <display:column titleKey="columnTitle.type">
                    <c:choose>
                        <c:when test="${includeItemWeightTypeReviewUrl}">
                            <c:url var="reviewUrl" value="/action/Item/ItemWeightType/Review">
                                <c:param name="ItemWeightTypeTypeName" value="${itemWeight.itemWeightType.itemWeightTypeName}" />
                            </c:url>
                            <a href="${reviewUrl}"><et:appearance appearance="${itemWeight.itemWeightType.entityInstance.entityAppearance.appearance}"><c:out value="${itemWeight.itemWeightType.description}" /></et:appearance></a>
                        </c:when>
                        <c:otherwise>
                            <et:appearance appearance="${itemWeight.itemWeightType.entityInstance.entityAppearance.appearance}"><c:out value="${itemWeight.itemWeightType.description}" /></et:appearance>
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column titleKey="columnTitle.weight">
                    <c:out value="${itemWeight.weight}" />
                </display:column>
                <display:column>
                    <c:url var="editUrl" value="/action/Item/ItemWeight/Edit">
                        <c:param name="ItemName" value="${itemWeight.item.itemName}" />
                        <c:param name="UnitOfMeasureTypeName" value="${itemWeight.unitOfMeasureType.unitOfMeasureTypeName}" />
                        <c:param name="ItemWeightTypeName" value="${itemWeight.itemWeightType.itemWeightTypeName}" />
                    </c:url>
                    <a href="${editUrl}">Edit</a>
                    <c:url var="deleteUrl" value="/action/Item/ItemWeight/Delete">
                        <c:param name="ItemName" value="${itemWeight.item.itemName}" />
                        <c:param name="UnitOfMeasureTypeName" value="${itemWeight.unitOfMeasureType.unitOfMeasureTypeName}" />
                        <c:param name="ItemWeightTypeName" value="${itemWeight.itemWeightType.itemWeightTypeName}" />
                    </c:url>
                    <a href="${deleteUrl}">Delete</a>
                </display:column>
            </display:table>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
