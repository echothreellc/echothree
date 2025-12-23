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
        <title>Entity Role Types</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Accounting/Main" />"><fmt:message key="navigation.accounting" /></a> &gt;&gt;
                <a href="<c:url value="/action/Accounting/TransactionType/Main" />">Transaction Types</a> &gt;&gt;
                <c:url var="transactionEntityRoleTypesUrl" value="/action/Accounting/TransactionEntityRoleType/Main">
                    <c:param name="TransactionTypeName" value="${transactionEntityRoleType.transactionType.transactionTypeName}" />
                </c:url>
                <a href="${transactionEntityRoleTypesUrl}">Entity Role Types</a> &gt;&gt;
                Descriptions
            </h2>
        </div>
        <div id="Content">
            <c:url var="addUrl" value="/action/Accounting/TransactionEntityRoleType/DescriptionAdd">
                <c:param name="TransactionTypeName" value="${transactionEntityRoleType.transactionType.transactionTypeName}" />
                <c:param name="TransactionEntityRoleTypeName" value="${transactionEntityRoleType.transactionEntityRoleTypeName}" />
            </c:url>
            <p><a href="${addUrl}">Add Description.</a></p>
            <display:table name="transactionEntityRoleTypeDescriptions" id="transactionEntityRoleTypeDescription" class="displaytag">
                <display:column titleKey="columnTitle.language">
                    <c:out value="${transactionEntityRoleTypeDescription.language.description}" />
                </display:column>
                <display:column titleKey="columnTitle.description">
                    <c:out value="${transactionEntityRoleTypeDescription.description}" />
                </display:column>
                <display:column>
                    <c:url var="editUrl" value="/action/Accounting/TransactionEntityRoleType/DescriptionEdit">
                        <c:param name="TransactionTypeName" value="${transactionEntityRoleTypeDescription.transactionEntityRoleType.transactionType.transactionTypeName}" />
                        <c:param name="TransactionEntityRoleTypeName" value="${transactionEntityRoleTypeDescription.transactionEntityRoleType.transactionEntityRoleTypeName}" />
                        <c:param name="LanguageIsoName" value="${transactionEntityRoleTypeDescription.language.languageIsoName}" />
                    </c:url>
                    <a href="${editUrl}">Edit</a>
                    <c:url var="deleteUrl" value="/action/Accounting/TransactionEntityRoleType/DescriptionDelete">
                        <c:param name="TransactionTypeName" value="${transactionEntityRoleTypeDescription.transactionEntityRoleType.transactionType.transactionTypeName}" />
                        <c:param name="TransactionEntityRoleTypeName" value="${transactionEntityRoleTypeDescription.transactionEntityRoleType.transactionEntityRoleTypeName}" />
                        <c:param name="LanguageIsoName" value="${transactionEntityRoleTypeDescription.language.languageIsoName}" />
                    </c:url>
                    <a href="${deleteUrl}">Delete</a>
                </display:column>
           </display:table>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
