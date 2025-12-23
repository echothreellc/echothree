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
        <title>Printer Groups</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Configuration/Main" />">Configuration</a> &gt;&gt;
                Groups
            </h2>
        </div>
        <div id="Content">
            <p><a href="<c:url value="/action/Configuration/PrinterGroup/Add" />">Add Group.</a></p>
            <et:checkSecurityRoles securityRoles="Event.List" />
            <display:table name="printerGroups" id="printerGroup" class="displaytag">
                <display:column property="printerGroupName" titleKey="columnTitle.name" />
                <display:column titleKey="columnTitle.description">
                    <c:out value="${printerGroup.description}" />
                </display:column>
                <display:column property="sortOrder" titleKey="columnTitle.sortOrder" />
                <display:column property="keepPrintedJobsTime" titleKey="columnTitle.keepPrintedJobsTime" />
                <display:column titleKey="columnTitle.default">
                    <c:choose>
                        <c:when test="${printerGroup.isDefault}">
                            Default
                        </c:when>
                        <c:otherwise>
                            <c:url var="setDefaultUrl" value="/action/Configuration/PrinterGroup/SetDefault">
                                <c:param name="PrinterGroupName" value="${printerGroup.printerGroupName}" />
                            </c:url>
                            <a href="${setDefaultUrl}">Set Default</a>
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column titleKey="columnTitle.status">
                    <c:url var="statusUrl" value="/action/Configuration/PrinterGroup/Status">
                        <c:param name="PrinterGroupName" value="${printerGroup.printerGroupName}" />
                    </c:url>
                    <a href="${statusUrl}"><c:out value="${printerGroup.printerGroupStatus.workflowStep.description}" /></a>
                </display:column>
                <display:column>
                    <c:url var="printersUrl" value="/action/Configuration/Printer/Main">
                        <c:param name="PrinterGroupName" value="${printerGroup.printerGroupName}" />
                    </c:url>
                    <a href="${printersUrl}">Printers</a>
                    <c:url var="jobsUrl" value="/action/Configuration/PrinterGroupJob/Main">
                        <c:param name="PrinterGroupName" value="${printerGroup.printerGroupName}" />
                    </c:url>
                    <a href="${jobsUrl}">Jobs</a><br />
                    <c:url var="editUrl" value="/action/Configuration/PrinterGroup/Edit">
                        <c:param name="OriginalPrinterGroupName" value="${printerGroup.printerGroupName}" />
                    </c:url>
                    <a href="${editUrl}">Edit</a>
                    <c:url var="descriptionsUrl" value="/action/Configuration/PrinterGroup/Description">
                        <c:param name="PrinterGroupName" value="${printerGroup.printerGroupName}" />
                    </c:url>
                    <a href="${descriptionsUrl}">Descriptions</a>
                    <c:url var="deleteUrl" value="/action/Configuration/PrinterGroup/Delete">
                        <c:param name="PrinterGroupName" value="${printerGroup.printerGroupName}" />
                    </c:url>
                    <a href="${deleteUrl}">Delete</a>
                </display:column>
                <et:hasSecurityRole securityRole="Event.List">
                    <display:column>
                        <c:url var="eventsUrl" value="/action/Core/Event/Main">
                            <c:param name="EntityRef" value="${printerGroup.entityInstance.entityRef}" />
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
