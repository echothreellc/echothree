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

package com.echothree.ui.web.main.action.configuration.harmonizedtariffschedulecode;

import com.echothree.control.user.item.common.ItemUtil;
import com.echothree.control.user.item.common.edit.HarmonizedTariffScheduleCodeEdit;
import com.echothree.control.user.item.common.form.EditHarmonizedTariffScheduleCodeForm;
import com.echothree.control.user.item.common.result.EditHarmonizedTariffScheduleCodeResult;
import com.echothree.control.user.item.common.spec.HarmonizedTariffScheduleCodeSpec;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.MainBaseEditAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.command.CommandResult;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

@SproutAction(
    path = "/Configuration/HarmonizedTariffScheduleCode/Edit",
    mappingClass = SecureActionMapping.class,
    name = "HarmonizedTariffScheduleCodeEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Configuration/HarmonizedTariffScheduleCode/Main", redirect = true),
        @SproutForward(name = "Form", path = "/configuration/harmonizedtariffschedulecode/edit.jsp")
    }
)
public class EditAction
        extends MainBaseEditAction<EditActionForm, HarmonizedTariffScheduleCodeSpec, HarmonizedTariffScheduleCodeEdit, EditHarmonizedTariffScheduleCodeForm, EditHarmonizedTariffScheduleCodeResult> {
    
    @Override
    protected HarmonizedTariffScheduleCodeSpec getSpec(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var spec = ItemUtil.getHome().getHarmonizedTariffScheduleCodeSpec();
        
        spec.setCountryName(findParameter(request, ParameterConstants.COUNTRY_NAME, actionForm.getCountryName()));
        spec.setHarmonizedTariffScheduleCodeName(findParameter(request, ParameterConstants.ORIGINAL_HARMONIZED_TARIFF_SCHEDULE_CODE_NAME, actionForm.getOriginalHarmonizedTariffScheduleCodeName()));
        
        return spec;
    }
    
    @Override
    protected HarmonizedTariffScheduleCodeEdit getEdit(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var edit = ItemUtil.getHome().getHarmonizedTariffScheduleCodeEdit();

        edit.setHarmonizedTariffScheduleCodeName(actionForm.getHarmonizedTariffScheduleCodeName());
        edit.setFirstHarmonizedTariffScheduleCodeUnitName(actionForm.getFirstHarmonizedTariffScheduleCodeUnitChoice());
        edit.setSecondHarmonizedTariffScheduleCodeUnitName(actionForm.getSecondHarmonizedTariffScheduleCodeUnitChoice());
        edit.setIsDefault(actionForm.getIsDefault().toString());
        edit.setSortOrder(actionForm.getSortOrder());
        edit.setDescription(actionForm.getDescription());
        edit.setOverviewMimeTypeName(actionForm.getOverviewMimeTypeChoice());
        edit.setOverview(actionForm.getOverview());

        return edit;
    }
    
    @Override
    protected EditHarmonizedTariffScheduleCodeForm getForm()
            throws NamingException {
        return ItemUtil.getHome().getEditHarmonizedTariffScheduleCodeForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, EditActionForm actionForm, EditHarmonizedTariffScheduleCodeResult result, HarmonizedTariffScheduleCodeSpec spec, HarmonizedTariffScheduleCodeEdit edit) {
        actionForm.setCountryName(spec.getCountryName());
        actionForm.setOriginalHarmonizedTariffScheduleCodeName(spec.getHarmonizedTariffScheduleCodeName());
        actionForm.setHarmonizedTariffScheduleCodeName(edit.getHarmonizedTariffScheduleCodeName());
        actionForm.setFirstHarmonizedTariffScheduleCodeUnitChoice(edit.getFirstHarmonizedTariffScheduleCodeUnitName());
        actionForm.setSecondHarmonizedTariffScheduleCodeUnitChoice(edit.getSecondHarmonizedTariffScheduleCodeUnitName());
        actionForm.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        actionForm.setSortOrder(edit.getSortOrder());
        actionForm.setDescription(edit.getDescription());
        actionForm.setOverviewMimeTypeChoice(edit.getOverviewMimeTypeName());
        actionForm.setOverview(edit.getOverview());
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditHarmonizedTariffScheduleCodeForm commandForm)
            throws Exception {
        return ItemUtil.getHome().editHarmonizedTariffScheduleCode(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(EditActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.COUNTRY_NAME, actionForm.getCountryName());
    }
    
    @Override
    protected void setupTransferForForm(HttpServletRequest request, EditActionForm actionForm, EditHarmonizedTariffScheduleCodeResult result) {
        request.setAttribute(AttributeConstants.HARMONIZED_TARIFF_SCHEDULE_CODE, result.getHarmonizedTariffScheduleCode());
    }

}
