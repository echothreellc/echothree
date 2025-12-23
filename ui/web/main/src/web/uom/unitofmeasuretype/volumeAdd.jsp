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
        <title>Unit Of Measure Types</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/UnitOfMeasure/Main" />">Units of Measure</a> &gt;&gt;
                <a href="<c:url value="/action/UnitOfMeasure/UnitOfMeasureKind/Main" />">Kinds</a> &gt;&gt;
                <c:url var="unitOfMeasureTypesUrl" value="/action/UnitOfMeasure/UnitOfMeasureType/Main">
                    <c:param name="UnitOfMeasureKindName" value="${unitOfMeasureKindName}" />
                </c:url>
                <a href="${unitOfMeasureTypesUrl}">Types</a> &gt;&gt;
                Volume
            </h2>
        </div>
        <div id="Content">
            <et:executionErrors id="errorMessage">
                <p class="executionErrors"><c:out value="${errorMessage}" /></p><br />
            </et:executionErrors>
            <html:form action="/UnitOfMeasure/UnitOfMeasureType/VolumeAdd" method="POST" focus="height">
                <table>
                    <tr>
                        <td align=right><fmt:message key="label.height" />:</td>
                        <td>
                            <html:text property="height" size="12" maxlength="12" />
                            <html:select property="heightUnitOfMeasureTypeChoice">
                                <html:optionsCollection property="heightUnitOfMeasureTypeChoices" />
                            </html:select> (*)
                            <et:validationErrors id="errorMessage" property="Height">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                            <et:validationErrors id="errorMessage" property="HeightUnitOfMeasureTypeName">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.width" />:</td>
                        <td>
                            <html:text property="width" size="12" maxlength="12" />
                            <html:select property="widthUnitOfMeasureTypeChoice">
                                <html:optionsCollection property="widthUnitOfMeasureTypeChoices" />
                            </html:select> (*)
                            <et:validationErrors id="errorMessage" property="Width">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                            <et:validationErrors id="errorMessage" property="WidthUnitOfMeasureTypeName">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.depth" />:</td>
                        <td>
                            <html:text property="depth" size="12" maxlength="12" />
                            <html:select property="depthUnitOfMeasureTypeChoice">
                                <html:optionsCollection property="depthUnitOfMeasureTypeChoices" />
                            </html:select> (*)
                            <et:validationErrors id="errorMessage" property="Depth">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                            <et:validationErrors id="errorMessage" property="DepthUnitOfMeasureTypeName">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <html:hidden property="unitOfMeasureKindName" />
                            <html:hidden property="unitOfMeasureTypeName" />
                        </td>
                        <td><html:submit onclick="onSubmitDisable(this);" /><input type="hidden" name="submitButton" /></td>
                    </tr>
                </table>
            </html:form>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>