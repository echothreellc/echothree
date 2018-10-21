<%@ include file="../include/taglibs.jsp" %>

<et:workAssignments options="EntityInstanceIncludeNames" var="workAssignments" scope="request" />
<c:if test="${workAssignments.size > 0}">
    <et:checkSecurityRoles securityRoles="WorkflowStep.Review" />
    <et:hasSecurityRole securityRole="WorkflowStep.Review" var="includeWorkflowStepReviewUrl" />
    <h5><fmt:message key="label.workAssignments" /></h5>
    <display:table name="workAssignments.list" id="workAssignment" class="displaytag">
        <display:column>
            <c:set var="entityInstance" scope="request" value="${workAssignment.workRequirement.workEffort.owningEntityInstance}" />
            <jsp:include page="../include/targetAsReviewLink.jsp" />
        </display:column>
        <display:column titleKey="columnTitle.description">
            <c:choose>
                <c:when test="${entityInstance.entityType.componentVendor.componentVendorName == 'ECHOTHREE'}">
                    <c:choose>
                        <c:when test="${entityInstance.entityType.entityTypeName == 'PartyTrainingClass'}">
                            <c:set var="partyTrainingClassName" scope="request" value="${entityInstance.entityNames.names.map.PartyTrainingClassName}" />
                            <et:partyTrainingClass partyTrainingClassName="${partyTrainingClassName}" var="partyTrainingClass" />
                            Training Class: <c:out value="${partyTrainingClass.trainingClass.description}" />
                        </c:when>
                    </c:choose>
                </c:when>
            </c:choose>
        </display:column>
        <display:column titleKey="columnTitle.requiredTime">
            <c:out value="${workAssignment.workRequirement.requiredTime}" />
        </display:column>
        <display:column titleKey="columnTitle.workRequirementStatus">
            <c:choose>
                <c:when test="${includeWorkflowStepReviewUrl}">
                    <c:url var="workflowStepUrl" value="/action/Configuration/WorkflowStep/Review">
                        <c:param name="WorkflowName" value="${workAssignment.workRequirement.workRequirementStatus.workflowStep.workflow.workflowName}" />
                        <c:param name="WorkflowStepName" value="${workAssignment.workRequirement.workRequirementStatus.workflowStep.workflowStepName}" />
                    </c:url>
                    <a href="${workflowStepUrl}"><c:out value="${workAssignment.workRequirement.workRequirementStatus.workflowStep.description}" /></a>
                </c:when>
                <c:otherwise>
                    <c:out value="${workAssignment.workRequirement.workRequirementStatus.workflowStep.description}" />
                </c:otherwise>
            </c:choose>
        </display:column>
        <display:column titleKey="columnTitle.workAssignmentStatus">
            <c:choose>
                <c:when test="${includeWorkflowStepReviewUrl}">
                    <c:url var="workflowStepUrl" value="/action/Configuration/WorkflowStep/Review">
                        <c:param name="WorkflowName" value="${workAssignment.workAssignmentStatus.workflowStep.workflow.workflowName}" />
                        <c:param name="WorkflowStepName" value="${workAssignment.workAssignmentStatus.workflowStep.workflowStepName}" />
                    </c:url>
                    <a href="${workflowStepUrl}"><c:out value="${workAssignment.workAssignmentStatus.workflowStep.description}" /></a>
                </c:when>
                <c:otherwise>
                    <c:out value="${workAssignment.workAssignmentStatus.workflowStep.description}" />
                </c:otherwise>
            </c:choose>
        </display:column>
        <display:column>
            <c:choose>
                <c:when test="${entityInstance.entityType.componentVendor.componentVendorName == 'ECHOTHREE'}">
                    <c:choose>
                        <c:when test="${entityInstance.entityType.entityTypeName == 'PartyTrainingClass'}">
                            <c:url var="partyTrainingClassUrl" value="/action/HumanResources/PartyTrainingClass/PartyTrainingClass">
                                <c:param name="PartyTrainingClassName" value="${partyTrainingClassName}" />
                            </c:url>
                            <a href="${partyTrainingClassUrl}">Begin Training</a>
                        </c:when>
                    </c:choose>
                </c:when>
            </c:choose>
        </display:column>
    </display:table>
    <br />
</c:if>
