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

package com.echothree.model.control.graphql.server.util;

import com.echothree.control.user.accounting.common.AccountingUtil;
import com.echothree.control.user.accounting.server.command.GetCurrenciesCommand;
import com.echothree.control.user.accounting.server.command.GetCurrencyCommand;
import com.echothree.control.user.content.common.ContentUtil;
import com.echothree.control.user.content.server.command.GetContentCatalogCommand;
import com.echothree.control.user.content.server.command.GetContentCatalogItemCommand;
import com.echothree.control.user.content.server.command.GetContentCatalogItemsCommand;
import com.echothree.control.user.content.server.command.GetContentCatalogsCommand;
import com.echothree.control.user.content.server.command.GetContentCategoriesCommand;
import com.echothree.control.user.content.server.command.GetContentCategoryCommand;
import com.echothree.control.user.content.server.command.GetContentCategoryItemCommand;
import com.echothree.control.user.content.server.command.GetContentCategoryItemsCommand;
import com.echothree.control.user.content.server.command.GetContentCollectionCommand;
import com.echothree.control.user.content.server.command.GetContentCollectionsCommand;
import com.echothree.control.user.content.server.command.GetContentPageAreaCommand;
import com.echothree.control.user.content.server.command.GetContentPageAreaTypeCommand;
import com.echothree.control.user.content.server.command.GetContentPageAreaTypesCommand;
import com.echothree.control.user.content.server.command.GetContentPageAreasCommand;
import com.echothree.control.user.content.server.command.GetContentPageCommand;
import com.echothree.control.user.content.server.command.GetContentPageLayoutAreaCommand;
import com.echothree.control.user.content.server.command.GetContentPageLayoutAreasCommand;
import com.echothree.control.user.content.server.command.GetContentPageLayoutCommand;
import com.echothree.control.user.content.server.command.GetContentPageLayoutsCommand;
import com.echothree.control.user.content.server.command.GetContentPagesCommand;
import com.echothree.control.user.content.server.command.GetContentSectionCommand;
import com.echothree.control.user.content.server.command.GetContentSectionsCommand;
import com.echothree.control.user.content.server.command.GetContentWebAddressCommand;
import com.echothree.control.user.content.server.command.GetContentWebAddressesCommand;
import com.echothree.control.user.core.common.CoreUtil;
import com.echothree.control.user.core.server.command.GetAppearanceCommand;
import com.echothree.control.user.core.server.command.GetAppearancesCommand;
import com.echothree.control.user.core.server.command.GetColorCommand;
import com.echothree.control.user.core.server.command.GetColorsCommand;
import com.echothree.control.user.core.server.command.GetComponentVendorCommand;
import com.echothree.control.user.core.server.command.GetComponentVendorsCommand;
import com.echothree.control.user.core.server.command.GetEntityAttributeTypeCommand;
import com.echothree.control.user.core.server.command.GetEntityAttributeTypesCommand;
import com.echothree.control.user.core.server.command.GetEntityInstanceCommand;
import com.echothree.control.user.core.server.command.GetEntityInstancesCommand;
import com.echothree.control.user.core.server.command.GetEntityTypeCommand;
import com.echothree.control.user.core.server.command.GetEntityTypesCommand;
import com.echothree.control.user.core.server.command.GetFontStyleCommand;
import com.echothree.control.user.core.server.command.GetFontStylesCommand;
import com.echothree.control.user.core.server.command.GetFontWeightCommand;
import com.echothree.control.user.core.server.command.GetFontWeightsCommand;
import com.echothree.control.user.core.server.command.GetMimeTypeCommand;
import com.echothree.control.user.core.server.command.GetMimeTypeFileExtensionCommand;
import com.echothree.control.user.core.server.command.GetMimeTypeFileExtensionsCommand;
import com.echothree.control.user.core.server.command.GetMimeTypeUsageTypeCommand;
import com.echothree.control.user.core.server.command.GetMimeTypeUsageTypesCommand;
import com.echothree.control.user.core.server.command.GetMimeTypesCommand;
import com.echothree.control.user.core.server.command.GetTextDecorationCommand;
import com.echothree.control.user.core.server.command.GetTextDecorationsCommand;
import com.echothree.control.user.core.server.command.GetTextTransformationCommand;
import com.echothree.control.user.core.server.command.GetTextTransformationsCommand;
import com.echothree.control.user.filter.common.FilterUtil;
import com.echothree.control.user.filter.server.command.GetFilterAdjustmentSourceCommand;
import com.echothree.control.user.filter.server.command.GetFilterAdjustmentSourcesCommand;
import com.echothree.control.user.filter.server.command.GetFilterAdjustmentTypeCommand;
import com.echothree.control.user.filter.server.command.GetFilterAdjustmentTypesCommand;
import com.echothree.control.user.filter.server.command.GetFilterKindCommand;
import com.echothree.control.user.filter.server.command.GetFilterKindsCommand;
import com.echothree.control.user.filter.server.command.GetFilterTypeCommand;
import com.echothree.control.user.filter.server.command.GetFilterTypesCommand;
import com.echothree.control.user.inventory.common.InventoryUtil;
import com.echothree.control.user.inventory.server.command.GetInventoryConditionCommand;
import com.echothree.control.user.inventory.server.command.GetInventoryConditionsCommand;
import com.echothree.control.user.inventory.server.command.GetLotCommand;
import com.echothree.control.user.inventory.server.command.GetLotsCommand;
import com.echothree.control.user.item.common.ItemUtil;
import com.echothree.control.user.item.server.command.GetItemCategoriesCommand;
import com.echothree.control.user.item.server.command.GetItemCategoryCommand;
import com.echothree.control.user.item.server.command.GetItemCommand;
import com.echothree.control.user.offer.common.OfferUtil;
import com.echothree.control.user.offer.server.command.GetOfferCommand;
import com.echothree.control.user.offer.server.command.GetOfferNameElementCommand;
import com.echothree.control.user.offer.server.command.GetOfferNameElementsCommand;
import com.echothree.control.user.offer.server.command.GetOfferUseCommand;
import com.echothree.control.user.offer.server.command.GetOfferUsesCommand;
import com.echothree.control.user.offer.server.command.GetOffersCommand;
import com.echothree.control.user.offer.server.command.GetUseCommand;
import com.echothree.control.user.offer.server.command.GetUseNameElementCommand;
import com.echothree.control.user.offer.server.command.GetUseNameElementsCommand;
import com.echothree.control.user.offer.server.command.GetUseTypeCommand;
import com.echothree.control.user.offer.server.command.GetUseTypesCommand;
import com.echothree.control.user.offer.server.command.GetUsesCommand;
import com.echothree.control.user.party.common.PartyUtil;
import com.echothree.control.user.party.server.command.GetDateTimeFormatCommand;
import com.echothree.control.user.party.server.command.GetDateTimeFormatsCommand;
import com.echothree.control.user.party.server.command.GetLanguageCommand;
import com.echothree.control.user.party.server.command.GetLanguagesCommand;
import com.echothree.control.user.party.server.command.GetNameSuffixesCommand;
import com.echothree.control.user.party.server.command.GetPersonalTitlesCommand;
import com.echothree.control.user.party.server.command.GetTimeZoneCommand;
import com.echothree.control.user.party.server.command.GetTimeZonesCommand;
import com.echothree.control.user.payment.common.PaymentUtil;
import com.echothree.control.user.payment.server.command.GetPaymentMethodTypeCommand;
import com.echothree.control.user.payment.server.command.GetPaymentMethodTypesCommand;
import com.echothree.control.user.payment.server.command.GetPaymentProcessorActionTypeCommand;
import com.echothree.control.user.payment.server.command.GetPaymentProcessorActionTypesCommand;
import com.echothree.control.user.payment.server.command.GetPaymentProcessorCommand;
import com.echothree.control.user.payment.server.command.GetPaymentProcessorResultCodeCommand;
import com.echothree.control.user.payment.server.command.GetPaymentProcessorResultCodesCommand;
import com.echothree.control.user.payment.server.command.GetPaymentProcessorTransactionCommand;
import com.echothree.control.user.payment.server.command.GetPaymentProcessorTransactionsCommand;
import com.echothree.control.user.payment.server.command.GetPaymentProcessorTypeCodeCommand;
import com.echothree.control.user.payment.server.command.GetPaymentProcessorTypeCodeTypeCommand;
import com.echothree.control.user.payment.server.command.GetPaymentProcessorTypeCommand;
import com.echothree.control.user.payment.server.command.GetPaymentProcessorTypesCommand;
import com.echothree.control.user.payment.server.command.GetPaymentProcessorsCommand;
import com.echothree.control.user.queue.common.QueueUtil;
import com.echothree.control.user.queue.server.command.GetQueueTypeCommand;
import com.echothree.control.user.queue.server.command.GetQueueTypesCommand;
import com.echothree.control.user.search.common.SearchUtil;
import com.echothree.control.user.search.server.command.GetCustomerResultsCommand;
import com.echothree.control.user.selector.common.SelectorUtil;
import com.echothree.control.user.selector.server.command.GetSelectorKindCommand;
import com.echothree.control.user.selector.server.command.GetSelectorKindsCommand;
import com.echothree.control.user.selector.server.command.GetSelectorTypeCommand;
import com.echothree.control.user.selector.server.command.GetSelectorTypesCommand;
import com.echothree.control.user.sequence.common.SequenceUtil;
import com.echothree.control.user.sequence.server.command.GetSequenceChecksumTypeCommand;
import com.echothree.control.user.sequence.server.command.GetSequenceChecksumTypesCommand;
import com.echothree.control.user.sequence.server.command.GetSequenceCommand;
import com.echothree.control.user.sequence.server.command.GetSequenceEncoderTypeCommand;
import com.echothree.control.user.sequence.server.command.GetSequenceEncoderTypesCommand;
import com.echothree.control.user.sequence.server.command.GetSequenceTypeCommand;
import com.echothree.control.user.sequence.server.command.GetSequenceTypesCommand;
import com.echothree.control.user.sequence.server.command.GetSequencesCommand;
import com.echothree.control.user.shipment.common.ShipmentUtil;
import com.echothree.control.user.shipment.server.command.GetFreeOnBoardCommand;
import com.echothree.control.user.shipment.server.command.GetFreeOnBoardsCommand;
import com.echothree.control.user.uom.common.UomUtil;
import com.echothree.control.user.uom.server.command.GetUnitOfMeasureKindCommand;
import com.echothree.control.user.uom.server.command.GetUnitOfMeasureKindUseCommand;
import com.echothree.control.user.uom.server.command.GetUnitOfMeasureKindUseTypeCommand;
import com.echothree.control.user.uom.server.command.GetUnitOfMeasureKindUseTypesCommand;
import com.echothree.control.user.uom.server.command.GetUnitOfMeasureKindUsesCommand;
import com.echothree.control.user.uom.server.command.GetUnitOfMeasureKindsCommand;
import com.echothree.control.user.uom.server.command.GetUnitOfMeasureTypeCommand;
import com.echothree.control.user.uom.server.command.GetUnitOfMeasureTypesCommand;
import com.echothree.control.user.user.common.UserUtil;
import com.echothree.control.user.user.server.command.GetRecoveryQuestionCommand;
import com.echothree.control.user.user.server.command.GetRecoveryQuestionsCommand;
import com.echothree.control.user.user.server.command.GetUserLoginCommand;
import com.echothree.model.control.accounting.server.graphql.CurrencyObject;
import com.echothree.model.control.content.server.graphql.ContentCatalogItemObject;
import com.echothree.model.control.content.server.graphql.ContentCatalogObject;
import com.echothree.model.control.content.server.graphql.ContentCategoryItemObject;
import com.echothree.model.control.content.server.graphql.ContentCategoryObject;
import com.echothree.model.control.content.server.graphql.ContentCollectionObject;
import com.echothree.model.control.content.server.graphql.ContentPageAreaObject;
import com.echothree.model.control.content.server.graphql.ContentPageAreaTypeObject;
import com.echothree.model.control.content.server.graphql.ContentPageLayoutAreaObject;
import com.echothree.model.control.content.server.graphql.ContentPageLayoutObject;
import com.echothree.model.control.content.server.graphql.ContentPageObject;
import com.echothree.model.control.content.server.graphql.ContentSectionObject;
import com.echothree.model.control.content.server.graphql.ContentWebAddressObject;
import com.echothree.model.control.core.server.graphql.AppearanceObject;
import com.echothree.model.control.core.server.graphql.ColorObject;
import com.echothree.model.control.core.server.graphql.ComponentVendorObject;
import com.echothree.model.control.core.server.graphql.EntityAttributeTypeObject;
import com.echothree.model.control.core.server.graphql.EntityInstanceObject;
import com.echothree.model.control.core.server.graphql.EntityTypeObject;
import com.echothree.model.control.core.server.graphql.FontStyleObject;
import com.echothree.model.control.core.server.graphql.FontWeightObject;
import com.echothree.model.control.core.server.graphql.MimeTypeFileExtensionObject;
import com.echothree.model.control.core.server.graphql.MimeTypeObject;
import com.echothree.model.control.core.server.graphql.MimeTypeUsageTypeObject;
import com.echothree.model.control.core.server.graphql.TextDecorationObject;
import com.echothree.model.control.core.server.graphql.TextTransformationObject;
import com.echothree.model.control.filter.server.graphql.FilterAdjustmentSourceObject;
import com.echothree.model.control.filter.server.graphql.FilterAdjustmentTypeObject;
import com.echothree.model.control.filter.server.graphql.FilterKindObject;
import com.echothree.model.control.filter.server.graphql.FilterTypeObject;
import com.echothree.model.control.inventory.server.graphql.InventoryConditionObject;
import com.echothree.model.control.inventory.server.graphql.LotObject;
import com.echothree.model.control.item.server.graphql.ItemCategoryObject;
import com.echothree.model.control.item.server.graphql.ItemObject;
import com.echothree.model.control.offer.server.graphql.OfferNameElementObject;
import com.echothree.model.control.offer.server.graphql.OfferObject;
import com.echothree.model.control.offer.server.graphql.OfferUseObject;
import com.echothree.model.control.offer.server.graphql.UseNameElementObject;
import com.echothree.model.control.offer.server.graphql.UseObject;
import com.echothree.model.control.offer.server.graphql.UseTypeObject;
import com.echothree.model.control.party.server.graphql.DateTimeFormatObject;
import com.echothree.model.control.party.server.graphql.LanguageObject;
import com.echothree.model.control.party.server.graphql.NameSuffixObject;
import com.echothree.model.control.party.server.graphql.PersonalTitleObject;
import com.echothree.model.control.party.server.graphql.TimeZoneObject;
import com.echothree.model.control.payment.server.graphql.PaymentMethodTypeObject;
import com.echothree.model.control.payment.server.graphql.PaymentProcessorActionTypeObject;
import com.echothree.model.control.payment.server.graphql.PaymentProcessorObject;
import com.echothree.model.control.payment.server.graphql.PaymentProcessorResultCodeObject;
import com.echothree.model.control.payment.server.graphql.PaymentProcessorTransactionObject;
import com.echothree.model.control.payment.server.graphql.PaymentProcessorTypeCodeObject;
import com.echothree.model.control.payment.server.graphql.PaymentProcessorTypeCodeTypeObject;
import com.echothree.model.control.payment.server.graphql.PaymentProcessorTypeObject;
import com.echothree.model.control.queue.server.graphql.QueueTypeObject;
import com.echothree.model.control.search.server.graphql.CustomerResultsObject;
import com.echothree.model.control.selector.server.graphql.SelectorKindObject;
import com.echothree.model.control.selector.server.graphql.SelectorTypeObject;
import com.echothree.model.control.sequence.server.graphql.SequenceChecksumTypeObject;
import com.echothree.model.control.sequence.server.graphql.SequenceEncoderTypeObject;
import com.echothree.model.control.sequence.server.graphql.SequenceObject;
import com.echothree.model.control.sequence.server.graphql.SequenceTypeObject;
import com.echothree.model.control.shipment.server.graphql.FreeOnBoardObject;
import com.echothree.model.control.uom.server.graphql.UnitOfMeasureKindObject;
import com.echothree.model.control.uom.server.graphql.UnitOfMeasureKindUseObject;
import com.echothree.model.control.uom.server.graphql.UnitOfMeasureKindUseTypeObject;
import com.echothree.model.control.uom.server.graphql.UnitOfMeasureTypeObject;
import com.echothree.model.control.user.server.graphql.RecoveryQuestionObject;
import com.echothree.model.control.user.server.graphql.UserLoginObject;
import com.echothree.model.control.user.server.graphql.UserSessionObject;
import com.echothree.model.control.user.server.graphql.UserVisitObject;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.content.server.entity.ContentCatalog;
import com.echothree.model.data.content.server.entity.ContentCatalogItem;
import com.echothree.model.data.content.server.entity.ContentCategory;
import com.echothree.model.data.content.server.entity.ContentCategoryItem;
import com.echothree.model.data.content.server.entity.ContentCollection;
import com.echothree.model.data.content.server.entity.ContentPage;
import com.echothree.model.data.content.server.entity.ContentPageArea;
import com.echothree.model.data.content.server.entity.ContentPageAreaType;
import com.echothree.model.data.content.server.entity.ContentPageLayout;
import com.echothree.model.data.content.server.entity.ContentPageLayoutArea;
import com.echothree.model.data.content.server.entity.ContentSection;
import com.echothree.model.data.content.server.entity.ContentWebAddress;
import com.echothree.model.data.core.server.entity.Appearance;
import com.echothree.model.data.core.server.entity.Color;
import com.echothree.model.data.core.server.entity.ComponentVendor;
import com.echothree.model.data.core.server.entity.EntityAttributeType;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.EntityType;
import com.echothree.model.data.core.server.entity.FontStyle;
import com.echothree.model.data.core.server.entity.FontWeight;
import com.echothree.model.data.core.server.entity.MimeType;
import com.echothree.model.data.core.server.entity.MimeTypeFileExtension;
import com.echothree.model.data.core.server.entity.MimeTypeUsageType;
import com.echothree.model.data.core.server.entity.TextDecoration;
import com.echothree.model.data.core.server.entity.TextTransformation;
import com.echothree.model.data.filter.server.entity.FilterAdjustmentSource;
import com.echothree.model.data.filter.server.entity.FilterAdjustmentType;
import com.echothree.model.data.filter.server.entity.FilterKind;
import com.echothree.model.data.filter.server.entity.FilterType;
import com.echothree.model.data.inventory.server.entity.InventoryCondition;
import com.echothree.model.data.inventory.server.entity.Lot;
import com.echothree.model.data.item.server.entity.Item;
import com.echothree.model.data.item.server.entity.ItemCategory;
import com.echothree.model.data.offer.server.entity.Offer;
import com.echothree.model.data.offer.server.entity.OfferNameElement;
import com.echothree.model.data.offer.server.entity.OfferUse;
import com.echothree.model.data.offer.server.entity.Use;
import com.echothree.model.data.offer.server.entity.UseNameElement;
import com.echothree.model.data.offer.server.entity.UseType;
import com.echothree.model.data.party.server.entity.DateTimeFormat;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.NameSuffix;
import com.echothree.model.data.party.server.entity.PersonalTitle;
import com.echothree.model.data.party.server.entity.TimeZone;
import com.echothree.model.data.payment.server.entity.PaymentMethodType;
import com.echothree.model.data.payment.server.entity.PaymentProcessor;
import com.echothree.model.data.payment.server.entity.PaymentProcessorActionType;
import com.echothree.model.data.payment.server.entity.PaymentProcessorResultCode;
import com.echothree.model.data.payment.server.entity.PaymentProcessorTransaction;
import com.echothree.model.data.payment.server.entity.PaymentProcessorType;
import com.echothree.model.data.payment.server.entity.PaymentProcessorTypeCode;
import com.echothree.model.data.payment.server.entity.PaymentProcessorTypeCodeType;
import com.echothree.model.data.queue.server.entity.QueueType;
import com.echothree.model.data.selector.server.entity.SelectorKind;
import com.echothree.model.data.selector.server.entity.SelectorType;
import com.echothree.model.data.sequence.server.entity.Sequence;
import com.echothree.model.data.sequence.server.entity.SequenceChecksumType;
import com.echothree.model.data.sequence.server.entity.SequenceEncoderType;
import com.echothree.model.data.sequence.server.entity.SequenceType;
import com.echothree.model.data.shipment.server.entity.FreeOnBoard;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKind;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKindUse;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKindUseType;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureType;
import com.echothree.model.data.user.server.entity.RecoveryQuestion;
import com.echothree.model.data.user.server.entity.UserLogin;
import com.echothree.model.data.user.server.entity.UserSession;
import com.echothree.model.data.user.server.entity.UserVisit;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLID;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;
import java.util.ArrayList;
import java.util.Collection;
import static java.util.Collections.emptyList;
import javax.naming.NamingException;

