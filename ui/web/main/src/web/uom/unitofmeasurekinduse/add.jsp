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
        <title>Uses</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/UnitOfMeasure/Main" />">Units of Measure</a> &gt;&gt;
                <c:choose>
                    <c:when test="${unitOfMeasureKindName != null}">
                        <a href="<c:url value="/action/UnitOfMeasure/UnitOfMeasureKind/Main" />">Kinds</a>
                    </c:when>
                    <c:when test="${unitOfMeasureKindUseTypeName != null}">
                        <a href="<c:url value="/action/UnitOfMeasure/UnitOfMeasureKindUseType/Main" />">Use Types</a>
                    </c:when>
                </c:choose>
                &gt;&gt;
                <c:url var="unitOfMeasureKindUsesUrl" value="/action/UnitOfMeasure/UnitOfMeasureKindUse/Main">
                    <c:choose>
                        <c:when test="${unitOfMeasureKindName != null}">
                            <c:param name="UnitOfMeasureKindName" value="${unitOfMeasureKindName}" />
                        </c:when>
                        <c:when test="${unitOfMeasureKindUseTypeName != null}">
                            <c:param name="UnitOfMeasureKindUseTypeName" value="${unitOfMeasureKindUseTypeName}" />
                        </c:when>
                    </c:choose>
                </c:url>
                <a href="${unitOfMeasureKindUsesUrl}">Uses</a> &gt;&gt;
                Add
            </h2>
        </div>
        <div id="Content">
            <et:executionErrors id="errorMessage">
                <p class="executionErrors"><c:out value="${errorMessage}" /></p><br />
            </et:executionErrors>
            <html:form action="/UnitOfMeasure/UnitOfMeasureKindUse/Add" method="POST" focus="sortOrder">
                <table>
                    <c:choose>
                        <c:when test="${unitOfMeasureKindName != null}">
                            <tr>
                                <td align=right><fmt:message key="label.unitOfMeasureKindUseTypeChoice" />:</td>
                                <td>
                                    <html:select property="unitOfMeasureKindUseTypeChoice">
                                        <html:optionsCollection property="unitOfMeasureKindUseTypeChoices" />
                                    </html:select> (*)
                                    <et:validationErrors id="errorMessage" property="UnitOfMeasureKindUseTypeName">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                        </c:when>
                        <c:when test="${unitOfMeasureKindUseTypeName != null}">
                            <tr>
                                <td align=right><fmt:message key="label.unitOfMeasureKindChoice" />:</td>
                                <td>
                                    <html:select property="unitOfMeasureKindChoice">
                                        <html:optionsCollection property="unitOfMeasureKindChoices" />
                                    </html:select> (*)
                                    <et:validationErrors id="errorMessage" property="UnitOfMeasureKindName">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                        </c:when>
                    </c:choose>
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
                        <td>
                            <html:hidden property="unitOfMeasureKindName" />
                            <html:hidden property="unitOfMeasureKindUseTypeName" />
                            <html:hidden property="forwardParameter" />
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