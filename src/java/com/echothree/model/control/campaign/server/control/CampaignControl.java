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

package com.echothree.model.control.campaign.server.control;

import com.echothree.model.control.campaign.common.choice.CampaignChoicesBean;
import com.echothree.model.control.campaign.common.choice.CampaignContentChoicesBean;
import com.echothree.model.control.campaign.common.choice.CampaignContentStatusChoicesBean;
import com.echothree.model.control.campaign.common.choice.CampaignMediumChoicesBean;
import com.echothree.model.control.campaign.common.choice.CampaignMediumStatusChoicesBean;
import com.echothree.model.control.campaign.common.choice.CampaignSourceChoicesBean;
import com.echothree.model.control.campaign.common.choice.CampaignSourceStatusChoicesBean;
import com.echothree.model.control.campaign.common.choice.CampaignStatusChoicesBean;
import com.echothree.model.control.campaign.common.choice.CampaignTermChoicesBean;
import com.echothree.model.control.campaign.common.choice.CampaignTermStatusChoicesBean;
import com.echothree.model.control.campaign.common.transfer.CampaignContentDescriptionTransfer;
import com.echothree.model.control.campaign.common.transfer.CampaignContentTransfer;
import com.echothree.model.control.campaign.common.transfer.CampaignDescriptionTransfer;
import com.echothree.model.control.campaign.common.transfer.CampaignMediumDescriptionTransfer;
import com.echothree.model.control.campaign.common.transfer.CampaignMediumTransfer;
import com.echothree.model.control.campaign.common.transfer.CampaignSourceDescriptionTransfer;
import com.echothree.model.control.campaign.common.transfer.CampaignSourceTransfer;
import com.echothree.model.control.campaign.common.transfer.CampaignTermDescriptionTransfer;
import com.echothree.model.control.campaign.common.transfer.CampaignTermTransfer;
import com.echothree.model.control.campaign.common.transfer.CampaignTransfer;
import com.echothree.model.control.campaign.common.transfer.UserVisitCampaignTransfer;
import com.echothree.model.control.campaign.common.workflow.CampaignContentStatusConstants;
import com.echothree.model.control.campaign.common.workflow.CampaignMediumStatusConstants;
import com.echothree.model.control.campaign.common.workflow.CampaignSourceStatusConstants;
import com.echothree.model.control.campaign.common.workflow.CampaignStatusConstants;
import com.echothree.model.control.campaign.common.workflow.CampaignTermStatusConstants;
import com.echothree.model.control.campaign.server.transfer.CampaignContentDescriptionTransferCache;
import com.echothree.model.control.campaign.server.transfer.CampaignContentTransferCache;
import com.echothree.model.control.campaign.server.transfer.CampaignDescriptionTransferCache;
import com.echothree.model.control.campaign.server.transfer.CampaignMediumDescriptionTransferCache;
import com.echothree.model.control.campaign.server.transfer.CampaignMediumTransferCache;
import com.echothree.model.control.campaign.server.transfer.CampaignSourceDescriptionTransferCache;
import com.echothree.model.control.campaign.server.transfer.CampaignSourceTransferCache;
import com.echothree.model.control.campaign.server.transfer.CampaignTermDescriptionTransferCache;
import com.echothree.model.control.campaign.server.transfer.CampaignTermTransferCache;
import com.echothree.model.control.campaign.server.transfer.CampaignTransferCache;
import com.echothree.model.control.campaign.server.transfer.UserVisitCampaignTransferCache;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.core.server.control.EntityInstanceControl;
import com.echothree.model.control.sequence.common.SequenceTypes;
import com.echothree.model.control.sequence.server.control.SequenceControl;
import com.echothree.model.control.sequence.server.logic.SequenceGeneratorLogic;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.campaign.common.pk.CampaignContentPK;
import com.echothree.model.data.campaign.common.pk.CampaignMediumPK;
import com.echothree.model.data.campaign.common.pk.CampaignPK;
import com.echothree.model.data.campaign.common.pk.CampaignSourcePK;
import com.echothree.model.data.campaign.common.pk.CampaignTermPK;
import com.echothree.model.data.campaign.server.entity.Campaign;
import com.echothree.model.data.campaign.server.entity.CampaignContent;
import com.echothree.model.data.campaign.server.entity.CampaignContentDescription;
import com.echothree.model.data.campaign.server.entity.CampaignDescription;
import com.echothree.model.data.campaign.server.entity.CampaignMedium;
import com.echothree.model.data.campaign.server.entity.CampaignMediumDescription;
import com.echothree.model.data.campaign.server.entity.CampaignSource;
import com.echothree.model.data.campaign.server.entity.CampaignSourceDescription;
import com.echothree.model.data.campaign.server.entity.CampaignTerm;
import com.echothree.model.data.campaign.server.entity.CampaignTermDescription;
import com.echothree.model.data.campaign.server.entity.UserVisitCampaign;
import com.echothree.model.data.campaign.server.factory.CampaignContentDescriptionFactory;
import com.echothree.model.data.campaign.server.factory.CampaignContentDetailFactory;
import com.echothree.model.data.campaign.server.factory.CampaignContentFactory;
import com.echothree.model.data.campaign.server.factory.CampaignDescriptionFactory;
import com.echothree.model.data.campaign.server.factory.CampaignDetailFactory;
import com.echothree.model.data.campaign.server.factory.CampaignFactory;
import com.echothree.model.data.campaign.server.factory.CampaignMediumDescriptionFactory;
import com.echothree.model.data.campaign.server.factory.CampaignMediumDetailFactory;
import com.echothree.model.data.campaign.server.factory.CampaignMediumFactory;
import com.echothree.model.data.campaign.server.factory.CampaignSourceDescriptionFactory;
import com.echothree.model.data.campaign.server.factory.CampaignSourceDetailFactory;
import com.echothree.model.data.campaign.server.factory.CampaignSourceFactory;
import com.echothree.model.data.campaign.server.factory.CampaignTermDescriptionFactory;
import com.echothree.model.data.campaign.server.factory.CampaignTermDetailFactory;
import com.echothree.model.data.campaign.server.factory.CampaignTermFactory;
import com.echothree.model.data.campaign.server.factory.UserVisitCampaignFactory;
import com.echothree.model.data.campaign.server.value.CampaignContentDescriptionValue;
import com.echothree.model.data.campaign.server.value.CampaignContentDetailValue;
import com.echothree.model.data.campaign.server.value.CampaignDescriptionValue;
import com.echothree.model.data.campaign.server.value.CampaignDetailValue;
import com.echothree.model.data.campaign.server.value.CampaignMediumDescriptionValue;
import com.echothree.model.data.campaign.server.value.CampaignMediumDetailValue;
import com.echothree.model.data.campaign.server.value.CampaignSourceDescriptionValue;
import com.echothree.model.data.campaign.server.value.CampaignSourceDetailValue;
import com.echothree.model.data.campaign.server.value.CampaignTermDescriptionValue;
import com.echothree.model.data.campaign.server.value.CampaignTermDetailValue;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseModelControl;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.persistence.Sha1Utils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import com.echothree.util.server.cdi.CommandScope;
import javax.inject.Inject;

