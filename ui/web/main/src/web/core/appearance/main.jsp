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
        <title><fmt:message key="pageTitle.appearances" /></title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Core/Main" />"><fmt:message key="navigation.core" /></a> &gt;&gt;
                <fmt:message key="navigation.appearances" />
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="Event.List:Appearance.Create:Appearance.Edit:Appearance.Delete:Appearance.Review:Appearance.Description:Color.Review:FontStyle.Review:FontWeight.Review:Appearance.AppearanceTextDecoration:Appearance.AppearanceTextTransformation" />
            <et:hasSecurityRole securityRole="Appearance.Create">
                <p><a href="<c:url value="/action/Core/Appearance/Add" />">Add Appearance.</a></p>
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRole="Appearance.Review" var="includeReviewUrl" />
            <et:hasSecurityRole securityRole="Color.Review" var="includeColorReviewUrl" />
            <et:hasSecurityRole securityRole="FontStyle.Review" var="includeFontStyleReviewUrl" />
            <et:hasSecurityRole securityRole="FontWeight.Review" var="includeFontWeightReviewUrl" />
            <et:hasSecurityRole securityRoles="Appearance.AppearanceTextDecoration:Appearance.AppearanceTextTransformation">
                <c:set var="linksInFirstRow" value="true" />
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRoles="Appearance.Edit:Appearance.Description:Appearance.Delete">
                <c:set var="linksInSecondRow" value="true" />
            </et:hasSecurityRole>
            <display:table name="appearances" id="appearance" class="displaytag">
                <display:column titleKey="columnTitle.name">
                    <c:choose>
                        <c:when test="${includeReviewUrl}">
                            <c:url var="reviewUrl" value="/action/Core/Appearance/Review">
                                <c:param name="AppearanceName" value="${appearance.appearanceName}" />
                            </c:url>
                            <a href="${reviewUrl}"><c:out value="${appearance.appearanceName}" /></a>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${appearance.appearanceName}" />
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column titleKey="columnTitle.description">
                    <c:out value="${appearance.description}" />
                </display:column>
                <display:column titleKey="columnTitle.textColor">
                    <c:if test="${appearance.textColor != null}">
                        <c:choose>
                            <c:when test="${includeColorReviewUrl}">
                                <c:url var="reviewUrl" value="/action/Core/Color/Review">
                                    <c:param name="ColorName" value="${appearance.textColor.colorName}" />
                                </c:url>
                                <a href="${reviewUrl}"><c:out value="${appearance.textColor.description}" /></a>
                            </c:when>
                            <c:otherwise>
                                <c:out value="${appearance.textColor.description}" />
                            </c:otherwise>
                        </c:choose>
                    </c:if>
                </display:column>
                <display:column titleKey="columnTitle.backgroundColor">
                    <c:if test="${appearance.backgroundColor != null}">
                        <c:choose>
                            <c:when test="${includeColorReviewUrl}">
                                <c:url var="reviewUrl" value="/action/Core/Color/Review">
                                    <c:param name="ColorName" value="${appearance.backgroundColor.colorName}" />
                                </c:url>
                                <a href="${reviewUrl}"><c:out value="${appearance.backgroundColor.description}" /></a>
                            </c:when>
                            <c:otherwise>
                                <c:out value="${appearance.backgroundColor.description}" />
                            </c:otherwise>
                        </c:choose>
                    </c:if>
                </display:column>
                <display:column titleKey="columnTitle.fontStyle">
                    <c:if test="${appearance.fontStyle != null}">
                        <c:choose>
                            <c:when test="${includeFontStyleReviewUrl}">
                                <c:url var="reviewUrl" value="/action/Core/FontStyle/Review">
                                    <c:param name="FontStyleName" value="${appearance.fontStyle.fontStyleName}" />
                                </c:url>
                                <a href="${reviewUrl}"><c:out value="${appearance.fontStyle.description}" /></a>
                            </c:when>
                            <c:otherwise>
                                <c:out value="${appearance.fontStyle.description}" />
                            </c:otherwise>
                        </c:choose>
                    </c:if>
                </display:column>
                <display:column titleKey="columnTitle.fontWeight">
                    <c:if test="${appearance.fontWeight != null}">
                        <c:choose>
                            <c:when test="${includeFontWeightReviewUrl}">
                                <c:url var="reviewUrl" value="/action/Core/FontWeight/Review">
                                    <c:param name="FontWeightName" value="${appearance.fontWeight.fontWeightName}" />
                                </c:url>
                                <a href="${reviewUrl}"><c:out value="${appearance.fontWeight.description}" /></a>
                            </c:when>
                            <c:otherwise>
                                <c:out value="${appearance.fontWeight.description}" />
                            </c:otherwise>
                        </c:choose>
                    </c:if>
                </display:column>
                <display:column property="sortOrder" titleKey="columnTitle.sortOrder" />
                <display:column titleKey="columnTitle.default">
                    <c:choose>
                        <c:when test="${appearance.isDefault}">
                            Default
                        </c:when>
                        <c:otherwise>
                            <et:hasSecurityRole securityRole="Appearance.Edit">
                                <c:url var="setDefaultUrl" value="/action/Core/Appearance/SetDefault">
                                    <c:param name="AppearanceName" value="${appearance.appearanceName}" />
                                </c:url>
                                <a href="${setDefaultUrl}">Set Default</a>
                            </et:hasSecurityRole>
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <c:if test="${linksInFirstRow || linksInSecondRow}">
                    <display:column>
                        <et:hasSecurityRole securityRole="Appearance.AppearanceTextDecoration">
                            <c:url var="appearanceTextDecorationsUrl" value="/action/Core/Appearance/TextDecoration">
                                <c:param name="AppearanceName" value="${appearance.appearanceName}" />
                            </c:url>
                            <a href="${appearanceTextDecorationsUrl}">Text Decorations</a>
                        </et:hasSecurityRole>
                        <et:hasSecurityRole securityRole="Appearance.AppearanceTextTransformation">
                            <c:url var="appearanceTextTransformationsUrl" value="/action/Core/Appearance/TextTransformation">
                                <c:param name="AppearanceName" value="${appearance.appearanceName}" />
                            </c:url>
                            <a href="${appearanceTextTransformationsUrl}">Text Transformations</a>
                        </et:hasSecurityRole>
                        <c:if test="${linksInFirstRow && linksInSecondRow}">
                            <br />
                        </c:if>
                        <et:hasSecurityRole securityRole="Appearance.Edit">
                            <c:url var="editUrl" value="/action/Core/Appearance/Edit">
                                <c:param name="OriginalAppearanceName" value="${appearance.appearanceName}" />
                            </c:url>
                            <a href="${editUrl}">Edit</a>
                        </et:hasSecurityRole>
                        <et:hasSecurityRole securityRole="Appearance.Description">
                            <c:url var="descriptionsUrl" value="/action/Core/Appearance/Description">
                                <c:param name="AppearanceName" value="${appearance.appearanceName}" />
                            </c:url>
                            <a href="${descriptionsUrl}">Descriptions</a>
                        </et:hasSecurityRole>
                        <et:hasSecurityRole securityRole="Appearance.Delete">
                            <c:url var="deleteUrl" value="/action/Core/Appearance/Delete">
                                <c:param name="AppearanceName" value="${appearance.appearanceName}" />
                            </c:url>
                            <a href="${deleteUrl}">Delete</a>
                        </et:hasSecurityRole>
                    </display:column>
                </c:if>
                <et:hasSecurityRole securityRole="Event.List">
                    <display:column>
                        <c:url var="eventsUrl" value="/action/Core/Event/Main">
                            <c:param name="EntityRef" value="${appearance.entityInstance.entityRef}" />
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
