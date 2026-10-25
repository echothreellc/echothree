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

import com.echothree.model.control.core.server.control.LinkControl;
import com.echothree.model.data.core.common.pk.EntityInstancePK;
import com.echothree.model.data.core.common.pk.EntityTypePK;
import com.echothree.model.data.core.common.pk.LinkCardinalityPK;
import com.echothree.model.data.core.common.pk.LinkDirectionalityPK;
import com.echothree.model.data.core.common.pk.LinkPK;
import com.echothree.model.data.core.common.pk.LinkTypeDetailPK;
import com.echothree.model.data.core.common.pk.LinkTypePK;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.Link;
import com.echothree.model.data.core.server.entity.LinkType;
import com.echothree.model.data.core.server.entity.LinkTypeDetail;
import com.echothree.model.data.core.server.value.EntityInstanceValue;
import com.echothree.model.data.core.server.value.LinkTypeDetailValue;
import com.echothree.model.data.core.server.value.LinkTypeValue;
import com.echothree.model.data.core.server.value.LinkValue;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.message.Messages;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.message.DummyExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LinkLogicTest {

    private static final EntityTypePK SOURCE_ENTITY_TYPE_PK = new EntityTypePK(1L);
    private static final EntityTypePK TARGET_ENTITY_TYPE_PK = new EntityTypePK(2L);

    private static EntityInstance entityInstance(long identifier, EntityTypePK entityTypePK) {
        return new EntityInstance(new EntityInstanceValue(new EntityInstancePK(identifier), entityTypePK, identifier, null),
                EntityPermission.READ_ONLY);
    }

    private static LinkType linkType() {
        var linkTypePK = new LinkTypePK(3L);
        var linkTypeDetail = new LinkTypeDetail(new LinkTypeDetailValue(new LinkTypeDetailPK(4L), linkTypePK,
                "TEST", null, SOURCE_ENTITY_TYPE_PK, TARGET_ENTITY_TYPE_PK, new LinkCardinalityPK(6L),
                new LinkDirectionalityPK(7L), false, null, 0, 0L, Long.MAX_VALUE),
                EntityPermission.READ_ONLY);

        return new LinkType(new LinkTypeValue(linkTypePK, null, null), EntityPermission.READ_ONLY) {
            @Override
            public LinkTypeDetail getLastDetail() {
                return linkTypeDetail;
            }
        };
    }

    private static class MemoryControl extends LinkControl {
        Link link;
        EntityPermission entityPermission;
        int creates;
        int deletes;

        @Override
        public Link getLink(LinkType linkType, EntityInstance sourceEntityInstance,
                EntityInstance targetEntityInstance, EntityPermission entityPermission) {
            this.entityPermission = entityPermission;
            return link;
        }

        @Override
        public Link createLink(LinkType linkType, EntityInstance sourceEntityInstance,
                EntityInstance targetEntityInstance, BasePK createdBy) {
            creates++;
            link = new Link(new LinkValue(new LinkPK(5L), linkType.getPrimaryKey(), sourceEntityInstance.getPrimaryKey(),
                    targetEntityInstance.getPrimaryKey(), 0L, Long.MAX_VALUE), EntityPermission.READ_WRITE);
            return link;
        }

        @Override
        public void deleteLink(Link link, BasePK deletedBy) {
            deletes++;
            this.link = null;
        }
    }

    @SuppressWarnings("UnnecessaryAssignment") // Supply CDI dependencies without a container.
    private static LinkLogic logic(MemoryControl linkControl) {
        var linkLogic = new LinkLogic();
        linkLogic.linkControl = linkControl;
        return linkLogic;
    }

    @Test
    public void createsLinkAndRejectsDuplicateWithoutCreatingAgain() {
        var linkControl = new MemoryControl();
        var linkLogic = logic(linkControl);
        var sourceEntityInstance = entityInstance(10L, SOURCE_ENTITY_TYPE_PK);
        var targetEntityInstance = entityInstance(11L, TARGET_ENTITY_TYPE_PK);
        var errors = new DummyExecutionErrorAccumulator();
        var link = linkLogic.createLink(errors, linkType(), sourceEntityInstance, targetEntityInstance, null);

        assertNotNull(link);
        assertFalse(errors.hasExecutionErrors());
        assertSame(link, linkLogic.createLink(errors, linkType(), sourceEntityInstance, targetEntityInstance, null));
        assertTrue(errors.hasExecutionErrors());
        assertEquals(1, linkControl.creates);
        assertTrue(errors.getExecutionErrors().containsKey(Messages.EXECUTION_ERROR,
                ExecutionErrors.DuplicateLink.name()));
    }

    @Test
    public void rejectsWrongSourceAndTargetTypesBeforePersistence() {
        var linkControl = new MemoryControl();
        var linkLogic = logic(linkControl);
        var sourceEntityInstance = entityInstance(10L, SOURCE_ENTITY_TYPE_PK);
        var targetEntityInstance = entityInstance(11L, TARGET_ENTITY_TYPE_PK);
        var errors = new DummyExecutionErrorAccumulator();

        assertNull(linkLogic.createLink(errors, linkType(), targetEntityInstance, targetEntityInstance, null));
        assertTrue(errors.hasExecutionErrors());
        errors = new DummyExecutionErrorAccumulator();
        assertNull(linkLogic.createLink(errors, linkType(), sourceEntityInstance, sourceEntityInstance, null));
        assertTrue(errors.hasExecutionErrors());
        assertEquals(0, linkControl.creates);
        assertTrue(errors.getExecutionErrors().containsKey(Messages.EXECUTION_ERROR,
                ExecutionErrors.InvalidLinkEndpoints.name()));
    }

    @Test
    public void reportsUnknownLinkAndDoesNotDelete() {
        var linkControl = new MemoryControl();
        var linkLogic = logic(linkControl);
        var sourceEntityInstance = entityInstance(10L, SOURCE_ENTITY_TYPE_PK);
        var targetEntityInstance = entityInstance(11L, TARGET_ENTITY_TYPE_PK);
        var errors = new DummyExecutionErrorAccumulator();

        assertNull(linkLogic.getLink(errors, linkType(), sourceEntityInstance, targetEntityInstance));
        assertTrue(errors.hasExecutionErrors());
        assertEquals(EntityPermission.READ_ONLY, linkControl.entityPermission);
        linkLogic.deleteLink(new DummyExecutionErrorAccumulator(), linkType(), sourceEntityInstance, targetEntityInstance, null);
        assertEquals(EntityPermission.READ_WRITE, linkControl.entityPermission);
        assertEquals(0, linkControl.deletes);
        assertTrue(errors.getExecutionErrors().containsKey(Messages.EXECUTION_ERROR,
                ExecutionErrors.UnknownLink.name()));
    }

    @Test
    public void deletesExistingLinkUsingWritePermission() {
        var linkControl = new MemoryControl();
        var linkLogic = logic(linkControl);
        var sourceEntityInstance = entityInstance(10L, SOURCE_ENTITY_TYPE_PK);
        var targetEntityInstance = entityInstance(11L, TARGET_ENTITY_TYPE_PK);
        linkLogic.createLink(null, linkType(), sourceEntityInstance, targetEntityInstance, null);
        var errors = new DummyExecutionErrorAccumulator();

        linkLogic.deleteLink(errors, linkType(), sourceEntityInstance, targetEntityInstance, null);

        assertFalse(errors.hasExecutionErrors());
        assertEquals(EntityPermission.READ_WRITE, linkControl.entityPermission);
        assertEquals(1, linkControl.deletes);
        assertNull(linkControl.link);
    }

}
