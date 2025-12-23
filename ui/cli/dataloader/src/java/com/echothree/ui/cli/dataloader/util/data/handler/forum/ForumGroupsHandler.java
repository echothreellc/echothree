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

public class ForumGroupsHandler
        extends BaseHandler {
    ForumService forumService;
    
    /** Creates a new instance of ForumGroupsHandler */
    public ForumGroupsHandler(InitialDataParser initialDataParser, BaseHandler parentHandler) {
        super(initialDataParser, parentHandler);
        
        try {
            forumService = ForumUtil.getHome();
        } catch (NamingException ne) {
            // TODO: Handle Exception
        }
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException {
        if(localName.equals("forumGroup")) {
            var form = ForumFormFactory.getCreateForumGroupForm();
            String forumGroupName = null;
            String iconName = null;
            String sortOrder = null;

            var count = attrs.getLength();
            for(var i = 0; i < count; i++) {
                if(attrs.getQName(i).equals("forumGroupName"))
                    forumGroupName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("iconName"))
                    iconName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("sortOrder"))
                    sortOrder = attrs.getValue(i);
            }
            
            form.setForumGroupName(forumGroupName);
            form.setIconName(iconName);
            form.setSortOrder(sortOrder);
            
            forumService.createForumGroup(initialDataParser.getUserVisit(), form);
            
            initialDataParser.pushHandler(new ForumGroupHandler(initialDataParser, this, forumGroupName));
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("forumGroups")) {
            initialDataParser.popHandler();
        }
    }
    
}
