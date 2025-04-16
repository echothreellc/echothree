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
import com.echothree.model.control.core.common.choice.ColorChoicesBean;
import com.echothree.model.control.core.common.transfer.ColorDescriptionTransfer;
import com.echothree.model.control.core.common.transfer.ColorTransfer;
import com.echothree.model.data.core.common.pk.ColorPK;
import com.echothree.model.data.core.server.entity.Color;
import com.echothree.model.data.core.server.entity.ColorDescription;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.factory.ColorDescriptionFactory;
import com.echothree.model.data.core.server.factory.ColorDetailFactory;
import com.echothree.model.data.core.server.factory.ColorFactory;
import com.echothree.model.data.core.server.value.ColorDescriptionValue;
import com.echothree.model.data.core.server.value.ColorDetailValue;
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

public class ColorControl
        extends BaseCoreControl {

    /** Creates a new instance of ColorControl */
    public ColorControl() {
        super();
    }

    // --------------------------------------------------------------------------------
    //   Colors
    // --------------------------------------------------------------------------------

    public Color createColor(String colorName, Integer red, Integer green, Integer blue, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultColor = getDefaultColor();
        var defaultFound = defaultColor != null;

        if(defaultFound && isDefault) {
            var defaultColorDetailValue = getDefaultColorDetailValueForUpdate();

            defaultColorDetailValue.setIsDefault(Boolean.FALSE);
            updateColorFromValue(defaultColorDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }

        var color = ColorFactory.getInstance().create();
        var colorDetail = ColorDetailFactory.getInstance().create(color, colorName, red, green, blue, isDefault, sortOrder, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);

        // Convert to R/W
        color = ColorFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, color.getPrimaryKey());
        color.setActiveDetail(colorDetail);
        color.setLastDetail(colorDetail);
        color.store();

        sendEvent(color.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return color;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.Color */
    public Color getColorByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new ColorPK(entityInstance.getEntityUniqueId());

        return ColorFactory.getInstance().getEntityFromPK(entityPermission, pk);
    }

    public Color getColorByEntityInstance(EntityInstance entityInstance) {
        return getColorByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public Color getColorByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getColorByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countColors() {
        return session.queryForLong("""
                SELECT COUNT(*)
                FROM colors, colordetails
                WHERE clr_activedetailid = clrdt_colordetailid
                """);
    }

    private static final Map<EntityPermission, String> getColorByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                        "FROM colors, colordetails " +
                        "WHERE clr_activedetailid = clrdt_colordetailid " +
                        "AND clrdt_colorname = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                        "FROM colors, colordetails " +
                        "WHERE clr_activedetailid = clrdt_colordetailid " +
                        "AND clrdt_colorname = ? " +
                        "FOR UPDATE");
        getColorByNameQueries = Collections.unmodifiableMap(queryMap);
    }

    private Color getColorByName(String colorName, EntityPermission entityPermission) {
        return ColorFactory.getInstance().getEntityFromQuery(entityPermission, getColorByNameQueries, colorName);
    }

    public Color getColorByName(String colorName) {
        return getColorByName(colorName, EntityPermission.READ_ONLY);
    }

    public Color getColorByNameForUpdate(String colorName) {
        return getColorByName(colorName, EntityPermission.READ_WRITE);
    }

    public ColorDetailValue getColorDetailValueForUpdate(Color color) {
        return color == null? null: color.getLastDetailForUpdate().getColorDetailValue().clone();
    }

    public ColorDetailValue getColorDetailValueByNameForUpdate(String colorName) {
        return getColorDetailValueForUpdate(getColorByNameForUpdate(colorName));
    }

    private static final Map<EntityPermission, String> getDefaultColorQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                        "FROM colors, colordetails " +
                        "WHERE clr_activedetailid = clrdt_colordetailid " +
                        "AND clrdt_isdefault = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                        "FROM colors, colordetails " +
                        "WHERE clr_activedetailid = clrdt_colordetailid " +
                        "AND clrdt_isdefault = 1 " +
                        "FOR UPDATE");
        getDefaultColorQueries = Collections.unmodifiableMap(queryMap);
    }

    private Color getDefaultColor(EntityPermission entityPermission) {
        return ColorFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultColorQueries);
    }

    public Color getDefaultColor() {
        return getDefaultColor(EntityPermission.READ_ONLY);
    }

    public Color getDefaultColorForUpdate() {
        return getDefaultColor(EntityPermission.READ_WRITE);
    }

    public ColorDetailValue getDefaultColorDetailValueForUpdate() {
        return getDefaultColorForUpdate().getLastDetailForUpdate().getColorDetailValue().clone();
    }

    private static final Map<EntityPermission, String> getColorsQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                        "FROM colors, colordetails " +
                        "WHERE clr_activedetailid = clrdt_colordetailid " +
                        "ORDER BY clrdt_sortorder, clrdt_colorname " +
                        "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                        "FROM colors, colordetails " +
                        "WHERE clr_activedetailid = clrdt_colordetailid " +
                        "FOR UPDATE");
        getColorsQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<Color> getColors(EntityPermission entityPermission) {
        return ColorFactory.getInstance().getEntitiesFromQuery(entityPermission, getColorsQueries);
    }

    public List<Color> getColors() {
        return getColors(EntityPermission.READ_ONLY);
    }

    public List<Color> getColorsForUpdate() {
        return getColors(EntityPermission.READ_WRITE);
    }

    public ColorTransfer getColorTransfer(UserVisit userVisit, Color color) {
        return getCoreTransferCaches(userVisit).getColorTransferCache().getColorTransfer(color);
    }

    public List<ColorTransfer> getColorTransfers(UserVisit userVisit, Collection<Color> entities) {
        List<ColorTransfer> transfers = new ArrayList<>(entities.size());
        var transferCache = getCoreTransferCaches(userVisit).getColorTransferCache();

        entities.forEach((entity) ->
                transfers.add(transferCache.getColorTransfer(entity))
        );

        return transfers;
    }

    public List<ColorTransfer> getColorTransfers(UserVisit userVisit) {
        return getColorTransfers(userVisit, getColors());
    }

    public ColorChoicesBean getColorChoices(String defaultColorChoice, Language language, boolean allowNullChoice) {
        var colors = getColors();
        var size = colors.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultColorChoice == null) {
                defaultValue = "";
            }
        }

        for(var color : colors) {
            var colorDetail = color.getLastDetail();

            var label = getBestColorDescription(color, language);
            var value = colorDetail.getColorName();

            labels.add(label == null? value: label);
            values.add(value);

            var usingDefaultChoice = defaultColorChoice != null && defaultColorChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && colorDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new ColorChoicesBean(labels, values, defaultValue);
    }

    private void updateColorFromValue(ColorDetailValue colorDetailValue, boolean checkDefault, BasePK updatedBy) {
        if(colorDetailValue.hasBeenModified()) {
            var color = ColorFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    colorDetailValue.getColorPK());
            var colorDetail = color.getActiveDetailForUpdate();

            colorDetail.setThruTime(session.START_TIME_LONG);
            colorDetail.store();

            var colorPK = colorDetail.getColorPK(); // Not updated
            var colorName = colorDetailValue.getColorName();
            var red = colorDetailValue.getRed();
            var green = colorDetailValue.getGreen();
            var blue = colorDetailValue.getBlue();
            var isDefault = colorDetailValue.getIsDefault();
            var sortOrder = colorDetailValue.getSortOrder();

            if(checkDefault) {
                var defaultColor = getDefaultColor();
                var defaultFound = defaultColor != null && !defaultColor.equals(color);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultColorDetailValue = getDefaultColorDetailValueForUpdate();

                    defaultColorDetailValue.setIsDefault(Boolean.FALSE);
                    updateColorFromValue(defaultColorDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }

            colorDetail = ColorDetailFactory.getInstance().create(colorPK, colorName, red, green, blue, isDefault, sortOrder, session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);

            color.setActiveDetail(colorDetail);
            color.setLastDetail(colorDetail);

            sendEvent(colorPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public void updateColorFromValue(ColorDetailValue colorDetailValue, BasePK updatedBy) {
        updateColorFromValue(colorDetailValue, true, updatedBy);
    }

    private void deleteColor(Color color, boolean checkDefault, BasePK deletedBy) {
        var appearanceControl = Session.getModelController(AppearanceControl.class);
        var colorDetail = color.getLastDetailForUpdate();

        appearanceControl.deleteAppearancesByColor(color, deletedBy);
        deleteColorDescriptionsByColor(color, deletedBy);

        colorDetail.setThruTime(session.START_TIME_LONG);
        color.setActiveDetail(null);
        color.store();

        if(checkDefault) {
            // Check for default, and pick one if necessary
            var defaultColor = getDefaultColor();

            if(defaultColor == null) {
                var colors = getColorsForUpdate();

                if(!colors.isEmpty()) {
                    var iter = colors.iterator();
                    if(iter.hasNext()) {
                        defaultColor = iter.next();
                    }
                    var colorDetailValue = Objects.requireNonNull(defaultColor).getLastDetailForUpdate().getColorDetailValue().clone();

                    colorDetailValue.setIsDefault(Boolean.TRUE);
                    updateColorFromValue(colorDetailValue, false, deletedBy);
                }
            }
        }

        sendEvent(color.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    public void deleteColor(Color color, BasePK deletedBy) {
        deleteColor(color, true, deletedBy);
    }

    private void deleteColors(List<Color> colors, boolean checkDefault, BasePK deletedBy) {
        colors.forEach((color) -> deleteColor(color, checkDefault, deletedBy));
    }

    public void deleteColors(List<Color> colors, BasePK deletedBy) {
        deleteColors(colors, true, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Color Descriptions
    // --------------------------------------------------------------------------------

    public ColorDescription createColorDescription(Color color, Language language, String description, BasePK createdBy) {
        var colorDescription = ColorDescriptionFactory.getInstance().create(color, language, description,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEvent(color.getPrimaryKey(), EventTypes.MODIFY, colorDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return colorDescription;
    }

    private static final Map<EntityPermission, String> getColorDescriptionQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                        "FROM colordescriptions " +
                        "WHERE clrd_clr_colorid = ? AND clrd_lang_languageid = ? AND clrd_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                        "FROM colordescriptions " +
                        "WHERE clrd_clr_colorid = ? AND clrd_lang_languageid = ? AND clrd_thrutime = ? " +
                        "FOR UPDATE");
        getColorDescriptionQueries = Collections.unmodifiableMap(queryMap);
    }

    private ColorDescription getColorDescription(Color color, Language language, EntityPermission entityPermission) {
        return ColorDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, getColorDescriptionQueries,
                color, language, Session.MAX_TIME);
    }

    public ColorDescription getColorDescription(Color color, Language language) {
        return getColorDescription(color, language, EntityPermission.READ_ONLY);
    }

    public ColorDescription getColorDescriptionForUpdate(Color color, Language language) {
        return getColorDescription(color, language, EntityPermission.READ_WRITE);
    }

    public ColorDescriptionValue getColorDescriptionValue(ColorDescription colorDescription) {
        return colorDescription == null? null: colorDescription.getColorDescriptionValue().clone();
    }

    public ColorDescriptionValue getColorDescriptionValueForUpdate(Color color, Language language) {
        return getColorDescriptionValue(getColorDescriptionForUpdate(color, language));
    }

    private static final Map<EntityPermission, String> getColorDescriptionsByColorQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                        "FROM colordescriptions, languages " +
                        "WHERE clrd_clr_colorid = ? AND clrd_thrutime = ? AND clrd_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                        "FROM colordescriptions " +
                        "WHERE clrd_clr_colorid = ? AND clrd_thrutime = ? " +
                        "FOR UPDATE");
        getColorDescriptionsByColorQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<ColorDescription> getColorDescriptionsByColor(Color color, EntityPermission entityPermission) {
        return ColorDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, getColorDescriptionsByColorQueries,
                color, Session.MAX_TIME);
    }

    public List<ColorDescription> getColorDescriptionsByColor(Color color) {
        return getColorDescriptionsByColor(color, EntityPermission.READ_ONLY);
    }

    public List<ColorDescription> getColorDescriptionsByColorForUpdate(Color color) {
        return getColorDescriptionsByColor(color, EntityPermission.READ_WRITE);
    }

    public String getBestColorDescription(Color color, Language language) {
        String description;
        var colorDescription = getColorDescription(color, language);

        if(colorDescription == null && !language.getIsDefault()) {
            colorDescription = getColorDescription(color, getPartyControl().getDefaultLanguage());
        }

        if(colorDescription == null) {
            description = color.getLastDetail().getColorName();
        } else {
            description = colorDescription.getDescription();
        }

        return description;
    }

    public ColorDescriptionTransfer getColorDescriptionTransfer(UserVisit userVisit, ColorDescription colorDescription) {
        return getCoreTransferCaches(userVisit).getColorDescriptionTransferCache().getColorDescriptionTransfer(colorDescription);
    }

    public List<ColorDescriptionTransfer> getColorDescriptionTransfersByColor(UserVisit userVisit, Color color) {
        var colorDescriptions = getColorDescriptionsByColor(color);
        List<ColorDescriptionTransfer> colorDescriptionTransfers = new ArrayList<>(colorDescriptions.size());
        var colorDescriptionTransferCache = getCoreTransferCaches(userVisit).getColorDescriptionTransferCache();

        colorDescriptions.forEach((colorDescription) ->
                colorDescriptionTransfers.add(colorDescriptionTransferCache.getColorDescriptionTransfer(colorDescription))
        );

        return colorDescriptionTransfers;
    }

    public void updateColorDescriptionFromValue(ColorDescriptionValue colorDescriptionValue, BasePK updatedBy) {
        if(colorDescriptionValue.hasBeenModified()) {
            var colorDescription = ColorDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    colorDescriptionValue.getPrimaryKey());

            colorDescription.setThruTime(session.START_TIME_LONG);
            colorDescription.store();

            var color = colorDescription.getColor();
            var language = colorDescription.getLanguage();
            var description = colorDescriptionValue.getDescription();

            colorDescription = ColorDescriptionFactory.getInstance().create(color, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEvent(color.getPrimaryKey(), EventTypes.MODIFY, colorDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteColorDescription(ColorDescription colorDescription, BasePK deletedBy) {
        colorDescription.setThruTime(session.START_TIME_LONG);

        sendEvent(colorDescription.getColorPK(), EventTypes.MODIFY, colorDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }

    public void deleteColorDescriptionsByColor(Color color, BasePK deletedBy) {
        var colorDescriptions = getColorDescriptionsByColorForUpdate(color);

        colorDescriptions.forEach((colorDescription) ->
                deleteColorDescription(colorDescription, deletedBy)
        );
    }

}
