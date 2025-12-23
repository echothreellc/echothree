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
        <title>Letter Sources</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Chain/Main" />">Chain</a> &gt;&gt;
                Letter Sources
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="Event.List:LetterSource.Create:LetterSource.Edit:LetterSource.Delete:LetterSource.Review:LetterSource.Description" />
            <et:hasSecurityRole securityRole="LetterSource.Create">
                <p><a href="<c:url value="/action/Chain/LetterSource/Add" />">Add Letter Source.</a></p>
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRole="LetterSource.Review" var="includeReviewUrl" />
            <display:table name="letterSources" id="letterSource" class="displaytag">
                <display:column titleKey="columnTitle.name">
                    <c:choose>
                        <c:when test="${includeReviewUrl}">
                            <c:url var="reviewUrl" value="/action/Chain/LetterSource/Review">
                                <c:param name="LetterSourceName" value="${letterSource.letterSourceName}" />
                            </c:url>
                            <a href="${reviewUrl}"><c:out value="${letterSource.letterSourceName}" /></a>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${letterSource.letterSourceName}" />
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column titleKey="columnTitle.description">
                    <c:out value="${letterSource.description}" />
                </display:column>
                <display:column property="sortOrder" titleKey="columnTitle.sortOrder" />
                <display:column titleKey="columnTitle.default">
                    <c:choose>
                        <c:when test="${letterSource.isDefault}">
                            Default
                        </c:when>
                        <c:otherwise>
                            <et:hasSecurityRole securityRole="LetterSource.Edit">
                                <c:url var="setDefaultUrl" value="/action/Chain/LetterSource/SetDefault">
                                    <c:param name="LetterSourceName" value="${letterSource.letterSourceName}" />
                                </c:url>
                                <a href="${setDefaultUrl}">Set Default</a>
                            </et:hasSecurityRole>
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <et:hasSecurityRole securityRoles="LetterSource.Edit:LetterSource.Delete:LetterSource.Description">
                    <display:column>
                        <et:hasSecurityRole securityRole="LetterSource.Edit">
                            <c:url var="editUrl" value="/action/Chain/LetterSource/Edit">
                                <c:param name="OriginalLetterSourceName" value="${letterSource.letterSourceName}" />
                            </c:url>
                            <a href="${editUrl}">Edit</a>
                        </et:hasSecurityRole>
                        <et:hasSecurityRole securityRole="LetterSource.Description">
                            <c:url var="descriptionsUrl" value="/action/Chain/LetterSource/Description">
                                <c:param name="LetterSourceName" value="${letterSource.letterSourceName}" />
                            </c:url>
                        </et:hasSecurityRole>
                        <a href="${descriptionsUrl}">Descriptions</a>
                        <et:hasSecurityRole securityRole="LetterSource.Delete">
                            <c:url var="deleteUrl" value="/action/Chain/LetterSource/Delete">
                                <c:param name="LetterSourceName" value="${letterSource.letterSourceName}" />
                            </c:url>
                            <a href="${deleteUrl}">Delete</a>
                        </et:hasSecurityRole>
                    </display:column>
                </et:hasSecurityRole>
                <et:hasSecurityRole securityRole="Event.List">
                    <display:column>
                        <c:url var="eventsUrl" value="/action/Core/Event/Main">
                            <c:param name="EntityRef" value="${letterSource.entityInstance.entityRef}" />
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
