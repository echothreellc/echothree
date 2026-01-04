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

package com.echothree.ui.cli.dataloader.util.data.handler.forum;

import com.echothree.control.user.forum.common.ForumUtil;
import com.echothree.control.user.forum.common.ForumService;
import com.echothree.control.user.forum.common.form.ForumFormFactory;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class ForumMessagePartTypeHandler
        extends BaseHandler {
    ForumService forumService;
    String forumMessagePartTypeName;
    
    /** Creates a new instance of ForumMessagePartTypeHandler */
    public ForumMessagePartTypeHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String forumMessagePartTypeName) {
        super(initialDataParser, parentHandler);
        
        try {
            forumService = ForumUtil.getHome();
        } catch (NamingException ne) {
            // TODO: Handle Exception
        }
        
        this.forumMessagePartTypeName = forumMessagePartTypeName;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
    throws SAXException {
        if(localName.equals("forumMessagePartTypeDescription")) {
            String languageIsoName = null;
            String description = null;

            var count = attrs.getLength();
            for(var i = 0; i < count; i++) {
                if(attrs.getQName(i).equals("languageIsoName"))
                    languageIsoName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("description"))
                    description = attrs.getValue(i);
            }
            
            try {
                var form = ForumFormFactory.getCreateForumMessagePartTypeDescriptionForm();
                
                form.setForumMessagePartTypeName(forumMessagePartTypeName);
                form.setLanguageIsoName(languageIsoName);
                form.setDescription(description);
                
                forumService.createForumMessagePartTypeDescription(initialDataParser.getUserVisit(), form);
            } catch (Exception e) {
                throw new SAXException(e);
            }
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
    throws SAXException {
        if(localName.equals("forumMessagePartType")) {
            initialDataParser.popHandler();
        }
    }
    
}
