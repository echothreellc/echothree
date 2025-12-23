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
import com.echothree.control.user.core.common.result.EditEntityTypeDescriptionResult;
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
import com.echothree.util.common.command.EditMode;
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
                var spec = CoreSpecFactory.getEntityTypeDescriptionSpec();
                var editForm = CoreFormFactory.getEditEntityTypeDescriptionForm();

                spec.setComponentVendorName(componentVendorName);
                spec.setEntityTypeName(entityTypeName);
                spec.set(getAttrsMap(attrs));

                var commandAction = getCommandAction(spec);
                getLogger().debug("Found: {}", commandAction);
                if(commandAction == null || commandAction.equals("create")) {
                    var attrsMap = getAttrsMap(attrs);

                    editForm.setSpec(spec);
                    editForm.setEditMode(EditMode.LOCK);

                    var commandResult = coreService.editEntityTypeDescription(initialDataParser.getUserVisit(), editForm);

                    if(commandResult.hasErrors()) {
                        if(commandResult.containsExecutionError(ExecutionErrors.UnknownEntityTypeDescription.name())) {
                            var createForm = CoreFormFactory.getCreateEntityTypeDescriptionForm();

                            createForm.set(spec.get());

                            getLogger().debug("Creating: {}", spec.getEntityTypeName());
                            commandResult = coreService.createEntityTypeDescription(initialDataParser.getUserVisit(), createForm);

                            if(commandResult.hasErrors()) {
                                getLogger().error(commandResult.toString());
                            }
                        } else {
                            getLogger().error(commandResult.toString());
                        }
                    } else {
                        var executionResult = commandResult.getExecutionResult();
                        var result = (EditEntityTypeDescriptionResult)executionResult.getResult();

                        getLogger().debug("Checking for modifications: {}", spec.getEntityTypeName());
                        if(result != null) {
                            updateEditFormValues(editForm, attrsMap, result);

                            commandResult = coreService.editEntityTypeDescription(initialDataParser.getUserVisit(), editForm);
                            if(commandResult.hasErrors()) {
                                getLogger().error(commandResult.toString());
                            }
                        }
                    }
                }
            }
            case "entityAliasType" -> {
                var commandForm = CoreFormFactory.getCreateEntityAliasTypeForm();

                commandForm.setComponentVendorName(componentVendorName);
                commandForm.setEntityTypeName(entityTypeName);
                commandForm.set(getAttrsMap(attrs));

                coreService.createEntityAliasType(initialDataParser.getUserVisit(), commandForm);

                initialDataParser.pushHandler(new EntityAliasTypeHandler(initialDataParser, this, componentVendorName, entityTypeName,
                        commandForm.getEntityAliasTypeName()));
            }
            case "entityAttribute" -> {
                var commandForm = CoreFormFactory.getCreateEntityAttributeForm();

                commandForm.setComponentVendorName(componentVendorName);
                commandForm.setEntityTypeName(entityTypeName);
                commandForm.set(getAttrsMap(attrs));

                coreService.createEntityAttribute(initialDataParser.getUserVisit(), commandForm);

                initialDataParser.pushHandler(new EntityAttributeHandler(initialDataParser, this, componentVendorName, entityTypeName,
                        commandForm.getEntityAttributeName()));
            }
            case "commentType" -> {
                var commandForm = CommentFormFactory.getCreateCommentTypeForm();

                commandForm.setComponentVendorName(componentVendorName);
                commandForm.setEntityTypeName(entityTypeName);
                commandForm.set(getAttrsMap(attrs));

                commentService.createCommentType(initialDataParser.getUserVisit(), commandForm);

                initialDataParser.pushHandler(new CommentTypeHandler(initialDataParser, this, componentVendorName, entityTypeName,
                        commandForm.getCommentTypeName()));
            }
            case "ratingType" -> {
                var commandForm = RatingFormFactory.getCreateRatingTypeForm();

                commandForm.setComponentVendorName(componentVendorName);
                commandForm.setEntityTypeName(entityTypeName);
                commandForm.set(getAttrsMap(attrs));

                ratingService.createRatingType(initialDataParser.getUserVisit(), commandForm);

                initialDataParser.pushHandler(new RatingTypeHandler(initialDataParser, this, componentVendorName, entityTypeName,
                        commandForm.getRatingTypeName()));
            }
            case "messageType" -> {
                var commandForm = MessageFormFactory.getCreateMessageTypeForm();

                commandForm.setComponentVendorName(componentVendorName);
                commandForm.setEntityTypeName(entityTypeName);
                commandForm.set(getAttrsMap(attrs));

                messageService.createMessageType(initialDataParser.getUserVisit(), commandForm);

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
