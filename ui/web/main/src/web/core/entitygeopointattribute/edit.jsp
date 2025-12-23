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
        <title>Entity Geo Point Attribute</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Core/Main" />">Core</a> &gt;&gt;
                Entity Geo Point Attribute &gt;&gt;
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
                    <html:form action="/Core/EntityGeoPointAttribute/Edit" method="POST" focus="latitude">
                        <table>
                            <tr>
                                <td align=right><fmt:message key="label.latitude" />:</td>
                                <td>
                                    <html:text property="latitude" size="12" maxlength="12" />&deg; (*)
                                    <et:validationErrors id="errorMessage" property="Latitude">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td align=right><fmt:message key="label.longitude" />:</td>
                                <td>
                                    <html:text property="longitude" size="12" maxlength="12" />&deg; (*)
                                    <et:validationErrors id="errorMessage" property="Longitude">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td align=right><fmt:message key="label.elevation" />:</td>
                                <td>
                                    <html:text property="elevation" size="12" maxlength="12" />
                                    <html:select property="elevationUnitOfMeasureTypeChoice">
                                        <html:optionsCollection property="elevationUnitOfMeasureTypeChoices" />
                                    </html:select>
                                    <et:validationErrors id="errorMessage" property="Elevation">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                    <et:validationErrors id="errorMessage" property="ElevationUnitOfMeasureTypeName">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td align=right><fmt:message key="label.altitude" />:</td>
                                <td>
                                    <html:text property="altitude" size="12" maxlength="12" />
                                    <html:select property="altitudeUnitOfMeasureTypeChoice">
                                        <html:optionsCollection property="altitudeUnitOfMeasureTypeChoices" />
                                    </html:select>
                                    <et:validationErrors id="errorMessage" property="Altitude">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                    <et:validationErrors id="errorMessage" property="AltitudeUnitOfMeasureTypeName">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <html:hidden property="entityAttributeName" />
                                    <html:hidden property="entityRef" />
                                    <html:hidden property="returnUrl" />
                                </td>
                                <td><html:submit onclick="onSubmitDisable(this);" />&nbsp;<html:reset /><html:hidden property="submitButton" /></td>
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
