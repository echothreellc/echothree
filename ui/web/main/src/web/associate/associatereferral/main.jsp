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
        <title>Associate Referrals</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Associate/Main" />">Associates</a> &gt;&gt;
                <a href="<c:url value="/action/Associate/AssociateProgram/Main" />">Associate Programs</a> &gt;&gt;
                <c:url var="associatesUrl" value="/action/Associate/Associate/Main">
                    <c:param name="AssociateProgramName" value="${associate.associateProgram.associateProgramName}" />
                </c:url>
                <a href="${associatesUrl}">Associates</a> &gt;&gt;
                Referrals
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="Event.List" />
            <display:table name="associateReferrals" id="associateReferral" class="displaytag">
                <display:column titleKey="columnTitle.name">
                    <c:url var="reviewUrl" value="/action/Associate/AssociateReferral/Review">
                        <c:param name="AssociateProgramName" value="${associateReferral.associate.associateProgram.associateProgramName}" />
                        <c:param name="AssociateName" value="${associateReferral.associate.associateName}" />
                        <c:param name="AssociateReferralName" value="${associateReferral.associateReferralName}" />
                    </c:url>
                    <a href="${reviewUrl}"><c:out value="${associateReferral.associateReferralName}" /></a>
                </display:column>
                <display:column titleKey="columnTitle.referralTime">
                    <c:out value="${associateReferral.associateReferralTime}" />
                </display:column>
                <display:column>
                    <c:url var="editUrl" value="/action/Associate/AssociateReferral/Edit">
                        <c:param name="AssociateProgramName" value="${associateReferral.associate.associateProgram.associateProgramName}" />
                        <c:param name="AssociateName" value="${associateReferral.associate.associateName}" />
                        <c:param name="OriginalAssociateReferralName" value="${associateReferral.associateReferralName}" />
                    </c:url>
                    <a href="${editUrl}">Edit</a>
                    <c:url var="deleteUrl" value="/action/Associate/AssociateReferral/Delete">
                        <c:param name="AssociateProgramName" value="${associateReferral.associate.associateProgram.associateProgramName}" />
                        <c:param name="AssociateName" value="${associateReferral.associate.associateName}" />
                        <c:param name="AssociateReferralName" value="${associateReferral.associateReferralName}" />
                    </c:url>
                    <a href="${deleteUrl}">Delete</a>
                </display:column>
                <et:hasSecurityRole securityRole="Event.List">
                    <display:column>
                        <c:url var="eventsUrl" value="/action/Core/Event/Main">
                            <c:param name="EntityRef" value="${associateReferral.entityInstance.entityRef}" />
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
