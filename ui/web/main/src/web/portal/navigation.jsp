<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>

<%@ include file="../include/taglibs.jsp" %>

<et:checkSecurityRoles securityRoles="ContactListFrequency.List:ContactListType.List:ContactListGroup.List:ContactList.List:ComponentVendor.List:CommandMessageType.List:EntityAttributeGroup.List:TagScope.List:MimeType.List:EventGroup.List:UserVisitGroup.List:BaseEncryptionKey.List:CacheEntry.List:QueueType.List:Application.List:Editor.List:Appearance.List:Color.List:Company.List:GlAccount.List:GlAccountCategory.List:GlAccountClass.List:GlResourceType.List:Tax.List:Term.List:TransactionType.List:TransactionGroup.List:Currency.List:ItemAccountingCategory.List:Offer.List:Use.List:Source.List:UseType.List:OfferNameElement.List:UseNameElement.List:AssociateProgram.List:Chain.List:LetterSource.List:Club.List:Configuration.List:ContentCollection.List:ContentWebAddress.List:Customer.List:Inventory.List:Item.List:ItemCategory.List:ItemAliasType.List:ItemWeightType.List:ItemDescriptionType.List:ItemDescriptionTypeUseType.List:ItemImageType.List:RelatedItemType.List:Filter.List:Forum.List:ForumGroup.List:Vendor.Search:ItemPurchasingCategory.List:SalesOrder.List:Selector.List:Sequence.List:Carrier.List:ShippingMethod.List:ShipmentType.List:Subscription.List:UnitOfMeasureKind.List:UnitOfMeasureKindUseType.List:WarehouseType.List:Warehouse.List:Wishlist.List:ReturnPolicy.List:ReturnKind.List:CancellationPolicy.List:CancellationKind.List:PaymentMethod.List:PaymentProcessor.List:Employee.Search:ResponsibilityType.List:SkillType.List:LeaveReason.List:LeaveType.List:TerminationReason.List:TerminationType.List:TrainingClass.List:EmployeeType.List:VendorType.List:HarmonizedTariffScheduleCodeUseType.List:HarmonizedTariffScheduleCodeUnit.List" />
<et:hasSecurityRole securityRoles="Company.List:GlAccount.List:GlAccountCategory.List:GlAccountClass.List:GlResourceType.List:Tax.List:Term.List:TransactionType.List:TransactionGroup.List:Currency.List:ItemAccountingCategory.List">
    <a href="<c:url value="/action/Accounting/Main" />"><fmt:message key="navigation.accounting" /></a><br />
</et:hasSecurityRole>
<et:hasSecurityRole securityRoles="Offer.List:Use.List:Source.List:UseType.List:OfferNameElement.List:UseNameElement.List">
    <a href="<c:url value="/action/Advertising/Main" />"><fmt:message key="navigation.advertising" /></a><br />
</et:hasSecurityRole>
<et:hasSecurityRole securityRoles="AssociateProgram.List">
    <a href="<c:url value="/action/Associate/Main" />"><fmt:message key="navigation.associates" /></a><br />
</et:hasSecurityRole>
<et:hasSecurityRole securityRoles="Chain.List:LetterSource.List">
    <a href="<c:url value="/action/Chain/Main" />"><fmt:message key="navigation.chains" /></a><br />
</et:hasSecurityRole>
<et:hasSecurityRole securityRole="Club.List">
    <a href="<c:url value="/action/Club/Main" />"><fmt:message key="navigation.clubs" /></a><br />
</et:hasSecurityRole>
<et:hasSecurityRole securityRole="Configuration.List">
    <a href="<c:url value="/action/Configuration/Main" />"><fmt:message key="navigation.configuration" /></a><br />
</et:hasSecurityRole>
<et:hasSecurityRole securityRoles="ContactListType.List:ContactListGroup.List:ContactListFrequency.List:ContactList.List">
    <a href="<c:url value="/action/ContactList/Main" />"><fmt:message key="navigation.contactLists" /></a><br />
</et:hasSecurityRole>
<et:hasSecurityRole securityRoles="ComponentVendor.List:CommandMessageType.List:EntityAttributeGroup.List:TagScope.List:MimeType.List:EventGroup.List:UserVisitGroup.List:BaseEncryptionKey.List:CacheEntry.List:QueueType.List:Application.List:Editor.List:Appearance.List:Color.List">
    <a href="<c:url value="/action/Core/Main" />"><fmt:message key="navigation.core" /></a><br />
</et:hasSecurityRole>
<et:hasSecurityRole securityRoles="ContentCollection.List:ContentWebAddress.List">
    <a href="<c:url value="/action/Content/Main" />"><fmt:message key="navigation.content" /></a><br />
