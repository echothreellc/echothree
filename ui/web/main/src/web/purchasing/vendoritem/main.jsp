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
        <title>Items (<c:out value="${vendor.vendorName}" />)</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Purchasing/Main" />">Purchasing</a> &gt;&gt;
                <a href="<c:url value="/action/Purchasing/Vendor/Main" />">Vendors</a> &gt;&gt;
                <et:countVendorResults searchTypeName="VENDOR_REVIEW" countVar="vendorResultsCount" commandResultVar="countVendorResultsCommandResult" logErrors="false" />
                <c:if test="${vendorResultsCount > 0}">
                    <a href="<c:url value="/action/Purchasing/Vendor/Result" />"><fmt:message key="navigation.results" /></a> &gt;&gt;
                </c:if>
                <c:url var="vendorUrl" value="/action/Purchasing/Vendor/Review">
                    <c:param name="VendorName" value="${vendor.vendorName}" />
                </c:url>
                <a href="${vendorUrl}">Review (<c:out value="${vendor.vendorName}" />)</a> &gt;&gt;
                Items
            </h2>
        </div>
        <div id="Content">
            <c:url var="addUrl" value="/action/Purchasing/VendorItem/Add">
                <c:param name="VendorName" value="${vendor.vendorName}" />
            </c:url>
            <p><a href="${addUrl}">Add Vendor Item.</a></p>
            <et:checkSecurityRoles securityRoles="VendorItem.Review:Item.Review:CancellationPolicy.Review:ReturnPolicy.Review:VendorItemStatus.Choices:Event.List" />
            <et:hasSecurityRole securityRole="Item.Review" var="includeItemUrl" />
            <et:hasSecurityRole securityRole="CancellationPolicy.Review" var="includeCancellationPolicyUrl" />
            <et:hasSecurityRole securityRole="ReturnPolicy.Review" var="includeReturnPolicyUrl" />
            <et:hasSecurityRole securityRole="VendorItemStatus.Choices" var="includeEditableVendorItemStatus">
                <c:url var="returnUrl" scope="request" value="Main">
                    <c:param name="VendorName" value="${vendor.vendorName}" />
                </c:url>
            </et:hasSecurityRole>
            <c:choose>
                <c:when test="${vendorItemCount == null || vendorItemCount < 21}">
                    <display:table name="vendorItems.list" id="vendorItem" class="displaytag" export="true" sort="list" requestURI="/action/Purchasing/VendorItem/Main">
                        <display:setProperty name="export.csv.filename" value="VendorItems.csv" />
                        <display:setProperty name="export.excel.filename" value="VendorItems.xls" />
                        <display:setProperty name="export.pdf.filename" value="VendorItems.pdf" />
                        <display:setProperty name="export.rtf.filename" value="VendorItems.rtf" />
                        <display:setProperty name="export.xml.filename" value="VendorItems.xml" />
                        
                        <et:hasSecurityRole securityRole="VendorItem.Review">
                            <display:column media="html">
                                <c:url var="reviewUrl" value="/action/Purchasing/VendorItem/Review">
                                    <c:param name="VendorName" value="${vendor.vendorName}" />
                                    <c:param name="VendorItemName" value="${vendorItem.vendorItemName}" />
                                </c:url>
                                <a href="${reviewUrl}">Review</a>
                            </display:column>
                        </et:hasSecurityRole>
                        <display:column titleKey="columnTitle.itemName" media="html" sortable="true" sortProperty="item.itemName">
                            <c:choose>
                                <c:when test="${includeItemUrl}">
                                    <c:url var="itemReviewUrl" value="/action/Item/Item/Review">
                                        <c:param name="ItemName" value="${vendorItem.item.itemName}" />
                                    </c:url>
                                    <a href="${itemReviewUrl}"><c:out value="${vendorItem.item.itemName}" /></a>
                                </c:when>
                                <c:otherwise>
                                    <c:out value="${vendorItem.item.itemName}" />
                                </c:otherwise>
                            </c:choose>
                        </display:column>
                        <display:column titleKey="columnTitle.vendorItemName" media="html" sortable="true" sortProperty="vendorItemName">
                            <c:out value="${vendorItem.vendorItemName}" />
                        </display:column>
                        <display:column titleKey="columnTitle.description" media="html" sortable="true" sortProperty="description">
                            <c:out value="${vendorItem.description}" />
                        </display:column>
                        <display:column titleKey="columnTitle.priority" media="html" sortable="true" sortProperty="priority">
                            <c:out value="${vendorItem.priority}" />
                        </display:column>
                        <display:column titleKey="columnTitle.cancellationPolicy" media="html" sortable="true" sortProperty="cancellationPolicy.cancellationPolicyName">
                            <c:if test="${vendorItem.cancellationPolicy != null}">
                                <c:choose>
                                    <c:when test="${includeCancellationPolicyUrl}">
                                        <c:url var="reviewUrl" value="/action/CancellationPolicy/CancellationPolicy/Review">
                                            <c:param name="CancellationKindName" value="${vendorItem.cancellationPolicy.cancellationKind.cancellationKindName}" />
                                            <c:param name="CancellationPolicyName" value="${vendorItem.cancellationPolicy.cancellationPolicyName}" />
                                        </c:url>
                                        <a href="${reviewUrl}"><c:out value="${vendorItem.cancellationPolicy.description}" /></a>
                                    </c:when>
                                    <c:otherwise>
                                        <c:out value="${vendorItem.cancellationPolicy.description}" />
                                    </c:otherwise>
                                </c:choose>
                            </c:if>
                        </display:column>
                        <display:column titleKey="columnTitle.returnPolicy" media="html" sortable="true" sortProperty="returnPolicy.returnPolicyName">
                            <c:if test="${vendorItem.returnPolicy != null}">
                                <c:choose>
                                    <c:when test="${includeReturnPolicyUrl}">
                                        <c:url var="reviewUrl" value="/action/ReturnPolicy/ReturnPolicy/Review">
                                            <c:param name="ReturnKindName" value="${vendorItem.returnPolicy.returnKind.returnKindName}" />
                                            <c:param name="ReturnPolicyName" value="${vendorItem.returnPolicy.returnPolicyName}" />
                                        </c:url>
                                        <a href="${reviewUrl}"><c:out value="${vendorItem.returnPolicy.description}" /></a>
                                    </c:when>
                                    <c:otherwise>
                                        <c:out value="${vendorItem.returnPolicy.description}" />
                                    </c:otherwise>
                                </c:choose>
                            </c:if>
                        </display:column>
                        <display:column titleKey="columnTitle.status" media="html" sortable="true" sortProperty="vendorItemStatus.workflowStep.workflowStepName">
                            <c:choose>
                                <c:when test="${includeEditableVendorItemStatus}">
                                    <c:url var="statusUrl" value="/action/Purchasing/VendorItem/Status">
                                        <c:param name="ReturnUrl" value="${returnUrl}" />
                                        <c:param name="VendorName" value="${vendor.vendorName}" />
                                        <c:param name="VendorItemName" value="${vendorItem.vendorItemName}" />
                                    </c:url>
                                    <a href="${statusUrl}"><c:out value="${vendorItem.vendorItemStatus.workflowStep.description}" /></a>
                                </c:when>
                                <c:otherwise>
                                    <c:out value="${vendorItem.vendorItemStatus.workflowStep.description}" />
                                </c:otherwise>
                            </c:choose>
                        </display:column>
                        <display:column media="html">
                            <c:url var="vendorItemCostsUrl" value="/action/Purchasing/VendorItemCost/Main">
                                <c:param name="VendorName" value="${vendor.vendorName}" />
                                <c:param name="VendorItemName" value="${vendorItem.vendorItemName}" />
                            </c:url>
                            <a href="${vendorItemCostsUrl}">Costs</a><br />
                            <c:url var="editUrl" value="/action/Purchasing/VendorItem/Edit">
                                <c:param name="VendorName" value="${vendor.vendorName}" />
                                <c:param name="OriginalVendorItemName" value="${vendorItem.vendorItemName}" />
                            </c:url>
                            <a href="${editUrl}">Edit</a>
                            <c:url var="deleteUrl" value="/action/Purchasing/VendorItem/Delete">
                                <c:param name="VendorName" value="${vendor.vendorName}" />
                                <c:param name="VendorItemName" value="${vendorItem.vendorItemName}" />
                            </c:url>
                            <a href="${deleteUrl}">Delete</a>
                        </display:column>
                        <et:hasSecurityRole securityRole="Event.List">
                            <display:column media="html">
                                <c:url var="eventsUrl" value="/action/Core/Event/Main">
                                    <c:param name="EntityRef" value="${vendorItem.entityInstance.entityRef}" />
                                </c:url>
                                <a href="${eventsUrl}">Events</a>
                            </display:column>
                        </et:hasSecurityRole>
                        <display:column property="vendor.vendorName" titleKey="columnTitle.vendorName" media="csv excel pdf rtf xml" />
                        <display:column property="item.itemName" titleKey="columnTitle.itemName" media="csv excel pdf rtf xml" />
                        <display:column property="vendorItemName" titleKey="columnTitle.vendorItemName" media="csv excel pdf rtf xml" />
                        <display:column property="description" titleKey="columnTitle.description" media="csv excel pdf rtf xml" />
                        <display:column property="priority" titleKey="columnTitle.priority" media="csv excel pdf rtf xml" />
                        <display:column property="cancellationPolicy.cancellationPolicyName" titleKey="columnTitle.cancellationPolicy" media="csv excel pdf rtf xml" />
                        <display:column property="returnPolicy.returnPolicyName" titleKey="columnTitle.returnPolicy" media="csv excel pdf rtf xml" />
                        <display:column property="vendorItemStatus.workflowStep.workflowStepName" titleKey="columnTitle.status" media="csv excel pdf rtf xml" />
                    </display:table>
                    <c:if test="${vendorItems.size > 20}">
                        <c:url var="resultsUrl" value="/action/Purchasing/VendorItem/Main">
                            <c:if test="${param.VendorName != null}">
                                <c:param name="VendorName" value="${param.VendorName}" />
                            </c:if>
                            <c:if test="${param.ItemName != null}">
                                <c:param name="ItemName" value="${param.ItemName}" />
                            </c:if>
                        </c:url>
                        <a href="${resultsUrl}">Paged Results</a>
                    </c:if>
                </c:when>
                <c:otherwise>
                    <display:table name="vendorItems.list" id="vendorItem" class="displaytag" partialList="true" pagesize="20" size="vendorItemCount" requestURI="/action/Purchasing/VendorItem/Main">
                        <et:hasSecurityRole securityRole="VendorItem.Review">
                            <display:column>
                                <c:url var="reviewUrl" value="/action/Purchasing/VendorItem/Review">
                                    <c:param name="VendorName" value="${vendor.vendorName}" />
                                    <c:param name="VendorItemName" value="${vendorItem.vendorItemName}" />
                                </c:url>
                                <a href="${reviewUrl}">Review</a>
                            </display:column>
                        </et:hasSecurityRole>
                        <display:column titleKey="columnTitle.itemName">
                            <c:choose>
                                <c:when test="${includeItemUrl}">
                                    <c:url var="itemReviewUrl" value="/action/Item/Item/Review">
                                        <c:param name="ItemName" value="${vendorItem.item.itemName}" />
                                    </c:url>
                                    <a href="${itemReviewUrl}"><c:out value="${vendorItem.item.itemName}" /></a>
                                </c:when>
                                <c:otherwise>
                                    <c:out value="${vendorItem.item.itemName}" />
                                </c:otherwise>
                            </c:choose>
                        </display:column>
                        <display:column titleKey="columnTitle.vendorItemName">
                            <c:out value="${vendorItem.vendorItemName}" />
                        </display:column>
                        <display:column titleKey="columnTitle.description">
                            <c:out value="${vendorItem.description}" />
                        </display:column>
                        <display:column titleKey="columnTitle.priority">
                            <c:out value="${vendorItem.priority}" />
                        </display:column>
                        <display:column titleKey="columnTitle.cancellationPolicy">
                            <c:if test="${vendorItem.cancellationPolicy != null}">
                                <c:choose>
                                    <c:when test="${includeCancellationPolicyUrl}">
                                        <c:url var="reviewUrl" value="/action/CancellationPolicy/CancellationPolicy/Review">
                                            <c:param name="CancellationKindName" value="${vendorItem.cancellationPolicy.cancellationKind.cancellationKindName}" />
                                            <c:param name="CancellationPolicyName" value="${vendorItem.cancellationPolicy.cancellationPolicyName}" />
                                        </c:url>
                                        <a href="${reviewUrl}"><c:out value="${vendorItem.cancellationPolicy.description}" /></a>
                                    </c:when>
                                    <c:otherwise>
                                        <c:out value="${vendorItem.cancellationPolicy.description}" />
                                    </c:otherwise>
                                </c:choose>
                            </c:if>
                        </display:column>
                        <display:column titleKey="columnTitle.returnPolicy">
                            <c:if test="${vendorItem.returnPolicy != null}">
                                <c:choose>
                                    <c:when test="${includeReturnPolicyUrl}">
                                        <c:url var="reviewUrl" value="/action/ReturnPolicy/ReturnPolicy/Review">
                                            <c:param name="ReturnKindName" value="${vendorItem.returnPolicy.returnKind.returnKindName}" />
                                            <c:param name="ReturnPolicyName" value="${vendorItem.returnPolicy.returnPolicyName}" />
                                        </c:url>
                                        <a href="${reviewUrl}"><c:out value="${vendorItem.returnPolicy.description}" /></a>
                                    </c:when>
                                    <c:otherwise>
                                        <c:out value="${vendorItem.returnPolicy.description}" />
                                    </c:otherwise>
                                </c:choose>
                            </c:if>
                        </display:column>
                        <display:column titleKey="columnTitle.status">
                            <c:choose>
                                <c:when test="${includeEditableVendorItemStatus}">
                                    <c:url var="statusUrl" value="/action/Purchasing/VendorItem/Status">
                                        <c:param name="ReturnUrl" value="${returnUrl}" />
                                        <c:param name="VendorName" value="${vendor.vendorName}" />
                                        <c:param name="VendorItemName" value="${vendorItem.vendorItemName}" />
                                    </c:url>
                                    <a href="${statusUrl}"><c:out value="${vendorItem.vendorItemStatus.workflowStep.description}" /></a>
                                </c:when>
                                <c:otherwise>
                                    <c:out value="${vendorItem.vendorItemStatus.workflowStep.description}" />
                                </c:otherwise>
                            </c:choose>
                        </display:column>
                        <display:column>
                            <c:url var="vendorItemCostsUrl" value="/action/Purchasing/VendorItemCost/Main">
                                <c:param name="VendorName" value="${vendor.vendorName}" />
                                <c:param name="VendorItemName" value="${vendorItem.vendorItemName}" />
                            </c:url>
                            <a href="${vendorItemCostsUrl}">Costs</a><br />
                            <c:url var="editUrl" value="/action/Purchasing/VendorItem/Edit">
                                <c:param name="VendorName" value="${vendor.vendorName}" />
                                <c:param name="OriginalVendorItemName" value="${vendorItem.vendorItemName}" />
                            </c:url>
                            <a href="${editUrl}">Edit</a>
                            <c:url var="deleteUrl" value="/action/Purchasing/VendorItem/Delete">
                                <c:param name="VendorName" value="${vendor.vendorName}" />
                                <c:param name="VendorItemName" value="${vendorItem.vendorItemName}" />
                            </c:url>
                            <a href="${deleteUrl}">Delete</a>
                        </display:column>
                        <et:hasSecurityRole securityRole="Event.List">
                            <display:column>
                                <c:url var="eventsUrl" value="/action/Core/Event/Main">
                                    <c:param name="EntityRef" value="${vendorItem.entityInstance.entityRef}" />
                                </c:url>
                                <a href="${eventsUrl}">Events</a>
                            </display:column>
                        </et:hasSecurityRole>
                    </display:table>
                    <c:url var="resultsUrl" value="/action/Purchasing/VendorItem/Main">
                        <c:param name="Results" value="Complete" />
                        <c:if test="${param.VendorName != null}">
                            <c:param name="VendorName" value="${param.VendorName}" />
                        </c:if>
                        <c:if test="${param.ItemName != null}">
                            <c:param name="ItemName" value="${param.ItemName}" />
                        </c:if>
                    </c:url>
                    <a href="${resultsUrl}">All Results</a>
                </c:otherwise>
            </c:choose>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
