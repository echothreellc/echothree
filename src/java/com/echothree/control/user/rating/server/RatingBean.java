// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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

package com.echothree.control.user.rating.server;

import com.echothree.control.user.rating.common.RatingRemote;
import com.echothree.control.user.rating.common.form.*;
import com.echothree.control.user.rating.server.command.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;
import javax.ejb.Stateless;

@Stateless
public class RatingBean
        extends RatingFormsImpl
        implements RatingRemote, RatingLocal {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    @Override
    public String ping() {
        return "RatingBean is alive!";
    }
    
    // -------------------------------------------------------------------------
    //   Rating  Types
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createRatingType(UserVisitPK userVisitPK, CreateRatingTypeForm form) {
        return new CreateRatingTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getRatingType(UserVisitPK userVisitPK, GetRatingTypeForm form) {
        return new GetRatingTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getRatingTypes(UserVisitPK userVisitPK, GetRatingTypesForm form) {
        return new GetRatingTypesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editRatingType(UserVisitPK userVisitPK, EditRatingTypeForm form) {
        return new EditRatingTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteRatingType(UserVisitPK userVisitPK, DeleteRatingTypeForm form) {
        return new DeleteRatingTypeCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Rating Type Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createRatingTypeDescription(UserVisitPK userVisitPK, CreateRatingTypeDescriptionForm form) {
        return new CreateRatingTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getRatingTypeDescription(UserVisitPK userVisitPK, GetRatingTypeDescriptionForm form) {
        return new GetRatingTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getRatingTypeDescriptions(UserVisitPK userVisitPK, GetRatingTypeDescriptionsForm form) {
        return new GetRatingTypeDescriptionsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editRatingTypeDescription(UserVisitPK userVisitPK, EditRatingTypeDescriptionForm form) {
        return new EditRatingTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteRatingTypeDescription(UserVisitPK userVisitPK, DeleteRatingTypeDescriptionForm form) {
        return new DeleteRatingTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Rating Type List Items
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createRatingTypeListItem(UserVisitPK userVisitPK, CreateRatingTypeListItemForm form) {
        return new CreateRatingTypeListItemCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getRatingTypeListItem(UserVisitPK userVisitPK, GetRatingTypeListItemForm form) {
        return new GetRatingTypeListItemCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getRatingTypeListItems(UserVisitPK userVisitPK, GetRatingTypeListItemsForm form) {
        return new GetRatingTypeListItemsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getRatingTypeListItemChoices(UserVisitPK userVisitPK, GetRatingTypeListItemChoicesForm form) {
        return new GetRatingTypeListItemChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setDefaultRatingTypeListItem(UserVisitPK userVisitPK, SetDefaultRatingTypeListItemForm form) {
        return new SetDefaultRatingTypeListItemCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editRatingTypeListItem(UserVisitPK userVisitPK, EditRatingTypeListItemForm form) {
        return new EditRatingTypeListItemCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteRatingTypeListItem(UserVisitPK userVisitPK, DeleteRatingTypeListItemForm form) {
        return new DeleteRatingTypeListItemCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Rating Type List Item Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createRatingTypeListItemDescription(UserVisitPK userVisitPK, CreateRatingTypeListItemDescriptionForm form) {
        return new CreateRatingTypeListItemDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getRatingTypeListItemDescription(UserVisitPK userVisitPK, GetRatingTypeListItemDescriptionForm form) {
        return new GetRatingTypeListItemDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getRatingTypeListItemDescriptions(UserVisitPK userVisitPK, GetRatingTypeListItemDescriptionsForm form) {
        return new GetRatingTypeListItemDescriptionsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editRatingTypeListItemDescription(UserVisitPK userVisitPK, EditRatingTypeListItemDescriptionForm form) {
        return new EditRatingTypeListItemDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteRatingTypeListItemDescription(UserVisitPK userVisitPK, DeleteRatingTypeListItemDescriptionForm form) {
        return new DeleteRatingTypeListItemDescriptionCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Ratings
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createRating(UserVisitPK userVisitPK, CreateRatingForm form) {
        return new CreateRatingCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getRating(UserVisitPK userVisitPK, GetRatingForm form) {
        return new GetRatingCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editRating(UserVisitPK userVisitPK, EditRatingForm form) {
        return new EditRatingCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteRating(UserVisitPK userVisitPK, DeleteRatingForm form) {
        return new DeleteRatingCommand(userVisitPK, form).run();
    }
    
}
