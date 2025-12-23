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

<html:form action="/Forum/ForumMessage/Edit" method="POST" focus="title">
    <table>
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
                <html:hidden property="forumMessageName" />
                <jsp:include page="../../include/displaytagFields.jsp" />
            </td>
            <td><html:submit onclick="onSubmitDisable(this);" />&nbsp;<html:cancel onclick="onSubmitDisable(this);" />&nbsp;<html:reset /><html:hidden property="submitButton" /></td>
        </tr>
    </table>
</html:form>
