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
        <title><fmt:message key="pageTitle.chainActionSetDescriptions" /></title>
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
                    <c:param name="ChainKindName" value="${chainActionSetDescription.chainActionSet.chain.chainType.chainKind.chainKindName}" />
                </c:url>
                <a href="${chainTypesUrl}"><fmt:message key="navigation.chainTypes" /></a> &gt;&gt;
                <c:url var="chainsUrl" value="/action/Chain/Chain/Main">
                    <c:param name="ChainKindName" value="${chainActionSetDescription.chainActionSet.chain.chainType.chainKind.chainKindName}" />
                    <c:param name="ChainTypeName" value="${chainActionSetDescription.chainActionSet.chain.chainType.chainTypeName}" />
                </c:url>
                <a href="${chainsUrl}"><fmt:message key="navigation.chains" /></a> &gt;&gt;
                <c:url var="chainActionSetsUrl" value="/action/Chain/ChainActionSet/Main">
                    <c:param name="ChainKindName" value="${chainActionSetDescription.chainActionSet.chain.chainType.chainKind.chainKindName}" />
                    <c:param name="ChainTypeName" value="${chainActionSetDescription.chainActionSet.chain.chainType.chainTypeName}" />
                    <c:param name="ChainName" value="${chainActionSetDescription.chainActionSet.chain.chainName}" />
                </c:url>
                <a href="${chainActionSetsUrl}"><fmt:message key="navigation.chainActionSets" /></a> &gt;&gt;
                <c:url var="descriptionsUrl" value="/action/Chain/ChainActionSet/Description">
                    <c:param name="ChainKindName" value="${chainActionSetDescription.chainActionSet.chain.chainType.chainKind.chainKindName}" />
                    <c:param name="ChainTypeName" value="${chainActionSetDescription.chainActionSet.chain.chainType.chainTypeName}" />
                    <c:param name="ChainName" value="${chainActionSetDescription.chainActionSet.chain.chainName}" />
                    <c:param name="ChainActionSetName" value="${chainActionSetDescription.chainActionSet.chainActionSetName}" />
                </c:url>
                <a href="${descriptionsUrl}"><fmt:message key="navigation.chainActionSetDescriptions" /></a> &gt;&gt;
                Delete
            </h2>
        </div>
        <div id="Content">
            <p>You are about to delete the <c:out value="${chainActionSetDescription.language.description}" />
            <c:out value="${fn:toLowerCase(partyChainActionSet.chainActionSet.description)}" /> for &quot;<c:out value="${chainActionSetDescription.chainActionSet.description}" />&quot;
            (<c:out value="${chainActionSetDescription.chainActionSet.chainActionSetName}" />).</p>
            <et:executionErrors id="errorMessage">
                <p class="executionErrors"><c:out value="${errorMessage}" /></p><br />
            </et:executionErrors>
            <html:form action="/Chain/ChainActionSet/DescriptionDelete" method="POST">
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
                <html:hidden property="chainKindName" />
                <html:hidden property="chainTypeName" />
                <html:hidden property="chainName" />
                <html:hidden property="chainActionSetName" />
                <html:hidden property="languageIsoName" />
            </html:form>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>