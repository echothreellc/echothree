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

package com.echothree.model.control.letter.server.control;

import com.echothree.model.control.chain.server.control.ChainControl;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.letter.common.choice.LetterChoicesBean;
import com.echothree.model.control.letter.common.choice.LetterSourceChoicesBean;
import com.echothree.model.control.letter.common.transfer.LetterContactMechanismPurposeTransfer;
import com.echothree.model.control.letter.common.transfer.LetterDescriptionTransfer;
import com.echothree.model.control.letter.common.transfer.LetterSourceDescriptionTransfer;
import com.echothree.model.control.letter.common.transfer.LetterSourceTransfer;
import com.echothree.model.control.letter.common.transfer.LetterTransfer;
import com.echothree.model.control.letter.common.transfer.QueuedLetterTransfer;
import com.echothree.model.control.letter.server.transfer.LetterTransferCaches;
import com.echothree.model.data.chain.server.entity.ChainInstance;
import com.echothree.model.data.chain.server.entity.ChainType;
import com.echothree.model.data.contact.server.entity.ContactMechanismPurpose;
import com.echothree.model.data.contact.server.entity.PartyContactMechanism;
import com.echothree.model.data.contactlist.server.entity.ContactList;
import com.echothree.model.data.letter.server.entity.Letter;
import com.echothree.model.data.letter.server.entity.LetterContactMechanismPurpose;
import com.echothree.model.data.letter.server.entity.LetterDescription;
import com.echothree.model.data.letter.server.entity.LetterSource;
import com.echothree.model.data.letter.server.entity.LetterSourceDescription;
import com.echothree.model.data.letter.server.entity.QueuedLetter;
import com.echothree.model.data.letter.server.factory.LetterContactMechanismPurposeDetailFactory;
import com.echothree.model.data.letter.server.factory.LetterContactMechanismPurposeFactory;
import com.echothree.model.data.letter.server.factory.LetterDescriptionFactory;
import com.echothree.model.data.letter.server.factory.LetterDetailFactory;
import com.echothree.model.data.letter.server.factory.LetterFactory;
import com.echothree.model.data.letter.server.factory.LetterSourceDescriptionFactory;
import com.echothree.model.data.letter.server.factory.LetterSourceDetailFactory;
import com.echothree.model.data.letter.server.factory.LetterSourceFactory;
import com.echothree.model.data.letter.server.factory.QueuedLetterFactory;
import com.echothree.model.data.letter.server.value.LetterContactMechanismPurposeDetailValue;
import com.echothree.model.data.letter.server.value.LetterDescriptionValue;
import com.echothree.model.data.letter.server.value.LetterDetailValue;
import com.echothree.model.data.letter.server.value.LetterSourceDescriptionValue;
import com.echothree.model.data.letter.server.value.LetterSourceDetailValue;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.exception.PersistenceDatabaseException;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseModelControl;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class LetterControl
        extends BaseModelControl {
    
    /** Creates a new instance of LetterControl */
    public LetterControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Letter Transfer Caches
    // --------------------------------------------------------------------------------
    
    private LetterTransferCaches letterTransferCaches;
    
    public LetterTransferCaches getLetterTransferCaches(UserVisit userVisit) {
        if(letterTransferCaches == null) {
            letterTransferCaches = new LetterTransferCaches(userVisit, this);
        }
        
        return letterTransferCaches;
    }
    
    // --------------------------------------------------------------------------------
    //   Letter Sources
    // --------------------------------------------------------------------------------
    
    public LetterSource createLetterSource(String letterSourceName, Party companyParty,
            PartyContactMechanism emailAddressPartyContactMechanism, PartyContactMechanism postalAddressPartyContactMechanism,
            PartyContactMechanism letterSourcePartyContactMechanism, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultLetterSource = getDefaultLetterSource();
        var defaultFound = defaultLetterSource != null;
        
        if(defaultFound && isDefault) {
            var defaultLetterSourceDetailValue = getDefaultLetterSourceDetailValueForUpdate();
            
            defaultLetterSourceDetailValue.setIsDefault(false);
            updateLetterSourceFromValue(defaultLetterSourceDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var letterSource = LetterSourceFactory.getInstance().create();
        var letterSourceDetail = LetterSourceDetailFactory.getInstance().create(letterSource,
                letterSourceName, companyParty, emailAddressPartyContactMechanism, postalAddressPartyContactMechanism,
                letterSourcePartyContactMechanism, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        letterSource = LetterSourceFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, letterSource.getPrimaryKey());
        letterSource.setActiveDetail(letterSourceDetail);
        letterSource.setLastDetail(letterSourceDetail);
        letterSource.store();
        
        sendEvent(letterSource.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return letterSource;
    }
    
    private LetterSource getLetterSourceByName(String letterSourceName, EntityPermission entityPermission) {
        LetterSource letterSource;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM lettersources, lettersourcedetails " +
                        "WHERE lttrsrc_activedetailid = lttrsrcdt_lettersourcedetailid AND lttrsrcdt_lettersourcename = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM lettersources, lettersourcedetails " +
                        "WHERE lttrsrc_activedetailid = lttrsrcdt_lettersourcedetailid AND lttrsrcdt_lettersourcename = ? " +
                        "FOR UPDATE";
            }

            var ps = LetterSourceFactory.getInstance().prepareStatement(query);
            
            ps.setString(1, letterSourceName);
            
            letterSource = LetterSourceFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return letterSource;
    }
    
    public LetterSource getLetterSourceByName(String letterSourceName) {
        return getLetterSourceByName(letterSourceName, EntityPermission.READ_ONLY);
    }
    
    public LetterSource getLetterSourceByNameForUpdate(String letterSourceName) {
        return getLetterSourceByName(letterSourceName, EntityPermission.READ_WRITE);
    }
    
    public LetterSourceDetailValue getLetterSourceDetailValueForUpdate(LetterSource letterSource) {
        return letterSource == null? null: letterSource.getLastDetailForUpdate().getLetterSourceDetailValue().clone();
    }
    
    public LetterSourceDetailValue getLetterSourceDetailValueByNameForUpdate(String letterSourceName) {
        return getLetterSourceDetailValueForUpdate(getLetterSourceByNameForUpdate(letterSourceName));
    }
    
    private LetterSource getDefaultLetterSource(EntityPermission entityPermission) {
        String query = null;
        
        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ " +
                    "FROM lettersources, lettersourcedetails " +
                    "WHERE lttrsrc_activedetailid = lttrsrcdt_lettersourcedetailid AND lttrsrcdt_isdefault = 1";
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ " +
                    "FROM lettersources, lettersourcedetails " +
                    "WHERE lttrsrc_activedetailid = lttrsrcdt_lettersourcedetailid AND lttrsrcdt_isdefault = 1 " +
                    "FOR UPDATE";
        }

        var ps = LetterSourceFactory.getInstance().prepareStatement(query);
        
        return LetterSourceFactory.getInstance().getEntityFromQuery(entityPermission, ps);
    }
    
    public LetterSource getDefaultLetterSource() {
        return getDefaultLetterSource(EntityPermission.READ_ONLY);
    }
    
    public LetterSource getDefaultLetterSourceForUpdate() {
        return getDefaultLetterSource(EntityPermission.READ_WRITE);
    }
    
    public LetterSourceDetailValue getDefaultLetterSourceDetailValueForUpdate() {
        return getDefaultLetterSourceForUpdate().getLastDetailForUpdate().getLetterSourceDetailValue().clone();
    }
    
    private List<LetterSource> getLetterSources(EntityPermission entityPermission) {
        String query = null;
        
        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ " +
                    "FROM lettersources, lettersourcedetails " +
                    "WHERE lttrsrc_activedetailid = lttrsrcdt_letterSourcedetailid " +
                    "ORDER BY lttrsrcdt_sortorder, lttrsrcdt_letterSourcename";
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ " +
                    "FROM lettersources, lettersourcedetails " +
                    "WHERE lttrsrc_activedetailid = lttrsrcdt_letterSourcedetailid " +
                    "FOR UPDATE";
        }

        var ps = LetterSourceFactory.getInstance().prepareStatement(query);
        
        return LetterSourceFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
    }
    
    public List<LetterSource> getLetterSources() {
        return getLetterSources(EntityPermission.READ_ONLY);
    }
    
    public List<LetterSource> getLetterSourcesForUpdate() {
        return getLetterSources(EntityPermission.READ_WRITE);
    }
    
    private List<LetterSource> getLetterSourcesByEmailAddressPartyContactMechanism(PartyContactMechanism emailAddressPartyContactMechanism,
            EntityPermission entityPermission) {
        List<LetterSource> letterSources;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM lettersources, lettersourcedetails " +
                        "WHERE lttrsrc_activedetailid = lttrsrcdt_lettersourcedetailid AND lttrsrcdt_emailaddresspartycontactmechanismid = ? " +
                        "ORDER BY lttrsrcdt_sortorder, lttrsrcdt_lettersourcename";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM lettersources, lettersourcedetails " +
                        "WHERE lttrsrc_activedetailid = lttrsrcdt_lettersourcedetailid AND lttrsrcdt_emailaddresspartycontactmechanismid = ? " +
                        "FOR UPDATE";
            }

            var ps = LetterSourceFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, emailAddressPartyContactMechanism.getPrimaryKey().getEntityId());
            
            letterSources = LetterSourceFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return letterSources;
    }
    
    public List<LetterSource> getLetterSourcesByEmailAddressPartyContactMechanism(PartyContactMechanism emailAddressPartyContactMechanism) {
        return getLetterSourcesByEmailAddressPartyContactMechanism(emailAddressPartyContactMechanism, EntityPermission.READ_ONLY);
    }
    
    public List<LetterSource> getLetterSourcesByEmailAddressPartyContactMechanismForUpdate(PartyContactMechanism emailAddressPartyContactMechanism) {
        return getLetterSourcesByEmailAddressPartyContactMechanism(emailAddressPartyContactMechanism, EntityPermission.READ_WRITE);
    }
    
    private List<LetterSource> getLetterSourcesByPostalAddressPartyContactMechanism(PartyContactMechanism postalAddressPartyContactMechanism,
            EntityPermission entityPermission) {
        List<LetterSource> letterSources;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM lettersources, lettersourcedetails " +
                        "WHERE lttrsrc_activedetailid = lttrsrcdt_lettersourcedetailid AND lttrsrcdt_postaladdresspartycontactmechanismid = ? " +
                        "ORDER BY lttrsrcdt_sortorder, lttrsrcdt_lettersourcename";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM lettersources, lettersourcedetails " +
                        "WHERE lttrsrc_activedetailid = lttrsrcdt_lettersourcedetailid AND lttrsrcdt_postaladdresspartycontactmechanismid = ? " +
                        "FOR UPDATE";
            }

            var ps = LetterSourceFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, postalAddressPartyContactMechanism.getPrimaryKey().getEntityId());
            
            letterSources = LetterSourceFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return letterSources;
    }
    
    public List<LetterSource> getLetterSourcesByPostalAddressPartyContactMechanism(PartyContactMechanism postalAddressPartyContactMechanism) {
        return getLetterSourcesByPostalAddressPartyContactMechanism(postalAddressPartyContactMechanism, EntityPermission.READ_ONLY);
    }
    
    public List<LetterSource> getLetterSourcesByPostalAddressPartyContactMechanismForUpdate(PartyContactMechanism postalAddressPartyContactMechanism) {
        return getLetterSourcesByPostalAddressPartyContactMechanism(postalAddressPartyContactMechanism, EntityPermission.READ_WRITE);
    }
    
    private List<LetterSource> getLetterSourcesByLetterSourcePartyContactMechanism(PartyContactMechanism letterSourcePartyContactMechanism,
            EntityPermission entityPermission) {
        List<LetterSource> letterSources;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM lettersources, lettersourcedetails " +
                        "WHERE lttrsrc_activedetailid = lttrsrcdt_lettersourcedetailid AND lttrsrcdt_lettersourcepartycontactmechanismid = ? " +
                        "ORDER BY lttrsrcdt_sortorder, lttrsrcdt_lettersourcename";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM lettersources, lettersourcedetails " +
                        "WHERE lttrsrc_activedetailid = lttrsrcdt_lettersourcedetailid AND lttrsrcdt_lettersourcepartycontactmechanismid = ? " +
                        "FOR UPDATE";
            }

            var ps = LetterSourceFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, letterSourcePartyContactMechanism.getPrimaryKey().getEntityId());
            
            letterSources = LetterSourceFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return letterSources;
    }
    
    public List<LetterSource> getLetterSourcesByLetterSourcePartyContactMechanism(PartyContactMechanism letterSourcePartyContactMechanism) {
        return getLetterSourcesByLetterSourcePartyContactMechanism(letterSourcePartyContactMechanism, EntityPermission.READ_ONLY);
    }
    
    public List<LetterSource> getLetterSourcesByLetterSourcePartyContactMechanismForUpdate(PartyContactMechanism letterSourcePartyContactMechanism) {
        return getLetterSourcesByLetterSourcePartyContactMechanism(letterSourcePartyContactMechanism, EntityPermission.READ_WRITE);
    }
    
    public LetterSourceTransfer getLetterSourceTransfer(UserVisit userVisit, LetterSource letterSource) {
        return getLetterTransferCaches(userVisit).getLetterSourceTransferCache().getLetterSourceTransfer(letterSource);
    }
    
    public List<LetterSourceTransfer> getLetterSourceTransfers(UserVisit userVisit) {
        var letterSources = getLetterSources();
        List<LetterSourceTransfer> letterSourceTransfers = new ArrayList<>(letterSources.size());
        var letterSourceTransferCache = getLetterTransferCaches(userVisit).getLetterSourceTransferCache();
        
        letterSources.forEach((letterSource) ->
                letterSourceTransfers.add(letterSourceTransferCache.getLetterSourceTransfer(letterSource))
        );
        
        return letterSourceTransfers;
    }
    
    public LetterSourceChoicesBean getLetterSourceChoices(String defaultLetterSourceChoice, Language language, boolean allowNullChoice) {
        var letterSources = getLetterSources();
        var size = letterSources.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultLetterSourceChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var letterSource : letterSources) {
            var letterSourceDetail = letterSource.getLastDetail();
            
            var label = getBestLetterSourceDescription(letterSource, language);
            var value = letterSourceDetail.getLetterSourceName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultLetterSourceChoice != null && defaultLetterSourceChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && letterSourceDetail.getIsDefault()))
                defaultValue = value;
        }
        
        return new LetterSourceChoicesBean(labels, values, defaultValue);
    }
    
    private void updateLetterSourceFromValue(LetterSourceDetailValue letterSourceDetailValue, boolean checkDefault, BasePK updatedBy) {
        if(letterSourceDetailValue.hasBeenModified()) {
            var letterSource = LetterSourceFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     letterSourceDetailValue.getLetterSourcePK());
            var letterSourceDetail = letterSource.getActiveDetailForUpdate();
            
            letterSourceDetail.setThruTime(session.START_TIME_LONG);
            letterSourceDetail.store();

            var letterSourcePK = letterSourceDetail.getLetterSourcePK(); // Not updated
            var letterSourceName = letterSourceDetailValue.getLetterSourceName();
            var companyPartyPK = letterSourceDetail.getCompanyPartyPK(); // Not updated
            var emailAddressPartyContactMechanismPK = letterSourceDetailValue.getEmailAddressPartyContactMechanismPK();
            var postalAddressPartyContactMechanismPK = letterSourceDetailValue.getPostalAddressPartyContactMechanismPK();
            var letterSourcePartyContactMechanismPK = letterSourceDetailValue.getLetterSourcePartyContactMechanismPK();
            var isDefault = letterSourceDetailValue.getIsDefault();
            var sortOrder = letterSourceDetailValue.getSortOrder();
            
            if(checkDefault) {
                var defaultLetterSource = getDefaultLetterSource();
                var defaultFound = defaultLetterSource != null && !defaultLetterSource.equals(letterSource);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultLetterSourceDetailValue = getDefaultLetterSourceDetailValueForUpdate();
                    
                    defaultLetterSourceDetailValue.setIsDefault(false);
                    updateLetterSourceFromValue(defaultLetterSourceDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }
            
            letterSourceDetail = LetterSourceDetailFactory.getInstance().create(letterSourcePK, letterSourceName,
                    companyPartyPK, emailAddressPartyContactMechanismPK, postalAddressPartyContactMechanismPK,
                    letterSourcePartyContactMechanismPK, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            letterSource.setActiveDetail(letterSourceDetail);
            letterSource.setLastDetail(letterSourceDetail);
            
            sendEvent(letterSourcePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void updateLetterSourceFromValue(LetterSourceDetailValue letterSourceDetailValue, BasePK updatedBy) {
        updateLetterSourceFromValue(letterSourceDetailValue, true, updatedBy);
    }
    
    public void deleteLetterSource(LetterSource letterSource, BasePK deletedBy) {
        deleteLettersByLetterSource(letterSource, deletedBy);
        deleteLetterSourceDescriptionsByLetterSource(letterSource, deletedBy);

        var letterSourceDetail = letterSource.getLastDetailForUpdate();
        letterSourceDetail.setThruTime(session.START_TIME_LONG);
        letterSource.setActiveDetail(null);
        letterSource.store();
        
        // Check for default, and pick one if necessary
        var defaultLetterSource = getDefaultLetterSource();
        if(defaultLetterSource == null) {
            var letterSources = getLetterSourcesForUpdate();
            
            if(!letterSources.isEmpty()) {
                Iterator iter = letterSources.iterator();
                if(iter.hasNext()) {
                    defaultLetterSource = (LetterSource)iter.next();
                }
                var letterSourceDetailValue = Objects.requireNonNull(defaultLetterSource).getLastDetailForUpdate().getLetterSourceDetailValue().clone();
                
                letterSourceDetailValue.setIsDefault(true);
                updateLetterSourceFromValue(letterSourceDetailValue, false, deletedBy);
            }
        }
        
        sendEvent(letterSource.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    public void deleteLetterSources(List<LetterSource> letterSources, BasePK deletedBy) {
        letterSources.forEach((letterSource) -> 
                deleteLetterSource(letterSource, deletedBy)
        );
    }
    
    public void deleteLetterSourcesByEmailAddressPartyContactMechanism(PartyContactMechanism emailAddressPartyContactMechanism, BasePK deletedBy) {
        deleteLetterSources(getLetterSourcesByEmailAddressPartyContactMechanismForUpdate(emailAddressPartyContactMechanism), deletedBy);
    }
    
    public void deleteLetterSourcesByPostalAddressPartyContactMechanism(PartyContactMechanism postalAddressPartyContactMechanism, BasePK deletedBy) {
        deleteLetterSources(getLetterSourcesByPostalAddressPartyContactMechanismForUpdate(postalAddressPartyContactMechanism), deletedBy);
    }
    
    public void deleteLetterSourcesByLetterSourcePartyContactMechanism(PartyContactMechanism letterSourcePartyContactMechanism, BasePK deletedBy) {
        deleteLetterSources(getLetterSourcesByLetterSourcePartyContactMechanismForUpdate(letterSourcePartyContactMechanism), deletedBy);
    }
    
    public void deleteLetterSourcesByPartyContactMechanism(PartyContactMechanism partyContactMechanism, BasePK deletedBy) {
        deleteLetterSourcesByEmailAddressPartyContactMechanism(partyContactMechanism, deletedBy);
        deleteLetterSourcesByPostalAddressPartyContactMechanism(partyContactMechanism, deletedBy);
        deleteLetterSourcesByLetterSourcePartyContactMechanism(partyContactMechanism, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Letter Source Descriptions
    // --------------------------------------------------------------------------------
    
    public LetterSourceDescription createLetterSourceDescription(LetterSource letterSource, Language language, String description, BasePK createdBy) {
        var letterSourceDescription = LetterSourceDescriptionFactory.getInstance().create(letterSource, language, description,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(letterSource.getPrimaryKey(), EventTypes.MODIFY, letterSourceDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return letterSourceDescription;
    }
    
    private LetterSourceDescription getLetterSourceDescription(LetterSource letterSource, Language language, EntityPermission entityPermission) {
        LetterSourceDescription letterSourceDescription;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM lettersourcedescriptions " +
                        "WHERE lttrsrcd_lttrsrc_letterSourceid = ? AND lttrsrcd_lang_languageid = ? AND lttrsrcd_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM lettersourcedescriptions " +
                        "WHERE lttrsrcd_lttrsrc_letterSourceid = ? AND lttrsrcd_lang_languageid = ? AND lttrsrcd_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = LetterSourceDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, letterSource.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            letterSourceDescription = LetterSourceDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return letterSourceDescription;
    }
    
    public LetterSourceDescription getLetterSourceDescription(LetterSource letterSource, Language language) {
        return getLetterSourceDescription(letterSource, language, EntityPermission.READ_ONLY);
    }
    
    public LetterSourceDescription getLetterSourceDescriptionForUpdate(LetterSource letterSource,
            Language language) {
        return getLetterSourceDescription(letterSource, language, EntityPermission.READ_WRITE);
    }
    
    public LetterSourceDescriptionValue getLetterSourceDescriptionValue(LetterSourceDescription letterSourceDescription) {
        return letterSourceDescription == null? null: letterSourceDescription.getLetterSourceDescriptionValue().clone();
    }
    
    public LetterSourceDescriptionValue getLetterSourceDescriptionValueForUpdate(LetterSource letterSource,
            Language language) {
        return getLetterSourceDescriptionValue(getLetterSourceDescriptionForUpdate(letterSource, language));
    }
    
    private List<LetterSourceDescription> getLetterSourceDescriptionsByLetterSource(LetterSource letterSource, EntityPermission entityPermission) {
        List<LetterSourceDescription> letterSourceDescriptions;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM lettersourcedescriptions, languages " +
                        "WHERE lttrsrcd_lttrsrc_letterSourceid = ? AND lttrsrcd_thrutime = ? AND lttrsrcd_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM lettersourcedescriptions " +
                        "WHERE lttrsrcd_lttrsrc_letterSourceid = ? AND lttrsrcd_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = LetterSourceDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, letterSource.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            letterSourceDescriptions = LetterSourceDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return letterSourceDescriptions;
    }
    
    public List<LetterSourceDescription> getLetterSourceDescriptionsByLetterSource(LetterSource letterSource) {
        return getLetterSourceDescriptionsByLetterSource(letterSource, EntityPermission.READ_ONLY);
    }
    
    public List<LetterSourceDescription> getLetterSourceDescriptionsByLetterSourceForUpdate(LetterSource letterSource) {
        return getLetterSourceDescriptionsByLetterSource(letterSource, EntityPermission.READ_WRITE);
    }
    
    public String getBestLetterSourceDescription(LetterSource letterSource, Language language) {
        String description;
        var letterSourceDescription = getLetterSourceDescription(letterSource, language);
        
        if(letterSourceDescription == null && !language.getIsDefault()) {
            letterSourceDescription = getLetterSourceDescription(letterSource, getPartyControl().getDefaultLanguage());
        }
        
        if(letterSourceDescription == null) {
            description = letterSource.getLastDetail().getLetterSourceName();
        } else {
            description = letterSourceDescription.getDescription();
        }
        
        return description;
    }
    
    public LetterSourceDescriptionTransfer getLetterSourceDescriptionTransfer(UserVisit userVisit, LetterSourceDescription letterSourceDescription) {
        return getLetterTransferCaches(userVisit).getLetterSourceDescriptionTransferCache().getLetterSourceDescriptionTransfer(letterSourceDescription);
    }
    
    public List<LetterSourceDescriptionTransfer> getLetterSourceDescriptionTransfersByLetterSource(UserVisit userVisit, LetterSource letterSource) {
        var letterSourceDescriptions = getLetterSourceDescriptionsByLetterSource(letterSource);
        List<LetterSourceDescriptionTransfer> letterSourceDescriptionTransfers = null;
        
        if(letterSourceDescriptions != null) {
            var letterSourceDescriptionTransferCache = getLetterTransferCaches(userVisit).getLetterSourceDescriptionTransferCache();
            
            letterSourceDescriptionTransfers = new ArrayList<>(letterSourceDescriptions.size());
            
            for(var letterSourceDescription : letterSourceDescriptions) {
                letterSourceDescriptionTransfers.add(letterSourceDescriptionTransferCache.getLetterSourceDescriptionTransfer(letterSourceDescription));
            }
        }
        
        return letterSourceDescriptionTransfers;
    }
    
    public void updateLetterSourceDescriptionFromValue(LetterSourceDescriptionValue letterSourceDescriptionValue, BasePK updatedBy) {
        if(letterSourceDescriptionValue.hasBeenModified()) {
            var letterSourceDescription = LetterSourceDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     letterSourceDescriptionValue.getPrimaryKey());
            
            letterSourceDescription.setThruTime(session.START_TIME_LONG);
            letterSourceDescription.store();

            var letterSource = letterSourceDescription.getLetterSource();
            var language = letterSourceDescription.getLanguage();
            var description = letterSourceDescriptionValue.getDescription();
            
            letterSourceDescription = LetterSourceDescriptionFactory.getInstance().create(letterSource,
                    language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(letterSource.getPrimaryKey(), EventTypes.MODIFY, letterSourceDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteLetterSourceDescription(LetterSourceDescription letterSourceDescription, BasePK deletedBy) {
        letterSourceDescription.setThruTime(session.START_TIME_LONG);
        
        sendEvent(letterSourceDescription.getLetterSourcePK(), EventTypes.MODIFY, letterSourceDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteLetterSourceDescriptionsByLetterSource(LetterSource letterSource, BasePK deletedBy) {
        var letterSourceDescriptions = getLetterSourceDescriptionsByLetterSourceForUpdate(letterSource);
        
        letterSourceDescriptions.forEach((letterSourceDescription) -> 
                deleteLetterSourceDescription(letterSourceDescription, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Letters
    // --------------------------------------------------------------------------------
    
    public Letter createLetter(ChainType chainType, String letterName, LetterSource letterSource, ContactList contactList,
            Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultLetter = getDefaultLetter(chainType);
        var defaultFound = defaultLetter != null;
        
        if(defaultFound && isDefault) {
            var defaultLetterDetailValue = getDefaultLetterDetailValueForUpdate(chainType);
            
            defaultLetterDetailValue.setIsDefault(false);
            updateLetterFromValue(defaultLetterDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var letter = LetterFactory.getInstance().create();
        var letterDetail = LetterDetailFactory.getInstance().create(letter, chainType, letterName,
                letterSource, contactList, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        letter = LetterFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, letter.getPrimaryKey());
        letter.setActiveDetail(letterDetail);
        letter.setLastDetail(letterDetail);
        letter.store();
        
        sendEvent(letter.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return letter;
    }
    
    private Letter getLetterByName(ChainType chainType, String letterName, EntityPermission entityPermission) {
        Letter letter;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM letters, letterdetails " +
                        "WHERE lttr_activedetailid = lttrdt_letterdetailid AND lttrdt_chntyp_chaintypeid = ? AND lttrdt_lettername = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM letters, letterdetails " +
                        "WHERE lttr_activedetailid = lttrdt_letterdetailid AND lttrdt_chntyp_chaintypeid = ? AND lttrdt_lettername = ? " +
                        "FOR UPDATE";
            }

            var ps = LetterFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, chainType.getPrimaryKey().getEntityId());
            ps.setString(2, letterName);
            
            letter = LetterFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return letter;
    }
    
    public Letter getLetterByName(ChainType chainType, String letterName) {
        return getLetterByName(chainType, letterName, EntityPermission.READ_ONLY);
    }
    
    public Letter getLetterByNameForUpdate(ChainType chainType, String letterName) {
        return getLetterByName(chainType, letterName, EntityPermission.READ_WRITE);
    }
    
    public LetterDetailValue getLetterDetailValueForUpdate(Letter letter) {
        return letter == null? null: letter.getLastDetailForUpdate().getLetterDetailValue().clone();
    }
    
    public LetterDetailValue getLetterDetailValueByNameForUpdate(ChainType chainType, String letterName) {
        return getLetterDetailValueForUpdate(getLetterByNameForUpdate(chainType, letterName));
    }
    
    private Letter getDefaultLetter(ChainType chainType, EntityPermission entityPermission) {
        Letter letter;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM letters, letterdetails " +
                        "WHERE lttr_activedetailid = lttrdt_letterdetailid AND lttrdt_chntyp_chaintypeid = ? AND lttrdt_isdefault = 1";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM letters, letterdetails " +
                        "WHERE lttr_activedetailid = lttrdt_letterdetailid AND lttrdt_chntyp_chaintypeid = ? AND lttrdt_isdefault = 1 " +
                        "FOR UPDATE";
            }

            var ps = LetterFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, chainType.getPrimaryKey().getEntityId());
            
            letter = LetterFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return letter;
    }
    
    public Letter getDefaultLetter(ChainType chainType) {
        return getDefaultLetter(chainType, EntityPermission.READ_ONLY);
    }
    
    public Letter getDefaultLetterForUpdate(ChainType chainType) {
        return getDefaultLetter(chainType, EntityPermission.READ_WRITE);
    }
    
    public LetterDetailValue getDefaultLetterDetailValueForUpdate(ChainType chainType) {
        return getDefaultLetterForUpdate(chainType).getLastDetailForUpdate().getLetterDetailValue().clone();
    }
    
    private List<Letter> getLettersByChainType(ChainType chainType, EntityPermission entityPermission) {
        List<Letter> letters;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM letters, letterdetails " +
                        "WHERE lttr_activedetailid = lttrdt_letterdetailid AND lttrdt_chntyp_chaintypeid = ? " +
                        "ORDER BY lttrdt_sortorder, lttrdt_lettername";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM letters, letterdetails " +
                        "WHERE lttr_activedetailid = lttrdt_letterdetailid AND lttrdt_chntyp_chaintypeid = ? " +
                        "FOR UPDATE";
            }

            var ps = LetterFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, chainType.getPrimaryKey().getEntityId());
            
            letters = LetterFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return letters;
    }
    
    public List<Letter> getLettersByChainType(ChainType chainType) {
        return getLettersByChainType(chainType, EntityPermission.READ_ONLY);
    }
    
    public List<Letter> getLettersByChainTypeForUpdate(ChainType chainType) {
        return getLettersByChainType(chainType, EntityPermission.READ_WRITE);
    }
    
    private List<Letter> getLettersByLetterSource(LetterSource letterSource, EntityPermission entityPermission) {
        List<Letter> letters;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM letters, letterdetails " +
                        "WHERE lttr_activedetailid = lttrdt_letterdetailid AND lttrdt_lttrsrc_lettersourceid = ? " +
                        "ORDER BY lttrdt_sortorder, lttrdt_lettername";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM letters, letterdetails " +
                        "WHERE lttr_activedetailid = lttrdt_letterdetailid AND lttrdt_lttrsrc_lettersourceid = ? " +
                        "FOR UPDATE";
            }

            var ps = LetterFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, letterSource.getPrimaryKey().getEntityId());
            
            letters = LetterFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return letters;
    }
    
    public List<Letter> getLettersByLetterSource(LetterSource letterSource) {
        return getLettersByLetterSource(letterSource, EntityPermission.READ_ONLY);
    }
    
    public List<Letter> getLettersByLetterSourceForUpdate(LetterSource letterSource) {
        return getLettersByLetterSource(letterSource, EntityPermission.READ_WRITE);
    }
    
    private List<Letter> getLettersByContactList(ContactList contactList, EntityPermission entityPermission) {
        List<Letter> letters;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM letters, letterdetails " +
                        "WHERE lttr_activedetailid = lttrdt_letterdetailid AND lttrdt_clst_contactlistid = ?";
                // TODO: ORDER BY
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM letters, letterdetails " +
                        "WHERE lttr_activedetailid = lttrdt_letterdetailid AND lttrdt_clst_contactlistid = ? " +
                        "FOR UPDATE";
            }

            var ps = LetterFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, contactList.getPrimaryKey().getEntityId());
            
            letters = LetterFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return letters;
    }
    
    public List<Letter> getLettersByContactList(ContactList contactList) {
        return getLettersByContactList(contactList, EntityPermission.READ_ONLY);
    }
    
    public List<Letter> getLettersByContactListForUpdate(ContactList contactList) {
        return getLettersByContactList(contactList, EntityPermission.READ_WRITE);
    }
    
    public LetterTransfer getLetterTransfer(UserVisit userVisit, Letter letter) {
        return getLetterTransferCaches(userVisit).getLetterTransferCache().getLetterTransfer(letter);
    }
    
    public List<LetterTransfer> getLetterTransfers(UserVisit userVisit, Collection<Letter> letters) {
        List<LetterTransfer> letterTransfers = new ArrayList<>(letters.size());
        var letterTransferCache = getLetterTransferCaches(userVisit).getLetterTransferCache();
        
        letters.forEach((letter) ->
                letterTransfers.add(letterTransferCache.getLetterTransfer(letter))
        );
        
        return letterTransfers;
    }
    
    public List<LetterTransfer> getLetterTransfersByChainType(UserVisit userVisit, ChainType chainType) {
        return getLetterTransfers(userVisit, getLettersByChainType(chainType));
    }
    
    public List<LetterTransfer> getLetterTransfersByLetterSource(UserVisit userVisit, LetterSource letterSource) {
        return getLetterTransfers(userVisit, getLettersByLetterSource(letterSource));
    }
    
    public LetterChoicesBean getLetterChoices(ChainType chainType, String defaultLetterChoice, Language language, boolean allowNullChoice) {
        var letters = getLettersByChainType(chainType);
        var size = letters.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultLetterChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var letter : letters) {
            var letterDetail = letter.getLastDetail();
            
            var label = getBestLetterDescription(letter, language);
            var value = letterDetail.getLetterName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultLetterChoice != null && defaultLetterChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && letterDetail.getIsDefault()))
                defaultValue = value;
        }
        
        return new LetterChoicesBean(labels, values, defaultValue);
    }
    
    private void updateLetterFromValue(LetterDetailValue letterDetailValue, boolean checkDefault, BasePK updatedBy) {
        if(letterDetailValue.hasBeenModified()) {
            var letter = LetterFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     letterDetailValue.getLetterPK());
            var letterDetail = letter.getActiveDetailForUpdate();
            
            letterDetail.setThruTime(session.START_TIME_LONG);
            letterDetail.store();

            var letterPK = letterDetail.getLetterPK();
            var chainType = letterDetail.getChainType();
            var chainTypePK = chainType.getPrimaryKey();
            var letterName = letterDetailValue.getLetterName();
            var letterSourcePK = letterDetailValue.getLetterSourcePK();
            var contactListPK = letterDetailValue.getContactListPK();
            var isDefault = letterDetailValue.getIsDefault();
            var sortOrder = letterDetailValue.getSortOrder();
            
            if(checkDefault) {
                var defaultLetter = getDefaultLetter(chainType);
                var defaultFound = defaultLetter != null && !defaultLetter.equals(letter);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultLetterDetailValue = getDefaultLetterDetailValueForUpdate(chainType);
                    
                    defaultLetterDetailValue.setIsDefault(false);
                    updateLetterFromValue(defaultLetterDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }
            
            letterDetail = LetterDetailFactory.getInstance().create(letterPK, chainTypePK, letterName, letterSourcePK,
                    contactListPK, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            letter.setActiveDetail(letterDetail);
            letter.setLastDetail(letterDetail);
            
            sendEvent(letterPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void updateLetterFromValue(LetterDetailValue letterDetailValue, BasePK updatedBy) {
        updateLetterFromValue(letterDetailValue, true, updatedBy);
    }
    
    public void deleteLetter(Letter letter, BasePK deletedBy) {
        removeQueuedLettersByLetter(letter, deletedBy);
        deleteLetterDescriptionsByLetter(letter, deletedBy);

        var letterDetail = letter.getLastDetailForUpdate();
        letterDetail.setThruTime(session.START_TIME_LONG);
        letter.setActiveDetail(null);
        letter.store();
        
        // Check for default, and pick one if necessary
        var chainType = letterDetail.getChainType();
        var defaultLetter = getDefaultLetter(chainType);
        if(defaultLetter == null) {
            var letters = getLettersByChainTypeForUpdate(chainType);
            
            if(!letters.isEmpty()) {
                Iterator iter = letters.iterator();
                if(iter.hasNext()) {
                    defaultLetter = (Letter)iter.next();
                }
                var letterDetailValue = Objects.requireNonNull(defaultLetter).getLastDetailForUpdate().getLetterDetailValue().clone();
                
                letterDetailValue.setIsDefault(true);
                updateLetterFromValue(letterDetailValue, false, deletedBy);
            }
        }
        
        sendEvent(letter.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    public void deleteLetters(List<Letter> letters, BasePK deletedBy) {
        letters.forEach((letter) -> 
                deleteLetter(letter, deletedBy)
        );
    }
    
    public void deleteLettersByLetterSource(LetterSource letterSource, BasePK deletedBy) {
        deleteLetters(getLettersByLetterSourceForUpdate(letterSource), deletedBy);
    }
    
    public void deleteLettersByContactList(ContactList contactList, BasePK deletedBy) {
        deleteLetters(getLettersByContactListForUpdate(contactList), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Letter Descriptions
    // --------------------------------------------------------------------------------
    
    public LetterDescription createLetterDescription(Letter letter, Language language, String description, BasePK createdBy) {
        var letterDescription = LetterDescriptionFactory.getInstance().create(letter, language, description,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(letter.getPrimaryKey(), EventTypes.MODIFY, letterDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return letterDescription;
    }
    
    private LetterDescription getLetterDescription(Letter letter, Language language, EntityPermission entityPermission) {
        LetterDescription letterDescription;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM letterdescriptions " +
                        "WHERE lttrd_lttr_letterid = ? AND lttrd_lang_languageid = ? AND lttrd_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM letterdescriptions " +
                        "WHERE lttrd_lttr_letterid = ? AND lttrd_lang_languageid = ? AND lttrd_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = LetterDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, letter.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            letterDescription = LetterDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return letterDescription;
    }
    
    public LetterDescription getLetterDescription(Letter letter, Language language) {
        return getLetterDescription(letter, language, EntityPermission.READ_ONLY);
    }
    
    public LetterDescription getLetterDescriptionForUpdate(Letter letter,
            Language language) {
        return getLetterDescription(letter, language, EntityPermission.READ_WRITE);
    }
    
    public LetterDescriptionValue getLetterDescriptionValue(LetterDescription letterDescription) {
        return letterDescription == null? null: letterDescription.getLetterDescriptionValue().clone();
    }
    
    public LetterDescriptionValue getLetterDescriptionValueForUpdate(Letter letter,
            Language language) {
        return getLetterDescriptionValue(getLetterDescriptionForUpdate(letter, language));
    }
    
    private List<LetterDescription> getLetterDescriptionsByLetter(Letter letter, EntityPermission entityPermission) {
        List<LetterDescription> letterDescriptions;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM letterdescriptions, languages " +
                        "WHERE lttrd_lttr_letterid = ? AND lttrd_thrutime = ? AND lttrd_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM letterdescriptions " +
                        "WHERE lttrd_lttr_letterid = ? AND lttrd_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = LetterDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, letter.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            letterDescriptions = LetterDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return letterDescriptions;
    }
    
    public List<LetterDescription> getLetterDescriptionsByLetter(Letter letter) {
        return getLetterDescriptionsByLetter(letter, EntityPermission.READ_ONLY);
    }
    
    public List<LetterDescription> getLetterDescriptionsByLetterForUpdate(Letter letter) {
        return getLetterDescriptionsByLetter(letter, EntityPermission.READ_WRITE);
    }
    
    public String getBestLetterDescription(Letter letter, Language language) {
        String description;
        var letterDescription = getLetterDescription(letter, language);
        
        if(letterDescription == null && !language.getIsDefault()) {
            letterDescription = getLetterDescription(letter, getPartyControl().getDefaultLanguage());
        }
        
        if(letterDescription == null) {
            description = letter.getLastDetail().getLetterName();
        } else {
            description = letterDescription.getDescription();
        }
        
        return description;
    }
    
    public LetterDescriptionTransfer getLetterDescriptionTransfer(UserVisit userVisit, LetterDescription letterDescription) {
        return getLetterTransferCaches(userVisit).getLetterDescriptionTransferCache().getLetterDescriptionTransfer(letterDescription);
    }
    
    public List<LetterDescriptionTransfer> getLetterDescriptionTransfersByLetter(UserVisit userVisit, Letter letter) {
        var letterDescriptions = getLetterDescriptionsByLetter(letter);
        List<LetterDescriptionTransfer> letterDescriptionTransfers = null;
        
        if(letterDescriptions != null) {
            var letterDescriptionTransferCache = getLetterTransferCaches(userVisit).getLetterDescriptionTransferCache();
            
            letterDescriptionTransfers = new ArrayList<>(letterDescriptions.size());
            
            for(var letterDescription : letterDescriptions) {
                letterDescriptionTransfers.add(letterDescriptionTransferCache.getLetterDescriptionTransfer(letterDescription));
            }
        }
        
        return letterDescriptionTransfers;
    }
    
    public void updateLetterDescriptionFromValue(LetterDescriptionValue letterDescriptionValue, BasePK updatedBy) {
        if(letterDescriptionValue.hasBeenModified()) {
            var letterDescription = LetterDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     letterDescriptionValue.getPrimaryKey());
            
            letterDescription.setThruTime(session.START_TIME_LONG);
            letterDescription.store();

            var letter = letterDescription.getLetter();
            var language = letterDescription.getLanguage();
            var description = letterDescriptionValue.getDescription();
            
            letterDescription = LetterDescriptionFactory.getInstance().create(letter,
                    language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(letter.getPrimaryKey(), EventTypes.MODIFY, letterDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteLetterDescription(LetterDescription letterDescription, BasePK deletedBy) {
        letterDescription.setThruTime(session.START_TIME_LONG);
        
        sendEvent(letterDescription.getLetterPK(), EventTypes.MODIFY, letterDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteLetterDescriptionsByLetter(Letter letter, BasePK deletedBy) {
        var letterDescriptions = getLetterDescriptionsByLetterForUpdate(letter);
        
        letterDescriptions.forEach((letterDescription) -> 
                deleteLetterDescription(letterDescription, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Contact List Contact Mechanism Purposes
    // --------------------------------------------------------------------------------
    
    public LetterContactMechanismPurpose createLetterContactMechanismPurpose(Letter letter, Integer priority,
            ContactMechanismPurpose contactMechanismPurpose, BasePK createdBy) {
        var letterContactMechanismPurpose = LetterContactMechanismPurposeFactory.getInstance().create();
        var letterContactMechanismPurposeDetail = LetterContactMechanismPurposeDetailFactory.getInstance().create(session,
                letterContactMechanismPurpose, letter, priority, contactMechanismPurpose, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);
        
        // Convert to R/W
        letterContactMechanismPurpose = LetterContactMechanismPurposeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                letterContactMechanismPurpose.getPrimaryKey());
        letterContactMechanismPurpose.setActiveDetail(letterContactMechanismPurposeDetail);
        letterContactMechanismPurpose.setLastDetail(letterContactMechanismPurposeDetail);
        letterContactMechanismPurpose.store();
        
        sendEvent(letter.getPrimaryKey(), EventTypes.MODIFY, letterContactMechanismPurpose.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return letterContactMechanismPurpose;
    }
    
    private LetterContactMechanismPurpose getLetterContactMechanismPurpose(Letter letter, Integer priority,
            EntityPermission entityPermission) {
        LetterContactMechanismPurpose letterContactMechanismPurpose;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM lettercontactmechanismpurposes, lettercontactmechanismpurposedetails " +
                        "WHERE lttrcmpr_activedetailid = lttrcmprdt_lettercontactmechanismpurposedetailid " +
                        "AND lttrcmprdt_lttr_letterid = ? AND lttrcmprdt_priority = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM lettercontactmechanismpurposes, lettercontactmechanismpurposedetails " +
                        "WHERE lttrcmpr_activedetailid = lttrcmprdt_lettercontactmechanismpurposedetailid " +
                        "AND lttrcmprdt_lttr_letterid = ? AND lttrcmprdt_priority = ? " +
                        "FOR UPDATE";
            }

            var ps = LetterContactMechanismPurposeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, letter.getPrimaryKey().getEntityId());
            ps.setInt(2, priority);
            
            letterContactMechanismPurpose = LetterContactMechanismPurposeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return letterContactMechanismPurpose;
    }
    
    public LetterContactMechanismPurpose getLetterContactMechanismPurpose(Letter letter, Integer priority) {
        return getLetterContactMechanismPurpose(letter, priority, EntityPermission.READ_ONLY);
    }
    
    public LetterContactMechanismPurpose getLetterContactMechanismPurposeForUpdate(Letter letter, Integer priority) {
        return getLetterContactMechanismPurpose(letter, priority, EntityPermission.READ_WRITE);
    }
    
    public LetterContactMechanismPurposeDetailValue getLetterContactMechanismPurposeDetailValueForUpdate(LetterContactMechanismPurpose letterContactMechanismPurpose) {
        return letterContactMechanismPurpose == null? null: letterContactMechanismPurpose.getLastDetailForUpdate().getLetterContactMechanismPurposeDetailValue().clone();
    }
    
    public LetterContactMechanismPurposeDetailValue getLetterContactMechanismPurposeDetailValueForUpdate(Letter letter, Integer priority) {
        return getLetterContactMechanismPurposeDetailValueForUpdate(getLetterContactMechanismPurposeForUpdate(letter, priority));
    }
    
    private List<LetterContactMechanismPurpose> getLetterContactMechanismPurposesByLetter(Letter letter,
            EntityPermission entityPermission) {
        List<LetterContactMechanismPurpose> letterContactMechanismPurposes;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM lettercontactmechanismpurposes, lettercontactmechanismpurposedetails " +
                        "WHERE lttrcmpr_activedetailid = lttrcmprdt_lettercontactmechanismpurposedetailid " +
                        "AND lttrcmprdt_lttr_letterid = ? " +
                        "ORDER BY lttrcmprdt_priority";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM lettercontactmechanismpurposes, lettercontactmechanismpurposedetails " +
                        "WHERE lttrcmpr_activedetailid = lttrcmprdt_lettercontactmechanismpurposedetailid " +
                        "AND lttrcmprdt_lttr_letterid = ? " +
                        "FOR UPDATE";
            }

            var ps = LetterContactMechanismPurposeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, letter.getPrimaryKey().getEntityId());
            
            letterContactMechanismPurposes = LetterContactMechanismPurposeFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return letterContactMechanismPurposes;
    }
    
    public List<LetterContactMechanismPurpose> getLetterContactMechanismPurposesByLetter(Letter letter) {
        return getLetterContactMechanismPurposesByLetter(letter, EntityPermission.READ_ONLY);
    }
    
    public List<LetterContactMechanismPurpose> getLetterContactMechanismPurposesByLetterForUpdate(Letter letter) {
        return getLetterContactMechanismPurposesByLetter(letter, EntityPermission.READ_WRITE);
    }
    
    public LetterContactMechanismPurposeTransfer getLetterContactMechanismPurposeTransfer(UserVisit userVisit, LetterContactMechanismPurpose letterContactMechanismPurpose) {
        return getLetterTransferCaches(userVisit).getLetterContactMechanismPurposeTransferCache().getLetterContactMechanismPurposeTransfer(letterContactMechanismPurpose);
    }
    
    public List<LetterContactMechanismPurposeTransfer> getLetterContactMechanismPurposeTransfersByLetter(UserVisit userVisit, Letter letter) {
        var letterContactMechanismPurposes = getLetterContactMechanismPurposesByLetter(letter);
        List<LetterContactMechanismPurposeTransfer> letterContactMechanismPurposeTransfers = new ArrayList<>(letterContactMechanismPurposes.size());
        var letterContactMechanismPurposeTransferCache = getLetterTransferCaches(userVisit).getLetterContactMechanismPurposeTransferCache();
        
        letterContactMechanismPurposes.forEach((letterContactMechanismPurpose) ->
                letterContactMechanismPurposeTransfers.add(letterContactMechanismPurposeTransferCache.getLetterContactMechanismPurposeTransfer(letterContactMechanismPurpose))
        );
        
        return letterContactMechanismPurposeTransfers;
    }
    
    public void updateLetterContactMechanismPurposeFromValue(LetterContactMechanismPurposeDetailValue letterContactMechanismPurposeDetailValue, BasePK updatedBy) {
        if(letterContactMechanismPurposeDetailValue.hasBeenModified()) {
            var letterContactMechanismPurpose = LetterContactMechanismPurposeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     letterContactMechanismPurposeDetailValue.getLetterContactMechanismPurposePK());
            var letterContactMechanismPurposeDetail = letterContactMechanismPurpose.getActiveDetailForUpdate();
            
            letterContactMechanismPurposeDetail.setThruTime(session.START_TIME_LONG);
            letterContactMechanismPurposeDetail.store();

            var letterContactMechanismPurposePK = letterContactMechanismPurposeDetail.getLetterContactMechanismPurposePK();
            var letterPK = letterContactMechanismPurposeDetail.getLetterPK();
            var priority = letterContactMechanismPurposeDetailValue.getPriority();
            var contactMechanismPurposePK = letterContactMechanismPurposeDetailValue.getContactMechanismPurposePK();
            
            letterContactMechanismPurposeDetail = LetterContactMechanismPurposeDetailFactory.getInstance().create(session,
                    letterContactMechanismPurposePK, letterPK, priority, contactMechanismPurposePK, session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);
            
            letterContactMechanismPurpose.setActiveDetail(letterContactMechanismPurposeDetail);
            letterContactMechanismPurpose.setLastDetail(letterContactMechanismPurposeDetail);
            
            sendEvent(letterPK, EventTypes.MODIFY, letterContactMechanismPurposePK, EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteLetterContactMechanismPurpose(LetterContactMechanismPurpose letterContactMechanismPurpose, BasePK deletedBy) {
        var letterContactMechanismPurposeDetail = letterContactMechanismPurpose.getLastDetailForUpdate();
        letterContactMechanismPurposeDetail.setThruTime(session.START_TIME_LONG);
        letterContactMechanismPurpose.setActiveDetail(null);
        letterContactMechanismPurpose.store();
        
        sendEvent(letterContactMechanismPurposeDetail.getLetterPK(), EventTypes.MODIFY, letterContactMechanismPurpose.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteLetterContactMechanismPurposesByLetter(Letter letter, BasePK deletedBy) {
        var letterContactMechanismPurposes = getLetterContactMechanismPurposesByLetterForUpdate(letter);
        
        letterContactMechanismPurposes.forEach((letterContactMechanismPurpose) -> 
                deleteLetterContactMechanismPurpose(letterContactMechanismPurpose, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Queued Letters
    // --------------------------------------------------------------------------------
    
    public QueuedLetter createQueuedLetter(ChainInstance chainInstance, Letter letter) {
        var chainControl = Session.getModelController(ChainControl.class);
        var chainInstanceStatus = chainControl.getChainInstanceStatusForUpdate(chainInstance);
        Integer queuedLetterSequence = chainInstanceStatus.getQueuedLetterSequence() + 1;

        chainInstanceStatus.setQueuedLetterSequence(queuedLetterSequence);

        return createQueuedLetter(chainInstance, queuedLetterSequence, letter);
    }

    public QueuedLetter createQueuedLetter(ChainInstance chainInstance, Integer queuedLetterSequence, Letter letter) {
        return QueuedLetterFactory.getInstance().create(chainInstance, queuedLetterSequence, letter);
    }

    public boolean isChainInstanceUsedByQueuedLetters(ChainInstance chainInstance) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM queuedletters " +
                "WHERE qlttr_chni_chaininstanceid = ?",
                chainInstance) != 1;
    }

    public boolean isChainInstanceUsedByQueuedLetters(Letter letter) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM queuedletters " +
                "WHERE qlttr_lttr_letterid = ?",
                letter) != 1;
    }

    private static final Map<EntityPermission, String> getQueuedLetterQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM queuedletters "
                + "WHERE qlttr_chni_chaininstanceid = ? AND qlttr_queuedlettersequence = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM queuedletters "
                + "WHERE qlttr_chni_chaininstanceid = ? AND qlttr_queuedlettersequence = ? "
                + "FOR UPDATE");
        getQueuedLetterQueries = Collections.unmodifiableMap(queryMap);
    }

    private QueuedLetter getQueuedLetter(ChainInstance chainInstance, Integer queuedLetterSequence, EntityPermission entityPermission) {
        return QueuedLetterFactory.getInstance().getEntityFromQuery(entityPermission, getQueuedLetterQueries,
                chainInstance, queuedLetterSequence);
    }

    public QueuedLetter getQueuedLetter(ChainInstance chainInstance, Integer queuedLetterSequence) {
        return getQueuedLetter(chainInstance, queuedLetterSequence, EntityPermission.READ_ONLY);
    }

    public QueuedLetter getQueuedLetterForUpdate(ChainInstance chainInstance, Integer queuedLetterSequence) {
        return getQueuedLetter(chainInstance, queuedLetterSequence, EntityPermission.READ_WRITE);
    }

    private List<QueuedLetter> getQueuedLetters(EntityPermission entityPermission) {
        String query = null;
        
        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ " +
                    "FROM queuedletters";
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ " +
                    "FROM queuedletters " +
                    "FOR UPDATE";
        }

        var ps = QueuedLetterFactory.getInstance().prepareStatement(query);
        
        return QueuedLetterFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
    }
    
    public List<QueuedLetter> getQueuedLetters() {
        return getQueuedLetters(EntityPermission.READ_ONLY);
    }
    
    public List<QueuedLetter> getQueuedLettersForUpdate() {
        return getQueuedLetters(EntityPermission.READ_WRITE);
    }
    
    private List<QueuedLetter> getQueuedLettersByChainInstance(ChainInstance chainInstance, EntityPermission entityPermission) {
        List<QueuedLetter> queuedLetters;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM queuedletters " +
                        "WHERE qlttr_chni_chaininstanceid = ? " +
                        "ORDER BY qlttr_queuedletterid";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM queuedletters " +
                        "WHERE qlttr_chni_chaininstanceid = ? " +
                        "FOR UPDATE";
            }

            var ps = QueuedLetterFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, chainInstance.getPrimaryKey().getEntityId());
            
            queuedLetters = QueuedLetterFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return queuedLetters;
    }
    
    public List<QueuedLetter> getQueuedLettersByChainInstance(ChainInstance chainInstance) {
        return getQueuedLettersByChainInstance(chainInstance, EntityPermission.READ_ONLY);
    }
    
    public List<QueuedLetter> getQueuedLettersByChainInstanceForUpdate(ChainInstance chainInstance) {
        return getQueuedLettersByChainInstance(chainInstance, EntityPermission.READ_WRITE);
    }
    
    private List<QueuedLetter> getQueuedLettersByLetter(Letter letter, EntityPermission entityPermission) {
        List<QueuedLetter> queuedLetters;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM queuedletters " +
                        "WHERE qlttr_lttr_letterid = ? " +
                        "ORDER BY qlttr_queuedletterid";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM queuedletters " +
                        "WHERE qlttr_lttr_letterid = ? " +
                        "FOR UPDATE";
            }

            var ps = QueuedLetterFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, letter.getPrimaryKey().getEntityId());
            
            queuedLetters = QueuedLetterFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return queuedLetters;
    }
    
    public List<QueuedLetter> getQueuedLettersByLetter(Letter letter) {
        return getQueuedLettersByLetter(letter, EntityPermission.READ_ONLY);
    }
    
    public List<QueuedLetter> getQueuedLettersByLetterForUpdate(Letter letter) {
        return getQueuedLettersByLetter(letter, EntityPermission.READ_WRITE);
    }
    
    public QueuedLetterTransfer getQueuedLetterTransfer(UserVisit userVisit, QueuedLetter queuedLetter) {
        return getLetterTransferCaches(userVisit).getQueuedLetterTransferCache().getQueuedLetterTransfer(queuedLetter);
    }
    
    public List<QueuedLetterTransfer> getQueuedLetterTransfers(UserVisit userVisit, Collection<QueuedLetter> queuedLetters) {
        List<QueuedLetterTransfer> queuedLetterTransfers = new ArrayList<>(queuedLetters.size());
        var queuedLetterTransferCache = getLetterTransferCaches(userVisit).getQueuedLetterTransferCache();

        queuedLetters.forEach((queuedLetter) ->
                queuedLetterTransfers.add(queuedLetterTransferCache.getQueuedLetterTransfer(queuedLetter))
        );

        return queuedLetterTransfers;
    }
    public List<QueuedLetterTransfer> getQueuedLetterTransfers(UserVisit userVisit) {
        return getQueuedLetterTransfers(userVisit, getQueuedLetters());
    }

    public List<QueuedLetterTransfer> getQueuedLetterTransfersByLetter(UserVisit userVisit, Letter letter) {
        return getQueuedLetterTransfers(userVisit, getQueuedLettersByLetter(letter));
    }

    private void removeQueuedLetter(QueuedLetter queuedLetter, boolean checkChainInstance, BasePK removedBy) {
        queuedLetter.remove();

        if(checkChainInstance) {
            var chainControl = Session.getModelController(ChainControl.class);

            chainControl.deleteChainInstanceIfUnused(queuedLetter.getChainInstanceForUpdate(), removedBy);
        }
    }

    public void removeQueuedLetter(QueuedLetter queuedLetter, BasePK removedBy) {
        removeQueuedLetter(queuedLetter, true, removedBy);
    }

    private void removedQueuedLetters(List<QueuedLetter> queuedletters, boolean checkChainInstance, BasePK removedBy) {
        queuedletters.forEach((queuedLetter) -> {
            removeQueuedLetter(queuedLetter, checkChainInstance, removedBy);
        });
    }
    
    public void removedQueuedLettersByChainInstance(ChainInstance chainInstance) {
        removedQueuedLetters(getQueuedLettersByChainInstanceForUpdate(chainInstance), false, null);
    }
    
    public void removeQueuedLettersByLetter(Letter letter, BasePK removedBy) {
        removedQueuedLetters(getQueuedLettersByLetterForUpdate(letter), true, removedBy);
    }
    
}
