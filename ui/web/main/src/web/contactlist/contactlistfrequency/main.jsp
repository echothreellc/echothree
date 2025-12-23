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
        <title><fmt:message key="pageTitle.contactListFrequencies" /></title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/ContactList/Main" />"><fmt:message key="navigation.contactLists" /></a> &gt;&gt;
                <fmt:message key="navigation.contactListFrequencies" />
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="Event.List:ContactListFrequency.Create:ContactListFrequency.Edit:ContactListFrequency.Delete:ContactListFrequency.Review:ContactListFrequency.Description" />
            <et:hasSecurityRole securityRole="ContactListFrequency.Create">
                <p><a href="<c:url value="/action/ContactList/ContactListFrequency/Add" />">Add Contact List Frequency.</a></p>
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRole="ContactListFrequency.Review" var="includeReviewUrl" />
            <display:table name="contactListFrequencies" id="contactListFrequency" class="displaytag">
                <display:column titleKey="columnTitle.name">
                    <c:choose>
                        <c:when test="${includeReviewUrl}">
                            <c:url var="reviewUrl" value="/action/ContactList/ContactListFrequency/Review">
                                <c:param name="ContactListFrequencyName" value="${contactListFrequency.contactListFrequencyName}" />
                            </c:url>
                            <a href="${reviewUrl}"><c:out value="${contactListFrequency.contactListFrequencyName}" /></a>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${contactListFrequency.contactListFrequencyName}" />
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column titleKey="columnTitle.description">
                    <c:out value="${contactListFrequency.description}" />
                </display:column>
                <display:column property="sortOrder" titleKey="columnTitle.sortOrder" />
                <display:column titleKey="columnTitle.default">
                    <c:choose>
                        <c:when test="${contactListFrequency.isDefault}">
                            Default
                        </c:when>
                        <c:otherwise>
                            <et:hasSecurityRole securityRole="ContactListFrequency.Edit">
                                <c:url var="setDefaultUrl" value="/action/ContactList/ContactListFrequency/SetDefault">
                                    <c:param name="ContactListFrequencyName" value="${contactListFrequency.contactListFrequencyName}" />
                                </c:url>
                                <a href="${setDefaultUrl}">Set Default</a>
                            </et:hasSecurityRole>
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <et:hasSecurityRole securityRoles="ContactListFrequency.Edit:ContactListFrequency.Description:ContactListFrequency.Delete">
                    <display:column>
                        <et:hasSecurityRole securityRole="ContactListFrequency.Edit">
                            <c:url var="editUrl" value="/action/ContactList/ContactListFrequency/Edit">
                                <c:param name="OriginalContactListFrequencyName" value="${contactListFrequency.contactListFrequencyName}" />
                            </c:url>
                            <a href="${editUrl}">Edit</a>
                        </et:hasSecurityRole>
                        <et:hasSecurityRole securityRole="ContactListFrequency.Description">
                            <c:url var="descriptionsUrl" value="/action/ContactList/ContactListFrequency/Description">
                                <c:param name="ContactListFrequencyName" value="${contactListFrequency.contactListFrequencyName}" />
                            </c:url>
                            <a href="${descriptionsUrl}">Descriptions</a>
                        </et:hasSecurityRole>
                        <et:hasSecurityRole securityRole="ContactListFrequency.Delete">
                            <c:url var="deleteUrl" value="/action/ContactList/ContactListFrequency/Delete">
                                <c:param name="ContactListFrequencyName" value="${contactListFrequency.contactListFrequencyName}" />
                            </c:url>
                            <a href="${deleteUrl}">Delete</a>
                        </et:hasSecurityRole>
                    </display:column>
                </et:hasSecurityRole>
                <et:hasSecurityRole securityRole="Event.List">
                    <display:column>
                        <c:url var="eventsUrl" value="/action/Core/Event/Main">
                            <c:param name="EntityRef" value="${contactListFrequency.entityInstance.entityRef}" />
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
