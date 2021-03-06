// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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

package com.echothree.cucumber.util.persona;

import com.echothree.control.user.contact.common.edit.ContactEmailAddressEdit;
import com.echothree.control.user.contact.common.edit.ContactPostalAddressEdit;
import com.echothree.control.user.contact.common.edit.ContactTelephoneEdit;
import com.echothree.control.user.contact.common.edit.ContactWebAddressEdit;
import com.echothree.control.user.contact.common.form.CreateContactEmailAddressForm;
import com.echothree.control.user.contact.common.form.CreateContactPostalAddressForm;
import com.echothree.control.user.contact.common.form.CreateContactTelephoneForm;
import com.echothree.control.user.contact.common.form.CreateContactWebAddressForm;
import com.echothree.control.user.contact.common.spec.PartyContactMechanismSpec;
import com.echothree.control.user.content.common.edit.ContentCatalogEdit;
import com.echothree.control.user.content.common.edit.ContentCategoryEdit;
import com.echothree.control.user.content.common.edit.ContentCategoryItemEdit;
import com.echothree.control.user.content.common.edit.ContentCollectionEdit;
import com.echothree.control.user.content.common.edit.ContentPageEdit;
import com.echothree.control.user.content.common.edit.ContentSectionEdit;
import com.echothree.control.user.content.common.form.CreateContentCatalogForm;
import com.echothree.control.user.content.common.form.CreateContentCategoryForm;
import com.echothree.control.user.content.common.form.CreateContentCategoryItemForm;
import com.echothree.control.user.content.common.form.CreateContentCollectionForm;
import com.echothree.control.user.content.common.form.CreateContentPageForm;
import com.echothree.control.user.content.common.form.CreateContentSectionForm;
import com.echothree.control.user.content.common.form.DeleteContentCatalogForm;
import com.echothree.control.user.content.common.form.DeleteContentCategoryForm;
import com.echothree.control.user.content.common.form.DeleteContentCategoryItemForm;
import com.echothree.control.user.content.common.form.DeleteContentCollectionForm;
import com.echothree.control.user.content.common.form.DeleteContentPageForm;
import com.echothree.control.user.content.common.form.DeleteContentSectionForm;
import com.echothree.control.user.content.common.spec.ContentCatalogSpec;
import com.echothree.control.user.content.common.spec.ContentCategoryItemSpec;
import com.echothree.control.user.content.common.spec.ContentCategorySpec;
import com.echothree.control.user.content.common.spec.ContentCollectionSpec;
import com.echothree.control.user.content.common.spec.ContentPageSpec;
import com.echothree.control.user.content.common.spec.ContentSectionSpec;
import com.echothree.control.user.core.common.edit.EntityAttributeEdit;
import com.echothree.control.user.core.common.edit.EntityAttributeEntityAttributeGroupEdit;
import com.echothree.control.user.core.common.edit.EntityAttributeGroupEdit;
import com.echothree.control.user.core.common.edit.EntityListItemEdit;
import com.echothree.control.user.core.common.form.CreateEntityAttributeEntityAttributeGroupForm;
import com.echothree.control.user.core.common.form.CreateEntityAttributeForm;
import com.echothree.control.user.core.common.form.CreateEntityAttributeGroupForm;
import com.echothree.control.user.core.common.form.CreateEntityListItemForm;
import com.echothree.control.user.core.common.form.DeleteEntityAttributeEntityAttributeGroupForm;
import com.echothree.control.user.core.common.form.DeleteEntityAttributeForm;
import com.echothree.control.user.core.common.form.DeleteEntityAttributeGroupForm;
import com.echothree.control.user.core.common.form.DeleteEntityListItemForm;
import com.echothree.control.user.core.common.spec.EntityAttributeEntityAttributeGroupSpec;
import com.echothree.control.user.core.common.spec.EntityAttributeGroupSpec;
import com.echothree.control.user.core.common.spec.EntityAttributeSpec;
import com.echothree.control.user.core.common.spec.EntityListItemSpec;
import com.echothree.control.user.item.common.edit.ItemEdit;
import com.echothree.control.user.item.common.edit.ItemPriceEdit;
import com.echothree.control.user.item.common.edit.ItemUnitOfMeasureTypeEdit;
import com.echothree.control.user.item.common.form.CreateItemForm;
import com.echothree.control.user.item.common.form.CreateItemPriceForm;
import com.echothree.control.user.item.common.form.CreateItemUnitOfMeasureTypeForm;
import com.echothree.control.user.item.common.form.DeleteItemPriceForm;
import com.echothree.control.user.item.common.form.DeleteItemUnitOfMeasureTypeForm;
import com.echothree.control.user.item.common.spec.ItemPriceSpec;
import com.echothree.control.user.item.common.spec.ItemSpec;
import com.echothree.control.user.item.common.spec.ItemUnitOfMeasureTypeSpec;
import com.echothree.control.user.offer.common.edit.OfferEdit;
import com.echothree.control.user.offer.common.edit.OfferItemPriceEdit;
import com.echothree.control.user.offer.common.edit.OfferUseEdit;
import com.echothree.control.user.offer.common.edit.SourceEdit;
import com.echothree.control.user.offer.common.edit.UseEdit;
import com.echothree.control.user.offer.common.edit.UseTypeEdit;
import com.echothree.control.user.offer.common.form.CreateOfferForm;
import com.echothree.control.user.offer.common.form.CreateOfferItemForm;
import com.echothree.control.user.offer.common.form.CreateOfferItemPriceForm;
import com.echothree.control.user.offer.common.form.CreateOfferUseForm;
import com.echothree.control.user.offer.common.form.CreateSourceForm;
import com.echothree.control.user.offer.common.form.CreateUseForm;
import com.echothree.control.user.offer.common.form.CreateUseTypeForm;
import com.echothree.control.user.offer.common.form.DeleteOfferForm;
import com.echothree.control.user.offer.common.form.DeleteOfferItemForm;
import com.echothree.control.user.offer.common.form.DeleteOfferItemPriceForm;
import com.echothree.control.user.offer.common.form.DeleteOfferUseForm;
import com.echothree.control.user.offer.common.form.DeleteSourceForm;
import com.echothree.control.user.offer.common.form.DeleteUseForm;
import com.echothree.control.user.offer.common.form.DeleteUseTypeForm;
import com.echothree.control.user.offer.common.spec.OfferItemPriceSpec;
import com.echothree.control.user.offer.common.spec.OfferSpec;
import com.echothree.control.user.offer.common.spec.OfferUseSpec;
import com.echothree.control.user.offer.common.spec.SourceSpec;
import com.echothree.control.user.offer.common.spec.UseSpec;
import com.echothree.control.user.offer.common.spec.UseTypeSpec;
import com.echothree.control.user.offer.common.spec.UseTypeUniversalSpec;
import com.echothree.control.user.party.common.form.CreateCustomerWithLoginForm;
import com.echothree.control.user.party.common.form.CreateVendorForm;
import com.echothree.control.user.payment.common.form.CreatePartyPaymentMethodForm;
import com.echothree.control.user.purchase.common.edit.PurchaseOrderEdit;
import com.echothree.control.user.purchase.common.form.CreatePurchaseOrderForm;
import com.echothree.control.user.purchase.common.form.SetPurchaseOrderStatusForm;
import com.echothree.control.user.purchase.common.spec.PurchaseOrderSpec;
import com.echothree.control.user.vendor.common.edit.VendorEdit;
import com.echothree.control.user.vendor.common.edit.VendorItemCostEdit;
import com.echothree.control.user.vendor.common.edit.VendorItemEdit;
import com.echothree.control.user.vendor.common.form.CreateVendorItemCostForm;
import com.echothree.control.user.vendor.common.form.CreateVendorItemForm;
import com.echothree.control.user.vendor.common.form.DeleteVendorItemCostForm;
import com.echothree.control.user.vendor.common.form.DeleteVendorItemForm;
import com.echothree.control.user.vendor.common.form.SetVendorItemStatusForm;
import com.echothree.control.user.vendor.common.spec.VendorItemCostSpec;
import com.echothree.control.user.vendor.common.spec.VendorItemSpec;
import com.echothree.control.user.vendor.common.spec.VendorSpec;
import com.echothree.cucumber.authentication.UserVisits;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import javax.naming.NamingException;

