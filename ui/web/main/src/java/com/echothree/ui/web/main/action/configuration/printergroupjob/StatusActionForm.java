// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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

package com.echothree.ui.web.main.action.configuration.printergroupjob;

import com.echothree.control.user.printer.common.PrinterUtil;
import com.echothree.control.user.printer.common.form.GetPrinterGroupJobStatusChoicesForm;
import com.echothree.control.user.printer.common.result.GetPrinterGroupJobStatusChoicesResult;
import com.echothree.model.control.printer.common.choice.PrinterGroupJobStatusChoicesBean;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.ExecutionResult;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="PrinterGroupJobStatus")
public class StatusActionForm
        extends BaseActionForm {
    
    private PrinterGroupJobStatusChoicesBean printerGroupJobStatusChoices;
    
    private String printerGroupName;
    private String printerGroupJobName;
    private String printerGroupJobStatusChoice;
    
    public void setupPrinterGroupJobStatusChoices() {
        if(printerGroupJobStatusChoices == null) {
            try {
                GetPrinterGroupJobStatusChoicesForm form = PrinterUtil.getHome().getGetPrinterGroupJobStatusChoicesForm();
                
                form.setPrinterGroupJobName(printerGroupJobName);
                form.setDefaultPrinterGroupJobStatusChoice(printerGroupJobStatusChoice);
                
                CommandResult commandResult = PrinterUtil.getHome().getPrinterGroupJobStatusChoices(userVisitPK, form);
                ExecutionResult executionResult = commandResult.getExecutionResult();
                GetPrinterGroupJobStatusChoicesResult getPrinterGroupJobStatusChoicesResult = (GetPrinterGroupJobStatusChoicesResult)executionResult.getResult();
                printerGroupJobStatusChoices = getPrinterGroupJobStatusChoicesResult.getPrinterGroupJobStatusChoices();
                
                if(printerGroupJobStatusChoice == null)
                    printerGroupJobStatusChoice = printerGroupJobStatusChoices.getDefaultValue();
            } catch (NamingException ne) {
                ne.printStackTrace();
                // failed, printerGroupJobStatusChoices remains null, no default
            }
        }
    }
    
    public String getPrinterGroupName() {
        return printerGroupName;
    }
    
    public void setPrinterGroupName(String printerGroupName) {
        this.printerGroupName = printerGroupName;
    }
    
    public void setPrinterGroupJobName(String printerGroupJobName) {
        this.printerGroupJobName = printerGroupJobName;
    }
    
    public String getPrinterGroupJobName() {
        return printerGroupJobName;
    }
    
    public String getPrinterGroupJobStatusChoice() {
        return printerGroupJobStatusChoice;
    }
    
    public void setPrinterGroupJobStatusChoice(String printerGroupJobStatusChoice) {
        this.printerGroupJobStatusChoice = printerGroupJobStatusChoice;
    }
    
    public List<LabelValueBean> getPrinterGroupJobStatusChoices() {
        List<LabelValueBean> choices = null;
        
        setupPrinterGroupJobStatusChoices();
        if(printerGroupJobStatusChoices != null)
            choices = convertChoices(printerGroupJobStatusChoices);
        
        return choices;
    }
    
    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);
    }
    
}
