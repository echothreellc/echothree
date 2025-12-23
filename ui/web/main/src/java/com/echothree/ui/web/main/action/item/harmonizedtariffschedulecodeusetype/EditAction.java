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

package com.echothree.ui.web.main.action.item.harmonizedtariffschedulecodeusetype;

import com.echothree.control.user.item.common.ItemUtil;
import com.echothree.control.user.item.common.edit.HarmonizedTariffScheduleCodeUseTypeEdit;
import com.echothree.control.user.item.common.form.EditHarmonizedTariffScheduleCodeUseTypeForm;
import com.echothree.control.user.item.common.result.EditHarmonizedTariffScheduleCodeUseTypeResult;
import com.echothree.control.user.item.common.spec.HarmonizedTariffScheduleCodeUseTypeSpec;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.MainBaseEditAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.command.CommandResult;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

@SproutAction(
    path = "/Item/HarmonizedTariffScheduleCodeUseType/Edit",
    mappingClass = SecureActionMapping.class,
    name = "HarmonizedTariffScheduleCodeUseTypeEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Item/HarmonizedTariffScheduleCodeUseType/Main", redirect = true),
        @SproutForward(name = "Form", path = "/item/harmonizedtariffschedulecodeusetype/edit.jsp")
    }
)
public class EditAction
        extends MainBaseEditAction<EditActionForm, HarmonizedTariffScheduleCodeUseTypeSpec, HarmonizedTariffScheduleCodeUseTypeEdit, EditHarmonizedTariffScheduleCodeUseTypeForm, EditHarmonizedTariffScheduleCodeUseTypeResult> {
    
    @Override
    protected HarmonizedTariffScheduleCodeUseTypeSpec getSpec(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var spec = ItemUtil.getHome().getHarmonizedTariffScheduleCodeUseTypeSpec();
        
        spec.setHarmonizedTariffScheduleCodeUseTypeName(findParameter(request, ParameterConstants.ORIGINAL_HARMONIZED_TARIFF_SCHEDULE_CODE_USE_TYPE_NAME, actionForm.getOriginalHarmonizedTariffScheduleCodeUseTypeName()));
        
        return spec;
    }
    
    @Override
    protected HarmonizedTariffScheduleCodeUseTypeEdit getEdit(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var edit = ItemUtil.getHome().getHarmonizedTariffScheduleCodeUseTypeEdit();

        edit.setHarmonizedTariffScheduleCodeUseTypeName(actionForm.getHarmonizedTariffScheduleCodeUseTypeName());
        edit.setIsDefault(actionForm.getIsDefault().toString());
        edit.setSortOrder(actionForm.getSortOrder());
        edit.setDescription(actionForm.getDescription());

        return edit;
    }
    
    @Override
    protected EditHarmonizedTariffScheduleCodeUseTypeForm getForm()
            throws NamingException {
        return ItemUtil.getHome().getEditHarmonizedTariffScheduleCodeUseTypeForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, EditActionForm actionForm, EditHarmonizedTariffScheduleCodeUseTypeResult result, HarmonizedTariffScheduleCodeUseTypeSpec spec, HarmonizedTariffScheduleCodeUseTypeEdit edit) {
        actionForm.setOriginalHarmonizedTariffScheduleCodeUseTypeName(spec.getHarmonizedTariffScheduleCodeUseTypeName());
        actionForm.setHarmonizedTariffScheduleCodeUseTypeName(edit.getHarmonizedTariffScheduleCodeUseTypeName());
        actionForm.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        actionForm.setSortOrder(edit.getSortOrder());
        actionForm.setDescription(edit.getDescription());
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditHarmonizedTariffScheduleCodeUseTypeForm commandForm)
            throws Exception {
        return ItemUtil.getHome().editHarmonizedTariffScheduleCodeUseType(getUserVisitPK(request), commandForm);
    }
    
    @Override
    protected void setupTransferForForm(HttpServletRequest request, EditActionForm actionForm, EditHarmonizedTariffScheduleCodeUseTypeResult result) {
        request.setAttribute(AttributeConstants.HARMONIZED_TARIFF_SCHEDULE_CODE_USE_TYPE, result.getHarmonizedTariffScheduleCodeUseType());
    }

}
