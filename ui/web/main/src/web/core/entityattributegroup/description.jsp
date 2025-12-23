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
        <title><fmt:message key="pageTitle.entityAttributeGroupDescriptions" /></title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Core/Main" />"><fmt:message key="navigation.core" /></a> &gt;&gt;
                <a href="<c:url value="/action/Core/EntityAttributeGroup/Main" />"><fmt:message key="navigation.entityAttributeGroups" /></a> &gt;&gt;
                <fmt:message key="navigation.entityAttributeGroupDescriptions" />
            </h2>
        </div>
        <div id="Content">
            <c:url var="addUrl" value="/action/Core/EntityAttributeGroup/DescriptionAdd">
                <c:param name="EntityAttributeGroupName" value="${entityAttributeGroup.entityAttributeGroupName}" />
            </c:url>
            <p><a href="${addUrl}">Add Description.</a></p>
            <display:table name="entityAttributeGroupDescriptions" id="entityAttributeGroupDescription" class="displaytag">
                <display:column titleKey="columnTitle.language">
                    <c:out value="${entityAttributeGroupDescription.language.description}" />
                </display:column>
                <display:column titleKey="columnTitle.description">
                    <c:out value="${entityAttributeGroupDescription.description}" />
                </display:column>
                <display:column>
                    <c:url var="editUrl" value="/action/Core/EntityAttributeGroup/DescriptionEdit">
                        <c:param name="EntityAttributeGroupName" value="${entityAttributeGroupDescription.entityAttributeGroup.entityAttributeGroupName}" />
                        <c:param name="LanguageIsoName" value="${entityAttributeGroupDescription.language.languageIsoName}" />
                    </c:url>
                    <a href="${editUrl}">Edit</a>
                    <c:url var="deleteUrl" value="/action/Core/EntityAttributeGroup/DescriptionDelete">
                        <c:param name="EntityAttributeGroupName" value="${entityAttributeGroupDescription.entityAttributeGroup.entityAttributeGroupName}" />
                        <c:param name="LanguageIsoName" value="${entityAttributeGroupDescription.language.languageIsoName}" />
                    </c:url>
                    <a href="${deleteUrl}">Delete</a>
                </display:column>
           </display:table>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
