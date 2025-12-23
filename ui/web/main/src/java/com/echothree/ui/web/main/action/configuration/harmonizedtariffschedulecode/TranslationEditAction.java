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
import com.echothree.control.user.item.common.edit.HarmonizedTariffScheduleCodeTranslationEdit;
import com.echothree.control.user.item.common.form.EditHarmonizedTariffScheduleCodeTranslationForm;
import com.echothree.control.user.item.common.result.EditHarmonizedTariffScheduleCodeTranslationResult;
import com.echothree.control.user.item.common.spec.HarmonizedTariffScheduleCodeTranslationSpec;
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
    path = "/Configuration/HarmonizedTariffScheduleCode/TranslationEdit",
    mappingClass = SecureActionMapping.class,
    name = "HarmonizedTariffScheduleCodeTranslationEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Configuration/HarmonizedTariffScheduleCode/Translation", redirect = true),
        @SproutForward(name = "Form", path = "/configuration/harmonizedtariffschedulecode/translationEdit.jsp")
    }
)
public class TranslationEditAction
        extends MainBaseEditAction<TranslationEditActionForm, HarmonizedTariffScheduleCodeTranslationSpec, HarmonizedTariffScheduleCodeTranslationEdit, EditHarmonizedTariffScheduleCodeTranslationForm, EditHarmonizedTariffScheduleCodeTranslationResult> {
    
    @Override
    protected HarmonizedTariffScheduleCodeTranslationSpec getSpec(HttpServletRequest request, TranslationEditActionForm actionForm)
            throws NamingException {
        var spec = ItemUtil.getHome().getHarmonizedTariffScheduleCodeTranslationSpec();
        
        spec.setCountryName(findParameter(request, ParameterConstants.COUNTRY_NAME, actionForm.getCountryName()));
        spec.setHarmonizedTariffScheduleCodeName(findParameter(request, ParameterConstants.HARMONIZED_TARIFF_SCHEDULE_CODE_NAME, actionForm.getHarmonizedTariffScheduleCodeName()));
        spec.setLanguageIsoName(findParameter(request, ParameterConstants.LANGUAGE_ISO_NAME, actionForm.getLanguageIsoName()));
        
        return spec;
    }
    
    @Override
    protected HarmonizedTariffScheduleCodeTranslationEdit getEdit(HttpServletRequest request, TranslationEditActionForm actionForm)
            throws NamingException {
        var edit = ItemUtil.getHome().getHarmonizedTariffScheduleCodeTranslationEdit();

        edit.setDescription(actionForm.getDescription());
        edit.setOverviewMimeTypeName(actionForm.getOverviewMimeTypeChoice());
        edit.setOverview(actionForm.getOverview());

        return edit;
    }
    
    @Override
    protected EditHarmonizedTariffScheduleCodeTranslationForm getForm()
            throws NamingException {
        return ItemUtil.getHome().getEditHarmonizedTariffScheduleCodeTranslationForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, TranslationEditActionForm actionForm, EditHarmonizedTariffScheduleCodeTranslationResult result, HarmonizedTariffScheduleCodeTranslationSpec spec, HarmonizedTariffScheduleCodeTranslationEdit edit) {
        actionForm.setCountryName(spec.getCountryName());
        actionForm.setHarmonizedTariffScheduleCodeName(spec.getHarmonizedTariffScheduleCodeName());
        actionForm.setLanguageIsoName(spec.getLanguageIsoName());
        actionForm.setDescription(edit.getDescription());
        actionForm.setOverviewMimeTypeChoice(edit.getOverviewMimeTypeName());
        actionForm.setOverview(edit.getOverview());
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditHarmonizedTariffScheduleCodeTranslationForm commandForm)
            throws Exception {
        return ItemUtil.getHome().editHarmonizedTariffScheduleCodeTranslation(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(TranslationEditActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.COUNTRY_NAME, actionForm.getCountryName());
        parameters.put(ParameterConstants.HARMONIZED_TARIFF_SCHEDULE_CODE_NAME, actionForm.getHarmonizedTariffScheduleCodeName());
    }
    
    @Override
    protected void setupTransferForForm(HttpServletRequest request, TranslationEditActionForm actionForm, EditHarmonizedTariffScheduleCodeTranslationResult result) {
        request.setAttribute(AttributeConstants.HARMONIZED_TARIFF_SCHEDULE_CODE_TRANSLATION, result.getHarmonizedTariffScheduleCodeTranslation());
    }

}
