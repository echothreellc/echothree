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
        <title>Equivalents</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/UnitOfMeasure/Main" />">Units of Measure</a> &gt;&gt;
                <a href="<c:url value="/action/UnitOfMeasure/UnitOfMeasureKind/Main" />">Kinds</a> &gt;&gt;
                <c:url var="unitOfMeasureEquivalentsUrl" value="/action/UnitOfMeasure/UnitOfMeasureEquivalent/Main">
                    <c:param name="UnitOfMeasureKindName" value="${unitOfMeasureKindName}" />
                </c:url>
                <a href="${unitOfMeasureEquivalentsUrl}">Equivalents</a> &gt;&gt;
                Add
            </h2>
        </div>
        <div id="Content">
            <et:executionErrors id="errorMessage">
                <p class="executionErrors"><c:out value="${errorMessage}" /></p><br />
            </et:executionErrors>
            <html:form action="/UnitOfMeasure/UnitOfMeasureEquivalent/Add" method="POST" focus="toQuantity">
                <table>
                    <tr>
                        <td align=right><fmt:message key="label.fromUnitOfMeasureTypeChoice" />:</td>
                        <td>
                            <html:select property="fromUnitOfMeasureTypeChoice">
                                <html:optionsCollection property="fromUnitOfMeasureTypeChoices" />
                            </html:select> (*)
                            <et:validationErrors id="errorMessage" property="FromUnitOfMeasureTypeName">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.toUnitOfMeasureTypeChoice" />:</td>
                        <td>
                            <html:select property="toUnitOfMeasureTypeChoice">
                                <html:optionsCollection property="toUnitOfMeasureTypeChoices" />
                            </html:select> (*)
                            <et:validationErrors id="errorMessage" property="ToUnitOfMeasureTypeName">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.toQuantity" />:</td>
                        <td>
                            <html:text property="toQuantity" size="12" maxlength="12" /> (*)
                            <et:validationErrors id="errorMessage" property="ToQuantity">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <html:hidden property="unitOfMeasureKindName" />
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