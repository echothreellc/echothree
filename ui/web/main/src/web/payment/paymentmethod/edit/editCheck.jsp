<%@ include file="../../../include/taglibs.jsp" %>

<tr>
    <td align=right><fmt:message key="label.holdDays" />:</td>
    <td>
        <html:text property="holdDays" size="12" maxlength="12" /> (*)
        <et:validationErrors id="errorMessage" property="HoldDays">
            <p><c:out value="${errorMessage}" /></p>
        </et:validationErrors>
    </td>
</tr>