public class BasePersona {

    public String persona;
    public UserVisitPK userVisitPK;

    public BasePersona(String persona)
            throws NamingException {
        this.persona = persona;
        userVisitPK = UserVisits.getUserVisitPK();
    }

    // Contact
    public PartyContactMechanismSpec partyContactMechanismSpec;

    public CreateContactEmailAddressForm createContactEmailAddressForm;
    public ContactEmailAddressEdit contactEmailAddressEdit;

    public CreateContactPostalAddressForm createContactPostalAddressForm;
    public ContactPostalAddressEdit contactPostalAddressEdit;

    public CreateContactTelephoneForm createContactTelephoneForm;
    public ContactTelephoneEdit contactTelephoneEdit;

    public CreateContactWebAddressForm createContactWebAddressForm;
    public ContactWebAddressEdit contactWebAddressEdit;

    public String lastEmailAddressContactMechanismName;
    public String lastPostalAddressContactMechanismName;
    public String lastTelephoneContactMechanismName;
    public String lastWebAddressContactMechanismName;

    // Content
    public CreateContentCollectionForm createContentCollectionForm;
    public DeleteContentCollectionForm deleteContentCollectionForm;
    public ContentCollectionSpec contentCollectionSpec;
    public ContentCollectionEdit contentCollectionEdit;

