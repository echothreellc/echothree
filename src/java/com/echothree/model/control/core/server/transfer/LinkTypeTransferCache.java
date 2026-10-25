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

import com.echothree.model.control.core.common.CoreOptions;
import com.echothree.model.control.core.common.transfer.LinkTypeTransfer;
import com.echothree.model.control.core.server.control.EntityTypeControl;
import com.echothree.model.control.core.server.control.LinkCardinalityControl;
import com.echothree.model.control.core.server.control.LinkDirectionalityControl;
import com.echothree.model.control.core.server.control.LinkControl;
import com.echothree.model.control.core.server.control.MimeTypeControl;
import com.echothree.model.data.core.common.pk.EntityInstancePK;
import com.echothree.model.data.core.common.pk.LinkTypePK;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.LinkType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.transfer.ListWrapper;
import java.util.HashMap;
import java.util.Map;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@RequestScoped
public class LinkTypeTransferCache
        extends BaseCoreTransferCache<LinkType, LinkTypeTransfer> {

    @Inject
    EntityTypeControl entityTypeControl;

    @Inject
    LinkCardinalityControl linkCardinalityControl;

    @Inject
    LinkControl linkControl;

    @Inject
    LinkDirectionalityControl linkDirectionalityControl;

    @Inject
    MimeTypeControl mimeTypeControl;

    private record Context(LinkTypePK linkTypePK, EntityInstancePK sourceEntityInstancePK, EntityInstancePK targetEntityInstancePK) {
    }

    private final Map<Context, LinkTypeTransfer> contextualTransfers = new HashMap<>();
    boolean includeLinks;

    /** Creates a new instance of LinkTypeTransferCache */
    protected LinkTypeTransferCache() {
        super();

        var options = session.getOptions();
        if(options != null) {
            includeLinks = options.contains(CoreOptions.LinkTypeIncludeLinks);
        }

        setIncludeEntityInstance(true);
    }

    public LinkTypeTransfer getTransfer(UserVisit userVisit, LinkType linkType) {
        return getTransfer(userVisit, linkType, null, null);
    }

    public LinkTypeTransfer getTransfer(UserVisit userVisit, LinkType linkType,
            EntityInstance sourceEntityInstance, EntityInstance targetEntityInstance) {
        var contextual = sourceEntityInstance != null || targetEntityInstance != null;
        var context = new Context(linkType.getPrimaryKey(), sourceEntityInstance == null ? null : sourceEntityInstance.getPrimaryKey(),
                targetEntityInstance == null ? null : targetEntityInstance.getPrimaryKey());
        var linkTypeTransfer = contextual ? contextualTransfers.get(context) : get(linkType);

        if(linkTypeTransfer == null) {
            var linkTypeDetail = linkType.getLastDetail();
            var linkTypeName = linkTypeDetail.getLinkTypeName();
            var linkTypeTranslation = linkTypeControl.getBestLinkTypeTranslation(linkType, getLanguage(userVisit));
            var parentLinkType = linkTypeDetail.getParentLinkType();
            var inverseLinkType = linkTypeDetail.getInverseLinkType();
            var guidanceMimeType = linkTypeTranslation == null ? null : linkTypeTranslation.getGuidanceMimeType();

            linkTypeTransfer = new LinkTypeTransfer(linkTypeName,
                    null, null, null, null, null,
                    linkTypeDetail.getIsTransitive(), null,
                    linkTypeDetail.getSortOrder(), linkTypeTranslation == null ? linkTypeName : linkTypeTranslation.getDescription(),
                    null,
                    linkTypeTranslation == null ? null : linkTypeTranslation.getGuidance());

            // Related transfers can include their own Links back to this LinkType.
            // Cache before resolving any of them, including endpoint types and Guidance's MimeType.
            if(contextual) {
                contextualTransfers.put(context, linkTypeTransfer);

                if(getIncludeEntityInstance()) {
                    setupEntityInstance(userVisit, linkType, null, linkTypeTransfer);
                }
            } else {
                put(userVisit, linkType, linkTypeTransfer);
            }

            linkTypeTransfer.setSourceEntityType(entityTypeControl.getEntityTypeTransfer(userVisit, linkTypeDetail.getSourceEntityType()));
            linkTypeTransfer.setTargetEntityType(entityTypeControl.getEntityTypeTransfer(userVisit, linkTypeDetail.getTargetEntityType()));
            linkTypeTransfer.setLinkCardinality(linkCardinalityControl.getLinkCardinalityTransfer(userVisit, linkTypeDetail.getLinkCardinality()));
            linkTypeTransfer.setLinkDirectionality(linkDirectionalityControl.getLinkDirectionalityTransfer(userVisit, linkTypeDetail.getLinkDirectionality()));
            linkTypeTransfer.setGuidanceMimeType(guidanceMimeType == null ? null : mimeTypeControl.getMimeTypeTransfer(userVisit, guidanceMimeType));
            linkTypeTransfer.setParentLinkType(parentLinkType == null ? null : linkTypeControl.getLinkTypeTransfer(userVisit, parentLinkType));
            linkTypeTransfer.setInverseLinkType(inverseLinkType == null ? null : linkTypeControl.getLinkTypeTransfer(userVisit, inverseLinkType));

            if(includeLinks) {
                linkTypeTransfer.setLinks(new ListWrapper<>(linkControl.getLinkTransfers(userVisit, linkType, sourceEntityInstance, targetEntityInstance)));
            }
        }

        return linkTypeTransfer;
    }

}
