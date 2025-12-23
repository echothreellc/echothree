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
        <title>Comment Types</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Core/Main" />">Core</a> &gt;&gt;
                <a href="<c:url value="/action/Core/ComponentVendor/Main" />">Component Vendors</a> &gt;&gt;
                <c:url var="entityTypesUrl" value="/action/Core/EntityType/Main">
                    <c:param name="ComponentVendorName" value="${componentVendorName}" />
                </c:url>
                <a href="${entityTypesUrl}">Entity Types</a> &gt;&gt;
                Comment Types
            </h2>
        </div>
        <div id="Content">
            <c:url var="addUrl" value="/action/Core/CommentType/Add">
                <c:param name="ComponentVendorName" value="${componentVendorName}" />
                <c:param name="EntityTypeName" value="${entityTypeName}" />
            </c:url>
            <p><a href="${addUrl}">Add Comment Type.</a></p>
            <et:checkSecurityRoles securityRoles="Event.List" />
            <display:table name="commentTypes" id="commentType" class="displaytag">
                <display:column titleKey="columnTitle.name">
                    <c:url var="reviewUrl" value="/action/Core/CommentType/Review">
                        <c:param name="ComponentVendorName" value="${commentType.entityType.componentVendor.componentVendorName}" />
                        <c:param name="EntityTypeName" value="${commentType.entityType.entityTypeName}" />
                        <c:param name="CommentTypeName" value="${commentType.commentTypeName}" />
                    </c:url>
                    <a href="${reviewUrl}"><c:out value="${commentType.commentTypeName}" /></a>
                </display:column>
                <display:column titleKey="columnTitle.description">
                    <c:out value="${commentType.description}" />
                </display:column>
                <display:column titleKey="columnTitle.commentSequence">
                    <c:out value="${commentType.commentSequence.description}" />
                </display:column>
                <display:column titleKey="columnTitle.workflowEntrance">
                    <c:if test='${commentType.workflowEntrance != null}'>
                        <c:url var="workflowUrl" value="/action/Configuration/Workflow/Review">
                            <c:param name="WorkflowName" value="${commentType.workflowEntrance.workflow.workflowName}" />
                        </c:url>
                        <c:url var="workflowEntranceUrl" value="/action/Configuration/WorkflowEntrance/Review">
                            <c:param name="WorkflowName" value="${commentType.workflowEntrance.workflow.workflowName}" />
                            <c:param name="WorkflowEntranceName" value="${commentType.workflowEntrance.workflowEntranceName}" />
                        </c:url>
                        <a href="${workflowUrl}"><c:out value="${commentType.workflowEntrance.workflow.description}" /></a>,
                        <a href="${workflowEntranceUrl}"><c:out value="${commentType.workflowEntrance.description}" /></a>
                    </c:if>
                </display:column>
                <display:column titleKey="columnTitle.mimeTypeUsageType">
                    <c:out value="${commentType.mimeTypeUsageType.description}" />
                </display:column>
                <display:column titleKey="columnTitle.sortOrder">
                    <c:out value="${commentType.sortOrder}" />
                </display:column>
                <display:column>
                    <c:url var="commentUsageTypesUrl" value="/action/Core/CommentUsageType/Main">
                        <c:param name="ComponentVendorName" value="${commentType.entityType.componentVendor.componentVendorName}" />
                        <c:param name="EntityTypeName" value="${commentType.entityType.entityTypeName}" />
                        <c:param name="CommentTypeName" value="${commentType.commentTypeName}" />
                    </c:url>
                    <a href="${commentUsageTypesUrl}">Comment Usage Types</a><br />
                    <c:url var="editUrl" value="/action/Core/CommentType/Edit">
                        <c:param name="ComponentVendorName" value="${commentType.entityType.componentVendor.componentVendorName}" />
                        <c:param name="EntityTypeName" value="${commentType.entityType.entityTypeName}" />
                        <c:param name="OriginalCommentTypeName" value="${commentType.commentTypeName}" />
                    </c:url>
                    <a href="${editUrl}">Edit</a>
                    <c:url var="descriptionsUrl" value="/action/Core/CommentType/Description">
                        <c:param name="ComponentVendorName" value="${commentType.entityType.componentVendor.componentVendorName}" />
                        <c:param name="EntityTypeName" value="${commentType.entityType.entityTypeName}" />
                        <c:param name="CommentTypeName" value="${commentType.commentTypeName}" />
                    </c:url>
                    <a href="${descriptionsUrl}">Descriptions</a>
                    <c:url var="deleteUrl" value="/action/Core/CommentType/Delete">
                        <c:param name="ComponentVendorName" value="${commentType.entityType.componentVendor.componentVendorName}" />
                        <c:param name="EntityTypeName" value="${commentType.entityType.entityTypeName}" />
                        <c:param name="CommentTypeName" value="${commentType.commentTypeName}" />
                    </c:url>
                    <a href="${deleteUrl}">Delete</a>
                </display:column>
                <et:hasSecurityRole securityRole="Event.List">
                    <display:column>
                        <c:url var="eventsUrl" value="/action/Core/Event/Main">
                            <c:param name="EntityRef" value="${commentType.entityInstance.entityRef}" />
                        </c:url>
                        <a href="${eventsUrl}">Events</a>
                    </display:column>
                </et:hasSecurityRole>
           </display:table>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
