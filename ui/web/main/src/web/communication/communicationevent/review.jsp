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
        <title>Review (<c:out value="${communicationEvent.communicationEventName}" />)</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                Review (<c:out value="${communicationEvent.communicationEventName}" />)
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="WorkEffort.Review" />
            <et:hasSecurityRole securityRole="WorkEffort.Review" var="includeWorkEffortReviewUrl" />
            <p><font size="+2"><b><c:out value="${communicationEvent.document.description}" /></b></font></p>
            <br />
            Communication Event Name: ${communicationEvent.communicationEventName}<br />
            <br />
            <c:if test="${communicationEvent.communicationSource != null}">
                Communication Source: <c:out value="${communicationEvent.communicationSource.description}" /><br />
            </c:if>
            Communication Event Purpose: <c:out value="${communicationEvent.communicationEventPurpose.description}" /><br />
            <c:if test="${communicationEvent.originalCommunicationEvent != null}">
                Original Communication Event: <c:out value="${communicationEvent.originalCommunicationEvent.communicationEventName}" /><br />
            </c:if>
            <c:if test="${communicationEvent.parentCommunicationEvent != null}">
                Parent Communication Event: <c:out value="${communicationEvent.parentCommunicationEvent.communicationEventName}" /><br />
            </c:if>
            Contact Mechanism Name: <c:out value="${communicationEvent.partyContactMechanism.contactMechanism.contactMechanismName}" /><br />
            <br />
            <c:choose>
                <c:when test="${communicationEvent.ownedWorkEfforts.map.RECEIVE_CUSTOMER_EMAIL != null}">
                    <c:set var="workEffort" value="${communicationEvent.ownedWorkEfforts.map.RECEIVE_CUSTOMER_EMAIL}" />
                </c:when>
                <c:when test="${communicationEvent.ownedWorkEfforts.map.SEND_CUSTOMER_EMAIL != null}">
                    <c:set var="workEffort" value="${communicationEvent.ownedWorkEfforts.map.SEND_CUSTOMER_EMAIL}" />
                </c:when>
            </c:choose>
            <c:if test="${workEffort != null}">
                <fmt:message key="label.workEffort" />:
                <c:choose>
                    <c:when test="${includeWorkEffortReviewUrl}">
                        <c:url var="workEffortUrl" value="/action/Work/WorkEffort/Review">
                            <c:param name="WorkEffortName" value="${workEffort.workEffortName}" />
                        </c:url>
                        <a href="${workEffortUrl}"><c:out value="${workEffort.workEffortName}" /></a>
                    </c:when>
                    <c:otherwise>
                        <c:out value="${workEffort.workEffortName}" />
                    </c:otherwise>
                </c:choose>
                <br />
            </c:if>
            <br />
            <br />
            Created: <c:out value="${communicationEvent.entityInstance.entityTime.createdTime}" /><br />
            <c:if test='${communicationEvent.entityInstance.entityTime.modifiedTime != null}'>
                Modified: <c:out value="${communicationEvent.entityInstance.entityTime.modifiedTime}" /><br />
            </c:if>
            <c:url var="eventsUrl" value="/action/Core/Event/Main">
                <c:param name="EntityRef" value="${communicationEvent.entityInstance.entityRef}" />
            </c:url>
            <a href="${eventsUrl}">Events</a>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
