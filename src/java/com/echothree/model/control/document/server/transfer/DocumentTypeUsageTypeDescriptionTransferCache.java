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

package com.echothree.model.control.document.server.transfer;

import com.echothree.model.control.document.common.transfer.DocumentTypeUsageTypeDescriptionTransfer;
import com.echothree.model.control.document.server.control.DocumentControl;
import com.echothree.model.data.document.server.entity.DocumentTypeUsageTypeDescription;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class DocumentTypeUsageTypeDescriptionTransferCache
        extends BaseDocumentDescriptionTransferCache<DocumentTypeUsageTypeDescription, DocumentTypeUsageTypeDescriptionTransfer> {

    DocumentControl documentControl = Session.getModelController(DocumentControl.class);

    /** Creates a new instance of DocumentTypeUsageTypeDescriptionTransferCache */
    protected DocumentTypeUsageTypeDescriptionTransferCache() {
        super();
    }
    
    public DocumentTypeUsageTypeDescriptionTransfer getDocumentTypeUsageTypeDescriptionTransfer(UserVisit userVisit, DocumentTypeUsageTypeDescription documentTypeUsageTypeDescription) {
        var documentTypeUsageTypeDescriptionTransfer = get(documentTypeUsageTypeDescription);
        
        if(documentTypeUsageTypeDescriptionTransfer == null) {
            var documentTypeUsageTypeTransfer = documentControl.getDocumentTypeUsageTypeTransfer(userVisit, documentTypeUsageTypeDescription.getDocumentTypeUsageType());
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, documentTypeUsageTypeDescription.getLanguage());
            
            documentTypeUsageTypeDescriptionTransfer = new DocumentTypeUsageTypeDescriptionTransfer(languageTransfer, documentTypeUsageTypeTransfer, documentTypeUsageTypeDescription.getDescription());
            put(userVisit, documentTypeUsageTypeDescription, documentTypeUsageTypeDescriptionTransfer);
        }
        
        return documentTypeUsageTypeDescriptionTransfer;
    }
    
}
