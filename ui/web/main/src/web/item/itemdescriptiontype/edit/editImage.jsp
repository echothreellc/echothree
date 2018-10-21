<%@ include file="../../../include/taglibs.jsp" %>

<tr>
    <td align=right><fmt:message key="label.minimumHeight" />:</td>
    <td>
        <html:text property="minimumHeight" size="12" maxlength="12" />
        <et:validationErrors id="errorMessage" property="MinimumHeight">
            <p><c:out value="${errorMessage}" /></p>
        </et:validationErrors>
    </td>
</tr>
<tr>
    <td align=right><fmt:message key="label.minimumWidth" />:</td>
    <td>
        <html:text property="minimumWidth" size="12" maxlength="12" />
        <et:validationErrors id="errorMessage" property="MinimumWidth">
            <p><c:out value="${errorMessage}" /></p>
        </et:validationErrors>
    </td>
</tr>
<tr>
    <td align=right><fmt:message key="label.maximumHeight" />:</td>
    <td>
        <html:text property="maximumHeight" size="12" maxlength="12" />
        <et:validationErrors id="errorMessage" property="MaximumHeight">
            <p><c:out value="${errorMessage}" /></p>
        </et:validationErrors>
    </td>
</tr>
<tr>
    <td align=right><fmt:message key="label.maximumWidth" />:</td>
    <td>
        <html:text property="maximumWidth" size="12" maxlength="12" />
        <et:validationErrors id="errorMessage" property="MaximumWidth">
            <p><c:out value="${errorMessage}" /></p>
        </et:validationErrors>
    </td>
</tr>
<tr>
    <td align=right><fmt:message key="label.preferredHeight" />:</td>
    <td>
        <html:text property="preferredHeight" size="12" maxlength="12" />
        <et:validationErrors id="errorMessage" property="PreferredHeight">
            <p><c:out value="${errorMessage}" /></p>
        </et:validationErrors>
    </td>
</tr>
<tr>
    <td align=right><fmt:message key="label.preferredWidth" />:</td>
    <td>
        <html:text property="preferredWidth" size="12" maxlength="12" />
        <et:validationErrors id="errorMessage" property="PreferredWidth">
            <p><c:out value="${errorMessage}" /></p>
        </et:validationErrors>
    </td>
</tr>
<tr>
    <td align=right><fmt:message key="label.preferredMimeType" />:</td>
    <td>
        <html:select property="preferredMimeTypeChoice">
            <html:optionsCollection property="preferredMimeTypeChoices" />
        </html:select>
        <et:validationErrors id="errorMessage" property="PreferredMimeTypeName">
            <p><c:out value="${errorMessage}" /></p>
        </et:validationErrors>
    </td>
</tr>
<tr>
    <td align=right><fmt:message key="label.quality" />:</td>
    <td>
        <html:text property="quality" size="5" maxlength="3" />
        <et:validationErrors id="errorMessage" property="Quality">
            <p><c:out value="${errorMessage}" /></p>
        </et:validationErrors>
    </td>
</tr>
<tr>
    <td align=right><fmt:message key="label.scaleFromParent" />:</td>
    <td>
        <html:checkbox property="scaleFromParent" /> (*)
        <et:validationErrors id="errorMessage" property="ScaleFromParent">
            <p><c:out value="${errorMessage}" /></p>
        </et:validationErrors>
    </td>
</tr>
