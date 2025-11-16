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

package com.echothree.model.control.associate.server.control;

import com.echothree.model.control.associate.common.choice.AssociateChoicesBean;
import com.echothree.model.control.associate.common.choice.AssociatePartyContactMechanismChoicesBean;
import com.echothree.model.control.associate.common.choice.AssociateProgramChoicesBean;
import com.echothree.model.control.associate.common.transfer.AssociatePartyContactMechanismTransfer;
import com.echothree.model.control.associate.common.transfer.AssociateProgramDescriptionTransfer;
import com.echothree.model.control.associate.common.transfer.AssociateProgramTransfer;
import com.echothree.model.control.associate.common.transfer.AssociateReferralTransfer;
import com.echothree.model.control.associate.common.transfer.AssociateTransfer;
import com.echothree.model.control.associate.server.transfer.AssociateTransferCaches;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.sequence.common.SequenceTypes;
import com.echothree.model.control.sequence.server.control.SequenceControl;
import com.echothree.model.control.sequence.server.logic.SequenceGeneratorLogic;
import com.echothree.model.data.associate.server.entity.Associate;
import com.echothree.model.data.associate.server.entity.AssociatePartyContactMechanism;
import com.echothree.model.data.associate.server.entity.AssociateProgram;
import com.echothree.model.data.associate.server.entity.AssociateProgramDescription;
import com.echothree.model.data.associate.server.entity.AssociateReferral;
import com.echothree.model.data.associate.server.factory.AssociateDetailFactory;
import com.echothree.model.data.associate.server.factory.AssociateFactory;
import com.echothree.model.data.associate.server.factory.AssociatePartyContactMechanismDetailFactory;
import com.echothree.model.data.associate.server.factory.AssociatePartyContactMechanismFactory;
import com.echothree.model.data.associate.server.factory.AssociateProgramDescriptionFactory;
import com.echothree.model.data.associate.server.factory.AssociateProgramDetailFactory;
import com.echothree.model.data.associate.server.factory.AssociateProgramFactory;
import com.echothree.model.data.associate.server.factory.AssociateReferralDetailFactory;
import com.echothree.model.data.associate.server.factory.AssociateReferralFactory;
import com.echothree.model.data.associate.server.value.AssociateDetailValue;
import com.echothree.model.data.associate.server.value.AssociatePartyContactMechanismDetailValue;
import com.echothree.model.data.associate.server.value.AssociateProgramDescriptionValue;
import com.echothree.model.data.associate.server.value.AssociateProgramDetailValue;
import com.echothree.model.data.associate.server.value.AssociateReferralDetailValue;
import com.echothree.model.data.contact.server.entity.PartyContactMechanism;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.MimeType;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.sequence.server.entity.Sequence;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.exception.PersistenceDatabaseException;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseModelControl;
import javax.enterprise.inject.spi.CDI;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class AssociateControl
        extends BaseModelControl {
    
    /** Creates a new instance of AssociateControl */
    protected AssociateControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Associate Transfer Caches
    // --------------------------------------------------------------------------------
    
    private AssociateTransferCaches associateTransferCaches;
    
    public AssociateTransferCaches getAssociateTransferCaches() {
        if(associateTransferCaches == null) {
            associateTransferCaches = CDI.current().select(AssociateTransferCaches.class).get();
        }
        
        return associateTransferCaches;
    }
    
    // --------------------------------------------------------------------------------
    //   Associate Programs
    // --------------------------------------------------------------------------------
    
    public AssociateProgram createAssociateProgram(String associateProgramName, Sequence associateSequence,
            Sequence associateContactMechanismSequence, Sequence associateReferralSequence, Integer itemIndirectSalePercent,
            Integer itemDirectSalePercent, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultAssociateProgram = getDefaultAssociateProgram();
        var defaultFound = defaultAssociateProgram != null;
        
        if(defaultFound && isDefault) {
            var defaultAssociateProgramDetailValue = getDefaultAssociateProgramDetailValueForUpdate();
            
            defaultAssociateProgramDetailValue.setIsDefault(false);
            updateAssociateProgramFromValue(defaultAssociateProgramDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var associateProgram = AssociateProgramFactory.getInstance().create();
        var associateProgramDetail = AssociateProgramDetailFactory.getInstance().create(associateProgram,
                associateProgramName, associateSequence, associateContactMechanismSequence, associateReferralSequence,
                itemIndirectSalePercent, itemDirectSalePercent, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        associateProgram = AssociateProgramFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                associateProgram.getPrimaryKey());
        associateProgram.setActiveDetail(associateProgramDetail);
        associateProgram.setLastDetail(associateProgramDetail);
        associateProgram.store();
        
        sendEvent(associateProgram.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return associateProgram;
    }
    
    private AssociateProgram getAssociateProgramByName(String associateProgramName, EntityPermission entityPermission) {
        AssociateProgram associateProgram;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM associateprograms, associateprogramdetails " +
                        "WHERE ascprgm_activedetailid = ascprgmdt_associateprogramdetailid AND ascprgmdt_associateprogramname = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM associateprograms, associateprogramdetails " +
                        "WHERE ascprgm_activedetailid = ascprgmdt_associateprogramdetailid AND ascprgmdt_associateprogramname = ? " +
                        "FOR UPDATE";
            }

            var ps = AssociateProgramFactory.getInstance().prepareStatement(query);
            
            ps.setString(1, associateProgramName);
            
            associateProgram = AssociateProgramFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return associateProgram;
    }
    
    public AssociateProgram getAssociateProgramByName(String associateProgramName) {
        return getAssociateProgramByName(associateProgramName, EntityPermission.READ_ONLY);
    }
    
    public AssociateProgram getAssociateProgramByNameForUpdate(String associateProgramName) {
        return getAssociateProgramByName(associateProgramName, EntityPermission.READ_WRITE);
    }
    
    public AssociateProgramDetailValue getAssociateProgramDetailValueForUpdate(AssociateProgram associateProgram) {
        return associateProgram == null? null: associateProgram.getLastDetailForUpdate().getAssociateProgramDetailValue().clone();
    }
    
    public AssociateProgramDetailValue getAssociateProgramDetailValueByNameForUpdate(String associateProgramName) {
        return getAssociateProgramDetailValueForUpdate(getAssociateProgramByNameForUpdate(associateProgramName));
    }
    
    private AssociateProgram getDefaultAssociateProgram(EntityPermission entityPermission) {
        String query = null;
        
        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ " +
                    "FROM associateprograms, associateprogramdetails " +
                    "WHERE ascprgm_activedetailid = ascprgmdt_associateprogramdetailid AND ascprgmdt_isdefault = 1";
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ " +
                    "FROM associateprograms, associateprogramdetails " +
                    "WHERE ascprgm_activedetailid = ascprgmdt_associateprogramdetailid AND ascprgmdt_isdefault = 1 " +
                    "FOR UPDATE";
        }

        var ps = AssociateProgramFactory.getInstance().prepareStatement(query);
        
        return AssociateProgramFactory.getInstance().getEntityFromQuery(entityPermission, ps);
    }
    
    public AssociateProgram getDefaultAssociateProgram() {
        return getDefaultAssociateProgram(EntityPermission.READ_ONLY);
    }
    
    public AssociateProgram getDefaultAssociateProgramForUpdate() {
        return getDefaultAssociateProgram(EntityPermission.READ_WRITE);
    }
    
    public AssociateProgramDetailValue getDefaultAssociateProgramDetailValueForUpdate() {
        return getDefaultAssociateProgram(EntityPermission.READ_WRITE).getLastDetailForUpdate().getAssociateProgramDetailValue();
    }
    
    private List<AssociateProgram> getAssociatePrograms(EntityPermission entityPermission) {
        String query = null;
        
        if(entityPermission.equals(EntityPermission.READ_ONLY)) {
            query = "SELECT _ALL_ " +
                    "FROM associateprograms, associateprogramdetails " +
                    "WHERE ascprgm_activedetailid = ascprgmdt_associateprogramdetailid " +
                    "ORDER BY ascprgmdt_sortorder, ascprgmdt_associateprogramname";
        } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
            query = "SELECT _ALL_ " +
                    "FROM associateprograms, associateprogramdetails " +
                    "WHERE ascprgm_activedetailid = ascprgmdt_associateprogramdetailid " +
                    "FOR UPDATE";
        }

        var ps = AssociateProgramFactory.getInstance().prepareStatement(query);
        
        return AssociateProgramFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
    }
    
    public List<AssociateProgram> getAssociatePrograms() {
        return getAssociatePrograms(EntityPermission.READ_ONLY);
    }
    
    public List<AssociateProgram> getAssociateProgramsForUpdate() {
        return getAssociatePrograms(EntityPermission.READ_WRITE);
    }
    
    public AssociateProgramChoicesBean getAssociateProgramChoices(String defaultAssociateProgramChoice, Language language, boolean allowNullChoice) {
        var associatePrograms = getAssociatePrograms();
        var size = associatePrograms.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultAssociateProgramChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var associateProgram : associatePrograms) {
            var associateProgramDetail = associateProgram.getLastDetail();
            
            var label = getBestAssociateProgramDescription(associateProgram, language);
            var value = associateProgramDetail.getAssociateProgramName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultAssociateProgramChoice != null && defaultAssociateProgramChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && associateProgramDetail.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new AssociateProgramChoicesBean(labels, values, defaultValue);
    }
    
    public AssociateProgramTransfer getAssociateProgramTransfer(UserVisit userVisit, AssociateProgram associateProgram) {
        return getAssociateTransferCaches().getAssociateProgramTransferCache().getTransfer(userVisit, associateProgram);
    }
    
    public List<AssociateProgramTransfer> getAssociateProgramTransfers(UserVisit userVisit) {
        var associatePrograms = getAssociatePrograms();
        List<AssociateProgramTransfer> associateProgramTransfers = new ArrayList<>(associatePrograms.size());
        var associateProgramTransferCache = getAssociateTransferCaches().getAssociateProgramTransferCache();
        
        associatePrograms.forEach((associateProgram) ->
                associateProgramTransfers.add(associateProgramTransferCache.getTransfer(userVisit, associateProgram))
        );
        
        return associateProgramTransfers;
    }
    
    private void updateAssociateProgramFromValue(AssociateProgramDetailValue associateProgramDetailValue, boolean checkDefault, BasePK updatedBy) {
        var associateProgram = AssociateProgramFactory.getInstance().getEntityFromPK(session,
                EntityPermission.READ_WRITE, associateProgramDetailValue.getAssociateProgramPK());
        var associateProgramDetail = associateProgram.getActiveDetailForUpdate();
        
        associateProgramDetail.setThruTime(session.START_TIME_LONG);
        associateProgramDetail.store();

        var associateProgramPK = associateProgramDetail.getAssociateProgramPK();
        var associateProgramName = associateProgramDetailValue.getAssociateProgramName();
        var associateSequencePK = associateProgramDetailValue.getAssociateSequencePK();
        var associatePartyContactMechanismSequencePK = associateProgramDetailValue.getAssociatePartyContactMechanismSequencePK();
        var associateReferralSequencePK = associateProgramDetailValue.getAssociateReferralSequencePK();
        var itemIndirectSalePercent = associateProgramDetailValue.getItemIndirectSalePercent();
        var itemDirectSalePercent = associateProgramDetailValue.getItemDirectSalePercent();
        var isDefault = associateProgramDetailValue.getIsDefault();
        var sortOrder = associateProgramDetailValue.getSortOrder();
        
        if(checkDefault) {
            var defaultAssociateProgram = getDefaultAssociateProgram();
            var defaultFound = defaultAssociateProgram != null && !defaultAssociateProgram.equals(associateProgram);
            
            if(isDefault && defaultFound) {
                // If I'm the default, and a default already existed...
                var defaultAssociateProgramDetailValue = getDefaultAssociateProgramDetailValueForUpdate();
                
                defaultAssociateProgramDetailValue.setIsDefault(false);
                updateAssociateProgramFromValue(defaultAssociateProgramDetailValue, false, updatedBy);
            } else if(!isDefault && !defaultFound) {
                // If I'm not the default, and no other default exists...
                isDefault = true;
            }
        }
        
        associateProgramDetail = AssociateProgramDetailFactory.getInstance().create(associateProgramPK,
                associateProgramName, associateSequencePK, associatePartyContactMechanismSequencePK, associateReferralSequencePK,
                itemIndirectSalePercent, itemDirectSalePercent, isDefault, sortOrder, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);
        
        associateProgram.setActiveDetail(associateProgramDetail);
        associateProgram.setLastDetail(associateProgramDetail);
        associateProgram.store();
        
        sendEvent(associateProgramPK, EventTypes.MODIFY, null, null, updatedBy);
    }
    
    public void updateAssociateProgramFromValue(AssociateProgramDetailValue associateProgramDetailValue, BasePK updatedBy) {
        updateAssociateProgramFromValue(associateProgramDetailValue, true, updatedBy);
    }
    
    public void deleteAssociateProgram(AssociateProgram associateProgram, BasePK deletedBy) {
        deleteAssociateProgramDescriptionsByAssociateProgram(associateProgram, deletedBy);
        deleteAssociatesByAssociateProgram(associateProgram, deletedBy);

        var associateProgramDetail = associateProgram.getLastDetailForUpdate();
        associateProgramDetail.setThruTime(session.START_TIME_LONG);
        associateProgram.setActiveDetail(null);
        associateProgram.store();
        
        // Check for default, and pick one if necessary
        var defaultAssociateProgram = getDefaultAssociateProgram();
        if(defaultAssociateProgram == null) {
            var associatePrograms = getAssociateProgramsForUpdate();
            
            if(!associatePrograms.isEmpty()) {
                var iter = associatePrograms.iterator();
                if(iter.hasNext()) {
                    defaultAssociateProgram = iter.next();
                }
                var associateProgramDetailValue = Objects.requireNonNull(defaultAssociateProgram).getLastDetailForUpdate().getAssociateProgramDetailValue().clone();
                
                associateProgramDetailValue.setIsDefault(true);
                updateAssociateProgramFromValue(associateProgramDetailValue, false, deletedBy);
            }
        }
        
        sendEvent(associateProgram.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Associate Program Descriptions
    // --------------------------------------------------------------------------------
    
    public AssociateProgramDescription createAssociateProgramDescription(AssociateProgram associateProgram, Language language, String description,
            BasePK createdBy) {
        var associateProgramDescription = AssociateProgramDescriptionFactory.getInstance().create(associateProgram,
                language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(associateProgram.getPrimaryKey(), EventTypes.MODIFY, associateProgramDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return associateProgramDescription;
    }
    
    private AssociateProgramDescription getAssociateProgramDescription(AssociateProgram associateProgram, Language language, EntityPermission entityPermission) {
        AssociateProgramDescription associateProgramDescription;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM associateprogramdescriptions " +
                        "WHERE ascprgmd_ascprgm_associateprogramid = ? AND ascprgmd_lang_languageid = ? AND ascprgmd_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM associateprogramdescriptions " +
                        "WHERE ascprgmd_ascprgm_associateprogramid = ? AND ascprgmd_lang_languageid = ? AND ascprgmd_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = AssociateProgramDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, associateProgram.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            associateProgramDescription = AssociateProgramDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return associateProgramDescription;
    }
    
    public AssociateProgramDescription getAssociateProgramDescription(AssociateProgram associateProgram, Language language) {
        return getAssociateProgramDescription(associateProgram, language, EntityPermission.READ_ONLY);
    }
    
    public AssociateProgramDescription getAssociateProgramDescriptionForUpdate(AssociateProgram associateProgram, Language language) {
        return getAssociateProgramDescription(associateProgram, language, EntityPermission.READ_WRITE);
    }
    
    public AssociateProgramDescriptionValue getAssociateProgramDescriptionValue(AssociateProgramDescription associateProgramDescription) {
        return associateProgramDescription == null? null: associateProgramDescription.getAssociateProgramDescriptionValue().clone();
    }
    
    public AssociateProgramDescriptionValue getAssociateProgramDescriptionValueForUpdate(AssociateProgram associateProgram, Language language) {
        return getAssociateProgramDescriptionValue(getAssociateProgramDescriptionForUpdate(associateProgram, language));
    }
    
    private List<AssociateProgramDescription> getAssociateProgramDescriptionsByAssociateProgram(AssociateProgram associateProgram, EntityPermission entityPermission) {
        List<AssociateProgramDescription> associateProgramDescriptions;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM associateprogramdescriptions, languages " +
                        "WHERE ascprgmd_ascprgm_associateprogramid = ? AND ascprgmd_thrutime = ? AND ascprgmd_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM associateprogramdescriptions " +
                        "WHERE ascprgmd_ascprgm_associateprogramid = ? AND ascprgmd_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = AssociateProgramDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, associateProgram.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            associateProgramDescriptions = AssociateProgramDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return associateProgramDescriptions;
    }
    
    public List<AssociateProgramDescription> getAssociateProgramDescriptionsByAssociateProgram(AssociateProgram associateProgram) {
        return getAssociateProgramDescriptionsByAssociateProgram(associateProgram, EntityPermission.READ_ONLY);
    }
    
    public List<AssociateProgramDescription> getAssociateProgramDescriptionsByAssociateProgramForUpdate(AssociateProgram associateProgram) {
        return getAssociateProgramDescriptionsByAssociateProgram(associateProgram, EntityPermission.READ_WRITE);
    }
    
    public String getBestAssociateProgramDescription(AssociateProgram associateProgram, Language language) {
        String description;
        var associateProgramDescription = getAssociateProgramDescription(associateProgram, language);
        
        if(associateProgramDescription == null && !language.getIsDefault()) {
            associateProgramDescription = getAssociateProgramDescription(associateProgram, partyControl.getDefaultLanguage());
        }
        
        if(associateProgramDescription == null) {
            description = associateProgram.getLastDetail().getAssociateProgramName();
        } else {
            description = associateProgramDescription.getDescription();
        }
        
        return description;
    }
    
    public AssociateProgramDescriptionTransfer getAssociateProgramDescriptionTransfer(UserVisit userVisit, AssociateProgramDescription associateProgramDescription) {
        return getAssociateTransferCaches().getAssociateProgramDescriptionTransferCache().getTransfer(userVisit, associateProgramDescription);
    }
    
    public List<AssociateProgramDescriptionTransfer> getAssociateProgramDescriptionTransfersByAssociateProgram(UserVisit userVisit, AssociateProgram associateProgram) {
        var associateProgramDescriptions = getAssociateProgramDescriptionsByAssociateProgram(associateProgram);
        List<AssociateProgramDescriptionTransfer> associateProgramDescriptionTransfers = new ArrayList<>(associateProgramDescriptions.size());
        var associateProgramDescriptionTransferCache = getAssociateTransferCaches().getAssociateProgramDescriptionTransferCache();
        
        associateProgramDescriptions.forEach((associateProgramDescription) ->
                associateProgramDescriptionTransfers.add(associateProgramDescriptionTransferCache.getTransfer(userVisit, associateProgramDescription))
        );
        
        return associateProgramDescriptionTransfers;
    }
    
    public void updateAssociateProgramDescriptionFromValue(AssociateProgramDescriptionValue associateProgramDescriptionValue, BasePK updatedBy) {
        if(associateProgramDescriptionValue.hasBeenModified()) {
            var associateProgramDescription = AssociateProgramDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     associateProgramDescriptionValue.getPrimaryKey());
            
            associateProgramDescription.setThruTime(session.START_TIME_LONG);
            associateProgramDescription.store();

            var associateProgram = associateProgramDescription.getAssociateProgram();
            var language = associateProgramDescription.getLanguage();
            var description = associateProgramDescriptionValue.getDescription();
            
            associateProgramDescription = AssociateProgramDescriptionFactory.getInstance().create(associateProgram, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(associateProgram.getPrimaryKey(), EventTypes.MODIFY, associateProgramDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteAssociateProgramDescription(AssociateProgramDescription associateProgramDescription, BasePK deletedBy) {
        associateProgramDescription.setThruTime(session.START_TIME_LONG);
        
        sendEvent(associateProgramDescription.getAssociateProgramPK(), EventTypes.MODIFY, associateProgramDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
        
    }
    
    public void deleteAssociateProgramDescriptionsByAssociateProgram(AssociateProgram associateProgram, BasePK deletedBy) {
        var associateProgramDescriptions = getAssociateProgramDescriptionsByAssociateProgramForUpdate(associateProgram);
        
        associateProgramDescriptions.forEach((associateProgramDescription) -> 
                deleteAssociateProgramDescription(associateProgramDescription, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Associates
    // --------------------------------------------------------------------------------
    
    public Associate createAssociate(AssociateProgram associateProgram, String associateName, Party party, String description,
            MimeType summaryMimeType, String summary, BasePK createdBy) {
        var associate = AssociateFactory.getInstance().create();
        var associateDetail = AssociateDetailFactory.getInstance().create(associate, associateProgram,
                associateName, party, description, summaryMimeType, summary, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        associate = AssociateFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                associate.getPrimaryKey());
        associate.setActiveDetail(associateDetail);
        associate.setLastDetail(associateDetail);
        associate.store();
        
        sendEvent(associate.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return associate;
    }
    
    private Associate getAssociateByName(AssociateProgram associateProgram, String associateName, EntityPermission entityPermission) {
        Associate associate;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM associates, associatedetails " +
                        "WHERE asc_activedetailid = ascdt_associatedetailid AND ascdt_ascprgm_associateprogramid = ? AND ascdt_associatename = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM associates, associatedetails " +
                        "WHERE asc_activedetailid = ascdt_associatedetailid AND ascdt_ascprgm_associateprogramid = ? AND ascdt_associatename = ? " +
                        "FOR UPDATE";
            }

            var ps = AssociateFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, associateProgram.getPrimaryKey().getEntityId());
            ps.setString(2, associateName);
            
            associate = AssociateFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return associate;
    }
    
    public Associate getAssociateByName(AssociateProgram associateProgram, String associateName) {
        return getAssociateByName(associateProgram, associateName, EntityPermission.READ_ONLY);
    }
    
    public Associate getAssociateByNameForUpdate(AssociateProgram associateProgram, String associateName) {
        return getAssociateByName(associateProgram, associateName, EntityPermission.READ_WRITE);
    }
    
    public AssociateDetailValue getAssociateDetailValueForUpdate(Associate associate) {
        return associate == null? null: associate.getLastDetailForUpdate().getAssociateDetailValue().clone();
    }
    
    public AssociateDetailValue getAssociateDetailValueByNameForUpdate(AssociateProgram associateProgram, String associateName) {
        return getAssociateDetailValueForUpdate(getAssociateByNameForUpdate(associateProgram, associateName));
    }
    
    private List<Associate> getAssociates(AssociateProgram associateProgram, EntityPermission entityPermission) {
        List<Associate> associates;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM associates, associatedetails " +
                        "WHERE asc_activedetailid = ascdt_associatedetailid AND ascdt_ascprgm_associateprogramid = ? " +
                        "ORDER BY ascdt_associatename";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM associates, associatedetails " +
                        "WHERE asc_activedetailid = ascdt_associatedetailid AND ascdt_ascprgm_associateprogramid = ? " +
                        "FOR UPDATE";
            }

            var ps = AssociateFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, associateProgram.getPrimaryKey().getEntityId());
            
            associates = AssociateFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return associates;
    }
    
    public List<Associate> getAssociates(AssociateProgram associateProgram) {
        return getAssociates(associateProgram, EntityPermission.READ_ONLY);
    }
    
    public List<Associate> getAssociatesForUpdate(AssociateProgram associateProgram) {
        return getAssociates(associateProgram, EntityPermission.READ_WRITE);
    }
    
    public AssociateChoicesBean getAssociateChoices(AssociateProgram associateProgram, String defaultAssociateChoice,
            Language language, boolean allowNullChoice) {
        var associates = getAssociates(associateProgram);
        var size = associates.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultAssociateChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var associate : associates) {
            var associateDetail = associate.getLastDetail();
            
            var label = associateDetail.getDescription();
            var value = associateDetail.getAssociateName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultAssociateChoice != null && defaultAssociateChoice.equals(value);
            if(usingDefaultChoice || defaultValue == null) {
                defaultValue = value;
            }
        }
        
        return new AssociateChoicesBean(labels, values, defaultValue);
    }
    
    public AssociateTransfer getAssociateTransfer(UserVisit userVisit, Associate associate) {
        return getAssociateTransferCaches().getAssociateTransferCache().getTransfer(userVisit, associate);
    }
    
    public List<AssociateTransfer> getAssociateTransfers(List<Associate> associates, UserVisit userVisit) {
        List<AssociateTransfer> associateTransfers = new ArrayList<>(associates.size());
        var associateTransferCache = getAssociateTransferCaches().getAssociateTransferCache();
        
        associates.forEach((associate) ->
                associateTransfers.add(associateTransferCache.getTransfer(userVisit, associate))
        );
        
        return associateTransfers;
    }
    
    public List<AssociateTransfer> getAssociateTransfersByAssociateProgram(AssociateProgram associateProgram, UserVisit userVisit) {
        return getAssociateTransfers(getAssociates(associateProgram), userVisit);
    }
    
    public void updateAssociateFromValue(AssociateDetailValue associateDetailValue, boolean checkDefault, BasePK updatedBy) {
        var associate = AssociateFactory.getInstance().getEntityFromPK(session,
                EntityPermission.READ_WRITE, associateDetailValue.getAssociatePK());
        var associateDetail = associate.getActiveDetailForUpdate();
        
        associateDetail.setThruTime(session.START_TIME_LONG);
        associateDetail.store();

        var associatePK = associateDetail.getAssociatePK(); // Not updated
        var associateProgramPK = associateDetail.getAssociateProgramPK(); // Not updated
        var associateName = associateDetailValue.getAssociateName();
        var partyPK = associateDetail.getPartyPK(); // Not updated
        var description = associateDetailValue.getDescription();
        var summaryMimeTypePK = associateDetailValue.getSummaryMimeTypePK();
        var summary = associateDetailValue.getSummary();
        
        associateDetail = AssociateDetailFactory.getInstance().create(associatePK, associateProgramPK, associateName,
                partyPK, description, summaryMimeTypePK, summary, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        associate.setActiveDetail(associateDetail);
        associate.setLastDetail(associateDetail);
        associate.store();
        
        sendEvent(associatePK, EventTypes.MODIFY, null, null, updatedBy);
    }
    
    public void deleteAssociate(Associate associate, BasePK deletedBy) {
        deleteAssociatePartyContactMechanismsByAssociate(associate, deletedBy);
        deleteAssociateReferralsByAssociate(associate, deletedBy);

        var associateDetail = associate.getLastDetailForUpdate();
        associateDetail.setThruTime(session.START_TIME_LONG);
        associate.setActiveDetail(null);
        associate.store();
        
        sendEvent(associate.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    public void deleteAssociates(List<Associate> associates, BasePK deletedBy) {
        associates.forEach((associate) -> 
                deleteAssociate(associate, deletedBy)
        );
    }
    
    public void deleteAssociatesByAssociateProgram(AssociateProgram associateProgram, BasePK deletedBy) {
        deleteAssociates(getAssociatesForUpdate(associateProgram), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Associate Contact Mechanisms
    // --------------------------------------------------------------------------------
    
    public AssociatePartyContactMechanism createAssociatePartyContactMechanism(Associate associate, String associatePartyContactMechanismName,
            PartyContactMechanism partyContactMechanism, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultAssociatePartyContactMechanism = getDefaultAssociatePartyContactMechanism(associate);
        var defaultFound = defaultAssociatePartyContactMechanism != null;
        
        if(defaultFound && isDefault) {
            var defaultAssociatePartyContactMechanismDetailValue = getDefaultAssociatePartyContactMechanismDetailValueForUpdate(associate);
            
            defaultAssociatePartyContactMechanismDetailValue.setIsDefault(false);
            updateAssociatePartyContactMechanismFromValue(defaultAssociatePartyContactMechanismDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var associatePartyContactMechanism = AssociatePartyContactMechanismFactory.getInstance().create();
        var associatePartyContactMechanismDetail = AssociatePartyContactMechanismDetailFactory.getInstance().create(session,
                associatePartyContactMechanism, associate, associatePartyContactMechanismName, partyContactMechanism, isDefault, sortOrder,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        associatePartyContactMechanism = AssociatePartyContactMechanismFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                associatePartyContactMechanism.getPrimaryKey());
        associatePartyContactMechanism.setActiveDetail(associatePartyContactMechanismDetail);
        associatePartyContactMechanism.setLastDetail(associatePartyContactMechanismDetail);
        associatePartyContactMechanism.store();
        
        sendEvent(associatePartyContactMechanism.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return associatePartyContactMechanism;
    }
    
    private AssociatePartyContactMechanism getAssociatePartyContactMechanismByName(Associate associate, String associatePartyContactMechanismName, EntityPermission entityPermission) {
        AssociatePartyContactMechanism associatePartyContactMechanism;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM associatepartycontactmechanisms, associatepartycontactmechanismdetails " +
                        "WHERE ascpcm_activedetailid = ascpcmdt_associatepartycontactmechanismdetailid " +
                        "AND ascpcmdt_asc_associateid = ? AND ascpcmdt_associatepartycontactmechanismname = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM associatepartycontactmechanisms, associatepartycontactmechanismdetails " +
                        "WHERE ascpcm_activedetailid = ascpcmdt_associatepartycontactmechanismdetailid " +
                        "AND ascpcmdt_asc_associateid = ? AND ascpcmdt_associatepartycontactmechanismname = ? " +
                        "FOR UPDATE";
            }

            var ps = AssociatePartyContactMechanismFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, associate.getPrimaryKey().getEntityId());
            ps.setString(2, associatePartyContactMechanismName);
            
            associatePartyContactMechanism = AssociatePartyContactMechanismFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return associatePartyContactMechanism;
    }
    
    public AssociatePartyContactMechanism getAssociatePartyContactMechanismByName(Associate associate, String associatePartyContactMechanismName) {
        return getAssociatePartyContactMechanismByName(associate, associatePartyContactMechanismName, EntityPermission.READ_ONLY);
    }
    
    public AssociatePartyContactMechanism getAssociatePartyContactMechanismByNameForUpdate(Associate associate, String associatePartyContactMechanismName) {
        return getAssociatePartyContactMechanismByName(associate, associatePartyContactMechanismName, EntityPermission.READ_WRITE);
    }
    
    public AssociatePartyContactMechanismDetailValue getAssociatePartyContactMechanismDetailValueForUpdate(AssociatePartyContactMechanism associatePartyContactMechanism) {
        return associatePartyContactMechanism == null? null: associatePartyContactMechanism.getLastDetailForUpdate().getAssociatePartyContactMechanismDetailValue().clone();
    }
    
    public AssociatePartyContactMechanismDetailValue getAssociatePartyContactMechanismDetailValueByNameForUpdate(Associate associate, String associatePartyContactMechanismName) {
        return getAssociatePartyContactMechanismDetailValueForUpdate(getAssociatePartyContactMechanismByNameForUpdate(associate, associatePartyContactMechanismName));
    }
    
    private AssociatePartyContactMechanism getDefaultAssociatePartyContactMechanism(Associate associate, EntityPermission entityPermission) {
        AssociatePartyContactMechanism associatePartyContactMechanism;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM associatepartycontactmechanisms, associatepartycontactmechanismdetails " +
                        "WHERE ascpcm_activedetailid = ascpcmdt_associatepartycontactmechanismdetailid " +
                        "AND ascpcmdt_asc_associateid = ? AND ascpcmdt_isdefault = 1";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM associatepartycontactmechanisms, associatepartycontactmechanismdetails " +
                        "WHERE ascpcm_activedetailid = ascpcmdt_associatepartycontactmechanismdetailid " +
                        "AND ascpcmdt_asc_associateid = ? AND ascpcmdt_isdefault = 1 " +
                        "FOR UPDATE";
            }

            var ps = AssociatePartyContactMechanismFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, associate.getPrimaryKey().getEntityId());
            
            associatePartyContactMechanism = AssociatePartyContactMechanismFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return associatePartyContactMechanism;
    }
    
    public AssociatePartyContactMechanism getDefaultAssociatePartyContactMechanism(Associate associate) {
        return getDefaultAssociatePartyContactMechanism(associate, EntityPermission.READ_ONLY);
    }
    
    public AssociatePartyContactMechanism getDefaultAssociatePartyContactMechanismForUpdate(Associate associate) {
        return getDefaultAssociatePartyContactMechanism(associate, EntityPermission.READ_WRITE);
    }
    
    public AssociatePartyContactMechanismDetailValue getDefaultAssociatePartyContactMechanismDetailValueForUpdate(Associate associate) {
        return getDefaultAssociatePartyContactMechanism(associate, EntityPermission.READ_WRITE).getLastDetailForUpdate().getAssociatePartyContactMechanismDetailValue();
    }
    
    private List<AssociatePartyContactMechanism> getAssociatePartyContactMechanismsByAssociate(Associate associate, EntityPermission entityPermission) {
        List<AssociatePartyContactMechanism> associatePartyContactMechanisms;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM associatepartycontactmechanisms, associatepartycontactmechanismdetails " +
                        "WHERE ascpcm_activedetailid = ascpcmdt_associatepartycontactmechanismdetailid " +
                        "AND ascpcmdt_asc_associateid = ? " +
                        "ORDER BY ascpcmdt_sortorder, ascpcmdt_associatepartycontactmechanismname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM associatepartycontactmechanisms, associatepartycontactmechanismdetails " +
                        "WHERE ascpcm_activedetailid = ascpcmdt_associatepartycontactmechanismdetailid " +
                        "AND ascpcmdt_asc_associateid = ? " +
                        "FOR UPDATE";
            }

            var ps = AssociatePartyContactMechanismFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, associate.getPrimaryKey().getEntityId());
            
            associatePartyContactMechanisms = AssociatePartyContactMechanismFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return associatePartyContactMechanisms;
    }
    
    public List<AssociatePartyContactMechanism> getAssociatePartyContactMechanismsByAssociate(Associate associate) {
        return getAssociatePartyContactMechanismsByAssociate(associate, EntityPermission.READ_ONLY);
    }
    
    public List<AssociatePartyContactMechanism> getAssociatePartyContactMechanismsByAssociateForUpdate(Associate associate) {
        return getAssociatePartyContactMechanismsByAssociate(associate, EntityPermission.READ_WRITE);
    }
    
    private List<AssociatePartyContactMechanism> getAssociatePartyContactMechanismsByPartyContactMechanism(PartyContactMechanism partyContactMechanism, EntityPermission entityPermission) {
        List<AssociatePartyContactMechanism> associatePartyContactMechanisms;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM associatepartycontactmechanisms, associatepartycontactmechanismdetails " +
                        "WHERE ascpcm_activedetailid = ascpcmdt_associatepartycontactmechanismdetailid " +
                        "AND ascpcmdt_pcm_partycontactmechanismid = ? " +
                        "ORDER BY ascpcmdt_sortorder, ascpcmdt_associatepartycontactmechanismname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM associatepartycontactmechanisms, associatepartycontactmechanismdetails " +
                        "WHERE ascpcm_activedetailid = ascpcmdt_associatepartycontactmechanismdetailid " +
                        "AND ascpcmdt_pcm_partycontactmechanismid = ? " +
                        "FOR UPDATE";
            }

            var ps = AssociatePartyContactMechanismFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, partyContactMechanism.getPrimaryKey().getEntityId());
            
            associatePartyContactMechanisms = AssociatePartyContactMechanismFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return associatePartyContactMechanisms;
    }
    
    public List<AssociatePartyContactMechanism> getAssociatePartyContactMechanismsByPartyContactMechanism(PartyContactMechanism partyContactMechanism) {
        return getAssociatePartyContactMechanismsByPartyContactMechanism(partyContactMechanism, EntityPermission.READ_ONLY);
    }
    
    public List<AssociatePartyContactMechanism> getAssociatePartyContactMechanismsByPartyContactMechanismForUpdate(PartyContactMechanism partyContactMechanism) {
        return getAssociatePartyContactMechanismsByPartyContactMechanism(partyContactMechanism, EntityPermission.READ_WRITE);
    }
    
    public AssociatePartyContactMechanismChoicesBean getAssociatePartyContactMechanismChoices(Associate associate,
            String defaultAssociatePartyContactMechanismChoice, Language language, boolean allowNullChoice) {
        var associatePartyContactMechanisms = getAssociatePartyContactMechanismsByAssociate(associate);
        var size = associatePartyContactMechanisms.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultAssociatePartyContactMechanismChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var associatePartyContactMechanism : associatePartyContactMechanisms) {
            var associatePartyContactMechanismDetail = associatePartyContactMechanism.getLastDetail();
            
            var label = associatePartyContactMechanismDetail.getAssociatePartyContactMechanismName();
            var value = label;
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultAssociatePartyContactMechanismChoice != null && defaultAssociatePartyContactMechanismChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && associatePartyContactMechanismDetail.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new AssociatePartyContactMechanismChoicesBean(labels, values, defaultValue);
    }
    
    public AssociatePartyContactMechanismTransfer getAssociatePartyContactMechanismTransfer(UserVisit userVisit, AssociatePartyContactMechanism associatePartyContactMechanism) {
        return getAssociateTransferCaches().getAssociatePartyContactMechanismTransferCache().getTransfer(userVisit, associatePartyContactMechanism);
    }
    
    public List<AssociatePartyContactMechanismTransfer> getAssociatePartyContactMechanismTransfers(List<AssociatePartyContactMechanism> associatePartyContactMechanisms, UserVisit userVisit) {
        List<AssociatePartyContactMechanismTransfer> associatePartyContactMechanismTransfers = new ArrayList<>(associatePartyContactMechanisms.size());
        var associatePartyContactMechanismTransferCache = getAssociateTransferCaches().getAssociatePartyContactMechanismTransferCache();
        
        associatePartyContactMechanisms.forEach((associatePartyContactMechanism) ->
                associatePartyContactMechanismTransfers.add(associatePartyContactMechanismTransferCache.getTransfer(userVisit, associatePartyContactMechanism))
        );
        
        return associatePartyContactMechanismTransfers;
    }
    
    public List<AssociatePartyContactMechanismTransfer> getAssociatePartyContactMechanismTransfersByAssociate(Associate associate, UserVisit userVisit) {
        return getAssociatePartyContactMechanismTransfers(getAssociatePartyContactMechanismsByAssociate(associate), userVisit);
    }
    
    public List<AssociatePartyContactMechanismTransfer> getAssociatePartyContactMechanismTransfersByPartyContactMechanism(PartyContactMechanism partyContactMechanism, UserVisit userVisit) {
        return getAssociatePartyContactMechanismTransfers(getAssociatePartyContactMechanismsByPartyContactMechanism(partyContactMechanism), userVisit);
    }
    
    private void updateAssociatePartyContactMechanismFromValue(AssociatePartyContactMechanismDetailValue associatePartyContactMechanismDetailValue,
            boolean checkDefault, BasePK updatedBy) {
        var associatePartyContactMechanism = AssociatePartyContactMechanismFactory.getInstance().getEntityFromPK(session,
                EntityPermission.READ_WRITE, associatePartyContactMechanismDetailValue.getAssociatePartyContactMechanismPK());
        var associatePartyContactMechanismDetail = associatePartyContactMechanism.getActiveDetailForUpdate();
        
        associatePartyContactMechanismDetail.setThruTime(session.START_TIME_LONG);
        associatePartyContactMechanismDetail.store();

        var associatePartyContactMechanismPK = associatePartyContactMechanismDetail.getAssociatePartyContactMechanismPK(); // Not updated
        var associate = associatePartyContactMechanismDetail.getAssociate();
        var associatePK = associate.getPrimaryKey(); // Not updated
        var associatePartyContactMechanismName = associatePartyContactMechanismDetailValue.getAssociatePartyContactMechanismName();
        var partyContactMechanismPK = associatePartyContactMechanismDetailValue.getPartyContactMechanismPK();
        var isDefault = associatePartyContactMechanismDetailValue.getIsDefault();
        var sortOrder = associatePartyContactMechanismDetailValue.getSortOrder();
        
        if(checkDefault) {
            var defaultAssociatePartyContactMechanism = getDefaultAssociatePartyContactMechanism(associate);
            var defaultFound = defaultAssociatePartyContactMechanism != null && !defaultAssociatePartyContactMechanism.equals(associatePartyContactMechanism);
            
            if(isDefault && defaultFound) {
                // If I'm the default, and a default already existed...
                var defaultAssociatePartyContactMechanismDetailValue = getDefaultAssociatePartyContactMechanismDetailValueForUpdate(associate);
                
                defaultAssociatePartyContactMechanismDetailValue.setIsDefault(false);
                updateAssociatePartyContactMechanismFromValue(defaultAssociatePartyContactMechanismDetailValue, false, updatedBy);
            } else if(!isDefault && !defaultFound) {
                // If I'm not the default, and no other default exists...
                isDefault = true;
            }
        }
        
        associatePartyContactMechanismDetail = AssociatePartyContactMechanismDetailFactory.getInstance().create(session,
                associatePartyContactMechanismPK, associatePK, associatePartyContactMechanismName, partyContactMechanismPK, isDefault, sortOrder,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        associatePartyContactMechanism.setActiveDetail(associatePartyContactMechanismDetail);
        associatePartyContactMechanism.setLastDetail(associatePartyContactMechanismDetail);
        associatePartyContactMechanism.store();
        
        sendEvent(associatePartyContactMechanismPK, EventTypes.MODIFY, null, null, updatedBy);
    }
    
    public void updateAssociatePartyContactMechanismFromValue(AssociatePartyContactMechanismDetailValue associatePartyContactMechanismDetailValue, BasePK updatedBy) {
        updateAssociatePartyContactMechanismFromValue(associatePartyContactMechanismDetailValue, true, updatedBy);
    }
    
    public void deleteAssociatePartyContactMechanism(AssociatePartyContactMechanism associatePartyContactMechanism, BasePK deletedBy) {
        deleteAssociateReferralsByAssociatePartyContactMechanism(associatePartyContactMechanism, deletedBy);

        var associatePartyContactMechanismDetail = associatePartyContactMechanism.getLastDetailForUpdate();
        associatePartyContactMechanismDetail.setThruTime(session.START_TIME_LONG);
        associatePartyContactMechanism.setActiveDetail(null);
        associatePartyContactMechanism.store();
        
        // Check for default, and pick one if necessary
        var associate = associatePartyContactMechanismDetail.getAssociate();
        var defaultAssociatePartyContactMechanism = getDefaultAssociatePartyContactMechanism(associate);
        if(defaultAssociatePartyContactMechanism == null) {
            var associatePartyContactMechanisms = getAssociatePartyContactMechanismsByAssociateForUpdate(associate);
            
            if(!associatePartyContactMechanisms.isEmpty()) {
                var iter = associatePartyContactMechanisms.iterator();
                if(iter.hasNext()) {
                    defaultAssociatePartyContactMechanism = iter.next();
                }
                var associatePartyContactMechanismDetailValue = Objects.requireNonNull(defaultAssociatePartyContactMechanism).getLastDetailForUpdate().getAssociatePartyContactMechanismDetailValue().clone();
                
                associatePartyContactMechanismDetailValue.setIsDefault(true);
                updateAssociatePartyContactMechanismFromValue(associatePartyContactMechanismDetailValue, false, deletedBy);
            }
        }
        
        sendEvent(associatePartyContactMechanism.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    public void deleteAssociatePartyContactMechanisms(List<AssociatePartyContactMechanism> associatePartyContactMechanisms, BasePK deletedBy) {
        associatePartyContactMechanisms.forEach((associatePartyContactMechanism) -> 
                deleteAssociatePartyContactMechanism(associatePartyContactMechanism, deletedBy)
        );
    }
    
    public void deleteAssociatePartyContactMechanismsByAssociate(Associate associate, BasePK deletedBy) {
        deleteAssociatePartyContactMechanisms(getAssociatePartyContactMechanismsByAssociate(associate), deletedBy);
    }
    
    public void deleteAssociatePartyContactMechanismsByPartyContactMechanism(PartyContactMechanism partyContactMechanism, BasePK deletedBy) {
        deleteAssociatePartyContactMechanisms(getAssociatePartyContactMechanismsByPartyContactMechanism(partyContactMechanism), deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Associate Referrals
    // --------------------------------------------------------------------------------
    
    public AssociateReferral createAssociateReferral(Associate associate, AssociatePartyContactMechanism associatePartyContactMechanism,
            EntityInstance targetEntityInstance, Long associateReferralTime, BasePK createdBy) {
        var sequenceControl = Session.getModelController(SequenceControl.class);
        var sequence = sequenceControl.getDefaultSequenceUsingNames(SequenceTypes.ASSOCIATE_REFERRAL.name());
        var associateReferralName = SequenceGeneratorLogic.getInstance().getNextSequenceValue(sequence);

        return createAssociateReferral(associateReferralName, associate, associatePartyContactMechanism, targetEntityInstance, associateReferralTime,
                createdBy);
    }
    
    public AssociateReferral createAssociateReferral(String associateReferralName, Associate associate,
            AssociatePartyContactMechanism associatePartyContactMechanism, EntityInstance targetEntityInstance, Long associateReferralTime,
            BasePK createdBy) {
        var associateReferral = AssociateReferralFactory.getInstance().create();
        var associateReferralDetail = AssociateReferralDetailFactory.getInstance().create(session,
                associateReferral, associateReferralName, associate, associatePartyContactMechanism, targetEntityInstance,
                associateReferralTime, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        associateReferral = AssociateReferralFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                associateReferral.getPrimaryKey());
        associateReferral.setActiveDetail(associateReferralDetail);
        associateReferral.setLastDetail(associateReferralDetail);
        associateReferral.store();
        
        sendEvent(associateReferral.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return associateReferral;
    }
    
    private AssociateReferral getAssociateReferralByName(String associateReferralName, EntityPermission entityPermission) {
        AssociateReferral associateReferral;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM associatereferrals, associatereferraldetails " +
                        "WHERE ascrfr_activedetailid = ascrfrdt_associatereferraldetailid " +
                        "AND ascrfrdt_associatereferralname = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM associatereferrals, associatereferraldetails " +
                        "WHERE ascrfr_activedetailid = ascrfrdt_associatereferraldetailid " +
                        "AND ascrfrdt_associatereferralname = ? " +
                        "FOR UPDATE";
            }

            var ps = AssociateReferralFactory.getInstance().prepareStatement(query);
            
            ps.setString(1, associateReferralName);
            
            associateReferral = AssociateReferralFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return associateReferral;
    }
    
    public AssociateReferral getAssociateReferralByName(String associateReferralName) {
        return getAssociateReferralByName(associateReferralName, EntityPermission.READ_ONLY);
    }
    
    public AssociateReferral getAssociateReferralByNameForUpdate(String associateReferralName) {
        return getAssociateReferralByName(associateReferralName, EntityPermission.READ_WRITE);
    }
    
    public AssociateReferralDetailValue getAssociateReferralDetailValueForUpdate(AssociateReferral associateReferral) {
        return associateReferral == null? null: associateReferral.getLastDetailForUpdate().getAssociateReferralDetailValue().clone();
    }
    
    public AssociateReferralDetailValue getAssociateReferralDetailValueByNameForUpdate(String associateReferralName) {
        return getAssociateReferralDetailValueForUpdate(getAssociateReferralByNameForUpdate(associateReferralName));
    }
    
    private List<AssociateReferral> getAssociateReferralsByAssociate(Associate associate, EntityPermission entityPermission) {
        List<AssociateReferral> associateReferrals;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM associatereferrals, associatereferraldetails " +
                        "WHERE ascrfr_activedetailid = ascrfrdt_associatereferraldetailid " +
                        "AND ascrfrdt_asc_associateid = ? " +
                        "ORDER BY ascrfrdt_associatereferralname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM associatereferrals, associatereferraldetails " +
                        "WHERE ascrfr_activedetailid = ascrfrdt_associatereferraldetailid " +
                        "AND ascrfrdt_asc_associateid = ? " +
                        "FOR UPDATE";
            }

            var ps = AssociateReferralFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, associate.getPrimaryKey().getEntityId());
            
            associateReferrals = AssociateReferralFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return associateReferrals;
    }
    
    public List<AssociateReferral> getAssociateReferralsByAssociate(Associate associate) {
        return getAssociateReferralsByAssociate(associate, EntityPermission.READ_ONLY);
    }
    
    public List<AssociateReferral> getAssociateReferralsByAssociateForUpdate(Associate associate) {
        return getAssociateReferralsByAssociate(associate, EntityPermission.READ_WRITE);
    }
    
    private List<AssociateReferral> getAssociateReferralsByAssociatePartyContactMechanism(AssociatePartyContactMechanism associatePartyContactMechanism,
            EntityPermission entityPermission) {
        List<AssociateReferral> associateReferrals;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM associatereferrals, associatereferraldetails " +
                        "WHERE ascrfr_activedetailid = ascrfrdt_associatereferraldetailid " +
                        "AND ascrfrdt_ascpcm_associatepartycontactmechanismid = ? " +
                        "ORDER BY ascrfrdt_associatereferralname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM associatereferrals, associatereferraldetails " +
                        "WHERE ascrfr_activedetailid = ascrfrdt_associatereferraldetailid " +
                        "AND ascrfrdt_ascpcm_associatepartycontactmechanismid = ? " +
                        "FOR UPDATE";
            }

            var ps = AssociateReferralFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, associatePartyContactMechanism.getPrimaryKey().getEntityId());
            
            associateReferrals = AssociateReferralFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return associateReferrals;
    }
    
    public List<AssociateReferral> getAssociateReferralsByAssociatePartyContactMechanism(AssociatePartyContactMechanism associatePartyContactMechanism) {
        return getAssociateReferralsByAssociatePartyContactMechanism(associatePartyContactMechanism, EntityPermission.READ_ONLY);
    }
    
    public List<AssociateReferral> getAssociateReferralsByAssociatePartyContactMechanismForUpdate(AssociatePartyContactMechanism associatePartyContactMechanism) {
        return getAssociateReferralsByAssociatePartyContactMechanism(associatePartyContactMechanism, EntityPermission.READ_WRITE);
    }
    
    private List<AssociateReferral> getAssociateReferralsByTargetEntityInstance(EntityInstance targetEntityInstance,
            EntityPermission entityPermission) {
        List<AssociateReferral> associateReferrals;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM associatereferrals, associatereferraldetails " +
                        "WHERE ascrfr_activedetailid = ascrfrdt_associatereferraldetailid " +
                        "AND ascrfrdt_targetentityinstanceid = ? " +
                        "ORDER BY ascrfrdt_associatereferralname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM associatereferrals, associatereferraldetails " +
                        "WHERE ascrfr_activedetailid = ascrfrdt_associatereferraldetailid " +
                        "AND ascrfrdt_targetentityinstanceid = ? " +
                        "FOR UPDATE";
            }

            var ps = AssociateReferralFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, targetEntityInstance.getPrimaryKey().getEntityId());
            
            associateReferrals = AssociateReferralFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return associateReferrals;
    }
    
    public List<AssociateReferral> getAssociateReferralsByTargetEntityInstance(EntityInstance targetEntityInstance) {
        return getAssociateReferralsByTargetEntityInstance(targetEntityInstance, EntityPermission.READ_ONLY);
    }
    
    public List<AssociateReferral> getAssociateReferralsByTargetEntityInstanceForUpdate(EntityInstance targetEntityInstance) {
        return getAssociateReferralsByTargetEntityInstance(targetEntityInstance, EntityPermission.READ_WRITE);
    }
    
    public AssociateReferralTransfer getAssociateReferralTransfer(UserVisit userVisit, AssociateReferral associateReferral) {
        return getAssociateTransferCaches().getAssociateReferralTransferCache().getTransfer(userVisit, associateReferral);
    }
    
    public List<AssociateReferralTransfer> getAssociateReferralTransfers(List<AssociateReferral> associateReferrals, UserVisit userVisit) {
        List<AssociateReferralTransfer> associateReferralTransfers = new ArrayList<>(associateReferrals.size());
        var associateReferralTransferCache = getAssociateTransferCaches().getAssociateReferralTransferCache();
        
        associateReferrals.forEach((associateReferral) ->
                associateReferralTransfers.add(associateReferralTransferCache.getTransfer(userVisit, associateReferral))
        );
        
        return associateReferralTransfers;
    }
    
    public List<AssociateReferralTransfer> getAssociateReferralTransfersByAssociate(Associate associate, UserVisit userVisit) {
        return getAssociateReferralTransfers(getAssociateReferralsByAssociate(associate), userVisit);
    }
    
    public void updateAssociateReferralFromValue(AssociateReferralDetailValue associateReferralDetailValue,
            boolean checkDefault, BasePK updatedBy) {
        var associateReferral = AssociateReferralFactory.getInstance().getEntityFromPK(session,
                EntityPermission.READ_WRITE, associateReferralDetailValue.getAssociateReferralPK());
        var associateReferralDetail = associateReferral.getActiveDetailForUpdate();
        
        associateReferralDetail.setThruTime(session.START_TIME_LONG);
        associateReferralDetail.store();

        var associateReferralPK = associateReferralDetail.getAssociateReferralPK(); // Not updated
        var associateReferralName = associateReferralDetailValue.getAssociateReferralName();
        var associatePK = associateReferralDetailValue.getAssociatePK();
        var associatePartyContactMechanismPK = associateReferralDetailValue.getAssociatePartyContactMechanismPK();
        var targetEntityInstancePK = associateReferralDetailValue.getTargetEntityInstancePK();
        var associateReferralTime = associateReferralDetailValue.getAssociateReferralTime();
        
        associateReferralDetail = AssociateReferralDetailFactory.getInstance().create(associateReferralPK,
                associateReferralName, associatePK, associatePartyContactMechanismPK, targetEntityInstancePK, associateReferralTime,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        associateReferral.setActiveDetail(associateReferralDetail);
        associateReferral.setLastDetail(associateReferralDetail);
        associateReferral.store();
        
        sendEvent(associateReferralPK, EventTypes.MODIFY, null, null, updatedBy);
    }
    
    public void deleteAssociateReferral(AssociateReferral associateReferral, BasePK deletedBy) {
        var associateReferralDetail = associateReferral.getLastDetailForUpdate();
        associateReferralDetail.setThruTime(session.START_TIME_LONG);
        associateReferral.setActiveDetail(null);
        associateReferral.store();
        
        sendEvent(associateReferral.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    public void deleteAssociateReferrals(List<AssociateReferral> associateReferrals, BasePK deletedBy) {
        associateReferrals.forEach((associateReferral) -> 
                deleteAssociateReferral(associateReferral, deletedBy)
        );
    }
    
    public void deleteAssociateReferralsByAssociate(Associate associate, BasePK deletedBy) {
        deleteAssociateReferrals(getAssociateReferralsByAssociateForUpdate(associate), deletedBy);
    }
    
    public void deleteAssociateReferralsByAssociatePartyContactMechanism(AssociatePartyContactMechanism associatePartyContactMechanism, BasePK deletedBy) {
        deleteAssociateReferrals(getAssociateReferralsByAssociatePartyContactMechanismForUpdate(associatePartyContactMechanism), deletedBy);
    }
    
    public void deleteAssociateReferralsByTargetEntityInstance(EntityInstance targetEntityInstance, BasePK deletedBy) {
        deleteAssociateReferrals(getAssociateReferralsByTargetEntityInstanceForUpdate(targetEntityInstance), deletedBy);
    }
    
}
