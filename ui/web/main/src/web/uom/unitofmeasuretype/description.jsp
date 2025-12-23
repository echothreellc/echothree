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
        <title>Unit of Measure Type Descriptions</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/UnitOfMeasure/Main" />">Units of Measure</a> &gt;&gt;
                <a href="<c:url value="/action/UnitOfMeasure/UnitOfMeasureKind/Main" />">Kinds</a> &gt;&gt;
                <c:url var="unitOfMeasureTypesUrl" value="/action/UnitOfMeasure/UnitOfMeasureType/Main">
                    <c:param name="UnitOfMeasureKindName" value="${unitOfMeasureKind.unitOfMeasureKindName}" />
                </c:url>
                <a href="${unitOfMeasureTypesUrl}">Types</a> &gt;&gt;
                Descriptions
            </h2>
        </div>
        <div id="Content">
            <c:url var="addDescriptionUrl" value="/action/UnitOfMeasure/UnitOfMeasureType/DescriptionAdd">
                <c:param name="UnitOfMeasureKindName" value="${unitOfMeasureKind.unitOfMeasureKindName}" />
                <c:param name="UnitOfMeasureTypeName" value="${unitOfMeasureType.unitOfMeasureTypeName}" />
            </c:url>
            <p><a href="${addDescriptionUrl}">Add Description.</a></p>
            <display:table name="unitOfMeasureTypeDescriptions" id="unitOfMeasureTypeDescription" class="displaytag">
                <display:column titleKey="columnTitle.language">
                    <c:out value="${unitOfMeasureTypeDescription.language.description}" />
                </display:column>
                <display:column titleKey="columnTitle.singularDescription">
                    <c:out value="${unitOfMeasureTypeDescription.singularDescription}" />
                </display:column>
                <display:column titleKey="columnTitle.pluralDescription">
                    <c:out value="${unitOfMeasureTypeDescription.pluralDescription}" />
                </display:column>
                <display:column titleKey="columnTitle.symbol">
                    <c:out value="${unitOfMeasureTypeDescription.symbol}" />
                </display:column>
                <display:column>
                    <c:url var="editDescriptionUrl" value="/action/UnitOfMeasure/UnitOfMeasureType/DescriptionEdit">
                        <c:param name="UnitOfMeasureKindName" value="${unitOfMeasureTypeDescription.unitOfMeasureType.unitOfMeasureKind.unitOfMeasureKindName}" />
                        <c:param name="UnitOfMeasureTypeName" value="${unitOfMeasureTypeDescription.unitOfMeasureType.unitOfMeasureTypeName}" />
                        <c:param name="LanguageIsoName" value="${unitOfMeasureTypeDescription.language.languageIsoName}" />
                    </c:url>
                    <a href="${editDescriptionUrl}">Edit</a>
                    <c:url var="deleteDescriptionUrl" value="/action/UnitOfMeasure/UnitOfMeasureType/DescriptionDelete">
                        <c:param name="UnitOfMeasureKindName" value="${unitOfMeasureTypeDescription.unitOfMeasureType.unitOfMeasureKind.unitOfMeasureKindName}" />
                        <c:param name="UnitOfMeasureTypeName" value="${unitOfMeasureTypeDescription.unitOfMeasureType.unitOfMeasureTypeName}" />
                        <c:param name="LanguageIsoName" value="${unitOfMeasureTypeDescription.language.languageIsoName}" />
                    </c:url>
                    <a href="${deleteDescriptionUrl}">Delete</a>
                </display:column>
           </display:table>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
