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
        <title>Offer Uses</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Advertising/Main" />">Advertising</a> &gt;&gt;
                <a href="<c:url value="/action/Advertising/Offer/Main" />">Offers</a> &gt;&gt;
                Offer Uses
            </h2>
        </div>
        <div id="Content">
            <c:url var="addUrl" value="/action/Advertising/OfferUse/Add">
                <c:param name="OfferName" value="${offer.offerName}" />
            </c:url>
            <p><a href="${addUrl}">Add Offer Use.</a></p>
            <et:checkSecurityRoles securityRoles="Event.List" />
            <display:table name="offerUses" id="offerUse" class="displaytag">
                <display:column titleKey="columnTitle.offerOfferUse">
                    <c:url var="reviewUrl" value="/action/Advertising/OfferUse/Review">
                        <c:param name="OfferName" value="${offerUse.offer.offerName}" />
                        <c:param name="UseName" value="${offerUse.use.useName}" />
                    </c:url>
                    <c:out value="${offerUse.offer.description}:${offerUse.use.description}" /> (<a href="${reviewUrl}">${offerUse.offer.offerName}:${offerUse.use.useName}</a>)
                </display:column>
                <display:column titleKey="columnTitle.salesOrderSequence">
                    <c:if test='${offerUse.salesOrderSequence != null}'>
                        <c:url var="salesOrderSequenceUrl" value="/action/Sequence/Sequence/Review">
                            <c:param name="SequenceTypeName" value="${offerUse.salesOrderSequence.sequenceType.sequenceTypeName}" />
                            <c:param name="SequenceName" value="${offerUse.salesOrderSequence.sequenceName}" />
                        </c:url>
                        <a href="${salesOrderSequenceUrl}">${offerUse.salesOrderSequence.description}</a>
                    </c:if>
                </display:column>
                <display:column>
                    <c:url var="editUrl" value="/action/Advertising/OfferUse/Edit">
                        <c:param name="OfferName" value="${offerUse.offer.offerName}" />
                        <c:param name="UseName" value="${offerUse.use.useName}" />
                    </c:url>
                    <a href="${editUrl}">Edit</a>
                    <c:url var="deleteUrl" value="/action/Advertising/OfferUse/Delete">
                        <c:param name="OfferName" value="${offerUse.offer.offerName}" />
                        <c:param name="UseName" value="${offerUse.use.useName}" />
                    </c:url>
                    <a href="${deleteUrl}">Delete</a>
                </display:column>
                <et:hasSecurityRole securityRole="Event.List">
                    <display:column>
                        <c:url var="eventsUrl" value="/action/Core/Event/Main">
                            <c:param name="EntityRef" value="${offerUse.entityInstance.entityRef}" />
                        </c:url>
                        <a href="${eventsUrl}">Events</a>
                    </display:column>
                </et:hasSecurityRole>
            </display:table>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
