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

package com.echothree.model.control.scale.common.transfer;

import com.echothree.model.control.core.common.transfer.ServerServiceTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

public class ScaleTransfer
        extends BaseTransfer {
    
    private String scaleName;
    private ScaleTypeTransfer scaleType;
    private ServerServiceTransfer serverService;
    private Boolean isDefault;
    private Integer sortOrder;
    private String description;

    /** Creates a new instance of ScaleTransfer */
    public ScaleTransfer(String scaleName, ScaleTypeTransfer scaleType, ServerServiceTransfer serverService, Boolean isDefault, Integer sortOrder,
            String description) {
        this.scaleName = scaleName;
        this.scaleType = scaleType;
        this.serverService = serverService;
        this.isDefault = isDefault;
        this.sortOrder = sortOrder;
        this.description = description;
    }

    /**
     * Returns the scaleName.
     * @return the scaleName
     */
    public String getScaleName() {
        return scaleName;
    }

    /**
     * Sets the scaleName.
     * @param scaleName the scaleName to set
     */
    public void setScaleName(String scaleName) {
        this.scaleName = scaleName;
    }

    /**
     * Returns the scaleType.
     * @return the scaleType
     */
    public ScaleTypeTransfer getScaleType() {
        return scaleType;
    }

    /**
     * Sets the scaleType.
     * @param scaleType the scaleType to set
     */
    public void setScaleType(ScaleTypeTransfer scaleType) {
        this.scaleType = scaleType;
    }

    /**
     * Returns the serverService.
     * @return the serverService
     */
    public ServerServiceTransfer getServerService() {
        return serverService;
    }

    /**
     * Sets the serverService.
     * @param serverService the serverService to set
     */
    public void setServerService(ServerServiceTransfer serverService) {
        this.serverService = serverService;
    }

    /**
     * Returns the isDefault.
     * @return the isDefault
     */
    public Boolean getIsDefault() {
        return isDefault;
    }

    /**
     * Sets the isDefault.
     * @param isDefault the isDefault to set
     */
    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }

    /**
     * Returns the sortOrder.
     * @return the sortOrder
     */
    public Integer getSortOrder() {
        return sortOrder;
    }

    /**
     * Sets the sortOrder.
     * @param sortOrder the sortOrder to set
     */
    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    /**
     * Returns the description.
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description.
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

}
