<!DOCTYPE html>
<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>

<!--                                                                                  -->
<!-- Copyright 2002-2018 Echo Three, LLC                                              -->
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
        <title><fmt:message key="navigation.humanResources" /></title>
        <%@ include file="../include/environment-b.jsp" %>
    </head>
    <%@ include file="../include/body-start-b.jsp" %>
        <et:checkSecurityRoles securityRoles="Employee.Search:Leave.Search:TrainingClass.List:ResponsibilityType.List:SkillType.List:LeaveReason.List:LeaveType.List:TerminationReason.List:TerminationType.List:EmployeeType.List" />
        <%@ include file="../include/breadcrumb/breadcrumbs-start.jsp" %>
            <jsp:include page="../include/breadcrumb/portal.jsp">
                <jsp:param name="showAsLink" value="true"/>
            </jsp:include>
            <jsp:include page="navigation.jsp">
                <jsp:param name="showAsLink" value="false"/>
            </jsp:include>
        <%@ include file="../include/breadcrumb/breadcrumbs-end.jsp" %>
        <et:hasSecurityRole securityRole="Employee.Search">
            <a href="<c:url value="/action/HumanResources/Employee/Main" />">Employees</a><br />
        </et:hasSecurityRole>
        <et:hasSecurityRole securityRole="Leave.Search">
            <a href="<c:url value="/action/HumanResources/Leave/Search" />">Leave</a><br />
        </et:hasSecurityRole>
        <et:hasSecurityRole securityRole="TrainingClass.List">
            <a href="<c:url value="/action/HumanResources/TrainingClass/Main" />">Training Classes</a><br />
        </et:hasSecurityRole>
        <et:hasSecurityRole securityRole="ResponsibilityType.List">
            <a href="<c:url value="/action/HumanResources/ResponsibilityType/Main" />">Responsibility Types</a><br />
        </et:hasSecurityRole>
        <et:hasSecurityRole securityRole="SkillType.List">
            <a href="<c:url value="/action/HumanResources/SkillType/Main" />">Skill Types</a><br />
        </et:hasSecurityRole>
        <et:hasSecurityRole securityRole="LeaveReason.List">
            <a href="<c:url value="/action/HumanResources/LeaveReason/Main" />">Leave Reasons</a><br />
        </et:hasSecurityRole>
        <et:hasSecurityRole securityRole="LeaveType.List">
            <a href="<c:url value="/action/HumanResources/LeaveType/Main" />">Leave Types</a><br />
        </et:hasSecurityRole>
        <et:hasSecurityRole securityRole="TerminationReason.List">
            <a href="<c:url value="/action/HumanResources/TerminationReason/Main" />">Termination Reasons</a><br />
        </et:hasSecurityRole>
        <et:hasSecurityRole securityRole="TerminationType.List">
            <a href="<c:url value="/action/HumanResources/TerminationType/Main" />">Termination Types</a><br />
        </et:hasSecurityRole>
        <et:hasSecurityRole securityRole="EmployeeType.List">
            <a href="<c:url value="/action/HumanResources/EmployeeType/Main" />">Employee Types</a><br />
        </et:hasSecurityRole>
    <%@ include file="../include/body-end-b.jsp" %>
</html>
