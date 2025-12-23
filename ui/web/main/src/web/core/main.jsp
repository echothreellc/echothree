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
        <title><fmt:message key="navigation.core" /></title>
        <%@ include file="../include/environment-b.jsp" %>
    </head>
    <%@ include file="../include/body-start-b.jsp" %>
        <et:checkSecurityRoles securityRoles="ComponentVendor.List:CommandMessageType.List:EntityAttributeGroup.List:TagScope.List:MimeType.List:EventGroup.List:UserVisitGroup.List:BaseEncryptionKey.List:CacheEntry.List:QueueType.List:Application.List:Editor.List:Appearance.List:Color.List" />
        <%@ include file="../include/breadcrumb/breadcrumbs-start.jsp" %>
            <jsp:include page="../include/breadcrumb/portal.jsp">
                <jsp:param name="showAsLink" value="true"/>
            </jsp:include>
            <jsp:include page="navigation.jsp">
                <jsp:param name="showAsLink" value="false"/>
            </jsp:include>
        <%@ include file="../include/breadcrumb/breadcrumbs-end.jsp" %>
        <et:hasSecurityRole securityRole="ComponentVendor.List">
            <a href="<c:url value="/action/Core/ComponentVendor/Main" />">Component Vendors</a><br />
        </et:hasSecurityRole>
        <et:hasSecurityRole securityRole="CommandMessageType.List">
            <a href="<c:url value="/action/Core/CommandMessageType/Main" />">Command Message Types</a><br />
        </et:hasSecurityRole>
        <et:hasSecurityRole securityRole="EntityAttributeGroup.List">
            <a href="<c:url value="/action/Core/EntityAttributeGroup/Main" />">Entity Attribute Groups</a><br />
        </et:hasSecurityRole>
        <et:hasSecurityRole securityRole="TagScope.List">
            <a href="<c:url value="/action/Core/TagScope/Main" />">Tag Scopes</a><br />
        </et:hasSecurityRole>
        <et:hasSecurityRole securityRole="MimeType.List">
            <a href="<c:url value="/action/Core/MimeType/Main" />">Mime Types</a><br />
        </et:hasSecurityRole>
        <et:hasSecurityRole securityRole="EventGroup.List">
            <a href="<c:url value="/action/Core/EventGroup/Main" />">Event Groups</a><br />
        </et:hasSecurityRole>
        <et:hasSecurityRole securityRole="UserVisitGroup.List">
            <a href="<c:url value="/action/Core/UserVisitGroup/Main" />">User Visit Groups</a><br />
        </et:hasSecurityRole>
        <et:hasSecurityRole securityRole="BaseEncryptionKey.List">
            <a href="<c:url value="/action/Core/BaseEncryptionKey/Main" />">Base Encryption Keys</a><br />
        </et:hasSecurityRole>
        <et:hasSecurityRole securityRole="CacheEntry.List">
            <a href="<c:url value="/action/Core/CacheEntry/Main" />">Cache Entries</a><br />
        </et:hasSecurityRole>
        <et:hasSecurityRole securityRole="QueueType.List">
            <a href="<c:url value="/action/Core/QueueType/Main" />">Queue Types</a><br />
        </et:hasSecurityRole>
        <et:hasSecurityRole securityRole="Application.List">
            <a href="<c:url value="/action/Core/Application/Main" />">Applications</a><br />
        </et:hasSecurityRole>
        <et:hasSecurityRole securityRole="Editor.List">
            <a href="<c:url value="/action/Core/Editor/Main" />">Editors</a><br />
        </et:hasSecurityRole>
        <et:hasSecurityRole securityRole="Appearance.List">
            <a href="<c:url value="/action/Core/Appearance/Main" />">Appearances</a><br />
        </et:hasSecurityRole>
        <et:hasSecurityRole securityRole="Color.List">
            <a href="<c:url value="/action/Core/Color/Main" />">Colors</a><br />
        </et:hasSecurityRole>
    <%@ include file="../include/body-end-b.jsp" %>
</html>
