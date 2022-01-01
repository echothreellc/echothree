// --------------------------------------------------------------------------------
// Copyright 2002-2022 Echo Three, LLC
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

    String EntityStringAttributeIncludeString = "EntityStringAttributeIncludeString";
    
    String EntityAttributeGroupIncludeEntityAttributes = "EntityAttributeGroupIncludeEntityAttributes";
    
    String EntityAttributeIncludeEntityListItems = "EntityAttributeIncludeEntityListItems";
    String EntityAttributeIncludeEntityAttributeEntityTypes = "EntityAttributeIncludeEntityAttributeEntityTypes";
    String EntityAttributeIncludeValue = "EntityAttributeIncludeValue";

    String EntityInstanceIncludeEntityAppearance = "EntityInstanceIncludeEntityAppearance";
    String EntityInstanceIncludeNames = "EntityInstanceIncludeNames";
    String EntityInstanceIncludeKeyIfAvailable = "EntityInstanceIncludeKeyIfAvailable";
    String EntityInstanceIncludeGuidIfAvailable = "EntityInstanceIncludeGuidIfAvailable";
    String EntityInstanceIncludeUlidIfAvailable = "EntityInstanceIncludeUlidIfAvailable";
    
    String CommandMessageIncludeTranslations = "CommandMessageIncludeTranslations";

    String CacheEntryIncludeClob = "CacheEntryIncludeClob";
    String CacheEntryIncludeBlob = "CacheEntryIncludeBlob";
    String CacheEntryIncludeCacheEntryDependencies = "CacheEntryIncludeCacheEntryDependencies";

    String ApplicationIncludeKey = "ApplicationIncludeKey";
    String ApplicationIncludeGuid = "ApplicationIncludeGuid";
    
    String AppearanceIncludeTextDecorations = "AppearanceIncludeTextDecorations";
    String AppearanceIncludeTextTransformations = "AppearanceIncludeTextTransformations";
    
    String MimeTypeIncludeMimeTypeFileExtensions = "MimeTypeIncludeMimeTypeFileExtensions";

    String EntityTypeIncludeIndexTypes = "EntityTypeIncludeIndexTypes";
    String EntityTypeIncludeIndexTypesCount = "EntityTypeIncludeIndexTypesCount";
    
}
