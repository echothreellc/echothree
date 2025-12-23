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
        <title>Review (${carrierService.carrierServiceName}, ${carrierOption.carrierOptionName})</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Shipping/Main" />">Shipping</a> &gt;&gt;
                <a href="<c:url value="/action/Shipping/Carrier/Main" />">Carriers</a> &gt;&gt;
                <c:url var="carrierServicesUrl" value="/action/Shipping/CarrierService/Main">
                    <c:param name="CarrierName" value="${carrier.carrierName}" />
                </c:url>
                <a href="${carrierServicesUrl}">Carrier Services</a> &gt;&gt;
                <c:url var="carrierServiceOptionsUrl" value="/action/Shipping/CarrierServiceOption/Main">
                    <c:param name="CarrierName" value="${carrier.carrierName}" />
                    <c:param name="CarrierServiceName" value="${carrierService.carrierServiceName}" />
                </c:url>
                <a href="${carrierServiceOptionsUrl}">Carrier Service Options</a> &gt;&gt;
                Review (${carrierService.carrierServiceName}, ${carrierOption.carrierOptionName})
            </h2>
        </div>
        <div id="Content">
            <p><font size="+2"><b><c:out value="${carrierService.description}, ${carrierOption.description}" /></b></font></p>
            <br />
            Carrier Service Name: ${carrierService.carrierServiceName}<br />
            Carrier Option Name: ${carrierOption.carrierOptionName}<br />
            <br />
            <br />
            <br />
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
