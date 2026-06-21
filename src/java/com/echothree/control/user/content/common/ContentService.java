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
import com.echothree.control.user.content.common.result.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.VoidResult;

public interface ContentService
        extends ContentForms {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    String ping();
    
    // --------------------------------------------------------------------------------
    //   Content Page Area Types
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createContentPageAreaType(UserVisitPK userVisitPK, CreateContentPageAreaTypeForm form);

    CommandResult<GetContentPageAreaTypeChoicesResult> getContentPageAreaTypeChoices(UserVisitPK userVisitPK, GetContentPageAreaTypeChoicesForm form);

    CommandResult<GetContentPageAreaTypeResult> getContentPageAreaType(UserVisitPK userVisitPK, GetContentPageAreaTypeForm form);

    CommandResult<GetContentPageAreaTypesResult> getContentPageAreaTypes(UserVisitPK userVisitPK, GetContentPageAreaTypesForm form);
    
    // --------------------------------------------------------------------------------
    //   Content Page Area Type Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createContentPageAreaTypeDescription(UserVisitPK userVisitPK, CreateContentPageAreaTypeDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Content Page Layouts
    // --------------------------------------------------------------------------------
    
    CommandResult<CreateContentPageLayoutResult> createContentPageLayout(UserVisitPK userVisitPK, CreateContentPageLayoutForm form);

    CommandResult<GetContentPageLayoutChoicesResult> getContentPageLayoutChoices(UserVisitPK userVisitPK, GetContentPageLayoutChoicesForm form);

    CommandResult<GetContentPageLayoutResult> getContentPageLayout(UserVisitPK userVisitPK, GetContentPageLayoutForm form);

    CommandResult<GetContentPageLayoutsResult> getContentPageLayouts(UserVisitPK userVisitPK, GetContentPageLayoutsForm form);

    CommandResult<VoidResult> setDefaultContentPageLayout(UserVisitPK userVisitPK, SetDefaultContentPageLayoutForm form);

    CommandResult<EditContentPageLayoutResult> editContentPageLayout(UserVisitPK userVisitPK, EditContentPageLayoutForm form);

    CommandResult<VoidResult> deleteContentPageLayout(UserVisitPK userVisitPK, DeleteContentPageLayoutForm form);
    
    // --------------------------------------------------------------------------------
    //   Content Page Layout Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createContentPageLayoutDescription(UserVisitPK userVisitPK, CreateContentPageLayoutDescriptionForm form);

    CommandResult<GetContentPageLayoutDescriptionResult> getContentPageLayoutDescription(UserVisitPK userVisitPK, GetContentPageLayoutDescriptionForm form);

    CommandResult<GetContentPageLayoutDescriptionsResult> getContentPageLayoutDescriptions(UserVisitPK userVisitPK, GetContentPageLayoutDescriptionsForm form);

    CommandResult<EditContentPageLayoutDescriptionResult> editContentPageLayoutDescription(UserVisitPK userVisitPK, EditContentPageLayoutDescriptionForm form);

    CommandResult<VoidResult> deleteContentPageLayoutDescription(UserVisitPK userVisitPK, DeleteContentPageLayoutDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Content Page Layout Areas
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createContentPageLayoutArea(UserVisitPK userVisitPK, CreateContentPageLayoutAreaForm form);
    
    CommandResult<GetContentPageLayoutAreaResult> getContentPageLayoutArea(UserVisitPK userVisitPK, GetContentPageLayoutAreaForm form);
    
    CommandResult<GetContentPageLayoutAreasResult> getContentPageLayoutAreas(UserVisitPK userVisitPK, GetContentPageLayoutAreasForm form);
    
    // --------------------------------------------------------------------------------
    //   Content Page Layout Area Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createContentPageLayoutAreaDescription(UserVisitPK userVisitPK, CreateContentPageLayoutAreaDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Content Collections
    // --------------------------------------------------------------------------------
    
    CommandResult<CreateContentCollectionResult> createContentCollection(UserVisitPK userVisitPK, CreateContentCollectionForm form);
    
    CommandResult<GetContentCollectionResult> getContentCollection(UserVisitPK userVisitPK, GetContentCollectionForm form);
    
    CommandResult<GetContentCollectionsResult> getContentCollections(UserVisitPK userVisitPK, GetContentCollectionsForm form);
    
    CommandResult<EditContentCollectionResult> editContentCollection(UserVisitPK userVisitPK, EditContentCollectionForm form);
    
    CommandResult<VoidResult> deleteContentCollection(UserVisitPK userVisitPK, DeleteContentCollectionForm form);
    
    CommandResult<GetContentCollectionChoicesResult> getContentCollectionChoices(UserVisitPK userVisitPK, GetContentCollectionChoicesForm form);
    
    // --------------------------------------------------------------------------------
    //   Content Collection Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createContentCollectionDescription(UserVisitPK userVisitPK, CreateContentCollectionDescriptionForm form);

    CommandResult<GetContentCollectionDescriptionResult> getContentCollectionDescription(UserVisitPK userVisitPK, GetContentCollectionDescriptionForm form);

    CommandResult<GetContentCollectionDescriptionsResult> getContentCollectionDescriptions(UserVisitPK userVisitPK, GetContentCollectionDescriptionsForm form);
    
    CommandResult<EditContentCollectionDescriptionResult> editContentCollectionDescription(UserVisitPK userVisitPK, EditContentCollectionDescriptionForm form);
    
    CommandResult<VoidResult> deleteContentCollectionDescription(UserVisitPK userVisitPK, DeleteContentCollectionDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Content Sections
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createContentSection(UserVisitPK userVisitPK, CreateContentSectionForm form);
    
    CommandResult<GetContentSectionResult> getContentSection(UserVisitPK userVisitPK, GetContentSectionForm form);
    
    CommandResult<GetContentSectionsResult> getContentSections(UserVisitPK userVisitPK, GetContentSectionsForm form);
    
    CommandResult<VoidResult> setDefaultContentSection(UserVisitPK userVisitPK, SetDefaultContentSectionForm form);
    
    CommandResult<EditContentSectionResult> editContentSection(UserVisitPK userVisitPK, EditContentSectionForm form);
    
    CommandResult<VoidResult> deleteContentSection(UserVisitPK userVisitPK, DeleteContentSectionForm form);
    
    CommandResult<GetContentSectionChoicesResult> getContentSectionChoices(UserVisitPK userVisitPK, GetContentSectionChoicesForm form);
    
    // --------------------------------------------------------------------------------
    //   Content Section Description
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createContentSectionDescription(UserVisitPK userVisitPK, CreateContentSectionDescriptionForm form);
    
    CommandResult<GetContentSectionDescriptionResult> getContentSectionDescription(UserVisitPK userVisitPK, GetContentSectionDescriptionForm form);

    CommandResult<GetContentSectionDescriptionsResult> getContentSectionDescriptions(UserVisitPK userVisitPK, GetContentSectionDescriptionsForm form);

    CommandResult<EditContentSectionDescriptionResult> editContentSectionDescription(UserVisitPK userVisitPK, EditContentSectionDescriptionForm form);
    
    CommandResult<VoidResult> deleteContentSectionDescription(UserVisitPK userVisitPK, DeleteContentSectionDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //  Content Pages
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createContentPage(UserVisitPK userVisitPK, CreateContentPageForm form);
    
    CommandResult<GetContentPageResult> getContentPage(UserVisitPK userVisitPK, GetContentPageForm form);
    
    CommandResult<GetContentPagesResult> getContentPages(UserVisitPK userVisitPK, GetContentPagesForm form);
    
    CommandResult<VoidResult> setDefaultContentPage(UserVisitPK userVisitPK, SetDefaultContentPageForm form);
    
    CommandResult<EditContentPageResult> editContentPage(UserVisitPK userVisitPK, EditContentPageForm form);
    
    CommandResult<VoidResult> deleteContentPage(UserVisitPK userVisitPK, DeleteContentPageForm form);
    
    // --------------------------------------------------------------------------------
    //   Content Page Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createContentPageDescription(UserVisitPK userVisitPK, CreateContentPageDescriptionForm form);
    
    CommandResult<GetContentPageDescriptionResult> getContentPageDescription(UserVisitPK userVisitPK, GetContentPageDescriptionForm form);

    CommandResult<GetContentPageDescriptionsResult> getContentPageDescriptions(UserVisitPK userVisitPK, GetContentPageDescriptionsForm form);

    CommandResult<EditContentPageDescriptionResult> editContentPageDescription(UserVisitPK userVisitPK, EditContentPageDescriptionForm form);
    
    CommandResult<VoidResult> deleteContentPageDescription(UserVisitPK userVisitPK, DeleteContentPageDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Content Catalogs
    // --------------------------------------------------------------------------------
    
    CommandResult<CreateContentCatalogResult> createContentCatalog(UserVisitPK userVisitPK, CreateContentCatalogForm form);
    
    CommandResult<GetContentCatalogResult> getContentCatalog(UserVisitPK userVisitPK, GetContentCatalogForm form);
    
    CommandResult<GetContentCatalogsResult> getContentCatalogs(UserVisitPK userVisitPK, GetContentCatalogsForm form);
    
    CommandResult<VoidResult> setDefaultContentCatalog(UserVisitPK userVisitPK, SetDefaultContentCatalogForm form);
    
    CommandResult<EditContentCatalogResult> editContentCatalog(UserVisitPK userVisitPK, EditContentCatalogForm form);
    
    CommandResult<VoidResult> deleteContentCatalog(UserVisitPK userVisitPK, DeleteContentCatalogForm form);
    
    // --------------------------------------------------------------------------------
    //   Content Catalog Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createContentCatalogDescription(UserVisitPK userVisitPK, CreateContentCatalogDescriptionForm form);

    CommandResult<GetContentCatalogDescriptionResult> getContentCatalogDescription(UserVisitPK userVisitPK, GetContentCatalogDescriptionForm form);

    CommandResult<GetContentCatalogDescriptionsResult> getContentCatalogDescriptions(UserVisitPK userVisitPK, GetContentCatalogDescriptionsForm form);
    
    CommandResult<EditContentCatalogDescriptionResult> editContentCatalogDescription(UserVisitPK userVisitPK, EditContentCatalogDescriptionForm form);
    
    CommandResult<VoidResult> deleteContentCatalogDescription(UserVisitPK userVisitPK, DeleteContentCatalogDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Content Catalog Items
    // --------------------------------------------------------------------------------

    CommandResult<GetContentCatalogItemResult> getContentCatalogItem(UserVisitPK userVisitPK, GetContentCatalogItemForm form);

    CommandResult<GetContentCatalogItemsResult> getContentCatalogItems(UserVisitPK userVisitPK, GetContentCatalogItemsForm form);

    // --------------------------------------------------------------------------------
    //   Content Categories
    // --------------------------------------------------------------------------------

    CommandResult<CreateContentCategoryResult> createContentCategory(UserVisitPK userVisitPK, CreateContentCategoryForm form);

    CommandResult<GetContentCategoryResult> getContentCategory(UserVisitPK userVisitPK, GetContentCategoryForm form);

    CommandResult<GetContentCategoriesResult> getContentCategories(UserVisitPK userVisitPK, GetContentCategoriesForm form);

    CommandResult<VoidResult> setDefaultContentCategory(UserVisitPK userVisitPK, SetDefaultContentCategoryForm form);

    CommandResult<EditContentCategoryResult> editContentCategory(UserVisitPK userVisitPK, EditContentCategoryForm form);

    CommandResult<VoidResult> deleteContentCategory(UserVisitPK userVisitPK, DeleteContentCategoryForm form);

    CommandResult<GetContentCategoryChoicesResult> getContentCategoryChoices(UserVisitPK userVisitPK, GetContentCategoryChoicesForm form);

    // --------------------------------------------------------------------------------
    //   Content Category Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createContentCategoryDescription(UserVisitPK userVisitPK, CreateContentCategoryDescriptionForm form);
    
    CommandResult<GetContentCategoryDescriptionResult> getContentCategoryDescription(UserVisitPK userVisitPK, GetContentCategoryDescriptionForm form);

    CommandResult<GetContentCategoryDescriptionsResult> getContentCategoryDescriptions(UserVisitPK userVisitPK, GetContentCategoryDescriptionsForm form);

    CommandResult<EditContentCategoryDescriptionResult> editContentCategoryDescription(UserVisitPK userVisitPK, EditContentCategoryDescriptionForm form);
    
    CommandResult<VoidResult> deleteContentCategoryDescription(UserVisitPK userVisitPK, DeleteContentCategoryDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Content Category Items
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createContentCategoryItem(UserVisitPK userVisitPK, CreateContentCategoryItemForm form);
    
    CommandResult<GetContentCategoryItemResult> getContentCategoryItem(UserVisitPK userVisitPK, GetContentCategoryItemForm form);
    
    CommandResult<GetContentCategoryItemsResult> getContentCategoryItems(UserVisitPK userVisitPK, GetContentCategoryItemsForm form);
    
    CommandResult<VoidResult> setDefaultContentCategoryItem(UserVisitPK userVisitPK, SetDefaultContentCategoryItemForm form);
    
    CommandResult<EditContentCategoryItemResult> editContentCategoryItem(UserVisitPK userVisitPK, EditContentCategoryItemForm form);
    
    CommandResult<VoidResult> deleteContentCategoryItem(UserVisitPK userVisitPK, DeleteContentCategoryItemForm form);
    
    // --------------------------------------------------------------------------------
    //   Content Forums
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createContentForum(UserVisitPK userVisitPK, CreateContentForumForm form);
    
    CommandResult<GetContentForumResult> getContentForum(UserVisitPK userVisitPK, GetContentForumForm form);
    
    CommandResult<GetContentForumsResult> getContentForums(UserVisitPK userVisitPK, GetContentForumsForm form);
    
    CommandResult<VoidResult> setDefaultContentForum(UserVisitPK userVisitPK, SetDefaultContentForumForm form);
    
    CommandResult<VoidResult> deleteContentForum(UserVisitPK userVisitPK, DeleteContentForumForm form);
    
    // --------------------------------------------------------------------------------
    //   Content Web Addresses
    // --------------------------------------------------------------------------------
    
    CommandResult<CreateContentWebAddressResult> createContentWebAddress(UserVisitPK userVisitPK, CreateContentWebAddressForm form);
    
    CommandResult<GetContentWebAddressResult> getContentWebAddress(UserVisitPK userVisitPK, GetContentWebAddressForm form);
    
    CommandResult<GetContentWebAddressesResult> getContentWebAddresses(UserVisitPK userVisitPK, GetContentWebAddressesForm form);
    
    CommandResult<EditContentWebAddressResult> editContentWebAddress(UserVisitPK userVisitPK, EditContentWebAddressForm form);
    
    CommandResult<VoidResult> deleteContentWebAddress(UserVisitPK userVisitPK, DeleteContentWebAddressForm form);
    
    // --------------------------------------------------------------------------------
    //   Content Web Address Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createContentWebAddressDescription(UserVisitPK userVisitPK, CreateContentWebAddressDescriptionForm form);
    
    CommandResult<GetContentWebAddressDescriptionResult> getContentWebAddressDescription(UserVisitPK userVisitPK, GetContentWebAddressDescriptionForm form);

    CommandResult<GetContentWebAddressDescriptionsResult> getContentWebAddressDescriptions(UserVisitPK userVisitPK, GetContentWebAddressDescriptionsForm form);

    CommandResult<EditContentWebAddressDescriptionResult> editContentWebAddressDescription(UserVisitPK userVisitPK, EditContentWebAddressDescriptionForm form);
    
    CommandResult<VoidResult> deleteContentWebAddressDescription(UserVisitPK userVisitPK, DeleteContentWebAddressDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Content Web Address Servers
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createContentWebAddressServer(UserVisitPK userVisitPK, CreateContentWebAddressServerForm form);
    
    // --------------------------------------------------------------------------------
    //  Content Page Areas
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createContentPageArea(UserVisitPK userVisitPK, CreateContentPageAreaForm form);
 
    CommandResult<GetContentPageAreaResult> getContentPageArea(UserVisitPK userVisitPK, GetContentPageAreaForm form);

    CommandResult<GetContentPageAreasResult> getContentPageAreas(UserVisitPK userVisitPK, GetContentPageAreasForm form);

    CommandResult<EditContentPageAreaResult> editContentPageArea(UserVisitPK userVisitPK, EditContentPageAreaForm form);
    
    CommandResult<VoidResult> deleteContentPageArea(UserVisitPK userVisitPK, DeleteContentPageAreaForm form);
    
}