</et:hasSecurityRole>
<et:hasSecurityRole securityRole="Customer.List">
    <a href="<c:url value="/action/Customer/Main" />"><fmt:message key="navigation.customers" /></a><br />
</et:hasSecurityRole>
<et:hasSecurityRole securityRoles="Employee.Search:ResponsibilityType.List:SkillType.List:LeaveReason.List:LeaveType.List:TerminationReason.List:TerminationType.List:TrainingClass.List:EmployeeType.List">
    <a href="<c:url value="/action/HumanResources/Main" />"><fmt:message key="navigation.humanResources" /></a><br />
</et:hasSecurityRole>
<et:hasSecurityRole securityRoles="Item.List:ItemCategory.List:ItemAliasType.List:ItemWeightType.List:ItemDescriptionType.List:ItemDescriptionTypeUseType.List:ItemImageType.List:RelatedItemType.List:HarmonizedTariffScheduleCodeUseType.List:HarmonizedTariffScheduleCodeUnit.List">
    <a href="<c:url value="/action/Item/Main" />"><fmt:message key="navigation.items" /></a><br />
</et:hasSecurityRole>
<et:hasSecurityRole securityRole="Filter.List">
    <a href="<c:url value="/action/Filter/Main" />"><fmt:message key="navigation.filters" /></a><br />
</et:hasSecurityRole>
<et:hasSecurityRole securityRoles="Forum.List:ForumGroup.List">
    <a href="<c:url value="/action/Forum/Main" />"><fmt:message key="navigation.forums" /></a><br />
</et:hasSecurityRole>
<et:hasSecurityRole securityRole="Inventory.List">
    <a href="<c:url value="/action/Inventory/Main" />"><fmt:message key="navigation.inventory" /></a><br />
</et:hasSecurityRole>
<et:hasSecurityRole securityRoles="PaymentMethod.List:PaymentProcessor.List">
    <a href="<c:url value="/action/Payment/Main" />"><fmt:message key="navigation.payments" /></a><br />
</et:hasSecurityRole>
<et:hasSecurityRole securityRoles="Vendor.Search:ItemPurchasingCategory.List:VendorType.List">
    <a href="<c:url value="/action/Purchasing/Main" />"><fmt:message key="navigation.purchasing" /></a><br />
</et:hasSecurityRole>
<et:hasSecurityRole securityRoles="CancellationPolicy.List:CancellationKind.List">
    <a href="<c:url value="/action/CancellationPolicy/Main" />"><fmt:message key="navigation.cancellations" /></a><br />
</et:hasSecurityRole>
<et:hasSecurityRole securityRoles="ReturnPolicy.List:ReturnKind.List">
    <a href="<c:url value="/action/ReturnPolicy/Main" />"><fmt:message key="navigation.returns" /></a><br />
</et:hasSecurityRole>
<et:hasSecurityRole securityRole="SalesOrder.List">
    <a href="<c:url value="/action/SalesOrder/Main" />"><fmt:message key="navigation.salesOrders" /></a><br />
</et:hasSecurityRole>
<et:hasSecurityRole securityRole="Selector.List">
    <a href="<c:url value="/action/Selector/Main" />"><fmt:message key="navigation.selectors" /></a><br />
</et:hasSecurityRole>
<et:hasSecurityRole securityRole="Sequence.List">
    <a href="<c:url value="/action/Sequence/Main" />"><fmt:message key="navigation.sequences" /></a><br />
</et:hasSecurityRole>
<et:hasSecurityRole securityRole="Subscription.List">
    <a href="<c:url value="/action/Subscription/Main" />"><fmt:message key="navigation.subscriptions" /></a><br />
</et:hasSecurityRole>
<et:hasSecurityRole securityRoles="UnitOfMeasureKind.List:UnitOfMeasureKindUseType.List">
    <a href="<c:url value="/action/UnitOfMeasure/Main" />"><fmt:message key="navigation.unitsOfMeasure" /></a><br />
</et:hasSecurityRole>
<et:hasSecurityRole securityRoles="WarehouseType.List:Warehouse.List">
    <a href="<c:url value="/action/Warehouse/Main" />"><fmt:message key="navigation.warehouses" /></a><br />
</et:hasSecurityRole>
<et:hasSecurityRole securityRoles="Carrier.List:ShippingMethod.List:ShipmentType.List">
    <a href="<c:url value="/action/Shipping/Main" />"><fmt:message key="navigation.shipping" /></a><br />
</et:hasSecurityRole>
<et:hasSecurityRole securityRole="Wishlist.List">
    <a href="<c:url value="/action/Wishlist/Main" />"><fmt:message key="navigation.wishlists" /></a><br />
</et:hasSecurityRole>
<br />
