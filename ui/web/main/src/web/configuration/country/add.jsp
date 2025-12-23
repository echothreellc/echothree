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
        <title><fmt:message key="pageTitle.countries" /></title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Configuration/Main" />"><fmt:message key="navigation.configuration" /></a> &gt;&gt;
                <a href="<c:url value="/action/Configuration/Country/Main" />"><fmt:message key="navigation.countries" /></a> &gt;&gt;
                Add
            </h2>
        </div>
        <div id="Content">
            <et:executionErrors id="errorMessage">
                <p class="executionErrors"><c:out value="${errorMessage}" /></p><br />
            </et:executionErrors>
            <html:form action="/Configuration/Country/Add" method="POST" focus="countryName">
                <table>
                    <tr>
                        <td align=right><fmt:message key="label.countryName" />:</td>
                        <td>
                            <html:text property="countryName" size="40" maxlength="40" /> (*)
                            <et:validationErrors id="errorMessage" property="CountryName">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.iso3Number" />:</td>
                        <td>
                            <html:text property="iso3Number" size="10" maxlength="3" /> (*)
                            <et:validationErrors id="errorMessage" property="Iso3Number">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.iso3Letter" />:</td>
                        <td>
                            <html:text property="iso3Letter" size="10" maxlength="3" /> (*)
                            <et:validationErrors id="errorMessage" property="Iso3Letter">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.iso2Letter" />:</td>
                        <td>
                            <html:text property="iso2Letter" size="10" maxlength="3" /> (*)
                            <et:validationErrors id="errorMessage" property="Iso2Letter">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.telephoneCode" />:</td>
                        <td>
                            <html:text property="telephoneCode" size="10" maxlength="5" />
                            <et:validationErrors id="errorMessage" property="TelephoneCode">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.areaCodePattern" />:</td>
                        <td>
                            <html:text property="areaCodePattern" size="40" maxlength="128" />
                            <et:validationErrors id="errorMessage" property="AreaCodePattern">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.areaCodeRequired" />:</td>
                        <td>
                            <html:checkbox property="areaCodeRequired" /> (*)
                            <et:validationErrors id="errorMessage" property="AreaCodeRequired">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.areaCodeExample" />:</td>
                        <td>
                            <html:text property="areaCodeExample" size="10" maxlength="5" />
                            <et:validationErrors id="errorMessage" property="AreaCodeExample">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.telephoneNumberPattern" />:</td>
                        <td>
                            <html:text property="telephoneNumberPattern" size="40" maxlength="128" />
                            <et:validationErrors id="errorMessage" property="TelephoneNumberPattern">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.telephoneNumberExample" />:</td>
                        <td>
                            <html:text property="telephoneNumberExample" size="30" maxlength="25" />
                            <et:validationErrors id="errorMessage" property="TelephoneNumberExample">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.postalAddressFormat" />:</td>
                        <td>
                            <html:select property="postalAddressFormatChoice">
                                <html:optionsCollection property="postalAddressFormatChoices" />
                            </html:select> (*)
                            <et:validationErrors id="errorMessage" property="PostalAddressFormatName">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.cityRequired" />:</td>
                        <td>
                            <html:checkbox property="cityRequired" /> (*)
                            <et:validationErrors id="errorMessage" property="CityRequired">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.cityGeoCodeRequired" />:</td>
                        <td>
                            <html:checkbox property="cityGeoCodeRequired" /> (*)
                            <et:validationErrors id="errorMessage" property="CityGeoCodeRequired">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.stateRequired" />:</td>
                        <td>
                            <html:checkbox property="stateRequired" /> (*)
                            <et:validationErrors id="errorMessage" property="StateRequired">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.stateGeoCodeRequired" />:</td>
                        <td>
                            <html:checkbox property="stateGeoCodeRequired" /> (*)
                            <et:validationErrors id="errorMessage" property="StateGeoCodeRequired">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.postalCodePattern" />:</td>
                        <td>
                            <html:text property="postalCodePattern" size="40" maxlength="128" />
                            <et:validationErrors id="errorMessage" property="PostalCodePattern">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.postalCodeRequired" />:</td>
                        <td>
                            <html:checkbox property="postalCodeRequired" /> (*)
                            <et:validationErrors id="errorMessage" property="PostalCodeRequired">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.postalCodeGeoCodeRequired" />:</td>
                        <td>
                            <html:checkbox property="postalCodeGeoCodeRequired" /> (*)
                            <et:validationErrors id="errorMessage" property="PostalCodeGeoCodeRequired">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>

                    <tr>
                        <td align=right><fmt:message key="label.postalCodeLength" />:</td>
                        <td>
                            <html:text property="postalCodeLength" size="12" maxlength="12" />
                            <et:validationErrors id="errorMessage" property="PostalCodeLength">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>

                    <tr>
                        <td align=right><fmt:message key="label.postalCodeGeoCodeLength" />:</td>
                        <td>
                            <html:text property="postalCodeGeoCodeLength" size="12" maxlength="12" />
                            <et:validationErrors id="errorMessage" property="PostalCodeGeoCodeLength">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.postalCodeExample" />:</td>
                        <td>
                            <html:text property="postalCodeExample" size="20" maxlength="15" />
                            <et:validationErrors id="errorMessage" property="PostalCodeExample">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.isDefault" />:</td>
                        <td>
                            <html:checkbox property="isDefault" /> (*)
                            <et:validationErrors id="errorMessage" property="IsDefault">
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
                        <td align=right><fmt:message key="label.description" />:</td>
                        <td>
                            <html:text property="description" size="60" maxlength="132" />
                            <et:validationErrors id="errorMessage" property="Description">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td></td>
                        <td><html:submit onclick="onSubmitDisable(this);" />&nbsp;<html:cancel onclick="onSubmitDisable(this);" /><html:hidden property="submitButton" /></td>
                    </tr>
                </table>
            </html:form>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>