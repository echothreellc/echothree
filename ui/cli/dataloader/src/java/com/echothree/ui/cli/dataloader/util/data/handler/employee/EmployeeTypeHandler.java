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

package com.echothree.ui.cli.dataloader.util.data.handler.employee;

import com.echothree.control.user.employee.common.EmployeeUtil;
import com.echothree.control.user.employee.common.EmployeeService;
import com.echothree.control.user.employee.common.form.EmployeeFormFactory;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class EmployeeTypeHandler
        extends BaseHandler {
    EmployeeService employeeService;
    String employeeTypeName;
    
    /** Creates a new instance of EmployeeTypeHandler */
    public EmployeeTypeHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String employeeTypeName)
            throws SAXException {
        super(initialDataParser, parentHandler);
        
        try {
            employeeService = EmployeeUtil.getHome();
        } catch (NamingException ne) {
            throw new SAXException(ne);
        }
        
        this.employeeTypeName = employeeTypeName;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException {
        if(localName.equals("employeeTypeDescription")) {
            var commandForm = EmployeeFormFactory.getCreateEmployeeTypeDescriptionForm();
            
            commandForm.setEmployeeTypeName(employeeTypeName);
            commandForm.set(getAttrsMap(attrs));
            
            employeeService.createEmployeeTypeDescription(initialDataParser.getUserVisit(), commandForm);
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("employeeType")) {
            initialDataParser.popHandler();
        }
    }
    
}
