<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>

<!--                                                                                  -->
<!-- Copyright 2002-2026 Echo Three, LLC                                              -->
<!--                                                                                  -->
<!-- Licensed under the Apache License, Version 2.0 (the "License");                  -->
<!-- you may not use this file except in compliance with the License.                 -->
<!-- You may obtain a copy of the License at                                          -->
<!--                                                                                  -->
<!--     http://www.apache.org/licenses/LICENSE-2.0                                   -->
<!--                                                                                  -->
<!-- Unless required by applicable law or agreed to in writing, software              -->
<!-- distributed under the License is distributed on an "AS IS" BASIS,                -->
<!-- WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.         -->
<!-- See the License for the specific language governing permissions and              -->
<!-- limitations under the License.                                                   -->
<!--                                                                                  -->

<%@ include file="../../include/taglibs.jsp" %>

<html:html xhtml="true">
    <head>
        <title>Review (<c:out value="${entityType.entityTypeName}" />)</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Core/Main" />">Core</a> &gt;&gt;
                <a href="<c:url value="/action/Core/ComponentVendor/Main" />">Component Vendors</a> &gt;&gt;
                <c:url var="entityTypesUrl" value="/action/Core/EntityType/Main">
                    <c:param name="ComponentVendorName" value="${entityType.componentVendor.componentVendorName}" />
                </c:url>
                <a href="${entityTypesUrl}">Entity Types</a> &gt;&gt;
                Review (<c:out value="${entityType.entityTypeName}" />)
            </h2>
        </div>
        <div id="Content">
            <et:checkSecurityRoles securityRoles="ComponentVendor.Review:EntityAliasType.List:EntityAttribute.List:CommentType.List:RatingType.List:MessageType.List:EntityInstance.List:Event.List" />
            <c:choose>
                <c:when test="${entityType.description != null}">
                    <p><font size="+2"><b><et:appearance appearance="${entityType.entityInstance.entityAppearance.appearance}"><c:out value="${entityType.description}" /></et:appearance></b></font></p>
                    <p><font size="+1"><et:appearance appearance="${entityType.entityInstance.entityAppearance.appearance}">${entityType.entityTypeName}</et:appearance></font></p>
                </c:when>
                <c:otherwise>
                    <p><font size="+2"><b><et:appearance appearance="${entityType.entityInstance.entityAppearance.appearance}"><c:out value="${entityType.entityTypeName}" /></et:appearance></b></font></p>
                </c:otherwise>
            </c:choose>
            <br />
            <fmt:message key="label.componentVendor" />:
            <et:hasSecurityRole securityRoles="ComponentVendor.Review">
                <c:set var="showComponentVendorAsLink" value="true" />
            </et:hasSecurityRole>
            <c:choose>
                <c:when test="${showComponentVendorAsLink}">
                    <c:url var="componentVendorUrl" value="/action/Core/ComponentVendor/Review">
                        <c:param name="ComponentVendorName" value="${entityType.componentVendor.componentVendorName}" />
                    </c:url>
                    <a href="${componentVendorUrl}"><et:appearance appearance="${entityType.componentVendor.entityInstance.entityAppearance.appearance}"><c:out value="${entityType.componentVendor.description}" /></et:appearance></a>
                </c:when>
                <c:otherwise>
                    <et:appearance appearance="${entityType.componentVendor.entityInstance.entityAppearance.appearance}"><c:out value="${entityType.componentVendor.description}" /></et:appearance>
                </c:otherwise>
            </c:choose>
            <br />
            <fmt:message key="label.entityTypeName" />: ${entityType.entityTypeName}<br />
            <fmt:message key="label.keepAllHistory" />:
            <c:choose>
                <c:when test="${entityType.keepAllHistory}">
                    <fmt:message key="phrase.yes" />
                </c:when>
                <c:otherwise>
                    <fmt:message key="phrase.no" />
                </c:otherwise>
            </c:choose>
            <br />
            <fmt:message key="label.lockTimeout" />:
            <c:choose>
                <c:when test="${entityType.lockTimeout == null}">
                    <i>Default</i>
                </c:when>
                <c:otherwise>
                    <c:out value="${entityType.lockTimeout}" />
                </c:otherwise>
            </c:choose>
            <br />
            <fmt:message key="label.isExtensible" />:
            <c:choose>
                <c:when test="${entityType.isExtensible}">
                    <fmt:message key="phrase.yes" />
                </c:when>
                <c:otherwise>
                    <fmt:message key="phrase.no" />
                </c:otherwise>
            </c:choose>
            <br />
            <fmt:message key="label.sortOrder" />: ${entityType.sortOrder}<br />
            <fmt:message key="label.description" />: ${entityType.description}<br />
            <br />
            <c:if test="${entityType.isExtensible}">
                <et:hasSecurityRole securityRole="EntityAliasType.List">
                    <h2>Entity Alias Types</h2>
                    <et:checkSecurityRoles securityRoles="EntityAliasType.Create:EntityAliasType.Edit:EntityAliasType.Delete:EntityAliasType.Review:EntityAliasType.Description" />
                    <et:hasSecurityRole securityRole="EntityAliasType.Create">
                        <c:url var="addUrl" value="/action/Core/EntityAliasType/Add">
                            <c:param name="ComponentVendorName" value="${entityType.componentVendor.componentVendorName}" />
                            <c:param name="EntityTypeName" value="${entityType.entityTypeName}" />
                        </c:url>
                        <p><a href="${addUrl}">Add Entity Alias Type.</a></p>
                    </et:hasSecurityRole>
                    <c:choose>
                        <c:when test='${entityType.entityAliasTypes.size == 0}'>
                            <br />
                        </c:when>
                        <c:otherwise>
                            <et:hasSecurityRole securityRole="EntityAliasType.Review" var="includeReviewUrl" />
                            <display:table name="entityType.entityAliasTypes.list" id="entityAliasType" class="displaytag">
                                <display:column titleKey="columnTitle.name">
                                    <c:choose>
                                        <c:when test="${includeReviewUrl}">
                                            <c:url var="reviewUrl" value="/action/Core/EntityAliasType/Review">
                                                <c:param name="ComponentVendorName" value="${entityAliasType.entityType.componentVendor.componentVendorName}" />
                                                <c:param name="EntityTypeName" value="${entityAliasType.entityType.entityTypeName}" />
                                                <c:param name="EntityAliasTypeName" value="${entityAliasType.entityAliasTypeName}" />
                                            </c:url>
                                            <a href="${reviewUrl}"><c:out value="${entityAliasType.entityAliasTypeName}" /></a>
                                        </c:when>
                                        <c:otherwise>
                                            <c:out value="${entityAliasType.entityAliasTypeName}" />
                                        </c:otherwise>
                                    </c:choose>
                                </display:column>
                                <display:column titleKey="columnTitle.description">
                                    <et:appearance appearance="${entityAliasType.entityInstance.entityAppearance.appearance}"><c:out value="${entityAliasType.description}" /></et:appearance>
                                </display:column>
                                <display:column titleKey="columnTitle.validationPattern">
                                    <c:out value="${entityAliasType.validationPattern}" />
                                </display:column>
                                <display:column titleKey="columnTitle.default">
                                    <c:choose>
                                        <c:when test="${entityAliasType.isDefault}">
                                            Default
                                        </c:when>
                                        <c:otherwise>
                                            <et:hasSecurityRole securityRole="EntityAliasType.Edit">
                                                <c:url var="setDefaultUrl" value="/action/Core/EntityAliasType/SetDefault">
                                                    <c:param name="ComponentVendorName" value="${entityAliasType.entityType.componentVendor.componentVendorName}" />
                                                    <c:param name="EntityTypeName" value="${entityAliasType.entityType.entityTypeName}" />
                                                    <c:param name="EntityAliasTypeName" value="${entityAliasType.entityAliasTypeName}" />
                                                </c:url>
                                                <a href="${setDefaultUrl}">Set Default</a>
                                            </et:hasSecurityRole>
                                        </c:otherwise>
                                    </c:choose>
                                </display:column>
                                <display:column titleKey="columnTitle.sortOrder">
                                    <c:out value="${entityAliasType.sortOrder}" />
                                </display:column>
                                <display:column>
                                    <et:hasSecurityRole securityRole="EntityAliasType.Edit">
                                        <c:url var="editUrl" value="/action/Core/EntityAliasType/Edit">
                                            <c:param name="ComponentVendorName" value="${entityAliasType.entityType.componentVendor.componentVendorName}" />
                                            <c:param name="EntityTypeName" value="${entityAliasType.entityType.entityTypeName}" />
                                            <c:param name="OriginalEntityAliasTypeName" value="${entityAliasType.entityAliasTypeName}" />
                                        </c:url>
                                        <a href="${editUrl}">Edit</a>
                                    </et:hasSecurityRole>
                                    <et:hasSecurityRole securityRole="EntityAliasType.Description">
                                        <c:url var="descriptionsUrl" value="/action/Core/EntityAliasType/Description">
                                            <c:param name="ComponentVendorName" value="${entityAliasType.entityType.componentVendor.componentVendorName}" />
                                            <c:param name="EntityTypeName" value="${entityAliasType.entityType.entityTypeName}" />
                                            <c:param name="EntityAliasTypeName" value="${entityAliasType.entityAliasTypeName}" />
                                        </c:url>
                                        <a href="${descriptionsUrl}">Descriptions</a>
                                    </et:hasSecurityRole>
                                    <et:hasSecurityRole securityRole="EntityAliasType.Delete">
                                        <c:url var="deleteUrl" value="/action/Core/EntityAliasType/Delete">
                                            <c:param name="ComponentVendorName" value="${entityAliasType.entityType.componentVendor.componentVendorName}" />
                                            <c:param name="EntityTypeName" value="${entityAliasType.entityType.entityTypeName}" />
                                            <c:param name="EntityAliasTypeName" value="${entityAliasType.entityAliasTypeName}" />
                                        </c:url>
                                        <a href="${deleteUrl}">Delete</a>
                                    </et:hasSecurityRole>
                                </display:column>
                                <et:hasSecurityRole securityRole="Event.List">
                                    <display:column>
                                        <c:url var="eventsUrl" value="/action/Core/Event/Main">
                                            <c:param name="EntityRef" value="${entityAliasType.entityInstance.entityRef}" />
                                        </c:url>
                                        <a href="${eventsUrl}">Events</a>
                                    </display:column>
                                </et:hasSecurityRole>
                            </display:table>
                        </c:otherwise>
                    </c:choose>
                    <br />
                </et:hasSecurityRole>
                <et:hasSecurityRole securityRole="EntityAttribute.List">
                    <h2>Entity Attributes</h2>
                    <et:checkSecurityRoles securityRoles="EntityAttribute.Create:EntityAttribute.Edit:EntityAttribute.Delete:EntityAttribute.Review:EntityAttribute.Description:EntityAttribute.EntityAttributeEntityAttributeGroup:EntityAttributeEntityType.List:EntityListItem.List:EntityIntegerRange.List:EntityLongRange.List" />
                    <et:hasSecurityRole securityRoles="EntityAttribute.Edit:EntityAttribute.Description:EntityAttribute.Delete">
                        <c:set var="linksInSecondRow" value="true" />
                    </et:hasSecurityRole>
                    <et:hasSecurityRole securityRole="EntityAttribute.Create">
                        <c:url var="addUrl" value="/action/Core/EntityAttribute/Add">
                            <c:param name="ComponentVendorName" value="${entityType.componentVendor.componentVendorName}" />
                            <c:param name="EntityTypeName" value="${entityType.entityTypeName}" />
                        </c:url>
                        <p><a href="${addUrl}">Add Entity Attribute.</a></p>
                    </et:hasSecurityRole>
                    <c:choose>
                        <c:when test='${entityType.entityAttributes.size == 0}'>
                            <br />
                        </c:when>
                        <c:otherwise>
                            <et:hasSecurityRole securityRole="EntityAttribute.Review" var="includeReviewUrl" />
                            <display:table name="entityType.entityAttributes.list" id="entityAttribute" class="displaytag">
                                <display:column titleKey="columnTitle.name">
                                    <c:choose>
                                        <c:when test="${includeReviewUrl}">
                                            <c:url var="reviewUrl" value="/action/Core/EntityAttribute/Review">
                                                <c:param name="ComponentVendorName" value="${entityAttribute.entityType.componentVendor.componentVendorName}" />
                                                <c:param name="EntityTypeName" value="${entityAttribute.entityType.entityTypeName}" />
                                                <c:param name="EntityAttributeName" value="${entityAttribute.entityAttributeName}" />
                                            </c:url>
                                            <a href="${reviewUrl}"><c:out value="${entityAttribute.entityAttributeName}" /></a>
                                        </c:when>
                                        <c:otherwise>
                                            <c:out value="${entityAttribute.entityAttributeName}" />
                                        </c:otherwise>
                                    </c:choose>
                                </display:column>
                                <display:column titleKey="columnTitle.description">
                                    <c:out value="${entityAttribute.description}" />
                                </display:column>
                                <display:column titleKey="columnTitle.type">
                                    <c:out value="${entityAttribute.entityAttributeType.description}" />
                                </display:column>
                                <display:column titleKey="columnTitle.trackRevisions">
                                    <c:choose>
                                        <c:when test="${entityAttribute.trackRevisions}">
                                            <fmt:message key="phrase.yes" />
                                        </c:when>
                                        <c:otherwise>
                                            <fmt:message key="phrase.no" />
                                        </c:otherwise>
                                    </c:choose>
                                </display:column>
                                <display:column titleKey="columnTitle.sortOrder">
                                    <c:out value="${entityAttribute.sortOrder}" />
                                </display:column>
                                <display:column>
                                    <et:hasSecurityRole securityRole="EntityAttribute.EntityAttributeEntityAttributeGroup">
                                        <c:url var="entityAttributeGroupsUrl" value="/action/Core/EntityAttribute/EntityAttributeGroup">
                                            <c:param name="ComponentVendorName" value="${entityAttribute.entityType.componentVendor.componentVendorName}" />
                                            <c:param name="EntityTypeName" value="${entityAttribute.entityType.entityTypeName}" />
                                            <c:param name="EntityAttributeName" value="${entityAttribute.entityAttributeName}" />
                                        </c:url>
                                        <a href="${entityAttributeGroupsUrl}">Groups</a>
                                        <c:set var="hasLinksInFirstRow" value="true" />
                                    </et:hasSecurityRole>
                                    <c:set value="${entityAttribute.entityAttributeType.entityAttributeTypeName}" var="entityAttributeTypeName" />
                                    <et:hasSecurityRole securityRole="EntityAttributeEntityType.List">
                                        <c:if test="${entityAttributeTypeName == 'ENTITY' || entityAttributeTypeName == 'COLLECTION'}">
                                            <c:url var="entityListItemsUrl" value="/action/Core/EntityAttributeEntityType/Main">
                                                <c:param name="ComponentVendorName" value="${entityAttribute.entityType.componentVendor.componentVendorName}" />
                                                <c:param name="EntityTypeName" value="${entityAttribute.entityType.entityTypeName}" />
                                                <c:param name="EntityAttributeName" value="${entityAttribute.entityAttributeName}" />
                                            </c:url>
                                            <a href="${entityListItemsUrl}">Entity Types</a>
                                            <c:set var="hasLinksInFirstRow" value="true" />
                                        </c:if>
                                    </et:hasSecurityRole>
                                    <et:hasSecurityRole securityRole="EntityListItem.List">
                                        <c:if test="${entityAttributeTypeName == 'LISTITEM' || entityAttributeTypeName == 'MULTIPLELISTITEM'}">
                                            <c:url var="entityListItemsUrl" value="/action/Core/EntityListItem/Main">
                                                <c:param name="ComponentVendorName" value="${entityAttribute.entityType.componentVendor.componentVendorName}" />
                                                <c:param name="EntityTypeName" value="${entityAttribute.entityType.entityTypeName}" />
                                                <c:param name="EntityAttributeName" value="${entityAttribute.entityAttributeName}" />
                                            </c:url>
                                            <a href="${entityListItemsUrl}">List Items</a>
                                            <c:set var="hasLinksInFirstRow" value="true" />
                                        </c:if>
                                    </et:hasSecurityRole>
                                    <et:hasSecurityRole securityRole="EntityIntegerRange.List">
                                        <c:if test="${entityAttributeTypeName == 'INTEGER'}">
                                            <c:url var="entityIntegerRangesUrl" value="/action/Core/EntityIntegerRange/Main">
                                                <c:param name="ComponentVendorName" value="${entityAttribute.entityType.componentVendor.componentVendorName}" />
                                                <c:param name="EntityTypeName" value="${entityAttribute.entityType.entityTypeName}" />
                                                <c:param name="EntityAttributeName" value="${entityAttribute.entityAttributeName}" />
                                            </c:url>
                                            <a href="${entityIntegerRangesUrl}">Ranges</a>
                                            <c:set var="hasLinksInFirstRow" value="true" />
                                        </c:if>
                                    </et:hasSecurityRole>
                                    <et:hasSecurityRole securityRole="EntityLongRange.List">
                                        <c:if test="${entityAttributeTypeName == 'LONG'}">
                                            <c:url var="entityLongRangesUrl" value="/action/Core/EntityLongRange/Main">
                                                <c:param name="ComponentVendorName" value="${entityAttribute.entityType.componentVendor.componentVendorName}" />
                                                <c:param name="EntityTypeName" value="${entityAttribute.entityType.entityTypeName}" />
                                                <c:param name="EntityAttributeName" value="${entityAttribute.entityAttributeName}" />
                                            </c:url>
                                            <a href="${entityLongRangesUrl}">Ranges</a>
                                            <c:set var="hasLinksInFirstRow" value="true" />
                                        </c:if>
                                    </et:hasSecurityRole>
                                    <c:if test="${hasLinksInFirstRow && linksInSecondRow}">
                                        <br />
                                    </c:if>
                                    <et:hasSecurityRole securityRole="EntityAttribute.Edit">
                                        <c:url var="editUrl" value="/action/Core/EntityAttribute/Edit">
                                            <c:param name="ComponentVendorName" value="${entityAttribute.entityType.componentVendor.componentVendorName}" />
                                            <c:param name="EntityTypeName" value="${entityAttribute.entityType.entityTypeName}" />
                                            <c:param name="OriginalEntityAttributeName" value="${entityAttribute.entityAttributeName}" />
                                        </c:url>
                                        <a href="${editUrl}">Edit</a>
                                    </et:hasSecurityRole>
                                    <et:hasSecurityRole securityRole="EntityAttribute.Description">
                                        <c:url var="descriptionsUrl" value="/action/Core/EntityAttribute/Description">
                                            <c:param name="ComponentVendorName" value="${entityAttribute.entityType.componentVendor.componentVendorName}" />
                                            <c:param name="EntityTypeName" value="${entityAttribute.entityType.entityTypeName}" />
                                            <c:param name="EntityAttributeName" value="${entityAttribute.entityAttributeName}" />
                                        </c:url>
                                        <a href="${descriptionsUrl}">Descriptions</a>
                                    </et:hasSecurityRole>
                                    <et:hasSecurityRole securityRole="EntityAttribute.Delete">
                                        <c:url var="deleteUrl" value="/action/Core/EntityAttribute/Delete">
                                            <c:param name="ComponentVendorName" value="${entityAttribute.entityType.componentVendor.componentVendorName}" />
                                            <c:param name="EntityTypeName" value="${entityAttribute.entityType.entityTypeName}" />
                                            <c:param name="EntityAttributeName" value="${entityAttribute.entityAttributeName}" />
                                        </c:url>
                                        <a href="${deleteUrl}">Delete</a>
                                    </et:hasSecurityRole>
                                </display:column>
                                <et:hasSecurityRole securityRole="Event.List">
                                    <display:column>
                                        <c:url var="eventsUrl" value="/action/Core/Event/Main">
                                            <c:param name="EntityRef" value="${entityAttribute.entityInstance.entityRef}" />
                                        </c:url>
                                        <a href="${eventsUrl}">Events</a>
                                    </display:column>
                                </et:hasSecurityRole>
                            </display:table>
                        </c:otherwise>
                    </c:choose>
                    <br />
                </et:hasSecurityRole>
                <et:hasSecurityRole securityRole="CommentType.List">
                    <h2>Comment Types</h2>
                    <c:url var="addUrl" value="/action/Core/CommentType/Add">
                        <c:param name="ComponentVendorName" value="${entityType.componentVendor.componentVendorName}" />
                        <c:param name="EntityTypeName" value="${entityType.entityTypeName}" />
                    </c:url>
                    <p><a href="${addUrl}">Add Comment Type.</a></p>
                    <c:choose>
                        <c:when test='${entityType.commentTypes.size == 0}'>
                            <br />
                        </c:when>
                        <c:otherwise>
                            <display:table name="entityType.commentTypes.list" id="commentType" class="displaytag">
                                <display:column titleKey="columnTitle.name">
                                    <c:url var="reviewUrl" value="/action/Core/CommentType/Review">
                                        <c:param name="ComponentVendorName" value="${commentType.entityType.componentVendor.componentVendorName}" />
                                        <c:param name="EntityTypeName" value="${commentType.entityType.entityTypeName}" />
                                        <c:param name="CommentTypeName" value="${commentType.commentTypeName}" />
                                    </c:url>
                                    <a href="${reviewUrl}"><c:out value="${commentType.commentTypeName}" /></a>
                                </display:column>
                                <display:column titleKey="columnTitle.description">
                                    <c:out value="${commentType.description}" />
                                </display:column>
                                <display:column titleKey="columnTitle.commentSequence">
                                    <c:out value="${commentType.commentSequence.description}" />
                                </display:column>
                                <display:column titleKey="columnTitle.workflowEntrance">
                                    <c:if test='${commentType.workflowEntrance != null}'>
                                        <c:url var="workflowUrl" value="/action/Configuration/Workflow/Review">
                                            <c:param name="WorkflowName" value="${commentType.workflowEntrance.workflow.workflowName}" />
                                        </c:url>
                                        <c:url var="workflowEntranceUrl" value="/action/Configuration/WorkflowEntrance/Review">
                                            <c:param name="WorkflowName" value="${commentType.workflowEntrance.workflow.workflowName}" />
                                            <c:param name="WorkflowEntranceName" value="${commentType.workflowEntrance.workflowEntranceName}" />
                                        </c:url>
                                        <a href="${workflowUrl}"><c:out value="${commentType.workflowEntrance.workflow.description}" /></a>,
                                        <a href="${workflowEntranceUrl}"><c:out value="${commentType.workflowEntrance.description}" /></a>
                                    </c:if>
                                </display:column>
                                <display:column titleKey="columnTitle.mimeTypeUsageType">
                                    <c:out value="${commentType.mimeTypeUsageType.description}" />
                                </display:column>
                                <display:column titleKey="columnTitle.sortOrder">
                                    <c:out value="${commentType.sortOrder}" />
                                </display:column>
                                <display:column>
                                    <c:url var="commentUsageTypesUrl" value="/action/Core/CommentUsageType/Main">
                                        <c:param name="ComponentVendorName" value="${commentType.entityType.componentVendor.componentVendorName}" />
                                        <c:param name="EntityTypeName" value="${commentType.entityType.entityTypeName}" />
                                        <c:param name="CommentTypeName" value="${commentType.commentTypeName}" />
                                    </c:url>
                                    <a href="${commentUsageTypesUrl}">Comment Usage Types</a><br />
                                    <c:url var="editUrl" value="/action/Core/CommentType/Edit">
                                        <c:param name="ComponentVendorName" value="${commentType.entityType.componentVendor.componentVendorName}" />
                                        <c:param name="EntityTypeName" value="${commentType.entityType.entityTypeName}" />
                                        <c:param name="OriginalCommentTypeName" value="${commentType.commentTypeName}" />
                                    </c:url>
                                    <a href="${editUrl}">Edit</a>
                                    <c:url var="descriptionsUrl" value="/action/Core/CommentType/Description">
                                        <c:param name="ComponentVendorName" value="${commentType.entityType.componentVendor.componentVendorName}" />
                                        <c:param name="EntityTypeName" value="${commentType.entityType.entityTypeName}" />
                                        <c:param name="CommentTypeName" value="${commentType.commentTypeName}" />
                                    </c:url>
                                    <a href="${descriptionsUrl}">Descriptions</a>
                                    <c:url var="deleteUrl" value="/action/Core/CommentType/Delete">
                                        <c:param name="ComponentVendorName" value="${commentType.entityType.componentVendor.componentVendorName}" />
                                        <c:param name="EntityTypeName" value="${commentType.entityType.entityTypeName}" />
                                        <c:param name="CommentTypeName" value="${commentType.commentTypeName}" />
                                    </c:url>
                                    <a href="${deleteUrl}">Delete</a>
                                </display:column>
                                <et:hasSecurityRole securityRole="Event.List">
                                    <display:column>
                                        <c:url var="eventsUrl" value="/action/Core/Event/Main">
                                            <c:param name="EntityRef" value="${commentType.entityInstance.entityRef}" />
                                        </c:url>
                                        <a href="${eventsUrl}">Events</a>
                                    </display:column>
                                </et:hasSecurityRole>
                            </display:table>
                        </c:otherwise>
                    </c:choose>
                    <br />
                </et:hasSecurityRole>
                <et:hasSecurityRole securityRole="RatingType.List">
                    <h2>Rating Types</h2>
                    <c:url var="addUrl" value="/action/Core/RatingType/Add">
                        <c:param name="ComponentVendorName" value="${entityType.componentVendor.componentVendorName}" />
                        <c:param name="EntityTypeName" value="${entityType.entityTypeName}" />
                    </c:url>
                    <p><a href="${addUrl}">Add Rating Type.</a></p>
                    <c:choose>
                        <c:when test='${entityType.ratingTypes.size == 0}'>
                            <br />
                        </c:when>
                        <c:otherwise>
                            <display:table name="entityType.ratingTypes.list" id="ratingType" class="displaytag">
                                <display:column property="ratingTypeName" titleKey="columnTitle.name" />
                                <display:column titleKey="columnTitle.description">
                                    <c:out value="${ratingType.description}" />
                                </display:column>
                                <display:column titleKey="columnTitle.ratingSequence">
                                    <c:out value="${ratingType.ratingSequence.description}" />
                                </display:column>
                                <display:column titleKey="columnTitle.sortOrder">
                                    <c:out value="${ratingType.sortOrder}" />
                                </display:column>
                                <display:column>
                                    <c:url var="ratingTypeListItemsUrl" value="/action/Core/RatingTypeListItem/Main">
                                        <c:param name="ComponentVendorName" value="${ratingType.entityType.componentVendor.componentVendorName}" />
                                        <c:param name="EntityTypeName" value="${ratingType.entityType.entityTypeName}" />
                                        <c:param name="RatingTypeName" value="${ratingType.ratingTypeName}" />
                                    </c:url>
                                    <a href="${ratingTypeListItemsUrl}">List Items</a><br />
                                    <c:url var="editUrl" value="/action/Core/RatingType/Edit">
                                        <c:param name="ComponentVendorName" value="${ratingType.entityType.componentVendor.componentVendorName}" />
                                        <c:param name="EntityTypeName" value="${ratingType.entityType.entityTypeName}" />
                                        <c:param name="OriginalRatingTypeName" value="${ratingType.ratingTypeName}" />
                                    </c:url>
                                    <a href="${editUrl}">Edit</a>
                                    <c:url var="descriptionsUrl" value="/action/Core/RatingType/Description">
                                        <c:param name="ComponentVendorName" value="${ratingType.entityType.componentVendor.componentVendorName}" />
                                        <c:param name="EntityTypeName" value="${ratingType.entityType.entityTypeName}" />
                                        <c:param name="RatingTypeName" value="${ratingType.ratingTypeName}" />
                                    </c:url>
                                    <a href="${descriptionsUrl}">Descriptions</a>
                                    <c:url var="deleteUrl" value="/action/Core/RatingType/Delete">
                                        <c:param name="ComponentVendorName" value="${ratingType.entityType.componentVendor.componentVendorName}" />
                                        <c:param name="EntityTypeName" value="${ratingType.entityType.entityTypeName}" />
                                        <c:param name="RatingTypeName" value="${ratingType.ratingTypeName}" />
                                    </c:url>
                                    <a href="${deleteUrl}">Delete</a>
                                </display:column>
                                <et:hasSecurityRole securityRole="Event.List">
                                    <display:column>
                                        <c:url var="eventsUrl" value="/action/Core/Event/Main">
                                            <c:param name="EntityRef" value="${ratingType.entityInstance.entityRef}" />
                                        </c:url>
                                        <a href="${eventsUrl}">Events</a>
                                    </display:column>
                                </et:hasSecurityRole>
                            </display:table>
                        </c:otherwise>
                    </c:choose>
                    <br />
                </et:hasSecurityRole>
                <et:hasSecurityRole securityRole="MessageType.List">
                    <h2>Message Types</h2>
                    <c:url var="addUrl" value="/action/Core/MessageType/Add">
                        <c:param name="ComponentVendorName" value="${entityType.componentVendor.componentVendorName}" />
                        <c:param name="EntityTypeName" value="${entityType.entityTypeName}" />
                    </c:url>
                    <p><a href="${addUrl}">Add Message Type.</a></p>
                    <c:choose>
                        <c:when test='${entityType.messageTypes.size == 0}'>
                            <br />
                        </c:when>
                        <c:otherwise>
                            <display:table name="entityType.messageTypes.list" id="messageType" class="displaytag">
                                <display:column property="messageTypeName" titleKey="columnTitle.name" />
                                <display:column titleKey="columnTitle.description">
                                    <c:out value="${messageType.description}" />
                                </display:column>
                                <display:column titleKey="columnTitle.mimeTypeUsageType">
                                    <c:out value="${messageType.mimeTypeUsageType.description}" />
                                </display:column>
                                <display:column titleKey="columnTitle.sortOrder">
                                    <c:out value="${messageType.sortOrder}" />
                                </display:column>
                                <display:column>
                                    <c:url var="messagesUrl" value="/action/Core/Message/Main">
                                        <c:param name="ComponentVendorName" value="${messageType.entityType.componentVendor.componentVendorName}" />
                                        <c:param name="EntityTypeName" value="${messageType.entityType.entityTypeName}" />
                                        <c:param name="MessageTypeName" value="${messageType.messageTypeName}" />
                                    </c:url>
                                    <a href="${messagesUrl}">Messages</a><br />
                                    <c:url var="editUrl" value="/action/Core/MessageType/Edit">
                                        <c:param name="ComponentVendorName" value="${messageType.entityType.componentVendor.componentVendorName}" />
                                        <c:param name="EntityTypeName" value="${messageType.entityType.entityTypeName}" />
                                        <c:param name="OriginalMessageTypeName" value="${messageType.messageTypeName}" />
                                    </c:url>
                                    <a href="${editUrl}">Edit</a>
                                    <c:url var="descriptionsUrl" value="/action/Core/MessageType/Description">
                                        <c:param name="ComponentVendorName" value="${messageType.entityType.componentVendor.componentVendorName}" />
                                        <c:param name="EntityTypeName" value="${messageType.entityType.entityTypeName}" />
                                        <c:param name="MessageTypeName" value="${messageType.messageTypeName}" />
                                    </c:url>
                                    <a href="${descriptionsUrl}">Descriptions</a>
                                    <c:url var="deleteUrl" value="/action/Core/MessageType/Delete">
                                        <c:param name="ComponentVendorName" value="${messageType.entityType.componentVendor.componentVendorName}" />
                                        <c:param name="EntityTypeName" value="${messageType.entityType.entityTypeName}" />
                                        <c:param name="MessageTypeName" value="${messageType.messageTypeName}" />
                                    </c:url>
                                    <a href="${deleteUrl}">Delete</a>
                                </display:column>
                                <et:hasSecurityRole securityRole="Event.List">
                                    <display:column>
                                        <c:url var="eventsUrl" value="/action/Core/Event/Main">
                                            <c:param name="EntityRef" value="${messageType.entityInstance.entityRef}" />
                                        </c:url>
                                        <a href="${eventsUrl}">Events</a>
                                    </display:column>
                                </et:hasSecurityRole>
                            </display:table>
                        </c:otherwise>
                    </c:choose>
                    <br />
                </et:hasSecurityRole>
            </c:if>
            <et:hasSecurityRole securityRole="EntityInstance.List">
                <h2>Entity Instances</h2>
                <c:choose>
                    <c:when test='${entityType.entityInstances.size == 0}'>
                        <br />
                    </c:when>
                    <c:otherwise>
                        <display:table name="entityType.entityInstances.list" id="entityInstance" class="displaytag">
                            <display:column property="entityRef" titleKey="columnTitle.entity" />
                            <display:column titleKey="columnTitle.description">
                                <c:out value="${entityInstance.description}" />
                            </display:column>
                            <display:column titleKey="columnTitle.created">
                                <c:out value="${entityInstance.entityTime.createdTime}" />
                            </display:column>
                            <display:column titleKey="columnTitle.modified">
                                <c:out value="${entityInstance.entityTime.modifiedTime}" />
                            </display:column>
                            <display:column titleKey="columnTitle.deleted">
                                <c:out value="${entityInstance.entityTime.deletedTime}" />
                            </display:column>
                            <display:column>
                                <c:if test="${entityInstance.entityTime != null}">
                                    <c:url var="eventsUrl" value="/action/Core/Event/Main">
                                        <c:param name="EntityRef" value="${entityInstance.entityRef}" />
                                    </c:url>
                                    <a href="${eventsUrl}">Events</a>
                                </c:if>
                            </display:column>
                        </display:table>
                        <c:if test='${entityType.entityInstancesCount > 10}'>
                            <c:url var="entityInstancesUrl" value="/action/Core/EntityInstance/Main">
                                <c:param name="ComponentVendorName" value="${entityType.componentVendor.componentVendorName}" />
                                <c:param name="EntityTypeName" value="${entityType.entityTypeName}" />
                            </c:url>
                            <a href="${entityInstancesUrl}">More...</a> (<c:out value="${entityType.entityInstancesCount}" /> total)<br />
                        </c:if>
                        <br />
                    </c:otherwise>
                </c:choose>
            </et:hasSecurityRole>
            <br />
            <c:set var="entityInstance" scope="request" value="${entityType.entityInstance}" />
            <c:url var="returnUrl" scope="request" value="/../action/Core/ComponentVendor/Review">
                <c:param name="ComponentVendorName" value="${entityType.entityTypeName}" />
            </c:url>
            <jsp:include page="../../include/entityInstance.jsp" />
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
