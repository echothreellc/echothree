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
        <title>Review (<c:out value="${customerName}" />)</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
        <%@ include file="customerProfileTinyMce.jsp" %>
    </head>
    <body onLoad="pageLoaded()">
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Customer/Main" />">Customers</a> &gt;&gt;
                <a href="<c:url value="/action/Customer/Customer/Main" />">Search</a> &gt;&gt;
                <et:countCustomerResults searchTypeName="ORDER_ENTRY" countVar="customerResultsCount" commandResultVar="countCustomerResultsCommandResult" logErrors="false" />
                <c:if test="${customerResultsCount > 0}">
                    <a href="<c:url value="/action/Customer/Customer/Result" />"><fmt:message key="navigation.results" /></a> &gt;&gt;
                </c:if>
                <c:url var="reviewUrl" value="/action/Customer/Customer/Review">
                    <c:param name="CustomerName" value="${customerName}" />
                </c:url>
                <a href="${reviewUrl}">Review (<c:out value="${customerName}" />)</a> &gt;&gt;
                Add Profile
            </h2>
        </div>
        <div id="Content">
            <et:executionErrors id="errorMessage">
                <p class="executionErrors"><c:out value="${errorMessage}" /></p><br />
            </et:executionErrors>
            <html:form action="/Customer/Customer/CustomerProfileAdd" method="POST" focus="nickname">
                <table>
                    <tr>
                        <td align=right><fmt:message key="label.nickname" />:</td>
                        <td>
                            <html:text property="nickname" size="40" maxlength="40" />
                            <et:validationErrors id="errorMessage" property="Nickname">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.icon" />:</td>
                        <td>
                            <html:select property="iconChoice">
                                <html:optionsCollection property="iconChoices" />
                            </html:select>
                            <et:validationErrors id="errorMessage" property="IconName">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.pronunciation" />:</td>
                        <td>
                            <html:text property="pronunciation" size="60" maxlength="200" />
                            <et:validationErrors id="errorMessage" property="Pronunciation">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.gender" />:</td>
                        <td>
                            <html:select property="genderChoice">
                                <html:optionsCollection property="genderChoices" />
                            </html:select>
                            <et:validationErrors id="errorMessage" property="GenderName">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.pronouns" />:</td>
                        <td>
                            <html:text property="pronouns" size="50" maxlength="50" />
                            <et:validationErrors id="errorMessage" property="Pronouns">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.birthday" />:</td>
                        <td>
                            <html:text property="birthday" size="10" maxlength="10" />
                            <et:validationErrors id="errorMessage" property="Birthday">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.birthdayFormat" />:</td>
                        <td>
                            <html:select property="birthdayFormatChoice">
                                <html:optionsCollection property="birthdayFormatChoices" />
                            </html:select> (*)
                            <et:validationErrors id="errorMessage" property="BirthdayFormatName">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.occupation" />:</td>
                        <td>
                            <html:text property="occupation" size="60" maxlength="200" />
                            <et:validationErrors id="errorMessage" property="Occupation">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.hobbies" />:</td>
                        <td>
                            <html:text property="hobbies" size="60" maxlength="200" />
                            <et:validationErrors id="errorMessage" property="Hobbies">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.location" />:</td>
                        <td>
                            <html:text property="location" size="60" maxlength="60" />
                            <et:validationErrors id="errorMessage" property="Location">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.bioMimeTypeChoice" />:</td>
                        <td>
                            <html:select onchange="bioMimeTypeChoiceChange()" property="bioMimeTypeChoice" styleId="bioMimeTypeChoices">
                                <html:optionsCollection property="bioMimeTypeChoices" />
                            </html:select>
                            <et:validationErrors id="errorMessage" property="BioMimeTypeName">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.bio" />:</td>
                        <td>
                            <html:textarea property="bio" cols="${customerProfileBioEditorUse.preferredWidth}" rows="${customerProfileBioEditorUse.preferredHeight}" styleId="bioTA" />
                            <et:validationErrors id="errorMessage" property="Bio">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.signatureMimeTypeChoice" />:</td>
                        <td>
                            <html:select onchange="signatureMimeTypeChoiceChange()" property="signatureMimeTypeChoice" styleId="signatureMimeTypeChoices">
                                <html:optionsCollection property="signatureMimeTypeChoices" />
                            </html:select>
                            <et:validationErrors id="errorMessage" property="SignatureMimeTypeName">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.signature" />:</td>
                        <td>
                            <html:textarea property="signature" cols="${customerProfileSignatureEditorUse.preferredWidth}" rows="${customerProfileSignatureEditorUse.preferredHeight}" styleId="signatureTA" />
                            <et:validationErrors id="errorMessage" property="Signature">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <html:hidden property="partyName" />
                            <html:hidden property="customerName" />
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