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

package com.echothree.ui.cli.dataloader.data.handler.employee;

import com.echothree.control.user.employee.common.EmployeeUtil;
import com.echothree.control.user.employee.remote.EmployeeService;
import com.echothree.control.user.employee.remote.form.CreateResponsibilityTypeForm;
import com.echothree.control.user.employee.remote.form.EmployeeFormFactory;
import com.echothree.ui.cli.dataloader.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class ResponsibilityTypesHandler
        extends BaseHandler {
    EmployeeService employeeService;
    
    /** Creates a new instance of ResponsibilityTypesHandler */
    public ResponsibilityTypesHandler(InitialDataParser initialDataParser, BaseHandler parentHandler) {
        super(initialDataParser, parentHandler);
        
        try {
            employeeService = EmployeeUtil.getHome();
        } catch (NamingException ne) {
            // TODO: Handle Exception
        }
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
    throws SAXException {
        if(localName.equals("responsibilityType")) {
            String responsibilityTypeName = null;
            String isDefault = null;
            String sortOrder = null;
            
            int count = attrs.getLength();
            for(int i = 0; i < count; i++) {
                if(attrs.getQName(i).equals("responsibilityTypeName"))
                    responsibilityTypeName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("isDefault"))
                    isDefault = attrs.getValue(i);
                else if(attrs.getQName(i).equals("sortOrder"))
                    sortOrder = attrs.getValue(i);
            }
            
            try {
                CreateResponsibilityTypeForm form = EmployeeFormFactory.getCreateResponsibilityTypeForm();
                
                form.setResponsibilityTypeName(responsibilityTypeName);
                form.setIsDefault(isDefault);
                form.setSortOrder(sortOrder);
                
                employeeService.createResponsibilityType(initialDataParser.getUserVisit(), form);
                
                initialDataParser.pushHandler(new ResponsibilityTypeHandler(initialDataParser, this, responsibilityTypeName));
            } catch (Exception e) {
                throw new SAXException(e);
            }
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
    throws SAXException {
        if(localName.equals("responsibilityTypes")) {
            initialDataParser.popHandler();
        }
    }
    
}