    public CreateContentSectionForm createContentSectionForm;
    public DeleteContentSectionForm deleteContentSectionForm;
    public ContentSectionSpec contentSectionSpec;
    public ContentSectionEdit contentSectionEdit;

    public CreateContentPageForm createContentPageForm;
    public DeleteContentPageForm deleteContentPageForm;
    public ContentPageSpec contentPageSpec;
    public ContentPageEdit contentPageEdit;

    public CreateContentCatalogForm createContentCatalogForm;
    public DeleteContentCatalogForm deleteContentCatalogForm;
    public ContentCatalogSpec contentCatalogSpec;
    public ContentCatalogEdit contentCatalogEdit;

    public CreateContentCategoryForm createContentCategoryForm;
    public DeleteContentCategoryForm deleteContentCategoryForm;
    public ContentCategorySpec contentCategorySpec;
    public ContentCategoryEdit contentCategoryEdit;

    public CreateContentCategoryItemForm createContentCategoryItemForm;
    public DeleteContentCategoryItemForm deleteContentCategoryItemForm;
    public ContentCategoryItemSpec contentCategoryItemSpec;
    public ContentCategoryItemEdit contentCategoryItemEdit;

    // Core
    public CreateEntityAttributeGroupForm createEntityAttributeGroupForm;
    public DeleteEntityAttributeGroupForm deleteEntityAttributeGroupForm;
    public EntityAttributeGroupSpec entityAttributeGroupSpec;
    public EntityAttributeGroupEdit entityAttributeGroupEdit;

    public CreateEntityAttributeForm createEntityAttributeForm;
    public DeleteEntityAttributeForm deleteEntityAttributeForm;
    public EntityAttributeSpec entityAttributeSpec;
    public EntityAttributeEdit entityAttributeEdit;

    public CreateEntityListItemForm createEntityListItemForm;
    public DeleteEntityListItemForm deleteEntityListItemForm;
    public EntityListItemSpec entityListItemSpec;
    public EntityListItemEdit entityListItemEdit;

    public CreateEntityAttributeEntityAttributeGroupForm createEntityAttributeEntityAttributeGroupForm;
    public DeleteEntityAttributeEntityAttributeGroupForm deleteEntityAttributeEntityAttributeGroupForm;
    public EntityAttributeEntityAttributeGroupSpec entityAttributeEntityAttributeGroupSpec;
    public EntityAttributeEntityAttributeGroupEdit entityAttributeEntityAttributeGroupEdit;

