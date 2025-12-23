// --------------------------------------------------------------------------------
// Copyright 2002-2026 Echo Three, LLC
// Copyright 1999-2004 The Apache Software Foundation.
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

package com.echothree.view.client.web.taglib;

import com.echothree.control.user.geo.common.GeoUtil;
import com.echothree.control.user.geo.common.result.GetCountryResult;
import com.echothree.util.common.form.TransferProperties;
import javax.naming.NamingException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

public class CountryTag
        extends BaseTag {

    protected String geoCodeName;
    protected String countryName;
    protected String iso3Number;
    protected String iso3Letter;
    protected String iso2Letter;
    protected String options;
    protected TransferProperties transferProperties;
    protected String var;
    protected String commandResultVar;
    protected int scope;
    protected boolean logErrors;

    private void init() {
        geoCodeName = null;
        countryName = null;
        iso3Number = null;
        iso3Letter = null;
        iso2Letter = null;
        options = null;
        transferProperties = null;
        var = null;
        commandResultVar = null;
        scope = PageContext.PAGE_SCOPE;
        logErrors = true;
    }

    /** Creates a new instance of CountryTag */
    public CountryTag() {
        super();
        init();
    }

    @Override
    public void release() {
        super.release();
        init();
    }

    public void setGeoCodeName(String geoCodeName) {
        this.geoCodeName = geoCodeName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public void setIso3Number(String iso3Number) {
        this.iso3Number = iso3Number;
    }

    public void setIso3Letter(String iso3Letter) {
        this.iso3Letter = iso3Letter;
    }

    public void setIso2Letter(String iso2Letter) {
        this.iso2Letter = iso2Letter;
    }
    
    public void setOptions(String options) {
        this.options = options;
    }
    
    public void setTransferProperties(TransferProperties transferProperties) {
        this.transferProperties = transferProperties;
    }

    public void setVar(String var) {
        this.var = var;
    }

    public void setCommandResultVar(String commandResultVar) {
        this.commandResultVar = commandResultVar;
    }

    public void setScope(String scope) {
        this.scope = translateScope(scope);
    }
    
    public void setLogErrors(Boolean logErrors) {
        this.logErrors = logErrors;
    }

    @Override
    public int doStartTag()
            throws JspException {
        try {
            var commandForm = GeoUtil.getHome().getGetCountryForm();

            commandForm.setGeoCodeName(geoCodeName);
            commandForm.setCountryName(countryName);
            commandForm.setIso3Number(iso3Number);
            commandForm.setIso3Letter(iso3Letter);
            commandForm.setIso2Letter(iso2Letter);

            setOptions(options, null, commandForm);

            commandForm.setTransferProperties(transferProperties);

            var commandResult = GeoUtil.getHome().getCountry(getUserVisitPK(), commandForm);
            
            pageContext.setAttribute(commandResultVar == null ? TagConstants.CommandResultName : commandResultVar, commandResult, scope);
            if(commandResult.hasErrors()) {
                if(logErrors) {
                    getLog().error(commandResult);
                }
            } else {
                var executionResult = commandResult.getExecutionResult();
                var result = (GetCountryResult)executionResult.getResult();

                pageContext.setAttribute(var, result.getCountry(), scope);
            }
        } catch(NamingException ne) {
            throw new JspException(ne);
        }

        return SKIP_BODY;
    }

}
