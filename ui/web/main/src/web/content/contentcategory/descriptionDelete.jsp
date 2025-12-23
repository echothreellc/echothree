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
        <title><fmt:message key="pageTitle.contentCategoryDescriptions" /></title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Content/Main" />"><fmt:message key="navigation.content" /></a> &gt;&gt;
                <a href="<c:url value="/action/Content/ContentCollection/Main" />"><fmt:message key="navigation.contentCollections" /></a> &gt;&gt;
                <c:url var="contentCatalogsUrl" value="/action/Content/ContentCatalog/Main">
                    <c:param name="ContentCollectionName" value="${contentCategoryDescription.contentCategory.contentCatalog.contentCollection.contentCollectionName}" />
                </c:url>
                <a href="${contentCatalogsUrl}"><fmt:message key="navigation.contentCatalogs" /></a> &gt;&gt;
                <c:url var="contentCategoriesUrl" value="/action/Content/ContentCategory/Main">
                    <c:param name="ContentCollectionName" value="${contentCategoryDescription.contentCategory.contentCatalog.contentCollection.contentCollectionName}" />
                    <c:param name="ContentCatalogName" value="${contentCategoryDescription.contentCategory.contentCatalog.contentCatalogName}" />
                    <c:param name="ParentContentCategoryName" value="${contentCategoryDescription.contentCategory.parentContentCategory.contentCategoryName}" />
                </c:url>
                <a href="${contentCategoriesUrl}"><fmt:message key="navigation.contentCategories" /></a> &gt;&gt;
                <c:url var="descriptionsUrl" value="/action/Content/ContentCategory/Description">
                    <c:param name="ContentCollectionName" value="${contentCategoryDescription.contentCategory.contentCatalog.contentCollection.contentCollectionName}" />
                    <c:param name="ContentCatalogName" value="${contentCategoryDescription.contentCategory.contentCatalog.contentCatalogName}" />
                    <c:param name="ContentCategoryName" value="${contentCategoryDescription.contentCategory.contentCategoryName}" />
                    <c:param name="ParentContentCategoryName" value="${contentCategoryDescription.contentCategory.parentContentCategory.contentCategoryName}" />
                </c:url>
                <a href="${descriptionsUrl}"><fmt:message key="navigation.contentCategoryDescriptions" /></a> &gt;&gt;
                Delete
            </h2>
        </div>
        <div id="Content">
            <p>You are about to delete the <c:out value="${contentCategoryDescription.language.description}" />
            <c:out value="${fn:toLowerCase(partyEntityType.entityType.description)}" /> for &quot;<c:out value="${contentCategoryDescription.contentCategory.description}" />&quot;
            (<c:out value="${contentCategoryDescription.contentCategory.contentCategoryName}" />).</p>
            <et:executionErrors id="errorMessage">
                <p class="executionErrors"><c:out value="${errorMessage}" /></p><br />
            </et:executionErrors>
            <html:form action="/Content/ContentCategory/DescriptionDelete" method="POST">
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
                <html:hidden property="contentCatalogName" />
                <html:hidden property="contentCategoryName" />
                <html:hidden property="languageIsoName" />
                <html:hidden property="parentContentCategoryName" />
            </html:form>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>