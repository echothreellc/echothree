<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>

<!--                                                                                  -->
<!-- Copyright 2002-2025 Echo Three, LLC                                              -->
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
        <title><fmt:message key="pageTitle.customerDocumentDescriptions" /></title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Customer/Main" />">Customers</a> &gt;&gt;
                <a href="<c:url value="/action/Customer/Customer/Main" />">Search</a> &gt;&gt;
                <et:countCustomerResults searchTypeName="ORDER_ENTRY" countVar="customerResultsCount" commandResultVar="countCustomerResultsCommandResult" logErrors="false" />
                <c:if test="${customerResultsCount > 0}">
                    <a href="<c:url value="/action/Customer/Customer/Result" />"><fmt:message key="navigation.results" /></a> &gt;&gt;
                </c:if>
                <c:url var="reviewUrl" value="/action/Customer/Customer/Review">
                    <c:param name="CustomerName" value="${customer.customerName}" />
                </c:url>
                <a href="${reviewUrl}">Review (<c:out value="${customer.customerName}" />)</a> &gt;&gt;
                <c:url var="customerDocumentsUrl" value="/action/Customer/CustomerDocument/Main">
                    <c:param name="CustomerName" value="${customer.customerName}" />
                </c:url>
                <a href="${customerDocumentsUrl}"><fmt:message key="navigation.customerDocuments" /></a> &gt;&gt;
                <fmt:message key="navigation.customerDocumentDescriptions" />
            </h2>
        </div>
        <div id="Content">
            <c:url var="addUrl" value="/action/Customer/CustomerDocument/DescriptionAdd">
                <c:param name="PartyName" value="${customer.partyName}" />
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
                    <c:url var="editUrl" value="/action/Customer/CustomerDocument/DescriptionEdit">
                        <c:param name="PartyName" value="${customer.partyName}" />
                        <c:param name="DocumentName" value="${documentDescription.document.documentName}" />
                        <c:param name="LanguageIsoName" value="${documentDescription.language.languageIsoName}" />
                    </c:url>
                    <a href="${editUrl}">Edit</a>
                    <c:url var="deleteUrl" value="/action/Customer/CustomerDocument/DescriptionDelete">
                        <c:param name="PartyName" value="${customer.partyName}" />
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
