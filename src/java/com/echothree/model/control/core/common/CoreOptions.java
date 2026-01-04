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

package com.echothree.model.control.core.common;

public interface CoreOptions {
    
    String EntityBlobAttributeIncludeBlob = "EntityBlobAttributeIncludeBlob";
    String EntityBlobAttributeIncludeETag = "EntityBlobAttributeIncludeETag";
    
    String EntityClobAttributeIncludeClob = "EntityClobAttributeIncludeClob";
    String EntityClobAttributeIncludeETag = "EntityClobAttributeIncludeETag";

    String EntityStringDefaultIncludeString = "EntityStringDefaultIncludeString";

    String EntityStringAttributeIncludeString = "EntityStringAttributeIncludeString";
    
    String EntityAttributeGroupIncludeEntityAttributes = "EntityAttributeGroupIncludeEntityAttributes";
    
    String EntityAttributeIncludeEntityListItems = "EntityAttributeIncludeEntityListItems";
    String EntityAttributeIncludeEntityListItemsCount = "EntityAttributeIncludeEntityListItemsCount";
    String EntityAttributeIncludeEntityAttributeEntityTypes = "EntityAttributeIncludeEntityAttributeEntityTypes";
    String EntityAttributeIncludeEntityAttributeEntityTypesCount = "EntityAttributeIncludeEntityAttributeEntityTypesCount";
    String EntityAttributeIncludeDefault = "EntityAttributeIncludeDefault";
    String EntityAttributeIncludeValue = "EntityAttributeIncludeValue";

    String EntityInstanceIncludeEntityAppearance = "EntityInstanceIncludeEntityAppearance";
    String EntityInstanceIncludeEntityVisit = "EntityInstanceIncludeEntityVisit";
    String EntityInstanceIncludeNames = "EntityInstanceIncludeNames";
    String EntityInstanceIncludeUuidIfAvailable = "EntityInstanceIncludeUuidIfAvailable";
    String EntityInstanceIncludeEntityAttributeGroups = "EntityInstanceIncludeEntityAttributeGroups";
    String EntityInstanceIncludeTagScopes = "EntityInstanceIncludeTagScopes";

    String EntityListItemIncludeEntityAttributeGroups = "EntityListItemIncludeEntityAttributeGroups";
    String EntityListItemIncludeTagScopes = "EntityListItemIncludeTagScopes";

    String CommandMessageIncludeTranslations = "CommandMessageIncludeTranslations";

    String CacheEntryIncludeClob = "CacheEntryIncludeClob";
    String CacheEntryIncludeBlob = "CacheEntryIncludeBlob";
    String CacheEntryIncludeCacheEntryDependencies = "CacheEntryIncludeCacheEntryDependencies";

    String ApplicationIncludeUuid = "ApplicationIncludeUuid";
    
    String AppearanceIncludeTextDecorations = "AppearanceIncludeTextDecorations";
    String AppearanceIncludeTextTransformations = "AppearanceIncludeTextTransformations";
    
    String MimeTypeIncludeMimeTypeFileExtensions = "MimeTypeIncludeMimeTypeFileExtensions";

    String EntityTypeIncludeIndexTypes = "EntityTypeIncludeIndexTypes";
    String EntityTypeIncludeIndexTypesCount = "EntityTypeIncludeIndexTypesCount";
    String EntityTypeIncludeEntityAliasTypes = "EntityTypeIncludeEntityAliasTypes";
    String EntityTypeIncludeEntityAttributes = "EntityTypeIncludeEntityAttributes";
    String EntityTypeIncludeCommentTypes = "EntityTypeIncludeCommentTypes";
    String EntityTypeIncludeRatingTypes = "EntityTypeIncludeRatingTypes";
    String EntityTypeIncludeMessageTypes = "EntityTypeIncludeMessageTypes";
    String EntityTypeIncludeEntityInstancesCount = "EntityTypeIncludeEntityInstancesCount";
    String EntityTypeIncludeEntityInstances = "EntityTypeIncludeEntityInstances";

    String EntityVisitIncludeEntityInstance = "EntityVisitIncludeEntityInstance";
    String EntityVisitIncludeVisitedEntityInstance = "EntityVisitIncludeVisitedEntityInstance";
    String EntityVisitIncludeVisitedTime = "EntityVisitIncludeVisitedTime";

    String EntityAliasTypeIncludeAlias = "EntityAliasTypeIncludeAlias";

}
