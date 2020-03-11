<%@ include file="taglibs.jsp" %>

<c:if test='${tagScopes.size > 0}'>
    <c:if test="${tagScopesSetupComplete == null}">
        <et:checkSecurityRoles securityRoles="TagScope.Review:EntityTag.Search:EntityTag.Delete:EntityTag.Create" />
        <et:hasSecurityRole securityRole="TagScope.Review" var="includeTagScopeReviewUrl" />
        <et:hasSecurityRole securityRole="EntityTag.Search" var="includeEntityTagSearchUrl" />
        <et:hasSecurityRole securityRole="EntityTag.Delete" var="includeEntityTagDeleteUrl" />
        <et:hasSecurityRole securityRole="EntityTag.Create" var="includeEntityTagCreateUrl" />
        <c:set var="tagScopesSetupComplete" value="true"/>
    </c:if>
    <h3>Tags</h3>
    <table>
        <c:forEach items="${tagScopes.list}" var="tagScope">
            <tr>
                <td align="right">
                    <c:choose>
                        <c:when test="${includeTagScopeReviewUrl}">
                            <c:url var="tagScopeUrl" value="/action/Core/TagScope/Review">
                                <c:param name="TagScopeName" value="${tagScope.tagScopeName}" />
                            </c:url>
                            <a href="${tagScopeUrl}"><c:out value="${tagScope.description}" /></a>:
                        </c:when>
                        <c:otherwise>
                            <c:out value="${tagScope.description}" />:
                        </c:otherwise>
                    </c:choose>
                </td>
                <td>
                    <c:choose>
                        <c:when test="${tagScope.tags.size == 0}">
                            <i>None.</i>&nbsp;&nbsp;
                        </c:when>
                        <c:otherwise>
                            <c:forEach items="${tagScope.tags.list}" var="tag">
                                <c:choose>
                                    <c:when test="${includeEntityTagSearchUrl}">
                                        <c:url var="entityTagsUrl" value="/action/Core/EntityTag/Search">
                                            <c:param name="TagScopeName" value="${tagScope.tagScopeName}" />
                                            <c:param name="TagName" value="${tag.tagName}" />
                                        </c:url>
                                        <a href="${entityTagsUrl}"><c:out value="${tag.tagName}" /></a>
                                    </c:when>
                                    <c:otherwise>
                                        <c:out value="${tag.tagName}" />
                                    </c:otherwise>
                                </c:choose>
                                <c:if test="${includeEntityTagDeleteUrl}">
                                    <c:url var="entityTagDeleteUrl" value="/action/Core/EntityTag/Delete">
                                        <c:param name="TagScopeName" value="${tagScope.tagScopeName}" />
                                        <c:param name="EntityRef" value="${entityInstance.entityRef}" />
                                        <c:param name="TagName" value="${tag.tagName}" />
                                        <c:param name="ReturnUrl" value="${returnUrl}" />
                                    </c:url>
                                    [<a href="${entityTagDeleteUrl}">X</a>]
                                </c:if>
                                &nbsp;&nbsp;
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                </td>
                <c:if test="${includeEntityTagCreateUrl}">
                    <td>
                        <html:form action="/Core/EntityTag/Add" method="POST">
                            <html:hidden property="tagScopeName" value="${tagScope.tagScopeName}" />
                            <html:hidden property="entityRef" value="${entityInstance.entityRef}" />
                            <html:hidden property="returnUrl" value="${returnUrl}" />
                            <html:text property="tagName" size="10" maxlength="40" />
                            <html:hidden property="submitButton" />
                            <html:submit value="Add" onclick="onSubmitDisable(this);" />
                        </html:form>
                    </td>
                </c:if>
            </tr>
        </c:forEach>
    </table>
    <br />
</c:if>
