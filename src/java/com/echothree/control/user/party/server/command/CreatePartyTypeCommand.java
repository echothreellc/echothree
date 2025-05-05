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

package com.echothree.control.user.party.server.command;

import com.echothree.control.user.party.common.form.CreatePartyTypeForm;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.sequence.server.control.SequenceControl;
import com.echothree.model.data.party.server.entity.PartyType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CreatePartyTypeCommand
        extends BaseSimpleCommand<CreatePartyTypeForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("PartyTypeName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("ParentPartyTypeName", FieldType.ENTITY_NAME, false, null, null),
            new FieldDefinition("BillingAccountSequenceTypeName", FieldType.ENTITY_NAME, false, null, null),
            new FieldDefinition("AllowUserLogins", FieldType.BOOLEAN, true, null, null),
            new FieldDefinition("AllowPartyAliases", FieldType.BOOLEAN, true, null, null),
            new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
            new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null)
        ));
    }
    
    /** Creates a new instance of CreatePartyTypeCommand */
    public CreatePartyTypeCommand() {
        super(null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var partyControl = Session.getModelController(PartyControl.class);
        var partyTypeName = form.getPartyTypeName();
        var partyType = partyControl.getPartyTypeByName(partyTypeName);

        if(partyType == null) {
            var parentPartyTypeName = form.getParentPartyTypeName();
            PartyType parentPartyType = null;

            if(parentPartyTypeName != null) {
                parentPartyType = partyControl.getPartyTypeByName(parentPartyTypeName);
            }
            if(parentPartyTypeName == null || (parentPartyTypeName != null && parentPartyType != null)) {
                var sequenceControl = Session.getModelController(SequenceControl.class);
                var billingAccountSequenceTypeName = form.getBillingAccountSequenceTypeName();
                var billingAccountSequenceType = billingAccountSequenceTypeName == null ? null : sequenceControl.getSequenceTypeByName(billingAccountSequenceTypeName);

                if(billingAccountSequenceTypeName == null || billingAccountSequenceType != null) {
                    var isDefault = Boolean.valueOf(form.getIsDefault());
                    var allowUserLogins = Boolean.valueOf(form.getAllowUserLogins());
                    var allowPartyAliases = Boolean.valueOf(form.getAllowPartyAliases());
                    var sortOrder = Integer.valueOf(form.getSortOrder());

                    partyControl.createPartyType(partyTypeName, parentPartyType, billingAccountSequenceType, allowUserLogins, allowPartyAliases, isDefault,
                            sortOrder);
                } else {
                    addExecutionError(ExecutionErrors.UnknownBillingAccountSequenceTypeName.name(), billingAccountSequenceTypeName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownParentPartyTypeName.name(), parentPartyTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.DuplicatePartyTypeName.name(), partyTypeName);
        }

        return null;
    }
    
}
