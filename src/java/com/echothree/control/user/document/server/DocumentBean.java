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
import javax.enterprise.inject.spi.CDI;

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
        return CDI.current().select(CreateDocumentTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getDocumentTypeChoices(UserVisitPK userVisitPK, GetDocumentTypeChoicesForm form) {
        return CDI.current().select(GetDocumentTypeChoicesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getDocumentType(UserVisitPK userVisitPK, GetDocumentTypeForm form) {
        return CDI.current().select(GetDocumentTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getDocumentTypes(UserVisitPK userVisitPK, GetDocumentTypesForm form) {
        return CDI.current().select(GetDocumentTypesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultDocumentType(UserVisitPK userVisitPK, SetDefaultDocumentTypeForm form) {
        return CDI.current().select(SetDefaultDocumentTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editDocumentType(UserVisitPK userVisitPK, EditDocumentTypeForm form) {
        return CDI.current().select(EditDocumentTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteDocumentType(UserVisitPK userVisitPK, DeleteDocumentTypeForm form) {
        return CDI.current().select(DeleteDocumentTypeCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Document Type Descriptions
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createDocumentTypeDescription(UserVisitPK userVisitPK, CreateDocumentTypeDescriptionForm form) {
        return CDI.current().select(CreateDocumentTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getDocumentTypeDescription(UserVisitPK userVisitPK, GetDocumentTypeDescriptionForm form) {
        return CDI.current().select(GetDocumentTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getDocumentTypeDescriptions(UserVisitPK userVisitPK, GetDocumentTypeDescriptionsForm form) {
        return CDI.current().select(GetDocumentTypeDescriptionsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editDocumentTypeDescription(UserVisitPK userVisitPK, EditDocumentTypeDescriptionForm form) {
        return CDI.current().select(EditDocumentTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteDocumentTypeDescription(UserVisitPK userVisitPK, DeleteDocumentTypeDescriptionForm form) {
        return CDI.current().select(DeleteDocumentTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Document Type Usage Types
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createDocumentTypeUsageType(UserVisitPK userVisitPK, CreateDocumentTypeUsageTypeForm form) {
        return CDI.current().select(CreateDocumentTypeUsageTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getDocumentTypeUsageTypeChoices(UserVisitPK userVisitPK, GetDocumentTypeUsageTypeChoicesForm form) {
        return CDI.current().select(GetDocumentTypeUsageTypeChoicesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getDocumentTypeUsageType(UserVisitPK userVisitPK, GetDocumentTypeUsageTypeForm form) {
        return CDI.current().select(GetDocumentTypeUsageTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getDocumentTypeUsageTypes(UserVisitPK userVisitPK, GetDocumentTypeUsageTypesForm form) {
        return CDI.current().select(GetDocumentTypeUsageTypesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultDocumentTypeUsageType(UserVisitPK userVisitPK, SetDefaultDocumentTypeUsageTypeForm form) {
        return CDI.current().select(SetDefaultDocumentTypeUsageTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editDocumentTypeUsageType(UserVisitPK userVisitPK, EditDocumentTypeUsageTypeForm form) {
        return CDI.current().select(EditDocumentTypeUsageTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteDocumentTypeUsageType(UserVisitPK userVisitPK, DeleteDocumentTypeUsageTypeForm form) {
        return CDI.current().select(DeleteDocumentTypeUsageTypeCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Document Type Usage Type Descriptions
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createDocumentTypeUsageTypeDescription(UserVisitPK userVisitPK, CreateDocumentTypeUsageTypeDescriptionForm form) {
        return CDI.current().select(CreateDocumentTypeUsageTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getDocumentTypeUsageTypeDescription(UserVisitPK userVisitPK, GetDocumentTypeUsageTypeDescriptionForm form) {
        return CDI.current().select(GetDocumentTypeUsageTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getDocumentTypeUsageTypeDescriptions(UserVisitPK userVisitPK, GetDocumentTypeUsageTypeDescriptionsForm form) {
        return CDI.current().select(GetDocumentTypeUsageTypeDescriptionsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editDocumentTypeUsageTypeDescription(UserVisitPK userVisitPK, EditDocumentTypeUsageTypeDescriptionForm form) {
        return CDI.current().select(EditDocumentTypeUsageTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteDocumentTypeUsageTypeDescription(UserVisitPK userVisitPK, DeleteDocumentTypeUsageTypeDescriptionForm form) {
        return CDI.current().select(DeleteDocumentTypeUsageTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Document Type Usages
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createDocumentTypeUsage(UserVisitPK userVisitPK, CreateDocumentTypeUsageForm form) {
        return CDI.current().select(CreateDocumentTypeUsageCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getDocumentTypeUsage(UserVisitPK userVisitPK, GetDocumentTypeUsageForm form) {
        return CDI.current().select(GetDocumentTypeUsageCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getDocumentTypeUsages(UserVisitPK userVisitPK, GetDocumentTypeUsagesForm form) {
        return CDI.current().select(GetDocumentTypeUsagesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultDocumentTypeUsage(UserVisitPK userVisitPK, SetDefaultDocumentTypeUsageForm form) {
        return CDI.current().select(SetDefaultDocumentTypeUsageCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editDocumentTypeUsage(UserVisitPK userVisitPK, EditDocumentTypeUsageForm form) {
        return CDI.current().select(EditDocumentTypeUsageCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteDocumentTypeUsage(UserVisitPK userVisitPK, DeleteDocumentTypeUsageForm form) {
        return CDI.current().select(DeleteDocumentTypeUsageCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Party Type Document Type Usage Types
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createPartyTypeDocumentTypeUsageType(UserVisitPK userVisitPK, CreatePartyTypeDocumentTypeUsageTypeForm form) {
        return CDI.current().select(CreatePartyTypeDocumentTypeUsageTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPartyTypeDocumentTypeUsageType(UserVisitPK userVisitPK, GetPartyTypeDocumentTypeUsageTypeForm form) {
        return CDI.current().select(GetPartyTypeDocumentTypeUsageTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPartyTypeDocumentTypeUsageTypes(UserVisitPK userVisitPK, GetPartyTypeDocumentTypeUsageTypesForm form) {
        return CDI.current().select(GetPartyTypeDocumentTypeUsageTypesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultPartyTypeDocumentTypeUsageType(UserVisitPK userVisitPK, SetDefaultPartyTypeDocumentTypeUsageTypeForm form) {
        return CDI.current().select(SetDefaultPartyTypeDocumentTypeUsageTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editPartyTypeDocumentTypeUsageType(UserVisitPK userVisitPK, EditPartyTypeDocumentTypeUsageTypeForm form) {
        return CDI.current().select(EditPartyTypeDocumentTypeUsageTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deletePartyTypeDocumentTypeUsageType(UserVisitPK userVisitPK, DeletePartyTypeDocumentTypeUsageTypeForm form) {
        return CDI.current().select(DeletePartyTypeDocumentTypeUsageTypeCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Party Documents
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createPartyDocument(UserVisitPK userVisitPK, CreatePartyDocumentForm form) {
        return CDI.current().select(CreatePartyDocumentCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPartyDocument(UserVisitPK userVisitPK, GetPartyDocumentForm form) {
        return CDI.current().select(GetPartyDocumentCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getPartyDocuments(UserVisitPK userVisitPK, GetPartyDocumentsForm form) {
        return CDI.current().select(GetPartyDocumentsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultPartyDocument(UserVisitPK userVisitPK, SetDefaultPartyDocumentForm form) {
        return CDI.current().select(SetDefaultPartyDocumentCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editPartyDocument(UserVisitPK userVisitPK, EditPartyDocumentForm form) {
        return CDI.current().select(EditPartyDocumentCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deletePartyDocument(UserVisitPK userVisitPK, DeletePartyDocumentForm form) {
        return CDI.current().select(DeletePartyDocumentCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Document Descriptions
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createDocumentDescription(UserVisitPK userVisitPK, CreateDocumentDescriptionForm form) {
        return CDI.current().select(CreateDocumentDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getDocumentDescription(UserVisitPK userVisitPK, GetDocumentDescriptionForm form) {
        return CDI.current().select(GetDocumentDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getDocumentDescriptions(UserVisitPK userVisitPK, GetDocumentDescriptionsForm form) {
        return CDI.current().select(GetDocumentDescriptionsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editDocumentDescription(UserVisitPK userVisitPK, EditDocumentDescriptionForm form) {
        return CDI.current().select(EditDocumentDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteDocumentDescription(UserVisitPK userVisitPK, DeleteDocumentDescriptionForm form) {
        return CDI.current().select(DeleteDocumentDescriptionCommand.class).get().run(userVisitPK, form);
    }

}
