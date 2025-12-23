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
        <title>Review (<c:out value="${offer.offerName}" />)</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Advertising/Main" />">Advertising</a> &gt;&gt;
                <a href="<c:url value="/action/Advertising/Offer/Main" />">Offers</a> &gt;&gt;
                Review (<c:out value="${offer.offerName}" />)
            </h2>
        </div>
        <div id="Content">
            <p><font size="+2"><b><c:out value="${offer.description}" /></b></font></p>
            <br />
            Offer Name: ${offer.offerName}<br />
            <br />
            Company: <c:out value="${offer.department.division.company.partyGroup.name}" /><br />
            Division: <c:out value="${offer.department.division.partyGroup.name}" /><br />
            Department: <c:out value="${offer.department.partyGroup.name}" /><br />
            <br />
            Sales Order Sequence:
            <c:choose>
                <c:when test="${offer.salesOrderSequence == null}">
                    Using Default
                </c:when>
                <c:otherwise>
                    <c:url var="reviewUrl" value="/action/Sequence/Sequence/Review">
                        <c:param name="SequenceTypeName" value="${offer.salesOrderSequence.sequenceType.sequenceTypeName}" />
                        <c:param name="SequenceName" value="${offer.salesOrderSequence.sequenceName}" />
                    </c:url>
                    <a href="${reviewUrl}"><c:out value="${offer.salesOrderSequence.description}" /></a>
                </c:otherwise>
            </c:choose>
            <br />
            <br />
            Selector:
            <c:choose>
                <c:when test="${offer.salesOrderSequence == null}">
                    <i><fmt:message key="phrase.notSetManuallyMaintained" /></i>
                </c:when>
                <c:otherwise>
                    <c:url var="reviewUrl" value="/action/Selector/Selector/Review">
                        <c:param name="SelectorKindName" value="${offer.offerItemSelector.selectorType.selectorKind.selectorKindName}" />
                        <c:param name="SelectorTypeName" value="${offer.offerItemSelector.selectorType.selectorTypeName}" />
                        <c:param name="SelectorName" value="${offer.offerItemSelector.selectorName}" />
                    </c:url>
                    <a href="${reviewUrl}"><c:out value="${offer.offerItemSelector.description}" /></a>
                </c:otherwise>
            </c:choose>
            <br />
            Price Filter:
            <c:choose>
                <c:when test="${offer.salesOrderSequence == null}">
                    <i><fmt:message key="phrase.notSetUsingItemDefault" /></i>
                </c:when>
                <c:otherwise>
                    <c:url var="reviewUrl" value="/action/Filter/Filter/Review">
                        <c:param name="FilterKindName" value="${offer.offerItemPriceFilter.filterType.filterKind.filterKindName}" />
                        <c:param name="FilterTypeName" value="${offer.offerItemPriceFilter.filterType.filterTypeName}" />
                        <c:param name="FilterName" value="${offer.offerItemPriceFilter.filterName}" />
                    </c:url>
                    <a href="${reviewUrl}"><c:out value="${offer.offerItemPriceFilter.description}" /></a>
                </c:otherwise>
            </c:choose>
            <br />
            <br />
            <br />
            <br />
            <h2>Offer Name Elements</h2>
            <display:table name="offer.offerNameElements.list" id="offerNameElement" class="displaytag">
                <display:column titleKey="columnTitle.offerNameElement">
                    <c:out value="${offerNameElement.description}" />
                </display:column>
                <display:column titleKey="columnTitle.value">
                    <c:out value="${fn:substring(offer.offerName, offerNameElement.offset, offerNameElement.offset + offerNameElement.length)}" />
                </display:column>
            </display:table>
            <br />
            <c:if test='${offer.offerCustomerTypes.size > 0}'>
                <h2>Customer Types</h2>
                <display:table name="offer.offerCustomerTypes.list" id="offerCustomerType" class="displaytag">
                    <display:column titleKey="columnTitle.customerType">
                        <c:url var="reviewUrl" value="/action/Customer/CustomerType/Review">
                            <c:param name="CustomerTypeName" value="${offerCustomerType.customerType.customerTypeName}" />
                        </c:url>
                        <a href="${reviewUrl}"><c:out value="${offerCustomerType.customerType.description}" /></a>
                    </display:column>
                    <display:column property="sortOrder" titleKey="columnTitle.sortOrder" />
                    <display:column titleKey="columnTitle.default">
                        <c:choose>
                            <c:when test="${offerCustomerType.isDefault}">
                                Default
                            </c:when>
                            <c:otherwise>
                                <c:url var="setDefaultUrl" value="/action/Advertising/OfferCustomerType/SetDefault">
                                    <c:param name="OfferName" value="${offerCustomerType.offer.offerName}" />
                                    <c:param name="CustomerTypeName" value="${offerCustomerType.customerType.customerTypeName}" />
                                </c:url>
                                <a href="${setDefaultUrl}">Set Default</a>
                            </c:otherwise>
                        </c:choose>
                    </display:column>
                    <display:column>
                        <c:url var="deleteUrl" value="/action/Advertising/OfferCustomerType/Delete">
                            <c:param name="OfferName" value="${offerCustomerType.offer.offerName}" />
                            <c:param name="CustomerTypeName" value="${offerCustomerType.customerType.customerTypeName}" />
                        </c:url>
                        <a href="${deleteUrl}">Delete</a>
                    </display:column>
                </display:table>
                <br />
            </c:if>
            <br />
            <br />
            <br />
            <c:set var="commonUrl" scope="request" value="Accounting/CompanyContactMechanism" />
            <c:set var="entityAttributeGroups" scope="request" value="${offer.entityAttributeGroups}" />
            <c:set var="entityInstance" scope="request" value="${offer.entityInstance}" />
            <c:url var="returnUrl" scope="request" value="/../action/Advertising/Offer/Review">
                <c:param name="OfferName" value="${offer.offerName}" />
            </c:url>
            <jsp:include page="../../include/entityAttributeGroups.jsp" />
            <jsp:include page="../../include/entityInstance.jsp" />
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
