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
        <title><fmt:message key="pageTitle.geoCodeAliasTypeDescriptions" /></title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Configuration/Main" />">Configuration</a> &gt;&gt;
                <a href="<c:url value="/action/Configuration/GeoCodeType/Main" />">Geo Code Types</a> &gt;&gt;
                <c:url var="geoCodeAliasTypesUrl" value="/action/Configuration/GeoCodeAliasType/Main">
                    <c:param name="GeoCodeTypeName" value="${geoCodeAliasTypeDescription.geoCodeAliasType.geoCodeType.geoCodeTypeName}" />
                </c:url>
                <a href="${geoCodeAliasTypesUrl}">Geo Code Alias Types</a> &gt;&gt;
                <c:url var="descriptionsUrl" value="/action/Configuration/GeoCodeAliasType/Description">
                    <c:param name="GeoCodeTypeName" value="${geoCodeAliasTypeDescription.geoCodeAliasType.geoCodeType.geoCodeTypeName}" />
                    <c:param name="GeoCodeAliasTypeName" value="${geoCodeAliasTypeDescription.geoCodeAliasType.geoCodeAliasTypeName}" />
                </c:url>
                <a href="${descriptionsUrl}">Descriptions</a> &gt;&gt;
                Delete
            </h2>
        </div>
        <div id="Content">
            <p>You are about to delete the <c:out value="${geoCodeAliasTypeDescription.language.description}" />
            <c:out value="${fn:toLowerCase(partyEntityType.entityType.description)}" /> for &quot;<c:out value="${geoCodeAliasTypeDescription.geoCodeAliasType.description}" />&quot;
            (<c:out value="${geoCodeAliasTypeDescription.geoCodeAliasType.geoCodeAliasTypeName}" />).</p>
            <et:executionErrors id="errorMessage">
                <p class="executionErrors"><c:out value="${errorMessage}" /></p><br />
            </et:executionErrors>
            <html:form action="/Configuration/GeoCodeAliasType/DescriptionDelete" method="POST">
                <table>
                    <tr>
                        <td align=right><fmt:message key="label.confirmDelete" />:</td>
                        <td>
                            <html:checkbox property="confirmDelete" /> (*)
                            <et:validationErrors id="errorMessage" property="ConfirmDelete">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                </table>
                <html:submit value="Delete" onclick="onSubmitDisable(this);" />&nbsp;<html:cancel onclick="onSubmitDisable(this);" /><html:hidden property="submitButton" />
                <html:hidden property="geoCodeTypeName" />
                <html:hidden property="geoCodeAliasTypeName" />
                <html:hidden property="languageIsoName" />
            </html:form>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>