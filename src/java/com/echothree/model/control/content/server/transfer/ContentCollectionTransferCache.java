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

package com.echothree.model.control.content.server.transfer;

import com.echothree.model.control.content.common.ContentOptions;
import com.echothree.model.control.content.common.transfer.ContentCollectionTransfer;
import com.echothree.model.control.content.server.control.ContentControl;
import com.echothree.model.control.offer.server.control.OfferUseControl;
import com.echothree.model.data.content.server.entity.ContentCollection;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.transfer.ListWrapper;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class ContentCollectionTransferCache
        extends BaseContentTransferCache<ContentCollection, ContentCollectionTransfer> {

    ContentControl contentControl = Session.getModelController(ContentControl.class);
    OfferUseControl offerUseControl = Session.getModelController(OfferUseControl.class);

    boolean includeContentCatalogs;
    boolean includeContentForums;
    boolean includeContentSections;

    /** Creates a new instance of ContentCollectionTransferCache */
    protected ContentCollectionTransferCache() {
        super();

        var options = session.getOptions();
        if(options != null) {
            includeContentCatalogs = options.contains(ContentOptions.ContentCollectionIncludeContentCatalogs);
            includeContentForums = options.contains(ContentOptions.ContentCollectionIncludeContentForums);
            includeContentSections = options.contains(ContentOptions.ContentCollectionIncludeContentSections);
            setIncludeUuid(options.contains(ContentOptions.ContentCollectionIncludeUuid));
            setIncludeEntityAttributeGroups(options.contains(ContentOptions.ContentCollectionIncludeEntityAttributeGroups));
            setIncludeTagScopes(options.contains(ContentOptions.ContentCollectionIncludeTagScopes));
        }
        
        setIncludeEntityInstance(true);
    }

    public ContentCollectionTransfer getContentCollectionTransfer(UserVisit userVisit, ContentCollection contentCollection) {
        var contentCollectionTransfer = get(contentCollection);
        
        if(contentCollectionTransfer == null) {
            var contentCollectionDetail = contentCollection.getLastDetail();
            var contentCollectionName = contentCollectionDetail.getContentCollectionName();
            var defaultOfferUse = offerUseControl.getOfferUseTransfer(userVisit, contentCollectionDetail.getDefaultOfferUse());
            var description = contentControl.getBestContentCollectionDescription(contentCollection, getLanguage(userVisit));
            
            contentCollectionTransfer = new ContentCollectionTransfer(contentCollectionName, defaultOfferUse, description);
            put(userVisit, contentCollection, contentCollectionTransfer);
            
            if(includeContentCatalogs) {
                contentCollectionTransfer.setContentCatalogs(new ListWrapper<>(contentControl.getContentCatalogTransfers(userVisit, contentCollection)));
            }

            if(includeContentForums) {
                contentCollectionTransfer.setContentForums(new ListWrapper<>(contentControl.getContentForumTransfers(userVisit, contentCollection)));
            }

            if(includeContentSections) {
                contentCollectionTransfer.setContentSections(new ListWrapper<>(contentControl.getContentSectionTransfers(userVisit, contentCollection)));
            }
        }
        
        return contentCollectionTransfer;
    }
    
}
