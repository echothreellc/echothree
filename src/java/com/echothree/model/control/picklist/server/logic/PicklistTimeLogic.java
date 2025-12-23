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

package com.echothree.model.control.picklist.server.logic;

import com.echothree.model.control.picklist.server.control.PicklistControl;
import com.echothree.model.data.picklist.server.entity.Picklist;
import com.echothree.model.data.picklist.server.entity.PicklistType;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class PicklistTimeLogic {

    protected PicklistTimeLogic() {
        super();
    }

    public static PicklistTimeLogic getInstance() {
        return CDI.current().select(PicklistTimeLogic.class).get();
    }

    private String getPicklistTypeName(PicklistType picklistType) {
        return picklistType.getLastDetail().getPicklistTypeName();
    }

    public void createOrUpdatePicklistTimeIfNotNull(final ExecutionErrorAccumulator ema, final Picklist picklist, final String picklistTimeTypeName, final Long time,
            final BasePK partyPK) {
        if(time != null) {
            createOrUpdatePicklistTime(ema, picklist, picklistTimeTypeName, time, partyPK);
        }
    }

    public void createOrUpdatePicklistTime(final ExecutionErrorAccumulator ema, final Picklist picklist, final String picklistTimeTypeName, final Long time,
            final BasePK partyPK) {
        var picklistControl = Session.getModelController(PicklistControl.class);
        var picklistDetail = picklist.getLastDetail();
        var picklistType = picklistDetail.getPicklistType();
        var picklistTimeType = picklistControl.getPicklistTimeTypeByName(picklistType, picklistTimeTypeName);

        if(picklistTimeType == null) {
            if(ema != null) {
                ema.addExecutionError(ExecutionErrors.UnknownPicklistTimeTypeName.name(), getPicklistTypeName(picklistType), picklistTimeTypeName);
            }
        } else {
            var picklistTimeValue = picklistControl.getPicklistTimeValueForUpdate(picklist, picklistTimeType);

            if(picklistTimeValue == null) {
                picklistControl.createPicklistTime(picklist, picklistTimeType, time, partyPK);
            } else {
                picklistTimeValue.setTime(time);
                picklistControl.updatePicklistTimeFromValue(picklistTimeValue, partyPK);
            }
        }
    }

    public Long getPicklistTime(final ExecutionErrorAccumulator ema, final Picklist picklist, final String picklistTimeTypeName) {
        var picklistControl = Session.getModelController(PicklistControl.class);
        var picklistDetail = picklist.getLastDetail();
        var picklistType = picklistDetail.getPicklistType();
        var picklistTimeType = picklistControl.getPicklistTimeTypeByName(picklistType, picklistTimeTypeName);
        Long result = null;

        if(picklistTimeType == null) {
            if(ema != null) {
                ema.addExecutionError(ExecutionErrors.UnknownPicklistTimeTypeName.name(), getPicklistTypeName(picklistType), picklistTimeTypeName);
            }
        } else {
            var picklistTime = picklistControl.getPicklistTimeForUpdate(picklist, picklistTimeType);

            if(picklistTime == null) {
                if(ema != null) {
                    ema.addExecutionError(ExecutionErrors.UnknownPicklistTime.name(), getPicklistTypeName(picklistType), picklistDetail.getPicklistName(), picklistTimeTypeName);
                }
            } else {
                result = picklistTime.getTime();
            }
        }

        return result;
    }

    public void deletePicklistTime(final ExecutionErrorAccumulator ema, final Picklist picklist, final String picklistTimeTypeName, final BasePK deletedBy) {
        var picklistControl = Session.getModelController(PicklistControl.class);
        var picklistDetail = picklist.getLastDetail();
        var picklistType = picklistDetail.getPicklistType();
        var picklistTimeType = picklistControl.getPicklistTimeTypeByName(picklistType, picklistTimeTypeName);

        if(picklistTimeType == null) {
            if(ema != null) {
                ema.addExecutionError(ExecutionErrors.UnknownPicklistTimeTypeName.name(), getPicklistTypeName(picklistType), picklistTimeTypeName);
            }
        } else {
            var picklistTime = picklistControl.getPicklistTimeForUpdate(picklist, picklistTimeType);

            if(picklistTime == null) {
                if(ema != null) {
                    ema.addExecutionError(ExecutionErrors.UnknownPicklistTime.name(), getPicklistTypeName(picklistType), picklistDetail.getPicklistName(), picklistTimeTypeName);
                }
            } else {
                picklistControl.deletePicklistTime(picklistTime, deletedBy);
            }
        }
    }

}
