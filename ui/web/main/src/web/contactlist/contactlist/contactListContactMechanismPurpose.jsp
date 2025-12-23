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
        <title><fmt:message key="pageTitle.contactListContactMechanismPurposes" /></title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/ContactList/Main" />"><fmt:message key="navigation.contactLists" /></a> &gt;&gt;
                <a href="<c:url value="/action/ContactList/ContactList/Main" />"><fmt:message key="navigation.contactLists" /></a> &gt;&gt;
                <fmt:message key="navigation.contactListContactMechanismPurposes" />
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="ContactList.Review" />
            <et:hasSecurityRole securityRole="ContactList.Review" var="includeContactListReviewUrl" />
            <p>
                Contact List:
                <c:choose>
                    <c:when test="${includeContactListReviewUrl}">
                        <c:url var="contactListUrl" value="/action/ContactList/ContactList/Review">
                            <c:param name="ContactListName" value="${contactList.contactListName}" />
                        </c:url>
                        <a href="${contactListUrl}"><c:out value="${contactList.description}" /></a>
                    </c:when>
                    <c:otherwise>
                        <c:out value="${contactList.description}" />
                    </c:otherwise>
                </c:choose>
            </p>
            <c:url var="addUrl" value="/action/ContactList/ContactList/ContactListContactMechanismPurposeAdd">
                <c:param name="ContactListName" value="${contactList.contactListName}" />
            </c:url>
            <p><a href="${addUrl}">Add Contact Mechanism Purpose.</a></p>
            <display:table name="contactListContactMechanismPurposes" id="contactListContactMechanismPurpose" class="displaytag">
                <display:column titleKey="columnTitle.contactMechanismPurpose">
                    <c:out value="${contactListContactMechanismPurpose.contactMechanismPurpose.description}" />
                </display:column>
                <display:column property="sortOrder" titleKey="columnTitle.sortOrder" media="html" sortable="true" sortProperty="sortOrder" />
                <display:column titleKey="columnTitle.default" media="html" sortable="true" sortProperty="isDefault">
                    <c:choose>
                        <c:when test="${contactListContactMechanismPurpose.isDefault}">
                            Default
                        </c:when>
                        <c:otherwise>
                            <c:url var="setDefaultUrl" value="/action/ContactList/ContactList/ContactListContactMechanismPurposeSetDefault">
                                <c:param name="ContactListName" value="${contactListContactMechanismPurpose.contactList.contactListName}" />
                                <c:param name="ContactMechanismPurposeName" value="${contactListContactMechanismPurpose.contactMechanismPurpose.contactMechanismPurposeName}" />
                            </c:url>
                            <a href="${setDefaultUrl}">Set Default</a>
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column>
                    <c:url var="editUrl" value="/action/ContactList/ContactList/ContactListContactMechanismPurposeEdit">
                        <c:param name="ContactListName" value="${contactListContactMechanismPurpose.contactList.contactListName}" />
                        <c:param name="ContactMechanismPurposeName" value="${contactListContactMechanismPurpose.contactMechanismPurpose.contactMechanismPurposeName}" />
                    </c:url>
                    <a href="${editUrl}">Edit</a>
                    <c:url var="deleteUrl" value="/action/ContactList/ContactList/ContactListContactMechanismPurposeDelete">
                        <c:param name="ContactListName" value="${contactListContactMechanismPurpose.contactList.contactListName}" />
                        <c:param name="ContactMechanismPurposeName" value="${contactListContactMechanismPurpose.contactMechanismPurpose.contactMechanismPurposeName}" />
                    </c:url>
                    <a href="${deleteUrl}">Delete</a>
                </display:column>
           </display:table>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
