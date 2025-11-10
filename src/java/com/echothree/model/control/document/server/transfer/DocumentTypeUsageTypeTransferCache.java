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

import com.echothree.model.control.document.common.transfer.DocumentTypeUsageTypeTransfer;
import com.echothree.model.control.document.server.control.DocumentControl;
import com.echothree.model.data.document.server.entity.DocumentTypeUsageType;
import com.echothree.model.data.user.server.entity.UserVisit;

public class DocumentTypeUsageTypeTransferCache
        extends BaseDocumentTransferCache<DocumentTypeUsageType, DocumentTypeUsageTypeTransfer> {
    
    /** Creates a new instance of DocumentTypeUsageTypeTransferCache */
    public DocumentTypeUsageTypeTransferCache(UserVisit userVisit, DocumentControl documentControl) {
        super(userVisit, documentControl);
        
        setIncludeEntityInstance(true);
    }
    
    public DocumentTypeUsageTypeTransfer getDocumentTypeUsageTypeTransfer(DocumentTypeUsageType documentTypeUsageType) {
        var documentTypeUsageTypeTransfer = get(documentTypeUsageType);
        
        if(documentTypeUsageTypeTransfer == null) {
            var documentTypeUsageTypeDetail = documentTypeUsageType.getLastDetail();
            var documentTypeUsageTypeName = documentTypeUsageTypeDetail.getDocumentTypeUsageTypeName();
            var isDefault = documentTypeUsageTypeDetail.getIsDefault();
            var sortOrder = documentTypeUsageTypeDetail.getSortOrder();
            var description = documentControl.getBestDocumentTypeUsageTypeDescription(documentTypeUsageType, getLanguage(userVisit));
            
            documentTypeUsageTypeTransfer = new DocumentTypeUsageTypeTransfer(documentTypeUsageTypeName, isDefault, sortOrder, description);
            put(userVisit, documentTypeUsageType, documentTypeUsageTypeTransfer);
        }
        return documentTypeUsageTypeTransfer;
    }
    
}
