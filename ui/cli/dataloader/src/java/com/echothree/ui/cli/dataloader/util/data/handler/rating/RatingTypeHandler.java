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

package com.echothree.ui.cli.dataloader.util.data.handler.rating;

import com.echothree.control.user.rating.common.RatingUtil;
import com.echothree.control.user.rating.common.RatingService;
import com.echothree.control.user.rating.common.form.RatingFormFactory;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class RatingTypeHandler
        extends BaseHandler {

    RatingService ratingService;
    String componentVendorName;
    String entityTypeName;
    String ratingTypeName;
    
    /** Creates a new instance of RatingTypeHandler */
    public RatingTypeHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String componentVendorName, String entityTypeName, String ratingTypeName)
            throws SAXException {
        super(initialDataParser, parentHandler);
        
        ratingService = lookupService(RatingService.class, RatingUtil::getHome);
        
        this.componentVendorName = componentVendorName;
        this.entityTypeName = entityTypeName;
        this.ratingTypeName = ratingTypeName;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
    throws SAXException {
        if(localName.equals("ratingTypeDescription")) {
            var createRatingTypeDescriptionForm = RatingFormFactory.getCreateRatingTypeDescriptionForm();

            createRatingTypeDescriptionForm.setComponentVendorName(componentVendorName);
            createRatingTypeDescriptionForm.setEntityTypeName(entityTypeName);
            createRatingTypeDescriptionForm.setRatingTypeName(ratingTypeName);
            createRatingTypeDescriptionForm.set(getAttrsMap(attrs));

            checkCommandResult(createRatingTypeDescriptionForm,
                    ratingService.createRatingTypeDescription(initialDataParser.getUserVisit(), createRatingTypeDescriptionForm));
        } else if(localName.equals("ratingTypeListItem")) {
            var createRatingTypeListItemForm = RatingFormFactory.getCreateRatingTypeListItemForm();

            createRatingTypeListItemForm.setComponentVendorName(componentVendorName);
            createRatingTypeListItemForm.setEntityTypeName(entityTypeName);
            createRatingTypeListItemForm.setRatingTypeName(ratingTypeName);
            createRatingTypeListItemForm.set(getAttrsMap(attrs));

            checkCommandResult(createRatingTypeListItemForm,
                    ratingService.createRatingTypeListItem(initialDataParser.getUserVisit(), createRatingTypeListItemForm));

            initialDataParser.pushHandler(new RatingTypeListItemHandler(initialDataParser, this, componentVendorName,
                    entityTypeName, ratingTypeName, createRatingTypeListItemForm.getRatingTypeListItemName()));
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
    throws SAXException {
        if(localName.equals("ratingType")) {
            initialDataParser.popHandler();
        }
    }
    
}
