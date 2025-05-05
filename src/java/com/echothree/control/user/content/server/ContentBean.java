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
        return new CreateContentPageAreaTypeCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Content Page Area Type Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createContentPageAreaTypeDescription(UserVisitPK userVisitPK, CreateContentPageAreaTypeDescriptionForm form) {
        return new CreateContentPageAreaTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getContentPageAreaTypeChoices(UserVisitPK userVisitPK, GetContentPageAreaTypeChoicesForm form) {
        return new GetContentPageAreaTypeChoicesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getContentPageAreaType(UserVisitPK userVisitPK, GetContentPageAreaTypeForm form) {
        return new GetContentPageAreaTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getContentPageAreaTypes(UserVisitPK userVisitPK, GetContentPageAreaTypesForm form) {
        return new GetContentPageAreaTypesCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Content Page Layouts
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createContentPageLayout(UserVisitPK userVisitPK, CreateContentPageLayoutForm form) {
        return new CreateContentPageLayoutCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getContentPageLayoutChoices(UserVisitPK userVisitPK, GetContentPageLayoutChoicesForm form) {
        return new GetContentPageLayoutChoicesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getContentPageLayout(UserVisitPK userVisitPK, GetContentPageLayoutForm form) {
        return new GetContentPageLayoutCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getContentPageLayouts(UserVisitPK userVisitPK, GetContentPageLayoutsForm form) {
        return new GetContentPageLayoutsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultContentPageLayout(UserVisitPK userVisitPK, SetDefaultContentPageLayoutForm form) {
        return new SetDefaultContentPageLayoutCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editContentPageLayout(UserVisitPK userVisitPK, EditContentPageLayoutForm form) {
        return new EditContentPageLayoutCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteContentPageLayout(UserVisitPK userVisitPK, DeleteContentPageLayoutForm form) {
        return new DeleteContentPageLayoutCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Content Page Layout Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createContentPageLayoutDescription(UserVisitPK userVisitPK, CreateContentPageLayoutDescriptionForm form) {
        return new CreateContentPageLayoutDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getContentPageLayoutDescription(UserVisitPK userVisitPK, GetContentPageLayoutDescriptionForm form) {
        return new GetContentPageLayoutDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getContentPageLayoutDescriptions(UserVisitPK userVisitPK, GetContentPageLayoutDescriptionsForm form) {
        return new GetContentPageLayoutDescriptionsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editContentPageLayoutDescription(UserVisitPK userVisitPK, EditContentPageLayoutDescriptionForm form) {
        return new EditContentPageLayoutDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteContentPageLayoutDescription(UserVisitPK userVisitPK, DeleteContentPageLayoutDescriptionForm form) {
        return new DeleteContentPageLayoutDescriptionCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Content Page Layout Areas
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createContentPageLayoutArea(UserVisitPK userVisitPK, CreateContentPageLayoutAreaForm form) {
        return new CreateContentPageLayoutAreaCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getContentPageLayoutArea(UserVisitPK userVisitPK, GetContentPageLayoutAreaForm form) {
        return new GetContentPageLayoutAreaCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getContentPageLayoutAreas(UserVisitPK userVisitPK, GetContentPageLayoutAreasForm form) {
        return new GetContentPageLayoutAreasCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Content Page Layout Area Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createContentPageLayoutAreaDescription(UserVisitPK userVisitPK, CreateContentPageLayoutAreaDescriptionForm form) {
        return new CreateContentPageLayoutAreaDescriptionCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Content Collections
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createContentCollection(UserVisitPK userVisitPK, CreateContentCollectionForm form) {
        return new CreateContentCollectionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getContentCollection(UserVisitPK userVisitPK, GetContentCollectionForm form) {
        return new GetContentCollectionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getContentCollections(UserVisitPK userVisitPK, GetContentCollectionsForm form) {
        return new GetContentCollectionsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editContentCollection(UserVisitPK userVisitPK, EditContentCollectionForm form) {
        return new EditContentCollectionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteContentCollection(UserVisitPK userVisitPK, DeleteContentCollectionForm form) {
        return new DeleteContentCollectionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getContentCollectionChoices(UserVisitPK userVisitPK, GetContentCollectionChoicesForm form) {
        return new GetContentCollectionChoicesCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Content Collection Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createContentCollectionDescription(UserVisitPK userVisitPK, CreateContentCollectionDescriptionForm form) {
        return new CreateContentCollectionDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getContentCollectionDescription(UserVisitPK userVisitPK, GetContentCollectionDescriptionForm form) {
        return new GetContentCollectionDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getContentCollectionDescriptions(UserVisitPK userVisitPK, GetContentCollectionDescriptionsForm form) {
        return new GetContentCollectionDescriptionsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editContentCollectionDescription(UserVisitPK userVisitPK, EditContentCollectionDescriptionForm form) {
        return new EditContentCollectionDescriptionCommand().run(userVisitPK, form);
    }
    
    
    @Override
    public CommandResult deleteContentCollectionDescription(UserVisitPK userVisitPK, DeleteContentCollectionDescriptionForm form) {
        return new DeleteContentCollectionDescriptionCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Content Sections
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createContentSection(UserVisitPK userVisitPK, CreateContentSectionForm form) {
        return new CreateContentSectionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getContentSection(UserVisitPK userVisitPK, GetContentSectionForm form) {
        return new GetContentSectionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getContentSections(UserVisitPK userVisitPK, GetContentSectionsForm form) {
        return new GetContentSectionsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultContentSection(UserVisitPK userVisitPK, SetDefaultContentSectionForm form) {
        return new SetDefaultContentSectionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editContentSection(UserVisitPK userVisitPK, EditContentSectionForm form) {
        return new EditContentSectionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteContentSection(UserVisitPK userVisitPK, DeleteContentSectionForm form) {
        return new DeleteContentSectionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getContentSectionChoices(UserVisitPK userVisitPK, GetContentSectionChoicesForm form) {
        return new GetContentSectionChoicesCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Content Section Description
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createContentSectionDescription(UserVisitPK userVisitPK, CreateContentSectionDescriptionForm form) {
        return new CreateContentSectionDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getContentSectionDescription(UserVisitPK userVisitPK, GetContentSectionDescriptionForm form) {
        return new GetContentSectionDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getContentSectionDescriptions(UserVisitPK userVisitPK, GetContentSectionDescriptionsForm form) {
        return new GetContentSectionDescriptionsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editContentSectionDescription(UserVisitPK userVisitPK, EditContentSectionDescriptionForm form) {
        return new EditContentSectionDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteContentSectionDescription(UserVisitPK userVisitPK, DeleteContentSectionDescriptionForm form) {
        return new DeleteContentSectionDescriptionCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //  Content Pages
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createContentPage(UserVisitPK userVisitPK, CreateContentPageForm form) {
        return new CreateContentPageCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getContentPage(UserVisitPK userVisitPK, GetContentPageForm form) {
        return new GetContentPageCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getContentPages(UserVisitPK userVisitPK, GetContentPagesForm form) {
        return new GetContentPagesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultContentPage(UserVisitPK userVisitPK, SetDefaultContentPageForm form) {
        return new SetDefaultContentPageCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editContentPage(UserVisitPK userVisitPK, EditContentPageForm form) {
        return new EditContentPageCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteContentPage(UserVisitPK userVisitPK, DeleteContentPageForm form) {
        return new DeleteContentPageCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Content Page Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createContentPageDescription(UserVisitPK userVisitPK, CreateContentPageDescriptionForm form) {
        return new CreateContentPageDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getContentPageDescription(UserVisitPK userVisitPK, GetContentPageDescriptionForm form) {
        return new GetContentPageDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getContentPageDescriptions(UserVisitPK userVisitPK, GetContentPageDescriptionsForm form) {
        return new GetContentPageDescriptionsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editContentPageDescription(UserVisitPK userVisitPK, EditContentPageDescriptionForm form) {
        return new EditContentPageDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteContentPageDescription(UserVisitPK userVisitPK, DeleteContentPageDescriptionForm form) {
        return new DeleteContentPageDescriptionCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Content Catalogs
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createContentCatalog(UserVisitPK userVisitPK, CreateContentCatalogForm form) {
        return new CreateContentCatalogCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getContentCatalog(UserVisitPK userVisitPK, GetContentCatalogForm form) {
        return new GetContentCatalogCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getContentCatalogs(UserVisitPK userVisitPK, GetContentCatalogsForm form) {
        return new GetContentCatalogsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultContentCatalog(UserVisitPK userVisitPK, SetDefaultContentCatalogForm form) {
        return new SetDefaultContentCatalogCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editContentCatalog(UserVisitPK userVisitPK, EditContentCatalogForm form) {
        return new EditContentCatalogCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteContentCatalog(UserVisitPK userVisitPK, DeleteContentCatalogForm form) {
        return new DeleteContentCatalogCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Content Catalog Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createContentCatalogDescription(UserVisitPK userVisitPK, CreateContentCatalogDescriptionForm form) {
        return new CreateContentCatalogDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getContentCatalogDescription(UserVisitPK userVisitPK, GetContentCatalogDescriptionForm form) {
        return new GetContentCatalogDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getContentCatalogDescriptions(UserVisitPK userVisitPK, GetContentCatalogDescriptionsForm form) {
        return new GetContentCatalogDescriptionsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editContentCatalogDescription(UserVisitPK userVisitPK, EditContentCatalogDescriptionForm form) {
        return new EditContentCatalogDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteContentCatalogDescription(UserVisitPK userVisitPK, DeleteContentCatalogDescriptionForm form) {
        return new DeleteContentCatalogDescriptionCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Content Catalog Items
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult getContentCatalogItem(UserVisitPK userVisitPK, GetContentCatalogItemForm form) {
        return new GetContentCatalogItemCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getContentCatalogItems(UserVisitPK userVisitPK, GetContentCatalogItemsForm form) {
        return new GetContentCatalogItemsCommand().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Content Categories
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createContentCategory(UserVisitPK userVisitPK, CreateContentCategoryForm form) {
        return new CreateContentCategoryCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getContentCategory(UserVisitPK userVisitPK, GetContentCategoryForm form) {
        return new GetContentCategoryCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getContentCategories(UserVisitPK userVisitPK, GetContentCategoriesForm form) {
        return new GetContentCategoriesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultContentCategory(UserVisitPK userVisitPK, SetDefaultContentCategoryForm form) {
        return new SetDefaultContentCategoryCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editContentCategory(UserVisitPK userVisitPK, EditContentCategoryForm form) {
        return new EditContentCategoryCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteContentCategory(UserVisitPK userVisitPK, DeleteContentCategoryForm form) {
        return new DeleteContentCategoryCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getContentCategoryChoices(UserVisitPK userVisitPK, GetContentCategoryChoicesForm form) {
        return new GetContentCategoryChoicesCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Content Category Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createContentCategoryDescription(UserVisitPK userVisitPK, CreateContentCategoryDescriptionForm form) {
        return new CreateContentCategoryDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getContentCategoryDescription(UserVisitPK userVisitPK, GetContentCategoryDescriptionForm form) {
        return new GetContentCategoryDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getContentCategoryDescriptions(UserVisitPK userVisitPK, GetContentCategoryDescriptionsForm form) {
        return new GetContentCategoryDescriptionsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editContentCategoryDescription(UserVisitPK userVisitPK, EditContentCategoryDescriptionForm form) {
        return new EditContentCategoryDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteContentCategoryDescription(UserVisitPK userVisitPK, DeleteContentCategoryDescriptionForm form) {
        return new DeleteContentCategoryDescriptionCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Content Category Items
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createContentCategoryItem(UserVisitPK userVisitPK, CreateContentCategoryItemForm form) {
        return new CreateContentCategoryItemCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getContentCategoryItem(UserVisitPK userVisitPK, GetContentCategoryItemForm form) {
        return new GetContentCategoryItemCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getContentCategoryItems(UserVisitPK userVisitPK, GetContentCategoryItemsForm form) {
        return new GetContentCategoryItemsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultContentCategoryItem(UserVisitPK userVisitPK, SetDefaultContentCategoryItemForm form) {
        return new SetDefaultContentCategoryItemCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editContentCategoryItem(UserVisitPK userVisitPK, EditContentCategoryItemForm form) {
        return new EditContentCategoryItemCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteContentCategoryItem(UserVisitPK userVisitPK, DeleteContentCategoryItemForm form) {
        return new DeleteContentCategoryItemCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Content Forums
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createContentForum(UserVisitPK userVisitPK, CreateContentForumForm form) {
        return new CreateContentForumCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getContentForum(UserVisitPK userVisitPK, GetContentForumForm form) {
        return new GetContentForumCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getContentForums(UserVisitPK userVisitPK, GetContentForumsForm form) {
        return new GetContentForumsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultContentForum(UserVisitPK userVisitPK, SetDefaultContentForumForm form) {
        return new SetDefaultContentForumCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteContentForum(UserVisitPK userVisitPK, DeleteContentForumForm form) {
        return new DeleteContentForumCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Content Web Addresses
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createContentWebAddress(UserVisitPK userVisitPK, CreateContentWebAddressForm form) {
        return new CreateContentWebAddressCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getContentWebAddress(UserVisitPK userVisitPK, GetContentWebAddressForm form) {
        return new GetContentWebAddressCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getContentWebAddresses(UserVisitPK userVisitPK, GetContentWebAddressesForm form) {
        return new GetContentWebAddressesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editContentWebAddress(UserVisitPK userVisitPK, EditContentWebAddressForm form) {
        return new EditContentWebAddressCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteContentWebAddress(UserVisitPK userVisitPK, DeleteContentWebAddressForm form) {
        return new DeleteContentWebAddressCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Content Web Address Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createContentWebAddressDescription(UserVisitPK userVisitPK, CreateContentWebAddressDescriptionForm form) {
        return new CreateContentWebAddressDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getContentWebAddressDescription(UserVisitPK userVisitPK, GetContentWebAddressDescriptionForm form) {
        return new GetContentWebAddressDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getContentWebAddressDescriptions(UserVisitPK userVisitPK, GetContentWebAddressDescriptionsForm form) {
        return new GetContentWebAddressDescriptionsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editContentWebAddressDescription(UserVisitPK userVisitPK, EditContentWebAddressDescriptionForm form) {
        return new EditContentWebAddressDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteContentWebAddressDescription(UserVisitPK userVisitPK, DeleteContentWebAddressDescriptionForm form) {
        return new DeleteContentWebAddressDescriptionCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Content Web Address Servers
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createContentWebAddressServer(UserVisitPK userVisitPK, CreateContentWebAddressServerForm form) {
        return new CreateContentWebAddressServerCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //  Content Page Areas
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createContentPageArea(UserVisitPK userVisitPK, CreateContentPageAreaForm form) {
        return new CreateContentPageAreaCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getContentPageArea(UserVisitPK userVisitPK, GetContentPageAreaForm form) {
        return new GetContentPageAreaCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getContentPageAreas(UserVisitPK userVisitPK, GetContentPageAreasForm form) {
        return new GetContentPageAreasCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editContentPageArea(UserVisitPK userVisitPK, EditContentPageAreaForm form) {
        return new EditContentPageAreaCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteContentPageArea(UserVisitPK userVisitPK, DeleteContentPageAreaForm form) {
        return new DeleteContentPageAreaCommand().run(userVisitPK, form);
    }
    
}
