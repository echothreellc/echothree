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
        <title>Review (<c:out value="${chainInstance.chainInstanceName}" />)</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Chain/Main" />">Chains</a> &gt;&gt;
                <a href="<c:url value="/action/Chain/ChainKind/Main" />">Kinds</a> &gt;&gt;
                <c:url var="chainTypesUrl" value="/action/Chain/ChainType/Main">
                    <c:param name="ChainKindName" value="${chainInstance.chain.chainType.chainKind.chainKindName}" />
                </c:url>
                <a href="${chainTypesUrl}">Types</a> &gt;&gt;
                <c:url var="chainsUrl" value="/action/Chain/Chain/Main">
                    <c:param name="ChainKindName" value="${chainInstance.chain.chainType.chainKind.chainKindName}" />
                    <c:param name="ChainTypeName" value="${chainInstance.chain.chainType.chainTypeName}" />
                </c:url>
                <a href="${chainsUrl}">Chains</a> &gt;&gt;
                <c:url var="chainInstancesUrl" value="/action/Chain/ChainInstance/Main">
                    <c:param name="ChainKindName" value="${chainInstance.chain.chainType.chainKind.chainKindName}" />
                    <c:param name="ChainTypeName" value="${chainInstance.chain.chainType.chainTypeName}" />
                    <c:param name="ChainName" value="${chainInstance.chain.chainName}" />
                </c:url>
                <a href="${chainInstancesUrl}">Instances</a> &gt;&gt;
                Review (<c:out value="${chainInstance.chainInstanceName}" />)
            </h2>
        </div>
        <div id="Content">
            <p><font size="+2"><b><c:out value="${chainInstance.chainInstanceName}" /></b></font></p>
            <br />
            <display:table name="chainInstance.chainInstanceEntityRoles.list" id="chainInstanceEntityRole" class="displaytag" export="true" sort="list" requestURI="/action/Chain/ChainInstance/Review">
                <display:setProperty name="export.csv.filename" value="ChainInstanceEntityRoles.csv" />
                <display:setProperty name="export.excel.filename" value="ChainInstanceEntityRoles.xls" />
                <display:setProperty name="export.pdf.filename" value="ChainInstanceEntityRoles.pdf" />
                <display:setProperty name="export.rtf.filename" value="ChainInstanceEntityRoles.rtf" />
                <display:setProperty name="export.xml.filename" value="ChainInstanceEntityRoles.xml" />
                <display:column titleKey="columnTitle.chainEntityRoleType" media="html" sortable="true" sortProperty="chainEntityRoleType.description">
                    <c:out value="${chainInstanceEntityRole.chainEntityRoleType.description}" />
                </display:column>
                <display:column titleKey="columnTitle.entityInstance" media="html" sortable="true" sortProperty="entityInstance.description">
                    <c:set var="entityInstance" scope="request" value="${chainInstanceEntityRole.entityInstance}" />
                    <jsp:include page="../../include/targetAsReviewLink.jsp" />
                    <jsp:include page="../../include/targetAsEditLink.jsp" />
                </display:column>
                <display:column property="chainEntityRoleType.description" titleKey="columnTitle.chainEntityRoleType" media="csv excel pdf rtf xml" />
                <display:column titleKey="columnTitle.name" media="csv excel pdf rtf xml">
                    <c:set var="entityInstance" scope="request" value="${chainInstanceEntityRole.entityInstance}" />
                    <jsp:include page="../../include/targetAsName.jsp" />
                </display:column>
                <display:column titleKey="columnTitle.entityInstance" media="csv excel pdf rtf xml">
                    <c:set var="entityInstance" scope="request" value="${chainInstanceEntityRole.entityInstance}" />
                    <jsp:include page="../../include/targetAsText.jsp" />
                </display:column>
            </display:table>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
