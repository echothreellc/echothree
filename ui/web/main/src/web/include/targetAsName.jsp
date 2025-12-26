<%@ include file="taglibs.jsp" %>

<c:set var="targetNames" value="unset"/>
<c:if test="${entityInstance.entityNames != null}">
    <c:choose>
        <c:when test="${entityInstance.entityNames.target == 'Employee'}">
            <c:set var="targetNames" value="${entityInstance.entityNames.names.map.PartyName}" />
        </c:when>
        <c:when test="${entityInstance.entityNames.target == 'Customer'}">
            <c:set var="targetNames" value="${entityInstance.entityNames.names.map.PartyName}" />
        </c:when>
        <c:when test="${entityInstance.entityNames.target == 'Company'}">
            <c:set var="targetNames" value="${entityInstance.entityNames.names.map.PartyName}" />
        </c:when>
        <c:when test="${entityInstance.entityNames.target == 'Division'}">
            <c:set var="targetNames" value="${entityInstance.entityNames.names.map.PartyName}" />
        </c:when>
        <c:when test="${entityInstance.entityNames.target == 'Department'}">
            <c:set var="targetNames" value="${entityInstance.entityNames.names.map.PartyName}" />
        </c:when>
        <c:when test="${entityInstance.entityNames.target == 'Vendor'}">
            <c:set var="targetNames" value="${entityInstance.entityNames.names.map.PartyName}" />
        </c:when>
        <c:when test="${entityInstance.entityNames.target == 'VendorItem'}">
            <c:set var="targetNames" value="${entityInstance.entityNames.names.map.PartyName}, ${entityInstance.entityNames.names.map.VendorItemName}" />
        </c:when>
        <c:when test="${entityInstance.entityNames.target == 'Carrier'}">
            <c:set var="targetNames" value="${entityInstance.entityNames.names.map.PartyName}" />
        </c:when>
        <c:when test="${entityInstance.entityNames.target == 'Warehouse'}">
            <c:set var="targetNames" value="${entityInstance.entityNames.names.map.PartyName}" />
        </c:when>
        <c:when test="${entityInstance.entityNames.target == 'Location'}">
            <c:set var="targetNames" value="${entityInstance.entityNames.names.map.WarehouseName}, ${entityInstance.entityNames.names.map.LocationName}" />
        </c:when>
        <c:when test="${entityInstance.entityNames.target == 'CommunicationEvent'}">
            <c:set var="targetNames" value="${entityInstance.entityNames.names.map.CommunicationEventName}" />
        </c:when>
        <c:when test="${entityInstance.entityNames.target == 'PartyTrainingClass'}">
            <c:set var="targetNames" value="${entityInstance.entityNames.names.map.PartyTrainingClassName}" />
        </c:when>
        <c:when test="${entityInstance.entityNames.target == 'TrainingClass'}">
            <c:set var="targetNames" value="${entityInstance.entityNames.names.map.TrainingClassName}" />
        </c:when>
        <c:when test="${entityInstance.entityNames.target == 'Item'}">
            <c:set var="targetNames" value="${entityInstance.entityNames.names.map.ItemName}" />
        </c:when>
        <c:when test="${entityInstance.entityNames.target == 'ItemDescription'}">
            <c:set var="targetNames" value="${entityInstance.entityNames.names.map.ItemDescriptionTypeName}, ${entityInstance.entityNames.names.map.ItemName}, ${entityInstance.entityNames.names.map.LanguageIsoName}" />
        </c:when>
        <c:when test="${entityInstance.entityNames.target == 'ForumGroup'}">
            <c:set var="targetNames" value="${entityInstance.entityNames.names.map.ForumGroup}" />
        </c:when>
        <c:when test="${entityInstance.entityNames.target == 'Forum'}">
            <c:set var="targetNames" value="${entityInstance.entityNames.names.map.Forum}" />
        </c:when>
        <c:when test="${entityInstance.entityNames.target == 'ForumThread'}">
            <c:set var="targetNames" value="${entityInstance.entityNames.names.map.ForumThread}" />
        </c:when>
        <c:when test="${entityInstance.entityNames.target == 'ForumMessage'}">
            <c:set var="targetNames" value="${entityInstance.entityNames.names.map.ForumMessage}" />
        </c:when>
        <c:when test="${entityInstance.entityNames.target == 'PartyContactList'}">
            <c:set var="targetNames" value="${entityInstance.entityNames.names.map.PartyName}, ${entityInstance.entityNames.names.map.ContactListName}" />
        </c:when>
        <c:when test="${entityInstance.entityNames.target == 'Subscription'}">
            <c:set var="targetNames" value="${entityInstance.entityNames.names.map.SubscriptionName}" />
        </c:when>
        <c:when test="${entityInstance.entityNames.target == 'MimeType'}">
            <c:set var="targetNames" value="${entityInstance.entityNames.names.map.MimeTypeName}" />
        </c:when>
        <c:when test="${entityInstance.entityNames.target == 'ComponentVendor'}">
            <c:set var="targetNames" value="${entityInstance.entityNames.names.map.ComponentVendorName}" />
        </c:when>
        <c:when test="${entityInstance.entityNames.target == 'EntityType'}">
            <c:set var="targetNames" value="${entityInstance.entityNames.names.map.ComponentVendorName}, ${entityInstance.entityNames.names.map.EntityTypeName}" />
        </c:when>
        <c:when test="${entityInstance.entityNames.target == 'EntityAttribute'}">
            <c:set var="targetNames" value="${entityInstance.entityNames.names.map.ComponentVendorName}, ${entityInstance.entityNames.names.map.EntityTypeName}, ${entityInstance.entityNames.names.map.EntityAttributeName}" />
        </c:when>
        <c:when test="${entityInstance.entityNames.target == 'EntityListItem'}">
            <c:set var="targetNames" value="${entityInstance.entityNames.names.map.ComponentVendorName}, ${entityInstance.entityNames.names.map.EntityTypeName}, ${entityInstance.entityNames.names.map.EntityAttributeName}, ${entityInstance.entityNames.names.map.EntityListItemName}" />
        </c:when>
        <c:when test="${entityInstance.entityNames.target == 'EntityAttributeGroup'}">
            <c:set var="targetNames" value="${entityInstance.entityNames.names.map.EntityAttributeGroupName}" />
        </c:when>
    </c:choose>
</c:if>

<c:if test="${targetNames != 'unset'}">
    <c:out value="${targetNames}" />
</c:if>
