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
        <title>Training Class Pages</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
        <%@ include file="tinyMce.jsp" %>
    </head>
    <body onLoad="pageLoaded()">
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/HumanResources/Main" />"><fmt:message key="navigation.humanResources" /></a> &gt;&gt;
                <a href="<c:url value="/action/HumanResources/TrainingClass/Main" />">Training Classes</a> &gt;&gt;
                <c:url var="trainingClassSectionsUrl" value="/action/HumanResources/TrainingClassSection/Main">
                    <c:param name="TrainingClassName" value="${trainingClassPage.trainingClassSection.trainingClass.trainingClassName}" />
                </c:url>
                <a href="${trainingClassSectionsUrl}">Sections</a> &gt;&gt;
                <c:url var="trainingClassPagesUrl" value="/action/HumanResources/TrainingClassPage/Main">
                    <c:param name="TrainingClassName" value="${trainingClassPage.trainingClassSection.trainingClass.trainingClassName}" />
                    <c:param name="TrainingClassSectionName" value="${trainingClassPage.trainingClassSection.trainingClassSectionName}" />
                </c:url>
                <a href="${trainingClassPagesUrl}">Pages</a> &gt;&gt;
                <c:url var="translationsUrl" value="/action/HumanResources/TrainingClassPage/Translation">
                    <c:param name="TrainingClassName" value="${trainingClassPage.trainingClassSection.trainingClass.trainingClassName}" />
                    <c:param name="TrainingClassSectionName" value="${trainingClassPage.trainingClassSection.trainingClassSectionName}" />
                    <c:param name="TrainingClassPageName" value="${trainingClassPage.trainingClassPageName}" />
                </c:url>
                <a href="${translationsUrl}">Translations</a> &gt;&gt;
                Add
            </h2>
        </div>
        <div id="Content">
            <et:executionErrors id="errorMessage">
                <p class="executionErrors"><c:out value="${errorMessage}" /></p><br />
            </et:executionErrors>
            <html:form action="/HumanResources/TrainingClassPage/TranslationAdd" method="POST" focus="description">
                <table>
                    <tr>
                        <td align=right><fmt:message key="label.language" />:</td>
                        <td>
                            <html:select property="languageChoice">
                                <html:optionsCollection property="languageChoices" />
                            </html:select> (*)
                            <et:validationErrors id="errorMessage" property="LanguageIsoName">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.description" />:</td>
                        <td>
                            <html:text property="description" size="60" maxlength="132" /> (*)
                            <et:validationErrors id="errorMessage" property="Description">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.pageMimeTypeChoice" />:</td>
                        <td>
                            <html:select onchange="pageMimeTypeChoiceChange()" property="pageMimeTypeChoice" styleId="pageMimeTypeChoices">
                                <html:optionsCollection property="pageMimeTypeChoices" />
                            </html:select> (*)
                            <et:validationErrors id="errorMessage" property="PageMimeTypeName">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.page" />:</td>
                        <td>
                            <html:textarea property="page" cols="${trainingClassPagePageEditorUse.preferredWidth}" rows="${trainingClassPagePageEditorUse.preferredHeight}" styleId="pageTA" /> (*)
                            <et:validationErrors id="errorMessage" property="Page">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <html:hidden property="trainingClassName" />
                            <html:hidden property="trainingClassSectionName" />
                            <html:hidden property="trainingClassPageName" />
                        </td>
                        <td><html:submit onclick="onSubmitDisable(this);" />&nbsp;<html:cancel onclick="onSubmitDisable(this);" /><html:hidden property="submitButton" /></td>
                    </tr>
                </table>
            </html:form>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>