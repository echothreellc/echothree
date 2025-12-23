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
        <title>Status</title>
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
                <c:url var="vendorUrl" value="/action/Purchasing/Vendor/Review">
                    <c:param name="VendorName" value="${vendorItem.vendor.vendorName}" />
                </c:url>
                <a href="${vendorUrl}">Review (<c:out value="${vendorItem.vendor.vendorName}" />)</a> &gt;&gt;
                <c:url var="vendorItemsUrl" value="/action/Purchasing/VendorItem/Main">
                    <c:param name="VendorName" value="${vendorItem.vendor.vendorName}" />
                </c:url>
                <a href="${vendorItemsUrl}">Items</a> &gt;&gt;
                <c:url var="reviewUrl" value="/action/Purchasing/VendorItem/Review">
                    <c:param name="VendorName" value="${vendorItem.vendor.vendorName}" />
                    <c:param name="VendorItemName" value="${vendorItem.vendorItemName}" />
                </c:url>
                <a href="${reviewUrl}">Review (<c:out value="${vendorItem.vendorItemName}" />)</a> &gt;&gt;
                Status
            </h2>
        </div>
        <div id="Content">
            <et:executionErrors id="errorMessage">
                <p class="executionErrors"><c:out value="${errorMessage}" /></p><br />
            </et:executionErrors>
            <html:form action="/Purchasing/VendorItem/Status" method="POST">
                <table>
                    <tr>
                        <td align=right><fmt:message key="label.vendorItemStatusChoice" />:</td>
                        <td>
                            <html:select property="vendorItemStatusChoice">
                                <html:optionsCollection property="vendorItemStatusChoices" />
                            </html:select>
                            <et:validationErrors id="errorMessage" property="VendorItemStatusChoice">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <html:hidden property="returnUrl" />
                            <html:hidden property="vendorName" />
                            <html:hidden property="vendorItemName" />
                        </td>
                        <td><html:submit onclick="onSubmitDisable(this);" />&nbsp;<html:cancel onclick="onSubmitDisable(this);" />&nbsp;<html:reset /><html:hidden property="submitButton" /></td>
                    </tr>
                </table>
            </html:form>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>