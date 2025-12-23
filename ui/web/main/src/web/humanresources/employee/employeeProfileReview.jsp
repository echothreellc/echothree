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
        <title>
            <fmt:message key="pageTitle.employee">
                <fmt:param value="${employee.employeeName}" />
            </fmt:message>
        </title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/HumanResources/Main" />"><fmt:message key="navigation.humanResources" /></a> &gt;&gt;
                <a href="<c:url value="/action/HumanResources/Employee/Main" />"><fmt:message key="navigation.employees" /></a> &gt;&gt;
                <et:countEmployeeResults searchTypeName="HUMAN_RESOURCES" countVar="employeeResultsCount" commandResultVar="countEmployeeResultsCommandResult" logErrors="false" />
                <c:if test="${employeeResultsCount > 0}">
                    <a href="<c:url value="/action/HumanResources/Employee/Result" />"><fmt:message key="navigation.results" /></a> &gt;&gt;
                </c:if>
                <c:url var="reviewUrl" value="/action/HumanResources/Employee/Review">
                    <c:param name="EmployeeName" value="${employeeName}" />
                </c:url>
                <a href="${reviewUrl}">Review (<c:out value="${employeeName}" />)</a> &gt;&gt;
                Review Profile
            </h2>
        </div>
        <div id="Content">
            <c:if test='${employee.person.firstName != null || employee.person.middleName != null || employee.person.lastName != null}'>
                <p><font size="+2"><b><c:out value="${employee.person.personalTitle.description}" /> <c:out value="${employee.person.firstName}" />
                <c:out value="${employee.person.middleName}" /> <c:out value="${employee.person.lastName}" />
                <c:out value="${employee.person.nameSuffix.description}" /></b></font></p>
            </c:if>
            <c:if test='${employee.partyGroup.name != null}'>
                <p><font size="+1"><c:out value="${employee.partyGroup.name}" /></font></p>
            </c:if>
            <br />
            Employee Name: ${employee.employeeName}<br />
            <br />
            <c:if test='${employee.profile.nickname != null}'>
                Nickname: ${employee.profile.nickname}<br />
            </c:if>
            <c:if test='${employee.profile.icon != null}'>
                Icon: ${employee.profile.icon.description}<br />
            </c:if>
            <c:if test='${employee.profile.pronunciation != null}'>
                Pronouns: ${employee.profile.pronunciation}<br />
            </c:if>
            <c:if test='${employee.profile.gender != null}'>
                Gender: ${employee.profile.gender.description}<br />
            </c:if>
            <c:if test='${employee.profile.pronouns != null}'>
                Pronouns: ${employee.profile.pronouns}<br />
            </c:if>
            <c:if test='${employee.profile.birthday != null}'>
                Birthday: ${employee.profile.birthday}<br />
            </c:if>
            Birthday Format: ${employee.profile.birthdayFormat.description}<br />
            <c:if test='${employee.profile.occupation != null}'>
                Occupation: ${employee.profile.occupation}<br />
            </c:if>
            <c:if test='${employee.profile.hobbies != null}'>
                Hobbies: ${employee.profile.hobbies}<br />
            </c:if>
            <c:if test='${employee.profile.location != null}'>
                Location: ${employee.profile.location}<br />
            </c:if>
            <c:if test='${employee.profile.bioMimeType != null}'>
                Bio:<br />
                <table class="displaytag">
                    <tbody>
                        <tr class="odd">
                            <td>
                                <et:out value="${employee.profile.bio}" mimeTypeName="${employee.profile.bioMimeType.mimeTypeName}" />
                            </td>
                        </tr>
                    </tbody>
                </table>
            </c:if>
            <c:if test='${employee.profile.signatureMimeType != null}'>
                Signature:<br />
                <table class="displaytag">
                    <tbody>
                        <tr class="odd">
                            <td>
                                <et:out value="${employee.profile.signature}" mimeTypeName="${employee.profile.signatureMimeType.mimeTypeName}" />
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