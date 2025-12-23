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
        <title><fmt:message key="pageTitle.vendorDocumentDescriptions" /></title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Purchasing/Main" />">Purchasing</a> &gt;&gt;
                <a href="<c:url value="/action/Purchasing/Vendor/Main" />">Vendors</a> &gt;&gt;
                <et:countVendorResults searchTypeName="VENDOR_REVIEW" countVar="vendorResultsCount" commandResultVar="countVendorResultsCommandResult" logErrors="false" />
                <c:if test="${vendorResultsCount > 0}">
                    <a href="<c:url value="/action/Purchasing/Vendor/Result" />"><fmt:message key="navigation.results" /></a> &gt;&gt;
                </c:if>
                <c:url var="reviewUrl" value="/action/Purchasing/Vendor/Review">
                    <c:param name="VendorName" value="${vendor.vendorName}" />
                </c:url>
                <a href="${reviewUrl}">Review (<c:out value="${vendor.vendorName}" />)</a> &gt;&gt;
                <c:url var="vendorDocumentsUrl" value="/action/Purchasing/VendorDocument/Main">
                    <c:param name="VendorName" value="${vendor.vendorName}" />
                </c:url>
                <a href="${vendorDocumentsUrl}"><fmt:message key="navigation.vendorDocuments" /></a> &gt;&gt;
                <fmt:message key="navigation.vendorDocumentDescriptions" />
            </h2>
        </div>
        <div id="Content">
            <c:url var="addUrl" value="/action/Purchasing/VendorDocument/DescriptionAdd">
                <c:param name="PartyName" value="${vendor.partyName}" />
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
                    <c:url var="editUrl" value="/action/Purchasing/VendorDocument/DescriptionEdit">
                        <c:param name="PartyName" value="${vendor.partyName}" />
                        <c:param name="DocumentName" value="${documentDescription.document.documentName}" />
                        <c:param name="LanguageIsoName" value="${documentDescription.language.languageIsoName}" />
                    </c:url>
                    <a href="${editUrl}">Edit</a>
                    <c:url var="deleteUrl" value="/action/Purchasing/VendorDocument/DescriptionDelete">
                        <c:param name="PartyName" value="${vendor.partyName}" />
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
