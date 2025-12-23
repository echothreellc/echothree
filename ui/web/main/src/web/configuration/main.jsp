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
        <title><fmt:message key="navigation.configuration" /></title>
        <%@ include file="../include/environment-b.jsp" %>
    </head>
    <%@ include file="../include/body-start-b.jsp" %>
        <et:checkSecurityRoles securityRoles="PartyType.List:PersonalTitle.List:NameSuffix.List:DateTimeFormat.List:SearchKind.List:Index.List:Language.List:TimeZone.List:CommunicationEventPurpose.List:CommunicationSource.List:GeoCodeType.List:GeoCodeScope.List:Country.List:PostalAddressFormat.List:SecurityRoleGroup.List:PartySecurityRoleTemplate.List:Workflow.List:WorkEffortType.List:RecoveryQuestion.List:Job.List:PrinterGroup.List:PrinterGroupUseType.List:Protocol.List:Service.List:Server.List:ScaleType.List:Scale.List:ScaleUseType.List" />
        <%@ include file="../include/breadcrumb/breadcrumbs-start.jsp" %>
            <jsp:include page="../include/breadcrumb/portal.jsp">
                <jsp:param name="showAsLink" value="true"/>
            </jsp:include>
            <jsp:include page="navigation.jsp">
                <jsp:param name="showAsLink" value="false"/>
            </jsp:include>
        <%@ include file="../include/breadcrumb/breadcrumbs-end.jsp" %>
        <et:hasSecurityRole securityRole="PersonalTitle.List">
            <a href="<c:url value="/action/Configuration/PersonalTitle/Main" />">Personal Titles</a><br />
        </et:hasSecurityRole>
        <et:hasSecurityRole securityRole="NameSuffix.List">
            <a href="<c:url value="/action/Configuration/NameSuffix/Main" />">Name Suffixes</a><br />
        </et:hasSecurityRole>
        <et:hasSecurityRole securityRole="DateTimeFormat.List">
            <a href="<c:url value="/action/Configuration/DateTimeFormat/Main" />">Date Time Formats</a><br />
        </et:hasSecurityRole>
        <et:hasSecurityRole securityRole="Language.List">
            <a href="<c:url value="/action/Configuration/Language/Main" />">Languages</a><br />
        </et:hasSecurityRole>
        <et:hasSecurityRole securityRole="TimeZone.List">
            <a href="<c:url value="/action/Configuration/TimeZone/Main" />">Time Zones</a><br />
        </et:hasSecurityRole>
        <et:hasSecurityRole securityRole="PartyType.List">
            <a href="<c:url value="/action/Configuration/PartyType/Main" />">Party Types</a><br />
        </et:hasSecurityRole>
        <et:hasSecurityRole securityRole="CommunicationEventPurpose.List">
            <a href="<c:url value="/action/Configuration/CommunicationEventPurpose/Main" />">Communication Event Purposes</a><br />
        </et:hasSecurityRole>
        <et:hasSecurityRole securityRole="CommunicationSource.List">
            <a href="<c:url value="/action/Configuration/CommunicationSource/Main" />">Communication Sources</a><br />
        </et:hasSecurityRole>
        <et:hasSecurityRole securityRole="GeoCodeType.List">
            <a href="<c:url value="/action/Configuration/GeoCodeType/Main" />">Geo Code Types</a><br />
        </et:hasSecurityRole>
        <et:hasSecurityRole securityRole="GeoCodeScope.List">
            <a href="<c:url value="/action/Configuration/GeoCodeScope/Main" />">Geo Code Scopes</a><br />
        </et:hasSecurityRole>
        <et:hasSecurityRole securityRole="Country.List">
            <a href="<c:url value="/action/Configuration/Country/Main" />">Countries</a><br />
        </et:hasSecurityRole>
        <et:hasSecurityRole securityRole="PostalAddressFormat.List">
            <a href="<c:url value="/action/Configuration/PostalAddressFormat/Main" />">Postal Address Formats</a><br />
        </et:hasSecurityRole>
        <et:hasSecurityRole securityRole="SecurityRoleGroup.List">
            <a href="<c:url value="/action/Configuration/SecurityRoleGroup/Main" />">Security Role Groups</a><br />
        </et:hasSecurityRole>
        <et:hasSecurityRole securityRole="PartySecurityRoleTemplate.List">
            <a href="<c:url value="/action/Configuration/PartySecurityRoleTemplate/Main" />">Security Role Templates</a><br />
        </et:hasSecurityRole>
        <et:hasSecurityRole securityRole="RecoveryQuestion.List">
            <a href="<c:url value="/action/Configuration/RecoveryQuestion/Main" />">Recovery Questions</a><br />
        </et:hasSecurityRole>
        <et:hasSecurityRole securityRole="Workflow.List">
            <a href="<c:url value="/action/Configuration/Workflow/Main" />">Workflows</a><br />
        </et:hasSecurityRole>
        <et:hasSecurityRole securityRole="WorkEffortType.List">
            <a href="<c:url value="/action/Configuration/WorkEffortType/Main" />">Work Effort Types</a><br />
        </et:hasSecurityRole>
        <et:hasSecurityRole securityRole="SearchKind.List">
            <a href="<c:url value="/action/Configuration/SearchKind/Main" />">Search Kinds</a><br />
        </et:hasSecurityRole>
        <et:hasSecurityRole securityRole="Index.List">
            <a href="<c:url value="/action/Configuration/Index/Main" />">Indexes</a><br />
        </et:hasSecurityRole>
        <et:hasSecurityRole securityRole="Job.List">
            <a href="<c:url value="/action/Configuration/Job/Main" />">Jobs</a><br />
        </et:hasSecurityRole>
        <et:hasSecurityRole securityRole="PrinterGroup.List">
            <a href="<c:url value="/action/Configuration/PrinterGroup/Main" />">Printer Groups</a><br />
        </et:hasSecurityRole>
        <et:hasSecurityRole securityRole="PrinterGroupUseType.List">
            <a href="<c:url value="/action/Configuration/PrinterGroupUseType/Main" />">Printer Group Use Types</a><br />
        </et:hasSecurityRole>
        <et:hasSecurityRole securityRole="Protocol.List">
            <a href="<c:url value="/action/Configuration/Protocol/Main" />">Protocol</a><br />
        </et:hasSecurityRole>
        <et:hasSecurityRole securityRole="Service.List">
            <a href="<c:url value="/action/Configuration/Service/Main" />">Services</a><br />
        </et:hasSecurityRole>
        <et:hasSecurityRole securityRole="Server.List">
            <a href="<c:url value="/action/Configuration/Server/Main" />">Servers</a><br />
        </et:hasSecurityRole>
        <et:hasSecurityRole securityRole="ScaleType.List">
            <a href="<c:url value="/action/Configuration/ScaleType/Main" />">Scale Types</a><br />
        </et:hasSecurityRole>
        <et:hasSecurityRole securityRole="Scale.List">
            <a href="<c:url value="/action/Configuration/Scale/Main" />">Scales</a><br />
        </et:hasSecurityRole>
        <et:hasSecurityRole securityRole="ScaleUseType.List">
            <a href="<c:url value="/action/Configuration/ScaleUseType/Main" />">Scale Use Types</a><br />
        </et:hasSecurityRole>
    <%@ include file="../include/body-end-b.jsp" %>
</html>
