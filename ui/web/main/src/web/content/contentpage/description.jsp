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
                Descriptions
            </h2>
        </div>
        <div id="Content">
                <c:url var="addUrl" value="/action/Content/ContentPage/DescriptionAdd">
                    <c:param name="ContentCollectionName" value="${contentCollectionName}" />
                    <c:param name="ParentContentSectionName" value="${parentContentSectionName}" />
                    <c:param name="ContentSectionName" value="${contentSectionName}" />
                    <c:param name="ContentPageName" value="${contentPageName}" />
                </c:url>
            <p><a href="${addUrl}">Add Description.</a></p>
            <display:table name="contentPageDescriptions" id="contentPageDescription" class="displaytag">
                <display:column titleKey="columnTitle.language">
                    <c:out value="${contentPageDescription.language.description}" />
                </display:column>
                <display:column titleKey="columnTitle.description">
                    <c:out value="${contentPageDescription.description}" />
                </display:column>
                <display:column>
                    <c:url var="editUrl" value="/action/Content/ContentPage/DescriptionEdit">
                        <c:param name="ContentCollectionName" value="${contentPageDescription.contentPage.contentSection.contentCollection.contentCollectionName}" />
                        <c:param name="ContentSectionName" value="${contentPageDescription.contentPage.contentSection.contentSectionName}" />
                        <c:param name="ContentPageName" value="${contentPageDescription.contentPage.contentPageName}" />
                        <c:param name="LanguageIsoName" value="${contentPageDescription.language.languageIsoName}" />
                        <c:param name="ParentContentSectionName" value="${contentPageDescription.contentPage.contentSection.parentContentSection.contentSectionName}" />
                    </c:url>
                    <a href="${editUrl}">Edit</a>
                    <c:url var="deleteUrl" value="/action/Content/ContentPage/DescriptionDelete">
                        <c:param name="ContentCollectionName" value="${contentPageDescription.contentPage.contentSection.contentCollection.contentCollectionName}" />
                        <c:param name="ContentSectionName" value="${contentPageDescription.contentPage.contentSection.contentSectionName}" />
                        <c:param name="ContentPageName" value="${contentPageDescription.contentPage.contentPageName}" />
                        <c:param name="LanguageIsoName" value="${contentPageDescription.language.languageIsoName}" />
                        <c:param name="ParentContentSectionName" value="${contentPageDescription.contentPage.contentSection.parentContentSection.contentSectionName}" />
                    </c:url>
                    <a href="${deleteUrl}">Delete</a>
                </display:column>
           </display:table>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
