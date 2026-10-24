// --------------------------------------------------------------------------------
// Copyright 2002-2026 Echo Three, LLC
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

package com.echothree.model.control.core.server.transfer;

import javax.inject.Inject;
import com.echothree.model.control.core.common.transfer.LinkDirectionalityTransfer;
import com.echothree.model.control.core.server.control.LinkDirectionalityControl;
import com.echothree.model.data.core.server.entity.LinkDirectionality;
import com.echothree.model.data.user.server.entity.UserVisit;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class LinkDirectionalityTransferCache
        extends BaseCoreTransferCache<LinkDirectionality, LinkDirectionalityTransfer> {

    @Inject
    LinkDirectionalityControl linkDirectionalityControl;

    /** Creates a new instance of LinkDirectionalityTransferCache */
    protected LinkDirectionalityTransferCache() {
        super();

        setIncludeEntityInstance(true);
    }

    public LinkDirectionalityTransfer getTransfer(UserVisit userVisit, LinkDirectionality linkDirectionality) {
        var linkDirectionalityTransfer = get(linkDirectionality);

        if(linkDirectionalityTransfer == null) {
            var linkDirectionalityDetail = linkDirectionality.getLastDetail();
            var linkDirectionalityName = linkDirectionalityDetail.getLinkDirectionalityName();
            var isDefault = linkDirectionalityDetail.getIsDefault();
            var sortOrder = linkDirectionalityDetail.getSortOrder();
            var description = linkDirectionalityControl.getBestLinkDirectionalityDescription(linkDirectionality, getLanguage(userVisit));

            linkDirectionalityTransfer = new LinkDirectionalityTransfer(linkDirectionalityName, isDefault,
                    sortOrder, description);
            put(userVisit, linkDirectionality, linkDirectionalityTransfer);
        }

        return linkDirectionalityTransfer;
    }

}
