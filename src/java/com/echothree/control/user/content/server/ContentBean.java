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

package com.echothree.control.user.content.server;

import com.echothree.control.user.content.common.ContentRemote;
import com.echothree.control.user.content.common.form.*;
import com.echothree.control.user.content.server.command.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;
import javax.ejb.Stateless;

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
        return new CreateContentPageAreaTypeCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Content Page Area Type Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createContentPageAreaTypeDescription(UserVisitPK userVisitPK, CreateContentPageAreaTypeDescriptionForm form) {
        return new CreateContentPageAreaTypeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getContentPageAreaTypeChoices(UserVisitPK userVisitPK, GetContentPageAreaTypeChoicesForm form) {
        return new GetContentPageAreaTypeChoicesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getContentPageAreaType(UserVisitPK userVisitPK, GetContentPageAreaTypeForm form) {
        return new GetContentPageAreaTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getContentPageAreaTypes(UserVisitPK userVisitPK, GetContentPageAreaTypesForm form) {
        return new GetContentPageAreaTypesCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Content Page Layouts
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createContentPageLayout(UserVisitPK userVisitPK, CreateContentPageLayoutForm form) {
        return new CreateContentPageLayoutCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getContentPageLayoutChoices(UserVisitPK userVisitPK, GetContentPageLayoutChoicesForm form) {
        return new GetContentPageLayoutChoicesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getContentPageLayout(UserVisitPK userVisitPK, GetContentPageLayoutForm form) {
        return new GetContentPageLayoutCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getContentPageLayouts(UserVisitPK userVisitPK, GetContentPageLayoutsForm form) {
        return new GetContentPageLayoutsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult setDefaultContentPageLayout(UserVisitPK userVisitPK, SetDefaultContentPageLayoutForm form) {
        return new SetDefaultContentPageLayoutCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editContentPageLayout(UserVisitPK userVisitPK, EditContentPageLayoutForm form) {
        return new EditContentPageLayoutCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteContentPageLayout(UserVisitPK userVisitPK, DeleteContentPageLayoutForm form) {
        return new DeleteContentPageLayoutCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Content Page Layout Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createContentPageLayoutDescription(UserVisitPK userVisitPK, CreateContentPageLayoutDescriptionForm form) {
        return new CreateContentPageLayoutDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getContentPageLayoutDescription(UserVisitPK userVisitPK, GetContentPageLayoutDescriptionForm form) {
        return new GetContentPageLayoutDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getContentPageLayoutDescriptions(UserVisitPK userVisitPK, GetContentPageLayoutDescriptionsForm form) {
        return new GetContentPageLayoutDescriptionsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editContentPageLayoutDescription(UserVisitPK userVisitPK, EditContentPageLayoutDescriptionForm form) {
        return new EditContentPageLayoutDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteContentPageLayoutDescription(UserVisitPK userVisitPK, DeleteContentPageLayoutDescriptionForm form) {
        return new DeleteContentPageLayoutDescriptionCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Content Page Layout Areas
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createContentPageLayoutArea(UserVisitPK userVisitPK, CreateContentPageLayoutAreaForm form) {
        return new CreateContentPageLayoutAreaCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getContentPageLayoutArea(UserVisitPK userVisitPK, GetContentPageLayoutAreaForm form) {
        return new GetContentPageLayoutAreaCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getContentPageLayoutAreas(UserVisitPK userVisitPK, GetContentPageLayoutAreasForm form) {
        return new GetContentPageLayoutAreasCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Content Page Layout Area Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createContentPageLayoutAreaDescription(UserVisitPK userVisitPK, CreateContentPageLayoutAreaDescriptionForm form) {
        return new CreateContentPageLayoutAreaDescriptionCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Content Collections
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createContentCollection(UserVisitPK userVisitPK, CreateContentCollectionForm form) {
        return new CreateContentCollectionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getContentCollection(UserVisitPK userVisitPK, GetContentCollectionForm form) {
        return new GetContentCollectionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getContentCollections(UserVisitPK userVisitPK, GetContentCollectionsForm form) {
        return new GetContentCollectionsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editContentCollection(UserVisitPK userVisitPK, EditContentCollectionForm form) {
        return new EditContentCollectionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteContentCollection(UserVisitPK userVisitPK, DeleteContentCollectionForm form) {
        return new DeleteContentCollectionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getContentCollectionChoices(UserVisitPK userVisitPK, GetContentCollectionChoicesForm form) {
        return new GetContentCollectionChoicesCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Content Collection Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createContentCollectionDescription(UserVisitPK userVisitPK, CreateContentCollectionDescriptionForm form) {
        return new CreateContentCollectionDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getContentCollectionDescription(UserVisitPK userVisitPK, GetContentCollectionDescriptionForm form) {
        return new GetContentCollectionDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getContentCollectionDescriptions(UserVisitPK userVisitPK, GetContentCollectionDescriptionsForm form) {
        return new GetContentCollectionDescriptionsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editContentCollectionDescription(UserVisitPK userVisitPK, EditContentCollectionDescriptionForm form) {
        return new EditContentCollectionDescriptionCommand(userVisitPK, form).run();
    }
    
    
    @Override
    public CommandResult deleteContentCollectionDescription(UserVisitPK userVisitPK, DeleteContentCollectionDescriptionForm form) {
        return new DeleteContentCollectionDescriptionCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Content Sections
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createContentSection(UserVisitPK userVisitPK, CreateContentSectionForm form) {
        return new CreateContentSectionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getContentSection(UserVisitPK userVisitPK, GetContentSectionForm form) {
        return new GetContentSectionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getContentSections(UserVisitPK userVisitPK, GetContentSectionsForm form) {
        return new GetContentSectionsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setDefaultContentSection(UserVisitPK userVisitPK, SetDefaultContentSectionForm form) {
        return new SetDefaultContentSectionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editContentSection(UserVisitPK userVisitPK, EditContentSectionForm form) {
        return new EditContentSectionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteContentSection(UserVisitPK userVisitPK, DeleteContentSectionForm form) {
        return new DeleteContentSectionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getContentSectionChoices(UserVisitPK userVisitPK, GetContentSectionChoicesForm form) {
        return new GetContentSectionChoicesCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Content Section Description
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createContentSectionDescription(UserVisitPK userVisitPK, CreateContentSectionDescriptionForm form) {
        return new CreateContentSectionDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getContentSectionDescription(UserVisitPK userVisitPK, GetContentSectionDescriptionForm form) {
        return new GetContentSectionDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getContentSectionDescriptions(UserVisitPK userVisitPK, GetContentSectionDescriptionsForm form) {
        return new GetContentSectionDescriptionsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editContentSectionDescription(UserVisitPK userVisitPK, EditContentSectionDescriptionForm form) {
        return new EditContentSectionDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteContentSectionDescription(UserVisitPK userVisitPK, DeleteContentSectionDescriptionForm form) {
        return new DeleteContentSectionDescriptionCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //  Content Pages
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createContentPage(UserVisitPK userVisitPK, CreateContentPageForm form) {
        return new CreateContentPageCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getContentPage(UserVisitPK userVisitPK, GetContentPageForm form) {
        return new GetContentPageCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getContentPages(UserVisitPK userVisitPK, GetContentPagesForm form) {
        return new GetContentPagesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setDefaultContentPage(UserVisitPK userVisitPK, SetDefaultContentPageForm form) {
        return new SetDefaultContentPageCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editContentPage(UserVisitPK userVisitPK, EditContentPageForm form) {
        return new EditContentPageCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteContentPage(UserVisitPK userVisitPK, DeleteContentPageForm form) {
        return new DeleteContentPageCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Content Page Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createContentPageDescription(UserVisitPK userVisitPK, CreateContentPageDescriptionForm form) {
        return new CreateContentPageDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getContentPageDescription(UserVisitPK userVisitPK, GetContentPageDescriptionForm form) {
        return new GetContentPageDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getContentPageDescriptions(UserVisitPK userVisitPK, GetContentPageDescriptionsForm form) {
        return new GetContentPageDescriptionsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editContentPageDescription(UserVisitPK userVisitPK, EditContentPageDescriptionForm form) {
        return new EditContentPageDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteContentPageDescription(UserVisitPK userVisitPK, DeleteContentPageDescriptionForm form) {
        return new DeleteContentPageDescriptionCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Content Catalogs
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createContentCatalog(UserVisitPK userVisitPK, CreateContentCatalogForm form) {
        return new CreateContentCatalogCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getContentCatalog(UserVisitPK userVisitPK, GetContentCatalogForm form) {
        return new GetContentCatalogCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getContentCatalogs(UserVisitPK userVisitPK, GetContentCatalogsForm form) {
        return new GetContentCatalogsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setDefaultContentCatalog(UserVisitPK userVisitPK, SetDefaultContentCatalogForm form) {
        return new SetDefaultContentCatalogCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editContentCatalog(UserVisitPK userVisitPK, EditContentCatalogForm form) {
        return new EditContentCatalogCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteContentCatalog(UserVisitPK userVisitPK, DeleteContentCatalogForm form) {
        return new DeleteContentCatalogCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Content Catalog Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createContentCatalogDescription(UserVisitPK userVisitPK, CreateContentCatalogDescriptionForm form) {
        return new CreateContentCatalogDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getContentCatalogDescription(UserVisitPK userVisitPK, GetContentCatalogDescriptionForm form) {
        return new GetContentCatalogDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getContentCatalogDescriptions(UserVisitPK userVisitPK, GetContentCatalogDescriptionsForm form) {
        return new GetContentCatalogDescriptionsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editContentCatalogDescription(UserVisitPK userVisitPK, EditContentCatalogDescriptionForm form) {
        return new EditContentCatalogDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteContentCatalogDescription(UserVisitPK userVisitPK, DeleteContentCatalogDescriptionForm form) {
        return new DeleteContentCatalogDescriptionCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Content Catalog Items
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult getContentCatalogItem(UserVisitPK userVisitPK, GetContentCatalogItemForm form) {
        return new GetContentCatalogItemCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getContentCatalogItems(UserVisitPK userVisitPK, GetContentCatalogItemsForm form) {
        return new GetContentCatalogItemsCommand(userVisitPK, form).run();
    }

    // --------------------------------------------------------------------------------
    //   Content Categories
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createContentCategory(UserVisitPK userVisitPK, CreateContentCategoryForm form) {
        return new CreateContentCategoryCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getContentCategory(UserVisitPK userVisitPK, GetContentCategoryForm form) {
        return new GetContentCategoryCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getContentCategories(UserVisitPK userVisitPK, GetContentCategoriesForm form) {
        return new GetContentCategoriesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setDefaultContentCategory(UserVisitPK userVisitPK, SetDefaultContentCategoryForm form) {
        return new SetDefaultContentCategoryCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editContentCategory(UserVisitPK userVisitPK, EditContentCategoryForm form) {
        return new EditContentCategoryCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteContentCategory(UserVisitPK userVisitPK, DeleteContentCategoryForm form) {
        return new DeleteContentCategoryCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getContentCategoryChoices(UserVisitPK userVisitPK, GetContentCategoryChoicesForm form) {
        return new GetContentCategoryChoicesCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Content Category Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createContentCategoryDescription(UserVisitPK userVisitPK, CreateContentCategoryDescriptionForm form) {
        return new CreateContentCategoryDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getContentCategoryDescription(UserVisitPK userVisitPK, GetContentCategoryDescriptionForm form) {
        return new GetContentCategoryDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getContentCategoryDescriptions(UserVisitPK userVisitPK, GetContentCategoryDescriptionsForm form) {
        return new GetContentCategoryDescriptionsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editContentCategoryDescription(UserVisitPK userVisitPK, EditContentCategoryDescriptionForm form) {
        return new EditContentCategoryDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteContentCategoryDescription(UserVisitPK userVisitPK, DeleteContentCategoryDescriptionForm form) {
        return new DeleteContentCategoryDescriptionCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Content Category Items
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createContentCategoryItem(UserVisitPK userVisitPK, CreateContentCategoryItemForm form) {
        return new CreateContentCategoryItemCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getContentCategoryItem(UserVisitPK userVisitPK, GetContentCategoryItemForm form) {
        return new GetContentCategoryItemCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getContentCategoryItems(UserVisitPK userVisitPK, GetContentCategoryItemsForm form) {
        return new GetContentCategoryItemsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setDefaultContentCategoryItem(UserVisitPK userVisitPK, SetDefaultContentCategoryItemForm form) {
        return new SetDefaultContentCategoryItemCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editContentCategoryItem(UserVisitPK userVisitPK, EditContentCategoryItemForm form) {
        return new EditContentCategoryItemCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteContentCategoryItem(UserVisitPK userVisitPK, DeleteContentCategoryItemForm form) {
        return new DeleteContentCategoryItemCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Content Forums
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createContentForum(UserVisitPK userVisitPK, CreateContentForumForm form) {
        return new CreateContentForumCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getContentForum(UserVisitPK userVisitPK, GetContentForumForm form) {
        return new GetContentForumCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getContentForums(UserVisitPK userVisitPK, GetContentForumsForm form) {
        return new GetContentForumsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setDefaultContentForum(UserVisitPK userVisitPK, SetDefaultContentForumForm form) {
        return new SetDefaultContentForumCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteContentForum(UserVisitPK userVisitPK, DeleteContentForumForm form) {
        return new DeleteContentForumCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Content Web Addresses
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createContentWebAddress(UserVisitPK userVisitPK, CreateContentWebAddressForm form) {
        return new CreateContentWebAddressCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getContentWebAddress(UserVisitPK userVisitPK, GetContentWebAddressForm form) {
        return new GetContentWebAddressCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getContentWebAddresses(UserVisitPK userVisitPK, GetContentWebAddressesForm form) {
        return new GetContentWebAddressesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editContentWebAddress(UserVisitPK userVisitPK, EditContentWebAddressForm form) {
        return new EditContentWebAddressCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteContentWebAddress(UserVisitPK userVisitPK, DeleteContentWebAddressForm form) {
        return new DeleteContentWebAddressCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Content Web Address Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createContentWebAddressDescription(UserVisitPK userVisitPK, CreateContentWebAddressDescriptionForm form) {
        return new CreateContentWebAddressDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getContentWebAddressDescription(UserVisitPK userVisitPK, GetContentWebAddressDescriptionForm form) {
        return new GetContentWebAddressDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getContentWebAddressDescriptions(UserVisitPK userVisitPK, GetContentWebAddressDescriptionsForm form) {
        return new GetContentWebAddressDescriptionsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editContentWebAddressDescription(UserVisitPK userVisitPK, EditContentWebAddressDescriptionForm form) {
        return new EditContentWebAddressDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteContentWebAddressDescription(UserVisitPK userVisitPK, DeleteContentWebAddressDescriptionForm form) {
        return new DeleteContentWebAddressDescriptionCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Content Web Address Servers
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createContentWebAddressServer(UserVisitPK userVisitPK, CreateContentWebAddressServerForm form) {
        return new CreateContentWebAddressServerCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //  Content Page Areas
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createContentPageArea(UserVisitPK userVisitPK, CreateContentPageAreaForm form) {
        return new CreateContentPageAreaCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getContentPageArea(UserVisitPK userVisitPK, GetContentPageAreaForm form) {
        return new GetContentPageAreaCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getContentPageAreas(UserVisitPK userVisitPK, GetContentPageAreasForm form) {
        return new GetContentPageAreasCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editContentPageArea(UserVisitPK userVisitPK, EditContentPageAreaForm form) {
        return new EditContentPageAreaCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteContentPageArea(UserVisitPK userVisitPK, DeleteContentPageAreaForm form) {
        return new DeleteContentPageAreaCommand(userVisitPK, form).run();
    }
    
}