@GraphQLName("query")
public final class GraphQlQueries
        extends BaseGraphQl {

    @GraphQLField
    @GraphQLName("sequence")
    public static SequenceObject sequence(final DataFetchingEnvironment env,
            @GraphQLName("sequenceTypeName") final String sequenceTypeName,
            @GraphQLName("sequenceName") final String sequenceName,
            @GraphQLName("id") @GraphQLID final String id) {
        Sequence sequence;

        try {
            var commandForm = SequenceUtil.getHome().getGetSequenceForm();

            commandForm.setSequenceTypeName(sequenceTypeName);
            commandForm.setSequenceName(sequenceName);
            commandForm.setUlid(id);

            sequence = new GetSequenceCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return sequence == null ? null : new SequenceObject(sequence);
    }

    @GraphQLField
    @GraphQLName("sequences")
    public static Collection<SequenceObject> sequences(final DataFetchingEnvironment env,
            @GraphQLName("sequenceTypeName") final String sequenceTypeName) {
        Collection<Sequence> sequences;
        Collection<SequenceObject> sequenceObjects;

        try {
            var commandForm = SequenceUtil.getHome().getGetSequencesForm();

            commandForm.setSequenceTypeName(sequenceTypeName);

            sequences = new GetSequencesCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        if(sequences == null) {
            sequenceObjects = emptyList();
        } else {
            sequenceObjects = new ArrayList<>(sequences.size());

            sequences.stream()
                    .map(SequenceObject::new)
                    .forEachOrdered(sequenceObjects::add);
        }

        return sequenceObjects;
    }

    @GraphQLField
    @GraphQLName("sequenceType")
    public static SequenceTypeObject sequenceType(final DataFetchingEnvironment env,
            @GraphQLName("sequenceTypeName") final String sequenceTypeName,
            @GraphQLName("id") @GraphQLID final String id) {
        SequenceType sequenceType;

        try {
            var commandForm = SequenceUtil.getHome().getGetSequenceTypeForm();

            commandForm.setSequenceTypeName(sequenceTypeName);
            commandForm.setUlid(id);

            sequenceType = new GetSequenceTypeCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return sequenceType == null ? null : new SequenceTypeObject(sequenceType);
    }

    @GraphQLField
    @GraphQLName("sequenceTypes")
    public static Collection<SequenceTypeObject> sequenceTypes(final DataFetchingEnvironment env) {
        Collection<SequenceType> sequenceTypes;
        Collection<SequenceTypeObject> sequenceTypeObjects;

        try {
            var commandForm = SequenceUtil.getHome().getGetSequenceTypesForm();

            sequenceTypes = new GetSequenceTypesCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        if(sequenceTypes == null) {
            sequenceTypeObjects = emptyList();
        } else {
            sequenceTypeObjects = new ArrayList<>(sequenceTypes.size());

            sequenceTypes.stream()
                    .map(SequenceTypeObject::new)
                    .forEachOrdered(sequenceTypeObjects::add);
        }

        return sequenceTypeObjects;
    }

    @GraphQLField
    @GraphQLName("sequenceChecksumType")
    public static SequenceChecksumTypeObject sequenceChecksumType(final DataFetchingEnvironment env,
            @GraphQLName("sequenceChecksumTypeName") final String sequenceChecksumTypeName) {
        SequenceChecksumType sequenceChecksumType;

        try {
            var commandForm = SequenceUtil.getHome().getGetSequenceChecksumTypeForm();

            commandForm.setSequenceChecksumTypeName(sequenceChecksumTypeName);

            sequenceChecksumType = new GetSequenceChecksumTypeCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return sequenceChecksumType == null ? null : new SequenceChecksumTypeObject(sequenceChecksumType);
    }

    @GraphQLField
    @GraphQLName("sequenceChecksumTypes")
    public static Collection<SequenceChecksumTypeObject> sequenceChecksumTypes(final DataFetchingEnvironment env) {
        Collection<SequenceChecksumType> sequenceChecksumTypes;
        Collection<SequenceChecksumTypeObject> sequenceChecksumTypeObjects;

        try {
            var commandForm = SequenceUtil.getHome().getGetSequenceChecksumTypesForm();

            sequenceChecksumTypes = new GetSequenceChecksumTypesCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        if(sequenceChecksumTypes == null) {
            sequenceChecksumTypeObjects = emptyList();
        } else {
            sequenceChecksumTypeObjects = new ArrayList<>(sequenceChecksumTypes.size());

            sequenceChecksumTypes.stream()
                    .map(SequenceChecksumTypeObject::new)
                    .forEachOrdered(sequenceChecksumTypeObjects::add);
        }

        return sequenceChecksumTypeObjects;
    }

    @GraphQLField
    @GraphQLName("sequenceEncoderType")
    public static SequenceEncoderTypeObject sequenceEncoderType(final DataFetchingEnvironment env,
            @GraphQLName("sequenceEncoderTypeName") final String sequenceEncoderTypeName) {
        SequenceEncoderType sequenceEncoderType;

        try {
            var commandForm = SequenceUtil.getHome().getGetSequenceEncoderTypeForm();

            commandForm.setSequenceEncoderTypeName(sequenceEncoderTypeName);

            sequenceEncoderType = new GetSequenceEncoderTypeCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return sequenceEncoderType == null ? null : new SequenceEncoderTypeObject(sequenceEncoderType);
    }

    @GraphQLField
    @GraphQLName("sequenceEncoderTypes")
    public static Collection<SequenceEncoderTypeObject> sequenceEncoderTypes(final DataFetchingEnvironment env) {
        Collection<SequenceEncoderType> sequenceEncoderTypes;
        Collection<SequenceEncoderTypeObject> sequenceEncoderTypeObjects;

        try {
            var commandForm = SequenceUtil.getHome().getGetSequenceEncoderTypesForm();

            sequenceEncoderTypes = new GetSequenceEncoderTypesCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        if(sequenceEncoderTypes == null) {
            sequenceEncoderTypeObjects = emptyList();
        } else {
            sequenceEncoderTypeObjects = new ArrayList<>(sequenceEncoderTypes.size());

            sequenceEncoderTypes.stream()
                    .map(SequenceEncoderTypeObject::new)
                    .forEachOrdered(sequenceEncoderTypeObjects::add);
        }

        return sequenceEncoderTypeObjects;
    }

    @GraphQLField
    @GraphQLName("selectorKind")
    public static SelectorKindObject selectorKind(final DataFetchingEnvironment env,
            @GraphQLName("selectorKindName") final String selectorKindName,
            @GraphQLName("id") @GraphQLID final String id) {
        SelectorKind selectorKind;

        try {
            var commandForm = SelectorUtil.getHome().getGetSelectorKindForm();

            commandForm.setSelectorKindName(selectorKindName);
            commandForm.setUlid(id);

            selectorKind = new GetSelectorKindCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return selectorKind == null ? null : new SelectorKindObject(selectorKind);
    }

    @GraphQLField
    @GraphQLName("selectorKinds")
    public static Collection<SelectorKindObject> selectorKinds(final DataFetchingEnvironment env) {
        Collection<SelectorKind> selectorKinds;
        Collection<SelectorKindObject> selectorKindObjects;

        try {
            var commandForm = SelectorUtil.getHome().getGetSelectorKindsForm();

            selectorKinds = new GetSelectorKindsCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        if(selectorKinds == null) {
            selectorKindObjects = emptyList();
        } else {
            selectorKindObjects = new ArrayList<>(selectorKinds.size());

            selectorKinds.stream()
                    .map(SelectorKindObject::new)
                    .forEachOrdered(selectorKindObjects::add);
        }

        return selectorKindObjects;
    }

    @GraphQLField
    @GraphQLName("selectorType")
    public static SelectorTypeObject selectorType(final DataFetchingEnvironment env,
            @GraphQLName("selectorKindName") final String selectorKindName,
            @GraphQLName("selectorTypeName") final String selectorTypeName,
            @GraphQLName("id") @GraphQLID final String id) {
        SelectorType selectorType;

        try {
            var commandForm = SelectorUtil.getHome().getGetSelectorTypeForm();

            commandForm.setSelectorKindName(selectorKindName);
            commandForm.setSelectorTypeName(selectorTypeName);
            commandForm.setUlid(id);

            selectorType = new GetSelectorTypeCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return selectorType == null ? null : new SelectorTypeObject(selectorType);
    }

    @GraphQLField
    @GraphQLName("selectorTypes")
    public static Collection<SelectorTypeObject> selectorTypes(final DataFetchingEnvironment env,
            @GraphQLName("selectorKindName") final String selectorKindName) {
        Collection<SelectorType> selectorTypes;
        Collection<SelectorTypeObject> selectorTypeObjects;

        try {
            var commandForm = SelectorUtil.getHome().getGetSelectorTypesForm();

            commandForm.setSelectorKindName(selectorKindName);

            selectorTypes = new GetSelectorTypesCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        if(selectorTypes == null) {
            selectorTypeObjects = emptyList();
        } else {
            selectorTypeObjects = new ArrayList<>(selectorTypes.size());

            selectorTypes.stream()
                    .map(SelectorTypeObject::new)
                    .forEachOrdered(selectorTypeObjects::add);
        }

        return selectorTypeObjects;
    }

    @GraphQLField
    @GraphQLName("filterKind")
    public static FilterKindObject filterKind(final DataFetchingEnvironment env,
            @GraphQLName("filterKindName") final String filterKindName,
            @GraphQLName("id") @GraphQLID final String id) {
        FilterKind filterKind;

        try {
            var commandForm = FilterUtil.getHome().getGetFilterKindForm();

            commandForm.setFilterKindName(filterKindName);
            commandForm.setUlid(id);

            filterKind = new GetFilterKindCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return filterKind == null ? null : new FilterKindObject(filterKind);
    }

    @GraphQLField
    @GraphQLName("filterKinds")
    public static Collection<FilterKindObject> filterKinds(final DataFetchingEnvironment env) {
        Collection<FilterKind> filterKinds;
        Collection<FilterKindObject> filterKindObjects;

        try {
            var commandForm = FilterUtil.getHome().getGetFilterKindsForm();

            filterKinds = new GetFilterKindsCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        if(filterKinds == null) {
            filterKindObjects = emptyList();
        } else {
            filterKindObjects = new ArrayList<>(filterKinds.size());

            filterKinds.stream()
                    .map(FilterKindObject::new)
                    .forEachOrdered(filterKindObjects::add);
        }

        return filterKindObjects;
    }

    @GraphQLField
    @GraphQLName("filterType")
    public static FilterTypeObject filterType(final DataFetchingEnvironment env,
            @GraphQLName("filterKindName") final String filterKindName,
            @GraphQLName("filterTypeName") final String filterTypeName,
            @GraphQLName("id") @GraphQLID final String id) {
        FilterType filterType;

        try {
            var commandForm = FilterUtil.getHome().getGetFilterTypeForm();

            commandForm.setFilterKindName(filterKindName);
            commandForm.setFilterTypeName(filterTypeName);
            commandForm.setUlid(id);

            filterType = new GetFilterTypeCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return filterType == null ? null : new FilterTypeObject(filterType);
    }

    @GraphQLField
    @GraphQLName("filterTypes")
    public static Collection<FilterTypeObject> filterTypes(final DataFetchingEnvironment env,
            @GraphQLName("filterKindName") final String filterKindName) {
        Collection<FilterType> filterTypes;
        Collection<FilterTypeObject> filterTypeObjects;

        try {
            var commandForm = FilterUtil.getHome().getGetFilterTypesForm();

            commandForm.setFilterKindName(filterKindName);

            filterTypes = new GetFilterTypesCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        if(filterTypes == null) {
            filterTypeObjects = emptyList();
        } else {
            filterTypeObjects = new ArrayList<>(filterTypes.size());

            filterTypes.stream()
                    .map(FilterTypeObject::new)
                    .forEachOrdered(filterTypeObjects::add);
        }

        return filterTypeObjects;
    }

    @GraphQLField
    @GraphQLName("filterAdjustmentSource")
    public static FilterAdjustmentSourceObject filterAdjustmentSource(final DataFetchingEnvironment env,
            @GraphQLName("filterAdjustmentSourceName") @GraphQLNonNull final String filterAdjustmentSourceName) {
        FilterAdjustmentSource filterAdjustmentSource;

        try {
            var commandForm = FilterUtil.getHome().getGetFilterAdjustmentSourceForm();

            commandForm.setFilterAdjustmentSourceName(filterAdjustmentSourceName);

            filterAdjustmentSource = new GetFilterAdjustmentSourceCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return filterAdjustmentSource == null ? null : new FilterAdjustmentSourceObject(filterAdjustmentSource);
    }

    @GraphQLField
    @GraphQLName("filterAdjustmentSources")
    public static Collection<FilterAdjustmentSourceObject> filterAdjustmentSources(final DataFetchingEnvironment env) {
        Collection<FilterAdjustmentSource> filterAdjustmentSources;
        Collection<FilterAdjustmentSourceObject> filterAdjustmentSourceObjects;

        try {
            var commandForm = FilterUtil.getHome().getGetFilterAdjustmentSourcesForm();

            filterAdjustmentSources = new GetFilterAdjustmentSourcesCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        if(filterAdjustmentSources == null) {
            filterAdjustmentSourceObjects = emptyList();
        } else {
            filterAdjustmentSourceObjects = new ArrayList<>(filterAdjustmentSources.size());

            filterAdjustmentSources.stream()
                    .map(FilterAdjustmentSourceObject::new)
                    .forEachOrdered(filterAdjustmentSourceObjects::add);
        }

        return filterAdjustmentSourceObjects;
    }

    @GraphQLField
    @GraphQLName("filterAdjustmentType")
    public static FilterAdjustmentTypeObject filterAdjustmentType(final DataFetchingEnvironment env,
            @GraphQLName("filterAdjustmentTypeName") @GraphQLNonNull final String filterAdjustmentTypeName) {
        FilterAdjustmentType filterAdjustmentType;

        try {
            var commandForm = FilterUtil.getHome().getGetFilterAdjustmentTypeForm();

            commandForm.setFilterAdjustmentTypeName(filterAdjustmentTypeName);

            filterAdjustmentType = new GetFilterAdjustmentTypeCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return filterAdjustmentType == null ? null : new FilterAdjustmentTypeObject(filterAdjustmentType);
    }

    @GraphQLField
    @GraphQLName("filterAdjustmentTypes")
    public static Collection<FilterAdjustmentTypeObject> filterAdjustmentTypes(final DataFetchingEnvironment env) {
        Collection<FilterAdjustmentType> filterAdjustmentTypes;
        Collection<FilterAdjustmentTypeObject> filterAdjustmentTypeObjects;

        try {
            var commandForm = FilterUtil.getHome().getGetFilterAdjustmentTypesForm();

            filterAdjustmentTypes = new GetFilterAdjustmentTypesCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        if(filterAdjustmentTypes == null) {
            filterAdjustmentTypeObjects = emptyList();
        } else {
            filterAdjustmentTypeObjects = new ArrayList<>(filterAdjustmentTypes.size());

            filterAdjustmentTypes.stream()
                    .map(FilterAdjustmentTypeObject::new)
                    .forEachOrdered(filterAdjustmentTypeObjects::add);
        }

        return filterAdjustmentTypeObjects;
    }

    @GraphQLField
    @GraphQLName("offerUse")
    public static OfferUseObject offerUse(final DataFetchingEnvironment env,
            @GraphQLName("offerName") @GraphQLNonNull final String offerName,
            @GraphQLName("useName") @GraphQLNonNull final String useName) {
        OfferUse offerUse;

        try {
            var commandForm = OfferUtil.getHome().getGetOfferUseForm();

            commandForm.setOfferName(offerName);
            commandForm.setUseName(useName);

            offerUse = new GetOfferUseCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return offerUse == null ? null : new OfferUseObject(offerUse);
    }

    @GraphQLField
    @GraphQLName("offerUses")
    public static Collection<OfferUseObject> offerUses(final DataFetchingEnvironment env,
            @GraphQLName("offerName") final String offerName,
            @GraphQLName("useName") final String useName) {
        Collection<OfferUse> offerUses;
        Collection<OfferUseObject> offerUseObjects;

        try {
            var commandForm = OfferUtil.getHome().getGetOfferUsesForm();

            commandForm.setOfferName(offerName);
            commandForm.setUseName(useName);

            offerUses = new GetOfferUsesCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        if(offerUses == null) {
            offerUseObjects = emptyList();
        } else {
            offerUseObjects = new ArrayList<>(offerUses.size());

            offerUses.stream()
                    .map(OfferUseObject::new)
                    .forEachOrdered(offerUseObjects::add);
        }

        return offerUseObjects;
    }

    @GraphQLField
    @GraphQLName("offer")
    public static OfferObject offer(final DataFetchingEnvironment env,
            @GraphQLName("offerName") final String offerName,
            @GraphQLName("id") @GraphQLID final String id) {
        Offer offer;

        try {
            var commandForm = OfferUtil.getHome().getGetOfferForm();

            commandForm.setOfferName(offerName);
            commandForm.setUlid(id);

            offer = new GetOfferCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return offer == null ? null : new OfferObject(offer);
    }

    @GraphQLField
    @GraphQLName("offers")
    public static Collection<OfferObject> offers(final DataFetchingEnvironment env) {
        Collection<Offer> offers;
        Collection<OfferObject> offerObjects;

        try {
            var commandForm = OfferUtil.getHome().getGetOffersForm();

            offers = new GetOffersCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        if(offers == null) {
            offerObjects = emptyList();
        } else {
            offerObjects = new ArrayList<>(offers.size());

            offers.stream()
                    .map(OfferObject::new)
                    .forEachOrdered(offerObjects::add);
        }

        return offerObjects;
    }

    @GraphQLField
    @GraphQLName("use")
    public static UseObject use(final DataFetchingEnvironment env,
            @GraphQLName("useName") final String useName,
            @GraphQLName("id") @GraphQLID final String id) {
        Use use;

        try {
            var commandForm = OfferUtil.getHome().getGetUseForm();

            commandForm.setUseName(useName);
            commandForm.setUlid(id);

            use = new GetUseCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return use == null ? null : new UseObject(use);
    }

    @GraphQLField
    @GraphQLName("uses")
    public static Collection<UseObject> uses(final DataFetchingEnvironment env) {
        Collection<Use> uses;
        Collection<UseObject> useObjects;

        try {
            var commandForm = OfferUtil.getHome().getGetUsesForm();

            uses = new GetUsesCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        if(uses == null) {
            useObjects = emptyList();
        } else {
            useObjects = new ArrayList<>(uses.size());

            uses.stream()
                    .map(UseObject::new)
                    .forEachOrdered(useObjects::add);
        }

        return useObjects;
    }

    @GraphQLField
    @GraphQLName("offerNameElement")
    public static OfferNameElementObject offerNameElement(final DataFetchingEnvironment env,
            @GraphQLName("offerNameElementName") final String offerNameElementName,
            @GraphQLName("id") @GraphQLID final String id) {
        OfferNameElement offerNameElement;

        try {
            var commandForm = OfferUtil.getHome().getGetOfferNameElementForm();

            commandForm.setOfferNameElementName(offerNameElementName);
            commandForm.setUlid(id);

            offerNameElement = new GetOfferNameElementCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return offerNameElement == null ? null : new OfferNameElementObject(offerNameElement);
    }

    @GraphQLField
    @GraphQLName("offerNameElements")
    public static Collection<OfferNameElementObject> offerNameElements(final DataFetchingEnvironment env) {
        Collection<OfferNameElement> offerNameElements;
        Collection<OfferNameElementObject> offerNameElementObjects;

        try {
            var commandForm = OfferUtil.getHome().getGetOfferNameElementsForm();

            offerNameElements = new GetOfferNameElementsCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        if(offerNameElements == null) {
            offerNameElementObjects = emptyList();
        } else {
            offerNameElementObjects = new ArrayList<>(offerNameElements.size());

            offerNameElements.stream()
                    .map(OfferNameElementObject::new)
                    .forEachOrdered(offerNameElementObjects::add);
        }

        return offerNameElementObjects;
    }

    @GraphQLField
    @GraphQLName("useNameElement")
    public static UseNameElementObject useNameElement(final DataFetchingEnvironment env,
            @GraphQLName("useNameElementName") final String useNameElementName,
            @GraphQLName("id") @GraphQLID final String id) {
        UseNameElement useNameElement;

        try {
            var commandForm = OfferUtil.getHome().getGetUseNameElementForm();

            commandForm.setUseNameElementName(useNameElementName);
            commandForm.setUlid(id);

            useNameElement = new GetUseNameElementCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return useNameElement == null ? null : new UseNameElementObject(useNameElement);
    }

    @GraphQLField
    @GraphQLName("useNameElements")
    public static Collection<UseNameElementObject> useNameElements(final DataFetchingEnvironment env) {
        Collection<UseNameElement> useNameElements;
        Collection<UseNameElementObject> useNameElementObjects;

        try {
            var commandForm = OfferUtil.getHome().getGetUseNameElementsForm();

            useNameElements = new GetUseNameElementsCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        if(useNameElements == null) {
            useNameElementObjects = emptyList();
        } else {
            useNameElementObjects = new ArrayList<>(useNameElements.size());

            useNameElements.stream()
                    .map(UseNameElementObject::new)
                    .forEachOrdered(useNameElementObjects::add);
        }

        return useNameElementObjects;
    }

    @GraphQLField
    @GraphQLName("useType")
    public static UseTypeObject useType(final DataFetchingEnvironment env,
            @GraphQLName("useTypeName") final String useTypeName,
            @GraphQLName("id") @GraphQLID final String id) {
        UseType useType;

        try {
            var commandForm = OfferUtil.getHome().getGetUseTypeForm();

            commandForm.setUseTypeName(useTypeName);
            commandForm.setUlid(id);

            useType = new GetUseTypeCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return useType == null ? null : new UseTypeObject(useType);
    }

    @GraphQLField
    @GraphQLName("useTypes")
    public static Collection<UseTypeObject> useTypes(final DataFetchingEnvironment env) {
        Collection<UseType> useTypes;
        Collection<UseTypeObject> useTypeObjects;

        try {
            var commandForm = OfferUtil.getHome().getGetUseTypesForm();

            useTypes = new GetUseTypesCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        if(useTypes == null) {
            useTypeObjects = emptyList();
        } else {
            useTypeObjects = new ArrayList<>(useTypes.size());

            useTypes.stream()
                    .map(UseTypeObject::new)
                    .forEachOrdered(useTypeObjects::add);
        }

        return useTypeObjects;
    }

    @GraphQLField
    @GraphQLName("freeOnBoard")
    public static FreeOnBoardObject freeOnBoard(final DataFetchingEnvironment env,
            @GraphQLName("freeOnBoardName") final String freeOnBoardName,
            @GraphQLName("id") @GraphQLID final String id) {
        FreeOnBoard freeOnBoard;

        try {
            var commandForm = ShipmentUtil.getHome().getGetFreeOnBoardForm();

            commandForm.setFreeOnBoardName(freeOnBoardName);
            commandForm.setUlid(id);

            freeOnBoard = new GetFreeOnBoardCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return freeOnBoard == null ? null : new FreeOnBoardObject(freeOnBoard);
    }

    @GraphQLField
    @GraphQLName("freeOnBoards")
    public static Collection<FreeOnBoardObject> freeOnBoards(final DataFetchingEnvironment env) {
        Collection<FreeOnBoard> freeOnBoards;
        Collection<FreeOnBoardObject> freeOnBoardObjects;

        try {
            var commandForm = ShipmentUtil.getHome().getGetFreeOnBoardsForm();

            freeOnBoards = new GetFreeOnBoardsCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        if(freeOnBoards == null) {
            freeOnBoardObjects = emptyList();
        } else {
            freeOnBoardObjects = new ArrayList<>(freeOnBoards.size());

            freeOnBoards.stream()
                    .map(FreeOnBoardObject::new)
                    .forEachOrdered(freeOnBoardObjects::add);
        }

        return freeOnBoardObjects;
    }

    @GraphQLField
    @GraphQLName("paymentProcessorTypeCodeType")
    public static PaymentProcessorTypeCodeTypeObject paymentProcessorTypeCodeType(final DataFetchingEnvironment env,
            @GraphQLName("paymentProcessorTypeName") final String paymentProcessorTypeName,
            @GraphQLName("paymentProcessorTypeCodeTypeName") final String paymentProcessorTypeCodeTypeName,
            @GraphQLName("id") @GraphQLID final String id) {
        PaymentProcessorTypeCodeType paymentProcessorTypeCodeType;

        try {
            var commandForm = PaymentUtil.getHome().getGetPaymentProcessorTypeCodeTypeForm();

            commandForm.setPaymentProcessorTypeName(paymentProcessorTypeName);
            commandForm.setPaymentProcessorTypeCodeTypeName(paymentProcessorTypeCodeTypeName);
            commandForm.setUlid(id);

            paymentProcessorTypeCodeType = new GetPaymentProcessorTypeCodeTypeCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return paymentProcessorTypeCodeType == null ? null : new PaymentProcessorTypeCodeTypeObject(paymentProcessorTypeCodeType);
    }

    @GraphQLField
    @GraphQLName("paymentProcessorTypeCode")
    public static PaymentProcessorTypeCodeObject paymentProcessorTypeCode(final DataFetchingEnvironment env,
            @GraphQLName("paymentProcessorTypeName") final String paymentProcessorTypeName,
            @GraphQLName("paymentProcessorTypeCodeTypeName") final String paymentProcessorTypeCodeTypeName,
            @GraphQLName("paymentProcessorTypeCodeName") final String paymentProcessorTypeCodeName,
            @GraphQLName("id") @GraphQLID final String id) {
        PaymentProcessorTypeCode paymentProcessorTypeCode;

        try {
            var commandForm = PaymentUtil.getHome().getGetPaymentProcessorTypeCodeForm();

            commandForm.setPaymentProcessorTypeName(paymentProcessorTypeName);
            commandForm.setPaymentProcessorTypeCodeTypeName(paymentProcessorTypeCodeTypeName);
            commandForm.setPaymentProcessorTypeCodeName(paymentProcessorTypeCodeName);
            commandForm.setUlid(id);

            paymentProcessorTypeCode = new GetPaymentProcessorTypeCodeCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return paymentProcessorTypeCode == null ? null : new PaymentProcessorTypeCodeObject(paymentProcessorTypeCode);
    }

    @GraphQLField
    @GraphQLName("paymentProcessorTransaction")
    public static PaymentProcessorTransactionObject paymentProcessorTransaction(final DataFetchingEnvironment env,
            @GraphQLName("paymentProcessorTransactionName") final String paymentProcessorTransactionName,
            @GraphQLName("id") @GraphQLID final String id) {
        PaymentProcessorTransaction paymentProcessorTransaction;

        try {
            var commandForm = PaymentUtil.getHome().getGetPaymentProcessorTransactionForm();

            commandForm.setPaymentProcessorTransactionName(paymentProcessorTransactionName);
            commandForm.setUlid(id);

            paymentProcessorTransaction = new GetPaymentProcessorTransactionCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return paymentProcessorTransaction == null ? null : new PaymentProcessorTransactionObject(paymentProcessorTransaction);
    }

    @GraphQLField
    @GraphQLName("paymentProcessorTransactions")
    public static Collection<PaymentProcessorTransactionObject> paymentProcessorTransactions(final DataFetchingEnvironment env) {
        Collection<PaymentProcessorTransaction> paymentProcessorTransactions;
        Collection<PaymentProcessorTransactionObject> paymentProcessorTransactionObjects;

        try {
            var commandForm = PaymentUtil.getHome().getGetPaymentProcessorTransactionsForm();

            paymentProcessorTransactions = new GetPaymentProcessorTransactionsCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        if(paymentProcessorTransactions == null) {
            paymentProcessorTransactionObjects = emptyList();
        } else {
            paymentProcessorTransactionObjects = new ArrayList<>(paymentProcessorTransactions.size());

            paymentProcessorTransactions.stream()
                    .map(PaymentProcessorTransactionObject::new)
                    .forEachOrdered(paymentProcessorTransactionObjects::add);
        }

        return paymentProcessorTransactionObjects;
    }

    @GraphQLField
    @GraphQLName("paymentProcessor")
    public static PaymentProcessorObject paymentProcessor(final DataFetchingEnvironment env,
            @GraphQLName("paymentProcessorName") final String paymentProcessorName,
            @GraphQLName("id") @GraphQLID final String id) {
        PaymentProcessor paymentProcessor;

        try {
            var commandForm = PaymentUtil.getHome().getGetPaymentProcessorForm();

            commandForm.setPaymentProcessorName(paymentProcessorName);
            commandForm.setUlid(id);

            paymentProcessor = new GetPaymentProcessorCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return paymentProcessor == null ? null : new PaymentProcessorObject(paymentProcessor);
    }

    @GraphQLField
    @GraphQLName("paymentProcessors")
    public static Collection<PaymentProcessorObject> paymentProcessors(final DataFetchingEnvironment env) {
        Collection<PaymentProcessor> paymentProcessors;
        Collection<PaymentProcessorObject> paymentProcessorObjects;

        try {
            var commandForm = PaymentUtil.getHome().getGetPaymentProcessorsForm();

            paymentProcessors = new GetPaymentProcessorsCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        if(paymentProcessors == null) {
            paymentProcessorObjects = emptyList();
        } else {
            paymentProcessorObjects = new ArrayList<>(paymentProcessors.size());

            paymentProcessors.stream()
                    .map(PaymentProcessorObject::new)
                    .forEachOrdered(paymentProcessorObjects::add);
        }

        return paymentProcessorObjects;
    }

    @GraphQLField
    @GraphQLName("paymentProcessorType")
    public static PaymentProcessorTypeObject paymentProcessorType(final DataFetchingEnvironment env,
            @GraphQLName("paymentProcessorTypeName") final String paymentProcessorTypeName,
            @GraphQLName("id") @GraphQLID final String id) {
        PaymentProcessorType paymentProcessorType;

        try {
            var commandForm = PaymentUtil.getHome().getGetPaymentProcessorTypeForm();

            commandForm.setPaymentProcessorTypeName(paymentProcessorTypeName);
            commandForm.setUlid(id);

            paymentProcessorType = new GetPaymentProcessorTypeCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return paymentProcessorType == null ? null : new PaymentProcessorTypeObject(paymentProcessorType);
    }

    @GraphQLField
    @GraphQLName("paymentProcessorTypes")
    public static Collection<PaymentProcessorTypeObject> paymentProcessorTypes(final DataFetchingEnvironment env) {
        Collection<PaymentProcessorType> paymentProcessorTypes;
        Collection<PaymentProcessorTypeObject> paymentProcessorTypeObjects;

        try {
            var commandForm = PaymentUtil.getHome().getGetPaymentProcessorTypesForm();

            paymentProcessorTypes = new GetPaymentProcessorTypesCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        if(paymentProcessorTypes == null) {
            paymentProcessorTypeObjects = emptyList();
        } else {
            paymentProcessorTypeObjects = new ArrayList<>(paymentProcessorTypes.size());

            paymentProcessorTypes.stream()
                    .map(PaymentProcessorTypeObject::new)
                    .forEachOrdered(paymentProcessorTypeObjects::add);
        }

        return paymentProcessorTypeObjects;
    }

    @GraphQLField
    @GraphQLName("paymentMethodType")
    public static PaymentMethodTypeObject paymentMethodType(final DataFetchingEnvironment env,
            @GraphQLName("paymentMethodTypeName") final String paymentMethodTypeName,
            @GraphQLName("id") @GraphQLID final String id) {
        PaymentMethodType paymentMethodType;

        try {
            var commandForm = PaymentUtil.getHome().getGetPaymentMethodTypeForm();

            commandForm.setPaymentMethodTypeName(paymentMethodTypeName);
            commandForm.setUlid(id);

            paymentMethodType = new GetPaymentMethodTypeCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return paymentMethodType == null ? null : new PaymentMethodTypeObject(paymentMethodType);
    }

    @GraphQLField
    @GraphQLName("paymentMethodTypes")
    public static Collection<PaymentMethodTypeObject> paymentMethodTypes(final DataFetchingEnvironment env) {
        Collection<PaymentMethodType> paymentMethodTypes;
        Collection<PaymentMethodTypeObject> paymentMethodTypeObjects;

        try {
            var commandForm = PaymentUtil.getHome().getGetPaymentMethodTypesForm();

            paymentMethodTypes = new GetPaymentMethodTypesCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        if(paymentMethodTypes == null) {
            paymentMethodTypeObjects = emptyList();
        } else {
            paymentMethodTypeObjects = new ArrayList<>(paymentMethodTypes.size());

            paymentMethodTypes.stream()
                    .map(PaymentMethodTypeObject::new)
                    .forEachOrdered(paymentMethodTypeObjects::add);
        }

        return paymentMethodTypeObjects;
    }

    @GraphQLField
    @GraphQLName("paymentProcessorResultCode")
    public static PaymentProcessorResultCodeObject paymentProcessorResultCode(final DataFetchingEnvironment env,
            @GraphQLName("paymentProcessorResultCodeName") final String paymentProcessorResultCodeName,
            @GraphQLName("id") @GraphQLID final String id) {
        PaymentProcessorResultCode paymentProcessorResultCode;

        try {
            var commandForm = PaymentUtil.getHome().getGetPaymentProcessorResultCodeForm();

            commandForm.setPaymentProcessorResultCodeName(paymentProcessorResultCodeName);
            commandForm.setUlid(id);

            paymentProcessorResultCode = new GetPaymentProcessorResultCodeCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return paymentProcessorResultCode == null ? null : new PaymentProcessorResultCodeObject(paymentProcessorResultCode);
    }

    @GraphQLField
    @GraphQLName("paymentProcessorResultCodes")
    public static Collection<PaymentProcessorResultCodeObject> paymentProcessorResultCodes(final DataFetchingEnvironment env) {
        Collection<PaymentProcessorResultCode> paymentProcessorResultCodes;
        Collection<PaymentProcessorResultCodeObject> paymentProcessorResultCodeObjects;

        try {
            var commandForm = PaymentUtil.getHome().getGetPaymentProcessorResultCodesForm();

            paymentProcessorResultCodes = new GetPaymentProcessorResultCodesCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        if(paymentProcessorResultCodes == null) {
            paymentProcessorResultCodeObjects = emptyList();
        } else {
            paymentProcessorResultCodeObjects = new ArrayList<>(paymentProcessorResultCodes.size());

            paymentProcessorResultCodes.stream()
                    .map(PaymentProcessorResultCodeObject::new)
                    .forEachOrdered(paymentProcessorResultCodeObjects::add);
        }

        return paymentProcessorResultCodeObjects;
    }

    @GraphQLField
    @GraphQLName("paymentProcessorActionType")
    public static PaymentProcessorActionTypeObject paymentProcessorActionType(final DataFetchingEnvironment env,
            @GraphQLName("paymentProcessorActionTypeName") final String paymentProcessorActionTypeName,
            @GraphQLName("id") @GraphQLID final String id) {
        PaymentProcessorActionType paymentProcessorActionType;

        try {
            var commandForm = PaymentUtil.getHome().getGetPaymentProcessorActionTypeForm();

            commandForm.setPaymentProcessorActionTypeName(paymentProcessorActionTypeName);
            commandForm.setUlid(id);

            paymentProcessorActionType = new GetPaymentProcessorActionTypeCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return paymentProcessorActionType == null ? null : new PaymentProcessorActionTypeObject(paymentProcessorActionType);
    }

    @GraphQLField
    @GraphQLName("paymentProcessorActionTypes")
    public static Collection<PaymentProcessorActionTypeObject> paymentProcessorActionTypes(final DataFetchingEnvironment env) {
        Collection<PaymentProcessorActionType> paymentProcessorActionTypes;
        Collection<PaymentProcessorActionTypeObject> paymentProcessorActionTypeObjects;

        try {
            var commandForm = PaymentUtil.getHome().getGetPaymentProcessorActionTypesForm();

            paymentProcessorActionTypes = new GetPaymentProcessorActionTypesCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        if(paymentProcessorActionTypes == null) {
            paymentProcessorActionTypeObjects = emptyList();
        } else {
            paymentProcessorActionTypeObjects = new ArrayList<>(paymentProcessorActionTypes.size());

            paymentProcessorActionTypes.stream()
                    .map(PaymentProcessorActionTypeObject::new)
                    .forEachOrdered(paymentProcessorActionTypeObjects::add);
        }

        return paymentProcessorActionTypeObjects;
    }

    @GraphQLField
    @GraphQLName("appearance")
    public static AppearanceObject appearance(final DataFetchingEnvironment env,
            @GraphQLName("appearanceName") final String appearanceName,
            @GraphQLName("id") @GraphQLID final String id) {
        Appearance appearance;

        try {
            var commandForm = CoreUtil.getHome().getGetAppearanceForm();

            commandForm.setAppearanceName(appearanceName);
            commandForm.setUlid(id);

            appearance = new GetAppearanceCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return appearance == null ? null : new AppearanceObject(appearance);
    }

    @GraphQLField
    @GraphQLName("appearances")
    public static Collection<AppearanceObject> appearances(final DataFetchingEnvironment env) {
        Collection<Appearance> appearances;
        Collection<AppearanceObject> appearanceObjects;

        try {
            var commandForm = CoreUtil.getHome().getGetAppearancesForm();

            appearances = new GetAppearancesCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        if(appearances == null) {
            appearanceObjects = emptyList();
        } else {
            appearanceObjects = new ArrayList<>(appearances.size());

            appearances.stream().map(AppearanceObject::new).forEachOrdered(appearanceObjects::add);
        }

        return appearanceObjects;
    }

    @GraphQLField
    @GraphQLName("entityInstance")
    public static EntityInstanceObject entityInstance(final DataFetchingEnvironment env,
            @GraphQLName("id") @GraphQLID final String id) {
        EntityInstance entityInstance;

        try {
            var commandForm = CoreUtil.getHome().getGetEntityInstanceForm();

            commandForm.setUlid(id);

            entityInstance = new GetEntityInstanceCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return entityInstance == null ? null : new EntityInstanceObject(entityInstance);
    }

    @GraphQLField
    @GraphQLName("entityInstances")
    public static Collection<EntityInstanceObject> entityInstances(final DataFetchingEnvironment env,
            @GraphQLName("componentVendorName") final String componentVendorName,
            @GraphQLName("entityTypeName") final String entityTypeName) {
        Collection<EntityInstance> entityInstances;
        Collection<EntityInstanceObject> entityInstanceObjects;

        try {
            var commandForm = CoreUtil.getHome().getGetEntityInstancesForm();

            commandForm.setComponentVendorName(componentVendorName);
            commandForm.setEntityTypeName(entityTypeName);

            entityInstances = new GetEntityInstancesCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        if(entityInstances == null) {
            entityInstanceObjects = emptyList();
        } else {
            entityInstanceObjects = new ArrayList<>(entityInstances.size());

            entityInstances.stream().map(EntityInstanceObject::new).forEachOrdered(entityInstanceObjects::add);
        }

        return entityInstanceObjects;
    }

    @GraphQLField
    @GraphQLName("entityType")
    public static EntityTypeObject entityType(final DataFetchingEnvironment env,
            @GraphQLName("componentVendorName") final String componentVendorName,
            @GraphQLName("entityTypeName") final String entityTypeName,
            @GraphQLName("id") @GraphQLID final String id) {
        EntityType entityType;

        try {
            var commandForm = CoreUtil.getHome().getGetEntityTypeForm();

            commandForm.setComponentVendorName(componentVendorName);
            commandForm.setEntityTypeName(entityTypeName);
            commandForm.setUlid(id);

            entityType = new GetEntityTypeCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return entityType == null ? null : new EntityTypeObject(entityType);
    }

    @GraphQLField
    @GraphQLName("entityTypes")
    public static Collection<EntityTypeObject> entityTypes(final DataFetchingEnvironment env,
            @GraphQLName("componentVendorName") final String componentVendorName) {
        Collection<EntityType> entityTypes;
        Collection<EntityTypeObject> entityTypeObjects;

        try {
            var commandForm = CoreUtil.getHome().getGetEntityTypesForm();

            commandForm.setComponentVendorName(componentVendorName);

            entityTypes = new GetEntityTypesCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        if(entityTypes == null) {
            entityTypeObjects = emptyList();
        } else {
            entityTypeObjects = new ArrayList<>(entityTypes.size());

            entityTypes.stream().map(EntityTypeObject::new).forEachOrdered(entityTypeObjects::add);
        }

        return entityTypeObjects;
    }

    @GraphQLField
    @GraphQLName("componentVendor")
    public static ComponentVendorObject componentVendor(final DataFetchingEnvironment env,
            @GraphQLName("componentVendorName") final String componentVendorName,
            @GraphQLName("id") @GraphQLID final String id) {
        ComponentVendor componentVendor;

        try {
            var commandForm = CoreUtil.getHome().getGetComponentVendorForm();

            commandForm.setComponentVendorName(componentVendorName);
            commandForm.setUlid(id);

            componentVendor = new GetComponentVendorCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return componentVendor == null ? null : new ComponentVendorObject(componentVendor);
    }

    @GraphQLField
    @GraphQLName("componentVendors")
    public static Collection<ComponentVendorObject> componentVendors(final DataFetchingEnvironment env) {
        Collection<ComponentVendor> componentVendors;
        Collection<ComponentVendorObject> componentVendorObjects;

        try {
            var commandForm = CoreUtil.getHome().getGetComponentVendorsForm();

            componentVendors = new GetComponentVendorsCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        if(componentVendors == null) {
            componentVendorObjects = emptyList();
        } else {
            componentVendorObjects = new ArrayList<>(componentVendors.size());

            componentVendors.stream().map(ComponentVendorObject::new).forEachOrdered(componentVendorObjects::add);
        }

        return componentVendorObjects;
    }

    @GraphQLField
    @GraphQLName("inventoryCondition")
    public static InventoryConditionObject inventoryCondition(final DataFetchingEnvironment env,
            @GraphQLName("inventoryConditionName") final String inventoryConditionName,
            @GraphQLName("id") @GraphQLID final String id) {
        InventoryCondition inventoryCondition;

        try {
            var commandForm = InventoryUtil.getHome().getGetInventoryConditionForm();

            commandForm.setInventoryConditionName(inventoryConditionName);
            commandForm.setUlid(id);

            inventoryCondition = new GetInventoryConditionCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return inventoryCondition == null ? null : new InventoryConditionObject(inventoryCondition);
    }

    @GraphQLField
    @GraphQLName("inventoryConditions")
    public static Collection<InventoryConditionObject> inventoryConditions(final DataFetchingEnvironment env) {
        Collection<InventoryCondition> inventoryConditions;
        Collection<InventoryConditionObject> inventoryConditionObjects;

        try {
            var commandForm = InventoryUtil.getHome().getGetInventoryConditionsForm();

            inventoryConditions = new GetInventoryConditionsCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        if(inventoryConditions == null) {
            inventoryConditionObjects = emptyList();
        } else {
            inventoryConditionObjects = new ArrayList<>(inventoryConditions.size());

            inventoryConditions.stream()
                    .map(InventoryConditionObject::new)
                    .forEachOrdered(inventoryConditionObjects::add);
        }

        return inventoryConditionObjects;
    }

    @GraphQLField
    @GraphQLName("lot")
    public static LotObject lot(final DataFetchingEnvironment env,
            @GraphQLName("lotName") final String lotName,
            @GraphQLName("id") @GraphQLID final String id) {
        Lot lot;

        try {
            var commandForm = InventoryUtil.getHome().getGetLotForm();

            commandForm.setLotName(lotName);
            commandForm.setUlid(id);

            lot = new GetLotCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return lot == null ? null : new LotObject(lot);
    }

    @GraphQLField
    @GraphQLName("lots")
    public static Collection<LotObject> lots(final DataFetchingEnvironment env) {
        Collection<Lot> lots;
        Collection<LotObject> lotObjects;

        try {
            var commandForm = InventoryUtil.getHome().getGetLotsForm();

            lots = new GetLotsCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        if(lots == null) {
            lotObjects = emptyList();
        } else {
            lotObjects = new ArrayList<>(lots.size());

            lots.stream()
                    .map(LotObject::new)
                    .forEachOrdered(lotObjects::add);
        }

        return lotObjects;
    }

    @GraphQLField
    @GraphQLName("contentPageLayout")
    public static ContentPageLayoutObject contentPageLayout(final DataFetchingEnvironment env,
            @GraphQLName("contentPageLayoutName") final String contentPageLayoutName,
            @GraphQLName("id") @GraphQLID final String id) {
        ContentPageLayout contentPageLayout;

        try {
            var commandForm = ContentUtil.getHome().getGetContentPageLayoutForm();

            commandForm.setContentPageLayoutName(contentPageLayoutName);
            commandForm.setUlid(id);
        
            contentPageLayout = new GetContentPageLayoutCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
        
        return contentPageLayout == null ? null : new ContentPageLayoutObject(contentPageLayout);
    }

    @GraphQLField
    @GraphQLName("contentPageLayouts")
    public static Collection<ContentPageLayoutObject> contentPageLayouts(final DataFetchingEnvironment env) {
        Collection<ContentPageLayout> contentPageLayouts;
        Collection<ContentPageLayoutObject> contentPageLayoutObjects;
        
        try {
            var commandForm = ContentUtil.getHome().getGetContentPageLayoutsForm();
        
            contentPageLayouts = new GetContentPageLayoutsCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
        
        if(contentPageLayouts == null) {
            contentPageLayoutObjects = emptyList();
        } else {
            contentPageLayoutObjects = new ArrayList<>(contentPageLayouts.size());

            contentPageLayouts.stream()
                    .map(ContentPageLayoutObject::new)
                    .forEachOrdered(contentPageLayoutObjects::add);
        }
        
        return contentPageLayoutObjects;
    }

    @GraphQLField
    @GraphQLName("contentPageLayoutArea")
    public static ContentPageLayoutAreaObject contentPageLayoutArea(final DataFetchingEnvironment env,
            @GraphQLName("contentCollectionName") @GraphQLNonNull final String contentCollectionName,
            @GraphQLName("contentSectionName") @GraphQLNonNull final String contentSectionName,
            @GraphQLName("contentPageName") @GraphQLNonNull final String contentPageName,
            @GraphQLName("sortOrder") @GraphQLNonNull final String sortOrder) {
        ContentPageLayoutArea contentPageLayoutArea;

        try {
            var commandForm = ContentUtil.getHome().getGetContentPageLayoutAreaForm();

            commandForm.setContentCollectionName(contentCollectionName);
            commandForm.setContentSectionName(contentSectionName);
            commandForm.setContentPageName(contentPageName);
            commandForm.setSortOrder(sortOrder);
        
            contentPageLayoutArea = new GetContentPageLayoutAreaCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
        
        return contentPageLayoutArea == null ? null : new ContentPageLayoutAreaObject(contentPageLayoutArea);
    }

    @GraphQLField
    @GraphQLName("contentPageLayoutAreas")
    public static Collection<ContentPageLayoutAreaObject> contentPageLayoutAreas(final DataFetchingEnvironment env,
            @GraphQLName("contentCollectionName") @GraphQLNonNull final String contentCollectionName,
            @GraphQLName("contentSectionName") @GraphQLNonNull final String contentSectionName,
            @GraphQLName("contentPageName") @GraphQLNonNull final String contentPageName) {
        Collection<ContentPageLayoutArea> contentPageLayoutAreas;
        Collection<ContentPageLayoutAreaObject> contentPageLayoutAreaObjects;
        
        try {
            var commandForm = ContentUtil.getHome().getGetContentPageLayoutAreasForm();

            commandForm.setContentCollectionName(contentCollectionName);
            commandForm.setContentSectionName(contentSectionName);
            commandForm.setContentPageName(contentPageName);
            
            contentPageLayoutAreas = new GetContentPageLayoutAreasCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
        
        if(contentPageLayoutAreas == null) {
            contentPageLayoutAreaObjects = emptyList();
        } else {
            contentPageLayoutAreaObjects = new ArrayList<>(contentPageLayoutAreas.size());

            contentPageLayoutAreas.stream()
                    .map(ContentPageLayoutAreaObject::new)
                    .forEachOrdered(contentPageLayoutAreaObjects::add);
        }
        
        return contentPageLayoutAreaObjects;
    }

    @GraphQLField
    @GraphQLName("contentPageAreaType")
    public static ContentPageAreaTypeObject contentPageAreaType(final DataFetchingEnvironment env,
            @GraphQLName("contentPageAreaTypeName") final String contentPageAreaTypeName,
            @GraphQLName("id") @GraphQLID final String id) {
        ContentPageAreaType contentPageAreaType;

        try {
            var commandForm = ContentUtil.getHome().getGetContentPageAreaTypeForm();

            commandForm.setContentPageAreaTypeName(contentPageAreaTypeName);
            commandForm.setUlid(id);
        
            contentPageAreaType = new GetContentPageAreaTypeCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
        
        return contentPageAreaType == null ? null : new ContentPageAreaTypeObject(contentPageAreaType);
    }

    @GraphQLField
    @GraphQLName("contentPageAreaTypes")
    public static Collection<ContentPageAreaTypeObject> contentPageAreaTypes(final DataFetchingEnvironment env) {
        Collection<ContentPageAreaType> contentPageAreaTypes;
        Collection<ContentPageAreaTypeObject> contentPageAreaTypeObjects;
        
        try {
            var commandForm = ContentUtil.getHome().getGetContentPageAreaTypesForm();
        
            contentPageAreaTypes = new GetContentPageAreaTypesCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
        
        if(contentPageAreaTypes == null) {
            contentPageAreaTypeObjects = emptyList();
        } else {
            contentPageAreaTypeObjects = new ArrayList<>(contentPageAreaTypes.size());

            contentPageAreaTypes.stream()
                    .map(ContentPageAreaTypeObject::new)
                    .forEachOrdered(contentPageAreaTypeObjects::add);
        }
        
        return contentPageAreaTypeObjects;
    }

    @GraphQLField
    @GraphQLName("contentWebAddress")
    public static ContentWebAddressObject contentWebAddress(final DataFetchingEnvironment env,
            @GraphQLName("contentWebAddressName") @GraphQLNonNull final String contentWebAddressName) {
        ContentWebAddress contentWebAddress;

        try {
            var commandForm = ContentUtil.getHome().getGetContentWebAddressForm();

            commandForm.setContentWebAddressName(contentWebAddressName);
        
            contentWebAddress = new GetContentWebAddressCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
        
        return contentWebAddress == null ? null : new ContentWebAddressObject(contentWebAddress);
    }

    @GraphQLField
    @GraphQLName("contentWebAddresses")
    public static Collection<ContentWebAddressObject> contentWebAddresses(final DataFetchingEnvironment env) {
        Collection<ContentWebAddress> contentWebAddresses;
        Collection<ContentWebAddressObject> contentWebAddressObjects;
        
        try {
            var commandForm = ContentUtil.getHome().getGetContentWebAddressesForm();
        
            contentWebAddresses = new GetContentWebAddressesCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
        
        if(contentWebAddresses == null) {
            contentWebAddressObjects = emptyList();
        } else {
            contentWebAddressObjects = new ArrayList<>(contentWebAddresses.size());

            contentWebAddresses.stream()
                    .map(ContentWebAddressObject::new)
                    .forEachOrdered(contentWebAddressObjects::add);
        }
        
        return contentWebAddressObjects;
    }

    @GraphQLField
    @GraphQLName("contentCollection")
    public static ContentCollectionObject contentCollection(final DataFetchingEnvironment env,
            @GraphQLName("contentCollectionName") final String contentCollectionName) {
        ContentCollection contentCollection;

        try {
            var commandForm = ContentUtil.getHome().getGetContentCollectionForm();

            commandForm.setContentCollectionName(contentCollectionName);

            contentCollection = new GetContentCollectionCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
        
        return contentCollection == null ? null : new ContentCollectionObject(contentCollection);
    }

    @GraphQLField
    @GraphQLName("contentCollections")
    public static Collection<ContentCollectionObject> contentCollections(final DataFetchingEnvironment env) {
        Collection<ContentCollection> contentCollections;
        Collection<ContentCollectionObject> contentCollectionObjects;
        
        try {
            var commandForm = ContentUtil.getHome().getGetContentCollectionsForm();
        
            contentCollections = new GetContentCollectionsCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
        
        if(contentCollections == null) {
            contentCollectionObjects = emptyList();
        } else {
            contentCollectionObjects = new ArrayList<>(contentCollections.size());

            contentCollections.stream()
                    .map(ContentCollectionObject::new)
                    .forEachOrdered(contentCollectionObjects::add);
        }
        
        return contentCollectionObjects;
    }

    @GraphQLField
    @GraphQLName("contentSection")
    public static ContentSectionObject contentSection(final DataFetchingEnvironment env,
            @GraphQLName("contentWebAddressName") final String contentWebAddressName,
            @GraphQLName("contentCollectionName") final String contentCollectionName,
            @GraphQLName("contentSectionName") final String contentSectionName,
            @GraphQLName("associateProgramName") final String associateProgramName,
            @GraphQLName("associateName") final String associateName,
            @GraphQLName("associatePartyContactMechanismName") final String associatePartyContactMechanismName) {
        ContentSection contentSection;

        try {
            var commandForm = ContentUtil.getHome().getGetContentSectionForm();

            commandForm.setContentWebAddressName(contentWebAddressName);
            commandForm.setContentCollectionName(contentCollectionName);
            commandForm.setContentSectionName(contentSectionName);
            commandForm.setAssociateProgramName(associateProgramName);
            commandForm.setAssociateName(associateName);
            commandForm.setAssociatePartyContactMechanismName(associatePartyContactMechanismName);

            contentSection = new GetContentSectionCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
        
        return contentSection == null ? null : new ContentSectionObject(contentSection);
    }

    @GraphQLField
    @GraphQLName("contentSections")
    public static Collection<ContentSectionObject> contentSections(final DataFetchingEnvironment env,
            @GraphQLName("contentWebAddressName") final String contentWebAddressName,
            @GraphQLName("contentCollectionName") final String contentCollectionName,
            @GraphQLName("parentContentSectionName") final String parentContentSectionName,
            @GraphQLName("associateProgramName") final String associateProgramName,
            @GraphQLName("associateName") final String associateName,
            @GraphQLName("associatePartyContactMechanismName") final String associatePartyContactMechanismName) {
        Collection<ContentSection> contentSections;
        Collection<ContentSectionObject> contentSectionObjects;
        
        try {
            var commandForm = ContentUtil.getHome().getGetContentSectionsForm();
        
            commandForm.setContentWebAddressName(contentWebAddressName);
            commandForm.setContentCollectionName(contentCollectionName);
            commandForm.setParentContentSectionName(parentContentSectionName);
            commandForm.setAssociateProgramName(associateProgramName);
            commandForm.setAssociateName(associateName);
            commandForm.setAssociatePartyContactMechanismName(associatePartyContactMechanismName);

            contentSections = new GetContentSectionsCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
        
        if(contentSections == null) {
            contentSectionObjects = emptyList();
        } else {
            contentSectionObjects = new ArrayList<>(contentSections.size());

            contentSections.stream()
                    .map(ContentSectionObject::new)
                    .forEachOrdered(contentSectionObjects::add);
        }
        
        return contentSectionObjects;
    }

    @GraphQLField
    @GraphQLName("contentPage")
    public static ContentPageObject contentPage(final DataFetchingEnvironment env,
            @GraphQLName("contentWebAddressName") final String contentWebAddressName,
            @GraphQLName("contentCollectionName") final String contentCollectionName,
            @GraphQLName("contentSectionName") final String contentSectionName,
            @GraphQLName("contentPageName") final String contentPageName,
            @GraphQLName("associateProgramName") final String associateProgramName,
            @GraphQLName("associateName") final String associateName,
            @GraphQLName("associatePartyContactMechanismName") final String associatePartyContactMechanismName) {
        ContentPage contentPage;

        try {
            var commandForm = ContentUtil.getHome().getGetContentPageForm();

            commandForm.setContentWebAddressName(contentWebAddressName);
            commandForm.setContentCollectionName(contentCollectionName);
            commandForm.setContentSectionName(contentSectionName);
            commandForm.setContentPageName(contentPageName);
            commandForm.setAssociateProgramName(associateProgramName);
            commandForm.setAssociateName(associateName);
            commandForm.setAssociatePartyContactMechanismName(associatePartyContactMechanismName);

            contentPage = new GetContentPageCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
        
        return contentPage == null ? null : new ContentPageObject(contentPage);
    }

    @GraphQLField
    @GraphQLName("contentPages")
    public static Collection<ContentPageObject> contentPages(final DataFetchingEnvironment env,
            @GraphQLName("contentWebAddressName") final String contentWebAddressName,
            @GraphQLName("contentCollectionName") final String contentCollectionName,
            @GraphQLName("contentSectionName") final String contentSectionName,
            @GraphQLName("associateProgramName") final String associateProgramName,
            @GraphQLName("associateName") final String associateName,
            @GraphQLName("associatePartyContactMechanismName") final String associatePartyContactMechanismName) {
        Collection<ContentPage> contentPages;
        Collection<ContentPageObject> contentPageObjects;
        
        try {
            var commandForm = ContentUtil.getHome().getGetContentPagesForm();
        
            commandForm.setContentWebAddressName(contentWebAddressName);
            commandForm.setContentCollectionName(contentCollectionName);
            commandForm.setContentSectionName(contentSectionName);
            commandForm.setAssociateProgramName(associateProgramName);
            commandForm.setAssociateName(associateName);
            commandForm.setAssociatePartyContactMechanismName(associatePartyContactMechanismName);

            contentPages = new GetContentPagesCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
        
        if(contentPages == null) {
            contentPageObjects = emptyList();
        } else {
            contentPageObjects = new ArrayList<>(contentPages.size());

            contentPages.stream()
                    .map(ContentPageObject::new)
                    .forEachOrdered(contentPageObjects::add);
        }
        
        return contentPageObjects;
    }

    @GraphQLField
    @GraphQLName("contentPageArea")
    public static ContentPageAreaObject contentPageArea(final DataFetchingEnvironment env,
            @GraphQLName("contentCollectionName") @GraphQLNonNull final String contentCollectionName,
            @GraphQLName("contentSectionName") @GraphQLNonNull final String contentSectionName,
            @GraphQLName("contentPageName") @GraphQLNonNull final String contentPageName,
            @GraphQLName("sortOrder") @GraphQLNonNull final String sortOrder,
            @GraphQLName("languageIsoName") @GraphQLNonNull final String languageIsoName) {
        ContentPageArea contentPageArea;

        try {
            var commandForm = ContentUtil.getHome().getGetContentPageAreaForm();

            commandForm.setContentCollectionName(contentCollectionName);
            commandForm.setContentSectionName(contentSectionName);
            commandForm.setContentPageName(contentPageName);
            commandForm.setSortOrder(sortOrder);
            commandForm.setLanguageIsoName(languageIsoName);

            contentPageArea = new GetContentPageAreaCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
        
        return contentPageArea == null ? null : new ContentPageAreaObject(contentPageArea);
    }

    @GraphQLField
    @GraphQLName("contentPageAreas")
    public static Collection<ContentPageAreaObject> contentPageAreas(final DataFetchingEnvironment env,
            @GraphQLName("contentCollectionName") @GraphQLNonNull final String contentCollectionName,
            @GraphQLName("contentSectionName") @GraphQLNonNull final String contentSectionName,
            @GraphQLName("contentPageName") @GraphQLNonNull final String contentPageName) {
        Collection<ContentPageArea> contentPageAreas;
        Collection<ContentPageAreaObject> contentPageAreaObjects;
        
        try {
            var commandForm = ContentUtil.getHome().getGetContentPageAreasForm();
        
            commandForm.setContentCollectionName(contentCollectionName);
            commandForm.setContentSectionName(contentSectionName);
            commandForm.setContentPageName(contentPageName);

            contentPageAreas = new GetContentPageAreasCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
        
        if(contentPageAreas == null) {
            contentPageAreaObjects = emptyList();
        } else {
            contentPageAreaObjects = new ArrayList<>(contentPageAreas.size());

            contentPageAreas.stream()
                    .map(ContentPageAreaObject::new)
                    .forEachOrdered(contentPageAreaObjects::add);
        }
        
        return contentPageAreaObjects;
    }

    @GraphQLField
    @GraphQLName("contentCatalog")
    public static ContentCatalogObject contentCatalog(final DataFetchingEnvironment env,
            @GraphQLName("contentWebAddressName") final String contentWebAddressName,
            @GraphQLName("contentCollectionName") final String contentCollectionName,
            @GraphQLName("contentCatalogName") final String contentCatalogName,
            @GraphQLName("associateProgramName") final String associateProgramName,
            @GraphQLName("associateName") final String associateName,
            @GraphQLName("associatePartyContactMechanismName") final String associatePartyContactMechanismName) {
        ContentCatalog contentCatalog;

        try {
            var commandForm = ContentUtil.getHome().getGetContentCatalogForm();

            commandForm.setContentWebAddressName(contentWebAddressName);
            commandForm.setContentCollectionName(contentCollectionName);
            commandForm.setContentCatalogName(contentCatalogName);
            commandForm.setAssociateProgramName(associateProgramName);
            commandForm.setAssociateName(associateName);
            commandForm.setAssociatePartyContactMechanismName(associatePartyContactMechanismName);

            contentCatalog = new GetContentCatalogCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
        
        return contentCatalog == null ? null : new ContentCatalogObject(contentCatalog);
    }

    @GraphQLField
    @GraphQLName("contentCatalogs")
    public static Collection<ContentCatalogObject> contentCatalogs(final DataFetchingEnvironment env,
            @GraphQLName("contentWebAddressName") final String contentWebAddressName,
            @GraphQLName("contentCollectionName") final String contentCollectionName,
            @GraphQLName("associateProgramName") final String associateProgramName,
            @GraphQLName("associateName") final String associateName,
            @GraphQLName("associatePartyContactMechanismName") final String associatePartyContactMechanismName) {
        Collection<ContentCatalog> contentCatalogs;
        Collection<ContentCatalogObject> contentCatalogObjects;
        
        try {
            var commandForm = ContentUtil.getHome().getGetContentCatalogsForm();
        
            commandForm.setContentWebAddressName(contentWebAddressName);
            commandForm.setContentCollectionName(contentCollectionName);
            commandForm.setAssociateProgramName(associateProgramName);
            commandForm.setAssociateName(associateName);
            commandForm.setAssociatePartyContactMechanismName(associatePartyContactMechanismName);

            contentCatalogs = new GetContentCatalogsCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
        
        if(contentCatalogs == null) {
            contentCatalogObjects = emptyList();
        } else {
            contentCatalogObjects = new ArrayList<>(contentCatalogs.size());

            contentCatalogs.stream()
                    .map(ContentCatalogObject::new)
                    .forEachOrdered(contentCatalogObjects::add);
        }
        
        return contentCatalogObjects;
    }

    @GraphQLField
    @GraphQLName("contentCatalogItem")
    public static ContentCatalogItemObject contentCatalogItem(final DataFetchingEnvironment env,
            @GraphQLName("contentWebAddressName") final String contentWebAddressName,
            @GraphQLName("contentCollectionName") final String contentCollectionName,
            @GraphQLName("contentCatalogName") final String contentCatalogName,
            @GraphQLName("itemName") @GraphQLNonNull final String itemName,
            @GraphQLName("inventoryConditionName") final String inventoryConditionName,
            @GraphQLName("unitOfMeasureTypeName") final String unitOfMeasureTypeName,
            @GraphQLName("currencyIsoName") final String currencyIsoName,
            @GraphQLName("associateProgramName") final String associateProgramName,
            @GraphQLName("associateName") final String associateName,
            @GraphQLName("associatePartyContactMechanismName") final String associatePartyContactMechanismName) {
        ContentCatalogItem contentCatalogItem;

        try {
            var commandForm = ContentUtil.getHome().getGetContentCatalogItemForm();

            commandForm.setContentWebAddressName(contentWebAddressName);
            commandForm.setContentCollectionName(contentCollectionName);
            commandForm.setContentCatalogName(contentCatalogName);
            commandForm.setItemName(itemName);
            commandForm.setInventoryConditionName(inventoryConditionName);
            commandForm.setUnitOfMeasureTypeName(unitOfMeasureTypeName);
            commandForm.setCurrencyIsoName(currencyIsoName);
            commandForm.setAssociateProgramName(associateProgramName);
            commandForm.setAssociateName(associateName);
            commandForm.setAssociatePartyContactMechanismName(associatePartyContactMechanismName);

            contentCatalogItem = new GetContentCatalogItemCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
        
        return contentCatalogItem == null ? null : new ContentCatalogItemObject(contentCatalogItem);
    }

    @GraphQLField
    @GraphQLName("contentCatalogItems")
    public static Collection<ContentCatalogItemObject> contentCatalogItems(final DataFetchingEnvironment env,
            @GraphQLName("contentWebAddressName") final String contentWebAddressName,
            @GraphQLName("contentCollectionName") final String contentCollectionName,
            @GraphQLName("contentCatalogName") final String contentCatalogName,
            @GraphQLName("associateProgramName") final String associateProgramName,
            @GraphQLName("associateName") final String associateName,
            @GraphQLName("associatePartyContactMechanismName") final String associatePartyContactMechanismName) {
        Collection<ContentCatalogItem> contentCatalogItems;
        Collection<ContentCatalogItemObject> contentCatalogItemObjects;
        
        try {
            var commandForm = ContentUtil.getHome().getGetContentCatalogItemsForm();
        
            commandForm.setContentWebAddressName(contentWebAddressName);
            commandForm.setContentCollectionName(contentCollectionName);
            commandForm.setContentCatalogName(contentCatalogName);
            commandForm.setAssociateProgramName(associateProgramName);
            commandForm.setAssociateName(associateName);
            commandForm.setAssociatePartyContactMechanismName(associatePartyContactMechanismName);

            contentCatalogItems = new GetContentCatalogItemsCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
        
        if(contentCatalogItems == null) {
            contentCatalogItemObjects = emptyList();
        } else {
            contentCatalogItemObjects = new ArrayList<>(contentCatalogItems.size());

            contentCatalogItems.stream()
                    .map(ContentCatalogItemObject::new)
                    .forEachOrdered(contentCatalogItemObjects::add);
        }
        
        return contentCatalogItemObjects;
    }

    @GraphQLField
    @GraphQLName("contentCategory")
    public static ContentCategoryObject contentCategory(final DataFetchingEnvironment env,
            @GraphQLName("contentWebAddressName") final String contentWebAddressName,
            @GraphQLName("contentCollectionName") final String contentCollectionName,
            @GraphQLName("contentCatalogName") final String contentCatalogName,
            @GraphQLName("contentCategoryName") final String contentCategoryName,
            @GraphQLName("associateProgramName") final String associateProgramName,
            @GraphQLName("associateName") final String associateName,
            @GraphQLName("associatePartyContactMechanismName") final String associatePartyContactMechanismName) {
        ContentCategory contentCategory;

        try {
            var commandForm = ContentUtil.getHome().getGetContentCategoryForm();

            commandForm.setContentWebAddressName(contentWebAddressName);
            commandForm.setContentCollectionName(contentCollectionName);
            commandForm.setContentCatalogName(contentCatalogName);
            commandForm.setContentCategoryName(contentCategoryName);
            commandForm.setAssociateProgramName(associateProgramName);
            commandForm.setAssociateName(associateName);
            commandForm.setAssociatePartyContactMechanismName(associatePartyContactMechanismName);

            contentCategory = new GetContentCategoryCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
        
        return contentCategory == null ? null : new ContentCategoryObject(contentCategory);
    }

    @GraphQLField
    @GraphQLName("contentCategories")
    public static Collection<ContentCategoryObject> contentCategories(final DataFetchingEnvironment env,
            @GraphQLName("contentWebAddressName") final String contentWebAddressName,
            @GraphQLName("contentCollectionName") final String contentCollectionName,
            @GraphQLName("contentCatalogName") final String contentCatalogName,
            @GraphQLName("parentContentCategoryName") final String parentContentCategoryName,
            @GraphQLName("associateProgramName") final String associateProgramName,
            @GraphQLName("associateName") final String associateName,
            @GraphQLName("associatePartyContactMechanismName") final String associatePartyContactMechanismName) {
        Collection<ContentCategory> contentCategories;
        Collection<ContentCategoryObject> contentCategoryObjects;
        
        try {
            var commandForm = ContentUtil.getHome().getGetContentCategoriesForm();
        
            commandForm.setContentWebAddressName(contentWebAddressName);
            commandForm.setContentCollectionName(contentCollectionName);
            commandForm.setContentCatalogName(contentCatalogName);
            commandForm.setParentContentCategoryName(parentContentCategoryName);
            commandForm.setAssociateProgramName(associateProgramName);
            commandForm.setAssociateName(associateName);
            commandForm.setAssociatePartyContactMechanismName(associatePartyContactMechanismName);

            contentCategories = new GetContentCategoriesCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
        
        if(contentCategories == null) {
            contentCategoryObjects = emptyList();
        } else {
            contentCategoryObjects = new ArrayList<>(contentCategories.size());

            contentCategories.stream()
                    .map(ContentCategoryObject::new)
                    .forEachOrdered(contentCategoryObjects::add);
        }
        
        return contentCategoryObjects;
    }

    @GraphQLField
    @GraphQLName("contentCategoryItem")
    public static ContentCategoryItemObject contentCategoryItem(final DataFetchingEnvironment env,
            @GraphQLName("contentWebAddressName") final String contentWebAddressName,
            @GraphQLName("contentCollectionName") final String contentCollectionName,
            @GraphQLName("contentCatalogName") final String contentCatalogName,
            @GraphQLName("contentCategoryName") final String contentCategoryName,
            @GraphQLName("itemName") @GraphQLNonNull final String itemName,
            @GraphQLName("inventoryConditionName") final String inventoryConditionName,
            @GraphQLName("unitOfMeasureTypeName") final String unitOfMeasureTypeName,
            @GraphQLName("currencyIsoName") final String currencyIsoName,
            @GraphQLName("associateProgramName") final String associateProgramName,
            @GraphQLName("associateName") final String associateName,
            @GraphQLName("associatePartyContactMechanismName") final String associatePartyContactMechanismName) {
        ContentCategoryItem contentCategoryItem;

        try {
            var commandForm = ContentUtil.getHome().getGetContentCategoryItemForm();

            commandForm.setContentWebAddressName(contentWebAddressName);
            commandForm.setContentCollectionName(contentCollectionName);
            commandForm.setContentCatalogName(contentCatalogName);
            commandForm.setContentCategoryName(contentCategoryName);
            commandForm.setItemName(itemName);
            commandForm.setInventoryConditionName(inventoryConditionName);
            commandForm.setUnitOfMeasureTypeName(unitOfMeasureTypeName);
            commandForm.setCurrencyIsoName(currencyIsoName);
            commandForm.setAssociateProgramName(associateProgramName);
            commandForm.setAssociateName(associateName);
            commandForm.setAssociatePartyContactMechanismName(associatePartyContactMechanismName);

            contentCategoryItem = new GetContentCategoryItemCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
        
        return contentCategoryItem == null ? null : new ContentCategoryItemObject(contentCategoryItem);
    }

    @GraphQLField
    @GraphQLName("contentCategoryItems")
    public static Collection<ContentCategoryItemObject> contentCategoryItems(final DataFetchingEnvironment env,
            @GraphQLName("contentWebAddressName") final String contentWebAddressName,
            @GraphQLName("contentCollectionName") final String contentCollectionName,
            @GraphQLName("contentCatalogName") final String contentCatalogName,
            @GraphQLName("contentCategoryName") final String contentCategoryName,
            @GraphQLName("associateProgramName") final String associateProgramName,
            @GraphQLName("associateName") final String associateName,
            @GraphQLName("associatePartyContactMechanismName") final String associatePartyContactMechanismName) {
        Collection<ContentCategoryItem> contentCategoryItems;
        Collection<ContentCategoryItemObject> contentCategoryItemObjects;
        
        try {
            var commandForm = ContentUtil.getHome().getGetContentCategoryItemsForm();
        
            commandForm.setContentWebAddressName(contentWebAddressName);
            commandForm.setContentCollectionName(contentCollectionName);
            commandForm.setContentCatalogName(contentCatalogName);
            commandForm.setContentCategoryName(contentCategoryName);
            commandForm.setAssociateProgramName(associateProgramName);
            commandForm.setAssociateName(associateName);
            commandForm.setAssociatePartyContactMechanismName(associatePartyContactMechanismName);

            contentCategoryItems = new GetContentCategoryItemsCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
        
        if(contentCategoryItems == null) {
            contentCategoryItemObjects = emptyList();
        } else {
            contentCategoryItemObjects = new ArrayList<>(contentCategoryItems.size());

            contentCategoryItems.stream()
                    .map(ContentCategoryItemObject::new)
                    .forEachOrdered(contentCategoryItemObjects::add);
        }
        
        return contentCategoryItemObjects;
    }

    @GraphQLField
    @GraphQLName("mimeTypeFileExtension")
    public static MimeTypeFileExtensionObject mimeTypeFileExtension(final DataFetchingEnvironment env,
            @GraphQLName("fileExtension") @GraphQLNonNull final String fileExtension) {
        MimeTypeFileExtension mimeTypeFileExtension;

        try {
            var commandForm = CoreUtil.getHome().getGetMimeTypeFileExtensionForm();

            commandForm.setFileExtension(fileExtension);

            mimeTypeFileExtension = new GetMimeTypeFileExtensionCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mimeTypeFileExtension == null ? null : new MimeTypeFileExtensionObject(mimeTypeFileExtension);
    }

    @GraphQLField
    @GraphQLName("mimeTypeFileExtensions")
    public static Collection<MimeTypeFileExtensionObject> mimeTypeFileExtensions(final DataFetchingEnvironment env) {
        Collection<MimeTypeFileExtension> mimeTypeFileExtensions;
        Collection<MimeTypeFileExtensionObject> mimeTypeFileExtensionObjects;

        try {
            var commandForm = CoreUtil.getHome().getGetMimeTypeFileExtensionsForm();

            mimeTypeFileExtensions = new GetMimeTypeFileExtensionsCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        if(mimeTypeFileExtensions == null) {
            mimeTypeFileExtensionObjects = emptyList();
        } else {
            mimeTypeFileExtensionObjects = new ArrayList<>(mimeTypeFileExtensions.size());

            mimeTypeFileExtensions.stream()
                    .map(MimeTypeFileExtensionObject::new)
                    .forEachOrdered(mimeTypeFileExtensionObjects::add);
        }

        return mimeTypeFileExtensionObjects;
    }

    @GraphQLField
    @GraphQLName("mimeTypeUsageType")
    public static MimeTypeUsageTypeObject mimeTypeUsageType(final DataFetchingEnvironment env,
            @GraphQLName("mimeTypeUsageTypeName") @GraphQLNonNull final String mimeTypeUsageTypeName) {
        MimeTypeUsageType mimeTypeUsageType;

        try {
            var commandForm = CoreUtil.getHome().getGetMimeTypeUsageTypeForm();

            commandForm.setMimeTypeUsageTypeName(mimeTypeUsageTypeName);

            mimeTypeUsageType = new GetMimeTypeUsageTypeCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mimeTypeUsageType == null ? null : new MimeTypeUsageTypeObject(mimeTypeUsageType);
    }

    @GraphQLField
    @GraphQLName("mimeTypeUsageTypes")
    public static Collection<MimeTypeUsageTypeObject> mimeTypeUsageTypes(final DataFetchingEnvironment env) {
        Collection<MimeTypeUsageType> mimeTypeUsageTypes;
        Collection<MimeTypeUsageTypeObject> mimeTypeUsageTypeObjects;

        try {
            var commandForm = CoreUtil.getHome().getGetMimeTypeUsageTypesForm();

            mimeTypeUsageTypes = new GetMimeTypeUsageTypesCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        if(mimeTypeUsageTypes == null) {
            mimeTypeUsageTypeObjects = emptyList();
        } else {
            mimeTypeUsageTypeObjects = new ArrayList<>(mimeTypeUsageTypes.size());

            mimeTypeUsageTypes.stream()
                    .map(MimeTypeUsageTypeObject::new)
                    .forEachOrdered(mimeTypeUsageTypeObjects::add);
        }

        return mimeTypeUsageTypeObjects;
    }

    @GraphQLField
    @GraphQLName("mimeType")
    public static MimeTypeObject mimeType(final DataFetchingEnvironment env,
            @GraphQLName("mimeTypeName") @GraphQLNonNull final String mimeTypeName) {
        MimeType mimeType;

        try {
            var commandForm = CoreUtil.getHome().getGetMimeTypeForm();

            commandForm.setMimeTypeName(mimeTypeName);

            mimeType = new GetMimeTypeCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return mimeType == null ? null : new MimeTypeObject(mimeType);
    }

    @GraphQLField
    @GraphQLName("mimeTypes")
    public static Collection<MimeTypeObject> mimeTypes(final DataFetchingEnvironment env,
           @GraphQLName("mimeTypeUsageTypeName") final String mimeTypeUsageTypeName) {
        Collection<MimeType> mimeTypes;
        Collection<MimeTypeObject> mimeTypeObjects;

        try {
            var commandForm = CoreUtil.getHome().getGetMimeTypesForm();

            commandForm.setMimeTypeUsageTypeName(mimeTypeUsageTypeName);

            mimeTypes = new GetMimeTypesCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        if(mimeTypes == null) {
            mimeTypeObjects = emptyList();
        } else {
            mimeTypeObjects = new ArrayList<>(mimeTypes.size());

            mimeTypes.stream()
                    .map(MimeTypeObject::new)
                    .forEachOrdered(mimeTypeObjects::add);
        }

        return mimeTypeObjects;
    }

    @GraphQLField
    @GraphQLName("queueType")
    public static QueueTypeObject queueType(final DataFetchingEnvironment env,
            @GraphQLName("queueTypeName") @GraphQLNonNull final String queueTypeName) {
        QueueType queueType;

        try {
            var commandForm = QueueUtil.getHome().getGetQueueTypeForm();

            commandForm.setQueueTypeName(queueTypeName);

            queueType = new GetQueueTypeCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return queueType == null ? null : new QueueTypeObject(queueType);
    }

    @GraphQLField
    @GraphQLName("queueTypes")
    public static Collection<QueueTypeObject> queueTypes(final DataFetchingEnvironment env) {
        Collection<QueueType> queueTypes;
        Collection<QueueTypeObject> queueTypeObjects;

        try {
            var commandForm = QueueUtil.getHome().getGetQueueTypesForm();

            queueTypes = new GetQueueTypesCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        if(queueTypes == null) {
            queueTypeObjects = emptyList();
        } else {
            queueTypeObjects = new ArrayList<>(queueTypes.size());

            queueTypes.stream()
                    .map(QueueTypeObject::new)
                    .forEachOrdered(queueTypeObjects::add);
        }

        return queueTypeObjects;
    }

    @GraphQLField
    @GraphQLName("unitOfMeasureKindUse")
    public static UnitOfMeasureKindUseObject unitOfMeasureKindUse(final DataFetchingEnvironment env,
            @GraphQLName("unitOfMeasureKindUseTypeName") @GraphQLNonNull final String unitOfMeasureKindUseTypeName,
            @GraphQLName("unitOfMeasureKindName") @GraphQLNonNull final String unitOfMeasureKindName) {
        UnitOfMeasureKindUse unitOfMeasureKindUse;

        try {
            var commandForm = UomUtil.getHome().getGetUnitOfMeasureKindUseForm();

            commandForm.setUnitOfMeasureKindUseTypeName(unitOfMeasureKindUseTypeName);
            commandForm.setUnitOfMeasureKindName(unitOfMeasureKindName);

            unitOfMeasureKindUse = new GetUnitOfMeasureKindUseCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        return unitOfMeasureKindUse == null ? null : new UnitOfMeasureKindUseObject(unitOfMeasureKindUse);
    }

    @GraphQLField
    @GraphQLName("unitOfMeasureKindUses")
    public static Collection<UnitOfMeasureKindUseObject> unitOfMeasureKindUses(final DataFetchingEnvironment env,
            @GraphQLName("unitOfMeasureKindUseTypeName") final String unitOfMeasureKindUseTypeName,
            @GraphQLName("unitOfMeasureKindName") final String unitOfMeasureKindName) {
        Collection<UnitOfMeasureKindUse> unitOfMeasureKindUses;
        Collection<UnitOfMeasureKindUseObject> unitOfMeasureKindUseObjects;

        try {
            var commandForm = UomUtil.getHome().getGetUnitOfMeasureKindUsesForm();

            commandForm.setUnitOfMeasureKindUseTypeName(unitOfMeasureKindUseTypeName);
            commandForm.setUnitOfMeasureKindName(unitOfMeasureKindName);

            unitOfMeasureKindUses = new GetUnitOfMeasureKindUsesCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        if(unitOfMeasureKindUses == null) {
            unitOfMeasureKindUseObjects = emptyList();
        } else {
            unitOfMeasureKindUseObjects = new ArrayList<>(unitOfMeasureKindUses.size());

            unitOfMeasureKindUses.stream()
                    .map(UnitOfMeasureKindUseObject::new)
                    .forEachOrdered(unitOfMeasureKindUseObjects::add);
        }

        return unitOfMeasureKindUseObjects;
    }

    @GraphQLField
    @GraphQLName("unitOfMeasureType")
    public static UnitOfMeasureTypeObject unitOfMeasureType(final DataFetchingEnvironment env,
            @GraphQLName("unitOfMeasureKindName") @GraphQLNonNull final String unitOfMeasureKindName,
            @GraphQLName("unitOfMeasureTypeName") @GraphQLNonNull final String unitOfMeasureTypeName) {
        UnitOfMeasureType unitOfMeasureType;

        try {
            var commandForm = UomUtil.getHome().getGetUnitOfMeasureTypeForm();

            commandForm.setUnitOfMeasureKindName(unitOfMeasureKindName);
            commandForm.setUnitOfMeasureTypeName(unitOfMeasureTypeName);
        
            unitOfMeasureType = new GetUnitOfMeasureTypeCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
        
        return unitOfMeasureType == null ? null : new UnitOfMeasureTypeObject(unitOfMeasureType);
    }

    @GraphQLField
    @GraphQLName("unitOfMeasureTypes")
    public static Collection<UnitOfMeasureTypeObject> unitOfMeasureTypes(final DataFetchingEnvironment env,
            @GraphQLName("unitOfMeasureKindName") @GraphQLNonNull final String unitOfMeasureKindName) {
        Collection<UnitOfMeasureType> unitOfMeasureTypes;
        Collection<UnitOfMeasureTypeObject> unitOfMeasureTypeObjects;
        
        try {
            var commandForm = UomUtil.getHome().getGetUnitOfMeasureTypesForm();
        
            commandForm.setUnitOfMeasureKindName(unitOfMeasureKindName);
            
            unitOfMeasureTypes = new GetUnitOfMeasureTypesCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
        
        if(unitOfMeasureTypes == null) {
            unitOfMeasureTypeObjects = emptyList();
        } else {
            unitOfMeasureTypeObjects = new ArrayList<>(unitOfMeasureTypes.size());

            unitOfMeasureTypes.stream()
                    .map(UnitOfMeasureTypeObject::new)
                    .forEachOrdered(unitOfMeasureTypeObjects::add);
        }
        
        return unitOfMeasureTypeObjects;
    }
    
    @GraphQLField
    @GraphQLName("unitOfMeasureKind")
    public static UnitOfMeasureKindObject unitOfMeasureKind(final DataFetchingEnvironment env,
            @GraphQLName("unitOfMeasureKindName") @GraphQLNonNull final String unitOfMeasureKindName) {
        UnitOfMeasureKind unitOfMeasureKind;

        try {
            var commandForm = UomUtil.getHome().getGetUnitOfMeasureKindForm();

            commandForm.setUnitOfMeasureKindName(unitOfMeasureKindName);
        
            unitOfMeasureKind = new GetUnitOfMeasureKindCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
        
        return unitOfMeasureKind == null ? null : new UnitOfMeasureKindObject(unitOfMeasureKind);
    }

    @GraphQLField
    @GraphQLName("unitOfMeasureKinds")
    public static Collection<UnitOfMeasureKindObject> unitOfMeasureKinds(final DataFetchingEnvironment env) {
        Collection<UnitOfMeasureKind> unitOfMeasureKinds;
        Collection<UnitOfMeasureKindObject> unitOfMeasureKindObjects;
        
        try {
            var commandForm = UomUtil.getHome().getGetUnitOfMeasureKindsForm();
        
            unitOfMeasureKinds = new GetUnitOfMeasureKindsCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
        
        if(unitOfMeasureKinds == null) {
            unitOfMeasureKindObjects = emptyList();
        } else {
            unitOfMeasureKindObjects = new ArrayList<>(unitOfMeasureKinds.size());

            unitOfMeasureKinds.stream()
                    .map(UnitOfMeasureKindObject::new)
                    .forEachOrdered(unitOfMeasureKindObjects::add);
        }
        
        return unitOfMeasureKindObjects;
    }
    
    @GraphQLField
    @GraphQLName("unitOfMeasureKindUseType")
    public static UnitOfMeasureKindUseTypeObject unitOfMeasureKindUseType(final DataFetchingEnvironment env,
            @GraphQLName("unitOfMeasureKindUseTypeName") @GraphQLNonNull final String unitOfMeasureKindUseTypeName) {
        UnitOfMeasureKindUseType unitOfMeasureKindUseType;

        try {
            var commandForm = UomUtil.getHome().getGetUnitOfMeasureKindUseTypeForm();

            commandForm.setUnitOfMeasureKindUseTypeName(unitOfMeasureKindUseTypeName);
        
            unitOfMeasureKindUseType = new GetUnitOfMeasureKindUseTypeCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
        
        return unitOfMeasureKindUseType == null ? null : new UnitOfMeasureKindUseTypeObject(unitOfMeasureKindUseType);
    }

    @GraphQLField
    @GraphQLName("unitOfMeasureKindUseTypes")
    public static Collection<UnitOfMeasureKindUseTypeObject> unitOfMeasureKindUseTypes(final DataFetchingEnvironment env) {
        Collection<UnitOfMeasureKindUseType> unitOfMeasureKindUseTypes;
        Collection<UnitOfMeasureKindUseTypeObject> unitOfMeasureKindUseTypeObjects;
        
        try {
            var commandForm = UomUtil.getHome().getGetUnitOfMeasureKindUseTypesForm();
        
            unitOfMeasureKindUseTypes = new GetUnitOfMeasureKindUseTypesCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
        
        if(unitOfMeasureKindUseTypes == null) {
            unitOfMeasureKindUseTypeObjects = emptyList();
        } else {
            unitOfMeasureKindUseTypeObjects = new ArrayList<>(unitOfMeasureKindUseTypes.size());

            unitOfMeasureKindUseTypes.stream()
                    .map(UnitOfMeasureKindUseTypeObject::new)
                    .forEachOrdered(unitOfMeasureKindUseTypeObjects::add);
        }
        
        return unitOfMeasureKindUseTypeObjects;
    }
    
    @GraphQLField
    @GraphQLName("entityAttributeType")
    public static EntityAttributeTypeObject entityAttributeType(final DataFetchingEnvironment env,
            @GraphQLName("entityAttributeTypeName") @GraphQLNonNull final String entityAttributeTypeName) {
        EntityAttributeType entityAttributeType;

        try {
            var commandForm = CoreUtil.getHome().getGetEntityAttributeTypeForm();

            commandForm.setEntityAttributeTypeName(entityAttributeTypeName);
        
            entityAttributeType = new GetEntityAttributeTypeCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
        
        return entityAttributeType == null ? null : new EntityAttributeTypeObject(entityAttributeType);
    }

    @GraphQLField
    @GraphQLName("entityAttributeTypes")
    public static Collection<EntityAttributeTypeObject> entityAttributeTypes(final DataFetchingEnvironment env) {
        Collection<EntityAttributeType> entityAttributeTypes;
        Collection<EntityAttributeTypeObject> entityAttributeTypeObjects;
        
        try {
            var commandForm = CoreUtil.getHome().getGetEntityAttributeTypesForm();
        
            entityAttributeTypes = new GetEntityAttributeTypesCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
        
        if(entityAttributeTypes == null) {
            entityAttributeTypeObjects = emptyList();
        } else {
            entityAttributeTypeObjects = new ArrayList<>(entityAttributeTypes.size());

            entityAttributeTypes.stream()
                    .map(EntityAttributeTypeObject::new)
                    .forEachOrdered(entityAttributeTypeObjects::add);
        }
        
        return entityAttributeTypeObjects;
    }
    
    @GraphQLField
    @GraphQLName("customerResults")
    public static CustomerResultsObject customerResults(final DataFetchingEnvironment env,
            @GraphQLName("searchTypeName") @GraphQLNonNull final String searchTypeName) {
        CustomerResultsObject customerResultsObject = new CustomerResultsObject();
        
        try {
            var commandForm = SearchUtil.getHome().getGetCustomerResultsForm();

            commandForm.setSearchTypeName(searchTypeName);
            
            if(new GetCustomerResultsCommand(getUserVisitPK(env), commandForm).canGetResultsForGraphQl()) {
                customerResultsObject.setForm(commandForm);
            }
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
        
        return customerResultsObject;
    }

    @GraphQLField
    @GraphQLName("color")
    public static ColorObject color(final DataFetchingEnvironment env,
            @GraphQLName("colorName") final String colorName,
            @GraphQLName("id") @GraphQLID final String id) {
        Color color;

        try {
            var commandForm = CoreUtil.getHome().getGetColorForm();

            commandForm.setColorName(colorName);
            commandForm.setUlid(id);
        
            color = new GetColorCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
        
        return color == null ? null : new ColorObject(color);
    }

    @GraphQLField
    @GraphQLName("colors")
    public static Collection<ColorObject> colors(final DataFetchingEnvironment env) {
        Collection<Color> colors;
        Collection<ColorObject> colorObjects;
        
        try {
            var commandForm = CoreUtil.getHome().getGetColorsForm();
        
            colors = new GetColorsCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
        
        if(colors == null) {
            colorObjects = emptyList();
        } else {
            colorObjects = new ArrayList<>(colors.size());

            colors.stream()
                    .map(ColorObject::new)
                    .forEachOrdered(colorObjects::add);
        }
        
        return colorObjects;
    }

    @GraphQLField
    @GraphQLName("fontStyle")
    public static FontStyleObject fontStyle(final DataFetchingEnvironment env,
            @GraphQLName("fontStyleName") final String fontStyleName,
            @GraphQLName("id") @GraphQLID final String id) {
        FontStyle fontStyle;

        try {
            var commandForm = CoreUtil.getHome().getGetFontStyleForm();

            commandForm.setFontStyleName(fontStyleName);
            commandForm.setUlid(id);
        
            fontStyle = new GetFontStyleCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
        
        return fontStyle == null ? null : new FontStyleObject(fontStyle);
    }

    @GraphQLField
    @GraphQLName("fontStyles")
    public static Collection<FontStyleObject> fontStyles(final DataFetchingEnvironment env) {
        Collection<FontStyle> fontStyles;
        Collection<FontStyleObject> fontStyleObjects;
        
        try {
            var commandForm = CoreUtil.getHome().getGetFontStylesForm();
        
            fontStyles = new GetFontStylesCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
        
        if(fontStyles == null) {
            fontStyleObjects = emptyList();
        } else {
            fontStyleObjects = new ArrayList<>(fontStyles.size());

            fontStyles.stream()
                    .map(FontStyleObject::new)
                    .forEachOrdered(fontStyleObjects::add);
        }
        
        return fontStyleObjects;
    }

    @GraphQLField
    @GraphQLName("fontWeight")
    public static FontWeightObject fontWeight(final DataFetchingEnvironment env,
            @GraphQLName("fontWeightName") final String fontWeightName,
            @GraphQLName("id") @GraphQLID final String id) {
        FontWeight fontWeight;

        try {
            var commandForm = CoreUtil.getHome().getGetFontWeightForm();

            commandForm.setFontWeightName(fontWeightName);
            commandForm.setUlid(id);
        
            fontWeight = new GetFontWeightCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
        
        return fontWeight == null ? null : new FontWeightObject(fontWeight);
    }

    @GraphQLField
    @GraphQLName("fontWeights")
    public static Collection<FontWeightObject> fontWeights(final DataFetchingEnvironment env) {
        Collection<FontWeight> fontWeights;
        Collection<FontWeightObject> fontWeightObjects;
        
        try {
            var commandForm = CoreUtil.getHome().getGetFontWeightsForm();
        
            fontWeights = new GetFontWeightsCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
        
        if(fontWeights == null) {
            fontWeightObjects = emptyList();
        } else {
            fontWeightObjects = new ArrayList<>(fontWeights.size());

            fontWeights.stream()
                    .map(FontWeightObject::new)
                    .forEachOrdered(fontWeightObjects::add);
        }
        
        return fontWeightObjects;
    }

    @GraphQLField
    @GraphQLName("textDecoration")
    public static TextDecorationObject textDecoration(final DataFetchingEnvironment env,
            @GraphQLName("textDecorationName") final String textDecorationName,
            @GraphQLName("id") @GraphQLID final String id) {
        TextDecoration textDecoration;

        try {
            var commandForm = CoreUtil.getHome().getGetTextDecorationForm();

            commandForm.setTextDecorationName(textDecorationName);
            commandForm.setUlid(id);
        
            textDecoration = new GetTextDecorationCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
        
        return textDecoration == null ? null : new TextDecorationObject(textDecoration);
    }

    @GraphQLField
    @GraphQLName("textDecorations")
    public static Collection<TextDecorationObject> textDecorations(final DataFetchingEnvironment env) {
        Collection<TextDecoration> textDecorations;
        Collection<TextDecorationObject> textDecorationObjects;
        
        try {
            var commandForm = CoreUtil.getHome().getGetTextDecorationsForm();
        
            textDecorations = new GetTextDecorationsCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
        
        if(textDecorations == null) {
            textDecorationObjects = emptyList();
        } else {
            textDecorationObjects = new ArrayList<>(textDecorations.size());

            textDecorations.stream()
                    .map(TextDecorationObject::new)
                    .forEachOrdered(textDecorationObjects::add);
        }
        
        return textDecorationObjects;
    }

    @GraphQLField
    @GraphQLName("textTransformation")
    public static TextTransformationObject textTransformation(final DataFetchingEnvironment env,
            @GraphQLName("textTransformationName") final String textTransformationName,
            @GraphQLName("id") @GraphQLID final String id) {
        TextTransformation textTransformation;

        try {
            var commandForm = CoreUtil.getHome().getGetTextTransformationForm();

            commandForm.setTextTransformationName(textTransformationName);
            commandForm.setUlid(id);
        
            textTransformation = new GetTextTransformationCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
        
        return textTransformation == null ? null : new TextTransformationObject(textTransformation);
    }

    @GraphQLField
    @GraphQLName("textTransformations")
    public static Collection<TextTransformationObject> textTransformations(final DataFetchingEnvironment env) {
        Collection<TextTransformation> textTransformations;
        Collection<TextTransformationObject> textTransformationObjects;
        
        try {
            var commandForm = CoreUtil.getHome().getGetTextTransformationsForm();
        
            textTransformations = new GetTextTransformationsCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
        
        if(textTransformations == null) {
            textTransformationObjects = emptyList();
        } else {
            textTransformationObjects = new ArrayList<>(textTransformations.size());

            textTransformations.stream()
                    .map(TextTransformationObject::new)
                    .forEachOrdered(textTransformationObjects::add);
        }
        
        return textTransformationObjects;
    }

    @GraphQLField
    @GraphQLName("userLogin")
    public static UserLoginObject userLogin(final DataFetchingEnvironment env,
            @GraphQLName("username") final String username,
            @GraphQLName("partyId") final String partyId) {
        UserLogin userLogin;
        UserLogin foundByUsernameUserLogin;

        try {
            var commandForm = UserUtil.getHome().getGetUserLoginForm();

            commandForm.setUsername(username);
            commandForm.setUlid(partyId);
        
            GetUserLoginCommand getUserLoginCommand = new GetUserLoginCommand(getUserVisitPK(env), commandForm);
            userLogin = getUserLoginCommand.runForGraphQl();
            foundByUsernameUserLogin = getUserLoginCommand.foundByUsernameUserLogin;
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
        
        return userLogin == null && foundByUsernameUserLogin == null? null : new UserLoginObject(userLogin, foundByUsernameUserLogin);
    }
    
    @GraphQLField
    @GraphQLName("recoveryQuestion")
    public static RecoveryQuestionObject recoveryQuestion(final DataFetchingEnvironment env,
            @GraphQLName("recoveryQuestionName") final String recoveryQuestionName,
            @GraphQLName("id") @GraphQLID final String id,
            @GraphQLName("username") final String username) {
        RecoveryQuestion recoveryQuestion;

        try {
            var commandForm = UserUtil.getHome().getGetRecoveryQuestionForm();

            commandForm.setRecoveryQuestionName(recoveryQuestionName);
            commandForm.setUlid(id);
            commandForm.setUsername(username);
        
            recoveryQuestion = new GetRecoveryQuestionCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
        
        return recoveryQuestion == null ? null : new RecoveryQuestionObject(recoveryQuestion);
    }

    @GraphQLField
    @GraphQLName("recoveryQuestions")
    public static Collection<RecoveryQuestionObject> recoveryQuestions(final DataFetchingEnvironment env) {
        Collection<RecoveryQuestion> recoveryQuestions;
        Collection<RecoveryQuestionObject> recoveryQuestionObjects;
        
        try {
            var commandForm = UserUtil.getHome().getGetRecoveryQuestionsForm();
        
            recoveryQuestions = new GetRecoveryQuestionsCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
        
        if(recoveryQuestions == null) {
            recoveryQuestionObjects = emptyList();
        } else {
            recoveryQuestionObjects = new ArrayList<>(recoveryQuestions.size());

            recoveryQuestions.stream()
                    .map(RecoveryQuestionObject::new)
                    .forEachOrdered(recoveryQuestionObjects::add);
        }
        
        return recoveryQuestionObjects;
    }

    @GraphQLField
    @GraphQLName("userSession")
    public static UserSessionObject userSession(final DataFetchingEnvironment env) {
        GraphQlContext context = env.getContext();
        UserSession userSession = context.getUserSession();
        
        return userSession == null ? null : new UserSessionObject(userSession);
    }
    
    @GraphQLField
    @GraphQLName("userVisit")
    public static UserVisitObject userVisit(final DataFetchingEnvironment env) {
        GraphQlContext context = env.getContext();
        UserVisit userVisit = context.getUserVisit();
        
        return userVisit == null ? null : new UserVisitObject(userVisit);
    }
    
    @GraphQLField
    @GraphQLName("currency")
    public static CurrencyObject currency(final DataFetchingEnvironment env,
            @GraphQLName("currencyIsoName") final String currencyIsoName,
            @GraphQLName("id") @GraphQLID final String id) {
        Currency currency;

        try {
            var commandForm = AccountingUtil.getHome().getGetCurrencyForm();

            commandForm.setCurrencyIsoName(currencyIsoName);
            commandForm.setUlid(id);
        
            currency = new GetCurrencyCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
        
        return currency == null ? null : new CurrencyObject(currency);
    }

    @GraphQLField
    @GraphQLName("currencies")
    public static Collection<CurrencyObject> currencies(final DataFetchingEnvironment env) {
        Collection<Currency> currencies;
        Collection<CurrencyObject> currencyObjects;
        
        try {
            var commandForm = AccountingUtil.getHome().getGetCurrenciesForm();
        
            currencies = new GetCurrenciesCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
        
        if(currencies == null) {
            currencyObjects = emptyList();
        } else {
            currencyObjects = new ArrayList<>(currencies.size());

            currencies.stream()
                    .map(CurrencyObject::new)
                    .forEachOrdered(currencyObjects::add);
        }
        
        return currencyObjects;
    }
    
    @GraphQLField
    @GraphQLName("language")
    public static LanguageObject language(final DataFetchingEnvironment env,
            @GraphQLName("languageIsoName") final String languageIsoName,
            @GraphQLName("id") @GraphQLID final String id) {
        Language language;

        try {
            var commandForm = PartyUtil.getHome().getGetLanguageForm();

            commandForm.setLanguageIsoName(languageIsoName);
            commandForm.setUlid(id);
        
            language = new GetLanguageCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
        
        return language == null ? null : new LanguageObject(language);
    }

    @GraphQLField
    @GraphQLName("languages")
    public static Collection<LanguageObject> languages(final DataFetchingEnvironment env) {
        Collection<Language> languages;
        Collection<LanguageObject> languageObjects;
        
        try {
            var commandForm = PartyUtil.getHome().getGetLanguagesForm();
        
            languages = new GetLanguagesCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
        
        if(languages == null) {
            languageObjects = emptyList();
        } else {
            languageObjects = new ArrayList<>(languages.size());

            languages.stream()
                    .map(LanguageObject::new)
                    .forEachOrdered(languageObjects::add);
        }
        
        return languageObjects;
    }

    @GraphQLField
    @GraphQLName("dateTimeFormat")
    public static DateTimeFormatObject dateTimeFormat(final DataFetchingEnvironment env,
            @GraphQLName("dateTimeFormatName") final String dateTimeFormatName,
            @GraphQLName("id") @GraphQLID final String id) {
        DateTimeFormat dateTimeFormat;

        try {
            var commandForm = PartyUtil.getHome().getGetDateTimeFormatForm();

            commandForm.setDateTimeFormatName(dateTimeFormatName);
            commandForm.setUlid(id);
        
            dateTimeFormat = new GetDateTimeFormatCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
        
        return dateTimeFormat == null ? null : new DateTimeFormatObject(dateTimeFormat);
    }

    @GraphQLField
    @GraphQLName("dateTimeFormats")
    public static Collection<DateTimeFormatObject> dateTimeFormats(final DataFetchingEnvironment env) {
        Collection<DateTimeFormat> dateTimeFormats;
        Collection<DateTimeFormatObject> dateTimeFormatObjects;
        
        try {
            var commandForm = PartyUtil.getHome().getGetDateTimeFormatsForm();
        
            dateTimeFormats = new GetDateTimeFormatsCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
        
        if(dateTimeFormats == null) {
            dateTimeFormatObjects = emptyList();
        } else {
            dateTimeFormatObjects = new ArrayList<>(dateTimeFormats.size());

            dateTimeFormats.stream()
                    .map(DateTimeFormatObject::new)
                    .forEachOrdered(dateTimeFormatObjects::add);
        }
        
        return dateTimeFormatObjects;
    }

    @GraphQLField
    @GraphQLName("timeZone")
    public static TimeZoneObject timeZone(final DataFetchingEnvironment env,
            @GraphQLName("javaTimeZoneName") final String javaTimeZoneName,
            @GraphQLName("id") @GraphQLID final String id) {
        TimeZone timeZone;

        try {
            var commandForm = PartyUtil.getHome().getGetTimeZoneForm();

            commandForm.setJavaTimeZoneName(javaTimeZoneName);
            commandForm.setUlid(id);
        
            timeZone = new GetTimeZoneCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
        
        return timeZone == null ? null : new TimeZoneObject(timeZone);
    }

    @GraphQLField
    @GraphQLName("timeZones")
    public static Collection<TimeZoneObject> timeZones(final DataFetchingEnvironment env) {
        Collection<TimeZone> timeZones;
        Collection<TimeZoneObject> timeZoneObjects;
        
        try {
            var commandForm = PartyUtil.getHome().getGetTimeZonesForm();
        
            timeZones = new GetTimeZonesCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
        
        if(timeZones == null) {
            timeZoneObjects = emptyList();
        } else {
            timeZoneObjects = new ArrayList<>(timeZones.size());

            timeZones.stream()
                    .map(TimeZoneObject::new)
                    .forEachOrdered(timeZoneObjects::add);
        }
        
        return timeZoneObjects;
    }

    @GraphQLField
    @GraphQLName("item")
    public static ItemObject item(final DataFetchingEnvironment env,
            @GraphQLName("itemName") final String itemName,
            @GraphQLName("itemNameOrAlias") final String itemNameOrAlias,
            @GraphQLName("id") @GraphQLID final String id) {
        Item item;

        try {
            var commandForm = ItemUtil.getHome().getGetItemForm();

            commandForm.setItemName(itemName);
            commandForm.setItemNameOrAlias(itemNameOrAlias);
            commandForm.setUlid(id);
        
            item = new GetItemCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
        
        return item == null ? null : new ItemObject(item);
    }
    
    @GraphQLField
    @GraphQLName("itemCategory")
    public static ItemCategoryObject itemCategory(final DataFetchingEnvironment env,
            @GraphQLName("itemCategoryName") final String itemCategoryName,
            @GraphQLName("id") @GraphQLID final String id) {
        ItemCategory itemCategory;

        try {
            var commandForm = ItemUtil.getHome().getGetItemCategoryForm();

            commandForm.setItemCategoryName(itemCategoryName);
            commandForm.setUlid(id);
        
            itemCategory = new GetItemCategoryCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
        
        return itemCategory == null ? null : new ItemCategoryObject(itemCategory);
    }

    @GraphQLField
    @GraphQLName("itemCategories")
    public static Collection<ItemCategoryObject> itemCategories(final DataFetchingEnvironment env,
            @GraphQLName("parentItemCategoryName") final String parentItemCategoryName) {
        Collection<ItemCategory> itemCategories;
        Collection<ItemCategoryObject> itemCategoryObjects;
        
        try {
            var commandForm = ItemUtil.getHome().getGetItemCategoriesForm();

            commandForm.setParentItemCategoryName(parentItemCategoryName);
        
            itemCategories = new GetItemCategoriesCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
        
        if(itemCategories == null) {
            itemCategoryObjects = emptyList();
        } else {
            itemCategoryObjects = new ArrayList<>(itemCategories.size());

            itemCategories.stream()
                    .map(ItemCategoryObject::new)
                    .forEachOrdered(itemCategoryObjects::add);
        }
        
        return itemCategoryObjects;
    }

    @GraphQLField
    @GraphQLName("personalTitles")
    public static Collection<PersonalTitleObject> personalTitles(final DataFetchingEnvironment env) {
        Collection<PersonalTitle> personalTitles;
        
        try {
            var commandForm = PartyUtil.getHome().getGetPersonalTitlesForm();
        
            personalTitles = new GetPersonalTitlesCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
        
        Collection<PersonalTitleObject> personalTitleObjects = new ArrayList<>(personalTitles.size());
        
        personalTitles.stream()
                .map(PersonalTitleObject::new)
                .forEachOrdered(personalTitleObjects::add);
        
        return personalTitleObjects;
    }

    @GraphQLField
    @GraphQLName("nameSuffixes")
    public static Collection<NameSuffixObject> nameSuffixes(final DataFetchingEnvironment env) {
        Collection<NameSuffix> nameSuffixes;
        
        try {
            var commandForm = PartyUtil.getHome().getGetNameSuffixesForm();
        
            nameSuffixes = new GetNameSuffixesCommand(getUserVisitPK(env), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        Collection<NameSuffixObject> nameSuffixObjects = new ArrayList<>(nameSuffixes.size());

        nameSuffixes.stream()
                .map(NameSuffixObject::new)
                .forEachOrdered(nameSuffixObjects::add);
        
        return nameSuffixObjects;
    }

}
