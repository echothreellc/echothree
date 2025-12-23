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
        <title>Descriptions (<c:out value="${itemDescription.item.itemName}" />)</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
        <%@ include file="descriptionTinyMce.jsp" %>
    </head>
    <body onLoad="pageLoaded()">
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Item/Main" />">Items</a> &gt;&gt;
                <a href="<c:url value="/action/Item/Item/Main" />">Search</a> &gt;&gt;
                <et:countItemResults searchTypeName="ITEM_MAINTENANCE" countVar="itemResultsCount" commandResultVar="countItemResultsCommandResult" logErrors="false" />
                <c:if test="${itemResultsCount > 0}">
                    <a href="<c:url value="/action/Item/Item/Result" />"><fmt:message key="navigation.results" /></a> &gt;&gt;
                </c:if>
                <c:url var="reviewUrl" value="/action/Item/Item/Review">
                    <c:param name="ItemName" value="${itemDescription.item.itemName}" />
                </c:url>
                <a href="${reviewUrl}">Review (<c:out value="${itemDescription.item.itemName}" />)</a> &gt;&gt;
                <c:url var="descriptionsUrl" value="/action/Item/Item/Description">
                    <c:param name="ItemName" value="${itemDescription.item.itemName}" />
                </c:url>
                <a href="${descriptionsUrl}">Descriptions</a> &gt;&gt;
                Delete
            </h2>
        </div>
        <div id="Content">
            <p><font size="+2"><b><c:out value="${itemDescription.item.description}" /></b></font></p>
            <p><font size="+1">${itemDescription.item.itemName}</font></p>
            <br />
            <p>You are about to delete the <c:out value="${fn:toLowerCase(partyEntityType.entityType.description)}" />:</p>
            &nbsp;&nbsp;&nbsp;&nbsp;Item Description Type: <c:out value="${itemDescription.itemDescriptionType.description}" /><br />
            &nbsp;&nbsp;&nbsp;&nbsp;Item: <c:out value="${itemDescription.item.description}" /><br />
            &nbsp;&nbsp;&nbsp;&nbsp;Language: <c:out value="${itemDescription.language.description}" /><br />
            <et:executionErrors id="errorMessage">
                <p class="executionErrors"><c:out value="${errorMessage}" /></p><br />
            </et:executionErrors>
            <html:form action="/Item/Item/DescriptionDelete" method="POST">
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
                <html:hidden property="itemDescriptionTypeName" />
                <html:hidden property="itemName" />
                <html:hidden property="languageIsoName" />
            </html:form>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>