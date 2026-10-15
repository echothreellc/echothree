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

package com.echothree.control.user.period.server.command;

import com.echothree.control.user.period.common.form.PeriodFormFactory;
import com.echothree.control.user.period.common.result.CreatePeriodTypeResult;
import com.echothree.model.control.period.server.control.PeriodControl;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.period.common.pk.PeriodKindDetailPK;
import com.echothree.model.data.period.common.pk.PeriodKindPK;
import com.echothree.model.data.period.common.pk.PeriodTypeDetailPK;
import com.echothree.model.data.period.common.pk.PeriodTypePK;
import com.echothree.model.data.period.server.entity.PeriodKind;
import com.echothree.model.data.period.server.entity.PeriodKindDetail;
import com.echothree.model.data.period.server.entity.PeriodType;
import com.echothree.model.data.period.server.entity.PeriodTypeDetail;
import com.echothree.model.data.period.server.value.PeriodKindDetailValue;
import com.echothree.model.data.period.server.value.PeriodKindValue;
import com.echothree.model.data.period.server.value.PeriodTypeDetailValue;
import com.echothree.model.data.period.server.value.PeriodTypeValue;
import com.echothree.model.data.workflow.server.entity.WorkflowEntrance;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.message.Messages;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.persistence.EntityPermission;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CreatePeriodTypeCommandTest {

    private static final String KIND_NAME = "TEST_KIND";
    private static final String TYPE_NAME = "TEST_TYPE";
    private static final PeriodKindPK KIND_PK = new PeriodKindPK(123L);
    private static final PeriodTypePK TYPE_PK = new PeriodTypePK(456L);

    private static PeriodKind kind() {
        var detailPK = new PeriodKindDetailPK(1L);
        var detail = new PeriodKindDetail(new PeriodKindDetailValue(detailPK, KIND_PK, KIND_NAME, null, false, 0, 0L, Long.MAX_VALUE), EntityPermission.READ_ONLY);

        return new PeriodKind(new PeriodKindValue(KIND_PK, detailPK, detailPK), EntityPermission.READ_ONLY) {
            @Override
            public PeriodKindDetail getLastDetail() {
                return detail;
            }
        };
    }

    private static PeriodType type(PeriodKind kind) {
        var detailPK = new PeriodTypeDetailPK(2L);
        var detail = new PeriodTypeDetail(new PeriodTypeDetailValue(detailPK, TYPE_PK, KIND_PK, TYPE_NAME, null, null, false, 0, 0L, Long.MAX_VALUE), EntityPermission.READ_ONLY) {
            @Override
            public PeriodKind getPeriodKind() {
                return kind;
            }
        };

        return new PeriodType(new PeriodTypeValue(TYPE_PK, detailPK, detailPK), EntityPermission.READ_ONLY) {
            @Override
            public PeriodTypeDetail getLastDetail() {
                return detail;
            }
        };
    }

    private static class MemoryControl extends PeriodControl {
        final PeriodKind kind = kind();
        PeriodType existing;
        int creates;

        MemoryControl(boolean duplicate) {
            existing = duplicate ? type(kind) : null;
        }

        @Override
        public PeriodKind getPeriodKindByName(String name) {
            assertEquals(KIND_NAME, name);
            return kind;
        }

        @Override
        public PeriodType getPeriodTypeByName(PeriodKind kind, String name) {
            assertSame(this.kind, kind);
            return TYPE_NAME.equals(name) ? existing : null;
        }

        @Override
        public PeriodType createPeriodType(PeriodKind kind, String name, PeriodType parent, WorkflowEntrance entrance,
                Boolean isDefault, Integer sortOrder, BasePK createdBy) {
            creates++;
            assertSame(this.kind, kind);
            assertEquals(TYPE_NAME, name);
            assertNull(parent);
            assertNull(entrance);
            existing = type(kind);
            return existing;
        }
    }

    private static class TestCommand extends CreatePeriodTypeCommand {
        @SuppressWarnings("UnnecessaryAssignment") // Supply the CDI dependency without a container.
        TestCommand(MemoryControl control) {
            periodControl = control;
            form = PeriodFormFactory.getCreatePeriodTypeForm();
            form.setPeriodKindName(KIND_NAME);
            form.setPeriodTypeName(TYPE_NAME);
            form.setIsDefault("false");
            form.setSortOrder("0");
        }

        void setWorkflowName(String name) {
            form.setWorkflowName(name);
        }

        @Override
        public PartyPK getPartyPK() {
            return new PartyPK(1L);
        }
    }

    @Test
    public void newlyCreatedEntityReturnsReferenceAndCompleteKey() {
        var control = new MemoryControl(false);
        var command = new TestCommand(control);
        var result = (CreatePeriodTypeResult)command.execute();

        assertEquals(TYPE_PK.getEntityRef(), result.getEntityRef());
        assertEquals(KIND_NAME, result.getPeriodKindName());
        assertEquals(TYPE_NAME, result.getPeriodTypeName());
        assertEquals(1, control.creates);
        assertFalse(command.hasExecutionErrors());
    }

    @Test
    public void duplicateReturnsExistingReferenceAndCompleteKeyWithError() {
        var control = new MemoryControl(true);
        var command = new TestCommand(control);
        var result = (CreatePeriodTypeResult)command.execute();

        assertEquals(TYPE_PK.getEntityRef(), result.getEntityRef());
        assertEquals(KIND_NAME, result.getPeriodKindName());
        assertEquals(TYPE_NAME, result.getPeriodTypeName());
        assertEquals(0, control.creates);
        var error = command.getExecutionErrors().get(Messages.EXECUTION_ERROR).next();
        assertEquals(ExecutionErrors.DuplicatePeriodTypeName.name(), error.getKey());
        assertArrayEquals(new Object[] { KIND_NAME, TYPE_NAME }, error.getValues());
    }

    @Test
    public void invalidWorkflowPairReturnsEmptyResultWithoutCreatingEntity() {
        var control = new MemoryControl(false);
        var command = new TestCommand(control);
        command.setWorkflowName("INCOMPLETE_WORKFLOW");
        var result = (CreatePeriodTypeResult)command.execute();

        assertNull(result.getEntityRef());
        assertNull(result.getPeriodKindName());
        assertNull(result.getPeriodTypeName());
        assertEquals(0, control.creates);
        var error = command.getExecutionErrors().get(Messages.EXECUTION_ERROR).next();
        assertEquals(ExecutionErrors.InvalidParameterCount.name(), error.getKey());
    }

}
