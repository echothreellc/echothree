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
        <title>Review (<c:out value="${associatePartyContactMechanism.associatePartyContactMechanismName}" />)</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Associate/Main" />">Associates</a> &gt;&gt;
                <a href="<c:url value="/action/Associate/AssociateProgram/Main" />">Associate Programs</a> &gt;&gt;
                <c:url var="associatesUrl" value="/action/Associate/Associate/Main">
                    <c:param name="AssociateProgramName" value="${associatePartyContactMechanism.associate.associateProgram.associateProgramName}" />
                </c:url>
                <a href="${associatesUrl}">Associates</a> &gt;&gt;
                <c:url var="associatesUrl" value="/action/Associate/AssociatePartyContactMechanism/Main">
                    <c:param name="AssociateProgramName" value="${associatePartyContactMechanism.associate.associateProgram.associateProgramName}" />
                    <c:param name="AssociateName" value="${associatePartyContactMechanism.associate.associateName}" />
                </c:url>
                <a href="${associatesUrl}">Contact Mechanisms</a> &gt;&gt;
                Review (<c:out value="${associatePartyContactMechanism.associatePartyContactMechanismName}" />)
            </h2>
        </div>
        <div id="Content">
            <p><font size="+2"><b><c:out value="${associatePartyContactMechanism.associatePartyContactMechanismName}" /></b></font></p>
            <br />
            <br />
            <br />
            <et:checkSecurityRoles securityRoles="Event.List" />
            Created: <c:out value="${associatePartyContactMechanism.entityInstance.entityTime.createdTime}" /><br />
            <c:if test='${associatePartyContactMechanism.entityInstance.entityTime.modifiedTime != null}'>
                Modified: <c:out value="${associatePartyContactMechanism.entityInstance.entityTime.modifiedTime}" /><br />
            </c:if>
            <c:if test='${associatePartyContactMechanism.entityInstance.entityTime.deletedTime != null}'>
                Deleted: <c:out value="${associatePartyContactMechanism.entityInstance.entityTime.deletedTime}" /><br />
            </c:if>
            <et:checkSecurityRoles securityRoles="Event.List" />
            <et:hasSecurityRole securityRole="Event.List">
                <c:url var="eventsUrl" value="/action/Core/Event/Main">
                    <c:param name="EntityRef" value="${associatePartyContactMechanism.entityInstance.entityRef}" />
                </c:url>
                <a href="${eventsUrl}">Events</a>
            </et:hasSecurityRole>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
