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

package com.echothree.model.control.core.server.control;

import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.core.common.choice.AppearanceChoicesBean;
import com.echothree.model.control.core.common.transfer.AppearanceDescriptionTransfer;
import com.echothree.model.control.core.common.transfer.AppearanceTextDecorationTransfer;
import com.echothree.model.control.core.common.transfer.AppearanceTextTransformationTransfer;
import com.echothree.model.control.core.common.transfer.AppearanceTransfer;
import com.echothree.model.control.core.common.transfer.EntityAppearanceTransfer;
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
import com.echothree.util.common.exception.PersistenceDatabaseException;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class AppearanceControl
        extends BaseCoreControl {

    /** Creates a new instance of AppearanceControl */
    protected AppearanceControl() {
        super();
    }

    // --------------------------------------------------------------------------------
    //   Appearances
    // --------------------------------------------------------------------------------

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

        var appearance = AppearanceFactory.getInstance().create();
        var appearanceDetail = AppearanceDetailFactory.getInstance().create(appearance, appearanceName, textColor, backgroundColor, fontStyle,
                fontWeight, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        // Convert to R/W
        appearance = AppearanceFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, appearance.getPrimaryKey());
        appearance.setActiveDetail(appearanceDetail);
        appearance.setLastDetail(appearanceDetail);
        appearance.store();

        sendEvent(appearance.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return appearance;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.Appearance */
    public Appearance getAppearanceByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new AppearancePK(entityInstance.getEntityUniqueId());
        var appearance = AppearanceFactory.getInstance().getEntityFromPK(entityPermission, pk);

        return appearance;
    }

    public Appearance getAppearanceByEntityInstance(EntityInstance entityInstance) {
        return getAppearanceByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public Appearance getAppearanceByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getAppearanceByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countAppearances() {
        return session.queryForLong("""
                SELECT COUNT(*)
                FROM appearances, appearancedetails
                WHERE apprnc_activedetailid = apprncdt_appearancedetailid
                """);
    }

    private static final Map<EntityPermission, String> getAppearanceByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                        "FROM appearances, appearancedetails " +
                        "WHERE apprnc_activedetailid = apprncdt_appearancedetailid " +
                        "AND apprncdt_appearancename = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                        "FROM appearances, appearancedetails " +
                        "WHERE apprnc_activedetailid = apprncdt_appearancedetailid " +
                        "AND apprncdt_appearancename = ? " +
                        "FOR UPDATE");
        getAppearanceByNameQueries = Collections.unmodifiableMap(queryMap);
    }

    public Appearance getAppearanceByName(String appearanceName, EntityPermission entityPermission) {
        return AppearanceFactory.getInstance().getEntityFromQuery(entityPermission, getAppearanceByNameQueries, appearanceName);
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

    private static final Map<EntityPermission, String> getDefaultAppearanceQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                        "FROM appearances, appearancedetails " +
                        "WHERE apprnc_activedetailid = apprncdt_appearancedetailid " +
                        "AND apprncdt_isdefault = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                        "FROM appearances, appearancedetails " +
                        "WHERE apprnc_activedetailid = apprncdt_appearancedetailid " +
                        "AND apprncdt_isdefault = 1 " +
                        "FOR UPDATE");
        getDefaultAppearanceQueries = Collections.unmodifiableMap(queryMap);
    }

    private Appearance getDefaultAppearance(EntityPermission entityPermission) {
        return AppearanceFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultAppearanceQueries);
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

    private static final Map<EntityPermission, String> getAppearancesQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                        "FROM appearances, appearancedetails " +
                        "WHERE apprnc_activedetailid = apprncdt_appearancedetailid " +
                        "ORDER BY apprncdt_sortorder, apprncdt_appearancename " +
                        "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                        "FROM appearances, appearancedetails " +
                        "WHERE apprnc_activedetailid = apprncdt_appearancedetailid " +
                        "FOR UPDATE");
        getAppearancesQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<Appearance> getAppearances(EntityPermission entityPermission) {
        return AppearanceFactory.getInstance().getEntitiesFromQuery(entityPermission, getAppearancesQueries);
    }

    public List<Appearance> getAppearances() {
        return getAppearances(EntityPermission.READ_ONLY);
    }

    public List<Appearance> getAppearancesForUpdate() {
        return getAppearances(EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getAppearancesByTextColorQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                        "FROM appearances, appearancedetails " +
                        "WHERE apprnc_activedetailid = apprncdt_appearancedetailid AND apprncdt_textcolorid = ? " +
                        "ORDER BY apprncdt_sortorder, apprncdt_appearancename " +
                        "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                        "FROM appearances, appearancedetails " +
                        "WHERE apprnc_activedetailid = apprncdt_appearancedetailid AND apprncdt_textcolorid = ? " +
                        "FOR UPDATE");
        getAppearancesByTextColorQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<Appearance> getAppearancesByTextColor(Color textColor, EntityPermission entityPermission) {
        return AppearanceFactory.getInstance().getEntitiesFromQuery(entityPermission, getAppearancesByTextColorQueries,
                textColor);
    }

    public List<Appearance> getAppearancesByTextColor(Color textColor) {
        return getAppearancesByTextColor(textColor, EntityPermission.READ_ONLY);
    }

    public List<Appearance> getAppearancesByTextColorForUpdate(Color textColor) {
        return getAppearancesByTextColor(textColor, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getAppearancesByBackgroundColorQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                        "FROM appearances, appearancedetails " +
                        "WHERE apprnc_activedetailid = apprncdt_appearancedetailid AND apprncdt_backgroundcolorid = ? " +
                        "ORDER BY apprncdt_sortorder, apprncdt_appearancename " +
                        "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                        "FROM appearances, appearancedetails " +
                        "WHERE apprnc_activedetailid = apprncdt_appearancedetailid AND apprncdt_backgroundcolorid = ? " +
                        "FOR UPDATE");
        getAppearancesByBackgroundColorQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<Appearance> getAppearancesByBackgroundColor(Color backgroundColor, EntityPermission entityPermission) {
        return AppearanceFactory.getInstance().getEntitiesFromQuery(entityPermission, getAppearancesByBackgroundColorQueries,
                backgroundColor);
    }

    public List<Appearance> getAppearancesByBackgroundColor(Color backgroundColor) {
        return getAppearancesByBackgroundColor(backgroundColor, EntityPermission.READ_ONLY);
    }

    public List<Appearance> getAppearancesByBackgroundColorForUpdate(Color backgroundColor) {
        return getAppearancesByBackgroundColor(backgroundColor, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getAppearancesByFontStyleQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                        "FROM appearances, appearancedetails " +
                        "WHERE apprnc_activedetailid = apprncdt_appearancedetailid AND apprncdt_fntstyl_fontstyleid = ? " +
                        "ORDER BY apprncdt_sortorder, apprncdt_appearancename " +
                        "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                        "FROM appearances, appearancedetails " +
                        "WHERE apprnc_activedetailid = apprncdt_appearancedetailid AND apprncdt_fntstyl_fontstyleid = ? " +
                        "FOR UPDATE");
        getAppearancesByFontStyleQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<Appearance> getAppearancesByFontStyle(FontStyle fontStyle, EntityPermission entityPermission) {
        return AppearanceFactory.getInstance().getEntitiesFromQuery(entityPermission, getAppearancesByFontStyleQueries,
                fontStyle);
    }

    public List<Appearance> getAppearancesByFontStyle(FontStyle fontStyle) {
        return getAppearancesByFontStyle(fontStyle, EntityPermission.READ_ONLY);
    }

    public List<Appearance> getAppearancesByFontStyleForUpdate(FontStyle fontStyle) {
        return getAppearancesByFontStyle(fontStyle, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getAppearancesByFontWeightQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                        "FROM appearances, appearancedetails " +
                        "WHERE apprnc_activedetailid = apprncdt_appearancedetailid AND apprncdt_fntwght_fontweightid = ? " +
                        "ORDER BY apprncdt_sortorder, apprncdt_appearancename " +
                        "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                        "FROM appearances, appearancedetails " +
                        "WHERE apprnc_activedetailid = apprncdt_appearancedetailid AND apprncdt_fntwght_fontweightid = ? " +
                        "FOR UPDATE");
        getAppearancesByFontWeightQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<Appearance> getAppearancesByFontWeight(FontWeight fontWeight, EntityPermission entityPermission) {
        return AppearanceFactory.getInstance().getEntitiesFromQuery(entityPermission, getAppearancesByFontWeightQueries,
                fontWeight);
    }

    public List<Appearance> getAppearancesByFontWeight(FontWeight fontWeight) {
        return getAppearancesByFontWeight(fontWeight, EntityPermission.READ_ONLY);
    }

    public List<Appearance> getAppearancesByFontWeightForUpdate(FontWeight fontWeight) {
        return getAppearancesByFontWeight(fontWeight, EntityPermission.READ_WRITE);
    }

    public AppearanceTransfer getAppearanceTransfer(UserVisit userVisit, Appearance appearance) {
        return getCoreTransferCaches().getAppearanceTransferCache().getAppearanceTransfer(userVisit, appearance);
    }

    public List<AppearanceTransfer> getAppearanceTransfers(UserVisit userVisit, Collection<Appearance> appearances) {
        List<AppearanceTransfer> appearanceTransfers = new ArrayList<>(appearances.size());
        var appearanceTransferCache = getCoreTransferCaches(userVisit).getAppearanceTransferCache();

        appearances.forEach((appearance) ->
                appearanceTransfers.add(appearanceTransferCache.getAppearanceTransfer(appearance))
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
            var appearance = AppearanceFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    appearanceDetailValue.getAppearancePK());
            var appearanceDetail = appearance.getActiveDetailForUpdate();

            appearanceDetail.setThruTime(session.START_TIME_LONG);
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

            appearanceDetail = AppearanceDetailFactory.getInstance().create(appearancePK, appearanceName, textColorPK, backgroundColorPK, fontStylePK,
                    fontWeightPK, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

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

        appearanceDetail.setThruTime(session.START_TIME_LONG);
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

    public AppearanceDescription createAppearanceDescription(Appearance appearance, Language language, String description, BasePK createdBy) {
        var appearanceDescription = AppearanceDescriptionFactory.getInstance().create(appearance, language, description,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEvent(appearance.getPrimaryKey(), EventTypes.MODIFY, appearanceDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return appearanceDescription;
    }

    private static final Map<EntityPermission, String> getAppearanceDescriptionQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                        "FROM appearancedescriptions " +
                        "WHERE apprncd_apprnc_appearanceid = ? AND apprncd_lang_languageid = ? AND apprncd_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                        "FROM appearancedescriptions " +
                        "WHERE apprncd_apprnc_appearanceid = ? AND apprncd_lang_languageid = ? AND apprncd_thrutime = ? " +
                        "FOR UPDATE");
        getAppearanceDescriptionQueries = Collections.unmodifiableMap(queryMap);
    }

    private AppearanceDescription getAppearanceDescription(Appearance appearance, Language language, EntityPermission entityPermission) {
        return AppearanceDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, getAppearanceDescriptionQueries,
                appearance, language, Session.MAX_TIME);
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

    private static final Map<EntityPermission, String> getAppearanceDescriptionsByAppearanceQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                        "FROM appearancedescriptions, languages " +
                        "WHERE apprncd_apprnc_appearanceid = ? AND apprncd_thrutime = ? AND apprncd_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                        "FROM appearancedescriptions " +
                        "WHERE apprncd_apprnc_appearanceid = ? AND apprncd_thrutime = ? " +
                        "FOR UPDATE");
        getAppearanceDescriptionsByAppearanceQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<AppearanceDescription> getAppearanceDescriptionsByAppearance(Appearance appearance, EntityPermission entityPermission) {
        return AppearanceDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, getAppearanceDescriptionsByAppearanceQueries,
                appearance, Session.MAX_TIME);
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
        return getCoreTransferCaches().getAppearanceDescriptionTransferCache().getAppearanceDescriptionTransfer(userVisit, appearanceDescription);
    }

    public List<AppearanceDescriptionTransfer> getAppearanceDescriptionTransfersByAppearance(UserVisit userVisit, Appearance appearance) {
        var appearanceDescriptions = getAppearanceDescriptionsByAppearance(appearance);
        List<AppearanceDescriptionTransfer> appearanceDescriptionTransfers = new ArrayList<>(appearanceDescriptions.size());
        var appearanceDescriptionTransferCache = getCoreTransferCaches(userVisit).getAppearanceDescriptionTransferCache();

        appearanceDescriptions.forEach((appearanceDescription) ->
                appearanceDescriptionTransfers.add(appearanceDescriptionTransferCache.getAppearanceDescriptionTransfer(appearanceDescription))
        );

        return appearanceDescriptionTransfers;
    }

    public void updateAppearanceDescriptionFromValue(AppearanceDescriptionValue appearanceDescriptionValue, BasePK updatedBy) {
        if(appearanceDescriptionValue.hasBeenModified()) {
            var appearanceDescription = AppearanceDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    appearanceDescriptionValue.getPrimaryKey());

            appearanceDescription.setThruTime(session.START_TIME_LONG);
            appearanceDescription.store();

            var appearance = appearanceDescription.getAppearance();
            var language = appearanceDescription.getLanguage();
            var description = appearanceDescriptionValue.getDescription();

            appearanceDescription = AppearanceDescriptionFactory.getInstance().create(appearance, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEvent(appearance.getPrimaryKey(), EventTypes.MODIFY, appearanceDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteAppearanceDescription(AppearanceDescription appearanceDescription, BasePK deletedBy) {
        appearanceDescription.setThruTime(session.START_TIME_LONG);

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

    public AppearanceTextDecoration createAppearanceTextDecoration(Appearance appearance, TextDecoration textDecoration, BasePK createdBy) {
        var appearanceTextDecoration = AppearanceTextDecorationFactory.getInstance().create(appearance, textDecoration,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEvent(appearance.getPrimaryKey(), EventTypes.MODIFY, appearanceTextDecoration.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return appearanceTextDecoration;
    }

    private static final Map<EntityPermission, String> getAppearanceTextDecorationQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                        + "FROM appearancetextdecorations "
                        + "WHERE apprntxtdcrtn_apprnc_appearanceid = ? AND apprntxtdcrtn_txtdcrtn_textdecorationid = ? AND apprntxtdcrtn_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                        + "FROM appearancetextdecorations "
                        + "WHERE apprntxtdcrtn_apprnc_appearanceid = ? AND apprntxtdcrtn_txtdcrtn_textdecorationid = ? AND apprntxtdcrtn_thrutime = ? "
                        + "FOR UPDATE");
        getAppearanceTextDecorationQueries = Collections.unmodifiableMap(queryMap);
    }

    private AppearanceTextDecoration getAppearanceTextDecoration(Appearance appearance, TextDecoration textDecoration, EntityPermission entityPermission) {
        return AppearanceTextDecorationFactory.getInstance().getEntityFromQuery(entityPermission, getAppearanceTextDecorationQueries,
                appearance, textDecoration, Session.MAX_TIME);
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

    private static final Map<EntityPermission, String> getAppearanceTextDecorationsByAppearanceQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                        + "FROM appearancetextdecorations, textdecorations, textdecorationdetails "
                        + "WHERE apprntxtdcrtn_apprnc_appearanceid = ? AND apprntxtdcrtn_thrutime = ? "
                        + "AND apprntxtdcrtn_txtdcrtn_textdecorationid = txtdcrtn_textdecorationid AND txtdcrtn_lastdetailid = txtdcrtndt_textdecorationdetailid "
                        + "ORDER BY txtdcrtndt_sortorder, txtdcrtndt_textdecorationname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                        + "FROM appearancetextdecorations "
                        + "WHERE apprntxtdcrtn_apprnc_appearanceid = ? AND apprntxtdcrtn_thrutime = ? "
                        + "FOR UPDATE");
        getAppearanceTextDecorationsByAppearanceQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<AppearanceTextDecoration> getAppearanceTextDecorationsByAppearance(Appearance appearance, EntityPermission entityPermission) {
        return AppearanceTextDecorationFactory.getInstance().getEntitiesFromQuery(entityPermission, getAppearanceTextDecorationsByAppearanceQueries,
                appearance, Session.MAX_TIME);
    }

    public List<AppearanceTextDecoration> getAppearanceTextDecorationsByAppearance(Appearance appearance) {
        return getAppearanceTextDecorationsByAppearance(appearance, EntityPermission.READ_ONLY);
    }

    public List<AppearanceTextDecoration> getAppearanceTextDecorationsByAppearanceForUpdate(Appearance appearance) {
        return getAppearanceTextDecorationsByAppearance(appearance, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getAppearanceTextDecorationsByTextDecorationQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                        + "FROM appearancetextdecorations, appearances, appearancedetails "
                        + "WHERE apprntxtdcrtn_txtdcrtn_textdecorationid = ? AND apprntxtdcrtn_thrutime = ? "
                        + "AND apprntxttrns_apprnc_appearanceid = apprnc_appearanceid AND apprnc_lastdetailid = apprncdt_appearancedetailid "
                        + "ORDER BY apprncdt_sortorder, apprncdt_appearancename");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                        + "FROM appearancetextdecorations "
                        + "WHERE apprncd_apprnc_appearanceid = ? AND apprntxtdcrtn_thrutime = ? "
                        + "FOR UPDATE");
        getAppearanceTextDecorationsByTextDecorationQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<AppearanceTextDecoration> getAppearanceTextDecorationsByTextDecoration(TextDecoration textDecoration, EntityPermission entityPermission) {
        return AppearanceTextDecorationFactory.getInstance().getEntitiesFromQuery(entityPermission, getAppearanceTextDecorationsByTextDecorationQueries,
                textDecoration, Session.MAX_TIME);
    }

    public List<AppearanceTextDecoration> getAppearanceTextDecorationsByTextDecoration(TextDecoration textDecoration) {
        return getAppearanceTextDecorationsByTextDecoration(textDecoration, EntityPermission.READ_ONLY);
    }

    public List<AppearanceTextDecoration> getAppearanceTextDecorationsByTextDecorationForUpdate(TextDecoration textDecoration) {
        return getAppearanceTextDecorationsByTextDecoration(textDecoration, EntityPermission.READ_WRITE);
    }

    public AppearanceTextDecorationTransfer getAppearanceTextDecorationTransfer(UserVisit userVisit, AppearanceTextDecoration appearanceTextDecoration) {
        return getCoreTransferCaches().getAppearanceTextDecorationTransferCache().getAppearanceTextDecorationTransfer(userVisit, appearanceTextDecoration);
    }

    public List<AppearanceTextDecorationTransfer> getAppearanceTextDecorationTransfers(UserVisit userVisit, Collection<AppearanceTextDecoration> appearanceTextDecorations) {
        List<AppearanceTextDecorationTransfer> appearanceTextDecorationTransfers = new ArrayList<>(appearanceTextDecorations.size());
        var appearanceTextDecorationTransferCache = getCoreTransferCaches(userVisit).getAppearanceTextDecorationTransferCache();

        appearanceTextDecorations.forEach((appearanceTextDecoration) ->
                appearanceTextDecorationTransfers.add(appearanceTextDecorationTransferCache.getAppearanceTextDecorationTransfer(appearanceTextDecoration))
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
        appearanceTextDecoration.setThruTime(session.START_TIME_LONG);

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

    public AppearanceTextTransformation createAppearanceTextTransformation(Appearance appearance, TextTransformation textTransformation, BasePK createdBy) {
        var appearanceTextTransformation = AppearanceTextTransformationFactory.getInstance().create(appearance, textTransformation,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEvent(appearance.getPrimaryKey(), EventTypes.MODIFY, appearanceTextTransformation.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return appearanceTextTransformation;
    }

    private static final Map<EntityPermission, String> getAppearanceTextTransformationQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                        + "FROM appearancetexttransformations "
                        + "WHERE apprntxttrns_apprnc_appearanceid = ? AND apprntxttrns_txttrns_texttransformationid = ? AND apprntxttrns_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                        + "FROM appearancetexttransformations "
                        + "WHERE apprntxttrns_apprnc_appearanceid = ? AND apprntxttrns_txttrns_texttransformationid = ? AND apprntxttrns_thrutime = ? "
                        + "FOR UPDATE");
        getAppearanceTextTransformationQueries = Collections.unmodifiableMap(queryMap);
    }

    private AppearanceTextTransformation getAppearanceTextTransformation(Appearance appearance, TextTransformation textTransformation, EntityPermission entityPermission) {
        return AppearanceTextTransformationFactory.getInstance().getEntityFromQuery(entityPermission, getAppearanceTextTransformationQueries,
                appearance, textTransformation, Session.MAX_TIME);
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

    private static final Map<EntityPermission, String> getAppearanceTextTransformationsByAppearanceQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                        + "FROM appearancetexttransformations, texttransformations, texttransformationdetails "
                        + "WHERE apprntxttrns_apprnc_appearanceid = ? AND apprntxttrns_thrutime = ? "
                        + "AND apprntxttrns_txttrns_texttransformationid = txttrns_texttransformationid AND txttrns_lastdetailid = txttrnsdt_texttransformationdetailid "
                        + "ORDER BY txttrnsdt_sortorder, txttrnsdt_texttransformationname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                        + "FROM appearancetexttransformations "
                        + "WHERE apprntxttrns_apprnc_appearanceid = ? AND apprntxttrns_thrutime = ? "
                        + "FOR UPDATE");
        getAppearanceTextTransformationsByAppearanceQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<AppearanceTextTransformation> getAppearanceTextTransformationsByAppearance(Appearance appearance, EntityPermission entityPermission) {
        return AppearanceTextTransformationFactory.getInstance().getEntitiesFromQuery(entityPermission, getAppearanceTextTransformationsByAppearanceQueries,
                appearance, Session.MAX_TIME);
    }

    public List<AppearanceTextTransformation> getAppearanceTextTransformationsByAppearance(Appearance appearance) {
        return getAppearanceTextTransformationsByAppearance(appearance, EntityPermission.READ_ONLY);
    }

    public List<AppearanceTextTransformation> getAppearanceTextTransformationsByAppearanceForUpdate(Appearance appearance) {
        return getAppearanceTextTransformationsByAppearance(appearance, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getAppearanceTextTransformationsByTextTransformationQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                        + "FROM appearancetexttransformations, appearances, appearancedetails "
                        + "WHERE apprntxttrns_txttrns_texttransformationid = ? AND apprntxttrns_thrutime = ? "
                        + "AND apprntxttrns_apprnc_appearanceid = apprnc_appearanceid AND apprnc_lastdetailid = apprncdt_appearancedetailid "
                        + "ORDER BY apprncdt_sortorder, apprncdt_appearancename");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                        + "FROM apprntxttrns_txttrns_texttransformationid "
                        + "WHERE apprncd_apprnc_appearanceid = ? AND apprntxttrns_thrutime = ? "
                        + "FOR UPDATE");
        getAppearanceTextTransformationsByTextTransformationQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<AppearanceTextTransformation> getAppearanceTextTransformationsByTextTransformation(TextTransformation textTransformation, EntityPermission entityPermission) {
        return AppearanceTextTransformationFactory.getInstance().getEntitiesFromQuery(entityPermission, getAppearanceTextTransformationsByTextTransformationQueries,
                textTransformation, Session.MAX_TIME);
    }

    public List<AppearanceTextTransformation> getAppearanceTextTransformationsByTextTransformation(TextTransformation textTransformation) {
        return getAppearanceTextTransformationsByTextTransformation(textTransformation, EntityPermission.READ_ONLY);
    }

    public List<AppearanceTextTransformation> getAppearanceTextTransformationsByTextTransformationForUpdate(TextTransformation textTransformation) {
        return getAppearanceTextTransformationsByTextTransformation(textTransformation, EntityPermission.READ_WRITE);
    }

    public AppearanceTextTransformationTransfer getAppearanceTextTransformationTransfer(UserVisit userVisit, AppearanceTextTransformation appearanceTextTransformation) {
        return getCoreTransferCaches().getAppearanceTextTransformationTransferCache().getAppearanceTextTransformationTransfer(userVisit, appearanceTextTransformation);
    }

    public List<AppearanceTextTransformationTransfer> getAppearanceTextTransformationTransfers(UserVisit userVisit, Collection<AppearanceTextTransformation> appearanceTextTransformations) {
        List<AppearanceTextTransformationTransfer> appearanceTextTransformationTransfers = new ArrayList<>(appearanceTextTransformations.size());
        var appearanceTextTransformationTransferCache = getCoreTransferCaches(userVisit).getAppearanceTextTransformationTransferCache();

        appearanceTextTransformations.forEach((appearanceTextTransformation) ->
                appearanceTextTransformationTransfers.add(appearanceTextTransformationTransferCache.getAppearanceTextTransformationTransfer(appearanceTextTransformation))
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
        appearanceTextTransformation.setThruTime(session.START_TIME_LONG);

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

    public EntityAppearance createEntityAppearance(EntityInstance entityInstance, Appearance appearance, BasePK createdBy) {
        var entityAppearance = EntityAppearanceFactory.getInstance().create(entityInstance, appearance, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);

        sendEvent(entityInstance, EventTypes.MODIFY, entityAppearance.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return entityAppearance;
    }

    private static final Map<EntityPermission, String> getEntityAppearanceQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                        "FROM entityappearances " +
                        "WHERE eniapprnc_eni_entityinstanceid = ? AND eniapprnc_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                        "FROM entityappearances " +
                        "WHERE eniapprnc_eni_entityinstanceid = ? AND eniapprnc_thrutime = ? " +
                        "FOR UPDATE");
        getEntityAppearanceQueries = Collections.unmodifiableMap(queryMap);
    }

    private EntityAppearance getEntityAppearance(EntityInstance entityInstance, EntityPermission entityPermission) {
        return EntityAppearanceFactory.getInstance().getEntityFromQuery(entityPermission, getEntityAppearanceQueries,
                entityInstance, Session.MAX_TIME);
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

    private static final Map<EntityPermission, String> getEntityAppearancesByAppearanceQueries;

    public List<EntityAppearance> getEntityAppearancesByEntityInstanceForUpdate(EntityInstance entityInstance) {
        List<EntityAppearance> entityAppearances;

        try {
            var ps = EntityAppearanceFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                            "FROM entityappearances " +
                            "WHERE eniapprnc_eni_entityinstanceid = ? AND eniapprnc_thrutime = ? " +
                            "FOR UPDATE");

            ps.setLong(1, entityInstance.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);

            entityAppearances = EntityAppearanceFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_WRITE, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return entityAppearances;
    }

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                        + "FROM entityappearances, entityinstances, entitytypes, entitytypedetails, componentvendors, componentvendordetails "
                        + "WHERE eniapprnc_apprnc_appearanceid = ? AND eniapprnc_thrutime = ? "
                        + "AND eniapprnc_eni_entityinstanceid = eni_entityinstanceid "
                        + "AND eni_ent_entitytypeid = ent_entitytypeid AND ent_lastdetailid = entdt_entitytypedetailid "
                        + "AND entdt_cvnd_componentvendorid = cvnd_componentvendorid AND cvnd_lastdetailid = cvndd_componentvendordetailid "
                        + "ORDER BY cvndd_componentvendorname, entdt_sortorder, entdt_entitytypename, eni_entityuniqueid");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                        + "FROM entityappearances "
                        + "WHERE eniapprnc_apprnc_appearanceid = ? AND eniapprnc_thrutime = ? "
                        + "FOR UPDATE");
        getEntityAppearancesByAppearanceQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<EntityAppearance> getEntityAppearancesByAppearance(Appearance appearance, EntityPermission entityPermission) {
        return EntityAppearanceFactory.getInstance().getEntitiesFromQuery(entityPermission, getEntityAppearancesByAppearanceQueries,
                appearance, Session.MAX_TIME);
    }

    public List<EntityAppearance> getEntityAppearancesByAppearance(Appearance appearance) {
        return getEntityAppearancesByAppearance(appearance, EntityPermission.READ_ONLY);
    }

    public List<EntityAppearance> getEntityAppearancesByAppearanceForUpdate(Appearance appearance) {
        return getEntityAppearancesByAppearance(appearance, EntityPermission.READ_WRITE);
    }

    public EntityAppearanceTransfer getEntityAppearanceTransfer(UserVisit userVisit, EntityAppearance entityAppearance) {
        return getCoreTransferCaches().getEntityAppearanceTransferCache().getEntityAppearanceTransfer(userVisit, entityAppearance);
    }

    public List<EntityAppearanceTransfer> getEntityAppearanceTransfersByAppearance(UserVisit userVisit, Appearance appearance) {
        var entityAppearances = getEntityAppearancesByAppearance(appearance);
        List<EntityAppearanceTransfer> entityAppearanceTransfers = new ArrayList<>(entityAppearances.size());
        var entityAppearanceTransferCache = getCoreTransferCaches(userVisit).getEntityAppearanceTransferCache();

        entityAppearances.forEach((entityAppearance) ->
                entityAppearanceTransfers.add(entityAppearanceTransferCache.getEntityAppearanceTransfer(entityAppearance))
        );

        return entityAppearanceTransfers;
    }

    public void updateEntityAppearanceFromValue(EntityAppearanceValue entityAppearanceValue, BasePK updatedBy) {
        if(entityAppearanceValue.hasBeenModified()) {
            var entityAppearance = EntityAppearanceFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    entityAppearanceValue.getPrimaryKey());

            entityAppearance.setThruTime(session.START_TIME_LONG);
            entityAppearance.store();

            var entityInstance = entityAppearance.getEntityInstance(); // Not updated.
            var appearancePK = entityAppearanceValue.getAppearancePK();

            entityAppearance = EntityAppearanceFactory.getInstance().create(entityInstance.getPrimaryKey(), appearancePK, session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEvent(entityInstance, EventTypes.MODIFY, entityAppearance.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteEntityAppearance(EntityAppearance entityAppearance, BasePK deletedBy) {
        entityAppearance.setThruTime(session.START_TIME_LONG);

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
