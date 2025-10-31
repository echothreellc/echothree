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

package com.echothree.control.user.associate.server.command;

import com.echothree.control.user.associate.common.form.GetAssociateReferralForm;
import com.echothree.control.user.associate.common.result.AssociateResultFactory;
import com.echothree.model.control.associate.server.control.AssociateControl;
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
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class GetAssociateReferralCommand
        extends BaseSimpleCommand<GetAssociateReferralForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("AssociateReferralName", FieldType.ENTITY_NAME, true, null, null)
        ));
    }
    
    /** Creates a new instance of GetAssociateReferralCommand */
    public GetAssociateReferralCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected BaseResult execute() {
        var associateControl = Session.getModelController(AssociateControl.class);
        var result = AssociateResultFactory.getGetAssociateReferralResult();
        var associateReferralName = form.getAssociateReferralName();
        var associateReferral = associateControl.getAssociateReferralByName(associateReferralName);
        
        if(associateReferral != null) {
            result.setAssociateReferral(associateControl.getAssociateReferralTransfer(getUserVisit(), associateReferral));
        } else {
            addExecutionError(ExecutionErrors.UnknownAssociateReferralName.name(), associateReferralName);
        }
        
        return result;
    }
    
}
