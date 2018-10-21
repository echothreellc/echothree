<%@ include file="../../include/taglibs.jsp" %>

<html:form action="/HumanResources/EmployeeDocument/Edit" method="POST" enctype="multipart/form-data">
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
                <html:text property="description" size="60" maxlength="80" />
                <et:validationErrors id="errorMessage" property="Description">
                    <p><c:out value="${errorMessage}" /></p>
                </et:validationErrors>
            </td>
        </tr>
        <tr>
            <td align=right><fmt:message key="label.blob" />:</td>
            <td>
                <html:file property="blob" />  (*)
                <et:validationErrors id="errorMessage" property="Blob">
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
