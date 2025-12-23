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
        <title>Review (<c:out value="${communicationSource.communicationSourceName}" />)</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Configuration/Main" />">Configuration</a> &gt;&gt;
                <a href="<c:url value="/action/Configuration/CommunicationSource/Main" />">Communication Sources</a> &gt;&gt;
                Review (<c:out value="${communicationSource.communicationSourceName}" />)
            </h2>
        </div>
        <div id="Content">
            <p><font size="+2"><b><c:out value="${communicationSource.description}" /></b></font></p>
            <br />
            Communication Source Name: ${communicationSource.communicationSourceName}<br />
            <c:if test='${communicationSource.communicationSourceType.communicationSourceTypeName == "EMAIL"}'>
                <br />
                Server: <c:out value="${communicationSource.communicationEmailSource.server.serverName}" /><br />
                Username: <c:out value="${communicationSource.communicationEmailSource.username}" /><br />
                <br />
                Receive Work Effort Scope: <c:out value="${communicationSource.communicationEmailSource.receiveWorkEffortScope.description}" /><br />
                Send Work Effort Scope: <c:out value="${communicationSource.communicationEmailSource.sendWorkEffortScope.description}" /><br />
                <br />
                <c:if test='${communicationSource.communicationEmailSource.reviewEmployeeSelector != null}'>
                    Review Employee Selector: <c:out value="${communicationSource.communicationEmailSource.reviewEmployeeSelector.description}" /><br />
                </c:if>
            </c:if>
            <br />
            <br />
            <br />
            Created: <c:out value="${communicationSource.entityInstance.entityTime.createdTime}" /><br />
            <c:if test='${communicationSource.entityInstance.entityTime.modifiedTime != null}'>
                Modified: <c:out value="${communicationSource.entityInstance.entityTime.modifiedTime}" /><br />
            </c:if>
            <c:if test='${communicationSource.entityInstance.entityTime.deletedTime != null}'>
                Deleted: <c:out value="${communicationSource.entityInstance.entityTime.deletedTime}" /><br />
            </c:if>
            <et:checkSecurityRoles securityRoles="Event.List" />
            <et:hasSecurityRole securityRole="Event.List">
                <c:url var="eventsUrl" value="/action/Core/Event/Main">
                    <c:param name="EntityRef" value="${communicationSource.entityInstance.entityRef}" />
                </c:url>
                <a href="${eventsUrl}">Events</a>
            </et:hasSecurityRole>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
