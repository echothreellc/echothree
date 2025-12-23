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
        <title>Review (<c:out value="${customerType.customerTypeName}" />)</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Customer/Main" />">Customers</a> &gt;&gt;
                <a href="<c:url value="/action/Customer/CustomerType/Main" />">Types</a> &gt;&gt;
                Review (<c:out value="${customerType.customerTypeName}" />)
            </h2>
        </div>
        <div id="Content">
            <p><font size="+2"><b><c:out value="${customerType.description}" /></b></font></p>
            <br />
            Customer Type Name: ${customerType.customerTypeName}<br />
            <br />
            <br />
            <br />
            <c:if test='${offerCustomerTypes != null}'>
                <h2>Offers</h2>
                <display:table name="offerCustomerTypes" id="offerCustomerType" class="displaytag">
                    <display:column titleKey="columnTitle.offer">
                        <c:url var="reviewUrl" value="/action/Advertising/Offer/Review">
                            <c:param name="OfferName" value="${offerCustomerType.offer.offerName}" />
                        </c:url>
                        <a href="${reviewUrl}"><c:out value="${offerCustomerType.offer.description}" /></a>
                    </display:column>
                    <display:column>
                        <c:url var="deleteUrl" value="/action/Customer/CustomerType/OfferCustomerTypeDelete">
                            <c:param name="OfferName" value="${offerCustomerType.offer.offerName}" />
                            <c:param name="CustomerTypeName" value="${offerCustomerType.customerType.customerTypeName}" />
                        </c:url>
                        <a href="${deleteUrl}">Delete</a>
                    </display:column>
                </display:table>
                <br />
            </c:if>
            Created: <c:out value="${customerType.entityInstance.entityTime.createdTime}" /><br />
            <c:if test='${customerType.entityInstance.entityTime.modifiedTime != null}'>
                Modified: <c:out value="${customerType.entityInstance.entityTime.modifiedTime}" /><br />
            </c:if>
            <c:if test='${customerType.entityInstance.entityTime.deletedTime != null}'>
                Deleted: <c:out value="${customerType.entityInstance.entityTime.deletedTime}" /><br />
            </c:if>
            <et:checkSecurityRoles securityRoles="Event.List" />
            <et:hasSecurityRole securityRole="Event.List">
                <c:url var="eventsUrl" value="/action/Core/Event/Main">
                    <c:param name="EntityRef" value="${customerType.entityInstance.entityRef}" />
                </c:url>
                <a href="${eventsUrl}">Events</a>
            </et:hasSecurityRole>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
