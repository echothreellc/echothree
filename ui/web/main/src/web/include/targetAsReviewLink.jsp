<%@ include file="taglibs.jsp" %>

<c:if test="${targetForReviewSetupComplete == null}">
    <et:checkSecurityRoles securityRoles="Employee.Review:Customer.Review:Company.Review:Division.Review:Department.Review:Vendor.Review:VendorItem.Review:Carrier.Review:Warehouse.Review:CommunicationEvent.Review:PartyTrainingClass.Review:TrainingClass.Review:PartyContactList.Review:ComponentVendor.Review:EntityType.Review:EntityAttribute.Review" />
    <c:set var="targetForReviewSetupComplete" value="true"/>
</c:if>

<c:set var="targetUrl" value="unset"/>
<c:if test="${entityInstance.entityNames != null}">
    <c:choose>
        <c:when test="${entityInstance.entityNames.target == 'Employee'}">
            <et:hasSecurityRole securityRole="Employee.Review">
                <c:url var="targetUrl" value="/action/HumanResources/Employee/Review">
                    <c:param name="PartyName" value="${entityInstance.entityNames.names.map.PartyName}" />
                </c:url>
            </et:hasSecurityRole>
        </c:when>
        <c:when test="${entityInstance.entityNames.target == 'Customer'}">
            <et:hasSecurityRole securityRole="Customer.Review">
                <c:url var="targetUrl" value="/action/Customer/Customer/Review">
                    <c:param name="PartyName" value="${entityInstance.entityNames.names.map.PartyName}" />
                </c:url>
            </et:hasSecurityRole>
        </c:when>
        <c:when test="${entityInstance.entityNames.target == 'Company'}">
            <et:hasSecurityRole securityRole="Company.Review">
                <c:url var="targetUrl" value="/action/Accounting/Company/Review">
                    <c:param name="PartyName" value="${entityInstance.entityNames.names.map.PartyName}" />
                </c:url>
            </et:hasSecurityRole>
        </c:when>
        <c:when test="${entityInstance.entityNames.target == 'Division'}">
            <et:hasSecurityRole securityRole="Division.Review">
                <c:url var="targetUrl" value="/action/Accounting/Division/Review">
                    <c:param name="PartyName" value="${entityInstance.entityNames.names.map.PartyName}" />
                </c:url>
            </et:hasSecurityRole>
        </c:when>
        <c:when test="${entityInstance.entityNames.target == 'Department'}">
            <et:hasSecurityRole securityRole="Department.Review">
                <c:url var="targetUrl" value="/action/Accounting/Department/Review">
                    <c:param name="PartyName" value="${entityInstance.entityNames.names.map.PartyName}" />
                </c:url>
            </et:hasSecurityRole>
        </c:when>
        <c:when test="${entityInstance.entityNames.target == 'Vendor'}">
            <et:hasSecurityRole securityRole="Vendor.Review">
                <c:url var="targetUrl" value="/action/Purchasing/Vendor/Review">
                    <c:param name="PartyName" value="${entityInstance.entityNames.names.map.PartyName}" />
                </c:url>
            </et:hasSecurityRole>
        </c:when>
        <c:when test="${entityInstance.entityNames.target == 'VendorItem'}">
            <et:hasSecurityRole securityRole="VendorItem.Review">
                <c:url var="targetUrl" value="/action/Purchasing/VendorItem/Review">
                    <c:param name="PartyName" value="${entityInstance.entityNames.names.map.PartyName}" />
                    <c:param name="VendorItemName" value="${entityInstance.entityNames.names.map.VendorItemName}" />
                </c:url>
            </et:hasSecurityRole>
        </c:when>
        <c:when test="${entityInstance.entityNames.target == 'Carrier'}">
            <et:hasSecurityRole securityRole="Carrier.Review">
                <c:url var="targetUrl" value="/action/Shipping/Carrier/Review">
                    <c:param name="PartyName" value="${entityInstance.entityNames.names.map.PartyName}" />
                </c:url>
            </et:hasSecurityRole>
        </c:when>
        <c:when test="${entityInstance.entityNames.target == 'Warehouse'}">
            <et:hasSecurityRole securityRole="Warehouse.Review">
                <c:url var="targetUrl" value="/action/Warehouse/Warehouse/Review">
                    <c:param name="PartyName" value="${entityInstance.entityNames.names.map.PartyName}" />
                </c:url>
            </et:hasSecurityRole>
        </c:when>
        <c:when test="${entityInstance.entityNames.target == 'Location'}">
            <et:hasSecurityRole securityRole="Location.Review">
                <c:url var="targetUrl" value="/action/Warehouse/Location/Review">
                    <c:param name="WarehouseName" value="${entityInstance.entityNames.names.map.WarehouseName}" />
                    <c:param name="LocationName" value="${entityInstance.entityNames.names.map.LocationName}" />
                </c:url>
            </et:hasSecurityRole>
        </c:when>
        <c:when test="${entityInstance.entityNames.target == 'CommunicationEvent'}">
            <et:hasSecurityRole securityRole="CommunicationEvent.Review">
                <c:url var="targetUrl" value="/action/Communication/CommunicationEvent/Review">
                    <c:param name="CommunicationEventName" value="${entityInstance.entityNames.names.map.CommunicationEventName}" />
                </c:url>
            </et:hasSecurityRole>
        </c:when>
        <c:when test="${entityInstance.entityNames.target == 'PartyTrainingClass'}">
            <et:hasSecurityRole securityRole="PartyTrainingClass.Review">
                <c:url var="targetUrl" value="/action/HumanResources/PartyTrainingClass/Review">
                    <c:param name="PartyTrainingClassName" value="${entityInstance.entityNames.names.map.PartyTrainingClassName}" />
                </c:url>
            </et:hasSecurityRole>
        </c:when>
        <c:when test="${entityInstance.entityNames.target == 'TrainingClass'}">
            <et:hasSecurityRole securityRole="TrainingClass.Review">
                <c:url var="targetUrl" value="/action/HumanResources/TrainingClass/Review">
                    <c:param name="TrainingClassName" value="${entityInstance.entityNames.names.map.TrainingClassName}" />
                </c:url>
            </et:hasSecurityRole>
        </c:when>
        <c:when test="${entityInstance.entityNames.target == 'Item'}">
            <c:url var="targetUrl" value="/action/Item/Item/Review">
                <c:param name="ItemName" value="${entityInstance.entityNames.names.map.ItemName}" />
            </c:url>
        </c:when>
        <c:when test="${entityInstance.entityNames.target == 'ItemDescription'}">
            <c:url var="targetUrl" value="/action/Item/Item/ItemDescriptionReview">
                <c:param name="ItemDescriptionTypeName" value="${entityInstance.entityNames.names.map.ItemDescriptionTypeName}" />
                <c:param name="ItemName" value="${entityInstance.entityNames.names.map.ItemName}" />
                <c:param name="LanguageIsoName" value="${entityInstance.entityNames.names.map.LanguageIsoName}" />
            </c:url>
        </c:when>
        <c:when test="${entityInstance.entityNames.target == 'ForumGroup'}">
            <c:url var="targetUrl" value="/action/Forum/ForumGroup/Review">
                <c:param name="ForumGroupName" value="${entityInstance.entityNames.names.map.ForumGroupName}" />
            </c:url>
        </c:when>
        <c:when test="${entityInstance.entityNames.target == 'Forum'}">
            <c:url var="targetUrl" value="/action/Forum/Forum/Review">
                <c:param name="ForumName" value="${entityInstance.entityNames.names.map.ForumName}" />
            </c:url>
        </c:when>
        <c:when test="${entityInstance.entityNames.target == 'ForumThread'}">
            <c:url var="targetUrl" value="/action/Forum/ForumThread/Review">
                <c:param name="ForumThreadName" value="${entityInstance.entityNames.names.map.ForumThreadName}" />
            </c:url>
        </c:when>
        <c:when test="${entityInstance.entityNames.target == 'ForumMessage'}">
            <c:url var="targetUrl" value="/action/Forum/ForumMessage/Review">
                <c:param name="ForumMessageName" value="${entityInstance.entityNames.names.map.ForumMessageName}" />
            </c:url>
        </c:when>
        <c:when test="${entityInstance.entityNames.target == 'PartyContactList'}">
            <et:hasSecurityRole securityRole="PartyContactList.Review">
                <c:url var="targetUrl" value="/action/ContactList/PartyContactList/Review">
                    <c:param name="PartyName" value="${entityInstance.entityNames.names.map.PartyName}" />
                    <c:param name="ContactListName" value="${entityInstance.entityNames.names.map.ContactListName}" />
                </c:url>
            </et:hasSecurityRole>
        </c:when>
        <c:when test="${entityInstance.entityNames.target == 'Subscription'}">
            <c:url var="targetUrl" value="/action/Subscription/Subscription/Review">
                <c:param name="SubscriptionName" value="${entityInstance.entityNames.names.map.SubscriptionName}" />
            </c:url>
        </c:when>
        <c:when test="${entityInstance.entityNames.target == 'MimeType'}">
            <c:url var="targetUrl" value="/action/Core/MimeType/Review">
                <c:param name="MimeTypeName" value="${entityInstance.entityNames.names.map.MimeTypeName}" />
            </c:url>
        </c:when>
        <c:when test="${entityInstance.entityNames.target == 'ComponentVendor'}">
            <et:hasSecurityRole securityRole="ComponentVendor.Review">
                <c:url var="targetUrl" value="/action/Core/ComponentVendor/Review">
                    <c:param name="ComponentVendorName" value="${entityInstance.entityNames.names.map.ComponentVendorName}" />
                </c:url>
            </et:hasSecurityRole>
        </c:when>
        <c:when test="${entityInstance.entityNames.target == 'EntityType'}">
            <et:hasSecurityRole securityRole="EntityType.Review">
                <c:url var="targetUrl" value="/action/Core/EntityType/Review">
                    <c:param name="ComponentVendorName" value="${entityInstance.entityNames.names.map.ComponentVendorName}" />
                    <c:param name="EntityTypeName" value="${entityInstance.entityNames.names.map.EntityTypeName}" />
                </c:url>
            </et:hasSecurityRole>
        </c:when>
        <c:when test="${entityInstance.entityNames.target == 'EntityAttribute'}">
            <et:hasSecurityRole securityRole="EntityAttribute.Review">
                <c:url var="targetUrl" value="/action/Core/EntityAttribute/Review">
                    <c:param name="ComponentVendorName" value="${entityInstance.entityNames.names.map.ComponentVendorName}" />
                    <c:param name="EntityTypeName" value="${entityInstance.entityNames.names.map.EntityTypeName}" />
                    <c:param name="EntityAttributeName" value="${entityInstance.entityNames.names.map.EntityAttributeName}" />
                </c:url>
            </et:hasSecurityRole>
        </c:when>
    </c:choose>
</c:if>

<c:choose>
    <c:when test="${targetUrl != 'unset'}">
        <c:choose>
            <c:when test="${entityInstance.description == null}">
                <a href="${targetUrl}"><c:out value="${entityInstance.entityRef}" /></a>
            </c:when>
            <c:otherwise>
                <a href="${targetUrl}"><c:out value="${entityInstance.description}" /></a>
            </c:otherwise>
        </c:choose>
    </c:when>
    <c:otherwise>
        <c:choose>
            <c:when test="${entityInstance.description == null}">
                <c:out value="${entityInstance.entityRef}" />
            </c:when>
            <c:otherwise>
                <c:out value="${entityInstance.description}" />
            </c:otherwise>
        </c:choose>
    </c:otherwise>
</c:choose>
