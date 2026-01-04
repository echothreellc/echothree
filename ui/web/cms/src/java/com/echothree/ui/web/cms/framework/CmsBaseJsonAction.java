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

package com.echothree.ui.web.cms.framework;

import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.transfer.BaseWrapper;
import com.echothree.util.common.transfer.MapWrapper;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.filters.CompositePropertyFilter;
import net.sf.json.util.CycleDetectionStrategy;
import net.sf.json.util.PropertyFilter;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public abstract class CmsBaseJsonAction
        extends CmsBaseDownloadAction {

    /** Creates a new instance of CmsBaseJsonAction */
    public CmsBaseJsonAction(boolean applicationRequired, boolean partyRequired) {
        super(applicationRequired, partyRequired);
    }

    protected abstract CommandResult getCommandResult(HttpServletRequest request)
            throws Exception;

    protected List<PropertyFilter> getPropertyFilters() {
        List<PropertyFilter> propertyFilters = new ArrayList<>();
        
        // Eliminate nulls that're found by the CycleDetectionStrategy.
        propertyFilters.add((PropertyFilter) (Object source, String name, Object value) -> {
            if(value == null) {
                return true;
            }
            
            return false;
        });
        
        // For ListWrapper, do not include the Collection view. For MapWrapper, do not
        // include either the Collection or the List views.
        propertyFilters.add((PropertyFilter) (Object source, String name, Object value) -> {
            if(source instanceof BaseWrapper) {
                if(name.equals("collection") || (source instanceof MapWrapper && name.equals("list"))) {
                    return true;
                }
            }
            
            return false;
        });
        
        return propertyFilters;
    }
    
    @Override
    protected StreamInfo getStreamInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        var jsonConfig = new JsonConfig();
        
        jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.NOPROP);
        jsonConfig.setJsonPropertyFilter(new CompositePropertyFilter(getPropertyFilters()));

        var commandResult = getCommandResult(request);
        StreamInfo streamInfo = new ByteArrayStreamInfo("application/json;charset=UTF-8",
                new ByteArrayInputStream(JSONObject.fromObject(commandResult, jsonConfig).toString().getBytes(StandardCharsets.UTF_8)));
        
        return streamInfo;
    }
    
}
