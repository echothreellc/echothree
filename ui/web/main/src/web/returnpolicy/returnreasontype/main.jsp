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
        <title>Return Types</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/ReturnPolicy/Main" />">Returns</a> &gt;&gt;
                <a href="<c:url value="/action/ReturnPolicy/ReturnKind/Main" />">Return Kinds</a> &gt;&gt;
                <c:url var="returnReasonsUrl" value="/action/ReturnPolicy/ReturnReason/Main">
                    <c:param name="ReturnKindName" value="${returnReason.returnKind.returnKindName}" />
                </c:url>
                <a href="${returnReasonsUrl}">Return Reasons</a> &gt;&gt;
                Return Types
            </h2>
        </div>
        <div id="Content">
            <c:url var="addUrl" value="/action/ReturnPolicy/ReturnReasonType/Add">
                <c:param name="ReturnKindName" value="${returnReason.returnKind.returnKindName}" />
                <c:param name="ReturnReasonName" value="${returnReason.returnReasonName}" />
            </c:url>
            <p><a href="${addUrl}">Add Return Type.</a></p>
            <display:table name="returnReasonTypes" id="returnReasonType" class="displaytag">
                <display:column titleKey="columnTitle.returnType">
                    <c:url var="returnTypeUrl" value="/action/ReturnPolicy/ReturnType/Review">
                        <c:param name="ReturnKindName" value="${returnReasonType.returnType.returnKind.returnKindName}" />
                        <c:param name="ReturnTypeName" value="${returnReasonType.returnType.returnTypeName}" />
                    </c:url>
                    <a href="${returnTypeUrl}"><c:out value="${returnReasonType.returnType.description}" /></a>
                </display:column>
                <display:column property="sortOrder" titleKey="columnTitle.sortOrder" />
                <display:column titleKey="columnTitle.default">
                    <c:choose>
                        <c:when test="${returnReasonType.isDefault}">
                            Default
                        </c:when>
                        <c:otherwise>
                            <c:url var="setDefaultUrl" value="/action/ReturnPolicy/ReturnReasonType/SetDefault">
                                <c:param name="ReturnKindName" value="${returnReasonType.returnReason.returnKind.returnKindName}" />
                                <c:param name="ReturnReasonName" value="${returnReasonType.returnReason.returnReasonName}" />
                                <c:param name="ReturnTypeName" value="${returnReasonType.returnType.returnTypeName}" />
                            </c:url>
                            <a href="${setDefaultUrl}">Set Default</a>
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column>
                    <c:url var="editUrl" value="/action/ReturnPolicy/ReturnReasonType/Edit">
                        <c:param name="ReturnKindName" value="${returnReasonType.returnReason.returnKind.returnKindName}" />
                        <c:param name="ReturnReasonName" value="${returnReasonType.returnReason.returnReasonName}" />
                        <c:param name="ReturnTypeName" value="${returnReasonType.returnType.returnTypeName}" />
                    </c:url>
                    <a href="${editUrl}">Edit</a>
                    <c:url var="deleteUrl" value="/action/ReturnPolicy/ReturnReasonType/Delete">
                        <c:param name="ReturnKindName" value="${returnReasonType.returnReason.returnKind.returnKindName}" />
                        <c:param name="ReturnReasonName" value="${returnReasonType.returnReason.returnReasonName}" />
                        <c:param name="ReturnTypeName" value="${returnReasonType.returnType.returnTypeName}" />
                    </c:url>
                    <a href="${deleteUrl}">Delete</a>
                </display:column>
            </display:table>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
