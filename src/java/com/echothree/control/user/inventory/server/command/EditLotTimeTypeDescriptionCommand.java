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

package com.echothree.control.user.inventory.server.command;

import com.echothree.control.user.inventory.common.edit.InventoryEditFactory;
import com.echothree.control.user.inventory.common.edit.LotTimeTypeDescriptionEdit;
import com.echothree.control.user.inventory.common.form.EditLotTimeTypeDescriptionForm;
import com.echothree.control.user.inventory.common.result.EditLotTimeTypeDescriptionResult;
import com.echothree.control.user.inventory.common.result.InventoryResultFactory;
import com.echothree.control.user.inventory.common.spec.LotTimeTypeDescriptionSpec;
import com.echothree.model.control.inventory.server.control.LotTimeControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.inventory.server.entity.LotTimeType;
import com.echothree.model.data.inventory.server.entity.LotTimeTypeDescription;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
public class EditLotTimeTypeDescriptionCommand
        extends BaseAbstractEditCommand<LotTimeTypeDescriptionSpec, LotTimeTypeDescriptionEdit, EditLotTimeTypeDescriptionResult, LotTimeTypeDescription, LotTimeType> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.LotTimeType.name(), SecurityRoles.Description.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("LotTimeTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditLotTimeTypeDescriptionCommand */
    public EditLotTimeTypeDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditLotTimeTypeDescriptionResult getResult() {
        return InventoryResultFactory.getEditLotTimeTypeDescriptionResult();
    }

    @Override
    public LotTimeTypeDescriptionEdit getEdit() {
        return InventoryEditFactory.getLotTimeTypeDescriptionEdit();
    }

    @Override
    public LotTimeTypeDescription getEntity(EditLotTimeTypeDescriptionResult result) {
        var lotTimeControl = Session.getModelController(LotTimeControl.class);
        LotTimeTypeDescription lotTimeTypeDescription = null;
        var lotTimeTypeName = spec.getLotTimeTypeName();
        var lotTimeType = lotTimeControl.getLotTimeTypeByName(lotTimeTypeName);

        if(lotTimeType != null) {
            var partyControl = Session.getModelController(PartyControl.class);
            var languageIsoName = spec.getLanguageIsoName();
            var language = partyControl.getLanguageByIsoName(languageIsoName);

            if(language != null) {
                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    lotTimeTypeDescription = lotTimeControl.getLotTimeTypeDescription(lotTimeType, language);
                } else { // EditMode.UPDATE
                    lotTimeTypeDescription = lotTimeControl.getLotTimeTypeDescriptionForUpdate(lotTimeType, language);
                }

                if(lotTimeTypeDescription == null) {
                    addExecutionError(ExecutionErrors.UnknownLotTimeTypeDescription.name(), lotTimeTypeName, languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownLotTimeTypeName.name(), lotTimeTypeName);
        }

        return lotTimeTypeDescription;
    }

    @Override
    public LotTimeType getLockEntity(LotTimeTypeDescription lotTimeTypeDescription) {
        return lotTimeTypeDescription.getLotTimeType();
    }

    @Override
    public void fillInResult(EditLotTimeTypeDescriptionResult result, LotTimeTypeDescription lotTimeTypeDescription) {
        var lotTimeControl = Session.getModelController(LotTimeControl.class);

        result.setLotTimeTypeDescription(lotTimeControl.getLotTimeTypeDescriptionTransfer(getUserVisit(), lotTimeTypeDescription));
    }

    @Override
    public void doLock(LotTimeTypeDescriptionEdit edit, LotTimeTypeDescription lotTimeTypeDescription) {
        edit.setDescription(lotTimeTypeDescription.getDescription());
    }

    @Override
    public void doUpdate(LotTimeTypeDescription lotTimeTypeDescription) {
        var lotTimeControl = Session.getModelController(LotTimeControl.class);
        var lotTimeTypeDescriptionValue = lotTimeControl.getLotTimeTypeDescriptionValue(lotTimeTypeDescription);
        lotTimeTypeDescriptionValue.setDescription(edit.getDescription());

        lotTimeControl.updateLotTimeTypeDescriptionFromValue(lotTimeTypeDescriptionValue, getPartyPK());
    }
    
}
