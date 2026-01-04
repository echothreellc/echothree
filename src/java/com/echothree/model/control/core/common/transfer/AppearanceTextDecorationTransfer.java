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

public class AppearanceTextDecorationTransfer
        extends BaseTransfer {
    
    private AppearanceTransfer appearance;
    private TextDecorationTransfer textDecoration;
    
    /** Creates a new instance of AppearanceTextDecorationTransfer */
    public AppearanceTextDecorationTransfer(AppearanceTransfer appearance, TextDecorationTransfer textDecoration) {
        this.appearance = appearance;
        this.textDecoration = textDecoration;
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
     * Returns the textDecoration.
     * @return the textDecoration
     */
    public TextDecorationTransfer getTextDecoration() {
        return textDecoration;
    }

    /**
     * Sets the textDecoration.
     * @param textDecoration the textDecoration to set
     */
    public void setTextDecoration(TextDecorationTransfer textDecoration) {
        this.textDecoration = textDecoration;
    }

}
