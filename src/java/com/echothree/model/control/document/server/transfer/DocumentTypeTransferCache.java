// --------------------------------------------------------------------------------
// Copyright 2002-2023 Echo Three, LLC
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

import com.echothree.model.control.core.common.transfer.MimeTypeUsageTypeTransfer;
import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.control.document.common.transfer.DocumentTypeTransfer;
import com.echothree.model.control.document.server.control.DocumentControl;
import com.echothree.model.data.core.server.entity.MimeTypeUsageType;
import com.echothree.model.data.document.server.entity.DocumentType;
import com.echothree.model.data.document.server.entity.DocumentTypeDetail;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class DocumentTypeTransferCache
        extends BaseDocumentTransferCache<DocumentType, DocumentTypeTransfer> {
    
    CoreControl coreControl = Session.getModelController(CoreControl.class);

    /** Creates a new instance of DocumentTypeTransferCache */
    public DocumentTypeTransferCache(UserVisit userVisit, DocumentControl documentControl) {
        super(userVisit, documentControl);
        
        setIncludeEntityInstance(true);
    }
    
    public DocumentTypeTransfer getDocumentTypeTransfer(DocumentType documentType) {
        DocumentTypeTransfer documentTypeTransfer = get(documentType);
        
        if(documentTypeTransfer == null) {
            DocumentTypeDetail documentTypeDetail = documentType.getLastDetail();
            String documentTypeName = documentTypeDetail.getDocumentTypeName();
            DocumentType parentDocumentType = documentTypeDetail.getParentDocumentType();
            DocumentTypeTransfer parentDocumentTypeTransfer = parentDocumentType == null ? null : documentControl.getDocumentTypeTransfer(userVisit, parentDocumentType);
            MimeTypeUsageType mimeTypeUsageType = documentTypeDetail.getMimeTypeUsageType();
            MimeTypeUsageTypeTransfer mimeTypeUsageTypeTransfer = mimeTypeUsageType == null ? null : coreControl.getMimeTypeUsageTypeTransfer(userVisit, mimeTypeUsageType);
            Integer maximumPages = documentTypeDetail.getMaximumPages();
            Boolean isDefault = documentTypeDetail.getIsDefault();
            Integer sortOrder = documentTypeDetail.getSortOrder();
            String description = documentControl.getBestDocumentTypeDescription(documentType, getLanguage());
            
            documentTypeTransfer = new DocumentTypeTransfer(documentTypeName, parentDocumentTypeTransfer, mimeTypeUsageTypeTransfer, maximumPages, isDefault,
                    sortOrder, description);
            put(documentType, documentTypeTransfer);
        }
        return documentTypeTransfer;
    }
    
}
