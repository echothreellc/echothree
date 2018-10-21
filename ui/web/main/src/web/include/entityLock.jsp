<%@ include file="taglibs.jsp" %>

<div id="EntityLock">
    <p>
        <c:choose>
            <c:when test="${entityLock == null}">
                Lock Details Unavailable
            </c:when>
            <c:otherwise>
                Lock Target: ${entityLock.lockTargetEntityInstance.description}, Locked By: ${entityLock.lockedByEntityInstance.description},
                Locked Time: ${entityLock.lockedTime}, Expiration Time: ${entityLock.expirationTime}
                <c:if test="${!commandResult.executionResult.hasLockErrors}">
                    <span id="entityLockStatus"></span>
                    <script type="text/javascript">
                        startEntityLockStatus(<c:out value="${entityLock.unformattedExpirationTime - entityLock.unformattedLockedTime}" />, "<c:out value='${entityLock.lockTargetEntityInstance.entityRef}' />");
                    </script>
                </c:if>
            </c:otherwise>
        </c:choose>
    </p>
</div>
