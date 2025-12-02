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

package com.echothree.control.user.inventory.server.command;

import com.echothree.control.user.inventory.common.edit.InventoryEditFactory;
import com.echothree.control.user.inventory.common.edit.LotAliasEdit;
import com.echothree.control.user.inventory.common.form.EditLotAliasForm;
import com.echothree.control.user.inventory.common.result.EditLotAliasResult;
import com.echothree.control.user.inventory.common.result.InventoryResultFactory;
import com.echothree.control.user.inventory.common.spec.LotAliasSpec;
import com.echothree.model.control.inventory.server.control.LotAliasControl;
import com.echothree.model.control.inventory.server.control.LotControl;
import com.echothree.model.control.inventory.server.logic.LotLogic;
import com.echothree.model.control.item.server.logic.ItemLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.inventory.server.entity.LotAlias;
import com.echothree.model.data.inventory.server.entity.LotAliasType;
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
public class EditLotAliasCommand
        extends BaseAbstractEditCommand<LotAliasSpec, LotAliasEdit, EditLotAliasResult, LotAlias, LotAlias> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.LotAliasType.name(), SecurityRoles.Edit.name())
                )))
        )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ItemName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LotIdentifier", FieldType.STRING, true, 1L, 40L),
                new FieldDefinition("LotAliasTypeName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Alias", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of EditLotAliasCommand */
    public EditLotAliasCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditLotAliasResult getResult() {
        return InventoryResultFactory.getEditLotAliasResult();
    }

    @Override
    public LotAliasEdit getEdit() {
        return InventoryEditFactory.getLotAliasEdit();
    }

    LotAliasType lotAliasType;
    
    @Override
    public LotAlias getEntity(EditLotAliasResult result) {
        LotAlias lotAlias = null;
        var item = ItemLogic.getInstance().getItemByName(this, spec.getItemName());

        if(!hasExecutionErrors()) {
            var lotIdentifier = spec.getLotIdentifier();
            var lot = LotLogic.getInstance().getLotByIdentifier(this, item, lotIdentifier);

            if(!hasExecutionErrors()) {
                var lotAliasControl = Session.getModelController(LotAliasControl.class);
                var lotAliasTypeName = spec.getLotAliasTypeName();

                lotAliasType = lotAliasControl.getLotAliasTypeByName(lotAliasTypeName);

                if(lotAliasType != null) {
                    if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                        lotAlias = lotAliasControl.getLotAlias(lot, lotAliasType);
                    } else { // EditMode.UPDATE
                        lotAlias = lotAliasControl.getLotAliasForUpdate(lot, lotAliasType);
                    }

                    if(lotAlias != null) {
                        result.setLotAlias(lotAliasControl.getLotAliasTransfer(getUserVisit(), lotAlias));
                    } else {
                        addExecutionError(ExecutionErrors.UnknownLotAlias.name(), item.getLastDetail().getItemName(),
                                lot.getLastDetail().getLotIdentifier(), lotAliasTypeName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownLotAliasTypeName.name(), lotAliasTypeName);
                }
            }
        }

        return lotAlias;
    }

    @Override
    public LotAlias getLockEntity(LotAlias lotAlias) {
        return lotAlias;
    }

    @Override
    public void fillInResult(EditLotAliasResult result, LotAlias lotAlias) {
        var lotAliasControl = Session.getModelController(LotAliasControl.class);

        result.setLotAlias(lotAliasControl.getLotAliasTransfer(getUserVisit(), lotAlias));
    }

    @Override
    public void doLock(LotAliasEdit edit, LotAlias lotAlias) {
        edit.setAlias(lotAlias.getAlias());
    }

    @Override
    public void canUpdate(LotAlias lotAlias) {
        var lotAliasControl = Session.getModelController(LotAliasControl.class);
        var alias = edit.getAlias();
        var duplicateLotAlias = lotAliasControl.getLotAliasByAlias(lotAliasType, alias);

        if(duplicateLotAlias != null && !lotAlias.equals(duplicateLotAlias)) {
            var lotAliasTypeDetail = lotAlias.getLotAliasType().getLastDetail();

            addExecutionError(ExecutionErrors.DuplicateLotAlias.name(), lotAliasTypeDetail.getLotAliasTypeName(), alias);
        }
    }

    @Override
    public void doUpdate(LotAlias lotAlias) {
        var lotAliasControl = Session.getModelController(LotAliasControl.class);
        var lotAliasValue = lotAliasControl.getLotAliasValue(lotAlias);

        lotAliasValue.setAlias(edit.getAlias());

        lotAliasControl.updateLotAliasFromValue(lotAliasValue, getPartyPK());
    }

}
