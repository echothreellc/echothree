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
        <title>Entity Clob Attribute</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
        <%@ include file="attributeTinyMce.jsp" %>
    </head>
    <body onLoad="pageLoaded()">
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Core/Main" />">Core</a> &gt;&gt;
                Entity Clob Attribute &gt;&gt;
                View
            </h2>
        </div>
        <div id="Content">
            Language: <c:out value="${entityClobAttribute.language.description}" /><br />
            Mime Type: <c:out value="${entityClobAttribute.mimeType.description}" /> (<c:out value="${entityClobAttribute.mimeType.mimeTypeName}" />)<br />
            <br />
            <table class="displaytag">
                <tbody>
                    <tr class="odd">
                        <td>
                            <et:out value="${entityClobAttribute.clobAttribute}" mimeTypeName="${entityClobAttribute.mimeType.mimeTypeName}" />
                        </td>
                    </tr>
                </tbody>
            </table>
            <a href="${returnUrl}">Return</a>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
