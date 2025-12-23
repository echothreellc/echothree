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

package com.echothree.ui.cli.dataloader.util.data.handler.core;

import com.echothree.control.user.core.common.CoreUtil;
import com.echothree.control.user.core.common.CoreService;
import com.echothree.control.user.core.common.form.CoreFormFactory;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class ComponentHandler
        extends BaseHandler {
    CoreService coreService;
    String componentVendorName;
    String componentName;
    
    /** Creates a new instance of ComponentHandler */
    public ComponentHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String componentVendorName, String componentName) {
        super(initialDataParser, parentHandler);
        
        try {
            coreService = CoreUtil.getHome();
        } catch (NamingException ne) {
            // TODO: Handle Exception
        }
        
        this.componentVendorName = componentVendorName;
        this.componentName = componentName;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
    throws SAXException {
        if(localName.equals("componentVersion")) {
            String majorRevision = null;
            String minorRevision = null;
            String componentStageName = null;
            String buildNumber = null;

            var count = attrs.getLength();
            for(var i = 0; i < count; i++) {
                if(attrs.getQName(i).equals("majorRevision"))
                    majorRevision = attrs.getValue(i);
                else if(attrs.getQName(i).equals("minorRevision"))
                    minorRevision = attrs.getValue(i);
                else if(attrs.getQName(i).equals("componentStageName"))
                    componentStageName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("buildNumber"))
                    buildNumber = attrs.getValue(i);
            }
            
            try {
                var createComponentVersionForm = CoreFormFactory.getCreateComponentVersionForm();
                
                createComponentVersionForm.setComponentVendorName(componentVendorName);
                createComponentVersionForm.setComponentName(componentName);
                createComponentVersionForm.setMajorRevision(majorRevision);
                createComponentVersionForm.setMinorRevision(minorRevision);
                createComponentVersionForm.setComponentStageName(componentStageName);
                createComponentVersionForm.setBuildNumber(buildNumber);
                
                coreService.createComponentVersion(initialDataParser.getUserVisit(), createComponentVersionForm);
            } catch (Exception e) {
                throw new SAXException(e);
            }
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
    throws SAXException {
        if(localName.equals("component")) {
            initialDataParser.popHandler();
        }
    }
    
}
