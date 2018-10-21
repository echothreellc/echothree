<%@ include file="../../../include/taglibs.jsp" %>

<tr>
    <td align=right><fmt:message key="label.requestNameOnCard" />:</td>
    <td>
        <html:checkbox property="requestNameOnCard" /> (*)
        <et:validationErrors id="errorMessage" property="RequestNameOnCard">
            <p><c:out value="${errorMessage}" /></p>
        </et:validationErrors>
    </td>
</tr>
<tr>
    <td align=right><fmt:message key="label.requireNameOnCard" />:</td>
    <td>
        <html:checkbox property="requireNameOnCard" /> (*)
        <et:validationErrors id="errorMessage" property="RequireNameOnCard">
            <p><c:out value="${errorMessage}" /></p>
        </et:validationErrors>
    </td>
</tr>
<tr>
    <td align=right><fmt:message key="label.checkCardNumber" />:</td>
    <td>
        <html:checkbox property="checkCardNumber" /> (*)
        <et:validationErrors id="errorMessage" property="CheckCardNumber">
            <p><c:out value="${errorMessage}" /></p>
        </et:validationErrors>
    </td>
</tr>
<tr>
    <td align=right><fmt:message key="label.requestExpirationDate" />:</td>
    <td>
        <html:checkbox property="requestExpirationDate" /> (*)
        <et:validationErrors id="errorMessage" property="RequestExpirationDate">
            <p><c:out value="${errorMessage}" /></p>
        </et:validationErrors>
    </td>
</tr>
<tr>
    <td align=right><fmt:message key="label.requireExpirationDate" />:</td>
    <td>
        <html:checkbox property="requireExpirationDate" /> (*)
        <et:validationErrors id="errorMessage" property="RequireExpirationDate">
            <p><c:out value="${errorMessage}" /></p>
        </et:validationErrors>
    </td>
</tr>
<tr>
    <td align=right><fmt:message key="label.checkExpirationDate" />:</td>
    <td>
        <html:checkbox property="checkExpirationDate" /> (*)
        <et:validationErrors id="errorMessage" property="CheckExpirationDate">
            <p><c:out value="${errorMessage}" /></p>
        </et:validationErrors>
    </td>
</tr>
<tr>
    <td align=right><fmt:message key="label.requestSecurityCode" />:</td>
    <td>
        <html:checkbox property="requestSecurityCode" /> (*)
        <et:validationErrors id="errorMessage" property="RequestSecurityCode">
            <p><c:out value="${errorMessage}" /></p>
        </et:validationErrors>
    </td>
</tr>
<tr>
    <td align=right><fmt:message key="label.requireSecurityCode" />:</td>
    <td>
        <html:checkbox property="requireSecurityCode" /> (*)
        <et:validationErrors id="errorMessage" property="RequireSecurityCode">
            <p><c:out value="${errorMessage}" /></p>
        </et:validationErrors>
    </td>
</tr>
<tr>
    <td align=right><fmt:message key="label.cardNumberValidationPattern" />:</td>
    <td>
        <html:text property="cardNumberValidationPattern" size="60" maxlength="128" />
        <et:validationErrors id="errorMessage" property="CardNumberValidationPattern">
            <p><c:out value="${errorMessage}" /></p>
        </et:validationErrors>
    </td>
</tr>
<tr>
    <td align=right><fmt:message key="label.securityCodeValidationPattern" />:</td>
    <td>
        <html:text property="securityCodeValidationPattern" size="60" maxlength="128" />
        <et:validationErrors id="errorMessage" property="SecurityCodeValidationPattern">
            <p><c:out value="${errorMessage}" /></p>
        </et:validationErrors>
    </td>
</tr>
<tr>
    <td align=right><fmt:message key="label.retainCreditCard" />:</td>
    <td>
        <html:checkbox property="retainCreditCard" /> (*)
        <et:validationErrors id="errorMessage" property="RetainCreditCard">
            <p><c:out value="${errorMessage}" /></p>
        </et:validationErrors>
    </td>
</tr>
<tr>
    <td align=right><fmt:message key="label.retainSecurityCode" />:</td>
    <td>
        <html:checkbox property="retainSecurityCode" /> (*)
        <et:validationErrors id="errorMessage" property="RetainSecurityCode">
            <p><c:out value="${errorMessage}" /></p>
        </et:validationErrors>
    </td>
</tr>
<tr>
    <td align=right><fmt:message key="label.requestBilling" />:</td>
    <td>
        <html:checkbox property="requestBilling" /> (*)
        <et:validationErrors id="errorMessage" property="RequestBilling">
            <p><c:out value="${errorMessage}" /></p>
        </et:validationErrors>
    </td>
</tr>
<tr>
    <td align=right><fmt:message key="label.requireBilling" />:</td>
    <td>
        <html:checkbox property="requireBilling" /> (*)
        <et:validationErrors id="errorMessage" property="RequireBilling">
            <p><c:out value="${errorMessage}" /></p>
        </et:validationErrors>
    </td>
</tr>
<tr>
    <td align=right><fmt:message key="label.requestIssuer" />:</td>
    <td>
        <html:checkbox property="requestIssuer" /> (*)
        <et:validationErrors id="errorMessage" property="RequestIssuer">
            <p><c:out value="${errorMessage}" /></p>
        </et:validationErrors>
    </td>
</tr>
<tr>
    <td align=right><fmt:message key="label.requireIssuer" />:</td>
    <td>
        <html:checkbox property="requireIssuer" /> (*)
        <et:validationErrors id="errorMessage" property="RequireIssuer">
            <p><c:out value="${errorMessage}" /></p>
        </et:validationErrors>
    </td>
</tr>
