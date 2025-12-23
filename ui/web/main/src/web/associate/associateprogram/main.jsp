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
        <title>Associate Programs</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Associate/Main" />">Associates</a> &gt;&gt;
                Associate Programs
            </h2>
        </div>
        <div id="Content">
            <p><a href="<c:url value="/action/Associate/AssociateProgram/Add" />">Add Associate Program.</a></p>
            <et:checkSecurityRoles securityRoles="Event.List:Associate.List" />
            <display:table name="associatePrograms" id="associateProgram" class="displaytag">
                <display:column titleKey="columnTitle.name">
                    <c:url var="reviewUrl" value="/action/Associate/AssociateProgram/Review">
                        <c:param name="AssociateProgramName" value="${associateProgram.associateProgramName}" />
                    </c:url>
                    <a href="${reviewUrl}"><c:out value="${associateProgram.associateProgramName}" /></a>
                </display:column>
                <display:column property="sortOrder" titleKey="columnTitle.sortOrder" />
                <display:column titleKey="columnTitle.default">
                    <c:choose>
                        <c:when test="${associateProgram.isDefault}">
                            Default
                        </c:when>
                        <c:otherwise>
                            <c:url var="setDefaultUrl" value="/action/Associate/AssociateProgram/SetDefault">
                                <c:param name="AssociateProgramName" value="${associateProgram.associateProgramName}" />
                            </c:url>
                            <a href="${setDefaultUrl}">Set Default</a>
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column titleKey="columnTitle.description">
                    <c:out value="${associateProgram.description}" />
                </display:column>
                <display:column>
                    <et:hasSecurityRole securityRole="Associate.List">
                        <c:url var="associatesUrl" value="/action/Associate/Associate/Main">
                            <c:param name="AssociateProgramName" value="${associateProgram.associateProgramName}" />
                        </c:url>
                        <a href="${associatesUrl}">Associates</a><br />
                    </et:hasSecurityRole>
                    <c:url var="editUrl" value="/action/Associate/AssociateProgram/Edit">
                        <c:param name="OriginalAssociateProgramName" value="${associateProgram.associateProgramName}" />
                    </c:url>
                    <a href="${editUrl}">Edit</a>
                    <c:url var="descriptionsUrl" value="/action/Associate/AssociateProgram/Description">
                        <c:param name="AssociateProgramName" value="${associateProgram.associateProgramName}" />
                    </c:url>
                    <a href="${descriptionsUrl}">Descriptions</a>
                    <c:url var="deleteUrl" value="/action/Associate/AssociateProgram/Delete">
                        <c:param name="AssociateProgramName" value="${associateProgram.associateProgramName}" />
                    </c:url>
                    <a href="${deleteUrl}">Delete</a>
                </display:column>
                <et:hasSecurityRole securityRole="Event.List">
                    <display:column>
                        <c:url var="eventsUrl" value="/action/Core/Event/Main">
                            <c:param name="EntityRef" value="${associateProgram.entityInstance.entityRef}" />
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
