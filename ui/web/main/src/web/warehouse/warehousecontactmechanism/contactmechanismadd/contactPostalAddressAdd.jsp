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

<%@ include file="../../../include/taglibs.jsp" %>

<html:html xhtml="true">
    <head>
        <title>
            <fmt:message key="pageTitle.warehouseContactMechanisms">
                <fmt:param value="${warehouse.warehouseName}" />
            </fmt:message>
        </title>
        <html:base/>
        <%@ include file="../../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Warehouse/Main" />"><fmt:message key="navigation.warehouses" /></a> &gt;&gt;
                <a href="<c:url value="/action/Warehouse/Warehouse/Main" />"><fmt:message key="navigation.warehouses" /></a> &gt;&gt;
                <et:countWarehouseResults searchTypeName="EMPLOYEE" countVar="warehouseResultsCount" commandResultVar="countWarehouseResultsCommandResult" logErrors="false" />
                <c:if test="${warehouseResultsCount > 0}">
                    <a href="<c:url value="/action/Warehouse/Warehouse/Result" />"><fmt:message key="navigation.results" /></a> &gt;&gt;
                </c:if>
                <c:url var="reviewUrl" value="/action/Warehouse/Warehouse/Review">
                    <c:param name="WarehouseName" value="${warehouse.warehouseName}" />
                </c:url>
                <a href="${reviewUrl}">Review (<c:out value="${warehouse.warehouseName}" />)</a> &gt;&gt;
                <c:url var="warehouseContactMechanismsUrl" value="/action/Warehouse/WarehouseContactMechanism/Main">
                    <c:param name="WarehouseName" value="${warehouse.warehouseName}" />
                </c:url>
                <a href="${warehouseContactMechanismsUrl}"><fmt:message key="navigation.warehouseContactMechanisms" /></a> &gt;&gt;
                Add Contact Mechanism
            </h2>
        </div>
        <div id="Content">
            <et:executionErrors id="errorMessage">
                <p class="executionErrors"><c:out value="${errorMessage}" /></p><br />
            </et:executionErrors>
            <html:form action="/Warehouse/WarehouseContactMechanism/ContactMechanismAdd/ContactPostalAddressAdd" method="POST" focus="firstName">
                <table>
                    <tr>
                        <td align=right><fmt:message key="label.allowSolicitation" />:</td>
                        <td>
                            <html:checkbox property="allowSolicitation" /> (*)
                            <et:validationErrors id="errorMessage" property="AllowSolicitation">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.isCommercial" />:</td>
                        <td>
                            <html:checkbox property="isCommercial" /> (*)
                            <et:validationErrors id="errorMessage" property="IsCommercial">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.personalTitle" />:</td>
                        <td>
                            <html:select property="personalTitleChoice">
                                <html:optionsCollection property="personalTitleChoices" />
                            </html:select>
                            <et:validationErrors id="errorMessage" property="PersonalTitleChoice">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.firstName" />:</td>
                        <td>
                            <html:text size="20" property="firstName" maxlength="20" />
                            <et:validationErrors id="errorMessage" property="FirstName">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.middleName" />:</td>
                        <td>
                            <html:text size="20" property="middleName" maxlength="20" />
                            <et:validationErrors id="errorMessage" property="MiddleName">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.lastName" />:</td>
                        <td>
                            <html:text size="20" property="lastName" maxlength="20" />
                            <et:validationErrors id="errorMessage" property="LastName">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.nameSuffix" />:</td>
                        <td>
                            <html:select property="nameSuffixChoice">
                                <html:optionsCollection property="nameSuffixChoices" />
                            </html:select>
                            <et:validationErrors id="errorMessage" property="NameSuffixChoice">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.company" />:</td>
                        <td>
                            <html:text size="20" property="companyName" maxlength="60" />
                            <et:validationErrors id="errorMessage" property="CompanyName">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.attention" />:</td>
                        <td>
                            <html:text size="20" property="attention" maxlength="60" />
                            <et:validationErrors id="errorMessage" property="Attention">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.address1" />:</td>
                        <td>
                            <html:text size="20" property="address1" maxlength="40" /> (*)
                            <et:validationErrors id="errorMessage" property="Address1">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.address2" />:</td>
                        <td>
                            <html:text size="20" property="address2" maxlength="40" />
                            <et:validationErrors id="errorMessage" property="Address2">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.address3" />:</td>
                        <td>
                            <html:text size="20" property="address3" maxlength="40" />
                            <et:validationErrors id="errorMessage" property="Address3">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.city" />:</td>
                        <td>
                            <html:text size="20" property="city" maxlength="40" />
                            <c:if test='${country.cityRequired}'>
                                (*)
                            </c:if>
                            <et:validationErrors id="errorMessage" property="City">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.state" />:</td>
                        <td>
                            <html:text size="20" property="state" maxlength="40" />
                            <c:if test='${country.stateRequired}'>
                                (*)
                            </c:if>
                            <et:validationErrors id="errorMessage" property="State">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.postalCode" />:</td>
                        <td>
                            <html:text size="20" property="postalCode" maxlength="40" />
                            <c:if test='${country.postalCodeRequired}'>
                                (*)
                            </c:if>
                            <c:if test='${country.postalCodeExample != null}'>
                                <i>(Example: <c:out value="${country.postalCodeExample}" />)</i>
                            </c:if>
                            <et:validationErrors id="errorMessage" property="PostalCode">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.description" />:</td>
                        <td>
                            <html:text property="description" size="60" maxlength="132" />
                            <et:validationErrors id="errorMessage" property="Description">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <html:hidden property="partyName" />
                            <html:hidden property="countryName" />
                        </td>
                        <td><html:submit onclick="onSubmitDisable(this);" /><html:hidden property="submitButton" /></td>
                    </tr>
                </table>
            </html:form>
        </div>
        <jsp:include page="../../../include/userSession.jsp" />
        <jsp:include page="../../../include/copyright.jsp" />
    </body>
</html:html>
