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
        <title>Work Effort Scopes</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Configuration/Main" />">Configuration</a> &gt;&gt;
                <a href="<c:url value="/action/Configuration/WorkEffortType/Main" />">Work Effort Types</a> &gt;&gt;
                <c:url var="workEffortScopesUrl" value="/action/Configuration/WorkEffortScope/Main">
                    <c:param name="WorkEffortTypeName" value="${workEffortScope.workEffortType.workEffortTypeName}" />
                </c:url>
                <a href="${workEffortScopesUrl}">Work Effort Scopes</a> &gt;&gt;
                Descriptions
            </h2>
        </div>
        <div id="Content">
            <c:url var="addUrl" value="/action/Configuration/WorkEffortScope/DescriptionAdd">
                <c:param name="WorkEffortTypeName" value="${workEffortScope.workEffortType.workEffortTypeName}" />
                <c:param name="WorkEffortScopeName" value="${workEffortScope.workEffortScopeName}" />
            </c:url>
            <p><a href="${addUrl}">Add Description.</a></p>
            <display:table name="workEffortScopeDescriptions" id="workEffortScopeDescription" class="displaytag">
                <display:column titleKey="columnTitle.language">
                    <c:out value="${workEffortScopeDescription.language.description}" />
                </display:column>
                <display:column titleKey="columnTitle.description">
                    <c:out value="${workEffortScopeDescription.description}" />
                </display:column>
                <display:column>
                    <c:url var="editUrl" value="/action/Configuration/WorkEffortScope/DescriptionEdit">
                        <c:param name="WorkEffortTypeName" value="${workEffortScopeDescription.workEffortScope.workEffortType.workEffortTypeName}" />
                        <c:param name="WorkEffortScopeName" value="${workEffortScopeDescription.workEffortScope.workEffortScopeName}" />
                        <c:param name="LanguageIsoName" value="${workEffortScopeDescription.language.languageIsoName}" />
                    </c:url>
                    <a href="${editUrl}">Edit</a>
                    <c:url var="deleteUrl" value="/action/Configuration/WorkEffortScope/DescriptionDelete">
                        <c:param name="WorkEffortTypeName" value="${workEffortScopeDescription.workEffortScope.workEffortType.workEffortTypeName}" />
                        <c:param name="WorkEffortScopeName" value="${workEffortScopeDescription.workEffortScope.workEffortScopeName}" />
                        <c:param name="LanguageIsoName" value="${workEffortScopeDescription.language.languageIsoName}" />
                    </c:url>
                    <a href="${deleteUrl}">Delete</a>
                </display:column>
           </display:table>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
