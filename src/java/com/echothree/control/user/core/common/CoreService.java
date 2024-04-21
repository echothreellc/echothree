// --------------------------------------------------------------------------------
// Copyright 2002-2024 Echo Three, LLC
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

package com.echothree.control.user.core.common;

import com.echothree.control.user.core.common.form.ChangeBaseKeysForm;
import com.echothree.control.user.core.common.form.CreateAppearanceDescriptionForm;
import com.echothree.control.user.core.common.form.CreateAppearanceForm;
import com.echothree.control.user.core.common.form.CreateAppearanceTextDecorationForm;
import com.echothree.control.user.core.common.form.CreateAppearanceTextTransformationForm;
import com.echothree.control.user.core.common.form.CreateApplicationDescriptionForm;
import com.echothree.control.user.core.common.form.CreateApplicationEditorForm;
import com.echothree.control.user.core.common.form.CreateApplicationEditorUseDescriptionForm;
import com.echothree.control.user.core.common.form.CreateApplicationEditorUseForm;
import com.echothree.control.user.core.common.form.CreateApplicationForm;
import com.echothree.control.user.core.common.form.CreateCacheEntryForm;
import com.echothree.control.user.core.common.form.CreateColorDescriptionForm;
import com.echothree.control.user.core.common.form.CreateColorForm;
import com.echothree.control.user.core.common.form.CreateCommandDescriptionForm;
import com.echothree.control.user.core.common.form.CreateCommandForm;
import com.echothree.control.user.core.common.form.CreateCommandMessageForm;
import com.echothree.control.user.core.common.form.CreateCommandMessageTranslationForm;
import com.echothree.control.user.core.common.form.CreateCommandMessageTypeDescriptionForm;
import com.echothree.control.user.core.common.form.CreateCommandMessageTypeForm;
import com.echothree.control.user.core.common.form.CreateComponentForm;
import com.echothree.control.user.core.common.form.CreateComponentStageForm;
import com.echothree.control.user.core.common.form.CreateComponentVendorForm;
import com.echothree.control.user.core.common.form.CreateComponentVersionForm;
import com.echothree.control.user.core.common.form.CreateEditorDescriptionForm;
import com.echothree.control.user.core.common.form.CreateEditorForm;
import com.echothree.control.user.core.common.form.CreateEntityAppearanceForm;
import com.echothree.control.user.core.common.form.CreateEntityAttributeDescriptionForm;
import com.echothree.control.user.core.common.form.CreateEntityAttributeEntityAttributeGroupForm;
import com.echothree.control.user.core.common.form.CreateEntityAttributeEntityTypeForm;
import com.echothree.control.user.core.common.form.CreateEntityAttributeForm;
import com.echothree.control.user.core.common.form.CreateEntityAttributeGroupDescriptionForm;
import com.echothree.control.user.core.common.form.CreateEntityAttributeGroupForm;
import com.echothree.control.user.core.common.form.CreateEntityAttributeTypeDescriptionForm;
import com.echothree.control.user.core.common.form.CreateEntityAttributeTypeForm;
import com.echothree.control.user.core.common.form.CreateEntityBlobAttributeForm;
import com.echothree.control.user.core.common.form.CreateEntityBooleanAttributeForm;
import com.echothree.control.user.core.common.form.CreateEntityClobAttributeForm;
import com.echothree.control.user.core.common.form.CreateEntityCollectionAttributeForm;
import com.echothree.control.user.core.common.form.CreateEntityDateAttributeForm;
import com.echothree.control.user.core.common.form.CreateEntityEntityAttributeForm;
import com.echothree.control.user.core.common.form.CreateEntityGeoPointAttributeForm;
import com.echothree.control.user.core.common.form.CreateEntityInstanceForm;
import com.echothree.control.user.core.common.form.CreateEntityIntegerAttributeForm;
import com.echothree.control.user.core.common.form.CreateEntityIntegerRangeDescriptionForm;
import com.echothree.control.user.core.common.form.CreateEntityIntegerRangeForm;
import com.echothree.control.user.core.common.form.CreateEntityListItemAttributeForm;
import com.echothree.control.user.core.common.form.CreateEntityListItemDescriptionForm;
import com.echothree.control.user.core.common.form.CreateEntityListItemForm;
import com.echothree.control.user.core.common.form.CreateEntityLongAttributeForm;
import com.echothree.control.user.core.common.form.CreateEntityLongRangeDescriptionForm;
import com.echothree.control.user.core.common.form.CreateEntityLongRangeForm;
import com.echothree.control.user.core.common.form.CreateEntityMultipleListItemAttributeForm;
import com.echothree.control.user.core.common.form.CreateEntityNameAttributeForm;
import com.echothree.control.user.core.common.form.CreateEntityStringAttributeForm;
import com.echothree.control.user.core.common.form.CreateEntityTimeAttributeForm;
import com.echothree.control.user.core.common.form.CreateEntityTypeDescriptionForm;
import com.echothree.control.user.core.common.form.CreateEntityTypeForm;
import com.echothree.control.user.core.common.form.CreateEventTypeDescriptionForm;
import com.echothree.control.user.core.common.form.CreateEventTypeForm;
import com.echothree.control.user.core.common.form.CreateFontStyleDescriptionForm;
import com.echothree.control.user.core.common.form.CreateFontStyleForm;
import com.echothree.control.user.core.common.form.CreateFontWeightDescriptionForm;
import com.echothree.control.user.core.common.form.CreateFontWeightForm;
import com.echothree.control.user.core.common.form.CreateMimeTypeDescriptionForm;
import com.echothree.control.user.core.common.form.CreateMimeTypeFileExtensionForm;
import com.echothree.control.user.core.common.form.CreateMimeTypeForm;
import com.echothree.control.user.core.common.form.CreateMimeTypeUsageForm;
import com.echothree.control.user.core.common.form.CreateMimeTypeUsageTypeDescriptionForm;
import com.echothree.control.user.core.common.form.CreateMimeTypeUsageTypeForm;
import com.echothree.control.user.core.common.form.CreatePartyApplicationEditorUseForm;
import com.echothree.control.user.core.common.form.CreatePartyEntityTypeForm;
import com.echothree.control.user.core.common.form.CreateProtocolDescriptionForm;
import com.echothree.control.user.core.common.form.CreateProtocolForm;
import com.echothree.control.user.core.common.form.CreateServerDescriptionForm;
import com.echothree.control.user.core.common.form.CreateServerForm;
import com.echothree.control.user.core.common.form.CreateServerServiceForm;
import com.echothree.control.user.core.common.form.CreateServiceDescriptionForm;
import com.echothree.control.user.core.common.form.CreateServiceForm;
import com.echothree.control.user.core.common.form.CreateTextDecorationDescriptionForm;
import com.echothree.control.user.core.common.form.CreateTextDecorationForm;
import com.echothree.control.user.core.common.form.CreateTextTransformationDescriptionForm;
import com.echothree.control.user.core.common.form.CreateTextTransformationForm;
import com.echothree.control.user.core.common.form.DecryptForm;
import com.echothree.control.user.core.common.form.DeleteAppearanceDescriptionForm;
import com.echothree.control.user.core.common.form.DeleteAppearanceForm;
import com.echothree.control.user.core.common.form.DeleteAppearanceTextDecorationForm;
import com.echothree.control.user.core.common.form.DeleteAppearanceTextTransformationForm;
import com.echothree.control.user.core.common.form.DeleteApplicationDescriptionForm;
import com.echothree.control.user.core.common.form.DeleteApplicationEditorForm;
import com.echothree.control.user.core.common.form.DeleteApplicationEditorUseDescriptionForm;
import com.echothree.control.user.core.common.form.DeleteApplicationEditorUseForm;
import com.echothree.control.user.core.common.form.DeleteApplicationForm;
import com.echothree.control.user.core.common.form.DeleteColorDescriptionForm;
import com.echothree.control.user.core.common.form.DeleteColorForm;
import com.echothree.control.user.core.common.form.DeleteCommandDescriptionForm;
import com.echothree.control.user.core.common.form.DeleteCommandForm;
import com.echothree.control.user.core.common.form.DeleteCommandMessageForm;
import com.echothree.control.user.core.common.form.DeleteCommandMessageTranslationForm;
import com.echothree.control.user.core.common.form.DeleteCommandMessageTypeDescriptionForm;
import com.echothree.control.user.core.common.form.DeleteCommandMessageTypeForm;
import com.echothree.control.user.core.common.form.DeleteComponentVendorForm;
import com.echothree.control.user.core.common.form.DeleteEditorDescriptionForm;
import com.echothree.control.user.core.common.form.DeleteEditorForm;
import com.echothree.control.user.core.common.form.DeleteEntityAppearanceForm;
import com.echothree.control.user.core.common.form.DeleteEntityAttributeDescriptionForm;
import com.echothree.control.user.core.common.form.DeleteEntityAttributeEntityAttributeGroupForm;
import com.echothree.control.user.core.common.form.DeleteEntityAttributeEntityTypeForm;
import com.echothree.control.user.core.common.form.DeleteEntityAttributeForm;
import com.echothree.control.user.core.common.form.DeleteEntityAttributeGroupDescriptionForm;
import com.echothree.control.user.core.common.form.DeleteEntityAttributeGroupForm;
import com.echothree.control.user.core.common.form.DeleteEntityBlobAttributeForm;
import com.echothree.control.user.core.common.form.DeleteEntityBooleanAttributeForm;
import com.echothree.control.user.core.common.form.DeleteEntityClobAttributeForm;
import com.echothree.control.user.core.common.form.DeleteEntityCollectionAttributeForm;
import com.echothree.control.user.core.common.form.DeleteEntityDateAttributeForm;
import com.echothree.control.user.core.common.form.DeleteEntityEntityAttributeForm;
import com.echothree.control.user.core.common.form.DeleteEntityGeoPointAttributeForm;
import com.echothree.control.user.core.common.form.DeleteEntityInstanceForm;
import com.echothree.control.user.core.common.form.DeleteEntityIntegerAttributeForm;
import com.echothree.control.user.core.common.form.DeleteEntityIntegerRangeDescriptionForm;
import com.echothree.control.user.core.common.form.DeleteEntityIntegerRangeForm;
import com.echothree.control.user.core.common.form.DeleteEntityListItemAttributeForm;
import com.echothree.control.user.core.common.form.DeleteEntityListItemDescriptionForm;
import com.echothree.control.user.core.common.form.DeleteEntityListItemForm;
import com.echothree.control.user.core.common.form.DeleteEntityLongAttributeForm;
import com.echothree.control.user.core.common.form.DeleteEntityLongRangeDescriptionForm;
import com.echothree.control.user.core.common.form.DeleteEntityLongRangeForm;
import com.echothree.control.user.core.common.form.DeleteEntityMultipleListItemAttributeForm;
import com.echothree.control.user.core.common.form.DeleteEntityNameAttributeForm;
import com.echothree.control.user.core.common.form.DeleteEntityStringAttributeForm;
import com.echothree.control.user.core.common.form.DeleteEntityTimeAttributeForm;
import com.echothree.control.user.core.common.form.DeleteEntityTypeDescriptionForm;
import com.echothree.control.user.core.common.form.DeleteEntityTypeForm;
import com.echothree.control.user.core.common.form.DeleteFontStyleDescriptionForm;
import com.echothree.control.user.core.common.form.DeleteFontStyleForm;
import com.echothree.control.user.core.common.form.DeleteFontWeightDescriptionForm;
import com.echothree.control.user.core.common.form.DeleteFontWeightForm;
import com.echothree.control.user.core.common.form.DeleteMimeTypeDescriptionForm;
import com.echothree.control.user.core.common.form.DeleteMimeTypeForm;
import com.echothree.control.user.core.common.form.DeletePartyApplicationEditorUseForm;
import com.echothree.control.user.core.common.form.DeletePartyEntityTypeForm;
import com.echothree.control.user.core.common.form.DeleteProtocolDescriptionForm;
import com.echothree.control.user.core.common.form.DeleteProtocolForm;
import com.echothree.control.user.core.common.form.DeleteServerDescriptionForm;
import com.echothree.control.user.core.common.form.DeleteServerForm;
import com.echothree.control.user.core.common.form.DeleteServerServiceForm;
import com.echothree.control.user.core.common.form.DeleteServiceDescriptionForm;
import com.echothree.control.user.core.common.form.DeleteServiceForm;
import com.echothree.control.user.core.common.form.DeleteTextDecorationDescriptionForm;
import com.echothree.control.user.core.common.form.DeleteTextDecorationForm;
import com.echothree.control.user.core.common.form.DeleteTextTransformationDescriptionForm;
import com.echothree.control.user.core.common.form.DeleteTextTransformationForm;
import com.echothree.control.user.core.common.form.EditAppearanceDescriptionForm;
import com.echothree.control.user.core.common.form.EditAppearanceForm;
import com.echothree.control.user.core.common.form.EditApplicationDescriptionForm;
import com.echothree.control.user.core.common.form.EditApplicationEditorForm;
import com.echothree.control.user.core.common.form.EditApplicationEditorUseDescriptionForm;
import com.echothree.control.user.core.common.form.EditApplicationEditorUseForm;
import com.echothree.control.user.core.common.form.EditApplicationForm;
import com.echothree.control.user.core.common.form.EditColorDescriptionForm;
import com.echothree.control.user.core.common.form.EditColorForm;
import com.echothree.control.user.core.common.form.EditCommandDescriptionForm;
import com.echothree.control.user.core.common.form.EditCommandForm;
import com.echothree.control.user.core.common.form.EditCommandMessageForm;
import com.echothree.control.user.core.common.form.EditCommandMessageTranslationForm;
import com.echothree.control.user.core.common.form.EditCommandMessageTypeDescriptionForm;
import com.echothree.control.user.core.common.form.EditCommandMessageTypeForm;
import com.echothree.control.user.core.common.form.EditComponentVendorForm;
import com.echothree.control.user.core.common.form.EditEditorDescriptionForm;
import com.echothree.control.user.core.common.form.EditEditorForm;
import com.echothree.control.user.core.common.form.EditEntityAppearanceForm;
import com.echothree.control.user.core.common.form.EditEntityAttributeDescriptionForm;
import com.echothree.control.user.core.common.form.EditEntityAttributeEntityAttributeGroupForm;
import com.echothree.control.user.core.common.form.EditEntityAttributeForm;
import com.echothree.control.user.core.common.form.EditEntityAttributeGroupDescriptionForm;
import com.echothree.control.user.core.common.form.EditEntityAttributeGroupForm;
import com.echothree.control.user.core.common.form.EditEntityBlobAttributeForm;
import com.echothree.control.user.core.common.form.EditEntityBooleanAttributeForm;
import com.echothree.control.user.core.common.form.EditEntityClobAttributeForm;
import com.echothree.control.user.core.common.form.EditEntityDateAttributeForm;
import com.echothree.control.user.core.common.form.EditEntityEntityAttributeForm;
import com.echothree.control.user.core.common.form.EditEntityGeoPointAttributeForm;
import com.echothree.control.user.core.common.form.EditEntityIntegerAttributeForm;
import com.echothree.control.user.core.common.form.EditEntityIntegerRangeDescriptionForm;
import com.echothree.control.user.core.common.form.EditEntityIntegerRangeForm;
import com.echothree.control.user.core.common.form.EditEntityListItemAttributeForm;
import com.echothree.control.user.core.common.form.EditEntityListItemDescriptionForm;
import com.echothree.control.user.core.common.form.EditEntityListItemForm;
import com.echothree.control.user.core.common.form.EditEntityLongAttributeForm;
import com.echothree.control.user.core.common.form.EditEntityLongRangeDescriptionForm;
import com.echothree.control.user.core.common.form.EditEntityLongRangeForm;
import com.echothree.control.user.core.common.form.EditEntityNameAttributeForm;
import com.echothree.control.user.core.common.form.EditEntityStringAttributeForm;
import com.echothree.control.user.core.common.form.EditEntityTimeAttributeForm;
import com.echothree.control.user.core.common.form.EditEntityTypeDescriptionForm;
import com.echothree.control.user.core.common.form.EditEntityTypeForm;
import com.echothree.control.user.core.common.form.EditFontStyleDescriptionForm;
import com.echothree.control.user.core.common.form.EditFontStyleForm;
import com.echothree.control.user.core.common.form.EditFontWeightDescriptionForm;
import com.echothree.control.user.core.common.form.EditFontWeightForm;
import com.echothree.control.user.core.common.form.EditMimeTypeDescriptionForm;
import com.echothree.control.user.core.common.form.EditMimeTypeForm;
import com.echothree.control.user.core.common.form.EditPartyApplicationEditorUseForm;
import com.echothree.control.user.core.common.form.EditPartyEntityTypeForm;
import com.echothree.control.user.core.common.form.EditProtocolDescriptionForm;
import com.echothree.control.user.core.common.form.EditProtocolForm;
import com.echothree.control.user.core.common.form.EditServerDescriptionForm;
import com.echothree.control.user.core.common.form.EditServerForm;
import com.echothree.control.user.core.common.form.EditServiceDescriptionForm;
import com.echothree.control.user.core.common.form.EditServiceForm;
import com.echothree.control.user.core.common.form.EditTextDecorationDescriptionForm;
import com.echothree.control.user.core.common.form.EditTextDecorationForm;
import com.echothree.control.user.core.common.form.EditTextTransformationDescriptionForm;
import com.echothree.control.user.core.common.form.EditTextTransformationForm;
import com.echothree.control.user.core.common.form.EncryptForm;
import com.echothree.control.user.core.common.form.GenerateGuidForm;
import com.echothree.control.user.core.common.form.GenerateKeyForm;
import com.echothree.control.user.core.common.form.GenerateUlidForm;
import com.echothree.control.user.core.common.form.GetAppearanceChoicesForm;
import com.echothree.control.user.core.common.form.GetAppearanceDescriptionForm;
import com.echothree.control.user.core.common.form.GetAppearanceDescriptionsForm;
import com.echothree.control.user.core.common.form.GetAppearanceForm;
import com.echothree.control.user.core.common.form.GetAppearanceTextDecorationForm;
import com.echothree.control.user.core.common.form.GetAppearanceTextDecorationsForm;
import com.echothree.control.user.core.common.form.GetAppearanceTextTransformationForm;
import com.echothree.control.user.core.common.form.GetAppearanceTextTransformationsForm;
import com.echothree.control.user.core.common.form.GetAppearancesForm;
import com.echothree.control.user.core.common.form.GetApplicationChoicesForm;
import com.echothree.control.user.core.common.form.GetApplicationDescriptionForm;
import com.echothree.control.user.core.common.form.GetApplicationDescriptionsForm;
import com.echothree.control.user.core.common.form.GetApplicationEditorChoicesForm;
import com.echothree.control.user.core.common.form.GetApplicationEditorForm;
import com.echothree.control.user.core.common.form.GetApplicationEditorUseChoicesForm;
import com.echothree.control.user.core.common.form.GetApplicationEditorUseDescriptionForm;
import com.echothree.control.user.core.common.form.GetApplicationEditorUseDescriptionsForm;
import com.echothree.control.user.core.common.form.GetApplicationEditorUseForm;
import com.echothree.control.user.core.common.form.GetApplicationEditorUsesForm;
import com.echothree.control.user.core.common.form.GetApplicationEditorsForm;
import com.echothree.control.user.core.common.form.GetApplicationForm;
import com.echothree.control.user.core.common.form.GetApplicationsForm;
import com.echothree.control.user.core.common.form.GetBaseEncryptionKeyForm;
import com.echothree.control.user.core.common.form.GetBaseEncryptionKeyStatusChoicesForm;
import com.echothree.control.user.core.common.form.GetBaseEncryptionKeysForm;
import com.echothree.control.user.core.common.form.GetCacheEntriesForm;
import com.echothree.control.user.core.common.form.GetCacheEntryDependenciesForm;
import com.echothree.control.user.core.common.form.GetCacheEntryForm;
import com.echothree.control.user.core.common.form.GetColorChoicesForm;
import com.echothree.control.user.core.common.form.GetColorDescriptionForm;
import com.echothree.control.user.core.common.form.GetColorDescriptionsForm;
import com.echothree.control.user.core.common.form.GetColorForm;
import com.echothree.control.user.core.common.form.GetColorsForm;
import com.echothree.control.user.core.common.form.GetCommandDescriptionForm;
import com.echothree.control.user.core.common.form.GetCommandDescriptionsForm;
import com.echothree.control.user.core.common.form.GetCommandForm;
import com.echothree.control.user.core.common.form.GetCommandMessageForm;
import com.echothree.control.user.core.common.form.GetCommandMessageTranslationForm;
import com.echothree.control.user.core.common.form.GetCommandMessageTranslationsForm;
import com.echothree.control.user.core.common.form.GetCommandMessageTypeChoicesForm;
import com.echothree.control.user.core.common.form.GetCommandMessageTypeDescriptionForm;
import com.echothree.control.user.core.common.form.GetCommandMessageTypeDescriptionsForm;
import com.echothree.control.user.core.common.form.GetCommandMessageTypeForm;
import com.echothree.control.user.core.common.form.GetCommandMessageTypesForm;
import com.echothree.control.user.core.common.form.GetCommandMessagesForm;
import com.echothree.control.user.core.common.form.GetCommandsForm;
import com.echothree.control.user.core.common.form.GetComponentVendorForm;
import com.echothree.control.user.core.common.form.GetComponentVendorsForm;
import com.echothree.control.user.core.common.form.GetEditorChoicesForm;
import com.echothree.control.user.core.common.form.GetEditorDescriptionForm;
import com.echothree.control.user.core.common.form.GetEditorDescriptionsForm;
import com.echothree.control.user.core.common.form.GetEditorForm;
import com.echothree.control.user.core.common.form.GetEditorsForm;
import com.echothree.control.user.core.common.form.GetEntityAppearanceForm;
import com.echothree.control.user.core.common.form.GetEntityAttributeDescriptionForm;
import com.echothree.control.user.core.common.form.GetEntityAttributeDescriptionsForm;
import com.echothree.control.user.core.common.form.GetEntityAttributeEntityAttributeGroupForm;
import com.echothree.control.user.core.common.form.GetEntityAttributeEntityAttributeGroupsForm;
import com.echothree.control.user.core.common.form.GetEntityAttributeEntityTypeForm;
import com.echothree.control.user.core.common.form.GetEntityAttributeEntityTypesForm;
import com.echothree.control.user.core.common.form.GetEntityAttributeForm;
import com.echothree.control.user.core.common.form.GetEntityAttributeGroupChoicesForm;
import com.echothree.control.user.core.common.form.GetEntityAttributeGroupDescriptionForm;
import com.echothree.control.user.core.common.form.GetEntityAttributeGroupDescriptionsForm;
import com.echothree.control.user.core.common.form.GetEntityAttributeGroupForm;
import com.echothree.control.user.core.common.form.GetEntityAttributeGroupsForm;
import com.echothree.control.user.core.common.form.GetEntityAttributeTypeChoicesForm;
import com.echothree.control.user.core.common.form.GetEntityAttributeTypeForm;
import com.echothree.control.user.core.common.form.GetEntityAttributeTypesForm;
import com.echothree.control.user.core.common.form.GetEntityAttributesForm;
import com.echothree.control.user.core.common.form.GetEntityBlobAttributeForm;
import com.echothree.control.user.core.common.form.GetEntityClobAttributeForm;
import com.echothree.control.user.core.common.form.GetEntityInstanceForm;
import com.echothree.control.user.core.common.form.GetEntityInstancesForm;
import com.echothree.control.user.core.common.form.GetEntityIntegerRangeChoicesForm;
import com.echothree.control.user.core.common.form.GetEntityIntegerRangeDescriptionForm;
import com.echothree.control.user.core.common.form.GetEntityIntegerRangeDescriptionsForm;
import com.echothree.control.user.core.common.form.GetEntityIntegerRangeForm;
import com.echothree.control.user.core.common.form.GetEntityIntegerRangesForm;
import com.echothree.control.user.core.common.form.GetEntityListItemChoicesForm;
import com.echothree.control.user.core.common.form.GetEntityListItemDescriptionForm;
import com.echothree.control.user.core.common.form.GetEntityListItemDescriptionsForm;
import com.echothree.control.user.core.common.form.GetEntityListItemForm;
import com.echothree.control.user.core.common.form.GetEntityListItemsForm;
import com.echothree.control.user.core.common.form.GetEntityLongRangeChoicesForm;
import com.echothree.control.user.core.common.form.GetEntityLongRangeDescriptionForm;
import com.echothree.control.user.core.common.form.GetEntityLongRangeDescriptionsForm;
import com.echothree.control.user.core.common.form.GetEntityLongRangeForm;
import com.echothree.control.user.core.common.form.GetEntityLongRangesForm;
import com.echothree.control.user.core.common.form.GetEntityTypeDescriptionForm;
import com.echothree.control.user.core.common.form.GetEntityTypeDescriptionsForm;
import com.echothree.control.user.core.common.form.GetEntityTypeForm;
import com.echothree.control.user.core.common.form.GetEntityTypesForm;
import com.echothree.control.user.core.common.form.GetEventGroupForm;
import com.echothree.control.user.core.common.form.GetEventGroupStatusChoicesForm;
import com.echothree.control.user.core.common.form.GetEventGroupsForm;
import com.echothree.control.user.core.common.form.GetEventsForm;
import com.echothree.control.user.core.common.form.GetFontStyleChoicesForm;
import com.echothree.control.user.core.common.form.GetFontStyleDescriptionForm;
import com.echothree.control.user.core.common.form.GetFontStyleDescriptionsForm;
import com.echothree.control.user.core.common.form.GetFontStyleForm;
import com.echothree.control.user.core.common.form.GetFontStylesForm;
import com.echothree.control.user.core.common.form.GetFontWeightChoicesForm;
import com.echothree.control.user.core.common.form.GetFontWeightDescriptionForm;
import com.echothree.control.user.core.common.form.GetFontWeightDescriptionsForm;
import com.echothree.control.user.core.common.form.GetFontWeightForm;
import com.echothree.control.user.core.common.form.GetFontWeightsForm;
import com.echothree.control.user.core.common.form.GetMimeTypeChoicesForm;
import com.echothree.control.user.core.common.form.GetMimeTypeDescriptionForm;
import com.echothree.control.user.core.common.form.GetMimeTypeDescriptionsForm;
import com.echothree.control.user.core.common.form.GetMimeTypeFileExtensionForm;
import com.echothree.control.user.core.common.form.GetMimeTypeFileExtensionsForm;
import com.echothree.control.user.core.common.form.GetMimeTypeForm;
import com.echothree.control.user.core.common.form.GetMimeTypeUsageTypeChoicesForm;
import com.echothree.control.user.core.common.form.GetMimeTypeUsageTypeForm;
import com.echothree.control.user.core.common.form.GetMimeTypeUsageTypesForm;
import com.echothree.control.user.core.common.form.GetMimeTypeUsagesForm;
import com.echothree.control.user.core.common.form.GetMimeTypesForm;
import com.echothree.control.user.core.common.form.GetPartyApplicationEditorUseForm;
import com.echothree.control.user.core.common.form.GetPartyApplicationEditorUsesForm;
import com.echothree.control.user.core.common.form.GetPartyEntityTypeForm;
import com.echothree.control.user.core.common.form.GetPartyEntityTypesForm;
import com.echothree.control.user.core.common.form.GetProtocolChoicesForm;
import com.echothree.control.user.core.common.form.GetProtocolDescriptionForm;
import com.echothree.control.user.core.common.form.GetProtocolDescriptionsForm;
import com.echothree.control.user.core.common.form.GetProtocolForm;
import com.echothree.control.user.core.common.form.GetProtocolsForm;
import com.echothree.control.user.core.common.form.GetServerChoicesForm;
import com.echothree.control.user.core.common.form.GetServerDescriptionForm;
import com.echothree.control.user.core.common.form.GetServerDescriptionsForm;
import com.echothree.control.user.core.common.form.GetServerForm;
import com.echothree.control.user.core.common.form.GetServerServiceForm;
import com.echothree.control.user.core.common.form.GetServerServicesForm;
import com.echothree.control.user.core.common.form.GetServersForm;
import com.echothree.control.user.core.common.form.GetServiceChoicesForm;
import com.echothree.control.user.core.common.form.GetServiceDescriptionForm;
import com.echothree.control.user.core.common.form.GetServiceDescriptionsForm;
import com.echothree.control.user.core.common.form.GetServiceForm;
import com.echothree.control.user.core.common.form.GetServicesForm;
import com.echothree.control.user.core.common.form.GetTextDecorationChoicesForm;
import com.echothree.control.user.core.common.form.GetTextDecorationDescriptionForm;
import com.echothree.control.user.core.common.form.GetTextDecorationDescriptionsForm;
import com.echothree.control.user.core.common.form.GetTextDecorationForm;
import com.echothree.control.user.core.common.form.GetTextDecorationsForm;
import com.echothree.control.user.core.common.form.GetTextTransformationChoicesForm;
import com.echothree.control.user.core.common.form.GetTextTransformationDescriptionForm;
import com.echothree.control.user.core.common.form.GetTextTransformationDescriptionsForm;
import com.echothree.control.user.core.common.form.GetTextTransformationForm;
import com.echothree.control.user.core.common.form.GetTextTransformationsForm;
import com.echothree.control.user.core.common.form.LoadBaseKeysForm;
import com.echothree.control.user.core.common.form.LockEntityForm;
import com.echothree.control.user.core.common.form.RemoveCacheEntryForm;
import com.echothree.control.user.core.common.form.RemoveEntityInstanceForm;
import com.echothree.control.user.core.common.form.SetBaseEncryptionKeyStatusForm;
import com.echothree.control.user.core.common.form.SetDefaultAppearanceForm;
import com.echothree.control.user.core.common.form.SetDefaultApplicationEditorForm;
import com.echothree.control.user.core.common.form.SetDefaultApplicationEditorUseForm;
import com.echothree.control.user.core.common.form.SetDefaultApplicationForm;
import com.echothree.control.user.core.common.form.SetDefaultColorForm;
import com.echothree.control.user.core.common.form.SetDefaultCommandMessageTypeForm;
import com.echothree.control.user.core.common.form.SetDefaultEditorForm;
import com.echothree.control.user.core.common.form.SetDefaultEntityAttributeGroupForm;
import com.echothree.control.user.core.common.form.SetDefaultEntityIntegerRangeForm;
import com.echothree.control.user.core.common.form.SetDefaultEntityListItemForm;
import com.echothree.control.user.core.common.form.SetDefaultEntityLongRangeForm;
import com.echothree.control.user.core.common.form.SetDefaultFontStyleForm;
import com.echothree.control.user.core.common.form.SetDefaultFontWeightForm;
import com.echothree.control.user.core.common.form.SetDefaultMimeTypeForm;
import com.echothree.control.user.core.common.form.SetDefaultProtocolForm;
import com.echothree.control.user.core.common.form.SetDefaultServerForm;
import com.echothree.control.user.core.common.form.SetDefaultServiceForm;
import com.echothree.control.user.core.common.form.SetDefaultTextDecorationForm;
import com.echothree.control.user.core.common.form.SetDefaultTextTransformationForm;
import com.echothree.control.user.core.common.form.SetEventGroupStatusForm;
import com.echothree.control.user.core.common.form.UnlockEntityForm;
import com.echothree.control.user.core.common.form.ValidateForm;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;

