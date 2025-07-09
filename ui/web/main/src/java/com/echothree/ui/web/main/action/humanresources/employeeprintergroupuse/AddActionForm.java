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

package com.echothree.ui.web.main.action.humanresources.employeeprintergroupuse;

import com.echothree.control.user.printer.common.PrinterUtil;
import com.echothree.control.user.printer.common.result.GetPrinterGroupChoicesResult;
import com.echothree.control.user.printer.common.result.GetPrinterGroupUseTypeChoicesResult;
import com.echothree.model.control.printer.common.choice.PrinterGroupChoicesBean;
import com.echothree.model.control.printer.common.choice.PrinterGroupUseTypeChoicesBean;
import com.echothree.view.client.web.struts.BaseLanguageActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="EmployeePrinterGroupUseAdd")
public class AddActionForm
        extends BaseLanguageActionForm {

    private PrinterGroupUseTypeChoicesBean printerGroupUseTypeChoices;
    private PrinterGroupChoicesBean printerGroupChoices;

    private String partyName;
    private String printerGroupUseTypeChoice;
    private String printerGroupChoice;

    private void setupPrinterGroupUseTypeChoices() {
        if(printerGroupUseTypeChoices == null) {
            try {
                var commandForm = PrinterUtil.getHome().getGetPrinterGroupUseTypeChoicesForm();

                commandForm.setDefaultPrinterGroupUseTypeChoice(printerGroupUseTypeChoice);
                commandForm.setAllowNullChoice(String.valueOf(false));

                var commandResult = PrinterUtil.getHome().getPrinterGroupUseTypeChoices(userVisitPK, commandForm);
                var executionResult = commandResult.getExecutionResult();
                var result = (GetPrinterGroupUseTypeChoicesResult)executionResult.getResult();
                printerGroupUseTypeChoices = result.getPrinterGroupUseTypeChoices();

                if(printerGroupUseTypeChoice == null) {
                    printerGroupUseTypeChoice = printerGroupUseTypeChoices.getDefaultValue();
                }
            } catch (NamingException ne) {
                // failed, printerGroupUseTypeChoices remains null, no default
            }
        }
    }

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

    public List<LabelValueBean> getPrinterGroupUseTypeChoices() {
        List<LabelValueBean> choices = null;

        setupPrinterGroupUseTypeChoices();
        if(printerGroupUseTypeChoices != null) {
            choices = convertChoices(printerGroupUseTypeChoices);
        }

        return choices;
    }

    public void setPrinterGroupUseTypeChoice(String printerGroupUseTypeChoice) {
        this.printerGroupUseTypeChoice = printerGroupUseTypeChoice;
    }

    public String getPrinterGroupUseTypeChoice() {
        setupPrinterGroupUseTypeChoices();

        return printerGroupUseTypeChoice;
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
