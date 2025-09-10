// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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

import com.echothree.control.user.authentication.common.form.CustomerLoginForm;
import com.echothree.control.user.authentication.common.form.EmployeeLoginForm;
import com.echothree.control.user.authentication.common.form.VendorLoginForm;
import com.echothree.control.user.campaign.common.form.CreateUserVisitCampaignForm;
import com.echothree.control.user.comment.common.edit.CommentTypeEdit;
import com.echothree.control.user.comment.common.form.CreateCommentTypeForm;
import com.echothree.control.user.comment.common.form.DeleteCommentTypeForm;
import com.echothree.control.user.comment.common.spec.CommentTypeSpec;
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
import com.echothree.control.user.core.common.edit.ComponentVendorEdit;
import com.echothree.control.user.core.common.edit.EntityAliasEdit;
import com.echothree.control.user.core.common.edit.EntityAliasTypeEdit;
import com.echothree.control.user.core.common.edit.EntityAttributeEdit;
import com.echothree.control.user.core.common.edit.EntityAttributeEntityAttributeGroupEdit;
import com.echothree.control.user.core.common.edit.EntityAttributeGroupEdit;
import com.echothree.control.user.core.common.edit.EntityListItemEdit;
import com.echothree.control.user.core.common.edit.EntityTypeEdit;
import com.echothree.control.user.core.common.form.CreateComponentVendorForm;
import com.echothree.control.user.core.common.form.CreateEntityAliasForm;
import com.echothree.control.user.core.common.form.CreateEntityAliasTypeForm;
import com.echothree.control.user.core.common.form.CreateEntityAttributeEntityAttributeGroupForm;
import com.echothree.control.user.core.common.form.CreateEntityAttributeForm;
import com.echothree.control.user.core.common.form.CreateEntityAttributeGroupForm;
import com.echothree.control.user.core.common.form.CreateEntityInstanceForm;
import com.echothree.control.user.core.common.form.CreateEntityListItemForm;
import com.echothree.control.user.core.common.form.CreateEntityTypeForm;
import com.echothree.control.user.core.common.form.DeleteComponentVendorForm;
import com.echothree.control.user.core.common.form.DeleteEntityAliasForm;
import com.echothree.control.user.core.common.form.DeleteEntityAliasTypeForm;
import com.echothree.control.user.core.common.form.DeleteEntityAttributeEntityAttributeGroupForm;
import com.echothree.control.user.core.common.form.DeleteEntityAttributeForm;
import com.echothree.control.user.core.common.form.DeleteEntityAttributeGroupForm;
import com.echothree.control.user.core.common.form.DeleteEntityInstanceForm;
import com.echothree.control.user.core.common.form.DeleteEntityListItemForm;
import com.echothree.control.user.core.common.form.DeleteEntityTypeForm;
import com.echothree.control.user.core.common.form.RemoveEntityInstanceForm;
import com.echothree.control.user.core.common.form.SendEventForm;
import com.echothree.control.user.core.common.spec.ComponentVendorSpec;
import com.echothree.control.user.core.common.spec.EntityAliasSpec;
import com.echothree.control.user.core.common.spec.EntityAliasTypeUniversalSpec;
import com.echothree.control.user.core.common.spec.EntityAttributeEntityAttributeGroupSpec;
import com.echothree.control.user.core.common.spec.EntityAttributeGroupSpec;
import com.echothree.control.user.core.common.spec.EntityAttributeUniversalSpec;
import com.echothree.control.user.core.common.spec.EntityListItemUniversalSpec;
import com.echothree.control.user.core.common.spec.EntityTypeSpec;
import com.echothree.control.user.filter.common.edit.FilterEdit;
import com.echothree.control.user.filter.common.edit.FilterKindEdit;
import com.echothree.control.user.filter.common.edit.FilterStepEdit;
import com.echothree.control.user.filter.common.edit.FilterTypeEdit;
import com.echothree.control.user.filter.common.form.CreateFilterForm;
import com.echothree.control.user.filter.common.form.CreateFilterKindForm;
import com.echothree.control.user.filter.common.form.CreateFilterStepForm;
import com.echothree.control.user.filter.common.form.CreateFilterTypeForm;
import com.echothree.control.user.filter.common.form.DeleteFilterForm;
import com.echothree.control.user.filter.common.form.DeleteFilterKindForm;
import com.echothree.control.user.filter.common.form.DeleteFilterStepForm;
import com.echothree.control.user.filter.common.form.DeleteFilterTypeForm;
import com.echothree.control.user.filter.common.spec.FilterKindSpec;
import com.echothree.control.user.filter.common.spec.FilterSpec;
import com.echothree.control.user.filter.common.spec.FilterStepSpec;
import com.echothree.control.user.filter.common.spec.FilterTypeSpec;
import com.echothree.control.user.inventory.common.edit.AllocationPriorityEdit;
import com.echothree.control.user.inventory.common.edit.InventoryConditionEdit;
import com.echothree.control.user.inventory.common.edit.InventoryLocationGroupEdit;
import com.echothree.control.user.inventory.common.form.CreateAllocationPriorityForm;
import com.echothree.control.user.inventory.common.form.CreateInventoryConditionForm;
import com.echothree.control.user.inventory.common.form.CreateInventoryLocationGroupForm;
import com.echothree.control.user.inventory.common.form.DeleteAllocationPriorityForm;
import com.echothree.control.user.inventory.common.form.DeleteInventoryConditionForm;
import com.echothree.control.user.inventory.common.form.DeleteInventoryLocationGroupForm;
import com.echothree.control.user.inventory.common.form.SetInventoryLocationGroupStatusForm;
import com.echothree.control.user.inventory.common.spec.AllocationPriorityUniversalSpec;
import com.echothree.control.user.inventory.common.spec.InventoryConditionUniversalSpec;
import com.echothree.control.user.inventory.common.spec.InventoryLocationGroupSpec;
import com.echothree.control.user.item.common.edit.ItemAliasEdit;
import com.echothree.control.user.item.common.edit.ItemAliasTypeEdit;
import com.echothree.control.user.item.common.edit.ItemDescriptionTypeUseTypeEdit;
import com.echothree.control.user.item.common.edit.ItemEdit;
import com.echothree.control.user.item.common.edit.ItemImageTypeEdit;
import com.echothree.control.user.item.common.edit.ItemPriceEdit;
import com.echothree.control.user.item.common.edit.ItemUnitOfMeasureTypeEdit;
import com.echothree.control.user.item.common.edit.ItemVolumeTypeEdit;
import com.echothree.control.user.item.common.edit.ItemWeightTypeEdit;
import com.echothree.control.user.item.common.form.CreateItemAliasForm;
import com.echothree.control.user.item.common.form.CreateItemAliasTypeForm;
import com.echothree.control.user.item.common.form.CreateItemDescriptionTypeUseTypeForm;
import com.echothree.control.user.item.common.form.CreateItemForm;
import com.echothree.control.user.item.common.form.CreateItemImageTypeForm;
import com.echothree.control.user.item.common.form.CreateItemPriceForm;
import com.echothree.control.user.item.common.form.CreateItemUnitOfMeasureTypeForm;
import com.echothree.control.user.item.common.form.CreateItemVolumeTypeForm;
import com.echothree.control.user.item.common.form.CreateItemWeightTypeForm;
import com.echothree.control.user.item.common.form.DeleteItemAliasForm;
import com.echothree.control.user.item.common.form.DeleteItemAliasTypeForm;
import com.echothree.control.user.item.common.form.DeleteItemDescriptionTypeUseTypeForm;
import com.echothree.control.user.item.common.form.DeleteItemImageTypeForm;
import com.echothree.control.user.item.common.form.DeleteItemPriceForm;
import com.echothree.control.user.item.common.form.DeleteItemUnitOfMeasureTypeForm;
import com.echothree.control.user.item.common.form.DeleteItemVolumeTypeForm;
import com.echothree.control.user.item.common.form.DeleteItemWeightTypeForm;
import com.echothree.control.user.item.common.spec.ItemAliasSpec;
import com.echothree.control.user.item.common.spec.ItemAliasTypeUniversalSpec;
import com.echothree.control.user.item.common.spec.ItemDescriptionTypeUseTypeUniversalSpec;
import com.echothree.control.user.item.common.spec.ItemImageTypeUniversalSpec;
import com.echothree.control.user.item.common.spec.ItemPriceSpec;
import com.echothree.control.user.item.common.spec.ItemSpec;
import com.echothree.control.user.item.common.spec.ItemUnitOfMeasureTypeSpec;
import com.echothree.control.user.item.common.spec.ItemVolumeTypeUniversalSpec;
import com.echothree.control.user.item.common.spec.ItemWeightTypeUniversalSpec;
import com.echothree.control.user.message.common.edit.MessageTypeEdit;
import com.echothree.control.user.message.common.form.CreateMessageTypeForm;
import com.echothree.control.user.message.common.form.DeleteMessageTypeForm;
import com.echothree.control.user.message.common.spec.MessageTypeSpec;
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
import com.echothree.control.user.offer.common.spec.UseTypeUniversalSpec;
import com.echothree.control.user.party.common.edit.PartyAliasEdit;
import com.echothree.control.user.party.common.edit.PartyAliasTypeEdit;
import com.echothree.control.user.party.common.form.CreateCustomerWithLoginForm;
import com.echothree.control.user.party.common.form.CreatePartyAliasForm;
import com.echothree.control.user.party.common.form.CreatePartyAliasTypeForm;
import com.echothree.control.user.party.common.form.CreateVendorForm;
import com.echothree.control.user.party.common.form.DeletePartyAliasForm;
import com.echothree.control.user.party.common.form.DeletePartyAliasTypeForm;
import com.echothree.control.user.party.common.spec.PartyAliasSpec;
import com.echothree.control.user.party.common.spec.PartyAliasTypeUniversalSpec;
import com.echothree.control.user.payment.common.form.CreatePartyPaymentMethodForm;
import com.echothree.control.user.purchase.common.edit.PurchaseOrderEdit;
import com.echothree.control.user.purchase.common.form.CreatePurchaseOrderForm;
import com.echothree.control.user.purchase.common.form.SetPurchaseOrderStatusForm;
import com.echothree.control.user.purchase.common.spec.PurchaseOrderSpec;
import com.echothree.control.user.rating.common.edit.RatingTypeEdit;
import com.echothree.control.user.rating.common.form.CreateRatingTypeForm;
import com.echothree.control.user.rating.common.form.DeleteRatingTypeForm;
import com.echothree.control.user.rating.common.spec.RatingTypeSpec;
import com.echothree.control.user.search.common.form.CheckItemSpellingForm;
import com.echothree.control.user.search.common.form.ClearItemResultsForm;
import com.echothree.control.user.search.common.form.CreateItemSearchResultActionForm;
import com.echothree.control.user.search.common.form.GetItemResultsFacetForm;
import com.echothree.control.user.search.common.form.GetItemResultsForm;
import com.echothree.control.user.search.common.form.SearchItemsForm;
import com.echothree.control.user.selector.common.edit.SelectorEdit;
import com.echothree.control.user.selector.common.edit.SelectorKindEdit;
import com.echothree.control.user.selector.common.edit.SelectorNodeEdit;
import com.echothree.control.user.selector.common.edit.SelectorTypeEdit;
import com.echothree.control.user.selector.common.form.CreateSelectorForm;
import com.echothree.control.user.selector.common.form.CreateSelectorKindForm;
import com.echothree.control.user.selector.common.form.CreateSelectorNodeForm;
import com.echothree.control.user.selector.common.form.CreateSelectorTypeForm;
import com.echothree.control.user.selector.common.form.DeleteSelectorForm;
import com.echothree.control.user.selector.common.form.DeleteSelectorKindForm;
import com.echothree.control.user.selector.common.form.DeleteSelectorNodeForm;
import com.echothree.control.user.selector.common.form.DeleteSelectorTypeForm;
import com.echothree.control.user.selector.common.spec.SelectorKindSpec;
import com.echothree.control.user.selector.common.spec.SelectorNodeSpec;
import com.echothree.control.user.selector.common.spec.SelectorSpec;
import com.echothree.control.user.selector.common.spec.SelectorTypeSpec;
import com.echothree.control.user.tag.common.edit.TagEdit;
import com.echothree.control.user.tag.common.edit.TagScopeEdit;
import com.echothree.control.user.tag.common.form.CreateTagForm;
import com.echothree.control.user.tag.common.form.CreateTagScopeEntityTypeForm;
import com.echothree.control.user.tag.common.form.CreateTagScopeForm;
import com.echothree.control.user.tag.common.form.DeleteTagForm;
import com.echothree.control.user.tag.common.form.DeleteTagScopeEntityTypeForm;
import com.echothree.control.user.tag.common.form.DeleteTagScopeForm;
import com.echothree.control.user.tag.common.spec.TagScopeSpec;
import com.echothree.control.user.tag.common.spec.TagSpec;
import com.echothree.control.user.track.common.form.CreateUserVisitTrackForm;
import com.echothree.control.user.vendor.common.edit.VendorEdit;
import com.echothree.control.user.vendor.common.edit.VendorItemCostEdit;
import com.echothree.control.user.vendor.common.edit.VendorItemEdit;
import com.echothree.control.user.vendor.common.form.CreateVendorItemCostForm;
import com.echothree.control.user.vendor.common.form.CreateVendorItemForm;
import com.echothree.control.user.vendor.common.form.DeleteVendorItemCostForm;
import com.echothree.control.user.vendor.common.form.DeleteVendorItemForm;
import com.echothree.control.user.vendor.common.form.SetVendorItemStatusForm;
import com.echothree.control.user.vendor.common.spec.VendorItemCostSpec;
import com.echothree.control.user.vendor.common.spec.VendorItemUniversalSpec;
import com.echothree.control.user.vendor.common.spec.VendorUniversalSpec;
import com.echothree.control.user.warehouse.common.edit.LocationEdit;
import com.echothree.control.user.warehouse.common.edit.LocationNameElementEdit;
import com.echothree.control.user.warehouse.common.edit.LocationTypeEdit;
import com.echothree.control.user.warehouse.common.edit.WarehouseEdit;
import com.echothree.control.user.warehouse.common.edit.WarehouseTypeEdit;
import com.echothree.control.user.warehouse.common.form.CreateLocationForm;
import com.echothree.control.user.warehouse.common.form.CreateLocationNameElementForm;
import com.echothree.control.user.warehouse.common.form.CreateLocationTypeForm;
import com.echothree.control.user.warehouse.common.form.CreateWarehouseForm;
import com.echothree.control.user.warehouse.common.form.CreateWarehouseTypeForm;
import com.echothree.control.user.warehouse.common.form.DeleteLocationForm;
import com.echothree.control.user.warehouse.common.form.DeleteLocationNameElementForm;
import com.echothree.control.user.warehouse.common.form.DeleteLocationTypeForm;
import com.echothree.control.user.warehouse.common.form.DeleteWarehouseForm;
import com.echothree.control.user.warehouse.common.form.DeleteWarehouseTypeForm;
import com.echothree.control.user.warehouse.common.form.SetLocationStatusForm;
import com.echothree.control.user.warehouse.common.spec.LocationNameElementSpec;
import com.echothree.control.user.warehouse.common.spec.LocationSpec;
import com.echothree.control.user.warehouse.common.spec.LocationTypeSpec;
import com.echothree.control.user.warehouse.common.spec.WarehouseTypeSpec;
import com.echothree.control.user.warehouse.common.spec.WarehouseUniversalSpec;
import com.echothree.control.user.workflow.common.edit.WorkflowDestinationEdit;
import com.echothree.control.user.workflow.common.edit.WorkflowEdit;
import com.echothree.control.user.workflow.common.edit.WorkflowEntranceEdit;
import com.echothree.control.user.workflow.common.edit.WorkflowStepEdit;
import com.echothree.control.user.workflow.common.form.CreateWorkflowDestinationForm;
import com.echothree.control.user.workflow.common.form.CreateWorkflowDestinationPartyTypeForm;
import com.echothree.control.user.workflow.common.form.CreateWorkflowDestinationSecurityRoleForm;
import com.echothree.control.user.workflow.common.form.CreateWorkflowDestinationStepForm;
import com.echothree.control.user.workflow.common.form.CreateWorkflowEntityTypeForm;
import com.echothree.control.user.workflow.common.form.CreateWorkflowEntranceForm;
import com.echothree.control.user.workflow.common.form.CreateWorkflowEntrancePartyTypeForm;
import com.echothree.control.user.workflow.common.form.CreateWorkflowEntranceSecurityRoleForm;
import com.echothree.control.user.workflow.common.form.CreateWorkflowEntranceStepForm;
import com.echothree.control.user.workflow.common.form.CreateWorkflowForm;
import com.echothree.control.user.workflow.common.form.CreateWorkflowStepForm;
import com.echothree.control.user.workflow.common.form.DeleteWorkflowDestinationForm;
import com.echothree.control.user.workflow.common.form.DeleteWorkflowDestinationPartyTypeForm;
import com.echothree.control.user.workflow.common.form.DeleteWorkflowDestinationSecurityRoleForm;
import com.echothree.control.user.workflow.common.form.DeleteWorkflowDestinationStepForm;
import com.echothree.control.user.workflow.common.form.DeleteWorkflowEntityTypeForm;
import com.echothree.control.user.workflow.common.form.DeleteWorkflowEntranceForm;
import com.echothree.control.user.workflow.common.form.DeleteWorkflowEntrancePartyTypeForm;
import com.echothree.control.user.workflow.common.form.DeleteWorkflowEntranceSecurityRoleForm;
import com.echothree.control.user.workflow.common.form.DeleteWorkflowEntranceStepForm;
import com.echothree.control.user.workflow.common.form.DeleteWorkflowForm;
import com.echothree.control.user.workflow.common.form.DeleteWorkflowStepForm;
import com.echothree.control.user.workflow.common.spec.WorkflowDestinationUniversalSpec;
import com.echothree.control.user.workflow.common.spec.WorkflowEntranceUniversalSpec;
import com.echothree.control.user.workflow.common.spec.WorkflowStepUniversalSpec;
import com.echothree.control.user.workflow.common.spec.WorkflowUniversalSpec;
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

    // Authentication
    public CustomerLoginForm customerLoginForm;
    public EmployeeLoginForm employeeLoginForm;
    public VendorLoginForm vendorLoginForm;

    // Campaign
    public CreateUserVisitCampaignForm createUserVisitCampaignForm;

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
    public CreateComponentVendorForm createComponentVendorForm;
    public DeleteComponentVendorForm deleteComponentVendorForm;
    public ComponentVendorSpec componentVendorSpec;
    public ComponentVendorEdit componentVendorEdit;

    public CreateEntityTypeForm createEntityTypeForm;
    public DeleteEntityTypeForm deleteEntityTypeForm;
    public EntityTypeSpec entityTypeSpec;
    public EntityTypeEdit entityTypeEdit;

    public CreateEntityAliasTypeForm createEntityAliasTypeForm;
    public DeleteEntityAliasTypeForm deleteEntityAliasTypeForm;
    public EntityAliasTypeUniversalSpec entityAliasTypeUniversalSpec;
    public EntityAliasTypeEdit entityAliasTypeEdit;

    public CreateEntityAliasForm createEntityAliasForm;
    public DeleteEntityAliasForm deleteEntityAliasForm;
    public EntityAliasSpec entityAliasSpec;
    public EntityAliasEdit entityAliasEdit;

    public CreateEntityAttributeGroupForm createEntityAttributeGroupForm;
    public DeleteEntityAttributeGroupForm deleteEntityAttributeGroupForm;
    public EntityAttributeGroupSpec entityAttributeGroupSpec;
    public EntityAttributeGroupEdit entityAttributeGroupEdit;

    public CreateEntityAttributeForm createEntityAttributeForm;
    public DeleteEntityAttributeForm deleteEntityAttributeForm;
    public EntityAttributeUniversalSpec entityAttributeUniversalSpec;
    public EntityAttributeEdit entityAttributeEdit;

    public CreateEntityListItemForm createEntityListItemForm;
    public DeleteEntityListItemForm deleteEntityListItemForm;
    public EntityListItemUniversalSpec entityListItemUniversalSpec;
    public EntityListItemEdit entityListItemEdit;

    public CreateEntityAttributeEntityAttributeGroupForm createEntityAttributeEntityAttributeGroupForm;
    public DeleteEntityAttributeEntityAttributeGroupForm deleteEntityAttributeEntityAttributeGroupForm;
    public EntityAttributeEntityAttributeGroupSpec entityAttributeEntityAttributeGroupSpec;
    public EntityAttributeEntityAttributeGroupEdit entityAttributeEntityAttributeGroupEdit;

    public CreateEntityInstanceForm createEntityInstanceForm;
    public DeleteEntityInstanceForm deleteEntityInstanceForm;
    public RemoveEntityInstanceForm removeEntityInstanceForm;

    public SendEventForm sendEventForm;

    public String lastComponentVendorName;
    public String lastEntityTypeName;
    public String lastEntityAliasTypeName;
    public String lastEntityAttributeGroupName;
    public String lastEntityAttributeName;
    public String lastEntityListItemName;
    public String lastEntityRef;

    // Comment
    public CreateCommentTypeForm createCommentTypeForm;
    public DeleteCommentTypeForm deleteCommentTypeForm;
    public CommentTypeSpec commentTypeSpec;
    public CommentTypeEdit commentTypeEdit;

    public String lastCommentTypeName;

    // Customer
    public CreateCustomerWithLoginForm createCustomerWithLoginForm;

    public String lastCustomerName;

    // Employee
    public String lastEmployeeName;

    // Filter
    public CreateFilterKindForm createFilterKindForm;
    public DeleteFilterKindForm deleteFilterKindForm;
    public FilterKindSpec filterKindSpec;
    public FilterKindEdit filterKindEdit;

    public CreateFilterTypeForm createFilterTypeForm;
    public DeleteFilterTypeForm deleteFilterTypeForm;
    public FilterTypeSpec filterTypeSpec;
    public FilterTypeEdit filterTypeEdit;

    public CreateFilterForm createFilterForm;
    public DeleteFilterForm deleteFilterForm;
    public FilterSpec filterSpec;
    public FilterEdit filterEdit;

    public CreateFilterStepForm createFilterStepForm;
    public DeleteFilterStepForm deleteFilterStepForm;
    public FilterStepSpec filterStepSpec;
    public FilterStepEdit filterStepEdit;

    public String lastFilterKindName;
    public String lastFilterTypeName;
    public String lastFilterName;
    public String lastFilterStepName;

    // Inventory
    public CreateInventoryLocationGroupForm createInventoryLocationGroupForm;
    public SetInventoryLocationGroupStatusForm setInventoryLocationGroupStatusForm;
    public DeleteInventoryLocationGroupForm deleteInventoryLocationGroupForm;
    public InventoryLocationGroupSpec inventoryLocationGroupSpec;
    public InventoryLocationGroupEdit inventoryLocationGroupEdit;

    public CreateInventoryConditionForm createInventoryConditionForm;
    public DeleteInventoryConditionForm deleteInventoryConditionForm;
    public InventoryConditionUniversalSpec inventoryConditionSpec;
    public InventoryConditionEdit inventoryConditionEdit;

    public CreateAllocationPriorityForm createAllocationPriorityForm;
    public DeleteAllocationPriorityForm deleteAllocationPriorityForm;
    public AllocationPriorityUniversalSpec allocationPrioritySpec;
    public AllocationPriorityEdit allocationPriorityEdit;

    public String lastInventoryLocationGroupName;
    public String lastInventoryConditionName;
    public String lastAllocationPriorityName;

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

    public CreateItemImageTypeForm createItemImageTypeForm;
    public DeleteItemImageTypeForm deleteItemImageTypeForm;
    public ItemImageTypeUniversalSpec itemImageTypeSpec;
    public ItemImageTypeEdit itemImageTypeEdit;

    public CreateItemDescriptionTypeUseTypeForm createItemDescriptionTypeUseTypeForm;
    public DeleteItemDescriptionTypeUseTypeForm deleteItemDescriptionTypeUseTypeForm;
    public ItemDescriptionTypeUseTypeUniversalSpec itemDescriptionTypeUseTypeSpec;
    public ItemDescriptionTypeUseTypeEdit itemDescriptionTypeUseTypeEdit;

    public CreateItemAliasTypeForm createItemAliasTypeForm;
    public DeleteItemAliasTypeForm deleteItemAliasTypeForm;
    public ItemAliasTypeUniversalSpec itemAliasTypeSpec;
    public ItemAliasTypeEdit itemAliasTypeEdit;

    public CreateItemAliasForm createItemAliasForm;
    public DeleteItemAliasForm deleteItemAliasForm;
    public ItemAliasSpec itemAliasSpec;
    public ItemAliasEdit itemAliasEdit;

    public CreateItemWeightTypeForm createItemWeightTypeForm;
    public DeleteItemWeightTypeForm deleteItemWeightTypeForm;
    public ItemWeightTypeUniversalSpec itemWeightTypeSpec;
    public ItemWeightTypeEdit itemWeightTypeEdit;

    public CreateItemVolumeTypeForm createItemVolumeTypeForm;
    public DeleteItemVolumeTypeForm deleteItemVolumeTypeForm;
    public ItemVolumeTypeUniversalSpec itemVolumeTypeSpec;
    public ItemVolumeTypeEdit itemVolumeTypeEdit;

    public String lastItemName;
    public String lastItemImageTypeName;
    public String lastItemDescriptionTypeUseTypeName;
    public String lastItemAliasTypeName;
    public String lastItemWeightTypeName;
    public String lastItemVolumeTypeName;
    public String lastAlias;

    // Message
    public CreateMessageTypeForm createMessageTypeForm;
    public DeleteMessageTypeForm deleteMessageTypeForm;
    public MessageTypeSpec messageTypeSpec;
    public MessageTypeEdit messageTypeEdit;

    public String lastMessageTypeName;

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
    public CreatePartyAliasTypeForm createPartyAliasTypeForm;
    public DeletePartyAliasTypeForm deletePartyAliasTypeForm;
    public PartyAliasTypeUniversalSpec partyAliasTypeSpec;
    public PartyAliasTypeEdit partyAliasTypeEdit;

    public CreatePartyAliasForm createPartyAliasForm;
    public DeletePartyAliasForm deletePartyAliasForm;
    public PartyAliasSpec partyAliasSpec;
    public PartyAliasEdit partyAliasEdit;

    public String lastPartyName;
    public String lastPartyAliasTypeName;

    // Payment
    public CreatePartyPaymentMethodForm createPartyPaymentMethodForm;

    public String lastPartyPaymentMethodName;

    // Purchase
    public CreatePurchaseOrderForm createPurchaseOrderForm;
    public SetPurchaseOrderStatusForm setPurchaseOrderStatusForm;
    public PurchaseOrderSpec purchaseOrderSpec;
    public PurchaseOrderEdit purchaseOrderEdit;

    public String lastPurchaseOrderName;

    // Rating
    public CreateRatingTypeForm createRatingTypeForm;
    public DeleteRatingTypeForm deleteRatingTypeForm;
    public RatingTypeSpec ratingTypeSpec;
    public RatingTypeEdit ratingTypeEdit;

    public String lastRatingTypeName;

    // Sales
    public String lastSalesOrderBatchName;
    public String lastSalesOrderName;
    public String lastSalesOrderLineSequence;

    // Search
    public SearchItemsForm searchItemsForm;
    public GetItemResultsForm getItemResultsForm;
    public GetItemResultsFacetForm getItemResultsFacetForm;
    public CreateItemSearchResultActionForm createItemSearchResultActionForm;
    public ClearItemResultsForm clearItemResultsForm;
    public CheckItemSpellingForm checkItemSpellingForm;

    public Long lastSearchItemsCount;

    // Selector
    public CreateSelectorKindForm createSelectorKindForm;
    public DeleteSelectorKindForm deleteSelectorKindForm;
    public SelectorKindSpec selectorKindSpec;
    public SelectorKindEdit selectorKindEdit;

    public CreateSelectorTypeForm createSelectorTypeForm;
    public DeleteSelectorTypeForm deleteSelectorTypeForm;
    public SelectorTypeSpec selectorTypeSpec;
    public SelectorTypeEdit selectorTypeEdit;

    public CreateSelectorForm createSelectorForm;
    public DeleteSelectorForm deleteSelectorForm;
    public SelectorSpec selectorSpec;
    public SelectorEdit selectorEdit;

    public CreateSelectorNodeForm createSelectorNodeForm;
    public DeleteSelectorNodeForm deleteSelectorNodeForm;
    public SelectorNodeSpec selectorNodeSpec;
    public SelectorNodeEdit selectorNodeEdit;

    public String lastSelectorKindName;
    public String lastSelectorTypeName;
    public String lastSelectorName;
    public String lastSelectorNodeName;

    // Tag
    public CreateTagScopeForm createTagScopeForm;
    public DeleteTagScopeForm deleteTagScopeForm;
    public TagScopeSpec tagScopeSpec;
    public TagScopeEdit tagScopeEdit;

    public CreateTagScopeEntityTypeForm createTagScopeEntityTypeForm;
    public DeleteTagScopeEntityTypeForm deleteTagScopeEntityTypeForm;

    public CreateTagForm createTagForm;
    public DeleteTagForm deleteTagForm;
    public TagSpec tagSpec;
    public TagEdit tagEdit;

    public String lastTagScopeName;
    public String lastTagName;

    // Track
    public CreateUserVisitTrackForm createUserVisitTrackForm;

    // Unit of Measure

    public String lastUnitOfMeasureKindName;
    public String lastUnitOfMeasureTypeName;

    // Vendor
    public CreateVendorForm createVendorForm;
    public VendorUniversalSpec vendorUniversalSpec;
    public VendorEdit vendorEdit;

    public CreateVendorItemForm createVendorItemForm;
    public SetVendorItemStatusForm setVendorItemStatusForm;
    public DeleteVendorItemForm deleteVendorItemForm;
    public VendorItemUniversalSpec vendorItemUniversalSpec;
    public VendorItemEdit vendorItemEdit;

    public CreateVendorItemCostForm createVendorItemCostForm;
    public DeleteVendorItemCostForm deleteVendorItemCostForm;
    public VendorItemCostSpec vendorItemCostSpec;
    public VendorItemCostEdit vendorItemCostEdit;

    public String lastVendorName;

    // Warehouse
    public CreateWarehouseTypeForm createWarehouseTypeForm;
    public DeleteWarehouseTypeForm deleteWarehouseTypeForm;
    public WarehouseTypeSpec warehouseTypeSpec;
    public WarehouseTypeEdit warehouseTypeEdit;

    public CreateWarehouseForm createWarehouseForm;
    public DeleteWarehouseForm deleteWarehouseForm;
    public WarehouseUniversalSpec warehouseUniversalSpec;
    public WarehouseEdit warehouseEdit;

    public CreateLocationTypeForm createLocationTypeForm;
    public DeleteLocationTypeForm deleteLocationTypeForm;
    public LocationTypeSpec locationTypeSpec;
    public LocationTypeEdit locationTypeEdit;

    public CreateLocationNameElementForm createLocationNameElementForm;
    public DeleteLocationNameElementForm deleteLocationNameElementForm;
    public LocationNameElementSpec locationNameElementSpec;
    public LocationNameElementEdit locationNameElementEdit;

    public CreateLocationForm createLocationForm;
    public SetLocationStatusForm setLocationStatusForm;
    public DeleteLocationForm deleteLocationForm;
    public LocationSpec locationSpec;
    public LocationEdit locationEdit;

    public String lastWarehouseTypeName;
    public String lastWarehouseName;
    public String lastLocationTypeName;
    public String lastLocationNameElementName;
    public String lastLocationName;

    // Workflow
    public CreateWorkflowForm createWorkflowForm;
    public DeleteWorkflowForm deleteWorkflowForm;
    public WorkflowUniversalSpec workflowUniversalSpec;
    public WorkflowEdit workflowEdit;

    public CreateWorkflowEntityTypeForm createWorkflowEntityTypeForm;
    public DeleteWorkflowEntityTypeForm deleteWorkflowEntityTypeForm;

    public CreateWorkflowStepForm createWorkflowStepForm;
    public DeleteWorkflowStepForm deleteWorkflowStepForm;
    public WorkflowStepUniversalSpec workflowStepUniversalSpec;
    public WorkflowStepEdit workflowStepEdit;

    public CreateWorkflowEntranceForm createWorkflowEntranceForm;
    public DeleteWorkflowEntranceForm deleteWorkflowEntranceForm;
    public WorkflowEntranceUniversalSpec workflowEntranceUniversalSpec;
    public WorkflowEntranceEdit workflowEntranceEdit;

    public CreateWorkflowEntranceStepForm createWorkflowEntranceStepForm;
    public DeleteWorkflowEntranceStepForm deleteWorkflowEntranceStepForm;

    public CreateWorkflowEntrancePartyTypeForm createWorkflowEntrancePartyTypeForm;
    public DeleteWorkflowEntrancePartyTypeForm deleteWorkflowEntrancePartyTypeForm;

    public CreateWorkflowEntranceSecurityRoleForm createWorkflowEntranceSecurityRoleForm;
    public DeleteWorkflowEntranceSecurityRoleForm deleteWorkflowEntranceSecurityRoleForm;

    public CreateWorkflowDestinationForm createWorkflowDestinationForm;
    public DeleteWorkflowDestinationForm deleteWorkflowDestinationForm;
    public WorkflowDestinationUniversalSpec workflowDestinationUniversalSpec;
    public WorkflowDestinationEdit workflowDestinationEdit;

    public CreateWorkflowDestinationStepForm createWorkflowDestinationStepForm;
    public DeleteWorkflowDestinationStepForm deleteWorkflowDestinationStepForm;

    public CreateWorkflowDestinationPartyTypeForm createWorkflowDestinationPartyTypeForm;
    public DeleteWorkflowDestinationPartyTypeForm deleteWorkflowDestinationPartyTypeForm;

    public CreateWorkflowDestinationSecurityRoleForm createWorkflowDestinationSecurityRoleForm;
    public DeleteWorkflowDestinationSecurityRoleForm deleteWorkflowDestinationSecurityRoleForm;

    public String lastWorkflowName;
    public String lastWorkflowStepName;
    public String lastWorkflowEntranceName;
    public String lastWorkflowDestinationName;

}
