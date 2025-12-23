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
            <fmt:message key="pageTitle.warehouseContactMechanism">
                <fmt:param value="${partyContactMechanism.contactMechanism.contactMechanismName}" />
            </fmt:message>
        </title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Warehouse/Main" />"><fmt:message key="navigation.warehouses" /></a> &gt;&gt;
                <a href="<c:url value="/action/Warehouse/Warehouse/Main" />"><fmt:message key="navigation.warehouses" /></a> &gt;&gt;
                <et:countWarehouseResults searchTypeName="EMPLOYEE" countVar="warehouseResultsCount" commandResultVar="countWarehouseResultsCommandResult" logErrors="false" />
                <c:if test="${warehouseResultsCount > 0}">
                    <a href="<c:url value="/action/Warehouse/Warehouse/Result" />"><fmt:message key="navigation.results" /></a> &gt;&gt;
                </c:if>
                <c:url var="reviewUrl" value="/action/Warehouse/Warehouse/Review">
                    <c:param name="WarehouseName" value="${warehouse.warehouseName}" />
                </c:url>
                <a href="${reviewUrl}">Review (<c:out value="${warehouse.warehouseName}" />)</a> &gt;&gt;
                <c:url var="warehouseContactMechanismsUrl" value="/action/Warehouse/WarehouseContactMechanism/Main">
                    <c:param name="WarehouseName" value="${warehouse.warehouseName}" />
                </c:url>
                <a href="${warehouseContactMechanismsUrl}"><fmt:message key="navigation.warehouseContactMechanisms" /></a> &gt;&gt;
                Warehouse Contact Mechanism (<c:out value="${partyContactMechanism.contactMechanism.contactMechanismName}" />)
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="Event.List" />
            <p><font size="+2"><b>
                <c:choose>
                    <c:when test="${partyContactMechanism.description == null}">
                        ${partyContactMechanism.contactMechanism.contactMechanismName}
                    </c:when>
                    <c:otherwise>
                        <c:out value="${partyContactMechanism.description}" />
                    </c:otherwise>
                </c:choose>
            </b></font></p>
            <br />
            Contact Mechanism Name: ${partyContactMechanism.contactMechanism.contactMechanismName}<br />
            <br />
            <br />
            <c:set var="commonUrl" scope="request" value="Warehouse/WarehouseContactMechanism" />
            <jsp:include page="../../include/partyContactMechanism.jsp" />
            <br />
            <br />
            <c:set var="comments" scope="request" value="${partyContactMechanism.contactMechanism.comments.map['CONTACT_MECHANISM']}" />
            <h2><c:out value="${comments.commentType.description}" /> Comments</h2>
            <c:url var="addUrl" value="/action/Warehouse/WarehouseContactMechanism/CommentAdd">
                <c:param name="PartyName" value="${partyContactMechanism.party.partyName}" />
                <c:param name="ContactMechanismName" value="${partyContactMechanism.contactMechanism.contactMechanismName}" />
            </c:url>
            <p><a href="${addUrl}">Add Comment.</a></p>
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
                            <c:url var="editUrl" value="/action/Warehouse/WarehouseContactMechanism/CommentEdit">
                                <c:param name="PartyName" value="${partyContactMechanism.party.partyName}" />
                                <c:param name="ContactMechanismName" value="${partyContactMechanism.contactMechanism.contactMechanismName}" />
                                <c:param name="CommentName" value="${comment.commentName}" />
                            </c:url>
                            <a href="${editUrl}">Edit</a>
                            <c:url var="deleteUrl" value="/action/Warehouse/WarehouseContactMechanism/CommentDelete">
                                <c:param name="PartyName" value="${partyContactMechanism.party.partyName}" />
                                <c:param name="ContactMechanismName" value="${partyContactMechanism.contactMechanism.contactMechanismName}" />
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
            <c:set var="tagScopes" scope="request" value="${partyContactMechanism.contactMechanism.tagScopes}" />
            <c:set var="entityAttributeGroups" scope="request" value="${partyContactMechanism.contactMechanism.entityAttributeGroups}" />
            <c:set var="entityInstance" scope="request" value="${partyContactMechanism.contactMechanism.entityInstance}" />
            <c:url var="returnUrl" scope="request" value="/../action/Warehouse/WarehouseContactMechanism/Review">
                <c:param name="PartyName" value="${partyContactMechanism.party.partyName}" />
                <c:param name="ContactMechanismName" value="${partyContactMechanism.contactMechanism.contactMechanismName}" />
            </c:url>
            <jsp:include page="../../include/tagScopes.jsp" />
            <jsp:include page="../../include/entityAttributeGroups.jsp" />
            <jsp:include page="../../include/entityInstance.jsp" />
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
