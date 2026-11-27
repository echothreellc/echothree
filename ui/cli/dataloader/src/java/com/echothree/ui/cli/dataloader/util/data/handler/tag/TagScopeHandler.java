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
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class TagScopeHandler
        extends BaseHandler {

    TagService tagService;
    String tagScopeName;
    
    /** Creates a new instance of TagScopeHandler */
    public TagScopeHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String tagScopeName)
            throws SAXException {
        super(initialDataParser, parentHandler);
        
        tagService = lookupService(TagService.class, TagUtil::getHome);
        
        this.tagScopeName = tagScopeName;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
    throws SAXException {
        if(localName.equals("tagScopeDescription")) {
            var commandForm = TagFormFactory.getCreateTagScopeDescriptionForm();

            commandForm.setTagScopeName(tagScopeName);
            commandForm.set(getAttrsMap(attrs));

            checkCommandResult(commandForm, tagService.createTagScopeDescription(initialDataParser.getUserVisit(), commandForm));
        } else if(localName.equals("tagScopeEntityType")) {
            var commandForm = TagFormFactory.getCreateTagScopeEntityTypeForm();

            commandForm.setTagScopeName(tagScopeName);
            commandForm.set(getAttrsMap(attrs));

            checkCommandResult(commandForm, tagService.createTagScopeEntityType(initialDataParser.getUserVisit(), commandForm));
        } else if(localName.equals("tag")) {
            var commandForm = TagFormFactory.getCreateTagForm();

            commandForm.setTagScopeName(tagScopeName);
            commandForm.set(getAttrsMap(attrs));

            checkCommandResult(commandForm, tagService.createTag(initialDataParser.getUserVisit(), commandForm));
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
    throws SAXException {
        if(localName.equals("tagScope")) {
            initialDataParser.popHandler();
        }
    }
    
}
