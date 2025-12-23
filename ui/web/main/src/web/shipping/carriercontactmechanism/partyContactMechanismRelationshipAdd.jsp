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
        <title>Carrier Contact Mechanism (<c:out value="${partyContactMechanism.contactMechanism.contactMechanismName}" />)</title>
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
                Add Contact Mechanism Relationship
            </h2>
        </div>
        <div id="Content">
            <et:executionErrors id="errorMessage">
                <p class="executionErrors"><c:out value="${errorMessage}" /></p><br />
            </et:executionErrors>
            <html:form action="/Shipping/CarrierContactMechanism/PartyContactMechanismRelationshipAdd" method="POST">
                <table>
                    <tr>
                        <td align=right><fmt:message key="label.toContactMechanism"/>:</td>
                        <td>
                            <html:select property="toContactMechanismChoice">
                                <html:optionsCollection property="toContactMechanismChoices" />
                            </html:select> (*)
                            <et:validationErrors id="errorMessage" property="ToContactMechanismName">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <html:hidden property="partyName" />
                            <html:hidden property="fromContactMechanismName" />
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
