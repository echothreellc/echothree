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
        <title><fmt:message key="pageTitle.contentPageAreas" /></title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Content/Main" />"><fmt:message key="navigation.content" /></a> &gt;&gt;
                <a href="<c:url value="/action/Content/ContentCollection/Main" />"><fmt:message key="navigation.contentCollections" /></a> &gt;&gt;
                <c:url var="contentSectionsUrl" value="/action/Content/ContentSection/Main">
                    <c:param name="ContentCollectionName" value="${contentPageArea.contentPage.contentSection.contentCollection.contentCollectionName}" />
                    <c:param name="ParentContentSectionName" value="${contentPageArea.contentPage.contentSection.parentContentSection.contentSectionName}" />
                </c:url>
                <a href="${contentSectionsUrl}"><fmt:message key="navigation.contentSections" /></a> &gt;&gt;
                <c:url var="contentPagesUrl" value="/action/Content/ContentPage/Main">
                    <c:param name="ContentCollectionName" value="${contentPageArea.contentPage.contentSection.contentCollection.contentCollectionName}" />
                    <c:param name="ParentContentSectionName" value="${contentPageArea.contentPage.contentSection.parentContentSection.contentSectionName}" />
                    <c:param name="ContentSectionName" value="${contentPageArea.contentPage.contentSection.contentSectionName}" />
                </c:url>
                <a href="${contentPagesUrl}"><fmt:message key="navigation.contentPages" /></a> &gt;&gt;
                <c:url var="contentPageAreasUrl" value="/action/Content/ContentPageArea/Main">
                    <c:param name="ContentCollectionName" value="${contentPageArea.contentPage.contentSection.contentCollection.contentCollectionName}" />
                    <c:param name="ParentContentSectionName" value="${contentPageArea.contentPage.contentSection.parentContentSection.contentSectionName}" />
                    <c:param name="ContentSectionName" value="${contentPageArea.contentPage.contentSection.contentSectionName}" />
                    <c:param name="ContentPageName" value="${contentPageArea.contentPage.contentPageName}" />
                </c:url>
                <a href="${contentPageAreasUrl}"><fmt:message key="navigation.contentPageAreas" /></a> &gt;&gt;
                Delete
            </h2>
        </div>
        <div id="Content">
            <c:choose>
                <c:when test="${commandResult.hasErrors}">
                    <et:executionErrors id="errorMessage">
                        <p class="executionErrors"><c:out value="${errorMessage}" /></p><br />
                    </et:executionErrors>
                </c:when>
                <c:otherwise>
                    <p>You are about to delete the <c:out value="${contentPageArea.language.description}" />
                    <c:out value="${fn:toLowerCase(partyEntityType.entityType.description)}" /> for <c:out value="${fn:toLowerCase(contentPageArea.contentPageLayoutArea.description)}" />.</p>
                    <html:form action="/Content/ContentPageArea/Delete" method="POST">
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
                        <html:hidden property="contentCollectionName" />
                        <html:hidden property="parentContentSectionName" />
                        <html:hidden property="contentSectionName" />
                        <html:hidden property="contentPageName" />
                        <html:hidden property="sortOrder" />
                        <html:hidden property="languageIsoName" />
                    </html:form>
                </c:otherwise>
            </c:choose>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
