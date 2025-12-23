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
import com.echothree.util.common.transfer.ListWrapper;

public class AppearanceTransfer
        extends BaseTransfer {
    
    private String appearanceName;
    private ColorTransfer textColor;
    private ColorTransfer backgroundColor;
    private FontStyleTransfer fontStyle;
    private FontWeightTransfer fontWeight;
    private Boolean isDefault;
    private Integer sortOrder;
    private String description;
    
    private ListWrapper<AppearanceTextDecorationTransfer> appearanceTextDecorations;
    private ListWrapper<AppearanceTextTransformationTransfer> appearanceTextTransformations;
    
    /** Creates a new instance of AppearanceTransfer */
    public AppearanceTransfer(String appearanceName, ColorTransfer textColor, ColorTransfer backgroundColor, FontStyleTransfer fontStyle,
            FontWeightTransfer fontWeight, Boolean isDefault, Integer sortOrder, String description) {
        this.appearanceName = appearanceName;
        this.textColor = textColor;
        this.backgroundColor = backgroundColor;
        this.fontStyle = fontStyle;
        this.fontWeight = fontWeight;
        this.isDefault = isDefault;
        this.sortOrder = sortOrder;
        this.description = description;
    }

    /**
     * Returns the appearanceName.
     * @return the appearanceName
     */
    public String getAppearanceName() {
        return appearanceName;
    }

    /**
     * Sets the appearanceName.
     * @param appearanceName the appearanceName to set
     */
    public void setAppearanceName(String appearanceName) {
        this.appearanceName = appearanceName;
    }

    /**
     * Returns the textColor.
     * @return the textColor
     */
    public ColorTransfer getTextColor() {
        return textColor;
    }

    /**
     * Sets the textColor.
     * @param textColor the textColor to set
     */
    public void setTextColor(ColorTransfer textColor) {
        this.textColor = textColor;
    }

    /**
     * Returns the backgroundColor.
     * @return the backgroundColor
     */
    public ColorTransfer getBackgroundColor() {
        return backgroundColor;
    }

    /**
     * Sets the backgroundColor.
     * @param backgroundColor the backgroundColor to set
     */
    public void setBackgroundColor(ColorTransfer backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    /**
     * Returns the fontStyle.
     * @return the fontStyle
     */
    public FontStyleTransfer getFontStyle() {
        return fontStyle;
    }

    /**
     * Sets the fontStyle.
     * @param fontStyle the fontStyle to set
     */
    public void setFontStyle(FontStyleTransfer fontStyle) {
        this.fontStyle = fontStyle;
    }

    /**
     * Returns the fontWeight.
     * @return the fontWeight
     */
    public FontWeightTransfer getFontWeight() {
        return fontWeight;
    }

    /**
     * Sets the fontWeight.
     * @param fontWeight the fontWeight to set
     */
    public void setFontWeight(FontWeightTransfer fontWeight) {
        this.fontWeight = fontWeight;
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

    /**
     * Returns the appearanceTextDecorations.
     * @return the appearanceTextDecorations
     */
    public ListWrapper<AppearanceTextDecorationTransfer> getAppearanceTextDecorations() {
        return appearanceTextDecorations;
    }

    /**
     * Sets the appearanceTextDecorations.
     * @param appearanceTextDecorations the appearanceTextDecorations to set
     */
    public void setAppearanceTextDecorations(ListWrapper<AppearanceTextDecorationTransfer> appearanceTextDecorations) {
        this.appearanceTextDecorations = appearanceTextDecorations;
    }

    /**
     * Returns the appearanceTextTransformations.
     * @return the appearanceTextTransformations
     */
    public ListWrapper<AppearanceTextTransformationTransfer> getAppearanceTextTransformations() {
        return appearanceTextTransformations;
    }

    /**
     * Sets the appearanceTextTransformations.
     * @param appearanceTextTransformations the appearanceTextTransformations to set
     */
    public void setAppearanceTextTransformations(ListWrapper<AppearanceTextTransformationTransfer> appearanceTextTransformations) {
        this.appearanceTextTransformations = appearanceTextTransformations;
    }

}
