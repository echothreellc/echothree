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
        <title>Review Password Policy (<c:out value="${partyTypePasswordStringPolicy.partyType.partyTypeName}" />)</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Configuration/Main" />">Configuration</a> &gt;&gt;
                <a href="<c:url value="/action/Configuration/PartyType/Main" />">Party Types</a> &gt;&gt;
                Review Password Policy (<c:out value="${partyTypePasswordStringPolicy.partyType.partyTypeName}" />)
            </h2>
        </div>
        <div id="Content">
            <p><font size="+2"><b><c:out value="${partyTypePasswordStringPolicy.partyType.description}" /> Password Policy</b></font></p>
            <br />
            Force Change After Create:
            <c:choose>
                <c:when test="${partyTypePasswordStringPolicy.forceChangeAfterCreate}">
                    <fmt:message key="phrase.yes" />
                </c:when>
                <c:otherwise>
                    <fmt:message key="phrase.no" />
                </c:otherwise>
            </c:choose>
            <br />
            Force Change After Reset:
            <c:choose>
                <c:when test="${partyTypePasswordStringPolicy.forceChangeAfterReset}">
                    <fmt:message key="phrase.yes" />
                </c:when>
                <c:otherwise>
                    <fmt:message key="phrase.no" />
                </c:otherwise>
            </c:choose>
            <br />
            Allow Change:
            <c:choose>
                <c:when test="${partyTypePasswordStringPolicy.allowChange}">
                    <fmt:message key="phrase.yes" />
                </c:when>
                <c:otherwise>
                    <fmt:message key="phrase.no" />
                </c:otherwise>
            </c:choose>
            <br />
            <br />
            Password History:
            <c:choose>
                <c:when test='${partyTypePasswordStringPolicy.passwordHistory == null}'>
                    <i><fmt:message key="phrase.notSet" /></i>
                </c:when>
                <c:otherwise>
                    <c:out value="${partyTypePasswordStringPolicy.passwordHistory}" />
                </c:otherwise>
            </c:choose>
            <br />
            Minimum Password Lifetime:
            <c:choose>
                <c:when test='${partyTypePasswordStringPolicy.minimumPasswordLifetime == null}'>
                    <i><fmt:message key="phrase.notSet" /></i>
                </c:when>
                <c:otherwise>
                    <c:out value="${partyTypePasswordStringPolicy.minimumPasswordLifetime}" />
                </c:otherwise>
            </c:choose>
            <br />
            Maximum Password Lifetime:
            <c:choose>
                <c:when test='${partyTypePasswordStringPolicy.maximumPasswordLifetime == null}'>
                    <i><fmt:message key="phrase.notSet" /></i>
                </c:when>
                <c:otherwise>
                    <c:out value="${partyTypePasswordStringPolicy.maximumPasswordLifetime}" />
                </c:otherwise>
            </c:choose>
            <br />
            <br />
            Expiration Warning Time:
            <c:choose>
                <c:when test='${partyTypePasswordStringPolicy.expirationWarningTime == null}'>
                    <i><fmt:message key="phrase.notSet" /></i>
                </c:when>
                <c:otherwise>
                    <c:out value="${partyTypePasswordStringPolicy.expirationWarningTime}" />
                </c:otherwise>
            </c:choose>
            <br />
            Expired Logins Permitted:
            <c:choose>
                <c:when test='${partyTypePasswordStringPolicy.expiredLoginsPermitted == null}'>
                    <i><fmt:message key="phrase.notSet" /></i>
                </c:when>
                <c:otherwise>
                    <c:out value="${partyTypePasswordStringPolicy.expiredLoginsPermitted}" />
                </c:otherwise>
            </c:choose>
            <br />
            <br />
            Minimum Length:
            <c:choose>
                <c:when test='${partyTypePasswordStringPolicy.minimumLength == null}'>
                    <i><fmt:message key="phrase.notSet" /></i>
                </c:when>
                <c:otherwise>
                    <c:out value="${partyTypePasswordStringPolicy.minimumLength}" />
                </c:otherwise>
            </c:choose>
            <br />
            Maximum Length:
            <c:choose>
                <c:when test='${partyTypePasswordStringPolicy.maximumLength == null}'>
                    <i><fmt:message key="phrase.notSet" /></i>
                </c:when>
                <c:otherwise>
                    <c:out value="${partyTypePasswordStringPolicy.maximumLength}" />
                </c:otherwise>
            </c:choose>
            <br />
            <br />
            Required Digit Count:
            <c:choose>
                <c:when test='${partyTypePasswordStringPolicy.requiredDigitCount == null}'>
                    <i><fmt:message key="phrase.notSet" /></i>
                </c:when>
                <c:otherwise>
                    <c:out value="${partyTypePasswordStringPolicy.requiredDigitCount}" />
                </c:otherwise>
            </c:choose>
            <br />
            Required Letter Count:
            <c:choose>
                <c:when test='${partyTypePasswordStringPolicy.requiredLetterCount == null}'>
                    <i><fmt:message key="phrase.notSet" /></i>
                </c:when>
                <c:otherwise>
                    <c:out value="${partyTypePasswordStringPolicy.requiredLetterCount}" />
                </c:otherwise>
            </c:choose>
            <br />
            Required Upper Case Count:
            <c:choose>
                <c:when test='${partyTypePasswordStringPolicy.requiredUpperCaseCount == null}'>
                    <i><fmt:message key="phrase.notSet" /></i>
                </c:when>
                <c:otherwise>
                    <c:out value="${partyTypePasswordStringPolicy.requiredUpperCaseCount}" />
                </c:otherwise>
            </c:choose>
            <br />
            Required Lower Case Count:
            <c:choose>
                <c:when test='${partyTypePasswordStringPolicy.requiredLowerCaseCount == null}'>
                    <i><fmt:message key="phrase.notSet" /></i>
                </c:when>
                <c:otherwise>
                    <c:out value="${partyTypePasswordStringPolicy.requiredLowerCaseCount}" />
                </c:otherwise>
            </c:choose>
            <br />
            Maximum Repeated:
            <c:choose>
                <c:when test='${partyTypePasswordStringPolicy.maximumRepeated == null}'>
                    <i><fmt:message key="phrase.notSet" /></i>
                </c:when>
                <c:otherwise>
                    <c:out value="${partyTypePasswordStringPolicy.maximumRepeated}" />
                </c:otherwise>
            </c:choose>
            <br />
            Minimum Character Types:
            <c:choose>
                <c:when test='${partyTypePasswordStringPolicy.minimumCharacterTypes == null}'>
                    <i><fmt:message key="phrase.notSet" /></i>
                </c:when>
                <c:otherwise>
                    <c:out value="${partyTypePasswordStringPolicy.minimumCharacterTypes}" />
                </c:otherwise>
            </c:choose>
            <br />
            <br />
            <br />
            <c:set var="entityInstance" scope="request" value="${partyTypePasswordStringPolicy.entityInstance}" />
            <jsp:include page="../../include/entityInstance.jsp" />
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
