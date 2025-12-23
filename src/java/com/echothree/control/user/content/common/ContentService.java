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

package com.echothree.control.user.content.common;

import com.echothree.control.user.content.common.form.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;

public interface ContentService
        extends ContentForms {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    String ping();
    
    // --------------------------------------------------------------------------------
    //   Content Page Area Types
    // --------------------------------------------------------------------------------
    
    CommandResult createContentPageAreaType(UserVisitPK userVisitPK, CreateContentPageAreaTypeForm form);

    CommandResult getContentPageAreaTypeChoices(UserVisitPK userVisitPK, GetContentPageAreaTypeChoicesForm form);

    CommandResult getContentPageAreaType(UserVisitPK userVisitPK, GetContentPageAreaTypeForm form);

    CommandResult getContentPageAreaTypes(UserVisitPK userVisitPK, GetContentPageAreaTypesForm form);
    
    // --------------------------------------------------------------------------------
    //   Content Page Area Type Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult createContentPageAreaTypeDescription(UserVisitPK userVisitPK, CreateContentPageAreaTypeDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Content Page Layouts
    // --------------------------------------------------------------------------------
    
    CommandResult createContentPageLayout(UserVisitPK userVisitPK, CreateContentPageLayoutForm form);

    CommandResult getContentPageLayoutChoices(UserVisitPK userVisitPK, GetContentPageLayoutChoicesForm form);

    CommandResult getContentPageLayout(UserVisitPK userVisitPK, GetContentPageLayoutForm form);

    CommandResult getContentPageLayouts(UserVisitPK userVisitPK, GetContentPageLayoutsForm form);

    CommandResult setDefaultContentPageLayout(UserVisitPK userVisitPK, SetDefaultContentPageLayoutForm form);

    CommandResult editContentPageLayout(UserVisitPK userVisitPK, EditContentPageLayoutForm form);

    CommandResult deleteContentPageLayout(UserVisitPK userVisitPK, DeleteContentPageLayoutForm form);
    
    // --------------------------------------------------------------------------------
    //   Content Page Layout Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult createContentPageLayoutDescription(UserVisitPK userVisitPK, CreateContentPageLayoutDescriptionForm form);

    CommandResult getContentPageLayoutDescription(UserVisitPK userVisitPK, GetContentPageLayoutDescriptionForm form);

    CommandResult getContentPageLayoutDescriptions(UserVisitPK userVisitPK, GetContentPageLayoutDescriptionsForm form);

    CommandResult editContentPageLayoutDescription(UserVisitPK userVisitPK, EditContentPageLayoutDescriptionForm form);

    CommandResult deleteContentPageLayoutDescription(UserVisitPK userVisitPK, DeleteContentPageLayoutDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Content Page Layout Areas
    // --------------------------------------------------------------------------------
    
    CommandResult createContentPageLayoutArea(UserVisitPK userVisitPK, CreateContentPageLayoutAreaForm form);
    
    CommandResult getContentPageLayoutArea(UserVisitPK userVisitPK, GetContentPageLayoutAreaForm form);
    
    CommandResult getContentPageLayoutAreas(UserVisitPK userVisitPK, GetContentPageLayoutAreasForm form);
    
    // --------------------------------------------------------------------------------
    //   Content Page Layout Area Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult createContentPageLayoutAreaDescription(UserVisitPK userVisitPK, CreateContentPageLayoutAreaDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Content Collections
    // --------------------------------------------------------------------------------
    
    CommandResult createContentCollection(UserVisitPK userVisitPK, CreateContentCollectionForm form);
    
    CommandResult getContentCollection(UserVisitPK userVisitPK, GetContentCollectionForm form);
    
    CommandResult getContentCollections(UserVisitPK userVisitPK, GetContentCollectionsForm form);
    
    CommandResult editContentCollection(UserVisitPK userVisitPK, EditContentCollectionForm form);
    
    CommandResult deleteContentCollection(UserVisitPK userVisitPK, DeleteContentCollectionForm form);
    
    CommandResult getContentCollectionChoices(UserVisitPK userVisitPK, GetContentCollectionChoicesForm form);
    
    // --------------------------------------------------------------------------------
    //   Content Collection Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult createContentCollectionDescription(UserVisitPK userVisitPK, CreateContentCollectionDescriptionForm form);

    CommandResult getContentCollectionDescription(UserVisitPK userVisitPK, GetContentCollectionDescriptionForm form);

    CommandResult getContentCollectionDescriptions(UserVisitPK userVisitPK, GetContentCollectionDescriptionsForm form);
    
    CommandResult editContentCollectionDescription(UserVisitPK userVisitPK, EditContentCollectionDescriptionForm form);
    
    CommandResult deleteContentCollectionDescription(UserVisitPK userVisitPK, DeleteContentCollectionDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Content Sections
    // --------------------------------------------------------------------------------
    
    CommandResult createContentSection(UserVisitPK userVisitPK, CreateContentSectionForm form);
    
    CommandResult getContentSection(UserVisitPK userVisitPK, GetContentSectionForm form);
    
    CommandResult getContentSections(UserVisitPK userVisitPK, GetContentSectionsForm form);
    
    CommandResult setDefaultContentSection(UserVisitPK userVisitPK, SetDefaultContentSectionForm form);
    
    CommandResult editContentSection(UserVisitPK userVisitPK, EditContentSectionForm form);
    
    CommandResult deleteContentSection(UserVisitPK userVisitPK, DeleteContentSectionForm form);
    
    CommandResult getContentSectionChoices(UserVisitPK userVisitPK, GetContentSectionChoicesForm form);
    
    // --------------------------------------------------------------------------------
    //   Content Section Description
    // --------------------------------------------------------------------------------
    
    CommandResult createContentSectionDescription(UserVisitPK userVisitPK, CreateContentSectionDescriptionForm form);
    
    CommandResult getContentSectionDescription(UserVisitPK userVisitPK, GetContentSectionDescriptionForm form);

    CommandResult getContentSectionDescriptions(UserVisitPK userVisitPK, GetContentSectionDescriptionsForm form);

    CommandResult editContentSectionDescription(UserVisitPK userVisitPK, EditContentSectionDescriptionForm form);
    
    CommandResult deleteContentSectionDescription(UserVisitPK userVisitPK, DeleteContentSectionDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //  Content Pages
    // --------------------------------------------------------------------------------
    
    CommandResult createContentPage(UserVisitPK userVisitPK, CreateContentPageForm form);
    
    CommandResult getContentPage(UserVisitPK userVisitPK, GetContentPageForm form);
    
    CommandResult getContentPages(UserVisitPK userVisitPK, GetContentPagesForm form);
    
    CommandResult setDefaultContentPage(UserVisitPK userVisitPK, SetDefaultContentPageForm form);
    
    CommandResult editContentPage(UserVisitPK userVisitPK, EditContentPageForm form);
    
    CommandResult deleteContentPage(UserVisitPK userVisitPK, DeleteContentPageForm form);
    
    // --------------------------------------------------------------------------------
    //   Content Page Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult createContentPageDescription(UserVisitPK userVisitPK, CreateContentPageDescriptionForm form);
    
    CommandResult getContentPageDescription(UserVisitPK userVisitPK, GetContentPageDescriptionForm form);

    CommandResult getContentPageDescriptions(UserVisitPK userVisitPK, GetContentPageDescriptionsForm form);

    CommandResult editContentPageDescription(UserVisitPK userVisitPK, EditContentPageDescriptionForm form);
    
    CommandResult deleteContentPageDescription(UserVisitPK userVisitPK, DeleteContentPageDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Content Catalogs
    // --------------------------------------------------------------------------------
    
    CommandResult createContentCatalog(UserVisitPK userVisitPK, CreateContentCatalogForm form);
    
    CommandResult getContentCatalog(UserVisitPK userVisitPK, GetContentCatalogForm form);
    
    CommandResult getContentCatalogs(UserVisitPK userVisitPK, GetContentCatalogsForm form);
    
    CommandResult setDefaultContentCatalog(UserVisitPK userVisitPK, SetDefaultContentCatalogForm form);
    
    CommandResult editContentCatalog(UserVisitPK userVisitPK, EditContentCatalogForm form);
    
    CommandResult deleteContentCatalog(UserVisitPK userVisitPK, DeleteContentCatalogForm form);
    
    // --------------------------------------------------------------------------------
    //   Content Catalog Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult createContentCatalogDescription(UserVisitPK userVisitPK, CreateContentCatalogDescriptionForm form);

    CommandResult getContentCatalogDescription(UserVisitPK userVisitPK, GetContentCatalogDescriptionForm form);

    CommandResult getContentCatalogDescriptions(UserVisitPK userVisitPK, GetContentCatalogDescriptionsForm form);
    
    CommandResult editContentCatalogDescription(UserVisitPK userVisitPK, EditContentCatalogDescriptionForm form);
    
    CommandResult deleteContentCatalogDescription(UserVisitPK userVisitPK, DeleteContentCatalogDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Content Catalog Items
    // --------------------------------------------------------------------------------

    CommandResult getContentCatalogItem(UserVisitPK userVisitPK, GetContentCatalogItemForm form);

    CommandResult getContentCatalogItems(UserVisitPK userVisitPK, GetContentCatalogItemsForm form);

    // --------------------------------------------------------------------------------
    //   Content Categories
    // --------------------------------------------------------------------------------

    CommandResult createContentCategory(UserVisitPK userVisitPK, CreateContentCategoryForm form);

    CommandResult getContentCategory(UserVisitPK userVisitPK, GetContentCategoryForm form);

    CommandResult getContentCategories(UserVisitPK userVisitPK, GetContentCategoriesForm form);

    CommandResult setDefaultContentCategory(UserVisitPK userVisitPK, SetDefaultContentCategoryForm form);

    CommandResult editContentCategory(UserVisitPK userVisitPK, EditContentCategoryForm form);

    CommandResult deleteContentCategory(UserVisitPK userVisitPK, DeleteContentCategoryForm form);

    CommandResult getContentCategoryChoices(UserVisitPK userVisitPK, GetContentCategoryChoicesForm form);

    // --------------------------------------------------------------------------------
    //   Content Category Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult createContentCategoryDescription(UserVisitPK userVisitPK, CreateContentCategoryDescriptionForm form);
    
    CommandResult getContentCategoryDescription(UserVisitPK userVisitPK, GetContentCategoryDescriptionForm form);

    CommandResult getContentCategoryDescriptions(UserVisitPK userVisitPK, GetContentCategoryDescriptionsForm form);

    CommandResult editContentCategoryDescription(UserVisitPK userVisitPK, EditContentCategoryDescriptionForm form);
    
    CommandResult deleteContentCategoryDescription(UserVisitPK userVisitPK, DeleteContentCategoryDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Content Category Items
    // --------------------------------------------------------------------------------
    
    CommandResult createContentCategoryItem(UserVisitPK userVisitPK, CreateContentCategoryItemForm form);
    
    CommandResult getContentCategoryItem(UserVisitPK userVisitPK, GetContentCategoryItemForm form);
    
    CommandResult getContentCategoryItems(UserVisitPK userVisitPK, GetContentCategoryItemsForm form);
    
    CommandResult setDefaultContentCategoryItem(UserVisitPK userVisitPK, SetDefaultContentCategoryItemForm form);
    
    CommandResult editContentCategoryItem(UserVisitPK userVisitPK, EditContentCategoryItemForm form);
    
    CommandResult deleteContentCategoryItem(UserVisitPK userVisitPK, DeleteContentCategoryItemForm form);
    
    // --------------------------------------------------------------------------------
    //   Content Forums
    // --------------------------------------------------------------------------------
    
    CommandResult createContentForum(UserVisitPK userVisitPK, CreateContentForumForm form);
    
    CommandResult getContentForum(UserVisitPK userVisitPK, GetContentForumForm form);
    
    CommandResult getContentForums(UserVisitPK userVisitPK, GetContentForumsForm form);
    
    CommandResult setDefaultContentForum(UserVisitPK userVisitPK, SetDefaultContentForumForm form);
    
    CommandResult deleteContentForum(UserVisitPK userVisitPK, DeleteContentForumForm form);
    
    // --------------------------------------------------------------------------------
    //   Content Web Addresses
    // --------------------------------------------------------------------------------
    
    CommandResult createContentWebAddress(UserVisitPK userVisitPK, CreateContentWebAddressForm form);
    
    CommandResult getContentWebAddress(UserVisitPK userVisitPK, GetContentWebAddressForm form);
    
    CommandResult getContentWebAddresses(UserVisitPK userVisitPK, GetContentWebAddressesForm form);
    
    CommandResult editContentWebAddress(UserVisitPK userVisitPK, EditContentWebAddressForm form);
    
    CommandResult deleteContentWebAddress(UserVisitPK userVisitPK, DeleteContentWebAddressForm form);
    
    // --------------------------------------------------------------------------------
    //   Content Web Address Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult createContentWebAddressDescription(UserVisitPK userVisitPK, CreateContentWebAddressDescriptionForm form);
    
    CommandResult getContentWebAddressDescription(UserVisitPK userVisitPK, GetContentWebAddressDescriptionForm form);

    CommandResult getContentWebAddressDescriptions(UserVisitPK userVisitPK, GetContentWebAddressDescriptionsForm form);

    CommandResult editContentWebAddressDescription(UserVisitPK userVisitPK, EditContentWebAddressDescriptionForm form);
    
    CommandResult deleteContentWebAddressDescription(UserVisitPK userVisitPK, DeleteContentWebAddressDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Content Web Address Servers
    // --------------------------------------------------------------------------------
    
    CommandResult createContentWebAddressServer(UserVisitPK userVisitPK, CreateContentWebAddressServerForm form);
    
    // --------------------------------------------------------------------------------
    //  Content Page Areas
    // --------------------------------------------------------------------------------
    
    CommandResult createContentPageArea(UserVisitPK userVisitPK, CreateContentPageAreaForm form);
 
    CommandResult getContentPageArea(UserVisitPK userVisitPK, GetContentPageAreaForm form);

    CommandResult getContentPageAreas(UserVisitPK userVisitPK, GetContentPageAreasForm form);

    CommandResult editContentPageArea(UserVisitPK userVisitPK, EditContentPageAreaForm form);
    
    CommandResult deleteContentPageArea(UserVisitPK userVisitPK, DeleteContentPageAreaForm form);
    
}
