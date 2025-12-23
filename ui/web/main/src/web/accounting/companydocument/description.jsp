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
        <title><fmt:message key="pageTitle.companyDocumentDescriptions" /></title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Accounting/Main" />"><fmt:message key="navigation.accounting" /></a> &gt;&gt;
                <a href="<c:url value="/action/Accounting/Company/Main" />"><fmt:message key="navigation.companies" /></a> &gt;&gt;
                <c:url var="reviewUrl" value="/action/Accounting/Company/Review">
                    <c:param name="CompanyName" value="${company.companyName}" />
                </c:url>
                <a href="${reviewUrl}">Review (<c:out value="${company.companyName}" />)</a> &gt;&gt;
                <c:url var="companyDocumentsUrl" value="/action/Accounting/CompanyDocument/Main">
                    <c:param name="CompanyName" value="${company.companyName}" />
                </c:url>
                <a href="${companyDocumentsUrl}"><fmt:message key="navigation.companyDocuments" /></a> &gt;&gt;
                <fmt:message key="navigation.companyDocumentDescriptions" />
            </h2>
        </div>
        <div id="Content">
            <c:url var="addUrl" value="/action/Accounting/CompanyDocument/DescriptionAdd">
                <c:param name="PartyName" value="${company.partyName}" />
                <c:param name="DocumentName" value="${document.documentName}" />
            </c:url>
            <p><a href="${addUrl}">Add Description.</a></p>
            <display:table name="documentDescriptions" id="documentDescription" class="displaytag">
                <display:column titleKey="columnTitle.language">
                    <c:out value="${documentDescription.language.description}" />
                </display:column>
                <display:column titleKey="columnTitle.description">
                    <c:out value="${documentDescription.description}" />
                </display:column>
                <display:column>
                    <c:url var="editUrl" value="/action/Accounting/CompanyDocument/DescriptionEdit">
                        <c:param name="PartyName" value="${company.partyName}" />
                        <c:param name="DocumentName" value="${documentDescription.document.documentName}" />
                        <c:param name="LanguageIsoName" value="${documentDescription.language.languageIsoName}" />
                    </c:url>
                    <a href="${editUrl}">Edit</a>
                    <c:url var="deleteUrl" value="/action/Accounting/CompanyDocument/DescriptionDelete">
                        <c:param name="PartyName" value="${company.partyName}" />
                        <c:param name="DocumentName" value="${documentDescription.document.documentName}" />
                        <c:param name="LanguageIsoName" value="${documentDescription.language.languageIsoName}" />
                    </c:url>
                    <a href="${deleteUrl}">Delete</a>
                </display:column>
           </display:table>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
