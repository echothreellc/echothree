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
        <title>Elements</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Configuration/Main" />">Configuration</a> &gt;&gt;
                <a href="<c:url value="/action/Configuration/PostalAddressFormat/Main" />">Postal Address Formats</a> &gt;&gt;
                <c:url var="postalAddressLinesUrl" value="/action/Configuration/PostalAddressLine/Main">
                    <c:param name="PostalAddressFormatName" value="${postalAddressFormat.postalAddressFormatName}" />
                </c:url>
                <a href="${postalAddressLinesUrl}">Lines</a> &gt;&gt;
                Elements
            </h2>
        </div>
        <div id="Content">
            <c:url var="addUrl" value="/action/Configuration/PostalAddressLineElement/Add">
                <c:param name="PostalAddressFormatName" value="${postalAddressFormat.postalAddressFormatName}" />
                <c:param name="PostalAddressLineSortOrder" value="${postalAddressLine.postalAddressLineSortOrder}" />
            </c:url>
            <p><a href="${addUrl}">Add Element.</a></p>
            <display:table name="postalAddressLineElements" id="postalAddressLineElement" class="displaytag">
                <display:column property="postalAddressLineElementSortOrder" titleKey="columnTitle.sortOrder" />
                <display:column titleKey="columnTitle.elementType">
                    <c:out value="${postalAddressLineElement.postalAddressElementType.description}" />
                </display:column>
                <display:column property="prefix" titleKey="columnTitle.prefix" />
                <display:column titleKey="columnTitle.alwaysIncludePrefix">
                    <c:choose>
                        <c:when test="${postalAddressLineElement.alwaysIncludePrefix}">
                            <fmt:message key="phrase.yes" />
                        </c:when>
                        <c:otherwise>
                            <fmt:message key="phrase.no" />
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column property="suffix" titleKey="columnTitle.suffix" />
                <display:column titleKey="columnTitle.alwaysIncludeSuffix">
                    <c:choose>
                        <c:when test="${postalAddressLineElement.alwaysIncludeSuffix}">
                            <fmt:message key="phrase.yes" />
                        </c:when>
                        <c:otherwise>
                            <fmt:message key="phrase.no" />
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column>
                    <c:url var="editUrl" value="/action/Configuration/PostalAddressLineElement/Edit">
                        <c:param name="PostalAddressFormatName" value="${postalAddressLineElement.postalAddressLine.postalAddressFormat.postalAddressFormatName}" />
                        <c:param name="PostalAddressLineSortOrder" value="${postalAddressLineElement.postalAddressLine.postalAddressLineSortOrder}" />
                        <c:param name="OriginalPostalAddressLineElementSortOrder" value="${postalAddressLineElement.postalAddressLineElementSortOrder}" />
                    </c:url>
                    <a href="${editUrl}">Edit</a>
                    <c:url var="deleteUrl" value="/action/Configuration/PostalAddressLineElement/Delete">
                        <c:param name="PostalAddressFormatName" value="${postalAddressLineElement.postalAddressLine.postalAddressFormat.postalAddressFormatName}" />
                        <c:param name="PostalAddressLineSortOrder" value="${postalAddressLineElement.postalAddressLine.postalAddressLineSortOrder}" />
                        <c:param name="PostalAddressLineElementSortOrder" value="${postalAddressLineElement.postalAddressLineElementSortOrder}" />
                    </c:url>
                    <a href="${deleteUrl}">Delete</a>
                </display:column>
            </display:table>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
