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
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@RequestScoped
public class DocumentTransferCaches
        extends BaseTransferCaches {
    
    @Inject
    DocumentTypeTransferCache documentTypeTransferCache;
    
    @Inject
    DocumentTypeDescriptionTransferCache documentTypeDescriptionTransferCache;
    
    @Inject
    DocumentTypeUsageTypeTransferCache documentTypeUsageTypeTransferCache;
    
    @Inject
    DocumentTypeUsageTypeDescriptionTransferCache documentTypeUsageTypeDescriptionTransferCache;
    
    @Inject
    DocumentTypeUsageTransferCache documentTypeUsageTransferCache;
    
    @Inject
    DocumentTransferCache documentTransferCache;
    
    @Inject
    DocumentDescriptionTransferCache documentDescriptionTransferCache;
    
    @Inject
    PartyTypeDocumentTypeUsageTypeTransferCache partyTypeDocumentTypeUsageTypeTransferCache;
    
    @Inject
    PartyDocumentTransferCache partyDocumentTransferCache;

    /** Creates a new instance of DocumentTransferCaches */
    protected DocumentTransferCaches() {
        super();
    }
    
    public DocumentTypeTransferCache getDocumentTypeTransferCache() {
        return documentTypeTransferCache;
    }

    public DocumentTypeDescriptionTransferCache getDocumentTypeDescriptionTransferCache() {
        return documentTypeDescriptionTransferCache;
    }

    public DocumentTypeUsageTypeTransferCache getDocumentTypeUsageTypeTransferCache() {
        return documentTypeUsageTypeTransferCache;
    }

    public DocumentTypeUsageTypeDescriptionTransferCache getDocumentTypeUsageTypeDescriptionTransferCache() {
        return documentTypeUsageTypeDescriptionTransferCache;
    }

    public DocumentTypeUsageTransferCache getDocumentTypeUsageTransferCache() {
        return documentTypeUsageTransferCache;
    }

    public DocumentTransferCache getDocumentTransferCache() {
        return documentTransferCache;
    }
    
    public DocumentDescriptionTransferCache getDocumentDescriptionTransferCache() {
        return documentDescriptionTransferCache;
    }

    public PartyTypeDocumentTypeUsageTypeTransferCache getPartyTypeDocumentTypeUsageTypeTransferCache() {
        return partyTypeDocumentTypeUsageTypeTransferCache;
    }

    public PartyDocumentTransferCache getPartyDocumentTransferCache() {
        return partyDocumentTransferCache;
    }

}
