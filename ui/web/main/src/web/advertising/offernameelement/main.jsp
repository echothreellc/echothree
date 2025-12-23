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
        <title>Offer Name Elements</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Advertising/Main" />">Advertising</a> &gt;&gt;
                Offer Name Elements
            </h2>
        </div>
        <div id="Content">
            <p><a href="<c:url value="/action/Advertising/OfferNameElement/Add" />">Add Offer Name Element.</a></p>
            <et:checkSecurityRoles securityRoles="Event.List" />
            <display:table name="offerNameElements" id="offerNameElement" class="displaytag">
                <display:column property="offerNameElementName" titleKey="columnTitle.name" />
                <display:column titleKey="columnTitle.description">
                    <c:out value="${offerNameElement.description}" />
                </display:column>
                <display:column property="offset" titleKey="columnTitle.offset" />
                <display:column property="length" titleKey="columnTitle.length" />
                <display:column property="validationPattern" titleKey="columnTitle.validationPattern" />
                <display:column>
                    <c:url var="editUrl" value="/action/Advertising/OfferNameElement/Edit">
                        <c:param name="OriginalOfferNameElementName" value="${offerNameElement.offerNameElementName}" />
                    </c:url>
                    <a href="${editUrl}">Edit</a>
                    <c:url var="descriptionsUrl" value="/action/Advertising/OfferNameElement/Description">
                        <c:param name="OfferNameElementName" value="${offerNameElement.offerNameElementName}" />
                    </c:url>
                    <a href="${descriptionsUrl}">Descriptions</a>
                    <c:url var="deleteUrl" value="/action/Advertising/OfferNameElement/Delete">
                        <c:param name="OfferNameElementName" value="${offerNameElement.offerNameElementName}" />
                    </c:url>
                    <a href="${deleteUrl}">Delete</a>
                </display:column>
                <et:hasSecurityRole securityRole="Event.List">
                    <display:column>
                        <c:url var="eventsUrl" value="/action/Core/Event/Main">
                            <c:param name="EntityRef" value="${offerNameElement.entityInstance.entityRef}" />
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
