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
        <title><fmt:message key="pageTitle.customerTypeContactListGroups" /></title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/ContactList/Main" />"><fmt:message key="navigation.contactLists" /></a> &gt;&gt;
                <a href="<c:url value="/action/ContactList/ContactListGroup/Main" />"><fmt:message key="navigation.contactListGroups" /></a> &gt;&gt;
                <fmt:message key="navigation.customerTypeContactListGroups" />
            </h2>
        </div>
        <div id="Content">
            <c:url var="addUrl" value="/action/ContactList/ContactListGroup/CustomerTypeContactListGroupAdd">
                <c:param name="ContactListGroupName" value="${contactListGroup.contactListGroupName}" />
            </c:url>
            <p><a href="${addUrl}">Add Customer Type.</a></p>
            <display:table name="customerTypeContactListGroups" id="customerTypeContactListGroup" class="displaytag">
                <display:column titleKey="columnTitle.customerType">
                    <c:out value="${customerTypeContactListGroup.customerType.description}" />
                </display:column>
                <display:column titleKey="columnTitle.addWhenCreated">
                    <c:choose>
                        <c:when test="${customerTypeContactListGroup.addWhenCreated}">
                            <fmt:message key="phrase.yes" />
                        </c:when>
                        <c:otherwise>
                            <fmt:message key="phrase.no" />
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column>
                    <c:url var="editUrl" value="/action/ContactList/ContactListGroup/CustomerTypeContactListGroupEdit">
                        <c:param name="ContactListGroupName" value="${customerTypeContactListGroup.contactListGroup.contactListGroupName}" />
                        <c:param name="CustomerTypeName" value="${customerTypeContactListGroup.customerType.customerTypeName}" />
                    </c:url>
                    <a href="${editUrl}">Edit</a>
                    <c:url var="deleteUrl" value="/action/ContactList/ContactListGroup/CustomerTypeContactListGroupDelete">
                        <c:param name="ContactListGroupName" value="${customerTypeContactListGroup.contactListGroup.contactListGroupName}" />
                        <c:param name="CustomerTypeName" value="${customerTypeContactListGroup.customerType.customerTypeName}" />
                    </c:url>
                    <a href="${deleteUrl}">Delete</a>
                </display:column>
           </display:table>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
