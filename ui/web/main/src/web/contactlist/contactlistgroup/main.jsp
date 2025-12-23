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
        <title><fmt:message key="pageTitle.contactListGroups" /></title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/ContactList/Main" />"><fmt:message key="navigation.contactLists" /></a> &gt;&gt;
                <fmt:message key="navigation.contactListGroups" />
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="Event.List:ContactListGroup.Create:ContactListGroup.Edit:ContactListGroup.Delete:ContactListGroup.Review:ContactListGroup.Description:ContactListGroup.PartyTypeContactListGroup:ContactListGroup.CustomerTypeContactListGroup" />
            <et:hasSecurityRole securityRoles="ContactListGroup.PartyTypeContactListGroup:ContactListGroup.CustomerTypeContactListGroup">
                <c:set var="linksInFirstRow" value="true" />
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRoles="ContactListGroup.Edit:ContactListGroup.Description:ContactListGroup.Delete">
                <c:set var="linksInSecondRow" value="true" />
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRole="ContactListGroup.Create">
                <p><a href="<c:url value="/action/ContactList/ContactListGroup/Add" />">Add Contact List Group.</a></p>
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRole="ContactListGroup.Review" var="includeReviewUrl" />
            <display:table name="contactListGroups" id="contactListGroup" class="displaytag">
                <display:column titleKey="columnTitle.name">
                    <c:choose>
                        <c:when test="${includeReviewUrl}">
                            <c:url var="reviewUrl" value="/action/ContactList/ContactListGroup/Review">
                                <c:param name="ContactListGroupName" value="${contactListGroup.contactListGroupName}" />
                            </c:url>
                            <a href="${reviewUrl}"><c:out value="${contactListGroup.contactListGroupName}" /></a>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${contactListGroup.contactListGroupName}" />
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column titleKey="columnTitle.description">
                    <c:out value="${contactListGroup.description}" />
                </display:column>
                <display:column property="sortOrder" titleKey="columnTitle.sortOrder" />
                <display:column titleKey="columnTitle.default">
                    <c:choose>
                        <c:when test="${contactListGroup.isDefault}">
                            Default
                        </c:when>
                        <c:otherwise>
                            <et:hasSecurityRole securityRole="ContactListGroup.Edit">
                                <c:url var="setDefaultUrl" value="/action/ContactList/ContactListGroup/SetDefault">
                                    <c:param name="ContactListGroupName" value="${contactListGroup.contactListGroupName}" />
                                </c:url>
                                <a href="${setDefaultUrl}">Set Default</a>
                            </et:hasSecurityRole>
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <c:if test="${linksInFirstRow || linksInSecondRow}">
                    <display:column>
                        <et:hasSecurityRole securityRole="ContactListGroup.PartyTypeContactListGroup">
                            <c:url var="partyTypeContactListGroupsUrl" value="/action/ContactList/ContactListGroup/PartyTypeContactListGroup">
                                <c:param name="ContactListGroupName" value="${contactListGroup.contactListGroupName}" />
                            </c:url>
                            <a href="${partyTypeContactListGroupsUrl}">Party Types</a>
                        </et:hasSecurityRole>
                        <et:hasSecurityRole securityRole="ContactListGroup.CustomerTypeContactListGroup">
                            <c:url var="customerTypeContactListGroupsUrl" value="/action/ContactList/ContactListGroup/CustomerTypeContactListGroup">
                                <c:param name="ContactListGroupName" value="${contactListGroup.contactListGroupName}" />
                            </c:url>
                            <a href="${customerTypeContactListGroupsUrl}">Customer Types</a>
                        </et:hasSecurityRole>
                        <c:if test="${linksInFirstRow && linksInSecondRow}">
                            <br />
                        </c:if>
                        <et:hasSecurityRole securityRole="ContactListGroup.Edit">
                            <c:url var="editUrl" value="/action/ContactList/ContactListGroup/Edit">
                                <c:param name="OriginalContactListGroupName" value="${contactListGroup.contactListGroupName}" />
                            </c:url>
                            <a href="${editUrl}">Edit</a>
                        </et:hasSecurityRole>
                        <et:hasSecurityRole securityRole="ContactListGroup.Description">
                            <c:url var="descriptionsUrl" value="/action/ContactList/ContactListGroup/Description">
                                <c:param name="ContactListGroupName" value="${contactListGroup.contactListGroupName}" />
                            </c:url>
                            <a href="${descriptionsUrl}">Descriptions</a>
                        </et:hasSecurityRole>
                        <et:hasSecurityRole securityRole="ContactListGroup.Delete">
                            <c:url var="deleteUrl" value="/action/ContactList/ContactListGroup/Delete">
                                <c:param name="ContactListGroupName" value="${contactListGroup.contactListGroupName}" />
                            </c:url>
                            <a href="${deleteUrl}">Delete</a>
                        </et:hasSecurityRole>
                    </display:column>
                </c:if>
                <et:hasSecurityRole securityRole="Event.List">
                    <display:column>
                        <c:url var="eventsUrl" value="/action/Core/Event/Main">
                            <c:param name="EntityRef" value="${contactListGroup.entityInstance.entityRef}" />
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
