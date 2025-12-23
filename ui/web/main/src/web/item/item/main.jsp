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
        <title>Items</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Item/Main" />">Items</a> &gt;&gt;
                Search
            </h2>
        </div>
        <div id="Content">
            <p><a href="<c:url value="/action/Item/Item/Add" />">Add Item.</a></p>
            <et:executionErrors id="errorMessage">
                <p class="executionErrors"><c:out value="${errorMessage}" /></p><br />
            </et:executionErrors>
            <html:form action="/Item/Item/Main" method="POST" focus="itemNameOrAlias">
                <table>
                    <tr>
                        <td align=right><fmt:message key="label.itemType" />:</td>
                        <td>
                            <html:select property="itemTypeChoice">
                                <html:optionsCollection property="itemTypeChoices" />
                            </html:select>
                            <et:validationErrors id="errorMessage" property="ItemTypeName">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.itemUseType" />:</td>
                        <td>
                            <html:select property="itemUseTypeChoice">
                                <html:optionsCollection property="itemUseTypeChoices" />
                            </html:select>
                            <et:validationErrors id="errorMessage" property="ItemUseTypeName">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td colspan=2>&nbsp;</td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.itemNameOrAlias" />:</td>
                        <td>
                            <html:text size="20" property="itemNameOrAlias" maxlength="40" />
                            <et:validationErrors id="errorMessage" property="ItemNameOrAlias">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td colspan=2>&nbsp;</td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.description" />:</td>
                        <td>
                            <html:text size="60" property="description" />
                            <et:validationErrors id="errorMessage" property="Name">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td colspan=2>&nbsp;</td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.itemStatus" />:</td>
                        <td>
                            <html:select property="itemStatusChoice">
                                <html:optionsCollection property="itemStatusChoices" />
                            </html:select>
                            <et:validationErrors id="errorMessage" property="ItemStatusChoice">
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
                        <td align=right><fmt:message key="label.searchDefaultOperator" />:</td>
                        <td>
                            <html:select property="searchDefaultOperatorChoice">
                                <html:optionsCollection property="searchDefaultOperatorChoices" />
                            </html:select>
                            <et:validationErrors id="errorMessage" property="SearchDefaultOperatorName">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.sortBy" />:</td>
                        <td>
                            <html:select property="searchSortOrderChoice">
                                <html:optionsCollection property="searchSortOrderChoices" />
                            </html:select>
                            <html:select property="searchSortDirectionChoice">
                                <html:optionsCollection property="searchSortDirectionChoices" />
                            </html:select>
                            <et:validationErrors id="errorMessage" property="SearchSortOrderName">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                            <et:validationErrors id="errorMessage" property="SearchSortDirectionName">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.rememberPreferences" />:</td>
                        <td>
                            <html:checkbox property="rememberPreferences" />
                            <et:validationErrors id="errorMessage" property="RememberPreferences">
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