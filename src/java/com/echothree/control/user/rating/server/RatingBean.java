// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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
        return new CreateRatingTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getRatingType(UserVisitPK userVisitPK, GetRatingTypeForm form) {
        return new GetRatingTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getRatingTypes(UserVisitPK userVisitPK, GetRatingTypesForm form) {
        return new GetRatingTypesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editRatingType(UserVisitPK userVisitPK, EditRatingTypeForm form) {
        return new EditRatingTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteRatingType(UserVisitPK userVisitPK, DeleteRatingTypeForm form) {
        return new DeleteRatingTypeCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Rating Type Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createRatingTypeDescription(UserVisitPK userVisitPK, CreateRatingTypeDescriptionForm form) {
        return new CreateRatingTypeDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getRatingTypeDescription(UserVisitPK userVisitPK, GetRatingTypeDescriptionForm form) {
        return new GetRatingTypeDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getRatingTypeDescriptions(UserVisitPK userVisitPK, GetRatingTypeDescriptionsForm form) {
        return new GetRatingTypeDescriptionsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editRatingTypeDescription(UserVisitPK userVisitPK, EditRatingTypeDescriptionForm form) {
        return new EditRatingTypeDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteRatingTypeDescription(UserVisitPK userVisitPK, DeleteRatingTypeDescriptionForm form) {
        return new DeleteRatingTypeDescriptionCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Rating Type List Items
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createRatingTypeListItem(UserVisitPK userVisitPK, CreateRatingTypeListItemForm form) {
        return new CreateRatingTypeListItemCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getRatingTypeListItem(UserVisitPK userVisitPK, GetRatingTypeListItemForm form) {
        return new GetRatingTypeListItemCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getRatingTypeListItems(UserVisitPK userVisitPK, GetRatingTypeListItemsForm form) {
        return new GetRatingTypeListItemsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getRatingTypeListItemChoices(UserVisitPK userVisitPK, GetRatingTypeListItemChoicesForm form) {
        return new GetRatingTypeListItemChoicesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultRatingTypeListItem(UserVisitPK userVisitPK, SetDefaultRatingTypeListItemForm form) {
        return new SetDefaultRatingTypeListItemCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editRatingTypeListItem(UserVisitPK userVisitPK, EditRatingTypeListItemForm form) {
        return new EditRatingTypeListItemCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteRatingTypeListItem(UserVisitPK userVisitPK, DeleteRatingTypeListItemForm form) {
        return new DeleteRatingTypeListItemCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Rating Type List Item Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createRatingTypeListItemDescription(UserVisitPK userVisitPK, CreateRatingTypeListItemDescriptionForm form) {
        return new CreateRatingTypeListItemDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getRatingTypeListItemDescription(UserVisitPK userVisitPK, GetRatingTypeListItemDescriptionForm form) {
        return new GetRatingTypeListItemDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getRatingTypeListItemDescriptions(UserVisitPK userVisitPK, GetRatingTypeListItemDescriptionsForm form) {
        return new GetRatingTypeListItemDescriptionsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editRatingTypeListItemDescription(UserVisitPK userVisitPK, EditRatingTypeListItemDescriptionForm form) {
        return new EditRatingTypeListItemDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteRatingTypeListItemDescription(UserVisitPK userVisitPK, DeleteRatingTypeListItemDescriptionForm form) {
        return new DeleteRatingTypeListItemDescriptionCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Ratings
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createRating(UserVisitPK userVisitPK, CreateRatingForm form) {
        return new CreateRatingCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getRating(UserVisitPK userVisitPK, GetRatingForm form) {
        return new GetRatingCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editRating(UserVisitPK userVisitPK, EditRatingForm form) {
        return new EditRatingCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteRating(UserVisitPK userVisitPK, DeleteRatingForm form) {
        return new DeleteRatingCommand().run(userVisitPK, form);
    }
    
}
