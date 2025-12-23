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
        <title>Results</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Purchasing/Main" />">Purchasing</a> &gt;&gt;
                <a href="<c:url value="/action/Purchasing/Vendor/Main" />">Vendors</a> &gt;&gt;
                <fmt:message key="navigation.results" />
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="VendorStatus.Choices:Event.List" />
            <p><a href="<c:url value="/action/Purchasing/Vendor/Add" />">Add Vendor.</a></p>
            <et:containsExecutionError key="UnknownUserVisitSearch">
                <c:set var="unknownUserVisitSearch" value="true" />
            </et:containsExecutionError>
            <c:choose>
                <c:when test="${unknownUserVisitSearch}">
                    <p>Your search results are no longer available, please perform your search again.</p>
                </c:when>
                <c:otherwise>
                    <et:hasSecurityRole securityRole="VendorStatus.Choices" var="includeEditableVendorStatus">
                        <c:url var="returnUrl" scope="request" value="Result" />
                    </et:hasSecurityRole>
                    <c:choose>
                        <c:when test="${vendorResultCount == null || vendorResultCount < 21}">
                            <display:table name="vendorResults.list" id="vendorResult" class="displaytag" export="true" sort="list" requestURI="/action/Purchasing/Vendor/Result">
                                <display:setProperty name="export.csv.filename" value="Vendors.csv" />
                                <display:setProperty name="export.excel.filename" value="Vendors.xls" />
                                <display:setProperty name="export.pdf.filename" value="Vendors.pdf" />
                                <display:setProperty name="export.rtf.filename" value="Vendors.rtf" />
                                <display:setProperty name="export.xml.filename" value="Vendors.xml" />
                                <display:column titleKey="columnTitle.name" media="html" sortable="true" sortProperty="vendor.vendorName">
                                    <c:url var="reviewUrl" value="/action/Purchasing/Vendor/Review">
                                        <c:param name="VendorName" value="${vendorResult.vendor.vendorName}" />
                                    </c:url>
                                    <a href="${reviewUrl}">${vendorResult.vendor.vendorName}</a>
                                </display:column>
                                <display:column titleKey="columnTitle.companyName" media="html" sortable="true" sortProperty="vendor.partyGroup.name">
                                    <c:out value="${vendorResult.vendor.partyGroup.name}" />
                                </display:column>
                                <display:column titleKey="columnTitle.firstName" media="html" sortable="true" sortProperty="vendor.person.firstName">
                                    <c:if test='${vendorResult.vendor.person.firstName != null}'>
                                        <c:url var="mainUrl" value="/action/Purchasing/Vendor/Main">
                                            <c:param name="FirstName" value="${vendorResult.vendor.person.firstName}" />
                                        </c:url>
                                        <a href="${mainUrl}"><c:out value="${vendorResult.vendor.person.firstName}" /></a>
                                    </c:if>
                                </display:column>
                                <display:column titleKey="columnTitle.lastName" media="html" sortable="true" sortProperty="vendor.person.lastName">
                                    <c:if test='${vendorResult.vendor.person.lastName != null}'>
                                        <c:url var="mainUrl" value="/action/Purchasing/Vendor/Main">
                                            <c:param name="FirstName" value="${vendorResult.vendor.person.lastName}" />
                                        </c:url>
                                        <a href="${mainUrl}"><c:out value="${vendorResult.vendor.person.lastName}" /></a>
                                    </c:if>
                                </display:column>
                                <display:column titleKey="columnTitle.status" media="html" sortable="true" sortProperty="vendor.vendorStatus.workflowStep.workflowStepName">
                                    <c:choose>
                                        <c:when test="${includeEditableVendorStatus}">
                                            <c:url var="statusUrl" value="/action/Purchasing/Vendor/Status">
                                                <c:param name="VendorName" value="${vendorResult.vendor.vendorName}" />
                                                <c:param name="ReturnUrl" value="${returnUrl}" />
                                            </c:url>
                                            <a href="${statusUrl}"><c:out value="${vendorResult.vendor.vendorStatus.workflowStep.description}" /></a>
                                        </c:when>
                                        <c:otherwise>
                                            <c:out value="${vendorResult.vendor.vendorStatus.workflowStep.description}" />
                                        </c:otherwise>
                                    </c:choose>
                                </display:column>
                                <display:column media="html">
                                    <c:url var="vendorContactMechanismsUrl" value="/action/Purchasing/VendorContactMechanism/Main">
                                        <c:param name="VendorName" value="${vendorResult.vendor.vendorName}" />
                                    </c:url>
                                    <a href="${vendorContactMechanismsUrl}">Contact Mechanisms</a>
                                    <c:url var="vendorDocumentsUrl" value="/action/Purchasing/VendorDocument/Main">
                                        <c:param name="VendorName" value="${vendorResult.vendor.vendorName}" />
                                    </c:url>
                                    <a href="${vendorDocumentsUrl}">Documents</a>
                                    <c:url var="vendorItemsUrl" value="/action/Purchasing/VendorItem/Main">
                                        <c:param name="VendorName" value="${vendorResult.vendor.vendorName}" />
                                    </c:url>
                                    <a href="${vendorItemsUrl}">Items</a><br />
                                    <c:url var="editUrl" value="/action/Purchasing/Vendor/VendorEdit">
                                        <c:param name="OriginalVendorName" value="${vendorResult.vendor.vendorName}" />
                                    </c:url>
                                    <a href="${editUrl}">Edit</a>
                                </display:column>
                                <et:hasSecurityRole securityRole="Event.List">
                                    <display:column media="html">
                                        <c:url var="eventsUrl" value="/action/Core/Event/Main">
                                            <c:param name="EntityRef" value="${vendorResult.vendor.entityInstance.entityRef}" />
                                        </c:url>
                                        <a href="${eventsUrl}">Events</a>
                                    </display:column>
                                </et:hasSecurityRole>
                                <display:column property="vendor.vendorName" titleKey="columnTitle.name" media="csv excel pdf rtf xml" />
                                <display:column property="vendor.partyGroup.name" titleKey="columnTitle.companyName" media="csv excel pdf rtf xml" />
                                <display:column property="vendor.person.firstName" titleKey="columnTitle.firstName" media="csv excel pdf rtf xml" />
                                <display:column property="vendor.person.lastName" titleKey="columnTitle.lastName" media="csv excel pdf rtf xml" />
                                <display:column property="vendor.vendorStatus.workflowStep.description" titleKey="columnTitle.status" media="csv excel pdf rtf xml" />
                            </display:table>
                            <c:if test="${itemResults.size > 20}">
                                <c:url var="resultsUrl" value="/action/Purchasing/Vendor/Result" />
                                <a href="${resultsUrl}">Paged Results</a>
                            </c:if>
                        </c:when>
                        <c:otherwise>
                            <display:table name="vendorResults.list" id="vendorResult" class="displaytag" partialList="true" pagesize="20" size="vendorResultCount" requestURI="/action/Purchasing/Vendor/Result">
                                <display:column titleKey="columnTitle.name">
                                    <c:url var="reviewUrl" value="/action/Purchasing/Vendor/Review">
                                        <c:param name="VendorName" value="${vendorResult.vendor.vendorName}" />
                                    </c:url>
                                    <a href="${reviewUrl}">${vendorResult.vendor.vendorName}</a>
                                </display:column>
                                <display:column titleKey="columnTitle.companyName">
                                    <c:out value="${vendorResult.vendor.partyGroup.name}" />
                                </display:column>
                                <display:column titleKey="columnTitle.firstName">
                                    <c:if test='${vendorResult.vendor.person.firstName != null}'>
                                        <c:url var="mainUrl" value="/action/Purchasing/Vendor/Main">
                                            <c:param name="FirstName" value="${vendorResult.vendor.person.firstName}" />
                                        </c:url>
                                        <a href="${mainUrl}"><c:out value="${vendorResult.vendor.person.firstName}" /></a>
                                    </c:if>
                                </display:column>
                                <display:column titleKey="columnTitle.lastName">
                                    <c:if test='${vendorResult.vendor.person.lastName != null}'>
                                        <c:url var="mainUrl" value="/action/Purchasing/Vendor/Main">
                                            <c:param name="FirstName" value="${vendorResult.vendor.person.lastName}" />
                                        </c:url>
                                        <a href="${mainUrl}"><c:out value="${vendorResult.vendor.person.lastName}" /></a>
                                    </c:if>
                                </display:column>
                                <display:column titleKey="columnTitle.status">
                                    <c:choose>
                                        <c:when test="${includeEditableVendorStatus}">
                                            <c:url var="statusUrl" value="/action/Purchasing/Vendor/Status">
                                                <c:param name="VendorName" value="${vendorResult.vendor.vendorName}" />
                                                <c:param name="ReturnUrl" value="${returnUrl}" />
                                            </c:url>
                                            <a href="${statusUrl}"><c:out value="${vendorResult.vendor.vendorStatus.workflowStep.description}" /></a>
                                        </c:when>
                                        <c:otherwise>
                                            <c:out value="${vendorResult.vendor.vendorStatus.workflowStep.description}" />
                                        </c:otherwise>
                                    </c:choose>
                                </display:column>
                                <display:column>
                                    <c:url var="vendorContactMechanismsUrl" value="/action/Purchasing/VendorContactMechanism/Main">
                                        <c:param name="VendorName" value="${vendorResult.vendor.vendorName}" />
                                    </c:url>
                                    <a href="${vendorContactMechanismsUrl}">Contact Mechanisms</a>
                                    <c:url var="vendorDocumentsUrl" value="/action/Purchasing/VendorDocument/Main">
                                        <c:param name="VendorName" value="${vendorResult.vendor.vendorName}" />
                                    </c:url>
                                    <a href="${vendorDocumentsUrl}">Documents</a>
                                    <c:url var="vendorItemsUrl" value="/action/Purchasing/VendorItem/Main">
                                        <c:param name="VendorName" value="${vendorResult.vendor.vendorName}" />
                                    </c:url>
                                    <a href="${vendorItemsUrl}">Items</a><br />
                                    <c:url var="editUrl" value="/action/Purchasing/Vendor/VendorEdit">
                                        <c:param name="OriginalVendorName" value="${vendorResult.vendor.vendorName}" />
                                    </c:url>
                                    <a href="${editUrl}">Edit</a>
                                </display:column>
                                <et:hasSecurityRole securityRole="Event.List">
                                    <display:column>
                                        <c:url var="eventsUrl" value="/action/Core/Event/Main">
                                            <c:param name="EntityRef" value="${vendorResult.vendor.entityInstance.entityRef}" />
                                        </c:url>
                                        <a href="${eventsUrl}">Events</a>
                                    </display:column>
                                </et:hasSecurityRole>
                            </display:table>
                            <c:url var="resultsUrl" value="/action/Purchasing/Vendor/Result">
                                <c:param name="Results" value="Complete" />
                            </c:url>
                            <a href="${resultsUrl}">All Results</a>
                        </c:otherwise>
                    </c:choose>
                </c:otherwise>
            </c:choose>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
