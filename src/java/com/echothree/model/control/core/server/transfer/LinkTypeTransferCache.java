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

import com.echothree.model.control.core.server.control.EntityTypeControl;
import com.echothree.model.control.core.server.control.LinkCardinalityControl;
import com.echothree.model.control.core.server.control.LinkDirectionalityControl;
import com.echothree.model.control.core.server.control.MimeTypeControl;
import javax.inject.Inject;
import com.echothree.model.control.core.common.transfer.LinkTypeTransfer;
import com.echothree.model.control.core.server.control.LinkTypeControl;
import com.echothree.model.data.core.server.entity.LinkType;
import com.echothree.model.data.user.server.entity.UserVisit;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class LinkTypeTransferCache
        extends BaseCoreTransferCache<LinkType, LinkTypeTransfer> {

    @Inject
    EntityTypeControl entityTypeControl;

    @Inject
    LinkCardinalityControl linkCardinalityControl;

    @Inject
    LinkDirectionalityControl linkDirectionalityControl;

    @Inject
    LinkTypeControl linkTypeControl;

    @Inject
    MimeTypeControl mimeTypeControl;

    /** Creates a new instance of LinkTypeTransferCache */
    protected LinkTypeTransferCache() {
        super();

        setIncludeEntityInstance(true);
    }

    public LinkTypeTransfer getTransfer(UserVisit userVisit, LinkType linkType) {
        var linkTypeTransfer = get(linkType);

        if(linkTypeTransfer == null) {
            var linkTypeDetail = linkType.getLastDetail();
            var linkTypeName = linkTypeDetail.getLinkTypeName();
            var linkTypeTranslation = linkTypeControl.getBestLinkTypeTranslation(linkType, getLanguage(userVisit));
            var parentLinkType = linkTypeDetail.getParentLinkType();
            var inverseLinkType = linkTypeDetail.getInverseLinkType();
            var guidanceMimeType = linkTypeTranslation == null ? null : linkTypeTranslation.getGuidanceMimeType();

            linkTypeTransfer = new LinkTypeTransfer(linkTypeName,
                    null,
                    entityTypeControl.getEntityTypeTransfer(userVisit, linkTypeDetail.getSourceEntityType()),
                    entityTypeControl.getEntityTypeTransfer(userVisit, linkTypeDetail.getTargetEntityType()),
                    linkCardinalityControl.getLinkCardinalityTransfer(userVisit, linkTypeDetail.getLinkCardinality()),
                    linkDirectionalityControl.getLinkDirectionalityTransfer(userVisit, linkTypeDetail.getLinkDirectionality()),
                    linkTypeDetail.getIsTransitive(), null,
                    linkTypeDetail.getSortOrder(), linkTypeTranslation == null ? linkTypeName : linkTypeTranslation.getDescription(),
                    guidanceMimeType == null ? null : mimeTypeControl.getMimeTypeTransfer(userVisit, guidanceMimeType),
                    linkTypeTranslation == null ? null : linkTypeTranslation.getGuidance());
            // Cache before resolving LinkType relationships, which may refer back to this transfer.
            put(userVisit, linkType, linkTypeTransfer);

            linkTypeTransfer.setParentLinkType(parentLinkType == null ? null : linkTypeControl.getLinkTypeTransfer(userVisit, parentLinkType));
            linkTypeTransfer.setInverseLinkType(inverseLinkType == null ? null : linkTypeControl.getLinkTypeTransfer(userVisit, inverseLinkType));
        }

        return linkTypeTransfer;
    }

}
