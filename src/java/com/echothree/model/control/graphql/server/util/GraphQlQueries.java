// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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
import com.echothree.control.user.accounting.common.form.GetCurrenciesForm;
import com.echothree.control.user.accounting.common.form.GetCurrencyForm;
import com.echothree.control.user.accounting.server.command.GetCurrenciesCommand;
import com.echothree.control.user.accounting.server.command.GetCurrencyCommand;
import com.echothree.control.user.content.common.ContentUtil;
import com.echothree.control.user.content.common.form.GetContentPageLayoutForm;
import com.echothree.control.user.content.common.form.GetContentPageLayoutsForm;
import com.echothree.control.user.content.server.command.GetContentPageLayoutCommand;
import com.echothree.control.user.content.server.command.GetContentPageLayoutsCommand;
import com.echothree.control.user.core.common.CoreUtil;
import com.echothree.control.user.core.common.form.GetColorForm;
import com.echothree.control.user.core.common.form.GetColorsForm;
import com.echothree.control.user.core.common.form.GetEntityAttributeTypeForm;
import com.echothree.control.user.core.common.form.GetEntityAttributeTypesForm;
import com.echothree.control.user.core.common.form.GetFontStyleForm;
import com.echothree.control.user.core.common.form.GetFontStylesForm;
import com.echothree.control.user.core.common.form.GetFontWeightForm;
import com.echothree.control.user.core.common.form.GetFontWeightsForm;
import com.echothree.control.user.core.common.form.GetMimeTypeFileExtensionForm;
import com.echothree.control.user.core.common.form.GetMimeTypeFileExtensionsForm;
import com.echothree.control.user.core.common.form.GetMimeTypeForm;
import com.echothree.control.user.core.common.form.GetMimeTypeUsageTypeForm;
import com.echothree.control.user.core.common.form.GetMimeTypeUsageTypesForm;
import com.echothree.control.user.core.common.form.GetMimeTypesForm;
import com.echothree.control.user.core.common.form.GetTextDecorationForm;
import com.echothree.control.user.core.common.form.GetTextDecorationsForm;
import com.echothree.control.user.core.common.form.GetTextTransformationForm;
import com.echothree.control.user.core.common.form.GetTextTransformationsForm;
import com.echothree.control.user.core.server.command.GetColorCommand;
import com.echothree.control.user.core.server.command.GetColorsCommand;
import com.echothree.control.user.core.server.command.GetEntityAttributeTypeCommand;
import com.echothree.control.user.core.server.command.GetEntityAttributeTypesCommand;
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
import com.echothree.control.user.item.common.ItemUtil;
import com.echothree.control.user.item.common.form.GetItemCategoriesForm;
import com.echothree.control.user.item.common.form.GetItemCategoryForm;
import com.echothree.control.user.item.common.form.GetItemForm;
import com.echothree.control.user.item.server.command.GetItemCategoriesCommand;
import com.echothree.control.user.item.server.command.GetItemCategoryCommand;
import com.echothree.control.user.item.server.command.GetItemCommand;
import com.echothree.control.user.party.common.PartyUtil;
import com.echothree.control.user.party.common.form.GetDateTimeFormatForm;
import com.echothree.control.user.party.common.form.GetDateTimeFormatsForm;
import com.echothree.control.user.party.common.form.GetLanguageForm;
import com.echothree.control.user.party.common.form.GetLanguagesForm;
import com.echothree.control.user.party.common.form.GetNameSuffixesForm;
import com.echothree.control.user.party.common.form.GetPersonalTitlesForm;
import com.echothree.control.user.party.common.form.GetTimeZoneForm;
import com.echothree.control.user.party.common.form.GetTimeZonesForm;
import com.echothree.control.user.party.server.command.GetDateTimeFormatCommand;
import com.echothree.control.user.party.server.command.GetDateTimeFormatsCommand;
import com.echothree.control.user.party.server.command.GetLanguageCommand;
import com.echothree.control.user.party.server.command.GetLanguagesCommand;
import com.echothree.control.user.party.server.command.GetNameSuffixesCommand;
import com.echothree.control.user.party.server.command.GetPersonalTitlesCommand;
import com.echothree.control.user.party.server.command.GetTimeZoneCommand;
import com.echothree.control.user.party.server.command.GetTimeZonesCommand;
import com.echothree.control.user.queue.common.QueueUtil;
import com.echothree.control.user.queue.common.form.GetQueueTypeForm;
import com.echothree.control.user.queue.common.form.GetQueueTypesForm;
import com.echothree.control.user.queue.server.command.GetQueueTypeCommand;
import com.echothree.control.user.queue.server.command.GetQueueTypesCommand;
import com.echothree.control.user.search.common.SearchUtil;
import com.echothree.control.user.search.common.form.GetCustomerResultsForm;
import com.echothree.control.user.search.server.command.GetCustomerResultsCommand;
import com.echothree.control.user.uom.common.UomUtil;
import com.echothree.control.user.uom.common.form.GetUnitOfMeasureKindForm;
import com.echothree.control.user.uom.common.form.GetUnitOfMeasureKindUseForm;
import com.echothree.control.user.uom.common.form.GetUnitOfMeasureKindUseTypeForm;
import com.echothree.control.user.uom.common.form.GetUnitOfMeasureKindUseTypesForm;
import com.echothree.control.user.uom.common.form.GetUnitOfMeasureKindUsesForm;
import com.echothree.control.user.uom.common.form.GetUnitOfMeasureKindsForm;
import com.echothree.control.user.uom.common.form.GetUnitOfMeasureTypeForm;
import com.echothree.control.user.uom.common.form.GetUnitOfMeasureTypesForm;
import com.echothree.control.user.uom.server.command.GetUnitOfMeasureKindCommand;
import com.echothree.control.user.uom.server.command.GetUnitOfMeasureKindUseCommand;
import com.echothree.control.user.uom.server.command.GetUnitOfMeasureKindUseTypeCommand;
import com.echothree.control.user.uom.server.command.GetUnitOfMeasureKindUseTypesCommand;
import com.echothree.control.user.uom.server.command.GetUnitOfMeasureKindUsesCommand;
import com.echothree.control.user.uom.server.command.GetUnitOfMeasureKindsCommand;
import com.echothree.control.user.uom.server.command.GetUnitOfMeasureTypeCommand;
import com.echothree.control.user.uom.server.command.GetUnitOfMeasureTypesCommand;
import com.echothree.control.user.user.common.UserUtil;
import com.echothree.control.user.user.common.form.GetRecoveryQuestionForm;
import com.echothree.control.user.user.common.form.GetRecoveryQuestionsForm;
import com.echothree.control.user.user.common.form.GetUserLoginForm;
import com.echothree.control.user.user.server.command.GetRecoveryQuestionCommand;
import com.echothree.control.user.user.server.command.GetRecoveryQuestionsCommand;
import com.echothree.control.user.user.server.command.GetUserLoginCommand;
import com.echothree.model.control.accounting.server.graphql.CurrencyObject;
import com.echothree.model.control.content.server.graphql.ContentPageLayoutObject;
import com.echothree.model.control.core.server.graphql.ColorObject;
import com.echothree.model.control.core.server.graphql.EntityAttributeTypeObject;
import com.echothree.model.control.core.server.graphql.FontStyleObject;
import com.echothree.model.control.core.server.graphql.FontWeightObject;
import com.echothree.model.control.core.server.graphql.MimeTypeFileExtensionObject;
import com.echothree.model.control.core.server.graphql.MimeTypeObject;
import com.echothree.model.control.core.server.graphql.MimeTypeUsageTypeObject;
import com.echothree.model.control.core.server.graphql.TextDecorationObject;
import com.echothree.model.control.core.server.graphql.TextTransformationObject;
import com.echothree.model.control.item.server.graphql.ItemCategoryObject;
import com.echothree.model.control.item.server.graphql.ItemObject;
import com.echothree.model.control.party.server.graphql.DateTimeFormatObject;
import com.echothree.model.control.party.server.graphql.LanguageObject;
import com.echothree.model.control.party.server.graphql.NameSuffixObject;
import com.echothree.model.control.party.server.graphql.PersonalTitleObject;
import com.echothree.model.control.party.server.graphql.TimeZoneObject;
import com.echothree.model.control.queue.server.graphql.QueueTypeObject;
import com.echothree.model.control.search.server.graphql.CustomerResultsObject;
import com.echothree.model.control.uom.server.graphql.UnitOfMeasureKindObject;
import com.echothree.model.control.uom.server.graphql.UnitOfMeasureKindUseObject;
import com.echothree.model.control.uom.server.graphql.UnitOfMeasureKindUseTypeObject;
import com.echothree.model.control.uom.server.graphql.UnitOfMeasureTypeObject;
import com.echothree.model.control.user.server.graphql.RecoveryQuestionObject;
import com.echothree.model.control.user.server.graphql.UserLoginObject;
import com.echothree.model.control.user.server.graphql.UserSessionObject;
import com.echothree.model.control.user.server.graphql.UserVisitObject;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.content.server.entity.ContentPageLayout;
import com.echothree.model.data.core.server.entity.Color;
import com.echothree.model.data.core.server.entity.EntityAttributeType;
import com.echothree.model.data.core.server.entity.FontStyle;
import com.echothree.model.data.core.server.entity.FontWeight;
import com.echothree.model.data.core.server.entity.MimeType;
import com.echothree.model.data.core.server.entity.MimeTypeFileExtension;
import com.echothree.model.data.core.server.entity.MimeTypeUsageType;
import com.echothree.model.data.core.server.entity.TextDecoration;
import com.echothree.model.data.core.server.entity.TextTransformation;
import com.echothree.model.data.item.server.entity.Item;
import com.echothree.model.data.item.server.entity.ItemCategory;
import com.echothree.model.data.party.server.entity.DateTimeFormat;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.NameSuffix;
import com.echothree.model.data.party.server.entity.PersonalTitle;
import com.echothree.model.data.party.server.entity.TimeZone;
import com.echothree.model.data.queue.server.entity.QueueType;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKind;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKindUse;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKindUseType;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureType;
import com.echothree.model.data.user.server.entity.RecoveryQuestion;
import com.echothree.model.data.user.server.entity.UserLogin;
import com.echothree.model.data.user.server.entity.UserSession;
import com.echothree.model.data.user.server.entity.UserVisit;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import javax.naming.NamingException;

