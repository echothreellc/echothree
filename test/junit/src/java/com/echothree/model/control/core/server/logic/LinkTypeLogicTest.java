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
import com.echothree.model.control.core.server.control.LinkTypeControl;
import com.echothree.model.data.core.common.pk.*;
import com.echothree.model.data.core.server.entity.*;
import com.echothree.model.data.core.server.value.*;
import com.echothree.util.server.message.DummyExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import java.util.List;
import org.jooq.Condition;
import org.jooq.impl.DSL;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LinkTypeLogicTest {

    private static final EntityPermission READ_ONLY = EntityPermission.READ_ONLY;
    private static final EntityType ITEM = new EntityType(new EntityTypeValue(new EntityTypePK(1L), null, null), READ_ONLY);
    private static final EntityType PARTY = new EntityType(new EntityTypeValue(new EntityTypePK(2L), null, null), READ_ONLY);

    private static LinkCardinality cardinality(String name) {
        var primaryKey = new LinkCardinalityPK((long)name.hashCode());
        var detail = new LinkCardinalityDetail(new LinkCardinalityDetailValue(new LinkCardinalityDetailPK(3L), primaryKey, name, false, 1, 0L, Long.MAX_VALUE), READ_ONLY);
        return new LinkCardinality(new LinkCardinalityValue(primaryKey, null, null), READ_ONLY) {
            @Override
            public LinkCardinalityDetail getLastDetail() {
                return detail;
            }
        };
    }

    private static LinkDirectionality directionality(String name) {
        var primaryKey = new LinkDirectionalityPK((long)name.hashCode());
        var detail = new LinkDirectionalityDetail(new LinkDirectionalityDetailValue(new LinkDirectionalityDetailPK(4L), primaryKey, name, false, 1, 0L, Long.MAX_VALUE), READ_ONLY);
        return new LinkDirectionality(new LinkDirectionalityValue(primaryKey, null, null), READ_ONLY) {
            @Override
            public LinkDirectionalityDetail getLastDetail() {
                return detail;
            }
        };
    }

    private static class MemoryLinkType extends LinkType {
        LinkType parent;
        LinkType inverse;
        final EntityType source;
        final EntityType target;
        final LinkCardinality cardinality;
        final LinkDirectionality directionality;
        final boolean transitive;

        MemoryLinkType(long identifier, EntityType source, EntityType target, String cardinalityName, String directionalityName, boolean transitive) {
            super(new LinkTypeValue(new LinkTypePK(identifier), null, null), READ_ONLY);
            this.source = source;
            this.target = target;
            this.cardinality = cardinality(cardinalityName);
            this.directionality = directionality(directionalityName);
            this.transitive = transitive;
        }

        @Override
        public LinkTypeDetail getLastDetail() {
            var value = new LinkTypeDetailValue(new LinkTypeDetailPK(getPrimaryKey().getEntityId() + 100L), getPrimaryKey(),
                    "TYPE_" + getPrimaryKey().getEntityId(), null, source.getPrimaryKey(), target.getPrimaryKey(),
                    cardinality.getPrimaryKey(), directionality.getPrimaryKey(), transitive, null, 1, 0L, Long.MAX_VALUE);
            return new LinkTypeDetail(value, READ_ONLY) {
                @Override
                public LinkType getParentLinkType() { return parent; }
                @Override
                public LinkType getInverseLinkType() { return inverse; }
                @Override
                public EntityType getSourceEntityType() { return source; }
                @Override
                public EntityType getTargetEntityType() { return target; }
                @Override
                public LinkCardinality getLinkCardinality() { return cardinality; }
                @Override
                public LinkDirectionality getLinkDirectionality() { return directionality; }
            };
        }
    }

    private static class TestLogic extends LinkTypeLogic {
        @SuppressWarnings("UnnecessaryAssignment") // Supply CDI dependencies without a container.
        TestLogic() {
            mimeTypeLogic = new MimeTypeLogic() {};
            linkTypeControl = new LinkTypeControl() {
                @Override
                public Condition getLinkTypeFilter(LinkType parent, EntityType source, EntityType target,
                        LinkCardinality cardinality, LinkDirectionality directionality, LinkType inverse) {
                    return DSL.trueCondition();
                }
                @Override
                public List<LinkType> getLinkTypesForUpdate(Condition condition) {
                    return List.of();
                }
            };
        }
    }

    private static LinkTypeLogic.Definition definition(LinkType parent, EntityType source, EntityType target,
            String cardinality, String directionality, boolean transitive, LinkType inverse) {
        return new LinkTypeLogic.Definition(parent, source, target, cardinality(cardinality), directionality(directionality), transitive, inverse, 1);
    }

    @Test
    public void inverseCardinalitiesReverseTheTwoEnds() {
        assertEquals(LinkCardinalities.MANY_TO_ONE, LinkTypeLogic.getInverseCardinality(LinkCardinalities.ONE_TO_MANY));
        assertEquals(LinkCardinalities.ONE_TO_MANY, LinkTypeLogic.getInverseCardinality(LinkCardinalities.MANY_TO_ONE));
        assertEquals(LinkCardinalities.ONE_TO_ONE, LinkTypeLogic.getInverseCardinality(LinkCardinalities.ONE_TO_ONE));
        assertEquals(LinkCardinalities.MANY_TO_MANY, LinkTypeLogic.getInverseCardinality(LinkCardinalities.MANY_TO_MANY));
        assertNull(LinkTypeLogic.getInverseCardinality(null));
    }

    @Test
    public void childCardinalityCannotBroadenItsParent() {
        assertTrue(LinkTypeLogic.isCardinalityCompatible(LinkCardinalities.MANY_TO_ONE, LinkCardinalities.MANY_TO_MANY));
        assertTrue(LinkTypeLogic.isCardinalityCompatible(LinkCardinalities.ONE_TO_ONE, LinkCardinalities.ONE_TO_MANY));
        assertFalse(LinkTypeLogic.isCardinalityCompatible(LinkCardinalities.MANY_TO_MANY, LinkCardinalities.MANY_TO_ONE));
        assertFalse(LinkTypeLogic.isCardinalityCompatible(LinkCardinalities.ONE_TO_MANY, LinkCardinalities.MANY_TO_ONE));
    }

    @Test
    public void acceptsDirectPartOfAsSpecializationOfTransitivePartOf() {
        var parent = new MemoryLinkType(10L, ITEM, ITEM, "MANY_TO_MANY", "DIRECTED", true);
        var errors = new DummyExecutionErrorAccumulator();
        new TestLogic().validateDefinition(errors, "DIRECT_PART_OF", null,
                definition(parent, ITEM, ITEM, "MANY_TO_ONE", "DIRECTED", false, null));
        assertFalse(errors.hasExecutionErrors());
    }

    @Test
    public void rejectsSelfParentAndLongerParentCycles() {
        var current = new MemoryLinkType(11L, ITEM, ITEM, "MANY_TO_MANY", "DIRECTED", false);
        var parent = new MemoryLinkType(12L, ITEM, ITEM, "MANY_TO_MANY", "DIRECTED", false);
        for(var proposedParent : List.of(current, parent)) {
            parent.parent = current;
            var errors = new DummyExecutionErrorAccumulator();
            new TestLogic().validateDefinition(errors, "CURRENT", current,
                    definition(proposedParent, ITEM, ITEM, "MANY_TO_MANY", "DIRECTED", false, null));
            assertTrue(errors.hasExecutionErrors());
        }
    }

    @Test
    public void rejectsUnknownCardinalityAndDirectionalityNames() {
        for(var invalidName : List.of("UNKNOWN", "directed")) {
            var cardinalityErrors = new DummyExecutionErrorAccumulator();
            new TestLogic().validateDefinition(cardinalityErrors, "INVALID", null,
                    definition(null, ITEM, ITEM, invalidName, "DIRECTED", false, null));
            assertTrue(cardinalityErrors.hasExecutionErrors());

            var directionalityErrors = new DummyExecutionErrorAccumulator();
            new TestLogic().validateDefinition(directionalityErrors, "INVALID", null,
                    definition(null, ITEM, ITEM, "MANY_TO_MANY", invalidName, false, null));
            assertTrue(directionalityErrors.hasExecutionErrors());
        }
    }

    @Test
    public void rejectsIncompatibleSymmetricAndTransitiveEndpoints() {
        for(var direction : List.of("DIRECTED", "SYMMETRIC")) {
            var errors = new DummyExecutionErrorAccumulator();
            new TestLogic().validateDefinition(errors, "INVALID", null,
                    definition(null, ITEM, PARTY, "MANY_TO_MANY", direction, true, null));
            assertTrue(errors.hasExecutionErrors());
        }
        var errors = new DummyExecutionErrorAccumulator();
        new TestLogic().validateDefinition(errors, "INVALID", null,
                definition(null, ITEM, ITEM, "MANY_TO_ONE", "SYMMETRIC", false, null));
        assertTrue(errors.hasExecutionErrors());
    }

    @Test
    public void inverseMustReverseEndpointsAndCardinalityAndCannotBeAlreadyPaired() {
        var inverse = new MemoryLinkType(13L, PARTY, ITEM, "ONE_TO_MANY", "DIRECTED", false);
        var errors = new DummyExecutionErrorAccumulator();
        new TestLogic().validateDefinition(errors, "VALID", null,
                definition(null, ITEM, PARTY, "MANY_TO_ONE", "DIRECTED", false, inverse));
        assertFalse(errors.hasExecutionErrors());
        inverse.inverse = new MemoryLinkType(14L, ITEM, PARTY, "MANY_TO_ONE", "DIRECTED", false);
        errors = new DummyExecutionErrorAccumulator();
        new TestLogic().validateDefinition(errors, "INVALID", null,
                definition(null, ITEM, PARTY, "MANY_TO_ONE", "DIRECTED", false, inverse));
        assertTrue(errors.hasExecutionErrors());
    }
    @Test
    public void guidanceAndMimeTypeMustBeSuppliedTogether() {
        var edit = com.echothree.control.user.core.common.edit.CoreEditFactory.getLinkTypeTranslationEdit();
        var logic = new TestLogic();
        var errors = new DummyExecutionErrorAccumulator();
        assertNull(logic.checkGuidance(errors, edit));
        assertFalse(errors.hasExecutionErrors());

        edit.setGuidance("Semantic guidance");
        errors = new DummyExecutionErrorAccumulator();
        assertNull(logic.checkGuidance(errors, edit));
        assertTrue(errors.hasExecutionErrors());

        edit.setGuidance(null);
        edit.setGuidanceMimeTypeName("text/plain");
        errors = new DummyExecutionErrorAccumulator();
        assertNull(logic.checkGuidance(errors, edit));
        assertTrue(errors.hasExecutionErrors());
    }

}
