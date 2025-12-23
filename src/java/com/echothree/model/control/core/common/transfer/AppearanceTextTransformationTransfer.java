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

public class AppearanceTextTransformationTransfer
        extends BaseTransfer {
    
    private AppearanceTransfer appearance;
    private TextTransformationTransfer textTransformation;
    
    /** Creates a new instance of AppearanceTextTransformationTransfer */
    public AppearanceTextTransformationTransfer(AppearanceTransfer appearance, TextTransformationTransfer textTransformation) {
        this.appearance = appearance;
        this.textTransformation = textTransformation;
    }

    /**
     * Returns the appearance.
     * @return the appearance
     */
    public AppearanceTransfer getAppearance() {
        return appearance;
    }

    /**
     * Sets the appearance.
     * @param appearance the appearance to set
     */
    public void setAppearance(AppearanceTransfer appearance) {
        this.appearance = appearance;
    }

    /**
     * Returns the textTransformation.
     * @return the textTransformation
     */
    public TextTransformationTransfer getTextTransformation() {
        return textTransformation;
    }

    /**
     * Sets the textTransformation.
     * @param textTransformation the textTransformation to set
     */
    public void setTextTransformation(TextTransformationTransfer textTransformation) {
        this.textTransformation = textTransformation;
    }

}
