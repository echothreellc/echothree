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
        <title>Shipment Types</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Shipping/Main" />">Shipping</a> &gt;&gt;
                Shipment Types
            </h2>
        </div>
        <div id="Content">
            <p><a href="<c:url value="/action/Shipping/ShipmentType/Add" />">Add Shipping Method.</a></p>
            <display:table name="shipmentTypes" id="shipmentType" class="displaytag">
                <display:column titleKey="columnTitle.name">
                    <c:url var="reviewUrl" value="/action/Shipping/ShipmentType/Review">
                        <c:param name="ShipmentTypeName" value="${shipmentType.shipmentTypeName}" />
                    </c:url>
                    <a href="${reviewUrl}"><c:out value="${shipmentType.shipmentTypeName}" /></a>
                </display:column>
                <display:column titleKey="columnTitle.description">
                    <c:out value="${shipmentType.description}" />
                </display:column>
                <display:column titleKey="columnTitle.parent">
                    <c:url var="parentShipmentTypeUrl" value="/action/Shipping/ShipmentType/Review">
                        <c:param name="ShipmentTypeName" value="${shipmentType.parentShipmentType.shipmentTypeName}" />
                    </c:url>
                    <a href="${parentShipmentTypeUrl}"><c:out value="${shipmentType.parentShipmentType.shipmentTypeName}" /></a>
                </display:column>
                <display:column titleKey="columnTitle.sequenceType">
                    <c:url var="shipmentSequenceTypeUrl" value="/action/Sequence/SequenceType/Review">
                        <c:param name="SequenceTypeName" value="${shipmentType.shipmentSequenceType.sequenceTypeName}" />
                    </c:url>
                    <a href="${shipmentSequenceTypeUrl}"><c:out value="${shipmentType.shipmentSequenceType.shipmentTypeName}" /></a>
                </display:column>
                <display:column>
                    <c:url var="shipmentTypeShippingMethodsUrl" value="/action/Shipping/ShipmentTypeShippingMethod/Main">
                        <c:param name="ShipmentTypeName" value="${shipmentType.shipmentTypeName}" />
                    </c:url>
                    <a href="${shipmentTypeShippingMethodsUrl}">Shipping Methods</a>
                </display:column>
            </display:table>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
