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
            <td align=right><fmt:message key="label.postedTime" />:</td>
            <td>
                <html:text property="postedTime" size="50" maxlength="50" />
                <et:validationErrors id="errorMessage" property="PostedTime">
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
