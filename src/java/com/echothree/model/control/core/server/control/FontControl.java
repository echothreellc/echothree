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
import com.echothree.model.control.core.common.choice.FontStyleChoicesBean;
import com.echothree.model.control.core.common.choice.FontWeightChoicesBean;
import com.echothree.model.control.core.common.transfer.FontStyleDescriptionTransfer;
import com.echothree.model.control.core.common.transfer.FontStyleTransfer;
import com.echothree.model.control.core.common.transfer.FontWeightDescriptionTransfer;
import com.echothree.model.control.core.common.transfer.FontWeightTransfer;
import com.echothree.model.data.core.common.pk.FontStylePK;
import com.echothree.model.data.core.common.pk.FontWeightPK;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.FontStyle;
import com.echothree.model.data.core.server.entity.FontStyleDescription;
import com.echothree.model.data.core.server.entity.FontWeight;
import com.echothree.model.data.core.server.entity.FontWeightDescription;
import com.echothree.model.data.core.server.factory.FontStyleDescriptionFactory;
import com.echothree.model.data.core.server.factory.FontStyleDetailFactory;
import com.echothree.model.data.core.server.factory.FontStyleFactory;
import com.echothree.model.data.core.server.factory.FontWeightDescriptionFactory;
import com.echothree.model.data.core.server.factory.FontWeightDetailFactory;
import com.echothree.model.data.core.server.factory.FontWeightFactory;
import com.echothree.model.data.core.server.value.FontStyleDescriptionValue;
import com.echothree.model.data.core.server.value.FontStyleDetailValue;
import com.echothree.model.data.core.server.value.FontWeightDescriptionValue;
import com.echothree.model.data.core.server.value.FontWeightDetailValue;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class FontControl
        extends BaseCoreControl {

    /** Creates a new instance of FontControl */
    protected FontControl() {
        super();
    }

    // --------------------------------------------------------------------------------
    //   Font Styles
    // --------------------------------------------------------------------------------

    public FontStyle createFontStyle(String fontStyleName, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultFontStyle = getDefaultFontStyle();
        var defaultFound = defaultFontStyle != null;

        if(defaultFound && isDefault) {
            var defaultFontStyleDetailValue = getDefaultFontStyleDetailValueForUpdate();

            defaultFontStyleDetailValue.setIsDefault(false);
            updateFontStyleFromValue(defaultFontStyleDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var fontStyle = FontStyleFactory.getInstance().create();
        var fontStyleDetail = FontStyleDetailFactory.getInstance().create(fontStyle, fontStyleName, isDefault, sortOrder, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);

        // Convert to R/W
        fontStyle = FontStyleFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, fontStyle.getPrimaryKey());
        fontStyle.setActiveDetail(fontStyleDetail);
        fontStyle.setLastDetail(fontStyleDetail);
        fontStyle.store();

        sendEvent(fontStyle.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return fontStyle;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.FontStyle */
    public FontStyle getFontStyleByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new FontStylePK(entityInstance.getEntityUniqueId());

        return FontStyleFactory.getInstance().getEntityFromPK(entityPermission, pk);
    }

    public FontStyle getFontStyleByEntityInstance(EntityInstance entityInstance) {
        return getFontStyleByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public FontStyle getFontStyleByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getFontStyleByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countFontStyles() {
        return session.queryForLong("""
                SELECT COUNT(*)
                FROM fontstyles, fontstyledetails
                WHERE fntstyl_activedetailid = fntstyldt_fontstyledetailid
                """);
    }

    private static final Map<EntityPermission, String> getFontStyleByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                        "FROM fontstyles, fontstyledetails " +
                        "WHERE fntstyl_activedetailid = fntstyldt_fontstyledetailid " +
                        "AND fntstyldt_fontstylename = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                        "FROM fontstyles, fontstyledetails " +
                        "WHERE fntstyl_activedetailid = fntstyldt_fontstyledetailid " +
                        "AND fntstyldt_fontstylename = ? " +
                        "FOR UPDATE");
        getFontStyleByNameQueries = Collections.unmodifiableMap(queryMap);
    }

    private FontStyle getFontStyleByName(String fontStyleName, EntityPermission entityPermission) {
        return FontStyleFactory.getInstance().getEntityFromQuery(entityPermission, getFontStyleByNameQueries, fontStyleName);
    }

    public FontStyle getFontStyleByName(String fontStyleName) {
        return getFontStyleByName(fontStyleName, EntityPermission.READ_ONLY);
    }

    public FontStyle getFontStyleByNameForUpdate(String fontStyleName) {
        return getFontStyleByName(fontStyleName, EntityPermission.READ_WRITE);
    }

    public FontStyleDetailValue getFontStyleDetailValueForUpdate(FontStyle fontStyle) {
        return fontStyle == null? null: fontStyle.getLastDetailForUpdate().getFontStyleDetailValue().clone();
    }

    public FontStyleDetailValue getFontStyleDetailValueByNameForUpdate(String fontStyleName) {
        return getFontStyleDetailValueForUpdate(getFontStyleByNameForUpdate(fontStyleName));
    }

    private static final Map<EntityPermission, String> getDefaultFontStyleQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                        "FROM fontstyles, fontstyledetails " +
                        "WHERE fntstyl_activedetailid = fntstyldt_fontstyledetailid " +
                        "AND fntstyldt_isdefault = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                        "FROM fontstyles, fontstyledetails " +
                        "WHERE fntstyl_activedetailid = fntstyldt_fontstyledetailid " +
                        "AND fntstyldt_isdefault = 1 " +
                        "FOR UPDATE");
        getDefaultFontStyleQueries = Collections.unmodifiableMap(queryMap);
    }

    private FontStyle getDefaultFontStyle(EntityPermission entityPermission) {
        return FontStyleFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultFontStyleQueries);
    }

    public FontStyle getDefaultFontStyle() {
        return getDefaultFontStyle(EntityPermission.READ_ONLY);
    }

    public FontStyle getDefaultFontStyleForUpdate() {
        return getDefaultFontStyle(EntityPermission.READ_WRITE);
    }

    public FontStyleDetailValue getDefaultFontStyleDetailValueForUpdate() {
        return getDefaultFontStyleForUpdate().getLastDetailForUpdate().getFontStyleDetailValue().clone();
    }

    private static final Map<EntityPermission, String> getFontStylesQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                        "FROM fontstyles, fontstyledetails " +
                        "WHERE fntstyl_activedetailid = fntstyldt_fontstyledetailid " +
                        "ORDER BY fntstyldt_sortorder, fntstyldt_fontstylename " +
                        "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                        "FROM fontstyles, fontstyledetails " +
                        "WHERE fntstyl_activedetailid = fntstyldt_fontstyledetailid " +
                        "FOR UPDATE");
        getFontStylesQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<FontStyle> getFontStyles(EntityPermission entityPermission) {
        return FontStyleFactory.getInstance().getEntitiesFromQuery(entityPermission, getFontStylesQueries);
    }

    public List<FontStyle> getFontStyles() {
        return getFontStyles(EntityPermission.READ_ONLY);
    }

    public List<FontStyle> getFontStylesForUpdate() {
        return getFontStyles(EntityPermission.READ_WRITE);
    }

    public FontStyleTransfer getFontStyleTransfer(UserVisit userVisit, FontStyle fontStyle) {
        return fontStyleTransferCache.getFontStyleTransfer(userVisit, fontStyle);
    }

    public List<FontStyleTransfer> getFontStyleTransfers(UserVisit userVisit, Collection<FontStyle> entities) {
        List<FontStyleTransfer> transfers = new ArrayList<>(entities.size());

        entities.forEach((entity) ->
                transfers.add(fontStyleTransferCache.getFontStyleTransfer(userVisit, entity))
        );

        return transfers;
    }

    public List<FontStyleTransfer> getFontStyleTransfers(UserVisit userVisit) {
        return getFontStyleTransfers(userVisit, getFontStyles());
    }

    public FontStyleChoicesBean getFontStyleChoices(String defaultFontStyleChoice, Language language, boolean allowNullChoice) {
        var fontStyles = getFontStyles();
        var size = fontStyles.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultFontStyleChoice == null) {
                defaultValue = "";
            }
        }

        for(var fontStyle : fontStyles) {
            var fontStyleDetail = fontStyle.getLastDetail();

            var label = getBestFontStyleDescription(fontStyle, language);
            var value = fontStyleDetail.getFontStyleName();

            labels.add(label == null? value: label);
            values.add(value);

            var usingDefaultChoice = defaultFontStyleChoice != null && defaultFontStyleChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && fontStyleDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new FontStyleChoicesBean(labels, values, defaultValue);
    }

    private void updateFontStyleFromValue(FontStyleDetailValue fontStyleDetailValue, boolean checkDefault, BasePK updatedBy) {
        if(fontStyleDetailValue.hasBeenModified()) {
            var fontStyle = FontStyleFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    fontStyleDetailValue.getFontStylePK());
            var fontStyleDetail = fontStyle.getActiveDetailForUpdate();

            fontStyleDetail.setThruTime(session.START_TIME_LONG);
            fontStyleDetail.store();

            var fontStylePK = fontStyleDetail.getFontStylePK(); // Not updated
            var fontStyleName = fontStyleDetailValue.getFontStyleName();
            var isDefault = fontStyleDetailValue.getIsDefault();
            var sortOrder = fontStyleDetailValue.getSortOrder();

            if(checkDefault) {
                var defaultFontStyle = getDefaultFontStyle();
                var defaultFound = defaultFontStyle != null && !defaultFontStyle.equals(fontStyle);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultFontStyleDetailValue = getDefaultFontStyleDetailValueForUpdate();

                    defaultFontStyleDetailValue.setIsDefault(false);
                    updateFontStyleFromValue(defaultFontStyleDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }

            fontStyleDetail = FontStyleDetailFactory.getInstance().create(fontStylePK, fontStyleName, isDefault, sortOrder, session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);

            fontStyle.setActiveDetail(fontStyleDetail);
            fontStyle.setLastDetail(fontStyleDetail);

            sendEvent(fontStylePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public void updateFontStyleFromValue(FontStyleDetailValue fontStyleDetailValue, BasePK updatedBy) {
        updateFontStyleFromValue(fontStyleDetailValue, true, updatedBy);
    }

    private void deleteFontStyle(FontStyle fontStyle, boolean checkDefault, BasePK deletedBy) {
        var appearanceControl = Session.getModelController(AppearanceControl.class);
        var fontStyleDetail = fontStyle.getLastDetailForUpdate();

        appearanceControl.deleteAppearancesByFontStyle(fontStyle, deletedBy);
        deleteFontStyleDescriptionsByFontStyle(fontStyle, deletedBy);

        fontStyleDetail.setThruTime(session.START_TIME_LONG);
        fontStyle.setActiveDetail(null);
        fontStyle.store();

        if(checkDefault) {
            // Check for default, and pick one if necessary
            var defaultFontStyle = getDefaultFontStyle();

            if(defaultFontStyle == null) {
                var fontStyles = getFontStylesForUpdate();

                if(!fontStyles.isEmpty()) {
                    var iter = fontStyles.iterator();
                    if(iter.hasNext()) {
                        defaultFontStyle = iter.next();
                    }
                    var fontStyleDetailValue = Objects.requireNonNull(defaultFontStyle).getLastDetailForUpdate().getFontStyleDetailValue().clone();

                    fontStyleDetailValue.setIsDefault(true);
                    updateFontStyleFromValue(fontStyleDetailValue, false, deletedBy);
                }
            }
        }

        sendEvent(fontStyle.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    public void deleteFontStyle(FontStyle fontStyle, BasePK deletedBy) {
        deleteFontStyle(fontStyle, true, deletedBy);
    }

    private void deleteFontStyles(List<FontStyle> fontStyles, boolean checkDefault, BasePK deletedBy) {
        fontStyles.forEach((fontStyle) -> deleteFontStyle(fontStyle, checkDefault, deletedBy));
    }

    public void deleteFontStyles(List<FontStyle> fontStyles, BasePK deletedBy) {
        deleteFontStyles(fontStyles, true, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Font Style Descriptions
    // --------------------------------------------------------------------------------

    public FontStyleDescription createFontStyleDescription(FontStyle fontStyle, Language language, String description, BasePK createdBy) {
        var fontStyleDescription = FontStyleDescriptionFactory.getInstance().create(fontStyle, language, description,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEvent(fontStyle.getPrimaryKey(), EventTypes.MODIFY, fontStyleDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return fontStyleDescription;
    }

    private static final Map<EntityPermission, String> getFontStyleDescriptionQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                        "FROM fontstyledescriptions " +
                        "WHERE fntstyld_fntstyl_fontstyleid = ? AND fntstyld_lang_languageid = ? AND fntstyld_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                        "FROM fontstyledescriptions " +
                        "WHERE fntstyld_fntstyl_fontstyleid = ? AND fntstyld_lang_languageid = ? AND fntstyld_thrutime = ? " +
                        "FOR UPDATE");
        getFontStyleDescriptionQueries = Collections.unmodifiableMap(queryMap);
    }

    private FontStyleDescription getFontStyleDescription(FontStyle fontStyle, Language language, EntityPermission entityPermission) {
        return FontStyleDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, getFontStyleDescriptionQueries,
                fontStyle, language, Session.MAX_TIME);
    }

    public FontStyleDescription getFontStyleDescription(FontStyle fontStyle, Language language) {
        return getFontStyleDescription(fontStyle, language, EntityPermission.READ_ONLY);
    }

    public FontStyleDescription getFontStyleDescriptionForUpdate(FontStyle fontStyle, Language language) {
        return getFontStyleDescription(fontStyle, language, EntityPermission.READ_WRITE);
    }

    public FontStyleDescriptionValue getFontStyleDescriptionValue(FontStyleDescription fontStyleDescription) {
        return fontStyleDescription == null? null: fontStyleDescription.getFontStyleDescriptionValue().clone();
    }

    public FontStyleDescriptionValue getFontStyleDescriptionValueForUpdate(FontStyle fontStyle, Language language) {
        return getFontStyleDescriptionValue(getFontStyleDescriptionForUpdate(fontStyle, language));
    }

    private static final Map<EntityPermission, String> getFontStyleDescriptionsByFontStyleQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                        "FROM fontstyledescriptions, languages " +
                        "WHERE fntstyld_fntstyl_fontstyleid = ? AND fntstyld_thrutime = ? AND fntstyld_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                        "FROM fontstyledescriptions " +
                        "WHERE fntstyld_fntstyl_fontstyleid = ? AND fntstyld_thrutime = ? " +
                        "FOR UPDATE");
        getFontStyleDescriptionsByFontStyleQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<FontStyleDescription> getFontStyleDescriptionsByFontStyle(FontStyle fontStyle, EntityPermission entityPermission) {
        return FontStyleDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, getFontStyleDescriptionsByFontStyleQueries,
                fontStyle, Session.MAX_TIME);
    }

    public List<FontStyleDescription> getFontStyleDescriptionsByFontStyle(FontStyle fontStyle) {
        return getFontStyleDescriptionsByFontStyle(fontStyle, EntityPermission.READ_ONLY);
    }

    public List<FontStyleDescription> getFontStyleDescriptionsByFontStyleForUpdate(FontStyle fontStyle) {
        return getFontStyleDescriptionsByFontStyle(fontStyle, EntityPermission.READ_WRITE);
    }

    public String getBestFontStyleDescription(FontStyle fontStyle, Language language) {
        String description;
        var fontStyleDescription = getFontStyleDescription(fontStyle, language);

        if(fontStyleDescription == null && !language.getIsDefault()) {
            fontStyleDescription = getFontStyleDescription(fontStyle, partyControl.getDefaultLanguage());
        }

        if(fontStyleDescription == null) {
            description = fontStyle.getLastDetail().getFontStyleName();
        } else {
            description = fontStyleDescription.getDescription();
        }

        return description;
    }

    public FontStyleDescriptionTransfer getFontStyleDescriptionTransfer(UserVisit userVisit, FontStyleDescription fontStyleDescription) {
        return fontStyleDescriptionTransferCache.getFontStyleDescriptionTransfer(userVisit, fontStyleDescription);
    }

    public List<FontStyleDescriptionTransfer> getFontStyleDescriptionTransfersByFontStyle(UserVisit userVisit, FontStyle fontStyle) {
        var fontStyleDescriptions = getFontStyleDescriptionsByFontStyle(fontStyle);
        List<FontStyleDescriptionTransfer> fontStyleDescriptionTransfers = new ArrayList<>(fontStyleDescriptions.size());

        fontStyleDescriptions.forEach((fontStyleDescription) ->
                fontStyleDescriptionTransfers.add(fontStyleDescriptionTransferCache.getFontStyleDescriptionTransfer(userVisit, fontStyleDescription))
        );

        return fontStyleDescriptionTransfers;
    }

    public void updateFontStyleDescriptionFromValue(FontStyleDescriptionValue fontStyleDescriptionValue, BasePK updatedBy) {
        if(fontStyleDescriptionValue.hasBeenModified()) {
            var fontStyleDescription = FontStyleDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    fontStyleDescriptionValue.getPrimaryKey());

            fontStyleDescription.setThruTime(session.START_TIME_LONG);
            fontStyleDescription.store();

            var fontStyle = fontStyleDescription.getFontStyle();
            var language = fontStyleDescription.getLanguage();
            var description = fontStyleDescriptionValue.getDescription();

            fontStyleDescription = FontStyleDescriptionFactory.getInstance().create(fontStyle, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEvent(fontStyle.getPrimaryKey(), EventTypes.MODIFY, fontStyleDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteFontStyleDescription(FontStyleDescription fontStyleDescription, BasePK deletedBy) {
        fontStyleDescription.setThruTime(session.START_TIME_LONG);

        sendEvent(fontStyleDescription.getFontStylePK(), EventTypes.MODIFY, fontStyleDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }

    public void deleteFontStyleDescriptionsByFontStyle(FontStyle fontStyle, BasePK deletedBy) {
        var fontStyleDescriptions = getFontStyleDescriptionsByFontStyleForUpdate(fontStyle);

        fontStyleDescriptions.forEach((fontStyleDescription) ->
                deleteFontStyleDescription(fontStyleDescription, deletedBy)
        );
    }

    // --------------------------------------------------------------------------------
    //   Font Weights
    // --------------------------------------------------------------------------------

    public FontWeight createFontWeight(String fontWeightName, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultFontWeight = getDefaultFontWeight();
        var defaultFound = defaultFontWeight != null;

        if(defaultFound && isDefault) {
            var defaultFontWeightDetailValue = getDefaultFontWeightDetailValueForUpdate();

            defaultFontWeightDetailValue.setIsDefault(false);
            updateFontWeightFromValue(defaultFontWeightDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var fontWeight = FontWeightFactory.getInstance().create();
        var fontWeightDetail = FontWeightDetailFactory.getInstance().create(fontWeight, fontWeightName, isDefault, sortOrder, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);

        // Convert to R/W
        fontWeight = FontWeightFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, fontWeight.getPrimaryKey());
        fontWeight.setActiveDetail(fontWeightDetail);
        fontWeight.setLastDetail(fontWeightDetail);
        fontWeight.store();

        sendEvent(fontWeight.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return fontWeight;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.FontWeight */
    public FontWeight getFontWeightByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new FontWeightPK(entityInstance.getEntityUniqueId());

        return FontWeightFactory.getInstance().getEntityFromPK(entityPermission, pk);
    }

    public FontWeight getFontWeightByEntityInstance(EntityInstance entityInstance) {
        return getFontWeightByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public FontWeight getFontWeightByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getFontWeightByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countFontWeights() {
        return session.queryForLong("""
                SELECT COUNT(*)
                FROM fontweights, fontweightdetails
                WHERE fntwght_activedetailid = fntwghtdt_fontweightdetailid
                """);
    }

    private static final Map<EntityPermission, String> getFontWeightByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                        "FROM fontweights, fontweightdetails " +
                        "WHERE fntwght_activedetailid = fntwghtdt_fontweightdetailid " +
                        "AND fntwghtdt_fontweightname = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                        "FROM fontweights, fontweightdetails " +
                        "WHERE fntwght_activedetailid = fntwghtdt_fontweightdetailid " +
                        "AND fntwghtdt_fontweightname = ? " +
                        "FOR UPDATE");
        getFontWeightByNameQueries = Collections.unmodifiableMap(queryMap);
    }

    private FontWeight getFontWeightByName(String fontWeightName, EntityPermission entityPermission) {
        return FontWeightFactory.getInstance().getEntityFromQuery(entityPermission, getFontWeightByNameQueries, fontWeightName);
    }

    public FontWeight getFontWeightByName(String fontWeightName) {
        return getFontWeightByName(fontWeightName, EntityPermission.READ_ONLY);
    }

    public FontWeight getFontWeightByNameForUpdate(String fontWeightName) {
        return getFontWeightByName(fontWeightName, EntityPermission.READ_WRITE);
    }

    public FontWeightDetailValue getFontWeightDetailValueForUpdate(FontWeight fontWeight) {
        return fontWeight == null? null: fontWeight.getLastDetailForUpdate().getFontWeightDetailValue().clone();
    }

    public FontWeightDetailValue getFontWeightDetailValueByNameForUpdate(String fontWeightName) {
        return getFontWeightDetailValueForUpdate(getFontWeightByNameForUpdate(fontWeightName));
    }

    private static final Map<EntityPermission, String> getDefaultFontWeightQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                        "FROM fontweights, fontweightdetails " +
                        "WHERE fntwght_activedetailid = fntwghtdt_fontweightdetailid " +
                        "AND fntwghtdt_isdefault = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                        "FROM fontweights, fontweightdetails " +
                        "WHERE fntwght_activedetailid = fntwghtdt_fontweightdetailid " +
                        "AND fntwghtdt_isdefault = 1 " +
                        "FOR UPDATE");
        getDefaultFontWeightQueries = Collections.unmodifiableMap(queryMap);
    }

    private FontWeight getDefaultFontWeight(EntityPermission entityPermission) {
        return FontWeightFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultFontWeightQueries);
    }

    public FontWeight getDefaultFontWeight() {
        return getDefaultFontWeight(EntityPermission.READ_ONLY);
    }

    public FontWeight getDefaultFontWeightForUpdate() {
        return getDefaultFontWeight(EntityPermission.READ_WRITE);
    }

    public FontWeightDetailValue getDefaultFontWeightDetailValueForUpdate() {
        return getDefaultFontWeightForUpdate().getLastDetailForUpdate().getFontWeightDetailValue().clone();
    }

    private static final Map<EntityPermission, String> getFontWeightsQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                        "FROM fontweights, fontweightdetails " +
                        "WHERE fntwght_activedetailid = fntwghtdt_fontweightdetailid " +
                        "ORDER BY fntwghtdt_sortorder, fntwghtdt_fontweightname " +
                        "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                        "FROM fontweights, fontweightdetails " +
                        "WHERE fntwght_activedetailid = fntwghtdt_fontweightdetailid " +
                        "FOR UPDATE");
        getFontWeightsQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<FontWeight> getFontWeights(EntityPermission entityPermission) {
        return FontWeightFactory.getInstance().getEntitiesFromQuery(entityPermission, getFontWeightsQueries);
    }

    public List<FontWeight> getFontWeights() {
        return getFontWeights(EntityPermission.READ_ONLY);
    }

    public List<FontWeight> getFontWeightsForUpdate() {
        return getFontWeights(EntityPermission.READ_WRITE);
    }

    public FontWeightTransfer getFontWeightTransfer(UserVisit userVisit, FontWeight fontWeight) {
        return fontWeightTransferCache.getFontWeightTransfer(userVisit, fontWeight);
    }

    public List<FontWeightTransfer> getFontWeightTransfers(UserVisit userVisit, Collection<FontWeight> entities) {
        List<FontWeightTransfer> transfers = new ArrayList<>(entities.size());

        entities.forEach((entity) ->
                transfers.add(fontWeightTransferCache.getFontWeightTransfer(userVisit, entity))
        );

        return transfers;
    }

    public List<FontWeightTransfer> getFontWeightTransfers(UserVisit userVisit) {
        return getFontWeightTransfers(userVisit, getFontWeights());
    }

    public FontWeightChoicesBean getFontWeightChoices(String defaultFontWeightChoice, Language language, boolean allowNullChoice) {
        var fontWeights = getFontWeights();
        var size = fontWeights.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultFontWeightChoice == null) {
                defaultValue = "";
            }
        }

        for(var fontWeight : fontWeights) {
            var fontWeightDetail = fontWeight.getLastDetail();

            var label = getBestFontWeightDescription(fontWeight, language);
            var value = fontWeightDetail.getFontWeightName();

            labels.add(label == null? value: label);
            values.add(value);

            var usingDefaultChoice = defaultFontWeightChoice != null && defaultFontWeightChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && fontWeightDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new FontWeightChoicesBean(labels, values, defaultValue);
    }

    private void updateFontWeightFromValue(FontWeightDetailValue fontWeightDetailValue, boolean checkDefault, BasePK updatedBy) {
        if(fontWeightDetailValue.hasBeenModified()) {
            var fontWeight = FontWeightFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    fontWeightDetailValue.getFontWeightPK());
            var fontWeightDetail = fontWeight.getActiveDetailForUpdate();

            fontWeightDetail.setThruTime(session.START_TIME_LONG);
            fontWeightDetail.store();

            var fontWeightPK = fontWeightDetail.getFontWeightPK(); // Not updated
            var fontWeightName = fontWeightDetailValue.getFontWeightName();
            var isDefault = fontWeightDetailValue.getIsDefault();
            var sortOrder = fontWeightDetailValue.getSortOrder();

            if(checkDefault) {
                var defaultFontWeight = getDefaultFontWeight();
                var defaultFound = defaultFontWeight != null && !defaultFontWeight.equals(fontWeight);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultFontWeightDetailValue = getDefaultFontWeightDetailValueForUpdate();

                    defaultFontWeightDetailValue.setIsDefault(false);
                    updateFontWeightFromValue(defaultFontWeightDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }

            fontWeightDetail = FontWeightDetailFactory.getInstance().create(fontWeightPK, fontWeightName, isDefault, sortOrder, session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);

            fontWeight.setActiveDetail(fontWeightDetail);
            fontWeight.setLastDetail(fontWeightDetail);

            sendEvent(fontWeightPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public void updateFontWeightFromValue(FontWeightDetailValue fontWeightDetailValue, BasePK updatedBy) {
        updateFontWeightFromValue(fontWeightDetailValue, true, updatedBy);
    }

    private void deleteFontWeight(FontWeight fontWeight, boolean checkDefault, BasePK deletedBy) {
        var appearanceControl = Session.getModelController(AppearanceControl.class);
        var fontWeightDetail = fontWeight.getLastDetailForUpdate();

        appearanceControl.deleteAppearancesByFontWeight(fontWeight, deletedBy);
        deleteFontWeightDescriptionsByFontWeight(fontWeight, deletedBy);

        fontWeightDetail.setThruTime(session.START_TIME_LONG);
        fontWeight.setActiveDetail(null);
        fontWeight.store();

        if(checkDefault) {
            // Check for default, and pick one if necessary
            var defaultFontWeight = getDefaultFontWeight();

            if(defaultFontWeight == null) {
                var fontWeights = getFontWeightsForUpdate();

                if(!fontWeights.isEmpty()) {
                    var iter = fontWeights.iterator();
                    if(iter.hasNext()) {
                        defaultFontWeight = iter.next();
                    }
                    var fontWeightDetailValue = Objects.requireNonNull(defaultFontWeight).getLastDetailForUpdate().getFontWeightDetailValue().clone();

                    fontWeightDetailValue.setIsDefault(true);
                    updateFontWeightFromValue(fontWeightDetailValue, false, deletedBy);
                }
            }
        }

        sendEvent(fontWeight.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    public void deleteFontWeight(FontWeight fontWeight, BasePK deletedBy) {
        deleteFontWeight(fontWeight, true, deletedBy);
    }

    private void deleteFontWeights(List<FontWeight> fontWeights, boolean checkDefault, BasePK deletedBy) {
        fontWeights.forEach((fontWeight) -> deleteFontWeight(fontWeight, checkDefault, deletedBy));
    }

    public void deleteFontWeights(List<FontWeight> fontWeights, BasePK deletedBy) {
        deleteFontWeights(fontWeights, true, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Font Weight Descriptions
    // --------------------------------------------------------------------------------

    public FontWeightDescription createFontWeightDescription(FontWeight fontWeight, Language language, String description, BasePK createdBy) {
        var fontWeightDescription = FontWeightDescriptionFactory.getInstance().create(fontWeight, language, description,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEvent(fontWeight.getPrimaryKey(), EventTypes.MODIFY, fontWeightDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return fontWeightDescription;
    }

    private static final Map<EntityPermission, String> getFontWeightDescriptionQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                        "FROM fontweightdescriptions " +
                        "WHERE fntwghtd_fntwght_fontweightid = ? AND fntwghtd_lang_languageid = ? AND fntwghtd_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                        "FROM fontweightdescriptions " +
                        "WHERE fntwghtd_fntwght_fontweightid = ? AND fntwghtd_lang_languageid = ? AND fntwghtd_thrutime = ? " +
                        "FOR UPDATE");
        getFontWeightDescriptionQueries = Collections.unmodifiableMap(queryMap);
    }

    private FontWeightDescription getFontWeightDescription(FontWeight fontWeight, Language language, EntityPermission entityPermission) {
        return FontWeightDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, getFontWeightDescriptionQueries,
                fontWeight, language, Session.MAX_TIME);
    }

    public FontWeightDescription getFontWeightDescription(FontWeight fontWeight, Language language) {
        return getFontWeightDescription(fontWeight, language, EntityPermission.READ_ONLY);
    }

    public FontWeightDescription getFontWeightDescriptionForUpdate(FontWeight fontWeight, Language language) {
        return getFontWeightDescription(fontWeight, language, EntityPermission.READ_WRITE);
    }

    public FontWeightDescriptionValue getFontWeightDescriptionValue(FontWeightDescription fontWeightDescription) {
        return fontWeightDescription == null? null: fontWeightDescription.getFontWeightDescriptionValue().clone();
    }

    public FontWeightDescriptionValue getFontWeightDescriptionValueForUpdate(FontWeight fontWeight, Language language) {
        return getFontWeightDescriptionValue(getFontWeightDescriptionForUpdate(fontWeight, language));
    }

    private static final Map<EntityPermission, String> getFontWeightDescriptionsByFontWeightQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                        "FROM fontweightdescriptions, languages " +
                        "WHERE fntwghtd_fntwght_fontweightid = ? AND fntwghtd_thrutime = ? AND fntwghtd_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                        "FROM fontweightdescriptions " +
                        "WHERE fntwghtd_fntwght_fontweightid = ? AND fntwghtd_thrutime = ? " +
                        "FOR UPDATE");
        getFontWeightDescriptionsByFontWeightQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<FontWeightDescription> getFontWeightDescriptionsByFontWeight(FontWeight fontWeight, EntityPermission entityPermission) {
        return FontWeightDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, getFontWeightDescriptionsByFontWeightQueries,
                fontWeight, Session.MAX_TIME);
    }

    public List<FontWeightDescription> getFontWeightDescriptionsByFontWeight(FontWeight fontWeight) {
        return getFontWeightDescriptionsByFontWeight(fontWeight, EntityPermission.READ_ONLY);
    }

    public List<FontWeightDescription> getFontWeightDescriptionsByFontWeightForUpdate(FontWeight fontWeight) {
        return getFontWeightDescriptionsByFontWeight(fontWeight, EntityPermission.READ_WRITE);
    }

    public String getBestFontWeightDescription(FontWeight fontWeight, Language language) {
        String description;
        var fontWeightDescription = getFontWeightDescription(fontWeight, language);

        if(fontWeightDescription == null && !language.getIsDefault()) {
            fontWeightDescription = getFontWeightDescription(fontWeight, partyControl.getDefaultLanguage());
        }

        if(fontWeightDescription == null) {
            description = fontWeight.getLastDetail().getFontWeightName();
        } else {
            description = fontWeightDescription.getDescription();
        }

        return description;
    }

    public FontWeightDescriptionTransfer getFontWeightDescriptionTransfer(UserVisit userVisit, FontWeightDescription fontWeightDescription) {
        return fontWeightDescriptionTransferCache.getFontWeightDescriptionTransfer(userVisit, fontWeightDescription);
    }

    public List<FontWeightDescriptionTransfer> getFontWeightDescriptionTransfersByFontWeight(UserVisit userVisit, FontWeight fontWeight) {
        var fontWeightDescriptions = getFontWeightDescriptionsByFontWeight(fontWeight);
        List<FontWeightDescriptionTransfer> fontWeightDescriptionTransfers = new ArrayList<>(fontWeightDescriptions.size());

        fontWeightDescriptions.forEach((fontWeightDescription) ->
                fontWeightDescriptionTransfers.add(fontWeightDescriptionTransferCache.getFontWeightDescriptionTransfer(userVisit, fontWeightDescription))
        );

        return fontWeightDescriptionTransfers;
    }

    public void updateFontWeightDescriptionFromValue(FontWeightDescriptionValue fontWeightDescriptionValue, BasePK updatedBy) {
        if(fontWeightDescriptionValue.hasBeenModified()) {
            var fontWeightDescription = FontWeightDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    fontWeightDescriptionValue.getPrimaryKey());

            fontWeightDescription.setThruTime(session.START_TIME_LONG);
            fontWeightDescription.store();

            var fontWeight = fontWeightDescription.getFontWeight();
            var language = fontWeightDescription.getLanguage();
            var description = fontWeightDescriptionValue.getDescription();

            fontWeightDescription = FontWeightDescriptionFactory.getInstance().create(fontWeight, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEvent(fontWeight.getPrimaryKey(), EventTypes.MODIFY, fontWeightDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteFontWeightDescription(FontWeightDescription fontWeightDescription, BasePK deletedBy) {
        fontWeightDescription.setThruTime(session.START_TIME_LONG);

        sendEvent(fontWeightDescription.getFontWeightPK(), EventTypes.MODIFY, fontWeightDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }

    public void deleteFontWeightDescriptionsByFontWeight(FontWeight fontWeight, BasePK deletedBy) {
        var fontWeightDescriptions = getFontWeightDescriptionsByFontWeightForUpdate(fontWeight);

        fontWeightDescriptions.forEach((fontWeightDescription) ->
                deleteFontWeightDescription(fontWeightDescription, deletedBy)
        );
    }

}
