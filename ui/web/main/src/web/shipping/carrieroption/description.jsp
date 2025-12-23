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
        <title>Carrier Option Descriptions</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Shipping/Main" />">Shipping</a> &gt;&gt;
                <a href="<c:url value="/action/Shipping/Carrier/Main" />">Carriers</a> &gt;&gt;
                <c:url var="carrierOptionsUrl" value="/action/Shipping/CarrierOption/Main">
                    <c:param name="CarrierName" value="${carrierOption.carrier.carrierName}" />
                </c:url>
                <a href="${carrierOptionsUrl}">Carrier Options</a> &gt;&gt;
                Descriptions
            </h2>
        </div>
        <div id="Content">
            <c:url var="addUrl" value="/action/Shipping/CarrierOption/DescriptionAdd">
                <c:param name="CarrierName" value="${carrierOption.carrier.carrierName}" />
                <c:param name="CarrierOptionName" value="${carrierOption.carrierOptionName}" />
            </c:url>
            <p><a href="${addUrl}">Add Description.</a></p>
            <display:table name="carrierOptionDescriptions" id="carrierOptionDescription" class="displaytag">
                <display:column titleKey="columnTitle.language">
                    <c:out value="${carrierOptionDescription.language.description}" />
                </display:column>
                <display:column titleKey="columnTitle.description">
                    <c:out value="${carrierOptionDescription.description}" />
                </display:column>
                <display:column>
                    <c:url var="editUrl" value="/action/Shipping/CarrierOption/DescriptionEdit">
                        <c:param name="CarrierName" value="${carrierOptionDescription.carrierOption.carrier.carrierName}" />
                        <c:param name="CarrierOptionName" value="${carrierOptionDescription.carrierOption.carrierOptionName}" />
                        <c:param name="LanguageIsoName" value="${carrierOptionDescription.language.languageIsoName}" />
                    </c:url>
                    <a href="${editUrl}">Edit</a>
                    <c:url var="deleteUrl" value="/action/Shipping/CarrierOption/DescriptionDelete">
                        <c:param name="CarrierName" value="${carrierOptionDescription.carrierOption.carrier.carrierName}" />
                        <c:param name="CarrierOptionName" value="${carrierOptionDescription.carrierOption.carrierOptionName}" />
                        <c:param name="LanguageIsoName" value="${carrierOptionDescription.language.languageIsoName}" />
                    </c:url>
                    <a href="${deleteUrl}">Delete</a>
                </display:column>
            </display:table>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
