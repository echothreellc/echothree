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
        <title>Instances</title>
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
                    <c:param name="ChainKindName" value="${chain.chainType.chainKind.chainKindName}" />
                </c:url>
                <a href="${chainTypesUrl}">Types</a> &gt;&gt;
                <c:url var="chainTypesUrl" value="/action/Chain/Chain/Main">
                    <c:param name="ChainKindName" value="${chain.chainType.chainKind.chainKindName}" />
                    <c:param name="ChainTypeName" value="${chain.chainType.chainTypeName}" />
                </c:url>
                <a href="${chainTypesUrl}">Chains</a> &gt;&gt;
                Instances
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="Event.List" />
            <display:table name="chainInstances" id="chainInstance" class="displaytag">
                <display:column titleKey="columnTitle.name">
                    <c:url var="reviewUrl" value="/action/Chain/ChainInstance/Review">
                        <c:param name="ChainInstanceName" value="${chainInstance.chainInstanceName}" />
                    </c:url>
                    <a href="${reviewUrl}"><c:out value="${chainInstance.chainInstanceName}" /></a>
                </display:column>
                <display:column titleKey="columnTitle.nextChainActionSet">
                    <c:if test="${chainInstance.chainInstanceStatus.nextChainActionSet != null}">
                        <c:url var="nextChainActionSetUrl" value="/action/Chain/ChainActionSet/Review">
                            <c:param name="ChainKindName" value="${chainInstance.chainInstanceStatus.nextChainActionSet.chain.chainType.chainKind.chainKindName}" />
                            <c:param name="ChainTypeName" value="${chainInstance.chainInstanceStatus.nextChainActionSet.chain.chainType.chainTypeName}" />
                            <c:param name="ChainName" value="${chainInstance.chainInstanceStatus.nextChainActionSet.chain.chainName}" />
                            <c:param name="ChainActionSetName" value="${chainInstance.chainInstanceStatus.nextChainActionSet.chainActionSetName}" />
                        </c:url>
                        <a href="${nextChainActionSetUrl}"><c:out value="${chainInstance.chainInstanceStatus.nextChainActionSet.chainActionSetName}" /></a>
                    </c:if>
                </display:column>
                <display:column titleKey="columnTitle.nextChainActionSetTime">
                    <c:out value="${chainInstance.chainInstanceStatus.nextChainActionSetTime}" />
                </display:column>
                <et:hasSecurityRole securityRole="Event.List">
                    <display:column>
                        <c:url var="eventsUrl" value="/action/Core/Event/Main">
                            <c:param name="EntityRef" value="${chainInstance.entityInstance.entityRef}" />
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
