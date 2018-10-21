<%@ include file="../../../include/taglibs.jsp" %>

<html:form action="/Item/Item/DescriptionAdd/Step2" method="POST" focus="stringDescription">
    <table>
        <tr>
            <td align=right><fmt:message key="label.language" />:</td>
            <td>
                <html:select property="languageChoice">
                    <html:optionsCollection property="languageChoices" />
                </html:select> (*)
                <et:validationErrors id="errorMessage" property="LanguageIsoName">
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
            <td align=right><fmt:message key="label.clobDescription" />:</td>
            <td>
                <html:textarea property="clobDescription" cols="${partyApplicationEditorUse.preferredWidth}" rows="${partyApplicationEditorUse.preferredHeight}" styleId="clobDescriptionTA" /> (*)
                <et:validationErrors id="errorMessage" property="clobDescription">
                    <p><c:out value="${errorMessage}" /></p>
                </et:validationErrors>
            </td>
        </tr>
        <tr>
            <td>
                <html:hidden property="itemName" />
                <html:hidden property="itemDescriptionTypeName" />
            </td>
            <td><html:submit onclick="onSubmitDisable(this);" />&nbsp;<html:cancel onclick="onSubmitDisable(this);" />&nbsp;<html:reset /><html:hidden property="submitButton" /></td>
        </tr>
    </table>
</html:form>
