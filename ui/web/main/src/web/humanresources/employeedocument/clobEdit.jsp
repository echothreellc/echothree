<%@ include file="../../include/taglibs.jsp" %>

<html:form action="/HumanResources/EmployeeDocument/Edit" method="POST" focus="stringDescription">
    <table>
        <tr>
            <td align=right><fmt:message key="label.isDefault" />:</td>
            <td>
                <html:checkbox property="isDefault" /> (*)
                <et:validationErrors id="errorMessage" property="IsDefault">
                    <p><c:out value="${errorMessage}" /></p>
                </et:validationErrors>
            </td>
        </tr>
        <tr>
            <td align=right><fmt:message key="label.sortOrder" />:</td>
            <td>
                <html:text property="sortOrder" size="12" maxlength="12" /> (*)
                <et:validationErrors id="errorMessage" property="SortOrder">
                    <p><c:out value="${errorMessage}" /></p>
                </et:validationErrors>
            </td>
        </tr>
        <tr>
            <td align=right><fmt:message key="label.description" />:</td>
            <td>
                <html:text property="description" size="60" maxlength="132" />
                <et:validationErrors id="errorMessage" property="Description">
                    <p><c:out value="${errorMessage}" /></p>
                </et:validationErrors>
            </td>
        </tr>
        <tr>
            <td align=right><fmt:message key="label.mimeType" />:</td>
            <td>
                <html:select onchange="mimeTypeChoiceChange()" property="mimeTypeChoice" styleId="mimeTypeChoices">
                    <html:optionsCollection property="mimeTypeChoices" />
                </html:select> (*)
                <et:validationErrors id="errorMessage" property="MimeTypeName">
                    <p><c:out value="${errorMessage}" /></p>
                </et:validationErrors>
            </td>
        </tr>
        <tr>
            <td align=right><fmt:message key="label.clob" />:</td>
            <td>
                <html:textarea property="clob" cols="${employeeDocumentEditorUse.preferredWidth}" rows="${employeeDocumentEditorUse.preferredHeight}" styleId="clobTA" /> (*)
                <et:validationErrors id="errorMessage" property="clob">
                    <p><c:out value="${errorMessage}" /></p>
                </et:validationErrors>
            </td>
        </tr>
        <tr>
            <td>
                <html:hidden property="partyName" />
                <html:hidden property="documentName" />
            </td>
            <td><html:submit onclick="onSubmitDisable(this);" />&nbsp;<html:cancel onclick="onSubmitDisable(this);" />&nbsp;<html:reset /><html:hidden property="submitButton" /></td>
        </tr>
    </table>
</html:form>