public interface CoreService
        extends CoreForms {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    String ping();
    
    // -------------------------------------------------------------------------
    //   Base Keys
    // -------------------------------------------------------------------------
    
    CommandResult generateBaseKeys(UserVisitPK userVisitPK);
    
    CommandResult loadBaseKeys(UserVisitPK userVisitPK, LoadBaseKeysForm form);
    
    CommandResult changeBaseKeys(UserVisitPK userVisitPK, ChangeBaseKeysForm form);
    
    // -------------------------------------------------------------------------
    //   Base Encryption Keys
    // -------------------------------------------------------------------------
    
    CommandResult getBaseEncryptionKey(UserVisitPK userVisitPK, GetBaseEncryptionKeyForm form);
    
    CommandResult getBaseEncryptionKeys(UserVisitPK userVisitPK, GetBaseEncryptionKeysForm form);
    
    CommandResult getBaseEncryptionKeyStatusChoices(UserVisitPK userVisitPK, GetBaseEncryptionKeyStatusChoicesForm form);
    
    CommandResult setBaseEncryptionKeyStatus(UserVisitPK userVisitPK, SetBaseEncryptionKeyStatusForm form);
    
    // -------------------------------------------------------------------------
    //   Entity Locks
    // -------------------------------------------------------------------------
    
    CommandResult lockEntity(UserVisitPK userVisitPK, LockEntityForm form);
    
    CommandResult unlockEntity(UserVisitPK userVisitPK, UnlockEntityForm form);
    
    CommandResult removedExpiredEntityLocks(UserVisitPK userVisitPK);
    
    // -------------------------------------------------------------------------
    //   Component Vendors
    // -------------------------------------------------------------------------
    
    CommandResult createComponentVendor(UserVisitPK userVisitPK, CreateComponentVendorForm form);
    
    CommandResult getComponentVendor(UserVisitPK userVisitPK, GetComponentVendorForm form);
    
    CommandResult getComponentVendors(UserVisitPK userVisitPK, GetComponentVendorsForm form);
    
    CommandResult editComponentVendor(UserVisitPK userVisitPK, EditComponentVendorForm form);
    
    CommandResult deleteComponentVendor(UserVisitPK userVisitPK, DeleteComponentVendorForm form);
    
    // -------------------------------------------------------------------------
    //   Entity Types
    // -------------------------------------------------------------------------
    
    CommandResult createEntityType(UserVisitPK userVisitPK, CreateEntityTypeForm form);
    
    CommandResult getEntityType(UserVisitPK userVisitPK, GetEntityTypeForm form);
    
    CommandResult getEntityTypes(UserVisitPK userVisitPK, GetEntityTypesForm form);
    
    CommandResult editEntityType(UserVisitPK userVisitPK, EditEntityTypeForm form);
    
    CommandResult deleteEntityType(UserVisitPK userVisitPK, DeleteEntityTypeForm form);
    
    // -------------------------------------------------------------------------
    //   Commands
    // -------------------------------------------------------------------------
    
    CommandResult createCommand(UserVisitPK userVisitPK, CreateCommandForm form);
    
    CommandResult getCommand(UserVisitPK userVisitPK, GetCommandForm form);
    
    CommandResult getCommands(UserVisitPK userVisitPK, GetCommandsForm form);
    
    CommandResult editCommand(UserVisitPK userVisitPK, EditCommandForm form);
    
    CommandResult deleteCommand(UserVisitPK userVisitPK, DeleteCommandForm form);
    
    // -------------------------------------------------------------------------
    //   Command Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult createCommandDescription(UserVisitPK userVisitPK, CreateCommandDescriptionForm form);
    
    CommandResult getCommandDescription(UserVisitPK userVisitPK, GetCommandDescriptionForm form);
    
    CommandResult getCommandDescriptions(UserVisitPK userVisitPK, GetCommandDescriptionsForm form);
    
    CommandResult editCommandDescription(UserVisitPK userVisitPK, EditCommandDescriptionForm form);
    
    CommandResult deleteCommandDescription(UserVisitPK userVisitPK, DeleteCommandDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Command Message Types
    // --------------------------------------------------------------------------------
    
    CommandResult createCommandMessageType(UserVisitPK userVisitPK, CreateCommandMessageTypeForm form);
    
    CommandResult getCommandMessageTypeChoices(UserVisitPK userVisitPK, GetCommandMessageTypeChoicesForm form);
    
    CommandResult getCommandMessageType(UserVisitPK userVisitPK, GetCommandMessageTypeForm form);
    
    CommandResult getCommandMessageTypes(UserVisitPK userVisitPK, GetCommandMessageTypesForm form);
    
    CommandResult setDefaultCommandMessageType(UserVisitPK userVisitPK, SetDefaultCommandMessageTypeForm form);
    
    CommandResult editCommandMessageType(UserVisitPK userVisitPK, EditCommandMessageTypeForm form);
    
    CommandResult deleteCommandMessageType(UserVisitPK userVisitPK, DeleteCommandMessageTypeForm form);
    
    // --------------------------------------------------------------------------------
    //   Command Message Type Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult createCommandMessageTypeDescription(UserVisitPK userVisitPK, CreateCommandMessageTypeDescriptionForm form);
    
    CommandResult getCommandMessageTypeDescription(UserVisitPK userVisitPK, GetCommandMessageTypeDescriptionForm form);
    
    CommandResult getCommandMessageTypeDescriptions(UserVisitPK userVisitPK, GetCommandMessageTypeDescriptionsForm form);
    
    CommandResult editCommandMessageTypeDescription(UserVisitPK userVisitPK, EditCommandMessageTypeDescriptionForm form);
    
    CommandResult deleteCommandMessageTypeDescription(UserVisitPK userVisitPK, DeleteCommandMessageTypeDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Command Messages
    // -------------------------------------------------------------------------
    
    CommandResult createCommandMessage(UserVisitPK userVisitPK, CreateCommandMessageForm form);
    
    CommandResult getCommandMessage(UserVisitPK userVisitPK, GetCommandMessageForm form);

    CommandResult getCommandMessages(UserVisitPK userVisitPK, GetCommandMessagesForm form);

    CommandResult editCommandMessage(UserVisitPK userVisitPK, EditCommandMessageForm form);

    CommandResult deleteCommandMessage(UserVisitPK userVisitPK, DeleteCommandMessageForm form);

    // -------------------------------------------------------------------------
    //   Command Message Translations
    // -------------------------------------------------------------------------

    CommandResult createCommandMessageTranslation(UserVisitPK userVisitPK, CreateCommandMessageTranslationForm form);

    CommandResult getCommandMessageTranslation(UserVisitPK userVisitPK, GetCommandMessageTranslationForm form);

    CommandResult getCommandMessageTranslations(UserVisitPK userVisitPK, GetCommandMessageTranslationsForm form);

    CommandResult editCommandMessageTranslation(UserVisitPK userVisitPK, EditCommandMessageTranslationForm form);

    CommandResult deleteCommandMessageTranslation(UserVisitPK userVisitPK, DeleteCommandMessageTranslationForm form);

    // -------------------------------------------------------------------------
    //   Entity Instances
    // -------------------------------------------------------------------------

    CommandResult createEntityInstance(UserVisitPK userVisitPK, CreateEntityInstanceForm form);

    CommandResult getEntityInstance(UserVisitPK userVisitPK, GetEntityInstanceForm form);

    CommandResult getEntityInstances(UserVisitPK userVisitPK, GetEntityInstancesForm form);

    CommandResult deleteEntityInstance(UserVisitPK userVisitPK, DeleteEntityInstanceForm form);

    CommandResult removeEntityInstance(UserVisitPK userVisitPK, RemoveEntityInstanceForm form);

    CommandResult generateKey(UserVisitPK userVisitPK, GenerateKeyForm form);
    
    CommandResult generateGuid(UserVisitPK userVisitPK, GenerateGuidForm form);
    
    CommandResult generateUlid(UserVisitPK userVisitPK, GenerateUlidForm form);
    
    // -------------------------------------------------------------------------
    //   Event Types
    // -------------------------------------------------------------------------
    
    CommandResult createEventType(UserVisitPK userVisitPK, CreateEventTypeForm form);
    
    // -------------------------------------------------------------------------
    //   Event Type Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult createEventTypeDescription(UserVisitPK userVisitPK, CreateEventTypeDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Event Groups
    // --------------------------------------------------------------------------------
    
    CommandResult getEventGroup(UserVisitPK userVisitPK, GetEventGroupForm form);
    
    CommandResult getEventGroups(UserVisitPK userVisitPK, GetEventGroupsForm form);
    
    CommandResult getEventGroupStatusChoices(UserVisitPK userVisitPK, GetEventGroupStatusChoicesForm form);
    
    CommandResult setEventGroupStatus(UserVisitPK userVisitPK, SetEventGroupStatusForm form);
    
    // -------------------------------------------------------------------------
    //   Events
    // -------------------------------------------------------------------------
    
    CommandResult getEvents(UserVisitPK userVisitPK, GetEventsForm form);
    
    // -------------------------------------------------------------------------
    //   Queued Events
    // -------------------------------------------------------------------------
    
    CommandResult processQueuedEvents(UserVisitPK userVisitPK);
    
    // -------------------------------------------------------------------------
    //   Components
    // -------------------------------------------------------------------------
    
    CommandResult createComponent(UserVisitPK userVisitPK, CreateComponentForm form);
    
    // -------------------------------------------------------------------------
    //   Component Stages
    // -------------------------------------------------------------------------
    
    CommandResult createComponentStage(UserVisitPK userVisitPK, CreateComponentStageForm form);
    
    // -------------------------------------------------------------------------
    //   Component Versions
    // -------------------------------------------------------------------------
    
    CommandResult createComponentVersion(UserVisitPK userVisitPK, CreateComponentVersionForm form);
    
    // -------------------------------------------------------------------------
    //   Entity Attribute Groups
    // -------------------------------------------------------------------------
    
    CommandResult createEntityAttributeGroup(UserVisitPK userVisitPK, CreateEntityAttributeGroupForm form);
    
    CommandResult getEntityAttributeGroup(UserVisitPK userVisitPK, GetEntityAttributeGroupForm form);
    
    CommandResult getEntityAttributeGroups(UserVisitPK userVisitPK, GetEntityAttributeGroupsForm form);
    
    CommandResult getEntityAttributeGroupChoices(UserVisitPK userVisitPK, GetEntityAttributeGroupChoicesForm form);
    
    CommandResult setDefaultEntityAttributeGroup(UserVisitPK userVisitPK, SetDefaultEntityAttributeGroupForm form);
    
    CommandResult editEntityAttributeGroup(UserVisitPK userVisitPK, EditEntityAttributeGroupForm form);
    
    CommandResult deleteEntityAttributeGroup(UserVisitPK userVisitPK, DeleteEntityAttributeGroupForm form);
    
    // -------------------------------------------------------------------------
    //   Entity Attribute Group Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult createEntityAttributeGroupDescription(UserVisitPK userVisitPK, CreateEntityAttributeGroupDescriptionForm form);
    
    CommandResult getEntityAttributeGroupDescription(UserVisitPK userVisitPK, GetEntityAttributeGroupDescriptionForm form);
    
    CommandResult getEntityAttributeGroupDescriptions(UserVisitPK userVisitPK, GetEntityAttributeGroupDescriptionsForm form);
    
    CommandResult editEntityAttributeGroupDescription(UserVisitPK userVisitPK, EditEntityAttributeGroupDescriptionForm form);
    
    CommandResult deleteEntityAttributeGroupDescription(UserVisitPK userVisitPK, DeleteEntityAttributeGroupDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Entity Attribute Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult createEntityAttributeDescription(UserVisitPK userVisitPK, CreateEntityAttributeDescriptionForm form);
    
    CommandResult getEntityAttributeDescription(UserVisitPK userVisitPK, GetEntityAttributeDescriptionForm form);
    
    CommandResult getEntityAttributeDescriptions(UserVisitPK userVisitPK, GetEntityAttributeDescriptionsForm form);
    
    CommandResult editEntityAttributeDescription(UserVisitPK userVisitPK, EditEntityAttributeDescriptionForm form);
    
    CommandResult deleteEntityAttributeDescription(UserVisitPK userVisitPK, DeleteEntityAttributeDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Entity Attributes
    // -------------------------------------------------------------------------
    
    CommandResult createEntityAttribute(UserVisitPK userVisitPK, CreateEntityAttributeForm form);
    
    CommandResult getEntityAttribute(UserVisitPK userVisitPK, GetEntityAttributeForm form);
    
    CommandResult getEntityAttributes(UserVisitPK userVisitPK, GetEntityAttributesForm form);
    
    CommandResult editEntityAttribute(UserVisitPK userVisitPK, EditEntityAttributeForm form);
    
    CommandResult deleteEntityAttribute(UserVisitPK userVisitPK, DeleteEntityAttributeForm form);
    
    // -------------------------------------------------------------------------
    //   Entity Attribute Entity Attribute Groups
    // -------------------------------------------------------------------------
    
    CommandResult createEntityAttributeEntityAttributeGroup(UserVisitPK userVisitPK, CreateEntityAttributeEntityAttributeGroupForm form);
    
    CommandResult getEntityAttributeEntityAttributeGroup(UserVisitPK userVisitPK, GetEntityAttributeEntityAttributeGroupForm form);
    
    CommandResult getEntityAttributeEntityAttributeGroups(UserVisitPK userVisitPK, GetEntityAttributeEntityAttributeGroupsForm form);
    
    CommandResult editEntityAttributeEntityAttributeGroup(UserVisitPK userVisitPK, EditEntityAttributeEntityAttributeGroupForm form);
    
    CommandResult deleteEntityAttributeEntityAttributeGroup(UserVisitPK userVisitPK, DeleteEntityAttributeEntityAttributeGroupForm form);
    
    // -------------------------------------------------------------------------
    //   Entity Attribute Type Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult createEntityAttributeTypeDescription(UserVisitPK userVisitPK, CreateEntityAttributeTypeDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Entity Attribute Types
    // -------------------------------------------------------------------------
    
    CommandResult createEntityAttributeType(UserVisitPK userVisitPK, CreateEntityAttributeTypeForm form);
    
    CommandResult getEntityAttributeType(UserVisitPK userVisitPK, GetEntityAttributeTypeForm form);
    
    CommandResult getEntityAttributeTypes(UserVisitPK userVisitPK, GetEntityAttributeTypesForm form);
    
    CommandResult getEntityAttributeTypeChoices(UserVisitPK userVisitPK, GetEntityAttributeTypeChoicesForm form);
    
    // -------------------------------------------------------------------------
    //   Entity List Item Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult createEntityListItemDescription(UserVisitPK userVisitPK, CreateEntityListItemDescriptionForm form);
    
    CommandResult getEntityListItemDescription(UserVisitPK userVisitPK, GetEntityListItemDescriptionForm form);
    
    CommandResult getEntityListItemDescriptions(UserVisitPK userVisitPK, GetEntityListItemDescriptionsForm form);
    
    CommandResult editEntityListItemDescription(UserVisitPK userVisitPK, EditEntityListItemDescriptionForm form);
    
    CommandResult deleteEntityListItemDescription(UserVisitPK userVisitPK, DeleteEntityListItemDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Entity List Items
    // -------------------------------------------------------------------------
    
    CommandResult createEntityListItem(UserVisitPK userVisitPK, CreateEntityListItemForm form);
    
    CommandResult getEntityListItem(UserVisitPK userVisitPK, GetEntityListItemForm form);
    
    CommandResult getEntityListItems(UserVisitPK userVisitPK, GetEntityListItemsForm form);
    
    CommandResult getEntityListItemChoices(UserVisitPK userVisitPK, GetEntityListItemChoicesForm form);
    
    CommandResult setDefaultEntityListItem(UserVisitPK userVisitPK, SetDefaultEntityListItemForm form);
    
    CommandResult editEntityListItem(UserVisitPK userVisitPK, EditEntityListItemForm form);
    
    CommandResult deleteEntityListItem(UserVisitPK userVisitPK, DeleteEntityListItemForm form);
    
    // -------------------------------------------------------------------------
    //   Entity Integer Range Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult createEntityIntegerRangeDescription(UserVisitPK userVisitPK, CreateEntityIntegerRangeDescriptionForm form);
    
    CommandResult getEntityIntegerRangeDescription(UserVisitPK userVisitPK, GetEntityIntegerRangeDescriptionForm form);
    
    CommandResult getEntityIntegerRangeDescriptions(UserVisitPK userVisitPK, GetEntityIntegerRangeDescriptionsForm form);
    
    CommandResult editEntityIntegerRangeDescription(UserVisitPK userVisitPK, EditEntityIntegerRangeDescriptionForm form);
    
    CommandResult deleteEntityIntegerRangeDescription(UserVisitPK userVisitPK, DeleteEntityIntegerRangeDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Entity Integer Ranges
    // -------------------------------------------------------------------------
    
    CommandResult createEntityIntegerRange(UserVisitPK userVisitPK, CreateEntityIntegerRangeForm form);
    
    CommandResult getEntityIntegerRange(UserVisitPK userVisitPK, GetEntityIntegerRangeForm form);
    
    CommandResult getEntityIntegerRanges(UserVisitPK userVisitPK, GetEntityIntegerRangesForm form);
    
    CommandResult getEntityIntegerRangeChoices(UserVisitPK userVisitPK, GetEntityIntegerRangeChoicesForm form);
    
    CommandResult setDefaultEntityIntegerRange(UserVisitPK userVisitPK, SetDefaultEntityIntegerRangeForm form);
    
    CommandResult editEntityIntegerRange(UserVisitPK userVisitPK, EditEntityIntegerRangeForm form);
    
    CommandResult deleteEntityIntegerRange(UserVisitPK userVisitPK, DeleteEntityIntegerRangeForm form);
    
    // -------------------------------------------------------------------------
    //   Entity Long Range Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult createEntityLongRangeDescription(UserVisitPK userVisitPK, CreateEntityLongRangeDescriptionForm form);
    
    CommandResult getEntityLongRangeDescription(UserVisitPK userVisitPK, GetEntityLongRangeDescriptionForm form);
    
    CommandResult getEntityLongRangeDescriptions(UserVisitPK userVisitPK, GetEntityLongRangeDescriptionsForm form);
    
    CommandResult editEntityLongRangeDescription(UserVisitPK userVisitPK, EditEntityLongRangeDescriptionForm form);
    
    CommandResult deleteEntityLongRangeDescription(UserVisitPK userVisitPK, DeleteEntityLongRangeDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Entity Long Ranges
    // -------------------------------------------------------------------------
    
    CommandResult createEntityLongRange(UserVisitPK userVisitPK, CreateEntityLongRangeForm form);
    
    CommandResult getEntityLongRange(UserVisitPK userVisitPK, GetEntityLongRangeForm form);
    
    CommandResult getEntityLongRanges(UserVisitPK userVisitPK, GetEntityLongRangesForm form);
    
    CommandResult getEntityLongRangeChoices(UserVisitPK userVisitPK, GetEntityLongRangeChoicesForm form);
    
    CommandResult setDefaultEntityLongRange(UserVisitPK userVisitPK, SetDefaultEntityLongRangeForm form);
    
    CommandResult editEntityLongRange(UserVisitPK userVisitPK, EditEntityLongRangeForm form);
    
    CommandResult deleteEntityLongRange(UserVisitPK userVisitPK, DeleteEntityLongRangeForm form);
    
    // -------------------------------------------------------------------------
    //   Entity Type Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult createEntityTypeDescription(UserVisitPK userVisitPK, CreateEntityTypeDescriptionForm form);
    
    CommandResult getEntityTypeDescription(UserVisitPK userVisitPK, GetEntityTypeDescriptionForm form);
    
    CommandResult getEntityTypeDescriptions(UserVisitPK userVisitPK, GetEntityTypeDescriptionsForm form);
    
    CommandResult editEntityTypeDescription(UserVisitPK userVisitPK, EditEntityTypeDescriptionForm form);
    
    CommandResult deleteEntityTypeDescription(UserVisitPK userVisitPK, DeleteEntityTypeDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Mime Type Usage Types
    // --------------------------------------------------------------------------------
    
    CommandResult createMimeTypeUsageType(UserVisitPK userVisitPK, CreateMimeTypeUsageTypeForm form);
    
    CommandResult getMimeTypeUsageType(UserVisitPK userVisitPK, GetMimeTypeUsageTypeForm form);

    CommandResult getMimeTypeUsageTypes(UserVisitPK userVisitPK, GetMimeTypeUsageTypesForm form);

    CommandResult getMimeTypeUsageTypeChoices(UserVisitPK userVisitPK, GetMimeTypeUsageTypeChoicesForm form);
    
    // --------------------------------------------------------------------------------
    //   Mime Type Usage Type Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult createMimeTypeUsageTypeDescription(UserVisitPK userVisitPK, CreateMimeTypeUsageTypeDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Mime Types
    // -------------------------------------------------------------------------

    CommandResult createMimeType(UserVisitPK userVisitPK, CreateMimeTypeForm form);

    CommandResult getMimeTypes(UserVisitPK userVisitPK, GetMimeTypesForm form);

    CommandResult getMimeType(UserVisitPK userVisitPK, GetMimeTypeForm form);

    CommandResult getMimeTypeChoices(UserVisitPK userVisitPK, GetMimeTypeChoicesForm form);

    CommandResult setDefaultMimeType(UserVisitPK userVisitPK, SetDefaultMimeTypeForm form);

    CommandResult editMimeType(UserVisitPK userVisitPK, EditMimeTypeForm form);

    CommandResult deleteMimeType(UserVisitPK userVisitPK, DeleteMimeTypeForm form);

    // -------------------------------------------------------------------------
    //   Mime Type Descriptions
    // -------------------------------------------------------------------------

    CommandResult createMimeTypeDescription(UserVisitPK userVisitPK, CreateMimeTypeDescriptionForm form);

    CommandResult getMimeTypeDescription(UserVisitPK userVisitPK, GetMimeTypeDescriptionForm form);

    CommandResult getMimeTypeDescriptions(UserVisitPK userVisitPK, GetMimeTypeDescriptionsForm form);

    CommandResult editMimeTypeDescription(UserVisitPK userVisitPK, EditMimeTypeDescriptionForm form);

    CommandResult deleteMimeTypeDescription(UserVisitPK userVisitPK, DeleteMimeTypeDescriptionForm form);

    // --------------------------------------------------------------------------------
    //   Mime Type Usages
    // --------------------------------------------------------------------------------
    
    CommandResult createMimeTypeUsage(UserVisitPK userVisitPK, CreateMimeTypeUsageForm form);
    
    CommandResult getMimeTypeUsages(UserVisitPK userVisitPK, GetMimeTypeUsagesForm form);
    
    // --------------------------------------------------------------------------------
    //   Mime Type File Extensions
    // --------------------------------------------------------------------------------
    
    CommandResult createMimeTypeFileExtension(UserVisitPK userVisitPK, CreateMimeTypeFileExtensionForm form);
    
    CommandResult getMimeTypeFileExtension(UserVisitPK userVisitPK, GetMimeTypeFileExtensionForm form);
    
    CommandResult getMimeTypeFileExtensions(UserVisitPK userVisitPK, GetMimeTypeFileExtensionsForm form);
    
    // --------------------------------------------------------------------------------
    //   Protocols
    // --------------------------------------------------------------------------------

    CommandResult createProtocol(UserVisitPK userVisitPK, CreateProtocolForm form);

    CommandResult getProtocolChoices(UserVisitPK userVisitPK, GetProtocolChoicesForm form);

    CommandResult getProtocol(UserVisitPK userVisitPK, GetProtocolForm form);

    CommandResult getProtocols(UserVisitPK userVisitPK, GetProtocolsForm form);

    CommandResult setDefaultProtocol(UserVisitPK userVisitPK, SetDefaultProtocolForm form);

    CommandResult editProtocol(UserVisitPK userVisitPK, EditProtocolForm form);

    CommandResult deleteProtocol(UserVisitPK userVisitPK, DeleteProtocolForm form);

    // --------------------------------------------------------------------------------
    //   Protocol Descriptions
    // --------------------------------------------------------------------------------

    CommandResult createProtocolDescription(UserVisitPK userVisitPK, CreateProtocolDescriptionForm form);

    CommandResult getProtocolDescription(UserVisitPK userVisitPK, GetProtocolDescriptionForm form);

    CommandResult getProtocolDescriptions(UserVisitPK userVisitPK, GetProtocolDescriptionsForm form);

    CommandResult editProtocolDescription(UserVisitPK userVisitPK, EditProtocolDescriptionForm form);

    CommandResult deleteProtocolDescription(UserVisitPK userVisitPK, DeleteProtocolDescriptionForm form);

    // --------------------------------------------------------------------------------
    //   Services
    // --------------------------------------------------------------------------------

    CommandResult createService(UserVisitPK userVisitPK, CreateServiceForm form);

    CommandResult getServiceChoices(UserVisitPK userVisitPK, GetServiceChoicesForm form);

    CommandResult getService(UserVisitPK userVisitPK, GetServiceForm form);

    CommandResult getServices(UserVisitPK userVisitPK, GetServicesForm form);

    CommandResult setDefaultService(UserVisitPK userVisitPK, SetDefaultServiceForm form);

    CommandResult editService(UserVisitPK userVisitPK, EditServiceForm form);

    CommandResult deleteService(UserVisitPK userVisitPK, DeleteServiceForm form);

    // --------------------------------------------------------------------------------
    //   Service Descriptions
    // --------------------------------------------------------------------------------

    CommandResult createServiceDescription(UserVisitPK userVisitPK, CreateServiceDescriptionForm form);

    CommandResult getServiceDescription(UserVisitPK userVisitPK, GetServiceDescriptionForm form);

    CommandResult getServiceDescriptions(UserVisitPK userVisitPK, GetServiceDescriptionsForm form);

    CommandResult editServiceDescription(UserVisitPK userVisitPK, EditServiceDescriptionForm form);

    CommandResult deleteServiceDescription(UserVisitPK userVisitPK, DeleteServiceDescriptionForm form);

    // --------------------------------------------------------------------------------
    //   Servers
    // --------------------------------------------------------------------------------

    CommandResult createServer(UserVisitPK userVisitPK, CreateServerForm form);

    CommandResult getServerChoices(UserVisitPK userVisitPK, GetServerChoicesForm form);

    CommandResult getServer(UserVisitPK userVisitPK, GetServerForm form);

    CommandResult getServers(UserVisitPK userVisitPK, GetServersForm form);

    CommandResult setDefaultServer(UserVisitPK userVisitPK, SetDefaultServerForm form);

    CommandResult editServer(UserVisitPK userVisitPK, EditServerForm form);

    CommandResult deleteServer(UserVisitPK userVisitPK, DeleteServerForm form);

    // --------------------------------------------------------------------------------
    //   Server Descriptions
    // --------------------------------------------------------------------------------

    CommandResult createServerDescription(UserVisitPK userVisitPK, CreateServerDescriptionForm form);

    CommandResult getServerDescription(UserVisitPK userVisitPK, GetServerDescriptionForm form);

    CommandResult getServerDescriptions(UserVisitPK userVisitPK, GetServerDescriptionsForm form);

    CommandResult editServerDescription(UserVisitPK userVisitPK, EditServerDescriptionForm form);

    CommandResult deleteServerDescription(UserVisitPK userVisitPK, DeleteServerDescriptionForm form);

    // --------------------------------------------------------------------------------
    //   Server Services
    // --------------------------------------------------------------------------------

    CommandResult createServerService(UserVisitPK userVisitPK, CreateServerServiceForm form);

    CommandResult getServerService(UserVisitPK userVisitPK, GetServerServiceForm form);

    CommandResult getServerServices(UserVisitPK userVisitPK, GetServerServicesForm form);

    CommandResult deleteServerService(UserVisitPK userVisitPK, DeleteServerServiceForm form);

    // --------------------------------------------------------------------------------
    //   Entity Boolean Attributes
    // --------------------------------------------------------------------------------
    
    CommandResult createEntityBooleanAttribute(UserVisitPK userVisitPK, CreateEntityBooleanAttributeForm form);
    
    CommandResult editEntityBooleanAttribute(UserVisitPK userVisitPK, EditEntityBooleanAttributeForm form);
    
    CommandResult deleteEntityBooleanAttribute(UserVisitPK userVisitPK, DeleteEntityBooleanAttributeForm form);
    
    // --------------------------------------------------------------------------------
    //   Entity Integer Attributes
    // --------------------------------------------------------------------------------
    
    CommandResult createEntityIntegerAttribute(UserVisitPK userVisitPK, CreateEntityIntegerAttributeForm form);
    
    CommandResult editEntityIntegerAttribute(UserVisitPK userVisitPK, EditEntityIntegerAttributeForm form);
    
    CommandResult deleteEntityIntegerAttribute(UserVisitPK userVisitPK, DeleteEntityIntegerAttributeForm form);
    
    // --------------------------------------------------------------------------------
    //   Entity List Item Attributes
    // --------------------------------------------------------------------------------
    
    CommandResult createEntityListItemAttribute(UserVisitPK userVisitPK, CreateEntityListItemAttributeForm form);
    
    CommandResult editEntityListItemAttribute(UserVisitPK userVisitPK, EditEntityListItemAttributeForm form);
    
    CommandResult deleteEntityListItemAttribute(UserVisitPK userVisitPK, DeleteEntityListItemAttributeForm form);
    
    // --------------------------------------------------------------------------------
    //   Entity Long Attributes
    // --------------------------------------------------------------------------------
    
    CommandResult createEntityLongAttribute(UserVisitPK userVisitPK, CreateEntityLongAttributeForm form);
    
    CommandResult editEntityLongAttribute(UserVisitPK userVisitPK, EditEntityLongAttributeForm form);
    
    CommandResult deleteEntityLongAttribute(UserVisitPK userVisitPK, DeleteEntityLongAttributeForm form);
    
    // --------------------------------------------------------------------------------
    //   Entity Multiple List Item Attributes
    // --------------------------------------------------------------------------------
    
    CommandResult createEntityMultipleListItemAttribute(UserVisitPK userVisitPK, CreateEntityMultipleListItemAttributeForm form);
    
    CommandResult deleteEntityMultipleListItemAttribute(UserVisitPK userVisitPK, DeleteEntityMultipleListItemAttributeForm form);
    
    // --------------------------------------------------------------------------------
    //   Entity Name Attributes
    // --------------------------------------------------------------------------------
    
    CommandResult createEntityNameAttribute(UserVisitPK userVisitPK, CreateEntityNameAttributeForm form);
    
    CommandResult editEntityNameAttribute(UserVisitPK userVisitPK, EditEntityNameAttributeForm form);
    
    CommandResult deleteEntityNameAttribute(UserVisitPK userVisitPK, DeleteEntityNameAttributeForm form);
    
    // --------------------------------------------------------------------------------
    //   Entity String Attributes
    // --------------------------------------------------------------------------------
    
    CommandResult createEntityStringAttribute(UserVisitPK userVisitPK, CreateEntityStringAttributeForm form);
    
    CommandResult editEntityStringAttribute(UserVisitPK userVisitPK, EditEntityStringAttributeForm form);
    
    CommandResult deleteEntityStringAttribute(UserVisitPK userVisitPK, DeleteEntityStringAttributeForm form);
    
    // --------------------------------------------------------------------------------
    //   Entity Geo Point Attributes
    // --------------------------------------------------------------------------------
    
    CommandResult createEntityGeoPointAttribute(UserVisitPK userVisitPK, CreateEntityGeoPointAttributeForm form);
    
    CommandResult editEntityGeoPointAttribute(UserVisitPK userVisitPK, EditEntityGeoPointAttributeForm form);
    
    CommandResult deleteEntityGeoPointAttribute(UserVisitPK userVisitPK, DeleteEntityGeoPointAttributeForm form);
    
    // --------------------------------------------------------------------------------
    //   Entity Blob Attributes
    // --------------------------------------------------------------------------------
    
    CommandResult createEntityBlobAttribute(UserVisitPK userVisitPK, CreateEntityBlobAttributeForm form);

    CommandResult editEntityBlobAttribute(UserVisitPK userVisitPK, EditEntityBlobAttributeForm form);

    CommandResult getEntityBlobAttribute(UserVisitPK userVisitPK, GetEntityBlobAttributeForm form);

    CommandResult deleteEntityBlobAttribute(UserVisitPK userVisitPK, DeleteEntityBlobAttributeForm form);
    
    // --------------------------------------------------------------------------------
    //   Entity Clob Attributes
    // --------------------------------------------------------------------------------
    
    CommandResult createEntityClobAttribute(UserVisitPK userVisitPK, CreateEntityClobAttributeForm form);

    CommandResult editEntityClobAttribute(UserVisitPK userVisitPK, EditEntityClobAttributeForm form);

    CommandResult getEntityClobAttribute(UserVisitPK userVisitPK, GetEntityClobAttributeForm form);

    CommandResult deleteEntityClobAttribute(UserVisitPK userVisitPK, DeleteEntityClobAttributeForm form);
    
    // --------------------------------------------------------------------------------
    //   Entity Time Attributes
    // --------------------------------------------------------------------------------

    CommandResult createEntityTimeAttribute(UserVisitPK userVisitPK, CreateEntityTimeAttributeForm form);

    CommandResult editEntityTimeAttribute(UserVisitPK userVisitPK, EditEntityTimeAttributeForm form);

    CommandResult deleteEntityTimeAttribute(UserVisitPK userVisitPK, DeleteEntityTimeAttributeForm form);

    // --------------------------------------------------------------------------------
    //   Entity Date Attributes
    // --------------------------------------------------------------------------------

    CommandResult createEntityDateAttribute(UserVisitPK userVisitPK, CreateEntityDateAttributeForm form);

    CommandResult editEntityDateAttribute(UserVisitPK userVisitPK, EditEntityDateAttributeForm form);

    CommandResult deleteEntityDateAttribute(UserVisitPK userVisitPK, DeleteEntityDateAttributeForm form);

    // --------------------------------------------------------------------------------
    //   Entity Attribute Entity Types
    // --------------------------------------------------------------------------------
    
    CommandResult createEntityAttributeEntityType(UserVisitPK userVisitPK, CreateEntityAttributeEntityTypeForm form);

    CommandResult getEntityAttributeEntityType(UserVisitPK userVisitPK, GetEntityAttributeEntityTypeForm form);

    CommandResult getEntityAttributeEntityTypes(UserVisitPK userVisitPK, GetEntityAttributeEntityTypesForm form);
    
    CommandResult deleteEntityAttributeEntityType(UserVisitPK userVisitPK, DeleteEntityAttributeEntityTypeForm form);

    // --------------------------------------------------------------------------------
    //   Entity Entity Attributes
    // --------------------------------------------------------------------------------
    
    CommandResult createEntityEntityAttribute(UserVisitPK userVisitPK, CreateEntityEntityAttributeForm form);
    
    CommandResult editEntityEntityAttribute(UserVisitPK userVisitPK, EditEntityEntityAttributeForm form);
    
    CommandResult deleteEntityEntityAttribute(UserVisitPK userVisitPK, DeleteEntityEntityAttributeForm form);
    
    // --------------------------------------------------------------------------------
    //   Entity Collection Attributes
    // --------------------------------------------------------------------------------
    
    CommandResult createEntityCollectionAttribute(UserVisitPK userVisitPK, CreateEntityCollectionAttributeForm form);
    
    CommandResult deleteEntityCollectionAttribute(UserVisitPK userVisitPK, DeleteEntityCollectionAttributeForm form);
    
    // -------------------------------------------------------------------------
    //   Party Entity Types
    // -------------------------------------------------------------------------
    
    CommandResult createPartyEntityType(UserVisitPK userVisitPK, CreatePartyEntityTypeForm form);
    
    CommandResult editPartyEntityType(UserVisitPK userVisitPK, EditPartyEntityTypeForm form);
    
    CommandResult getPartyEntityType(UserVisitPK userVisitPK, GetPartyEntityTypeForm form);
    
    CommandResult getPartyEntityTypes(UserVisitPK userVisitPK, GetPartyEntityTypesForm form);
    
    CommandResult deletePartyEntityType(UserVisitPK userVisitPK, DeletePartyEntityTypeForm form);
    
    // -------------------------------------------------------------------------
    //   Cache Entries
    // -------------------------------------------------------------------------

    CommandResult createCacheEntry(UserVisitPK userVisitPK, CreateCacheEntryForm form);

    CommandResult getCacheEntry(UserVisitPK userVisitPK, GetCacheEntryForm form);

    CommandResult getCacheEntries(UserVisitPK userVisitPK, GetCacheEntriesForm form);

    CommandResult removeCacheEntry(UserVisitPK userVisitPK, RemoveCacheEntryForm form);

    // -------------------------------------------------------------------------
    //   Cache Entry Dependencies
    // -------------------------------------------------------------------------

    CommandResult getCacheEntryDependencies(UserVisitPK userVisitPK, GetCacheEntryDependenciesForm form);

    // --------------------------------------------------------------------------------
    //   Applications
    // --------------------------------------------------------------------------------
    
    CommandResult createApplication(UserVisitPK userVisitPK, CreateApplicationForm form);
    
    CommandResult getApplicationChoices(UserVisitPK userVisitPK, GetApplicationChoicesForm form);
    
    CommandResult getApplication(UserVisitPK userVisitPK, GetApplicationForm form);
    
    CommandResult getApplications(UserVisitPK userVisitPK, GetApplicationsForm form);
    
    CommandResult setDefaultApplication(UserVisitPK userVisitPK, SetDefaultApplicationForm form);
    
    CommandResult editApplication(UserVisitPK userVisitPK, EditApplicationForm form);
    
    CommandResult deleteApplication(UserVisitPK userVisitPK, DeleteApplicationForm form);
    
    // --------------------------------------------------------------------------------
    //   Application Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult createApplicationDescription(UserVisitPK userVisitPK, CreateApplicationDescriptionForm form);
    
    CommandResult getApplicationDescription(UserVisitPK userVisitPK, GetApplicationDescriptionForm form);
    
    CommandResult getApplicationDescriptions(UserVisitPK userVisitPK, GetApplicationDescriptionsForm form);
    
    CommandResult editApplicationDescription(UserVisitPK userVisitPK, EditApplicationDescriptionForm form);
    
    CommandResult deleteApplicationDescription(UserVisitPK userVisitPK, DeleteApplicationDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Editors
    // --------------------------------------------------------------------------------
    
    CommandResult createEditor(UserVisitPK userVisitPK, CreateEditorForm form);
    
    CommandResult getEditorChoices(UserVisitPK userVisitPK, GetEditorChoicesForm form);
    
    CommandResult getEditor(UserVisitPK userVisitPK, GetEditorForm form);
    
    CommandResult getEditors(UserVisitPK userVisitPK, GetEditorsForm form);
    
    CommandResult setDefaultEditor(UserVisitPK userVisitPK, SetDefaultEditorForm form);
    
    CommandResult editEditor(UserVisitPK userVisitPK, EditEditorForm form);
    
    CommandResult deleteEditor(UserVisitPK userVisitPK, DeleteEditorForm form);
    
    // --------------------------------------------------------------------------------
    //   Editor Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult createEditorDescription(UserVisitPK userVisitPK, CreateEditorDescriptionForm form);
    
    CommandResult getEditorDescription(UserVisitPK userVisitPK, GetEditorDescriptionForm form);
    
    CommandResult getEditorDescriptions(UserVisitPK userVisitPK, GetEditorDescriptionsForm form);
    
    CommandResult editEditorDescription(UserVisitPK userVisitPK, EditEditorDescriptionForm form);
    
    CommandResult deleteEditorDescription(UserVisitPK userVisitPK, DeleteEditorDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Application Editors
    // --------------------------------------------------------------------------------
    
    CommandResult createApplicationEditor(UserVisitPK userVisitPK, CreateApplicationEditorForm form);
    
    CommandResult getApplicationEditorChoices(UserVisitPK userVisitPK, GetApplicationEditorChoicesForm form);
    
    CommandResult getApplicationEditor(UserVisitPK userVisitPK, GetApplicationEditorForm form);
    
    CommandResult getApplicationEditors(UserVisitPK userVisitPK, GetApplicationEditorsForm form);
    
    CommandResult setDefaultApplicationEditor(UserVisitPK userVisitPK, SetDefaultApplicationEditorForm form);
    
    CommandResult editApplicationEditor(UserVisitPK userVisitPK, EditApplicationEditorForm form);
    
    CommandResult deleteApplicationEditor(UserVisitPK userVisitPK, DeleteApplicationEditorForm form);
    
    // --------------------------------------------------------------------------------
    //   Application Editor Uses
    // --------------------------------------------------------------------------------
    
    CommandResult createApplicationEditorUse(UserVisitPK userVisitPK, CreateApplicationEditorUseForm form);
    
    CommandResult getApplicationEditorUseChoices(UserVisitPK userVisitPK, GetApplicationEditorUseChoicesForm form);
    
    CommandResult getApplicationEditorUse(UserVisitPK userVisitPK, GetApplicationEditorUseForm form);
    
    CommandResult getApplicationEditorUses(UserVisitPK userVisitPK, GetApplicationEditorUsesForm form);
    
    CommandResult setDefaultApplicationEditorUse(UserVisitPK userVisitPK, SetDefaultApplicationEditorUseForm form);
    
    CommandResult editApplicationEditorUse(UserVisitPK userVisitPK, EditApplicationEditorUseForm form);
    
    CommandResult deleteApplicationEditorUse(UserVisitPK userVisitPK, DeleteApplicationEditorUseForm form);
    
    // --------------------------------------------------------------------------------
    //   Application Editor Use Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult createApplicationEditorUseDescription(UserVisitPK userVisitPK, CreateApplicationEditorUseDescriptionForm form);
    
    CommandResult getApplicationEditorUseDescription(UserVisitPK userVisitPK, GetApplicationEditorUseDescriptionForm form);
    
    CommandResult getApplicationEditorUseDescriptions(UserVisitPK userVisitPK, GetApplicationEditorUseDescriptionsForm form);
    
    CommandResult editApplicationEditorUseDescription(UserVisitPK userVisitPK, EditApplicationEditorUseDescriptionForm form);
    
    CommandResult deleteApplicationEditorUseDescription(UserVisitPK userVisitPK, DeleteApplicationEditorUseDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Party Application Editor Uses
    // --------------------------------------------------------------------------------
    
    CommandResult createPartyApplicationEditorUse(UserVisitPK userVisitPK, CreatePartyApplicationEditorUseForm form);
    
    CommandResult getPartyApplicationEditorUse(UserVisitPK userVisitPK, GetPartyApplicationEditorUseForm form);
    
    CommandResult getPartyApplicationEditorUses(UserVisitPK userVisitPK, GetPartyApplicationEditorUsesForm form);
    
    CommandResult editPartyApplicationEditorUse(UserVisitPK userVisitPK, EditPartyApplicationEditorUseForm form);
    
    CommandResult deletePartyApplicationEditorUse(UserVisitPK userVisitPK, DeletePartyApplicationEditorUseForm form);
    
    // --------------------------------------------------------------------------------
    //   Appearances
    // --------------------------------------------------------------------------------
    
    CommandResult createAppearance(UserVisitPK userVisitPK, CreateAppearanceForm form);
    
    CommandResult getAppearanceChoices(UserVisitPK userVisitPK, GetAppearanceChoicesForm form);
    
    CommandResult getAppearance(UserVisitPK userVisitPK, GetAppearanceForm form);
    
    CommandResult getAppearances(UserVisitPK userVisitPK, GetAppearancesForm form);
    
    CommandResult setDefaultAppearance(UserVisitPK userVisitPK, SetDefaultAppearanceForm form);
    
    CommandResult editAppearance(UserVisitPK userVisitPK, EditAppearanceForm form);
    
    CommandResult deleteAppearance(UserVisitPK userVisitPK, DeleteAppearanceForm form);
    
    // --------------------------------------------------------------------------------
    //   Appearance Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult createAppearanceDescription(UserVisitPK userVisitPK, CreateAppearanceDescriptionForm form);
    
    CommandResult getAppearanceDescription(UserVisitPK userVisitPK, GetAppearanceDescriptionForm form);
    
    CommandResult getAppearanceDescriptions(UserVisitPK userVisitPK, GetAppearanceDescriptionsForm form);
    
    CommandResult editAppearanceDescription(UserVisitPK userVisitPK, EditAppearanceDescriptionForm form);
    
    CommandResult deleteAppearanceDescription(UserVisitPK userVisitPK, DeleteAppearanceDescriptionForm form);
    
     // --------------------------------------------------------------------------------
    //   Appearance Text Decorations
    // --------------------------------------------------------------------------------
    
    CommandResult createAppearanceTextDecoration(UserVisitPK userVisitPK, CreateAppearanceTextDecorationForm form);
    
    CommandResult getAppearanceTextDecoration(UserVisitPK userVisitPK, GetAppearanceTextDecorationForm form);
    
    CommandResult getAppearanceTextDecorations(UserVisitPK userVisitPK, GetAppearanceTextDecorationsForm form);
    
    CommandResult deleteAppearanceTextDecoration(UserVisitPK userVisitPK, DeleteAppearanceTextDecorationForm form);
    
     // --------------------------------------------------------------------------------
    //   Appearance Text Transformations
    // --------------------------------------------------------------------------------
    
    CommandResult createAppearanceTextTransformation(UserVisitPK userVisitPK, CreateAppearanceTextTransformationForm form);
    
    CommandResult getAppearanceTextTransformation(UserVisitPK userVisitPK, GetAppearanceTextTransformationForm form);
    
    CommandResult getAppearanceTextTransformations(UserVisitPK userVisitPK, GetAppearanceTextTransformationsForm form);
    
    CommandResult deleteAppearanceTextTransformation(UserVisitPK userVisitPK, DeleteAppearanceTextTransformationForm form);
    
   // --------------------------------------------------------------------------------
    //   Colors
    // --------------------------------------------------------------------------------
    
    CommandResult createColor(UserVisitPK userVisitPK, CreateColorForm form);
    
    CommandResult getColorChoices(UserVisitPK userVisitPK, GetColorChoicesForm form);
    
    CommandResult getColor(UserVisitPK userVisitPK, GetColorForm form);
    
    CommandResult getColors(UserVisitPK userVisitPK, GetColorsForm form);
    
    CommandResult setDefaultColor(UserVisitPK userVisitPK, SetDefaultColorForm form);
    
    CommandResult editColor(UserVisitPK userVisitPK, EditColorForm form);
    
    CommandResult deleteColor(UserVisitPK userVisitPK, DeleteColorForm form);
    
    // --------------------------------------------------------------------------------
    //   Color Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult createColorDescription(UserVisitPK userVisitPK, CreateColorDescriptionForm form);
    
    CommandResult getColorDescription(UserVisitPK userVisitPK, GetColorDescriptionForm form);
    
    CommandResult getColorDescriptions(UserVisitPK userVisitPK, GetColorDescriptionsForm form);
    
    CommandResult editColorDescription(UserVisitPK userVisitPK, EditColorDescriptionForm form);
    
    CommandResult deleteColorDescription(UserVisitPK userVisitPK, DeleteColorDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Font Styles
    // --------------------------------------------------------------------------------
    
    CommandResult createFontStyle(UserVisitPK userVisitPK, CreateFontStyleForm form);
    
    CommandResult getFontStyleChoices(UserVisitPK userVisitPK, GetFontStyleChoicesForm form);
    
    CommandResult getFontStyle(UserVisitPK userVisitPK, GetFontStyleForm form);
    
    CommandResult getFontStyles(UserVisitPK userVisitPK, GetFontStylesForm form);
    
    CommandResult setDefaultFontStyle(UserVisitPK userVisitPK, SetDefaultFontStyleForm form);
    
    CommandResult editFontStyle(UserVisitPK userVisitPK, EditFontStyleForm form);
    
    CommandResult deleteFontStyle(UserVisitPK userVisitPK, DeleteFontStyleForm form);
    
    // --------------------------------------------------------------------------------
    //   Font Style Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult createFontStyleDescription(UserVisitPK userVisitPK, CreateFontStyleDescriptionForm form);
    
    CommandResult getFontStyleDescription(UserVisitPK userVisitPK, GetFontStyleDescriptionForm form);
    
    CommandResult getFontStyleDescriptions(UserVisitPK userVisitPK, GetFontStyleDescriptionsForm form);
    
    CommandResult editFontStyleDescription(UserVisitPK userVisitPK, EditFontStyleDescriptionForm form);
    
    CommandResult deleteFontStyleDescription(UserVisitPK userVisitPK, DeleteFontStyleDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Font Weights
    // --------------------------------------------------------------------------------
    
    CommandResult createFontWeight(UserVisitPK userVisitPK, CreateFontWeightForm form);
    
    CommandResult getFontWeightChoices(UserVisitPK userVisitPK, GetFontWeightChoicesForm form);
    
    CommandResult getFontWeight(UserVisitPK userVisitPK, GetFontWeightForm form);
    
    CommandResult getFontWeights(UserVisitPK userVisitPK, GetFontWeightsForm form);
    
    CommandResult setDefaultFontWeight(UserVisitPK userVisitPK, SetDefaultFontWeightForm form);
    
    CommandResult editFontWeight(UserVisitPK userVisitPK, EditFontWeightForm form);
    
    CommandResult deleteFontWeight(UserVisitPK userVisitPK, DeleteFontWeightForm form);
    
    // --------------------------------------------------------------------------------
    //   Font Weight Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult createFontWeightDescription(UserVisitPK userVisitPK, CreateFontWeightDescriptionForm form);
    
    CommandResult getFontWeightDescription(UserVisitPK userVisitPK, GetFontWeightDescriptionForm form);
    
    CommandResult getFontWeightDescriptions(UserVisitPK userVisitPK, GetFontWeightDescriptionsForm form);
    
    CommandResult editFontWeightDescription(UserVisitPK userVisitPK, EditFontWeightDescriptionForm form);
    
    CommandResult deleteFontWeightDescription(UserVisitPK userVisitPK, DeleteFontWeightDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Font Decorations
    // --------------------------------------------------------------------------------
    
    CommandResult createTextDecoration(UserVisitPK userVisitPK, CreateTextDecorationForm form);
    
    CommandResult getTextDecorationChoices(UserVisitPK userVisitPK, GetTextDecorationChoicesForm form);
    
    CommandResult getTextDecoration(UserVisitPK userVisitPK, GetTextDecorationForm form);
    
    CommandResult getTextDecorations(UserVisitPK userVisitPK, GetTextDecorationsForm form);
    
    CommandResult setDefaultTextDecoration(UserVisitPK userVisitPK, SetDefaultTextDecorationForm form);
    
    CommandResult editTextDecoration(UserVisitPK userVisitPK, EditTextDecorationForm form);
    
    CommandResult deleteTextDecoration(UserVisitPK userVisitPK, DeleteTextDecorationForm form);
    
    // --------------------------------------------------------------------------------
    //   Font Decoration Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult createTextDecorationDescription(UserVisitPK userVisitPK, CreateTextDecorationDescriptionForm form);
    
    CommandResult getTextDecorationDescription(UserVisitPK userVisitPK, GetTextDecorationDescriptionForm form);
    
    CommandResult getTextDecorationDescriptions(UserVisitPK userVisitPK, GetTextDecorationDescriptionsForm form);
    
    CommandResult editTextDecorationDescription(UserVisitPK userVisitPK, EditTextDecorationDescriptionForm form);
    
    CommandResult deleteTextDecorationDescription(UserVisitPK userVisitPK, DeleteTextDecorationDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Font Transformations
    // --------------------------------------------------------------------------------
    
    CommandResult createTextTransformation(UserVisitPK userVisitPK, CreateTextTransformationForm form);
    
    CommandResult getTextTransformationChoices(UserVisitPK userVisitPK, GetTextTransformationChoicesForm form);
    
    CommandResult getTextTransformation(UserVisitPK userVisitPK, GetTextTransformationForm form);
    
    CommandResult getTextTransformations(UserVisitPK userVisitPK, GetTextTransformationsForm form);
    
    CommandResult setDefaultTextTransformation(UserVisitPK userVisitPK, SetDefaultTextTransformationForm form);
    
    CommandResult editTextTransformation(UserVisitPK userVisitPK, EditTextTransformationForm form);
    
    CommandResult deleteTextTransformation(UserVisitPK userVisitPK, DeleteTextTransformationForm form);
    
    // --------------------------------------------------------------------------------
    //   Font Transformation Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult createTextTransformationDescription(UserVisitPK userVisitPK, CreateTextTransformationDescriptionForm form);
    
    CommandResult getTextTransformationDescription(UserVisitPK userVisitPK, GetTextTransformationDescriptionForm form);
    
    CommandResult getTextTransformationDescriptions(UserVisitPK userVisitPK, GetTextTransformationDescriptionsForm form);
    
    CommandResult editTextTransformationDescription(UserVisitPK userVisitPK, EditTextTransformationDescriptionForm form);
    
    CommandResult deleteTextTransformationDescription(UserVisitPK userVisitPK, DeleteTextTransformationDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   Entity Appearances
    // --------------------------------------------------------------------------------
    
    CommandResult createEntityAppearance(UserVisitPK userVisitPK, CreateEntityAppearanceForm form);
    
    CommandResult getEntityAppearance(UserVisitPK userVisitPK, GetEntityAppearanceForm form);
    
    CommandResult editEntityAppearance(UserVisitPK userVisitPK, EditEntityAppearanceForm form);
    
    CommandResult deleteEntityAppearance(UserVisitPK userVisitPK, DeleteEntityAppearanceForm form);
    
    // -------------------------------------------------------------------------
    //   Utilities
    // -------------------------------------------------------------------------

    CommandResult encrypt(UserVisitPK userVisitPK, EncryptForm form);

    CommandResult decrypt(UserVisitPK userVisitPK, DecryptForm form);

    CommandResult validate(UserVisitPK userVisit, ValidateForm form);

}
