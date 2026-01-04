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

package com.echothree.ui.cli.dataloader.util.hts;

import com.echothree.util.common.string.StringUtils;

public class HtsUnitedStatesCode {

    private String commodity;
    private String descrip1;
    private String quantity1;
    private String quantity2;

    /** Creates a new instance of HtsUnitedStatesCode */
    public HtsUnitedStatesCode(String codeLine) {
        // 1-10       COMMODITY   Schedule B Code
        // 16-165     DESCRIP_1   Description of 10-digit commodity - Long
        // 171-173    QUANTITY_1  Alphabetic abbreviation of the first unit of quantity
        // 179-181    QUANTITY_2  Alphabetic abbreviation of the second unit of quantity (if required)

        commodity = codeLine.substring(0, 10);
        descrip1 = StringUtils.getInstance().trimToNull(codeLine.substring(15, 165));
        quantity1 = StringUtils.getInstance().trimToNull(codeLine.substring(170, 173));
        quantity2 = StringUtils.getInstance().trimToNull(codeLine.substring(178, 181));
    }

    /**
     * Returns the commodity.
     * @return the commodity
     */
    public String getCommodity() {
        return commodity;
    }

    /**
     * Sets the commodity.
     * @param commodity the commodity to set
     */
    public void setCommodity(String commodity) {
        this.commodity = commodity;
    }

    /**
     * Returns the descrip1.
     * @return the descrip1
     */
    public String getDescrip1() {
        return descrip1;
    }

    /**
     * Sets the descrip1.
     * @param descrip1 the descrip1 to set
     */
    public void setDescrip1(String descrip1) {
        this.descrip1 = descrip1;
    }

    /**
     * Returns the quantity1.
     * @return the quantity1
     */
    public String getQuantity1() {
        return quantity1;
    }

    /**
     * Sets the quantity1.
     * @param quantity1 the quantity1 to set
     */
    public void setQuantity1(String quantity1) {
        this.quantity1 = quantity1;
    }

    /**
     * Returns the quantity2.
     * @return the quantity2
     */
    public String getQuantity2() {
        return quantity2;
    }

    /**
     * Sets the quantity2.
     * @param quantity2 the quantity2 to set
     */
    public void setQuantity2(String quantity2) {
        this.quantity2 = quantity2;
    }

}
