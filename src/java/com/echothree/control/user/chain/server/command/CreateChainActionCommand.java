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

package com.echothree.control.user.chain.server.command;

import com.echothree.control.user.chain.common.form.CreateChainActionForm;
import com.echothree.model.control.chain.common.ChainConstants;
import com.echothree.model.control.chain.server.control.ChainControl;
import com.echothree.model.control.letter.server.control.LetterControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.uom.common.UomConstants;
import com.echothree.model.control.uom.server.control.UomControl;
import com.echothree.model.control.uom.server.util.Conversion;
import com.echothree.model.data.chain.server.entity.Chain;
import com.echothree.model.data.chain.server.entity.ChainAction;
import com.echothree.model.data.chain.server.entity.ChainActionSet;
import com.echothree.model.data.chain.server.entity.ChainActionType;
import com.echothree.model.data.chain.server.entity.ChainType;
import com.echothree.model.data.letter.server.entity.Letter;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.form.ValidationResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.validation.Validator;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class CreateChainActionCommand
        extends BaseSimpleCommand<CreateChainActionForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> letterFormFieldDefinitions;
    private final static List<FieldDefinition> surveyFormFieldDefinitions;
    private final static List<FieldDefinition> chainActionSetFormFieldDefinitions;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                    new SecurityRoleDefinition(SecurityRoleGroups.ChainAction.name(), SecurityRoles.Create.name())
                    )))
                )));

        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ChainKindName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ChainTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ChainName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ChainActionSetName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ChainActionName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ChainActionTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
        
        letterFormFieldDefinitions = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("LetterName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        surveyFormFieldDefinitions = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("SurveyName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        chainActionSetFormFieldDefinitions = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("NextChainActionSetName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("DelayTime", FieldType.UNSIGNED_LONG, true, null, null),
                new FieldDefinition("DelayTimeUnitOfMeasureTypeName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of CreateChainActionCommand */
    public CreateChainActionCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected ValidationResult validate() {
        var validator = new Validator(this);
        var validationResult = validator.validate(form, FORM_FIELD_DEFINITIONS);
        
        if(!validationResult.getHasErrors()) {
            var chainActionTypeName = form.getChainActionTypeName();
            
            if(chainActionTypeName.equals(ChainConstants.ChainActionType_LETTER)) {
                validationResult = validator.validate(form, letterFormFieldDefinitions);
            } else if(chainActionTypeName.equals(ChainConstants.ChainActionType_SURVEY)) {
                validationResult = validator.validate(form, surveyFormFieldDefinitions);
            } else if(chainActionTypeName.equals(ChainConstants.ChainActionType_CHAIN_ACTION_SET)) {
                validationResult = validator.validate(form, chainActionSetFormFieldDefinitions);
            }
        }
        
        return validationResult;
    }
    
    private abstract class BaseChainActionType {

        ChainControl chainControl;
        ChainActionType chainActionType;

        public BaseChainActionType(ChainControl chainControl, String chainActionTypeName) {
            this.chainControl = chainControl;
            chainActionType = chainControl.getChainActionTypeByName(chainActionTypeName);

            if(chainActionType == null) {
                addExecutionError(ExecutionErrors.UnknownChainActionTypeName.name(), chainActionTypeName);
            }
        }

        public abstract void execute(ChainAction chainAction, PartyPK partyPK);
    }
    
    private abstract class LetterComponentChainActionType
            extends BaseChainActionType {

        protected LetterControl letterControl = Session.getModelController(LetterControl.class);

        public LetterComponentChainActionType(ChainControl chainControl, String chainActionTypeName) {
            super(chainControl, chainActionTypeName);
        }
    }
    
    private class LetterChainActionType
        extends LetterComponentChainActionType {
        private Letter letter = null;
        
        public LetterChainActionType(ChainControl chainControl, ChainType chainType) {
            super(chainControl, ChainConstants.ChainActionType_LETTER);
            
            if(!hasExecutionErrors()) {
                var letterName = form.getLetterName();
                
                letter = letterControl.getLetterByName(chainType, letterName);
                
                if(letter == null) {
                    addExecutionError(ExecutionErrors.UnknownLetterName.name(), letterName);
                }
            }
        }
        
        @Override
        public void execute(ChainAction chainAction, PartyPK partyPK) {
            chainControl.createChainActionLetter(chainAction, letter, partyPK);
        }
    }
    
    private class ChainActionSetChainActionType
        extends BaseChainActionType {
        ChainActionSet nextChainActionSet = null;
        Long delayTime = null;
        
        public ChainActionSetChainActionType(ChainControl chainControl, Chain chain) {
            super(chainControl, ChainConstants.ChainActionType_CHAIN_ACTION_SET);
            
            if(!hasExecutionErrors()) {
                var nextChainActionSetName = form.getNextChainActionSetName();
                
                nextChainActionSet = chainControl.getChainActionSetByName(chain, nextChainActionSetName);
                
                if(nextChainActionSet != null) {
                    var uomControl = Session.getModelController(UomControl.class);
                    var timeUnitOfMeasureKind = uomControl.getUnitOfMeasureKindByUnitOfMeasureKindUseTypeUsingNames(UomConstants.UnitOfMeasureKindUseType_TIME);

                    if(timeUnitOfMeasureKind != null) {
                        var delayTimeUnitOfMeasureTypeName = form.getDelayTimeUnitOfMeasureTypeName();
                        var delayTimeUnitOfMeasureType = uomControl.getUnitOfMeasureTypeByName(timeUnitOfMeasureKind, delayTimeUnitOfMeasureTypeName);

                        if(delayTimeUnitOfMeasureType != null) {
                            delayTime = new Conversion(uomControl, delayTimeUnitOfMeasureType, Long.valueOf(form.getDelayTime())).convertToLowestUnitOfMeasureType().getQuantity();
                        } else {
                            addExecutionError(ExecutionErrors.UnknownRetainUserVisitsTimeUnitOfMeasureTypeName.name(), delayTimeUnitOfMeasureTypeName);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownTimeUnitOfMeasureKind.name());
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownNextChainActionSetName.name(), nextChainActionSetName);
                }
            }
        }
        
        @Override
        public void execute(ChainAction chainAction, PartyPK partyPK) {
            chainControl.createChainActionChainActionSet(chainAction, nextChainActionSet, delayTime, partyPK);
        }
    }
    
    @Override
    protected BaseResult execute() {
        var chainControl = Session.getModelController(ChainControl.class);
        var chainKindName = form.getChainKindName();
        var chainKind = chainControl.getChainKindByName(chainKindName);

        if(chainKind != null) {
            var chainTypeName = form.getChainTypeName();
            var chainType = chainControl.getChainTypeByName(chainKind, chainTypeName);

            if(chainType != null) {
                var chainName = form.getChainName();
                var chain = chainControl.getChainByName(chainType, chainName);

                if(chain != null) {
                    var chainActionSetName = form.getChainActionSetName();
                    var chainActionSet = chainControl.getChainActionSetByName(chain, chainActionSetName);

                    if(chainActionSet != null) {
                        var chainActionName = form.getChainActionName();
                        var chainAction = chainControl.getChainActionByName(chainActionSet, chainActionName);

                        if(chainAction == null) {
                            var chainActionTypeName = form.getChainActionTypeName();
                            var chainActionType = chainControl.getChainActionTypeByName(chainActionTypeName);

                            if(chainActionType != null) {
                                BaseChainActionType baseChainActionType = null;
                                
                                if(chainActionTypeName.equals(ChainConstants.ChainActionType_LETTER)) {
                                    baseChainActionType = new LetterChainActionType(chainControl, chainType);
                                } else if(chainActionTypeName.equals(ChainConstants.ChainActionType_SURVEY)) {
                                    // TODO
                                } else if(chainActionTypeName.equals(ChainConstants.ChainActionType_CHAIN_ACTION_SET)) {
                                    baseChainActionType = new ChainActionSetChainActionType(chainControl, chain);
                                }
                                
                                if(!hasExecutionErrors()) {
                                    var partyPK = getPartyPK();
                                    var sortOrder = Integer.valueOf(form.getSortOrder());
                                    var description = form.getDescription();

                                    chainAction = chainControl.createChainAction(chainActionSet, chainActionName, chainActionType, sortOrder, partyPK);

                                            if(baseChainActionType != null) { // TODO: Remove test once all Types are implemented.
                                                baseChainActionType.execute(chainAction, partyPK);
                                            }

                                    if(description != null) {
                                        chainControl.createChainActionDescription(chainAction, getPreferredLanguage(), description, partyPK);
                                    }
                                }
                            } else {
                                    addExecutionError(ExecutionErrors.UnknownChainActionTypeName.name(), chainActionTypeName);
                            }
                        } else {
                                addExecutionError(ExecutionErrors.DuplicateChainActionName.name(), chainKindName, chainTypeName, chainName, chainActionSetName, chainActionName);
                        }
                    } else {
                            addExecutionError(ExecutionErrors.UnknownChainActionSetName.name(), chainKindName, chainTypeName, chainName, chainActionSetName);
                    }
                } else {
                        addExecutionError(ExecutionErrors.UnknownChainName.name(), chainKindName, chainTypeName, chainName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownChainTypeName.name(), chainKindName, chainTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownChainKindName.name(), chainKindName);
        }

        return null;
    }
    
}
