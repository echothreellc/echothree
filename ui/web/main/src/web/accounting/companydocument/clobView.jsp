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
        <title>Company Document (<c:out value="${partyDocument.document.documentName}" />)</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Accounting/Main" />"><fmt:message key="navigation.accounting" /></a> &gt;&gt;
                <a href="<c:url value="/action/Accounting/Company/Main" />"><fmt:message key="navigation.companies" /></a> &gt;&gt;
                <c:url var="reviewUrl" value="/action/Accounting/Company/Review">
                    <c:param name="CompanyName" value="${company.companyName}" />
                </c:url>
                <a href="${reviewUrl}">Review (<c:out value="${company.companyName}" />)</a> &gt;&gt;
                <c:url var="companyDocumentsUrl" value="/action/Accounting/CompanyDocument/Main">
                    <c:param name="CompanyName" value="${company.companyName}" />
                </c:url>
                <a href="${companyDocumentsUrl}">Documents</a> &gt;&gt;
                View Company Document (<c:out value="${partyDocument.document.documentName}" />)
            </h2>
        </div>
        <div id="Content">
            <p><font size="+2"><b>
                <c:choose>
                    <c:when test="${partyDocument.document.description == null}">
                        ${partyDocument.document.documentName}
                    </c:when>
                    <c:otherwise>
                        <c:out value="${partyDocument.document.description}" />
                    </c:otherwise>
                </c:choose>
            </b></font></p>
            <br />
            Document Name: ${partyDocument.document.documentName}<br />
            Description Type: <c:out value="${partyDocument.document.documentType.description}" /><br />
            Mime Type: <c:out value="${partyDocument.document.mimeType.description}" /><br />
            <br />
            <table class="displaytag">
                <tbody>
                    <tr class="odd">
                        <td>
                            <et:out value="${partyDocument.document.clob}" mimeTypeName="${partyDocument.document.mimeType.mimeTypeName}" />
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
