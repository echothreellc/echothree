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
        <title>Forum Threads</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
        <%@ include file="tinyMce.jsp" %>
    </head>
    <body onLoad="pageLoaded()">
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Forum/Main" />">Forums</a> &gt;&gt;
                <a href="<c:url value="/action/Forum/Forum/Main" />">Forums</a> &gt;&gt;
                <c:url var="forumThreadsUrl" value="/action/Forum/ForumThread/Main">
                    <c:param name="ForumName" value="${forumName}" />
                </c:url>
                <a href="${forumThreadsUrl}">Forum Threads</a> &gt;&gt;
                Add
            </h2>
        </div>
        <div id="Content">
            <et:executionErrors id="errorMessage">
                <p class="executionErrors"><c:out value="${errorMessage}" /></p><br />
            </et:executionErrors>
            <html:form action="/Forum/ForumThread/Add" method="POST" focus="title">
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
                        <td align=right><fmt:message key="label.forumThreadIconChoice" />:</td>
                        <td>
                            <html:select property="forumThreadIconChoice">
                                <html:optionsCollection property="forumThreadIconChoices" />
                            </html:select>
                            <et:validationErrors id="errorMessage" property="ForumThreadIconName">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.postedTime" />:</td>
                        <td>
                            <html:text property="postedTime" size="50" maxlength="50" />
                            <et:validationErrors id="errorMessage" property="PostedTime">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.sortOrder" />:</td>
                        <td>
                            <html:text property="sortOrder" size="12" maxlength="12" /> (*)
                            <et:validationErrors id="errorMessage" property="SortOrder">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.forumMessageIconChoice" />:</td>
                        <td>
                            <html:select property="forumMessageIconChoice">
                                <html:optionsCollection property="forumMessageIconChoices" />
                            </html:select>
                            <et:validationErrors id="errorMessage" property="ForumMessageIconName">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.title" />:</td>
                        <td>
                            <html:text property="title" size="60" maxlength="512" /> (*)
                            <et:validationErrors id="errorMessage" property="Title">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.feedSummaryMimeTypeChoice" />:</td>
                        <td>
                            <html:select onchange="feedSummaryMimeTypeChoiceChange()" property="feedSummaryMimeTypeChoice" styleId="feedSummaryMimeTypeChoices">
                                <html:optionsCollection property="feedSummaryMimeTypeChoices" />
                            </html:select>
                            <et:validationErrors id="errorMessage" property="FeedSummaryMimeTypeName">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.feedSummary" />:</td>
                        <td>
                            <html:textarea property="feedSummary" cols="${blogFeedSummaryEditor.preferredWidth}" rows="${blogFeedSummaryEditor.preferredHeight}" styleId="feedSummaryTA" />
                            <et:validationErrors id="errorMessage" property="FeedSummary">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.summaryMimeTypeChoice" />:</td>
                        <td>
                            <html:select onchange="summaryMimeTypeChoiceChange()" property="summaryMimeTypeChoice" styleId="summaryMimeTypeChoices">
                                <html:optionsCollection property="summaryMimeTypeChoices" />
                            </html:select>
                            <et:validationErrors id="errorMessage" property="SummaryMimeTypeName">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.summary" />:</td>
                        <td>
                            <html:textarea property="summary" cols="${blogSummaryEditor.preferredWidth}" rows="${blogSummaryEditor.preferredHeight}" styleId="summaryTA" />
                            <et:validationErrors id="errorMessage" property="Summary">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.contentMimeTypeChoice" />:</td>
                        <td>
                            <html:select onchange="contentMimeTypeChoiceChange()" property="contentMimeTypeChoice" styleId="contentMimeTypeChoices">
                                <html:optionsCollection property="contentMimeTypeChoices" />
                            </html:select> (*)
                            <et:validationErrors id="errorMessage" property="ContentMimeTypeName">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.content" />:</td>
                        <td>
                            <html:textarea property="content" cols="${blogContentEditor.preferredWidth}" rows="${blogContentEditor.preferredHeight}" styleId="contentTA" /> (*)
                            <et:validationErrors id="errorMessage" property="Content">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <html:hidden property="forumName" />
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