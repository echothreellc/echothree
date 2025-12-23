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
        <title>Item Results</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Item/Main" />">Items</a> &gt;&gt;
                <a href="<c:url value="/action/Item/Item/Main" />">Search</a> &gt;&gt;
                <fmt:message key="navigation.results" />
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="Item.Create:Item.Review:ItemStatus.Choices:ItemUnitOfMeasureType.List:ItemPrice.List:ItemWeight.List:ItemVolume.List:ItemAlias.List:RelatedItem.List:ItemPackCheckRequirement.List:ItemShippingTime.List:PartyInventoryLevel.List:Item.Edit:ItemDescription.List:Event.List" />
            <et:hasSecurityRole securityRole="Item.Create">
                <p><a href="<c:url value="/action/Item/Item/Add" />">Add Item.</a></p>
            </et:hasSecurityRole>
            <et:containsExecutionError key="UnknownUserVisitSearch">
                <c:set var="unknownUserVisitSearch" value="true" />
            </et:containsExecutionError>
            <c:choose>
                <c:when test="${unknownUserVisitSearch}">
                    <p>Your search results are no longer available, please perform your search again.</p>
                </c:when>
                <c:otherwise>
                    <et:hasSecurityRole securityRoles="ItemUnitOfMeasureType.List:ItemPrice.List:ItemWeight.List:ItemVolume.List:ItemAlias.List:RelatedItem.List">
                        <c:set var="linksInFirstRow" value="true" />
                    </et:hasSecurityRole>
                    <et:hasSecurityRole securityRoles="ItemPackCheckRequirement.List:ItemShippingTime.List:PartyInventoryLevel.List">
                        <c:set var="linksInSecondRow" value="true" />
                    </et:hasSecurityRole>
                    <et:hasSecurityRole securityRoles="Item.Edit:ItemDescription.List">
                        <c:set var="linksInThirdRow" value="true" />
                    </et:hasSecurityRole>
                    <et:hasSecurityRole securityRole="Item.Review" var="includeReviewUrl" />
                    <et:hasSecurityRole securityRole="ItemStatus.Choices" var="includeItemStatusUrl" />
                    <c:choose>
                        <c:when test="${itemResultCount == null || itemResultCount < 21}">
                            <display:table name="itemResults.list" id="itemResult" class="displaytag" export="true" sort="list" requestURI="/action/Item/Item/Result">
                                <display:setProperty name="export.csv.filename" value="Items.csv" />
                                <display:setProperty name="export.excel.filename" value="Items.xls" />
                                <display:setProperty name="export.pdf.filename" value="Items.pdf" />
                                <display:setProperty name="export.rtf.filename" value="Items.rtf" />
                                <display:setProperty name="export.xml.filename" value="Items.xml" />
                                <display:column media="html">
                                    <c:choose>
                                        <c:when test="${itemResult.item.entityInstance.entityVisit == null}">
                                            New
                                        </c:when>
                                        <c:otherwise>
                                            <c:choose>
                                                <c:when test="${itemResult.item.entityInstance.entityVisit.unformattedVisitedTime >= itemResult.item.entityInstance.entityTime.unformattedModifiedTime}">
                                                    Unchanged
                                                </c:when>
                                                <c:otherwise>
                                                    Updated
                                                </c:otherwise>
                                            </c:choose>
                                        </c:otherwise>
                                    </c:choose>
                                </display:column>
                                <display:column titleKey="columnTitle.name" media="html" sortable="true" sortProperty="item.itemName">
                                    <c:choose>
                                        <c:when test="${includeReviewUrl}">
                                            <c:url var="reviewUrl" value="/action/Item/Item/Review">
                                                <c:param name="ItemName" value="${itemResult.item.itemName}" />
                                            </c:url>
                                            <a href="${reviewUrl}"><et:appearance appearance="${itemResult.item.entityInstance.entityAppearance.appearance}"><c:out value="${itemResult.item.itemName}" /></et:appearance></a>
                                        </c:when>
                                        <c:otherwise>
                                            <et:appearance appearance="${itemResult.item.entityInstance.entityAppearance.appearance}"><c:out value="${itemResult.item.itemName}" /></et:appearance>
                                        </c:otherwise>
                                    </c:choose>
                                </display:column>
                                <display:column titleKey="columnTitle.description" media="html" sortable="true" sortProperty="item.description">
                                    <et:appearance appearance="${itemResult.item.entityInstance.entityAppearance.appearance}"><c:out value="${itemResult.item.description}" /></et:appearance>
                                </display:column>
                                <display:column titleKey="columnTitle.type" media="html" sortable="true" sortProperty="item.itemType.description">
                                    <c:out value="${itemResult.item.itemType.description}" />
                                </display:column>
                                <display:column titleKey="columnTitle.useType" media="html" sortable="true" sortProperty="item.itemUseType.description">
                                    <c:out value="${itemResult.item.itemUseType.description}" />
                                </display:column>
                                <display:column titleKey="columnTitle.status" media="html" sortable="true" sortProperty="item.itemStatus.workflowStep.description">
                                    <c:choose>
                                        <c:when test="${includeItemStatusUrl}">
                                            <c:url var="statusUrl" value="/action/Item/Item/Status">
                                                <c:param name="ForwardKey" value="Result" />
                                                <c:param name="ItemName" value="${itemResult.item.itemName}" />
                                            </c:url>
                                            <a href="${statusUrl}"><et:appearance appearance="${itemResult.item.itemStatus.workflowStep.entityInstance.entityAppearance.appearance}"><c:out value="${itemResult.item.itemStatus.workflowStep.description}" /></et:appearance></a>
                                        </c:when>
                                        <c:otherwise>
                                            <et:appearance appearance="${itemResult.item.itemStatus.workflowStep.entityInstance.entityAppearance.appearance}"><c:out value="${itemResult.item.itemStatus.workflowStep.description}" /></et:appearance>
                                        </c:otherwise>
                                    </c:choose>
                                </display:column>
                                <c:if test="${linksInFirstRow || linksInSecondRow || linksInThirdRow}">
                                    <display:column media="html">
                                        <et:hasSecurityRole securityRole="ItemUnitOfMeasureType.List">
                                            <c:url var="itemUnitOfMeasureTypesUrl" value="/action/Item/ItemUnitOfMeasureType/Main">
                                                <c:param name="ItemName" value="${itemResult.item.itemName}" />
                                            </c:url>
                                            <a href="${itemUnitOfMeasureTypesUrl}">Unit Of Measure Types</a>
                                        </et:hasSecurityRole>
                                        <et:hasSecurityRole securityRole="ItemPrice.List">
                                            <c:url var="itemPricesUrl" value="/action/Item/ItemPrice/Main">
                                                <c:param name="ItemName" value="${itemResult.item.itemName}" />
                                            </c:url>
                                            <a href="${itemPricesUrl}">Prices</a>
                                        </et:hasSecurityRole>
                                        <et:hasSecurityRole securityRole="ItemWeight.List">
                                            <c:url var="itemWeightsUrl" value="/action/Item/ItemWeight/Main">
                                                <c:param name="ItemName" value="${itemResult.item.itemName}" />
                                            </c:url>
                                            <a href="${itemWeightsUrl}">Weights</a>
                                        </et:hasSecurityRole>
                                        <et:hasSecurityRole securityRole="ItemVolume.List">
                                            <c:url var="itemVolumesUrl" value="/action/Item/ItemVolume/Main">
                                                <c:param name="ItemName" value="${itemResult.item.itemName}" />
                                            </c:url>
                                            <a href="${itemVolumesUrl}">Volumes</a>
                                        </et:hasSecurityRole>
                                        <et:hasSecurityRole securityRole="ItemAlias.List">
                                            <c:url var="itemAliasesUrl" value="/action/Item/ItemAlias/Main">
                                                <c:param name="ItemName" value="${itemResult.item.itemName}" />
                                            </c:url>
                                            <a href="${itemAliasesUrl}">Aliases</a>
                                        </et:hasSecurityRole>
                                        <et:hasSecurityRole securityRole="RelatedItem.List">
                                            <c:url var="relatedItemsUrl" value="/action/Item/RelatedItem/Main">
                                                <c:param name="ItemName" value="${itemResult.item.itemName}" />
                                            </c:url>
                                            <a href="${relatedItemsUrl}">Related Items</a>
                                        </et:hasSecurityRole>
                                        <c:if test="${linksInFirstRow && linksInSecondRow}">
                                            <br />
                                        </c:if>
                                        <et:hasSecurityRole securityRole="ItemPackCheckRequirement.List">
                                            <c:url var="itemPackCheckRequirementsUrl" value="/action/Item/ItemPackCheckRequirement/Main">
                                                <c:param name="ItemName" value="${itemResult.item.itemName}" />
                                            </c:url>
                                            <a href="${itemPackCheckRequirementsUrl}">Pack Check Requirements</a>
                                        </et:hasSecurityRole>
                                        <et:hasSecurityRole securityRole="ItemShippingTime.List">
                                            <c:url var="itemShippingTimesUrl" value="/action/Item/ItemShippingTime/Main">
                                                <c:param name="ItemName" value="${itemResult.item.itemName}" />
                                            </c:url>
                                            <a href="${itemShippingTimesUrl}">Shipping Times</a>
                                        </et:hasSecurityRole>
                                        <et:hasSecurityRole securityRole="PartyInventoryLevel.List">
                                            <c:url var="partyInventoryLevelsUrl" value="/action/Item/PartyInventoryLevel/Main">
                                                <c:param name="ItemName" value="${itemResult.item.itemName}" />
                                            </c:url>
                                            <a href="${partyInventoryLevelsUrl}">Inventory Levels</a>
                                        </et:hasSecurityRole>
                                        <c:if test="${linksInSecondRow && linksInThirdRow}">
                                            <br />
                                        </c:if>
                                        <et:hasSecurityRole securityRole="Item.Edit">
                                            <c:url var="editUrl" value="/action/Item/Item/Edit">
                                                <c:param name="OriginalItemName" value="${itemResult.item.itemName}" />
                                            </c:url>
                                            <a href="${editUrl}">Edit</a>
                                        </et:hasSecurityRole>
                                        <et:hasSecurityRole securityRole="ItemDescription.List">
                                            <c:url var="descriptionsUrl" value="/action/Item/Item/Description">
                                                <c:param name="ItemName" value="${itemResult.item.itemName}" />
                                            </c:url>
                                            <a href="${descriptionsUrl}">Descriptions</a>
                                        </et:hasSecurityRole>
                                    </display:column>
                                </c:if>
                                <et:hasSecurityRole securityRole="Event.List">
                                    <display:column media="html">
                                        <c:url var="eventsUrl" value="/action/Core/Event/Main">
                                            <c:param name="EntityRef" value="${itemResult.item.entityInstance.entityRef}" />
                                        </c:url>
                                        <a href="${eventsUrl}">Events</a>
                                    </display:column>
                                </et:hasSecurityRole>
                                <display:column property="item.itemName" titleKey="columnTitle.name" media="csv excel pdf rtf xml" />
                                <display:column property="item.description" titleKey="columnTitle.description" media="csv excel pdf rtf xml" />
                                <display:column property="item.itemType.description" titleKey="columnTitle.type" media="csv excel pdf rtf xml" />
                                <display:column property="item.itemUseType.description" titleKey="columnTitle.useType" media="csv excel pdf rtf xml" />
                                <display:column property="item.itemStatus.workflowStep.description" titleKey="columnTitle.status" media="csv excel pdf rtf xml" />
                            </display:table>
                            <c:if test="${itemResults.size > 20}">
                                <c:url var="resultsUrl" value="/action/Item/Item/Result" />
                                <a href="${resultsUrl}">Paged Results</a>
                            </c:if>
                        </c:when>
                        <c:otherwise>
                            <display:table name="itemResults.list" id="itemResult" class="displaytag" partialList="true" pagesize="20" size="itemResultCount" requestURI="/action/Item/Item/Result">
                                <display:column>
                                    <c:choose>
                                        <c:when test="${itemResult.item.entityInstance.entityVisit == null}">
                                            New
                                        </c:when>
                                        <c:otherwise>
                                            <c:choose>
                                                <c:when test="${itemResult.item.entityInstance.entityVisit.unformattedVisitedTime >= itemResult.item.entityInstance.entityTime.unformattedModifiedTime}">
                                                    Unchanged
                                                </c:when>
                                                <c:otherwise>
                                                    Updated
                                                </c:otherwise>
                                            </c:choose>
                                        </c:otherwise>
                                    </c:choose>
                                </display:column>
                                <display:column titleKey="columnTitle.name">
                                    <c:choose>
                                        <c:when test="${includeReviewUrl}">
                                            <c:url var="reviewUrl" value="/action/Item/Item/Review">
                                                <c:param name="ItemName" value="${itemResult.item.itemName}" />
                                            </c:url>
                                            <a href="${reviewUrl}"><et:appearance appearance="${itemResult.item.entityInstance.entityAppearance.appearance}"><c:out value="${itemResult.item.itemName}" /></et:appearance></a>
                                        </c:when>
                                        <c:otherwise>
                                            <et:appearance appearance="${itemResult.item.entityInstance.entityAppearance.appearance}"><c:out value="${itemResult.item.itemName}" /></et:appearance>
                                        </c:otherwise>
                                    </c:choose>
                                </display:column>
                                <display:column titleKey="columnTitle.description">
                                    <et:appearance appearance="${itemResult.item.entityInstance.entityAppearance.appearance}"><c:out value="${itemResult.item.description}" /></et:appearance>
                                </display:column>
                                <display:column titleKey="columnTitle.type">
                                    <c:out value="${itemResult.item.itemType.description}" />
                                </display:column>
                                <display:column titleKey="columnTitle.useType">
                                    <c:out value="${itemResult.item.itemUseType.description}" />
                                </display:column>
                                <display:column titleKey="columnTitle.status">
                                    <c:choose>
                                        <c:when test="${includeItemStatusUrl}">
                                            <c:url var="statusUrl" value="/action/Item/Item/Status">
                                                <c:param name="ForwardKey" value="Result" />
                                                <c:param name="ItemName" value="${itemResult.item.itemName}" />
                                            </c:url>
                                            <a href="${statusUrl}"><et:appearance appearance="${itemResult.item.itemStatus.workflowStep.entityInstance.entityAppearance.appearance}"><c:out value="${itemResult.item.itemStatus.workflowStep.description}" /></et:appearance></a>
                                        </c:when>
                                        <c:otherwise>
                                            <et:appearance appearance="${itemResult.item.itemStatus.workflowStep.entityInstance.entityAppearance.appearance}"><c:out value="${itemResult.item.itemStatus.workflowStep.description}" /></et:appearance>
                                        </c:otherwise>
                                    </c:choose>
                                </display:column>
                                <c:if test="${linksInFirstRow || linksInSecondRow || linksInThirdRow}">
                                    <display:column>
                                        <et:hasSecurityRole securityRole="ItemUnitOfMeasureType.List">
                                            <c:url var="itemUnitOfMeasureTypesUrl" value="/action/Item/ItemUnitOfMeasureType/Main">
                                                <c:param name="ItemName" value="${itemResult.item.itemName}" />
                                            </c:url>
                                            <a href="${itemUnitOfMeasureTypesUrl}">Unit Of Measure Types</a>
                                        </et:hasSecurityRole>
                                        <et:hasSecurityRole securityRole="ItemPrice.List">
                                            <c:url var="itemPricesUrl" value="/action/Item/ItemPrice/Main">
                                                <c:param name="ItemName" value="${itemResult.item.itemName}" />
                                            </c:url>
                                            <a href="${itemPricesUrl}">Prices</a>
                                        </et:hasSecurityRole>
                                        <et:hasSecurityRole securityRole="ItemWeight.List">
                                            <c:url var="itemWeightsUrl" value="/action/Item/ItemWeight/Main">
                                                <c:param name="ItemName" value="${itemResult.item.itemName}" />
                                            </c:url>
                                            <a href="${itemWeightsUrl}">Weights</a>
                                        </et:hasSecurityRole>
                                        <et:hasSecurityRole securityRole="ItemVolume.List">
                                            <c:url var="itemVolumesUrl" value="/action/Item/ItemVolume/Main">
                                                <c:param name="ItemName" value="${itemResult.item.itemName}" />
                                            </c:url>
                                            <a href="${itemVolumesUrl}">Volumes</a>
                                        </et:hasSecurityRole>
                                        <et:hasSecurityRole securityRole="ItemAlias.List">
                                            <c:url var="itemAliasesUrl" value="/action/Item/ItemAlias/Main">
                                                <c:param name="ItemName" value="${itemResult.item.itemName}" />
                                            </c:url>
                                            <a href="${itemAliasesUrl}">Aliases</a>
                                        </et:hasSecurityRole>
                                        <et:hasSecurityRole securityRole="RelatedItem.List">
                                            <c:url var="relatedItemsUrl" value="/action/Item/RelatedItem/Main">
                                                <c:param name="ItemName" value="${itemResult.item.itemName}" />
                                            </c:url>
                                            <a href="${relatedItemsUrl}">Related Items</a>
                                        </et:hasSecurityRole>
                                        <c:if test="${linksInFirstRow && linksInSecondRow}">
                                            <br />
                                        </c:if>
                                        <et:hasSecurityRole securityRole="ItemPackCheckRequirement.List">
                                            <c:url var="itemPackCheckRequirementsUrl" value="/action/Item/ItemPackCheckRequirement/Main">
                                                <c:param name="ItemName" value="${itemResult.item.itemName}" />
                                            </c:url>
                                            <a href="${itemPackCheckRequirementsUrl}">Pack Check Requirements</a>
                                        </et:hasSecurityRole>
                                        <et:hasSecurityRole securityRole="ItemShippingTime.List">
                                            <c:url var="itemShippingTimesUrl" value="/action/Item/ItemShippingTime/Main">
                                                <c:param name="ItemName" value="${itemResult.item.itemName}" />
                                            </c:url>
                                            <a href="${itemShippingTimesUrl}">Shipping Times</a>
                                        </et:hasSecurityRole>
                                        <et:hasSecurityRole securityRole="PartyInventoryLevel.List">
                                            <c:url var="partyInventoryLevelsUrl" value="/action/Item/PartyInventoryLevel/Main">
                                                <c:param name="ItemName" value="${itemResult.item.itemName}" />
                                            </c:url>
                                            <a href="${partyInventoryLevelsUrl}">Inventory Levels</a>
                                        </et:hasSecurityRole>
                                        <c:if test="${linksInSecondRow && linksInThirdRow}">
                                            <br />
                                        </c:if>
                                        <et:hasSecurityRole securityRole="Item.Edit">
                                            <c:url var="editUrl" value="/action/Item/Item/Edit">
                                                <c:param name="OriginalItemName" value="${itemResult.item.itemName}" />
                                            </c:url>
                                            <a href="${editUrl}">Edit</a>
                                        </et:hasSecurityRole>
                                        <et:hasSecurityRole securityRole="ItemDescription.List">
                                            <c:url var="descriptionsUrl" value="/action/Item/Item/Description">
                                                <c:param name="ItemName" value="${itemResult.item.itemName}" />
                                            </c:url>
                                            <a href="${descriptionsUrl}">Descriptions</a>
                                        </et:hasSecurityRole>
                                    </display:column>
                                </c:if>
                                <et:hasSecurityRole securityRole="Event.List">
                                    <display:column>
                                        <c:url var="eventsUrl" value="/action/Core/Event/Main">
                                            <c:param name="EntityRef" value="${itemResult.item.entityInstance.entityRef}" />
                                        </c:url>
                                        <a href="${eventsUrl}">Events</a>
                                    </display:column>
                                </et:hasSecurityRole>
                            </display:table>
                            <c:url var="resultsUrl" value="/action/Item/Item/Result">
                                <c:param name="Results" value="Complete" />
                            </c:url>
                            <a href="${resultsUrl}">All Results</a>
                        </c:otherwise>
                    </c:choose>
                </c:otherwise>
            </c:choose>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
