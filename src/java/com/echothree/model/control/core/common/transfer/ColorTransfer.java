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

package com.echothree.model.control.core.common.transfer;

import com.echothree.util.common.transfer.BaseTransfer;

public class ColorTransfer
        extends BaseTransfer {
    
    private String colorName;
    private Integer red;
    private Integer green;
    private Integer blue;
    private Boolean isDefault;
    private Integer sortOrder;
    private String description;
    
    /** Creates a new instance of ColorTransfer */
    public ColorTransfer(String colorName, Integer red, Integer green, Integer blue, Boolean isDefault, Integer sortOrder, String description) {
        this.colorName = colorName;
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.isDefault = isDefault;
        this.sortOrder = sortOrder;
        this.description = description;
    }

    /**
     * Returns the colorName.
     * @return the colorName
     */
    public String getColorName() {
        return colorName;
    }

    /**
     * Sets the colorName.
     * @param colorName the colorName to set
     */
    public void setColorName(String colorName) {
        this.colorName = colorName;
    }

    /**
     * Returns the red.
     * @return the red
     */
    public Integer getRed() {
        return red;
    }

    /**
     * Sets the red.
     * @param red the red to set
     */
    public void setRed(Integer red) {
        this.red = red;
    }

    /**
     * Returns the green.
     * @return the green
     */
    public Integer getGreen() {
        return green;
    }

    /**
     * Sets the green.
     * @param green the green to set
     */
    public void setGreen(Integer green) {
        this.green = green;
    }

    /**
     * Returns the blue.
     * @return the blue
     */
    public Integer getBlue() {
        return blue;
    }

    /**
     * Sets the blue.
     * @param blue the blue to set
     */
    public void setBlue(Integer blue) {
        this.blue = blue;
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
