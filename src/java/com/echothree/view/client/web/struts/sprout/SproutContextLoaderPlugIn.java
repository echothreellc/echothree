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

package com.echothree.view.client.web.struts.sprout;

import javax.servlet.ServletException;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.action.PlugIn;
import org.apache.struts.config.ModuleConfig;

public class SproutContextLoaderPlugIn
        implements PlugIn {

    private ActionServlet actionServlet;
    private ModuleConfig moduleConfig;

    @Override
    public final void init(final ActionServlet actionServlet, final ModuleConfig moduleConfig)
            throws ServletException {
        this.actionServlet = actionServlet;
        this.moduleConfig = moduleConfig;

        onInit();
    }

    @Override
    public void destroy() {
        onDestroy();
    }

    public final ModuleConfig getModuleConfig() {
        return this.moduleConfig;
    }

    public final ActionServlet getActionServlet() {
        return this.actionServlet;
    }

    protected void onInit()
            throws ServletException {
    }

    protected void onDestroy() {
    }

}
