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
        <title>Review (<c:out value="${unitOfMeasureType.unitOfMeasureTypeName}" />)</title>
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
                    <c:param name="UnitOfMeasureKindName" value="${unitOfMeasureType.unitOfMeasureKind.unitOfMeasureKindName}" />
                </c:url>
                <a href="${unitOfMeasureTypesUrl}">Types</a> &gt;&gt;
                Review (<c:out value="${unitOfMeasureType.unitOfMeasureTypeName}" />)
            </h2>
        </div>
        <div id="Content">
            <p><font size="+2"><b><c:out value="${unitOfMeasureType.description}" /></b></font></p>
            <br />
            Unit of Measure Kind Name: ${unitOfMeasureType.unitOfMeasureKind.unitOfMeasureKindName}<br />
            Unit of Measure Type Name: ${unitOfMeasureType.unitOfMeasureTypeName}<br />
            <br />
            Symbol Position: <c:out value="${unitOfMeasureType.symbolPosition.description}" /> <br />
            Suppress Symbol Separator:
            <c:choose>
                <c:when test="${unitOfMeasureType.suppressSymbolSeparator}">
                    <fmt:message key="phrase.yes" />
                </c:when>
                <c:otherwise>
                    <fmt:message key="phrase.no" />
                </c:otherwise>
            </c:choose>
            <br />
            Is Default:
            <c:choose>
                <c:when test="${unitOfMeasureType.isDefault}">
                    <fmt:message key="phrase.yes" />
                </c:when>
                <c:otherwise>
                    <fmt:message key="phrase.no" />
                </c:otherwise>
            </c:choose>
            <br />
            Sort Order: <c:out value="${unitOfMeasureType.sortOrder}" /> <br />
            <br />
            <br />
            <br />
            Created: <c:out value="${unitOfMeasureType.entityInstance.entityTime.createdTime}" /><br />
            <c:if test='${unitOfMeasureType.entityInstance.entityTime.modifiedTime != null}'>
                Modified: <c:out value="${unitOfMeasureType.entityInstance.entityTime.modifiedTime}" /><br />
            </c:if>
            <c:if test='${unitOfMeasureType.entityInstance.entityTime.deletedTime != null}'>
                Deleted: <c:out value="${unitOfMeasureType.entityInstance.entityTime.deletedTime}" /><br />
            </c:if>
            <et:checkSecurityRoles securityRoles="Event.List" />
            <et:hasSecurityRole securityRole="Event.List">
                <c:url var="eventsUrl" value="/action/Core/Event/Main">
                    <c:param name="EntityRef" value="${unitOfMeasureType.entityInstance.entityRef}" />
                </c:url>
                <a href="${eventsUrl}">Events</a>
            </et:hasSecurityRole>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
