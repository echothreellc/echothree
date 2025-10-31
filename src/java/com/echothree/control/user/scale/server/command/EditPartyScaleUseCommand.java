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

package com.echothree.control.user.scale.server.command;

import com.echothree.control.user.scale.common.edit.PartyScaleUseEdit;
import com.echothree.control.user.scale.common.edit.ScaleEditFactory;
import com.echothree.control.user.scale.common.form.EditPartyScaleUseForm;
import com.echothree.control.user.scale.common.result.EditPartyScaleUseResult;
import com.echothree.control.user.scale.common.result.ScaleResultFactory;
import com.echothree.control.user.scale.common.spec.PartyScaleUseSpec;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.scale.server.control.ScaleControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.scale.server.entity.PartyScaleUse;
import com.echothree.model.data.scale.server.entity.Scale;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class EditPartyScaleUseCommand
        extends BaseAbstractEditCommand<PartyScaleUseSpec, PartyScaleUseEdit, EditPartyScaleUseResult, PartyScaleUse, Party> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.PartyScaleUse.name(), SecurityRoles.Edit.name())
                        )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PartyName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("ScaleUseTypeName", FieldType.ENTITY_NAME, true, null, null)
                ));

        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ScaleName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of EditPartyScaleUseCommand */
    public EditPartyScaleUseCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditPartyScaleUseResult getResult() {
        return ScaleResultFactory.getEditPartyScaleUseResult();
    }

    @Override
    public PartyScaleUseEdit getEdit() {
        return ScaleEditFactory.getPartyScaleUseEdit();
    }

    @Override
    public PartyScaleUse getEntity(EditPartyScaleUseResult result) {
        PartyScaleUse partyScaleUse = null;
        var partyName = spec.getPartyName();
        Party party;

        if(partyName != null) {
            var partyControl = Session.getModelController(PartyControl.class);

            party = partyControl.getPartyByName(partyName);

            if(party == null) {
                addExecutionError(ExecutionErrors.UnknownPartyName.name(), partyName);
            }
        } else {
            party = getParty();
        }

        if(!hasExecutionErrors()) {
            var scaleControl = Session.getModelController(ScaleControl.class);
            var scaleUseTypeName = spec.getScaleUseTypeName();
            var scaleUseType = scaleControl.getScaleUseTypeByName(scaleUseTypeName);

            if(scaleUseType != null) {
                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    partyScaleUse = scaleControl.getPartyScaleUse(party, scaleUseType);
                } else { // EditMode.UPDATE
                    partyScaleUse = scaleControl.getPartyScaleUseForUpdate(party, scaleUseType);
                }

                if(partyScaleUse == null) {
                    addExecutionError(ExecutionErrors.UnknownPartyScaleUse.name(), party.getLastDetail().getPartyName(), scaleUseTypeName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownScaleUseTypeName.name(), scaleUseTypeName);
            }
        }

        return partyScaleUse;
    }

    @Override
    public Party getLockEntity(PartyScaleUse partyScaleUse) {
        return partyScaleUse.getParty();
    }

    @Override
    public void fillInResult(EditPartyScaleUseResult result, PartyScaleUse partyScaleUse) {
        var scaleControl = Session.getModelController(ScaleControl.class);

        result.setPartyScaleUse(scaleControl.getPartyScaleUseTransfer(getUserVisit(), partyScaleUse));
    }

    @Override
    public void doLock(PartyScaleUseEdit edit, PartyScaleUse partyScaleUse) {
        edit.setScaleName(partyScaleUse.getScale().getLastDetail().getScaleName());
    }

    Scale scale;

    @Override
    public void canUpdate(PartyScaleUse partyScaleUse) {
        var scaleControl = Session.getModelController(ScaleControl.class);
        var scaleName = edit.getScaleName();

        scale = scaleControl.getScaleByName(scaleName);

        if(scale == null) {
            addExecutionError(ExecutionErrors.DuplicateScaleName.name(), scaleName);
        }
    }

    @Override
    public void doUpdate(PartyScaleUse partyScaleUse) {
        var scaleControl = Session.getModelController(ScaleControl.class);
        var partyScaleUseValue = scaleControl.getPartyScaleUseValue(partyScaleUse);

        partyScaleUseValue.setScalePK(scale.getPrimaryKey());

        scaleControl.updatePartyScaleUseFromValue(partyScaleUseValue, getPartyPK());
    }
    
}
