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
        <title>Jobs</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Configuration/Main" />">Configuration</a> &gt;&gt;
                <a href="<c:url value="/action/Configuration/PrinterGroup/Main" />">Printer Groups</a> &gt;&gt;
                Jobs
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="Event.List" />
            <display:table name="printerGroupJobs" id="printerGroupJob" class="displaytag">
                <display:column property="printerGroupJobName" titleKey="columnTitle.name" />
                <display:column titleKey="columnTitle.description">
                    <c:out value="${printerGroupJob.document.description}" />
                </display:column>
                <display:column titleKey="columnTitle.mimeType">
                    <c:out value="${printerGroupJob.document.mimeType.description}" />
                </display:column>
                <display:column titleKey="columnTitle.copies">
                    <c:out value="${printerGroupJob.copies}" />
                </display:column>
                <display:column titleKey="columnTitle.priority">
                    <c:out value="${printerGroupJob.priority}" />
                </display:column>
                <display:column titleKey="columnTitle.pages">
                    <c:out value="${printerGroupJob.document.pages}" />
                </display:column>
                <display:column titleKey="columnTitle.status">
                    <c:url var="statusUrl" value="/action/Configuration/PrinterGroupJob/Status">
                        <c:param name="PrinterGroupName" value="${printerGroupJob.printerGroup.printerGroupName}" />
                        <c:param name="PrinterGroupJobName" value="${printerGroupJob.printerGroupJobName}" />
                    </c:url>
                    <a href="${statusUrl}"><c:out value="${printerGroupJob.printerGroupJobStatus.workflowStep.description}" /></a>
                </display:column>
                <display:column>
                    <c:url var="deleteUrl" value="/action/Configuration/PrinterGroupJob/Delete">
                        <c:param name="PrinterGroupJobName" value="${printerGroupJob.printerGroupJobName}" />
                    </c:url>
                    <a href="${deleteUrl}">Delete</a>
                </display:column>
                <et:hasSecurityRole securityRole="Event.List">
                    <display:column>
                        <c:url var="eventsUrl" value="/action/Core/Event/Main">
                            <c:param name="EntityRef" value="${printerGroupJob.entityInstance.entityRef}" />
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
