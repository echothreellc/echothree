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
        <title>Filter Adjustment Descriptions</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Filter/Main" />">Filters</a> &gt;&gt;
                <a href="<c:url value="/action/Filter/FilterKind/Main" />">Kinds</a> &gt;&gt;
                <c:url var="filterAdjustmentsUrl" value="/action/Filter/FilterAdjustment/Main">
                    <c:param name="FilterKindName" value="${filterKind.filterKindName}" />
                </c:url>
                <a href="${filterAdjustmentsUrl}">Adjustments</a> &gt;&gt;
                Descriptions
            </h2>
        </div>
        <div id="Content">
            <c:url var="addDescriptionUrl" value="/action/Filter/FilterAdjustment/DescriptionAdd">
                    <c:param name="FilterKindName" value="${filterKind.filterKindName}" />
                    <c:param name="FilterAdjustmentName" value="${filterAdjustment.filterAdjustmentName}" />
            </c:url>
            <p><a href="${addDescriptionUrl}">Add Description.</a></p>
            <display:table name="filterAdjustmentDescriptions" id="filterAdjustmentDescription" class="displaytag">
                <display:column titleKey="columnTitle.language">
                    <c:out value="${filterAdjustmentDescription.language.description}" />
                </display:column>
                <display:column titleKey="columnTitle.description">
                    <c:out value="${filterAdjustmentDescription.description}" />
                </display:column>
                <display:column>
                    <c:url var="editDescriptionUrl" value="/action/Filter/FilterAdjustment/DescriptionEdit">
                        <c:param name="FilterKindName" value="${filterAdjustmentDescription.filterAdjustment.filterKind.filterKindName}" />
                        <c:param name="FilterAdjustmentName" value="${filterAdjustmentDescription.filterAdjustment.filterAdjustmentName}" />
                        <c:param name="LanguageIsoName" value="${filterAdjustmentDescription.language.languageIsoName}" />
                    </c:url>
                    <a href="${editDescriptionUrl}">Edit</a>
                    <c:url var="deleteDescriptionUrl" value="/action/Filter/FilterAdjustment/DescriptionDelete">
                        <c:param name="FilterKindName" value="${filterAdjustmentDescription.filterAdjustment.filterKind.filterKindName}" />
                        <c:param name="FilterAdjustmentName" value="${filterAdjustmentDescription.filterAdjustment.filterAdjustmentName}" />
                        <c:param name="LanguageIsoName" value="${filterAdjustmentDescription.language.languageIsoName}" />
                    </c:url>
                    <a href="${deleteDescriptionUrl}">Delete</a>
                </display:column>
           </display:table>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
