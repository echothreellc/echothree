<%@ include file="../../include/taglibs.jsp" %>

<html:form action="/Item/Item/DescriptionEdit" method="POST" enctype="multipart/form-data">
    <table>
        <c:if test='${itemDescriptionType.mimeTypeUsageType.mimeTypeUsageTypeName == "IMAGE"}'>
            <tr>
                <td align=right><fmt:message key="label.itemImageType" />:</td>
                <td>
                    <html:select property="itemImageTypeChoice">
                        <html:optionsCollection property="itemImageTypeChoices" />
                    </html:select>
                    <et:validationErrors id="errorMessage" property="ItemImageTypeName">
                        <p><c:out value="${errorMessage}" /></p>
                    </et:validationErrors>
                </td>
            </tr>
        </c:if>
        <tr>
            <td align=right><fmt:message key="label.blobDescription" />:</td>
            <td>
                <html:file property="blobDescription" />  (*)
                <et:validationErrors id="errorMessage" property="BlobDescription">
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
