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

package com.echothree.model.control.index.server.transfer;

import com.echothree.model.control.index.common.IndexOptions;
import com.echothree.model.control.index.common.transfer.IndexTransfer;
import com.echothree.model.control.index.common.transfer.IndexTypeTransfer;
import com.echothree.model.control.index.server.IndexControl;
import com.echothree.model.control.party.common.transfer.LanguageTransfer;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.data.index.server.entity.Index;
import com.echothree.model.data.index.server.entity.IndexDetail;
import com.echothree.model.data.index.server.entity.IndexStatus;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import java.util.Set;

public class IndexTransferCache
        extends BaseIndexTransferCache<Index, IndexTransfer> {

    PartyControl partyControl = (PartyControl)Session.getModelController(PartyControl.class);
    
    /** Creates a new instance of IndexTransferCache */
    public IndexTransferCache(UserVisit userVisit, IndexControl indexControl) {
        super(userVisit, indexControl);
        
        Set<String> options = session.getOptions();
        if(options != null) {
            setIncludeKey(options.contains(IndexOptions.IndexIncludeKey));
            setIncludeGuid(options.contains(IndexOptions.IndexIncludeGuid));
        }
        
        setIncludeEntityInstance(true);
    }

    public IndexTransfer getIndexTransfer(Index index) {
        IndexTransfer indexTransfer = get(index);

        if(indexTransfer == null) {
            IndexDetail indexDetail = index.getLastDetail();
            IndexStatus indexStatus = indexControl.getIndexStatus(index);
            String indexName = indexDetail.getIndexName();
            IndexTypeTransfer indexTypeTransfer = indexControl.getIndexTypeTransfer(userVisit, indexDetail.getIndexType());
            Language language = indexDetail.getLanguage();
            LanguageTransfer languageTransfer = language == null ? null : partyControl.getLanguageTransfer(userVisit, language);
            String directory = indexDetail.getDirectory();
            Boolean isDefault = indexDetail.getIsDefault();
            Integer sortOrder = indexDetail.getSortOrder();
            String description = indexControl.getBestIndexDescription(index, getLanguage());
            Long unformattedCreatedTime = indexStatus.getCreatedTime();
            String createdTime = formatTypicalDateTime(unformattedCreatedTime);

            indexTransfer = new IndexTransfer(indexName, indexTypeTransfer, languageTransfer, directory, isDefault, sortOrder, description,
                    unformattedCreatedTime, createdTime);
            put(index, indexTransfer);
        }

        return indexTransfer;
    }

}
