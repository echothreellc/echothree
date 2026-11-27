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

import com.echothree.control.user.comment.common.CommentService;
import com.echothree.control.user.comment.common.CommentUtil;
import com.echothree.control.user.comment.common.form.CommentFormFactory;
import com.echothree.control.user.core.common.CoreService;
import com.echothree.control.user.core.common.CoreUtil;
import com.echothree.control.user.core.common.form.CoreFormFactory;
import com.echothree.control.user.core.common.spec.CoreSpecFactory;
import com.echothree.control.user.message.common.MessageService;
import com.echothree.control.user.message.common.MessageUtil;
import com.echothree.control.user.message.common.form.MessageFormFactory;
import com.echothree.control.user.rating.common.RatingService;
import com.echothree.control.user.rating.common.RatingUtil;
import com.echothree.control.user.rating.common.form.RatingFormFactory;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.comment.CommentTypeHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.message.MessageTypeHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.rating.RatingTypeHandler;
import com.echothree.util.common.message.ExecutionErrors;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class EntityTypeHandler
        extends BaseHandler {

    CommentService commentService;
    CoreService coreService;
    MessageService messageService;
    RatingService ratingService;
    String componentVendorName;
    String entityTypeName;
    
    /** Creates a new instance of EntityTypeHandler */
    public EntityTypeHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String componentVendorName, String entityTypeName)
    throws SAXException {
        super(initialDataParser, parentHandler);
        
        try {
            commentService = CommentUtil.getHome();
            coreService = CoreUtil.getHome();
            messageService = MessageUtil.getHome();
            ratingService = RatingUtil.getHome();
        } catch (NamingException ne) {
            throw new SAXException(ne);
        }
        
        this.componentVendorName = componentVendorName;
        this.entityTypeName = entityTypeName;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
    throws SAXException {
        switch(localName) {
            case "entityTypeDescription" -> {
                var attrsMap = getAttrsMap(attrs);
                attrsMap.putIfAbsent("ComponentVendorName", componentVendorName);
                attrsMap.putIfAbsent("EntityTypeName", entityTypeName);

                if(shouldEdit(attrsMap)) {
                    editOrCreate(attrsMap, CoreSpecFactory.getEntityTypeDescriptionSpec(),
                            CoreFormFactory.getEditEntityTypeDescriptionForm(), coreService::editEntityTypeDescription,
                            CoreFormFactory.getCreateEntityTypeDescriptionForm(), coreService::createEntityTypeDescription,
                            ExecutionErrors.UnknownEntityTypeDescription);
                }
            }
            case "entityAliasType" -> {
                var commandForm = CoreFormFactory.getCreateEntityAliasTypeForm();

                commandForm.setComponentVendorName(componentVendorName);
                commandForm.setEntityTypeName(entityTypeName);
                commandForm.set(getAttrsMap(attrs));

                checkCommandResult(commandForm, coreService.createEntityAliasType(initialDataParser.getUserVisit(), commandForm));

                initialDataParser.pushHandler(new EntityAliasTypeHandler(initialDataParser, this, componentVendorName, entityTypeName,
                        commandForm.getEntityAliasTypeName()));
            }
            case "entityAttributeGroup" -> {
                var commandForm = CoreFormFactory.getCreateEntityAttributeGroupForm();

                commandForm.setComponentVendorName(componentVendorName);
                commandForm.setEntityTypeName(entityTypeName);
                commandForm.set(getAttrsMap(attrs));

                checkCommandResult(commandForm, coreService.createEntityAttributeGroup(initialDataParser.getUserVisit(), commandForm));

                initialDataParser.pushHandler(new EntityAttributeGroupHandler(initialDataParser, this, componentVendorName, entityTypeName,
                        commandForm.getEntityAttributeGroupName()));
            }
            case "entityAttribute" -> {
                var commandForm = CoreFormFactory.getCreateEntityAttributeForm();

                commandForm.setComponentVendorName(componentVendorName);
                commandForm.setEntityTypeName(entityTypeName);
                commandForm.set(getAttrsMap(attrs));

                checkCommandResult(commandForm, coreService.createEntityAttribute(initialDataParser.getUserVisit(), commandForm));

                initialDataParser.pushHandler(new EntityAttributeHandler(initialDataParser, this, componentVendorName, entityTypeName,
                        commandForm.getEntityAttributeName()));
            }
            case "commentType" -> {
                var commandForm = CommentFormFactory.getCreateCommentTypeForm();

                commandForm.setComponentVendorName(componentVendorName);
                commandForm.setEntityTypeName(entityTypeName);
                commandForm.set(getAttrsMap(attrs));

                checkCommandResult(commandForm, commentService.createCommentType(initialDataParser.getUserVisit(), commandForm));

                initialDataParser.pushHandler(new CommentTypeHandler(initialDataParser, this, componentVendorName, entityTypeName,
                        commandForm.getCommentTypeName()));
            }
            case "ratingType" -> {
                var commandForm = RatingFormFactory.getCreateRatingTypeForm();

                commandForm.setComponentVendorName(componentVendorName);
                commandForm.setEntityTypeName(entityTypeName);
                commandForm.set(getAttrsMap(attrs));

                checkCommandResult(commandForm, ratingService.createRatingType(initialDataParser.getUserVisit(), commandForm));

                initialDataParser.pushHandler(new RatingTypeHandler(initialDataParser, this, componentVendorName, entityTypeName,
                        commandForm.getRatingTypeName()));
            }
            case "messageType" -> {
                var commandForm = MessageFormFactory.getCreateMessageTypeForm();

                commandForm.setComponentVendorName(componentVendorName);
                commandForm.setEntityTypeName(entityTypeName);
                commandForm.set(getAttrsMap(attrs));

                checkCommandResult(commandForm, messageService.createMessageType(initialDataParser.getUserVisit(), commandForm));

                initialDataParser.pushHandler(new MessageTypeHandler(initialDataParser, this, componentVendorName, entityTypeName,
                        commandForm.getMessageTypeName()));
            }
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
    throws SAXException {
        if(localName.equals("entityType")) {
            initialDataParser.popHandler();
        }
    }
    
}
