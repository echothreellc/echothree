<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>

<%@ include file="../include/taglibs.jsp" %>

<et:checkSecurityRoles securityRoles="ContactListFrequency.List:ContactListType.List:ContactListGroup.List:ContactList.List:ComponentVendor.List:CommandMessageType.List:EntityAttributeGroup.List:TagScope.List:MimeType.List:EventGroup.List:UserVisitGroup.List:BaseEncryptionKey.List:CacheEntry.List:QueueType.List:Application.List:Editor.List:Appearance.List:Color.List:Company.List:GlAccount.List:GlAccountCategory.List:GlAccountClass.List:GlResourceType.List:Tax.List:Term.List:TransactionType.List:TransactionGroup.List:Currency.List:ItemAccountingCategory.List:Offer.List:Use.List:Source.List:UseType.List:OfferNameElement.List:UseNameElement.List:AssociateProgram.List:Chain.List:LetterSource.List:Club.List:Configuration.List:ContentCollection.List:ContentWebAddress.List:Customer.List:Inventory.List:Item.List:ItemCategory.List:ItemAliasType.List:ItemDescriptionType.List:ItemDescriptionTypeUseType.List:ItemImageType.List:RelatedItemType.List:Filter.List:Forum.List:ForumGroup.List:Vendor.Search:ItemPurchasingCategory.List:SalesOrder.List:Selector.List:Sequence.List:Carrier.List:ShippingMethod.List:ShipmentType.List:Subscription.List:UnitOfMeasureKind.List:UnitOfMeasureKindUseType.List:Warehouse.List:Wishlist.List:ReturnPolicy.List:ReturnKind.List:CancellationPolicy.List:CancellationKind.List:PaymentMethod.List:PaymentProcessor.List:Employee.Search:ResponsibilityType.List:SkillType.List:LeaveReason.List:LeaveType.List:TerminationReason.List:TerminationType.List:TrainingClass.List:EmployeeType.List:VendorType.List:HarmonizedTariffScheduleCodeUseType.List:HarmonizedTariffScheduleCodeUnit.List" />
<et:hasSecurityRole securityRoles="Company.List:GlAccount.List:GlAccountCategory.List:GlAccountClass.List:GlResourceType.List:Tax.List:Term.List:TransactionType.List:TransactionGroup.List:Currency.List:ItemAccountingCategory.List">
    <a href="<c:url value="/action/Accounting/Main" />">Accounting</a><br />
</et:hasSecurityRole>
<et:hasSecurityRole securityRoles="Offer.List:Use.List:Source.List:UseType.List:OfferNameElement.List:UseNameElement.List">
    <a href="<c:url value="/action/Advertising/Main" />">Advertising</a><br />
</et:hasSecurityRole>
<et:hasSecurityRole securityRoles="AssociateProgram.List">
    <a href="<c:url value="/action/Associate/Main" />">Associates</a><br />
</et:hasSecurityRole>
<et:hasSecurityRole securityRoles="Chain.List:LetterSource.List">
    <a href="<c:url value="/action/Chain/Main" />">Chains</a><br />
</et:hasSecurityRole>
<et:hasSecurityRole securityRole="Club.List">
    <a href="<c:url value="/action/Club/Main" />">Clubs</a><br />
</et:hasSecurityRole>
<et:hasSecurityRole securityRole="Configuration.List">
    <a href="<c:url value="/action/Configuration/Main" />">Configuration</a><br />
</et:hasSecurityRole>
<et:hasSecurityRole securityRoles="ContactListType.List:ContactListGroup.List:ContactListFrequency.List:ContactList.List">
    <a href="<c:url value="/action/ContactList/Main" />">Contact Lists</a><br />
</et:hasSecurityRole>
<et:hasSecurityRole securityRoles="ComponentVendor.List:CommandMessageType.List:EntityAttributeGroup.List:TagScope.List:MimeType.List:EventGroup.List:UserVisitGroup.List:BaseEncryptionKey.List:CacheEntry.List:QueueType.List:Application.List:Editor.List:Appearance.List:Color.List">
    <a href="<c:url value="/action/Core/Main" />">Core</a><br />
