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
        <title>Jobs</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Configuration/Main" />">Configuration</a> &gt;&gt;
                Jobs
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="Event.List:Job.Create:Job.Edit:Job.Delete:Job.Review:Job.Description" />
            <et:hasSecurityRole securityRole="Job.Create">
                <p><a href="<c:url value="/action/Configuration/Job/Add" />">Add Job.</a></p>
            </et:hasSecurityRole>
            <et:hasSecurityRole securityRole="Job.Review" var="includeReviewUrl" />
            <et:hasSecurityRole securityRole="Job.Edit" var="includeEditUrl" />
            <display:table name="jobs" id="job" class="displaytag">
                <display:column titleKey="columnTitle.name">
                    <c:choose>
                        <c:when test="${includeReviewUrl}">
                            <c:url var="reviewUrl" value="/action/Configuration/Job/Review">
                                <c:param name="JobName" value="${job.jobName}" />
                            </c:url>
                            <a href="${reviewUrl}"><c:out value="${job.jobName}" /></a>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${job.jobName}" />
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <display:column titleKey="columnTitle.description">
                    <c:out value="${job.description}" />
                </display:column>
                <display:column property="sortOrder" titleKey="columnTitle.sortOrder" />
                <display:column titleKey="columnTitle.lastStartTime">
                    <c:out value="${job.lastStartTime}" />
                </display:column>
                <display:column titleKey="columnTitle.lastEndTime">
                    <c:out value="${job.lastEndTime}" />
                </display:column>
                <display:column titleKey="columnTitle.status">
                    <c:choose>
                        <c:when test="${includeEditUrl}">
                            <c:url var="statusUrl" value="/action/Configuration/Job/Status">
                                <c:param name="JobName" value="${job.jobName}" />
                            </c:url>
                            <a href="${statusUrl}"><c:out value="${job.jobStatus.workflowStep.description}" /></a>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${job.jobStatus.workflowStep.description}" />
                        </c:otherwise>
                    </c:choose>
                </display:column>
                <et:hasSecurityRole securityRoles="Job.Edit:Job.Delete:Job.Description">
                    <display:column>
                        <et:hasSecurityRole securityRole="Job.Edit">
                            <c:url var="editUrl" value="/action/Configuration/Job/Edit">
                                <c:param name="OriginalJobName" value="${job.jobName}" />
                            </c:url>
                            <a href="${editUrl}">Edit</a>
                        </et:hasSecurityRole>
                        <et:hasSecurityRole securityRole="Job.Description">
                            <c:url var="descriptionsUrl" value="/action/Configuration/Job/Description">
                                <c:param name="JobName" value="${job.jobName}" />
                            </c:url>
                        </et:hasSecurityRole>
                        <a href="${descriptionsUrl}">Descriptions</a>
                        <et:hasSecurityRole securityRole="Job.Delete">
                            <c:url var="deleteUrl" value="/action/Configuration/Job/Delete">
                                <c:param name="JobName" value="${job.jobName}" />
                            </c:url>
                            <a href="${deleteUrl}">Delete</a>
                        </et:hasSecurityRole>
                    </display:column>
                </et:hasSecurityRole>
                <et:hasSecurityRole securityRole="Event.List">
                    <display:column>
                        <c:url var="eventsUrl" value="/action/Core/Event/Main">
                            <c:param name="EntityRef" value="${job.entityInstance.entityRef}" />
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
