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

import com.echothree.model.control.core.common.transfer.LinkCardinalityTransfer;
import com.echothree.model.control.core.server.control.LinkCardinalityControl;
import com.echothree.model.data.core.server.entity.LinkCardinality;
import com.echothree.model.data.user.server.entity.UserVisit;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@RequestScoped
public class LinkCardinalityTransferCache
        extends BaseCoreTransferCache<LinkCardinality, LinkCardinalityTransfer> {

    @Inject
    LinkCardinalityControl linkCardinalityControl;

    /** Creates a new instance of LinkCardinalityTransferCache */
    protected LinkCardinalityTransferCache() {
        super();

        setIncludeEntityInstance(true);
    }

    public LinkCardinalityTransfer getTransfer(UserVisit userVisit, LinkCardinality linkCardinality) {
        var linkCardinalityTransfer = get(linkCardinality);

        if(linkCardinalityTransfer == null) {
            var linkCardinalityDetail = linkCardinality.getLastDetail();
            var linkCardinalityName = linkCardinalityDetail.getLinkCardinalityName();
            var isDefault = linkCardinalityDetail.getIsDefault();
            var sortOrder = linkCardinalityDetail.getSortOrder();
            var description = linkCardinalityControl.getBestLinkCardinalityDescription(linkCardinality, getLanguage(userVisit));

            linkCardinalityTransfer = new LinkCardinalityTransfer(linkCardinalityName, isDefault,
                    sortOrder, description);
            put(userVisit, linkCardinality, linkCardinalityTransfer);
        }

        return linkCardinalityTransfer;
    }

}
