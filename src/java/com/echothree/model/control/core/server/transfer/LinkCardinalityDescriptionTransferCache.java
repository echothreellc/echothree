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
import com.echothree.model.control.core.common.transfer.LinkCardinalityDescriptionTransfer;
import com.echothree.model.control.core.server.control.LinkCardinalityControl;
import com.echothree.model.data.core.server.entity.LinkCardinalityDescription;
import com.echothree.model.data.user.server.entity.UserVisit;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class LinkCardinalityDescriptionTransferCache
        extends BaseCoreDescriptionTransferCache<LinkCardinalityDescription, LinkCardinalityDescriptionTransfer> {

    @Inject
    LinkCardinalityControl linkCardinalityControl;

    /** Creates a new instance of LinkCardinalityDescriptionTransferCache */
    protected LinkCardinalityDescriptionTransferCache() {
        super();
    }

    public LinkCardinalityDescriptionTransfer getTransfer(UserVisit userVisit, LinkCardinalityDescription linkCardinalityDescription) {
        var linkCardinalityDescriptionTransfer = get(linkCardinalityDescription);

        if(linkCardinalityDescriptionTransfer == null) {
            var linkCardinalityTransfer = linkCardinalityControl.getLinkCardinalityTransfer(userVisit, linkCardinalityDescription.getLinkCardinality());
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, linkCardinalityDescription.getLanguage());

            linkCardinalityDescriptionTransfer = new LinkCardinalityDescriptionTransfer(languageTransfer, linkCardinalityTransfer, linkCardinalityDescription.getDescription());
            put(userVisit, linkCardinalityDescription, linkCardinalityDescriptionTransfer);
        }

        return linkCardinalityDescriptionTransfer;
    }

}
