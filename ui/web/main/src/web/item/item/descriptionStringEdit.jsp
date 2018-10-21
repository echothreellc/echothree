<%@ include file="../../include/taglibs.jsp" %>

<html:form action="/Item/Item/DescriptionEdit" method="POST" focus="stringDescription">
    <table>
        <tr>
            <td align=right><fmt:message key="label.stringDescription" />:</td>
            <td>
                <html:text property="stringDescription" size="60" maxlength="512" /> (*)
                <et:validationErrors id="errorMessage" property="StringDescription">
                    <p><c:out value="${errorMessage}" /></p>
                </et:validationErrors>
            </td>
        </tr>
        <tr>
            <td>
                <html:hidden property="itemName" />
                <html:hidden property="itemDescriptionTypeName" />
                <html:hidden property="languageIsoName" />
            </td>
            <td><html:submit onclick="onSubmitDisable(this);" />&nbsp;<html:cancel onclick="onSubmitDisable(this);" />&nbsp;<html:reset /><html:hidden property="submitButton" /></td>
        </tr>
    </table>
</html:form>
