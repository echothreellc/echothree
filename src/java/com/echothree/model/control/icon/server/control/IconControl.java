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
import com.echothree.util.common.exception.PersistenceDatabaseException;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseModelControl;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@RequestScoped
public class IconControl
        extends BaseModelControl {
    
    /** Creates a new instance of IconControl */
    protected IconControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Icon Transfer Caches
    // --------------------------------------------------------------------------------

    @Inject
    IconTransferCache iconTransferCache;

    @Inject
    IconUsageTypeTransferCache iconUsageTypeTransferCache;

    @Inject
    IconUsageTypeDescriptionTransferCache iconUsageTypeDescriptionTransferCache;

    @Inject
    IconUsageTransferCache iconUsageTransferCache;

    // --------------------------------------------------------------------------------
    //   Icon
    // --------------------------------------------------------------------------------
    
    public Icon createIcon(String iconName, Document document, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var icon = IconFactory.getInstance().create();
        var iconDetail = IconDetailFactory.getInstance().create(icon, iconName, document, isDefault, sortOrder,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        icon = IconFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, icon.getPrimaryKey());
        icon.setActiveDetail(iconDetail);
        icon.setLastDetail(iconDetail);
        icon.store();
        
        sendEvent(icon.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return icon;
    }
    
    private List<Icon> getIcons(EntityPermission entityPermission) {
        String query = null;
        
        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ " +
                    "FROM icons, icondetails " +
                    "WHERE icn_activedetailid = icndt_icondetailid " +
                    "ORDER BY icndt_sortorder, icndt_iconname";
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ " +
                    "FROM icons, icondetails " +
                    "WHERE icn_activedetailid = icndt_icondetailid " +
                    "FOR UPDATE";
        }

        var ps = IconFactory.getInstance().prepareStatement(query);
        
        return IconFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
    }
    
    public List<Icon> getIcons() {
        return getIcons(EntityPermission.READ_ONLY);
    }
    
    public List<Icon> getIconsForUpdate() {
        return getIcons(EntityPermission.READ_WRITE);
    }
    
    private Icon getIconByName(String iconName, EntityPermission entityPermission) {
        Icon icon;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM icons, icondetails " +
                        "WHERE icn_activedetailid = icndt_icondetailid AND icndt_iconname = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM icons, icondetails " +
                        "WHERE icn_activedetailid = icndt_icondetailid AND icndt_iconname = ? " +
                        "FOR UPDATE";
            }

            var ps = IconFactory.getInstance().prepareStatement(query);
            
            ps.setString(1, iconName);
            
            icon = IconFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return icon;
    }
    
    public Icon getIconByName(String iconName) {
        return getIconByName(iconName, EntityPermission.READ_ONLY);
    }
    
    public Icon getIconByNameForUpdate(String iconName) {
        return getIconByName(iconName, EntityPermission.READ_WRITE);
    }
    
    public IconChoicesBean getIconChoicesByIconUsageType(IconUsageType iconUsageType, String defaultIconChoice, Language language,
            boolean allowNullChoice) {
        var documentControl = Session.getModelController(DocumentControl.class);
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
        iconDetail.setThruTime(session.START_TIME_LONG);
        iconDetail.store();
        icon.setActiveDetail(null);
        
        sendEvent(iconDetail.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Icon Usage Types
    // --------------------------------------------------------------------------------
    
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

        var iconUsageType = IconUsageTypeFactory.getInstance().create();
        var iconUsageTypeDetail = IconUsageTypeDetailFactory.getInstance().create(iconUsageType,
                iconUsageTypeName, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        iconUsageType = IconUsageTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                iconUsageType.getPrimaryKey());
        iconUsageType.setActiveDetail(iconUsageTypeDetail);
        iconUsageType.setLastDetail(iconUsageTypeDetail);
        iconUsageType.store();
        
        sendEvent(iconUsageType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return iconUsageType;
    }
    
    private IconUsageType getIconUsageTypeByName(String iconUsageTypeName, EntityPermission entityPermission) {
        IconUsageType iconUsageType;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM iconusagetypes, iconusagetypedetails " +
                        "WHERE icnutyp_activedetailid = icnutypdt_iconusagetypedetailid AND icnutypdt_iconusagetypename = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM iconusagetypes, iconusagetypedetails " +
                        "WHERE icnutyp_activedetailid = icnutypdt_iconusagetypedetailid AND icnutypdt_iconusagetypename = ? " +
                        "FOR UPDATE";
            }

            var ps = IconUsageTypeFactory.getInstance().prepareStatement(query);
            
            ps.setString(1, iconUsageTypeName);
            
            iconUsageType = IconUsageTypeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return iconUsageType;
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
        String query = null;
        
        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ " +
                    "FROM iconusagetypes, iconusagetypedetails " +
                    "WHERE icnutyp_activedetailid = icnutypdt_iconusagetypedetailid AND icnutypdt_isdefault = 1";
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ " +
                    "FROM iconusagetypes, iconusagetypedetails " +
                    "WHERE icnutyp_activedetailid = icnutypdt_iconusagetypedetailid AND icnutypdt_isdefault = 1 " +
                    "FOR UPDATE";
        }

        var ps = IconUsageTypeFactory.getInstance().prepareStatement(query);
        
        return IconUsageTypeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
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
        String query = null;
        
        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ " +
                    "FROM iconusagetypes, iconusagetypedetails " +
                    "WHERE icnutyp_activedetailid = icnutypdt_iconusagetypedetailid " +
                    "ORDER BY icnutypdt_sortorder, icnutypdt_iconusagetypename";
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ " +
                    "FROM iconusagetypes, iconusagetypedetails " +
                    "WHERE icnutyp_activedetailid = icnutypdt_iconusagetypedetailid " +
                    "FOR UPDATE";
        }

        var ps = IconUsageTypeFactory.getInstance().prepareStatement(query);
        
        return IconUsageTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
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
        var iconUsageType = IconUsageTypeFactory.getInstance().getEntityFromPK(session,
                EntityPermission.READ_WRITE, iconUsageTypeDetailValue.getIconUsageTypePK());
        var iconUsageTypeDetail = iconUsageType.getActiveDetailForUpdate();
        
        iconUsageTypeDetail.setThruTime(session.START_TIME_LONG);
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
        
        iconUsageTypeDetail = IconUsageTypeDetailFactory.getInstance().create(iconUsageTypePK, iconUsageTypeName,
                isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
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
        iconUsageTypeDetail.setThruTime(session.START_TIME_LONG);
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
    
    public IconUsageTypeDescription createIconUsageTypeDescription(IconUsageType iconUsageType, Language language, String description,
            BasePK createdBy) {
        var iconUsageTypeDescription = IconUsageTypeDescriptionFactory.getInstance().create(iconUsageType,
                language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(iconUsageType.getPrimaryKey(), EventTypes.MODIFY, iconUsageTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return iconUsageTypeDescription;
    }
    
    private IconUsageTypeDescription getIconUsageTypeDescription(IconUsageType iconUsageType, Language language, EntityPermission entityPermission) {
        IconUsageTypeDescription iconUsageTypeDescription;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM iconusagetypedescriptions " +
                        "WHERE icnutypd_icnutyp_iconusagetypeid = ? AND icnutypd_lang_languageid = ? AND icnutypd_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM iconusagetypedescriptions " +
                        "WHERE icnutypd_icnutyp_iconusagetypeid = ? AND icnutypd_lang_languageid = ? AND icnutypd_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = IconUsageTypeDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, iconUsageType.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            iconUsageTypeDescription = IconUsageTypeDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return iconUsageTypeDescription;
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
        List<IconUsageTypeDescription> iconUsageTypeDescriptions;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM iconusagetypedescriptions, languages " +
                        "WHERE icnutypd_icnutyp_iconusagetypeid = ? AND icnutypd_thrutime = ? AND icnutypd_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM iconusagetypedescriptions " +
                        "WHERE icnutypd_icnutyp_iconusagetypeid = ? AND icnutypd_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = IconUsageTypeDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, iconUsageType.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            iconUsageTypeDescriptions = IconUsageTypeDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return iconUsageTypeDescriptions;
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
            var iconUsageTypeDescription = IconUsageTypeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     iconUsageTypeDescriptionValue.getPrimaryKey());
            
            iconUsageTypeDescription.setThruTime(session.START_TIME_LONG);
            iconUsageTypeDescription.store();

            var iconUsageType = iconUsageTypeDescription.getIconUsageType();
            var language = iconUsageTypeDescription.getLanguage();
            var description = iconUsageTypeDescriptionValue.getDescription();
            
            iconUsageTypeDescription = IconUsageTypeDescriptionFactory.getInstance().create(iconUsageType, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(iconUsageType.getPrimaryKey(), EventTypes.MODIFY, iconUsageTypeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteIconUsageTypeDescription(IconUsageTypeDescription iconUsageTypeDescription, BasePK deletedBy) {
        iconUsageTypeDescription.setThruTime(session.START_TIME_LONG);
        
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

        var iconUsage = IconUsageFactory.getInstance().create(iconUsageType, icon,
                isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(iconUsageType.getPrimaryKey(), EventTypes.MODIFY, iconUsage.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return iconUsage;
    }
    
    private IconUsage getIconUsage(IconUsageType iconUsageType, Icon icon, EntityPermission entityPermission) {
        IconUsage iconUsage;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM iconusages " +
                        "WHERE icnu_icnutyp_iconusagetypeid = ? AND icnu_icn_iconid = ? AND icnu_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM iconusages " +
                        "WHERE icnu_icnutyp_iconusagetypeid = ? AND icnu_icn_iconid = ? AND icnu_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = IconUsageFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, iconUsageType.getPrimaryKey().getEntityId());
            ps.setLong(2, icon.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            iconUsage = IconUsageFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return iconUsage;
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
        IconUsage iconUsage;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM iconusages " +
                        "WHERE icnu_icnutyp_iconusagetypeid = ? AND icnu_isdefault = 1 AND icnu_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM iconusages " +
                        "WHERE icnu_icnutyp_iconusagetypeid = ? AND icnu_isdefault = 1 AND icnu_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = IconUsageFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, iconUsageType.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            iconUsage = IconUsageFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return iconUsage;
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
        List<IconUsage> iconUsages;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM iconusages, icons, icondetails " +
                        "WHERE icnu_icnutyp_iconusagetypeid = ? AND icnu_thrutime = ? " +
                        "AND icnu_icn_iconid = icn_iconid AND icn_lastdetailid = icndt_icondetailid " +
                        "ORDER BY icndt_sortorder, icndt_iconname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM iconusages " +
                        "WHERE icnu_icnutyp_iconusagetypeid = ? AND icnu_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = IconUsageFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, iconUsageType.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            iconUsages = IconUsageFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return iconUsages;
    }
    
    public List<IconUsage> getIconUsagesByIconUsageType(IconUsageType iconUsageType) {
        return getIconUsagesByIconUsageType(iconUsageType, EntityPermission.READ_ONLY);
    }
    
    public List<IconUsage> getIconUsagesByIconUsageTypeForUpdate(IconUsageType iconUsageType) {
        return getIconUsagesByIconUsageType(iconUsageType, EntityPermission.READ_WRITE);
    }
    
    private List<IconUsage> getIconUsagesByIcon(Icon icon, EntityPermission entityPermission) {
        List<IconUsage> iconUsages;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM iconusages, iconusagetypes, iconusagetypedetails, returnkinds, returnkinddetails " +
                        "WHERE icnu_icn_iconid = ? AND icnu_thrutime = ? " +
                        "AND icnu_icnutyp_iconusagetypeid = icnutyp_iconusagetypeid AND icnutyp_lastdetailid = icnutypdt_iconusagetypedetailid " +
                        "AND icnutypdt_rtnk_returnkindid = rtnk_returnkindid AND rtnk_lastdetailid = rtnkdt_returnkinddetailid " +
                        "ORDER BY icnutypdt_sortorder, icnutypdt_iconusagetypename, rtnkdt_sortorder, rtnkdt_returnkindname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM iconusages " +
                        "WHERE icnu_icn_iconid = ? AND icnu_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = IconUsageFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, icon.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            iconUsages = IconUsageFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return iconUsages;
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
            var iconUsage = IconUsageFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     iconUsageValue.getPrimaryKey());
            
            iconUsage.setThruTime(session.START_TIME_LONG);
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
            
            iconUsage = IconUsageFactory.getInstance().create(iconUsageTypePK, iconPK,
                    isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(iconUsageTypePK, EventTypes.MODIFY, iconUsage.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void updateIconUsageFromValue(IconUsageValue iconUsageValue, BasePK updatedBy) {
        updateIconUsageFromValue(iconUsageValue, true, updatedBy);
    }
    
    public void deleteIconUsage(IconUsage iconUsage, BasePK deletedBy) {
        iconUsage.setThruTime(session.START_TIME_LONG);
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
