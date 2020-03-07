<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>

<!--                                                                                  -->
<!-- Copyright 2002-2020 Echo Three, LLC                                              -->
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

<html>
    <head>
        <title><fmt:message key="pageTitle.customers" /></title>
        <html:base/>
        <%@ include file="../../include/environment-b.jsp" %>
    </head>
    <%@ include file="../../include/body-start-b.jsp" %>
        <%@ include file="../../include/breadcrumb/breadcrumbs-start.jsp" %>
            <jsp:include page="../../include/breadcrumb/portal.jsp">
                <jsp:param name="showAsLink" value="true"/>
            </jsp:include>
            <jsp:include page="../../include/breadcrumb/customers.jsp">
                <jsp:param name="showAsLink" value="true"/>
            </jsp:include>
            <jsp:include page="../../include/breadcrumb/customers-search.jsp">
                <jsp:param name="showAsLink" value="false"/>
            </jsp:include>
        <%@ include file="../../include/breadcrumb/breadcrumbs-end.jsp" %>
        <div id="Content">
            <p><a href="<c:url value="/action/Customer/Customer/Add" />">Add Customer.</a></p>
            <et:executionErrors id="errorMessage">
                <p class="executionErrors"><c:out value="${errorMessage}" /></p><br />
            </et:executionErrors>

            <html:form action="/Customer/Customer/Main" method="POST" focus="firstName">
                <%@ include file="../../include/field/customerTypeChoice-b.jsp" %>
                <c:set var="showSoundex" value="true"/>
                <%@ include file="../../include/field/firstName-b.jsp" %>
                <%@ include file="../../include/field/middleName-b.jsp" %>
                <%@ include file="../../include/field/lastName-b.jsp" %>
                <%@ include file="../../include/field/name-b.jsp" %>
                <%@ include file="../../include/field/customerName-b.jsp" %>
                <%@ include file="../../include/field/emailAddress-b.jsp" %>
                <%@ include file="../../include/field/countryChoice-b.jsp" %>

                <%@ include file="../../include/field/customerAliasTypeChoice-b.jsp" %>
                <%@ include file="../../include/field/alias-b.jsp" %>
                <%@ include file="../../include/field/createdSince-b.jsp" %>
                <%@ include file="../../include/field/modifiedSince-b.jsp" %>
                <%@ include file="../../include/field/submit-b.jsp" %>
            </html:form>

            <html:form action="/Customer/Customer/Main" method="POST" focus="firstName">
                <table>
                    <tr>
                        <td align=right><fmt:message key="label.customerType" />:</td>
                        <td>
                            <html:select property="customerTypeChoice">
                                <html:optionsCollection property="customerTypeChoices" />
                            </html:select>
                            <et:validationErrors id="errorMessage" property="CustomerTypeName">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td colspan=2>&nbsp;</td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.firstName" />:</td>
                        <td>
                            <html:text size="20" property="firstName" maxlength="20" />
                            <fmt:message key="label.soundex" />: <html:checkbox property="firstNameSoundex" value="true" />
                            <et:validationErrors id="errorMessage" property="FirstName">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                            <et:validationErrors id="errorMessage" property="FirstNameSoundex">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <!--
                    <tr>
                        <td align=right><fmt:message key="label.middleName" />:</td>
                        <td>
                            <html:text size="20" property="middleName" maxlength="20" />
                            <fmt:message key="label.soundex" />: <html:checkbox property="middleNameSoundex" value="true" />
                            <et:validationErrors id="errorMessage" property="MiddleName">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                            <et:validationErrors id="errorMessage" property="MiddleNameSoundex">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    -->
                    <tr>
                        <td align=right><fmt:message key="label.lastName" />:</td>
                        <td>
                            <html:text size="20" property="lastName" maxlength="20" />
                            <fmt:message key="label.soundex" />: <html:checkbox property="lastNameSoundex" value="true" />
                            <et:validationErrors id="errorMessage" property="LastName">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                            <et:validationErrors id="errorMessage" property="LastNameSoundex">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td colspan=2>&nbsp;</td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.company" />:</td>
                        <td>
                            <html:text size="40" property="name" />
                            <et:validationErrors id="errorMessage" property="Name">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td colspan=2>&nbsp;</td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.customerName" />:</td>
                        <td>
                            <html:text size="20" property="customerName" maxlength="40" />
                            <et:validationErrors id="errorMessage" property="CustomerName">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td colspan=2>&nbsp;</td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.emailAddress" />:</td>
                        <td>
                            <html:text property="emailAddress" size="40" maxlength="80" />
                            <et:validationErrors id="errorMessage" property="EmailAddress">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.country" />:</td>
                        <td>
                            <html:select property="countryChoice">
                                <html:optionsCollection property="countryChoices" />
                            </html:select>
                            <et:validationErrors id="errorMessage" property="CountryName">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td>&nbsp;</td>
                        <td>
                            <table>
                                <tr>
                                    <td><html:text property="areaCode" size="5" maxlength="5" /></td>
                                    <td><html:text property="telephoneNumber" size="15" maxlength="25" /></td>
                                    <td><html:text property="telephoneExtension" size="10" maxlength="10" /></td>
                                </tr>
                                <tr>
                                    <td class="fieldlabel"><fmt:message key="label.areaCode" /></td>
                                    <td class="fieldlabel"><fmt:message key="label.telephoneNumber" /></td>
                                    <td class="fieldlabel"><fmt:message key="label.telephoneExtension" /></td>
                                </tr>
                            </table>
                            <et:validationErrors id="errorMessage" property="AreaCode">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                            <et:validationErrors id="errorMessage" property="TelephoneNumber">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                            <et:validationErrors id="errorMessage" property="TelephoneExtension">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td colspan=2>&nbsp;</td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.customerAliasType" />:</td>
                        <td>
                            <html:select property="partyAliasTypeChoice">
                                <html:optionsCollection property="partyAliasTypeChoices" />
                            </html:select>
                            <et:validationErrors id="errorMessage" property="PartyAliasTypeName">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.alias" />:</td>
                        <td>
                            <html:text size="20" property="alias" maxlength="40" />
                            <et:validationErrors id="errorMessage" property="Alias">
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
    <%@ include file="../../include/body-end-b.jsp" %>
</html>
