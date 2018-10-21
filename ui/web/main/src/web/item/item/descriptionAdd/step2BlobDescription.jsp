<%@ include file="../../../include/taglibs.jsp" %>

<c:if test='${itemDescriptionType.minimumHeight != null || itemDescriptionType.minimumWidth != null || itemDescriptionType.maximumHeight != null || itemDescriptionType.maximumWidth != null || itemDescriptionType.preferredHeight != null || itemDescriptionType.preferredWidth != null || itemDescriptionType.preferredMimeType != null}'>
    <c:set var="includeBreak" value="true" />
</c:if>

<c:if test="${includeBreak}">
    <br />
</c:if>

<c:if test='${itemDescriptionType.minimumHeight != null}'>
    Minimum Height: <c:out value="${itemDescriptionType.minimumHeight}" /><br />
</c:if>
<c:if test='${itemDescriptionType.minimumWidth != null}'>
    Minimum Width: <c:out value="${itemDescriptionType.minimumWidth}" /><br />
</c:if>
<c:if test='${itemDescriptionType.maximumHeight != null}'>
    Maximum Height: <c:out value="${itemDescriptionType.maximumHeight}" /><br />
</c:if>
<c:if test='${itemDescriptionType.maximumWidth != null}'>
    Maximum Width: <c:out value="${itemDescriptionType.maximumWidth}" /><br />
</c:if>
<c:if test='${itemDescriptionType.preferredHeight != null}'>
    Preferred Height: <c:out value="${itemDescriptionType.preferredHeight}" /><br />
</c:if>
<c:if test='${itemDescriptionType.preferredWidth != null}'>
    Preferred Width: <c:out value="${itemDescriptionType.preferredWidth}" /><br />
</c:if>
<c:if test='${itemDescriptionType.preferredMimeType != null}'>
    <c:url var="mimeTypeUrl" value="/action/Core/MimeType/Review">
        <c:param name="MimeTypeName" value="${itemDescriptionType.preferredMimeType.mimeTypeName}" />
    </c:url>
    Preferred Mime Type: <a href="${mimeTypeUrl}"><c:out value="${itemDescriptionType.preferredMimeType.description}" /></a><br />
</c:if>

<c:if test="${includeBreak}">
    <br />
</c:if>

<html:form action="/Item/Item/DescriptionAdd/Step2" method="POST" enctype="multipart/form-data">
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
            </td>
            <td><html:submit onclick="onSubmitDisable(this);" />&nbsp;<html:cancel onclick="onSubmitDisable(this);" />&nbsp;<html:reset /><html:hidden property="submitButton" /></td>
        </tr>
    </table>
</html:form>
