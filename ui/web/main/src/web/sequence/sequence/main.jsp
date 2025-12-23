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
        <title>Sequences</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Sequence/Main" />">Sequences</a> &gt;&gt;
                <a href="<c:url value="/action/Sequence/SequenceType/Main" />">Types</a> &gt;&gt;
                Sequences
            </h2>
        </div>
        <div id="Content">
            <c:url var="addUrl" value="/action/Sequence/Sequence/Add">
                <c:param name="SequenceTypeName" value="${sequenceType.sequenceTypeName}" />
            </c:url>
            <p><a href="${addUrl}">Add Sequence.</a></p>
            <et:checkSecurityRoles securityRoles="Event.List" />
            <display:table name="sequences" id="sequence" class="displaytag">
                <display:column titleKey="columnTitle.name">
                    <c:url var="reviewUrl" value="/action/Sequence/Sequence/Review">
                        <c:param name="SequenceTypeName" value="${sequence.sequenceType.sequenceTypeName}" />
                        <c:param name="SequenceName" value="${sequence.sequenceName}" />
                    </c:url>
                    <a href="${reviewUrl}"><c:out value="${sequence.sequenceName}" /></a>
                </display:column>
                <display:column titleKey="columnTitle.description">
                    <c:out value="${sequence.description}" />
                </display:column>
                <display:column titleKey="columnTitle.mask">
                    <c:out value="${sequence.mask}" />
                </display:column>
                <display:column titleKey="columnTitle.value">
                    <c:out value="${sequence.value}" />
                </display:column>
                <display:column titleKey="columnTitle.default">
                    <c:choose>
                        <c:when test="${sequence.isDefault}">
                            Default
                        </c:when>
                        <c:otherwise>
                            <c:url var="setDefaultSequenceUrl" value="/action/Sequence/Sequence/SetDefault">
                        <c:param name="SequenceTypeName" value="${sequence.sequenceType.sequenceTypeName}" />
                        <c:param name="SequenceName" value="${sequence.sequenceName}" />
                            </c:url>
                            <a href="${setDefaultSequenceUrl}">Set Default</a>
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column>
                    <c:url var="editSequenceUrl" value="/action/Sequence/Sequence/Edit">
                        <c:param name="SequenceTypeName" value="${sequence.sequenceType.sequenceTypeName}" />
                        <c:param name="OriginalSequenceName" value="${sequence.sequenceName}" />
                    </c:url>
                    <a href="${editSequenceUrl}">Edit</a>
                    <c:url var="sequenceDescriptionsUrl" value="/action/Sequence/Sequence/Description">
                        <c:param name="SequenceTypeName" value="${sequence.sequenceType.sequenceTypeName}" />
                        <c:param name="SequenceName" value="${sequence.sequenceName}" />
                    </c:url>
                    <a href="${sequenceDescriptionsUrl}">Descriptions</a>
                    <c:url var="deleteSequenceUrl" value="/action/Sequence/Sequence/Delete">
                        <c:param name="SequenceTypeName" value="${sequence.sequenceType.sequenceTypeName}" />
                        <c:param name="SequenceName" value="${sequence.sequenceName}" />
                    </c:url>
                    <a href="${deleteSequenceUrl}">Delete</a>
                </display:column>
                <et:hasSecurityRole securityRole="Event.List">
                    <display:column>
                        <c:url var="eventsUrl" value="/action/Core/Event/Main">
                            <c:param name="EntityRef" value="${sequence.entityInstance.entityRef}" />
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
