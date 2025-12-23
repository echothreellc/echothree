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
        <title>Review (<c:out value="${shippingMethod.shippingMethodName}" />)</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Shipping/Main" />">Shipping</a> &gt;&gt;
                <a href="<c:url value="/action/Shipping/ShippingMethod/Main" />">Shipping Methods</a> &gt;&gt;
                Review (<c:out value="${shippingMethod.shippingMethodName}" />)
            </h2>
        </div>
        <div id="Content">
            <p><font size="+2"><b><c:out value="${shippingMethod.description}" /></b></font></p>
            <br />
            Shipping Method Name: ${shippingMethod.shippingMethodName}<br />
            <br />
            <br />
            <c:set var="comments" scope="request" value="${shippingMethod.comments.map['SHIPPING_METHOD']}" />
            <h2><c:out value="${comments.commentType.description}" /> Comments</h2>
            <c:url var="addUrl" value="/action/Shipping/ShippingMethod/CommentAdd">
                <c:param name="ShippingMethodName" value="${shippingMethod.shippingMethodName}" />
            </c:url>
            <p><a href="${addUrl}">Add <c:out value="${comments.commentType.description}" /> Comment.</a></p>
            <c:choose>
                <c:when test='${comments.size == 0}'>
                    <br />
                </c:when>
                <c:otherwise>
                    <display:table name="comments.list" id="comment" class="displaytag">
                        <display:column titleKey="columnTitle.dateTime">
                            <c:out value="${comment.entityInstance.entityTime.createdTime}" />
                        </display:column>
                        <display:column titleKey="columnTitle.comment">
                            <c:if test='${comment.description != null}'>
                                <b><c:out value="${comment.description}" /></b><br />
                            </c:if>
                            <et:out value="${comment.clob}" mimeTypeName="${comment.mimeType.mimeTypeName}" />
                        </display:column>
                        <display:column property="commentedByEntityInstance.description" titleKey="columnTitle.enteredBy" />
                        <display:column>
                            <c:url var="editUrl" value="/action/Shipping/ShippingMethod/CommentEdit">
                                <c:param name="ShippingMethodName" value="${shippingMethod.shippingMethodName}" />
                                <c:param name="CommentName" value="${comment.commentName}" />
                            </c:url>
                            <a href="${editUrl}">Edit</a>
                            <c:url var="deleteUrl" value="/action/Shipping/ShippingMethod/CommentDelete">
                                <c:param name="ShippingMethodName" value="${shippingMethod.shippingMethodName}" />
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
            <br />
            <br />
            <c:set var="entityAttributeGroups" scope="request" value="${shippingMethod.entityAttributeGroups}" />
            <c:set var="entityInstance" scope="request" value="${shippingMethod.entityInstance}" />
            <c:url var="returnUrl" scope="request" value="/../action/Shipping/ShippingMethod/Review">
                <c:param name="ShippingMethodName" value="${shippingMethod.shippingMethodName}" />
            </c:url>
            <jsp:include page="../../include/entityAttributeGroups.jsp" />
            <jsp:include page="../../include/entityInstance.jsp" />
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
