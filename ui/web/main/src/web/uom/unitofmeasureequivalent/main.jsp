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
        <title>Equivalents</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/UnitOfMeasure/Main" />">Units of Measure</a> &gt;&gt;
                <a href="<c:url value="/action/UnitOfMeasure/UnitOfMeasureKind/Main" />">Kinds</a> &gt;&gt;
                Equivalents
            </h2>
        </div>
        <div id="Content">
            <c:url var="addUrl" value="/action/UnitOfMeasure/UnitOfMeasureEquivalent/Add">
                <c:param name="UnitOfMeasureKindName" value="${unitOfMeasureKind.unitOfMeasureKindName}" />
            </c:url>
            <p><a href="${addUrl}">Add Equivalent.</a></p>
            <display:table name="unitOfMeasureEquivalents" id="unitOfMeasureEquivalent" class="displaytag">
                <display:column titleKey="columnTitle.fromType">
                    <c:out value="${unitOfMeasureEquivalent.fromUnitOfMeasureType.description}" />
                    (<c:out value="${unitOfMeasureEquivalent.fromUnitOfMeasureType.unitOfMeasureTypeName}" />)
                </display:column>
                <display:column titleKey="columnTitle.toType">
                    <c:out value="${unitOfMeasureEquivalent.toUnitOfMeasureType.description}" />
                    (<c:out value="${unitOfMeasureEquivalent.toUnitOfMeasureType.unitOfMeasureTypeName}" />)
                </display:column>
                <display:column titleKey="columnTitle.toQuantity">
                    <c:out value="${unitOfMeasureEquivalent.toQuantity}" />
                </display:column>
                <display:column>
                    <c:url var="editUrl" value="/action/UnitOfMeasure/UnitOfMeasureEquivalent/Edit">
                        <c:param name="UnitOfMeasureKindName" value="${unitOfMeasureKind.unitOfMeasureKindName}" />
                        <c:param name="FromUnitOfMeasureTypeName" value="${unitOfMeasureEquivalent.fromUnitOfMeasureType.unitOfMeasureTypeName}" />
                        <c:param name="ToUnitOfMeasureTypeName" value="${unitOfMeasureEquivalent.toUnitOfMeasureType.unitOfMeasureTypeName}" />
                    </c:url>
                    <a href="${editUrl}">Edit</a>
                    <c:url var="deleteUrl" value="/action/UnitOfMeasure/UnitOfMeasureEquivalent/Delete">
                        <c:param name="UnitOfMeasureKindName" value="${unitOfMeasureKind.unitOfMeasureKindName}" />
                        <c:param name="FromUnitOfMeasureTypeName" value="${unitOfMeasureEquivalent.fromUnitOfMeasureType.unitOfMeasureTypeName}" />
                        <c:param name="ToUnitOfMeasureTypeName" value="${unitOfMeasureEquivalent.toUnitOfMeasureType.unitOfMeasureTypeName}" />
                    </c:url>
                    <a href="${deleteUrl}">Delete</a>
                </display:column>
            </display:table>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
