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

import com.echothree.model.control.core.server.control.MimeTypeControl;
import com.echothree.model.control.document.common.transfer.DocumentTypeTransfer;
import com.echothree.model.control.document.server.control.DocumentControl;
import com.echothree.model.data.document.server.entity.DocumentType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class DocumentTypeTransferCache
        extends BaseDocumentTransferCache<DocumentType, DocumentTypeTransfer> {

    MimeTypeControl mimeTypeControl = Session.getModelController(MimeTypeControl.class);

    /** Creates a new instance of DocumentTypeTransferCache */
    public DocumentTypeTransferCache(UserVisit userVisit, DocumentControl documentControl) {
        super(userVisit, documentControl);
        
        setIncludeEntityInstance(true);
    }
    
    public DocumentTypeTransfer getDocumentTypeTransfer(DocumentType documentType) {
        var documentTypeTransfer = get(documentType);
        
        if(documentTypeTransfer == null) {
            var documentTypeDetail = documentType.getLastDetail();
            var documentTypeName = documentTypeDetail.getDocumentTypeName();
            var parentDocumentType = documentTypeDetail.getParentDocumentType();
            var parentDocumentTypeTransfer = parentDocumentType == null ? null : documentControl.getDocumentTypeTransfer(userVisit, parentDocumentType);
            var mimeTypeUsageType = documentTypeDetail.getMimeTypeUsageType();
            var mimeTypeUsageTypeTransfer = mimeTypeUsageType == null ? null : mimeTypeControl.getMimeTypeUsageTypeTransfer(userVisit, mimeTypeUsageType);
            var maximumPages = documentTypeDetail.getMaximumPages();
            var isDefault = documentTypeDetail.getIsDefault();
            var sortOrder = documentTypeDetail.getSortOrder();
            var description = documentControl.getBestDocumentTypeDescription(documentType, getLanguage());
            
            documentTypeTransfer = new DocumentTypeTransfer(documentTypeName, parentDocumentTypeTransfer, mimeTypeUsageTypeTransfer, maximumPages, isDefault,
                    sortOrder, description);
            put(documentType, documentTypeTransfer);
        }
        return documentTypeTransfer;
    }
    
}
