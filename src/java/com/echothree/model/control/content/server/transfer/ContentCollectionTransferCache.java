// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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
import com.echothree.model.control.content.server.ContentControl;
import com.echothree.model.control.offer.common.transfer.OfferUseTransfer;
import com.echothree.model.control.offer.server.OfferControl;
import com.echothree.model.data.content.server.entity.ContentCollection;
import com.echothree.model.data.content.server.entity.ContentCollectionDetail;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.transfer.ListWrapper;
import com.echothree.util.server.persistence.Session;
import java.util.Set;

public class ContentCollectionTransferCache
        extends BaseContentTransferCache<ContentCollection, ContentCollectionTransfer> {

    OfferControl offerControl = (OfferControl)Session.getModelController(OfferControl.class);
    boolean includeContentCatalogs;
    boolean includeContentForums;
    boolean includeContentSections;

    /** Creates a new instance of ContentCollectionTransferCache */
    public ContentCollectionTransferCache(UserVisit userVisit, ContentControl contentControl) {
        super(userVisit, contentControl);

        Set<String> options = session.getOptions();
        if(options != null) {
            includeContentCatalogs = options.contains(ContentOptions.ContentCollectionIncludeContentCatalogs);
            includeContentForums = options.contains(ContentOptions.ContentCollectionIncludeContentForums);
            includeContentSections = options.contains(ContentOptions.ContentCollectionIncludeContentSections);
            setIncludeKey(options.contains(ContentOptions.ContentCollectionIncludeKey));
            setIncludeGuid(options.contains(ContentOptions.ContentCollectionIncludeGuid));
            setIncludeEntityAttributeGroups(options.contains(ContentOptions.ContentCollectionIncludeEntityAttributeGroups));
            setIncludeTagScopes(options.contains(ContentOptions.ContentCollectionIncludeTagScopes));
        }
        
        setIncludeEntityInstance(true);
    }

    public ContentCollectionTransfer getContentCollectionTransfer(ContentCollection contentCollection) {
        ContentCollectionTransfer contentCollectionTransfer = get(contentCollection);
        
        if(contentCollectionTransfer == null) {
            ContentCollectionDetail contentCollectionDetail = contentCollection.getLastDetail();
            String contentCollectionName = contentCollectionDetail.getContentCollectionName();
            OfferUseTransfer defaultOfferUse = offerControl.getOfferUseTransfer(userVisit, contentCollectionDetail.getDefaultOfferUse());
            String description = contentControl.getBestContentCollectionDescription(contentCollection, getLanguage());
            
            contentCollectionTransfer = new ContentCollectionTransfer(contentCollectionName, defaultOfferUse, description);
            put(contentCollection, contentCollectionTransfer);
            
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
