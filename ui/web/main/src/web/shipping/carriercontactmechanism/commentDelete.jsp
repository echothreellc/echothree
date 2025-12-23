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
        <title>Carrier Contact Mechanism (<c:out value="${contactMechanism.contactMechanismName}" />)</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Shipping/Main" />">Shipping</a> &gt;&gt;
                <a href="<c:url value="/action/Shipping/Carrier/Main" />">Carriers</a> &gt;&gt;
                <c:url var="reviewUrl" value="/action/Shipping/Carrier/Review">
                    <c:param name="CarrierName" value="${carrier.carrierName}" />
                </c:url>
                <a href="${reviewUrl}">Review (<c:out value="${carrier.carrierName}" />)</a> &gt;&gt;
                <c:url var="carrierContactMechanismsUrl" value="/action/Shipping/CarrierContactMechanism/Main">
                    <c:param name="CarrierName" value="${carrier.carrierName}" />
                </c:url>
                <a href="${carrierContactMechanismsUrl}">Contact Mechanisms</a> &gt;&gt;
                <c:url var="reviewUrl" value="/action/Shipping/CarrierContactMechanism/Review">
                    <c:param name="PartyName" value="${carrier.partyName}" />
                    <c:param name="ContactMechanismName" value="${contactMechanism.contactMechanismName}" />
                </c:url>
                <a href="${reviewUrl}">Carrier Contact Mechanism (<c:out value="${contactMechanism.contactMechanismName}" />)</a> &gt;&gt;
                Delete <c:out value="${partyEntityType.entityType.description}" />
            </h2>
        </div>
        <div id="Content">
            <p>You are about to delete the <c:out value="${fn:toLowerCase(comment.commentType.description)}" />
            <c:out value="${fn:toLowerCase(partyEntityType.entityType.description)}" />
            <c:choose>
                <c:when test="${comment.description == null}">
                    &quot;<c:out value="${comment.commentName}" />.&quot;
                </c:when>
                <c:otherwise>
                    &quot;<c:out value="${comment.description}" />.&quot;
                </c:otherwise>
            </c:choose>
            </p>
            <et:executionErrors id="errorMessage">
                <p class="executionErrors"><c:out value="${errorMessage}" /></p><br />
            </et:executionErrors>
            <html:form action="/Shipping/CarrierContactMechanism/CommentDelete" method="POST">
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
                <html:hidden property="partyName" />
                <html:hidden property="contactMechanismName" />
                <html:hidden property="commentName" />
            </html:form>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
