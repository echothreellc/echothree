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

package com.echothree.model.control.index.server.transfer;

import com.echothree.model.control.index.common.IndexOptions;
import com.echothree.model.control.index.common.transfer.IndexTransfer;
import com.echothree.model.control.index.server.control.IndexControl;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.data.index.server.entity.Index;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class IndexTransferCache
        extends BaseIndexTransferCache<Index, IndexTransfer> {

    PartyControl partyControl = Session.getModelController(PartyControl.class);
    
    /** Creates a new instance of IndexTransferCache */
    public IndexTransferCache(IndexControl indexControl) {
        super(indexControl);
        
        var options = session.getOptions();
        if(options != null) {
            setIncludeUuid(options.contains(IndexOptions.IndexIncludeUuid));
        }
        
        setIncludeEntityInstance(true);
    }

    public IndexTransfer getIndexTransfer(UserVisit userVisit, Index index) {
        var indexTransfer = get(index);

        if(indexTransfer == null) {
            var indexDetail = index.getLastDetail();
            var indexStatus = indexControl.getIndexStatus(index);
            var indexName = indexDetail.getIndexName();
            var indexTypeTransfer = indexControl.getIndexTypeTransfer(userVisit, indexDetail.getIndexType());
            var language = indexDetail.getLanguage();
            var languageTransfer = language == null ? null : partyControl.getLanguageTransfer(userVisit, language);
            var directory = indexDetail.getDirectory();
            var isDefault = indexDetail.getIsDefault();
            var sortOrder = indexDetail.getSortOrder();
            var description = indexControl.getBestIndexDescription(index, getLanguage(userVisit));
            var unformattedCreatedTime = indexStatus.getCreatedTime();
            var createdTime = formatTypicalDateTime(userVisit, unformattedCreatedTime);

            indexTransfer = new IndexTransfer(indexName, indexTypeTransfer, languageTransfer, directory, isDefault, sortOrder, description,
                    unformattedCreatedTime, createdTime);
            put(userVisit, index, indexTransfer);
        }

        return indexTransfer;
    }

}
