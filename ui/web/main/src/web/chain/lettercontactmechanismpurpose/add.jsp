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
        <title>Contact Mechanism Purposes</title>
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
                    <c:param name="ChainKindName" value="${chainKindName}" />
                </c:url>
                <a href="${chainTypesUrl}">Types</a> &gt;&gt;
                <c:url var="lettersUrl" value="/action/Chain/Letter/Main">
                    <c:param name="ChainKindName" value="${chainKindName}" />
                    <c:param name="ChainTypeName" value="${chainTypeName}" />
                </c:url>
                <a href="${lettersUrl}">Letters</a> &gt;&gt;
                <c:url var="contactMechanismPurposesUrl" value="/action/Chain/LetterContactMechanismPurpose/Main">
                    <c:param name="ChainKindName" value="${chainKindName}" />
                    <c:param name="ChainTypeName" value="${chainTypeName}" />
                    <c:param name="LetterName" value="${letterName}" />
                </c:url>
                <a href="${contactMechanismPurposesUrl}">Contact Mechanism Purposes</a> &gt;&gt;
                Add
            </h2>
        </div>
        <div id="Content">
            <et:executionErrors id="errorMessage">
                <p class="executionErrors"><c:out value="${errorMessage}" /></p><br />
            </et:executionErrors>
            <html:form action="/Chain/LetterContactMechanismPurpose/Add" method="POST" focus="priority">
                <table>
                    <tr>
                        <td align=right><fmt:message key="label.priority" />:</td>
                        <td>
                            <html:text property="priority" size="12" maxlength="12" /> (*)
                            <et:validationErrors id="errorMessage" property="Priority">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.contactMechanismPurpose" />:</td>
                        <td>
                            <html:select property="contactMechanismPurposeChoice">
                                <html:optionsCollection property="contactMechanismPurposeChoices" />
                            </html:select>
                            <et:validationErrors id="errorMessage" property="ContactMechanismPurposeChoiceName">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <html:hidden property="chainKindName" />
                            <html:hidden property="chainTypeName" />
                            <html:hidden property="letterName" />
                        </td>
                        <td><html:submit onclick="onSubmitDisable(this);" /><input type="hidden" name="submitButton" /></td>
                    </tr>
                </table>
            </html:form>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>