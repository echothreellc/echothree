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
        <title>Leaves</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/HumanResources/Main" />"><fmt:message key="navigation.humanResources" /></a> &gt;&gt;
                <fmt:message key="navigation.leaves" /> &gt;&gt;
                Search
            </h2>
        </div>
        <div id="Content">
            <et:executionErrors id="errorMessage">
                <p class="executionErrors"><c:out value="${errorMessage}" /></p><br />
            </et:executionErrors>
            <html:form action="/HumanResources/Leave/Search" method="POST" focus="leaveName">
                <table>
                    <tr>
                        <td align=right><fmt:message key="label.leaveName" />:</td>
                        <td>
                            <html:text size="20" property="leaveName" maxlength="40" />
                            <et:validationErrors id="errorMessage" property="LeaveName">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td colspan=2>&nbsp;</td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.leaveType" />:</td>
                        <td>
                            <html:select property="leaveTypeChoice">
                                <html:optionsCollection property="leaveTypeChoices" />
                            </html:select>
                            <et:validationErrors id="errorMessage" property="LeaveTypeName">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.leaveReason" />:</td>
                        <td>
                            <html:select property="leaveReasonChoice">
                                <html:optionsCollection property="leaveReasonChoices" />
                            </html:select>
                            <et:validationErrors id="errorMessage" property="LeaveReasonName">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td colspan=2>&nbsp;</td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.leaveStatus" />:</td>
                        <td>
                            <html:select property="leaveStatusChoice">
                                <html:optionsCollection property="leaveStatusChoices" />
                            </html:select>
                            <et:validationErrors id="errorMessage" property="LeaveStatusChoice">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td colspan=2>&nbsp;</td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.createdSince" />:</td>
                        <td>
                            <html:text size="60" property="createdSince" maxlength="30" />
                            <et:validationErrors id="errorMessage" property="CreatedSince">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.modifiedSince" />:</td>
                        <td>
                            <html:text size="60" property="modifiedSince" maxlength="30" />
                            <et:validationErrors id="errorMessage" property="ModifiedSince">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td colspan=2>&nbsp;</td>
                    </tr>
                    <tr>
                        <td></td>
                        <td><html:submit onclick="onSubmitDisable(this);" /><input type="hidden" name="submitButton" /></td>
                    </tr>
                </table>
            </html:form>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>