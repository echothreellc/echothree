<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>

<!--                                                                                  -->
<!-- Copyright 2002-2025 Echo Three, LLC                                              -->
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
        <title>Selector Descriptions</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Selector/Main" />">Selectors</a> &gt;&gt;
                <a href="<c:url value="/action/Selector/SelectorKind/Main" />">Kinds</a> &gt;&gt;
                <c:url var="selectorTypesUrl" value="/action/Selector/SelectorType/Main">
                    <c:param name="SelectorKindName" value="${selectorKindName}" />
                </c:url>
                <a href="${selectorTypesUrl}">Types</a> &gt;&gt;
                <c:url var="selectorsUrl" value="/action/Selector/Selector/Main">
                    <c:param name="SelectorKindName" value="${selectorKindName}" />
                    <c:param name="SelectorTypeName" value="${selectorTypeName}" />
                </c:url>
                <a href="${selectorsUrl}">Selectors</a> &gt;&gt;
                <c:url var="descriptionsUrl" value="/action/Selector/Selector/Description">
                    <c:param name="SelectorKindName" value="${selectorKindName}" />
                    <c:param name="SelectorTypeName" value="${selectorTypeName}" />
                    <c:param name="SelectorName" value="${selectorName}" />
                </c:url>
                <a href="${descriptionsUrl}">Descriptions</a> &gt;&gt;
                Add
            </h2>
        </div>
        <div id="Content">
            <et:executionErrors id="errorMessage">
                <p class="executionErrors"><c:out value="${errorMessage}" /></p><br />
            </et:executionErrors>
            <html:form action="/Selector/Selector/DescriptionAdd" method="POST" focus="description">
                <table>
                    <tr>
                        <td align=right><fmt:message key="label.language" />:</td>
                        <td>
                            <html:select property="languageChoice">
                                <html:optionsCollection property="languageChoices" />
                            </html:select> (*)
                            <et:validationErrors id="errorMessage" property="LanguageIsoName">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.description" />:</td>
                        <td>
                            <html:text property="description" size="60" maxlength="132" /> (*)
                            <et:validationErrors id="errorMessage" property="Description">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <html:hidden property="selectorKindName" />
                            <html:hidden property="selectorTypeName" />
                            <html:hidden property="selectorName" />
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