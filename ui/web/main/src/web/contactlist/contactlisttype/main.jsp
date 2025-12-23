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
        <title><fmt:message key="pageTitle.contactListTypes" /></title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/ContactList/Main" />"><fmt:message key="navigation.contactLists" /></a> &gt;&gt;
                <fmt:message key="navigation.contactListTypes" />
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="Event.List:ContactListType.Create:ContactListType.Edit:ContactListType.Delete:ContactListType.Review:ContactListType.Description" />
            <et:hasSecurityRole securityRole="ContactListType.Create">
                <p><a href="<c:url value="/action/ContactList/ContactListType/Add" />">Add Contact List Type.</a></p>
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRole="ContactListType.Review" var="includeReviewUrl" />
            <display:table name="contactListTypes" id="contactListType" class="displaytag">
                <display:column titleKey="columnTitle.name">
                    <c:choose>
                        <c:when test="${includeReviewUrl}">
                            <c:url var="reviewUrl" value="/action/ContactList/ContactListType/Review">
                                <c:param name="ContactListTypeName" value="${contactListType.contactListTypeName}" />
                            </c:url>
                            <a href="${reviewUrl}"><c:out value="${contactListType.contactListTypeName}" /></a>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${contactListType.contactListTypeName}" />
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column titleKey="columnTitle.description">
                    <c:out value="${contactListType.description}" />
                </display:column>
                <display:column titleKey="columnTitle.usedForSolicitation">
                    <c:choose>
                        <c:when test="${contactListType.usedForSolicitation}">
                            <fmt:message key="phrase.yes" />
                        </c:when>
                        <c:otherwise>
                            <fmt:message key="phrase.no" />
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column property="sortOrder" titleKey="columnTitle.sortOrder" />
                <display:column titleKey="columnTitle.default">
                    <c:choose>
                        <c:when test="${contactListType.isDefault}">
                            Default
                        </c:when>
                        <c:otherwise>
                            <et:hasSecurityRole securityRole="ContactListType.Edit">
                                <c:url var="setDefaultUrl" value="/action/ContactList/ContactListType/SetDefault">
                                    <c:param name="ContactListTypeName" value="${contactListType.contactListTypeName}" />
                                </c:url>
                                <a href="${setDefaultUrl}">Set Default</a>
                            </et:hasSecurityRole>
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <et:hasSecurityRole securityRoles="ContactListType.Edit:ContactListType.Description:ContactListType.Delete">
                    <display:column>
                        <et:hasSecurityRole securityRole="ContactListType.Edit">
                            <c:url var="editUrl" value="/action/ContactList/ContactListType/Edit">
                                <c:param name="OriginalContactListTypeName" value="${contactListType.contactListTypeName}" />
                            </c:url>
                            <a href="${editUrl}">Edit</a>
                        </et:hasSecurityRole>
                        <et:hasSecurityRole securityRole="ContactListType.Description">
                            <c:url var="descriptionsUrl" value="/action/ContactList/ContactListType/Description">
                                <c:param name="ContactListTypeName" value="${contactListType.contactListTypeName}" />
                            </c:url>
                            <a href="${descriptionsUrl}">Descriptions</a>
                        </et:hasSecurityRole>
                        <et:hasSecurityRole securityRole="ContactListType.Delete">
                            <c:url var="deleteUrl" value="/action/ContactList/ContactListType/Delete">
                                <c:param name="ContactListTypeName" value="${contactListType.contactListTypeName}" />
                            </c:url>
                            <a href="${deleteUrl}">Delete</a>
                        </et:hasSecurityRole>
                    </display:column>
                </et:hasSecurityRole>
                <et:hasSecurityRole securityRole="Event.List">
                    <display:column>
                        <c:url var="eventsUrl" value="/action/Core/Event/Main">
                            <c:param name="EntityRef" value="${contactListType.entityInstance.entityRef}" />
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
