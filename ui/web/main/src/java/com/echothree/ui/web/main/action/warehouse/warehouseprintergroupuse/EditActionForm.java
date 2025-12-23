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

package com.echothree.ui.web.main.action.warehouse.warehouseprintergroupuse;

import com.echothree.control.user.printer.common.PrinterUtil;
import com.echothree.control.user.printer.common.result.GetPrinterGroupChoicesResult;
import com.echothree.model.control.printer.common.choice.PrinterGroupChoicesBean;
import com.echothree.view.client.web.struts.BaseLanguageActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="WarehousePrinterGroupUseEdit")
public class EditActionForm
        extends BaseLanguageActionForm {

    private PrinterGroupChoicesBean printerGroupChoices;

    private String partyName;
    private String printerGroupUseTypeName;
    private String printerGroupChoice;

    private void setupPrinterGroupChoices() {
        if(printerGroupChoices == null) {
            try {
                var commandForm = PrinterUtil.getHome().getGetPrinterGroupChoicesForm();

                commandForm.setDefaultPrinterGroupChoice(printerGroupChoice);
                commandForm.setAllowNullChoice(String.valueOf(false));

                var commandResult = PrinterUtil.getHome().getPrinterGroupChoices(userVisitPK, commandForm);
                var executionResult = commandResult.getExecutionResult();
                var result = (GetPrinterGroupChoicesResult)executionResult.getResult();
                printerGroupChoices = result.getPrinterGroupChoices();

                if(printerGroupChoice == null) {
                    printerGroupChoice = printerGroupChoices.getDefaultValue();
                }
            } catch (NamingException ne) {
                // failed, printerGroupChoices remains null, no default
            }
        }
    }

    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    public void setPrinterGroupUseTypeName(String printerGroupUseTypeName) {
        this.printerGroupUseTypeName = printerGroupUseTypeName;
    }

    public String getPrinterGroupUseTypeName() {
        return printerGroupUseTypeName;
    }

    public List<LabelValueBean> getPrinterGroupChoices() {
        List<LabelValueBean> choices = null;

        setupPrinterGroupChoices();
        if(printerGroupChoices != null) {
            choices = convertChoices(printerGroupChoices);
        }

        return choices;
    }

    public void setPrinterGroupChoice(String printerGroupChoice) {
        this.printerGroupChoice = printerGroupChoice;
    }

    public String getPrinterGroupChoice() {
        setupPrinterGroupChoices();

        return printerGroupChoice;
    }

}
