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

import com.echothree.util.server.transfer.BaseTransferCaches;

public class DocumentTransferCaches
        extends BaseTransferCaches {
    
    protected DocumentTypeTransferCache documentTypeTransferCache;
    protected DocumentTypeDescriptionTransferCache documentTypeDescriptionTransferCache;
    protected DocumentTypeUsageTypeTransferCache documentTypeUsageTypeTransferCache;
    protected DocumentTypeUsageTypeDescriptionTransferCache documentTypeUsageTypeDescriptionTransferCache;
    protected DocumentTypeUsageTransferCache documentTypeUsageTransferCache;
    protected DocumentTransferCache documentTransferCache;
    protected DocumentDescriptionTransferCache documentDescriptionTransferCache;
    protected PartyTypeDocumentTypeUsageTypeTransferCache partyTypeDocumentTypeUsageTypeTransferCache;
    protected PartyDocumentTransferCache partyDocumentTransferCache;
    
    /** Creates a new instance of DocumentTransferCaches */
    public DocumentTransferCaches() {
        super();
    }
    
    public DocumentTypeTransferCache getDocumentTypeTransferCache() {
        if(documentTypeTransferCache == null)
            documentTypeTransferCache = new DocumentTypeTransferCache();

        return documentTypeTransferCache;
    }

    public DocumentTypeDescriptionTransferCache getDocumentTypeDescriptionTransferCache() {
        if(documentTypeDescriptionTransferCache == null)
            documentTypeDescriptionTransferCache = new DocumentTypeDescriptionTransferCache();

        return documentTypeDescriptionTransferCache;
    }

    public DocumentTypeUsageTypeTransferCache getDocumentTypeUsageTypeTransferCache() {
        if(documentTypeUsageTypeTransferCache == null)
            documentTypeUsageTypeTransferCache = new DocumentTypeUsageTypeTransferCache();

        return documentTypeUsageTypeTransferCache;
    }

    public DocumentTypeUsageTypeDescriptionTransferCache getDocumentTypeUsageTypeDescriptionTransferCache() {
        if(documentTypeUsageTypeDescriptionTransferCache == null)
            documentTypeUsageTypeDescriptionTransferCache = new DocumentTypeUsageTypeDescriptionTransferCache();

        return documentTypeUsageTypeDescriptionTransferCache;
    }

    public DocumentTypeUsageTransferCache getDocumentTypeUsageTransferCache() {
        if(documentTypeUsageTransferCache == null)
            documentTypeUsageTransferCache = new DocumentTypeUsageTransferCache();

        return documentTypeUsageTransferCache;
    }

    public DocumentTransferCache getDocumentTransferCache() {
        if(documentTransferCache == null)
            documentTransferCache = new DocumentTransferCache();
        
        return documentTransferCache;
    }
    
    public DocumentDescriptionTransferCache getDocumentDescriptionTransferCache() {
        if(documentDescriptionTransferCache == null)
            documentDescriptionTransferCache = new DocumentDescriptionTransferCache();

        return documentDescriptionTransferCache;
    }

    public PartyTypeDocumentTypeUsageTypeTransferCache getPartyTypeDocumentTypeUsageTypeTransferCache() {
        if(partyTypeDocumentTypeUsageTypeTransferCache == null)
            partyTypeDocumentTypeUsageTypeTransferCache = new PartyTypeDocumentTypeUsageTypeTransferCache();

        return partyTypeDocumentTypeUsageTypeTransferCache;
    }

    public PartyDocumentTransferCache getPartyDocumentTransferCache() {
        if(partyDocumentTransferCache == null)
            partyDocumentTransferCache = new PartyDocumentTransferCache();

        return partyDocumentTransferCache;
    }

}
