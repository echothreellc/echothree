// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

package com.echothree.ui.cli.dataloader.data.handler.core;

import com.echothree.control.user.comment.common.CommentUtil;
import com.echothree.control.user.comment.common.CommentService;
import com.echothree.control.user.comment.common.form.CommentFormFactory;
import com.echothree.control.user.comment.common.form.CreateCommentTypeForm;
import com.echothree.control.user.core.common.CoreUtil;
import com.echothree.control.user.core.common.CoreService;
import com.echothree.control.user.core.common.form.CoreFormFactory;
import com.echothree.control.user.core.common.form.CreateEntityAttributeForm;
import com.echothree.control.user.core.common.form.CreateEntityTypeDescriptionForm;
import com.echothree.control.user.message.common.MessageUtil;
import com.echothree.control.user.message.common.MessageService;
import com.echothree.control.user.message.common.form.CreateMessageTypeForm;
import com.echothree.control.user.message.common.form.MessageFormFactory;
import com.echothree.control.user.rating.common.RatingUtil;
import com.echothree.control.user.rating.common.RatingService;
import com.echothree.control.user.rating.common.form.CreateRatingTypeForm;
import com.echothree.control.user.rating.common.form.RatingFormFactory;
import com.echothree.ui.cli.dataloader.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.data.handler.BaseHandler;
import com.echothree.ui.cli.dataloader.data.handler.comment.CommentTypeHandler;
import com.echothree.ui.cli.dataloader.data.handler.message.MessageTypeHandler;
import com.echothree.ui.cli.dataloader.data.handler.rating.RatingTypeHandler;
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
        if(localName.equals("entityTypeDescription")) {
            CreateEntityTypeDescriptionForm commandForm = CoreFormFactory.getCreateEntityTypeDescriptionForm();

            commandForm.setComponentVendorName(componentVendorName);
            commandForm.setEntityTypeName(entityTypeName);
            commandForm.set(getAttrsMap(attrs));

            coreService.createEntityTypeDescription(initialDataParser.getUserVisit(), commandForm);
        } else if(localName.equals("entityAttribute")) {
            CreateEntityAttributeForm commandForm = CoreFormFactory.getCreateEntityAttributeForm();

            commandForm.setComponentVendorName(componentVendorName);
            commandForm.setEntityTypeName(entityTypeName);
            commandForm.set(getAttrsMap(attrs));

            coreService.createEntityAttribute(initialDataParser.getUserVisit(), commandForm);

            initialDataParser.pushHandler(new EntityAttributeHandler(initialDataParser, this, componentVendorName, entityTypeName,
                    commandForm.getEntityAttributeName()));
        } else if(localName.equals("commentType")) {
            CreateCommentTypeForm commandForm = CommentFormFactory.getCreateCommentTypeForm();

            commandForm.setComponentVendorName(componentVendorName);
            commandForm.setEntityTypeName(entityTypeName);
            commandForm.set(getAttrsMap(attrs));

            commentService.createCommentType(initialDataParser.getUserVisit(), commandForm);

            initialDataParser.pushHandler(new CommentTypeHandler(initialDataParser, this, componentVendorName, entityTypeName,
                    commandForm.getCommentTypeName()));
        } else if(localName.equals("ratingType")) {
            CreateRatingTypeForm commandForm = RatingFormFactory.getCreateRatingTypeForm();

            commandForm.setComponentVendorName(componentVendorName);
            commandForm.setEntityTypeName(entityTypeName);
            commandForm.set(getAttrsMap(attrs));

            ratingService.createRatingType(initialDataParser.getUserVisit(), commandForm);

            initialDataParser.pushHandler(new RatingTypeHandler(initialDataParser, this, componentVendorName, entityTypeName,
                    commandForm.getRatingTypeName()));
        } else if(localName.equals("messageType")) {
            CreateMessageTypeForm commandForm = MessageFormFactory.getCreateMessageTypeForm();

            commandForm.setComponentVendorName(componentVendorName);
            commandForm.setEntityTypeName(entityTypeName);
            commandForm.set(getAttrsMap(attrs));

            messageService.createMessageType(initialDataParser.getUserVisit(), commandForm);

            initialDataParser.pushHandler(new MessageTypeHandler(initialDataParser, this, componentVendorName, entityTypeName,
                    commandForm.getMessageTypeName()));
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
