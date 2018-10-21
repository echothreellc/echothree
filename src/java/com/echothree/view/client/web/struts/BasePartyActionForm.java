// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

package com.echothree.view.client.web.struts;

import com.echothree.control.user.accounting.common.AccountingUtil;
import com.echothree.control.user.accounting.remote.form.GetCurrencyChoicesForm;
import com.echothree.control.user.accounting.remote.result.GetCurrencyChoicesResult;
import com.echothree.control.user.party.common.PartyUtil;
import com.echothree.control.user.party.remote.form.GetDateTimeFormatChoicesForm;
import com.echothree.control.user.party.remote.form.GetLanguageChoicesForm;
import com.echothree.control.user.party.remote.form.GetTimeZoneChoicesForm;
import com.echothree.control.user.party.remote.result.GetDateTimeFormatChoicesResult;
import com.echothree.control.user.party.remote.result.GetLanguageChoicesResult;
import com.echothree.control.user.party.remote.result.GetTimeZoneChoicesResult;
import com.echothree.model.control.accounting.remote.choice.CurrencyChoicesBean;
import com.echothree.model.control.party.remote.choice.DateTimeFormatChoicesBean;
import com.echothree.model.control.party.remote.choice.LanguageChoicesBean;
import com.echothree.model.control.party.remote.choice.TimeZoneChoicesBean;
import com.echothree.util.remote.command.CommandResult;
import com.echothree.util.remote.command.ExecutionResult;
import java.util.List;
import javax.naming.NamingException;
import org.apache.struts.util.LabelValueBean;

