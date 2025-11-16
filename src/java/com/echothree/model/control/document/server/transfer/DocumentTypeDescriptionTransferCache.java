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

package com.echothree.model.control.document.server.transfer;

import com.echothree.model.control.document.common.transfer.DocumentTypeDescriptionTransfer;
import com.echothree.model.control.document.server.control.DocumentControl;
import com.echothree.model.data.document.server.entity.DocumentTypeDescription;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class DocumentTypeDescriptionTransferCache
        extends BaseDocumentDescriptionTransferCache<DocumentTypeDescription, DocumentTypeDescriptionTransfer> {

    DocumentControl documentControl = Session.getModelController(DocumentControl.class);

    /** Creates a new instance of DocumentTypeDescriptionTransferCache */
    public DocumentTypeDescriptionTransferCache() {
        super();
    }
    
    public DocumentTypeDescriptionTransfer getDocumentTypeDescriptionTransfer(UserVisit userVisit, DocumentTypeDescription documentTypeDescription) {
        var documentTypeDescriptionTransfer = get(documentTypeDescription);
        
        if(documentTypeDescriptionTransfer == null) {
            var documentTypeTransfer = documentControl.getDocumentTypeTransfer(userVisit, documentTypeDescription.getDocumentType());
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, documentTypeDescription.getLanguage());
            
            documentTypeDescriptionTransfer = new DocumentTypeDescriptionTransfer(languageTransfer, documentTypeTransfer, documentTypeDescription.getDescription());
            put(userVisit, documentTypeDescription, documentTypeDescriptionTransfer);
        }
        
        return documentTypeDescriptionTransfer;
    }
    
}