    public String lastEntityAttributeGroupName;
    public String lastEntityAttributeName;
    public String lastEntityListItemName;
    public String lastEntityRef;

    // Customer
    public CreateCustomerWithLoginForm createCustomerWithLoginForm;

    public String lastCustomerName;

    // Employee
    public String lastEmployeeName;

    // Item
    public CreateItemForm createItemForm;
    public ItemSpec itemSpec;
    public ItemEdit itemEdit;

    public CreateItemPriceForm createItemPriceForm;
    public DeleteItemPriceForm deleteItemPriceForm;
    public ItemPriceSpec itemPriceSpec;
    public ItemPriceEdit itemPriceEdit;

    public CreateItemUnitOfMeasureTypeForm createItemUnitOfMeasureTypeForm;
    public DeleteItemUnitOfMeasureTypeForm deleteItemUnitOfMeasureTypeForm;
    public ItemUnitOfMeasureTypeSpec itemUnitOfMeasureTypeSpec;
    public ItemUnitOfMeasureTypeEdit itemUnitOfMeasureTypeEdit;

    public String lastItemName;

    // Offer
    public CreateOfferForm createOfferForm;
    public DeleteOfferForm deleteOfferForm;
    public OfferSpec offerSpec;
    public OfferEdit offerEdit;

    public CreateOfferItemForm createOfferItemForm;
    public DeleteOfferItemForm deleteOfferItemForm;

    public CreateOfferItemPriceForm createOfferItemPriceForm;
    public DeleteOfferItemPriceForm deleteOfferItemPriceForm;
    public OfferItemPriceSpec offerItemPriceSpec;
    public OfferItemPriceEdit offerItemPriceEdit;

    public CreateUseTypeForm createUseTypeForm;
    public DeleteUseTypeForm deleteUseTypeForm;
    public UseTypeUniversalSpec useTypeSpec;
    public UseTypeEdit useTypeEdit;

    public CreateUseForm createUseForm;
    public DeleteUseForm deleteUseForm;
    public UseSpec useSpec;
    public UseEdit useEdit;

    public CreateOfferUseForm createOfferUseForm;
    public DeleteOfferUseForm deleteOfferUseForm;
    public OfferUseSpec offerUseSpec;
    public OfferUseEdit offerUseEdit;

    public CreateSourceForm createSourceForm;
    public DeleteSourceForm deleteSourceForm;
    public SourceSpec sourceSpec;
    public SourceEdit sourceEdit;

    public String lastOfferName;
    public String lastUseTypeName;
    public String lastUseName;
    public String lastSourceName;

    // Party
    public String lastPartyName;

    // Payment
    public CreatePartyPaymentMethodForm createPartyPaymentMethodForm;

    public String lastPartyPaymentMethodName;

    // Purchase
    public CreatePurchaseOrderForm createPurchaseOrderForm;
    public SetPurchaseOrderStatusForm setPurchaseOrderStatusForm;
    public PurchaseOrderSpec purchaseOrderSpec;
    public PurchaseOrderEdit purchaseOrderEdit;

    public String lastPurchaseOrderName;

    // Sales
    public String lastSalesOrderBatchName;
    public String lastSalesOrderName;
    public String lastSalesOrderLineSequence;
    
    // Vendor
    public CreateVendorForm createVendorForm;
    public VendorSpec vendorSpec;
    public VendorEdit vendorEdit;

    public CreateVendorItemForm createVendorItemForm;
    public SetVendorItemStatusForm setVendorItemStatusForm;
    public DeleteVendorItemForm deleteVendorItemForm;
    public VendorItemSpec vendorItemSpec;
    public VendorItemEdit vendorItemEdit;

    public CreateVendorItemCostForm createVendorItemCostForm;
    public DeleteVendorItemCostForm deleteVendorItemCostForm;
    public VendorItemCostSpec vendorItemCostSpec;
    public VendorItemCostEdit vendorItemCostEdit;

    public String lastVendorName;

    // Warehouse
    public String lastWarehouseName;

}
