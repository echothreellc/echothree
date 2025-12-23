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
        <title>Carriers</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Shipping/Main" />">Shipping</a> &gt;&gt;
                Carriers
            </h2>
        </div>
        <div id="Content">
            <p><a href="<c:url value="/action/Shipping/Carrier/Add" />">Add Carrier.</a></p>
            <et:checkSecurityRoles securityRoles="Event.List" />
            <display:table name="carriers" id="carrier" class="displaytag">
                <display:column titleKey="columnTitle.name">
                    <c:url var="reviewUrl" value="/action/Shipping/Carrier/Review">
                        <c:param name="CarrierName" value="${carrier.carrierName}" />
                    </c:url>
                    <a href="${reviewUrl}"><c:out value="${carrier.carrierName}" /></a>
                </display:column>
                <display:column property="sortOrder" titleKey="columnTitle.sortOrder" />
                <display:column titleKey="columnTitle.default">
                    <c:choose>
                        <c:when test="${carrier.isDefault}">
                            Default
                        </c:when>
                        <c:otherwise>
                            <c:url var="setDefaultUrl" value="/action/Shipping/Carrier/SetDefault">
                                <c:param name="CarrierName" value="${carrier.carrierName}" />
                            </c:url>
                            <a href="${setDefaultUrl}">Set Default</a>
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column titleKey="columnTitle.carrierName">
                    <c:out value="${carrier.partyGroup.name}" />
                </display:column>
                <display:column>
                    <c:url var="carrierContactMechanismsUrl" value="/action/Shipping/CarrierContactMechanism/Main">
                        <c:param name="CarrierName" value="${carrier.carrierName}" />
                    </c:url>
                    <a href="${carrierContactMechanismsUrl}">Contact Mechanisms</a>
                    <c:url var="carrierServicesUrl" value="/action/Shipping/CarrierService/Main">
                        <c:param name="CarrierName" value="${carrier.carrierName}" />
                    </c:url>
                    <a href="${carrierServicesUrl}">Carrier Services</a>
                    <c:url var="carrierOptionsUrl" value="/action/Shipping/CarrierOption/Main">
                        <c:param name="CarrierName" value="${carrier.carrierName}" />
                    </c:url>
                    <a href="${carrierOptionsUrl}">Carrier Options</a><br />
                    <c:url var="editUrl" value="/action/Shipping/Carrier/Edit">
                        <c:param name="OriginalCarrierName" value="${carrier.carrierName}" />
                    </c:url>
                    <a href="${editUrl}">Edit</a>
                    <c:url var="deleteUrl" value="/action/Shipping/Carrier/Delete">
                        <c:param name="CarrierName" value="${carrier.carrierName}" />
                    </c:url>
                    <a href="${deleteUrl}">Delete</a>
                </display:column>
                <et:hasSecurityRole securityRole="Event.List">
                    <display:column>
                        <c:url var="eventsUrl" value="/action/Core/Event/Main">
                            <c:param name="EntityRef" value="${carrier.entityInstance.entityRef}" />
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
