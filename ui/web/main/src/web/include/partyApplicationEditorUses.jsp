<%@ include file="taglibs.jsp" %>

<h2>Application Editor Uses</h2>
<et:checkSecurityRoles securityRoles="PartyApplicationEditorUse.Create:PartyApplicationEditorUse.Edit:PartyApplicationEditorUse.Delete:Application.Review:ApplicationEditorUse.Review:ApplicationEditor.Review" />
<!--
<et:hasSecurityRole securityRole="PartyApplicationEditorUse.Create">
    <c:url var="addApplicationEditorUseUrl" value="/action/${commonUrl}/Add">
        <c:param name="PartyName" value="${party.partyName}" />
    </c:url>
    <p><a href="${addApplicationEditorUseUrl}">Add Application Editor Use.</a></p>
</et:hasSecurityRole>
-->
<c:choose>
    <c:when test='${partyApplicationEditorUses.size == 0}'>
        <br />
    </c:when>
    <c:otherwise>
        <et:hasSecurityRole securityRole="Application.Review" var="includeApplicationReviewUrl" />
        <et:hasSecurityRole securityRole="ApplicationEditorUse.Review" var="includeApplicationEditorUseReviewUrl" />
        <et:hasSecurityRole securityRole="ApplicationEditor.Review" var="includeApplicationEditorReviewUrl" />
        <display:table name="partyApplicationEditorUses.list" id="partyApplicationEditorUse" class="displaytag">
            <display:column titleKey="columnTitle.application">
                <c:choose>
                    <c:when test="${includeApplicationReviewUrl}">
                        <c:url var="reviewUrl" value="/action/Core/Application/Review">
                            <c:param name="ApplicationName" value="${partyApplicationEditorUse.applicationEditorUse.application.applicationName}" />
                        </c:url>
                        <a href="${reviewUrl}"><c:out value="${partyApplicationEditorUse.applicationEditorUse.application.description}" /></a>
                    </c:when>
                    <c:otherwise>
                        <c:out value="${partyApplicationEditorUse.applicationEditorUse.application.description}" />
                    </c:otherwise>
                </c:choose>
            </display:column>
            <display:column titleKey="columnTitle.applicationEditorUse">
                <c:choose>
                    <c:when test="${includeApplicationEditorUseReviewUrl}">
                        <c:url var="reviewUrl" value="/action/Core/ApplicationEditorUse/Review">
                            <c:param name="ApplicationName" value="${partyApplicationEditorUse.applicationEditorUse.application.applicationName}" />
                            <c:param name="ApplicationEditorUseName" value="${partyApplicationEditorUse.applicationEditorUse.applicationEditorUseName}" />
                        </c:url>
                        <a href="${reviewUrl}"><c:out value="${partyApplicationEditorUse.applicationEditorUse.description}" /></a>
                    </c:when>
                    <c:otherwise>
                        <c:out value="${partyApplicationEditorUse.applicationEditorUse.description}" />
                    </c:otherwise>
                </c:choose>
            </display:column>
            <display:column titleKey="columnTitle.applicationEditor">
                <c:if test="${partyApplicationEditorUse.applicationEditor != null}">
                    <c:choose>
                        <c:when test="${includeApplicationEditorReviewUrl}">
                            <c:url var="reviewUrl" value="/action/Core/ApplicationEditor/Review">
                                <c:param name="ApplicationName" value="${partyApplicationEditorUse.applicationEditor.application.applicationName}" />
                                <c:param name="EditorName" value="${partyApplicationEditorUse.applicationEditor.editor.editorName}" />
                            </c:url>
                            <a href="${reviewUrl}"><c:out value="${partyApplicationEditorUse.applicationEditor.editor.description}" /></a>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${partyApplicationEditorUse.applicationEditor.editor.description}" />
                        </c:otherwise>
                    </c:choose>
                </c:if>
            </display:column>
            <display:column titleKey="columnTitle.preferredSize">
                <c:choose>
                    <c:when test="${partyApplicationEditorUse.preferredHeight != null}">
                        <c:out value="${partyApplicationEditorUse.preferredHeight}" />
                    </c:when>
                    <c:otherwise>
                        <i>Not Set.</i>
                    </c:otherwise>
                </c:choose>
                        x
                <c:choose>
                    <c:when test="${partyApplicationEditorUse.preferredWidth != null}">
                        <c:out value="${partyApplicationEditorUse.preferredWidth}" />
                    </c:when>
                    <c:otherwise>
                        <i>Not Set.</i>
                    </c:otherwise>
                </c:choose>
            </display:column>
            <et:hasSecurityRole securityRoles="PartyApplicationEditorUse.Edit:PartyApplicationEditorUse.Delete">
                <display:column>
                    <et:hasSecurityRole securityRole="PartyApplicationEditorUse.Edit">
                        <c:url var="editUrl" value="/action/${commonUrl}/Edit">
                            <c:param name="PartyName" value="${partyApplicationEditorUse.party.partyName}" />
                            <c:param name="ApplicationName" value="${partyApplicationEditorUse.applicationEditorUse.application.applicationName}" />
                            <c:param name="ApplicationEditorUseName" value="${partyApplicationEditorUse.applicationEditorUse.applicationEditorUseName}" />
                        </c:url>
                        <a href="${editUrl}">Edit</a>
                    </et:hasSecurityRole>
                    <et:hasSecurityRole securityRole="PartyApplicationEditorUse.Delete">
                        <c:url var="deleteUrl" value="/action/${commonUrl}/Delete">
                            <c:param name="PartyName" value="${partyApplicationEditorUse.party.partyName}" />
                            <c:param name="ApplicationName" value="${partyApplicationEditorUse.applicationEditorUse.application.applicationName}" />
                            <c:param name="ApplicationEditorUseName" value="${partyApplicationEditorUse.applicationEditorUse.applicationEditorUseName}" />
                        </c:url>
                        <a href="${deleteUrl}">Delete</a>
                    </et:hasSecurityRole>
                </display:column>
            </et:hasSecurityRole>
        </display:table>
    </c:otherwise>
</c:choose>
<br />
