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
        <title>Shipping Methods</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Shipping/Main" />">Shipping</a> &gt;&gt;
                Shipping Methods
            </h2>
        </div>
        <div id="Content">
            <p><a href="<c:url value="/action/Shipping/ShippingMethod/Add" />">Add Shipping Method.</a></p>
            <et:checkSecurityRoles securityRoles="Event.List" />
            <display:table name="shippingMethods" id="shippingMethod" class="displaytag">
                <display:column titleKey="columnTitle.name">
                    <c:url var="reviewUrl" value="/action/Shipping/ShippingMethod/Review">
                        <c:param name="ShippingMethodName" value="${shippingMethod.shippingMethodName}" />
                    </c:url>
                    <a href="${reviewUrl}"><c:out value="${shippingMethod.shippingMethodName}" /></a>
                </display:column>
                <display:column property="sortOrder" titleKey="columnTitle.sortOrder" />
                <display:column titleKey="columnTitle.description">
                    <c:out value="${shippingMethod.description}" />
                </display:column>
                <display:column>
                    <c:url var="shippingMethodCarrierServiceUrl" value="/action/Shipping/ShippingMethodCarrierService/Main">
                        <c:param name="ShippingMethodName" value="${shippingMethod.shippingMethodName}" />
                    </c:url>
                    <a href="${shippingMethodCarrierServiceUrl}">Carrier Services</a><br />
                    <c:url var="editUrl" value="/action/Shipping/ShippingMethod/Edit">
                        <c:param name="OriginalShippingMethodName" value="${shippingMethod.shippingMethodName}" />
                    </c:url>
                    <a href="${editUrl}">Edit</a>
                    <c:url var="descriptionsUrl" value="/action/Shipping/ShippingMethod/Description">
                        <c:param name="ShippingMethodName" value="${shippingMethod.shippingMethodName}" />
                    </c:url>
                    <a href="${descriptionsUrl}">Descriptions</a>
                    <c:url var="deleteUrl" value="/action/Shipping/ShippingMethod/Delete">
                        <c:param name="ShippingMethodName" value="${shippingMethod.shippingMethodName}" />
                    </c:url>
                    <a href="${deleteUrl}">Delete</a>
                </display:column>
                <et:hasSecurityRole securityRole="Event.List">
                    <display:column>
                        <c:url var="eventsUrl" value="/action/Core/Event/Main">
                            <c:param name="EntityRef" value="${shippingMethod.entityInstance.entityRef}" />
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
