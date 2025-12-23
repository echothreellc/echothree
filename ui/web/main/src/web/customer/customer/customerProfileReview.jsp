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
        <title>Review (<c:out value="${customerName}" />)</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
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
                    <c:param name="CustomerName" value="${customerName}" />
                </c:url>
                <a href="${reviewUrl}">Review (<c:out value="${customerName}" />)</a> &gt;&gt;
                Review Profile
            </h2>
        </div>
        <div id="Content">
            <c:if test='${customer.person.firstName != null || customer.person.middleName != null || customer.person.lastName != null}'>
                <p><font size="+2"><b><c:out value="${customer.person.personalTitle.description}" /> <c:out value="${customer.person.firstName}" />
                <c:out value="${customer.person.middleName}" /> <c:out value="${customer.person.lastName}" />
                <c:out value="${customer.person.nameSuffix.description}" /></b></font></p>
            </c:if>
            <c:if test='${customer.partyGroup.name != null}'>
                <p><font size="+1"><c:out value="${customer.partyGroup.name}" /></font></p>
            </c:if>
            <br />
            Customer Name: ${customer.customerName}<br />
            <br />
            <c:if test='${customer.profile.nickname != null}'>
                Nickname: ${customer.profile.nickname}<br />
            </c:if>
            <c:if test='${customer.profile.icon != null}'>
                Icon: ${customer.profile.icon.description}<br />
            </c:if>
            <c:if test='${customer.profile.pronunciation != null}'>
                Pronouns: ${customer.profile.pronunciation}<br />
            </c:if>
            <c:if test='${customer.profile.gender != null}'>
                Gender: ${customer.profile.gender.description}<br />
            </c:if>
            <c:if test='${customer.profile.pronouns != null}'>
                Pronouns: ${customer.profile.pronouns}<br />
            </c:if>
            <c:if test='${customer.profile.birthday != null}'>
                Birthday: ${customer.profile.birthday}<br />
            </c:if>
            Birthday Format: ${customer.profile.birthdayFormat.description}<br />
            <c:if test='${customer.profile.occupation != null}'>
                Occupation: ${customer.profile.occupation}<br />
            </c:if>
            <c:if test='${customer.profile.hobbies != null}'>
                Hobbies: ${customer.profile.hobbies}<br />
            </c:if>
            <c:if test='${customer.profile.location != null}'>
                Location: ${customer.profile.location}<br />
            </c:if>
            <c:if test='${customer.profile.bioMimeType != null}'>
                Bio:<br />
                <table class="displaytag">
                    <tbody>
                        <tr class="odd">
                            <td>
                                <et:out value="${customer.profile.bio}" mimeTypeName="${customer.profile.bioMimeType.mimeTypeName}" />
                            </td>
                        </tr>
                    </tbody>
                </table>
            </c:if>
            <c:if test='${customer.profile.signatureMimeType != null}'>
                Signature:<br />
                <table class="displaytag">
                    <tbody>
                        <tr class="odd">
                            <td>
                                <et:out value="${customer.profile.signature}" mimeTypeName="${customer.profile.signatureMimeType.mimeTypeName}" />
                            </td>
                        </tr>
                    </tbody>
                </table>
            </c:if>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>