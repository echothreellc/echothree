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

package com.echothree.ui.cli.dataloader.util.data.handler.user;

import com.echothree.control.user.user.common.UserUtil;
import com.echothree.control.user.user.common.UserService;
import com.echothree.control.user.user.common.form.UserFormFactory;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class UserLoginPasswordEncoderTypesHandler
        extends BaseHandler {
    UserService userService;
    
    /** Creates a new instance of UserLoginPasswordEncoderTypesHandler */
    public UserLoginPasswordEncoderTypesHandler(InitialDataParser initialDataParser, BaseHandler parentHandler) {
        super(initialDataParser, parentHandler);
        
        try {
            userService = UserUtil.getHome();
        } catch (NamingException ne) {
            // TODO: Handle Exception
        }
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException {
        if(localName.equals("userLoginPasswordEncoderType")) {
            var commandForm = UserFormFactory.getCreateUserLoginPasswordEncoderTypeForm();
            String userLoginPasswordEncoderTypeName = null;

            var count = attrs.getLength();
            for(var i = 0; i < count; i++) {
                if(attrs.getQName(i).equals("userLoginPasswordEncoderTypeName"))
                    userLoginPasswordEncoderTypeName = attrs.getValue(i);
            }
            
            commandForm.setUserLoginPasswordEncoderTypeName(userLoginPasswordEncoderTypeName);
            
            userService.createUserLoginPasswordEncoderType(initialDataParser.getUserVisit(), commandForm);
            
            initialDataParser.pushHandler(new UserLoginPasswordEncoderTypeHandler(initialDataParser, this, userLoginPasswordEncoderTypeName));
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("userLoginPasswordEncoderTypes")) {
            initialDataParser.popHandler();
        }
    }
    
}
