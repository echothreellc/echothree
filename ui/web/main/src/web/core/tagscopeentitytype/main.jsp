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
        <title>Entity Types</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Core/Main" />">Core</a> &gt;&gt;
                <a href="<c:url value="/action/Core/TagScope/Main" />">Tag Scopes</a> &gt;&gt;
                Entity Types
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="TagScopeEntityType.Create:TagScopeEntityType.Delete" />
            <et:hasSecurityRole securityRole="TagScopeEntityType.Create">
                <c:url var="addUrl" value="/action/Core/TagScopeEntityType/Add/Step1">
                    <c:param name="TagScopeName" value="${tagScope.tagScopeName}" />
                </c:url>
                <p><a href="${addUrl}">Add Entity Type.</a></p>
            </et:hasSecurityRole>
            <display:table name="tagScopeEntityTypes.list" id="tagScopeEntityType" class="displaytag">
                <display:column titleKey="columnTitle.componentVendor">
                    <c:out value="${tagScopeEntityType.entityType.componentVendor.description}" />
                </display:column>
                <display:column titleKey="columnTitle.entityType">
                    <c:out value="${tagScopeEntityType.entityType.description}" />
                </display:column>
                <et:hasSecurityRole securityRoles="TagScopeEntityType.Delete">
                    <display:column>
                        <et:hasSecurityRole securityRole="TagScopeEntityType.Delete">
                            <c:url var="deleteUrl" value="/action/Core/TagScopeEntityType/Delete">
                                <c:param name="TagScopeName" value="${tagScopeEntityType.tagScope.tagScopeName}" />
                                <c:param name="ComponentVendorName" value="${tagScopeEntityType.entityType.componentVendor.componentVendorName}" />
                                <c:param name="EntityTypeName" value="${tagScopeEntityType.entityType.entityTypeName}" />
                            </c:url>
                            <a href="${deleteUrl}">Delete</a>
                        </et:hasSecurityRole>
                    </display:column>
                </et:hasSecurityRole>
            </display:table>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
