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

package com.echothree.model.control.core.server.control;

import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.core.common.transfer.LinkTransfer;
import com.echothree.model.control.core.server.transfer.LinkTransferCache;
import com.echothree.model.data.core.common.pk.LinkPK;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.Link;
import com.echothree.model.data.core.server.entity.LinkType;
import com.echothree.model.data.core.server.factory.LinkFactory;
import com.echothree.model.data.user.server.entity.UserVisit;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.LINK_TYPES_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.tables.core.EntityInstances.EntityInstances;
import static com.echothree.model.jooq.server.tables.core.LinkTypeDetails.LinkTypeDetails;
import static com.echothree.model.jooq.server.tables.core.LinkTypes.LinkTypes;
import static com.echothree.model.jooq.server.tables.core.Links.Links;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.cdi.CommandScope;
import com.echothree.util.server.control.BaseModelControl;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.inject.Inject;
import org.jooq.Condition;
import org.jooq.impl.DSL;

@CommandScope
public class LinkControl
        extends BaseModelControl {

    @Inject
    LinkTransferCache linkTransferCache;

    /**
     * Creates a new instance of LinkControl
     */
    protected LinkControl() {
        super();
    }

    // --------------------------------------------------------------------------------
    //   Links
    // --------------------------------------------------------------------------------

    @Inject
    protected LinkFactory linkFactory;

    public Link createLink(LinkType linkType, EntityInstance sourceEntityInstance,
            EntityInstance targetEntityInstance, BasePK createdBy) {
        var link = linkFactory.create(linkType, sourceEntityInstance, targetEntityInstance,
                session.getStartTime(), Session.MAX_TIME);

        sendEvent(link.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return link;
    }

    public Condition getLinkFilter(LinkType linkType, EntityInstance sourceEntityInstance, EntityInstance targetEntityInstance) {
        Condition condition = DSL.trueCondition();

        if(linkType != null) {
            condition = condition.and(Links.LINK_TYPE.eq(linkType.getPrimaryKey()));
        }

        if(sourceEntityInstance != null) {
            condition = condition.and(Links.SOURCE_ENTITY_INSTANCE.eq(sourceEntityInstance.getPrimaryKey()));
        }

        if(targetEntityInstance != null) {
            condition = condition.and(Links.TARGET_ENTITY_INSTANCE.eq(targetEntityInstance.getPrimaryKey()));
        }

        return condition;
    }

    private Link getLink(Condition condition, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(Links.fields())
                .from(Links)
                .where(condition)
                .and(Links.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return linkFactory.getEntityFromQuery(entityPermission, query);
    }

    public Link getLink(LinkType linkType, EntityInstance sourceEntityInstance,
            EntityInstance targetEntityInstance, EntityPermission entityPermission) {
        return getLink(getLinkFilter(linkType, sourceEntityInstance, targetEntityInstance), entityPermission);
    }

    public Link getLink(LinkType linkType, EntityInstance sourceEntityInstance, EntityInstance targetEntityInstance) {
        return getLink(linkType, sourceEntityInstance, targetEntityInstance, EntityPermission.READ_ONLY);
    }

    public Link getLinkForUpdate(LinkType linkType, EntityInstance sourceEntityInstance, EntityInstance targetEntityInstance) {
        return getLink(linkType, sourceEntityInstance, targetEntityInstance, EntityPermission.READ_WRITE);
    }

    public Link getLinkByPK(LinkPK linkPK, EntityPermission entityPermission) {
        return getLink(Links.LINK.eq(linkPK), entityPermission);
    }

    public Link getLinkByPK(LinkPK linkPK) {
        return getLinkByPK(linkPK, EntityPermission.READ_ONLY);
    }

    public Link getLinkByPKForUpdate(LinkPK linkPK) {
        return getLinkByPK(linkPK, EntityPermission.READ_WRITE);
    }

    public long countLinks(Condition condition) {
        return session.getDslContext()
                .selectCount()
                .from(Links)
                .where(condition)
                .and(Links.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countLinks() {
        return countLinks(DSL.trueCondition());
    }

    private List<Link> getLinks(Condition condition, EntityPermission entityPermission) {
        var sourceEntityInstances = EntityInstances.as("SourceEntityInstances");
        var targetEntityInstances = EntityInstances.as("TargetEntityInstances");
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(Links.fields())
                    .from(Links)
                    .join(LinkTypes).on(Links.LINK_TYPE.eq(LinkTypes.LINK_TYPE))
                    .join(LinkTypeDetails).onKey(LINK_TYPES_LAST_DETAIL_FK)
                    .join(sourceEntityInstances).on(Links.SOURCE_ENTITY_INSTANCE.eq(sourceEntityInstances.ENTITY_INSTANCE))
                    .join(targetEntityInstances).on(Links.TARGET_ENTITY_INSTANCE.eq(targetEntityInstances.ENTITY_INSTANCE))
                    .where(condition)
                    .and(Links.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(LinkTypeDetails.SORT_ORDER, LinkTypeDetails.LINK_TYPE_NAME,
                            sourceEntityInstances.ENTITY_UNIQUE, targetEntityInstances.ENTITY_UNIQUE),
                    LinkFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(Links.fields())
                    .from(Links)
                    .where(condition)
                    .and(Links.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return linkFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<Link> getLinks(Condition condition) {
        return getLinks(condition, EntityPermission.READ_ONLY);
    }

    public List<Link> getLinks() {
        return getLinks(DSL.trueCondition());
    }

    public List<Link> getLinksForUpdate(Condition condition) {
        return getLinks(condition, EntityPermission.READ_WRITE);
    }

    public LinkTransfer getLinkTransfer(UserVisit userVisit, Link link) {
        return linkTransferCache.getTransfer(userVisit, link);
    }

    public List<LinkTransfer> getLinkTransfers(UserVisit userVisit, Collection<Link> links) {
        var linkTransfers = new ArrayList<LinkTransfer>(links.size());

        links.forEach(link -> linkTransfers.add(getLinkTransfer(userVisit, link)));

        return linkTransfers;
    }

    public List<LinkTransfer> getLinkTransfers(UserVisit userVisit, LinkType linkType,
            EntityInstance sourceEntityInstance, EntityInstance targetEntityInstance) {
        return getLinkTransfers(userVisit, getLinks(getLinkFilter(linkType, sourceEntityInstance, targetEntityInstance)));
    }

    public void deleteLink(Link link, BasePK deletedBy) {
        link.setThruTime(session.getStartTime());

        sendEvent(link.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    public void deleteLinks(Collection<Link> links, BasePK deletedBy) {
        links.forEach(link -> deleteLink(link, deletedBy));
    }

    public List<Link> getLinksByLinkType(LinkType linkType) {
        return getLinks(getLinkFilter(linkType, null, null));
    }

    public List<Link> getLinksByLinkTypeForUpdate(LinkType linkType) {
        return getLinksForUpdate(getLinkFilter(linkType, null, null));
    }

    public long countLinksByLinkType(LinkType linkType) {
        return countLinks(getLinkFilter(linkType, null, null));
    }

    public void deleteLinksByLinkType(LinkType linkType, BasePK deletedBy) {
        deleteLinks(getLinksByLinkTypeForUpdate(linkType), deletedBy);
    }

    public List<Link> getLinksBySourceEntityInstance(EntityInstance sourceEntityInstance) {
        return getLinks(getLinkFilter(null, sourceEntityInstance, null));
    }

    public List<Link> getLinksBySourceEntityInstanceForUpdate(EntityInstance sourceEntityInstance) {
        return getLinksForUpdate(getLinkFilter(null, sourceEntityInstance, null));
    }

    public long countLinksBySourceEntityInstance(EntityInstance sourceEntityInstance) {
        return countLinks(getLinkFilter(null, sourceEntityInstance, null));
    }

    public void deleteLinksBySourceEntityInstance(EntityInstance sourceEntityInstance, BasePK deletedBy) {
        deleteLinks(getLinksBySourceEntityInstanceForUpdate(sourceEntityInstance), deletedBy);
    }

    public List<Link> getLinksByTargetEntityInstance(EntityInstance targetEntityInstance) {
        return getLinks(getLinkFilter(null, null, targetEntityInstance));
    }

    public List<Link> getLinksByTargetEntityInstanceForUpdate(EntityInstance targetEntityInstance) {
        return getLinksForUpdate(getLinkFilter(null, null, targetEntityInstance));
    }

    public long countLinksByTargetEntityInstance(EntityInstance targetEntityInstance) {
        return countLinks(getLinkFilter(null, null, targetEntityInstance));
    }

    public void deleteLinksByTargetEntityInstance(EntityInstance targetEntityInstance, BasePK deletedBy) {
        deleteLinks(getLinksByTargetEntityInstanceForUpdate(targetEntityInstance), deletedBy);
    }

}
