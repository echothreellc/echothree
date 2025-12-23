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
            <fmt:message key="pageTitle.contactList">
                <fmt:param value="${contactList.contactListName}" />
            </fmt:message>
        </title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/ContactList/Main" />"><fmt:message key="navigation.contactLists" /></a> &gt;&gt;
                <a href="<c:url value="/action/ContactList/ContactList/Main" />"><fmt:message key="navigation.contactLists" /></a> &gt;&gt;
                <fmt:message key="navigation.contactList">
                    <fmt:param value="${contactList.contactListName}" />
                </fmt:message>
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="ContactListGroup.Review:ContactListType.Review:ContactListFrequency.Review:WorkflowEntrance.Review" />
            <et:hasSecurityRole securityRole="ContactListGroup.Review" var="includeContactListGroupReviewUrl" />
            <et:hasSecurityRole securityRole="ContactListType.Review" var="includeContactListTypeReviewUrl" />
            <et:hasSecurityRole securityRole="ContactListFrequency.Review" var="includeContactListFrequencyReviewUrl" />
            <et:hasSecurityRole securityRole="WorkflowEntrance.Review" var="includeWorkflowEntranceReviewUrl" />
            <c:choose>
                <c:when test="${contactList.contactListName != contactList.description}">
                    <p><font size="+2"><b><c:out value="${contactList.description}" /></b></font></p>
                    <p><font size="+1"><c:out value="${contactList.contactListName}" /></font></p>
                </c:when>
                <c:otherwise>
                    <p><font size="+2"><b><c:out value="${contactList.contactListName}" /></b></font></p>
                </c:otherwise>
            </c:choose>
            <br />
            Contact List Name: ${contactList.contactListName}<br />
            <br />
            <fmt:message key="label.contactListGroup" />:
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
            <br />
            <fmt:message key="label.contactListType" />:
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
            <br />
            <fmt:message key="label.contactListFrequency" />:
            <c:choose>
                <c:when test='${contactList.contactListFrequency == null}'>
                    <i><fmt:message key="phrase.notSet" /></i>
                </c:when>
                <c:otherwise>
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
                </c:otherwise>
            </c:choose>
            <br />
            <fmt:message key="label.defaultPartyContactListStatus" />:
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
            <br />
            <br />
            <br />
            <br />
            <br />
            <c:set var="entityInstance" scope="request" value="${contactList.entityInstance}" />
            <jsp:include page="../../include/entityInstance.jsp" />
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
