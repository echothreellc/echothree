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

import com.echothree.model.control.core.common.transfer.LinkTransfer;
import com.echothree.model.data.core.server.entity.Link;
import com.echothree.model.data.user.server.entity.UserVisit;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@RequestScoped
public class LinkTransferCache
        extends BaseCoreTransferCache<Link, LinkTransfer> {

    /** Creates a new instance of LinkTransferCache */
    protected LinkTransferCache() {
        super();
    }

    public LinkTransfer getTransfer(UserVisit userVisit, Link link) {
        var linkTransfer = get(link);

        if(linkTransfer == null) {
            linkTransfer = new LinkTransfer(null, null, null);

            // Cache before following endpoints or the LinkType, which can include this Link again.
            put(userVisit, link, linkTransfer);

            linkTransfer.setLinkType(linkTypeControl.getLinkTypeTransfer(userVisit, link.getLinkType()));
            linkTransfer.setSourceEntityInstance(entityInstanceControl.getEntityInstanceTransfer(userVisit, link.getSourceEntityInstance(), false, false, false, false));
            linkTransfer.setTargetEntityInstance(entityInstanceControl.getEntityInstanceTransfer(userVisit, link.getTargetEntityInstance(), false, false, false, false));
        }

        return linkTransfer;
    }

}
