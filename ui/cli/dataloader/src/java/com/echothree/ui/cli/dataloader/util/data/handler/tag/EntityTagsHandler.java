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

package com.echothree.ui.cli.dataloader.util.data.handler.tag;

import com.echothree.control.user.tag.common.TagUtil;
import com.echothree.control.user.tag.common.TagService;
import com.echothree.control.user.tag.common.form.TagFormFactory;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class EntityTagsHandler
        extends BaseHandler {
    TagService tagService;
    String entityRef;
    
    /** Creates a new instance of EntityTagsHandler */
    public EntityTagsHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String entityRef) {
        super(initialDataParser, parentHandler);
        
        try {
            tagService = TagUtil.getHome();
        } catch (NamingException ne) {
            // TODO: Handle Exception
        }
        
        this.entityRef = entityRef;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException {
        if(localName.equals("entityTag")) {
            var commandForm = TagFormFactory.getCreateEntityTagForm();
            
            commandForm.setEntityRef(entityRef);
            commandForm.set(getAttrsMap(attrs));
            
            tagService.createEntityTag(initialDataParser.getUserVisit(), commandForm);
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("entityTags")) {
            initialDataParser.popHandler();
        }
    }
    
}
