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

package com.echothree.ui.web.main.action.configuration.printergroup;

import com.echothree.control.user.printer.common.PrinterUtil;
import com.echothree.control.user.printer.common.result.GetPrinterGroupStatusChoicesResult;
import com.echothree.model.control.printer.common.choice.PrinterGroupStatusChoicesBean;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="PrinterGroupStatus")
public class StatusActionForm
        extends BaseActionForm {
    
    private PrinterGroupStatusChoicesBean printerGroupStatusChoices;
    
    private String printerGroupName;
    private String printerGroupStatusChoice;
    
    public void setupPrinterGroupStatusChoices()
            throws NamingException {
        if(printerGroupStatusChoices == null) {
            var form = PrinterUtil.getHome().getGetPrinterGroupStatusChoicesForm();

            form.setPrinterGroupName(printerGroupName);
            form.setDefaultPrinterGroupStatusChoice(printerGroupStatusChoice);

            var commandResult = PrinterUtil.getHome().getPrinterGroupStatusChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var getPrinterGroupStatusChoicesResult = (GetPrinterGroupStatusChoicesResult)executionResult.getResult();
            printerGroupStatusChoices = getPrinterGroupStatusChoicesResult.getPrinterGroupStatusChoices();

            if(printerGroupStatusChoice == null)
                printerGroupStatusChoice = printerGroupStatusChoices.getDefaultValue();
        }
    }
    
    public String getPrinterGroupName() {
        return printerGroupName;
    }
    
    public void setPrinterGroupName(String printerGroupName) {
        this.printerGroupName = printerGroupName;
    }
    
    public String getPrinterGroupStatusChoice() {
        return printerGroupStatusChoice;
    }
    
    public void setPrinterGroupStatusChoice(String printerGroupStatusChoice) {
        this.printerGroupStatusChoice = printerGroupStatusChoice;
    }
    
    public List<LabelValueBean> getPrinterGroupStatusChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupPrinterGroupStatusChoices();
        if(printerGroupStatusChoices != null)
            choices = convertChoices(printerGroupStatusChoices);
        
        return choices;
    }
    
    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);
    }
    
}
