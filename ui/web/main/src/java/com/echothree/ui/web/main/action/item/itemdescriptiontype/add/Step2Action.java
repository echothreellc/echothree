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

package com.echothree.ui.web.main.action.item.itemdescriptiontype.add;

import com.echothree.control.user.core.common.CoreUtil;
import com.echothree.control.user.core.common.result.GetMimeTypeUsageTypeResult;
import com.echothree.control.user.item.common.ItemUtil;
import com.echothree.model.control.core.common.MimeTypeUsageTypes;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.MainBaseAddAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.command.CommandResult;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

@SproutAction(
    path = "/Item/ItemDescriptionType/Add/Step2",
    mappingClass = SecureActionMapping.class,
    name = "ItemDescriptionTypeAddStep2",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Item/ItemDescriptionType/Main", redirect = true),
        @SproutForward(name = "Form", path = "/item/itemdescriptiontype/add/step2.jsp")
    }
)
public class Step2Action
        extends MainBaseAddAction<Step2ActionForm> {

    @Override
    public void setupParameters(Step2ActionForm actionForm, HttpServletRequest request) {
        actionForm.setMimeTypeUsageTypeName(findParameter(request, ParameterConstants.MIME_TYPE_USAGE_TYPE_NAME, actionForm.getMimeTypeUsageTypeName()));
    }

    @Override
    public void setupDefaults(Step2ActionForm actionForm)
            throws NamingException {
        actionForm.setSortOrder("1");
    }
    
    @Override
    public void setupTransfer(Step2ActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = CoreUtil.getHome().getGetMimeTypeUsageTypeForm();

        commandForm.setMimeTypeUsageTypeName(actionForm.getMimeTypeUsageTypeName());

        var commandResult = CoreUtil.getHome().getMimeTypeUsageType(getUserVisitPK(request), commandForm);
        if(!commandResult.hasErrors()) {
            var executionResult = commandResult.getExecutionResult();
            var result = (GetMimeTypeUsageTypeResult)executionResult.getResult();

            request.setAttribute(AttributeConstants.MIME_TYPE_USAGE_TYPE, result.getMimeTypeUsageType());
        }
    }

    @Override
    public CommandResult doAdd(Step2ActionForm actionForm, HttpServletRequest request)
            throws NamingException {
        var commandForm = ItemUtil.getHome().getCreateItemDescriptionTypeForm();
        var mimeTypeUsageTypeName = actionForm.getMimeTypeUsageTypeName();

        commandForm.setItemDescriptionTypeName(actionForm.getItemDescriptionTypeName());
        commandForm.setParentItemDescriptionTypeName(actionForm.getParentItemDescriptionTypeChoice());
        commandForm.setUseParentIfMissing(actionForm.getUseParentIfMissing().toString());
        commandForm.setMimeTypeUsageTypeName(mimeTypeUsageTypeName);
        commandForm.setCheckContentWebAddress(actionForm.getCheckContentWebAddress().toString());
        commandForm.setIncludeInIndex(actionForm.getIncludeInIndex().toString());
        commandForm.setIndexDefault(actionForm.getIndexDefault().toString());
        commandForm.setIsDefault(actionForm.getIsDefault().toString());
        commandForm.setSortOrder(actionForm.getSortOrder());
        commandForm.setDescription(actionForm.getDescription());

        if(mimeTypeUsageTypeName != null) {
            if(mimeTypeUsageTypeName.equals(MimeTypeUsageTypes.IMAGE.name())) {
                commandForm.setMinimumHeight(actionForm.getMinimumHeight());
                commandForm.setMinimumWidth(actionForm.getMinimumWidth());
                commandForm.setMaximumHeight(actionForm.getMaximumHeight());
                commandForm.setMaximumWidth(actionForm.getMaximumWidth());
                commandForm.setPreferredHeight(actionForm.getPreferredHeight());
                commandForm.setPreferredWidth(actionForm.getPreferredWidth());
                commandForm.setPreferredMimeTypeName(actionForm.getPreferredMimeTypeChoice());
                commandForm.setQuality(actionForm.getQuality());
                commandForm.setScaleFromParent(actionForm.getScaleFromParent().toString());
            }
        }

        return ItemUtil.getHome().createItemDescriptionType(getUserVisitPK(request), commandForm);
    }
    
}
