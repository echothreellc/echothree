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
        <title><fmt:message key="pageTitle.editors" /></title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Core/Main" />"><fmt:message key="navigation.core" /></a> &gt;&gt;
                <fmt:message key="navigation.editors" />
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="Event.List:Editor.Create:Editor.Edit:Editor.Delete:Editor.Review:Editor.Description" />
            <et:hasSecurityRole securityRole="Editor.Create">
                <p><a href="<c:url value="/action/Core/Editor/Add" />">Add Editor.</a></p>
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRole="Editor.Review" var="includeReviewUrl" />
            <display:table name="editors" id="editor" class="displaytag">
                <display:column titleKey="columnTitle.name">
                    <c:choose>
                        <c:when test="${includeReviewUrl}">
                            <c:url var="reviewUrl" value="/action/Core/Editor/Review">
                                <c:param name="EditorName" value="${editor.editorName}" />
                            </c:url>
                            <a href="${reviewUrl}"><c:out value="${editor.editorName}" /></a>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${editor.editorName}" />
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column titleKey="columnTitle.description">
                    <c:out value="${editor.description}" />
                </display:column>
                <display:column titleKey="columnTitle.hasDimensions">
                    <c:choose>
                        <c:when test="${editor.hasDimensions}">
                            <fmt:message key="phrase.yes" />
                        </c:when>
                        <c:otherwise>
                            <fmt:message key="phrase.no" />
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column titleKey="columnTitle.minimumSize">
                    <c:if test="${editor.hasDimensions}">
                        <c:choose>
                            <c:when test="${editor.minimumHeight != null}">
                                <c:out value="${editor.minimumHeight}" />
                            </c:when>
                            <c:otherwise>
                                <i><fmt:message key="phrase.notSet" /></i>
                            </c:otherwise>
                        </c:choose>
                                x
                        <c:choose>
                            <c:when test="${editor.minimumWidth != null}">
                                <c:out value="${editor.minimumWidth}" />
                            </c:when>
                            <c:otherwise>
                                <i><fmt:message key="phrase.notSet" /></i>
                            </c:otherwise>
                        </c:choose>
                    </c:if>
                </display:column>
                <display:column titleKey="columnTitle.maximumSize">
                    <c:if test="${editor.hasDimensions}">
                        <c:choose>
                            <c:when test="${editor.maximumHeight != null}">
                                <c:out value="${editor.maximumHeight}" />
                            </c:when>
                            <c:otherwise>
                                <i><fmt:message key="phrase.notSet" /></i>
                            </c:otherwise>
                        </c:choose>
                                x
                        <c:choose>
                            <c:when test="${editor.maximumWidth != null}">
                                <c:out value="${editor.maximumWidth}" />
                            </c:when>
                            <c:otherwise>
                                <i><fmt:message key="phrase.notSet" /></i>
                            </c:otherwise>
                        </c:choose>
                    </c:if>
                </display:column>
                <display:column titleKey="columnTitle.defaultSize">
                    <c:if test="${editor.hasDimensions}">
                        <c:choose>
                            <c:when test="${editor.defaultHeight != null}">
                                <c:out value="${editor.defaultHeight}" />
                            </c:when>
                            <c:otherwise>
                                <i><fmt:message key="phrase.notSet" /></i>
                            </c:otherwise>
                        </c:choose>
                                x
                        <c:choose>
                            <c:when test="${editor.defaultWidth != null}">
                                <c:out value="${editor.defaultWidth}" />
                            </c:when>
                            <c:otherwise>
                                <i><fmt:message key="phrase.notSet" /></i>
                            </c:otherwise>
                        </c:choose>
                    </c:if>
                </display:column>
                <display:column property="sortOrder" titleKey="columnTitle.sortOrder" />
                <display:column titleKey="columnTitle.default">
                    <c:choose>
                        <c:when test="${editor.isDefault}">
                            Default
                        </c:when>
                        <c:otherwise>
                            <et:hasSecurityRole securityRole="Editor.Edit">
                                <c:url var="setDefaultUrl" value="/action/Core/Editor/SetDefault">
                                    <c:param name="EditorName" value="${editor.editorName}" />
                                </c:url>
                                <a href="${setDefaultUrl}">Set Default</a>
                            </et:hasSecurityRole>
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <et:hasSecurityRole securityRoles="Editor.Edit:Editor.Description:Editor.Delete">
                    <display:column>
                        <et:hasSecurityRole securityRole="Editor.Edit">
                            <c:url var="editUrl" value="/action/Core/Editor/Edit">
                                <c:param name="OriginalEditorName" value="${editor.editorName}" />
                            </c:url>
                            <a href="${editUrl}">Edit</a>
                        </et:hasSecurityRole>
                        <et:hasSecurityRole securityRole="Editor.Description">
                            <c:url var="descriptionsUrl" value="/action/Core/Editor/Description">
                                <c:param name="EditorName" value="${editor.editorName}" />
                            </c:url>
                            <a href="${descriptionsUrl}">Descriptions</a>
                        </et:hasSecurityRole>
                        <et:hasSecurityRole securityRole="Editor.Delete">
                            <c:url var="deleteUrl" value="/action/Core/Editor/Delete">
                                <c:param name="EditorName" value="${editor.editorName}" />
                            </c:url>
                            <a href="${deleteUrl}">Delete</a>
                        </et:hasSecurityRole>
                    </display:column>
                </et:hasSecurityRole>
                <et:hasSecurityRole securityRole="Event.List">
                    <display:column>
                        <c:url var="eventsUrl" value="/action/Core/Event/Main">
                            <c:param name="EntityRef" value="${editor.entityInstance.entityRef}" />
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
