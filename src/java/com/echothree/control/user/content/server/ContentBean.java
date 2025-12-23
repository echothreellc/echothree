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

package com.echothree.control.user.content.server;

import com.echothree.control.user.content.common.ContentRemote;
import com.echothree.control.user.content.common.form.*;
import com.echothree.control.user.content.server.command.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;
import javax.ejb.Stateless;
import javax.enterprise.inject.spi.CDI;

@Stateless
public class ContentBean
        extends ContentFormsImpl
        implements ContentRemote, ContentLocal {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    @Override
    public String ping() {
        return "ContentBean is alive!";
    }
    
    // --------------------------------------------------------------------------------
    //   Content Page Area Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createContentPageAreaType(UserVisitPK userVisitPK, CreateContentPageAreaTypeForm form) {
        return CDI.current().select(CreateContentPageAreaTypeCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Content Page Area Type Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createContentPageAreaTypeDescription(UserVisitPK userVisitPK, CreateContentPageAreaTypeDescriptionForm form) {
        return CDI.current().select(CreateContentPageAreaTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getContentPageAreaTypeChoices(UserVisitPK userVisitPK, GetContentPageAreaTypeChoicesForm form) {
        return CDI.current().select(GetContentPageAreaTypeChoicesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getContentPageAreaType(UserVisitPK userVisitPK, GetContentPageAreaTypeForm form) {
        return CDI.current().select(GetContentPageAreaTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getContentPageAreaTypes(UserVisitPK userVisitPK, GetContentPageAreaTypesForm form) {
        return CDI.current().select(GetContentPageAreaTypesCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Content Page Layouts
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createContentPageLayout(UserVisitPK userVisitPK, CreateContentPageLayoutForm form) {
        return CDI.current().select(CreateContentPageLayoutCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getContentPageLayoutChoices(UserVisitPK userVisitPK, GetContentPageLayoutChoicesForm form) {
        return CDI.current().select(GetContentPageLayoutChoicesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getContentPageLayout(UserVisitPK userVisitPK, GetContentPageLayoutForm form) {
        return CDI.current().select(GetContentPageLayoutCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getContentPageLayouts(UserVisitPK userVisitPK, GetContentPageLayoutsForm form) {
        return CDI.current().select(GetContentPageLayoutsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultContentPageLayout(UserVisitPK userVisitPK, SetDefaultContentPageLayoutForm form) {
        return CDI.current().select(SetDefaultContentPageLayoutCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editContentPageLayout(UserVisitPK userVisitPK, EditContentPageLayoutForm form) {
        return CDI.current().select(EditContentPageLayoutCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteContentPageLayout(UserVisitPK userVisitPK, DeleteContentPageLayoutForm form) {
        return CDI.current().select(DeleteContentPageLayoutCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Content Page Layout Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createContentPageLayoutDescription(UserVisitPK userVisitPK, CreateContentPageLayoutDescriptionForm form) {
        return CDI.current().select(CreateContentPageLayoutDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getContentPageLayoutDescription(UserVisitPK userVisitPK, GetContentPageLayoutDescriptionForm form) {
        return CDI.current().select(GetContentPageLayoutDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getContentPageLayoutDescriptions(UserVisitPK userVisitPK, GetContentPageLayoutDescriptionsForm form) {
        return CDI.current().select(GetContentPageLayoutDescriptionsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editContentPageLayoutDescription(UserVisitPK userVisitPK, EditContentPageLayoutDescriptionForm form) {
        return CDI.current().select(EditContentPageLayoutDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteContentPageLayoutDescription(UserVisitPK userVisitPK, DeleteContentPageLayoutDescriptionForm form) {
        return CDI.current().select(DeleteContentPageLayoutDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Content Page Layout Areas
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createContentPageLayoutArea(UserVisitPK userVisitPK, CreateContentPageLayoutAreaForm form) {
        return CDI.current().select(CreateContentPageLayoutAreaCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getContentPageLayoutArea(UserVisitPK userVisitPK, GetContentPageLayoutAreaForm form) {
        return CDI.current().select(GetContentPageLayoutAreaCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getContentPageLayoutAreas(UserVisitPK userVisitPK, GetContentPageLayoutAreasForm form) {
        return CDI.current().select(GetContentPageLayoutAreasCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Content Page Layout Area Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createContentPageLayoutAreaDescription(UserVisitPK userVisitPK, CreateContentPageLayoutAreaDescriptionForm form) {
        return CDI.current().select(CreateContentPageLayoutAreaDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Content Collections
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createContentCollection(UserVisitPK userVisitPK, CreateContentCollectionForm form) {
        return CDI.current().select(CreateContentCollectionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getContentCollection(UserVisitPK userVisitPK, GetContentCollectionForm form) {
        return CDI.current().select(GetContentCollectionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getContentCollections(UserVisitPK userVisitPK, GetContentCollectionsForm form) {
        return CDI.current().select(GetContentCollectionsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editContentCollection(UserVisitPK userVisitPK, EditContentCollectionForm form) {
        return CDI.current().select(EditContentCollectionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteContentCollection(UserVisitPK userVisitPK, DeleteContentCollectionForm form) {
        return CDI.current().select(DeleteContentCollectionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getContentCollectionChoices(UserVisitPK userVisitPK, GetContentCollectionChoicesForm form) {
        return CDI.current().select(GetContentCollectionChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Content Collection Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createContentCollectionDescription(UserVisitPK userVisitPK, CreateContentCollectionDescriptionForm form) {
        return CDI.current().select(CreateContentCollectionDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getContentCollectionDescription(UserVisitPK userVisitPK, GetContentCollectionDescriptionForm form) {
        return CDI.current().select(GetContentCollectionDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getContentCollectionDescriptions(UserVisitPK userVisitPK, GetContentCollectionDescriptionsForm form) {
        return CDI.current().select(GetContentCollectionDescriptionsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editContentCollectionDescription(UserVisitPK userVisitPK, EditContentCollectionDescriptionForm form) {
        return CDI.current().select(EditContentCollectionDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    
    @Override
    public CommandResult deleteContentCollectionDescription(UserVisitPK userVisitPK, DeleteContentCollectionDescriptionForm form) {
        return CDI.current().select(DeleteContentCollectionDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Content Sections
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createContentSection(UserVisitPK userVisitPK, CreateContentSectionForm form) {
        return CDI.current().select(CreateContentSectionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getContentSection(UserVisitPK userVisitPK, GetContentSectionForm form) {
        return CDI.current().select(GetContentSectionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getContentSections(UserVisitPK userVisitPK, GetContentSectionsForm form) {
        return CDI.current().select(GetContentSectionsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultContentSection(UserVisitPK userVisitPK, SetDefaultContentSectionForm form) {
        return CDI.current().select(SetDefaultContentSectionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editContentSection(UserVisitPK userVisitPK, EditContentSectionForm form) {
        return CDI.current().select(EditContentSectionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteContentSection(UserVisitPK userVisitPK, DeleteContentSectionForm form) {
        return CDI.current().select(DeleteContentSectionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getContentSectionChoices(UserVisitPK userVisitPK, GetContentSectionChoicesForm form) {
        return CDI.current().select(GetContentSectionChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Content Section Description
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createContentSectionDescription(UserVisitPK userVisitPK, CreateContentSectionDescriptionForm form) {
        return CDI.current().select(CreateContentSectionDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getContentSectionDescription(UserVisitPK userVisitPK, GetContentSectionDescriptionForm form) {
        return CDI.current().select(GetContentSectionDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getContentSectionDescriptions(UserVisitPK userVisitPK, GetContentSectionDescriptionsForm form) {
        return CDI.current().select(GetContentSectionDescriptionsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editContentSectionDescription(UserVisitPK userVisitPK, EditContentSectionDescriptionForm form) {
        return CDI.current().select(EditContentSectionDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteContentSectionDescription(UserVisitPK userVisitPK, DeleteContentSectionDescriptionForm form) {
        return CDI.current().select(DeleteContentSectionDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //  Content Pages
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createContentPage(UserVisitPK userVisitPK, CreateContentPageForm form) {
        return CDI.current().select(CreateContentPageCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getContentPage(UserVisitPK userVisitPK, GetContentPageForm form) {
        return CDI.current().select(GetContentPageCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getContentPages(UserVisitPK userVisitPK, GetContentPagesForm form) {
        return CDI.current().select(GetContentPagesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultContentPage(UserVisitPK userVisitPK, SetDefaultContentPageForm form) {
        return CDI.current().select(SetDefaultContentPageCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editContentPage(UserVisitPK userVisitPK, EditContentPageForm form) {
        return CDI.current().select(EditContentPageCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteContentPage(UserVisitPK userVisitPK, DeleteContentPageForm form) {
        return CDI.current().select(DeleteContentPageCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Content Page Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createContentPageDescription(UserVisitPK userVisitPK, CreateContentPageDescriptionForm form) {
        return CDI.current().select(CreateContentPageDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getContentPageDescription(UserVisitPK userVisitPK, GetContentPageDescriptionForm form) {
        return CDI.current().select(GetContentPageDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getContentPageDescriptions(UserVisitPK userVisitPK, GetContentPageDescriptionsForm form) {
        return CDI.current().select(GetContentPageDescriptionsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editContentPageDescription(UserVisitPK userVisitPK, EditContentPageDescriptionForm form) {
        return CDI.current().select(EditContentPageDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteContentPageDescription(UserVisitPK userVisitPK, DeleteContentPageDescriptionForm form) {
        return CDI.current().select(DeleteContentPageDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Content Catalogs
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createContentCatalog(UserVisitPK userVisitPK, CreateContentCatalogForm form) {
        return CDI.current().select(CreateContentCatalogCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getContentCatalog(UserVisitPK userVisitPK, GetContentCatalogForm form) {
        return CDI.current().select(GetContentCatalogCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getContentCatalogs(UserVisitPK userVisitPK, GetContentCatalogsForm form) {
        return CDI.current().select(GetContentCatalogsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultContentCatalog(UserVisitPK userVisitPK, SetDefaultContentCatalogForm form) {
        return CDI.current().select(SetDefaultContentCatalogCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editContentCatalog(UserVisitPK userVisitPK, EditContentCatalogForm form) {
        return CDI.current().select(EditContentCatalogCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteContentCatalog(UserVisitPK userVisitPK, DeleteContentCatalogForm form) {
        return CDI.current().select(DeleteContentCatalogCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Content Catalog Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createContentCatalogDescription(UserVisitPK userVisitPK, CreateContentCatalogDescriptionForm form) {
        return CDI.current().select(CreateContentCatalogDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getContentCatalogDescription(UserVisitPK userVisitPK, GetContentCatalogDescriptionForm form) {
        return CDI.current().select(GetContentCatalogDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getContentCatalogDescriptions(UserVisitPK userVisitPK, GetContentCatalogDescriptionsForm form) {
        return CDI.current().select(GetContentCatalogDescriptionsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editContentCatalogDescription(UserVisitPK userVisitPK, EditContentCatalogDescriptionForm form) {
        return CDI.current().select(EditContentCatalogDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteContentCatalogDescription(UserVisitPK userVisitPK, DeleteContentCatalogDescriptionForm form) {
        return CDI.current().select(DeleteContentCatalogDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Content Catalog Items
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult getContentCatalogItem(UserVisitPK userVisitPK, GetContentCatalogItemForm form) {
        return CDI.current().select(GetContentCatalogItemCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getContentCatalogItems(UserVisitPK userVisitPK, GetContentCatalogItemsForm form) {
        return CDI.current().select(GetContentCatalogItemsCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Content Categories
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createContentCategory(UserVisitPK userVisitPK, CreateContentCategoryForm form) {
        return CDI.current().select(CreateContentCategoryCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getContentCategory(UserVisitPK userVisitPK, GetContentCategoryForm form) {
        return CDI.current().select(GetContentCategoryCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getContentCategories(UserVisitPK userVisitPK, GetContentCategoriesForm form) {
        return CDI.current().select(GetContentCategoriesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultContentCategory(UserVisitPK userVisitPK, SetDefaultContentCategoryForm form) {
        return CDI.current().select(SetDefaultContentCategoryCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editContentCategory(UserVisitPK userVisitPK, EditContentCategoryForm form) {
        return CDI.current().select(EditContentCategoryCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteContentCategory(UserVisitPK userVisitPK, DeleteContentCategoryForm form) {
        return CDI.current().select(DeleteContentCategoryCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getContentCategoryChoices(UserVisitPK userVisitPK, GetContentCategoryChoicesForm form) {
        return CDI.current().select(GetContentCategoryChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Content Category Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createContentCategoryDescription(UserVisitPK userVisitPK, CreateContentCategoryDescriptionForm form) {
        return CDI.current().select(CreateContentCategoryDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getContentCategoryDescription(UserVisitPK userVisitPK, GetContentCategoryDescriptionForm form) {
        return CDI.current().select(GetContentCategoryDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getContentCategoryDescriptions(UserVisitPK userVisitPK, GetContentCategoryDescriptionsForm form) {
        return CDI.current().select(GetContentCategoryDescriptionsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editContentCategoryDescription(UserVisitPK userVisitPK, EditContentCategoryDescriptionForm form) {
        return CDI.current().select(EditContentCategoryDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteContentCategoryDescription(UserVisitPK userVisitPK, DeleteContentCategoryDescriptionForm form) {
        return CDI.current().select(DeleteContentCategoryDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Content Category Items
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createContentCategoryItem(UserVisitPK userVisitPK, CreateContentCategoryItemForm form) {
        return CDI.current().select(CreateContentCategoryItemCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getContentCategoryItem(UserVisitPK userVisitPK, GetContentCategoryItemForm form) {
        return CDI.current().select(GetContentCategoryItemCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getContentCategoryItems(UserVisitPK userVisitPK, GetContentCategoryItemsForm form) {
        return CDI.current().select(GetContentCategoryItemsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultContentCategoryItem(UserVisitPK userVisitPK, SetDefaultContentCategoryItemForm form) {
        return CDI.current().select(SetDefaultContentCategoryItemCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editContentCategoryItem(UserVisitPK userVisitPK, EditContentCategoryItemForm form) {
        return CDI.current().select(EditContentCategoryItemCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteContentCategoryItem(UserVisitPK userVisitPK, DeleteContentCategoryItemForm form) {
        return CDI.current().select(DeleteContentCategoryItemCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Content Forums
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createContentForum(UserVisitPK userVisitPK, CreateContentForumForm form) {
        return CDI.current().select(CreateContentForumCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getContentForum(UserVisitPK userVisitPK, GetContentForumForm form) {
        return CDI.current().select(GetContentForumCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getContentForums(UserVisitPK userVisitPK, GetContentForumsForm form) {
        return CDI.current().select(GetContentForumsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultContentForum(UserVisitPK userVisitPK, SetDefaultContentForumForm form) {
        return CDI.current().select(SetDefaultContentForumCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteContentForum(UserVisitPK userVisitPK, DeleteContentForumForm form) {
        return CDI.current().select(DeleteContentForumCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Content Web Addresses
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createContentWebAddress(UserVisitPK userVisitPK, CreateContentWebAddressForm form) {
        return CDI.current().select(CreateContentWebAddressCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getContentWebAddress(UserVisitPK userVisitPK, GetContentWebAddressForm form) {
        return CDI.current().select(GetContentWebAddressCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getContentWebAddresses(UserVisitPK userVisitPK, GetContentWebAddressesForm form) {
        return CDI.current().select(GetContentWebAddressesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editContentWebAddress(UserVisitPK userVisitPK, EditContentWebAddressForm form) {
        return CDI.current().select(EditContentWebAddressCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteContentWebAddress(UserVisitPK userVisitPK, DeleteContentWebAddressForm form) {
        return CDI.current().select(DeleteContentWebAddressCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Content Web Address Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createContentWebAddressDescription(UserVisitPK userVisitPK, CreateContentWebAddressDescriptionForm form) {
        return CDI.current().select(CreateContentWebAddressDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getContentWebAddressDescription(UserVisitPK userVisitPK, GetContentWebAddressDescriptionForm form) {
        return CDI.current().select(GetContentWebAddressDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getContentWebAddressDescriptions(UserVisitPK userVisitPK, GetContentWebAddressDescriptionsForm form) {
        return CDI.current().select(GetContentWebAddressDescriptionsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editContentWebAddressDescription(UserVisitPK userVisitPK, EditContentWebAddressDescriptionForm form) {
        return CDI.current().select(EditContentWebAddressDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteContentWebAddressDescription(UserVisitPK userVisitPK, DeleteContentWebAddressDescriptionForm form) {
        return CDI.current().select(DeleteContentWebAddressDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Content Web Address Servers
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createContentWebAddressServer(UserVisitPK userVisitPK, CreateContentWebAddressServerForm form) {
        return CDI.current().select(CreateContentWebAddressServerCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //  Content Page Areas
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createContentPageArea(UserVisitPK userVisitPK, CreateContentPageAreaForm form) {
        return CDI.current().select(CreateContentPageAreaCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getContentPageArea(UserVisitPK userVisitPK, GetContentPageAreaForm form) {
        return CDI.current().select(GetContentPageAreaCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getContentPageAreas(UserVisitPK userVisitPK, GetContentPageAreasForm form) {
        return CDI.current().select(GetContentPageAreasCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editContentPageArea(UserVisitPK userVisitPK, EditContentPageAreaForm form) {
        return CDI.current().select(EditContentPageAreaCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteContentPageArea(UserVisitPK userVisitPK, DeleteContentPageAreaForm form) {
        return CDI.current().select(DeleteContentPageAreaCommand.class).get().run(userVisitPK, form);
    }
    
}
