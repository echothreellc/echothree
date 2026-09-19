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
import com.echothree.model.control.core.common.choice.ColorChoicesBean;
import com.echothree.model.control.core.common.transfer.ColorDescriptionTransfer;
import com.echothree.model.control.core.common.transfer.ColorTransfer;
import com.echothree.model.control.core.server.transfer.ColorDescriptionTransferCache;
import com.echothree.model.control.core.server.transfer.ColorTransferCache;
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
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.COLORS_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.COLOR_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.tables.core.ColorDescriptions.ColorDescriptions;
import static com.echothree.model.jooq.server.tables.core.ColorDetails.ColorDetails;
import static com.echothree.model.jooq.server.tables.core.Colors.Colors;
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
public class ColorControl
        extends BaseModelControl {

    @Inject
    protected AppearanceControl appearanceControl;

    /** Creates a new instance of ColorControl */
    protected ColorControl() {
        super();
    }

    // --------------------------------------------------------------------------------
    //   Colors
    // --------------------------------------------------------------------------------

    @Inject
    protected ColorDetailFactory colorDetailFactory;

    @Inject
    protected ColorFactory colorFactory;

    @Inject
    ColorTransferCache colorTransferCache;

    public Color createColor(String colorName, Integer red, Integer green, Integer blue, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultColor = getDefaultColor();
        var defaultFound = defaultColor != null;

        if(defaultFound && isDefault) {
            var defaultColorDetailValue = getDefaultColorDetailValueForUpdate();

            defaultColorDetailValue.setIsDefault(false);
            updateColorFromValue(defaultColorDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var color = colorFactory.create();
        var colorDetail = colorDetailFactory.create(color, colorName, red, green, blue, isDefault, sortOrder, session.getStartTime(),
                Session.MAX_TIME);

        // Convert to R/W
        color = colorFactory.getEntityFromPK(EntityPermission.READ_WRITE, color.getPrimaryKey());
        color.setActiveDetail(colorDetail);
        color.setLastDetail(colorDetail);
        color.store();

        sendEvent(color.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return color;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.Color */
    public Color getColorByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new ColorPK(entityInstance.getEntityUniqueId());

        return colorFactory.getEntityFromPK(entityPermission, pk);
    }

    public Color getColorByEntityInstance(EntityInstance entityInstance) {
        return getColorByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public Color getColorByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getColorByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countColors() {
        return session.getDslContext()
                .selectCount()
                .from(Colors)
                .join(ColorDetails).onKey(COLORS_ACTIVE_DETAIL_FK)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private Color getColorByName(String colorName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(Colors.fields())
                .from(Colors)
                .join(ColorDetails).onKey(COLORS_ACTIVE_DETAIL_FK)
                .where(ColorDetails.COLOR_NAME.eq(colorName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return colorFactory.getEntityFromQuery(entityPermission, query);
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

    private Color getDefaultColor(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(Colors.fields())
                .from(Colors)
                .join(ColorDetails).onKey(COLORS_ACTIVE_DETAIL_FK)
                .where(ColorDetails.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return colorFactory.getEntityFromQuery(entityPermission, query);
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

    private List<Color> getColors(EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(Colors.fields())
                    .from(Colors)
                    .join(ColorDetails).onKey(COLORS_ACTIVE_DETAIL_FK)
                    .orderBy(ColorDetails.SORT_ORDER, ColorDetails.COLOR_NAME),
                    ColorFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(Colors.fields())
                    .from(Colors)
                    .where(Colors.ACTIVE_DETAIL.isNotNull())
                    .forUpdate();
        };

        return colorFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<Color> getColors() {
        return getColors(EntityPermission.READ_ONLY);
    }

    public List<Color> getColorsForUpdate() {
        return getColors(EntityPermission.READ_WRITE);
    }

    public ColorTransfer getColorTransfer(UserVisit userVisit, Color color) {
        return colorTransferCache.getColorTransfer(userVisit, color);
    }

    public List<ColorTransfer> getColorTransfers(UserVisit userVisit, Collection<Color> entities) {
        List<ColorTransfer> transfers = new ArrayList<>(entities.size());

        entities.forEach((entity) ->
                transfers.add(colorTransferCache.getColorTransfer(userVisit, entity))
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
            var color = colorFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    colorDetailValue.getColorPK());
            var colorDetail = color.getActiveDetailForUpdate();

            colorDetail.setThruTime(session.getStartTime());
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

                    defaultColorDetailValue.setIsDefault(false);
                    updateColorFromValue(defaultColorDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }

            colorDetail = colorDetailFactory.create(colorPK, colorName, red, green, blue, isDefault, sortOrder, session.getStartTime(),
                    Session.MAX_TIME);

            color.setActiveDetail(colorDetail);
            color.setLastDetail(colorDetail);

            sendEvent(colorPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public void updateColorFromValue(ColorDetailValue colorDetailValue, BasePK updatedBy) {
        updateColorFromValue(colorDetailValue, true, updatedBy);
    }

    private void deleteColor(Color color, boolean checkDefault, BasePK deletedBy) {
        var colorDetail = color.getLastDetailForUpdate();

        appearanceControl.deleteAppearancesByColor(color, deletedBy);
        deleteColorDescriptionsByColor(color, deletedBy);

        colorDetail.setThruTime(session.getStartTime());
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

                    colorDetailValue.setIsDefault(true);
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

    @Inject
    protected ColorDescriptionFactory colorDescriptionFactory;

    @Inject
    ColorDescriptionTransferCache colorDescriptionTransferCache;

    public ColorDescription createColorDescription(Color color, Language language, String description, BasePK createdBy) {
        var colorDescription = colorDescriptionFactory.create(color, language, description,
                session.getStartTime(), Session.MAX_TIME);

        sendEvent(color.getPrimaryKey(), EventTypes.MODIFY, colorDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return colorDescription;
    }

    private ColorDescription getColorDescription(Color color, Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ColorDescriptions.fields())
                .from(ColorDescriptions)
                .where(ColorDescriptions.COLOR.eq(color.getPrimaryKey()),
                        ColorDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        ColorDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return colorDescriptionFactory.getEntityFromQuery(entityPermission, query);
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

    private List<ColorDescription> getColorDescriptionsByColor(Color color, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(ColorDescriptions.fields())
                    .from(ColorDescriptions)
                    .join(Languages).onKey(COLOR_DESCRIPTIONS_LANGUAGE_FK)
                    .where(ColorDescriptions.COLOR.eq(color.getPrimaryKey()),
                            ColorDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    ColorDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(ColorDescriptions.fields())
                    .from(ColorDescriptions)
                    .where(ColorDescriptions.COLOR.eq(color.getPrimaryKey()),
                            ColorDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return colorDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
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
            colorDescription = getColorDescription(color, partyControl.getDefaultLanguage());
        }

        if(colorDescription == null) {
            description = color.getLastDetail().getColorName();
        } else {
            description = colorDescription.getDescription();
        }

        return description;
    }

    public ColorDescriptionTransfer getColorDescriptionTransfer(UserVisit userVisit, ColorDescription colorDescription) {
        return colorDescriptionTransferCache.getColorDescriptionTransfer(userVisit, colorDescription);
    }

    public List<ColorDescriptionTransfer> getColorDescriptionTransfersByColor(UserVisit userVisit, Color color) {
        var colorDescriptions = getColorDescriptionsByColor(color);
        List<ColorDescriptionTransfer> colorDescriptionTransfers = new ArrayList<>(colorDescriptions.size());

        colorDescriptions.forEach((colorDescription) ->
                colorDescriptionTransfers.add(colorDescriptionTransferCache.getColorDescriptionTransfer(userVisit, colorDescription))
        );

        return colorDescriptionTransfers;
    }

    public void updateColorDescriptionFromValue(ColorDescriptionValue colorDescriptionValue, BasePK updatedBy) {
        if(colorDescriptionValue.hasBeenModified()) {
            var colorDescription = colorDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    colorDescriptionValue.getPrimaryKey());

            colorDescription.setThruTime(session.getStartTime());
            colorDescription.store();

            var color = colorDescription.getColor();
            var language = colorDescription.getLanguage();
            var description = colorDescriptionValue.getDescription();

            colorDescription = colorDescriptionFactory.create(color, language, description,
                    session.getStartTime(), Session.MAX_TIME);

            sendEvent(color.getPrimaryKey(), EventTypes.MODIFY, colorDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteColorDescription(ColorDescription colorDescription, BasePK deletedBy) {
        colorDescription.setThruTime(session.getStartTime());

        sendEvent(colorDescription.getColorPK(), EventTypes.MODIFY, colorDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }

    public void deleteColorDescriptionsByColor(Color color, BasePK deletedBy) {
        var colorDescriptions = getColorDescriptionsByColorForUpdate(color);

        colorDescriptions.forEach((colorDescription) ->
                deleteColorDescription(colorDescription, deletedBy)
        );
    }

}
