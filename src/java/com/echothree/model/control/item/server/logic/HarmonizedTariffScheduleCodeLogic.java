// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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

package com.echothree.model.control.item.server.logic;

import com.echothree.model.control.item.common.exception.UnknownHarmonizedTariffScheduleCodeUnitNameException;
import com.echothree.model.control.item.common.exception.UnknownHarmonizedTariffScheduleCodeUseTypeNameException;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.data.item.server.entity.HarmonizedTariffScheduleCodeUnit;
import com.echothree.model.data.item.server.entity.HarmonizedTariffScheduleCodeUseType;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class HarmonizedTariffScheduleCodeLogic
        extends BaseLogic {

    protected HarmonizedTariffScheduleCodeLogic() {
        super();
    }

    public static HarmonizedTariffScheduleCodeLogic getInstance() {
        return CDI.current().select(HarmonizedTariffScheduleCodeLogic.class).get();
    }

    public HarmonizedTariffScheduleCodeUnit getHarmonizedTariffScheduleCodeUnitByName(final ExecutionErrorAccumulator eea, final String harmonizedTariffScheduleCodeUnitName) {
        var itemControl = Session.getModelController(ItemControl.class);
        var harmonizedTariffScheduleCodeUnit = itemControl.getHarmonizedTariffScheduleCodeUnitByName(harmonizedTariffScheduleCodeUnitName);

        if(harmonizedTariffScheduleCodeUnit == null) {
            handleExecutionError(UnknownHarmonizedTariffScheduleCodeUnitNameException.class, eea, ExecutionErrors.UnknownHarmonizedTariffScheduleCodeUnitName.name(), harmonizedTariffScheduleCodeUnitName);
        }

        return harmonizedTariffScheduleCodeUnit;
    }
    
    public HarmonizedTariffScheduleCodeUseType getHarmonizedTariffScheduleCodeUseTypeByName(final ExecutionErrorAccumulator eea, final String harmonizedTariffScheduleCodeUseTypeName) {
        var itemControl = Session.getModelController(ItemControl.class);
        var harmonizedTariffScheduleCodeUseType = itemControl.getHarmonizedTariffScheduleCodeUseTypeByName(harmonizedTariffScheduleCodeUseTypeName);

        if(harmonizedTariffScheduleCodeUseType == null) {
            handleExecutionError(UnknownHarmonizedTariffScheduleCodeUseTypeNameException.class, eea, ExecutionErrors.UnknownHarmonizedTariffScheduleCodeUseTypeName.name(), harmonizedTariffScheduleCodeUseTypeName);
        }

        return harmonizedTariffScheduleCodeUseType;
    }
    
}
