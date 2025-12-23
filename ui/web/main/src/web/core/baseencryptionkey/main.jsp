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
        <title>Base Encryption Keys</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Core/Main" />">Core</a> &gt;&gt;
                Base Encryption Keys
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="Event.List:BaseEncryptionKey.Review" />
            <et:hasSecurityRole securityRole="BaseEncryptionKey.Review" var="includeReviewUrl" />
            <display:table name="baseEncryptionKeys" id="baseEncryptionKey" class="displaytag">
                <display:column titleKey="columnTitle.name">
                    <c:choose>
                        <c:when test="${includeReviewUrl}">
                            <c:url var="reviewUrl" value="/action/Core/BaseEncryptionKey/Review">
                                <c:param name="BaseEncryptionKeyName" value="${baseEncryptionKey.baseEncryptionKeyName}" />
                            </c:url>
                            <a href="${reviewUrl}"><c:out value="${baseEncryptionKey.baseEncryptionKeyName}" /></a>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${baseEncryptionKey.baseEncryptionKeyName}" />
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column titleKey="columnTitle.status">
                    <c:url var="statusUrl" value="/action/Core/BaseEncryptionKey/Status">
                        <c:param name="BaseEncryptionKeyName" value="${baseEncryptionKey.baseEncryptionKeyName}" />
                    </c:url>
                    <a href="${statusUrl}"><c:out value="${baseEncryptionKey.baseEncryptionKeyStatus.workflowStep.description}" /></a>
                </display:column>
                <display:column titleKey="columnTitle.created">
                    <c:out value="${baseEncryptionKey.entityInstance.entityTime.createdTime}" />
                </display:column>
                <display:column titleKey="columnTitle.modified">
                    <c:out value="${baseEncryptionKey.entityInstance.entityTime.modifiedTime}" />
                </display:column>
                <et:hasSecurityRole securityRole="Event.List">
                    <display:column>
                        <c:url var="eventsUrl" value="/action/Core/Event/Main">
                            <c:param name="EntityRef" value="${baseEncryptionKey.entityInstance.entityRef}" />
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
