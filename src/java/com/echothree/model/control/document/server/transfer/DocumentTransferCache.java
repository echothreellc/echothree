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
import com.echothree.model.control.document.common.DocumentOptions;
import com.echothree.model.control.document.common.transfer.DocumentTransfer;
import com.echothree.model.control.document.server.control.DocumentControl;
import com.echothree.model.data.document.server.entity.Document;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.persistence.type.ByteArray;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class DocumentTransferCache
        extends BaseDocumentTransferCache<Document, DocumentTransfer> {

    DocumentControl documentControl = Session.getModelController(DocumentControl.class);
    MimeTypeControl mimeTypeControl = Session.getModelController(MimeTypeControl.class);

    boolean includeBlob;
    boolean includeClob;
    boolean includeETag;
    
    /** Creates a new instance of DocumentTransferCache */
    public DocumentTransferCache() {
        super();
        
        var options = session.getOptions();
        if(options != null) {
            includeBlob = options.contains(DocumentOptions.DocumentIncludeBlob);
            includeClob = options.contains(DocumentOptions.DocumentIncludeClob);
            includeETag = options.contains(DocumentOptions.DocumentIncludeETag);
        }
        
        setIncludeEntityInstance(true);
    }
    
    public DocumentTransfer getDocumentTransfer(UserVisit userVisit, Document document) {
        var documentTransfer = get(document);
        
        if(documentTransfer == null) {
            var documentDetail = document.getLastDetail();
            var documentName = documentDetail.getDocumentName();
            var documentType = documentControl.getDocumentTypeTransfer(userVisit, documentDetail.getDocumentType());
            var mimeType = mimeTypeControl.getMimeTypeTransfer(userVisit, documentDetail.getMimeType());
            var pages = documentDetail.getPages();
            var description = documentControl.getBestDocumentDescription(document, getLanguage(userVisit));
            ByteArray blob = null;
            String clob = null;
            String eTag = null;
            long eTagEntityId = 0;
            var eTagSize = 0;
            
            if(includeBlob) {
                var documentBlob = documentControl.getDocumentBlob(document);
                
                if(documentBlob != null) {
                    blob = documentBlob.getBlob();
                }
            }
            
            if(includeClob) {
                var documentClob = documentControl.getDocumentClob(document);
                
                if(documentClob != null) {
                    clob = documentClob.getClob();
                }
            }
            
            if(includeETag && eTagEntityId != 0) {
                var entityTimeTransfer = documentTransfer.getEntityInstance().getEntityTime();
                var modifiedTime = entityTimeTransfer.getUnformattedModifiedTime();
                long maxTime = modifiedTime == null ? entityTimeTransfer.getUnformattedCreatedTime() : modifiedTime;

                // EntityId-Size-ModifiedTime
                eTag = Long.toHexString(eTagEntityId) + '-' + Integer.toHexString(eTagSize) + '-' + Long.toHexString(maxTime);
            }

            documentTransfer = new DocumentTransfer(documentName, documentType, mimeType, pages, description, blob, clob, eTag);
            put(userVisit, document, documentTransfer);
        }
        
        return documentTransfer;
    }
    
}
