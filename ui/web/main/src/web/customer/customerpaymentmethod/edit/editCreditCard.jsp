<%@ include file="../../../include/taglibs.jsp" %>

<c:if test='${partyPaymentMethod.paymentMethod.requestNameOnCard}'>
    <tr>
        <td align=right><fmt:message key="label.personalTitle" />:</td>
        <td>
            <html:select property="personalTitleChoice">
                <html:optionsCollection property="personalTitleChoices" />
            </html:select>
            <et:validationErrors id="errorMessage" property="PersonalTitleId">
                <p><c:out value="${errorMessage}" /></p>
            </et:validationErrors>
        </td>
    </tr>
    <tr>
        <td align=right><fmt:message key="label.firstName" />:</td>
        <td>
            <html:text size="20" property="firstName" maxlength="20" />
            <c:if test='${partyPaymentMethod.paymentMethod.requireNameOnCard}'>
                (*)
            </c:if>
            <et:validationErrors id="errorMessage" property="FirstName">
                <p><c:out value="${errorMessage}" /></p>
            </et:validationErrors>
        </td>
    </tr>
    <tr>
        <td align=right><fmt:message key="label.middleName" />:</td>
        <td>
            <html:text size="20" property="middleName" maxlength="20" />
            <et:validationErrors id="errorMessage" property="MiddleName">
                <p><c:out value="${errorMessage}" /></p>
            </et:validationErrors>
        </td>
    </tr>
    <tr>
        <td align=right><fmt:message key="label.lastName" />:</td>
        <td>
            <html:text size="20" property="lastName" maxlength="20" />
            <c:if test='${partyPaymentMethod.paymentMethod.requireNameOnCard}'>
                (*)
            </c:if>
            <et:validationErrors id="errorMessage" property="LastName">
                <p><c:out value="${errorMessage}" /></p>
            </et:validationErrors>
        </td>
    </tr>
    <tr>
        <td align=right><fmt:message key="label.nameSuffix" />:</td>
        <td>
            <html:select property="nameSuffixChoice">
                <html:optionsCollection property="nameSuffixChoices" />
            </html:select>
            <et:validationErrors id="errorMessage" property="NameSuffixId">
                <p><c:out value="${errorMessage}" /></p>
            </et:validationErrors>
        </td>
    </tr>
    <tr>
        <td align=right><fmt:message key="label.company" />:</td>
        <td>
            <html:text size="20" property="name" maxlength="60" />
            <et:validationErrors id="errorMessage" property="Name">
                <p><c:out value="${errorMessage}" /></p>
            </et:validationErrors>
        </td>
    </tr>
</c:if>
<tr>
    <td align=right><fmt:message key="label.number" />:</td>
    <td>
        <html:text size="30" property="number" maxlength="40" /> (*)
        <et:validationErrors id="errorMessage" property="Number">
            <p><c:out value="${errorMessage}" /></p>
        </et:validationErrors>
    </td>
</tr>
<c:if test='${partyPaymentMethod.paymentMethod.requestSecurityCode}'>
    <tr>
        <td align=right><fmt:message key="label.securityCode" />:</td>
        <td>
            <html:text size="10" property="securityCode" maxlength="10" />
            <c:if test='${partyPaymentMethod.paymentMethod.requireSecurityCode}'>
                (*)
            </c:if>
            <et:validationErrors id="errorMessage" property="SecurityCode">
                <p><c:out value="${errorMessage}" /></p>
            </et:validationErrors>
        </td>
    </tr>
</c:if>
<c:if test='${partyPaymentMethod.paymentMethod.requestExpirationDate}'>
    <tr>
        <td align=right><fmt:message key="label.expirationMonth" />:</td>
        <td>
            <html:text size="2" property="expirationMonth" maxlength="4" />
            <c:if test='${partyPaymentMethod.paymentMethod.requireExpirationDate}'>
                (*)
            </c:if>
            <et:validationErrors id="errorMessage" property="ExpirationMonth">
                <p><c:out value="${errorMessage}" /></p>
            </et:validationErrors>
        </td>
    </tr>
    <tr>
        <td align=right><fmt:message key="label.expirationYear" />:</td>
        <td>
            <html:text size="4" property="expirationYear" maxlength="6" />
            <c:if test='${partyPaymentMethod.paymentMethod.requireExpirationDate}'>
                (*)
            </c:if>
            <et:validationErrors id="errorMessage" property="ExpirationYear">
                <p><c:out value="${errorMessage}" /></p>
            </et:validationErrors>
        </td>
    </tr>
</c:if>
<c:if test='${partyPaymentMethod.paymentMethod.requestBilling}'>
    <tr>
        <td align=right><fmt:message key="label.billingContactMechanism" />:</td>
        <td>
            <html:select property="billingContactMechanismChoice">
                <html:optionsCollection property="billingContactMechanismChoices" />
            </html:select>
            <c:if test='${partyPaymentMethod.paymentMethod.requireBilling}'>
                (*)
            </c:if>
            <et:validationErrors id="errorMessage" property="BillingContactMechanismName">
                <p><c:out value="${errorMessage}" /></p>
            </et:validationErrors>
        </td>
    </tr>
</c:if>
<c:if test='${partyPaymentMethod.paymentMethod.requestIssuer}'>
    <tr>
        <td align=right><fmt:message key="label.issuerName" />:</td>
        <td>
            <html:text property="issuerName" size="40" maxlength="60" />
            <c:if test='${partyPaymentMethod.paymentMethod.requireIssuer}'>
                (*)
            </c:if>
            <et:validationErrors id="errorMessage" property="IssuerName">
                <p><c:out value="${errorMessage}" /></p>
            </et:validationErrors>
        </td>
    </tr>
    <tr>
        <td align=right><fmt:message key="label.issuerContactMechanism" />:</td>
        <td>
            <html:select property="issuerContactMechanismChoice">
                <html:optionsCollection property="issuerContactMechanismChoices" />
            </html:select>
            <et:validationErrors id="errorMessage" property="IssuerContactMechanismName">
                <p><c:out value="${errorMessage}" /></p>
            </et:validationErrors>
        </td>
    </tr>
</c:if>
