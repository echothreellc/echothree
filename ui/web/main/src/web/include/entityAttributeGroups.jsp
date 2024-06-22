<%@ include file="taglibs.jsp" %>

<c:if test='${entityAttributeGroups.size > 0}'>
    <c:if test="${entityAttributeGroupComplete == null}">
        <et:checkSecurityRoles securityRoles="EntityAttributeGroup.Review:EntityAttribute.Review:EntityListItem.Review" />
        <et:hasSecurityRole securityRole="EntityAttributeGroup.Review" var="includeEntityAttributeGroupReviewUrl" />
        <et:hasSecurityRole securityRole="EntityAttribute.Review" var="includeEntityAttributeReviewUrl" />
        <et:hasSecurityRole securityRole="EntityListItem.Review" var="includeEntityListItemReviewUrl" />
        <c:set var="entityAttributeGroupComplete" value="true"/>
    </c:if>
    <h3>Attributes</h3>
    <table>
        <c:forEach items="${entityAttributeGroups.list}" var="entityAttributeGroup">
            <tr>
                <td colspan="2">
                    <c:choose>
                        <c:when test="${includeEntityAttributeGroupReviewUrl}">
                            <c:url var="entityAttributeGroupUrl" value="/action/Core/EntityAttributeGroup/Review">
                                <c:param name="EntityAttributeGroupName" value="${entityAttributeGroup.entityAttributeGroupName}" />
                            </c:url>
                            <a href="${entityAttributeGroupUrl}"><b><c:out value="${entityAttributeGroup.description}" /></b></a>
                        </c:when>
                        <c:otherwise>
                            <b><c:out value="${entityAttributeGroup.description}" /></b>
                        </c:otherwise>
                    </c:choose>
                </td>
            </tr>
            <c:forEach items="${entityAttributeGroup.entityAttributes.list}" var="entityAttribute">
                <tr>
                    <td style="text-align: right; vertical-align: top">
                        <c:choose>
                            <c:when test="${includeEntityAttributeReviewUrl}">
                                <c:url var="entityAttributeUrl" value="/action/Core/EntityAttribute/Review">
                                    <c:param name="ComponentVendorName" value="${entityAttribute.entityType.componentVendor.componentVendorName}" />
                                    <c:param name="EntityTypeName" value="${entityAttribute.entityType.entityTypeName}" />
                                    <c:param name="EntityAttributeName" value="${entityAttribute.entityAttributeName}" />
                                </c:url>
                                <a href="${entityAttributeUrl}"><c:out value="${entityAttribute.description}" /></a>:
                            </c:when>
                            <c:otherwise>
                                <c:out value="${entityAttribute.description}" />:
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td style="vertical-align: top">
                        <c:set var="entityAttributeTypeName" value="${entityAttribute.entityAttributeType.entityAttributeTypeName}" />
                        <c:choose>
                            <c:when test="${entityAttributeTypeName == 'BOOLEAN'}">
                                <c:choose>
                                    <c:when test="${entityAttribute.entityBooleanAttribute == null}">
                                        <i><fmt:message key="phrase.notSet" /></i>
                                        <c:url var="addUrl" value="/action/Core/EntityBooleanAttribute/Add">
                                            <c:param name="EntityRef" value="${entityInstance.entityRef}" />
                                            <c:param name="EntityAttributeName" value="${entityAttribute.entityAttributeName}" />
                                            <c:param name="ReturnUrl" value="${returnUrl}" />
                                        </c:url>
                                        [<a href="${addUrl}">Add</a>]
                                    </c:when>
                                    <c:otherwise>
                                        <c:choose>
                                            <c:when test="${entityAttribute.entityBooleanAttribute.booleanAttribute}">
                                                True
                                            </c:when>
                                            <c:otherwise>
                                                False
                                            </c:otherwise>
                                        </c:choose>
                                        <c:url var="editUrl" value="/action/Core/EntityBooleanAttribute/Edit">
                                            <c:param name="EntityRef" value="${entityInstance.entityRef}" />
                                            <c:param name="EntityAttributeName" value="${entityAttribute.entityAttributeName}" />
                                            <c:param name="ReturnUrl" value="${returnUrl}" />
                                        </c:url>
                                        [<a href="${editUrl}">Edit</a>]
                                        <c:url var="clearUrl" value="/action/Core/EntityBooleanAttribute/Delete">
                                            <c:param name="EntityRef" value="${entityInstance.entityRef}" />
                                            <c:param name="EntityAttributeName" value="${entityAttribute.entityAttributeName}" />
                                            <c:param name="ReturnUrl" value="${returnUrl}" />
                                        </c:url>
                                        [<a href="${clearUrl}">Clear</a>]
                                    </c:otherwise>
                                </c:choose>
                            </c:when>
                            <c:when test="${entityAttributeTypeName == 'LISTITEM'}">
                                <c:choose>
                                    <c:when test="${entityAttribute.entityListItemAttribute == null}">
                                        <i><fmt:message key="phrase.notSet" /></i>
                                        <c:url var="addUrl" value="/action/Core/EntityListItemAttribute/Add">
                                            <c:param name="EntityRef" value="${entityInstance.entityRef}" />
                                            <c:param name="EntityAttributeName" value="${entityAttribute.entityAttributeName}" />
                                            <c:param name="ReturnUrl" value="${returnUrl}" />
                                        </c:url>
                                        [<a href="${addUrl}">Add</a>]
                                    </c:when>
                                    <c:otherwise>
                                        <c:choose>
                                            <c:when test="${includeEntityAttributeReviewUrl}">
                                                <c:url var="entityListItemUrl" value="/action/Core/EntityListItem/Review">
                                                    <c:param name="ComponentVendorName" value="${entityAttribute.entityType.componentVendor.componentVendorName}" />
                                                    <c:param name="EntityTypeName" value="${entityAttribute.entityType.entityTypeName}" />
                                                    <c:param name="EntityAttributeName" value="${entityAttribute.entityAttributeName}" />
                                                    <c:param name="EntityListItemName" value="${entityAttribute.entityListItemAttribute.entityListItem.entityListItemName}" />
                                                </c:url>
                                                <a href="${entityListItemUrl}"><c:out value="${entityAttribute.entityListItemAttribute.entityListItem.description}" /></a>
                                            </c:when>
                                            <c:otherwise>
                                                <c:out value="${entityAttribute.entityListItemAttribute.entityListItem.description}" />
                                            </c:otherwise>
                                        </c:choose>
                                        <c:url var="editUrl" value="/action/Core/EntityListItemAttribute/Edit">
                                            <c:param name="EntityRef" value="${entityInstance.entityRef}" />
                                            <c:param name="EntityAttributeName" value="${entityAttribute.entityAttributeName}" />
                                            <c:param name="ReturnUrl" value="${returnUrl}" />
                                        </c:url>
                                        [<a href="${editUrl}">Edit</a>]
                                        <c:url var="clearUrl" value="/action/Core/EntityListItemAttribute/Delete">
                                            <c:param name="EntityRef" value="${entityInstance.entityRef}" />
                                            <c:param name="EntityAttributeName" value="${entityAttribute.entityAttributeName}" />
                                            <c:param name="ReturnUrl" value="${returnUrl}" />
                                        </c:url>
                                        [<a href="${clearUrl}">Clear</a>]
                                    </c:otherwise>
                                </c:choose>
                            </c:when>
                            <c:when test="${entityAttributeTypeName == 'MULTIPLELISTITEM'}">
                                <table style="padding: 0px; border-spacing: 0px">
                                    <c:url var="addUrl" value="/action/Core/EntityMultipleListItemAttribute/Add">
                                        <c:param name="EntityRef" value="${entityInstance.entityRef}" />
                                        <c:param name="EntityAttributeName" value="${entityAttribute.entityAttributeName}" />
                                        <c:param name="ReturnUrl" value="${returnUrl}" />
                                    </c:url>
                                    <c:choose>
                                        <c:when test="${entityAttribute.entityMultipleListItemAttributes == null}">
                                            <i><fmt:message key="phrase.notSet" /></i>
                                        </c:when>
                                        <c:otherwise>
                                            <c:choose>
                                                <c:when test='${entityAttribute.entityMultipleListItemAttributes.size != 0}'>
                                                    <c:forEach items="${entityAttribute.entityMultipleListItemAttributes.list}" var="entityMultipleListItemAttribute">
                                                        <tr>
                                                            <td>
                                                                <c:choose>
                                                                    <c:when test="${includeEntityAttributeReviewUrl}">
                                                                        <c:url var="entityListItemUrl" value="/action/Core/EntityListItem/Review">
                                                                            <c:param name="ComponentVendorName" value="${entityAttribute.entityType.componentVendor.componentVendorName}" />
                                                                            <c:param name="EntityTypeName" value="${entityAttribute.entityType.entityTypeName}" />
                                                                            <c:param name="EntityAttributeName" value="${entityAttribute.entityAttributeName}" />
                                                                            <c:param name="EntityListItemName" value="${entityMultipleListItemAttribute.entityListItem.entityListItemName}" />
                                                                        </c:url>
                                                                        <a href="${entityListItemUrl}"><c:out value="${entityMultipleListItemAttribute.entityListItem.description}" /></a>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <c:out value="${entityMultipleListItemAttribute.entityListItem.description}" />
                                                                    </c:otherwise>
                                                                </c:choose>
                                                                <c:url var="deleteUrl" value="/action/Core/EntityMultipleListItemAttribute/Delete">
                                                                    <c:param name="EntityRef" value="${entityInstance.entityRef}" />
                                                                    <c:param name="EntityAttributeName" value="${entityAttribute.entityAttributeName}" />
                                                                    <c:param name="EntityListItemName" value="${entityMultipleListItemAttribute.entityListItem.entityListItemName}" />
                                                                    <c:param name="ReturnUrl" value="${returnUrl}" />
                                                                </c:url>
                                                                [<a href="${deleteUrl}">Delete</a>]
                                                            </td>
                                                        </tr>
                                                    </c:forEach>
                                                    <tr><td>[<a href="${addUrl}">Add</a>]</td></tr>
                                                </c:when>
                                                <c:otherwise>
                                                    <tr><td><i><fmt:message key="phrase.notSet" /></i> [<a href="${addUrl}">Add</a>]</td></tr>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:otherwise>
                                    </c:choose>
                                </table>
                            </c:when>
                            <c:when test="${entityAttributeTypeName == 'INTEGER'}">
                                <c:choose>
                                    <c:when test="${entityAttribute.entityIntegerAttribute == null}">
                                        <i><fmt:message key="phrase.notSet" /></i>
                                        <c:url var="addUrl" value="/action/Core/EntityIntegerAttribute/Add">
                                            <c:param name="EntityRef" value="${entityInstance.entityRef}" />
                                            <c:param name="EntityAttributeName" value="${entityAttribute.entityAttributeName}" />
                                            <c:param name="ReturnUrl" value="${returnUrl}" />
                                        </c:url>
                                        [<a href="${addUrl}">Add</a>]
                                    </c:when>
                                    <c:otherwise>
                                        <c:out value="${entityAttribute.entityIntegerAttribute.integerAttribute}" />
                                        <c:url var="editUrl" value="/action/Core/EntityIntegerAttribute/Edit">
                                            <c:param name="EntityRef" value="${entityInstance.entityRef}" />
                                            <c:param name="EntityAttributeName" value="${entityAttribute.entityAttributeName}" />
                                            <c:param name="ReturnUrl" value="${returnUrl}" />
                                        </c:url>
                                        [<a href="${editUrl}">Edit</a>]
                                        <c:url var="clearUrl" value="/action/Core/EntityIntegerAttribute/Delete">
                                            <c:param name="EntityRef" value="${entityInstance.entityRef}" />
                                            <c:param name="EntityAttributeName" value="${entityAttribute.entityAttributeName}" />
                                            <c:param name="ReturnUrl" value="${returnUrl}" />
                                        </c:url>
                                        [<a href="${clearUrl}">Clear</a>]
                                    </c:otherwise>
                                </c:choose>
                            </c:when>
                            <c:when test="${entityAttributeTypeName == 'LONG'}">
                                <c:choose>
                                    <c:when test="${entityAttribute.entityLongAttribute == null}">
                                        <i><fmt:message key="phrase.notSet" /></i>
                                        <c:url var="addUrl" value="/action/Core/EntityLongAttribute/Add">
                                            <c:param name="EntityRef" value="${entityInstance.entityRef}" />
                                            <c:param name="EntityAttributeName" value="${entityAttribute.entityAttributeName}" />
                                            <c:param name="ReturnUrl" value="${returnUrl}" />
                                        </c:url>
                                        [<a href="${addUrl}">Add</a>]
                                    </c:when>
                                    <c:otherwise>
                                        <c:out value="${entityAttribute.entityLongAttribute.longAttribute}" />
                                        <c:url var="editUrl" value="/action/Core/EntityLongAttribute/Edit">
                                            <c:param name="EntityRef" value="${entityInstance.entityRef}" />
                                            <c:param name="EntityAttributeName" value="${entityAttribute.entityAttributeName}" />
                                            <c:param name="ReturnUrl" value="${returnUrl}" />
                                        </c:url>
                                        [<a href="${editUrl}">Edit</a>]
                                        <c:url var="clearUrl" value="/action/Core/EntityLongAttribute/Delete">
                                            <c:param name="EntityRef" value="${entityInstance.entityRef}" />
                                            <c:param name="EntityAttributeName" value="${entityAttribute.entityAttributeName}" />
                                            <c:param name="ReturnUrl" value="${returnUrl}" />
                                        </c:url>
                                        [<a href="${clearUrl}">Clear</a>]
                                    </c:otherwise>
                                </c:choose>
                            </c:when>
                            <c:when test="${entityAttributeTypeName == 'NAME'}">
                                <c:choose>
                                    <c:when test="${entityAttribute.entityNameAttribute == null}">
                                        <i><fmt:message key="phrase.notSet" /></i>
                                        <c:url var="addUrl" value="/action/Core/EntityNameAttribute/Add">
                                            <c:param name="EntityRef" value="${entityInstance.entityRef}" />
                                            <c:param name="EntityAttributeName" value="${entityAttribute.entityAttributeName}" />
                                            <c:param name="ReturnUrl" value="${returnUrl}" />
                                        </c:url>
                                        [<a href="${addUrl}">Add</a>]
                                    </c:when>
                                    <c:otherwise>
                                        <c:out value="${entityAttribute.entityNameAttribute.nameAttribute}" />
                                        <c:url var="editUrl" value="/action/Core/EntityNameAttribute/Edit">
                                            <c:param name="EntityRef" value="${entityInstance.entityRef}" />
                                            <c:param name="EntityAttributeName" value="${entityAttribute.entityAttributeName}" />
                                            <c:param name="ReturnUrl" value="${returnUrl}" />
                                        </c:url>
                                        [<a href="${editUrl}">Edit</a>]
                                        <c:url var="clearUrl" value="/action/Core/EntityNameAttribute/Delete">
                                            <c:param name="EntityRef" value="${entityInstance.entityRef}" />
                                            <c:param name="EntityAttributeName" value="${entityAttribute.entityAttributeName}" />
                                            <c:param name="ReturnUrl" value="${returnUrl}" />
                                        </c:url>
                                        [<a href="${clearUrl}">Clear</a>]
                                    </c:otherwise>
                                </c:choose>
                            </c:when>
                            <c:when test="${entityAttributeTypeName == 'STRING'}">
                                <c:choose>
                                    <c:when test="${entityAttribute.entityStringAttribute == null}">
                                        <i><fmt:message key="phrase.notSet" /></i>
                                        <c:url var="addUrl" value="/action/Core/EntityStringAttribute/Add">
                                            <c:param name="EntityRef" value="${entityInstance.entityRef}" />
                                            <c:param name="EntityAttributeName" value="${entityAttribute.entityAttributeName}" />
                                            <c:param name="ReturnUrl" value="${returnUrl}" />
                                        </c:url>
                                        [<a href="${addUrl}">Add</a>]
                                    </c:when>
                                    <c:otherwise>
                                        <c:out value="${entityAttribute.entityStringAttribute.stringAttribute}" />
                                        <c:url var="editUrl" value="/action/Core/EntityStringAttribute/Edit">
                                            <c:param name="EntityRef" value="${entityInstance.entityRef}" />
                                            <c:param name="EntityAttributeName" value="${entityAttribute.entityAttributeName}" />
                                            <c:param name="LanguageIsoName" value="${entityAttribute.entityStringAttribute.language.languageIsoName}" />
                                            <c:param name="ReturnUrl" value="${returnUrl}" />
                                        </c:url>
                                        [<a href="${editUrl}">Edit</a>]
                                        <c:url var="clearUrl" value="/action/Core/EntityStringAttribute/Delete">
                                            <c:param name="EntityRef" value="${entityInstance.entityRef}" />
                                            <c:param name="EntityAttributeName" value="${entityAttribute.entityAttributeName}" />
                                            <c:param name="LanguageIsoName" value="${entityAttribute.entityStringAttribute.language.languageIsoName}" />
                                            <c:param name="ReturnUrl" value="${returnUrl}" />
                                        </c:url>
                                        [<a href="${clearUrl}">Clear</a>]
                                    </c:otherwise>
                                </c:choose>
                            </c:when>
                            <c:when test="${entityAttributeTypeName == 'GEOPOINT'}">
                                <c:choose>
                                    <c:when test="${entityAttribute.entityGeoPointAttribute == null}">
                                        <i><fmt:message key="phrase.notSet" /></i>
                                        <c:url var="addUrl" value="/action/Core/EntityGeoPointAttribute/Add">
                                            <c:param name="EntityRef" value="${entityInstance.entityRef}" />
                                            <c:param name="EntityAttributeName" value="${entityAttribute.entityAttributeName}" />
                                            <c:param name="ReturnUrl" value="${returnUrl}" />
                                        </c:url>
                                        [<a href="${addUrl}">Add</a>]
                                    </c:when>
                                    <c:otherwise>
                                        <c:out value="${entityAttribute.entityGeoPointAttribute.latitude}" />&deg;,
                                        <c:choose>
                                            <c:when test="${entityAttribute.entityGeoPointAttribute.elevation != null || entityAttribute.entityGeoPointAttribute.altitude != null}">
                                                <c:out value="${entityAttribute.entityGeoPointAttribute.longitude}" />&deg;,
                                            </c:when>
                                            <c:otherwise>
                                                <c:out value="${entityAttribute.entityGeoPointAttribute.longitude}" />&deg;
                                            </c:otherwise>
                                        </c:choose>
                                        <c:if test='${entityAttribute.entityGeoPointAttribute.elevation != null}'>
                                            <c:choose>
                                                <c:when test="${entityAttribute.entityGeoPointAttribute.altitude != null}">
                                                    Elevation: <c:out value="${entityAttribute.entityGeoPointAttribute.elevation}" />,
                                                </c:when>
                                                <c:otherwise>
                                                    Elevation: <c:out value="${entityAttribute.entityGeoPointAttribute.elevation}" />
                                                </c:otherwise>
                                            </c:choose>
                                        </c:if>
                                        <c:if test='${entityAttribute.entityGeoPointAttribute.altitude != null}'>
                                            Altitude: <c:out value="${entityAttribute.entityGeoPointAttribute.altitude}" />
                                        </c:if>
                                        <c:url var="editUrl" value="/action/Core/EntityGeoPointAttribute/Edit">
                                            <c:param name="EntityRef" value="${entityInstance.entityRef}" />
                                            <c:param name="EntityAttributeName" value="${entityAttribute.entityAttributeName}" />
                                            <c:param name="ReturnUrl" value="${returnUrl}" />
                                        </c:url>
                                        [<a href="${editUrl}">Edit</a>]
                                        <c:url var="clearUrl" value="/action/Core/EntityGeoPointAttribute/Delete">
                                            <c:param name="EntityRef" value="${entityInstance.entityRef}" />
                                            <c:param name="EntityAttributeName" value="${entityAttribute.entityAttributeName}" />
                                            <c:param name="ReturnUrl" value="${returnUrl}" />
                                        </c:url>
                                        [<a href="${clearUrl}">Clear</a>]
                                    </c:otherwise>
                                </c:choose>
                            </c:when>
                            <c:when test="${entityAttributeTypeName == 'DATE'}">
                                <c:choose>
                                    <c:when test="${entityAttribute.entityDateAttribute == null}">
                                        <i><fmt:message key="phrase.notSet" /></i>
                                        <c:url var="addUrl" value="/action/Core/EntityDateAttribute/Add">
                                            <c:param name="EntityRef" value="${entityInstance.entityRef}" />
                                            <c:param name="EntityAttributeName" value="${entityAttribute.entityAttributeName}" />
                                            <c:param name="ReturnUrl" value="${returnUrl}" />
                                        </c:url>
                                        [<a href="${addUrl}">Add</a>]
                                    </c:when>
                                    <c:otherwise>
                                        <c:out value="${entityAttribute.entityDateAttribute.dateAttribute}" />
                                        <c:url var="editUrl" value="/action/Core/EntityDateAttribute/Edit">
                                            <c:param name="EntityRef" value="${entityInstance.entityRef}" />
                                            <c:param name="EntityAttributeName" value="${entityAttribute.entityAttributeName}" />
                                            <c:param name="ReturnUrl" value="${returnUrl}" />
                                        </c:url>
                                        [<a href="${editUrl}">Edit</a>]
                                        <c:url var="clearUrl" value="/action/Core/EntityDateAttribute/Delete">
                                            <c:param name="EntityRef" value="${entityInstance.entityRef}" />
                                            <c:param name="EntityAttributeName" value="${entityAttribute.entityAttributeName}" />
                                            <c:param name="ReturnUrl" value="${returnUrl}" />
                                        </c:url>
                                        [<a href="${clearUrl}">Clear</a>]
                                    </c:otherwise>
                                </c:choose>
                            </c:when>
                            <c:when test="${entityAttributeTypeName == 'TIME'}">
                                <c:choose>
                                    <c:when test="${entityAttribute.entityTimeAttribute == null}">
                                        <i><fmt:message key="phrase.notSet" /></i>
                                        <c:url var="addUrl" value="/action/Core/EntityTimeAttribute/Add">
                                            <c:param name="EntityRef" value="${entityInstance.entityRef}" />
                                            <c:param name="EntityAttributeName" value="${entityAttribute.entityAttributeName}" />
                                            <c:param name="ReturnUrl" value="${returnUrl}" />
                                        </c:url>
                                        [<a href="${addUrl}">Add</a>]
                                    </c:when>
                                    <c:otherwise>
                                        <c:out value="${entityAttribute.entityTimeAttribute.timeAttribute}" />
                                        <c:url var="editUrl" value="/action/Core/EntityTimeAttribute/Edit">
                                            <c:param name="EntityRef" value="${entityInstance.entityRef}" />
                                            <c:param name="EntityAttributeName" value="${entityAttribute.entityAttributeName}" />
                                            <c:param name="ReturnUrl" value="${returnUrl}" />
                                        </c:url>
                                        [<a href="${editUrl}">Edit</a>]
                                        <c:url var="clearUrl" value="/action/Core/EntityTimeAttribute/Delete">
                                            <c:param name="EntityRef" value="${entityInstance.entityRef}" />
                                            <c:param name="EntityAttributeName" value="${entityAttribute.entityAttributeName}" />
                                            <c:param name="ReturnUrl" value="${returnUrl}" />
                                        </c:url>
                                        [<a href="${clearUrl}">Clear</a>]
                                    </c:otherwise>
                                </c:choose>
                            </c:when>
                            <c:when test="${entityAttributeTypeName == 'BLOB'}">
                                <c:choose>
                                    <c:when test="${entityAttribute.entityBlobAttribute == null}">
                                        <i><fmt:message key="phrase.notSet" /></i>
                                        <c:url var="addUrl" value="/action/Core/EntityBlobAttribute/Add">
                                            <c:param name="EntityRef" value="${entityInstance.entityRef}" />
                                            <c:param name="EntityAttributeName" value="${entityAttribute.entityAttributeName}" />
                                            <c:param name="ReturnUrl" value="${returnUrl}" />
                                        </c:url>
                                        [<a href="${addUrl}">Add</a>]
                                    </c:when>
                                    <c:otherwise>
                                        <c:url var="reviewUrl" value="/action/Core/EntityBlobAttribute/Review">
                                            <c:param name="EntityRef" value="${entityInstance.entityRef}" />
                                            <c:param name="EntityAttributeName" value="${entityAttribute.entityAttributeName}" />
                                            <c:param name="LanguageIsoName" value="${entityAttribute.entityBlobAttribute.language.languageIsoName}" />
                                            <c:param name="ReturnUrl" value="${returnUrl}" />
                                        </c:url>
                                        [<a href="${reviewUrl}">View</a>]
                                        <c:url var="editUrl" value="/action/Core/EntityBlobAttribute/Edit">
                                            <c:param name="EntityRef" value="${entityInstance.entityRef}" />
                                            <c:param name="EntityAttributeName" value="${entityAttribute.entityAttributeName}" />
                                            <c:param name="LanguageIsoName" value="${entityAttribute.entityBlobAttribute.language.languageIsoName}" />
                                            <c:param name="ReturnUrl" value="${returnUrl}" />
                                        </c:url>
                                        [<a href="${editUrl}">Edit</a>]
                                        <c:url var="clearUrl" value="/action/Core/EntityBlobAttribute/Delete">
                                            <c:param name="EntityRef" value="${entityInstance.entityRef}" />
                                            <c:param name="EntityAttributeName" value="${entityAttribute.entityAttributeName}" />
                                            <c:param name="LanguageIsoName" value="${entityAttribute.entityBlobAttribute.language.languageIsoName}" />
                                            <c:param name="ReturnUrl" value="${returnUrl}" />
                                        </c:url>
                                        [<a href="${clearUrl}">Clear</a>]
                                    </c:otherwise>
                                </c:choose>
                            </c:when>
                            <c:when test="${entityAttributeTypeName == 'CLOB'}">
                                <c:choose>
                                    <c:when test="${entityAttribute.entityClobAttribute == null}">
                                        <i><fmt:message key="phrase.notSet" /></i>
                                        <c:url var="addUrl" value="/action/Core/EntityClobAttribute/Add">
                                            <c:param name="EntityRef" value="${entityInstance.entityRef}" />
                                            <c:param name="EntityAttributeName" value="${entityAttribute.entityAttributeName}" />
                                            <c:param name="ReturnUrl" value="${returnUrl}" />
                                        </c:url>
                                        [<a href="${addUrl}">Add</a>]
                                    </c:when>
                                    <c:otherwise>
                                        <c:url var="reviewUrl" value="/action/Core/EntityClobAttribute/Review">
                                            <c:param name="EntityRef" value="${entityInstance.entityRef}" />
                                            <c:param name="EntityAttributeName" value="${entityAttribute.entityAttributeName}" />
                                            <c:param name="LanguageIsoName" value="${entityAttribute.entityClobAttribute.language.languageIsoName}" />
                                            <c:param name="ReturnUrl" value="${returnUrl}" />
                                        </c:url>
                                        [<a href="${reviewUrl}">View</a>]
                                        <c:url var="editUrl" value="/action/Core/EntityClobAttribute/Edit">
                                            <c:param name="EntityRef" value="${entityInstance.entityRef}" />
                                            <c:param name="EntityAttributeName" value="${entityAttribute.entityAttributeName}" />
                                            <c:param name="LanguageIsoName" value="${entityAttribute.entityClobAttribute.language.languageIsoName}" />
                                            <c:param name="ReturnUrl" value="${returnUrl}" />
                                        </c:url>
                                        [<a href="${editUrl}">Edit</a>]
                                        <c:url var="clearUrl" value="/action/Core/EntityClobAttribute/Delete">
                                            <c:param name="EntityRef" value="${entityInstance.entityRef}" />
                                            <c:param name="EntityAttributeName" value="${entityAttribute.entityAttributeName}" />
                                            <c:param name="LanguageIsoName" value="${entityAttribute.entityClobAttribute.language.languageIsoName}" />
                                            <c:param name="ReturnUrl" value="${returnUrl}" />
                                        </c:url>
                                        [<a href="${clearUrl}">Clear</a>]
                                    </c:otherwise>
                                </c:choose>
                            </c:when>
                            <c:when test="${entityAttributeTypeName == 'ENTITY'}">
                                <c:choose>
                                    <c:when test="${entityAttribute.entityEntityAttribute == null}">
                                        <i><fmt:message key="phrase.notSet" /></i>
                                        <c:url var="addUrl" value="/action/Core/EntityEntityAttribute/Add">
                                            <c:param name="EntityRef" value="${entityInstance.entityRef}" />
                                            <c:param name="EntityAttributeName" value="${entityAttribute.entityAttributeName}" />
                                            <c:param name="ReturnUrl" value="${returnUrl}" />
                                        </c:url>
                                        [<a href="${addUrl}">Add</a>]
                                    </c:when>
                                    <c:otherwise>
                                        <c:set var="previousEntityInstance" scope="request" value="${entityInstance}" />
                                        <c:set var="entityInstance" scope="request" value="${entityAttribute.entityEntityAttribute.entityInstanceAttribute}" />
                                        <jsp:include page="targetAsReviewLink.jsp" />
                                        <c:set var="entityInstance" scope="request" value="${previousEntityInstance}" />
                                        <c:url var="editUrl" value="/action/Core/EntityEntityAttribute/Edit">
                                            <c:param name="EntityRef" value="${entityInstance.entityRef}" />
                                            <c:param name="EntityAttributeName" value="${entityAttribute.entityAttributeName}" />
                                            <c:param name="ReturnUrl" value="${returnUrl}" />
                                        </c:url>
                                        [<a href="${editUrl}">Edit</a>]
                                        <c:url var="clearUrl" value="/action/Core/EntityEntityAttribute/Delete">
                                            <c:param name="EntityRef" value="${entityInstance.entityRef}" />
                                            <c:param name="EntityAttributeName" value="${entityAttribute.entityAttributeName}" />
                                            <c:param name="ReturnUrl" value="${returnUrl}" />
                                        </c:url>
                                        [<a href="${clearUrl}">Clear</a>]
                                    </c:otherwise>
                                </c:choose>
                            </c:when>
                            <c:when test="${entityAttributeTypeName == 'COLLECTION'}">
                                <c:choose>
                                    <c:when test="${entityAttribute.entityCollectionAttributes == null}">
                                        <i><fmt:message key="phrase.notSet" /></i>
                                    </c:when>
                                    <c:otherwise>
                                        <c:choose>
                                            <c:when test='${entityAttribute.entityCollectionAttributes.size != 0}'>
                                                <c:forEach items="${entityAttribute.entityCollectionAttributes.list}" var="entityCollectionAttribute">
                                                    <c:set var="previousEntityInstance" scope="request" value="${entityInstance}" />
                                                    <c:set var="entityInstance" scope="request" value="${entityCollectionAttribute.entityInstanceAttribute}" />
                                                    <jsp:include page="targetAsReviewLink.jsp" />
                                                    <c:set var="entityInstance" scope="request" value="${previousEntityInstance}" />
                                                    <c:url var="deleteUrl" value="/action/Core/EntityCollectionAttribute/Delete">
                                                        <c:param name="EntityRef" value="${entityInstance.entityRef}" />
                                                        <c:param name="EntityAttributeName" value="${entityAttribute.entityAttributeName}" />
                                                        <c:param name="EntityRefAttribute" value="${entityCollectionAttribute.entityInstanceAttribute.entityRef}" />
                                                        <c:param name="ReturnUrl" value="${returnUrl}" />
                                                    </c:url>
                                                    [<a href="${deleteUrl}">Delete</a>],
                                                </c:forEach>
                                            </c:when>
                                            <c:otherwise>
                                                <i><fmt:message key="phrase.notSet" /></i>
                                            </c:otherwise>
                                        </c:choose>
                                        <c:url var="addUrl" value="/action/Core/EntityCollectionAttribute/Add">
                                            <c:param name="EntityRef" value="${entityInstance.entityRef}" />
                                            <c:param name="EntityAttributeName" value="${entityAttribute.entityAttributeName}" />
                                            <c:param name="ReturnUrl" value="${returnUrl}" />
                                        </c:url>
                                        [<a href="${addUrl}">Add</a>]
                                    </c:otherwise>
                                </c:choose>
                            </c:when>
                        </c:choose>
                    </td>
                </tr>
            </c:forEach>
        </c:forEach>
    </table>
    <br />
</c:if>
