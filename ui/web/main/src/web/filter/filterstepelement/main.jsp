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
        <title>Filter Step Elements</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Filter/Main" />">Filters</a> &gt;&gt;
                <a href="<c:url value="/action/Filter/FilterKind/Main" />">Kinds</a> &gt;&gt;
                <c:url var="filterTypesUrl" value="/action/Filter/FilterType/Main">
                    <c:param name="FilterKindName" value="${filterKind.filterKindName}" />
                </c:url>
                <a href="${filterTypesUrl}">Types</a> &gt;&gt;
                <c:url var="filtersUrl" value="/action/Filter/Filter/Main">
                    <c:param name="FilterKindName" value="${filterKind.filterKindName}" />
                    <c:param name="FilterTypeName" value="${filterType.filterTypeName}" />
                </c:url>
                <a href="${filtersUrl}">Filters</a> &gt;&gt;
                <c:url var="filterStepsUrl" value="/action/Filter/FilterStep/Main">
                    <c:param name="FilterKindName" value="${filterKind.filterKindName}" />
                    <c:param name="FilterTypeName" value="${filterType.filterTypeName}" />
                    <c:param name="FilterName" value="${filter.filterName}" />
                </c:url>
                <a href="${filterStepsUrl}">Steps</a> &gt;&gt;
                Elements
            </h2>
        </div>
        <div id="Content">
            <c:url var="addUrl" value="/action/Filter/FilterStepElement/Add">
                <c:param name="FilterKindName" value="${filterKind.filterKindName}" />
                <c:param name="FilterTypeName" value="${filterType.filterTypeName}" />
                <c:param name="FilterName" value="${filter.filterName}" />
                <c:param name="FilterStepName" value="${filterStep.filterStepName}" />
            </c:url>
            <p><a href="${addUrl}">Add Element.</a></p>
            <display:table name="filterStepElements" id="filterStepElement" class="displaytag">
                <display:column titleKey="columnTitle.name">
                    <c:url var="reviewUrl" value="/action/Filter/FilterStepElement/Review">
                        <c:param name="FilterKindName" value="${filterStepElement.filterStep.filter.filterType.filterKind.filterKindName}" />
                        <c:param name="FilterTypeName" value="${filterStepElement.filterStep.filter.filterType.filterTypeName}" />
                        <c:param name="FilterName" value="${filterStepElement.filterStep.filter.filterName}" />
                        <c:param name="FilterStepName" value="${filterStepElement.filterStep.filterStepName}" />
                        <c:param name="FilterStepElementName" value="${filterStepElement.filterStepElementName}" />
                    </c:url>
                    <a href="${reviewUrl}"><c:out value="${filterStepElement.filterStepElementName}" /></a>
                </display:column>
                <display:column titleKey="columnTitle.description">
                    <c:out value="${filterStepElement.description}" />
                </display:column>
                <display:column property="filterItemSelector.selectorName" titleKey="columnTitle.itemSelector" />
                <display:column property="filterAdjustment.filterAdjustmentName" titleKey="columnTitle.adjustment" />
                <display:column>
                    <c:url var="editUrl" value="/action/Filter/FilterStepElement/Edit">
                        <c:param name="FilterKindName" value="${filterStepElement.filterStep.filter.filterType.filterKind.filterKindName}" />
                        <c:param name="FilterTypeName" value="${filterStepElement.filterStep.filter.filterType.filterTypeName}" />
                        <c:param name="FilterName" value="${filterStepElement.filterStep.filter.filterName}" />
                        <c:param name="FilterStepName" value="${filterStepElement.filterStep.filterStepName}" />
                        <c:param name="OriginalFilterStepElementName" value="${filterStepElement.filterStepElementName}" />
                    </c:url>
                    <a href="${editUrl}">Edit</a>
                    <c:url var="descriptionsUrl" value="/action/Filter/FilterStepElement/Description">
                        <c:param name="FilterKindName" value="${filterStepElement.filterStep.filter.filterType.filterKind.filterKindName}" />
                        <c:param name="FilterTypeName" value="${filterStepElement.filterStep.filter.filterType.filterTypeName}" />
                        <c:param name="FilterName" value="${filterStepElement.filterStep.filter.filterName}" />
                        <c:param name="FilterStepName" value="${filterStepElement.filterStep.filterStepName}" />
                        <c:param name="FilterStepElementName" value="${filterStepElement.filterStepElementName}" />
                    </c:url>
                    <a href="${descriptionsUrl}">Descriptions</a>
                    <c:url var="deleteUrl" value="/action/Filter/FilterStepElement/Delete">
                        <c:param name="FilterKindName" value="${filterStepElement.filterStep.filter.filterType.filterKind.filterKindName}" />
                        <c:param name="FilterTypeName" value="${filterStepElement.filterStep.filter.filterType.filterTypeName}" />
                        <c:param name="FilterName" value="${filterStepElement.filterStep.filter.filterName}" />
                        <c:param name="FilterStepName" value="${filterStepElement.filterStep.filterStepName}" />
                        <c:param name="FilterStepElementName" value="${filterStepElement.filterStepElementName}" />
                    </c:url>
                    <a href="${deleteUrl}">Delete</a>
                </display:column>
            </display:table>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
