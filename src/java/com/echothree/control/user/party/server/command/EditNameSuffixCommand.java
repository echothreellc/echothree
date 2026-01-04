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

package com.echothree.control.user.party.server.command;

import com.echothree.control.user.party.common.edit.NameSuffixEdit;
import com.echothree.control.user.party.common.edit.PartyEditFactory;
import com.echothree.control.user.party.common.form.EditNameSuffixForm;
import com.echothree.control.user.party.common.result.PartyResultFactory;
import com.echothree.control.user.party.common.spec.NameSuffixSpec;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.server.control.BaseEditCommand;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
public class EditNameSuffixCommand
        extends BaseEditCommand<NameSuffixSpec, NameSuffixEdit> {
    
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("NameSuffixId", FieldType.ID, true, null, null)
        ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L),
            new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
            new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null)
        ));
    }
    
    /** Creates a new instance of EditNameSuffixCommand */
    public EditNameSuffixCommand() {
        super(null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var partyControl = Session.getModelController(PartyControl.class);
        var result = PartyResultFactory.getEditNameSuffixResult();
        
        if(editMode.equals(EditMode.LOCK)) {
            var nameSuffixId = spec.getNameSuffixId();
            var nameSuffix = partyControl.convertNameSuffixIdToEntity(nameSuffixId, EntityPermission.READ_ONLY);
            
            if(nameSuffix != null) {
                result.setNameSuffix(partyControl.getNameSuffixTransfer(getUserVisit(), nameSuffix));
                
                if(lockEntity(nameSuffix)) {
                    var edit = PartyEditFactory.getNameSuffixEdit();
                    var nameSuffixDetail = nameSuffix.getLastDetail();
                    
                    result.setEdit(edit);
                    edit.setDescription(nameSuffixDetail.getDescription());
                    edit.setIsDefault(nameSuffixDetail.getIsDefault().toString());
                    edit.setSortOrder(nameSuffixDetail.getSortOrder().toString());
                } else {
                    addExecutionError(ExecutionErrors.EntityLockFailed.name());
                }
                
                result.setEntityLock(getEntityLockTransfer(nameSuffix));
            } else {
                addExecutionError(ExecutionErrors.UnknownNameSuffixId.name(), nameSuffixId);
            }
        } else if(editMode.equals(EditMode.UPDATE)) {
            var nameSuffixId = spec.getNameSuffixId();
            var nameSuffixPK = partyControl.convertNameSuffixIdToPK(nameSuffixId);
            
            if(nameSuffixPK != null) {
                if(lockEntityForUpdate(nameSuffixPK)) {
                    try {
                        var partyPK = getPartyPK();
                        var nameSuffixDetailValue = partyControl.getNameSuffixDetailValueByPKForUpdate(nameSuffixPK);
                        
                        nameSuffixDetailValue.setDescription(edit.getDescription());
                        nameSuffixDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
                        nameSuffixDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));
                        
                        partyControl.updateNameSuffixFromValue(nameSuffixDetailValue, partyPK);
                    } finally {
                        unlockEntity(nameSuffixPK);
                    }
                } else {
                    addExecutionError(ExecutionErrors.EntityLockStale.name());
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownNameSuffixId.name(), nameSuffixId);
            }
        }
        
        return result;
    }
    
}
