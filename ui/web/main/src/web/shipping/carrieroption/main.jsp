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
        <title>Carrier Options</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Shipping/Main" />">Shipping</a> &gt;&gt;
                <a href="<c:url value="/action/Shipping/Carrier/Main" />">Carriers</a> &gt;&gt;
                Carrier Options
            </h2>
        </div>
        <div id="Content">
            <c:url var="addUrl" value="/action/Shipping/CarrierOption/Add">
                <c:param name="CarrierName" value="${carrier.carrierName}" />
            </c:url>
            <p><a href="${addUrl}">Add Carrier Option.</a></p>
            <et:checkSecurityRoles securityRoles="Event.List" />
            <display:table name="carrierOptions" id="carrierOption" class="displaytag">
                <display:column titleKey="columnTitle.name">
                    <c:url var="reviewUrl" value="/action/Shipping/CarrierOption/Review">
                        <c:param name="CarrierName" value="${carrierOption.carrier.carrierName}" />
                        <c:param name="CarrierOptionName" value="${carrierOption.carrierOptionName}" />
                    </c:url>
                    <a href="${reviewUrl}"><c:out value="${carrierOption.carrierOptionName}" /></a>
                </display:column>
                <display:column property="sortOrder" titleKey="columnTitle.sortOrder" />
                <display:column titleKey="columnTitle.default">
                    <c:choose>
                        <c:when test="${carrierOption.isDefault}">
                            Default
                        </c:when>
                        <c:otherwise>
                            <c:url var="setDefaultUrl" value="/action/Shipping/CarrierOption/SetDefault">
                                <c:param name="CarrierName" value="${carrierOption.carrier.carrierName}" />
                                <c:param name="CarrierOptionName" value="${carrierOption.carrierOptionName}" />
                            </c:url>
                            <a href="${setDefaultUrl}">Set Default</a>
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column titleKey="columnTitle.description">
                    <c:out value="${carrierOption.description}" />
                </display:column>
                <display:column>
                    <c:url var="editUrl" value="/action/Shipping/CarrierOption/Edit">
                        <c:param name="CarrierName" value="${carrierOption.carrier.carrierName}" />
                        <c:param name="OriginalCarrierOptionName" value="${carrierOption.carrierOptionName}" />
                    </c:url>
                    <a href="${editUrl}">Edit</a>
                    <c:url var="descriptionsUrl" value="/action/Shipping/CarrierOption/Description">
                        <c:param name="CarrierName" value="${carrierOption.carrier.carrierName}" />
                        <c:param name="CarrierOptionName" value="${carrierOption.carrierOptionName}" />
                    </c:url>
                    <a href="${descriptionsUrl}">Descriptions</a>
                    <c:url var="deleteUrl" value="/action/Shipping/CarrierOption/Delete">
                        <c:param name="CarrierName" value="${carrierOption.carrier.carrierName}" />
                        <c:param name="CarrierOptionName" value="${carrierOption.carrierOptionName}" />
                    </c:url>
                    <a href="${deleteUrl}">Delete</a>
                </display:column>
                <et:hasSecurityRole securityRole="Event.List">
                    <display:column>
                        <c:url var="eventsUrl" value="/action/Core/Event/Main">
                            <c:param name="EntityRef" value="${carrierOption.entityInstance.entityRef}" />
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
