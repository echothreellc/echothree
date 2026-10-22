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

package com.echothree.model.control.period.server.logic;

import com.echothree.model.control.period.server.PeriodTestEntities;
import com.echothree.model.data.period.server.entity.Period;
import com.echothree.model.data.period.server.entity.PeriodKind;
import com.echothree.model.data.period.server.entity.PeriodType;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.message.Messages;
import com.echothree.util.server.message.DummyExecutionErrorAccumulator;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PeriodSelectionTest {

    private static class TestPeriodLogic extends PeriodLogic {
        final PeriodKind periodKind = PeriodTestEntities.kind(1L, "FISCAL");
        final PeriodType periodType = PeriodTestEntities.type(2L, periodKind, "YEAR");
        final Period parentPeriod = PeriodTestEntities.period(3L, periodKind, "PARENT", null, null);

        @Override
        public Period getPeriodByName(ExecutionErrorAccumulator accumulator, PeriodKind selectedPeriodKind, String periodName) {
            assertSame(periodKind, selectedPeriodKind);
            if("PARENT".equals(periodName)) {
                return parentPeriod;
            }
            accumulator.addExecutionError(ExecutionErrors.UnknownPeriodName.name(), "FISCAL", periodName);
            return null;
        }

        @SuppressWarnings("UnnecessaryAssignment") // Supply CDI dependencies without a container.
        TestPeriodLogic() {
            periodTypeLogic = new PeriodTypeLogic() {
                @Override
                public PeriodType getPeriodTypeByName(ExecutionErrorAccumulator accumulator, PeriodKind selectedPeriodKind, String periodTypeName) {
                    assertSame(periodKind, selectedPeriodKind);
                    if("YEAR".equals(periodTypeName)) {
                        return periodType;
                    }
                    accumulator.addExecutionError(ExecutionErrors.UnknownPeriodTypeName.name(), "FISCAL", periodTypeName);
                    return null;
                }
            };
        }
    }

    @Test
    public void everyParentAndTypeNameCombinationPreservesAllFilters() {
        var periodLogic = new TestPeriodLogic();

        for(int filterMask = 0; filterMask < 4; filterMask++) {
            var errors = new DummyExecutionErrorAccumulator();
            var periodSelection = periodLogic.getPeriodSelection(errors, periodLogic.periodKind,
                    (filterMask & 1) == 0 ? null : "PARENT", (filterMask & 2) == 0 ? null : "YEAR");

            assertFalse(errors.hasExecutionErrors());
            assertSame(periodLogic.periodKind, periodSelection.periodKind());
            assertSame((filterMask & 1) == 0 ? null : periodLogic.parentPeriod, periodSelection.parentPeriod());
            assertSame((filterMask & 2) == 0 ? null : periodLogic.periodType, periodSelection.periodType());
        }
    }

    @Test
    public void unknownTypeDoesNotDropTheFilter() {
        var periodLogic = new TestPeriodLogic();
        var errors = new DummyExecutionErrorAccumulator();

        assertNull(periodLogic.getPeriodSelection(errors, periodLogic.periodKind, null, "MISSING"));
        var error = errors.getExecutionErrors().get(Messages.EXECUTION_ERROR).next();

        assertEquals(ExecutionErrors.UnknownPeriodTypeName.name(), error.getKey());
        assertArrayEquals(new Object[] { "FISCAL", "MISSING" }, error.getValues());
    }

    @Test
    public void unknownParentReportsItsCompleteKeyAndDoesNotDropTheFilter() {
        var periodLogic = new TestPeriodLogic();
        var errors = new DummyExecutionErrorAccumulator();

        assertNull(periodLogic.getPeriodSelection(errors, periodLogic.periodKind, "MISSING", null));
        var error = errors.getExecutionErrors().get(Messages.EXECUTION_ERROR).next();

        assertEquals(ExecutionErrors.UnknownPeriodName.name(), error.getKey());
        assertArrayEquals(new Object[] { "FISCAL", "MISSING" }, error.getValues());
    }

}
