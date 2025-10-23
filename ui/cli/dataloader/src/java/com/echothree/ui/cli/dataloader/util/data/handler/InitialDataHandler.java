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

package com.echothree.ui.cli.dataloader.util.data.handler;

import com.echothree.control.user.core.client.helper.BaseKeysHelper;
import com.echothree.control.user.core.common.CoreService;
import com.echothree.control.user.core.common.CoreUtil;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.accounting.CurrenciesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.accounting.GlAccountCategoriesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.accounting.GlAccountClassesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.accounting.GlAccountTypesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.accounting.GlAccountsHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.accounting.GlResourceTypesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.accounting.ItemAccountingCategoriesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.accounting.SymbolPositionsHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.accounting.TransactionTimeTypesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.accounting.TransactionTypesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.associate.AssociateProgramsHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.batch.BatchTypesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.cancellationpolicy.CancellationKindsHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.carrier.CarrierTypesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.carrier.CarriersHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.chain.ChainActionTypesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.chain.ChainKindsHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.club.ClubItemTypesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.club.ClubsHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.communication.CommunicationEventPurposesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.communication.CommunicationEventRoleTypesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.communication.CommunicationEventTypesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.communication.CommunicationSourceTypesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.communication.CommunicationSourcesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.contact.ContactMechanismAliasTypesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.contact.ContactMechanismPurposesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.contact.ContactMechanismTypesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.contact.PostalAddressElementTypesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.contact.PostalAddressFormatsHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.contactlist.ContactListFrequenciesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.contactlist.ContactListGroupsHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.contactlist.ContactListTypesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.contactlist.ContactListsHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.content.ContentCollectionsHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.content.ContentPageAreaTypesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.content.ContentPageLayoutsHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.content.ContentWebAddressesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.core.AppearancesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.core.ApplicationsHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.core.ColorsHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.core.CommandMessageTypesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.core.ComponentStagesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.core.ComponentVendorsHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.core.EditorsHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.core.EntityAttributeGroupsHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.core.EntityAttributeTypesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.core.EventTypesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.core.FontStylesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.core.FontWeightsHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.core.MimeTypeUsageTypesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.core.MimeTypesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.core.ProtocolsHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.core.ServersHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.core.ServicesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.core.TextDecorationsHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.core.TextTransformationsHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.customer.CustomerTypesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.customer.CustomersHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.document.DocumentTypeUsageTypesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.document.DocumentTypesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.employee.EmployeeTypesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.employee.LeaveReasonsHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.employee.LeaveTypesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.employee.ResponsibilityTypesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.employee.SkillTypesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.employee.TerminationReasonsHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.employee.TerminationTypesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.filter.FilterAdjustmentSourcesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.filter.FilterAdjustmentTypesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.filter.FilterKindsHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.forum.ForumGroupsHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.forum.ForumMessagePartTypesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.forum.ForumMessageTypesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.forum.ForumRoleTypesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.forum.ForumTypesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.forum.ForumsHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.geo.CountriesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.geo.GeoCodeScopesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.geo.GeoCodeTypesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.icon.IconUsageTypesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.index.IndexTypesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.index.IndexesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.inventory.AllocationPrioritiesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.inventory.InventoryConditionUseTypesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.inventory.InventoryConditionsHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.inventory.InventoryTransactionTypesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.inventory.LotAliasTypesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.inventory.LotTimeTypesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.invoice.InvoiceLineUseTypesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.invoice.InvoiceRoleTypesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.invoice.InvoiceTypesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.item.HarmonizedTariffScheduleCodeUnitsHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.item.HarmonizedTariffScheduleCodeUseTypesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.item.ItemAliasChecksumTypesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.item.ItemAliasTypesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.item.ItemCategoriesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.item.ItemDeliveryTypesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.item.ItemDescriptionTypeUseTypesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.item.ItemDescriptionTypesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.item.ItemImageTypesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.item.ItemInventoryTypesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.item.ItemPriceTypesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.item.ItemTypesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.item.ItemUseTypesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.item.ItemVolumeTypesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.item.ItemWeightTypesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.item.ItemsHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.item.RelatedItemTypesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.job.JobsHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.license.LicenseTypesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.offer.OfferNameElementsHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.offer.OffersHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.offer.UseNameElementsHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.offer.UseTypesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.offer.UsesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.order.OrderRoleTypesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.order.OrderTypesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.party.BirthdayFormatsHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.party.CompaniesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.party.DateTimeFormatsHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.party.EmployeesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.party.GendersHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.party.LanguagesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.party.MoodsHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.party.NameSuffixesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.party.PartyRelationshipTypesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.party.PartyTypeUseTypesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.party.PartyTypesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.party.PersonalTitlesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.party.RoleTypesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.party.TimeZonesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.payment.BillingAccountRoleTypesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.payment.PaymentMethodTypesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.payment.PaymentMethodsHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.payment.PaymentProcessorActionTypesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.payment.PaymentProcessorResultCodesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.payment.PaymentProcessorTypesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.payment.PaymentProcessorsHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.period.PeriodKindsHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.picklist.PicklistTypesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.printer.PrinterGroupUseTypesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.printer.PrinterGroupsHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.queue.QueueTypesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.returnpolicy.ReturnKindsHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.sales.SalesOrderBatchesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.scale.ScaleTypesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.scale.ScaleUseTypesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.scale.ScalesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.search.SearchCheckSpellingActionTypesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.search.SearchDefaultOperatorsHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.search.SearchKindsHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.search.SearchResultActionTypesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.search.SearchSortDirectionsHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.search.SearchUseTypesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.security.PartySecurityRoleTemplatesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.security.SecurityRoleGroupsHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.selector.SelectorBooleanTypesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.selector.SelectorComparisonTypesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.selector.SelectorKindsHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.selector.SelectorNodeTypesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.selector.SelectorTextSearchTypesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.sequence.SequenceChecksumTypesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.sequence.SequenceEncoderTypesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.sequence.SequenceTypesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.shipment.FreeOnBoardsHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.shipment.ShipmentTypesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.shipping.ShippingMethodsHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.subscription.SubscriptionKindsHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.tag.TagScopesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.tax.TaxesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.term.TermTypesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.term.TermsHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.training.TrainingClassesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.uom.UnitOfMeasureKindUseTypesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.uom.UnitOfMeasureKindsHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.user.RecoveryQuestionsHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.user.UserLoginPasswordEncoderTypesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.user.UserLoginPasswordTypesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.vendor.ItemPurchasingCategoriesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.vendor.VendorTypesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.vendor.VendorsHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.warehouse.LocationUseTypesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.warehouse.WarehouseTypesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.warehouse.WarehousesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.wishlist.WishlistTypesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.workeffort.WorkEffortTypesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.workflow.WorkflowStepTypesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.workflow.WorkflowsHandler;
import java.io.IOException;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class InitialDataHandler
        extends BaseHandler {
    
    CoreService coreService;
    
    /** Creates a new instance of InitialDataHandler */
    public InitialDataHandler(InitialDataParser initialDataParser, BaseHandler parentHandler)
            throws SAXException {
        super(initialDataParser, parentHandler);
        
        try {
            coreService = CoreUtil.getHome();
        } catch (NamingException ne) {
            throw new SAXException(ne);
        }
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException, NamingException {
        if(localName.equals("languages")) {
            initialDataParser.pushHandler(new LanguagesHandler(initialDataParser, this));
        } else if(localName.equals("partyTypes")) {
            initialDataParser.pushHandler(new PartyTypesHandler(initialDataParser, this));
        } else if(localName.equals("partyTypeUseTypes")) {
            initialDataParser.pushHandler(new PartyTypeUseTypesHandler(initialDataParser, this));
        } else if(localName.equals("eventTypes")) {
            initialDataParser.pushHandler(new EventTypesHandler(initialDataParser, this));
        } else if(localName.equals("componentVendors")) {
            initialDataParser.pushHandler(new ComponentVendorsHandler(initialDataParser, this));
        } else if(localName.equals("personalTitles")) {
            initialDataParser.pushHandler(new PersonalTitlesHandler(initialDataParser, this));
        } else if(localName.equals("nameSuffixes")) {
            initialDataParser.pushHandler(new NameSuffixesHandler(initialDataParser, this));
        } else if(localName.equals("timeZones")) {
            initialDataParser.pushHandler(new TimeZonesHandler(initialDataParser, this));
        } else if(localName.equals("dateTimeFormats")) {
            initialDataParser.pushHandler(new DateTimeFormatsHandler(initialDataParser, this));
        } else if(localName.equals("currencies")) {
            initialDataParser.pushHandler(new CurrenciesHandler(initialDataParser, this));
        } else if(localName.equals("indexTypes")) {
            initialDataParser.pushHandler(new IndexTypesHandler(initialDataParser, this));
        } else if(localName.equals("indexes")) {
            initialDataParser.pushHandler(new IndexesHandler(initialDataParser, this));
        } else if(localName.equals("sequenceChecksumTypes")) {
            initialDataParser.pushHandler(new SequenceChecksumTypesHandler(initialDataParser, this));
        } else if(localName.equals("sequenceEncoderTypes")) {
            initialDataParser.pushHandler(new SequenceEncoderTypesHandler(initialDataParser, this));
        } else if(localName.equals("sequenceTypes")) {
            initialDataParser.pushHandler(new SequenceTypesHandler(initialDataParser, this));
        } else if(localName.equals("offerNameElements")) {
            initialDataParser.pushHandler(new OfferNameElementsHandler(initialDataParser, this));
        } else if(localName.equals("offers")) {
            initialDataParser.pushHandler(new OffersHandler(initialDataParser, this));
        } else if(localName.equals("useNameElements")) {
            initialDataParser.pushHandler(new UseNameElementsHandler(initialDataParser, this));
        } else if(localName.equals("uses")) {
            initialDataParser.pushHandler(new UsesHandler(initialDataParser, this));
        } else if(localName.equals("useTypes")) {
            initialDataParser.pushHandler(new UseTypesHandler(initialDataParser, this));
        } else if(localName.equals("componentStages")) {
            initialDataParser.pushHandler(new ComponentStagesHandler(initialDataParser, this));
        } else if(localName.equals("entityAttributeTypes")) {
            initialDataParser.pushHandler(new EntityAttributeTypesHandler(initialDataParser, this));
        } else if(localName.equals("mimeTypeUsageTypes")) {
            initialDataParser.pushHandler(new MimeTypeUsageTypesHandler(initialDataParser, this));
        } else if(localName.equals("mimeTypes")) {
            initialDataParser.pushHandler(new MimeTypesHandler(initialDataParser, this));
        } else if(localName.equals("protocols")) {
            initialDataParser.pushHandler(new ProtocolsHandler(initialDataParser, this));
        } else if(localName.equals("services")) {
            initialDataParser.pushHandler(new ServicesHandler(initialDataParser, this));
        } else if(localName.equals("servers")) {
            initialDataParser.pushHandler(new ServersHandler(initialDataParser, this));
        } else if(localName.equals("contentPageAreaTypes")) {
            initialDataParser.pushHandler(new ContentPageAreaTypesHandler(initialDataParser, this));
        } else if(localName.equals("contentPageLayouts")) {
            initialDataParser.pushHandler(new ContentPageLayoutsHandler(initialDataParser, this));
        } else if(localName.equals("contentCollections")) {
            initialDataParser.pushHandler(new ContentCollectionsHandler(initialDataParser, this));
        } else if(localName.equals("contentWebAddresses")) {
            initialDataParser.pushHandler(new ContentWebAddressesHandler(initialDataParser, this));
        } else if(localName.equals("selectorKinds")) {
            initialDataParser.pushHandler(new SelectorKindsHandler(initialDataParser, this));
        } else if(localName.equals("selectorBooleanTypes")) {
            initialDataParser.pushHandler(new SelectorBooleanTypesHandler(initialDataParser, this));
        } else if(localName.equals("selectorComparisonTypes")) {
            initialDataParser.pushHandler(new SelectorComparisonTypesHandler(initialDataParser, this));
        } else if(localName.equals("selectorNodeTypes")) {
            initialDataParser.pushHandler(new SelectorNodeTypesHandler(initialDataParser, this));
        } else if(localName.equals("selectorTextSearchTypes")) {
            initialDataParser.pushHandler(new SelectorTextSearchTypesHandler(initialDataParser, this));
        } else if(localName.equals("workflowStepTypes")) {
            initialDataParser.pushHandler(new WorkflowStepTypesHandler(initialDataParser, this));
        } else if(localName.equals("workflows")) {
            initialDataParser.pushHandler(new WorkflowsHandler(initialDataParser, this));
        } else if(localName.equals("unitOfMeasureKinds")) {
            initialDataParser.pushHandler(new UnitOfMeasureKindsHandler(initialDataParser, this));
        } else if(localName.equals("unitOfMeasureKindUseTypes")) {
            initialDataParser.pushHandler(new UnitOfMeasureKindUseTypesHandler(initialDataParser, this));
        } else if(localName.equals("inventoryConditionUseTypes")) {
            initialDataParser.pushHandler(new InventoryConditionUseTypesHandler(initialDataParser, this));
        } else if(localName.equals("itemDescriptionTypes")) {
            initialDataParser.pushHandler(new ItemDescriptionTypesHandler(initialDataParser, this));
        } else if(localName.equals("itemDescriptionTypeUseTypes")) {
            initialDataParser.pushHandler(new ItemDescriptionTypeUseTypesHandler(initialDataParser, this));
        } else if(localName.equals("itemImageTypes")) {
            initialDataParser.pushHandler(new ItemImageTypesHandler(initialDataParser, this));
        } else if(localName.equals("itemTypes")) {
            initialDataParser.pushHandler(new ItemTypesHandler(initialDataParser, this));
        } else if(localName.equals("itemDeliveryTypes")) {
            initialDataParser.pushHandler(new ItemDeliveryTypesHandler(initialDataParser, this));
        } else if(localName.equals("itemInventoryTypes")) {
            initialDataParser.pushHandler(new ItemInventoryTypesHandler(initialDataParser, this));
        } else if(localName.equals("itemUseTypes")) {
            initialDataParser.pushHandler(new ItemUseTypesHandler(initialDataParser, this));
        } else if(localName.equals("itemAliasChecksumTypes")) {
            initialDataParser.pushHandler(new ItemAliasChecksumTypesHandler(initialDataParser, this));
        } else if(localName.equals("itemAliasTypes")) {
            initialDataParser.pushHandler(new ItemAliasTypesHandler(initialDataParser, this));
        } else if(localName.equals("itemWeightTypes")) {
            initialDataParser.pushHandler(new ItemWeightTypesHandler(initialDataParser, this));
        } else if(localName.equals("itemVolumeTypes")) {
            initialDataParser.pushHandler(new ItemVolumeTypesHandler(initialDataParser, this));
        } else if(localName.equals("relatedItemTypes")) {
            initialDataParser.pushHandler(new RelatedItemTypesHandler(initialDataParser, this));
        } else if(localName.equals("itemCategories")) {
            initialDataParser.pushHandler(new ItemCategoriesHandler(initialDataParser, this));
        } else if(localName.equals("items")) {
            initialDataParser.pushHandler(new ItemsHandler(initialDataParser, this));
        } else if(localName.equals("itemPriceTypes")) {
            initialDataParser.pushHandler(new ItemPriceTypesHandler(initialDataParser, this));
        } else if(localName.equals("harmonizedTariffScheduleCodeUnits")) {
            initialDataParser.pushHandler(new HarmonizedTariffScheduleCodeUnitsHandler(initialDataParser, this));
        } else if(localName.equals("harmonizedTariffScheduleCodeUseTypes")) {
            initialDataParser.pushHandler(new HarmonizedTariffScheduleCodeUseTypesHandler(initialDataParser, this));
        } else if(localName.equals("geoCodeTypes")) {
            initialDataParser.pushHandler(new GeoCodeTypesHandler(initialDataParser, this));
        } else if(localName.equals("geoCodeScopes")) {
            initialDataParser.pushHandler(new GeoCodeScopesHandler(initialDataParser, this));
        } else if(localName.equals("userLoginPasswordEncoderTypes")) {
            initialDataParser.pushHandler(new UserLoginPasswordEncoderTypesHandler(initialDataParser, this));
        } else if(localName.equals("userLoginPasswordTypes")) {
            initialDataParser.pushHandler(new UserLoginPasswordTypesHandler(initialDataParser, this));
        } else if(localName.equals("recoveryQuestions")) {
            initialDataParser.pushHandler(new RecoveryQuestionsHandler(initialDataParser, this));
        } else if(localName.equals("companies")) {
            initialDataParser.pushHandler(new CompaniesHandler(initialDataParser, this));
        } else if(localName.equals("customerTypes")) {
            initialDataParser.pushHandler(new CustomerTypesHandler(initialDataParser, this));
        } else if(localName.equals("customers")) {
            initialDataParser.pushHandler(new CustomersHandler(initialDataParser, this));
        }  else if(localName.equals("roleTypes")) {
            initialDataParser.pushHandler(new RoleTypesHandler(initialDataParser, this));
        } else if(localName.equals("contactMechanismAliasTypes")) {
            initialDataParser.pushHandler(new ContactMechanismAliasTypesHandler(initialDataParser, this));
        } else if(localName.equals("contactMechanismTypes")) {
            initialDataParser.pushHandler(new ContactMechanismTypesHandler(initialDataParser, this));
        } else if(localName.equals("partyRelationshipTypes")) {
            initialDataParser.pushHandler(new PartyRelationshipTypesHandler(initialDataParser, this));
        } else if(localName.equals("contactMechanismPurposes")) {
            initialDataParser.pushHandler(new ContactMechanismPurposesHandler(initialDataParser, this));
        } else if(localName.equals("employees")) {
            initialDataParser.pushHandler(new EmployeesHandler(initialDataParser, this));
        } else if(localName.equals("countries")) {
            initialDataParser.pushHandler(new CountriesHandler(initialDataParser, this));
        } else if(localName.equals("taxes")) {
            initialDataParser.pushHandler(new TaxesHandler(initialDataParser, this));
        } else if(localName.equals("securityRoleGroups")) {
            initialDataParser.pushHandler(new SecurityRoleGroupsHandler(initialDataParser, this));
        } else if(localName.equals("partySecurityRoleTemplates")) {
            initialDataParser.pushHandler(new PartySecurityRoleTemplatesHandler(initialDataParser, this));
        } else if(localName.equals("filterKinds")) {
            initialDataParser.pushHandler(new FilterKindsHandler(initialDataParser, this));
        } else if(localName.equals("filterAdjustmentSources")) {
            initialDataParser.pushHandler(new FilterAdjustmentSourcesHandler(initialDataParser, this));
        } else if(localName.equals("filterAdjustmentTypes")) {
            initialDataParser.pushHandler(new FilterAdjustmentTypesHandler(initialDataParser, this));
        } else if(localName.equals("printerGroupUseTypes")) {
            initialDataParser.pushHandler(new PrinterGroupUseTypesHandler(initialDataParser, this));
        } else if(localName.equals("printerGroups")) {
            initialDataParser.pushHandler(new PrinterGroupsHandler(initialDataParser, this));
        } else if(localName.equals("scaleUseTypes")) {
            initialDataParser.pushHandler(new ScaleUseTypesHandler(initialDataParser, this));
        } else if(localName.equals("scaleTypes")) {
            initialDataParser.pushHandler(new ScaleTypesHandler(initialDataParser, this));
        } else if(localName.equals("scales")) {
            initialDataParser.pushHandler(new ScalesHandler(initialDataParser, this));
        } else if(localName.equals("searchDefaultOperators")) {
            initialDataParser.pushHandler(new SearchDefaultOperatorsHandler(initialDataParser, this));
        } else if(localName.equals("searchSortDirections")) {
            initialDataParser.pushHandler(new SearchSortDirectionsHandler(initialDataParser, this));
        } else if(localName.equals("searchUseTypes")) {
            initialDataParser.pushHandler(new SearchUseTypesHandler(initialDataParser, this));
        } else if(localName.equals("searchResultActionTypes")) {
            initialDataParser.pushHandler(new SearchResultActionTypesHandler(initialDataParser, this));
        } else if(localName.equals("searchCheckSpellingActionTypes")) {
            initialDataParser.pushHandler(new SearchCheckSpellingActionTypesHandler(initialDataParser, this));
        } else if(localName.equals("searchKinds")) {
            initialDataParser.pushHandler(new SearchKindsHandler(initialDataParser, this));
        } else if(localName.equals("orderTypes")) {
            initialDataParser.pushHandler(new OrderTypesHandler(initialDataParser, this));
        } else if(localName.equals("orderRoleTypes")) {
            initialDataParser.pushHandler(new OrderRoleTypesHandler(initialDataParser, this));
        } else if(localName.equals("lotAliasTypes")) {
            initialDataParser.pushHandler(new LotAliasTypesHandler(initialDataParser, this));
        } else if(localName.equals("lotTimeTypes")) {
            initialDataParser.pushHandler(new LotTimeTypesHandler(initialDataParser, this));
        } else if(localName.equals("freeOnBoards")) {
            initialDataParser.pushHandler(new FreeOnBoardsHandler(initialDataParser, this));
        } else if(localName.equals("shipmentTypes")) {
            initialDataParser.pushHandler(new ShipmentTypesHandler(initialDataParser, this));
        } else if(localName.equals("picklistTypes")) {
            initialDataParser.pushHandler(new PicklistTypesHandler(initialDataParser, this));
        } else if(localName.equals("locationUseTypes")) {
            initialDataParser.pushHandler(new LocationUseTypesHandler(initialDataParser, this));
        } else if(localName.equals("warehouseTypes")) {
            initialDataParser.pushHandler(new WarehouseTypesHandler(initialDataParser, this));
        } else if(localName.equals("warehouses")) {
            initialDataParser.pushHandler(new WarehousesHandler(initialDataParser, this));
        } else if(localName.equals("inventoryConditions")) {
            initialDataParser.pushHandler(new InventoryConditionsHandler(initialDataParser, this));
        } else if(localName.equals("inventoryTransactionTypes")) {
            initialDataParser.pushHandler(new InventoryTransactionTypesHandler(initialDataParser, this));
        } else if(localName.equals("allocationPriorities")) {
            initialDataParser.pushHandler(new AllocationPrioritiesHandler(initialDataParser, this));
        } else if(localName.equals("wishlistTypes")) {
            initialDataParser.pushHandler(new WishlistTypesHandler(initialDataParser, this));
        } else if(localName.equals("paymentMethodTypes")) {
            initialDataParser.pushHandler(new PaymentMethodTypesHandler(initialDataParser, this));
        } else if(localName.equals("paymentMethods")) {
            initialDataParser.pushHandler(new PaymentMethodsHandler(initialDataParser, this));
        } else if(localName.equals("paymentProcessorActionTypes")) {
            initialDataParser.pushHandler(new PaymentProcessorActionTypesHandler(initialDataParser, this));
        } else if(localName.equals("paymentProcessorResultCodes")) {
            initialDataParser.pushHandler(new PaymentProcessorResultCodesHandler(initialDataParser, this));
        } else if(localName.equals("paymentProcessorTypes")) {
            initialDataParser.pushHandler(new PaymentProcessorTypesHandler(initialDataParser, this));
        } else if(localName.equals("paymentProcessors")) {
            initialDataParser.pushHandler(new PaymentProcessorsHandler(initialDataParser, this));
        } else if(localName.equals("glAccountTypes")) {
            initialDataParser.pushHandler(new GlAccountTypesHandler(initialDataParser, this));
        } else if(localName.equals("glAccountClasses")) {
            initialDataParser.pushHandler(new GlAccountClassesHandler(initialDataParser, this));
        } else if(localName.equals("glAccountCategories")) {
            initialDataParser.pushHandler(new GlAccountCategoriesHandler(initialDataParser, this));
        } else if(localName.equals("glResourceTypes")) {
            initialDataParser.pushHandler(new GlResourceTypesHandler(initialDataParser, this));
        } else if(localName.equals("symbolPositions")) {
            initialDataParser.pushHandler(new SymbolPositionsHandler(initialDataParser, this));
        } else if(localName.equals("itemAccountingCategories")) {
            initialDataParser.pushHandler(new ItemAccountingCategoriesHandler(initialDataParser, this));
        } else if(localName.equals("itemPurchasingCategories")) {
            initialDataParser.pushHandler(new ItemPurchasingCategoriesHandler(initialDataParser, this));
        } else if(localName.equals("glAccounts")) {
            initialDataParser.pushHandler(new GlAccountsHandler(initialDataParser, this));
        } else if(localName.equals("transactionTimeTypes")) {
            initialDataParser.pushHandler(new TransactionTimeTypesHandler(initialDataParser, this));
        } else if(localName.equals("transactionTypes")) {
            initialDataParser.pushHandler(new TransactionTypesHandler(initialDataParser, this));
        } else if(localName.equals("termTypes")) {
            initialDataParser.pushHandler(new TermTypesHandler(initialDataParser, this));
        } else if(localName.equals("terms")) {
            initialDataParser.pushHandler(new TermsHandler(initialDataParser, this));
        } else if(localName.equals("cancellationKinds")) {
            initialDataParser.pushHandler(new CancellationKindsHandler(initialDataParser, this));
        } else if(localName.equals("returnKinds")) {
            initialDataParser.pushHandler(new ReturnKindsHandler(initialDataParser, this));
        } else if(localName.equals("postalAddressFormats")) {
            initialDataParser.pushHandler(new PostalAddressFormatsHandler(initialDataParser, this));
        } else if(localName.equals("postalAddressElementTypes")) {
            initialDataParser.pushHandler(new PostalAddressElementTypesHandler(initialDataParser, this));
        } else if(localName.equals("vendors")) {
            initialDataParser.pushHandler(new VendorsHandler(initialDataParser, this));
        } else if(localName.equals("chainActionTypes")) {
            initialDataParser.pushHandler(new ChainActionTypesHandler(initialDataParser, this));
        } else if(localName.equals("chainKinds")) {
            initialDataParser.pushHandler(new ChainKindsHandler(initialDataParser, this));
        } else if(localName.equals("forumRoleTypes")) {
            initialDataParser.pushHandler(new ForumRoleTypesHandler(initialDataParser, this));
        } else if(localName.equals("forumTypes")) {
            initialDataParser.pushHandler(new ForumTypesHandler(initialDataParser, this));
        } else if(localName.equals("forumMessageTypes")) {
            initialDataParser.pushHandler(new ForumMessageTypesHandler(initialDataParser, this));
        } else if(localName.equals("forumMessagePartTypes")) {
            initialDataParser.pushHandler(new ForumMessagePartTypesHandler(initialDataParser, this));
        } else if(localName.equals("forumGroups")) {
            initialDataParser.pushHandler(new ForumGroupsHandler(initialDataParser, this));
        } else if(localName.equals("forums")) {
            initialDataParser.pushHandler(new ForumsHandler(initialDataParser, this));
        } else if(localName.equals("iconUsageTypes")) {
            initialDataParser.pushHandler(new IconUsageTypesHandler(initialDataParser, this));
        } else if(localName.equals("subscriptionKinds")) {
            initialDataParser.pushHandler(new SubscriptionKindsHandler(initialDataParser, this));
        } else if(localName.equals("clubs")) {
            initialDataParser.pushHandler(new ClubsHandler(initialDataParser, this));
        } else if(localName.equals("clubItemTypes")) {
            initialDataParser.pushHandler(new ClubItemTypesHandler(initialDataParser, this));
        } else if(localName.equals("salesOrderBatches")) {
            initialDataParser.pushHandler(new SalesOrderBatchesHandler(initialDataParser, this));
        } else if(localName.equals("communicationEventRoleTypes")) {
            initialDataParser.pushHandler(new CommunicationEventRoleTypesHandler(initialDataParser, this));
        } else if(localName.equals("communicationEventTypes")) {
            initialDataParser.pushHandler(new CommunicationEventTypesHandler(initialDataParser, this));
        } else if(localName.equals("communicationEventPurposes")) {
            initialDataParser.pushHandler(new CommunicationEventPurposesHandler(initialDataParser, this));
        } else if(localName.equals("responsibilityTypes")) {
            initialDataParser.pushHandler(new ResponsibilityTypesHandler(initialDataParser, this));
        } else if(localName.equals("skillTypes")) {
            initialDataParser.pushHandler(new SkillTypesHandler(initialDataParser, this));
        } else if(localName.equals("trainingClasses")) {
            initialDataParser.pushHandler(new TrainingClassesHandler(initialDataParser, this));
        } else if(localName.equals("leaveReasons")) {
            initialDataParser.pushHandler(new LeaveReasonsHandler(initialDataParser, this));
        } else if(localName.equals("leaveTypes")) {
            initialDataParser.pushHandler(new LeaveTypesHandler(initialDataParser, this));
        } else if(localName.equals("terminationReasons")) {
            initialDataParser.pushHandler(new TerminationReasonsHandler(initialDataParser, this));
        } else if(localName.equals("terminationTypes")) {
            initialDataParser.pushHandler(new TerminationTypesHandler(initialDataParser, this));
        } else if(localName.equals("communicationSourceTypes")) {
            initialDataParser.pushHandler(new CommunicationSourceTypesHandler(initialDataParser, this));
        } else if(localName.equals("communicationSources")) {
            initialDataParser.pushHandler(new CommunicationSourcesHandler(initialDataParser, this));
        } else if(localName.equals("documentTypes")) {
            initialDataParser.pushHandler(new DocumentTypesHandler(initialDataParser, this));
        } else if(localName.equals("documentTypeUsageTypes")) {
            initialDataParser.pushHandler(new DocumentTypeUsageTypesHandler(initialDataParser, this));
        } else if(localName.equals("carrierTypes")) {
            initialDataParser.pushHandler(new CarrierTypesHandler(initialDataParser, this));
        } else if(localName.equals("carriers")) {
            initialDataParser.pushHandler(new CarriersHandler(initialDataParser, this));
        } else if(localName.equals("shippingMethods")) {
            initialDataParser.pushHandler(new ShippingMethodsHandler(initialDataParser, this));
        } else if(localName.equals("workEffortTypes")) {
            initialDataParser.pushHandler(new WorkEffortTypesHandler(initialDataParser, this));
        } else if(localName.equals("tagScopes")) {
            initialDataParser.pushHandler(new TagScopesHandler(initialDataParser, this));
        } else if(localName.equals("associatePrograms")) {
            initialDataParser.pushHandler(new AssociateProgramsHandler(initialDataParser, this));
        } else if(localName.equals("entityAttributeGroups")) {
            initialDataParser.pushHandler(new EntityAttributeGroupsHandler(initialDataParser, this));
        } else if(localName.equals("genders")) {
            initialDataParser.pushHandler(new GendersHandler(initialDataParser, this));
        } else if(localName.equals("moods")) {
            initialDataParser.pushHandler(new MoodsHandler(initialDataParser, this));
        } else if(localName.equals("birthdayFormats")) {
            initialDataParser.pushHandler(new BirthdayFormatsHandler(initialDataParser, this));
        } else if(localName.equals("commandMessageTypes")) {
            initialDataParser.pushHandler(new CommandMessageTypesHandler(initialDataParser, this));
        } else if(localName.equals("contactListTypes")) {
            initialDataParser.pushHandler(new ContactListTypesHandler(initialDataParser, this));
        } else if(localName.equals("contactListGroups")) {
            initialDataParser.pushHandler(new ContactListGroupsHandler(initialDataParser, this));
        } else if(localName.equals("contactListFrequencies")) {
            initialDataParser.pushHandler(new ContactListFrequenciesHandler(initialDataParser, this));
        } else if(localName.equals("contactLists")) {
            initialDataParser.pushHandler(new ContactListsHandler(initialDataParser, this));
        } else if(localName.equals("jobs")) {
            initialDataParser.pushHandler(new JobsHandler(initialDataParser, this));
        } else if(localName.equals("employeeTypes")) {
            initialDataParser.pushHandler(new EmployeeTypesHandler(initialDataParser, this));
        } else if(localName.equals("vendorTypes")) {
            initialDataParser.pushHandler(new VendorTypesHandler(initialDataParser, this));
        } else if(localName.equals("billingAccountRoleTypes")) {
            initialDataParser.pushHandler(new BillingAccountRoleTypesHandler(initialDataParser, this));
        } else if(localName.equals("invoiceRoleTypes")) {
            initialDataParser.pushHandler(new InvoiceRoleTypesHandler(initialDataParser, this));
        } else if(localName.equals("invoiceLineUseTypes")) {
            initialDataParser.pushHandler(new InvoiceLineUseTypesHandler(initialDataParser, this));
        } else if(localName.equals("invoiceTypes")) {
            initialDataParser.pushHandler(new InvoiceTypesHandler(initialDataParser, this));
        } else if(localName.equals("periodKinds")) {
            initialDataParser.pushHandler(new PeriodKindsHandler(initialDataParser, this));
        } else if(localName.equals("batchTypes")) {
            initialDataParser.pushHandler(new BatchTypesHandler(initialDataParser, this));
        } else if(localName.equals("applications")) {
            initialDataParser.pushHandler(new ApplicationsHandler(initialDataParser, this));
        } else if(localName.equals("editors")) {
            initialDataParser.pushHandler(new EditorsHandler(initialDataParser, this));
        } else if(localName.equals("appearances")) {
            initialDataParser.pushHandler(new AppearancesHandler(initialDataParser, this));
        } else if(localName.equals("colors")) {
            initialDataParser.pushHandler(new ColorsHandler(initialDataParser, this));
        } else if(localName.equals("fontStyles")) {
            initialDataParser.pushHandler(new FontStylesHandler(initialDataParser, this));
        } else if(localName.equals("fontWeights")) {
            initialDataParser.pushHandler(new FontWeightsHandler(initialDataParser, this));
        } else if(localName.equals("textDecorations")) {
            initialDataParser.pushHandler(new TextDecorationsHandler(initialDataParser, this));
        } else if(localName.equals("textTransformations")) {
            initialDataParser.pushHandler(new TextTransformationsHandler(initialDataParser, this));
        } else if(localName.equals("licenseTypes")) {
            initialDataParser.pushHandler(new LicenseTypesHandler(initialDataParser, this));
        } else if(localName.equals("queueTypes")) {
            initialDataParser.pushHandler(new QueueTypesHandler(initialDataParser, this));
        } else if(localName.equals("includes")) {
            initialDataParser.pushHandler(new IncludesHandler(initialDataParser, this));
        } else if(localName.equals("generateBaseKeys")) {
            handleGenerateBaseKeys();
        } else if(localName.equals("loadBaseKeys")) {
            handleLoadBaseKeys();
        } else if(localName.equals("changeBaseKeys")) {
            handleChangeBaseKeys();
        }
    }

    public void handleGenerateBaseKeys()
            throws SAXException {
        try {
            BaseKeysHelper.getInstance().handleGenerateBaseKeys(initialDataParser.getUserVisit());
        } catch (IOException | NamingException ex) {
            throw new SAXException(ex);
        }
    }

    public void handleLoadBaseKeys()
            throws SAXException {
        try {
            BaseKeysHelper.getInstance().handleLoadBaseKeys(initialDataParser.getUserVisit());
        } catch (IOException | NamingException ex) {
            throw new SAXException(ex);
        }
    }

    public void handleChangeBaseKeys()
            throws SAXException {
        try {
            BaseKeysHelper.getInstance().handleChangeBaseKeys(initialDataParser.getUserVisit());
        } catch (IOException | NamingException ex) {
            throw new SAXException(ex);
        }
    }

    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("initialData")) {
            initialDataParser.popHandler();
        }
    }
    
}
