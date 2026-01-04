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

public class EntityAttributeHandler
        extends BaseHandler {
    
    CoreService coreService;
    String componentVendorName;
    String entityTypeName;
    String entityAttributeName;
    
    /** Creates a new instance of EntityAttributeHandler */
    public EntityAttributeHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String componentVendorName, String entityTypeName,
            String entityAttributeName)
            throws SAXException {
        super(initialDataParser, parentHandler);
        
        try {
            coreService = CoreUtil.getHome();
        } catch (NamingException ne) {
            throw new SAXException(ne);
        }
        
        this.componentVendorName = componentVendorName;
        this.entityTypeName = entityTypeName;
        this.entityAttributeName = entityAttributeName;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException {
        if(localName.equals("entityAttributeDescription")) {
            var commandForm = CoreFormFactory.getCreateEntityAttributeDescriptionForm();

            commandForm.setComponentVendorName(componentVendorName);
            commandForm.setEntityTypeName(entityTypeName);
            commandForm.setEntityAttributeName(entityAttributeName);
            commandForm.set(getAttrsMap(attrs));

            coreService.createEntityAttributeDescription(initialDataParser.getUserVisit(), commandForm);
        } if(localName.equals("entityDateDefault")) {
            var commandForm = CoreFormFactory.getCreateEntityDateDefaultForm();

            commandForm.setComponentVendorName(componentVendorName);
            commandForm.setEntityTypeName(entityTypeName);
            commandForm.setEntityAttributeName(entityAttributeName);
            commandForm.set(getAttrsMap(attrs));

            coreService.createEntityDateDefault(initialDataParser.getUserVisit(), commandForm);
        }  if(localName.equals("entityBooleanDefault")) {
            var commandForm = CoreFormFactory.getCreateEntityBooleanDefaultForm();

            commandForm.setComponentVendorName(componentVendorName);
            commandForm.setEntityTypeName(entityTypeName);
            commandForm.setEntityAttributeName(entityAttributeName);
            commandForm.set(getAttrsMap(attrs));

            coreService.createEntityBooleanDefault(initialDataParser.getUserVisit(), commandForm);
        } if(localName.equals("entityGeoPointDefault")) {
            var commandForm = CoreFormFactory.getCreateEntityGeoPointDefaultForm();

            commandForm.setComponentVendorName(componentVendorName);
            commandForm.setEntityTypeName(entityTypeName);
            commandForm.setEntityAttributeName(entityAttributeName);
            commandForm.set(getAttrsMap(attrs));

            coreService.createEntityGeoPointDefault(initialDataParser.getUserVisit(), commandForm);
        } if(localName.equals("entityIntegerDefault")) {
            var commandForm = CoreFormFactory.getCreateEntityIntegerDefaultForm();

            commandForm.setComponentVendorName(componentVendorName);
            commandForm.setEntityTypeName(entityTypeName);
            commandForm.setEntityAttributeName(entityAttributeName);
            commandForm.set(getAttrsMap(attrs));

            coreService.createEntityIntegerDefault(initialDataParser.getUserVisit(), commandForm);
        } if(localName.equals("entityListItemDefault")) {
            var commandForm = CoreFormFactory.getCreateEntityListItemDefaultForm();

            commandForm.setComponentVendorName(componentVendorName);
            commandForm.setEntityTypeName(entityTypeName);
            commandForm.setEntityAttributeName(entityAttributeName);
            commandForm.set(getAttrsMap(attrs));

            coreService.createEntityListItemDefault(initialDataParser.getUserVisit(), commandForm);
        } if(localName.equals("entityLongDefault")) {
            var commandForm = CoreFormFactory.getCreateEntityLongDefaultForm();

            commandForm.setComponentVendorName(componentVendorName);
            commandForm.setEntityTypeName(entityTypeName);
            commandForm.setEntityAttributeName(entityAttributeName);
            commandForm.set(getAttrsMap(attrs));

            coreService.createEntityLongDefault(initialDataParser.getUserVisit(), commandForm);
        } if(localName.equals("entityMultipleListItemDefault")) {
            var commandForm = CoreFormFactory.getCreateEntityMultipleListItemDefaultForm();

            commandForm.setComponentVendorName(componentVendorName);
            commandForm.setEntityTypeName(entityTypeName);
            commandForm.setEntityAttributeName(entityAttributeName);
            commandForm.set(getAttrsMap(attrs));

            coreService.createEntityMultipleListItemDefault(initialDataParser.getUserVisit(), commandForm);
        } if(localName.equals("entityStringDefault")) {
            var commandForm = CoreFormFactory.getCreateEntityStringDefaultForm();

            commandForm.setComponentVendorName(componentVendorName);
            commandForm.setEntityTypeName(entityTypeName);
            commandForm.setEntityAttributeName(entityAttributeName);
            commandForm.set(getAttrsMap(attrs));

            coreService.createEntityStringDefault(initialDataParser.getUserVisit(), commandForm);
        } if(localName.equals("entityTimeDefault")) {
            var commandForm = CoreFormFactory.getCreateEntityTimeDefaultForm();

            commandForm.setComponentVendorName(componentVendorName);
            commandForm.setEntityTypeName(entityTypeName);
            commandForm.setEntityAttributeName(entityAttributeName);
            commandForm.set(getAttrsMap(attrs));

            coreService.createEntityTimeDefault(initialDataParser.getUserVisit(), commandForm);
        } if(localName.equals("entityAttributeEntityAttributeGroup")) {
            var commandForm = CoreFormFactory.getCreateEntityAttributeEntityAttributeGroupForm();

            commandForm.setComponentVendorName(componentVendorName);
            commandForm.setEntityTypeName(entityTypeName);
            commandForm.setEntityAttributeName(entityAttributeName);
            commandForm.set(getAttrsMap(attrs));

            coreService.createEntityAttributeEntityAttributeGroup(initialDataParser.getUserVisit(), commandForm);
        } else if(localName.equals("entityListItem")) {
            var commandForm = CoreFormFactory.getCreateEntityListItemForm();

            commandForm.setComponentVendorName(componentVendorName);
            commandForm.setEntityTypeName(entityTypeName);
            commandForm.setEntityAttributeName(entityAttributeName);
            commandForm.set(getAttrsMap(attrs));

            coreService.createEntityListItem(initialDataParser.getUserVisit(), commandForm);

            initialDataParser.pushHandler(new EntityListItemHandler(initialDataParser, this, componentVendorName, entityTypeName, entityAttributeName,
                    commandForm.getEntityListItemName()));
        } else if(localName.equals("entityIntegerRange")) {
            var commandForm = CoreFormFactory.getCreateEntityIntegerRangeForm();

            commandForm.setComponentVendorName(componentVendorName);
            commandForm.setEntityTypeName(entityTypeName);
            commandForm.setEntityAttributeName(entityAttributeName);
            commandForm.set(getAttrsMap(attrs));

            coreService.createEntityIntegerRange(initialDataParser.getUserVisit(), commandForm);

            initialDataParser.pushHandler(new EntityIntegerRangeHandler(initialDataParser, this, componentVendorName, entityTypeName, entityAttributeName,
                    commandForm.getEntityIntegerRangeName()));
        } else if(localName.equals("entityLongRange")) {
            var commandForm = CoreFormFactory.getCreateEntityLongRangeForm();

            commandForm.setComponentVendorName(componentVendorName);
            commandForm.setEntityTypeName(entityTypeName);
            commandForm.setEntityAttributeName(entityAttributeName);
            commandForm.set(getAttrsMap(attrs));

            coreService.createEntityLongRange(initialDataParser.getUserVisit(), commandForm);

            initialDataParser.pushHandler(new EntityLongRangeHandler(initialDataParser, this, componentVendorName, entityTypeName, entityAttributeName,
                    commandForm.getEntityLongRangeName()));
        } else if(localName.equals("entityAttributeEntityType")) {
            var commandForm = CoreFormFactory.getCreateEntityAttributeEntityTypeForm();

            commandForm.setComponentVendorName(componentVendorName);
            commandForm.setEntityTypeName(entityTypeName);
            commandForm.setEntityAttributeName(entityAttributeName);
            commandForm.set(getAttrsMap(attrs));

            coreService.createEntityAttributeEntityType(initialDataParser.getUserVisit(), commandForm);
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("entityAttribute")) {
            initialDataParser.popHandler();
        }
    }
    
}
