<%@ include file="taglibs.jsp" %>

<c:if test="${targetForEditSetupComplete == null}">
    <et:checkSecurityRoles securityRoles="Item.Edit:ItemDescription.Edit:ComponentVendor.Edit:EntityType.Edit:EntityAttribute.Edit" />
    <c:set var="targetForEditSetupComplete" value="true"/>
</c:if>

<c:set var="targetUrl" value="unset"/>
<c:if test="${entityInstance.entityNames != null}">
    <c:choose>
        <c:when test="${entityInstance.entityNames.target == 'Item'}">
            <et:hasSecurityRole securityRole="Item.Edit">
                <c:url var="targetUrl" value="/action/Item/Item/Edit">
                    <c:param name="OriginalItemName" value="${entityInstance.entityNames.names.map.ItemName}" />
                </c:url>
            </et:hasSecurityRole>
        </c:when>
        <c:when test="${entityInstance.entityNames.target == 'ItemDescription'}">
            <et:hasSecurityRole securityRole="ItemDescription.Edit">
                <c:url var="targetUrl" value="/action/Item/Item/DescriptionEdit">
                    <c:param name="ItemDescriptionTypeName" value="${entityInstance.entityNames.names.map.ItemDescriptionTypeName}" />
                    <c:param name="ItemName" value="${entityInstance.entityNames.names.map.ItemName}" />
                    <c:param name="LanguageIsoName" value="${entityInstance.entityNames.names.map.LanguageIsoName}" />
                </c:url>
            </et:hasSecurityRole>
        </c:when>
        <c:when test="${entityInstance.entityNames.target == 'ForumGroup'}">
            <c:url var="targetUrl" value="/action/Forum/ForumGroup/Edit">
                <c:param name="OriginalForumGroupName" value="${entityInstance.entityNames.names.map.ForumGroupName}" />
            </c:url>
        </c:when>
        <c:when test="${entityInstance.entityNames.target == 'Forum'}">
            <c:url var="targetUrl" value="/action/Forum/Forum/Edit">
                <c:param name="OriginalForumName" value="${entityInstance.entityNames.names.map.ForumName}" />
            </c:url>
        </c:when>
        <c:when test="${entityInstance.entityNames.target == 'MimeType'}">
            <c:url var="targetUrl" value="/action/Core/MimeType/Edit">
                <c:param name="MimeTypeName" value="${entityInstance.entityNames.names.map.MimeTypeName}" />
            </c:url>
        </c:when>
        <c:when test="${entityInstance.entityNames.target == 'ComponentVendor'}">
            <et:hasSecurityRole securityRole="ComponentVendor.Edit">
                <c:url var="targetUrl" value="/action/Core/ComponentVendor/Edit">
                    <c:param name="ComponentVendorName" value="${entityInstance.entityNames.names.map.ComponentVendorName}" />
                </c:url>
            </et:hasSecurityRole>
        </c:when>
        <c:when test="${entityInstance.entityNames.target == 'EntityType'}">
            <et:hasSecurityRole securityRole="EntityType.Edit">
                <c:url var="targetUrl" value="/action/Core/EntityType/Edit">
                    <c:param name="ComponentVendorName" value="${entityInstance.entityNames.names.map.ComponentVendorName}" />
                    <c:param name="EntityTypeName" value="${entityInstance.entityNames.names.map.EntityTypeName}" />
                </c:url>
            </et:hasSecurityRole>
        </c:when>
        <c:when test="${entityInstance.entityNames.target == 'EntityAttribute'}">
            <et:hasSecurityRole securityRole="EntityAttribute.Edit">
                <c:url var="targetUrl" value="/action/Core/EntityAttribute/Edit">
                    <c:param name="ComponentVendorName" value="${entityInstance.entityNames.names.map.ComponentVendorName}" />
                    <c:param name="EntityTypeName" value="${entityInstance.entityNames.names.map.EntityTypeName}" />
                    <c:param name="EntityAttributeName" value="${entityInstance.entityNames.names.map.EntityAttributeName}" />
                </c:url>
            </et:hasSecurityRole>
        </c:when>
    </c:choose>
</c:if>

<c:if test="${targetUrl != 'unset'}">
    [<a href="${targetUrl}">Edit</a>]
</c:if>
