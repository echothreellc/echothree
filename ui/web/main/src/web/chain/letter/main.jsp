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
        <title>Letters</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Chain/Main" />">Chains</a> &gt;&gt;
                <a href="<c:url value="/action/Chain/ChainKind/Main" />">Kinds</a> &gt;&gt;
                <c:url var="chainTypesUrl" value="/action/Chain/ChainType/Main">
                    <c:param name="ChainKindName" value="${chainType.chainKind.chainKindName}" />
                </c:url>
                <a href="${chainTypesUrl}">Types</a> &gt;&gt;
                Letters
            </h2>
        </div>
        <div id="Content">
            <c:url var="addUrl" value="/action/Chain/Letter/Add">
                <c:param name="ChainKindName" value="${chainType.chainKind.chainKindName}" />
                <c:param name="ChainTypeName" value="${chainType.chainTypeName}" />
            </c:url>
            <p><a href="${addUrl}">Add Letter.</a></p>
            <et:checkSecurityRoles securityRoles="Event.List" />
            <display:table name="letters" id="letter" class="displaytag">
                <display:column titleKey="columnTitle.name">
                    <c:url var="reviewUrl" value="/action/Chain/Letter/Review">
                        <c:param name="ChainKindName" value="${letter.chainType.chainKind.chainKindName}" />
                        <c:param name="ChainTypeName" value="${letter.chainType.chainTypeName}" />
                        <c:param name="LetterName" value="${letter.letterName}" />
                    </c:url>
                    <a href="${reviewUrl}"><c:out value="${letter.letterName}" /></a>
                </display:column>
                <display:column titleKey="columnTitle.description">
                    <c:out value="${letter.description}" />
                </display:column>
                <display:column property="sortOrder" titleKey="columnTitle.sortOrder" />
                <display:column titleKey="columnTitle.default">
                    <c:choose>
                        <c:when test="${letter.isDefault}">
                            Default
                        </c:when>
                        <c:otherwise>
                            <c:url var="setDefaultUrl" value="/action/Chain/Letter/SetDefault">
                                <c:param name="ChainKindName" value="${letter.chainType.chainKind.chainKindName}" />
                                <c:param name="ChainTypeName" value="${letter.chainType.chainTypeName}" />
                                <c:param name="LetterName" value="${letter.letterName}" />
                            </c:url>
                            <a href="${setDefaultUrl}">Set Default</a>
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column>
                    <c:url var="letterContactMechanismPurposeUrl" value="/action/Chain/LetterContactMechanismPurpose/Main">
                        <c:param name="ChainKindName" value="${letter.chainType.chainKind.chainKindName}" />
                        <c:param name="ChainTypeName" value="${letter.chainType.chainTypeName}" />
                        <c:param name="LetterName" value="${letter.letterName}" />
                    </c:url>
                    <a href="${letterContactMechanismPurposeUrl}">Contact Mechanism Purposes</a><br />
                    <c:url var="editUrl" value="/action/Chain/Letter/Edit">
                        <c:param name="ChainKindName" value="${letter.chainType.chainKind.chainKindName}" />
                        <c:param name="ChainTypeName" value="${letter.chainType.chainTypeName}" />
                        <c:param name="OriginalLetterName" value="${letter.letterName}" />
                    </c:url>
                    <a href="${editUrl}">Edit</a>
                    <c:url var="letterDescriptionsUrl" value="/action/Chain/Letter/Description">
                        <c:param name="ChainKindName" value="${letter.chainType.chainKind.chainKindName}" />
                        <c:param name="ChainTypeName" value="${letter.chainType.chainTypeName}" />
                        <c:param name="LetterName" value="${letter.letterName}" />
                    </c:url>
                    <a href="${letterDescriptionsUrl}">Descriptions</a>
                    <c:url var="deleteUrl" value="/action/Chain/Letter/Delete">
                        <c:param name="ChainKindName" value="${letter.chainType.chainKind.chainKindName}" />
                        <c:param name="ChainTypeName" value="${letter.chainType.chainTypeName}" />
                        <c:param name="LetterName" value="${letter.letterName}" />
                    </c:url>
                    <a href="${deleteUrl}">Delete</a>
                </display:column>
                <et:hasSecurityRole securityRole="Event.List">
                    <display:column>
                        <c:url var="eventsUrl" value="/action/Core/Event/Main">
                            <c:param name="EntityRef" value="${letter.entityInstance.entityRef}" />
                        </c:url>
                        <a href="${eventsUrl}">Events</a>
                    </display:column>
                </et:hasSecurityRole>
            </display:table>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