@GraphQLName("query")
public final class GraphQlQueries {

    @GraphQLField
    @GraphQLName("contentPageLayout")
    public static ContentPageLayoutObject contentPageLayout(final DataFetchingEnvironment env,
            @GraphQLName("contentPageLayoutName") final String contentPageLayoutName,
            @GraphQLName("id") final String id) {
        ContentPageLayout contentPageLayout;

        try {
            GraphQlContext context = env.getContext();
            GetContentPageLayoutForm commandForm = ContentUtil.getHome().getGetContentPageLayoutForm();

            commandForm.setContentPageLayoutName(contentPageLayoutName);
            commandForm.setUlid(id);
        
            contentPageLayout = new GetContentPageLayoutCommand(context.getUserVisitPK(), commandForm).runForGraphQl();
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
            GraphQlContext context = env.getContext();
            GetContentPageLayoutsForm commandForm = ContentUtil.getHome().getGetContentPageLayoutsForm();
        
            contentPageLayouts = new GetContentPageLayoutsCommand(context.getUserVisitPK(), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
        
        if(contentPageLayouts == null) {
            contentPageLayoutObjects = Collections.EMPTY_LIST;
        } else {
            contentPageLayoutObjects = new ArrayList<>(contentPageLayouts.size());

            contentPageLayouts.stream().map((contentPageLayout) -> {
                return new ContentPageLayoutObject(contentPageLayout);
            }).forEachOrdered((contentPageLayoutObject) -> {
                contentPageLayoutObjects.add(contentPageLayoutObject);
            });
        }
        
        return contentPageLayoutObjects;
    }

    @GraphQLField
    @GraphQLName("mimeTypeFileExtension")
    public static MimeTypeFileExtensionObject mimeTypeFileExtension(final DataFetchingEnvironment env,
            @GraphQLName("fileExtension") @GraphQLNonNull final String fileExtension) {
        MimeTypeFileExtension mimeTypeFileExtension;

        try {
            GraphQlContext context = env.getContext();
            GetMimeTypeFileExtensionForm commandForm = CoreUtil.getHome().getGetMimeTypeFileExtensionForm();

            commandForm.setFileExtension(fileExtension);

            mimeTypeFileExtension = new GetMimeTypeFileExtensionCommand(context.getUserVisitPK(), commandForm).runForGraphQl();
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
            GraphQlContext context = env.getContext();
            GetMimeTypeFileExtensionsForm commandForm = CoreUtil.getHome().getGetMimeTypeFileExtensionsForm();

            mimeTypeFileExtensions = new GetMimeTypeFileExtensionsCommand(context.getUserVisitPK(), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        if(mimeTypeFileExtensions == null) {
            mimeTypeFileExtensionObjects = Collections.EMPTY_LIST;
        } else {
            mimeTypeFileExtensionObjects = new ArrayList<>(mimeTypeFileExtensions.size());

            mimeTypeFileExtensions.stream().map((mimeTypeFileExtension) -> {
                return new MimeTypeFileExtensionObject(mimeTypeFileExtension);
            }).forEachOrdered((mimeTypeFileExtensionObject) -> {
                mimeTypeFileExtensionObjects.add(mimeTypeFileExtensionObject);
            });
        }

        return mimeTypeFileExtensionObjects;
    }

    @GraphQLField
    @GraphQLName("mimeTypeUsageType")
    public static MimeTypeUsageTypeObject mimeTypeUsageType(final DataFetchingEnvironment env,
            @GraphQLName("mimeTypeUsageTypeName") @GraphQLNonNull final String mimeTypeUsageTypeName) {
        MimeTypeUsageType mimeTypeUsageType;

        try {
            GraphQlContext context = env.getContext();
            GetMimeTypeUsageTypeForm commandForm = CoreUtil.getHome().getGetMimeTypeUsageTypeForm();

            commandForm.setMimeTypeUsageTypeName(mimeTypeUsageTypeName);

            mimeTypeUsageType = new GetMimeTypeUsageTypeCommand(context.getUserVisitPK(), commandForm).runForGraphQl();
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
            GraphQlContext context = env.getContext();
            GetMimeTypeUsageTypesForm commandForm = CoreUtil.getHome().getGetMimeTypeUsageTypesForm();

            mimeTypeUsageTypes = new GetMimeTypeUsageTypesCommand(context.getUserVisitPK(), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        if(mimeTypeUsageTypes == null) {
            mimeTypeUsageTypeObjects = Collections.EMPTY_LIST;
        } else {
            mimeTypeUsageTypeObjects = new ArrayList<>(mimeTypeUsageTypes.size());

            mimeTypeUsageTypes.stream().map((mimeTypeUsageType) -> {
                return new MimeTypeUsageTypeObject(mimeTypeUsageType);
            }).forEachOrdered((mimeTypeUsageTypeObject) -> {
                mimeTypeUsageTypeObjects.add(mimeTypeUsageTypeObject);
            });
        }

        return mimeTypeUsageTypeObjects;
    }

    @GraphQLField
    @GraphQLName("mimeType")
    public static MimeTypeObject mimeType(final DataFetchingEnvironment env,
            @GraphQLName("mimeTypeName") @GraphQLNonNull final String mimeTypeName) {
        MimeType mimeType;

        try {
            GraphQlContext context = env.getContext();
            GetMimeTypeForm commandForm = CoreUtil.getHome().getGetMimeTypeForm();

            commandForm.setMimeTypeName(mimeTypeName);

            mimeType = new GetMimeTypeCommand(context.getUserVisitPK(), commandForm).runForGraphQl();
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
            GraphQlContext context = env.getContext();
            GetMimeTypesForm commandForm = CoreUtil.getHome().getGetMimeTypesForm();

            commandForm.setMimeTypeUsageTypeName(mimeTypeUsageTypeName);

            mimeTypes = new GetMimeTypesCommand(context.getUserVisitPK(), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        if(mimeTypes == null) {
            mimeTypeObjects = Collections.EMPTY_LIST;
        } else {
            mimeTypeObjects = new ArrayList<>(mimeTypes.size());

            mimeTypes.stream().map((mimeType) -> {
                return new MimeTypeObject(mimeType);
            }).forEachOrdered((mimeTypeObject) -> {
                mimeTypeObjects.add(mimeTypeObject);
            });
        }

        return mimeTypeObjects;
    }

    @GraphQLField
    @GraphQLName("queueType")
    public static QueueTypeObject queueType(final DataFetchingEnvironment env,
                                            @GraphQLName("queueTypeName") @GraphQLNonNull final String queueTypeName) {
        QueueType queueType;

        try {
            GraphQlContext context = env.getContext();
            GetQueueTypeForm commandForm = QueueUtil.getHome().getGetQueueTypeForm();

            commandForm.setQueueTypeName(queueTypeName);

            queueType = new GetQueueTypeCommand(context.getUserVisitPK(), commandForm).runForGraphQl();
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
            GraphQlContext context = env.getContext();
            GetQueueTypesForm commandForm = QueueUtil.getHome().getGetQueueTypesForm();

            queueTypes = new GetQueueTypesCommand(context.getUserVisitPK(), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        if(queueTypes == null) {
            queueTypeObjects = Collections.EMPTY_LIST;
        } else {
            queueTypeObjects = new ArrayList<>(queueTypes.size());

            queueTypes.stream().map((queueType) -> {
                return new QueueTypeObject(queueType);
            }).forEachOrdered((queueTypeObject) -> {
                queueTypeObjects.add(queueTypeObject);
            });
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
            GraphQlContext context = env.getContext();
            GetUnitOfMeasureKindUseForm commandForm = UomUtil.getHome().getGetUnitOfMeasureKindUseForm();

            commandForm.setUnitOfMeasureKindUseTypeName(unitOfMeasureKindUseTypeName);
            commandForm.setUnitOfMeasureKindName(unitOfMeasureKindName);

            unitOfMeasureKindUse = new GetUnitOfMeasureKindUseCommand(context.getUserVisitPK(), commandForm).runForGraphQl();
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
            GraphQlContext context = env.getContext();
            GetUnitOfMeasureKindUsesForm commandForm = UomUtil.getHome().getGetUnitOfMeasureKindUsesForm();

            commandForm.setUnitOfMeasureKindUseTypeName(unitOfMeasureKindUseTypeName);
            commandForm.setUnitOfMeasureKindName(unitOfMeasureKindName);

            unitOfMeasureKindUses = new GetUnitOfMeasureKindUsesCommand(context.getUserVisitPK(), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        if(unitOfMeasureKindUses == null) {
            unitOfMeasureKindUseObjects = Collections.EMPTY_LIST;
        } else {
            unitOfMeasureKindUseObjects = new ArrayList<>(unitOfMeasureKindUses.size());

            unitOfMeasureKindUses.stream().map((unitOfMeasureKindUse) -> {
                return new UnitOfMeasureKindUseObject(unitOfMeasureKindUse);
            }).forEachOrdered((unitOfMeasureKindUseObject) -> {
                unitOfMeasureKindUseObjects.add(unitOfMeasureKindUseObject);
            });
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
            GraphQlContext context = env.getContext();
            GetUnitOfMeasureTypeForm commandForm = UomUtil.getHome().getGetUnitOfMeasureTypeForm();

            commandForm.setUnitOfMeasureKindName(unitOfMeasureKindName);
            commandForm.setUnitOfMeasureTypeName(unitOfMeasureTypeName);
        
            unitOfMeasureType = new GetUnitOfMeasureTypeCommand(context.getUserVisitPK(), commandForm).runForGraphQl();
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
            GraphQlContext context = env.getContext();
            GetUnitOfMeasureTypesForm commandForm = UomUtil.getHome().getGetUnitOfMeasureTypesForm();
        
            commandForm.setUnitOfMeasureKindName(unitOfMeasureKindName);
            
            unitOfMeasureTypes = new GetUnitOfMeasureTypesCommand(context.getUserVisitPK(), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
        
        if(unitOfMeasureTypes == null) {
            unitOfMeasureTypeObjects = Collections.EMPTY_LIST;
        } else {
            unitOfMeasureTypeObjects = new ArrayList<>(unitOfMeasureTypes.size());

            unitOfMeasureTypes.stream().map((unitOfMeasureType) -> {
                return new UnitOfMeasureTypeObject(unitOfMeasureType);
            }).forEachOrdered((unitOfMeasureTypeObject) -> {
                unitOfMeasureTypeObjects.add(unitOfMeasureTypeObject);
            });
        }
        
        return unitOfMeasureTypeObjects;
    }
    
    @GraphQLField
    @GraphQLName("unitOfMeasureKind")
    public static UnitOfMeasureKindObject unitOfMeasureKind(final DataFetchingEnvironment env,
            @GraphQLName("unitOfMeasureKindName") @GraphQLNonNull final String unitOfMeasureKindName) {
        UnitOfMeasureKind unitOfMeasureKind;

        try {
            GraphQlContext context = env.getContext();
            GetUnitOfMeasureKindForm commandForm = UomUtil.getHome().getGetUnitOfMeasureKindForm();

            commandForm.setUnitOfMeasureKindName(unitOfMeasureKindName);
        
            unitOfMeasureKind = new GetUnitOfMeasureKindCommand(context.getUserVisitPK(), commandForm).runForGraphQl();
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
            GraphQlContext context = env.getContext();
            GetUnitOfMeasureKindsForm commandForm = UomUtil.getHome().getGetUnitOfMeasureKindsForm();
        
            unitOfMeasureKinds = new GetUnitOfMeasureKindsCommand(context.getUserVisitPK(), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
        
        if(unitOfMeasureKinds == null) {
            unitOfMeasureKindObjects = Collections.EMPTY_LIST;
        } else {
            unitOfMeasureKindObjects = new ArrayList<>(unitOfMeasureKinds.size());

            unitOfMeasureKinds.stream().map((unitOfMeasureKind) -> {
                return new UnitOfMeasureKindObject(unitOfMeasureKind);
            }).forEachOrdered((unitOfMeasureKindObject) -> {
                unitOfMeasureKindObjects.add(unitOfMeasureKindObject);
            });
        }
        
        return unitOfMeasureKindObjects;
    }
    
    @GraphQLField
    @GraphQLName("unitOfMeasureKindUseType")
    public static UnitOfMeasureKindUseTypeObject unitOfMeasureKindUseType(final DataFetchingEnvironment env,
            @GraphQLName("unitOfMeasureKindUseTypeName") @GraphQLNonNull final String unitOfMeasureKindUseTypeName) {
        UnitOfMeasureKindUseType unitOfMeasureKindUseType;

        try {
            GraphQlContext context = env.getContext();
            GetUnitOfMeasureKindUseTypeForm commandForm = UomUtil.getHome().getGetUnitOfMeasureKindUseTypeForm();

            commandForm.setUnitOfMeasureKindUseTypeName(unitOfMeasureKindUseTypeName);
        
            unitOfMeasureKindUseType = new GetUnitOfMeasureKindUseTypeCommand(context.getUserVisitPK(), commandForm).runForGraphQl();
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
            GraphQlContext context = env.getContext();
            GetUnitOfMeasureKindUseTypesForm commandForm = UomUtil.getHome().getGetUnitOfMeasureKindUseTypesForm();
        
            unitOfMeasureKindUseTypes = new GetUnitOfMeasureKindUseTypesCommand(context.getUserVisitPK(), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
        
        if(unitOfMeasureKindUseTypes == null) {
            unitOfMeasureKindUseTypeObjects = Collections.EMPTY_LIST;
        } else {
            unitOfMeasureKindUseTypeObjects = new ArrayList<>(unitOfMeasureKindUseTypes.size());

            unitOfMeasureKindUseTypes.stream().map((unitOfMeasureKindUseType) -> {
                return new UnitOfMeasureKindUseTypeObject(unitOfMeasureKindUseType);
            }).forEachOrdered((unitOfMeasureKindUseTypeObject) -> {
                unitOfMeasureKindUseTypeObjects.add(unitOfMeasureKindUseTypeObject);
            });
        }
        
        return unitOfMeasureKindUseTypeObjects;
    }
    
    @GraphQLField
    @GraphQLName("entityAttributeType")
    public static EntityAttributeTypeObject entityAttributeType(final DataFetchingEnvironment env,
            @GraphQLName("entityAttributeTypeName") @GraphQLNonNull final String entityAttributeTypeName) {
        EntityAttributeType entityAttributeType;

        try {
            GraphQlContext context = env.getContext();
            GetEntityAttributeTypeForm commandForm = CoreUtil.getHome().getGetEntityAttributeTypeForm();

            commandForm.setEntityAttributeTypeName(entityAttributeTypeName);
        
            entityAttributeType = new GetEntityAttributeTypeCommand(context.getUserVisitPK(), commandForm).runForGraphQl();
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
            GraphQlContext context = env.getContext();
            GetEntityAttributeTypesForm commandForm = CoreUtil.getHome().getGetEntityAttributeTypesForm();
        
            entityAttributeTypes = new GetEntityAttributeTypesCommand(context.getUserVisitPK(), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
        
        if(entityAttributeTypes == null) {
            entityAttributeTypeObjects = Collections.EMPTY_LIST;
        } else {
            entityAttributeTypeObjects = new ArrayList<>(entityAttributeTypes.size());

            entityAttributeTypes.stream().map((entityAttributeType) -> {
                return new EntityAttributeTypeObject(entityAttributeType);
            }).forEachOrdered((entityAttributeTypeObject) -> {
                entityAttributeTypeObjects.add(entityAttributeTypeObject);
            });
        }
        
        return entityAttributeTypeObjects;
    }
    
    @GraphQLField
    @GraphQLName("customerResults")
    public static CustomerResultsObject customerResults(final DataFetchingEnvironment env,
            @GraphQLName("searchTypeName") @GraphQLNonNull final String searchTypeName) {
        CustomerResultsObject customerResultsObject = new CustomerResultsObject();
        
        try {
            GraphQlContext context = env.getContext();
            GetCustomerResultsForm commandForm = SearchUtil.getHome().getGetCustomerResultsForm();

            commandForm.setSearchTypeName(searchTypeName);
            
            if(new GetCustomerResultsCommand(context.getUserVisitPK(), commandForm).canGetResultsForGraphQl()) {
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
            @GraphQLName("id") final String id) {
        Color color;

        try {
            GraphQlContext context = env.getContext();
            GetColorForm commandForm = CoreUtil.getHome().getGetColorForm();

            commandForm.setColorName(colorName);
            commandForm.setUlid(id);
        
            color = new GetColorCommand(context.getUserVisitPK(), commandForm).runForGraphQl();
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
            GraphQlContext context = env.getContext();
            GetColorsForm commandForm = CoreUtil.getHome().getGetColorsForm();
        
            colors = new GetColorsCommand(context.getUserVisitPK(), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
        
        if(colors == null) {
            colorObjects = Collections.EMPTY_LIST;
        } else {
            colorObjects = new ArrayList<>(colors.size());

            colors.stream().map((color) -> {
                return new ColorObject(color);
            }).forEachOrdered((colorObject) -> {
                colorObjects.add(colorObject);
            });
        }
        
        return colorObjects;
    }

    @GraphQLField
    @GraphQLName("fontStyle")
    public static FontStyleObject fontStyle(final DataFetchingEnvironment env,
            @GraphQLName("fontStyleName") final String fontStyleName,
            @GraphQLName("id") final String id) {
        FontStyle fontStyle;

        try {
            GraphQlContext context = env.getContext();
            GetFontStyleForm commandForm = CoreUtil.getHome().getGetFontStyleForm();

            commandForm.setFontStyleName(fontStyleName);
            commandForm.setUlid(id);
        
            fontStyle = new GetFontStyleCommand(context.getUserVisitPK(), commandForm).runForGraphQl();
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
            GraphQlContext context = env.getContext();
            GetFontStylesForm commandForm = CoreUtil.getHome().getGetFontStylesForm();
        
            fontStyles = new GetFontStylesCommand(context.getUserVisitPK(), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
        
        if(fontStyles == null) {
            fontStyleObjects = Collections.EMPTY_LIST;
        } else {
            fontStyleObjects = new ArrayList<>(fontStyles.size());

            fontStyles.stream().map((fontStyle) -> {
                return new FontStyleObject(fontStyle);
            }).forEachOrdered((fontStyleObject) -> {
                fontStyleObjects.add(fontStyleObject);
            });
        }
        
        return fontStyleObjects;
    }

    @GraphQLField
    @GraphQLName("fontWeight")
    public static FontWeightObject fontWeight(final DataFetchingEnvironment env,
            @GraphQLName("fontWeightName") final String fontWeightName,
            @GraphQLName("id") final String id) {
        FontWeight fontWeight;

        try {
            GraphQlContext context = env.getContext();
            GetFontWeightForm commandForm = CoreUtil.getHome().getGetFontWeightForm();

            commandForm.setFontWeightName(fontWeightName);
            commandForm.setUlid(id);
        
            fontWeight = new GetFontWeightCommand(context.getUserVisitPK(), commandForm).runForGraphQl();
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
            GraphQlContext context = env.getContext();
            GetFontWeightsForm commandForm = CoreUtil.getHome().getGetFontWeightsForm();
        
            fontWeights = new GetFontWeightsCommand(context.getUserVisitPK(), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
        
        if(fontWeights == null) {
            fontWeightObjects = Collections.EMPTY_LIST;
        } else {
            fontWeightObjects = new ArrayList<>(fontWeights.size());

            fontWeights.stream().map((fontWeight) -> {
                return new FontWeightObject(fontWeight);
            }).forEachOrdered((fontWeightObject) -> {
                fontWeightObjects.add(fontWeightObject);
            });
        }
        
        return fontWeightObjects;
    }

    @GraphQLField
    @GraphQLName("textDecoration")
    public static TextDecorationObject textDecoration(final DataFetchingEnvironment env,
            @GraphQLName("textDecorationName") final String textDecorationName,
            @GraphQLName("id") final String id) {
        TextDecoration textDecoration;

        try {
            GraphQlContext context = env.getContext();
            GetTextDecorationForm commandForm = CoreUtil.getHome().getGetTextDecorationForm();

            commandForm.setTextDecorationName(textDecorationName);
            commandForm.setUlid(id);
        
            textDecoration = new GetTextDecorationCommand(context.getUserVisitPK(), commandForm).runForGraphQl();
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
            GraphQlContext context = env.getContext();
            GetTextDecorationsForm commandForm = CoreUtil.getHome().getGetTextDecorationsForm();
        
            textDecorations = new GetTextDecorationsCommand(context.getUserVisitPK(), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
        
        if(textDecorations == null) {
            textDecorationObjects = Collections.EMPTY_LIST;
        } else {
            textDecorationObjects = new ArrayList<>(textDecorations.size());

            textDecorations.stream().map((textDecoration) -> {
                return new TextDecorationObject(textDecoration);
            }).forEachOrdered((textDecorationObject) -> {
                textDecorationObjects.add(textDecorationObject);
            });
        }
        
        return textDecorationObjects;
    }

    @GraphQLField
    @GraphQLName("textTransformation")
    public static TextTransformationObject textTransformation(final DataFetchingEnvironment env,
            @GraphQLName("textTransformationName") final String textTransformationName,
            @GraphQLName("id") final String id) {
        TextTransformation textTransformation;

        try {
            GraphQlContext context = env.getContext();
            GetTextTransformationForm commandForm = CoreUtil.getHome().getGetTextTransformationForm();

            commandForm.setTextTransformationName(textTransformationName);
            commandForm.setUlid(id);
        
            textTransformation = new GetTextTransformationCommand(context.getUserVisitPK(), commandForm).runForGraphQl();
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
            GraphQlContext context = env.getContext();
            GetTextTransformationsForm commandForm = CoreUtil.getHome().getGetTextTransformationsForm();
        
            textTransformations = new GetTextTransformationsCommand(context.getUserVisitPK(), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
        
        if(textTransformations == null) {
            textTransformationObjects = Collections.EMPTY_LIST;
        } else {
            textTransformationObjects = new ArrayList<>(textTransformations.size());

            textTransformations.stream().map((textTransformation) -> {
                return new TextTransformationObject(textTransformation);
            }).forEachOrdered((textTransformationObject) -> {
                textTransformationObjects.add(textTransformationObject);
            });
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
            GraphQlContext context = env.getContext();
            GetUserLoginForm commandForm = UserUtil.getHome().getGetUserLoginForm();

            commandForm.setUsername(username);
            commandForm.setUlid(partyId);
        
            GetUserLoginCommand getUserLoginCommand = new GetUserLoginCommand(context.getUserVisitPK(), commandForm);
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
            @GraphQLName("id") final String id,
            @GraphQLName("username") final String username) {
        RecoveryQuestion recoveryQuestion;

        try {
            GraphQlContext context = env.getContext();
            GetRecoveryQuestionForm commandForm = UserUtil.getHome().getGetRecoveryQuestionForm();

            commandForm.setRecoveryQuestionName(recoveryQuestionName);
            commandForm.setUlid(id);
            commandForm.setUsername(username);
        
            recoveryQuestion = new GetRecoveryQuestionCommand(context.getUserVisitPK(), commandForm).runForGraphQl();
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
            GraphQlContext context = env.getContext();
            GetRecoveryQuestionsForm commandForm = UserUtil.getHome().getGetRecoveryQuestionsForm();
        
            recoveryQuestions = new GetRecoveryQuestionsCommand(context.getUserVisitPK(), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
        
        if(recoveryQuestions == null) {
            recoveryQuestionObjects = Collections.EMPTY_LIST;
        } else {
            recoveryQuestionObjects = new ArrayList<>(recoveryQuestions.size());

            recoveryQuestions.stream().map((recoveryQuestion) -> {
                return new RecoveryQuestionObject(recoveryQuestion);
            }).forEachOrdered((recoveryQuestionObject) -> {
                recoveryQuestionObjects.add(recoveryQuestionObject);
            });
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
            @GraphQLName("id") final String id) {
        Currency currency;

        try {
            GraphQlContext context = env.getContext();
            GetCurrencyForm commandForm = AccountingUtil.getHome().getGetCurrencyForm();

            commandForm.setCurrencyIsoName(currencyIsoName);
            commandForm.setUlid(id);
        
            currency = new GetCurrencyCommand(context.getUserVisitPK(), commandForm).runForGraphQl();
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
            GraphQlContext context = env.getContext();
            GetCurrenciesForm commandForm = AccountingUtil.getHome().getGetCurrenciesForm();
        
            currencies = new GetCurrenciesCommand(context.getUserVisitPK(), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
        
        if(currencies == null) {
            currencyObjects = Collections.EMPTY_LIST;
        } else {
            currencyObjects = new ArrayList<>(currencies.size());

            currencies.stream().map((currency) -> {
                return new CurrencyObject(currency);
            }).forEachOrdered((currencyObject) -> {
                currencyObjects.add(currencyObject);
            });
        }
        
        return currencyObjects;
    }
    
    @GraphQLField
    @GraphQLName("language")
    public static LanguageObject language(final DataFetchingEnvironment env,
            @GraphQLName("languageIsoName") final String languageIsoName,
            @GraphQLName("id") final String id) {
        Language language;

        try {
            GraphQlContext context = env.getContext();
            GetLanguageForm commandForm = PartyUtil.getHome().getGetLanguageForm();

            commandForm.setLanguageIsoName(languageIsoName);
            commandForm.setUlid(id);
        
            language = new GetLanguageCommand(context.getUserVisitPK(), commandForm).runForGraphQl();
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
            GraphQlContext context = env.getContext();
            GetLanguagesForm commandForm = PartyUtil.getHome().getGetLanguagesForm();
        
            languages = new GetLanguagesCommand(context.getUserVisitPK(), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
        
        if(languages == null) {
            languageObjects = Collections.EMPTY_LIST;
        } else {
            languageObjects = new ArrayList<>(languages.size());

            languages.stream().map((language) -> {
                return new LanguageObject(language);
            }).forEachOrdered((languageObject) -> {
                languageObjects.add(languageObject);
            });
        }
        
        return languageObjects;
    }

    @GraphQLField
    @GraphQLName("dateTimeFormat")
    public static DateTimeFormatObject dateTimeFormat(final DataFetchingEnvironment env,
            @GraphQLName("dateTimeFormatName") final String dateTimeFormatName,
            @GraphQLName("id") final String id) {
        DateTimeFormat dateTimeFormat;

        try {
            GraphQlContext context = env.getContext();
            GetDateTimeFormatForm commandForm = PartyUtil.getHome().getGetDateTimeFormatForm();

            commandForm.setDateTimeFormatName(dateTimeFormatName);
            commandForm.setUlid(id);
        
            dateTimeFormat = new GetDateTimeFormatCommand(context.getUserVisitPK(), commandForm).runForGraphQl();
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
            GraphQlContext context = env.getContext();
            GetDateTimeFormatsForm commandForm = PartyUtil.getHome().getGetDateTimeFormatsForm();
        
            dateTimeFormats = new GetDateTimeFormatsCommand(context.getUserVisitPK(), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
        
        if(dateTimeFormats == null) {
            dateTimeFormatObjects = Collections.EMPTY_LIST;
        } else {
            dateTimeFormatObjects = new ArrayList<>(dateTimeFormats.size());

            dateTimeFormats.stream().map((dateTimeFormat) -> {
                return new DateTimeFormatObject(dateTimeFormat);
            }).forEachOrdered((dateTimeFormatObject) -> {
                dateTimeFormatObjects.add(dateTimeFormatObject);
            });
        }
        
        return dateTimeFormatObjects;
    }

    @GraphQLField
    @GraphQLName("timeZone")
    public static TimeZoneObject timeZone(final DataFetchingEnvironment env,
            @GraphQLName("javaTimeZoneName") final String javaTimeZoneName,
            @GraphQLName("id") final String id) {
        TimeZone timeZone;

        try {
            GraphQlContext context = env.getContext();
            GetTimeZoneForm commandForm = PartyUtil.getHome().getGetTimeZoneForm();

            commandForm.setJavaTimeZoneName(javaTimeZoneName);
            commandForm.setUlid(id);
        
            timeZone = new GetTimeZoneCommand(context.getUserVisitPK(), commandForm).runForGraphQl();
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
            GraphQlContext context = env.getContext();
            GetTimeZonesForm commandForm = PartyUtil.getHome().getGetTimeZonesForm();
        
            timeZones = new GetTimeZonesCommand(context.getUserVisitPK(), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
        
        if(timeZones == null) {
            timeZoneObjects = Collections.EMPTY_LIST;
        } else {
            timeZoneObjects = new ArrayList<>(timeZones.size());

            timeZones.stream().map((timeZone) -> {
                return new TimeZoneObject(timeZone);
            }).forEachOrdered((timeZoneObject) -> {
                timeZoneObjects.add(timeZoneObject);
            });
        }
        
        return timeZoneObjects;
    }

    @GraphQLField
    @GraphQLName("item")
    public static ItemObject item(final DataFetchingEnvironment env,
            @GraphQLName("itemName") final String itemName,
            @GraphQLName("itemNameOrAlias") final String itemNameOrAlias,
            @GraphQLName("id") final String id) {
        Item item;

        try {
            GraphQlContext context = env.getContext();
            GetItemForm commandForm = ItemUtil.getHome().getGetItemForm();

            commandForm.setItemName(itemName);
            commandForm.setItemNameOrAlias(itemNameOrAlias);
            commandForm.setUlid(id);
        
            item = new GetItemCommand(context.getUserVisitPK(), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
        
        return item == null ? null : new ItemObject(item);
    }
    
    @GraphQLField
    @GraphQLName("itemCategory")
    public static ItemCategoryObject itemCategory(final DataFetchingEnvironment env,
            @GraphQLName("itemCategoryName") final String itemCategoryName,
            @GraphQLName("id") final String id) {
        ItemCategory itemCategory;

        try {
            GraphQlContext context = env.getContext();
            GetItemCategoryForm commandForm = ItemUtil.getHome().getGetItemCategoryForm();

            commandForm.setItemCategoryName(itemCategoryName);
            commandForm.setUlid(id);
        
            itemCategory = new GetItemCategoryCommand(context.getUserVisitPK(), commandForm).runForGraphQl();
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
            GraphQlContext context = env.getContext();
            GetItemCategoriesForm commandForm = ItemUtil.getHome().getGetItemCategoriesForm();

            commandForm.setParentItemCategoryName(parentItemCategoryName);
        
            itemCategories = new GetItemCategoriesCommand(context.getUserVisitPK(), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
        
        if(itemCategories == null) {
            itemCategoryObjects = Collections.EMPTY_LIST;
        } else {
            itemCategoryObjects = new ArrayList<>(itemCategories.size());

            itemCategories.stream().map((itemCategory) -> {
                return new ItemCategoryObject(itemCategory);
            }).forEachOrdered((itemCategoryObject) -> {
                itemCategoryObjects.add(itemCategoryObject);
            });
        }
        
        return itemCategoryObjects;
    }

    @GraphQLField
    @GraphQLName("personalTitles")
    public static Collection<PersonalTitleObject> personalTitles(final DataFetchingEnvironment env) {
        Collection<PersonalTitle> personalTitles;
        
        try {
            GraphQlContext context = env.getContext();
            GetPersonalTitlesForm commandForm = PartyUtil.getHome().getGetPersonalTitlesForm();
        
            personalTitles = new GetPersonalTitlesCommand(context.getUserVisitPK(), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
        
        Collection<PersonalTitleObject> personalTitleObjects = new ArrayList<>(personalTitles.size());
        
        personalTitles.stream().map((personalTitle) -> {
            return new PersonalTitleObject(personalTitle);
        }).forEachOrdered((personalTitleObject) -> {
            personalTitleObjects.add(personalTitleObject);
        });
        
        return personalTitleObjects;
    }

    @GraphQLField
    @GraphQLName("nameSuffixes")
    public static Collection<NameSuffixObject> nameSuffixes(final DataFetchingEnvironment env) {
        Collection<NameSuffix> nameSuffixes;
        
        try {
            GraphQlContext context = env.getContext();
            GetNameSuffixesForm commandForm = PartyUtil.getHome().getGetNameSuffixesForm();
        
            nameSuffixes = new GetNameSuffixesCommand(context.getUserVisitPK(), commandForm).runForGraphQl();
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }

        Collection<NameSuffixObject> nameSuffixObjects = new ArrayList<>(nameSuffixes.size());

        nameSuffixes.stream().map((nameSuffix) -> {
            return new NameSuffixObject(nameSuffix);
        }).forEachOrdered((nameSuffixObject) -> {
            nameSuffixObjects.add(nameSuffixObject);
        });
        
        return nameSuffixObjects;
    }

}
