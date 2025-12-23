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
        <title>Customer Contact Mechanisms (<c:out value="${customer.customerName}" />)</title>
        <html:base/>
        <%@ include file="../../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Customer/Main" />">Customers</a> &gt;&gt;
                <a href="<c:url value="/action/Customer/Customer/Main" />">Search</a> &gt;&gt;
                <et:countCustomerResults searchTypeName="ORDER_ENTRY" countVar="customerResultsCount" commandResultVar="countCustomerResultsCommandResult" logErrors="false" />
                <c:if test="${customerResultsCount > 0}">
                    <a href="<c:url value="/action/Customer/Customer/Result" />"><fmt:message key="navigation.results" /></a> &gt;&gt;
                </c:if>
                <c:url var="reviewUrl" value="/action/Customer/Customer/Review">
                    <c:param name="CustomerName" value="${customer.customerName}" />
                </c:url>
                <a href="${reviewUrl}">Review (<c:out value="${customer.customerName}" />)</a> &gt;&gt;
                <c:url var="customerContactMechanismsUrl" value="/action/Customer/CustomerContactMechanism/Main">
                    <c:param name="CustomerName" value="${customer.customerName}" />
                </c:url>
                <a href="${customerContactMechanismsUrl}">Contact Mechanisms</a> &gt;&gt;
                Add Contact Mechanism
            </h2>
        </div>
        <div id="Content">
            Type of Contact Mechanism:<br /><br />
            <table>
                <c:forEach items="${contactMechanismTypes}" var="contactMechanismType">
                    <tr>
                        <c:if test="${contactMechanismType.contactMechanismTypeName == 'EMAIL_ADDRESS' || contactMechanismType.contactMechanismTypeName == 'POSTAL_ADDRESS' || contactMechanismType.contactMechanismTypeName == 'TELECOM_ADDRESS' || contactMechanismType.contactMechanismTypeName == 'WEB_ADDRESS'}">
                            <c:choose>
                                <c:when test="${contactMechanismType.contactMechanismTypeName == 'POSTAL_ADDRESS' || contactMechanismType.contactMechanismTypeName == 'TELECOM_ADDRESS'}">
                                    <c:choose>
                                        <c:when test="${contactMechanismType.contactMechanismTypeName == 'POSTAL_ADDRESS'}">
                                            <c:url var="addUrl" value="../../../action/Customer/CustomerContactMechanism/ContactMechanismAdd/Step2">
                                                <c:param name="PartyName" value="${customer.partyName}" />
                                                <c:param name="ContactMechanismTypeName" value="${contactMechanismType.contactMechanismTypeName}" />
                                            </c:url>
                                            <c:url var="addUsingDefaultCountryUrl" value="../../../action/Customer/CustomerContactMechanism/ContactMechanismAdd/ContactPostalAddressAdd">
                                                <c:param name="PartyName" value="${customer.partyName}" />
                                                <c:param name="CountryName" value="${defaultCountry.geoCodeAliases.map['COUNTRY_NAME'].alias}" />
                                            </c:url>
                                        </c:when>
                                    </c:choose>
                                    <c:choose>
                                        <c:when test="${contactMechanismType.contactMechanismTypeName == 'TELECOM_ADDRESS'}">
                                            <c:url var="addUrl" value="../../../action/Customer/CustomerContactMechanism/ContactMechanismAdd/Step2">
                                                <c:param name="PartyName" value="${customer.partyName}" />
                                                <c:param name="ContactMechanismTypeName" value="${contactMechanismType.contactMechanismTypeName}" />
                                            </c:url>
                                            <c:url var="addUsingDefaultCountryUrl" value="../../../action/Customer/CustomerContactMechanism/ContactMechanismAdd/ContactTelephoneAdd">
                                                <c:param name="PartyName" value="${customer.partyName}" />
                                                <c:param name="CountryName" value="${defaultCountry.geoCodeAliases.map['COUNTRY_NAME'].alias}" />
                                            </c:url>
                                        </c:when>
                                    </c:choose>
                                    <td align="right">&nbsp;&nbsp;&nbsp;&nbsp;<c:out value="${contactMechanismType.description}" />:</td>
                                    <td>&nbsp;&nbsp;<a href="${addUsingDefaultCountryUrl}"><c:out value="${defaultCountry.description}" /></a></td>
                                    <td>&nbsp;&nbsp;<a href="${addUrl}">Other Country</a></td>
                                </c:when>
                                <c:when test="${contactMechanismType.contactMechanismTypeName == 'EMAIL_ADDRESS'}">
                                    <c:url var="addUrl" value="../../../action/Customer/CustomerContactMechanism/ContactMechanismAdd/ContactEmailAddressAdd">
                                        <c:param name="PartyName" value="${customer.partyName}" />
                                    </c:url>
                                </c:when>
                                <c:when test="${contactMechanismType.contactMechanismTypeName == 'WEB_ADDRESS'}">
                                    <c:url var="addUrl" value="../../../action/Customer/CustomerContactMechanism/ContactMechanismAdd/ContactWebAddressAdd">
                                        <c:param name="PartyName" value="${customer.partyName}" />
                                    </c:url>
                                </c:when>
                            </c:choose>
                            <c:if test="${contactMechanismType.contactMechanismTypeName == 'EMAIL_ADDRESS' || contactMechanismType.contactMechanismTypeName == 'WEB_ADDRESS'}">
                                <td align="right">&nbsp;&nbsp;&nbsp;&nbsp;<c:out value="${contactMechanismType.description}" />:</td>
                                <td colspan="2">&nbsp;&nbsp;<a href="${addUrl}">All Countries</a></td>
                            </c:if>
                        </c:if>
                    </tr>
                </c:forEach>
            </table>
        </div>
        <jsp:include page="../../../include/userSession.jsp" />
        <jsp:include page="../../../include/copyright.jsp" />
    </body>
</html:html>
