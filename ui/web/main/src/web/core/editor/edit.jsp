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
                <a href="<c:url value="/action/Core/Editor/Main" />"><fmt:message key="navigation.editors" /></a> &gt;&gt;
                Edit
            </h2>
        </div>
        <div id="Content">
            <c:choose>
                <c:when test="${commandResult.executionResult.hasLockErrors}">
                    <et:executionErrors id="errorMessage">
                        <p class="executionErrors"><c:out value="${errorMessage}" /></p><br />
                    </et:executionErrors>
                </c:when>
                <c:otherwise>
                    <et:executionErrors id="errorMessage">
                        <p class="executionErrors"><c:out value="${errorMessage}" /></p><br />
                    </et:executionErrors>
                    <html:form action="/Core/Editor/Edit" method="POST" focus="editorName">
                        <table>
                            <tr>
                                <td align=right><fmt:message key="label.editorName" />:</td>
                                <td>
                                    <html:text property="editorName" size="40" maxlength="40" /> (*)
                                    <et:validationErrors id="errorMessage" property="EditorName">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td align=right><fmt:message key="label.hasDimensions" />:</td>
                                <td>
                                    <html:checkbox property="hasDimensions" /> (*)
                                    <et:validationErrors id="errorMessage" property="HasDimensions">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td align=right><fmt:message key="label.minimumHeight" />:</td>
                                <td>
                                    <html:text property="minimumHeight" size="12" maxlength="12" />
                                    <et:validationErrors id="errorMessage" property="MinimumHeight">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td align=right><fmt:message key="label.minimumWidth" />:</td>
                                <td>
                                    <html:text property="minimumWidth" size="12" maxlength="12" />
                                    <et:validationErrors id="errorMessage" property="MinimumWidth">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td align=right><fmt:message key="label.maximumHeight" />:</td>
                                <td>
                                    <html:text property="maximumHeight" size="12" maxlength="12" />
                                    <et:validationErrors id="errorMessage" property="MaximumHeight">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td align=right><fmt:message key="label.maximumWidth" />:</td>
                                <td>
                                    <html:text property="maximumWidth" size="12" maxlength="12" />
                                    <et:validationErrors id="errorMessage" property="MaximumWidth">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td align=right><fmt:message key="label.defaultHeight" />:</td>
                                <td>
                                    <html:text property="defaultHeight" size="12" maxlength="12" />
                                    <et:validationErrors id="errorMessage" property="DefaultHeight">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td align=right><fmt:message key="label.defaultWidth" />:</td>
                                <td>
                                    <html:text property="defaultWidth" size="12" maxlength="12" />
                                    <et:validationErrors id="errorMessage" property="DefaultWidth">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td align=right><fmt:message key="label.isDefault" />:</td>
                                <td>
                                    <html:checkbox property="isDefault" /> (*)
                                    <et:validationErrors id="errorMessage" property="IsDefault">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td align=right><fmt:message key="label.sortOrder" />:</td>
                                <td>
                                    <html:text property="sortOrder" size="12" maxlength="12" /> (*)
                                    <et:validationErrors id="errorMessage" property="SortOrder">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td align=right><fmt:message key="label.description" />:</td>
                                <td>
                                    <html:text property="description" size="60" maxlength="132" />
                                    <et:validationErrors id="errorMessage" property="Description">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <html:hidden property="originalEditorName" />
                                </td>
                                <td><html:submit onclick="onSubmitDisable(this);" />&nbsp;<html:cancel onclick="onSubmitDisable(this);" />&nbsp;<html:reset /><html:hidden property="submitButton" /></td>
                            </tr>
                        </table>
                    </html:form>
                </c:otherwise>
            </c:choose>
        </div>
        <jsp:include page="../../include/entityLock.jsp" />
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>