@CommandScope
public class CampaignControl
        extends BaseModelControl {
    
    /** Creates a new instance of CampaignControl */
    protected CampaignControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Campaign Transfer Caches
    // --------------------------------------------------------------------------------

    @Inject
    CampaignTransferCache campaignTransferCache;

    @Inject
    CampaignDescriptionTransferCache campaignDescriptionTransferCache;

    @Inject
    CampaignSourceTransferCache campaignSourceTransferCache;

    @Inject
    CampaignSourceDescriptionTransferCache campaignSourceDescriptionTransferCache;

    @Inject
    CampaignMediumTransferCache campaignMediumTransferCache;

    @Inject
    CampaignMediumDescriptionTransferCache campaignMediumDescriptionTransferCache;

    @Inject
    CampaignTermTransferCache campaignTermTransferCache;

    @Inject
    CampaignTermDescriptionTransferCache campaignTermDescriptionTransferCache;

    @Inject
    CampaignContentTransferCache campaignContentTransferCache;

    @Inject
    CampaignContentDescriptionTransferCache campaignContentDescriptionTransferCache;

    @Inject
    UserVisitCampaignTransferCache userVisitCampaignTransferCache;

    // --------------------------------------------------------------------------------
    //   Campaigns
    // --------------------------------------------------------------------------------

    public Campaign createCampaign(String value, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var sequenceControl = Session.getModelController(SequenceControl.class);
        var sequence = sequenceControl.getDefaultSequenceUsingNames(SequenceTypes.CAMPAIGN.name());
        var campaignName = SequenceGeneratorLogic.getInstance().getNextSequenceValue(sequence);
        
        return createCampaign(campaignName, value, isDefault, sortOrder, createdBy);
    }
    
    public Campaign createCampaign(String campaignName, String value, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var valueSha1Hash = Sha1Utils.getInstance().hash(value.toLowerCase(Locale.getDefault()));
        var defaultCampaign = getDefaultCampaign();
        var defaultFound = defaultCampaign != null;

        if(defaultFound && isDefault) {
            var defaultCampaignDetailValue = getDefaultCampaignDetailValueForUpdate();

            defaultCampaignDetailValue.setIsDefault(false);
            updateCampaignFromValue(defaultCampaignDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var campaign = CampaignFactory.getInstance().create();
        var campaignDetail = CampaignDetailFactory.getInstance().create(campaign, campaignName, valueSha1Hash, value,
                isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);

        // Convert to R/W
        campaign = CampaignFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, campaign.getPrimaryKey());
        campaign.setActiveDetail(campaignDetail);
        campaign.setLastDetail(campaignDetail);
        campaign.store();

        var campaignPK = campaign.getPrimaryKey();
        sendEvent(campaignPK, EventTypes.CREATE, null, null, createdBy);

        var entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
        var entityInstance = entityInstanceControl.getEntityInstanceByBasePK(campaignPK);
        workflowControl.addEntityToWorkflowUsingNames(null, CampaignStatusConstants.Workflow_CAMPAIGN_STATUS,
                CampaignStatusConstants.WorkflowEntrance_NEW_ACTIVE, entityInstance, null, null, createdBy);
        
        return campaign;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.Campaign */
    public Campaign getCampaignByEntityInstance(EntityInstance entityInstance) {
        var pk = new CampaignPK(entityInstance.getEntityUniqueId());
        var campaign = CampaignFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY, pk);

        return campaign;
    }

    private static final Map<EntityPermission, String> getCampaignByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM campaigns, campaigndetails " +
                "WHERE cmpgn_activedetailid = cmpgndt_campaigndetailid " +
                "AND cmpgndt_campaignname = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM campaigns, campaigndetails " +
                "WHERE cmpgn_activedetailid = cmpgndt_campaigndetailid " +
                "AND cmpgndt_campaignname = ? " +
                "FOR UPDATE");
        getCampaignByNameQueries = Collections.unmodifiableMap(queryMap);
    }

    private Campaign getCampaignByName(String campaignName, EntityPermission entityPermission) {
        return CampaignFactory.getInstance().getEntityFromQuery(entityPermission, getCampaignByNameQueries, campaignName);
    }

    public Campaign getCampaignByName(String campaignName) {
        return getCampaignByName(campaignName, EntityPermission.READ_ONLY);
    }

    public Campaign getCampaignByNameForUpdate(String campaignName) {
        return getCampaignByName(campaignName, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getCampaignByValueQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM campaigns, campaigndetails " +
                "WHERE cmpgn_activedetailid = cmpgndt_campaigndetailid " +
                "AND cmpgndt_valuesha1hash = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM campaigns, campaigndetails " +
                "WHERE cmpgn_activedetailid = cmpgndt_campaigndetailid " +
                "AND cmpgndt_valuesha1hash = ? " +
                "FOR UPDATE");
        getCampaignByValueQueries = Collections.unmodifiableMap(queryMap);
    }

    private Campaign getCampaignByValue(String value, EntityPermission entityPermission) {
        return CampaignFactory.getInstance().getEntityFromQuery(entityPermission, getCampaignByValueQueries, 
                Sha1Utils.getInstance().hash(value.toLowerCase(Locale.getDefault())));
    }

    public Campaign getCampaignByValue(String value) {
        return getCampaignByValue(value, EntityPermission.READ_ONLY);
    }

    public Campaign getCampaignByValueForUpdate(String value) {
        return getCampaignByValue(value, EntityPermission.READ_WRITE);
    }

    public CampaignDetailValue getCampaignDetailValueForUpdate(Campaign campaign) {
        return campaign == null? null: campaign.getLastDetailForUpdate().getCampaignDetailValue().clone();
    }

    public CampaignDetailValue getCampaignDetailValueByNameForUpdate(String campaignName) {
        return getCampaignDetailValueForUpdate(getCampaignByNameForUpdate(campaignName));
    }

    private static final Map<EntityPermission, String> getDefaultCampaignQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM campaigns, campaigndetails " +
                "WHERE cmpgn_activedetailid = cmpgndt_campaigndetailid " +
                "AND cmpgndt_isdefault = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM campaigns, campaigndetails " +
                "WHERE cmpgn_activedetailid = cmpgndt_campaigndetailid " +
                "AND cmpgndt_isdefault = 1 " +
                "FOR UPDATE");
        getDefaultCampaignQueries = Collections.unmodifiableMap(queryMap);
    }

    private Campaign getDefaultCampaign(EntityPermission entityPermission) {
        return CampaignFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultCampaignQueries);
    }

    public Campaign getDefaultCampaign() {
        return getDefaultCampaign(EntityPermission.READ_ONLY);
    }

    public Campaign getDefaultCampaignForUpdate() {
        return getDefaultCampaign(EntityPermission.READ_WRITE);
    }

    public CampaignDetailValue getDefaultCampaignDetailValueForUpdate() {
        return getDefaultCampaignForUpdate().getLastDetailForUpdate().getCampaignDetailValue().clone();
    }

    private static final Map<EntityPermission, String> getCampaignsQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM campaigns, campaigndetails " +
                "WHERE cmpgn_activedetailid = cmpgndt_campaigndetailid " +
                "ORDER BY cmpgndt_sortorder, cmpgndt_campaignname " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM campaigns, campaigndetails " +
                "WHERE cmpgn_activedetailid = cmpgndt_campaigndetailid " +
                "FOR UPDATE");
        getCampaignsQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<Campaign> getCampaigns(EntityPermission entityPermission) {
        return CampaignFactory.getInstance().getEntitiesFromQuery(entityPermission, getCampaignsQueries);
    }

    public List<Campaign> getCampaigns() {
        return getCampaigns(EntityPermission.READ_ONLY);
    }

    public List<Campaign> getCampaignsForUpdate() {
        return getCampaigns(EntityPermission.READ_WRITE);
    }

    public CampaignStatusChoicesBean getCampaignStatusChoices(String defaultCampaignStatusChoice, Language language,
            boolean allowNullChoice, Campaign campaign, PartyPK partyPK) {
        var employeeStatusChoicesBean = new CampaignStatusChoicesBean();
        
        if(campaign == null) {
            workflowControl.getWorkflowEntranceChoices(employeeStatusChoicesBean, defaultCampaignStatusChoice, language, allowNullChoice,
                    workflowControl.getWorkflowByName(CampaignStatusConstants.Workflow_CAMPAIGN_STATUS), partyPK);
        } else {
            var entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
            var entityInstance = entityInstanceControl.getEntityInstanceByBasePK(campaign.getPrimaryKey());
            var workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceUsingNames(CampaignStatusConstants.Workflow_CAMPAIGN_STATUS,
                    entityInstance);
            
            workflowControl.getWorkflowDestinationChoices(employeeStatusChoicesBean, defaultCampaignStatusChoice, language, allowNullChoice,
                    workflowEntityStatus.getWorkflowStep(), partyPK);
        }
        
        return employeeStatusChoicesBean;
    }
    
    public void setCampaignStatus(ExecutionErrorAccumulator eea, Party party, String employeeStatusChoice, PartyPK modifiedBy) {
        var entityInstance = getEntityInstanceByBaseEntity(party);
        var workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceForUpdateUsingNames(CampaignStatusConstants.Workflow_CAMPAIGN_STATUS,
                entityInstance);
        var workflowDestination = employeeStatusChoice == null? null:
            workflowControl.getWorkflowDestinationByName(workflowEntityStatus.getWorkflowStep(), employeeStatusChoice);
        
        if(workflowDestination != null || employeeStatusChoice == null) {
            workflowControl.transitionEntityInWorkflow(eea, workflowEntityStatus, workflowDestination, null, modifiedBy);
        } else {
            eea.addExecutionError(ExecutionErrors.UnknownCampaignStatusChoice.name(), employeeStatusChoice);
        }
    }
    
   public CampaignTransfer getCampaignTransfer(UserVisit userVisit, Campaign campaign) {
        return campaignTransferCache.getCampaignTransfer(userVisit, campaign);
    }

    public List<CampaignTransfer> getCampaignTransfers(UserVisit userVisit) {
        var campaigns = getCampaigns();
        List<CampaignTransfer> campaignTransfers = new ArrayList<>(campaigns.size());

        campaigns.forEach((campaign) ->
                campaignTransfers.add(campaignTransferCache.getCampaignTransfer(userVisit, campaign))
        );

        return campaignTransfers;
    }

    public CampaignChoicesBean getCampaignChoices(String defaultCampaignChoice, Language language, boolean allowNullChoice) {
        var campaigns = getCampaigns();
        var size = campaigns.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultCampaignChoice == null) {
                defaultValue = "";
            }
        }

        for(var campaign : campaigns) {
            var campaignDetail = campaign.getLastDetail();

            var label = getBestCampaignDescription(campaign, language);
            var value = campaignDetail.getCampaignName();

            labels.add(label == null? value: label);
            values.add(value);

            var usingDefaultChoice = defaultCampaignChoice != null && defaultCampaignChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && campaignDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new CampaignChoicesBean(labels, values, defaultValue);
    }

    private void updateCampaignFromValue(CampaignDetailValue campaignDetailValue, boolean checkDefault, BasePK updatedBy) {
        if(campaignDetailValue.hasBeenModified()) {
            var campaign = CampaignFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     campaignDetailValue.getCampaignPK());
            var campaignDetail = campaign.getActiveDetailForUpdate();

            campaignDetail.setThruTime(session.getStartTime());
            campaignDetail.store();

            var campaignPK = campaignDetail.getCampaignPK(); // Not updated
            var campaignName = campaignDetailValue.getCampaignName();
            var value = campaignDetailValue.getValue();
            var valueSha1Hash = Sha1Utils.getInstance().hash(value.toLowerCase(Locale.getDefault()));
            var isDefault = campaignDetailValue.getIsDefault();
            var sortOrder = campaignDetailValue.getSortOrder();

            if(checkDefault) {
                var defaultCampaign = getDefaultCampaign();
                var defaultFound = defaultCampaign != null && !defaultCampaign.equals(campaign);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultCampaignDetailValue = getDefaultCampaignDetailValueForUpdate();

                    defaultCampaignDetailValue.setIsDefault(false);
                    updateCampaignFromValue(defaultCampaignDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }

            campaignDetail = CampaignDetailFactory.getInstance().create(campaignPK, campaignName, valueSha1Hash, value, isDefault,
                    sortOrder, session.getStartTime(), Session.MAX_TIME);

            campaign.setActiveDetail(campaignDetail);
            campaign.setLastDetail(campaignDetail);

            sendEvent(campaignPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public void updateCampaignFromValue(CampaignDetailValue campaignDetailValue, BasePK updatedBy) {
        updateCampaignFromValue(campaignDetailValue, true, updatedBy);
    }

    private void deleteCampaign(Campaign campaign, boolean checkDefault, BasePK deletedBy) {
        var campaignDetail = campaign.getLastDetailForUpdate();

        deleteUserVisitCampaignsByCampaign(campaign);
        deleteCampaignDescriptionsByCampaign(campaign, deletedBy);

        campaignDetail.setThruTime(session.getStartTime());
        campaign.setActiveDetail(null);
        campaign.store();

        if(checkDefault) {
            // Check for default, and pick one if necessary
            var defaultCampaign = getDefaultCampaign();

            if(defaultCampaign == null) {
                var campaigns = getCampaignsForUpdate();

                if(!campaigns.isEmpty()) {
                    var iter = campaigns.iterator();
                    if(iter.hasNext()) {
                        defaultCampaign = iter.next();
                    }
                    var campaignDetailValue = Objects.requireNonNull(defaultCampaign).getLastDetailForUpdate().getCampaignDetailValue().clone();

                    campaignDetailValue.setIsDefault(true);
                    updateCampaignFromValue(campaignDetailValue, false, deletedBy);
                }
            }
        }

        sendEvent(campaign.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    public void deleteCampaign(Campaign campaign, BasePK deletedBy) {
        deleteCampaign(campaign, true, deletedBy);
    }

    private void deleteCampaigns(List<Campaign> campaigns, boolean checkDefault, BasePK deletedBy) {
        campaigns.forEach((campaign) -> deleteCampaign(campaign, checkDefault, deletedBy));
    }

    public void deleteCampaigns(List<Campaign> campaigns, BasePK deletedBy) {
        deleteCampaigns(campaigns, true, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Campaign Descriptions
    // --------------------------------------------------------------------------------

    public CampaignDescription createCampaignDescription(Campaign campaign, Language language, String description, BasePK createdBy) {
        var campaignDescription = CampaignDescriptionFactory.getInstance().create(campaign, language, description,
                session.getStartTime(), Session.MAX_TIME);

        sendEvent(campaign.getPrimaryKey(), EventTypes.MODIFY, campaignDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return campaignDescription;
    }

    private static final Map<EntityPermission, String> getCampaignDescriptionQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM campaigndescriptions " +
                "WHERE cmpgnd_cmpgn_campaignid = ? AND cmpgnd_lang_languageid = ? AND cmpgnd_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM campaigndescriptions " +
                "WHERE cmpgnd_cmpgn_campaignid = ? AND cmpgnd_lang_languageid = ? AND cmpgnd_thrutime = ? " +
                "FOR UPDATE");
        getCampaignDescriptionQueries = Collections.unmodifiableMap(queryMap);
    }

    private CampaignDescription getCampaignDescription(Campaign campaign, Language language, EntityPermission entityPermission) {
        return CampaignDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, getCampaignDescriptionQueries,
                campaign, language, Session.MAX_TIME);
    }

    public CampaignDescription getCampaignDescription(Campaign campaign, Language language) {
        return getCampaignDescription(campaign, language, EntityPermission.READ_ONLY);
    }

    public CampaignDescription getCampaignDescriptionForUpdate(Campaign campaign, Language language) {
        return getCampaignDescription(campaign, language, EntityPermission.READ_WRITE);
    }

    public CampaignDescriptionValue getCampaignDescriptionValue(CampaignDescription campaignDescription) {
        return campaignDescription == null? null: campaignDescription.getCampaignDescriptionValue().clone();
    }

    public CampaignDescriptionValue getCampaignDescriptionValueForUpdate(Campaign campaign, Language language) {
        return getCampaignDescriptionValue(getCampaignDescriptionForUpdate(campaign, language));
    }

    private static final Map<EntityPermission, String> getCampaignDescriptionsByCampaignQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM campaigndescriptions, languages " +
                "WHERE cmpgnd_cmpgn_campaignid = ? AND cmpgnd_thrutime = ? AND cmpgnd_lang_languageid = lang_languageid " +
                "ORDER BY lang_sortorder, lang_languageisoname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM campaigndescriptions " +
                "WHERE cmpgnd_cmpgn_campaignid = ? AND cmpgnd_thrutime = ? " +
                "FOR UPDATE");
        getCampaignDescriptionsByCampaignQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<CampaignDescription> getCampaignDescriptionsByCampaign(Campaign campaign, EntityPermission entityPermission) {
        return CampaignDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, getCampaignDescriptionsByCampaignQueries,
                campaign, Session.MAX_TIME);
    }

    public List<CampaignDescription> getCampaignDescriptionsByCampaign(Campaign campaign) {
        return getCampaignDescriptionsByCampaign(campaign, EntityPermission.READ_ONLY);
    }

    public List<CampaignDescription> getCampaignDescriptionsByCampaignForUpdate(Campaign campaign) {
        return getCampaignDescriptionsByCampaign(campaign, EntityPermission.READ_WRITE);
    }

    public String getBestCampaignDescription(Campaign campaign, Language language) {
        String description;
        var campaignDescription = getCampaignDescription(campaign, language);

        if(campaignDescription == null && !language.getIsDefault()) {
            campaignDescription = getCampaignDescription(campaign, partyControl.getDefaultLanguage());
        }

        if(campaignDescription == null) {
            description = campaign.getLastDetail().getCampaignName();
        } else {
            description = campaignDescription.getDescription();
        }

        return description;
    }

    public CampaignDescriptionTransfer getCampaignDescriptionTransfer(UserVisit userVisit, CampaignDescription campaignDescription) {
        return campaignDescriptionTransferCache.getCampaignDescriptionTransfer(userVisit, campaignDescription);
    }

    public List<CampaignDescriptionTransfer> getCampaignDescriptionTransfersByCampaign(UserVisit userVisit, Campaign campaign) {
        var campaignDescriptions = getCampaignDescriptionsByCampaign(campaign);
        List<CampaignDescriptionTransfer> campaignDescriptionTransfers = new ArrayList<>(campaignDescriptions.size());

        campaignDescriptions.forEach((campaignDescription) ->
                campaignDescriptionTransfers.add(campaignDescriptionTransferCache.getCampaignDescriptionTransfer(userVisit, campaignDescription))
        );

        return campaignDescriptionTransfers;
    }

    public void updateCampaignDescriptionFromValue(CampaignDescriptionValue campaignDescriptionValue, BasePK updatedBy) {
        if(campaignDescriptionValue.hasBeenModified()) {
            var campaignDescription = CampaignDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    campaignDescriptionValue.getPrimaryKey());

            campaignDescription.setThruTime(session.getStartTime());
            campaignDescription.store();

            var campaign = campaignDescription.getCampaign();
            var language = campaignDescription.getLanguage();
            var description = campaignDescriptionValue.getDescription();

            campaignDescription = CampaignDescriptionFactory.getInstance().create(campaign, language, description,
                    session.getStartTime(), Session.MAX_TIME);

            sendEvent(campaign.getPrimaryKey(), EventTypes.MODIFY, campaignDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteCampaignDescription(CampaignDescription campaignDescription, BasePK deletedBy) {
        campaignDescription.setThruTime(session.getStartTime());

        sendEvent(campaignDescription.getCampaignPK(), EventTypes.MODIFY, campaignDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }

    public void deleteCampaignDescriptionsByCampaign(Campaign campaign, BasePK deletedBy) {
        var campaignDescriptions = getCampaignDescriptionsByCampaignForUpdate(campaign);

        campaignDescriptions.forEach((campaignDescription) -> 
                deleteCampaignDescription(campaignDescription, deletedBy)
        );
    }

    // --------------------------------------------------------------------------------
    //   Campaign Sources
    // --------------------------------------------------------------------------------

    public CampaignSource createCampaignSource(String value, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var sequenceControl = Session.getModelController(SequenceControl.class);
        var sequence = sequenceControl.getDefaultSequenceUsingNames(SequenceTypes.CAMPAIGN_SOURCE.name());
        var campaignSourceName = SequenceGeneratorLogic.getInstance().getNextSequenceValue(sequence);
        
        return createCampaignSource(campaignSourceName, value, isDefault, sortOrder, createdBy);
    }
    
    public CampaignSource createCampaignSource(String campaignSourceName, String value, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var valueSha1Hash = Sha1Utils.getInstance().hash(value.toLowerCase(Locale.getDefault()));
        var defaultCampaignSource = getDefaultCampaignSource();
        var defaultFound = defaultCampaignSource != null;

        if(defaultFound && isDefault) {
            var defaultCampaignSourceDetailValue = getDefaultCampaignSourceDetailValueForUpdate();

            defaultCampaignSourceDetailValue.setIsDefault(false);
            updateCampaignSourceFromValue(defaultCampaignSourceDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var campaignSource = CampaignSourceFactory.getInstance().create();
        var campaignSourceDetail = CampaignSourceDetailFactory.getInstance().create(campaignSource, campaignSourceName, valueSha1Hash, value,
                isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);

        // Convert to R/W
        campaignSource = CampaignSourceFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, campaignSource.getPrimaryKey());
        campaignSource.setActiveDetail(campaignSourceDetail);
        campaignSource.setLastDetail(campaignSourceDetail);
        campaignSource.store();

        var campaignSourcePK = campaignSource.getPrimaryKey();
        sendEvent(campaignSourcePK, EventTypes.CREATE, null, null, createdBy);

        var entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
        var entityInstance = entityInstanceControl.getEntityInstanceByBasePK(campaignSourcePK);
        workflowControl.addEntityToWorkflowUsingNames(null, CampaignSourceStatusConstants.Workflow_CAMPAIGN_SOURCE_STATUS,
                CampaignSourceStatusConstants.WorkflowEntrance_NEW_ACTIVE, entityInstance, null, null, createdBy);

        return campaignSource;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.CampaignSource */
    public CampaignSource getCampaignSourceByEntityInstance(EntityInstance entityInstance) {
        var pk = new CampaignSourcePK(entityInstance.getEntityUniqueId());
        var campaignSource = CampaignSourceFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY, pk);

        return campaignSource;
    }

    private static final Map<EntityPermission, String> getCampaignSourceByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM campaignsources, campaignsourcedetails " +
                "WHERE cmpgnsrc_activedetailid = cmpgnsrcdt_campaignsourcedetailid " +
                "AND cmpgnsrcdt_campaignsourcename = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM campaignsources, campaignsourcedetails " +
                "WHERE cmpgnsrc_activedetailid = cmpgnsrcdt_campaignsourcedetailid " +
                "AND cmpgnsrcdt_campaignsourcename = ? " +
                "FOR UPDATE");
        getCampaignSourceByNameQueries = Collections.unmodifiableMap(queryMap);
    }

    private CampaignSource getCampaignSourceByName(String campaignSourceName, EntityPermission entityPermission) {
        return CampaignSourceFactory.getInstance().getEntityFromQuery(entityPermission, getCampaignSourceByNameQueries, campaignSourceName);
    }

    public CampaignSource getCampaignSourceByName(String campaignSourceName) {
        return getCampaignSourceByName(campaignSourceName, EntityPermission.READ_ONLY);
    }

    public CampaignSource getCampaignSourceByNameForUpdate(String campaignSourceName) {
        return getCampaignSourceByName(campaignSourceName, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getCampaignSourceByValueQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM campaignsources, campaignsourcedetails " +
                "WHERE cmpgnsrc_activedetailid = cmpgnsrcdt_campaignsourcedetailid " +
                "AND cmpgnsrcdt_valuesha1hash = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM campaignsources, campaignsourcedetails " +
                "WHERE cmpgnsrc_activedetailid = cmpgnsrcdt_campaignsourcedetailid " +
                "AND cmpgnsrcdt_valuesha1hash = ? " +
                "FOR UPDATE");
        getCampaignSourceByValueQueries = Collections.unmodifiableMap(queryMap);
    }

    private CampaignSource getCampaignSourceByValue(String value, EntityPermission entityPermission) {
        return CampaignSourceFactory.getInstance().getEntityFromQuery(entityPermission, getCampaignSourceByValueQueries, 
                Sha1Utils.getInstance().hash(value.toLowerCase(Locale.getDefault())));
    }

    public CampaignSource getCampaignSourceByValue(String value) {
        return getCampaignSourceByValue(value, EntityPermission.READ_ONLY);
    }

    public CampaignSource getCampaignSourceByValueForUpdate(String value) {
        return getCampaignSourceByValue(value, EntityPermission.READ_WRITE);
    }

    public CampaignSourceDetailValue getCampaignSourceDetailValueForUpdate(CampaignSource campaignSource) {
        return campaignSource == null? null: campaignSource.getLastDetailForUpdate().getCampaignSourceDetailValue().clone();
    }

    public CampaignSourceDetailValue getCampaignSourceDetailValueByNameForUpdate(String campaignSourceName) {
        return getCampaignSourceDetailValueForUpdate(getCampaignSourceByNameForUpdate(campaignSourceName));
    }

    private static final Map<EntityPermission, String> getDefaultCampaignSourceQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM campaignsources, campaignsourcedetails " +
                "WHERE cmpgnsrc_activedetailid = cmpgnsrcdt_campaignsourcedetailid " +
                "AND cmpgnsrcdt_isdefault = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM campaignsources, campaignsourcedetails " +
                "WHERE cmpgnsrc_activedetailid = cmpgnsrcdt_campaignsourcedetailid " +
                "AND cmpgnsrcdt_isdefault = 1 " +
                "FOR UPDATE");
        getDefaultCampaignSourceQueries = Collections.unmodifiableMap(queryMap);
    }

    private CampaignSource getDefaultCampaignSource(EntityPermission entityPermission) {
        return CampaignSourceFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultCampaignSourceQueries);
    }

    public CampaignSource getDefaultCampaignSource() {
        return getDefaultCampaignSource(EntityPermission.READ_ONLY);
    }

    public CampaignSource getDefaultCampaignSourceForUpdate() {
        return getDefaultCampaignSource(EntityPermission.READ_WRITE);
    }

    public CampaignSourceDetailValue getDefaultCampaignSourceDetailValueForUpdate() {
        return getDefaultCampaignSourceForUpdate().getLastDetailForUpdate().getCampaignSourceDetailValue().clone();
    }

    private static final Map<EntityPermission, String> getCampaignSourcesQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM campaignsources, campaignsourcedetails " +
                "WHERE cmpgnsrc_activedetailid = cmpgnsrcdt_campaignsourcedetailid " +
                "ORDER BY cmpgnsrcdt_sortorder, cmpgnsrcdt_campaignsourcename " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM campaignsources, campaignsourcedetails " +
                "WHERE cmpgnsrc_activedetailid = cmpgnsrcdt_campaignsourcedetailid " +
                "FOR UPDATE");
        getCampaignSourcesQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<CampaignSource> getCampaignSources(EntityPermission entityPermission) {
        return CampaignSourceFactory.getInstance().getEntitiesFromQuery(entityPermission, getCampaignSourcesQueries);
    }

    public List<CampaignSource> getCampaignSources() {
        return getCampaignSources(EntityPermission.READ_ONLY);
    }

    public List<CampaignSource> getCampaignSourcesForUpdate() {
        return getCampaignSources(EntityPermission.READ_WRITE);
    }

    public CampaignSourceStatusChoicesBean getCampaignSourceStatusChoices(String defaultCampaignSourceStatusChoice, Language language,
            boolean allowNullChoice, CampaignSource campaignSource, PartyPK partyPK) {
        var employeeStatusChoicesBean = new CampaignSourceStatusChoicesBean();
        
        if(campaignSource == null) {
            workflowControl.getWorkflowEntranceChoices(employeeStatusChoicesBean, defaultCampaignSourceStatusChoice, language, allowNullChoice,
                    workflowControl.getWorkflowByName(CampaignSourceStatusConstants.Workflow_CAMPAIGN_SOURCE_STATUS), partyPK);
        } else {
            var entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
            var entityInstance = entityInstanceControl.getEntityInstanceByBasePK(campaignSource.getPrimaryKey());
            var workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceUsingNames(CampaignSourceStatusConstants.Workflow_CAMPAIGN_SOURCE_STATUS,
                    entityInstance);
            
            workflowControl.getWorkflowDestinationChoices(employeeStatusChoicesBean, defaultCampaignSourceStatusChoice, language, allowNullChoice,
                    workflowEntityStatus.getWorkflowStep(), partyPK);
        }
        
        return employeeStatusChoicesBean;
    }
    
    public void setCampaignSourceStatus(ExecutionErrorAccumulator eea, Party party, String employeeStatusChoice, PartyPK modifiedBy) {
        var entityInstance = getEntityInstanceByBaseEntity(party);
        var workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceForUpdateUsingNames(CampaignSourceStatusConstants.Workflow_CAMPAIGN_SOURCE_STATUS,
                entityInstance);
        var workflowDestination = employeeStatusChoice == null? null:
            workflowControl.getWorkflowDestinationByName(workflowEntityStatus.getWorkflowStep(), employeeStatusChoice);
        
        if(workflowDestination != null || employeeStatusChoice == null) {
            workflowControl.transitionEntityInWorkflow(eea, workflowEntityStatus, workflowDestination, null, modifiedBy);
        } else {
            eea.addExecutionError(ExecutionErrors.UnknownCampaignSourceStatusChoice.name(), employeeStatusChoice);
        }
    }
    
   public CampaignSourceTransfer getCampaignSourceTransfer(UserVisit userVisit, CampaignSource campaignSource) {
        return campaignSourceTransferCache.getCampaignSourceTransfer(userVisit, campaignSource);
    }

    public List<CampaignSourceTransfer> getCampaignSourceTransfers(UserVisit userVisit) {
        var campaignSources = getCampaignSources();
        List<CampaignSourceTransfer> campaignSourceTransfers = new ArrayList<>(campaignSources.size());

        campaignSources.forEach((campaignSource) ->
                campaignSourceTransfers.add(campaignSourceTransferCache.getCampaignSourceTransfer(userVisit, campaignSource))
        );

        return campaignSourceTransfers;
    }

    public CampaignSourceChoicesBean getCampaignSourceChoices(String defaultCampaignSourceChoice, Language language, boolean allowNullChoice) {
        var campaignSources = getCampaignSources();
        var size = campaignSources.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultCampaignSourceChoice == null) {
                defaultValue = "";
            }
        }

        for(var campaignSource : campaignSources) {
            var campaignSourceDetail = campaignSource.getLastDetail();

            var label = getBestCampaignSourceDescription(campaignSource, language);
            var value = campaignSourceDetail.getCampaignSourceName();

            labels.add(label == null? value: label);
            values.add(value);

            var usingDefaultChoice = defaultCampaignSourceChoice != null && defaultCampaignSourceChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && campaignSourceDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new CampaignSourceChoicesBean(labels, values, defaultValue);
    }

    private void updateCampaignSourceFromValue(CampaignSourceDetailValue campaignSourceDetailValue, boolean checkDefault, BasePK updatedBy) {
        if(campaignSourceDetailValue.hasBeenModified()) {
            var campaignSource = CampaignSourceFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     campaignSourceDetailValue.getCampaignSourcePK());
            var campaignSourceDetail = campaignSource.getActiveDetailForUpdate();

            campaignSourceDetail.setThruTime(session.getStartTime());
            campaignSourceDetail.store();

            var campaignSourcePK = campaignSourceDetail.getCampaignSourcePK(); // Not updated
            var campaignSourceName = campaignSourceDetailValue.getCampaignSourceName();
            var value = campaignSourceDetailValue.getValue();
            var valueSha1Hash = Sha1Utils.getInstance().hash(value.toLowerCase(Locale.getDefault()));
            var isDefault = campaignSourceDetailValue.getIsDefault();
            var sortOrder = campaignSourceDetailValue.getSortOrder();

            if(checkDefault) {
                var defaultCampaignSource = getDefaultCampaignSource();
                var defaultFound = defaultCampaignSource != null && !defaultCampaignSource.equals(campaignSource);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultCampaignSourceDetailValue = getDefaultCampaignSourceDetailValueForUpdate();

                    defaultCampaignSourceDetailValue.setIsDefault(false);
                    updateCampaignSourceFromValue(defaultCampaignSourceDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }

            campaignSourceDetail = CampaignSourceDetailFactory.getInstance().create(campaignSourcePK, campaignSourceName, valueSha1Hash, value, isDefault,
                    sortOrder, session.getStartTime(), Session.MAX_TIME);

            campaignSource.setActiveDetail(campaignSourceDetail);
            campaignSource.setLastDetail(campaignSourceDetail);

            sendEvent(campaignSourcePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public void updateCampaignSourceFromValue(CampaignSourceDetailValue campaignSourceDetailValue, BasePK updatedBy) {
        updateCampaignSourceFromValue(campaignSourceDetailValue, true, updatedBy);
    }

    private void deleteCampaignSource(CampaignSource campaignSource, boolean checkDefault, BasePK deletedBy) {
        var campaignSourceDetail = campaignSource.getLastDetailForUpdate();

        deleteUserVisitCampaignsByCampaignSource(campaignSource);
        deleteCampaignSourceDescriptionsByCampaignSource(campaignSource, deletedBy);

        campaignSourceDetail.setThruTime(session.getStartTime());
        campaignSource.setActiveDetail(null);
        campaignSource.store();

        if(checkDefault) {
            // Check for default, and pick one if necessary
            var defaultCampaignSource = getDefaultCampaignSource();

            if(defaultCampaignSource == null) {
                var campaignSources = getCampaignSourcesForUpdate();

                if(!campaignSources.isEmpty()) {
                    var iter = campaignSources.iterator();
                    if(iter.hasNext()) {
                        defaultCampaignSource = iter.next();
                    }
                    var campaignSourceDetailValue = Objects.requireNonNull(defaultCampaignSource).getLastDetailForUpdate().getCampaignSourceDetailValue().clone();

                    campaignSourceDetailValue.setIsDefault(true);
                    updateCampaignSourceFromValue(campaignSourceDetailValue, false, deletedBy);
                }
            }
        }

        sendEvent(campaignSource.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    public void deleteCampaignSource(CampaignSource campaignSource, BasePK deletedBy) {
        deleteCampaignSource(campaignSource, true, deletedBy);
    }

    private void deleteCampaignSources(List<CampaignSource> campaignSources, boolean checkDefault, BasePK deletedBy) {
        campaignSources.forEach((campaignSource) -> deleteCampaignSource(campaignSource, checkDefault, deletedBy));
    }

    public void deleteCampaignSources(List<CampaignSource> campaignSources, BasePK deletedBy) {
        deleteCampaignSources(campaignSources, true, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Campaign Source Descriptions
    // --------------------------------------------------------------------------------

    public CampaignSourceDescription createCampaignSourceDescription(CampaignSource campaignSource, Language language, String description, BasePK createdBy) {
        var campaignSourceDescription = CampaignSourceDescriptionFactory.getInstance().create(campaignSource, language, description,
                session.getStartTime(), Session.MAX_TIME);

        sendEvent(campaignSource.getPrimaryKey(), EventTypes.MODIFY, campaignSourceDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return campaignSourceDescription;
    }

    private static final Map<EntityPermission, String> getCampaignSourceDescriptionQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM campaignsourcedescriptions " +
                "WHERE cmpgnsrcd_cmpgnsrc_campaignsourceid = ? AND cmpgnsrcd_lang_languageid = ? AND cmpgnsrcd_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM campaignsourcedescriptions " +
                "WHERE cmpgnsrcd_cmpgnsrc_campaignsourceid = ? AND cmpgnsrcd_lang_languageid = ? AND cmpgnsrcd_thrutime = ? " +
                "FOR UPDATE");
        getCampaignSourceDescriptionQueries = Collections.unmodifiableMap(queryMap);
    }

    private CampaignSourceDescription getCampaignSourceDescription(CampaignSource campaignSource, Language language, EntityPermission entityPermission) {
        return CampaignSourceDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, getCampaignSourceDescriptionQueries,
                campaignSource, language, Session.MAX_TIME);
    }

    public CampaignSourceDescription getCampaignSourceDescription(CampaignSource campaignSource, Language language) {
        return getCampaignSourceDescription(campaignSource, language, EntityPermission.READ_ONLY);
    }

    public CampaignSourceDescription getCampaignSourceDescriptionForUpdate(CampaignSource campaignSource, Language language) {
        return getCampaignSourceDescription(campaignSource, language, EntityPermission.READ_WRITE);
    }

    public CampaignSourceDescriptionValue getCampaignSourceDescriptionValue(CampaignSourceDescription campaignSourceDescription) {
        return campaignSourceDescription == null? null: campaignSourceDescription.getCampaignSourceDescriptionValue().clone();
    }

    public CampaignSourceDescriptionValue getCampaignSourceDescriptionValueForUpdate(CampaignSource campaignSource, Language language) {
        return getCampaignSourceDescriptionValue(getCampaignSourceDescriptionForUpdate(campaignSource, language));
    }

    private static final Map<EntityPermission, String> getCampaignSourceDescriptionsByCampaignSourceQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM campaignsourcedescriptions, languages " +
                "WHERE cmpgnsrcd_cmpgnsrc_campaignsourceid = ? AND cmpgnsrcd_thrutime = ? AND cmpgnsrcd_lang_languageid = lang_languageid " +
                "ORDER BY lang_sortorder, lang_languageisoname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM campaignsourcedescriptions " +
                "WHERE cmpgnsrcd_cmpgnsrc_campaignsourceid = ? AND cmpgnsrcd_thrutime = ? " +
                "FOR UPDATE");
        getCampaignSourceDescriptionsByCampaignSourceQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<CampaignSourceDescription> getCampaignSourceDescriptionsByCampaignSource(CampaignSource campaignSource, EntityPermission entityPermission) {
        return CampaignSourceDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, getCampaignSourceDescriptionsByCampaignSourceQueries,
                campaignSource, Session.MAX_TIME);
    }

    public List<CampaignSourceDescription> getCampaignSourceDescriptionsByCampaignSource(CampaignSource campaignSource) {
        return getCampaignSourceDescriptionsByCampaignSource(campaignSource, EntityPermission.READ_ONLY);
    }

    public List<CampaignSourceDescription> getCampaignSourceDescriptionsByCampaignSourceForUpdate(CampaignSource campaignSource) {
        return getCampaignSourceDescriptionsByCampaignSource(campaignSource, EntityPermission.READ_WRITE);
    }

    public String getBestCampaignSourceDescription(CampaignSource campaignSource, Language language) {
        String description;
        var campaignSourceDescription = getCampaignSourceDescription(campaignSource, language);

        if(campaignSourceDescription == null && !language.getIsDefault()) {
            campaignSourceDescription = getCampaignSourceDescription(campaignSource, partyControl.getDefaultLanguage());
        }

        if(campaignSourceDescription == null) {
            description = campaignSource.getLastDetail().getCampaignSourceName();
        } else {
            description = campaignSourceDescription.getDescription();
        }

        return description;
    }

    public CampaignSourceDescriptionTransfer getCampaignSourceDescriptionTransfer(UserVisit userVisit, CampaignSourceDescription campaignSourceDescription) {
        return campaignSourceDescriptionTransferCache.getCampaignSourceDescriptionTransfer(userVisit, campaignSourceDescription);
    }

    public List<CampaignSourceDescriptionTransfer> getCampaignSourceDescriptionTransfersByCampaignSource(UserVisit userVisit, CampaignSource campaignSource) {
        var campaignSourceDescriptions = getCampaignSourceDescriptionsByCampaignSource(campaignSource);
        List<CampaignSourceDescriptionTransfer> campaignSourceDescriptionTransfers = new ArrayList<>(campaignSourceDescriptions.size());

        campaignSourceDescriptions.forEach((campaignSourceDescription) ->
                campaignSourceDescriptionTransfers.add(campaignSourceDescriptionTransferCache.getCampaignSourceDescriptionTransfer(userVisit, campaignSourceDescription))
        );

        return campaignSourceDescriptionTransfers;
    }

    public void updateCampaignSourceDescriptionFromValue(CampaignSourceDescriptionValue campaignSourceDescriptionValue, BasePK updatedBy) {
        if(campaignSourceDescriptionValue.hasBeenModified()) {
            var campaignSourceDescription = CampaignSourceDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    campaignSourceDescriptionValue.getPrimaryKey());

            campaignSourceDescription.setThruTime(session.getStartTime());
            campaignSourceDescription.store();

            var campaignSource = campaignSourceDescription.getCampaignSource();
            var language = campaignSourceDescription.getLanguage();
            var description = campaignSourceDescriptionValue.getDescription();

            campaignSourceDescription = CampaignSourceDescriptionFactory.getInstance().create(campaignSource, language, description,
                    session.getStartTime(), Session.MAX_TIME);

            sendEvent(campaignSource.getPrimaryKey(), EventTypes.MODIFY, campaignSourceDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteCampaignSourceDescription(CampaignSourceDescription campaignSourceDescription, BasePK deletedBy) {
        campaignSourceDescription.setThruTime(session.getStartTime());

        sendEvent(campaignSourceDescription.getCampaignSourcePK(), EventTypes.MODIFY, campaignSourceDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }

    public void deleteCampaignSourceDescriptionsByCampaignSource(CampaignSource campaignSource, BasePK deletedBy) {
        var campaignSourceDescriptions = getCampaignSourceDescriptionsByCampaignSourceForUpdate(campaignSource);

        campaignSourceDescriptions.forEach((campaignSourceDescription) -> 
                deleteCampaignSourceDescription(campaignSourceDescription, deletedBy)
        );
    }

    // --------------------------------------------------------------------------------
    //   Campaign Mediums
    // --------------------------------------------------------------------------------

    public CampaignMedium createCampaignMedium(String value, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var sequenceControl = Session.getModelController(SequenceControl.class);
        var sequence = sequenceControl.getDefaultSequenceUsingNames(SequenceTypes.CAMPAIGN_MEDIUM.name());
        var campaignMediumName = SequenceGeneratorLogic.getInstance().getNextSequenceValue(sequence);
        
        return createCampaignMedium(campaignMediumName, value, isDefault, sortOrder, createdBy);
    }
    
    public CampaignMedium createCampaignMedium(String campaignMediumName, String value, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var valueSha1Hash = Sha1Utils.getInstance().hash(value.toLowerCase(Locale.getDefault()));
        var defaultCampaignMedium = getDefaultCampaignMedium();
        var defaultFound = defaultCampaignMedium != null;

        if(defaultFound && isDefault) {
            var defaultCampaignMediumDetailValue = getDefaultCampaignMediumDetailValueForUpdate();

            defaultCampaignMediumDetailValue.setIsDefault(false);
            updateCampaignMediumFromValue(defaultCampaignMediumDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var campaignMedium = CampaignMediumFactory.getInstance().create();
        var campaignMediumDetail = CampaignMediumDetailFactory.getInstance().create(campaignMedium, campaignMediumName, valueSha1Hash, value,
                isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);

        // Convert to R/W
        campaignMedium = CampaignMediumFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, campaignMedium.getPrimaryKey());
        campaignMedium.setActiveDetail(campaignMediumDetail);
        campaignMedium.setLastDetail(campaignMediumDetail);
        campaignMedium.store();

        var campaignMediumPK = campaignMedium.getPrimaryKey();
        sendEvent(campaignMediumPK, EventTypes.CREATE, null, null, createdBy);

        var entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
        var entityInstance = entityInstanceControl.getEntityInstanceByBasePK(campaignMediumPK);
        workflowControl.addEntityToWorkflowUsingNames(null, CampaignMediumStatusConstants.Workflow_CAMPAIGN_MEDIUM_STATUS,
                CampaignMediumStatusConstants.WorkflowEntrance_NEW_ACTIVE, entityInstance, null, null, createdBy);

        return campaignMedium;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.CampaignMedium */
    public CampaignMedium getCampaignMediumByEntityInstance(EntityInstance entityInstance) {
        var pk = new CampaignMediumPK(entityInstance.getEntityUniqueId());
        var campaignMedium = CampaignMediumFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY, pk);

        return campaignMedium;
    }

    private static final Map<EntityPermission, String> getCampaignMediumByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM campaignmediums, campaignmediumdetails " +
                "WHERE cmpgnmdm_activedetailid = cmpgnmdmdt_campaignmediumdetailid " +
                "AND cmpgnmdmdt_campaignmediumname = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM campaignmediums, campaignmediumdetails " +
                "WHERE cmpgnmdm_activedetailid = cmpgnmdmdt_campaignmediumdetailid " +
                "AND cmpgnmdmdt_campaignmediumname = ? " +
                "FOR UPDATE");
        getCampaignMediumByNameQueries = Collections.unmodifiableMap(queryMap);
    }

    private CampaignMedium getCampaignMediumByName(String campaignMediumName, EntityPermission entityPermission) {
        return CampaignMediumFactory.getInstance().getEntityFromQuery(entityPermission, getCampaignMediumByNameQueries, campaignMediumName);
    }

    public CampaignMedium getCampaignMediumByName(String campaignMediumName) {
        return getCampaignMediumByName(campaignMediumName, EntityPermission.READ_ONLY);
    }

    public CampaignMedium getCampaignMediumByNameForUpdate(String campaignMediumName) {
        return getCampaignMediumByName(campaignMediumName, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getCampaignMediumByValueQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM campaignmediums, campaignmediumdetails " +
                "WHERE cmpgnmdm_activedetailid = cmpgnmdmdt_campaignmediumdetailid " +
                "AND cmpgnmdmdt_valuesha1hash = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM campaignmediums, campaignmediumdetails " +
                "WHERE cmpgnmdm_activedetailid = cmpgnmdmdt_campaignmediumdetailid " +
                "AND cmpgnmdmdt_valuesha1hash = ? " +
                "FOR UPDATE");
        getCampaignMediumByValueQueries = Collections.unmodifiableMap(queryMap);
    }

    private CampaignMedium getCampaignMediumByValue(String value, EntityPermission entityPermission) {
        return CampaignMediumFactory.getInstance().getEntityFromQuery(entityPermission, getCampaignMediumByValueQueries, 
                Sha1Utils.getInstance().hash(value.toLowerCase(Locale.getDefault())));
    }

    public CampaignMedium getCampaignMediumByValue(String value) {
        return getCampaignMediumByValue(value, EntityPermission.READ_ONLY);
    }

    public CampaignMedium getCampaignMediumByValueForUpdate(String value) {
        return getCampaignMediumByValue(value, EntityPermission.READ_WRITE);
    }

    public CampaignMediumDetailValue getCampaignMediumDetailValueForUpdate(CampaignMedium campaignMedium) {
        return campaignMedium == null? null: campaignMedium.getLastDetailForUpdate().getCampaignMediumDetailValue().clone();
    }

    public CampaignMediumDetailValue getCampaignMediumDetailValueByNameForUpdate(String campaignMediumName) {
        return getCampaignMediumDetailValueForUpdate(getCampaignMediumByNameForUpdate(campaignMediumName));
    }

    private static final Map<EntityPermission, String> getDefaultCampaignMediumQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM campaignmediums, campaignmediumdetails " +
                "WHERE cmpgnmdm_activedetailid = cmpgnmdmdt_campaignmediumdetailid " +
                "AND cmpgnmdmdt_isdefault = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM campaignmediums, campaignmediumdetails " +
                "WHERE cmpgnmdm_activedetailid = cmpgnmdmdt_campaignmediumdetailid " +
                "AND cmpgnmdmdt_isdefault = 1 " +
                "FOR UPDATE");
        getDefaultCampaignMediumQueries = Collections.unmodifiableMap(queryMap);
    }

    private CampaignMedium getDefaultCampaignMedium(EntityPermission entityPermission) {
        return CampaignMediumFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultCampaignMediumQueries);
    }

    public CampaignMedium getDefaultCampaignMedium() {
        return getDefaultCampaignMedium(EntityPermission.READ_ONLY);
    }

    public CampaignMedium getDefaultCampaignMediumForUpdate() {
        return getDefaultCampaignMedium(EntityPermission.READ_WRITE);
    }

    public CampaignMediumDetailValue getDefaultCampaignMediumDetailValueForUpdate() {
        return getDefaultCampaignMediumForUpdate().getLastDetailForUpdate().getCampaignMediumDetailValue().clone();
    }

    private static final Map<EntityPermission, String> getCampaignMediumsQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM campaignmediums, campaignmediumdetails " +
                "WHERE cmpgnmdm_activedetailid = cmpgnmdmdt_campaignmediumdetailid " +
                "ORDER BY cmpgnmdmdt_sortorder, cmpgnmdmdt_campaignmediumname " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM campaignmediums, campaignmediumdetails " +
                "WHERE cmpgnmdm_activedetailid = cmpgnmdmdt_campaignmediumdetailid " +
                "FOR UPDATE");
        getCampaignMediumsQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<CampaignMedium> getCampaignMediums(EntityPermission entityPermission) {
        return CampaignMediumFactory.getInstance().getEntitiesFromQuery(entityPermission, getCampaignMediumsQueries);
    }

    public List<CampaignMedium> getCampaignMediums() {
        return getCampaignMediums(EntityPermission.READ_ONLY);
    }

    public List<CampaignMedium> getCampaignMediumsForUpdate() {
        return getCampaignMediums(EntityPermission.READ_WRITE);
    }

    public CampaignMediumStatusChoicesBean getCampaignMediumStatusChoices(String defaultCampaignMediumStatusChoice, Language language,
            boolean allowNullChoice, CampaignMedium campaignMedium, PartyPK partyPK) {
        var employeeStatusChoicesBean = new CampaignMediumStatusChoicesBean();
        
        if(campaignMedium == null) {
            workflowControl.getWorkflowEntranceChoices(employeeStatusChoicesBean, defaultCampaignMediumStatusChoice, language, allowNullChoice,
                    workflowControl.getWorkflowByName(CampaignMediumStatusConstants.Workflow_CAMPAIGN_MEDIUM_STATUS), partyPK);
        } else {
            var entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
            var entityInstance = entityInstanceControl.getEntityInstanceByBasePK(campaignMedium.getPrimaryKey());
            var workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceUsingNames(CampaignMediumStatusConstants.Workflow_CAMPAIGN_MEDIUM_STATUS,
                    entityInstance);
            
            workflowControl.getWorkflowDestinationChoices(employeeStatusChoicesBean, defaultCampaignMediumStatusChoice, language, allowNullChoice,
                    workflowEntityStatus.getWorkflowStep(), partyPK);
        }
        
        return employeeStatusChoicesBean;
    }
    
    public void setCampaignMediumStatus(ExecutionErrorAccumulator eea, Party party, String employeeStatusChoice, PartyPK modifiedBy) {
        var entityInstance = getEntityInstanceByBaseEntity(party);
        var workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceForUpdateUsingNames(CampaignMediumStatusConstants.Workflow_CAMPAIGN_MEDIUM_STATUS,
                entityInstance);
        var workflowDestination = employeeStatusChoice == null? null:
            workflowControl.getWorkflowDestinationByName(workflowEntityStatus.getWorkflowStep(), employeeStatusChoice);
        
        if(workflowDestination != null || employeeStatusChoice == null) {
            workflowControl.transitionEntityInWorkflow(eea, workflowEntityStatus, workflowDestination, null, modifiedBy);
        } else {
            eea.addExecutionError(ExecutionErrors.UnknownCampaignMediumStatusChoice.name(), employeeStatusChoice);
        }
    }
    
   public CampaignMediumTransfer getCampaignMediumTransfer(UserVisit userVisit, CampaignMedium campaignMedium) {
        return campaignMediumTransferCache.getCampaignMediumTransfer(userVisit, campaignMedium);
    }

    public List<CampaignMediumTransfer> getCampaignMediumTransfers(UserVisit userVisit) {
        var campaignMediums = getCampaignMediums();
        List<CampaignMediumTransfer> campaignMediumTransfers = new ArrayList<>(campaignMediums.size());

        campaignMediums.forEach((campaignMedium) ->
                campaignMediumTransfers.add(campaignMediumTransferCache.getCampaignMediumTransfer(userVisit, campaignMedium))
        );

        return campaignMediumTransfers;
    }

    public CampaignMediumChoicesBean getCampaignMediumChoices(String defaultCampaignMediumChoice, Language language, boolean allowNullChoice) {
        var campaignMediums = getCampaignMediums();
        var size = campaignMediums.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultCampaignMediumChoice == null) {
                defaultValue = "";
            }
        }

        for(var campaignMedium : campaignMediums) {
            var campaignMediumDetail = campaignMedium.getLastDetail();

            var label = getBestCampaignMediumDescription(campaignMedium, language);
            var value = campaignMediumDetail.getCampaignMediumName();

            labels.add(label == null? value: label);
            values.add(value);

            var usingDefaultChoice = defaultCampaignMediumChoice != null && defaultCampaignMediumChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && campaignMediumDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new CampaignMediumChoicesBean(labels, values, defaultValue);
    }

    private void updateCampaignMediumFromValue(CampaignMediumDetailValue campaignMediumDetailValue, boolean checkDefault, BasePK updatedBy) {
        if(campaignMediumDetailValue.hasBeenModified()) {
            var campaignMedium = CampaignMediumFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     campaignMediumDetailValue.getCampaignMediumPK());
            var campaignMediumDetail = campaignMedium.getActiveDetailForUpdate();

            campaignMediumDetail.setThruTime(session.getStartTime());
            campaignMediumDetail.store();

            var campaignMediumPK = campaignMediumDetail.getCampaignMediumPK(); // Not updated
            var campaignMediumName = campaignMediumDetailValue.getCampaignMediumName();
            var value = campaignMediumDetailValue.getValue();
            var valueSha1Hash = Sha1Utils.getInstance().hash(value.toLowerCase(Locale.getDefault()));
            var isDefault = campaignMediumDetailValue.getIsDefault();
            var sortOrder = campaignMediumDetailValue.getSortOrder();

            if(checkDefault) {
                var defaultCampaignMedium = getDefaultCampaignMedium();
                var defaultFound = defaultCampaignMedium != null && !defaultCampaignMedium.equals(campaignMedium);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultCampaignMediumDetailValue = getDefaultCampaignMediumDetailValueForUpdate();

                    defaultCampaignMediumDetailValue.setIsDefault(false);
                    updateCampaignMediumFromValue(defaultCampaignMediumDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }

            campaignMediumDetail = CampaignMediumDetailFactory.getInstance().create(campaignMediumPK, campaignMediumName, valueSha1Hash, value, isDefault,
                    sortOrder, session.getStartTime(), Session.MAX_TIME);

            campaignMedium.setActiveDetail(campaignMediumDetail);
            campaignMedium.setLastDetail(campaignMediumDetail);

            sendEvent(campaignMediumPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public void updateCampaignMediumFromValue(CampaignMediumDetailValue campaignMediumDetailValue, BasePK updatedBy) {
        updateCampaignMediumFromValue(campaignMediumDetailValue, true, updatedBy);
    }

    private void deleteCampaignMedium(CampaignMedium campaignMedium, boolean checkDefault, BasePK deletedBy) {
        var campaignMediumDetail = campaignMedium.getLastDetailForUpdate();

        deleteUserVisitCampaignsByCampaignMedium(campaignMedium);
        deleteCampaignMediumDescriptionsByCampaignMedium(campaignMedium, deletedBy);

        campaignMediumDetail.setThruTime(session.getStartTime());
        campaignMedium.setActiveDetail(null);
        campaignMedium.store();

        if(checkDefault) {
            // Check for default, and pick one if necessary
            var defaultCampaignMedium = getDefaultCampaignMedium();

            if(defaultCampaignMedium == null) {
                var campaignMediums = getCampaignMediumsForUpdate();

                if(!campaignMediums.isEmpty()) {
                    var iter = campaignMediums.iterator();
                    if(iter.hasNext()) {
                        defaultCampaignMedium = iter.next();
                    }
                    var campaignMediumDetailValue = Objects.requireNonNull(defaultCampaignMedium).getLastDetailForUpdate().getCampaignMediumDetailValue().clone();

                    campaignMediumDetailValue.setIsDefault(true);
                    updateCampaignMediumFromValue(campaignMediumDetailValue, false, deletedBy);
                }
            }
        }

        sendEvent(campaignMedium.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    public void deleteCampaignMedium(CampaignMedium campaignMedium, BasePK deletedBy) {
        deleteCampaignMedium(campaignMedium, true, deletedBy);
    }

    private void deleteCampaignMediums(List<CampaignMedium> campaignMediums, boolean checkDefault, BasePK deletedBy) {
        campaignMediums.forEach((campaignMedium) -> deleteCampaignMedium(campaignMedium, checkDefault, deletedBy));
    }

    public void deleteCampaignMediums(List<CampaignMedium> campaignMediums, BasePK deletedBy) {
        deleteCampaignMediums(campaignMediums, true, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Campaign Medium Descriptions
    // --------------------------------------------------------------------------------

    public CampaignMediumDescription createCampaignMediumDescription(CampaignMedium campaignMedium, Language language, String description, BasePK createdBy) {
        var campaignMediumDescription = CampaignMediumDescriptionFactory.getInstance().create(campaignMedium, language, description,
                session.getStartTime(), Session.MAX_TIME);

        sendEvent(campaignMedium.getPrimaryKey(), EventTypes.MODIFY, campaignMediumDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return campaignMediumDescription;
    }

    private static final Map<EntityPermission, String> getCampaignMediumDescriptionQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM campaignmediumdescriptions " +
                "WHERE cmpgnmdmd_cmpgnmdm_campaignmediumid = ? AND cmpgnmdmd_lang_languageid = ? AND cmpgnmdmd_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM campaignmediumdescriptions " +
                "WHERE cmpgnmdmd_cmpgnmdm_campaignmediumid = ? AND cmpgnmdmd_lang_languageid = ? AND cmpgnmdmd_thrutime = ? " +
                "FOR UPDATE");
        getCampaignMediumDescriptionQueries = Collections.unmodifiableMap(queryMap);
    }

    private CampaignMediumDescription getCampaignMediumDescription(CampaignMedium campaignMedium, Language language, EntityPermission entityPermission) {
        return CampaignMediumDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, getCampaignMediumDescriptionQueries,
                campaignMedium, language, Session.MAX_TIME);
    }

    public CampaignMediumDescription getCampaignMediumDescription(CampaignMedium campaignMedium, Language language) {
        return getCampaignMediumDescription(campaignMedium, language, EntityPermission.READ_ONLY);
    }

    public CampaignMediumDescription getCampaignMediumDescriptionForUpdate(CampaignMedium campaignMedium, Language language) {
        return getCampaignMediumDescription(campaignMedium, language, EntityPermission.READ_WRITE);
    }

    public CampaignMediumDescriptionValue getCampaignMediumDescriptionValue(CampaignMediumDescription campaignMediumDescription) {
        return campaignMediumDescription == null? null: campaignMediumDescription.getCampaignMediumDescriptionValue().clone();
    }

    public CampaignMediumDescriptionValue getCampaignMediumDescriptionValueForUpdate(CampaignMedium campaignMedium, Language language) {
        return getCampaignMediumDescriptionValue(getCampaignMediumDescriptionForUpdate(campaignMedium, language));
    }

    private static final Map<EntityPermission, String> getCampaignMediumDescriptionsByCampaignMediumQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM campaignmediumdescriptions, languages " +
                "WHERE cmpgnmdmd_cmpgnmdm_campaignmediumid = ? AND cmpgnmdmd_thrutime = ? AND cmpgnmdmd_lang_languageid = lang_languageid " +
                "ORDER BY lang_sortorder, lang_languageisoname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM campaignmediumdescriptions " +
                "WHERE cmpgnmdmd_cmpgnmdm_campaignmediumid = ? AND cmpgnmdmd_thrutime = ? " +
                "FOR UPDATE");
        getCampaignMediumDescriptionsByCampaignMediumQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<CampaignMediumDescription> getCampaignMediumDescriptionsByCampaignMedium(CampaignMedium campaignMedium, EntityPermission entityPermission) {
        return CampaignMediumDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, getCampaignMediumDescriptionsByCampaignMediumQueries,
                campaignMedium, Session.MAX_TIME);
    }

    public List<CampaignMediumDescription> getCampaignMediumDescriptionsByCampaignMedium(CampaignMedium campaignMedium) {
        return getCampaignMediumDescriptionsByCampaignMedium(campaignMedium, EntityPermission.READ_ONLY);
    }

    public List<CampaignMediumDescription> getCampaignMediumDescriptionsByCampaignMediumForUpdate(CampaignMedium campaignMedium) {
        return getCampaignMediumDescriptionsByCampaignMedium(campaignMedium, EntityPermission.READ_WRITE);
    }

    public String getBestCampaignMediumDescription(CampaignMedium campaignMedium, Language language) {
        String description;
        var campaignMediumDescription = getCampaignMediumDescription(campaignMedium, language);

        if(campaignMediumDescription == null && !language.getIsDefault()) {
            campaignMediumDescription = getCampaignMediumDescription(campaignMedium, partyControl.getDefaultLanguage());
        }

        if(campaignMediumDescription == null) {
            description = campaignMedium.getLastDetail().getCampaignMediumName();
        } else {
            description = campaignMediumDescription.getDescription();
        }

        return description;
    }

    public CampaignMediumDescriptionTransfer getCampaignMediumDescriptionTransfer(UserVisit userVisit, CampaignMediumDescription campaignMediumDescription) {
        return campaignMediumDescriptionTransferCache.getCampaignMediumDescriptionTransfer(userVisit, campaignMediumDescription);
    }

    public List<CampaignMediumDescriptionTransfer> getCampaignMediumDescriptionTransfersByCampaignMedium(UserVisit userVisit, CampaignMedium campaignMedium) {
        var campaignMediumDescriptions = getCampaignMediumDescriptionsByCampaignMedium(campaignMedium);
        List<CampaignMediumDescriptionTransfer> campaignMediumDescriptionTransfers = new ArrayList<>(campaignMediumDescriptions.size());

        campaignMediumDescriptions.forEach((campaignMediumDescription) ->
                campaignMediumDescriptionTransfers.add(campaignMediumDescriptionTransferCache.getCampaignMediumDescriptionTransfer(userVisit, campaignMediumDescription))
        );

        return campaignMediumDescriptionTransfers;
    }

    public void updateCampaignMediumDescriptionFromValue(CampaignMediumDescriptionValue campaignMediumDescriptionValue, BasePK updatedBy) {
        if(campaignMediumDescriptionValue.hasBeenModified()) {
            var campaignMediumDescription = CampaignMediumDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    campaignMediumDescriptionValue.getPrimaryKey());

            campaignMediumDescription.setThruTime(session.getStartTime());
            campaignMediumDescription.store();

            var campaignMedium = campaignMediumDescription.getCampaignMedium();
            var language = campaignMediumDescription.getLanguage();
            var description = campaignMediumDescriptionValue.getDescription();

            campaignMediumDescription = CampaignMediumDescriptionFactory.getInstance().create(campaignMedium, language, description,
                    session.getStartTime(), Session.MAX_TIME);

            sendEvent(campaignMedium.getPrimaryKey(), EventTypes.MODIFY, campaignMediumDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteCampaignMediumDescription(CampaignMediumDescription campaignMediumDescription, BasePK deletedBy) {
        campaignMediumDescription.setThruTime(session.getStartTime());

        sendEvent(campaignMediumDescription.getCampaignMediumPK(), EventTypes.MODIFY, campaignMediumDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }

    public void deleteCampaignMediumDescriptionsByCampaignMedium(CampaignMedium campaignMedium, BasePK deletedBy) {
        var campaignMediumDescriptions = getCampaignMediumDescriptionsByCampaignMediumForUpdate(campaignMedium);

        campaignMediumDescriptions.forEach((campaignMediumDescription) -> 
                deleteCampaignMediumDescription(campaignMediumDescription, deletedBy)
        );
    }

    // --------------------------------------------------------------------------------
    //   Campaign Terms
    // --------------------------------------------------------------------------------

    public CampaignTerm createCampaignTerm(String value, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var sequenceControl = Session.getModelController(SequenceControl.class);
        var sequence = sequenceControl.getDefaultSequenceUsingNames(SequenceTypes.CAMPAIGN_TERM.name());
        var campaignTermName = SequenceGeneratorLogic.getInstance().getNextSequenceValue(sequence);
        
        return createCampaignTerm(campaignTermName, value, isDefault, sortOrder, createdBy);
    }
    
    public CampaignTerm createCampaignTerm(String campaignTermName, String value, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var valueSha1Hash = Sha1Utils.getInstance().hash(value.toLowerCase(Locale.getDefault()));
        var defaultCampaignTerm = getDefaultCampaignTerm();
        var defaultFound = defaultCampaignTerm != null;

        if(defaultFound && isDefault) {
            var defaultCampaignTermDetailValue = getDefaultCampaignTermDetailValueForUpdate();

            defaultCampaignTermDetailValue.setIsDefault(false);
            updateCampaignTermFromValue(defaultCampaignTermDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var campaignTerm = CampaignTermFactory.getInstance().create();
        var campaignTermDetail = CampaignTermDetailFactory.getInstance().create(campaignTerm, campaignTermName, valueSha1Hash, value,
                isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);

        // Convert to R/W
        campaignTerm = CampaignTermFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, campaignTerm.getPrimaryKey());
        campaignTerm.setActiveDetail(campaignTermDetail);
        campaignTerm.setLastDetail(campaignTermDetail);
        campaignTerm.store();

        var campaignTermPK = campaignTerm.getPrimaryKey();
        sendEvent(campaignTermPK, EventTypes.CREATE, null, null, createdBy);

        var entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
        var entityInstance = entityInstanceControl.getEntityInstanceByBasePK(campaignTermPK);
        workflowControl.addEntityToWorkflowUsingNames(null, CampaignTermStatusConstants.Workflow_CAMPAIGN_TERM_STATUS,
                CampaignTermStatusConstants.WorkflowEntrance_NEW_ACTIVE, entityInstance, null, null, createdBy);

        return campaignTerm;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.CampaignTerm */
    public CampaignTerm getCampaignTermByEntityInstance(EntityInstance entityInstance) {
        var pk = new CampaignTermPK(entityInstance.getEntityUniqueId());
        var campaignTerm = CampaignTermFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY, pk);

        return campaignTerm;
    }

    private static final Map<EntityPermission, String> getCampaignTermByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM campaignterms, campaigntermdetails " +
                "WHERE cmpgntrm_activedetailid = cmpgntrmdt_campaigntermdetailid " +
                "AND cmpgntrmdt_campaigntermname = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM campaignterms, campaigntermdetails " +
                "WHERE cmpgntrm_activedetailid = cmpgntrmdt_campaigntermdetailid " +
                "AND cmpgntrmdt_campaigntermname = ? " +
                "FOR UPDATE");
        getCampaignTermByNameQueries = Collections.unmodifiableMap(queryMap);
    }

    private CampaignTerm getCampaignTermByName(String campaignTermName, EntityPermission entityPermission) {
        return CampaignTermFactory.getInstance().getEntityFromQuery(entityPermission, getCampaignTermByNameQueries, campaignTermName);
    }

    public CampaignTerm getCampaignTermByName(String campaignTermName) {
        return getCampaignTermByName(campaignTermName, EntityPermission.READ_ONLY);
    }

    public CampaignTerm getCampaignTermByNameForUpdate(String campaignTermName) {
        return getCampaignTermByName(campaignTermName, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getCampaignTermByValueQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM campaignterms, campaigntermdetails " +
                "WHERE cmpgntrm_activedetailid = cmpgntrmdt_campaigntermdetailid " +
                "AND cmpgntrmdt_valuesha1hash = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM campaignterms, campaigntermdetails " +
                "WHERE cmpgntrm_activedetailid = cmpgntrmdt_campaigntermdetailid " +
                "AND cmpgntrmdt_valuesha1hash = ? " +
                "FOR UPDATE");
        getCampaignTermByValueQueries = Collections.unmodifiableMap(queryMap);
    }

    private CampaignTerm getCampaignTermByValue(String value, EntityPermission entityPermission) {
        return CampaignTermFactory.getInstance().getEntityFromQuery(entityPermission, getCampaignTermByValueQueries, 
                Sha1Utils.getInstance().hash(value.toLowerCase(Locale.getDefault())));
    }

    public CampaignTerm getCampaignTermByValue(String value) {
        return getCampaignTermByValue(value, EntityPermission.READ_ONLY);
    }

    public CampaignTerm getCampaignTermByValueForUpdate(String value) {
        return getCampaignTermByValue(value, EntityPermission.READ_WRITE);
    }

    public CampaignTermDetailValue getCampaignTermDetailValueForUpdate(CampaignTerm campaignTerm) {
        return campaignTerm == null? null: campaignTerm.getLastDetailForUpdate().getCampaignTermDetailValue().clone();
    }

    public CampaignTermDetailValue getCampaignTermDetailValueByNameForUpdate(String campaignTermName) {
        return getCampaignTermDetailValueForUpdate(getCampaignTermByNameForUpdate(campaignTermName));
    }

    private static final Map<EntityPermission, String> getDefaultCampaignTermQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM campaignterms, campaigntermdetails " +
                "WHERE cmpgntrm_activedetailid = cmpgntrmdt_campaigntermdetailid " +
                "AND cmpgntrmdt_isdefault = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM campaignterms, campaigntermdetails " +
                "WHERE cmpgntrm_activedetailid = cmpgntrmdt_campaigntermdetailid " +
                "AND cmpgntrmdt_isdefault = 1 " +
                "FOR UPDATE");
        getDefaultCampaignTermQueries = Collections.unmodifiableMap(queryMap);
    }

    private CampaignTerm getDefaultCampaignTerm(EntityPermission entityPermission) {
        return CampaignTermFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultCampaignTermQueries);
    }

    public CampaignTerm getDefaultCampaignTerm() {
        return getDefaultCampaignTerm(EntityPermission.READ_ONLY);
    }

    public CampaignTerm getDefaultCampaignTermForUpdate() {
        return getDefaultCampaignTerm(EntityPermission.READ_WRITE);
    }

    public CampaignTermDetailValue getDefaultCampaignTermDetailValueForUpdate() {
        return getDefaultCampaignTermForUpdate().getLastDetailForUpdate().getCampaignTermDetailValue().clone();
    }

    private static final Map<EntityPermission, String> getCampaignTermsQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM campaignterms, campaigntermdetails " +
                "WHERE cmpgntrm_activedetailid = cmpgntrmdt_campaigntermdetailid " +
                "ORDER BY cmpgntrmdt_sortorder, cmpgntrmdt_campaigntermname " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM campaignterms, campaigntermdetails " +
                "WHERE cmpgntrm_activedetailid = cmpgntrmdt_campaigntermdetailid " +
                "FOR UPDATE");
        getCampaignTermsQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<CampaignTerm> getCampaignTerms(EntityPermission entityPermission) {
        return CampaignTermFactory.getInstance().getEntitiesFromQuery(entityPermission, getCampaignTermsQueries);
    }

    public List<CampaignTerm> getCampaignTerms() {
        return getCampaignTerms(EntityPermission.READ_ONLY);
    }

    public List<CampaignTerm> getCampaignTermsForUpdate() {
        return getCampaignTerms(EntityPermission.READ_WRITE);
    }

    public CampaignTermStatusChoicesBean getCampaignTermStatusChoices(String defaultCampaignTermStatusChoice, Language language,
            boolean allowNullChoice, CampaignTerm campaignTerm, PartyPK partyPK) {
        var employeeStatusChoicesBean = new CampaignTermStatusChoicesBean();
        
        if(campaignTerm == null) {
            workflowControl.getWorkflowEntranceChoices(employeeStatusChoicesBean, defaultCampaignTermStatusChoice, language, allowNullChoice,
                    workflowControl.getWorkflowByName(CampaignTermStatusConstants.Workflow_CAMPAIGN_TERM_STATUS), partyPK);
        } else {
            var entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
            var entityInstance = entityInstanceControl.getEntityInstanceByBasePK(campaignTerm.getPrimaryKey());
            var workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceUsingNames(CampaignTermStatusConstants.Workflow_CAMPAIGN_TERM_STATUS,
                    entityInstance);
            
            workflowControl.getWorkflowDestinationChoices(employeeStatusChoicesBean, defaultCampaignTermStatusChoice, language, allowNullChoice,
                    workflowEntityStatus.getWorkflowStep(), partyPK);
        }
        
        return employeeStatusChoicesBean;
    }
    
    public void setCampaignTermStatus(ExecutionErrorAccumulator eea, Party party, String employeeStatusChoice, PartyPK modifiedBy) {
        var entityInstance = getEntityInstanceByBaseEntity(party);
        var workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceForUpdateUsingNames(CampaignTermStatusConstants.Workflow_CAMPAIGN_TERM_STATUS,
                entityInstance);
        var workflowDestination = employeeStatusChoice == null? null:
            workflowControl.getWorkflowDestinationByName(workflowEntityStatus.getWorkflowStep(), employeeStatusChoice);
        
        if(workflowDestination != null || employeeStatusChoice == null) {
            workflowControl.transitionEntityInWorkflow(eea, workflowEntityStatus, workflowDestination, null, modifiedBy);
        } else {
            eea.addExecutionError(ExecutionErrors.UnknownCampaignTermStatusChoice.name(), employeeStatusChoice);
        }
    }
    
   public CampaignTermTransfer getCampaignTermTransfer(UserVisit userVisit, CampaignTerm campaignTerm) {
        return campaignTermTransferCache.getCampaignTermTransfer(userVisit, campaignTerm);
    }

    public List<CampaignTermTransfer> getCampaignTermTransfers(UserVisit userVisit) {
        var campaignTerms = getCampaignTerms();
        List<CampaignTermTransfer> campaignTermTransfers = new ArrayList<>(campaignTerms.size());

        campaignTerms.forEach((campaignTerm) ->
                campaignTermTransfers.add(campaignTermTransferCache.getCampaignTermTransfer(userVisit, campaignTerm))
        );

        return campaignTermTransfers;
    }

    public CampaignTermChoicesBean getCampaignTermChoices(String defaultCampaignTermChoice, Language language, boolean allowNullChoice) {
        var campaignTerms = getCampaignTerms();
        var size = campaignTerms.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultCampaignTermChoice == null) {
                defaultValue = "";
            }
        }

        for(var campaignTerm : campaignTerms) {
            var campaignTermDetail = campaignTerm.getLastDetail();

            var label = getBestCampaignTermDescription(campaignTerm, language);
            var value = campaignTermDetail.getCampaignTermName();

            labels.add(label == null? value: label);
            values.add(value);

            var usingDefaultChoice = defaultCampaignTermChoice != null && defaultCampaignTermChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && campaignTermDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new CampaignTermChoicesBean(labels, values, defaultValue);
    }

    private void updateCampaignTermFromValue(CampaignTermDetailValue campaignTermDetailValue, boolean checkDefault, BasePK updatedBy) {
        if(campaignTermDetailValue.hasBeenModified()) {
            var campaignTerm = CampaignTermFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     campaignTermDetailValue.getCampaignTermPK());
            var campaignTermDetail = campaignTerm.getActiveDetailForUpdate();

            campaignTermDetail.setThruTime(session.getStartTime());
            campaignTermDetail.store();

            var campaignTermPK = campaignTermDetail.getCampaignTermPK(); // Not updated
            var campaignTermName = campaignTermDetailValue.getCampaignTermName();
            var value = campaignTermDetailValue.getValue();
            var valueSha1Hash = Sha1Utils.getInstance().hash(value.toLowerCase(Locale.getDefault()));
            var isDefault = campaignTermDetailValue.getIsDefault();
            var sortOrder = campaignTermDetailValue.getSortOrder();

            if(checkDefault) {
                var defaultCampaignTerm = getDefaultCampaignTerm();
                var defaultFound = defaultCampaignTerm != null && !defaultCampaignTerm.equals(campaignTerm);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultCampaignTermDetailValue = getDefaultCampaignTermDetailValueForUpdate();

                    defaultCampaignTermDetailValue.setIsDefault(false);
                    updateCampaignTermFromValue(defaultCampaignTermDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }

            campaignTermDetail = CampaignTermDetailFactory.getInstance().create(campaignTermPK, campaignTermName, valueSha1Hash, value, isDefault,
                    sortOrder, session.getStartTime(), Session.MAX_TIME);

            campaignTerm.setActiveDetail(campaignTermDetail);
            campaignTerm.setLastDetail(campaignTermDetail);

            sendEvent(campaignTermPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public void updateCampaignTermFromValue(CampaignTermDetailValue campaignTermDetailValue, BasePK updatedBy) {
        updateCampaignTermFromValue(campaignTermDetailValue, true, updatedBy);
    }

    private void deleteCampaignTerm(CampaignTerm campaignTerm, boolean checkDefault, BasePK deletedBy) {
        var campaignTermDetail = campaignTerm.getLastDetailForUpdate();

        deleteUserVisitCampaignsByCampaignTerm(campaignTerm);
        deleteCampaignTermDescriptionsByCampaignTerm(campaignTerm, deletedBy);

        campaignTermDetail.setThruTime(session.getStartTime());
        campaignTerm.setActiveDetail(null);
        campaignTerm.store();

        if(checkDefault) {
            // Check for default, and pick one if necessary
            var defaultCampaignTerm = getDefaultCampaignTerm();

            if(defaultCampaignTerm == null) {
                var campaignTerms = getCampaignTermsForUpdate();

                if(!campaignTerms.isEmpty()) {
                    var iter = campaignTerms.iterator();
                    if(iter.hasNext()) {
                        defaultCampaignTerm = iter.next();
                    }
                    var campaignTermDetailValue = Objects.requireNonNull(defaultCampaignTerm).getLastDetailForUpdate().getCampaignTermDetailValue().clone();

                    campaignTermDetailValue.setIsDefault(true);
                    updateCampaignTermFromValue(campaignTermDetailValue, false, deletedBy);
                }
            }
        }

        sendEvent(campaignTerm.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    public void deleteCampaignTerm(CampaignTerm campaignTerm, BasePK deletedBy) {
        deleteCampaignTerm(campaignTerm, true, deletedBy);
    }

    private void deleteCampaignTerms(List<CampaignTerm> campaignTerms, boolean checkDefault, BasePK deletedBy) {
        campaignTerms.forEach((campaignTerm) -> deleteCampaignTerm(campaignTerm, checkDefault, deletedBy));
    }

    public void deleteCampaignTerms(List<CampaignTerm> campaignTerms, BasePK deletedBy) {
        deleteCampaignTerms(campaignTerms, true, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Campaign Term Descriptions
    // --------------------------------------------------------------------------------

    public CampaignTermDescription createCampaignTermDescription(CampaignTerm campaignTerm, Language language, String description, BasePK createdBy) {
        var campaignTermDescription = CampaignTermDescriptionFactory.getInstance().create(campaignTerm, language, description,
                session.getStartTime(), Session.MAX_TIME);

        sendEvent(campaignTerm.getPrimaryKey(), EventTypes.MODIFY, campaignTermDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return campaignTermDescription;
    }

    private static final Map<EntityPermission, String> getCampaignTermDescriptionQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM campaigntermdescriptions " +
                "WHERE cmpgntrmd_cmpgntrm_campaigntermid = ? AND cmpgntrmd_lang_languageid = ? AND cmpgntrmd_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM campaigntermdescriptions " +
                "WHERE cmpgntrmd_cmpgntrm_campaigntermid = ? AND cmpgntrmd_lang_languageid = ? AND cmpgntrmd_thrutime = ? " +
                "FOR UPDATE");
        getCampaignTermDescriptionQueries = Collections.unmodifiableMap(queryMap);
    }

    private CampaignTermDescription getCampaignTermDescription(CampaignTerm campaignTerm, Language language, EntityPermission entityPermission) {
        return CampaignTermDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, getCampaignTermDescriptionQueries,
                campaignTerm, language, Session.MAX_TIME);
    }

    public CampaignTermDescription getCampaignTermDescription(CampaignTerm campaignTerm, Language language) {
        return getCampaignTermDescription(campaignTerm, language, EntityPermission.READ_ONLY);
    }

    public CampaignTermDescription getCampaignTermDescriptionForUpdate(CampaignTerm campaignTerm, Language language) {
        return getCampaignTermDescription(campaignTerm, language, EntityPermission.READ_WRITE);
    }

    public CampaignTermDescriptionValue getCampaignTermDescriptionValue(CampaignTermDescription campaignTermDescription) {
        return campaignTermDescription == null? null: campaignTermDescription.getCampaignTermDescriptionValue().clone();
    }

    public CampaignTermDescriptionValue getCampaignTermDescriptionValueForUpdate(CampaignTerm campaignTerm, Language language) {
        return getCampaignTermDescriptionValue(getCampaignTermDescriptionForUpdate(campaignTerm, language));
    }

    private static final Map<EntityPermission, String> getCampaignTermDescriptionsByCampaignTermQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM campaigntermdescriptions, languages " +
                "WHERE cmpgntrmd_cmpgntrm_campaigntermid = ? AND cmpgntrmd_thrutime = ? AND cmpgntrmd_lang_languageid = lang_languageid " +
                "ORDER BY lang_sortorder, lang_languageisoname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM campaigntermdescriptions " +
                "WHERE cmpgntrmd_cmpgntrm_campaigntermid = ? AND cmpgntrmd_thrutime = ? " +
                "FOR UPDATE");
        getCampaignTermDescriptionsByCampaignTermQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<CampaignTermDescription> getCampaignTermDescriptionsByCampaignTerm(CampaignTerm campaignTerm, EntityPermission entityPermission) {
        return CampaignTermDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, getCampaignTermDescriptionsByCampaignTermQueries,
                campaignTerm, Session.MAX_TIME);
    }

    public List<CampaignTermDescription> getCampaignTermDescriptionsByCampaignTerm(CampaignTerm campaignTerm) {
        return getCampaignTermDescriptionsByCampaignTerm(campaignTerm, EntityPermission.READ_ONLY);
    }

    public List<CampaignTermDescription> getCampaignTermDescriptionsByCampaignTermForUpdate(CampaignTerm campaignTerm) {
        return getCampaignTermDescriptionsByCampaignTerm(campaignTerm, EntityPermission.READ_WRITE);
    }

    public String getBestCampaignTermDescription(CampaignTerm campaignTerm, Language language) {
        String description;
        var campaignTermDescription = getCampaignTermDescription(campaignTerm, language);

        if(campaignTermDescription == null && !language.getIsDefault()) {
            campaignTermDescription = getCampaignTermDescription(campaignTerm, partyControl.getDefaultLanguage());
        }

        if(campaignTermDescription == null) {
            description = campaignTerm.getLastDetail().getCampaignTermName();
        } else {
            description = campaignTermDescription.getDescription();
        }

        return description;
    }

    public CampaignTermDescriptionTransfer getCampaignTermDescriptionTransfer(UserVisit userVisit, CampaignTermDescription campaignTermDescription) {
        return campaignTermDescriptionTransferCache.getCampaignTermDescriptionTransfer(userVisit, campaignTermDescription);
    }

    public List<CampaignTermDescriptionTransfer> getCampaignTermDescriptionTransfersByCampaignTerm(UserVisit userVisit, CampaignTerm campaignTerm) {
        var campaignTermDescriptions = getCampaignTermDescriptionsByCampaignTerm(campaignTerm);
        List<CampaignTermDescriptionTransfer> campaignTermDescriptionTransfers = new ArrayList<>(campaignTermDescriptions.size());

        campaignTermDescriptions.forEach((campaignTermDescription) ->
                campaignTermDescriptionTransfers.add(campaignTermDescriptionTransferCache.getCampaignTermDescriptionTransfer(userVisit, campaignTermDescription))
        );

        return campaignTermDescriptionTransfers;
    }

    public void updateCampaignTermDescriptionFromValue(CampaignTermDescriptionValue campaignTermDescriptionValue, BasePK updatedBy) {
        if(campaignTermDescriptionValue.hasBeenModified()) {
            var campaignTermDescription = CampaignTermDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    campaignTermDescriptionValue.getPrimaryKey());

            campaignTermDescription.setThruTime(session.getStartTime());
            campaignTermDescription.store();

            var campaignTerm = campaignTermDescription.getCampaignTerm();
            var language = campaignTermDescription.getLanguage();
            var description = campaignTermDescriptionValue.getDescription();

            campaignTermDescription = CampaignTermDescriptionFactory.getInstance().create(campaignTerm, language, description,
                    session.getStartTime(), Session.MAX_TIME);

            sendEvent(campaignTerm.getPrimaryKey(), EventTypes.MODIFY, campaignTermDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteCampaignTermDescription(CampaignTermDescription campaignTermDescription, BasePK deletedBy) {
        campaignTermDescription.setThruTime(session.getStartTime());

        sendEvent(campaignTermDescription.getCampaignTermPK(), EventTypes.MODIFY, campaignTermDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }

    public void deleteCampaignTermDescriptionsByCampaignTerm(CampaignTerm campaignTerm, BasePK deletedBy) {
        var campaignTermDescriptions = getCampaignTermDescriptionsByCampaignTermForUpdate(campaignTerm);

        campaignTermDescriptions.forEach((campaignTermDescription) -> 
                deleteCampaignTermDescription(campaignTermDescription, deletedBy)
        );
    }

    // --------------------------------------------------------------------------------
    //   Campaign Contents
    // --------------------------------------------------------------------------------

    public CampaignContent createCampaignContent(String value, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var sequenceControl = Session.getModelController(SequenceControl.class);
        var sequence = sequenceControl.getDefaultSequenceUsingNames(SequenceTypes.CAMPAIGN_CONTENT.name());
        var campaignContentName = SequenceGeneratorLogic.getInstance().getNextSequenceValue(sequence);
        
        return createCampaignContent(campaignContentName, value, isDefault, sortOrder, createdBy);
    }
    
    public CampaignContent createCampaignContent(String campaignContentName, String value, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var valueSha1Hash = Sha1Utils.getInstance().hash(value.toLowerCase(Locale.getDefault()));
        var defaultCampaignContent = getDefaultCampaignContent();
        var defaultFound = defaultCampaignContent != null;

        if(defaultFound && isDefault) {
            var defaultCampaignContentDetailValue = getDefaultCampaignContentDetailValueForUpdate();

            defaultCampaignContentDetailValue.setIsDefault(false);
            updateCampaignContentFromValue(defaultCampaignContentDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var campaignContent = CampaignContentFactory.getInstance().create();
        var campaignContentDetail = CampaignContentDetailFactory.getInstance().create(campaignContent, campaignContentName, valueSha1Hash, value,
                isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);

        // Convert to R/W
        campaignContent = CampaignContentFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, campaignContent.getPrimaryKey());
        campaignContent.setActiveDetail(campaignContentDetail);
        campaignContent.setLastDetail(campaignContentDetail);
        campaignContent.store();

        var campaignContentPK = campaignContent.getPrimaryKey();
        sendEvent(campaignContentPK, EventTypes.CREATE, null, null, createdBy);

        var entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
        var entityInstance = entityInstanceControl.getEntityInstanceByBasePK(campaignContentPK);
        workflowControl.addEntityToWorkflowUsingNames(null, CampaignContentStatusConstants.Workflow_CAMPAIGN_CONTENT_STATUS,
                CampaignContentStatusConstants.WorkflowEntrance_NEW_ACTIVE, entityInstance, null, null, createdBy);

        return campaignContent;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.CampaignContent */
    public CampaignContent getCampaignContentByEntityInstance(EntityInstance entityInstance) {
        var pk = new CampaignContentPK(entityInstance.getEntityUniqueId());
        var campaignContent = CampaignContentFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY, pk);

        return campaignContent;
    }

    private static final Map<EntityPermission, String> getCampaignContentByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM campaigncontents, campaigncontentdetails " +
                "WHERE cmpgncnt_activedetailid = cmpgncntdt_campaigncontentdetailid " +
                "AND cmpgncntdt_campaigncontentname = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM campaigncontents, campaigncontentdetails " +
                "WHERE cmpgncnt_activedetailid = cmpgncntdt_campaigncontentdetailid " +
                "AND cmpgncntdt_campaigncontentname = ? " +
                "FOR UPDATE");
        getCampaignContentByNameQueries = Collections.unmodifiableMap(queryMap);
    }

    private CampaignContent getCampaignContentByName(String campaignContentName, EntityPermission entityPermission) {
        return CampaignContentFactory.getInstance().getEntityFromQuery(entityPermission, getCampaignContentByNameQueries, campaignContentName);
    }

    public CampaignContent getCampaignContentByName(String campaignContentName) {
        return getCampaignContentByName(campaignContentName, EntityPermission.READ_ONLY);
    }

    public CampaignContent getCampaignContentByNameForUpdate(String campaignContentName) {
        return getCampaignContentByName(campaignContentName, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getCampaignContentByValueQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM campaigncontents, campaigncontentdetails " +
                "WHERE cmpgncnt_activedetailid = cmpgncntdt_campaigncontentdetailid " +
                "AND cmpgncntdt_valuesha1hash = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM campaigncontents, campaigncontentdetails " +
                "WHERE cmpgncnt_activedetailid = cmpgncntdt_campaigncontentdetailid " +
                "AND cmpgncntdt_valuesha1hash = ? " +
                "FOR UPDATE");
        getCampaignContentByValueQueries = Collections.unmodifiableMap(queryMap);
    }

    private CampaignContent getCampaignContentByValue(String value, EntityPermission entityPermission) {
        return CampaignContentFactory.getInstance().getEntityFromQuery(entityPermission, getCampaignContentByValueQueries, 
                Sha1Utils.getInstance().hash(value.toLowerCase(Locale.getDefault())));
    }

    public CampaignContent getCampaignContentByValue(String value) {
        return getCampaignContentByValue(value, EntityPermission.READ_ONLY);
    }

    public CampaignContent getCampaignContentByValueForUpdate(String value) {
        return getCampaignContentByValue(value, EntityPermission.READ_WRITE);
    }

    public CampaignContentDetailValue getCampaignContentDetailValueForUpdate(CampaignContent campaignContent) {
        return campaignContent == null? null: campaignContent.getLastDetailForUpdate().getCampaignContentDetailValue().clone();
    }

    public CampaignContentDetailValue getCampaignContentDetailValueByNameForUpdate(String campaignContentName) {
        return getCampaignContentDetailValueForUpdate(getCampaignContentByNameForUpdate(campaignContentName));
    }

    private static final Map<EntityPermission, String> getDefaultCampaignContentQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM campaigncontents, campaigncontentdetails " +
                "WHERE cmpgncnt_activedetailid = cmpgncntdt_campaigncontentdetailid " +
                "AND cmpgncntdt_isdefault = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM campaigncontents, campaigncontentdetails " +
                "WHERE cmpgncnt_activedetailid = cmpgncntdt_campaigncontentdetailid " +
                "AND cmpgncntdt_isdefault = 1 " +
                "FOR UPDATE");
        getDefaultCampaignContentQueries = Collections.unmodifiableMap(queryMap);
    }

    private CampaignContent getDefaultCampaignContent(EntityPermission entityPermission) {
        return CampaignContentFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultCampaignContentQueries);
    }

    public CampaignContent getDefaultCampaignContent() {
        return getDefaultCampaignContent(EntityPermission.READ_ONLY);
    }

    public CampaignContent getDefaultCampaignContentForUpdate() {
        return getDefaultCampaignContent(EntityPermission.READ_WRITE);
    }

    public CampaignContentDetailValue getDefaultCampaignContentDetailValueForUpdate() {
        return getDefaultCampaignContentForUpdate().getLastDetailForUpdate().getCampaignContentDetailValue().clone();
    }

    private static final Map<EntityPermission, String> getCampaignContentsQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM campaigncontents, campaigncontentdetails " +
                "WHERE cmpgncnt_activedetailid = cmpgncntdt_campaigncontentdetailid " +
                "ORDER BY cmpgncntdt_sortorder, cmpgncntdt_campaigncontentname " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM campaigncontents, campaigncontentdetails " +
                "WHERE cmpgncnt_activedetailid = cmpgncntdt_campaigncontentdetailid " +
                "FOR UPDATE");
        getCampaignContentsQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<CampaignContent> getCampaignContents(EntityPermission entityPermission) {
        return CampaignContentFactory.getInstance().getEntitiesFromQuery(entityPermission, getCampaignContentsQueries);
    }

    public List<CampaignContent> getCampaignContents() {
        return getCampaignContents(EntityPermission.READ_ONLY);
    }

    public List<CampaignContent> getCampaignContentsForUpdate() {
        return getCampaignContents(EntityPermission.READ_WRITE);
    }

    public CampaignContentStatusChoicesBean getCampaignContentStatusChoices(String defaultCampaignContentStatusChoice, Language language,
            boolean allowNullChoice, CampaignContent campaignContent, PartyPK partyPK) {
        var employeeStatusChoicesBean = new CampaignContentStatusChoicesBean();
        
        if(campaignContent == null) {
            workflowControl.getWorkflowEntranceChoices(employeeStatusChoicesBean, defaultCampaignContentStatusChoice, language, allowNullChoice,
                    workflowControl.getWorkflowByName(CampaignContentStatusConstants.Workflow_CAMPAIGN_CONTENT_STATUS), partyPK);
        } else {
            var entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
            var entityInstance = entityInstanceControl.getEntityInstanceByBasePK(campaignContent.getPrimaryKey());
            var workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceUsingNames(CampaignContentStatusConstants.Workflow_CAMPAIGN_CONTENT_STATUS,
                    entityInstance);
            
            workflowControl.getWorkflowDestinationChoices(employeeStatusChoicesBean, defaultCampaignContentStatusChoice, language, allowNullChoice,
                    workflowEntityStatus.getWorkflowStep(), partyPK);
        }
        
        return employeeStatusChoicesBean;
    }
    
    public void setCampaignContentStatus(ExecutionErrorAccumulator eea, Party party, String employeeStatusChoice, PartyPK modifiedBy) {
        var entityInstance = getEntityInstanceByBaseEntity(party);
        var workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceForUpdateUsingNames(CampaignContentStatusConstants.Workflow_CAMPAIGN_CONTENT_STATUS,
                entityInstance);
        var workflowDestination = employeeStatusChoice == null? null:
            workflowControl.getWorkflowDestinationByName(workflowEntityStatus.getWorkflowStep(), employeeStatusChoice);
        
        if(workflowDestination != null || employeeStatusChoice == null) {
            workflowControl.transitionEntityInWorkflow(eea, workflowEntityStatus, workflowDestination, null, modifiedBy);
        } else {
            eea.addExecutionError(ExecutionErrors.UnknownCampaignContentStatusChoice.name(), employeeStatusChoice);
        }
    }
    
   public CampaignContentTransfer getCampaignContentTransfer(UserVisit userVisit, CampaignContent campaignContent) {
        return campaignContentTransferCache.getCampaignContentTransfer(userVisit, campaignContent);
    }

    public List<CampaignContentTransfer> getCampaignContentTransfers(UserVisit userVisit) {
        var campaignContents = getCampaignContents();
        List<CampaignContentTransfer> campaignContentTransfers = new ArrayList<>(campaignContents.size());

        campaignContents.forEach((campaignContent) ->
                campaignContentTransfers.add(campaignContentTransferCache.getCampaignContentTransfer(userVisit, campaignContent))
        );

        return campaignContentTransfers;
    }

    public CampaignContentChoicesBean getCampaignContentChoices(String defaultCampaignContentChoice, Language language, boolean allowNullChoice) {
        var campaignContents = getCampaignContents();
        var size = campaignContents.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultCampaignContentChoice == null) {
                defaultValue = "";
            }
        }

        for(var campaignContent : campaignContents) {
            var campaignContentDetail = campaignContent.getLastDetail();

            var label = getBestCampaignContentDescription(campaignContent, language);
            var value = campaignContentDetail.getCampaignContentName();

            labels.add(label == null? value: label);
            values.add(value);

            var usingDefaultChoice = defaultCampaignContentChoice != null && defaultCampaignContentChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && campaignContentDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new CampaignContentChoicesBean(labels, values, defaultValue);
    }

    private void updateCampaignContentFromValue(CampaignContentDetailValue campaignContentDetailValue, boolean checkDefault, BasePK updatedBy) {
        if(campaignContentDetailValue.hasBeenModified()) {
            var campaignContent = CampaignContentFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     campaignContentDetailValue.getCampaignContentPK());
            var campaignContentDetail = campaignContent.getActiveDetailForUpdate();

            campaignContentDetail.setThruTime(session.getStartTime());
            campaignContentDetail.store();

            var campaignContentPK = campaignContentDetail.getCampaignContentPK(); // Not updated
            var campaignContentName = campaignContentDetailValue.getCampaignContentName();
            var value = campaignContentDetailValue.getValue();
            var valueSha1Hash = Sha1Utils.getInstance().hash(value.toLowerCase(Locale.getDefault()));
            var isDefault = campaignContentDetailValue.getIsDefault();
            var sortOrder = campaignContentDetailValue.getSortOrder();

            if(checkDefault) {
                var defaultCampaignContent = getDefaultCampaignContent();
                var defaultFound = defaultCampaignContent != null && !defaultCampaignContent.equals(campaignContent);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultCampaignContentDetailValue = getDefaultCampaignContentDetailValueForUpdate();

                    defaultCampaignContentDetailValue.setIsDefault(false);
                    updateCampaignContentFromValue(defaultCampaignContentDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }

            campaignContentDetail = CampaignContentDetailFactory.getInstance().create(campaignContentPK, campaignContentName, valueSha1Hash, value, isDefault,
                    sortOrder, session.getStartTime(), Session.MAX_TIME);

            campaignContent.setActiveDetail(campaignContentDetail);
            campaignContent.setLastDetail(campaignContentDetail);

            sendEvent(campaignContentPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public void updateCampaignContentFromValue(CampaignContentDetailValue campaignContentDetailValue, BasePK updatedBy) {
        updateCampaignContentFromValue(campaignContentDetailValue, true, updatedBy);
    }

    private void deleteCampaignContent(CampaignContent campaignContent, boolean checkDefault, BasePK deletedBy) {
        var campaignContentDetail = campaignContent.getLastDetailForUpdate();

        deleteUserVisitCampaignsByCampaignContent(campaignContent);
        deleteCampaignContentDescriptionsByCampaignContent(campaignContent, deletedBy);

        campaignContentDetail.setThruTime(session.getStartTime());
        campaignContent.setActiveDetail(null);
        campaignContent.store();

        if(checkDefault) {
            // Check for default, and pick one if necessary
            var defaultCampaignContent = getDefaultCampaignContent();

            if(defaultCampaignContent == null) {
                var campaignContents = getCampaignContentsForUpdate();

                if(!campaignContents.isEmpty()) {
                    var iter = campaignContents.iterator();
                    if(iter.hasNext()) {
                        defaultCampaignContent = iter.next();
                    }
                    var campaignContentDetailValue = Objects.requireNonNull(defaultCampaignContent).getLastDetailForUpdate().getCampaignContentDetailValue().clone();

                    campaignContentDetailValue.setIsDefault(true);
                    updateCampaignContentFromValue(campaignContentDetailValue, false, deletedBy);
                }
            }
        }

        sendEvent(campaignContent.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    public void deleteCampaignContent(CampaignContent campaignContent, BasePK deletedBy) {
        deleteCampaignContent(campaignContent, true, deletedBy);
    }

    private void deleteCampaignContents(List<CampaignContent> campaignContents, boolean checkDefault, BasePK deletedBy) {
        campaignContents.forEach((campaignContent) -> deleteCampaignContent(campaignContent, checkDefault, deletedBy));
    }

    public void deleteCampaignContents(List<CampaignContent> campaignContents, BasePK deletedBy) {
        deleteCampaignContents(campaignContents, true, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Campaign Content Descriptions
    // --------------------------------------------------------------------------------

    public CampaignContentDescription createCampaignContentDescription(CampaignContent campaignContent, Language language, String description, BasePK createdBy) {
        var campaignContentDescription = CampaignContentDescriptionFactory.getInstance().create(campaignContent, language, description,
                session.getStartTime(), Session.MAX_TIME);

        sendEvent(campaignContent.getPrimaryKey(), EventTypes.MODIFY, campaignContentDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return campaignContentDescription;
    }

    private static final Map<EntityPermission, String> getCampaignContentDescriptionQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM campaigncontentdescriptions " +
                "WHERE cmpgncntd_cmpgncnt_campaigncontentid = ? AND cmpgncntd_lang_languageid = ? AND cmpgncntd_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM campaigncontentdescriptions " +
                "WHERE cmpgncntd_cmpgncnt_campaigncontentid = ? AND cmpgncntd_lang_languageid = ? AND cmpgncntd_thrutime = ? " +
                "FOR UPDATE");
        getCampaignContentDescriptionQueries = Collections.unmodifiableMap(queryMap);
    }

    private CampaignContentDescription getCampaignContentDescription(CampaignContent campaignContent, Language language, EntityPermission entityPermission) {
        return CampaignContentDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, getCampaignContentDescriptionQueries,
                campaignContent, language, Session.MAX_TIME);
    }

    public CampaignContentDescription getCampaignContentDescription(CampaignContent campaignContent, Language language) {
        return getCampaignContentDescription(campaignContent, language, EntityPermission.READ_ONLY);
    }

    public CampaignContentDescription getCampaignContentDescriptionForUpdate(CampaignContent campaignContent, Language language) {
        return getCampaignContentDescription(campaignContent, language, EntityPermission.READ_WRITE);
    }

    public CampaignContentDescriptionValue getCampaignContentDescriptionValue(CampaignContentDescription campaignContentDescription) {
        return campaignContentDescription == null? null: campaignContentDescription.getCampaignContentDescriptionValue().clone();
    }

    public CampaignContentDescriptionValue getCampaignContentDescriptionValueForUpdate(CampaignContent campaignContent, Language language) {
        return getCampaignContentDescriptionValue(getCampaignContentDescriptionForUpdate(campaignContent, language));
    }

    private static final Map<EntityPermission, String> getCampaignContentDescriptionsByCampaignContentQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM campaigncontentdescriptions, languages " +
                "WHERE cmpgncntd_cmpgncnt_campaigncontentid = ? AND cmpgncntd_thrutime = ? AND cmpgncntd_lang_languageid = lang_languageid " +
                "ORDER BY lang_sortorder, lang_languageisoname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM campaigncontentdescriptions " +
                "WHERE cmpgncntd_cmpgncnt_campaigncontentid = ? AND cmpgncntd_thrutime = ? " +
                "FOR UPDATE");
        getCampaignContentDescriptionsByCampaignContentQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<CampaignContentDescription> getCampaignContentDescriptionsByCampaignContent(CampaignContent campaignContent, EntityPermission entityPermission) {
        return CampaignContentDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, getCampaignContentDescriptionsByCampaignContentQueries,
                campaignContent, Session.MAX_TIME);
    }

    public List<CampaignContentDescription> getCampaignContentDescriptionsByCampaignContent(CampaignContent campaignContent) {
        return getCampaignContentDescriptionsByCampaignContent(campaignContent, EntityPermission.READ_ONLY);
    }

    public List<CampaignContentDescription> getCampaignContentDescriptionsByCampaignContentForUpdate(CampaignContent campaignContent) {
        return getCampaignContentDescriptionsByCampaignContent(campaignContent, EntityPermission.READ_WRITE);
    }

    public String getBestCampaignContentDescription(CampaignContent campaignContent, Language language) {
        String description;
        var campaignContentDescription = getCampaignContentDescription(campaignContent, language);

        if(campaignContentDescription == null && !language.getIsDefault()) {
            campaignContentDescription = getCampaignContentDescription(campaignContent, partyControl.getDefaultLanguage());
        }

        if(campaignContentDescription == null) {
            description = campaignContent.getLastDetail().getCampaignContentName();
        } else {
            description = campaignContentDescription.getDescription();
        }

        return description;
    }

    public CampaignContentDescriptionTransfer getCampaignContentDescriptionTransfer(UserVisit userVisit, CampaignContentDescription campaignContentDescription) {
        return campaignContentDescriptionTransferCache.getCampaignContentDescriptionTransfer(userVisit, campaignContentDescription);
    }

    public List<CampaignContentDescriptionTransfer> getCampaignContentDescriptionTransfersByCampaignContent(UserVisit userVisit, CampaignContent campaignContent) {
        var campaignContentDescriptions = getCampaignContentDescriptionsByCampaignContent(campaignContent);
        List<CampaignContentDescriptionTransfer> campaignContentDescriptionTransfers = new ArrayList<>(campaignContentDescriptions.size());

        campaignContentDescriptions.forEach((campaignContentDescription) ->
                campaignContentDescriptionTransfers.add(campaignContentDescriptionTransferCache.getCampaignContentDescriptionTransfer(userVisit, campaignContentDescription))
        );

        return campaignContentDescriptionTransfers;
    }

    public void updateCampaignContentDescriptionFromValue(CampaignContentDescriptionValue campaignContentDescriptionValue, BasePK updatedBy) {
        if(campaignContentDescriptionValue.hasBeenModified()) {
            var campaignContentDescription = CampaignContentDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    campaignContentDescriptionValue.getPrimaryKey());

            campaignContentDescription.setThruTime(session.getStartTime());
            campaignContentDescription.store();

            var campaignContent = campaignContentDescription.getCampaignContent();
            var language = campaignContentDescription.getLanguage();
            var description = campaignContentDescriptionValue.getDescription();

            campaignContentDescription = CampaignContentDescriptionFactory.getInstance().create(campaignContent, language, description,
                    session.getStartTime(), Session.MAX_TIME);

            sendEvent(campaignContent.getPrimaryKey(), EventTypes.MODIFY, campaignContentDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteCampaignContentDescription(CampaignContentDescription campaignContentDescription, BasePK deletedBy) {
        campaignContentDescription.setThruTime(session.getStartTime());

        sendEvent(campaignContentDescription.getCampaignContentPK(), EventTypes.MODIFY, campaignContentDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }

    public void deleteCampaignContentDescriptionsByCampaignContent(CampaignContent campaignContent, BasePK deletedBy) {
        var campaignContentDescriptions = getCampaignContentDescriptionsByCampaignContentForUpdate(campaignContent);

        campaignContentDescriptions.forEach((campaignContentDescription) -> 
                deleteCampaignContentDescription(campaignContentDescription, deletedBy)
        );
    }

    // --------------------------------------------------------------------------------
    //   User Visit Tracks
    // --------------------------------------------------------------------------------

    public UserVisitCampaign createUserVisitCampaign(UserVisit userVisit, Long time, Campaign campaign, CampaignSource campaignSource,
            CampaignMedium campaignMedium, CampaignTerm campaignTerm, CampaignContent campaignContent) {
        var userControl = Session.getModelController(UserControl.class);
        var userVisitStatus = userControl.getUserVisitStatusForUpdate(userVisit);
        Integer userVisitCampaignSequence = userVisitStatus.getUserVisitCampaignSequence()+ 1;
        
        userVisitStatus.setUserVisitCampaignSequence(userVisitCampaignSequence);
        
        return createUserVisitCampaign(userVisit, userVisitCampaignSequence, time, campaign, campaignSource, campaignMedium, campaignTerm, campaignContent);
    }

    public UserVisitCampaign createUserVisitCampaign(UserVisit userVisit, Integer userVisitCampaignSequence, Long time, Campaign campaign,
            CampaignSource campaignSource, CampaignMedium campaignMedium, CampaignTerm campaignTerm, CampaignContent campaignContent) {
        var userVisitCampaign = UserVisitCampaignFactory.getInstance().create(userVisit, userVisitCampaignSequence, time, campaign,
                campaignSource, campaignMedium, campaignTerm, campaignContent, session.getStartTime(), Session.MAX_TIME);

        return userVisitCampaign;
    }
    
    public Integer getMinimumUserVisitCampaignSequence(UserVisit userVisit) {
        return session.queryForInteger(
                "SELECT MIN(uviscmpgn_uservisitcampaignsequence) " +
                "FROM uservisitcampaigns " +
                "WHERE uviscmpgn_uvis_uservisitid = ? AND uviscmpgn_thrutime = ?",
                userVisit, Session.MAX_TIME);
    }

    private static final Map<EntityPermission, String> getUserVisitCampaignQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM uservisitcampaigns " +
                "WHERE uviscmpgn_uvis_uservisitid = ? AND uviscmpgn_uservisitcampaignsequence = ? AND uviscmpgn_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM uservisitcampaigns " +
                "WHERE uviscmpgn_uvis_uservisitid = ? AND uviscmpgn_uservisitcampaignsequence = ? AND uviscmpgn_thrutime = ? " +
                "FOR UPDATE");
        getUserVisitCampaignQueries = Collections.unmodifiableMap(queryMap);
    }

    private UserVisitCampaign getUserVisitCampaign(UserVisit userVisit, Integer userVisitCampaignSequence, EntityPermission entityPermission) {
        return UserVisitCampaignFactory.getInstance().getEntityFromQuery(entityPermission, getUserVisitCampaignQueries,
                userVisit, userVisitCampaignSequence, Session.MAX_TIME);
    }

    public UserVisitCampaign getUserVisitCampaign(UserVisit userVisit, Integer userVisitCampaignSequence) {
        return getUserVisitCampaign(userVisit, userVisitCampaignSequence, EntityPermission.READ_ONLY);
    }

    public UserVisitCampaign getUserVisitCampaignForUpdate(UserVisit userVisit, Integer userVisitCampaignSequence) {
        return getUserVisitCampaign(userVisit, userVisitCampaignSequence, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getUserVisitCampaignsByUserVisitQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM uservisitcampaigns " +
                "WHERE uviscmpgn_uvis_uservisitid = ? AND uviscmpgn_thrutime = ? " +
                "ORDER BY uviscmpgn_uservisitcampaignsequence");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM uservisitcampaigns " +
                "WHERE uviscmpgn_uvis_uservisitid = ? AND uviscmpgn_thrutime = ? " +
                "FOR UPDATE");
        getUserVisitCampaignsByUserVisitQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<UserVisitCampaign> getUserVisitCampaignsByUserVisit(UserVisit userVisit, EntityPermission entityPermission) {
        return UserVisitCampaignFactory.getInstance().getEntitiesFromQuery(entityPermission, getUserVisitCampaignsByUserVisitQueries,
                userVisit, Session.MAX_TIME);
    }

    public List<UserVisitCampaign> getUserVisitCampaignsByUserVisit(UserVisit userVisit) {
        return getUserVisitCampaignsByUserVisit(userVisit, EntityPermission.READ_ONLY);
    }

    public List<UserVisitCampaign> getUserVisitCampaignsByUserVisitForUpdate(UserVisit userVisit) {
        return getUserVisitCampaignsByUserVisit(userVisit, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getUserVisitCampaignsByCampaignQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(1);

        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM uservisitcampaigns " +
                "WHERE uviscmpgn_cmpgn_campaignid = ? AND uviscmpgn_thrutime = ? " +
                "FOR UPDATE");
        getUserVisitCampaignsByCampaignQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<UserVisitCampaign> getUserVisitCampaignsByCampaign(Campaign campaign, EntityPermission entityPermission) {
        return UserVisitCampaignFactory.getInstance().getEntitiesFromQuery(entityPermission, getUserVisitCampaignsByCampaignQueries,
                campaign, Session.MAX_TIME);
    }

    public List<UserVisitCampaign> getUserVisitCampaignsByCampaignForUpdate(Campaign campaign) {
        return getUserVisitCampaignsByCampaign(campaign, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getUserVisitCampaignsByCampaignSourceQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(1);

        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM uservisitcampaigns " +
                "WHERE uviscmpgn_cmpgnsrc_campaignsourceid = ? AND uviscmpgn_thrutime = ? " +
                "FOR UPDATE");
        getUserVisitCampaignsByCampaignSourceQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<UserVisitCampaign> getUserVisitCampaignsByCampaignSource(CampaignSource campaignSource, EntityPermission entityPermission) {
        return UserVisitCampaignFactory.getInstance().getEntitiesFromQuery(entityPermission, getUserVisitCampaignsByCampaignSourceQueries,
                campaignSource, Session.MAX_TIME);
    }

    public List<UserVisitCampaign> getUserVisitCampaignsByCampaignSourceForUpdate(CampaignSource campaignSource) {
        return getUserVisitCampaignsByCampaignSource(campaignSource, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getUserVisitCampaignsByCampaignMediumQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(1);

        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM uservisitcampaigns " +
                "WHERE uviscmpgn_cmpgnmdm_campaignmediumid = ? AND uviscmpgn_thrutime = ? " +
                "FOR UPDATE");
        getUserVisitCampaignsByCampaignMediumQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<UserVisitCampaign> getUserVisitCampaignsByCampaignMedium(CampaignMedium campaignMedium, EntityPermission entityPermission) {
        return UserVisitCampaignFactory.getInstance().getEntitiesFromQuery(entityPermission, getUserVisitCampaignsByCampaignMediumQueries,
                campaignMedium, Session.MAX_TIME);
    }

    public List<UserVisitCampaign> getUserVisitCampaignsByCampaignMediumForUpdate(CampaignMedium campaignMedium) {
        return getUserVisitCampaignsByCampaignMedium(campaignMedium, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getUserVisitCampaignsByCampaignTermQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(1);

        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM uservisitcampaigns " +
                "WHERE uviscmpgn_cmpgntrm_campaigntermid = ? AND uviscmpgn_thrutime = ? " +
                "FOR UPDATE");
        getUserVisitCampaignsByCampaignTermQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<UserVisitCampaign> getUserVisitCampaignsByCampaignTerm(CampaignTerm campaignTerm, EntityPermission entityPermission) {
        return UserVisitCampaignFactory.getInstance().getEntitiesFromQuery(entityPermission, getUserVisitCampaignsByCampaignTermQueries,
                campaignTerm, Session.MAX_TIME);
    }

    public List<UserVisitCampaign> getUserVisitCampaignsByCampaignTermForUpdate(CampaignTerm campaignTerm) {
        return getUserVisitCampaignsByCampaignTerm(campaignTerm, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getUserVisitCampaignsByCampaignContentQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(1);

        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM uservisitcampaigns " +
                "WHERE uviscmpgn_cmpgncnt_campaigncontentid = ? AND uviscmpgn_thrutime = ? " +
                "FOR UPDATE");
        getUserVisitCampaignsByCampaignContentQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<UserVisitCampaign> getUserVisitCampaignsByCampaignContent(CampaignContent campaignContent, EntityPermission entityPermission) {
        return UserVisitCampaignFactory.getInstance().getEntitiesFromQuery(entityPermission, getUserVisitCampaignsByCampaignContentQueries,
                campaignContent, Session.MAX_TIME);
    }

    public List<UserVisitCampaign> getUserVisitCampaignsByCampaignContentForUpdate(CampaignContent campaignContent) {
        return getUserVisitCampaignsByCampaignContent(campaignContent, EntityPermission.READ_WRITE);
    }

    public UserVisitCampaignTransfer getUserVisitCampaignTransfer(UserVisit userVisit, UserVisitCampaign userVisitCampaign) {
        return userVisitCampaignTransferCache.getUserVisitCampaignTransfer(userVisit, userVisitCampaign);
    }

    public List<UserVisitCampaignTransfer> getUserVisitCampaignTransfers(UserVisit userVisit, Collection<UserVisitCampaign> userVisitCampaigns) {
        List<UserVisitCampaignTransfer> userVisitCampaignTransfers = new ArrayList<>(userVisitCampaigns.size());

        userVisitCampaigns.forEach((userVisitCampaign) ->
                userVisitCampaignTransfers.add(userVisitCampaignTransferCache.getUserVisitCampaignTransfer(userVisit, userVisitCampaign))
        );

        return userVisitCampaignTransfers;
    }
    
    public List<UserVisitCampaignTransfer> getUserVisitCampaignTransfersByUserVisit(UserVisit userVisit, UserVisit userVisitEntity) {
        return getUserVisitCampaignTransfers(userVisit, getUserVisitCampaignsByUserVisit(userVisitEntity));
    }
    
    public void deleteUserVisitCampaign(UserVisitCampaign userVisitCampaign) {
        userVisitCampaign.setThruTime(session.getStartTime());
        userVisitCampaign.store();
    }
    
    public void deleteUserVisitCampaigns(List<UserVisitCampaign> userVisitCampaigns) {
        userVisitCampaigns.forEach((userVisitCampaign) -> {
            deleteUserVisitCampaign(userVisitCampaign);
        });
    }
    
    public void deleteUserVisitCampaignsByUserVisit(UserVisit userVisit) {
        deleteUserVisitCampaigns(getUserVisitCampaignsByUserVisitForUpdate(userVisit));
    }
    
    public void deleteUserVisitCampaignsByCampaign(Campaign campaign) {
        deleteUserVisitCampaigns(getUserVisitCampaignsByCampaignForUpdate(campaign));
    }
    
    public void deleteUserVisitCampaignsByCampaignSource(CampaignSource campaignSource) {
        deleteUserVisitCampaigns(getUserVisitCampaignsByCampaignSourceForUpdate(campaignSource));
    }
    
    public void deleteUserVisitCampaignsByCampaignMedium(CampaignMedium campaignMedium) {
        deleteUserVisitCampaigns(getUserVisitCampaignsByCampaignMediumForUpdate(campaignMedium));
    }
    
    public void deleteUserVisitCampaignsByCampaignTerm(CampaignTerm campaignTerm) {
        deleteUserVisitCampaigns(getUserVisitCampaignsByCampaignTermForUpdate(campaignTerm));
    }
    
    public void deleteUserVisitCampaignsByCampaignContent(CampaignContent campaignContent) {
        deleteUserVisitCampaigns(getUserVisitCampaignsByCampaignContentForUpdate(campaignContent));
    }
    
    public void removeUserVisitCampaign(UserVisitCampaign userVisitCampaign) {
        userVisitCampaign.remove();
    }
    
    public void removeUserVisitCampaigns(List<UserVisitCampaign> userVisitCampaigns) {
        userVisitCampaigns.forEach((userVisitCampaign) -> {
            removeUserVisitCampaign(userVisitCampaign);
        });
    }
    
    public void removeUserVisitCampaignsByUserVisit(UserVisit userVisit) {
        removeUserVisitCampaigns(getUserVisitCampaignsByUserVisitForUpdate(userVisit));
    }
    
    public void removeUserVisitCampaignsByCampaign(Campaign campaign) {
        removeUserVisitCampaigns(getUserVisitCampaignsByCampaignForUpdate(campaign));
    }
    
    public void removeUserVisitCampaignsByCampaignSource(CampaignSource campaignSource) {
        removeUserVisitCampaigns(getUserVisitCampaignsByCampaignSourceForUpdate(campaignSource));
    }
    
    public void removeUserVisitCampaignsByCampaignMedium(CampaignMedium campaignMedium) {
        removeUserVisitCampaigns(getUserVisitCampaignsByCampaignMediumForUpdate(campaignMedium));
    }
    
    public void removeUserVisitCampaignsByCampaignTerm(CampaignTerm campaignTerm) {
        removeUserVisitCampaigns(getUserVisitCampaignsByCampaignTermForUpdate(campaignTerm));
    }
    
    public void removeUserVisitCampaignsByCampaignContent(CampaignContent campaignContent) {
        removeUserVisitCampaigns(getUserVisitCampaignsByCampaignContentForUpdate(campaignContent));
    }
    
}
