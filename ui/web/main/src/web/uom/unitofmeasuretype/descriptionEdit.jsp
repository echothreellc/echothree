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
        <title>Unit of Measure Type Descriptions</title>
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
                <c:url var="descriptionsUrl" value="/action/UnitOfMeasure/UnitOfMeasureType/Description">
                    <c:param name="UnitOfMeasureKindName" value="${unitOfMeasureKindName}" />
                    <c:param name="UnitOfMeasureTypeName" value="${unitOfMeasureTypeName}" />
                </c:url>
                <a href="${descriptionsUrl}">Descriptions</a> &gt;&gt;
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
                    <html:form action="/UnitOfMeasure/UnitOfMeasureType/DescriptionEdit" method="POST" focus="singularDescription">
                        <table>
                            <tr>
                                <td align=right><fmt:message key="label.singularDescription" />:</td>
                                <td>
                                    <html:text property="singularDescription" size="60" maxlength="132" /> (*)
                                    <et:validationErrors id="errorMessage" property="SingularDescription">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td align=right><fmt:message key="label.pluralDescription" />:</td>
                                <td>
                                    <html:text property="pluralDescription" size="60" maxlength="132" /> (*)
                                    <et:validationErrors id="errorMessage" property="PluralDescription">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td align=right><fmt:message key="label.symbol" />:</td>
                                <td>
                                    <html:text property="symbol" size="20" maxlength="20" /> (*)
                                    <et:validationErrors id="errorMessage" property="Symbol">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <html:hidden property="unitOfMeasureKindName" />
                                    <html:hidden property="unitOfMeasureTypeName" />
                                    <html:hidden property="languageIsoName" />
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