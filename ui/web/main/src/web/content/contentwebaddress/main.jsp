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
        <title>Web Addresses</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Content/Main" />">Content</a> &gt;&gt;
                Web Addresses
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="Event.List" />
            <p><a href="<c:url value="/action/Content/ContentWebAddress/Add" />">Add Web Address.</a></p>
            <display:table name="contentWebAddresses" id="contentWebAddress" class="displaytag">
                <display:column titleKey="columnTitle.name">
                    <c:url var="reviewUrl" value="/action/Content/ContentWebAddress/Review">
                        <c:param name="ContentWebAddressName" value="${contentWebAddress.contentWebAddressName}" />
                    </c:url>
                    <a href="${reviewUrl}"><c:out value="${contentWebAddress.contentWebAddressName}" /></a>
                </display:column>
                <display:column titleKey="columnTitle.description">
                    <c:out value="${contentWebAddress.description}" />
                </display:column>
                <display:column titleKey="columnTitle.contentCollection">
                    <c:url var="contentCollectionUrl" value="/action/Content/ContentCollection/Review">
                        <c:param name="ContentCollectionName" value="${contentWebAddress.contentCollection.contentCollectionName}" />
                    </c:url>
                    <a href="${contentCollectionUrl}"><c:out value="${contentWebAddress.contentCollection.description}" /></a>
                </display:column>
                <display:column>
                    <c:url var="editContentWebAddressUrl" value="/action/Content/ContentWebAddress/Edit">
                        <c:param name="OriginalContentWebAddressName" value="${contentWebAddress.contentWebAddressName}" />
                    </c:url>
                    <a href="${editContentWebAddressUrl}">Edit</a>
                    <c:url var="contentWebAddressDescriptionsUrl" value="/action/Content/ContentWebAddress/Description">
                        <c:param name="ContentWebAddressName" value="${contentWebAddress.contentWebAddressName}" />
                    </c:url>
                    <a href="${contentWebAddressDescriptionsUrl}">Descriptions</a>
                    <c:url var="deleteContentWebAddressUrl" value="/action/Content/ContentWebAddress/Delete">
                        <c:param name="ContentWebAddressName" value="${contentWebAddress.contentWebAddressName}" />
                    </c:url>
                    <a href="${deleteContentWebAddressUrl}">Delete</a>
                </display:column>
                <et:hasSecurityRole securityRole="Event.List">
                    <display:column>
                        <c:url var="eventsUrl" value="/action/Core/Event/Main">
                            <c:param name="EntityRef" value="${contentWebAddress.entityInstance.entityRef}" />
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