</et:hasSecurityRole>
<et:hasSecurityRole securityRoles="ContentCollection.List:ContentWebAddress.List">
    <a href="<c:url value="/action/Content/Main" />">Content</a><br />
</et:hasSecurityRole>
<et:hasSecurityRole securityRole="Customer.List">
    <a href="<c:url value="/action/Customer/Main" />">Customers</a><br />
</et:hasSecurityRole>
<et:hasSecurityRole securityRoles="Employee.Search:ResponsibilityType.List:SkillType.List:LeaveReason.List:LeaveType.List:TerminationReason.List:TerminationType.List:TrainingClass.List:EmployeeType.List">
    <a href="<c:url value="/action/HumanResources/Main" />">Human Resources</a><br />
</et:hasSecurityRole>
<et:hasSecurityRole securityRoles="Item.List:ItemCategory.List:ItemAliasType.List:ItemDescriptionType.List:ItemDescriptionTypeUseType.List:ItemImageType.List:RelatedItemType.List:HarmonizedTariffScheduleCodeUseType.List:HarmonizedTariffScheduleCodeUnit.List">
    <a href="<c:url value="/action/Item/Main" />">Items</a><br />
</et:hasSecurityRole>
<et:hasSecurityRole securityRole="Filter.List">
    <a href="<c:url value="/action/Filter/Main" />">Filters</a><br />
</et:hasSecurityRole>
<et:hasSecurityRole securityRoles="Forum.List:ForumGroup.List">
    <a href="<c:url value="/action/Forum/Main" />">Forums</a><br />
</et:hasSecurityRole>
<et:hasSecurityRole securityRole="Inventory.List">
    <a href="<c:url value="/action/Inventory/Main" />">Inventory</a><br />
</et:hasSecurityRole>
<et:hasSecurityRole securityRoles="PaymentMethod.List:PaymentProcessor.List">
    <a href="<c:url value="/action/Payment/Main" />">Payments</a><br />
</et:hasSecurityRole>
<et:hasSecurityRole securityRoles="Vendor.Search:ItemPurchasingCategory.List:VendorType.List">
    <a href="<c:url value="/action/Purchasing/Main" />">Purchasing</a><br />
</et:hasSecurityRole>
<et:hasSecurityRole securityRoles="CancellationPolicy.List:CancellationKind.List">
    <a href="<c:url value="/action/CancellationPolicy/Main" />">Cancellations</a><br />
</et:hasSecurityRole>
<et:hasSecurityRole securityRoles="ReturnPolicy.List:ReturnKind.List">
    <a href="<c:url value="/action/ReturnPolicy/Main" />">Returns</a><br />
</et:hasSecurityRole>
<et:hasSecurityRole securityRole="SalesOrder.List">
    <a href="<c:url value="/action/SalesOrder/Main" />">Sales Orders</a><br />
</et:hasSecurityRole>
<et:hasSecurityRole securityRole="Selector.List">
    <a href="<c:url value="/action/Selector/Main" />">Selectors</a><br />
</et:hasSecurityRole>
<et:hasSecurityRole securityRole="Sequence.List">
    <a href="<c:url value="/action/Sequence/Main" />">Sequences</a><br />
</et:hasSecurityRole>
<et:hasSecurityRole securityRole="Subscription.List">
    <a href="<c:url value="/action/Subscription/Main" />">Subscriptions</a><br />
</et:hasSecurityRole>
<et:hasSecurityRole securityRoles="UnitOfMeasureKind.List:UnitOfMeasureKindUseType.List">
    <a href="<c:url value="/action/UnitOfMeasure/Main" />">Units of Measure</a><br />
</et:hasSecurityRole>
<et:hasSecurityRole securityRole="Warehouse.List">
    <a href="<c:url value="/action/Warehouse/Main" />"><fmt:message key="navigation.warehouses" /></a><br />
</et:hasSecurityRole>
<et:hasSecurityRole securityRoles="Carrier.List:ShippingMethod.List:ShipmentType.List">
    <a href="<c:url value="/action/Shipping/Main" />">Shipping</a><br />
</et:hasSecurityRole>
<et:hasSecurityRole securityRole="Wishlist.List">
    <a href="<c:url value="/action/Wishlist/Main" />">Wishlists</a><br />
</et:hasSecurityRole>
<br />
