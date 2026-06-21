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
import com.echothree.control.user.document.common.result.*;
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

    CommandResult<?> createDocumentType(UserVisitPK userVisitPK, CreateDocumentTypeForm form);

    CommandResult<GetDocumentTypeChoicesResult> getDocumentTypeChoices(UserVisitPK userVisitPK, GetDocumentTypeChoicesForm form);

    CommandResult<GetDocumentTypeResult> getDocumentType(UserVisitPK userVisitPK, GetDocumentTypeForm form);

    CommandResult<GetDocumentTypesResult> getDocumentTypes(UserVisitPK userVisitPK, GetDocumentTypesForm form);

    CommandResult<?> setDefaultDocumentType(UserVisitPK userVisitPK, SetDefaultDocumentTypeForm form);

    CommandResult<EditDocumentTypeResult> editDocumentType(UserVisitPK userVisitPK, EditDocumentTypeForm form);

    CommandResult<?> deleteDocumentType(UserVisitPK userVisitPK, DeleteDocumentTypeForm form);

    // --------------------------------------------------------------------------------
    //   Document Type Descriptions
    // --------------------------------------------------------------------------------

    CommandResult<?> createDocumentTypeDescription(UserVisitPK userVisitPK, CreateDocumentTypeDescriptionForm form);

    CommandResult<GetDocumentTypeDescriptionResult> getDocumentTypeDescription(UserVisitPK userVisitPK, GetDocumentTypeDescriptionForm form);

    CommandResult<GetDocumentTypeDescriptionsResult> getDocumentTypeDescriptions(UserVisitPK userVisitPK, GetDocumentTypeDescriptionsForm form);

    CommandResult<EditDocumentTypeDescriptionResult> editDocumentTypeDescription(UserVisitPK userVisitPK, EditDocumentTypeDescriptionForm form);

    CommandResult<?> deleteDocumentTypeDescription(UserVisitPK userVisitPK, DeleteDocumentTypeDescriptionForm form);

    // --------------------------------------------------------------------------------
    //   Document Type Usage Types
    // --------------------------------------------------------------------------------

    CommandResult<?> createDocumentTypeUsageType(UserVisitPK userVisitPK, CreateDocumentTypeUsageTypeForm form);

    CommandResult<GetDocumentTypeUsageTypeChoicesResult> getDocumentTypeUsageTypeChoices(UserVisitPK userVisitPK, GetDocumentTypeUsageTypeChoicesForm form);

    CommandResult<GetDocumentTypeUsageTypeResult> getDocumentTypeUsageType(UserVisitPK userVisitPK, GetDocumentTypeUsageTypeForm form);

    CommandResult<GetDocumentTypeUsageTypesResult> getDocumentTypeUsageTypes(UserVisitPK userVisitPK, GetDocumentTypeUsageTypesForm form);

    CommandResult<?> setDefaultDocumentTypeUsageType(UserVisitPK userVisitPK, SetDefaultDocumentTypeUsageTypeForm form);

    CommandResult<EditDocumentTypeUsageTypeResult> editDocumentTypeUsageType(UserVisitPK userVisitPK, EditDocumentTypeUsageTypeForm form);

    CommandResult<?> deleteDocumentTypeUsageType(UserVisitPK userVisitPK, DeleteDocumentTypeUsageTypeForm form);

    // --------------------------------------------------------------------------------
    //   Document Type Usage Type Descriptions
    // --------------------------------------------------------------------------------

    CommandResult<?> createDocumentTypeUsageTypeDescription(UserVisitPK userVisitPK, CreateDocumentTypeUsageTypeDescriptionForm form);

    CommandResult<GetDocumentTypeUsageTypeDescriptionResult> getDocumentTypeUsageTypeDescription(UserVisitPK userVisitPK, GetDocumentTypeUsageTypeDescriptionForm form);

    CommandResult<GetDocumentTypeUsageTypeDescriptionsResult> getDocumentTypeUsageTypeDescriptions(UserVisitPK userVisitPK, GetDocumentTypeUsageTypeDescriptionsForm form);

    CommandResult<EditDocumentTypeUsageTypeDescriptionResult> editDocumentTypeUsageTypeDescription(UserVisitPK userVisitPK, EditDocumentTypeUsageTypeDescriptionForm form);

    CommandResult<?> deleteDocumentTypeUsageTypeDescription(UserVisitPK userVisitPK, DeleteDocumentTypeUsageTypeDescriptionForm form);

    // --------------------------------------------------------------------------------
    //   Document Type Usage Descriptions
    // --------------------------------------------------------------------------------

    CommandResult<?> createDocumentTypeUsage(UserVisitPK userVisitPK, CreateDocumentTypeUsageForm form);

    CommandResult<GetDocumentTypeUsageResult> getDocumentTypeUsage(UserVisitPK userVisitPK, GetDocumentTypeUsageForm form);

    CommandResult<GetDocumentTypeUsagesResult> getDocumentTypeUsages(UserVisitPK userVisitPK, GetDocumentTypeUsagesForm form);

    CommandResult<?> setDefaultDocumentTypeUsage(UserVisitPK userVisitPK, SetDefaultDocumentTypeUsageForm form);

    CommandResult<EditDocumentTypeUsageResult> editDocumentTypeUsage(UserVisitPK userVisitPK, EditDocumentTypeUsageForm form);

    CommandResult<?> deleteDocumentTypeUsage(UserVisitPK userVisitPK, DeleteDocumentTypeUsageForm form);

    // --------------------------------------------------------------------------------
    //   Party Type Document Type Usage Types
    // --------------------------------------------------------------------------------

    CommandResult<?> createPartyTypeDocumentTypeUsageType(UserVisitPK userVisitPK, CreatePartyTypeDocumentTypeUsageTypeForm form);

    CommandResult<GetPartyTypeDocumentTypeUsageTypeResult> getPartyTypeDocumentTypeUsageType(UserVisitPK userVisitPK, GetPartyTypeDocumentTypeUsageTypeForm form);

    CommandResult<GetPartyTypeDocumentTypeUsageTypesResult> getPartyTypeDocumentTypeUsageTypes(UserVisitPK userVisitPK, GetPartyTypeDocumentTypeUsageTypesForm form);

    CommandResult<?> setDefaultPartyTypeDocumentTypeUsageType(UserVisitPK userVisitPK, SetDefaultPartyTypeDocumentTypeUsageTypeForm form);

    CommandResult<EditPartyTypeDocumentTypeUsageTypeResult> editPartyTypeDocumentTypeUsageType(UserVisitPK userVisitPK, EditPartyTypeDocumentTypeUsageTypeForm form);

    CommandResult<?> deletePartyTypeDocumentTypeUsageType(UserVisitPK userVisitPK, DeletePartyTypeDocumentTypeUsageTypeForm form);

    // --------------------------------------------------------------------------------
    //   Party Documents
    // --------------------------------------------------------------------------------

    CommandResult<?> createPartyDocument(UserVisitPK userVisitPK, CreatePartyDocumentForm form);

    CommandResult<GetPartyDocumentResult> getPartyDocument(UserVisitPK userVisitPK, GetPartyDocumentForm form);

    CommandResult<GetPartyDocumentsResult> getPartyDocuments(UserVisitPK userVisitPK, GetPartyDocumentsForm form);

    CommandResult<?> setDefaultPartyDocument(UserVisitPK userVisitPK, SetDefaultPartyDocumentForm form);

    CommandResult<EditPartyDocumentResult> editPartyDocument(UserVisitPK userVisitPK, EditPartyDocumentForm form);

    CommandResult<?> deletePartyDocument(UserVisitPK userVisitPK, DeletePartyDocumentForm form);

    // --------------------------------------------------------------------------------
    //   Document Descriptions
    // --------------------------------------------------------------------------------

    CommandResult<?> createDocumentDescription(UserVisitPK userVisitPK, CreateDocumentDescriptionForm form);

    CommandResult<GetDocumentDescriptionResult> getDocumentDescription(UserVisitPK userVisitPK, GetDocumentDescriptionForm form);

    CommandResult<GetDocumentDescriptionsResult> getDocumentDescriptions(UserVisitPK userVisitPK, GetDocumentDescriptionsForm form);

    CommandResult<EditDocumentDescriptionResult> editDocumentDescription(UserVisitPK userVisitPK, EditDocumentDescriptionForm form);

    CommandResult<?> deleteDocumentDescription(UserVisitPK userVisitPK, DeleteDocumentDescriptionForm form);

}
