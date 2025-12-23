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
        <title>Items (<c:out value="${vendorName}" />)</title>
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
                    <c:param name="VendorName" value="${vendorName}" />
                </c:url>
                <a href="${vendorUrl}">Review (<c:out value="${vendorName}" />)</a> &gt;&gt;
                <c:url var="vendorItemsUrl" value="/action/Purchasing/VendorItem/Main">
                    <c:param name="VendorName" value="${vendorName}" />
                </c:url>
                <a href="${vendorItemsUrl}">Items</a> &gt;&gt;
                <c:url var="reviewUrl" value="/action/Purchasing/VendorItem/Review">
                    <c:param name="VendorName" value="${vendorName}" />
                    <c:param name="VendorItemName" value="${vendorItemName}" />
                </c:url>
                <a href="${reviewUrl}">Review (<c:out value="${vendorItemName}" />)</a> &gt;&gt;
                <c:url var="vendorItemCostsUrl" value="/action/Purchasing/VendorItemCost/Main">
                    <c:param name="VendorName" value="${vendorName}" />
                    <c:param name="VendorItemName" value="${vendorItemName}" />
                </c:url>
                <a href="${vendorItemCostsUrl}">Costs (<c:out value="${inventoryConditionName}" />, <c:out value="${unitOfMeasureTypeName}" />)</a> &gt;&gt;
                Edit
            </h2>
        </div>
        <div id="Content">
            <c:choose>
                <c:when test="${commandResult.executionResult.hasLockErrors}">
                    <et:executionErrors id="errorMessage">
                        <p class="executionErrors"><c:out value="${errorMessage}" /></p><br />
                    </et:executionErrors>
                </c:when>
                <c:otherwise>
                    <et:executionErrors id="errorMessage">
                        <p class="executionErrors"><c:out value="${errorMessage}" /></p><br />
                    </et:executionErrors>
                    <html:form action="/Purchasing/VendorItemCost/Edit" method="POST" focus="unitCost">
                        <table>
                            <tr>
                                <td align=right><fmt:message key="label.unitCost" />:</td>
                                <td>
                                    <html:text property="unitCost" size="15" maxlength="15" /> (*)
                                    <et:validationErrors id="errorMessage" property="UnitCost">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <html:hidden property="vendorName" />
                                    <html:hidden property="vendorItemName" />
                                    <html:hidden property="inventoryConditionName" />
                                    <html:hidden property="unitOfMeasureTypeName" />
                                </td>
                                <td><html:submit onclick="onSubmitDisable(this);" />&nbsp;<html:reset /><html:hidden property="submitButton" /></td>
                            </tr>
                        </table>
                    </html:form>
                </c:otherwise>
            </c:choose>
        </div>
        <jsp:include page="../../include/entityLock.jsp" />
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
