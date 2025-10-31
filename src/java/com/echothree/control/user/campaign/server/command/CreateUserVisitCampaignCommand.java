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

package com.echothree.control.user.campaign.server.command;

import com.echothree.control.user.campaign.common.form.CreateUserVisitCampaignForm;
import com.echothree.model.control.campaign.server.control.CampaignControl;
import com.echothree.model.data.campaign.server.entity.Campaign;
import com.echothree.model.data.campaign.server.entity.CampaignContent;
import com.echothree.model.data.campaign.server.entity.CampaignMedium;
import com.echothree.model.data.campaign.server.entity.CampaignSource;
import com.echothree.model.data.campaign.server.entity.CampaignTerm;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.exception.PersistenceDatabaseException;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.PersistenceUtils;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class CreateUserVisitCampaignCommand
        extends BaseSimpleCommand<CreateUserVisitCampaignForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("CampaignValue", FieldType.STRING, false, null, null),
                new FieldDefinition("CampaignSourceValue", FieldType.STRING, false, null, null),
                new FieldDefinition("CampaignMediumValue", FieldType.STRING, false, null, null),
                new FieldDefinition("CampaignTermValue", FieldType.STRING, false, null, null),
                new FieldDefinition("CampaignContentValue", FieldType.STRING, false, null, null)
                ));
    }
    
    /** Creates a new instance of CreateCampaignCommand */
    public CreateUserVisitCampaignCommand() {
        super(null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var campaignValue = form.getCampaignValue();
        var campaignSourceValue = form.getCampaignSourceValue();
        var campaignMediumValue = form.getCampaignMediumValue();
        var campaignTermValue = form.getCampaignTermValue();
        var campaignContentValue = form.getCampaignContentValue();
        var parameterCount = (campaignValue == null ? 0 : 1) + (campaignSourceValue == null ? 0 : 1) + (campaignMediumValue == null ? 0 : 1)
                + (campaignTermValue == null ? 0 : 1) + (campaignContentValue == null ? 0 : 1);

        if(parameterCount > 0) {
            var campaignControl = Session.getModelController(CampaignControl.class);
            Campaign campaign = null;
            CampaignSource campaignSource = null;
            CampaignMedium campaignMedium = null;
            CampaignTerm campaignTerm = null;
            CampaignContent campaignContent = null;
            
            if(campaignValue != null) {
                campaign = campaignControl.getCampaignByValue(campaignValue);
                
                if(campaign == null) {
                    try {
                        campaign = campaignControl.createCampaign(campaignValue, false, 0, getPartyPK());
                    } catch(PersistenceDatabaseException pde) {
                        if(PersistenceUtils.getInstance().isIntegrityConstraintViolation(pde)) {
                            campaign = campaignControl.getCampaignByValue(campaignValue);
                            pde = null;
                        }

                        if(pde != null) {
                            throw pde;
                        }
                    }
                }
            }
            
            if(campaignSourceValue != null) {
                campaignSource = campaignControl.getCampaignSourceByValue(campaignSourceValue);
                
                if(campaignSource == null) {
                    try {
                        campaignSource = campaignControl.createCampaignSource(campaignSourceValue, false, 0, getPartyPK());
                    } catch(PersistenceDatabaseException pde) {
                        if(PersistenceUtils.getInstance().isIntegrityConstraintViolation(pde)) {
                            campaignSource = campaignControl.getCampaignSourceByValue(campaignSourceValue);
                            pde = null;
                        }

                        if(pde != null) {
                            throw pde;
                        }
                    }
                }
            }
            
            if(campaignMediumValue != null) {
                campaignMedium = campaignControl.getCampaignMediumByValue(campaignMediumValue);
                
                if(campaignMedium == null) {
                    try {
                        campaignMedium = campaignControl.createCampaignMedium(campaignMediumValue, false, 0, getPartyPK());
                    } catch(PersistenceDatabaseException pde) {
                        if(PersistenceUtils.getInstance().isIntegrityConstraintViolation(pde)) {
                            campaignMedium = campaignControl.getCampaignMediumByValue(campaignMediumValue);
                            pde = null;
                        }

                        if(pde != null) {
                            throw pde;
                        }
                    }
                }
            }
            
            if(campaignTermValue != null) {
                campaignTerm = campaignControl.getCampaignTermByValue(campaignTermValue);
                
                if(campaignTerm == null) {
                    try {
                        campaignTerm = campaignControl.createCampaignTerm(campaignTermValue, false, 0, getPartyPK());
                    } catch(PersistenceDatabaseException pde) {
                        if(PersistenceUtils.getInstance().isIntegrityConstraintViolation(pde)) {
                            campaignTerm = campaignControl.getCampaignTermByValue(campaignTermValue);
                            pde = null;
                        }

                        if(pde != null) {
                            throw pde;
                        }
                    }
                }
            }
            
            if(campaignContentValue != null) {
                campaignContent = campaignControl.getCampaignContentByValue(campaignContentValue);
                
                if(campaignContent == null) {
                    try {
                        campaignContent = campaignControl.createCampaignContent(campaignContentValue, false, 0, getPartyPK());
                    } catch(PersistenceDatabaseException pde) {
                        if(PersistenceUtils.getInstance().isIntegrityConstraintViolation(pde)) {
                            campaignContent = campaignControl.getCampaignContentByValue(campaignContentValue);
                            pde = null;
                        }

                        if(pde != null) {
                            throw pde;
                        }
                    }
                }
            }
            
            campaignControl.createUserVisitCampaign(getUserVisit(), session.START_TIME_LONG, campaign, campaignSource, campaignMedium, campaignTerm,
                    campaignContent);
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }
        
        return null;
    }
    
}
