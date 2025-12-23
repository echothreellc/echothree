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
        <title>Kind Descriptions</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Subscription/Main" />">Subscriptions</a> &gt;&gt;
                <a href="<c:url value="/action/Subscription/SubscriptionKind/Main" />">Kinds</a> &gt;&gt;
                <c:url var="subscriptionTypesUrl" value="/action/Subscription/SubscriptionType/Main">
                    <c:param name="SubscriptionKindName" value="${subscriptionKind.subscriptionKindName}" />
                </c:url>
                <a href="${subscriptionTypesUrl}">Types</a> &gt;&gt;
                Descriptions
            </h2>
        </div>
        <div id="Content">
            <c:url var="addDescriptionUrl" value="/action/Subscription/SubscriptionType/DescriptionAdd">
                <c:param name="SubscriptionKindName" value="${subscriptionKind.subscriptionKindName}" />
                <c:param name="SubscriptionTypeName" value="${subscriptionType.subscriptionTypeName}" />
            </c:url>
            <p><a href="${addDescriptionUrl}">Add Description.</a></p>
            <display:table name="subscriptionTypeDescriptions" id="subscriptionTypeDescription" class="displaytag">
                <display:column titleKey="columnTitle.language">
                    <c:out value="${subscriptionTypeDescription.language.description}" />
                </display:column>
                <display:column titleKey="columnTitle.description">
                    <c:out value="${subscriptionTypeDescription.description}" />
                </display:column>
                <display:column>
                    <c:url var="editDescriptionUrl" value="/action/Subscription/SubscriptionType/DescriptionEdit">
                        <c:param name="SubscriptionKindName" value="${subscriptionTypeDescription.subscriptionType.subscriptionKind.subscriptionKindName}" />
                        <c:param name="SubscriptionTypeName" value="${subscriptionTypeDescription.subscriptionType.subscriptionTypeName}" />
                        <c:param name="LanguageIsoName" value="${subscriptionTypeDescription.language.languageIsoName}" />
                    </c:url>
                    <a href="${editDescriptionUrl}">Edit</a>
                    <c:url var="deleteDescriptionUrl" value="/action/Subscription/SubscriptionType/DescriptionDelete">
                        <c:param name="SubscriptionKindName" value="${subscriptionTypeDescription.subscriptionType.subscriptionKind.subscriptionKindName}" />
                        <c:param name="SubscriptionTypeName" value="${subscriptionTypeDescription.subscriptionType.subscriptionTypeName}" />
                        <c:param name="LanguageIsoName" value="${subscriptionTypeDescription.language.languageIsoName}" />
                    </c:url>
                    <a href="${deleteDescriptionUrl}">Delete</a>
                </display:column>
           </display:table>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
