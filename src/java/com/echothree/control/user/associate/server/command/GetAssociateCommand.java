// --------------------------------------------------------------------------------
// Copyright 2002-2022 Echo Three, LLC
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

package com.echothree.control.user.associate.server.command;

import com.echothree.control.user.associate.common.form.GetAssociateForm;
import com.echothree.control.user.associate.common.result.AssociateResultFactory;
import com.echothree.control.user.associate.common.result.GetAssociateResult;
import com.echothree.model.control.associate.server.control.AssociateControl;
import com.echothree.model.data.associate.server.entity.Associate;
import com.echothree.model.data.associate.server.entity.AssociateProgram;
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

public class GetAssociateCommand
        extends BaseSimpleCommand<GetAssociateForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("AssociateProgramName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("AssociateName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of GetAssociateCommand */
    public GetAssociateCommand(UserVisitPK userVisitPK, GetAssociateForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected BaseResult execute() {
        var associateControl = Session.getModelController(AssociateControl.class);
        GetAssociateResult result = AssociateResultFactory.getGetAssociateResult();
        String associateProgramName = form.getAssociateProgramName();
        AssociateProgram associateProgram = associateControl.getAssociateProgramByName(associateProgramName);
        
        if(associateProgram != null) {
            String associateName = form.getAssociateName();
            Associate associate = associateControl.getAssociateByName(associateProgram, associateName);
            
            if(associate != null) {
                result.setAssociate(associateControl.getAssociateTransfer(getUserVisit(), associate));
            } else {
                addExecutionError(ExecutionErrors.UnknownAssociateName.name(), associateProgramName, associateName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownAssociateProgramName.name(), associateProgramName);
        }
        
        return result;
    }
    
}
