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

package com.echothree.control.user.document.server;

import com.echothree.control.user.document.common.DocumentRemote;
import com.echothree.control.user.document.common.form.*;
import com.echothree.control.user.document.server.command.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;
import javax.ejb.Stateless;

@Stateless
public class DocumentBean
        extends DocumentFormsImpl
        implements DocumentRemote, DocumentLocal {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    @Override
    public String ping() {
        return "DocumentBean is alive!";
    }
    
    // --------------------------------------------------------------------------------
    //   Document Types
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createDocumentType(UserVisitPK userVisitPK, CreateDocumentTypeForm form) {
        return new CreateDocumentTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getDocumentTypeChoices(UserVisitPK userVisitPK, GetDocumentTypeChoicesForm form) {
        return new GetDocumentTypeChoicesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getDocumentType(UserVisitPK userVisitPK, GetDocumentTypeForm form) {
        return new GetDocumentTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getDocumentTypes(UserVisitPK userVisitPK, GetDocumentTypesForm form) {
        return new GetDocumentTypesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult setDefaultDocumentType(UserVisitPK userVisitPK, SetDefaultDocumentTypeForm form) {
        return new SetDefaultDocumentTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editDocumentType(UserVisitPK userVisitPK, EditDocumentTypeForm form) {
        return new EditDocumentTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteDocumentType(UserVisitPK userVisitPK, DeleteDocumentTypeForm form) {
        return new DeleteDocumentTypeCommand(userVisitPK, form).run();
    }

    // --------------------------------------------------------------------------------
    //   Document Type Descriptions
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createDocumentTypeDescription(UserVisitPK userVisitPK, CreateDocumentTypeDescriptionForm form) {
        return new CreateDocumentTypeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getDocumentTypeDescription(UserVisitPK userVisitPK, GetDocumentTypeDescriptionForm form) {
        return new GetDocumentTypeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getDocumentTypeDescriptions(UserVisitPK userVisitPK, GetDocumentTypeDescriptionsForm form) {
        return new GetDocumentTypeDescriptionsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editDocumentTypeDescription(UserVisitPK userVisitPK, EditDocumentTypeDescriptionForm form) {
        return new EditDocumentTypeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteDocumentTypeDescription(UserVisitPK userVisitPK, DeleteDocumentTypeDescriptionForm form) {
        return new DeleteDocumentTypeDescriptionCommand(userVisitPK, form).run();
    }

    // --------------------------------------------------------------------------------
    //   Document Type Usage Types
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createDocumentTypeUsageType(UserVisitPK userVisitPK, CreateDocumentTypeUsageTypeForm form) {
        return new CreateDocumentTypeUsageTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getDocumentTypeUsageTypeChoices(UserVisitPK userVisitPK, GetDocumentTypeUsageTypeChoicesForm form) {
        return new GetDocumentTypeUsageTypeChoicesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getDocumentTypeUsageType(UserVisitPK userVisitPK, GetDocumentTypeUsageTypeForm form) {
        return new GetDocumentTypeUsageTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getDocumentTypeUsageTypes(UserVisitPK userVisitPK, GetDocumentTypeUsageTypesForm form) {
        return new GetDocumentTypeUsageTypesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult setDefaultDocumentTypeUsageType(UserVisitPK userVisitPK, SetDefaultDocumentTypeUsageTypeForm form) {
        return new SetDefaultDocumentTypeUsageTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editDocumentTypeUsageType(UserVisitPK userVisitPK, EditDocumentTypeUsageTypeForm form) {
        return new EditDocumentTypeUsageTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteDocumentTypeUsageType(UserVisitPK userVisitPK, DeleteDocumentTypeUsageTypeForm form) {
        return new DeleteDocumentTypeUsageTypeCommand(userVisitPK, form).run();
    }

    // --------------------------------------------------------------------------------
    //   Document Type Usage Type Descriptions
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createDocumentTypeUsageTypeDescription(UserVisitPK userVisitPK, CreateDocumentTypeUsageTypeDescriptionForm form) {
        return new CreateDocumentTypeUsageTypeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getDocumentTypeUsageTypeDescription(UserVisitPK userVisitPK, GetDocumentTypeUsageTypeDescriptionForm form) {
        return new GetDocumentTypeUsageTypeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getDocumentTypeUsageTypeDescriptions(UserVisitPK userVisitPK, GetDocumentTypeUsageTypeDescriptionsForm form) {
        return new GetDocumentTypeUsageTypeDescriptionsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editDocumentTypeUsageTypeDescription(UserVisitPK userVisitPK, EditDocumentTypeUsageTypeDescriptionForm form) {
        return new EditDocumentTypeUsageTypeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteDocumentTypeUsageTypeDescription(UserVisitPK userVisitPK, DeleteDocumentTypeUsageTypeDescriptionForm form) {
        return new DeleteDocumentTypeUsageTypeDescriptionCommand(userVisitPK, form).run();
    }

    // --------------------------------------------------------------------------------
    //   Document Type Usages
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createDocumentTypeUsage(UserVisitPK userVisitPK, CreateDocumentTypeUsageForm form) {
        return new CreateDocumentTypeUsageCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getDocumentTypeUsage(UserVisitPK userVisitPK, GetDocumentTypeUsageForm form) {
        return new GetDocumentTypeUsageCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getDocumentTypeUsages(UserVisitPK userVisitPK, GetDocumentTypeUsagesForm form) {
        return new GetDocumentTypeUsagesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult setDefaultDocumentTypeUsage(UserVisitPK userVisitPK, SetDefaultDocumentTypeUsageForm form) {
        return new SetDefaultDocumentTypeUsageCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editDocumentTypeUsage(UserVisitPK userVisitPK, EditDocumentTypeUsageForm form) {
        return new EditDocumentTypeUsageCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteDocumentTypeUsage(UserVisitPK userVisitPK, DeleteDocumentTypeUsageForm form) {
        return new DeleteDocumentTypeUsageCommand(userVisitPK, form).run();
    }

    // --------------------------------------------------------------------------------
    //   Party Type Document Type Usage Types
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createPartyTypeDocumentTypeUsageType(UserVisitPK userVisitPK, CreatePartyTypeDocumentTypeUsageTypeForm form) {
        return new CreatePartyTypeDocumentTypeUsageTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getPartyTypeDocumentTypeUsageType(UserVisitPK userVisitPK, GetPartyTypeDocumentTypeUsageTypeForm form) {
        return new GetPartyTypeDocumentTypeUsageTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getPartyTypeDocumentTypeUsageTypes(UserVisitPK userVisitPK, GetPartyTypeDocumentTypeUsageTypesForm form) {
        return new GetPartyTypeDocumentTypeUsageTypesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult setDefaultPartyTypeDocumentTypeUsageType(UserVisitPK userVisitPK, SetDefaultPartyTypeDocumentTypeUsageTypeForm form) {
        return new SetDefaultPartyTypeDocumentTypeUsageTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editPartyTypeDocumentTypeUsageType(UserVisitPK userVisitPK, EditPartyTypeDocumentTypeUsageTypeForm form) {
        return new EditPartyTypeDocumentTypeUsageTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deletePartyTypeDocumentTypeUsageType(UserVisitPK userVisitPK, DeletePartyTypeDocumentTypeUsageTypeForm form) {
        return new DeletePartyTypeDocumentTypeUsageTypeCommand(userVisitPK, form).run();
    }

    // --------------------------------------------------------------------------------
    //   Party Documents
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createPartyDocument(UserVisitPK userVisitPK, CreatePartyDocumentForm form) {
        return new CreatePartyDocumentCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getPartyDocument(UserVisitPK userVisitPK, GetPartyDocumentForm form) {
        return new GetPartyDocumentCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getPartyDocuments(UserVisitPK userVisitPK, GetPartyDocumentsForm form) {
        return new GetPartyDocumentsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult setDefaultPartyDocument(UserVisitPK userVisitPK, SetDefaultPartyDocumentForm form) {
        return new SetDefaultPartyDocumentCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editPartyDocument(UserVisitPK userVisitPK, EditPartyDocumentForm form) {
        return new EditPartyDocumentCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deletePartyDocument(UserVisitPK userVisitPK, DeletePartyDocumentForm form) {
        return new DeletePartyDocumentCommand(userVisitPK, form).run();
    }

    // --------------------------------------------------------------------------------
    //   Document Descriptions
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createDocumentDescription(UserVisitPK userVisitPK, CreateDocumentDescriptionForm form) {
        return new CreateDocumentDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getDocumentDescription(UserVisitPK userVisitPK, GetDocumentDescriptionForm form) {
        return new GetDocumentDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getDocumentDescriptions(UserVisitPK userVisitPK, GetDocumentDescriptionsForm form) {
        return new GetDocumentDescriptionsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editDocumentDescription(UserVisitPK userVisitPK, EditDocumentDescriptionForm form) {
        return new EditDocumentDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteDocumentDescription(UserVisitPK userVisitPK, DeleteDocumentDescriptionForm form) {
        return new DeleteDocumentDescriptionCommand(userVisitPK, form).run();
    }

}
