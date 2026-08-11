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

package com.echothree.control.user.document.server.command;

import com.echothree.control.user.document.common.form.GetPartyDocumentForm;
import com.echothree.control.user.document.common.result.DocumentResultFactory;
import com.echothree.model.control.content.server.logic.ContentLogic;
import com.echothree.model.control.document.server.control.DocumentControl;
import com.echothree.model.control.document.server.logic.DocumentLogic;
import com.echothree.model.data.document.server.entity.PartyDocument;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSingleEntityCommand;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetPartyDocumentCommand
        extends BaseSingleEntityCommand<PartyDocument, GetPartyDocumentForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("DocumentName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityRef", FieldType.ENTITY_REF, false, null, null),
                new FieldDefinition("Uuid", FieldType.UUID, false, null, null),
                new FieldDefinition("Referrer", FieldType.URL, false, null, null)
        );
    }

    @Inject
    DocumentControl documentControl;

    @Inject
    ContentLogic contentLogic;

    @Inject
    DocumentLogic documentLogic;

    /** Creates a new instance of GetPartyDocumentCommand */
    public GetPartyDocumentCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }

    @Override
    protected PartyDocument getEntity() {
        PartyDocument partyDocument = null;

        contentLogic.checkReferrer(this, form.getReferrer());

        if(!hasExecutionErrors()) {
            var document = documentLogic.getDocumentByUniversalSpec(this, form);

            if(!hasExecutionErrors()) {
                partyDocument = documentControl.getPartyDocumentByDocument(document);

                if(partyDocument == null) {
                    addExecutionError(ExecutionErrors.UnknownPartyDocument.name(),
                            document.getLastDetail().getDocumentName());
                }
            }
        }

        return partyDocument;
    }

    @Override
    protected BaseResult getResult(PartyDocument partyDocument) {
        var result = DocumentResultFactory.getGetPartyDocumentResult();

        if(partyDocument != null) {
            result.setPartyDocument(documentControl.getPartyDocumentTransfer(getUserVisit(), partyDocument));
        }

        return result;
    }
    
}
