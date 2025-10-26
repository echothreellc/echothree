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

package com.echothree.model.control.inventory.server.logic;

import com.echothree.model.control.inventory.server.control.LotTimeControl;
import com.echothree.model.data.inventory.server.entity.Lot;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;

public class LotTimeLogic {

    private LotTimeLogic() {
        super();
    }

    private static class LotTimeLogicHolder {
        static LotTimeLogic instance = new LotTimeLogic();
    }

    public static LotTimeLogic getInstance() {
        return LotTimeLogicHolder.instance;
    }

    public void createOrUpdateLotTimeIfNotNull(final ExecutionErrorAccumulator ema, final Lot lot, final String lotTimeTypeName, final Long time,
            final BasePK partyPK) {
        if(time != null) {
            createOrUpdateLotTime(ema, lot, lotTimeTypeName, time, partyPK);
        }
    }

    public void createOrUpdateLotTime(final ExecutionErrorAccumulator ema, final Lot lot, final String lotTimeTypeName, final Long time,
            final BasePK partyPK) {
        var lotTimeControl = Session.getModelController(LotTimeControl.class);
        var lotTimeType = lotTimeControl.getLotTimeTypeByName(lotTimeTypeName);

        if(lotTimeType == null) {
            if(ema != null) {
                ema.addExecutionError(ExecutionErrors.UnknownLotTimeTypeName.name(), lotTimeTypeName);
            }
        } else {
            var lotTimeValue = lotTimeControl.getLotTimeValueForUpdate(lot, lotTimeType);

            if(lotTimeValue == null) {
                lotTimeControl.createLotTime(lot, lotTimeType, time, partyPK);
            } else {
                lotTimeValue.setTime(time);
                lotTimeControl.updateLotTimeFromValue(lotTimeValue, partyPK);
            }
        }
    }

    public Long getLotTime(final ExecutionErrorAccumulator ema, final Lot lot, final String lotTimeTypeName) {
        var lotTimeControl = Session.getModelController(LotTimeControl.class);
        var lotDetail = lot.getLastDetail();
        var lotTimeType = lotTimeControl.getLotTimeTypeByName(lotTimeTypeName);
        Long result = null;

        if(lotTimeType == null) {
            if(ema != null) {
                ema.addExecutionError(ExecutionErrors.UnknownLotTimeTypeName.name(), lotTimeTypeName);
            }
        } else {
            var lotTime = lotTimeControl.getLotTime(lot, lotTimeType);

            if(lotTime == null) {
                if(ema != null) {
                    ema.addExecutionError(ExecutionErrors.UnknownLotTime.name(), lotDetail.getLotIdentifier(), lotTimeTypeName);
                }
            } else {
                result = lotTime.getTime();
            }
        }

        return result;
    }

    public void deleteLotTime(final ExecutionErrorAccumulator ema, final Lot lot, final String lotTimeTypeName, final BasePK deletedBy) {
        var lotTimeControl = Session.getModelController(LotTimeControl.class);
        var lotDetail = lot.getLastDetail();
        var lotTimeType = lotTimeControl.getLotTimeTypeByName(lotTimeTypeName);

        if(lotTimeType == null) {
            if(ema != null) {
                ema.addExecutionError(ExecutionErrors.UnknownLotTimeTypeName.name(), lotTimeTypeName);
            }
        } else {
            var lotTime = lotTimeControl.getLotTimeForUpdate(lot, lotTimeType);

            if(lotTime == null) {
                if(ema != null) {
                    ema.addExecutionError(ExecutionErrors.UnknownLotTime.name(), lotDetail.getLotIdentifier(), lotTimeTypeName);
                }
            } else {
                lotTimeControl.deleteLotTime(lotTime, deletedBy);
            }
        }
    }

}
