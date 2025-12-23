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
        <title>Payment Processors</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Payment/Main" />">Payment</a> &gt;&gt;
                <a href="<c:url value="/action/Payment/PaymentProcessor/Main" />">Payment Processors</a> &gt;&gt;
                Descriptions
            </h2>
        </div>
        <div id="Content">
            <c:url var="addUrl" value="/action/Payment/PaymentProcessor/DescriptionAdd">
                <c:param name="PaymentProcessorName" value="${paymentProcessor.paymentProcessorName}" />
            </c:url>
            <p><a href="${addUrl}">Add Description.</a></p>
            <display:table name="paymentProcessorDescriptions" id="paymentProcessorDescription" class="displaytag">
                <display:column titleKey="columnTitle.language">
                    <c:out value="${paymentProcessorDescription.language.description}" />
                </display:column>
                <display:column titleKey="columnTitle.description">
                    <c:out value="${paymentProcessorDescription.description}" />
                </display:column>
                <display:column>
                    <c:url var="editUrl" value="/action/Payment/PaymentProcessor/DescriptionEdit">
                        <c:param name="PaymentProcessorName" value="${paymentProcessorDescription.paymentProcessor.paymentProcessorName}" />
                        <c:param name="LanguageIsoName" value="${paymentProcessorDescription.language.languageIsoName}" />
                    </c:url>
                    <a href="${editUrl}">Edit</a>
                    <c:url var="deleteUrl" value="/action/Payment/PaymentProcessor/DescriptionDelete">
                        <c:param name="PaymentProcessorName" value="${paymentProcessorDescription.paymentProcessor.paymentProcessorName}" />
                        <c:param name="LanguageIsoName" value="${paymentProcessorDescription.language.languageIsoName}" />
                    </c:url>
                    <a href="${deleteUrl}">Delete</a>
                </display:column>
           </display:table>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
