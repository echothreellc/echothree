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

package com.echothree.control.user.document.common;

import com.echothree.control.user.document.common.form.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;

public interface DocumentService
        extends DocumentForms {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    String ping();
    
    // --------------------------------------------------------------------------------
    //   Document Types
    // --------------------------------------------------------------------------------

    CommandResult createDocumentType(UserVisitPK userVisitPK, CreateDocumentTypeForm form);

    CommandResult getDocumentTypeChoices(UserVisitPK userVisitPK, GetDocumentTypeChoicesForm form);

    CommandResult getDocumentType(UserVisitPK userVisitPK, GetDocumentTypeForm form);

    CommandResult getDocumentTypes(UserVisitPK userVisitPK, GetDocumentTypesForm form);

    CommandResult setDefaultDocumentType(UserVisitPK userVisitPK, SetDefaultDocumentTypeForm form);

    CommandResult editDocumentType(UserVisitPK userVisitPK, EditDocumentTypeForm form);

    CommandResult deleteDocumentType(UserVisitPK userVisitPK, DeleteDocumentTypeForm form);

    // --------------------------------------------------------------------------------
    //   Document Type Descriptions
    // --------------------------------------------------------------------------------

    CommandResult createDocumentTypeDescription(UserVisitPK userVisitPK, CreateDocumentTypeDescriptionForm form);

    CommandResult getDocumentTypeDescription(UserVisitPK userVisitPK, GetDocumentTypeDescriptionForm form);

    CommandResult getDocumentTypeDescriptions(UserVisitPK userVisitPK, GetDocumentTypeDescriptionsForm form);

    CommandResult editDocumentTypeDescription(UserVisitPK userVisitPK, EditDocumentTypeDescriptionForm form);

    CommandResult deleteDocumentTypeDescription(UserVisitPK userVisitPK, DeleteDocumentTypeDescriptionForm form);

    // --------------------------------------------------------------------------------
    //   Document Type Usage Types
    // --------------------------------------------------------------------------------

    CommandResult createDocumentTypeUsageType(UserVisitPK userVisitPK, CreateDocumentTypeUsageTypeForm form);

    CommandResult getDocumentTypeUsageTypeChoices(UserVisitPK userVisitPK, GetDocumentTypeUsageTypeChoicesForm form);

    CommandResult getDocumentTypeUsageType(UserVisitPK userVisitPK, GetDocumentTypeUsageTypeForm form);

    CommandResult getDocumentTypeUsageTypes(UserVisitPK userVisitPK, GetDocumentTypeUsageTypesForm form);

    CommandResult setDefaultDocumentTypeUsageType(UserVisitPK userVisitPK, SetDefaultDocumentTypeUsageTypeForm form);

    CommandResult editDocumentTypeUsageType(UserVisitPK userVisitPK, EditDocumentTypeUsageTypeForm form);

    CommandResult deleteDocumentTypeUsageType(UserVisitPK userVisitPK, DeleteDocumentTypeUsageTypeForm form);

    // --------------------------------------------------------------------------------
    //   Document Type Usage Type Descriptions
    // --------------------------------------------------------------------------------

    CommandResult createDocumentTypeUsageTypeDescription(UserVisitPK userVisitPK, CreateDocumentTypeUsageTypeDescriptionForm form);

    CommandResult getDocumentTypeUsageTypeDescription(UserVisitPK userVisitPK, GetDocumentTypeUsageTypeDescriptionForm form);

    CommandResult getDocumentTypeUsageTypeDescriptions(UserVisitPK userVisitPK, GetDocumentTypeUsageTypeDescriptionsForm form);

    CommandResult editDocumentTypeUsageTypeDescription(UserVisitPK userVisitPK, EditDocumentTypeUsageTypeDescriptionForm form);

    CommandResult deleteDocumentTypeUsageTypeDescription(UserVisitPK userVisitPK, DeleteDocumentTypeUsageTypeDescriptionForm form);

    // --------------------------------------------------------------------------------
    //   Document Type Usage Descriptions
    // --------------------------------------------------------------------------------

    CommandResult createDocumentTypeUsage(UserVisitPK userVisitPK, CreateDocumentTypeUsageForm form);

    CommandResult getDocumentTypeUsage(UserVisitPK userVisitPK, GetDocumentTypeUsageForm form);

    CommandResult getDocumentTypeUsages(UserVisitPK userVisitPK, GetDocumentTypeUsagesForm form);

    CommandResult setDefaultDocumentTypeUsage(UserVisitPK userVisitPK, SetDefaultDocumentTypeUsageForm form);

    CommandResult editDocumentTypeUsage(UserVisitPK userVisitPK, EditDocumentTypeUsageForm form);

    CommandResult deleteDocumentTypeUsage(UserVisitPK userVisitPK, DeleteDocumentTypeUsageForm form);

    // --------------------------------------------------------------------------------
    //   Party Type Document Type Usage Types
    // --------------------------------------------------------------------------------

    CommandResult createPartyTypeDocumentTypeUsageType(UserVisitPK userVisitPK, CreatePartyTypeDocumentTypeUsageTypeForm form);

    CommandResult getPartyTypeDocumentTypeUsageType(UserVisitPK userVisitPK, GetPartyTypeDocumentTypeUsageTypeForm form);

    CommandResult getPartyTypeDocumentTypeUsageTypes(UserVisitPK userVisitPK, GetPartyTypeDocumentTypeUsageTypesForm form);

    CommandResult setDefaultPartyTypeDocumentTypeUsageType(UserVisitPK userVisitPK, SetDefaultPartyTypeDocumentTypeUsageTypeForm form);

    CommandResult editPartyTypeDocumentTypeUsageType(UserVisitPK userVisitPK, EditPartyTypeDocumentTypeUsageTypeForm form);

    CommandResult deletePartyTypeDocumentTypeUsageType(UserVisitPK userVisitPK, DeletePartyTypeDocumentTypeUsageTypeForm form);

    // --------------------------------------------------------------------------------
    //   Party Documents
    // --------------------------------------------------------------------------------

    CommandResult createPartyDocument(UserVisitPK userVisitPK, CreatePartyDocumentForm form);

    CommandResult getPartyDocument(UserVisitPK userVisitPK, GetPartyDocumentForm form);

    CommandResult getPartyDocuments(UserVisitPK userVisitPK, GetPartyDocumentsForm form);

    CommandResult setDefaultPartyDocument(UserVisitPK userVisitPK, SetDefaultPartyDocumentForm form);

    CommandResult editPartyDocument(UserVisitPK userVisitPK, EditPartyDocumentForm form);

    CommandResult deletePartyDocument(UserVisitPK userVisitPK, DeletePartyDocumentForm form);

    // --------------------------------------------------------------------------------
    //   Document Descriptions
    // --------------------------------------------------------------------------------

    CommandResult createDocumentDescription(UserVisitPK userVisitPK, CreateDocumentDescriptionForm form);

    CommandResult getDocumentDescription(UserVisitPK userVisitPK, GetDocumentDescriptionForm form);

    CommandResult getDocumentDescriptions(UserVisitPK userVisitPK, GetDocumentDescriptionsForm form);

    CommandResult editDocumentDescription(UserVisitPK userVisitPK, EditDocumentDescriptionForm form);

    CommandResult deleteDocumentDescription(UserVisitPK userVisitPK, DeleteDocumentDescriptionForm form);

}
