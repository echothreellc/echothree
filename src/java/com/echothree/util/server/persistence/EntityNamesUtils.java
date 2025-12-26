// --------------------------------------------------------------------------------
// Copyright 2002-2026 Echo Three, LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
// --------------------------------------------------------------------------------

package com.echothree.util.server.persistence;

import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.security.server.logic.SecurityRoleLogic;
import com.echothree.model.control.sequence.common.SequenceTypes;
import com.echothree.model.control.sequence.server.logic.SequenceGeneratorLogic;
import com.echothree.model.control.warehouse.server.control.WarehouseControl;
import com.echothree.model.data.communication.common.pk.CommunicationEventPK;
import com.echothree.model.data.communication.server.factory.CommunicationEventFactory;
import com.echothree.model.data.contactlist.common.pk.PartyContactListPK;
import com.echothree.model.data.contactlist.server.factory.PartyContactListFactory;
import com.echothree.model.data.core.common.pk.ComponentVendorPK;
import com.echothree.model.data.core.common.pk.EntityAttributeGroupPK;
import com.echothree.model.data.core.common.pk.EntityAttributePK;
import com.echothree.model.data.core.common.pk.EntityListItemPK;
import com.echothree.model.data.core.common.pk.EntityTypePK;
import com.echothree.model.data.core.common.pk.MimeTypePK;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.factory.ComponentVendorFactory;
import com.echothree.model.data.core.server.factory.EntityAttributeFactory;
import com.echothree.model.data.core.server.factory.EntityAttributeGroupFactory;
import com.echothree.model.data.core.server.factory.EntityListItemFactory;
import com.echothree.model.data.core.server.factory.EntityTypeFactory;
import com.echothree.model.data.core.server.factory.MimeTypeFactory;
import com.echothree.model.data.forum.common.pk.ForumGroupPK;
import com.echothree.model.data.forum.common.pk.ForumMessagePK;
import com.echothree.model.data.forum.common.pk.ForumPK;
import com.echothree.model.data.forum.common.pk.ForumThreadPK;
import com.echothree.model.data.forum.server.factory.ForumFactory;
import com.echothree.model.data.forum.server.factory.ForumGroupFactory;
import com.echothree.model.data.forum.server.factory.ForumMessageFactory;
import com.echothree.model.data.forum.server.factory.ForumThreadFactory;
import com.echothree.model.data.item.common.pk.ItemDescriptionPK;
import com.echothree.model.data.item.common.pk.ItemPK;
import com.echothree.model.data.item.server.factory.ItemDescriptionFactory;
import com.echothree.model.data.item.server.factory.ItemFactory;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.subscription.common.pk.SubscriptionPK;
import com.echothree.model.data.subscription.server.factory.SubscriptionFactory;
import com.echothree.model.data.training.common.pk.PartyTrainingClassPK;
import com.echothree.model.data.training.common.pk.TrainingClassPK;
import com.echothree.model.data.training.server.factory.PartyTrainingClassFactory;
import com.echothree.model.data.training.server.factory.TrainingClassFactory;
import com.echothree.model.data.vendor.common.pk.VendorItemPK;
import com.echothree.model.data.vendor.server.factory.VendorItemFactory;
import com.echothree.model.data.warehouse.common.pk.LocationPK;
import com.echothree.model.data.warehouse.server.factory.LocationFactory;
import com.echothree.util.common.persistence.EntityNames;
import com.echothree.util.common.persistence.Names;
import com.echothree.util.common.persistence.Targets;
import com.echothree.util.common.transfer.MapWrapper;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.translator.ComponentVendorTranslator;
import com.echothree.util.server.persistence.translator.EntityInstanceAndNames;
import com.echothree.util.server.persistence.translator.EntityInstanceTranslator;
import com.echothree.util.server.persistence.translator.InvoiceNameTranslator;
import com.echothree.util.server.persistence.translator.OrderNameTranslator;
import com.echothree.util.server.persistence.translator.PartyNameTranslator;
import com.echothree.util.server.persistence.translator.SequenceTypeTranslator;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class EntityNamesUtils {
    
    private EntityNamesUtils() {
        super();
    }
    
    private static class EntityNamesUtilsHolder {
        static EntityNamesUtils instance = new EntityNamesUtils();
    }
    
    public static EntityNamesUtils getInstance() {
        return EntityNamesUtilsHolder.instance;
    }

    private final static Map<String, ComponentVendorTranslator> componentVendorTranslators = new HashMap<>();

    public static void addComponentVendorTranslator(String componentVendorName, ComponentVendorTranslator componentVendorTranslator) {
        componentVendorTranslators.put(componentVendorName, componentVendorTranslator);
    }

    static {
        Map<String, EntityInstanceTranslator> nameTranslators = new HashMap<>(16);

        nameTranslators.put(EntityTypes.Invoice.name(), new InvoiceNameTranslator());
        nameTranslators.put(EntityTypes.Order.name(), new OrderNameTranslator());
        nameTranslators.put(EntityTypes.Party.name(), new PartyNameTranslator());

        nameTranslators.put(EntityTypes.Item.name(), (final EntityInstance entityInstance) -> {
            var itemDetail = ItemFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY,
                    new ItemPK(entityInstance.getEntityUniqueId())).getLastDetail();
            var names = new MapWrapper<String>(1);

            names.put(Names.ItemName.name(), itemDetail.getItemName());

            return new EntityNames(Targets.Item.name(), names);
        });

        nameTranslators.put(EntityTypes.ItemDescription.name(), (final EntityInstance entityInstance) -> {
            var itemDescriptionDetail = ItemDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY,
                    new ItemDescriptionPK(entityInstance.getEntityUniqueId())).getLastDetail();
            var names = new MapWrapper<String>(3);

            names.put(Names.ItemDescriptionTypeName.name(), itemDescriptionDetail.getItemDescriptionType().getLastDetail().getItemDescriptionTypeName());
            names.put(Names.ItemName.name(), itemDescriptionDetail.getItem().getLastDetail().getItemName());
            names.put(Names.LanguageIsoName.name(), itemDescriptionDetail.getLanguage().getLanguageIsoName());

            return new EntityNames(Targets.ItemDescription.name(), names);
        });

        nameTranslators.put(EntityTypes.ForumGroup.name(), (final EntityInstance entityInstance) -> {
            var forumGroupDetail = ForumGroupFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY,
                    new ForumGroupPK(entityInstance.getEntityUniqueId())).getLastDetail();
            var names = new MapWrapper<String>(1);

            names.put(Names.ForumGroupName.name(), forumGroupDetail.getForumGroupName());

            return new EntityNames(Targets.ForumGroup.name(), names);
        });

        nameTranslators.put(EntityTypes.Forum.name(), (final EntityInstance entityInstance) -> {
            var forumDetail = ForumFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY,
                    new ForumPK(entityInstance.getEntityUniqueId())).getLastDetail();
            var names = new MapWrapper<String>(1);

            names.put(Names.ForumName.name(), forumDetail.getForumName());

            return new EntityNames(Targets.Forum.name(), names);
        });

        nameTranslators.put(EntityTypes.ForumMessage.name(), (final EntityInstance entityInstance) -> {
            var forumMessageDetail = ForumMessageFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY,
                    new ForumMessagePK(entityInstance.getEntityUniqueId())).getLastDetail();
            var names = new MapWrapper<String>(1);

            names.put(Names.ForumMessageName.name(), forumMessageDetail.getForumMessageName());

            return new EntityNames(Targets.ForumMessage.name(), names);
        });

        nameTranslators.put(EntityTypes.ForumThread.name(), (final EntityInstance entityInstance) -> {
            var forumThreadDetail = ForumThreadFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY,
                    new ForumThreadPK(entityInstance.getEntityUniqueId())).getLastDetail();
            var names = new MapWrapper<String>(1);

            names.put(Names.ForumThreadName.name(), forumThreadDetail.getForumThreadName());

            return new EntityNames(Targets.ForumThread.name(), names);
        });

        nameTranslators.put(EntityTypes.TrainingClass.name(), (final EntityInstance entityInstance) -> {
            var trainingClassDetail = TrainingClassFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY,
                    new TrainingClassPK(entityInstance.getEntityUniqueId())).getLastDetail();
            var names = new MapWrapper<String>(1);

            names.put(Names.TrainingClassName.name(), trainingClassDetail.getTrainingClassName());

            return new EntityNames(Targets.TrainingClass.name(), names);
        });

        nameTranslators.put(EntityTypes.PartyTrainingClass.name(), (final EntityInstance entityInstance) -> {
            var partyTrainingClassDetail = PartyTrainingClassFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY,
                    new PartyTrainingClassPK(entityInstance.getEntityUniqueId())).getLastDetail();
            var names = new MapWrapper<String>(1);

            names.put(Names.PartyTrainingClassName.name(), partyTrainingClassDetail.getPartyTrainingClassName());

            return new EntityNames(Targets.PartyTrainingClass.name(), names);
        });

        nameTranslators.put(EntityTypes.CommunicationEvent.name(), (final EntityInstance entityInstance) -> {
            var communicationEventDetail = CommunicationEventFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY,
                    new CommunicationEventPK(entityInstance.getEntityUniqueId())).getLastDetail();
            var names = new MapWrapper<String>(1);

            names.put(Names.CommunicationEventName.name(), communicationEventDetail.getCommunicationEventName());

            return new EntityNames(Targets.CommunicationEvent.name(), names);
        });

        nameTranslators.put(EntityTypes.VendorItem.name(), (final EntityInstance entityInstance) -> {
            var vendorItemDetail = VendorItemFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY,
                    new VendorItemPK(entityInstance.getEntityUniqueId())).getLastDetail();
            var names = new MapWrapper<String>(2);

            names.put(Names.VendorItemName.name(), vendorItemDetail.getVendorItemName());
            names.put(Names.PartyName.name(), vendorItemDetail.getVendorParty().getLastDetail().getPartyName());

            return new EntityNames(Targets.VendorItem.name(), names);
        });

        nameTranslators.put(EntityTypes.PartyContactList.name(), (final EntityInstance entityInstance) -> {
            var partyContactListDetail = PartyContactListFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY,
                    new PartyContactListPK(entityInstance.getEntityUniqueId())).getLastDetail();
            var names = new MapWrapper<String>(2);

            names.put(Names.PartyName.name(), partyContactListDetail.getParty().getLastDetail().getPartyName());
            names.put(Names.ContactListName.name(), partyContactListDetail.getContactList().getLastDetail().getContactListName());

            return new EntityNames(Targets.PartyContactList.name(), names);
        });

        nameTranslators.put(EntityTypes.Subscription.name(), (final EntityInstance entityInstance) -> {
            var subscription = SubscriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY,
                    new SubscriptionPK(entityInstance.getEntityUniqueId()));
            var names = new MapWrapper<String>(1);

            names.put(Names.SubscriptionName.name(), subscription.getLastDetail().getSubscriptionName());

            return new EntityNames(Targets.Subscription.name(), names);
        });

        nameTranslators.put(EntityTypes.MimeType.name(), (final EntityInstance entityInstance) -> {
            var mimeType = MimeTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY,
                    new MimeTypePK(entityInstance.getEntityUniqueId()));
            var names = new MapWrapper<String>(1);

            names.put(Names.MimeTypeName.name(), mimeType.getLastDetail().getMimeTypeName());

            return new EntityNames(Targets.MimeType.name(), names);
        });

        nameTranslators.put(EntityTypes.Location.name(), (final EntityInstance entityInstance) -> {
            var warehouseControl = Session.getModelController(WarehouseControl.class);
            var location = LocationFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY,
                    new LocationPK(entityInstance.getEntityUniqueId()));
            var names = new MapWrapper<String>(2);
            var locationDetail = location.getLastDetail();

            names.put(Names.WarehouseName.name(), warehouseControl.getWarehouse(locationDetail.getWarehouseParty()).getWarehouseName());
            names.put(Names.LocationName.name(), locationDetail.getLocationName());

            return new EntityNames(Targets.Location.name(), names);
        });

        nameTranslators.put(EntityTypes.ComponentVendor.name(), (final EntityInstance entityInstance) -> {
            var componentVendor = ComponentVendorFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY,
                    new ComponentVendorPK(entityInstance.getEntityUniqueId()));
            var names = new MapWrapper<String>(1);

            names.put(Names.ComponentVendorName.name(), componentVendor.getLastDetail().getComponentVendorName());

            return new EntityNames(Targets.ComponentVendor.name(), names);
        });

        nameTranslators.put(EntityTypes.EntityType.name(), (final EntityInstance entityInstance) -> {
            var entityType = EntityTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY,
                    new EntityTypePK(entityInstance.getEntityUniqueId()));
            var names = new MapWrapper<String>(2);
            var entityTypeDetail = entityType.getLastDetail();

            names.put(Names.ComponentVendorName.name(), entityTypeDetail.getComponentVendor().getLastDetail().getComponentVendorName());
            names.put(Names.EntityTypeName.name(), entityTypeDetail.getEntityTypeName());

            return new EntityNames(Targets.EntityType.name(), names);
        });

        nameTranslators.put(EntityTypes.EntityAttribute.name(), (final EntityInstance entityInstance) -> {
            var entityAttribute = EntityAttributeFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY,
                    new EntityAttributePK(entityInstance.getEntityUniqueId()));
            var names = new MapWrapper<String>(3);
            var entityAttributeDetail = entityAttribute.getLastDetail();

            names.put(Names.ComponentVendorName.name(), entityAttributeDetail.getEntityType().getLastDetail().getComponentVendor().getLastDetail().getComponentVendorName());
            names.put(Names.EntityTypeName.name(), entityAttributeDetail.getEntityType().getLastDetail().getEntityTypeName());
            names.put(Names.EntityAttributeName.name(), entityAttributeDetail.getEntityAttributeName());

            return new EntityNames(Targets.EntityAttribute.name(), names);
        });

        nameTranslators.put(EntityTypes.EntityListItem.name(), (final EntityInstance entityInstance) -> {
            var entityListItem = EntityListItemFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY,
                    new EntityListItemPK(entityInstance.getEntityUniqueId()));
            var names = new MapWrapper<String>(4);
            var entityListItemDetail = entityListItem.getLastDetail();

            names.put(Names.ComponentVendorName.name(), entityListItemDetail.getEntityAttribute().getLastDetail().getEntityType().getLastDetail().getEntityType().getLastDetail().getComponentVendor().getLastDetail().getComponentVendorName());
            names.put(Names.EntityTypeName.name(), entityListItemDetail.getEntityAttribute().getLastDetail().getEntityType().getLastDetail().getEntityTypeName());
            names.put(Names.EntityAttributeName.name(), entityListItemDetail.getEntityAttribute().getLastDetail().getEntityAttributeName());
            names.put(Names.EntityListItemName.name(), entityListItemDetail.getEntityListItemName());

            return new EntityNames(Targets.EntityListItem.name(), names);
        });

        nameTranslators.put(EntityTypes.EntityAttributeGroup.name(), (final EntityInstance entityInstance) -> {
            var entityAttributeGroup = EntityAttributeGroupFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY,
                    new EntityAttributeGroupPK(entityInstance.getEntityUniqueId()));
            var names = new MapWrapper<String>(1);
            var entityAttributeGroupDetail = entityAttributeGroup.getLastDetail();

            names.put(Names.EntityAttributeGroupName.name(), entityAttributeGroupDetail.getEntityAttributeGroupName());

            return new EntityNames(Targets.EntityAttributeGroup.name(), names);
        });

        nameTranslators = Collections.unmodifiableMap(nameTranslators);

        addComponentVendorTranslator(ComponentVendors.ECHO_THREE.name(), new ComponentVendorTranslator(nameTranslators));
    }

    // Entry point from the Identify UC. Permissions have already been checked at this point.
    public EntityInstanceAndNames getEntityNames(final EntityInstance entityInstance) {
        EntityNames result = null;
        var entityType = entityInstance.getEntityType();
        var componentVendor = entityType.getLastDetail().getComponentVendor();
        var componentVendorName = componentVendor.getLastDetail().getComponentVendorName();

        var componentVendorTranslator = componentVendorTranslators.get(componentVendorName);

        if(componentVendorTranslator != null) {
            var nameTranslator = componentVendorTranslator.getNameTranslators().get(entityType.getLastDetail().getEntityTypeName());

            if(nameTranslator != null) {
                result = nameTranslator.getNames(entityInstance);
            }
        }

        return result == null ? null : new EntityInstanceAndNames(entityInstance, result);
    }
    
    private final static Map<String, SequenceTypeTranslator> sequenceTypeTranslators;

    private final static InvoiceNameTranslator INVOICE_NAME_TRANSLATOR = new InvoiceNameTranslator();
    private final static OrderNameTranslator ORDER_NAME_TRANSLATOR = new OrderNameTranslator();
    private final static PartyNameTranslator PARTY_NAME_TRANSLATOR = new PartyNameTranslator();

    static {
        var sequenceTypeTranslatorsMap = new HashMap<String, SequenceTypeTranslator>(7);

        sequenceTypeTranslatorsMap.put(SequenceTypes.PURCHASE_INVOICE.name(), INVOICE_NAME_TRANSLATOR);
        sequenceTypeTranslatorsMap.put(SequenceTypes.SALES_INVOICE.name(), INVOICE_NAME_TRANSLATOR);
        sequenceTypeTranslatorsMap.put(SequenceTypes.PURCHASE_ORDER.name(), ORDER_NAME_TRANSLATOR);
        sequenceTypeTranslatorsMap.put(SequenceTypes.SALES_ORDER.name(), ORDER_NAME_TRANSLATOR);
        sequenceTypeTranslatorsMap.put(SequenceTypes.WISHLIST.name(), ORDER_NAME_TRANSLATOR);
        sequenceTypeTranslatorsMap.put(SequenceTypes.CUSTOMER.name(), PARTY_NAME_TRANSLATOR);
        sequenceTypeTranslatorsMap.put(SequenceTypes.EMPLOYEE.name(), PARTY_NAME_TRANSLATOR);
        
        sequenceTypeTranslators = Collections.unmodifiableMap(sequenceTypeTranslatorsMap);
    }
    
    private EntityInstanceAndNames getEntityNames(final String sequenceTypeName, final String value,
            final boolean includeEntityInstance) {
        EntityInstanceAndNames result = null;
        var sequenceTypeTranslator = sequenceTypeTranslators.get(sequenceTypeName);
        
        if(sequenceTypeTranslator != null) {
            result = sequenceTypeTranslator.getNames(sequenceTypeName, value, includeEntityInstance);
        }
        
        return result;
    }

    // Entry point from the Identify UC. Permissions have NOT been checked at this point.
    public EntityInstanceAndNames getEntityNames(final ExecutionErrorAccumulator eea, final Party requestingParty,
            final String value, final boolean includeEntityInstance) {
        EntityInstanceAndNames result = null;
        var sequenceType = SequenceGeneratorLogic.getInstance().identifySequenceType(value);

        if(sequenceType != null) {
            var sequenceTypeName = sequenceType.getLastDetail().getSequenceTypeName();
            var hasAccess = false;

            if(sequenceTypeName.equals(SequenceTypes.PURCHASE_INVOICE.name())
                    && SecurityRoleLogic.getInstance().hasSecurityRoleUsingNames(eea, requestingParty,
                    SecurityRoleGroups.PurchaseInvoice.name(), SecurityRoles.Search.name())) {
                hasAccess = true;
            } else if(sequenceTypeName.equals(SequenceTypes.SALES_INVOICE.name())
                    && SecurityRoleLogic.getInstance().hasSecurityRoleUsingNames(eea, requestingParty,
                    SecurityRoleGroups.SalesOrder.name(), SecurityRoles.Search.name())) {
                hasAccess = true;
            } else if(sequenceTypeName.equals(SequenceTypes.PURCHASE_ORDER.name())
                    && SecurityRoleLogic.getInstance().hasSecurityRoleUsingNames(eea, requestingParty,
                    SecurityRoleGroups.PurchaseOrder.name(), SecurityRoles.Search.name())) {
                hasAccess = true;
            } else if(sequenceTypeName.equals(SequenceTypes.SALES_ORDER.name())
                    && SecurityRoleLogic.getInstance().hasSecurityRoleUsingNames(eea, requestingParty,
                    SecurityRoleGroups.SalesOrder.name(), SecurityRoles.Search.name())) {
                hasAccess = true;
            } else if(sequenceTypeName.equals(SequenceTypes.WISHLIST.name())
                    && SecurityRoleLogic.getInstance().hasSecurityRoleUsingNames(eea, requestingParty,
                    SecurityRoleGroups.Wishlist.name(), SecurityRoles.Search.name())) {
                hasAccess = true;
            } else if(sequenceTypeName.equals(SequenceTypes.CUSTOMER.name())
                    && SecurityRoleLogic.getInstance().hasSecurityRoleUsingNames(eea, requestingParty,
                    SecurityRoleGroups.Customer.name(), SecurityRoles.Search.name())) {
                hasAccess = true;
            } else if(sequenceTypeName.equals(SequenceTypes.EMPLOYEE.name())
                    && SecurityRoleLogic.getInstance().hasSecurityRoleUsingNames(eea, requestingParty,
                    SecurityRoleGroups.Employee.name(), SecurityRoles.Search.name())) {
                hasAccess = true;
            }

            result = hasAccess ? getEntityNames(sequenceTypeName, value, includeEntityInstance) : null;
        }
        
        return result;
    }
    
}
