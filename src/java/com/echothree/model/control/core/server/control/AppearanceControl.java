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

package com.echothree.model.control.core.server.control;

import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.core.common.choice.AppearanceChoicesBean;
import com.echothree.model.control.core.common.transfer.AppearanceDescriptionTransfer;
import com.echothree.model.control.core.common.transfer.AppearanceTextDecorationTransfer;
import com.echothree.model.control.core.common.transfer.AppearanceTextTransformationTransfer;
import com.echothree.model.control.core.common.transfer.AppearanceTransfer;
import com.echothree.model.control.core.common.transfer.EntityAppearanceTransfer;
import com.echothree.model.control.core.server.transfer.AppearanceDescriptionTransferCache;
import com.echothree.model.control.core.server.transfer.AppearanceTextDecorationTransferCache;
import com.echothree.model.control.core.server.transfer.AppearanceTextTransformationTransferCache;
import com.echothree.model.control.core.server.transfer.AppearanceTransferCache;
import com.echothree.model.control.core.server.transfer.EntityAppearanceTransferCache;
import com.echothree.model.data.core.common.pk.AppearancePK;
import com.echothree.model.data.core.server.entity.Appearance;
import com.echothree.model.data.core.server.entity.AppearanceDescription;
import com.echothree.model.data.core.server.entity.AppearanceTextDecoration;
import com.echothree.model.data.core.server.entity.AppearanceTextTransformation;
import com.echothree.model.data.core.server.entity.Color;
import com.echothree.model.data.core.server.entity.EntityAppearance;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.FontStyle;
import com.echothree.model.data.core.server.entity.FontWeight;
import com.echothree.model.data.core.server.entity.TextDecoration;
import com.echothree.model.data.core.server.entity.TextTransformation;
import com.echothree.model.data.core.server.factory.AppearanceDescriptionFactory;
import com.echothree.model.data.core.server.factory.AppearanceDetailFactory;
import com.echothree.model.data.core.server.factory.AppearanceFactory;
import com.echothree.model.data.core.server.factory.AppearanceTextDecorationFactory;
import com.echothree.model.data.core.server.factory.AppearanceTextTransformationFactory;
import com.echothree.model.data.core.server.factory.EntityAppearanceFactory;
import com.echothree.model.data.core.server.value.AppearanceDescriptionValue;
import com.echothree.model.data.core.server.value.AppearanceDetailValue;
import com.echothree.model.data.core.server.value.AppearanceTextDecorationValue;
import com.echothree.model.data.core.server.value.AppearanceTextTransformationValue;
import com.echothree.model.data.core.server.value.EntityAppearanceValue;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.user.server.entity.UserVisit;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.APPEARANCES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.APPEARANCES_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.APPEARANCE_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.APPEARANCE_TEXT_DECORATIONS_APPEARANCE_FK;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.APPEARANCE_TEXT_DECORATIONS_TEXT_DECORATION_FK;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.APPEARANCE_TEXT_TRANSFORMATIONS_APPEARANCE_FK;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.APPEARANCE_TEXT_TRANSFORMATIONS_TEXT_TRANSFORMATION_FK;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.COMPONENT_VENDORS_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.ENTITY_APPEARANCES_ENTITY_INSTANCE_FK;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.ENTITY_INSTANCES_ENTITY_TYPE_FK;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.ENTITY_TYPES_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.ENTITY_TYPE_DETAILS_COMPONENT_VENDOR_FK;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.TEXT_DECORATIONS_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.TEXT_TRANSFORMATIONS_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.tables.core.AppearanceDescriptions.AppearanceDescriptions;
import static com.echothree.model.jooq.server.tables.core.AppearanceDetails.AppearanceDetails;
import static com.echothree.model.jooq.server.tables.core.AppearanceTextDecorations.AppearanceTextDecorations;
import static com.echothree.model.jooq.server.tables.core.AppearanceTextTransformations.AppearanceTextTransformations;
import static com.echothree.model.jooq.server.tables.core.Appearances.Appearances;
import static com.echothree.model.jooq.server.tables.core.ComponentVendorDetails.ComponentVendorDetails;
import static com.echothree.model.jooq.server.tables.core.ComponentVendors.ComponentVendors;
import static com.echothree.model.jooq.server.tables.core.EntityAppearances.EntityAppearances;
import static com.echothree.model.jooq.server.tables.core.EntityInstances.EntityInstances;
import static com.echothree.model.jooq.server.tables.core.EntityTypeDetails.EntityTypeDetails;
import static com.echothree.model.jooq.server.tables.core.EntityTypes.EntityTypes;
import static com.echothree.model.jooq.server.tables.core.TextDecorationDetails.TextDecorationDetails;
import static com.echothree.model.jooq.server.tables.core.TextDecorations.TextDecorations;
import static com.echothree.model.jooq.server.tables.core.TextTransformationDetails.TextTransformationDetails;
import static com.echothree.model.jooq.server.tables.core.TextTransformations.TextTransformations;
import static com.echothree.model.jooq.server.tables.party.Languages.Languages;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.cdi.CommandScope;
import com.echothree.util.server.control.BaseModelControl;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import javax.inject.Inject;

