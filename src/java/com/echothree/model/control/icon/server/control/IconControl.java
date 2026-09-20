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

package com.echothree.model.control.icon.server.control;

import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.document.server.control.DocumentControl;
import com.echothree.model.control.icon.common.choice.IconChoicesBean;
import com.echothree.model.control.icon.common.choice.IconUsageTypeChoicesBean;
import com.echothree.model.control.icon.common.transfer.IconTransfer;
import com.echothree.model.control.icon.common.transfer.IconUsageTransfer;
import com.echothree.model.control.icon.common.transfer.IconUsageTypeDescriptionTransfer;
import com.echothree.model.control.icon.common.transfer.IconUsageTypeTransfer;
import com.echothree.model.control.icon.server.transfer.IconTransferCache;
import com.echothree.model.control.icon.server.transfer.IconUsageTransferCache;
import com.echothree.model.control.icon.server.transfer.IconUsageTypeDescriptionTransferCache;
import com.echothree.model.control.icon.server.transfer.IconUsageTypeTransferCache;
import com.echothree.model.data.document.server.entity.Document;
import com.echothree.model.data.icon.server.entity.Icon;
import com.echothree.model.data.icon.server.entity.IconUsage;
import com.echothree.model.data.icon.server.entity.IconUsageType;
import com.echothree.model.data.icon.server.entity.IconUsageTypeDescription;
import com.echothree.model.data.icon.server.factory.IconDetailFactory;
import com.echothree.model.data.icon.server.factory.IconFactory;
import com.echothree.model.data.icon.server.factory.IconUsageFactory;
import com.echothree.model.data.icon.server.factory.IconUsageTypeDescriptionFactory;
import com.echothree.model.data.icon.server.factory.IconUsageTypeDetailFactory;
import com.echothree.model.data.icon.server.factory.IconUsageTypeFactory;
import com.echothree.model.data.icon.server.value.IconUsageTypeDescriptionValue;
import com.echothree.model.data.icon.server.value.IconUsageTypeDetailValue;
import com.echothree.model.data.icon.server.value.IconUsageValue;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.user.server.entity.UserVisit;
import static com.echothree.model.jooq.server.keys.icon.IconForeignKeys.ICONS_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.icon.IconForeignKeys.ICONS_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.icon.IconForeignKeys.ICON_USAGES_ICON_FK;
import static com.echothree.model.jooq.server.keys.icon.IconForeignKeys.ICON_USAGES_ICON_USAGE_TYPE_FK;
import static com.echothree.model.jooq.server.keys.icon.IconForeignKeys.ICON_USAGE_TYPES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.icon.IconForeignKeys.ICON_USAGE_TYPES_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.icon.IconForeignKeys.ICON_USAGE_TYPE_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.tables.icon.IconDetails.IconDetails;
import static com.echothree.model.jooq.server.tables.icon.IconUsageTypeDescriptions.IconUsageTypeDescriptions;
import static com.echothree.model.jooq.server.tables.icon.IconUsageTypeDetails.IconUsageTypeDetails;
import static com.echothree.model.jooq.server.tables.icon.IconUsageTypes.IconUsageTypes;
import static com.echothree.model.jooq.server.tables.icon.IconUsages.IconUsages;
import static com.echothree.model.jooq.server.tables.icon.Icons.Icons;
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
public class IconControl
        extends BaseModelControl {
    
    @Inject
    protected DocumentControl documentControl;

    /** Creates a new instance of IconControl */
    protected IconControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Icon
    // --------------------------------------------------------------------------------

    @Inject
    protected IconDetailFactory iconDetailFactory;

    @Inject
    protected IconFactory iconFactory;

    @Inject
    IconTransferCache iconTransferCache;

    public Icon createIcon(String iconName, Document document, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var icon = iconFactory.create();
        var iconDetail = iconDetailFactory.create(icon, iconName, document, isDefault, sortOrder,
                session.getStartTime(), Session.MAX_TIME);
        
        // Convert to R/W
        icon = iconFactory.getEntityFromPK(EntityPermission.READ_WRITE, icon.getPrimaryKey());
        icon.setActiveDetail(iconDetail);
        icon.setLastDetail(iconDetail);
        icon.store();
        
        sendEvent(icon.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return icon;
    }
    
    private List<Icon> getIcons(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(Icons.fields())
                .from(Icons)
                .join(IconDetails).onKey(ICONS_ACTIVE_DETAIL_FK);

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(IconDetails.SORT_ORDER, IconDetails.ICON_NAME),
                    IconFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return iconFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<Icon> getIcons() {
        return getIcons(EntityPermission.READ_ONLY);
    }
    
    public List<Icon> getIconsForUpdate() {
        return getIcons(EntityPermission.READ_WRITE);
    }
    
    private Icon getIconByName(String iconName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(Icons.fields())
                .from(Icons)
                .join(IconDetails).onKey(ICONS_ACTIVE_DETAIL_FK)
                .where(IconDetails.ICON_NAME.eq(iconName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return iconFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public Icon getIconByName(String iconName) {
        return getIconByName(iconName, EntityPermission.READ_ONLY);
    }
    
    public Icon getIconByNameForUpdate(String iconName) {
        return getIconByName(iconName, EntityPermission.READ_WRITE);
    }
    
    public IconChoicesBean getIconChoicesByIconUsageType(IconUsageType iconUsageType, String defaultIconChoice, Language language,
            boolean allowNullChoice) {
        var iconUsages = getIconUsagesByIconUsageType(iconUsageType);
        var icons = getIcons();
        var size = icons.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
        }
        
        for(var iconUsage : iconUsages) {
            var iconDetail = iconUsage.getIcon().getLastDetail();
            var document = iconDetail.getDocument();
            var label = documentControl.getBestDocumentDescription(document, language);
            var value = iconDetail.getIconName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultIconChoice != null && defaultIconChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && iconUsage.getIsDefault()))
                defaultValue = value;
        }
        
        return new IconChoicesBean(labels, values, defaultValue);
    }
    
    public IconTransfer getIconTransfer(UserVisit userVisit, Icon icon) {
        return iconTransferCache.getIconTransfer(userVisit, icon);
    }
    
    public List<IconTransfer> getIconTransfers(UserVisit userVisit) {
        var icons = getIcons();
        List<IconTransfer> iconTransfers = new ArrayList<>(icons.size());
        
        icons.forEach((icon) -> {
            iconTransfers.add(getIconTransfer(userVisit, icon));
        });
        
        return iconTransfers;
    }
    
    public void deleteIcon(Icon icon, BasePK deletedBy) {
        deleteIconUsagesByIcon(icon, deletedBy);

        var iconDetail = icon.getLastDetailForUpdate();
        iconDetail.setThruTime(session.getStartTime());
        iconDetail.store();
        icon.setActiveDetail(null);
        
        sendEvent(iconDetail.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Icon Usage Types
    // --------------------------------------------------------------------------------

    @Inject
    protected IconUsageTypeDetailFactory iconUsageTypeDetailFactory;

    @Inject
    protected IconUsageTypeFactory iconUsageTypeFactory;

    @Inject
    IconUsageTypeTransferCache iconUsageTypeTransferCache;

    public IconUsageType createIconUsageType(String iconUsageTypeName, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultIconUsageType = getDefaultIconUsageType();
        var defaultFound = defaultIconUsageType != null;
        
        if(defaultFound && isDefault) {
            var defaultIconUsageTypeDetailValue = getDefaultIconUsageTypeDetailValueForUpdate();
            
            defaultIconUsageTypeDetailValue.setIsDefault(false);
            updateIconUsageTypeFromValue(defaultIconUsageTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var iconUsageType = iconUsageTypeFactory.create();
        var iconUsageTypeDetail = iconUsageTypeDetailFactory.create(iconUsageType,
                iconUsageTypeName, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);
        
        // Convert to R/W
        iconUsageType = iconUsageTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                iconUsageType.getPrimaryKey());
        iconUsageType.setActiveDetail(iconUsageTypeDetail);
        iconUsageType.setLastDetail(iconUsageTypeDetail);
        iconUsageType.store();
        
        sendEvent(iconUsageType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return iconUsageType;
    }
    
    private IconUsageType getIconUsageTypeByName(String iconUsageTypeName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(IconUsageTypes.fields())
                .from(IconUsageTypes)
                .join(IconUsageTypeDetails).onKey(ICON_USAGE_TYPES_ACTIVE_DETAIL_FK)
                .where(IconUsageTypeDetails.ICON_USAGE_TYPE_NAME.eq(iconUsageTypeName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return iconUsageTypeFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public IconUsageType getIconUsageTypeByName(String iconUsageTypeName) {
        return getIconUsageTypeByName(iconUsageTypeName, EntityPermission.READ_ONLY);
    }
    
    public IconUsageType getIconUsageTypeByNameForUpdate(String iconUsageTypeName) {
        return getIconUsageTypeByName(iconUsageTypeName, EntityPermission.READ_WRITE);
    }
    
    public IconUsageTypeDetailValue getIconUsageTypeDetailValueForUpdate(IconUsageType iconUsageType) {
        return iconUsageType == null? null: iconUsageType.getLastDetailForUpdate().getIconUsageTypeDetailValue().clone();
    }
    
    public IconUsageTypeDetailValue getIconUsageTypeDetailValueByNameForUpdate(String iconUsageTypeName) {
        return getIconUsageTypeDetailValueForUpdate(getIconUsageTypeByNameForUpdate(iconUsageTypeName));
    }
    
    private IconUsageType getDefaultIconUsageType(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(IconUsageTypes.fields())
                .from(IconUsageTypes)
                .join(IconUsageTypeDetails).onKey(ICON_USAGE_TYPES_ACTIVE_DETAIL_FK)
                .where(IconUsageTypeDetails.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return iconUsageTypeFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public IconUsageType getDefaultIconUsageType() {
        return getDefaultIconUsageType(EntityPermission.READ_ONLY);
    }
    
    public IconUsageType getDefaultIconUsageTypeForUpdate() {
        return getDefaultIconUsageType(EntityPermission.READ_WRITE);
    }
    
    public IconUsageTypeDetailValue getDefaultIconUsageTypeDetailValueForUpdate() {
        return getDefaultIconUsageType(EntityPermission.READ_WRITE).getLastDetailForUpdate().getIconUsageTypeDetailValue();
    }
    
    private List<IconUsageType> getIconUsageTypes(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(IconUsageTypes.fields())
                .from(IconUsageTypes)
                .join(IconUsageTypeDetails).onKey(ICON_USAGE_TYPES_ACTIVE_DETAIL_FK);

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(IconUsageTypeDetails.SORT_ORDER, IconUsageTypeDetails.ICON_USAGE_TYPE_NAME),
                    IconUsageTypeFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return iconUsageTypeFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<IconUsageType> getIconUsageTypes() {
        return getIconUsageTypes(EntityPermission.READ_ONLY);
    }
    
    public List<IconUsageType> getIconUsageTypesForUpdate() {
        return getIconUsageTypes(EntityPermission.READ_WRITE);
    }
    
    public IconUsageTypeChoicesBean getIconUsageTypeChoices(String defaultIconUsageTypeChoice, Language language, boolean allowNullChoice) {
        var iconUsageTypes = getIconUsageTypes();
        var size = iconUsageTypes.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultIconUsageTypeChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var iconUsageType : iconUsageTypes) {
            var iconUsageTypeDetail = iconUsageType.getLastDetail();
            
            var label = getBestIconUsageTypeDescription(iconUsageType, language);
            var value = iconUsageTypeDetail.getIconUsageTypeName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultIconUsageTypeChoice != null && defaultIconUsageTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && iconUsageTypeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new IconUsageTypeChoicesBean(labels, values, defaultValue);
    }
    
    public IconUsageTypeTransfer getIconUsageTypeTransfer(UserVisit userVisit, IconUsageType iconUsageType) {
        return iconUsageTypeTransferCache.getIconUsageTypeTransfer(userVisit, iconUsageType);
    }
    
    public List<IconUsageTypeTransfer> getIconUsageTypeTransfers(UserVisit userVisit) {
        var iconUsageTypes = getIconUsageTypes();
        List<IconUsageTypeTransfer> iconUsageTypeTransfers = new ArrayList<>(iconUsageTypes.size());
        
        iconUsageTypes.forEach((iconUsageType) ->
                iconUsageTypeTransfers.add(iconUsageTypeTransferCache.getIconUsageTypeTransfer(userVisit, iconUsageType))
        );
        
        return iconUsageTypeTransfers;
    }
    
    private void updateIconUsageTypeFromValue(IconUsageTypeDetailValue iconUsageTypeDetailValue, boolean checkDefault, BasePK updatedBy) {
        var iconUsageType = iconUsageTypeFactory.getEntityFromPK(
                EntityPermission.READ_WRITE, iconUsageTypeDetailValue.getIconUsageTypePK());
        var iconUsageTypeDetail = iconUsageType.getActiveDetailForUpdate();
        
        iconUsageTypeDetail.setThruTime(session.getStartTime());
        iconUsageTypeDetail.store();

        var iconUsageTypePK = iconUsageTypeDetail.getIconUsageTypePK();
        var iconUsageTypeName = iconUsageTypeDetailValue.getIconUsageTypeName();
        var isDefault = iconUsageTypeDetailValue.getIsDefault();
        var sortOrder = iconUsageTypeDetailValue.getSortOrder();
        
        if(checkDefault) {
            var defaultIconUsageType = getDefaultIconUsageType();
            var defaultFound = defaultIconUsageType != null && !defaultIconUsageType.equals(iconUsageType);
            
            if(isDefault && defaultFound) {
                // If I'm the default, and a default already existed...
                var defaultIconUsageTypeDetailValue = getDefaultIconUsageTypeDetailValueForUpdate();
                
                defaultIconUsageTypeDetailValue.setIsDefault(false);
                updateIconUsageTypeFromValue(defaultIconUsageTypeDetailValue, false, updatedBy);
            } else if(!isDefault && !defaultFound) {
                // If I'm not the default, and no other default exists...
                isDefault = true;
            }
        }
        
        iconUsageTypeDetail = iconUsageTypeDetailFactory.create(iconUsageTypePK, iconUsageTypeName,
                isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);
        
        iconUsageType.setActiveDetail(iconUsageTypeDetail);
        iconUsageType.setLastDetail(iconUsageTypeDetail);
        iconUsageType.store();
        
        sendEvent(iconUsageTypePK, EventTypes.MODIFY, null, null, updatedBy);
    }
    
    public void updateIconUsageTypeFromValue(IconUsageTypeDetailValue iconUsageTypeDetailValue, BasePK updatedBy) {
        updateIconUsageTypeFromValue(iconUsageTypeDetailValue, true, updatedBy);
    }
    
    public void deleteIconUsageType(IconUsageType iconUsageType, BasePK deletedBy) {
        deleteIconUsageTypeDescriptionsByIconUsageType(iconUsageType, deletedBy);

        var iconUsageTypeDetail = iconUsageType.getLastDetailForUpdate();
        iconUsageTypeDetail.setThruTime(session.getStartTime());
        iconUsageType.setActiveDetail(null);
        iconUsageType.store();
        
        // Check for default, and pick one if necessary
        var defaultIconUsageType = getDefaultIconUsageType();
        if(defaultIconUsageType == null) {
            var iconUsageTypes = getIconUsageTypesForUpdate();
            
            if(!iconUsageTypes.isEmpty()) {
                var iter = iconUsageTypes.iterator();
                if(iter.hasNext()) {
                    defaultIconUsageType = iter.next();
                }
                var iconUsageTypeDetailValue = Objects.requireNonNull(defaultIconUsageType).getLastDetailForUpdate().getIconUsageTypeDetailValue().clone();
                
                iconUsageTypeDetailValue.setIsDefault(true);
                updateIconUsageTypeFromValue(iconUsageTypeDetailValue, false, deletedBy);
            }
        }
        
        sendEvent(iconUsageType.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Icon Usage Type Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    protected IconUsageTypeDescriptionFactory iconUsageTypeDescriptionFactory;

    @Inject
    IconUsageTypeDescriptionTransferCache iconUsageTypeDescriptionTransferCache;

    public IconUsageTypeDescription createIconUsageTypeDescription(IconUsageType iconUsageType, Language language, String description,
            BasePK createdBy) {
        var iconUsageTypeDescription = iconUsageTypeDescriptionFactory.create(iconUsageType,
                language, description, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(iconUsageType.getPrimaryKey(), EventTypes.MODIFY, iconUsageTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return iconUsageTypeDescription;
    }
    
    private IconUsageTypeDescription getIconUsageTypeDescription(IconUsageType iconUsageType, Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(IconUsageTypeDescriptions.fields())
                .from(IconUsageTypeDescriptions)
                .where(IconUsageTypeDescriptions.ICON_USAGE_TYPE.eq(iconUsageType.getPrimaryKey()),
                        IconUsageTypeDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        IconUsageTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return iconUsageTypeDescriptionFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public IconUsageTypeDescription getIconUsageTypeDescription(IconUsageType iconUsageType, Language language) {
        return getIconUsageTypeDescription(iconUsageType, language, EntityPermission.READ_ONLY);
    }
    
    public IconUsageTypeDescription getIconUsageTypeDescriptionForUpdate(IconUsageType iconUsageType, Language language) {
        return getIconUsageTypeDescription(iconUsageType, language, EntityPermission.READ_WRITE);
    }
    
    public IconUsageTypeDescriptionValue getIconUsageTypeDescriptionValue(IconUsageTypeDescription iconUsageTypeDescription) {
        return iconUsageTypeDescription == null? null: iconUsageTypeDescription.getIconUsageTypeDescriptionValue().clone();
    }
    
    public IconUsageTypeDescriptionValue getIconUsageTypeDescriptionValueForUpdate(IconUsageType iconUsageType, Language language) {
        return getIconUsageTypeDescriptionValue(getIconUsageTypeDescriptionForUpdate(iconUsageType, language));
    }
    
    private List<IconUsageTypeDescription> getIconUsageTypeDescriptionsByIconUsageType(IconUsageType iconUsageType, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(IconUsageTypeDescriptions.fields())
                    .from(IconUsageTypeDescriptions)
                    .join(Languages).onKey(ICON_USAGE_TYPE_DESCRIPTIONS_LANGUAGE_FK)
                    .where(IconUsageTypeDescriptions.ICON_USAGE_TYPE.eq(iconUsageType.getPrimaryKey()),
                            IconUsageTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    IconUsageTypeDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(IconUsageTypeDescriptions.fields())
                    .from(IconUsageTypeDescriptions)
                    .where(IconUsageTypeDescriptions.ICON_USAGE_TYPE.eq(iconUsageType.getPrimaryKey()),
                            IconUsageTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return iconUsageTypeDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<IconUsageTypeDescription> getIconUsageTypeDescriptionsByIconUsageType(IconUsageType iconUsageType) {
        return getIconUsageTypeDescriptionsByIconUsageType(iconUsageType, EntityPermission.READ_ONLY);
    }
    
    public List<IconUsageTypeDescription> getIconUsageTypeDescriptionsByIconUsageTypeForUpdate(IconUsageType iconUsageType) {
        return getIconUsageTypeDescriptionsByIconUsageType(iconUsageType, EntityPermission.READ_WRITE);
    }
    
    public String getBestIconUsageTypeDescription(IconUsageType iconUsageType, Language language) {
        String description;
        var iconUsageTypeDescription = getIconUsageTypeDescription(iconUsageType, language);
        
        if(iconUsageTypeDescription == null && !language.getIsDefault()) {
            iconUsageTypeDescription = getIconUsageTypeDescription(iconUsageType, partyControl.getDefaultLanguage());
        }
        
        if(iconUsageTypeDescription == null) {
            description = iconUsageType.getLastDetail().getIconUsageTypeName();
        } else {
            description = iconUsageTypeDescription.getDescription();
        }
        
        return description;
    }
    
    public IconUsageTypeDescriptionTransfer getIconUsageTypeDescriptionTransfer(UserVisit userVisit, IconUsageTypeDescription iconUsageTypeDescription) {
        return iconUsageTypeDescriptionTransferCache.getIconUsageTypeDescriptionTransfer(userVisit, iconUsageTypeDescription);
    }
    
    public List<IconUsageTypeDescriptionTransfer> getIconUsageTypeDescriptionTransfersByIconUsageType(UserVisit userVisit, IconUsageType iconUsageType) {
        var iconUsageTypeDescriptions = getIconUsageTypeDescriptionsByIconUsageType(iconUsageType);
        List<IconUsageTypeDescriptionTransfer> iconUsageTypeDescriptionTransfers = new ArrayList<>(iconUsageTypeDescriptions.size());
        
        iconUsageTypeDescriptions.forEach((iconUsageTypeDescription) -> {
            iconUsageTypeDescriptionTransfers.add(iconUsageTypeDescriptionTransferCache.getIconUsageTypeDescriptionTransfer(userVisit, iconUsageTypeDescription));
        });
        
        return iconUsageTypeDescriptionTransfers;
    }
    
    public void updateIconUsageTypeDescriptionFromValue(IconUsageTypeDescriptionValue iconUsageTypeDescriptionValue, BasePK updatedBy) {
        if(iconUsageTypeDescriptionValue.hasBeenModified()) {
            var iconUsageTypeDescription = iconUsageTypeDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     iconUsageTypeDescriptionValue.getPrimaryKey());
            
            iconUsageTypeDescription.setThruTime(session.getStartTime());
            iconUsageTypeDescription.store();

            var iconUsageType = iconUsageTypeDescription.getIconUsageType();
            var language = iconUsageTypeDescription.getLanguage();
            var description = iconUsageTypeDescriptionValue.getDescription();
            
            iconUsageTypeDescription = iconUsageTypeDescriptionFactory.create(iconUsageType, language, description,
                    session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(iconUsageType.getPrimaryKey(), EventTypes.MODIFY, iconUsageTypeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteIconUsageTypeDescription(IconUsageTypeDescription iconUsageTypeDescription, BasePK deletedBy) {
        iconUsageTypeDescription.setThruTime(session.getStartTime());
        
        sendEvent(iconUsageTypeDescription.getIconUsageTypePK(), EventTypes.MODIFY, iconUsageTypeDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
        
    }
    
    public void deleteIconUsageTypeDescriptionsByIconUsageType(IconUsageType iconUsageType, BasePK deletedBy) {
        var iconUsageTypeDescriptions = getIconUsageTypeDescriptionsByIconUsageTypeForUpdate(iconUsageType);
        
        iconUsageTypeDescriptions.forEach((iconUsageTypeDescription) -> 
                deleteIconUsageTypeDescription(iconUsageTypeDescription, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Icon Usage
    // --------------------------------------------------------------------------------

    @Inject
    protected IconUsageFactory iconUsageFactory;

    @Inject
    IconUsageTransferCache iconUsageTransferCache;

    public IconUsage createIconUsage(IconUsageType iconUsageType, Icon icon, Boolean isDefault,
            Integer sortOrder, BasePK createdBy) {
        var defaultIconUsage = getDefaultIconUsage(iconUsageType);
        var defaultFound = defaultIconUsage != null;
        
        if(defaultFound && isDefault) {
            var defaultIconUsageValue = getDefaultIconUsageValueForUpdate(iconUsageType);
            
            defaultIconUsageValue.setIsDefault(false);
            updateIconUsageFromValue(defaultIconUsageValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var iconUsage = iconUsageFactory.create(iconUsageType, icon,
                isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(iconUsageType.getPrimaryKey(), EventTypes.MODIFY, iconUsage.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return iconUsage;
    }
    
    private IconUsage getIconUsage(IconUsageType iconUsageType, Icon icon, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(IconUsages.fields())
                .from(IconUsages)
                .where(IconUsages.ICON_USAGE_TYPE.eq(iconUsageType.getPrimaryKey()),
                        IconUsages.ICON.eq(icon.getPrimaryKey()),
                        IconUsages.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return iconUsageFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public IconUsage getIconUsage(IconUsageType iconUsageType, Icon icon) {
        return getIconUsage(iconUsageType, icon, EntityPermission.READ_ONLY);
    }
    
    public IconUsage getIconUsageForUpdate(IconUsageType iconUsageType, Icon icon) {
        return getIconUsage(iconUsageType, icon, EntityPermission.READ_WRITE);
    }
    
    public IconUsageValue getIconUsageValueForUpdate(IconUsageType iconUsageType, Icon icon) {
        var iconUsage = getIconUsageForUpdate(iconUsageType, icon);
        
        return iconUsage == null? null: iconUsage.getIconUsageValue().clone();
    }
    
    private IconUsage getDefaultIconUsage(IconUsageType iconUsageType, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(IconUsages.fields())
                .from(IconUsages)
                .where(IconUsages.ICON_USAGE_TYPE.eq(iconUsageType.getPrimaryKey()),
                        IconUsages.IS_DEFAULT.eq(true),
                        IconUsages.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return iconUsageFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public IconUsage getDefaultIconUsage(IconUsageType iconUsageType) {
        return getDefaultIconUsage(iconUsageType, EntityPermission.READ_ONLY);
    }
    
    public IconUsage getDefaultIconUsageForUpdate(IconUsageType iconUsageType) {
        return getDefaultIconUsage(iconUsageType, EntityPermission.READ_WRITE);
    }
    
    public IconUsageValue getDefaultIconUsageValueForUpdate(IconUsageType iconUsageType) {
        var iconUsage = getDefaultIconUsageForUpdate(iconUsageType);
        
        return iconUsage == null? null: iconUsage.getIconUsageValue().clone();
    }
    
    private List<IconUsage> getIconUsagesByIconUsageType(IconUsageType iconUsageType, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(IconUsages.fields())
                    .from(IconUsages)
                    .join(Icons).onKey(ICON_USAGES_ICON_FK)
                    .join(IconDetails).onKey(ICONS_LAST_DETAIL_FK)
                    .where(IconUsages.ICON_USAGE_TYPE.eq(iconUsageType.getPrimaryKey()),
                            IconUsages.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(IconDetails.SORT_ORDER, IconDetails.ICON_NAME),
                    IconUsageFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(IconUsages.fields())
                    .from(IconUsages)
                    .where(IconUsages.ICON_USAGE_TYPE.eq(iconUsageType.getPrimaryKey()),
                            IconUsages.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return iconUsageFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<IconUsage> getIconUsagesByIconUsageType(IconUsageType iconUsageType) {
        return getIconUsagesByIconUsageType(iconUsageType, EntityPermission.READ_ONLY);
    }
    
    public List<IconUsage> getIconUsagesByIconUsageTypeForUpdate(IconUsageType iconUsageType) {
        return getIconUsagesByIconUsageType(iconUsageType, EntityPermission.READ_WRITE);
    }
    
    private List<IconUsage> getIconUsagesByIcon(Icon icon, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(IconUsages.fields())
                    .from(IconUsages)
                    .join(IconUsageTypes).onKey(ICON_USAGES_ICON_USAGE_TYPE_FK)
                    .join(IconUsageTypeDetails).onKey(ICON_USAGE_TYPES_LAST_DETAIL_FK)
                    .where(IconUsages.ICON.eq(icon.getPrimaryKey()),
                            IconUsages.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(IconUsageTypeDetails.SORT_ORDER, IconUsageTypeDetails.ICON_USAGE_TYPE_NAME),
                    IconUsageFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(IconUsages.fields())
                    .from(IconUsages)
                    .where(IconUsages.ICON.eq(icon.getPrimaryKey()),
                            IconUsages.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return iconUsageFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<IconUsage> getIconUsagesByIcon(Icon icon) {
        return getIconUsagesByIcon(icon, EntityPermission.READ_ONLY);
    }
    
    public List<IconUsage> getIconUsagesByIconForUpdate(Icon icon) {
        return getIconUsagesByIcon(icon, EntityPermission.READ_WRITE);
    }
    
    public List<IconUsageTransfer> getIconUsageTransfers(UserVisit userVisit, Collection<IconUsage> iconUsages) {
        List<IconUsageTransfer> iconUsageTransfers = new ArrayList<>(iconUsages.size());
        
        iconUsages.forEach((iconUsage) ->
                iconUsageTransfers.add(iconUsageTransferCache.getIconUsageTransfer(userVisit, iconUsage))
        );
        
        return iconUsageTransfers;
    }
    
    public List<IconUsageTransfer> getIconUsageTransfersByIconUsageType(UserVisit userVisit, IconUsageType iconUsageType) {
        return getIconUsageTransfers(userVisit, getIconUsagesByIconUsageType(iconUsageType));
    }
    
    public List<IconUsageTransfer> getIconUsageTransfersByIcon(UserVisit userVisit, Icon icon) {
        return getIconUsageTransfers(userVisit, getIconUsagesByIcon(icon));
    }
    
    public IconUsageTransfer getIconUsageTransfer(UserVisit userVisit, IconUsage iconUsage) {
        return iconUsageTransferCache.getIconUsageTransfer(userVisit, iconUsage);
    }
    
    private void updateIconUsageFromValue(IconUsageValue iconUsageValue, boolean checkDefault, BasePK updatedBy) {
        if(iconUsageValue.hasBeenModified()) {
            var iconUsage = iconUsageFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     iconUsageValue.getPrimaryKey());
            
            iconUsage.setThruTime(session.getStartTime());
            iconUsage.store();

            var iconUsageType = iconUsage.getIconUsageType(); // Not Updated
            var iconUsageTypePK = iconUsageType.getPrimaryKey(); // Not Updated
            var iconPK = iconUsage.getIconPK(); // Not Updated
            var isDefault = iconUsageValue.getIsDefault();
            var sortOrder = iconUsageValue.getSortOrder();
            
            if(checkDefault) {
                var defaultIconUsage = getDefaultIconUsage(iconUsageType);
                var defaultFound = defaultIconUsage != null && !defaultIconUsage.equals(iconUsage);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultIconUsageValue = getDefaultIconUsageValueForUpdate(iconUsageType);
                    
                    defaultIconUsageValue.setIsDefault(false);
                    updateIconUsageFromValue(defaultIconUsageValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }
            
            iconUsage = iconUsageFactory.create(iconUsageTypePK, iconPK,
                    isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(iconUsageTypePK, EventTypes.MODIFY, iconUsage.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void updateIconUsageFromValue(IconUsageValue iconUsageValue, BasePK updatedBy) {
        updateIconUsageFromValue(iconUsageValue, true, updatedBy);
    }
    
    public void deleteIconUsage(IconUsage iconUsage, BasePK deletedBy) {
        iconUsage.setThruTime(session.getStartTime());
        iconUsage.store();
        
        // Check for default, and pick one if necessary
        var iconUsageType = iconUsage.getIconUsageType();
        var defaultIconUsage = getDefaultIconUsage(iconUsageType);
        if(defaultIconUsage == null) {
            var iconUsages = getIconUsagesByIconUsageTypeForUpdate(iconUsageType);
            
            if(!iconUsages.isEmpty()) {
                var iter = iconUsages.iterator();
                if(iter.hasNext()) {
                    defaultIconUsage = iter.next();
                }
                var iconUsageValue = defaultIconUsage.getIconUsageValue().clone();
                
                iconUsageValue.setIsDefault(true);
                updateIconUsageFromValue(iconUsageValue, false, deletedBy);
            }
        }
        
        sendEvent(iconUsageType.getPrimaryKey(), EventTypes.MODIFY, iconUsage.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteIconUsages(List<IconUsage> iconUsages, BasePK deletedBy) {
        iconUsages.forEach((iconUsage) -> 
                deleteIconUsage(iconUsage, deletedBy)
        );
    }
    
    public void deleteIconUsagesByIconUsageType(IconUsageType iconUsageType, BasePK deletedBy) {
        deleteIconUsages(getIconUsagesByIconUsageTypeForUpdate(iconUsageType), deletedBy);
    }
    
    public void deleteIconUsagesByIcon(Icon icon, BasePK deletedBy) {
        deleteIconUsages(getIconUsagesByIconForUpdate(icon), deletedBy);
    }
    
}
