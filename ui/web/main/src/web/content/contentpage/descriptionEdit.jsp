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
        <title>Page Descriptions</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Content/Main" />">Content</a> &gt;&gt;
                <a href="<c:url value="/action/Content/ContentCollection/Main" />">Collections</a> &gt;&gt;
                <c:url var="contentSectionsUrl" value="/action/Content/ContentSection/Main">
                    <c:param name="ContentCollectionName" value="${contentCollectionName}" />
                    <c:param name="ParentContentSectionName" value="${parentContentSectionName}" />
                </c:url>
                <a href="${contentSectionsUrl}">Sections</a> &gt;&gt;
                <c:url var="contentPagesUrl" value="/action/Content/ContentPage/Main">
                    <c:param name="ContentCollectionName" value="${contentCollectionName}" />
                    <c:param name="ParentContentSectionName" value="${parentContentSectionName}" />
                    <c:param name="ContentSectionName" value="${contentSectionName}" />
                </c:url>
                <a href="${contentPagesUrl}">Pages</a> &gt;&gt;
                <c:url var="contentPageDescriptionsUrl" value="/action/Content/ContentPage/Description">
                    <c:param name="ContentCollectionName" value="${contentCollectionName}" />
                    <c:param name="ParentContentSectionName" value="${parentContentSectionName}" />
                    <c:param name="ContentSectionName" value="${contentSectionName}" />
                    <c:param name="ContentPageName" value="${contentPageName}" />
                </c:url>
                <a href="${contentPageDescriptionsUrl}">Descriptions</a> &gt;&gt;
                Edit
            </h2>
        </div>
        <div id="Content">
            <et:executionErrors id="errorMessage">
                <p class="executionErrors"><c:out value="${errorMessage}" /></p><br />
            </et:executionErrors>
            <html:form action="/Content/ContentPage/DescriptionEdit" method="POST" focus="description">
                <table>
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
                        <td>
                            <html:hidden property="contentCollectionName" />
                            <html:hidden property="contentSectionName" />
                            <html:hidden property="contentPageName" />
                            <html:hidden property="languageIsoName" />
                            <html:hidden property="parentContentSectionName" />
                        </td>
                        <td><html:submit onclick="onSubmitDisable(this);" />&nbsp;<html:reset /><html:hidden property="submitButton" /></td>
                    </tr>
                </table>
            </html:form>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>