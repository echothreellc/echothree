<%@ include file="taglibs.jsp" %>

<c:if test='${partyTypePasswordStringPolicy != null}'>
    <c:if test="${partyTypePasswordStringPolicy.allowChange == false || partyTypePasswordStringPolicy.passwordHistory != null || partyTypePasswordStringPolicy.minimumPasswordLifetime != null || partyTypePasswordStringPolicy.minimumLength != null || partyTypePasswordStringPolicy.maximumLength != null || partyTypePasswordStringPolicy.requiredDigitCount != null || partyTypePasswordStringPolicy.requiredLetterCount != null || partyTypePasswordStringPolicy.requiredUpperCaseCount != null || partyTypePasswordStringPolicy.requiredLowerCaseCount != null || partyTypePasswordStringPolicy.maximumRepeated != null || partyTypePasswordStringPolicy.minimumCharacterTypes != null}">
        The new password must follow these guidelines:
        <ul>
            <c:if test="${partyTypePasswordStringPolicy.allowChange == false}">
                <li>You may not change the password.</li>
            </c:if>
            <c:if test="${partyTypePasswordStringPolicy.passwordHistory != null}">
                <li>
                    <c:choose>
                        <c:when test='${partyTypePasswordStringPolicy.passwordHistory == 1}'>
                            The new password may not be the same as the current password.
                        </c:when>
                        <c:otherwise>
                            The new password may not be the same as any of the previous <c:out value="${partyTypePasswordStringPolicy.passwordHistory}" /> passwords.
                        </c:otherwise>
                    </c:choose>
                </li>
            </c:if>
            <c:if test="${partyTypePasswordStringPolicy.minimumPasswordLifetime != null}">
                <li> You may only change the password if the current one was set <c:out value="${partyTypePasswordStringPolicy.minimumPasswordLifetime}" /> ago.</li>
            </c:if>
            <c:if test="${partyTypePasswordStringPolicy.minimumLength != null}">
                <li> The password must be at least <c:out value="${partyTypePasswordStringPolicy.minimumLength}" />
                    <c:choose>
                        <c:when test='${partyTypePasswordStringPolicy.minimumLength == 1}'>
                            character.
                        </c:when>
                        <c:otherwise>
                            characters.
                        </c:otherwise>
                    </c:choose>
                </li>
            </c:if>
            <c:if test="${partyTypePasswordStringPolicy.maximumLength != null}">
                <li> The password must be at least <c:out value="${partyTypePasswordStringPolicy.maximumLength}" />
                    <c:choose>
                        <c:when test='${partyTypePasswordStringPolicy.maximumLength == 1}'>
                            character.
                        </c:when>
                        <c:otherwise>
                            characters.
                        </c:otherwise>
                    </c:choose>
                </li>
            </c:if>
            <c:if test="${partyTypePasswordStringPolicy.requiredDigitCount != null}">
                <li> The password must contain at least <c:out value="${partyTypePasswordStringPolicy.requiredDigitCount}" />
                    <c:choose>
                        <c:when test='${partyTypePasswordStringPolicy.requiredDigitCount == 1}'>
                            digit.
                        </c:when>
                        <c:otherwise>
                            digits.
                        </c:otherwise>
                    </c:choose>
                </li>
            </c:if>
            <c:if test="${partyTypePasswordStringPolicy.requiredLetterCount != null}">
                <li> The password must contain at least <c:out value="${partyTypePasswordStringPolicy.requiredLetterCount}" />
                    <c:choose>
                        <c:when test='${partyTypePasswordStringPolicy.requiredLetterCount == 1}'>
                            letter.
                        </c:when>
                        <c:otherwise>
                            letters.
                        </c:otherwise>
                    </c:choose>
                </li>
            </c:if>
            <c:if test="${partyTypePasswordStringPolicy.requiredUpperCaseCount != null}">
                <li> The password must contain at least <c:out value="${partyTypePasswordStringPolicy.requiredUpperCaseCount}" /> upper case
                    <c:choose>
                        <c:when test='${partyTypePasswordStringPolicy.requiredUpperCaseCount == 1}'>
                            character.
                        </c:when>
                        <c:otherwise>
                            characters.
                        </c:otherwise>
                    </c:choose>
                </li>
            </c:if>
            <c:if test="${partyTypePasswordStringPolicy.requiredLowerCaseCount != null}">
                <li> The password must contain at least <c:out value="${partyTypePasswordStringPolicy.requiredLowerCaseCount}" /> lower case
                    <c:choose>
                        <c:when test='${partyTypePasswordStringPolicy.requiredLowerCaseCount == 1}'>
                            character.
                        </c:when>
                        <c:otherwise>
                            characters.
                        </c:otherwise>
                    </c:choose>
                </li>
            </c:if>
            <c:if test="${partyTypePasswordStringPolicy.maximumRepeated != null}">
                <li> The password must contain at least <c:out value="${partyTypePasswordStringPolicy.maximumRepeated}" />
                    <c:choose>
                        <c:when test='${partyTypePasswordStringPolicy.maximumRepeated == 1}'>
                            digit.
                        </c:when>
                        <c:otherwise>
                            digits.
                        </c:otherwise>
                    </c:choose>
                </li>
            </c:if>
            <c:if test="${partyTypePasswordStringPolicy.minimumCharacterTypes != null}">
                <li> The password must contain at least <c:out value="${partyTypePasswordStringPolicy.minimumCharacterTypes}" />
                    <c:choose>
                        <c:when test='${partyTypePasswordStringPolicy.minimumCharacterTypes == 1}'>
                            type
                        </c:when>
                        <c:otherwise>
                            types
                        </c:otherwise>
                    </c:choose>
                    of characters.
                </li>
            </c:if>
        </ul>
    </c:if>
</c:if>
