// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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

import com.echothree.model.control.core.common.transfer.EntityTimeTransfer;
import com.echothree.model.control.core.common.transfer.MimeTypeTransfer;
import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.control.document.common.DocumentOptions;
import com.echothree.model.control.document.common.transfer.DocumentTransfer;
import com.echothree.model.control.document.common.transfer.DocumentTypeTransfer;
import com.echothree.model.control.document.server.control.DocumentControl;
import com.echothree.model.data.document.server.entity.Document;
import com.echothree.model.data.document.server.entity.DocumentBlob;
import com.echothree.model.data.document.server.entity.DocumentClob;
import com.echothree.model.data.document.server.entity.DocumentDetail;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.persistence.type.ByteArray;
import com.echothree.util.server.persistence.Session;
import java.util.Set;

public class DocumentTransferCache
        extends BaseDocumentTransferCache<Document, DocumentTransfer> {
    
    CoreControl coreControl = (CoreControl)Session.getModelController(CoreControl.class);
    boolean includeBlob;
    boolean includeClob;
    boolean includeETag;
    
    /** Creates a new instance of DocumentTransferCache */
    public DocumentTransferCache(UserVisit userVisit, DocumentControl documentControl) {
        super(userVisit, documentControl);
        
        Set<String> options = session.getOptions();
        if(options != null) {
            includeBlob = options.contains(DocumentOptions.DocumentIncludeBlob);
            includeClob = options.contains(DocumentOptions.DocumentIncludeClob);
            includeETag = options.contains(DocumentOptions.DocumentIncludeETag);
        }
        
        setIncludeEntityInstance(true);
    }
    
    public DocumentTransfer getDocumentTransfer(Document document) {
        DocumentTransfer documentTransfer = get(document);
        
        if(documentTransfer == null) {
            DocumentDetail documentDetail = document.getLastDetail();
            String documentName = documentDetail.getDocumentName();
            DocumentTypeTransfer documentType = documentControl.getDocumentTypeTransfer(userVisit, documentDetail.getDocumentType());
            MimeTypeTransfer mimeType = coreControl.getMimeTypeTransfer(userVisit, documentDetail.getMimeType());
            Integer pages = documentDetail.getPages();
            String description = documentControl.getBestDocumentDescription(document, getLanguage());
            ByteArray blob = null;
            String clob = null;
            String eTag = null;
            long eTagEntityId = 0;
            int eTagSize = 0;
            
            if(includeBlob) {
                DocumentBlob documentBlob = documentControl.getDocumentBlob(document);
                
                if(documentBlob != null) {
                    blob = documentBlob.getBlob();
                }
            }
            
            if(includeClob) {
                DocumentClob documentClob = documentControl.getDocumentClob(document);
                
                if(documentClob != null) {
                    clob = documentClob.getClob();
                }
            }
            
            if(includeETag && eTagEntityId != 0) {
                EntityTimeTransfer entityTimeTransfer = documentTransfer.getEntityInstance().getEntityTime();
                Long modifiedTime = entityTimeTransfer.getUnformattedModifiedTime();
                long maxTime = modifiedTime == null ? entityTimeTransfer.getUnformattedCreatedTime() : modifiedTime;

                // EntityId-Size-ModifiedTime
                eTag = new StringBuilder(Long.toHexString(eTagEntityId)).append('-').append(Integer.toHexString(eTagSize)).append('-').append(Long.toHexString(maxTime)).toString();
            }

            documentTransfer = new DocumentTransfer(documentName, documentType, mimeType, pages, description, blob, clob, eTag);
            put(document, documentTransfer);
        }
        
        return documentTransfer;
    }
    
}
