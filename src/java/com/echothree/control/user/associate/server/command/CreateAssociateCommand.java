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

import com.echothree.control.user.associate.common.form.CreateAssociateForm;
import com.echothree.model.control.associate.server.control.AssociateControl;
import com.echothree.model.control.core.server.control.MimeTypeControl;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CreateAssociateCommand
        extends BaseSimpleCommand<CreateAssociateForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("AssociateProgramName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("AssociateName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("PartyName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L),
            new FieldDefinition("SummaryMimeTypeName", FieldType.MIME_TYPE, true, null, null),
            new FieldDefinition("Summary", FieldType.STRING, true, null, null)
        ));
    }
    
    /** Creates a new instance of CreateAssociateCommand */
    public CreateAssociateCommand(UserVisitPK userVisitPK, CreateAssociateForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var associateControl = Session.getModelController(AssociateControl.class);
        var associateProgramName = form.getAssociateProgramName();
        var associateProgram = associateControl.getAssociateProgramByName(associateProgramName);
        
        if(associateProgram != null) {
            var associateName = form.getAssociateName();
            var associate = associateControl.getAssociateByName(associateProgram, associateName);
            
            if(associate == null) {
                var partyControl = Session.getModelController(PartyControl.class);
                var partyName = form.getPartyName();
                var party = partyControl.getPartyByName(partyName);
                
                if(party != null) {
                    var mimeTypeControl = Session.getModelController(MimeTypeControl.class);
                    var summaryMimeTypeName = form.getSummaryMimeTypeName();
                    var summaryMimeType = mimeTypeControl.getMimeTypeByName(summaryMimeTypeName);
                    
                    if(summaryMimeType != null) {
                        var description = form.getDescription();
                        var summary = form.getSummary();
                        
                        associateControl.createAssociate(associateProgram, associateName, party, description, summaryMimeType,
                                summary, getPartyPK());
                    } else {
                        addExecutionError(ExecutionErrors.UnknownSummaryMimeTypeName.name(), summaryMimeTypeName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownPartyName.name(), partyName);
                }
            } else {
                addExecutionError(ExecutionErrors.DuplicateAssociateName.name(), associateName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownAssociateProgramName.name(), associateProgramName);
        }
        
        return null;
    }
    
}
