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
        <title>
            <fmt:message key="pageTitle.entityInstance">
                <fmt:param value="${entityInstance.entityRef}" />
            </fmt:message>
        </title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Core/Main" />"><fmt:message key="navigation.core" /></a> &gt;&gt;
                <a href="<c:url value="/action/Core/ComponentVendor/Main" />"><fmt:message key="navigation.componentVendors" /></a> &gt;&gt;
                <c:url var="entityTypesUrl" value="/action/Core/EntityType/Main">
                    <c:param name="ComponentVendorName" value="${entityInstance.entityType.componentVendor.componentVendorName}" />
                </c:url>
                <a href="${entityTypesUrl}"><fmt:message key="navigation.entityTypes" /></a> &gt;&gt;
                <c:url var="entityInstancesUrl" value="/action/Core/EntityInstance/Main">
                    <c:param name="ComponentVendorName" value="${entityInstance.entityType.componentVendor.componentVendorName}" />
                    <c:param name="EntityTypeName" value="${entityInstance.entityType.entityTypeName}" />
                </c:url>
                <a href="${entityInstancesUrl}"><fmt:message key="navigation.entityInstances" /></a> &gt;&gt;
                <fmt:message key="navigation.entityInstance">
                    <fmt:param value="${entityInstance.entityRef}" />
                </fmt:message>
            </h2>
        </div>
        <div id="Content">
            <p><font size="+2"><b><et:appearance appearance="${entityInstance.entityAppearance.appearance}"><c:out value="${entityInstance.entityRef}" /></et:appearance></b></font></p>
            <br />
            <c:url var="returnUrl" scope="request" value="/../action/Core/EntityInstance/Review">
                <c:param name="EntityRef" value="${entityInstance.entityRef}" />
            </c:url>

            <jsp:useBean id="entityInstance" scope="request" type="com.echothree.model.control.core.common.transfer.EntityInstanceTransfer"/>
            <c:forEach items="${entityInstance.comments.list}" var="comments">
                <h2><c:out value="${comments.commentType.description}" /> Comments</h2>
                <c:url var="addUrl" value="/action/Core/EntityInstance/CommentAdd">
                    <c:param name="EntityRef" value="${entityInstance.entityRef}" />
                    <c:param name="CommentTypeName" value="${comments.commentType.commentTypeName}" />
                </c:url>
                <p><a href="${addUrl}">Add <c:out value="${comments.commentType.description}" /> Comment.</a></p>
                <c:choose>
                    <c:when test='${comments.size == 0}'>
                        <br />
                    </c:when>
                    <c:otherwise>
                        <et:checkSecurityRoles securityRoles="Event.List" />
                        <display:table name="${comments.list}" id="comment" class="displaytag">
                            <display:column titleKey="columnTitle.dateTime">
                                <c:out value="${comment.entityInstance.entityTime.createdTime}" />
                            </display:column>
                            <display:column titleKey="columnTitle.comment">
                                <c:if test='${comment.description != null}'>
                                    <b><c:out value="${comment.description}" /></b><br />
                                </c:if>
                                <et:out value="${comment.clob}" mimeTypeName="${comment.mimeType.mimeTypeName}" />
                            </display:column>
                            <display:column titleKey="columnTitle.enteredBy">
                                <c:set var="entityInstance" scope="request" value="${comment.commentedByEntityInstance}" />
                                <jsp:include page="../../include/targetAsReviewLink.jsp" />
                            </display:column>
                            <c:if test="${comments.commentType.workflowEntrance != null}">
                                <display:column titleKey="columnTitle.status">
                                    <c:url var="statusUrl" value="/action/Core/EntityInstance/CommentStatus">
                                        <c:param name="EntityRef" value="${entityInstance.entityRef}" />
                                        <c:param name="CommentName" value="${comment.commentName}" />
                                    </c:url>
                                    <a href="${statusUrl}"><c:out value="${comment.commentStatus.workflowStep.description}" /></a>
                                </display:column>
                            </c:if>
                            <display:column>
                                <c:url var="editUrl" value="/action/Core/EntityInstance/CommentEdit">
                                    <c:param name="EntityRef" value="${entityInstance.entityRef}" />
                                    <c:param name="CommentName" value="${comment.commentName}" />
                                </c:url>
                                <a href="${editUrl}">Edit</a>
                                <c:url var="deleteUrl" value="/action/Core/EntityInstance/CommentDelete">
                                    <c:param name="EntityRef" value="${entityInstance.entityRef}" />
                                    <c:param name="CommentName" value="${comment.commentName}" />
                                </c:url>
                                <a href="${deleteUrl}">Delete</a>
                            </display:column>
                            <et:hasSecurityRole securityRole="Event.List">
                                <display:column>
                                    <c:url var="eventsUrl" value="/action/Core/Event/Main">
                                        <c:param name="EntityRef" value="${comment.entityInstance.entityRef}" />
                                    </c:url>
                                    <a href="${eventsUrl}">Events</a>
                                </display:column>
                            </et:hasSecurityRole>
                        </display:table>
                    </c:otherwise>
                </c:choose>
                <br />
            </c:forEach>

            <c:set var="tagScopes" scope="request" value="${entityInstance.tagScopes}" />
            <c:set var="entityAttributeGroups" scope="request" value="${entityInstance.entityAttributeGroups}" />
            <jsp:include page="../../include/tagScopes.jsp" />
            <jsp:include page="../../include/entityAttributeGroups.jsp" />
            <jsp:include page="../../include/entityInstance.jsp" />
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