public class BasePartyActionForm
        extends BaseActionForm {
    
    private CurrencyChoicesBean currencyChoices = null;
    private DateTimeFormatChoicesBean dateTimeFormatChoices = null;
    private LanguageChoicesBean languageChoices = null;
    private TimeZoneChoicesBean timeZoneChoices = null;
    
    private String currencyChoice = null;
    private String dateTimeFormatChoice = null;
    private String languageChoice = null;
    private String timeZoneChoice = null;
    
    /** Creates a new instance of BasePartyActionForm */
    public BasePartyActionForm() {
        super();
    }
    
    private void setupCurrencyChoices() {
        if(currencyChoices == null) {
            try {
                GetCurrencyChoicesForm commandForm = AccountingUtil.getHome().getGetCurrencyChoicesForm();
                
                commandForm.setDefaultCurrencyChoice(currencyChoice);
                commandForm.setAllowNullChoice(Boolean.TRUE.toString());
                
                CommandResult commandResult = AccountingUtil.getHome().getCurrencyChoices(userVisitPK, commandForm);
                ExecutionResult executionResult = commandResult.getExecutionResult();
                GetCurrencyChoicesResult getCurrencyChoicesResult = (GetCurrencyChoicesResult)executionResult.getResult();
                currencyChoices = getCurrencyChoicesResult.getCurrencyChoices();
                
                if(currencyChoice == null) {
                    currencyChoice = currencyChoices.getDefaultValue();
                }
            } catch (NamingException ne) {
                // failed, currencyChoices remains null, no default
            }
        }
    }
    
    public List<LabelValueBean> getCurrencyChoices() {
        List<LabelValueBean> choices = null;
        
        setupCurrencyChoices();
        if(currencyChoices != null) {
            choices = convertChoices(currencyChoices);
        }
        
        return choices;
    }
    
    public void setCurrencyChoice(String currencyChoice) {
        this.currencyChoice = currencyChoice;
    }
    
    public String getCurrencyChoice() {
        setupCurrencyChoices();
        
        return currencyChoice;
    }
    
    private void setupDateTimeFormatChoices() {
        if(dateTimeFormatChoices == null) {
            try {
                GetDateTimeFormatChoicesForm commandForm = PartyUtil.getHome().getGetDateTimeFormatChoicesForm();
                
                commandForm.setDefaultDateTimeFormatChoice(dateTimeFormatChoice);
                commandForm.setAllowNullChoice(Boolean.TRUE.toString());
                
                CommandResult commandResult = PartyUtil.getHome().getDateTimeFormatChoices(userVisitPK, commandForm);
                ExecutionResult executionResult = commandResult.getExecutionResult();
                GetDateTimeFormatChoicesResult getDateTimeFormatChoicesResult = (GetDateTimeFormatChoicesResult)executionResult.getResult();
                dateTimeFormatChoices = getDateTimeFormatChoicesResult.getDateTimeFormatChoices();
                
                if(dateTimeFormatChoice == null) {
                    dateTimeFormatChoice = dateTimeFormatChoices.getDefaultValue();
                }
            } catch (NamingException ne) {
                // failed, dateTimeFormatChoices remains null, no default
            }
        }
    }
    
    public List<LabelValueBean> getDateTimeFormatChoices() {
        List<LabelValueBean> choices = null;
        
        setupDateTimeFormatChoices();
        if(dateTimeFormatChoices != null) {
            choices = convertChoices(dateTimeFormatChoices);
        }
        
        return choices;
    }
    
    public void setDateTimeFormatChoice(String dateTimeFormatChoice) {
        this.dateTimeFormatChoice = dateTimeFormatChoice;
    }
    
    public String getDateTimeFormatChoice() {
        setupDateTimeFormatChoices();
        
        return dateTimeFormatChoice;
    }
    
    private void setupLanguageChoices() {
        if(languageChoices == null) {
            try {
                GetLanguageChoicesForm commandForm = PartyUtil.getHome().getGetLanguageChoicesForm();
                
                commandForm.setDefaultLanguageChoice(languageChoice);
                commandForm.setAllowNullChoice(Boolean.TRUE.toString());
                
                CommandResult commandResult = PartyUtil.getHome().getLanguageChoices(userVisitPK, commandForm);
                ExecutionResult executionResult = commandResult.getExecutionResult();
                GetLanguageChoicesResult getLanguageChoicesResult = (GetLanguageChoicesResult)executionResult.getResult();
                languageChoices = getLanguageChoicesResult.getLanguageChoices();
                
                if(languageChoice == null) {
                    languageChoice = languageChoices.getDefaultValue();
                }
            } catch (NamingException ne) {
                // failed, languageChoices remains null, no default
            }
        }
    }
    
    public List<LabelValueBean> getLanguageChoices() {
        List<LabelValueBean> choices = null;
        
        setupLanguageChoices();
        if(languageChoices != null) {
            choices = convertChoices(languageChoices);
        }
        
        return choices;
    }
    
    public void setLanguageChoice(String languageChoice) {
        this.languageChoice = languageChoice;
    }
    
    public String getLanguageChoice() {
        setupLanguageChoices();
        
        return languageChoice;
    }
    
    private void setupTimeZoneChoices() {
        if(timeZoneChoices == null) {
            try {
                GetTimeZoneChoicesForm commandForm = PartyUtil.getHome().getGetTimeZoneChoicesForm();
                
                commandForm.setDefaultTimeZoneChoice(timeZoneChoice);
                commandForm.setAllowNullChoice(Boolean.TRUE.toString());
                
                CommandResult commandResult = PartyUtil.getHome().getTimeZoneChoices(userVisitPK, commandForm);
                ExecutionResult executionResult = commandResult.getExecutionResult();
                GetTimeZoneChoicesResult getTimeZoneChoicesResult = (GetTimeZoneChoicesResult)executionResult.getResult();
                timeZoneChoices = getTimeZoneChoicesResult.getTimeZoneChoices();
                
                if(timeZoneChoice == null) {
                    timeZoneChoice = timeZoneChoices.getDefaultValue();
                }
            } catch (NamingException ne) {
                // failed, timeZoneChoices remains null, no default
            }
        }
    }
    
    public List<LabelValueBean> getTimeZoneChoices() {
        List<LabelValueBean> choices = null;
        
        setupTimeZoneChoices();
        if(timeZoneChoices != null) {
            choices = convertChoices(timeZoneChoices);
        }
        
        return choices;
    }
    
    public void setTimeZoneChoice(String timeZoneChoice) {
        this.timeZoneChoice = timeZoneChoice;
    }
    
    public String getTimeZoneChoice() {
        setupTimeZoneChoices();
        
        return timeZoneChoice;
    }
    
}
