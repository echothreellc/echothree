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
        return new CreateDocumentTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getDocumentTypeChoices(UserVisitPK userVisitPK, GetDocumentTypeChoicesForm form) {
        return new GetDocumentTypeChoicesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getDocumentType(UserVisitPK userVisitPK, GetDocumentTypeForm form) {
        return new GetDocumentTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getDocumentTypes(UserVisitPK userVisitPK, GetDocumentTypesForm form) {
        return new GetDocumentTypesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultDocumentType(UserVisitPK userVisitPK, SetDefaultDocumentTypeForm form) {
        return new SetDefaultDocumentTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editDocumentType(UserVisitPK userVisitPK, EditDocumentTypeForm form) {
        return new EditDocumentTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteDocumentType(UserVisitPK userVisitPK, DeleteDocumentTypeForm form) {
        return new DeleteDocumentTypeCommand().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Document Type Descriptions
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createDocumentTypeDescription(UserVisitPK userVisitPK, CreateDocumentTypeDescriptionForm form) {
        return new CreateDocumentTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getDocumentTypeDescription(UserVisitPK userVisitPK, GetDocumentTypeDescriptionForm form) {
        return new GetDocumentTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getDocumentTypeDescriptions(UserVisitPK userVisitPK, GetDocumentTypeDescriptionsForm form) {
        return new GetDocumentTypeDescriptionsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editDocumentTypeDescription(UserVisitPK userVisitPK, EditDocumentTypeDescriptionForm form) {
        return new EditDocumentTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteDocumentTypeDescription(UserVisitPK userVisitPK, DeleteDocumentTypeDescriptionForm form) {
        return new DeleteDocumentTypeDescriptionCommand().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Document Type Usage Types
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createDocumentTypeUsageType(UserVisitPK userVisitPK, CreateDocumentTypeUsageTypeForm form) {
        return new CreateDocumentTypeUsageTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getDocumentTypeUsageTypeChoices(UserVisitPK userVisitPK, GetDocumentTypeUsageTypeChoicesForm form) {
        return new GetDocumentTypeUsageTypeChoicesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getDocumentTypeUsageType(UserVisitPK userVisitPK, GetDocumentTypeUsageTypeForm form) {
        return new GetDocumentTypeUsageTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getDocumentTypeUsageTypes(UserVisitPK userVisitPK, GetDocumentTypeUsageTypesForm form) {
        return new GetDocumentTypeUsageTypesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultDocumentTypeUsageType(UserVisitPK userVisitPK, SetDefaultDocumentTypeUsageTypeForm form) {
        return new SetDefaultDocumentTypeUsageTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editDocumentTypeUsageType(UserVisitPK userVisitPK, EditDocumentTypeUsageTypeForm form) {
        return new EditDocumentTypeUsageTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteDocumentTypeUsageType(UserVisitPK userVisitPK, DeleteDocumentTypeUsageTypeForm form) {
        return new DeleteDocumentTypeUsageTypeCommand().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Document Type Usage Type Descriptions
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createDocumentTypeUsageTypeDescription(UserVisitPK userVisitPK, CreateDocumentTypeUsageTypeDescriptionForm form) {
        return new CreateDocumentTypeUsageTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getDocumentTypeUsageTypeDescription(UserVisitPK userVisitPK, GetDocumentTypeUsageTypeDescriptionForm form) {
        return new GetDocumentTypeUsageTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getDocumentTypeUsageTypeDescriptions(UserVisitPK userVisitPK, GetDocumentTypeUsageTypeDescriptionsForm form) {
        return new GetDocumentTypeUsageTypeDescriptionsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editDocumentTypeUsageTypeDescription(UserVisitPK userVisitPK, EditDocumentTypeUsageTypeDescriptionForm form) {
        return new EditDocumentTypeUsageTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteDocumentTypeUsageTypeDescription(UserVisitPK userVisitPK, DeleteDocumentTypeUsageTypeDescriptionForm form) {
        return new DeleteDocumentTypeUsageTypeDescriptionCommand().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Document Type Usages
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createDocumentTypeUsage(UserVisitPK userVisitPK, CreateDocumentTypeUsageForm form) {
        return new CreateDocumentTypeUsageCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getDocumentTypeUsage(UserVisitPK userVisitPK, GetDocumentTypeUsageForm form) {
        return new GetDocumentTypeUsageCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getDocumentTypeUsages(UserVisitPK userVisitPK, GetDocumentTypeUsagesForm form) {
        return new GetDocumentTypeUsagesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultDocumentTypeUsage(UserVisitPK userVisitPK, SetDefaultDocumentTypeUsageForm form) {
        return new SetDefaultDocumentTypeUsageCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editDocumentTypeUsage(UserVisitPK userVisitPK, EditDocumentTypeUsageForm form) {
        return new EditDocumentTypeUsageCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteDocumentTypeUsage(UserVisitPK userVisitPK, DeleteDocumentTypeUsageForm form) {
        return new DeleteDocumentTypeUsageCommand().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Party Type Document Type Usage Types
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createPartyTypeDocumentTypeUsageType(UserVisitPK userVisitPK, CreatePartyTypeDocumentTypeUsageTypeForm form) {
        return new CreatePartyTypeDocumentTypeUsageTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPartyTypeDocumentTypeUsageType(UserVisitPK userVisitPK, GetPartyTypeDocumentTypeUsageTypeForm form) {
        return new GetPartyTypeDocumentTypeUsageTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPartyTypeDocumentTypeUsageTypes(UserVisitPK userVisitPK, GetPartyTypeDocumentTypeUsageTypesForm form) {
        return new GetPartyTypeDocumentTypeUsageTypesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultPartyTypeDocumentTypeUsageType(UserVisitPK userVisitPK, SetDefaultPartyTypeDocumentTypeUsageTypeForm form) {
        return new SetDefaultPartyTypeDocumentTypeUsageTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editPartyTypeDocumentTypeUsageType(UserVisitPK userVisitPK, EditPartyTypeDocumentTypeUsageTypeForm form) {
        return new EditPartyTypeDocumentTypeUsageTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deletePartyTypeDocumentTypeUsageType(UserVisitPK userVisitPK, DeletePartyTypeDocumentTypeUsageTypeForm form) {
        return new DeletePartyTypeDocumentTypeUsageTypeCommand().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Party Documents
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createPartyDocument(UserVisitPK userVisitPK, CreatePartyDocumentForm form) {
        return new CreatePartyDocumentCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPartyDocument(UserVisitPK userVisitPK, GetPartyDocumentForm form) {
        return new GetPartyDocumentCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPartyDocuments(UserVisitPK userVisitPK, GetPartyDocumentsForm form) {
        return new GetPartyDocumentsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultPartyDocument(UserVisitPK userVisitPK, SetDefaultPartyDocumentForm form) {
        return new SetDefaultPartyDocumentCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editPartyDocument(UserVisitPK userVisitPK, EditPartyDocumentForm form) {
        return new EditPartyDocumentCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deletePartyDocument(UserVisitPK userVisitPK, DeletePartyDocumentForm form) {
        return new DeletePartyDocumentCommand().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Document Descriptions
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createDocumentDescription(UserVisitPK userVisitPK, CreateDocumentDescriptionForm form) {
        return new CreateDocumentDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getDocumentDescription(UserVisitPK userVisitPK, GetDocumentDescriptionForm form) {
        return new GetDocumentDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getDocumentDescriptions(UserVisitPK userVisitPK, GetDocumentDescriptionsForm form) {
        return new GetDocumentDescriptionsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editDocumentDescription(UserVisitPK userVisitPK, EditDocumentDescriptionForm form) {
        return new EditDocumentDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteDocumentDescription(UserVisitPK userVisitPK, DeleteDocumentDescriptionForm form) {
        return new DeleteDocumentDescriptionCommand().run(userVisitPK, form);
    }

}
