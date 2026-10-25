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

package com.echothree.model.control.core.server.logic;

import com.echothree.model.control.core.common.LinkCardinalities;
import com.echothree.model.control.core.common.LinkDirectionalities;
import com.echothree.model.control.core.common.exception.DuplicateLinkException;
import com.echothree.model.control.core.common.exception.InvalidLinkEndpointsException;
import com.echothree.model.control.core.common.exception.InvalidLinkTypeEndpointsException;
import com.echothree.model.control.core.common.exception.LinkCardinalityViolationException;
import com.echothree.model.control.core.common.exception.UnknownLinkException;
import com.echothree.model.control.core.server.control.EntityInstanceControl;
import com.echothree.model.control.core.server.control.LinkControl;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.Link;
import com.echothree.model.data.core.server.entity.LinkType;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class LinkLogic
        extends BaseLogic {

    @Inject
    EntityInstanceControl entityInstanceControl;

    @Inject
    LinkControl linkControl;

    @Inject
    LinkTypeLogic linkTypeLogic;

    protected LinkLogic() {
        super();
    }

    public Link createLink(ExecutionErrorAccumulator eea, String linkTypeName, EntityInstance sourceEntityInstance,
            EntityInstance targetEntityInstance, BasePK createdBy) {
        Link link = null;
        var linkType = linkTypeLogic.getLinkTypeByName(eea, linkTypeName);

        if(!hasExecutionErrors(eea)) {
            link = createLink(eea, linkType, sourceEntityInstance, targetEntityInstance, createdBy);
        }

        return link;
    }

    public Link createLink(ExecutionErrorAccumulator eea, LinkType linkType, EntityInstance sourceEntityInstance,
            EntityInstance targetEntityInstance, BasePK createdBy) {
        Link link = null;
        var linkTypeDetail = linkType.getLastDetail();
        var linkTypeName = linkTypeDetail.getLinkTypeName();

        if(!sourceEntityInstance.getEntityTypePK().equals(linkTypeDetail.getSourceEntityTypePK())
                || !targetEntityInstance.getEntityTypePK().equals(linkTypeDetail.getTargetEntityTypePK())) {
            handleExecutionError(InvalidLinkEndpointsException.class, eea, ExecutionErrors.InvalidLinkEndpoints.name(),
                    linkTypeName, sourceEntityInstance.getPrimaryKey().getEntityRef(), targetEntityInstance.getPrimaryKey().getEntityRef());
        } else {
            var linkCardinality = LinkTypeLogic.getCardinality(linkTypeDetail.getLinkCardinality().getLastDetail().getLinkCardinalityName());
            var linkDirectionality = LinkTypeLogic.getDirectionality(linkTypeDetail.getLinkDirectionality().getLastDetail().getLinkDirectionalityName());
            var isSymmetric = linkDirectionality == LinkDirectionalities.SYMMETRIC;

            if(linkCardinality == null || linkDirectionality == null
                    || (isSymmetric && linkCardinality != LinkCardinalities.ONE_TO_ONE && linkCardinality != LinkCardinalities.MANY_TO_MANY)) {
                handleExecutionError(InvalidLinkTypeEndpointsException.class, eea, ExecutionErrors.InvalidLinkTypeEndpoints.name(), linkTypeName);
            } else {
                lockEndpoints(sourceEntityInstance, targetEntityInstance);
                link = findLink(linkType, sourceEntityInstance, targetEntityInstance, isSymmetric, EntityPermission.READ_WRITE);

                if(link != null) {
                    handleExecutionError(DuplicateLinkException.class, eea, ExecutionErrors.DuplicateLink.name(),
                            linkTypeName, sourceEntityInstance.getPrimaryKey().getEntityRef(), targetEntityInstance.getPrimaryKey().getEntityRef());
                } else {
                    var sourceHasSingleTarget = linkCardinality == LinkCardinalities.ONE_TO_ONE || linkCardinality == LinkCardinalities.MANY_TO_ONE;
                    var targetHasSingleSource = linkCardinality == LinkCardinalities.ONE_TO_ONE || linkCardinality == LinkCardinalities.ONE_TO_MANY;
                    var violatesCardinality = (sourceHasSingleTarget && hasLinks(linkType, sourceEntityInstance, true, isSymmetric))
                            || (targetHasSingleSource && hasLinks(linkType, targetEntityInstance, false, isSymmetric));

                    if(violatesCardinality) {
                        handleExecutionError(LinkCardinalityViolationException.class, eea, ExecutionErrors.LinkCardinalityViolation.name(),
                                linkTypeName, sourceEntityInstance.getPrimaryKey().getEntityRef(), targetEntityInstance.getPrimaryKey().getEntityRef());
                    } else {
                        link = linkControl.createLink(linkType, sourceEntityInstance, targetEntityInstance, createdBy);
                    }
                }
            }
        }

        return link;
    }

    private void lockEndpoints(EntityInstance sourceEntityInstance, EntityInstance targetEntityInstance) {
        var sourceEntityInstancePK = sourceEntityInstance.getPrimaryKey();
        var targetEntityInstancePK = targetEntityInstance.getPrimaryKey();
        var sourceEntityInstanceFirst = sourceEntityInstancePK.getEntityId() <= targetEntityInstancePK.getEntityId();

        entityInstanceControl.getEntityInstanceByPKForUpdate(sourceEntityInstanceFirst ? sourceEntityInstancePK : targetEntityInstancePK);

        if(!sourceEntityInstancePK.equals(targetEntityInstancePK)) {
            entityInstanceControl.getEntityInstanceByPKForUpdate(sourceEntityInstanceFirst ? targetEntityInstancePK : sourceEntityInstancePK);
        }
    }

    private Link findLink(LinkType linkType, EntityInstance sourceEntityInstance, EntityInstance targetEntityInstance,
            boolean isSymmetric, EntityPermission entityPermission) {
        var link = linkControl.getLink(linkType, sourceEntityInstance, targetEntityInstance, entityPermission);

        if(link == null && isSymmetric && !sourceEntityInstance.equals(targetEntityInstance)) {
            link = linkControl.getLink(linkType, targetEntityInstance, sourceEntityInstance, entityPermission);
        }

        return link;
    }

    private boolean hasLinks(LinkType linkType, EntityInstance entityInstance, boolean asSource, boolean isSymmetric) {
        var condition = linkControl.getLinkFilter(linkType, asSource ? entityInstance : null, asSource ? null : entityInstance);
        // Use current, unpaginated reads after locking the endpoints, rather than a transaction snapshot.
        var hasLinks = !linkControl.getLinksForUpdate(condition).isEmpty();

        if(!hasLinks && isSymmetric) {
            condition = linkControl.getLinkFilter(linkType, asSource ? null : entityInstance, asSource ? entityInstance : null);
            hasLinks = !linkControl.getLinksForUpdate(condition).isEmpty();
        }

        return hasLinks;
    }

    public Link getLink(ExecutionErrorAccumulator eea, LinkType linkType, EntityInstance sourceEntityInstance,
            EntityInstance targetEntityInstance, EntityPermission entityPermission) {
        Link link = null;
        var linkDirectionality = LinkTypeLogic.getDirectionality(linkType.getLastDetail().getLinkDirectionality().getLastDetail().getLinkDirectionalityName());

        if(linkDirectionality == null) {
            handleExecutionError(InvalidLinkTypeEndpointsException.class, eea, ExecutionErrors.InvalidLinkTypeEndpoints.name(),
                    linkType.getLastDetail().getLinkTypeName());
        } else {
            link = findLink(linkType, sourceEntityInstance, targetEntityInstance,
                    linkDirectionality == LinkDirectionalities.SYMMETRIC, entityPermission);

            if(link == null) {
                handleExecutionError(UnknownLinkException.class, eea, ExecutionErrors.UnknownLink.name(),
                        linkType.getLastDetail().getLinkTypeName(), sourceEntityInstance.getPrimaryKey().getEntityRef(),
                        targetEntityInstance.getPrimaryKey().getEntityRef());
            }
        }

        return link;
    }

    public Link getLink(ExecutionErrorAccumulator eea, LinkType linkType, EntityInstance sourceEntityInstance,
            EntityInstance targetEntityInstance) {
        return getLink(eea, linkType, sourceEntityInstance, targetEntityInstance, EntityPermission.READ_ONLY);
    }

    public Link getLinkForUpdate(ExecutionErrorAccumulator eea, LinkType linkType, EntityInstance sourceEntityInstance,
            EntityInstance targetEntityInstance) {
        return getLink(eea, linkType, sourceEntityInstance, targetEntityInstance, EntityPermission.READ_WRITE);
    }

    public void deleteLink(ExecutionErrorAccumulator eea, LinkType linkType, EntityInstance sourceEntityInstance,
            EntityInstance targetEntityInstance, BasePK deletedBy) {
        var link = getLinkForUpdate(eea, linkType, sourceEntityInstance, targetEntityInstance);

        if(!hasExecutionErrors(eea)) {
            deleteLink(link, deletedBy);
        }
    }

    public void deleteLink(Link link, BasePK deletedBy) {
        linkControl.deleteLink(link, deletedBy);
    }

}
