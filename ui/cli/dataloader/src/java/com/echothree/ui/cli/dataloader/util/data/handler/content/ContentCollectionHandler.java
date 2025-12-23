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

package com.echothree.ui.cli.dataloader.util.data.handler.content;

import com.echothree.control.user.content.common.ContentUtil;
import com.echothree.control.user.content.common.ContentService;
import com.echothree.control.user.content.common.form.ContentFormFactory;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class ContentCollectionHandler
        extends BaseHandler {
    ContentService contentService;
    String contentCollectionName;
    
    /** Creates a new instance of ContentCollectionHandler */
    public ContentCollectionHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String contentCollectionName) {
        super(initialDataParser, parentHandler);
        
        try {
            contentService = ContentUtil.getHome();
        } catch (NamingException ne) {
            // TODO: Handle Exception
        }
        
        this.contentCollectionName = contentCollectionName;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
    throws SAXException {
        if(localName.equals("contentCollectionDescription")) {
            String languageIsoName = null;
            String description = null;
            String doUpdate = null;

            var count = attrs.getLength();
            for(var i = 0; i < count; i++) {
                if(attrs.getQName(i).equals("languageIsoName"))
                    languageIsoName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("description"))
                    description = attrs.getValue(i);
                else if(attrs.getQName(i).equals("doUpdate"))
                    doUpdate = attrs.getValue(i);
            }
            
            try {
                var commandForm = ContentFormFactory.getCreateContentCollectionDescriptionForm();
                
                commandForm.setContentCollectionName(contentCollectionName);
                commandForm.setLanguageIsoName(languageIsoName);
                commandForm.setDescription(description);
                
                contentService.createContentCollectionDescription(initialDataParser.getUserVisit(), commandForm);
            } catch (Exception e) {
                throw new SAXException(e);
            }
        } else if(localName.equals("contentSection")) {
            String contentSectionName = null;
            String parentContentSectionName = null;
            String isDefault = null;
            String sortOrder = null;
            String doUpdate = null;

            var count = attrs.getLength();
            for(var i = 0; i < count; i++) {
                if(attrs.getQName(i).equals("contentSectionName"))
                    contentSectionName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("parentContentSectionName"))
                    parentContentSectionName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("isDefault"))
                    isDefault = attrs.getValue(i);
                else if(attrs.getQName(i).equals("sortOrder"))
                    sortOrder = attrs.getValue(i);
                else if(attrs.getQName(i).equals("doUpdate"))
                    doUpdate = attrs.getValue(i);
            }
            
            try {
                var commandForm = ContentFormFactory.getCreateContentSectionForm();
                
                commandForm.setContentCollectionName(contentCollectionName);
                commandForm.setContentSectionName(contentSectionName);
                commandForm.setParentContentSectionName(parentContentSectionName);
                commandForm.setIsDefault(isDefault);
                commandForm.setSortOrder(sortOrder);
                
                contentService.createContentSection(initialDataParser.getUserVisit(), commandForm);
                
                initialDataParser.pushHandler(new ContentSectionHandler(initialDataParser, this, contentCollectionName, contentSectionName));
            } catch (Exception e) {
                throw new SAXException(e);
            }
        } else if(localName.equals("contentCatalog")) {
            String contentCatalogName = null;
            String defaultOfferName = null;
            String defaultUseName = null;
            String defaultSourceName = null;
            String isDefault = null;
            String sortOrder = null;
            String doUpdate = null;

            var count = attrs.getLength();
            for(var i = 0; i < count; i++) {
                if(attrs.getQName(i).equals("contentCatalogName"))
                    contentCatalogName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("defaultOfferName"))
                    defaultOfferName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("defaultUseName"))
                    defaultUseName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("defaultSourceName"))
                    defaultSourceName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("isDefault"))
                    isDefault = attrs.getValue(i);
                else if(attrs.getQName(i).equals("sortOrder"))
                    sortOrder = attrs.getValue(i);
                else if(attrs.getQName(i).equals("doUpdate"))
                    doUpdate = attrs.getValue(i);
            }
            
            try {
                var commandForm = ContentFormFactory.getCreateContentCatalogForm();
                
                commandForm.setContentCollectionName(contentCollectionName);
                commandForm.setContentCatalogName(contentCatalogName);
                commandForm.setDefaultOfferName(defaultOfferName);
                commandForm.setDefaultUseName(defaultUseName);
                commandForm.setDefaultSourceName(defaultSourceName);
                commandForm.setIsDefault(isDefault);
                commandForm.setSortOrder(sortOrder);
                
                contentService.createContentCatalog(initialDataParser.getUserVisit(), commandForm);
                
                initialDataParser.pushHandler(new ContentCatalogHandler(initialDataParser, this, contentCollectionName, contentCatalogName));
            } catch (Exception e) {
                throw new SAXException(e);
            }
        } else if(localName.equals("contentForum")) {
            String forumName = null;
            String isDefault = null;

            var count = attrs.getLength();
            for(var i = 0; i < count; i++) {
                if(attrs.getQName(i).equals("forumName"))
                    forumName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("isDefault"))
                    isDefault = attrs.getValue(i);
            }
            
            try {
                var commandForm = ContentFormFactory.getCreateContentForumForm();
                
                commandForm.setContentCollectionName(contentCollectionName);
                commandForm.setForumName(forumName);
                commandForm.setIsDefault(isDefault);
                
                contentService.createContentForum(initialDataParser.getUserVisit(), commandForm);
            } catch (Exception e) {
                throw new SAXException(e);
            }
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
    throws SAXException {
        if(localName.equals("contentCollection")) {
            initialDataParser.popHandler();
        }
    }
    
}
