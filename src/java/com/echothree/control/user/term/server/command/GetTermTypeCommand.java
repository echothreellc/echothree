// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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

package com.echothree.control.user.term.server.command;

import com.echothree.control.user.term.common.form.GetTermTypeForm;
import com.echothree.control.user.term.common.result.GetTermTypeResult;
import com.echothree.control.user.term.common.result.TermResultFactory;
import com.echothree.model.control.term.server.TermControl;
import com.echothree.model.data.term.server.entity.TermType;
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

public class GetTermTypeCommand
        extends BaseSimpleCommand<GetTermTypeForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("TermTypeName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of GetTermTypeCommand */
    public GetTermTypeCommand(UserVisitPK userVisitPK, GetTermTypeForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected BaseResult execute() {
        var termControl = (TermControl)Session.getModelController(TermControl.class);
        GetTermTypeResult result = TermResultFactory.getGetTermTypeResult();
        String termTypeName = form.getTermTypeName();
        TermType termType = termControl.getTermTypeByName(termTypeName);
        
        if(termType != null) {
            result.setTermType(termControl.getTermTypeTransfer(getUserVisit(), termType));
        } else {
            addExecutionError(ExecutionErrors.UnknownTermTypeName.name(), termTypeName);
        }
        
        return result;
    }
    
}
