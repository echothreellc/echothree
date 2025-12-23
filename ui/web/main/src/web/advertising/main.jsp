<!DOCTYPE html>
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

<%@ include file="../include/taglibs.jsp" %>

<html>
    <head>
        <title><fmt:message key="pageTitle.advertising" /></title>
        <%@ include file="../include/environment-b.jsp" %>
    </head>
    <%@ include file="../include/body-start-b.jsp" %>
        <et:checkSecurityRoles securityRoles="Offer.List:Use.List:Source.List:UseType.List:OfferNameElement.List:UseNameElement.List" />
        <%@ include file="../include/breadcrumb/breadcrumbs-start.jsp" %>
            <jsp:include page="../include/breadcrumb/portal.jsp">
                <jsp:param name="showAsLink" value="true"/>
            </jsp:include>
            <jsp:include page="navigation.jsp">
                <jsp:param name="showAsLink" value="false"/>
            </jsp:include>
        <%@ include file="../include/breadcrumb/breadcrumbs-end.jsp" %>
        <et:hasSecurityRole securityRole="Offer.List">
            <a href="<c:url value="/action/Advertising/Offer/Main" />">Offers</a><br />
        </et:hasSecurityRole>
        <et:hasSecurityRole securityRole="Use.List">
            <a href="<c:url value="/action/Advertising/Use/Main" />">Uses</a><br />
        </et:hasSecurityRole>
        <et:hasSecurityRole securityRole="Source.List">
            <a href="<c:url value="/action/Advertising/Source/Main" />">Sources</a><br />
        </et:hasSecurityRole>
        <et:hasSecurityRole securityRole="UseType.List">
            <a href="<c:url value="/action/Advertising/UseType/Main" />">Use Types</a><br />
        </et:hasSecurityRole>
        <et:hasSecurityRole securityRole="OfferNameElement.List">
            <a href="<c:url value="/action/Advertising/OfferNameElement/Main" />">Offer Name Elements</a><br />
        </et:hasSecurityRole>
        <et:hasSecurityRole securityRole="UseNameElement.List">
            <a href="<c:url value="/action/Advertising/UseNameElement/Main" />">Use Name Elements</a><br />
        </et:hasSecurityRole>
    <%@ include file="../include/body-end-b.jsp" %>
</html>
