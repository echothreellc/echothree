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

package com.echothree.control.user.document.server.command;

import com.echothree.control.user.document.common.form.GetPartyDocumentForm;
import com.echothree.control.user.document.common.result.DocumentResultFactory;
import com.echothree.model.control.content.server.logic.ContentLogic;
import com.echothree.model.control.document.server.control.DocumentControl;
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
public class GetPartyDocumentCommand
        extends BaseSimpleCommand<GetPartyDocumentForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("DocumentName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }
    
    /** Creates a new instance of GetPartyDocumentCommand */
    public GetPartyDocumentCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }
    
    @Override
    protected BaseResult execute() {
        var result = DocumentResultFactory.getGetPartyDocumentResult();
        ContentLogic.getInstance().checkReferrer(this, form.getReferrer());
        
        if(!hasExecutionErrors()) {
            var documentControl = Session.getModelController(DocumentControl.class);
            var documentName = form.getDocumentName();
            var document = documentControl.getDocumentByName(documentName);

            if(document != null) {
                var partyDocument = documentControl.getPartyDocumentByDocument(document);

                if(partyDocument != null) {
                    result.setPartyDocument(documentControl.getPartyDocumentTransfer(getUserVisit(), partyDocument));
                } else {
                    addExecutionError(ExecutionErrors.UnknownPartyDocument.name(), documentName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownDocumentName.name(), documentName);
            }
        }

        return result;
    }
    
}
