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
        <title><fmt:message key="pageTitle.contactLists" /></title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/ContactList/Main" />"><fmt:message key="navigation.contactLists" /></a> &gt;&gt;
                <fmt:message key="navigation.contactLists" />
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="Event.List:ContactList.Create:ContactList.Edit:ContactList.Delete:ContactList.Review:ContactList.Description:ContactListGroup.Review:ContactListType.Review:ContactListFrequency.Review:WorkflowEntrance.Review:ContactList.PartyTypeContactList:ContactList.CustomerTypeContactList:ContactList.ContactListContactMechanismPurpose" />
            <et:hasSecurityRole securityRoles="ContactList.PartyTypeContactList:ContactList.CustomerTypeContactList">
                <c:set var="linksInFirstRow" value="true" />
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRoles="ContactList.ContactListContactMechanismPurpose">
                <c:set var="linksInSecondRow" value="true" />
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRoles="ContactList.Edit:ContactList.Description:ContactList.Delete">
                <c:set var="linksInThirdRow" value="true" />
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRole="ContactList.Create">
                <p><a href="<c:url value="/action/ContactList/ContactList/Add" />">Add Contact List.</a></p>
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRole="ContactList.Review" var="includeReviewUrl" />
            <et:hasSecurityRole securityRole="ContactListGroup.Review" var="includeContactListGroupReviewUrl" />
            <et:hasSecurityRole securityRole="ContactListType.Review" var="includeContactListTypeReviewUrl" />
            <et:hasSecurityRole securityRole="ContactListFrequency.Review" var="includeContactListFrequencyReviewUrl" />
            <et:hasSecurityRole securityRole="WorkflowEntrance.Review" var="includeWorkflowEntranceReviewUrl" />
            <display:table name="contactLists" id="contactList" class="displaytag" export="true" sort="list" requestURI="/action/ContactList/ContactList/Main">
                <display:setProperty name="export.csv.filename" value="ContactLists.csv" />
                <display:setProperty name="export.excel.filename" value="ContactLists.xls" />
                <display:setProperty name="export.pdf.filename" value="ContactLists.pdf" />
                <display:setProperty name="export.rtf.filename" value="ContactLists.rtf" />
                <display:setProperty name="export.xml.filename" value="ContactLists.xml" />
                <display:column titleKey="columnTitle.name" media="html" sortable="true" sortProperty="contactListName">
                    <c:choose>
                        <c:when test="${includeReviewUrl}">
                            <c:url var="reviewUrl" value="/action/ContactList/ContactList/Review">
                                <c:param name="ContactListName" value="${contactList.contactListName}" />
                            </c:url>
                            <a href="${reviewUrl}"><c:out value="${contactList.contactListName}" /></a>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${contactList.contactListName}" />
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column titleKey="columnTitle.description" media="html" sortable="true" sortProperty="description">
                    <c:out value="${contactList.description}" />
                </display:column>
                <display:column titleKey="columnTitle.contactListGroup" media="html" sortable="true" sortProperty="contactListGroup.contactListGroupName">
                    <c:choose>
                        <c:when test="${includeContactListGroupReviewUrl}">
                            <c:url var="reviewUrl" value="/action/ContactList/ContactListGroup/Review">
                                <c:param name="ContactListGroupName" value="${contactList.contactListGroup.contactListGroupName}" />
                            </c:url>
                            <a href="${reviewUrl}"><c:out value="${contactList.contactListGroup.description}" /></a>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${contactList.contactListGroup.description}" />
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column titleKey="columnTitle.contactListType" media="html" sortable="true" sortProperty="contactListType.contactListTypeName">
                    <c:choose>
                        <c:when test="${includeContactListTypeReviewUrl}">
                            <c:url var="reviewUrl" value="/action/ContactList/ContactListType/Review">
                                <c:param name="ContactListTypeName" value="${contactList.contactListType.contactListTypeName}" />
                            </c:url>
                            <a href="${reviewUrl}"><c:out value="${contactList.contactListType.description}" /></a>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${contactList.contactListType.description}" />
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column titleKey="columnTitle.contactListFrequency" media="html" sortable="true" sortProperty="contactListFrequency.contactListFrequencyName">
                    <c:if test="${contactList.contactListFrequency != null}">
                        <c:choose>
                            <c:when test="${includeContactListFrequencyReviewUrl}">
                                <c:url var="reviewUrl" value="/action/ContactList/ContactListFrequency/Review">
                                    <c:param name="ContactListFrequencyName" value="${contactList.contactListFrequency.contactListFrequencyName}" />
                                </c:url>
                                <a href="${reviewUrl}"><c:out value="${contactList.contactListFrequency.description}" /></a>
                            </c:when>
                            <c:otherwise>
                                <c:out value="${contactList.contactListFrequency.description}" />
                            </c:otherwise>
                        </c:choose>
                    </c:if>
                </display:column>
                <display:column titleKey="columnTitle.defaultPartyContactListStatus" media="html" sortable="true" sortProperty="defaultPartyContactListStatus.workflowEntranceName">
                    <c:choose>
                        <c:when test="${includeWorkflowEntranceReviewUrl}">
                            <c:url var="reviewUrl" value="/action/Configuration/WorkflowEntrance/Review">
                                <c:param name="WorkflowName" value="${contactList.defaultPartyContactListStatus.workflow.workflowName}" />
                                <c:param name="WorkflowEntranceName" value="${contactList.defaultPartyContactListStatus.workflowEntranceName}" />
                            </c:url>
                            <a href="${reviewUrl}"><c:out value="${contactList.defaultPartyContactListStatus.description}" /></a>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${contactList.defaultPartyContactListStatus.description}" />
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column property="sortOrder" titleKey="columnTitle.sortOrder" media="html" sortable="true" sortProperty="sortOrder" />
                <display:column titleKey="columnTitle.default" media="html" sortable="true" sortProperty="isDefault">
                    <c:choose>
                        <c:when test="${contactList.isDefault}">
                            Default
                        </c:when>
                        <c:otherwise>
                            <et:hasSecurityRole securityRole="ContactList.Edit">
                                <c:url var="setDefaultUrl" value="/action/ContactList/ContactList/SetDefault">
                                    <c:param name="ContactListName" value="${contactList.contactListName}" />
                                </c:url>
                                <a href="${setDefaultUrl}">Set Default</a>
                            </et:hasSecurityRole>
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <c:if test="${linksInFirstRow || linksInSecondRow || linksInThirdRow}">
                    <display:column media="html">
                        <et:hasSecurityRole securityRole="ContactList.PartyTypeContactList">
                            <c:url var="partyTypeContactListsUrl" value="/action/ContactList/ContactList/PartyTypeContactList">
                                <c:param name="ContactListName" value="${contactList.contactListName}" />
                            </c:url>
                            <a href="${partyTypeContactListsUrl}">Party Types</a>
                        </et:hasSecurityRole>
                        <et:hasSecurityRole securityRole="ContactList.CustomerTypeContactList">
                            <c:url var="customerTypeContactListsUrl" value="/action/ContactList/ContactList/CustomerTypeContactList">
                                <c:param name="ContactListName" value="${contactList.contactListName}" />
                            </c:url>
                            <a href="${customerTypeContactListsUrl}">Customer Types</a>
                        </et:hasSecurityRole>
                        <c:if test="${linksInFirstRow && (linksInSecondRow || linksInThirdRow)}">
                            <br />
                        </c:if>
                        <et:hasSecurityRole securityRole="ContactList.ContactListContactMechanismPurpose">
                            <c:url var="contactListContactMechanismPurposesUrl" value="/action/ContactList/ContactList/ContactListContactMechanismPurpose">
                                <c:param name="ContactListName" value="${contactList.contactListName}" />
                            </c:url>
                            <a href="${contactListContactMechanismPurposesUrl}">Contact Mechanism Purposes</a>
                        </et:hasSecurityRole>
                        <c:if test="${(linksInFirstRow || linksInSecondRow) && linksInThirdRow}">
                            <br />
                        </c:if>
                        <et:hasSecurityRole securityRole="ContactList.Edit">
                            <c:url var="editUrl" value="/action/ContactList/ContactList/Edit">
                                <c:param name="OriginalContactListName" value="${contactList.contactListName}" />
                            </c:url>
                            <a href="${editUrl}">Edit</a>
                        </et:hasSecurityRole>
                        <et:hasSecurityRole securityRole="ContactList.Description">
                            <c:url var="descriptionsUrl" value="/action/ContactList/ContactList/Description">
                                <c:param name="ContactListName" value="${contactList.contactListName}" />
                            </c:url>
                            <a href="${descriptionsUrl}">Descriptions</a>
                        </et:hasSecurityRole>
                        <et:hasSecurityRole securityRole="ContactList.Delete">
                            <c:url var="deleteUrl" value="/action/ContactList/ContactList/Delete">
                                <c:param name="ContactListName" value="${contactList.contactListName}" />
                            </c:url>
                            <a href="${deleteUrl}">Delete</a>
                        </et:hasSecurityRole>
                    </display:column>
                </c:if>
                <et:hasSecurityRole securityRole="Event.List">
                    <display:column media="html">
                        <c:url var="eventsUrl" value="/action/Core/Event/Main">
                            <c:param name="EntityRef" value="${contactList.entityInstance.entityRef}" />
                        </c:url>
                        <a href="${eventsUrl}">Events</a>
                    </display:column>
                </et:hasSecurityRole>
                <display:column property="contactListName" titleKey="columnTitle.name" media="csv excel pdf rtf xml" />
                <display:column property="description" titleKey="columnTitle.description" media="csv excel pdf rtf xml" />
                <display:column property="contactListGroup.contactListGroupName" titleKey="columnTitle.contactListGroupName" media="csv excel pdf rtf xml" />
                <display:column property="contactListGroup.description" titleKey="columnTitle.contactListGroup" media="csv excel pdf rtf xml" />
                <display:column property="contactListType.contactListTypeName" titleKey="columnTitle.contactListTypeName" media="csv excel pdf rtf xml" />
                <display:column property="contactListType.description" titleKey="columnTitle.contactListType" media="csv excel pdf rtf xml" />
                <display:column property="contactListFrequency.contactListFrequencyName" titleKey="columnTitle.contactListFrequencyName" media="csv excel pdf rtf xml" />
                <display:column property="contactListFrequency.description" titleKey="columnTitle.contactListFrequency" media="csv excel pdf rtf xml" />
                <display:column property="defaultPartyContactListStatus.workflowEntranceName" titleKey="columnTitle.defaultPartyContactListStatusName" media="csv excel pdf rtf xml" />
                <display:column property="defaultPartyContactListStatus.description" titleKey="columnTitle.defaultPartyContactListStatus" media="csv excel pdf rtf xml" />
                <display:column property="sortOrder" titleKey="columnTitle.sortOrder" media="csv excel pdf rtf xml" />
                <display:column property="isDefault" titleKey="columnTitle.default" media="csv excel pdf rtf xml" />
            </display:table>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
