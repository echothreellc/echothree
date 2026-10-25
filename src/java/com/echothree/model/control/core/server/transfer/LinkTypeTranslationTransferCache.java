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

import com.echothree.model.control.core.common.transfer.LinkTypeTranslationTransfer;
import com.echothree.model.control.core.server.control.MimeTypeControl;
import com.echothree.model.data.core.server.entity.LinkTypeTranslation;
import com.echothree.model.data.user.server.entity.UserVisit;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@RequestScoped
public class LinkTypeTranslationTransferCache
        extends BaseCoreDescriptionTransferCache<LinkTypeTranslation, LinkTypeTranslationTransfer> {

    @Inject
    MimeTypeControl mimeTypeControl;

    /** Creates a new instance of LinkTypeTranslationTransferCache */
    protected LinkTypeTranslationTransferCache() {
        super();
    }

    public LinkTypeTranslationTransfer getTransfer(UserVisit userVisit, LinkTypeTranslation linkTypeTranslation) {
        var linkTypeTranslationTransfer = get(linkTypeTranslation);

        if(linkTypeTranslationTransfer == null) {
            var linkTypeTransfer = linkTypeControl.getLinkTypeTransfer(userVisit, linkTypeTranslation.getLinkType());
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, linkTypeTranslation.getLanguage());
            var guidanceMimeType = linkTypeTranslation.getGuidanceMimeType();

            linkTypeTranslationTransfer = new LinkTypeTranslationTransfer(linkTypeTransfer, languageTransfer, linkTypeTranslation.getDescription(),
                    guidanceMimeType == null ? null : mimeTypeControl.getMimeTypeTransfer(userVisit, guidanceMimeType), linkTypeTranslation.getGuidance());
            put(userVisit, linkTypeTranslation, linkTypeTranslationTransfer);
        }

        return linkTypeTranslationTransfer;
    }

}
