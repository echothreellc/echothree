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

public class ContentCatalogHandler
        extends BaseHandler {
    ContentService contentService;
    String contentCollectionName;
    String contentCatalogName;
    
    /** Creates a new instance of ContentCatalogHandler */
    public ContentCatalogHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String contentCollectionName,
            String contentCatalogName) {
        super(initialDataParser, parentHandler);
        
        try {
            contentService = ContentUtil.getHome();
        } catch (NamingException ne) {
            // TODO: Handle Exception
        }
        
        this.contentCollectionName = contentCollectionName;
        this.contentCatalogName = contentCatalogName;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException {
        if(localName.equals("contentCatalogDescription")) {
            var commandForm = ContentFormFactory.getCreateContentCatalogDescriptionForm();
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
            
            commandForm.setContentCollectionName(contentCollectionName);
            commandForm.setContentCatalogName(contentCatalogName);
            commandForm.setLanguageIsoName(languageIsoName);
            commandForm.setDescription(description);
            
            contentService.createContentCatalogDescription(initialDataParser.getUserVisit(), commandForm);
        } else if(localName.equals("contentCategory")) {
            var commandForm = ContentFormFactory.getCreateContentCategoryForm();
            String contentCategoryName = null;
            String parentContentCategoryName = null;
            String defaultOfferName = null;
            String defaultUseName = null;
            String defaultSourceName = null;
            String isDefault = null;
            String sortOrder = null;
            String doUpdate = null;

            var count = attrs.getLength();
            for(var i = 0; i < count; i++) {
                if(attrs.getQName(i).equals("contentCategoryName"))
                    contentCategoryName = attrs.getValue(i);
                if(attrs.getQName(i).equals("parentContentCategoryName"))
                    parentContentCategoryName = attrs.getValue(i);
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
            
            commandForm.setContentCollectionName(contentCollectionName);
            commandForm.setContentCatalogName(contentCatalogName);
            commandForm.setContentCategoryName(contentCategoryName);
            commandForm.setParentContentCategoryName(parentContentCategoryName);
            commandForm.setDefaultOfferName(defaultOfferName);
            commandForm.setDefaultUseName(defaultUseName);
            commandForm.setDefaultSourceName(defaultSourceName);
            commandForm.setIsDefault(isDefault);
            commandForm.setSortOrder(sortOrder);
            
            contentService.createContentCategory(initialDataParser.getUserVisit(), commandForm);
            
            initialDataParser.pushHandler(new ContentCategoryHandler(initialDataParser, this, contentCollectionName,
                    contentCatalogName, contentCategoryName));
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("contentCatalog")) {
            initialDataParser.popHandler();
        }
    }
    
}
