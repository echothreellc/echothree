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

package com.echothree.ui.web.main.action.item.harmonizedtariffschedulecodeunit;

import com.echothree.control.user.item.common.ItemUtil;
import com.echothree.control.user.item.common.edit.HarmonizedTariffScheduleCodeUnitDescriptionEdit;
import com.echothree.control.user.item.common.form.EditHarmonizedTariffScheduleCodeUnitDescriptionForm;
import com.echothree.control.user.item.common.result.EditHarmonizedTariffScheduleCodeUnitDescriptionResult;
import com.echothree.control.user.item.common.spec.HarmonizedTariffScheduleCodeUnitDescriptionSpec;
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
    path = "/Item/HarmonizedTariffScheduleCodeUnit/DescriptionEdit",
    mappingClass = SecureActionMapping.class,
    name = "HarmonizedTariffScheduleCodeUnitDescriptionEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Item/HarmonizedTariffScheduleCodeUnit/Description", redirect = true),
        @SproutForward(name = "Form", path = "/item/harmonizedtariffschedulecodeunit/descriptionEdit.jsp")
    }
)
public class DescriptionEditAction
        extends MainBaseEditAction<DescriptionEditActionForm, HarmonizedTariffScheduleCodeUnitDescriptionSpec, HarmonizedTariffScheduleCodeUnitDescriptionEdit, EditHarmonizedTariffScheduleCodeUnitDescriptionForm, EditHarmonizedTariffScheduleCodeUnitDescriptionResult> {
    
    @Override
    protected HarmonizedTariffScheduleCodeUnitDescriptionSpec getSpec(HttpServletRequest request, DescriptionEditActionForm actionForm)
            throws NamingException {
        var spec = ItemUtil.getHome().getHarmonizedTariffScheduleCodeUnitDescriptionSpec();
        
        spec.setHarmonizedTariffScheduleCodeUnitName(findParameter(request, ParameterConstants.HARMONIZED_TARIFF_SCHEDULE_CODE_UNIT_NAME, actionForm.getHarmonizedTariffScheduleCodeUnitName()));
        spec.setLanguageIsoName(findParameter(request, ParameterConstants.LANGUAGE_ISO_NAME, actionForm.getLanguageIsoName()));
        
        return spec;
    }
    
    @Override
    protected HarmonizedTariffScheduleCodeUnitDescriptionEdit getEdit(HttpServletRequest request, DescriptionEditActionForm actionForm)
            throws NamingException {
        var edit = ItemUtil.getHome().getHarmonizedTariffScheduleCodeUnitDescriptionEdit();

        edit.setDescription(actionForm.getDescription());

        return edit;
    }
    
    @Override
    protected EditHarmonizedTariffScheduleCodeUnitDescriptionForm getForm()
            throws NamingException {
        return ItemUtil.getHome().getEditHarmonizedTariffScheduleCodeUnitDescriptionForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, DescriptionEditActionForm actionForm, EditHarmonizedTariffScheduleCodeUnitDescriptionResult result, HarmonizedTariffScheduleCodeUnitDescriptionSpec spec, HarmonizedTariffScheduleCodeUnitDescriptionEdit edit) {
        actionForm.setHarmonizedTariffScheduleCodeUnitName(spec.getHarmonizedTariffScheduleCodeUnitName());
        actionForm.setLanguageIsoName(spec.getLanguageIsoName());
        actionForm.setDescription(edit.getDescription());
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditHarmonizedTariffScheduleCodeUnitDescriptionForm commandForm)
            throws Exception {
        return ItemUtil.getHome().editHarmonizedTariffScheduleCodeUnitDescription(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(DescriptionEditActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.HARMONIZED_TARIFF_SCHEDULE_CODE_UNIT_NAME, actionForm.getHarmonizedTariffScheduleCodeUnitName());
    }
    
    @Override
    protected void setupTransferForForm(HttpServletRequest request, DescriptionEditActionForm actionForm, EditHarmonizedTariffScheduleCodeUnitDescriptionResult result) {
        request.setAttribute(AttributeConstants.HARMONIZED_TARIFF_SCHEDULE_CODE_UNIT_DESCRIPTION, result.getHarmonizedTariffScheduleCodeUnitDescription());
    }

}
