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

package com.echothree.control.user.rating.server;

import com.echothree.control.user.rating.common.RatingRemote;
import com.echothree.control.user.rating.common.form.*;
import com.echothree.control.user.rating.common.result.*;
import com.echothree.control.user.rating.server.command.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.VoidResult;
import javax.ejb.Stateless;
import javax.enterprise.inject.spi.CDI;

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
    public CommandResult<CreateRatingTypeResult> createRatingType(UserVisitPK userVisitPK, CreateRatingTypeForm form) {
        return CDI.current().select(CreateRatingTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetRatingTypeResult> getRatingType(UserVisitPK userVisitPK, GetRatingTypeForm form) {
        return CDI.current().select(GetRatingTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetRatingTypesResult> getRatingTypes(UserVisitPK userVisitPK, GetRatingTypesForm form) {
        return CDI.current().select(GetRatingTypesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<EditRatingTypeResult> editRatingType(UserVisitPK userVisitPK, EditRatingTypeForm form) {
        return CDI.current().select(EditRatingTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<VoidResult> deleteRatingType(UserVisitPK userVisitPK, DeleteRatingTypeForm form) {
        return CDI.current().select(DeleteRatingTypeCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Rating Type Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult<VoidResult> createRatingTypeDescription(UserVisitPK userVisitPK, CreateRatingTypeDescriptionForm form) {
        return CDI.current().select(CreateRatingTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetRatingTypeDescriptionResult> getRatingTypeDescription(UserVisitPK userVisitPK, GetRatingTypeDescriptionForm form) {
        return CDI.current().select(GetRatingTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetRatingTypeDescriptionsResult> getRatingTypeDescriptions(UserVisitPK userVisitPK, GetRatingTypeDescriptionsForm form) {
        return CDI.current().select(GetRatingTypeDescriptionsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<EditRatingTypeDescriptionResult> editRatingTypeDescription(UserVisitPK userVisitPK, EditRatingTypeDescriptionForm form) {
        return CDI.current().select(EditRatingTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<VoidResult> deleteRatingTypeDescription(UserVisitPK userVisitPK, DeleteRatingTypeDescriptionForm form) {
        return CDI.current().select(DeleteRatingTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Rating Type List Items
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult<VoidResult> createRatingTypeListItem(UserVisitPK userVisitPK, CreateRatingTypeListItemForm form) {
        return CDI.current().select(CreateRatingTypeListItemCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetRatingTypeListItemResult> getRatingTypeListItem(UserVisitPK userVisitPK, GetRatingTypeListItemForm form) {
        return CDI.current().select(GetRatingTypeListItemCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetRatingTypeListItemsResult> getRatingTypeListItems(UserVisitPK userVisitPK, GetRatingTypeListItemsForm form) {
        return CDI.current().select(GetRatingTypeListItemsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetRatingTypeListItemChoicesResult> getRatingTypeListItemChoices(UserVisitPK userVisitPK, GetRatingTypeListItemChoicesForm form) {
        return CDI.current().select(GetRatingTypeListItemChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<VoidResult> setDefaultRatingTypeListItem(UserVisitPK userVisitPK, SetDefaultRatingTypeListItemForm form) {
        return CDI.current().select(SetDefaultRatingTypeListItemCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<EditRatingTypeListItemResult> editRatingTypeListItem(UserVisitPK userVisitPK, EditRatingTypeListItemForm form) {
        return CDI.current().select(EditRatingTypeListItemCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<VoidResult> deleteRatingTypeListItem(UserVisitPK userVisitPK, DeleteRatingTypeListItemForm form) {
        return CDI.current().select(DeleteRatingTypeListItemCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Rating Type List Item Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult<VoidResult> createRatingTypeListItemDescription(UserVisitPK userVisitPK, CreateRatingTypeListItemDescriptionForm form) {
        return CDI.current().select(CreateRatingTypeListItemDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetRatingTypeListItemDescriptionResult> getRatingTypeListItemDescription(UserVisitPK userVisitPK, GetRatingTypeListItemDescriptionForm form) {
        return CDI.current().select(GetRatingTypeListItemDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetRatingTypeListItemDescriptionsResult> getRatingTypeListItemDescriptions(UserVisitPK userVisitPK, GetRatingTypeListItemDescriptionsForm form) {
        return CDI.current().select(GetRatingTypeListItemDescriptionsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<EditRatingTypeListItemDescriptionResult> editRatingTypeListItemDescription(UserVisitPK userVisitPK, EditRatingTypeListItemDescriptionForm form) {
        return CDI.current().select(EditRatingTypeListItemDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<VoidResult> deleteRatingTypeListItemDescription(UserVisitPK userVisitPK, DeleteRatingTypeListItemDescriptionForm form) {
        return CDI.current().select(DeleteRatingTypeListItemDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Ratings
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult<CreateRatingResult> createRating(UserVisitPK userVisitPK, CreateRatingForm form) {
        return CDI.current().select(CreateRatingCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetRatingResult> getRating(UserVisitPK userVisitPK, GetRatingForm form) {
        return CDI.current().select(GetRatingCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<EditRatingResult> editRating(UserVisitPK userVisitPK, EditRatingForm form) {
        return CDI.current().select(EditRatingCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<VoidResult> deleteRating(UserVisitPK userVisitPK, DeleteRatingForm form) {
        return CDI.current().select(DeleteRatingCommand.class).get().run(userVisitPK, form);
    }
    
}
