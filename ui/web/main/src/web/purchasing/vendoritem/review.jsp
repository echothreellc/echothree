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
        <title>Review (<c:out value="${vendorItem.vendorItemName}" />)</title>
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
                    <c:param name="VendorName" value="${vendorItem.vendor.vendorName}" />
                </c:url>
                <a href="${vendorUrl}">Review (<c:out value="${vendorItem.vendor.vendorName}" />)</a> &gt;&gt;
                <c:url var="vendorItemsUrl" value="/action/Purchasing/VendorItem/Main">
                    <c:param name="VendorName" value="${vendorItem.vendor.vendorName}" />
                </c:url>
                <a href="${vendorItemsUrl}">Items</a> &gt;&gt;
                Review (<c:out value="${vendorItem.vendorItemName}" />)
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="VendorItemStatus.Choices:Vendor.Review:Item.Review:CancellationPolicy.Review:ReturnPolicy.Review" />
            <et:hasSecurityRole securityRole="Vendor.Review" var="includeVendorUrl" />
            <et:hasSecurityRole securityRole="Item.Review" var="includeItemUrl" />
            <et:hasSecurityRole securityRole="CancellationPolicy.Review" var="includeCancellationPolicyUrl" />
            <et:hasSecurityRole securityRole="ReturnPolicy.Review" var="includeReturnPolicyUrl" />
            <p><font size="+2"><b><c:out value="${vendorItem.vendor.vendorName}" />, <c:out value="${vendorItem.vendorItemName}" /></b></font></p>
            <br />
            Item:
            <c:choose>
                <c:when test="${includeItemUrl}">
                    <c:url var="reviewUrl" value="/action/Item/Item/Review">
                        <c:param name="ItemName" value="${vendorItem.item.itemName}" />
                    </c:url>
                    <a href="${reviewUrl}"><c:out value="${vendorItem.item.description}" /></a>
                </c:when>
                <c:otherwise>
                    <c:out value="${vendorItem.item.description}" />
                </c:otherwise>
            </c:choose>
            <br />
            Vendor:
            <c:choose>
                <c:when test="${includeVendorUrl}">
                    <c:url var="reviewUrl" value="/action/Purchasing/Vendor/Review">
                        <c:param name="VendorName" value="${vendorItem.vendor.vendorName}" />
                    </c:url>
                    <c:if test='${vendorItem.vendor.partyGroup.name != null}'>
                        <a href="${reviewUrl}"><c:out value="${vendorItem.vendor.partyGroup.name}" /></font></p>
                    </c:if>
                    <c:if test='${vendorItem.vendor.person.firstName != null || vendorItem.vendor.person.middleName != null || vendorItem.vendor.person.lastName != null}'>
                        <a href="${reviewUrl}"><c:out value="${vendorItem.vendor.person.personalTitle.description}" /> <c:out value="${vendorItem.vendor.person.firstName}" />
                            <c:out value="${vendorItem.vendor.person.middleName}" /> <c:out value="${vendorItem.vendor.person.lastName}" />
                            <c:out value="${vendorItem.vendor.person.nameSuffix.description}" /></a>
                    </c:if>
                </c:when>
                <c:otherwise>
                    <c:if test='${vendorItem.vendor.partyGroup.name != null}'>
                        <p><font size="+2"><c:out value="${vendorItem.vendor.partyGroup.name}" /></font></p>
                    </c:if>
                    <c:if test='${vendorItem.vendor.person.firstName != null || vendorItem.vendor.person.middleName != null || vendorItem.vendor.person.lastName != null}'>
                        <p><font size="+1"><b><c:out value="${vendorItem.vendor.person.personalTitle.description}" /> <c:out value="${vendorItem.vendor.person.firstName}" />
                            <c:out value="${vendorItem.vendor.person.middleName}" /> <c:out value="${vendorItem.vendor.person.lastName}" />
                            <c:out value="${vendorItem.vendor.person.nameSuffix.description}" /></b></font></p>
                    </c:if>
                </c:otherwise>
            </c:choose>
            <br />
            <br />
            Description:
            <c:choose>
                <c:when test="${vendorItem.description != null}">
                    <c:out value="${vendorItem.description}" />
                </c:when>
                <c:otherwise>
                    <i><fmt:message key="phrase.notSet" /></i>
                </c:otherwise>
            </c:choose>
            <br />
            Priority: <c:out value="${vendorItem.priority}" /><br />
            <br />
            Cancellation Policy:
            <c:choose>
                <c:when test="${vendorItem.cancellationPolicy != null}">
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
                </c:when>
                <c:otherwise>
                    <i><fmt:message key="phrase.notSet" /></i>
                </c:otherwise>
            </c:choose>
            <br />
            Return Policy:
            <c:choose>
                <c:when test="${vendorItem.returnPolicy != null}">
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
                </c:when>
                <c:otherwise>
                    <i><fmt:message key="phrase.notSet" /></i>
                </c:otherwise>
            </c:choose>
            <br />
            <br />
            Vendor Item Status: <c:out value="${vendorItem.vendorItemStatus.workflowStep.description}" />
            <et:hasSecurityRole securityRole="VendorItemStatus.Choices">
                <c:url var="returnUrl" scope="request" value="Review">
                    <c:param name="VendorName" value="${vendorItem.vendor.vendorName}" />
                    <c:param name="VendorItemName" value="${vendorItem.vendorItemName}" />
                </c:url>
                <c:url var="editUrl" value="/action/Purchasing/VendorItem/Status">
                    <c:param name="VendorName" value="${vendorItem.vendor.vendorName}" />
                    <c:param name="VendorItemName" value="${vendorItem.vendorItemName}" />
                    <c:param name="ReturnUrl" value="${returnUrl}" />
                </c:url>
                <a href="${editUrl}">Edit</a>
            </et:hasSecurityRole>
            <br />
            <br />
            <br />
            <c:set var="commonUrl" scope="request" value="Purchasing/VendorItem" />
            <c:set var="comments" scope="request" value="${vendorItem.comments.map['VENDOR_ITEM_PURCHASING']}" />
            <jsp:include page="../../include/vendorItemComments.jsp" />

            <c:set var="tagScopes" scope="request" value="${vendorItem.tagScopes}" />
            <c:set var="entityAttributeGroups" scope="request" value="${vendorItem.entityAttributeGroups}" />
            <c:set var="entityInstance" scope="request" value="${vendorItem.entityInstance}" />
            <c:url var="returnUrl" scope="request" value="/../action/Purchasing/VendorItem/Review">
                <c:param name="VendorName" value="${vendorItem.vendor.vendorName}" />
                <c:param name="VendorItemName" value="${vendorItem.vendorItemName}" />
            </c:url>
            <jsp:include page="../../include/tagScopes.jsp" />
            <jsp:include page="../../include/entityAttributeGroups.jsp" />
            <jsp:include page="../../include/entityInstance.jsp" />
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