@CommandScope
public class AppearanceControl
        extends BaseModelControl {

    /** Creates a new instance of AppearanceControl */
    protected AppearanceControl() {
        super();
    }

    // --------------------------------------------------------------------------------
    //   Appearances
    // --------------------------------------------------------------------------------

    @Inject
    protected AppearanceDetailFactory appearanceDetailFactory;

    @Inject
    protected AppearanceFactory appearanceFactory;

    @Inject
    AppearanceTransferCache appearanceTransferCache;

    public Appearance createAppearance(String appearanceName, Color textColor, Color backgroundColor, FontStyle fontStyle, FontWeight fontWeight,
            Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultAppearance = getDefaultAppearance();
        var defaultFound = defaultAppearance != null;

        if(defaultFound && isDefault) {
            var defaultAppearanceDetailValue = getDefaultAppearanceDetailValueForUpdate();

            defaultAppearanceDetailValue.setIsDefault(false);
            updateAppearanceFromValue(defaultAppearanceDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var appearance = appearanceFactory.create();
        var appearanceDetail = appearanceDetailFactory.create(appearance, appearanceName, textColor, backgroundColor, fontStyle,
                fontWeight, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);

        // Convert to R/W
        appearance = appearanceFactory.getEntityFromPK(EntityPermission.READ_WRITE, appearance.getPrimaryKey());
        appearance.setActiveDetail(appearanceDetail);
        appearance.setLastDetail(appearanceDetail);
        appearance.store();

        sendEvent(appearance.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return appearance;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.Appearance */
    public Appearance getAppearanceByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new AppearancePK(entityInstance.getEntityUniqueId());
        var appearance = appearanceFactory.getEntityFromPK(entityPermission, pk);

        return appearance;
    }

    public Appearance getAppearanceByEntityInstance(EntityInstance entityInstance) {
        return getAppearanceByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public Appearance getAppearanceByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getAppearanceByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countAppearances() {
        return session.getDslContext()
                .selectCount()
                .from(Appearances)
                .join(AppearanceDetails).onKey(APPEARANCES_ACTIVE_DETAIL_FK)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public Appearance getAppearanceByName(String appearanceName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(Appearances.fields())
                .from(Appearances)
                .join(AppearanceDetails).onKey(APPEARANCES_ACTIVE_DETAIL_FK)
                .where(AppearanceDetails.APPEARANCE_NAME.eq(appearanceName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return appearanceFactory.getEntityFromQuery(entityPermission, query);
    }

    public Appearance getAppearanceByName(String appearanceName) {
        return getAppearanceByName(appearanceName, EntityPermission.READ_ONLY);
    }

    public Appearance getAppearanceByNameForUpdate(String appearanceName) {
        return getAppearanceByName(appearanceName, EntityPermission.READ_WRITE);
    }

    public AppearanceDetailValue getAppearanceDetailValueForUpdate(Appearance appearance) {
        return appearance == null? null: appearance.getLastDetailForUpdate().getAppearanceDetailValue().clone();
    }

    public AppearanceDetailValue getAppearanceDetailValueByNameForUpdate(String appearanceName) {
        return getAppearanceDetailValueForUpdate(getAppearanceByNameForUpdate(appearanceName));
    }

    private Appearance getDefaultAppearance(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(Appearances.fields())
                .from(Appearances)
                .join(AppearanceDetails).onKey(APPEARANCES_ACTIVE_DETAIL_FK)
                .where(AppearanceDetails.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return appearanceFactory.getEntityFromQuery(entityPermission, query);
    }

    public Appearance getDefaultAppearance() {
        return getDefaultAppearance(EntityPermission.READ_ONLY);
    }

    public Appearance getDefaultAppearanceForUpdate() {
        return getDefaultAppearance(EntityPermission.READ_WRITE);
    }

    public AppearanceDetailValue getDefaultAppearanceDetailValueForUpdate() {
        return getDefaultAppearanceForUpdate().getLastDetailForUpdate().getAppearanceDetailValue().clone();
    }

    private List<Appearance> getAppearances(EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(Appearances.fields())
                    .from(Appearances)
                    .join(AppearanceDetails).onKey(APPEARANCES_ACTIVE_DETAIL_FK)
                    .orderBy(AppearanceDetails.SORT_ORDER, AppearanceDetails.APPEARANCE_NAME),
                    AppearanceFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(Appearances.fields())
                    .from(Appearances)
                    .where(Appearances.ACTIVE_DETAIL.isNotNull())
                    .forUpdate();
        };

        return appearanceFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<Appearance> getAppearances() {
        return getAppearances(EntityPermission.READ_ONLY);
    }

    public List<Appearance> getAppearancesForUpdate() {
        return getAppearances(EntityPermission.READ_WRITE);
    }

    private List<Appearance> getAppearancesByTextColor(Color textColor, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(Appearances.fields())
                    .from(Appearances)
                    .join(AppearanceDetails).onKey(APPEARANCES_ACTIVE_DETAIL_FK)
                    .where(AppearanceDetails.TEXT_COLOR.eq(textColor.getPrimaryKey()))
                    .orderBy(AppearanceDetails.SORT_ORDER, AppearanceDetails.APPEARANCE_NAME),
                    AppearanceFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(Appearances.fields())
                    .from(Appearances)
                    .join(AppearanceDetails).onKey(APPEARANCES_ACTIVE_DETAIL_FK)
                    .where(AppearanceDetails.TEXT_COLOR.eq(textColor.getPrimaryKey()))
                    .forUpdate();
        };

        return appearanceFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<Appearance> getAppearancesByTextColor(Color textColor) {
        return getAppearancesByTextColor(textColor, EntityPermission.READ_ONLY);
    }

    public List<Appearance> getAppearancesByTextColorForUpdate(Color textColor) {
        return getAppearancesByTextColor(textColor, EntityPermission.READ_WRITE);
    }

    private List<Appearance> getAppearancesByBackgroundColor(Color backgroundColor, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(Appearances.fields())
                    .from(Appearances)
                    .join(AppearanceDetails).onKey(APPEARANCES_ACTIVE_DETAIL_FK)
                    .where(AppearanceDetails.BACKGROUND_COLOR.eq(backgroundColor.getPrimaryKey()))
                    .orderBy(AppearanceDetails.SORT_ORDER, AppearanceDetails.APPEARANCE_NAME),
                    AppearanceFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(Appearances.fields())
                    .from(Appearances)
                    .join(AppearanceDetails).onKey(APPEARANCES_ACTIVE_DETAIL_FK)
                    .where(AppearanceDetails.BACKGROUND_COLOR.eq(backgroundColor.getPrimaryKey()))
                    .forUpdate();
        };

        return appearanceFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<Appearance> getAppearancesByBackgroundColor(Color backgroundColor) {
        return getAppearancesByBackgroundColor(backgroundColor, EntityPermission.READ_ONLY);
    }

    public List<Appearance> getAppearancesByBackgroundColorForUpdate(Color backgroundColor) {
        return getAppearancesByBackgroundColor(backgroundColor, EntityPermission.READ_WRITE);
    }

    private List<Appearance> getAppearancesByFontStyle(FontStyle fontStyle, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(Appearances.fields())
                    .from(Appearances)
                    .join(AppearanceDetails).onKey(APPEARANCES_ACTIVE_DETAIL_FK)
                    .where(AppearanceDetails.FONT_STYLE.eq(fontStyle.getPrimaryKey()))
                    .orderBy(AppearanceDetails.SORT_ORDER, AppearanceDetails.APPEARANCE_NAME),
                    AppearanceFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(Appearances.fields())
                    .from(Appearances)
                    .join(AppearanceDetails).onKey(APPEARANCES_ACTIVE_DETAIL_FK)
                    .where(AppearanceDetails.FONT_STYLE.eq(fontStyle.getPrimaryKey()))
                    .forUpdate();
        };

        return appearanceFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<Appearance> getAppearancesByFontStyle(FontStyle fontStyle) {
        return getAppearancesByFontStyle(fontStyle, EntityPermission.READ_ONLY);
    }

    public List<Appearance> getAppearancesByFontStyleForUpdate(FontStyle fontStyle) {
        return getAppearancesByFontStyle(fontStyle, EntityPermission.READ_WRITE);
    }

    private List<Appearance> getAppearancesByFontWeight(FontWeight fontWeight, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(Appearances.fields())
                    .from(Appearances)
                    .join(AppearanceDetails).onKey(APPEARANCES_ACTIVE_DETAIL_FK)
                    .where(AppearanceDetails.FONT_WEIGHT.eq(fontWeight.getPrimaryKey()))
                    .orderBy(AppearanceDetails.SORT_ORDER, AppearanceDetails.APPEARANCE_NAME),
                    AppearanceFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(Appearances.fields())
                    .from(Appearances)
                    .join(AppearanceDetails).onKey(APPEARANCES_ACTIVE_DETAIL_FK)
                    .where(AppearanceDetails.FONT_WEIGHT.eq(fontWeight.getPrimaryKey()))
                    .forUpdate();
        };

        return appearanceFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<Appearance> getAppearancesByFontWeight(FontWeight fontWeight) {
        return getAppearancesByFontWeight(fontWeight, EntityPermission.READ_ONLY);
    }

    public List<Appearance> getAppearancesByFontWeightForUpdate(FontWeight fontWeight) {
        return getAppearancesByFontWeight(fontWeight, EntityPermission.READ_WRITE);
    }

    public AppearanceTransfer getAppearanceTransfer(UserVisit userVisit, Appearance appearance) {
        return appearanceTransferCache.getAppearanceTransfer(userVisit, appearance);
    }

    public List<AppearanceTransfer> getAppearanceTransfers(UserVisit userVisit, Collection<Appearance> appearances) {
        List<AppearanceTransfer> appearanceTransfers = new ArrayList<>(appearances.size());

        appearances.forEach((appearance) ->
                appearanceTransfers.add(appearanceTransferCache.getAppearanceTransfer(userVisit, appearance))
        );

        return appearanceTransfers;
    }

    public List<AppearanceTransfer> getAppearanceTransfers(UserVisit userVisit) {
        return getAppearanceTransfers(userVisit, getAppearances());
    }

    public AppearanceChoicesBean getAppearanceChoices(String defaultAppearanceChoice, Language language, boolean allowNullChoice) {
        var appearances = getAppearances();
        var size = appearances.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultAppearanceChoice == null) {
                defaultValue = "";
            }
        }

        for(var appearance : appearances) {
            var appearanceDetail = appearance.getLastDetail();

            var label = getBestAppearanceDescription(appearance, language);
            var value = appearanceDetail.getAppearanceName();

            labels.add(label == null? value: label);
            values.add(value);

            var usingDefaultChoice = defaultAppearanceChoice != null && defaultAppearanceChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && appearanceDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new AppearanceChoicesBean(labels, values, defaultValue);
    }

    private void updateAppearanceFromValue(AppearanceDetailValue appearanceDetailValue, boolean checkDefault, BasePK updatedBy) {
        if(appearanceDetailValue.hasBeenModified()) {
            var appearance = appearanceFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    appearanceDetailValue.getAppearancePK());
            var appearanceDetail = appearance.getActiveDetailForUpdate();

            appearanceDetail.setThruTime(session.getStartTime());
            appearanceDetail.store();

            var appearancePK = appearanceDetail.getAppearancePK(); // Not updated
            var appearanceName = appearanceDetailValue.getAppearanceName();
            var textColorPK = appearanceDetailValue.getTextColorPK();
            var backgroundColorPK = appearanceDetailValue.getBackgroundColorPK();
            var fontStylePK = appearanceDetailValue.getFontStylePK();
            var fontWeightPK = appearanceDetailValue.getFontWeightPK();
            var isDefault = appearanceDetailValue.getIsDefault();
            var sortOrder = appearanceDetailValue.getSortOrder();

            if(checkDefault) {
                var defaultAppearance = getDefaultAppearance();
                var defaultFound = defaultAppearance != null && !defaultAppearance.equals(appearance);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultAppearanceDetailValue = getDefaultAppearanceDetailValueForUpdate();

                    defaultAppearanceDetailValue.setIsDefault(false);
                    updateAppearanceFromValue(defaultAppearanceDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }

            appearanceDetail = appearanceDetailFactory.create(appearancePK, appearanceName, textColorPK, backgroundColorPK, fontStylePK,
                    fontWeightPK, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);

            appearance.setActiveDetail(appearanceDetail);
            appearance.setLastDetail(appearanceDetail);

            sendEvent(appearancePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public void updateAppearanceFromValue(AppearanceDetailValue appearanceDetailValue, BasePK updatedBy) {
        updateAppearanceFromValue(appearanceDetailValue, true, updatedBy);
    }

    private void deleteAppearance(Appearance appearance, boolean checkDefault, BasePK deletedBy) {
        var appearanceDetail = appearance.getLastDetailForUpdate();

        deleteAppearanceTextDecorationsByAppearance(appearance, deletedBy);
        deleteAppearanceTextTransformationsByAppearance(appearance, deletedBy);
        deleteAppearanceDescriptionsByAppearance(appearance, deletedBy);
        deleteEntityAppearancesByAppearance(appearance, deletedBy);

        appearanceDetail.setThruTime(session.getStartTime());
        appearance.setActiveDetail(null);
        appearance.store();

        if(checkDefault) {
            // Check for default, and pick one if necessary
            var defaultAppearance = getDefaultAppearance();

            if(defaultAppearance == null) {
                var appearances = getAppearancesForUpdate();

                if(!appearances.isEmpty()) {
                    var iter = appearances.iterator();
                    if(iter.hasNext()) {
                        defaultAppearance = iter.next();
                    }
                    var appearanceDetailValue = Objects.requireNonNull(defaultAppearance).getLastDetailForUpdate().getAppearanceDetailValue().clone();

                    appearanceDetailValue.setIsDefault(true);
                    updateAppearanceFromValue(appearanceDetailValue, false, deletedBy);
                }
            }
        }

        sendEvent(appearance.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    public void deleteAppearance(Appearance appearance, BasePK deletedBy) {
        deleteAppearance(appearance, true, deletedBy);
    }

    private void deleteAppearances(List<Appearance> appearances, boolean checkDefault, BasePK deletedBy) {
        appearances.forEach((appearance) -> deleteAppearance(appearance, checkDefault, deletedBy));
    }

    public void deleteAppearances(List<Appearance> appearances, BasePK deletedBy) {
        deleteAppearances(appearances, true, deletedBy);
    }

    public void deleteAppearancesByTextColor(Color textColor, BasePK deletedBy) {
        deleteAppearances(getAppearancesByTextColorForUpdate(textColor), deletedBy);
    }

    public void deleteAppearancesByBackgroundColor(Color backgroundColor, BasePK deletedBy) {
        deleteAppearances(getAppearancesByBackgroundColorForUpdate(backgroundColor), deletedBy);
    }

    public void deleteAppearancesByColor(Color color, BasePK deletedBy) {
        deleteAppearancesByTextColor(color, deletedBy);
        deleteAppearancesByBackgroundColor(color, deletedBy);
    }

    public void deleteAppearancesByFontStyle(FontStyle fontStyle, BasePK deletedBy) {
        deleteAppearances(getAppearancesByFontStyleForUpdate(fontStyle), deletedBy);
    }

    public void deleteAppearancesByFontWeight(FontWeight fontWeight, BasePK deletedBy) {
        deleteAppearances(getAppearancesByFontWeightForUpdate(fontWeight), deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Appearance Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    protected AppearanceDescriptionFactory appearanceDescriptionFactory;

    @Inject
    AppearanceDescriptionTransferCache appearanceDescriptionTransferCache;

    public AppearanceDescription createAppearanceDescription(Appearance appearance, Language language, String description, BasePK createdBy) {
        var appearanceDescription = appearanceDescriptionFactory.create(appearance, language, description,
                session.getStartTime(), Session.MAX_TIME);

        sendEvent(appearance.getPrimaryKey(), EventTypes.MODIFY, appearanceDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return appearanceDescription;
    }

    private AppearanceDescription getAppearanceDescription(Appearance appearance, Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(AppearanceDescriptions.fields())
                .from(AppearanceDescriptions)
                .where(AppearanceDescriptions.APPEARANCE.eq(appearance.getPrimaryKey()),
                        AppearanceDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        AppearanceDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return appearanceDescriptionFactory.getEntityFromQuery(entityPermission, query);
    }

    public AppearanceDescription getAppearanceDescription(Appearance appearance, Language language) {
        return getAppearanceDescription(appearance, language, EntityPermission.READ_ONLY);
    }

    public AppearanceDescription getAppearanceDescriptionForUpdate(Appearance appearance, Language language) {
        return getAppearanceDescription(appearance, language, EntityPermission.READ_WRITE);
    }

    public AppearanceDescriptionValue getAppearanceDescriptionValue(AppearanceDescription appearanceDescription) {
        return appearanceDescription == null? null: appearanceDescription.getAppearanceDescriptionValue().clone();
    }

    public AppearanceDescriptionValue getAppearanceDescriptionValueForUpdate(Appearance appearance, Language language) {
        return getAppearanceDescriptionValue(getAppearanceDescriptionForUpdate(appearance, language));
    }

    private List<AppearanceDescription> getAppearanceDescriptionsByAppearance(Appearance appearance, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(AppearanceDescriptions.fields())
                    .from(AppearanceDescriptions)
                    .join(Languages).onKey(APPEARANCE_DESCRIPTIONS_LANGUAGE_FK)
                    .where(AppearanceDescriptions.APPEARANCE.eq(appearance.getPrimaryKey()),
                            AppearanceDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    AppearanceDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(AppearanceDescriptions.fields())
                    .from(AppearanceDescriptions)
                    .where(AppearanceDescriptions.APPEARANCE.eq(appearance.getPrimaryKey()),
                            AppearanceDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return appearanceDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<AppearanceDescription> getAppearanceDescriptionsByAppearance(Appearance appearance) {
        return getAppearanceDescriptionsByAppearance(appearance, EntityPermission.READ_ONLY);
    }

    public List<AppearanceDescription> getAppearanceDescriptionsByAppearanceForUpdate(Appearance appearance) {
        return getAppearanceDescriptionsByAppearance(appearance, EntityPermission.READ_WRITE);
    }

    public String getBestAppearanceDescription(Appearance appearance, Language language) {
        String description;
        var appearanceDescription = getAppearanceDescription(appearance, language);

        if(appearanceDescription == null && !language.getIsDefault()) {
            appearanceDescription = getAppearanceDescription(appearance, partyControl.getDefaultLanguage());
        }

        if(appearanceDescription == null) {
            description = appearance.getLastDetail().getAppearanceName();
        } else {
            description = appearanceDescription.getDescription();
        }

        return description;
    }

    public AppearanceDescriptionTransfer getAppearanceDescriptionTransfer(UserVisit userVisit, AppearanceDescription appearanceDescription) {
        return appearanceDescriptionTransferCache.getAppearanceDescriptionTransfer(userVisit, appearanceDescription);
    }

    public List<AppearanceDescriptionTransfer> getAppearanceDescriptionTransfersByAppearance(UserVisit userVisit, Appearance appearance) {
        var appearanceDescriptions = getAppearanceDescriptionsByAppearance(appearance);
        List<AppearanceDescriptionTransfer> appearanceDescriptionTransfers = new ArrayList<>(appearanceDescriptions.size());

        appearanceDescriptions.forEach((appearanceDescription) ->
                appearanceDescriptionTransfers.add(appearanceDescriptionTransferCache.getAppearanceDescriptionTransfer(userVisit, appearanceDescription))
        );

        return appearanceDescriptionTransfers;
    }

    public void updateAppearanceDescriptionFromValue(AppearanceDescriptionValue appearanceDescriptionValue, BasePK updatedBy) {
        if(appearanceDescriptionValue.hasBeenModified()) {
            var appearanceDescription = appearanceDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    appearanceDescriptionValue.getPrimaryKey());

            appearanceDescription.setThruTime(session.getStartTime());
            appearanceDescription.store();

            var appearance = appearanceDescription.getAppearance();
            var language = appearanceDescription.getLanguage();
            var description = appearanceDescriptionValue.getDescription();

            appearanceDescription = appearanceDescriptionFactory.create(appearance, language, description,
                    session.getStartTime(), Session.MAX_TIME);

            sendEvent(appearance.getPrimaryKey(), EventTypes.MODIFY, appearanceDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteAppearanceDescription(AppearanceDescription appearanceDescription, BasePK deletedBy) {
        appearanceDescription.setThruTime(session.getStartTime());

        sendEvent(appearanceDescription.getAppearancePK(), EventTypes.MODIFY, appearanceDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }

    public void deleteAppearanceDescriptionsByAppearance(Appearance appearance, BasePK deletedBy) {
        var appearanceDescriptions = getAppearanceDescriptionsByAppearanceForUpdate(appearance);

        appearanceDescriptions.forEach((appearanceDescription) ->
                deleteAppearanceDescription(appearanceDescription, deletedBy)
        );
    }

    // --------------------------------------------------------------------------------
    //   Appearance Text Decorations
    // --------------------------------------------------------------------------------

    @Inject
    protected AppearanceTextDecorationFactory appearanceTextDecorationFactory;

    @Inject
    AppearanceTextDecorationTransferCache appearanceTextDecorationTransferCache;

    public AppearanceTextDecoration createAppearanceTextDecoration(Appearance appearance, TextDecoration textDecoration, BasePK createdBy) {
        var appearanceTextDecoration = appearanceTextDecorationFactory.create(appearance, textDecoration,
                session.getStartTime(), Session.MAX_TIME);

        sendEvent(appearance.getPrimaryKey(), EventTypes.MODIFY, appearanceTextDecoration.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return appearanceTextDecoration;
    }

    public long countAppearanceTextDecorationsByAppearance(final Appearance appearance) {
        return session.getDslContext()
                .selectCount()
                .from(AppearanceTextDecorations)
                .where(AppearanceTextDecorations.APPEARANCE.eq(appearance.getPrimaryKey()),
                        AppearanceTextDecorations.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countAppearanceTextDecorationsByTextDecoration(final TextDecoration textDecoration) {
        return session.getDslContext()
                .selectCount()
                .from(AppearanceTextDecorations)
                .where(AppearanceTextDecorations.TEXT_DECORATION.eq(textDecoration.getPrimaryKey()),
                        AppearanceTextDecorations.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private AppearanceTextDecoration getAppearanceTextDecoration(Appearance appearance, TextDecoration textDecoration, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(AppearanceTextDecorations.fields())
                .from(AppearanceTextDecorations)
                .where(AppearanceTextDecorations.APPEARANCE.eq(appearance.getPrimaryKey()),
                        AppearanceTextDecorations.TEXT_DECORATION.eq(textDecoration.getPrimaryKey()),
                        AppearanceTextDecorations.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return appearanceTextDecorationFactory.getEntityFromQuery(entityPermission, query);
    }

    public AppearanceTextDecoration getAppearanceTextDecoration(Appearance appearance, TextDecoration textDecoration) {
        return getAppearanceTextDecoration(appearance, textDecoration, EntityPermission.READ_ONLY);
    }

    public AppearanceTextDecoration getAppearanceTextDecorationForUpdate(Appearance appearance, TextDecoration textDecoration) {
        return getAppearanceTextDecoration(appearance, textDecoration, EntityPermission.READ_WRITE);
    }

    public AppearanceTextDecorationValue getAppearanceTextDecorationValue(AppearanceTextDecoration appearanceTextDecoration) {
        return appearanceTextDecoration == null? null: appearanceTextDecoration.getAppearanceTextDecorationValue().clone();
    }

    public AppearanceTextDecorationValue getAppearanceTextDecorationValueForUpdate(Appearance appearance, TextDecoration textDecoration) {
        return getAppearanceTextDecorationValue(getAppearanceTextDecorationForUpdate(appearance, textDecoration));
    }

    private List<AppearanceTextDecoration> getAppearanceTextDecorationsByAppearance(Appearance appearance, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(AppearanceTextDecorations.fields())
                    .from(AppearanceTextDecorations)
                    .join(TextDecorations).onKey(APPEARANCE_TEXT_DECORATIONS_TEXT_DECORATION_FK)
                    .join(TextDecorationDetails).onKey(TEXT_DECORATIONS_LAST_DETAIL_FK)
                    .where(AppearanceTextDecorations.APPEARANCE.eq(appearance.getPrimaryKey()),
                            AppearanceTextDecorations.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(TextDecorationDetails.SORT_ORDER, TextDecorationDetails.TEXT_DECORATION_NAME),
                    AppearanceTextDecorationFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(AppearanceTextDecorations.fields())
                    .from(AppearanceTextDecorations)
                    .where(AppearanceTextDecorations.APPEARANCE.eq(appearance.getPrimaryKey()),
                            AppearanceTextDecorations.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return appearanceTextDecorationFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<AppearanceTextDecoration> getAppearanceTextDecorationsByAppearance(Appearance appearance) {
        return getAppearanceTextDecorationsByAppearance(appearance, EntityPermission.READ_ONLY);
    }

    public List<AppearanceTextDecoration> getAppearanceTextDecorationsByAppearanceForUpdate(Appearance appearance) {
        return getAppearanceTextDecorationsByAppearance(appearance, EntityPermission.READ_WRITE);
    }

    private List<AppearanceTextDecoration> getAppearanceTextDecorationsByTextDecoration(TextDecoration textDecoration, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(AppearanceTextDecorations.fields())
                    .from(AppearanceTextDecorations)
                    .join(Appearances).onKey(APPEARANCE_TEXT_DECORATIONS_APPEARANCE_FK)
                    .join(AppearanceDetails).onKey(APPEARANCES_LAST_DETAIL_FK)
                    .where(AppearanceTextDecorations.TEXT_DECORATION.eq(textDecoration.getPrimaryKey()),
                            AppearanceTextDecorations.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(AppearanceDetails.SORT_ORDER, AppearanceDetails.APPEARANCE_NAME),
                    AppearanceTextDecorationFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(AppearanceTextDecorations.fields())
                    .from(AppearanceTextDecorations)
                    .where(AppearanceTextDecorations.TEXT_DECORATION.eq(textDecoration.getPrimaryKey()),
                            AppearanceTextDecorations.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return appearanceTextDecorationFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<AppearanceTextDecoration> getAppearanceTextDecorationsByTextDecoration(TextDecoration textDecoration) {
        return getAppearanceTextDecorationsByTextDecoration(textDecoration, EntityPermission.READ_ONLY);
    }

    public List<AppearanceTextDecoration> getAppearanceTextDecorationsByTextDecorationForUpdate(TextDecoration textDecoration) {
        return getAppearanceTextDecorationsByTextDecoration(textDecoration, EntityPermission.READ_WRITE);
    }

    public AppearanceTextDecorationTransfer getAppearanceTextDecorationTransfer(UserVisit userVisit, AppearanceTextDecoration appearanceTextDecoration) {
        return appearanceTextDecorationTransferCache.getAppearanceTextDecorationTransfer(userVisit, appearanceTextDecoration);
    }

    public List<AppearanceTextDecorationTransfer> getAppearanceTextDecorationTransfers(UserVisit userVisit, Collection<AppearanceTextDecoration> appearanceTextDecorations) {
        List<AppearanceTextDecorationTransfer> appearanceTextDecorationTransfers = new ArrayList<>(appearanceTextDecorations.size());

        appearanceTextDecorations.forEach((appearanceTextDecoration) ->
                appearanceTextDecorationTransfers.add(appearanceTextDecorationTransferCache.getAppearanceTextDecorationTransfer(userVisit, appearanceTextDecoration))
        );

        return appearanceTextDecorationTransfers;
    }

    public List<AppearanceTextDecorationTransfer> getAppearanceTextDecorationTransfersByAppearance(UserVisit userVisit, Appearance appearance) {
        return getAppearanceTextDecorationTransfers(userVisit, getAppearanceTextDecorationsByAppearance(appearance));
    }

    public List<AppearanceTextDecorationTransfer> getAppearanceTextDecorationTransfersByTextDecoration(UserVisit userVisit, TextDecoration textDecoration) {
        return getAppearanceTextDecorationTransfers(userVisit, getAppearanceTextDecorationsByTextDecoration(textDecoration));
    }

    public void deleteAppearanceTextDecoration(AppearanceTextDecoration appearanceTextDecoration, BasePK deletedBy) {
        appearanceTextDecoration.setThruTime(session.getStartTime());

        sendEvent(appearanceTextDecoration.getAppearancePK(), EventTypes.MODIFY, appearanceTextDecoration.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }

    public void deleteAppearanceTextDecorationsByAppearance(List<AppearanceTextDecoration> appearanceTextDecorations, BasePK deletedBy) {
        appearanceTextDecorations.forEach((appearanceTextDecoration) ->
                deleteAppearanceTextDecoration(appearanceTextDecoration, deletedBy)
        );
    }

    public void deleteAppearanceTextDecorationsByAppearance(Appearance appearance, BasePK deletedBy) {
        deleteAppearanceTextDecorationsByAppearance(getAppearanceTextDecorationsByAppearanceForUpdate(appearance), deletedBy);
    }

    public void deleteAppearanceTextDecorationsByTextDecoration(TextDecoration textDecoration, BasePK deletedBy) {
        deleteAppearanceTextDecorationsByAppearance(getAppearanceTextDecorationsByTextDecorationForUpdate(textDecoration), deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Appearance Text Transformations
    // --------------------------------------------------------------------------------

    @Inject
    protected AppearanceTextTransformationFactory appearanceTextTransformationFactory;

    @Inject
    AppearanceTextTransformationTransferCache appearanceTextTransformationTransferCache;

    public AppearanceTextTransformation createAppearanceTextTransformation(Appearance appearance, TextTransformation textTransformation, BasePK createdBy) {
        var appearanceTextTransformation = appearanceTextTransformationFactory.create(appearance, textTransformation,
                session.getStartTime(), Session.MAX_TIME);

        sendEvent(appearance.getPrimaryKey(), EventTypes.MODIFY, appearanceTextTransformation.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return appearanceTextTransformation;
    }

    public long countAppearanceTextTransformationsByAppearance(final Appearance appearance) {
        return session.getDslContext()
                .selectCount()
                .from(AppearanceTextTransformations)
                .where(AppearanceTextTransformations.APPEARANCE.eq(appearance.getPrimaryKey()),
                        AppearanceTextTransformations.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countAppearanceTextTransformationsByTextTransformation(final TextTransformation textTransformation) {
        return session.getDslContext()
                .selectCount()
                .from(AppearanceTextTransformations)
                .where(AppearanceTextTransformations.TEXT_TRANSFORMATION.eq(textTransformation.getPrimaryKey()),
                        AppearanceTextTransformations.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private AppearanceTextTransformation getAppearanceTextTransformation(Appearance appearance, TextTransformation textTransformation, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(AppearanceTextTransformations.fields())
                .from(AppearanceTextTransformations)
                .where(AppearanceTextTransformations.APPEARANCE.eq(appearance.getPrimaryKey()),
                        AppearanceTextTransformations.TEXT_TRANSFORMATION.eq(textTransformation.getPrimaryKey()),
                        AppearanceTextTransformations.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return appearanceTextTransformationFactory.getEntityFromQuery(entityPermission, query);
    }

    public AppearanceTextTransformation getAppearanceTextTransformation(Appearance appearance, TextTransformation textTransformation) {
        return getAppearanceTextTransformation(appearance, textTransformation, EntityPermission.READ_ONLY);
    }

    public AppearanceTextTransformation getAppearanceTextTransformationForUpdate(Appearance appearance, TextTransformation textTransformation) {
        return getAppearanceTextTransformation(appearance, textTransformation, EntityPermission.READ_WRITE);
    }

    public AppearanceTextTransformationValue getAppearanceTextTransformationValue(AppearanceTextTransformation appearanceTextTransformation) {
        return appearanceTextTransformation == null? null: appearanceTextTransformation.getAppearanceTextTransformationValue().clone();
    }

    public AppearanceTextTransformationValue getAppearanceTextTransformationValueForUpdate(Appearance appearance, TextTransformation textTransformation) {
        return getAppearanceTextTransformationValue(getAppearanceTextTransformationForUpdate(appearance, textTransformation));
    }

    private List<AppearanceTextTransformation> getAppearanceTextTransformationsByAppearance(Appearance appearance, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(AppearanceTextTransformations.fields())
                    .from(AppearanceTextTransformations)
                    .join(TextTransformations).onKey(APPEARANCE_TEXT_TRANSFORMATIONS_TEXT_TRANSFORMATION_FK)
                    .join(TextTransformationDetails).onKey(TEXT_TRANSFORMATIONS_LAST_DETAIL_FK)
                    .where(AppearanceTextTransformations.APPEARANCE.eq(appearance.getPrimaryKey()),
                            AppearanceTextTransformations.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(TextTransformationDetails.SORT_ORDER, TextTransformationDetails.TEXT_TRANSFORMATION_NAME),
                    AppearanceTextTransformationFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(AppearanceTextTransformations.fields())
                    .from(AppearanceTextTransformations)
                    .where(AppearanceTextTransformations.APPEARANCE.eq(appearance.getPrimaryKey()),
                            AppearanceTextTransformations.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return appearanceTextTransformationFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<AppearanceTextTransformation> getAppearanceTextTransformationsByAppearance(Appearance appearance) {
        return getAppearanceTextTransformationsByAppearance(appearance, EntityPermission.READ_ONLY);
    }

    public List<AppearanceTextTransformation> getAppearanceTextTransformationsByAppearanceForUpdate(Appearance appearance) {
        return getAppearanceTextTransformationsByAppearance(appearance, EntityPermission.READ_WRITE);
    }

    private List<AppearanceTextTransformation> getAppearanceTextTransformationsByTextTransformation(TextTransformation textTransformation, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(AppearanceTextTransformations.fields())
                    .from(AppearanceTextTransformations)
                    .join(Appearances).onKey(APPEARANCE_TEXT_TRANSFORMATIONS_APPEARANCE_FK)
                    .join(AppearanceDetails).onKey(APPEARANCES_LAST_DETAIL_FK)
                    .where(AppearanceTextTransformations.TEXT_TRANSFORMATION.eq(textTransformation.getPrimaryKey()),
                            AppearanceTextTransformations.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(AppearanceDetails.SORT_ORDER, AppearanceDetails.APPEARANCE_NAME),
                    AppearanceTextTransformationFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(AppearanceTextTransformations.fields())
                    .from(AppearanceTextTransformations)
                    .where(AppearanceTextTransformations.TEXT_TRANSFORMATION.eq(textTransformation.getPrimaryKey()),
                            AppearanceTextTransformations.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return appearanceTextTransformationFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<AppearanceTextTransformation> getAppearanceTextTransformationsByTextTransformation(TextTransformation textTransformation) {
        return getAppearanceTextTransformationsByTextTransformation(textTransformation, EntityPermission.READ_ONLY);
    }

    public List<AppearanceTextTransformation> getAppearanceTextTransformationsByTextTransformationForUpdate(TextTransformation textTransformation) {
        return getAppearanceTextTransformationsByTextTransformation(textTransformation, EntityPermission.READ_WRITE);
    }

    public AppearanceTextTransformationTransfer getAppearanceTextTransformationTransfer(UserVisit userVisit, AppearanceTextTransformation appearanceTextTransformation) {
        return appearanceTextTransformationTransferCache.getAppearanceTextTransformationTransfer(userVisit, appearanceTextTransformation);
    }

    public List<AppearanceTextTransformationTransfer> getAppearanceTextTransformationTransfers(UserVisit userVisit, Collection<AppearanceTextTransformation> appearanceTextTransformations) {
        List<AppearanceTextTransformationTransfer> appearanceTextTransformationTransfers = new ArrayList<>(appearanceTextTransformations.size());

        appearanceTextTransformations.forEach((appearanceTextTransformation) ->
                appearanceTextTransformationTransfers.add(appearanceTextTransformationTransferCache.getAppearanceTextTransformationTransfer(userVisit, appearanceTextTransformation))
        );

        return appearanceTextTransformationTransfers;
    }

    public List<AppearanceTextTransformationTransfer> getAppearanceTextTransformationTransfersByAppearance(UserVisit userVisit, Appearance appearance) {
        return getAppearanceTextTransformationTransfers(userVisit, getAppearanceTextTransformationsByAppearance(appearance));
    }

    public List<AppearanceTextTransformationTransfer> getAppearanceTextTransformationTransfersByTextTransformation(UserVisit userVisit, TextTransformation textTransformation) {
        return getAppearanceTextTransformationTransfers(userVisit, getAppearanceTextTransformationsByTextTransformation(textTransformation));
    }

    public void deleteAppearanceTextTransformation(AppearanceTextTransformation appearanceTextTransformation, BasePK deletedBy) {
        appearanceTextTransformation.setThruTime(session.getStartTime());

        sendEvent(appearanceTextTransformation.getAppearancePK(), EventTypes.MODIFY, appearanceTextTransformation.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }

    public void deleteAppearanceTextTransformationsByAppearance(List<AppearanceTextTransformation> appearanceTextTransformations, BasePK deletedBy) {
        appearanceTextTransformations.forEach((appearanceTextTransformation) ->
                deleteAppearanceTextTransformation(appearanceTextTransformation, deletedBy)
        );
    }

    public void deleteAppearanceTextTransformationsByAppearance(Appearance appearance, BasePK deletedBy) {
        deleteAppearanceTextTransformationsByAppearance(getAppearanceTextTransformationsByAppearanceForUpdate(appearance), deletedBy);
    }

    public void deleteAppearanceTextTransformationsByTextTransformation(TextTransformation textTransformation, BasePK deletedBy) {
        deleteAppearanceTextTransformationsByAppearance(getAppearanceTextTransformationsByTextTransformationForUpdate(textTransformation), deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Entity Appearances
    // --------------------------------------------------------------------------------

    @Inject
    protected EntityAppearanceFactory entityAppearanceFactory;

    @Inject
    EntityAppearanceTransferCache entityAppearanceTransferCache;

    public EntityAppearance createEntityAppearance(EntityInstance entityInstance, Appearance appearance, BasePK createdBy) {
        var entityAppearance = entityAppearanceFactory.create(entityInstance, appearance, session.getStartTime(),
                Session.MAX_TIME);

        sendEvent(entityInstance, EventTypes.MODIFY, entityAppearance.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return entityAppearance;
    }

    public long countEntityAppearancesByEntityInstance(final EntityInstance entityInstance) {
        return session.getDslContext()
                .selectCount()
                .from(EntityAppearances)
                .where(EntityAppearances.ENTITY_INSTANCE.eq(entityInstance.getPrimaryKey()),
                        EntityAppearances.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countEntityAppearancesByAppearance(final Appearance appearance) {
        return session.getDslContext()
                .selectCount()
                .from(EntityAppearances)
                .where(EntityAppearances.APPEARANCE.eq(appearance.getPrimaryKey()),
                        EntityAppearances.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private EntityAppearance getEntityAppearance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(EntityAppearances.fields())
                .from(EntityAppearances)
                .where(EntityAppearances.ENTITY_INSTANCE.eq(entityInstance.getPrimaryKey()),
                        EntityAppearances.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return entityAppearanceFactory.getEntityFromQuery(entityPermission, query);
    }

    public EntityAppearance getEntityAppearance(EntityInstance entityInstance) {
        return getEntityAppearance(entityInstance, EntityPermission.READ_ONLY);
    }

    public EntityAppearance getEntityAppearanceForUpdate(EntityInstance entityInstance) {
        return getEntityAppearance(entityInstance, EntityPermission.READ_WRITE);
    }

    public EntityAppearanceValue getEntityAppearanceValue(EntityAppearance entityAppearance) {
        return entityAppearance == null? null: entityAppearance.getEntityAppearanceValue().clone();
    }

    public EntityAppearanceValue getEntityAppearanceValueForUpdate(EntityInstance entityInstance) {
        return getEntityAppearanceValue(getEntityAppearanceForUpdate(entityInstance));
    }

    public List<EntityAppearance> getEntityAppearancesByEntityInstanceForUpdate(EntityInstance entityInstance) {
        var query = session.getDslContext()
                .select(EntityAppearances.fields())
                .from(EntityAppearances)
                .where(EntityAppearances.ENTITY_INSTANCE.eq(entityInstance.getPrimaryKey()),
                        EntityAppearances.THRU_TIME.eq(Session.MAX_TIME))
                .forUpdate();

        return entityAppearanceFactory.getEntitiesFromQuery(EntityPermission.READ_WRITE, query);
    }

    private List<EntityAppearance> getEntityAppearancesByAppearance(Appearance appearance, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(EntityAppearances.fields())
                    .from(EntityAppearances)
                    .join(EntityInstances).onKey(ENTITY_APPEARANCES_ENTITY_INSTANCE_FK)
                    .join(EntityTypes).onKey(ENTITY_INSTANCES_ENTITY_TYPE_FK)
                    .join(EntityTypeDetails).onKey(ENTITY_TYPES_LAST_DETAIL_FK)
                    .join(ComponentVendors).onKey(ENTITY_TYPE_DETAILS_COMPONENT_VENDOR_FK)
                    .join(ComponentVendorDetails).onKey(COMPONENT_VENDORS_LAST_DETAIL_FK)
                    .where(EntityAppearances.APPEARANCE.eq(appearance.getPrimaryKey()),
                            EntityAppearances.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(ComponentVendorDetails.COMPONENT_VENDOR_NAME, EntityTypeDetails.SORT_ORDER, EntityTypeDetails.ENTITY_TYPE_NAME, EntityInstances.ENTITY_UNIQUE),
                    EntityAppearanceFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(EntityAppearances.fields())
                    .from(EntityAppearances)
                    .where(EntityAppearances.APPEARANCE.eq(appearance.getPrimaryKey()),
                            EntityAppearances.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return entityAppearanceFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<EntityAppearance> getEntityAppearancesByAppearance(Appearance appearance) {
        return getEntityAppearancesByAppearance(appearance, EntityPermission.READ_ONLY);
    }

    public List<EntityAppearance> getEntityAppearancesByAppearanceForUpdate(Appearance appearance) {
        return getEntityAppearancesByAppearance(appearance, EntityPermission.READ_WRITE);
    }

    public EntityAppearanceTransfer getEntityAppearanceTransfer(UserVisit userVisit, EntityAppearance entityAppearance) {
        return entityAppearanceTransferCache.getEntityAppearanceTransfer(userVisit, entityAppearance);
    }

    public List<EntityAppearanceTransfer> getEntityAppearanceTransfersByAppearance(UserVisit userVisit, Appearance appearance) {
        var entityAppearances = getEntityAppearancesByAppearance(appearance);
        List<EntityAppearanceTransfer> entityAppearanceTransfers = new ArrayList<>(entityAppearances.size());

        entityAppearances.forEach((entityAppearance) ->
                entityAppearanceTransfers.add(entityAppearanceTransferCache.getEntityAppearanceTransfer(userVisit, entityAppearance))
        );

        return entityAppearanceTransfers;
    }

    public void updateEntityAppearanceFromValue(EntityAppearanceValue entityAppearanceValue, BasePK updatedBy) {
        if(entityAppearanceValue.hasBeenModified()) {
            var entityAppearance = entityAppearanceFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    entityAppearanceValue.getPrimaryKey());

            entityAppearance.setThruTime(session.getStartTime());
            entityAppearance.store();

            var entityInstance = entityAppearance.getEntityInstance(); // Not updated.
            var appearancePK = entityAppearanceValue.getAppearancePK();

            entityAppearance = entityAppearanceFactory.create(entityInstance.getPrimaryKey(), appearancePK, session.getStartTime(), Session.MAX_TIME);

            sendEvent(entityInstance, EventTypes.MODIFY, entityAppearance.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteEntityAppearance(EntityAppearance entityAppearance, BasePK deletedBy) {
        entityAppearance.setThruTime(session.getStartTime());

        sendEvent(entityAppearance.getEntityInstance(), EventTypes.MODIFY, entityAppearance.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }

    public void deleteEntityAppearancesByEntityInstance(EntityInstance entityInstance, BasePK deletedBy) {
        var entityAppearances = getEntityAppearancesByEntityInstanceForUpdate(entityInstance);

        entityAppearances.forEach((entityAppearance) ->
                deleteEntityAppearance(entityAppearance, deletedBy)
        );
    }

    public void deleteEntityAppearancesByAppearance(Appearance appearance, BasePK deletedBy) {
        var entityAppearances = getEntityAppearancesByAppearanceForUpdate(appearance);

        entityAppearances.forEach((entityAppearance) ->
                deleteEntityAppearance(entityAppearance, deletedBy)
        );
    }

}
