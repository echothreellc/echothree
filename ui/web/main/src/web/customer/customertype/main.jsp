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
        <title>Types</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Customer/Main" />">Customers</a> &gt;&gt;
                Types
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="Event.List:CustomerType.Create:CustomerType.Edit:CustomerType.Delete:CustomerType.Review:CustomerType.Description:OfferUse.Review:CustomerTypeCreditLimit.List:CustomerTypePaymentMethod.List:CustomerTypeShippingMethod.List" />
            <et:hasSecurityRole securityRoles="CustomerTypeCreditLimit.List:CustomerTypePaymentMethod.List:CustomerTypeShippingMethod.List">
                <c:set var="linksInFirstRow" value="true" />
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRoles="CustomerType.Edit:CustomerType.Description:CustomerType.Delete">
                <c:set var="linksInSecondRow" value="true" />
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRole="CustomerType.Create">
                <p><a href="<c:url value="/action/Customer/CustomerType/Add" />">Add Type.</a></p>
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRole="CustomerType.Review" var="includeReviewUrl" />
            <et:hasSecurityRole securityRole="OfferUse.Review" var="includeOfferUseReviewUrl" />
            <display:table name="customerTypes" id="customerType" class="displaytag">
                <display:column titleKey="columnTitle.name">
                    <c:choose>
                        <c:when test="${includeReviewUrl}">
                            <c:url var="reviewUrl" value="/action/Customer/CustomerType/Review">
                                <c:param name="CustomerTypeName" value="${customerType.customerTypeName}" />
                            </c:url>
                            <a href="${reviewUrl}"><c:out value="${customerType.customerTypeName}" /></a>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${customerType.customerTypeName}" />
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column titleKey="columnTitle.description">
                    <c:out value="${customerType.description}" />
                </display:column>
                <display:column titleKey="columnTitle.defaultOfferOfferUse">
                    <c:if test='${customerType.defaultOfferUse != null}'>
                        <c:choose>
                            <c:when test="${includeOfferUseReviewUrl}">
                                <c:url var="offerUseUrl" value="/action/Advertising/OfferUse/Review">
                                    <c:param name="OfferName" value="${customerType.defaultOfferUse.offer.offerName}" />
                                    <c:param name="UseName" value="${customerType.defaultOfferUse.use.useName}" />
                                </c:url>
                                <c:out value="${customerType.defaultOfferUse.offer.description}:${customerType.defaultOfferUse.use.description}" /> (<a href="${offerUseUrl}">${customerType.defaultOfferUse.offer.offerName}:${customerType.defaultOfferUse.use.useName}</a>)
                            </c:when>
                            <c:otherwise>
                                <c:out value="${customerType.defaultOfferUse.offer.description}:${customerType.defaultOfferUse.use.description}" /> (${customerType.defaultOfferUse.offer.offerName}:${customerType.defaultOfferUse.use.useName})
                            </c:otherwise>
                        </c:choose>
                    </c:if>
                </display:column>
                <display:column property="sortOrder" titleKey="columnTitle.sortOrder" />
                <display:column titleKey="columnTitle.default">
                    <c:choose>
                        <c:when test="${customerType.isDefault}">
                            Default
                        </c:when>
                        <c:otherwise>
                            <et:hasSecurityRole securityRole="CustomerType.Edit">
                                <c:url var="setDefaultUrl" value="/action/Customer/CustomerType/SetDefault">
                                    <c:param name="CustomerTypeName" value="${customerType.customerTypeName}" />
                                </c:url>
                                <a href="${setDefaultUrl}">Set Default</a>
                            </et:hasSecurityRole>
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <c:if test="${linksInFirstRow || linksInSecondRow}">
                    <display:column>
                        <et:hasSecurityRole securityRole="CustomerTypePaymentMethod.List">
                            <c:url var="customerTypePaymentMethodsUrl" value="/action/Customer/CustomerTypePaymentMethod/Main">
                                <c:param name="CustomerTypeName" value="${customerType.customerTypeName}" />
                            </c:url>
                            <a href="${customerTypePaymentMethodsUrl}">Payment Methods</a>
                        </et:hasSecurityRole>
                        <et:hasSecurityRole securityRole="CustomerTypeShippingMethod.List">
                            <c:url var="customerTypeShippingMethodsUrl" value="/action/Customer/CustomerTypeShippingMethod/Main">
                                <c:param name="CustomerTypeName" value="${customerType.customerTypeName}" />
                            </c:url>
                            <a href="${customerTypeShippingMethodsUrl}">Shipping Methods</a>
                        </et:hasSecurityRole>
                        <et:hasSecurityRole securityRole="CustomerTypeCreditLimit.List">
                            <c:url var="customerTypeCreditLimitsUrl" value="/action/Customer/CustomerTypeCreditLimit/Main">
                                <c:param name="CustomerTypeName" value="${customerType.customerTypeName}" />
                            </c:url>
                            <a href="${customerTypeCreditLimitsUrl}">Credit Limits</a>
                        </et:hasSecurityRole>
                        <c:if test="${linksInFirstRow && linksInSecondRow}">
                            <br />
                        </c:if>
                        <et:hasSecurityRole securityRole="CustomerType.Edit">
                            <c:url var="editUrl" value="/action/Customer/CustomerType/Edit">
                                <c:param name="OriginalCustomerTypeName" value="${customerType.customerTypeName}" />
                            </c:url>
                            <a href="${editUrl}">Edit</a>
                        </et:hasSecurityRole>
                        <et:hasSecurityRole securityRole="CustomerType.Description">
                            <c:url var="descriptionsUrl" value="/action/Customer/CustomerType/Description">
                                <c:param name="CustomerTypeName" value="${customerType.customerTypeName}" />
                            </c:url>
                            <a href="${descriptionsUrl}">Descriptions</a>
                        </et:hasSecurityRole>
                        <et:hasSecurityRole securityRole="CustomerType.Delete">
                            <c:url var="deleteUrl" value="/action/Customer/CustomerType/Delete">
                                <c:param name="CustomerTypeName" value="${customerType.customerTypeName}" />
                            </c:url>
                            <a href="${deleteUrl}">Delete</a>
                        </et:hasSecurityRole>
                    </display:column>
                </c:if>
                <et:hasSecurityRole securityRole="Event.List">
                    <display:column>
                        <c:url var="eventsUrl" value="/action/Core/Event/Main">
                            <c:param name="EntityRef" value="${customerType.entityInstance.entityRef}" />
                        </c:url>
                        <a href="${eventsUrl}">Events</a>
                    </display:column>
                </et:hasSecurityRole>
            </display:table>

        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
