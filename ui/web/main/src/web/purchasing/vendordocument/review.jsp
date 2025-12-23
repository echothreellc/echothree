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
        <title>Vendor Document (<c:out value="${partyDocument.document.documentName}" />)</title>
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
                <a href="${vendorDocumentsUrl}">Documents</a> &gt;&gt;
                Vendor Document (<c:out value="${partyDocument.document.documentName}" />)
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="Event.List" />
            <p><font size="+2"><b>
                <c:choose>
                    <c:when test="${partyDocument.document.description == null}">
                        ${partyDocument.document.documentName}
                    </c:when>
                    <c:otherwise>
                        <c:out value="${partyDocument.document.description}" />
                    </c:otherwise>
                </c:choose>
            </b></font></p>
            <br />
            Document Name: ${partyDocument.document.documentName}<br />
            <br />
            <br />
            <br />
            Created: <c:out value="${partyDocument.document.entityInstance.entityTime.createdTime}" /><br />
            Modified: <c:out value="${partyDocument.document.entityInstance.entityTime.modifiedTime}" /><br />
            <c:url var="eventsUrl" value="/action/Core/Event/Main">
                <c:param name="EntityRef" value="${partyDocument.document.entityInstance.entityRef}" />
            </c:url>
            <a href="${eventsUrl}">Events</a>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
