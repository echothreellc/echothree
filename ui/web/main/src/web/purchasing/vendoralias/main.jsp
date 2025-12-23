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
        <title>Vendor Aliases (<c:out value="${vendor.vendorName}" />)</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Purchasing/Main" />"><fmt:message key="navigation.purchasing" /></a> &gt;&gt;
                <a href="<c:url value="/action/Purchasing/Vendor/Main" />"><fmt:message key="navigation.vendors" /></a> &gt;&gt;
                <et:countVendorResults searchTypeName="VENDOR_REVIEW" countVar="vendorResultsCount" commandResultVar="countVendorResultsCommandResult" logErrors="false" />
                <c:if test="${vendorResultsCount > 0}">
                    <a href="<c:url value="/action/Purchasing/Vendor/Result" />"><fmt:message key="navigation.results" /></a> &gt;&gt;
                </c:if>
                <c:url var="reviewUrl" value="/action/Purchasing/Vendor/Review">
                    <c:param name="VendorName" value="${vendor.vendorName}" />
                </c:url>
                <a href="${reviewUrl}">Review (<c:out value="${vendor.vendorName}" />)</a> &gt;&gt;
                <fmt:message key="navigation.vendorAliases" />
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="Event.List" />
            <c:if test='${vendor.person.firstName != null || vendor.person.middleName != null || vendor.person.lastName != null}'>
                <p><font size="+2"><b><c:out value="${vendor.person.personalTitle.description}" /> <c:out value="${vendor.person.firstName}" />
                <c:out value="${vendor.person.middleName}" /> <c:out value="${vendor.person.lastName}" />
                <c:out value="${vendor.person.nameSuffix.description}" /></b></font></p>
            </c:if>
            <c:if test='${vendor.partyGroup.name != null}'>
                <p><font size="+1"><c:out value="${vendor.partyGroup.name}" /></font></p>
            </c:if>
            <br />
            <br />
            <c:set var="commonUrl" scope="request" value="Purchasing/VendorAlias" />
            <c:set var="partyAliases" scope="request" value="${vendor.partyAliases}" />
            <c:set var="securityRoleGroupNamePrefix" scope="request" value="Vendor" />
            <c:set var="party" scope="request" value="${vendor}" />
            <jsp:include page="../../include/partyAliases.jsp" />
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
