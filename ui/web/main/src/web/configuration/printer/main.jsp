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
        <title>Printers</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Configuration/Main" />">Configuration</a> &gt;&gt;
                <a href="<c:url value="/action/Configuration/PrinterGroup/Main" />">Printer Groups</a> &gt;&gt;
                Printers
            </h2>
        </div>
        <div id="Content">
            <c:url var="addUrl" value="/action/Configuration/Printer/Add">
                <c:param name="PrinterGroupName" value="${printerGroup.printerGroupName}" />
            </c:url>
            <p><a href="${addUrl}">Add Printer.</a></p>
            <et:checkSecurityRoles securityRoles="Event.List" />
            <display:table name="printers" id="printer" class="displaytag">
                <display:column property="printerName" titleKey="columnTitle.name" />
                <display:column titleKey="columnTitle.description">
                    <c:out value="${printer.description}" />
                </display:column>
                <display:column property="priority" titleKey="columnTitle.priority" />
                <display:column titleKey="columnTitle.status">
                    <c:url var="statusUrl" value="/action/Configuration/Printer/Status">
                        <c:param name="PrinterGroupName" value="${printer.printerGroup.printerGroupName}" />
                        <c:param name="PrinterName" value="${printer.printerName}" />
                    </c:url>
                    <a href="${statusUrl}"><c:out value="${printer.printerStatus.workflowStep.description}" /></a>
                </display:column>
                <display:column>
                    <c:url var="editUrl" value="/action/Configuration/Printer/Edit">
                        <c:param name="PrinterGroupName" value="${printer.printerGroup.printerGroupName}" />
                        <c:param name="OriginalPrinterName" value="${printer.printerName}" />
                    </c:url>
                    <a href="${editUrl}">Edit</a>
                    <c:url var="descriptionsUrl" value="/action/Configuration/Printer/Description">
                        <c:param name="PrinterGroupName" value="${printer.printerGroup.printerGroupName}" />
                        <c:param name="PrinterName" value="${printer.printerName}" />
                    </c:url>
                    <a href="${descriptionsUrl}">Descriptions</a>
                    <c:url var="deleteUrl" value="/action/Configuration/Printer/Delete">
                        <c:param name="PrinterGroupName" value="${printer.printerGroup.printerGroupName}" />
                        <c:param name="PrinterName" value="${printer.printerName}" />
                    </c:url>
                    <a href="${deleteUrl}">Delete</a>
                </display:column>
                <et:hasSecurityRole securityRole="Event.List">
                    <display:column>
                        <c:url var="eventsUrl" value="/action/Core/Event/Main">
                            <c:param name="EntityRef" value="${printer.entityInstance.entityRef}" />
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
