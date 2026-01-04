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

package com.echothree.ui.web.main.action.configuration.printer;

import com.echothree.control.user.printer.common.PrinterUtil;
import com.echothree.control.user.printer.common.result.GetPrinterStatusChoicesResult;
import com.echothree.model.control.printer.common.choice.PrinterStatusChoicesBean;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="PrinterStatus")
public class StatusActionForm
        extends BaseActionForm {
    
    private PrinterStatusChoicesBean printerStatusChoices;
    
    private String printerGroupName;
    private String printerName;
    private String printerStatusChoice;
    
    public void setupPrinterStatusChoices()
            throws NamingException {
        if(printerStatusChoices == null) {
            var form = PrinterUtil.getHome().getGetPrinterStatusChoicesForm();

            form.setPrinterName(printerName);
            form.setDefaultPrinterStatusChoice(printerStatusChoice);

            var commandResult = PrinterUtil.getHome().getPrinterStatusChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var getPrinterStatusChoicesResult = (GetPrinterStatusChoicesResult)executionResult.getResult();
            printerStatusChoices = getPrinterStatusChoicesResult.getPrinterStatusChoices();

            if(printerStatusChoice == null)
                printerStatusChoice = printerStatusChoices.getDefaultValue();
        }
    }
    
    public String getPrinterGroupName() {
        return printerGroupName;
    }
    
    public void setPrinterGroupName(String printerGroupName) {
        this.printerGroupName = printerGroupName;
    }
    
    public void setPrinterName(String printerName) {
        this.printerName = printerName;
    }
    
    public String getPrinterName() {
        return printerName;
    }
    
    public String getPrinterStatusChoice() {
        return printerStatusChoice;
    }
    
    public void setPrinterStatusChoice(String printerStatusChoice) {
        this.printerStatusChoice = printerStatusChoice;
    }
    
    public List<LabelValueBean> getPrinterStatusChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupPrinterStatusChoices();
        if(printerStatusChoices != null)
            choices = convertChoices(printerStatusChoices);
        
        return choices;
    }
    
    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);
    }
    
}
