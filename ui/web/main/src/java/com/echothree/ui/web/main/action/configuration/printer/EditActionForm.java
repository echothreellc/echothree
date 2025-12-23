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
import com.echothree.control.user.printer.common.result.GetPrinterGroupChoicesResult;
import com.echothree.model.control.printer.common.choice.PrinterGroupChoicesBean;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="PrinterEdit")
public class EditActionForm
        extends BaseActionForm {
    
    private PrinterGroupChoicesBean printerGroupChoices;
    
    private String originalPrinterName;
    private String printerGroupName;
    private String printerName;
    private String printerGroupChoice;
    private String priority;
    private String description;
    
    private void setupPrinterGroupChoices()
            throws NamingException {
        if(printerGroupChoices == null) {
            var form = PrinterUtil.getHome().getGetPrinterGroupChoicesForm();

            form.setDefaultPrinterGroupChoice(printerGroupChoice);
            form.setAllowNullChoice(String.valueOf(false));

            var commandResult = PrinterUtil.getHome().getPrinterGroupChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var getPrinterGroupChoicesResult = (GetPrinterGroupChoicesResult)executionResult.getResult();
            printerGroupChoices = getPrinterGroupChoicesResult.getPrinterGroupChoices();

            if(printerGroupChoice == null)
                printerGroupChoice = printerGroupChoices.getDefaultValue();
        }
    }
    
    public void setOriginalPrinterName(String originalPrinterName) {
        this.originalPrinterName = originalPrinterName;
    }
    
    public String getOriginalPrinterName() {
        return originalPrinterName;
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
    
    public String getPrinterGroupChoice() {
        return printerGroupChoice;
    }
    
    public void setPrinterGroupChoice(String printerGroupChoice) {
        this.printerGroupChoice = printerGroupChoice;
    }
    
    public List<LabelValueBean> getPrinterGroupChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupPrinterGroupChoices();
        if(printerGroupChoices != null)
            choices = convertChoices(printerGroupChoices);
        
        return choices;
    }
    
    public String getPriority() {
        return priority;
    }
    
    public void setPriority(String priority) {
        this.priority = priority;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
    
    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);
    }
    
}
