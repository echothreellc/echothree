// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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

package com.echothree.control.user.communication.server.server;

import com.echothree.control.user.communication.common.form.CreateCommunicationSourceForm;
import com.echothree.model.control.communication.common.CommunicationConstants;
import com.echothree.model.control.communication.server.control.CommunicationControl;
import com.echothree.model.control.selector.common.SelectorConstants;
import com.echothree.model.control.selector.server.control.SelectorControl;
import com.echothree.model.control.workeffort.common.workeffort.ReceiveCustomerEmailConstants;
import com.echothree.model.control.workeffort.common.workeffort.SendCustomerEmailConstants;
import com.echothree.model.control.workeffort.server.control.WorkEffortControl;
import com.echothree.model.data.communication.server.entity.CommunicationSource;
import com.echothree.model.data.communication.server.entity.CommunicationSourceType;
import com.echothree.model.data.core.server.entity.Server;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.selector.server.entity.Selector;
import com.echothree.model.data.selector.server.entity.SelectorKind;
import com.echothree.model.data.selector.server.entity.SelectorType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.workeffort.server.entity.WorkEffortScope;
import com.echothree.model.data.workeffort.server.entity.WorkEffortType;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.form.ValidationResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.validation.Validator;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CreateCommunicationSourceCommand
        extends BaseSimpleCommand<CreateCommunicationSourceForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> formEmailFieldDefinitions;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("CommunicationSourceName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("CommunicationSourceTypeName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
            new FieldDefinition("Description", FieldType.STRING, false, 1L, 80L)
        ));
        
        formEmailFieldDefinitions = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("ServerName", FieldType.HOST_NAME, true, null, 40L),
            new FieldDefinition("Username", FieldType.STRING, true, 1L, 80L),
            new FieldDefinition("Password", FieldType.STRING, true, 1L, 40L),
            new FieldDefinition("ReceiveWorkEffortScopeName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("SendWorkEffortScopeName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("ReviewEmployeeSelectorName", FieldType.ENTITY_NAME, false, null, null)
        ));
    }
    
    /** Creates a new instance of CreateCommunicationSourceCommand */
    public CreateCommunicationSourceCommand(UserVisitPK userVisitPK, CreateCommunicationSourceForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected ValidationResult validate() {
        Validator validator = new Validator(this);
        ValidationResult validationResult = validator.validate(form, FORM_FIELD_DEFINITIONS);
        
        if(!validationResult.getHasErrors()) {
            String communicationSourceTypeName = form.getCommunicationSourceTypeName();
            
            if(communicationSourceTypeName.equals(CommunicationConstants.CommunicationSourceType_EMAIL)) {
                validationResult = validator.validate(form, formEmailFieldDefinitions);
            }
        }
        
        return validationResult;
    }
    
    @Override
    protected BaseResult execute() {
        var communicationControl = Session.getModelController(CommunicationControl.class);
        String communicationSourceName = form.getCommunicationSourceName();
        CommunicationSource communicationSource = communicationControl.getCommunicationSourceByName(communicationSourceName);
        
        if(communicationSource == null) {
            String communicationSourceTypeName = form.getCommunicationSourceTypeName();
            CommunicationSourceType communicationSourceType = communicationControl.getCommunicationSourceTypeByName(communicationSourceTypeName);
            
            if(communicationSourceType != null) {
                PartyPK createdBy = getPartyPK();
                var sortOrder = Integer.valueOf(form.getSortOrder());
                var description = form.getDescription();
                
                communicationSourceTypeName = communicationSourceType.getCommunicationSourceTypeName();
                
                if(communicationSourceTypeName.equals(CommunicationConstants.CommunicationSourceType_EMAIL)) {
                    var coreControl = getCoreControl();
                    String serverName = form.getServerName();
                    Server server = coreControl.getServerByName(serverName);
                    
                    if(server != null) {
                        var workEffortControl = Session.getModelController(WorkEffortControl.class);
                        String receiveWorkEffortScopeName = form.getReceiveWorkEffortScopeName();
                        WorkEffortType workEffortType = workEffortControl.getWorkEffortTypeByName(ReceiveCustomerEmailConstants.WorkEffortType_RECEIVE_CUSTOMER_EMAIL);
                        WorkEffortScope receiveWorkEffortScope = workEffortControl.getWorkEffortScopeByName(workEffortType, receiveWorkEffortScopeName);
                        
                        if(receiveWorkEffortScope != null) {
                            String sendWorkEffortScopeName = form.getSendWorkEffortScopeName();
                            
                            workEffortType = workEffortControl.getWorkEffortTypeByName(SendCustomerEmailConstants.WorkEffortType_SEND_CUSTOMER_EMAIL);
                            WorkEffortScope sendWorkEffortScope = workEffortControl.getWorkEffortScopeByName(workEffortType, sendWorkEffortScopeName);
                            
                            if(sendWorkEffortScope != null) {
                                String reviewEmployeeSelectorName = form.getReviewEmployeeSelectorName();
                                Selector reviewEmployeeSelector = null;
                                
                                if(reviewEmployeeSelectorName != null) {
                                    var selectorControl = Session.getModelController(SelectorControl.class);
                                    SelectorKind selectorKind = selectorControl.getSelectorKindByName(SelectorConstants.SelectorKind_EMPLOYEE);
                                    
                                    if(selectorKind != null) {
                                        SelectorType selectorType = selectorControl.getSelectorTypeByName(selectorKind, SelectorConstants.SelectorType_EMAIL_REVIEW);
                                        
                                        if(selectorType != null) {
                                            reviewEmployeeSelector = selectorControl.getSelectorByName(selectorType, reviewEmployeeSelectorName);
                                        } else {
                                            addExecutionError(ExecutionErrors.UnknownSelectorTypeName.name(), SelectorConstants.SelectorType_EMAIL_REVIEW);
                                        }
                                    } else {
                                        addExecutionError(ExecutionErrors.UnknownSelectorKindName.name(), SelectorConstants.SelectorKind_EMPLOYEE);
                                    }
                                }
                                
                                if(reviewEmployeeSelectorName == null || reviewEmployeeSelector != null) {
                                    String username = form.getUsername();
                                    String password = form.getPassword();
                                    
                                    communicationSource = communicationControl.createCommunicationSource(communicationSourceName,
                                            communicationSourceType, sortOrder, createdBy);
                                    communicationControl.createCommunicationEmailSource(communicationSource, server, username, password,
                                            receiveWorkEffortScope, sendWorkEffortScope, reviewEmployeeSelector, createdBy);
                                } else {
                                    addExecutionError(ExecutionErrors.UnknownReviewEmployeeSelectorName.name(), reviewEmployeeSelectorName);
                                }
                            } else {
                                addExecutionError(ExecutionErrors.UnknownSendWorkEffortScopeName.name(), sendWorkEffortScopeName);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.UnknownReceiveWorkEffortScopeName.name(), receiveWorkEffortScopeName);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownServerName.name(), serverName);
                    }
                }
                
                if(communicationSource != null && description != null) {
                    communicationControl.createCommunicationSourceDescription(communicationSource, getPreferredLanguage(),
                            description, createdBy);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownCommunicationSourceTypeName.name(), communicationSourceTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.DuplicateCommunicationSourceName.name(), communicationSourceName);
        }
        
        return null;
    }
    
}
