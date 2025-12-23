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
        <title>Uses</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/UnitOfMeasure/Main" />">Units of Measure</a> &gt;&gt;
                <c:choose>
                    <c:when test="${unitOfMeasureKind != null}">
                        <a href="<c:url value="/action/UnitOfMeasure/UnitOfMeasureKind/Main" />">Kinds</a>
                    </c:when>
                    <c:when test="${unitOfMeasureKindUseType != null}">
                        <a href="<c:url value="/action/UnitOfMeasure/UnitOfMeasureKindUseType/Main" />">Use Types</a>
                    </c:when>
                </c:choose>
                &gt;&gt; Uses
            </h2>
        </div>
        <div id="Content">
            <c:url var="addUrl" value="/action/UnitOfMeasure/UnitOfMeasureKindUse/Add">
                <c:choose>
                    <c:when test="${unitOfMeasureKind != null}">
                        <c:param name="UnitOfMeasureKindName" value="${unitOfMeasureKind.unitOfMeasureKindName}" />
                        <c:param name="ForwardParameter" value="UnitOfMeasureKindName" />
                    </c:when>
                    <c:when test="${unitOfMeasureKindUseType != null}">
                        <c:param name="UnitOfMeasureKindUseTypeName" value="${unitOfMeasureKindUseType.unitOfMeasureKindUseTypeName}" />
                        <c:param name="ForwardParameter" value="UnitOfMeasureKindUseTypeName" />
                    </c:when>
                </c:choose>
            </c:url>
            <p><a href="${addUrl}">Add
                <c:choose>
                    <c:when test="${unitOfMeasureKind != null}">
                        Use.
                    </c:when>
                    <c:when test="${unitOfMeasureKindUseType != null}">
                        Kind.
                    </c:when>
                </c:choose>
            </a></p>
            <display:table name="unitOfMeasureKindUses" id="unitOfMeasureKindUse" class="displaytag">
                <c:choose>
                    <c:when test='${unitOfMeasureKind != null}'>
                        <display:column titleKey="columnTitle.useType">
                            <c:out value="${unitOfMeasureKindUse.unitOfMeasureKindUseType.description}" />
                        </display:column>
                    </c:when>
                    <c:when test='${unitOfMeasureKindUseType != null}'>
                        <display:column titleKey="columnTitle.kind">
                            <c:out value="${unitOfMeasureKindUse.unitOfMeasureKind.description}" />
                            (<c:out value="${unitOfMeasureKindUse.unitOfMeasureKind.unitOfMeasureKindName}" />)
                        </display:column>
                    </c:when>
                </c:choose>
                <display:column property="sortOrder" titleKey="columnTitle.sortOrder" />
                <display:column titleKey="columnTitle.default">
                    <c:choose>
                        <c:when test="${unitOfMeasureKindUse.isDefault}">
                            Default
                        </c:when>
                        <c:otherwise>
                            <c:url var="setDefaultUrl" value="/action/UnitOfMeasure/UnitOfMeasureKindUse/SetDefault">
                                <c:param name="UnitOfMeasureKindUseTypeName" value="${unitOfMeasureKindUse.unitOfMeasureKindUseType.unitOfMeasureKindUseTypeName}" />
                                <c:param name="UnitOfMeasureKindName" value="${unitOfMeasureKindUse.unitOfMeasureKind.unitOfMeasureKindName}" />
                                <c:choose>
                                    <c:when test="${unitOfMeasureKind != null}">
                                        <c:param name="ForwardParameter" value="UnitOfMeasureKindName" />
                                    </c:when>
                                    <c:when test="${unitOfMeasureKindUseType != null}">
                                        <c:param name="ForwardParameter" value="UnitOfMeasureKindUseTypeName" />
                                    </c:when>
                                </c:choose>
                            </c:url>
                            <a href="${setDefaultUrl}">Set Default</a>
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column>
                    <c:url var="editUrl" value="/action/UnitOfMeasure/UnitOfMeasureKindUse/Edit">
                        <c:param name="UnitOfMeasureKindUseTypeName" value="${unitOfMeasureKindUse.unitOfMeasureKindUseType.unitOfMeasureKindUseTypeName}" />
                        <c:param name="UnitOfMeasureKindName" value="${unitOfMeasureKindUse.unitOfMeasureKind.unitOfMeasureKindName}" />
                        <c:choose>
                            <c:when test="${unitOfMeasureKind != null}">
                                <c:param name="ForwardParameter" value="UnitOfMeasureKindName" />
                            </c:when>
                            <c:when test="${unitOfMeasureKindUseType != null}">
                                <c:param name="ForwardParameter" value="UnitOfMeasureKindUseTypeName" />
                            </c:when>
                        </c:choose>
                    </c:url>
                    <a href="${editUrl}">Edit</a>
                    <c:url var="deleteUrl" value="/action/UnitOfMeasure/UnitOfMeasureKindUse/Delete">
                        <c:param name="UnitOfMeasureKindUseTypeName" value="${unitOfMeasureKindUse.unitOfMeasureKindUseType.unitOfMeasureKindUseTypeName}" />
                        <c:param name="UnitOfMeasureKindName" value="${unitOfMeasureKindUse.unitOfMeasureKind.unitOfMeasureKindName}" />
                        <c:choose>
                            <c:when test="${unitOfMeasureKind != null}">
                                <c:param name="ForwardParameter" value="UnitOfMeasureKindName" />
                            </c:when>
                            <c:when test="${unitOfMeasureKindUseType != null}">
                                <c:param name="ForwardParameter" value="UnitOfMeasureKindUseTypeName" />
                            </c:when>
                        </c:choose>
                    </c:url>
                    <a href="${deleteUrl}">Delete</a>
                </display:column>
            </display:table>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
