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
        <title>Review (<c:out value="${carrier.carrierName}" />)</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Shipping/Main" />">Shipping</a> &gt;&gt;
                <a href="<c:url value="/action/Shipping/Carrier/Main" />">Carriers</a> &gt;&gt;
                Review (<c:out value="${carrier.carrierName}" />)
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="Event.List" />
            <p><font size="+2"><b><c:out value="${carrier.partyGroup.name}" /></b></font></p>
            <br />
            Carrier Name: ${carrier.carrierName}<br />
            <br />
            <br />
            <br />
            <c:set var="commonUrl" scope="request" value="Shipping/CarrierContactMechanism" />
            <c:set var="party" scope="request" value="${carrier}" />
            <c:set var="partyContactMechanisms" scope="request" value="${carrier.partyContactMechanisms}" />
            <jsp:include page="../../include/partyContactMechanisms.jsp" />

            <c:set var="tagScopes" scope="request" value="${carrier.tagScopes}" />
            <c:set var="entityAttributeGroups" scope="request" value="${carrier.entityAttributeGroups}" />
            <c:set var="entityInstance" scope="request" value="${carrier.entityInstance}" />
            <c:url var="returnUrl" scope="request" value="/../action/Shipping/Carrier/Review">
                <c:param name="PartyName" value="${carrier.partyName}" />
            </c:url>
            <jsp:include page="../../include/tagScopes.jsp" />
            <jsp:include page="../../include/entityAttributeGroups.jsp" />
            <jsp:include page="../../include/entityInstance.jsp" />
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
