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
        <title><fmt:message key="pageTitle.chains" /></title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Chain/Main" />"><fmt:message key="navigation.chains" /></a> &gt;&gt;
                <a href="<c:url value="/action/Chain/ChainKind/Main" />"><fmt:message key="navigation.chainKinds" /></a> &gt;&gt;
                <c:url var="chainTypesUrl" value="/action/Chain/ChainType/Main">
                    <c:param name="ChainKindName" value="${chainType.chainKind.chainKindName}" />
                </c:url>
                <a href="${chainTypesUrl}"><fmt:message key="navigation.chainTypes" /></a> &gt;&gt;
                <fmt:message key="navigation.chains" />
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="Event.List:Chain.Create:Chain.Edit:Chain.Delete:Chain.Review:Chain.Description:Sequence.Review:ChainActionSet.List" />
            <et:hasSecurityRole securityRoles="ChainActionSet.List">
                <c:set var="linksInFirstRow" value="true" />
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRoles="Chain.Edit:Chain.Description:Chain.Delete">
                <c:set var="linksInSecondRow" value="true" />
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRole="Chain.Create">
                <c:url var="addUrl" value="/action/Chain/Chain/Add">
                    <c:param name="ChainKindName" value="${chainType.chainKind.chainKindName}" />
                    <c:param name="ChainTypeName" value="${chainType.chainTypeName}" />
                </c:url>
                <p><a href="${addUrl}">Add Chain.</a></p>
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRole="Chain.Review" var="includeChainReviewUrl" />
            <et:hasSecurityRole securityRole="Sequence.Review" var="includeSequenceReviewUrl" />
            <display:table name="chains" id="chain" class="displaytag">
                <display:column titleKey="columnTitle.name">
                    <c:choose>
                        <c:when test="${includeChainReviewUrl}">
                            <c:url var="reviewUrl" value="/action/Chain/Chain/Review">
                                <c:param name="ChainKindName" value="${chain.chainType.chainKind.chainKindName}" />
                                <c:param name="ChainTypeName" value="${chain.chainType.chainTypeName}" />
                                <c:param name="ChainName" value="${chain.chainName}" />
                            </c:url>
                            <a href="${reviewUrl}"><c:out value="${chain.chainName}" /></a>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${chain.chainName}" />
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column titleKey="columnTitle.description">
                    <c:out value="${chain.description}" />
                </display:column>
                <display:column titleKey="columnTitle.chainInstanceSequence">
                    <c:if test='${chain.chainInstanceSequence != null}'>
                        <c:choose>
                            <c:when test="${includeSequenceReviewUrl}">
                                <c:url var="chainInstanceSequenceUrl" value="/action/Sequence/Sequence/Review">
                                    <c:param name="SequenceTypeName" value="${chain.chainInstanceSequence.sequenceType.sequenceTypeName}" />
                                    <c:param name="SequenceName" value="${chain.chainInstanceSequence.sequenceName}" />
                                </c:url>
                                <a href="${chainInstanceSequenceUrl}">${chain.chainInstanceSequence.description}</a>
                            </c:when>
                            <c:otherwise>
                                <c:out value="${chain.chainInstanceSequence.description}" />
                            </c:otherwise>
                        </c:choose>
                    </c:if>
                </display:column>
                <display:column property="sortOrder" titleKey="columnTitle.sortOrder" />
                <display:column titleKey="columnTitle.default">
                    <c:choose>
                        <c:when test="${chain.isDefault}">
                            Default
                        </c:when>
                        <c:otherwise>
                            <et:hasSecurityRole securityRole="Chain.Edit">
                                <c:url var="setDefaultUrl" value="/action/Chain/Chain/SetDefault">
                                    <c:param name="ChainKindName" value="${chain.chainType.chainKind.chainKindName}" />
                                    <c:param name="ChainTypeName" value="${chain.chainType.chainTypeName}" />
                                    <c:param name="ChainName" value="${chain.chainName}" />
                                </c:url>
                                <a href="${setDefaultUrl}">Set Default</a>
                            </et:hasSecurityRole>
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <c:if test="${linksInFirstRow || linksInSecondRow}">
                    <display:column>
                        <et:hasSecurityRole securityRole="ChainActionSet.List">
                            <c:url var="chainActionsUrl" value="/action/Chain/ChainActionSet/Main">
                                <c:param name="ChainKindName" value="${chain.chainType.chainKind.chainKindName}" />
                                <c:param name="ChainTypeName" value="${chain.chainType.chainTypeName}" />
                                <c:param name="ChainName" value="${chain.chainName}" />
                            </c:url>
                        </et:hasSecurityRole>
                        <a href="${chainActionsUrl}">Chain Action Sets</a>
                        <c:if test="${linksInFirstRow && linksInSecondRow}">
                            <br />
                        </c:if>
                        <et:hasSecurityRole securityRole="Chain.Edit">
                            <c:url var="editUrl" value="/action/Chain/Chain/Edit">
                                <c:param name="ChainKindName" value="${chain.chainType.chainKind.chainKindName}" />
                                <c:param name="ChainTypeName" value="${chain.chainType.chainTypeName}" />
                                <c:param name="OriginalChainName" value="${chain.chainName}" />
                            </c:url>
                            <a href="${editUrl}">Edit</a>
                        </et:hasSecurityRole>
                        <et:hasSecurityRole securityRole="Chain.Description">
                            <c:url var="descriptionsUrl" value="/action/Chain/Chain/Description">
                                <c:param name="ChainKindName" value="${chain.chainType.chainKind.chainKindName}" />
                                <c:param name="ChainTypeName" value="${chain.chainType.chainTypeName}" />
                                <c:param name="ChainName" value="${chain.chainName}" />
                            </c:url>
                            <a href="${descriptionsUrl}">Descriptions</a>
                        </et:hasSecurityRole>
                        <et:hasSecurityRole securityRole="Chain.Delete">
                            <c:url var="deleteUrl" value="/action/Chain/Chain/Delete">
                                <c:param name="ChainKindName" value="${chain.chainType.chainKind.chainKindName}" />
                                <c:param name="ChainTypeName" value="${chain.chainType.chainTypeName}" />
                                <c:param name="ChainName" value="${chain.chainName}" />
                            </c:url>
                            <a href="${deleteUrl}">Delete</a>
                        </et:hasSecurityRole>
                    </display:column>
                </c:if>
                <et:hasSecurityRole securityRole="Event.List">
                    <display:column>
                        <c:if test='${chain.entityInstance.entityTime.createdTime != null}'>
                            <c:url var="eventsUrl" value="/action/Core/Event/Main">
                                <c:param name="EntityRef" value="${chain.entityInstance.entityRef}" />
                            </c:url>
                            <a href="${eventsUrl}">Events</a>
                        </c:if>
                    </display:column>
                </et:hasSecurityRole>
            </display:table>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
