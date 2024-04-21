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

package com.echothree.control.user.core.server;

import com.echothree.control.user.core.common.CoreRemote;
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
import com.echothree.control.user.core.server.command.ChangeBaseKeysCommand;
import com.echothree.control.user.core.server.command.CreateAppearanceCommand;
import com.echothree.control.user.core.server.command.CreateAppearanceDescriptionCommand;
import com.echothree.control.user.core.server.command.CreateAppearanceTextDecorationCommand;
import com.echothree.control.user.core.server.command.CreateAppearanceTextTransformationCommand;
import com.echothree.control.user.core.server.command.CreateApplicationCommand;
import com.echothree.control.user.core.server.command.CreateApplicationDescriptionCommand;
import com.echothree.control.user.core.server.command.CreateApplicationEditorCommand;
import com.echothree.control.user.core.server.command.CreateApplicationEditorUseCommand;
import com.echothree.control.user.core.server.command.CreateApplicationEditorUseDescriptionCommand;
import com.echothree.control.user.core.server.command.CreateCacheEntryCommand;
import com.echothree.control.user.core.server.command.CreateColorCommand;
import com.echothree.control.user.core.server.command.CreateColorDescriptionCommand;
import com.echothree.control.user.core.server.command.CreateCommandCommand;
import com.echothree.control.user.core.server.command.CreateCommandDescriptionCommand;
import com.echothree.control.user.core.server.command.CreateCommandMessageCommand;
import com.echothree.control.user.core.server.command.CreateCommandMessageTranslationCommand;
import com.echothree.control.user.core.server.command.CreateCommandMessageTypeCommand;
import com.echothree.control.user.core.server.command.CreateCommandMessageTypeDescriptionCommand;
import com.echothree.control.user.core.server.command.CreateComponentCommand;
import com.echothree.control.user.core.server.command.CreateComponentStageCommand;
import com.echothree.control.user.core.server.command.CreateComponentVendorCommand;
import com.echothree.control.user.core.server.command.CreateComponentVersionCommand;
import com.echothree.control.user.core.server.command.CreateEditorCommand;
import com.echothree.control.user.core.server.command.CreateEditorDescriptionCommand;
import com.echothree.control.user.core.server.command.CreateEntityAppearanceCommand;
import com.echothree.control.user.core.server.command.CreateEntityAttributeCommand;
import com.echothree.control.user.core.server.command.CreateEntityAttributeDescriptionCommand;
import com.echothree.control.user.core.server.command.CreateEntityAttributeEntityAttributeGroupCommand;
import com.echothree.control.user.core.server.command.CreateEntityAttributeEntityTypeCommand;
import com.echothree.control.user.core.server.command.CreateEntityAttributeGroupCommand;
import com.echothree.control.user.core.server.command.CreateEntityAttributeGroupDescriptionCommand;
import com.echothree.control.user.core.server.command.CreateEntityAttributeTypeCommand;
import com.echothree.control.user.core.server.command.CreateEntityAttributeTypeDescriptionCommand;
import com.echothree.control.user.core.server.command.CreateEntityBlobAttributeCommand;
import com.echothree.control.user.core.server.command.CreateEntityBooleanAttributeCommand;
import com.echothree.control.user.core.server.command.CreateEntityClobAttributeCommand;
import com.echothree.control.user.core.server.command.CreateEntityCollectionAttributeCommand;
import com.echothree.control.user.core.server.command.CreateEntityDateAttributeCommand;
import com.echothree.control.user.core.server.command.CreateEntityEntityAttributeCommand;
import com.echothree.control.user.core.server.command.CreateEntityGeoPointAttributeCommand;
import com.echothree.control.user.core.server.command.CreateEntityInstanceCommand;
import com.echothree.control.user.core.server.command.CreateEntityIntegerAttributeCommand;
import com.echothree.control.user.core.server.command.CreateEntityIntegerRangeCommand;
import com.echothree.control.user.core.server.command.CreateEntityIntegerRangeDescriptionCommand;
import com.echothree.control.user.core.server.command.CreateEntityListItemAttributeCommand;
import com.echothree.control.user.core.server.command.CreateEntityListItemCommand;
import com.echothree.control.user.core.server.command.CreateEntityListItemDescriptionCommand;
import com.echothree.control.user.core.server.command.CreateEntityLongAttributeCommand;
import com.echothree.control.user.core.server.command.CreateEntityLongRangeCommand;
import com.echothree.control.user.core.server.command.CreateEntityLongRangeDescriptionCommand;
import com.echothree.control.user.core.server.command.CreateEntityMultipleListItemAttributeCommand;
import com.echothree.control.user.core.server.command.CreateEntityNameAttributeCommand;
import com.echothree.control.user.core.server.command.CreateEntityStringAttributeCommand;
import com.echothree.control.user.core.server.command.CreateEntityTimeAttributeCommand;
import com.echothree.control.user.core.server.command.CreateEntityTypeCommand;
import com.echothree.control.user.core.server.command.CreateEntityTypeDescriptionCommand;
import com.echothree.control.user.core.server.command.CreateEventTypeCommand;
import com.echothree.control.user.core.server.command.CreateEventTypeDescriptionCommand;
import com.echothree.control.user.core.server.command.CreateFontStyleCommand;
import com.echothree.control.user.core.server.command.CreateFontStyleDescriptionCommand;
import com.echothree.control.user.core.server.command.CreateFontWeightCommand;
import com.echothree.control.user.core.server.command.CreateFontWeightDescriptionCommand;
import com.echothree.control.user.core.server.command.CreateMimeTypeCommand;
import com.echothree.control.user.core.server.command.CreateMimeTypeDescriptionCommand;
import com.echothree.control.user.core.server.command.CreateMimeTypeFileExtensionCommand;
import com.echothree.control.user.core.server.command.CreateMimeTypeUsageCommand;
import com.echothree.control.user.core.server.command.CreateMimeTypeUsageTypeCommand;
import com.echothree.control.user.core.server.command.CreateMimeTypeUsageTypeDescriptionCommand;
import com.echothree.control.user.core.server.command.CreatePartyApplicationEditorUseCommand;
import com.echothree.control.user.core.server.command.CreatePartyEntityTypeCommand;
import com.echothree.control.user.core.server.command.CreateProtocolCommand;
import com.echothree.control.user.core.server.command.CreateProtocolDescriptionCommand;
import com.echothree.control.user.core.server.command.CreateServerCommand;
import com.echothree.control.user.core.server.command.CreateServerDescriptionCommand;
import com.echothree.control.user.core.server.command.CreateServerServiceCommand;
import com.echothree.control.user.core.server.command.CreateServiceCommand;
import com.echothree.control.user.core.server.command.CreateServiceDescriptionCommand;
import com.echothree.control.user.core.server.command.CreateTextDecorationCommand;
import com.echothree.control.user.core.server.command.CreateTextDecorationDescriptionCommand;
import com.echothree.control.user.core.server.command.CreateTextTransformationCommand;
import com.echothree.control.user.core.server.command.CreateTextTransformationDescriptionCommand;
import com.echothree.control.user.core.server.command.DecryptCommand;
import com.echothree.control.user.core.server.command.DeleteAppearanceCommand;
import com.echothree.control.user.core.server.command.DeleteAppearanceDescriptionCommand;
import com.echothree.control.user.core.server.command.DeleteAppearanceTextDecorationCommand;
import com.echothree.control.user.core.server.command.DeleteAppearanceTextTransformationCommand;
import com.echothree.control.user.core.server.command.DeleteApplicationCommand;
import com.echothree.control.user.core.server.command.DeleteApplicationDescriptionCommand;
import com.echothree.control.user.core.server.command.DeleteApplicationEditorCommand;
import com.echothree.control.user.core.server.command.DeleteApplicationEditorUseCommand;
import com.echothree.control.user.core.server.command.DeleteApplicationEditorUseDescriptionCommand;
import com.echothree.control.user.core.server.command.DeleteColorCommand;
import com.echothree.control.user.core.server.command.DeleteColorDescriptionCommand;
import com.echothree.control.user.core.server.command.DeleteCommandCommand;
import com.echothree.control.user.core.server.command.DeleteCommandDescriptionCommand;
import com.echothree.control.user.core.server.command.DeleteCommandMessageCommand;
import com.echothree.control.user.core.server.command.DeleteCommandMessageTranslationCommand;
import com.echothree.control.user.core.server.command.DeleteCommandMessageTypeCommand;
import com.echothree.control.user.core.server.command.DeleteCommandMessageTypeDescriptionCommand;
import com.echothree.control.user.core.server.command.DeleteComponentVendorCommand;
import com.echothree.control.user.core.server.command.DeleteEditorCommand;
import com.echothree.control.user.core.server.command.DeleteEditorDescriptionCommand;
import com.echothree.control.user.core.server.command.DeleteEntityAppearanceCommand;
import com.echothree.control.user.core.server.command.DeleteEntityAttributeCommand;
import com.echothree.control.user.core.server.command.DeleteEntityAttributeDescriptionCommand;
import com.echothree.control.user.core.server.command.DeleteEntityAttributeEntityAttributeGroupCommand;
import com.echothree.control.user.core.server.command.DeleteEntityAttributeEntityTypeCommand;
import com.echothree.control.user.core.server.command.DeleteEntityAttributeGroupCommand;
import com.echothree.control.user.core.server.command.DeleteEntityAttributeGroupDescriptionCommand;
import com.echothree.control.user.core.server.command.DeleteEntityBlobAttributeCommand;
import com.echothree.control.user.core.server.command.DeleteEntityBooleanAttributeCommand;
import com.echothree.control.user.core.server.command.DeleteEntityClobAttributeCommand;
import com.echothree.control.user.core.server.command.DeleteEntityCollectionAttributeCommand;
import com.echothree.control.user.core.server.command.DeleteEntityDateAttributeCommand;
import com.echothree.control.user.core.server.command.DeleteEntityEntityAttributeCommand;
import com.echothree.control.user.core.server.command.DeleteEntityGeoPointAttributeCommand;
import com.echothree.control.user.core.server.command.DeleteEntityIntegerAttributeCommand;
import com.echothree.control.user.core.server.command.DeleteEntityIntegerRangeCommand;
import com.echothree.control.user.core.server.command.DeleteEntityIntegerRangeDescriptionCommand;
import com.echothree.control.user.core.server.command.DeleteEntityListItemAttributeCommand;
import com.echothree.control.user.core.server.command.DeleteEntityListItemCommand;
import com.echothree.control.user.core.server.command.DeleteEntityListItemDescriptionCommand;
import com.echothree.control.user.core.server.command.DeleteEntityLongAttributeCommand;
import com.echothree.control.user.core.server.command.DeleteEntityLongRangeCommand;
import com.echothree.control.user.core.server.command.DeleteEntityLongRangeDescriptionCommand;
import com.echothree.control.user.core.server.command.DeleteEntityMultipleListItemAttributeCommand;
import com.echothree.control.user.core.server.command.DeleteEntityNameAttributeCommand;
import com.echothree.control.user.core.server.command.DeleteEntityStringAttributeCommand;
import com.echothree.control.user.core.server.command.DeleteEntityTimeAttributeCommand;
import com.echothree.control.user.core.server.command.DeleteEntityTypeCommand;
import com.echothree.control.user.core.server.command.DeleteEntityTypeDescriptionCommand;
import com.echothree.control.user.core.server.command.DeleteFontStyleCommand;
import com.echothree.control.user.core.server.command.DeleteFontStyleDescriptionCommand;
import com.echothree.control.user.core.server.command.DeleteFontWeightCommand;
import com.echothree.control.user.core.server.command.DeleteFontWeightDescriptionCommand;
import com.echothree.control.user.core.server.command.DeleteMimeTypeCommand;
import com.echothree.control.user.core.server.command.DeleteMimeTypeDescriptionCommand;
import com.echothree.control.user.core.server.command.DeletePartyApplicationEditorUseCommand;
import com.echothree.control.user.core.server.command.DeletePartyEntityTypeCommand;
import com.echothree.control.user.core.server.command.DeleteProtocolCommand;
import com.echothree.control.user.core.server.command.DeleteProtocolDescriptionCommand;
import com.echothree.control.user.core.server.command.DeleteServerCommand;
import com.echothree.control.user.core.server.command.DeleteServerDescriptionCommand;
import com.echothree.control.user.core.server.command.DeleteServerServiceCommand;
import com.echothree.control.user.core.server.command.DeleteServiceCommand;
import com.echothree.control.user.core.server.command.DeleteServiceDescriptionCommand;
import com.echothree.control.user.core.server.command.DeleteTextDecorationCommand;
import com.echothree.control.user.core.server.command.DeleteTextDecorationDescriptionCommand;
import com.echothree.control.user.core.server.command.DeleteTextTransformationCommand;
import com.echothree.control.user.core.server.command.DeleteTextTransformationDescriptionCommand;
import com.echothree.control.user.core.server.command.EditAppearanceCommand;
import com.echothree.control.user.core.server.command.EditAppearanceDescriptionCommand;
import com.echothree.control.user.core.server.command.EditApplicationCommand;
import com.echothree.control.user.core.server.command.EditApplicationDescriptionCommand;
import com.echothree.control.user.core.server.command.EditApplicationEditorCommand;
import com.echothree.control.user.core.server.command.EditApplicationEditorUseCommand;
import com.echothree.control.user.core.server.command.EditApplicationEditorUseDescriptionCommand;
import com.echothree.control.user.core.server.command.EditColorCommand;
import com.echothree.control.user.core.server.command.EditColorDescriptionCommand;
import com.echothree.control.user.core.server.command.EditCommandCommand;
import com.echothree.control.user.core.server.command.EditCommandDescriptionCommand;
import com.echothree.control.user.core.server.command.EditCommandMessageCommand;
import com.echothree.control.user.core.server.command.EditCommandMessageTranslationCommand;
import com.echothree.control.user.core.server.command.EditCommandMessageTypeCommand;
import com.echothree.control.user.core.server.command.EditCommandMessageTypeDescriptionCommand;
import com.echothree.control.user.core.server.command.EditComponentVendorCommand;
import com.echothree.control.user.core.server.command.EditEditorCommand;
import com.echothree.control.user.core.server.command.EditEditorDescriptionCommand;
import com.echothree.control.user.core.server.command.EditEntityAppearanceCommand;
import com.echothree.control.user.core.server.command.EditEntityAttributeCommand;
import com.echothree.control.user.core.server.command.EditEntityAttributeDescriptionCommand;
import com.echothree.control.user.core.server.command.EditEntityAttributeEntityAttributeGroupCommand;
import com.echothree.control.user.core.server.command.EditEntityAttributeGroupCommand;
import com.echothree.control.user.core.server.command.EditEntityAttributeGroupDescriptionCommand;
import com.echothree.control.user.core.server.command.EditEntityBlobAttributeCommand;
import com.echothree.control.user.core.server.command.EditEntityBooleanAttributeCommand;
import com.echothree.control.user.core.server.command.EditEntityClobAttributeCommand;
import com.echothree.control.user.core.server.command.EditEntityDateAttributeCommand;
import com.echothree.control.user.core.server.command.EditEntityEntityAttributeCommand;
import com.echothree.control.user.core.server.command.EditEntityGeoPointAttributeCommand;
import com.echothree.control.user.core.server.command.EditEntityIntegerAttributeCommand;
import com.echothree.control.user.core.server.command.EditEntityIntegerRangeCommand;
import com.echothree.control.user.core.server.command.EditEntityIntegerRangeDescriptionCommand;
import com.echothree.control.user.core.server.command.EditEntityListItemAttributeCommand;
import com.echothree.control.user.core.server.command.EditEntityListItemCommand;
import com.echothree.control.user.core.server.command.EditEntityListItemDescriptionCommand;
import com.echothree.control.user.core.server.command.EditEntityLongAttributeCommand;
import com.echothree.control.user.core.server.command.EditEntityLongRangeCommand;
import com.echothree.control.user.core.server.command.EditEntityLongRangeDescriptionCommand;
import com.echothree.control.user.core.server.command.EditEntityNameAttributeCommand;
import com.echothree.control.user.core.server.command.EditEntityStringAttributeCommand;
import com.echothree.control.user.core.server.command.EditEntityTimeAttributeCommand;
import com.echothree.control.user.core.server.command.EditEntityTypeCommand;
import com.echothree.control.user.core.server.command.EditEntityTypeDescriptionCommand;
import com.echothree.control.user.core.server.command.EditFontStyleCommand;
import com.echothree.control.user.core.server.command.EditFontStyleDescriptionCommand;
import com.echothree.control.user.core.server.command.EditFontWeightCommand;
import com.echothree.control.user.core.server.command.EditFontWeightDescriptionCommand;
import com.echothree.control.user.core.server.command.EditMimeTypeCommand;
import com.echothree.control.user.core.server.command.EditMimeTypeDescriptionCommand;
import com.echothree.control.user.core.server.command.EditPartyApplicationEditorUseCommand;
import com.echothree.control.user.core.server.command.EditPartyEntityTypeCommand;
import com.echothree.control.user.core.server.command.EditProtocolCommand;
import com.echothree.control.user.core.server.command.EditProtocolDescriptionCommand;
import com.echothree.control.user.core.server.command.EditServerCommand;
import com.echothree.control.user.core.server.command.EditServerDescriptionCommand;
import com.echothree.control.user.core.server.command.EditServiceCommand;
import com.echothree.control.user.core.server.command.EditServiceDescriptionCommand;
import com.echothree.control.user.core.server.command.EditTextDecorationCommand;
import com.echothree.control.user.core.server.command.EditTextDecorationDescriptionCommand;
import com.echothree.control.user.core.server.command.EditTextTransformationCommand;
import com.echothree.control.user.core.server.command.EditTextTransformationDescriptionCommand;
import com.echothree.control.user.core.server.command.EncryptCommand;
import com.echothree.control.user.core.server.command.GenerateBaseKeysCommand;
import com.echothree.control.user.core.server.command.GenerateGuidCommand;
import com.echothree.control.user.core.server.command.GenerateKeyCommand;
import com.echothree.control.user.core.server.command.GenerateUlidCommand;
import com.echothree.control.user.core.server.command.GetAppearanceChoicesCommand;
import com.echothree.control.user.core.server.command.GetAppearanceCommand;
import com.echothree.control.user.core.server.command.GetAppearanceDescriptionCommand;
import com.echothree.control.user.core.server.command.GetAppearanceDescriptionsCommand;
import com.echothree.control.user.core.server.command.GetAppearanceTextDecorationCommand;
import com.echothree.control.user.core.server.command.GetAppearanceTextDecorationsCommand;
import com.echothree.control.user.core.server.command.GetAppearanceTextTransformationCommand;
import com.echothree.control.user.core.server.command.GetAppearanceTextTransformationsCommand;
import com.echothree.control.user.core.server.command.GetAppearancesCommand;
import com.echothree.control.user.core.server.command.GetApplicationChoicesCommand;
import com.echothree.control.user.core.server.command.GetApplicationCommand;
import com.echothree.control.user.core.server.command.GetApplicationDescriptionCommand;
import com.echothree.control.user.core.server.command.GetApplicationDescriptionsCommand;
import com.echothree.control.user.core.server.command.GetApplicationEditorChoicesCommand;
import com.echothree.control.user.core.server.command.GetApplicationEditorCommand;
import com.echothree.control.user.core.server.command.GetApplicationEditorUseChoicesCommand;
import com.echothree.control.user.core.server.command.GetApplicationEditorUseCommand;
import com.echothree.control.user.core.server.command.GetApplicationEditorUseDescriptionCommand;
import com.echothree.control.user.core.server.command.GetApplicationEditorUseDescriptionsCommand;
import com.echothree.control.user.core.server.command.GetApplicationEditorUsesCommand;
import com.echothree.control.user.core.server.command.GetApplicationEditorsCommand;
import com.echothree.control.user.core.server.command.GetApplicationsCommand;
import com.echothree.control.user.core.server.command.GetBaseEncryptionKeyCommand;
import com.echothree.control.user.core.server.command.GetBaseEncryptionKeyStatusChoicesCommand;
import com.echothree.control.user.core.server.command.GetBaseEncryptionKeysCommand;
import com.echothree.control.user.core.server.command.GetCacheEntriesCommand;
import com.echothree.control.user.core.server.command.GetCacheEntryCommand;
import com.echothree.control.user.core.server.command.GetCacheEntryDependenciesCommand;
import com.echothree.control.user.core.server.command.GetColorChoicesCommand;
import com.echothree.control.user.core.server.command.GetColorCommand;
import com.echothree.control.user.core.server.command.GetColorDescriptionCommand;
import com.echothree.control.user.core.server.command.GetColorDescriptionsCommand;
import com.echothree.control.user.core.server.command.GetColorsCommand;
import com.echothree.control.user.core.server.command.GetCommandCommand;
import com.echothree.control.user.core.server.command.GetCommandDescriptionCommand;
import com.echothree.control.user.core.server.command.GetCommandDescriptionsCommand;
import com.echothree.control.user.core.server.command.GetCommandMessageCommand;
import com.echothree.control.user.core.server.command.GetCommandMessageTranslationCommand;
import com.echothree.control.user.core.server.command.GetCommandMessageTranslationsCommand;
import com.echothree.control.user.core.server.command.GetCommandMessageTypeChoicesCommand;
import com.echothree.control.user.core.server.command.GetCommandMessageTypeCommand;
import com.echothree.control.user.core.server.command.GetCommandMessageTypeDescriptionCommand;
import com.echothree.control.user.core.server.command.GetCommandMessageTypeDescriptionsCommand;
import com.echothree.control.user.core.server.command.GetCommandMessageTypesCommand;
import com.echothree.control.user.core.server.command.GetCommandMessagesCommand;
import com.echothree.control.user.core.server.command.GetCommandsCommand;
import com.echothree.control.user.core.server.command.GetComponentVendorCommand;
import com.echothree.control.user.core.server.command.GetComponentVendorsCommand;
import com.echothree.control.user.core.server.command.GetEditorChoicesCommand;
import com.echothree.control.user.core.server.command.GetEditorCommand;
import com.echothree.control.user.core.server.command.GetEditorDescriptionCommand;
import com.echothree.control.user.core.server.command.GetEditorDescriptionsCommand;
import com.echothree.control.user.core.server.command.GetEditorsCommand;
import com.echothree.control.user.core.server.command.GetEntityAppearanceCommand;
import com.echothree.control.user.core.server.command.GetEntityAttributeCommand;
import com.echothree.control.user.core.server.command.GetEntityAttributeDescriptionCommand;
import com.echothree.control.user.core.server.command.GetEntityAttributeDescriptionsCommand;
import com.echothree.control.user.core.server.command.GetEntityAttributeEntityAttributeGroupCommand;
import com.echothree.control.user.core.server.command.GetEntityAttributeEntityAttributeGroupsCommand;
import com.echothree.control.user.core.server.command.GetEntityAttributeEntityTypeCommand;
import com.echothree.control.user.core.server.command.GetEntityAttributeEntityTypesCommand;
import com.echothree.control.user.core.server.command.GetEntityAttributeGroupChoicesCommand;
import com.echothree.control.user.core.server.command.GetEntityAttributeGroupCommand;
import com.echothree.control.user.core.server.command.GetEntityAttributeGroupDescriptionCommand;
import com.echothree.control.user.core.server.command.GetEntityAttributeGroupDescriptionsCommand;
import com.echothree.control.user.core.server.command.GetEntityAttributeGroupsCommand;
import com.echothree.control.user.core.server.command.GetEntityAttributeTypeChoicesCommand;
import com.echothree.control.user.core.server.command.GetEntityAttributeTypeCommand;
import com.echothree.control.user.core.server.command.GetEntityAttributeTypesCommand;
import com.echothree.control.user.core.server.command.GetEntityAttributesCommand;
import com.echothree.control.user.core.server.command.GetEntityBlobAttributeCommand;
import com.echothree.control.user.core.server.command.GetEntityClobAttributeCommand;
import com.echothree.control.user.core.server.command.GetEntityInstanceCommand;
import com.echothree.control.user.core.server.command.GetEntityInstancesCommand;
import com.echothree.control.user.core.server.command.GetEntityIntegerRangeChoicesCommand;
import com.echothree.control.user.core.server.command.GetEntityIntegerRangeCommand;
import com.echothree.control.user.core.server.command.GetEntityIntegerRangeDescriptionCommand;
import com.echothree.control.user.core.server.command.GetEntityIntegerRangeDescriptionsCommand;
import com.echothree.control.user.core.server.command.GetEntityIntegerRangesCommand;
import com.echothree.control.user.core.server.command.GetEntityListItemChoicesCommand;
import com.echothree.control.user.core.server.command.GetEntityListItemCommand;
import com.echothree.control.user.core.server.command.GetEntityListItemDescriptionCommand;
import com.echothree.control.user.core.server.command.GetEntityListItemDescriptionsCommand;
import com.echothree.control.user.core.server.command.GetEntityListItemsCommand;
import com.echothree.control.user.core.server.command.GetEntityLongRangeChoicesCommand;
import com.echothree.control.user.core.server.command.GetEntityLongRangeCommand;
import com.echothree.control.user.core.server.command.GetEntityLongRangeDescriptionCommand;
import com.echothree.control.user.core.server.command.GetEntityLongRangeDescriptionsCommand;
import com.echothree.control.user.core.server.command.GetEntityLongRangesCommand;
import com.echothree.control.user.core.server.command.GetEntityTypeCommand;
import com.echothree.control.user.core.server.command.GetEntityTypeDescriptionCommand;
import com.echothree.control.user.core.server.command.GetEntityTypeDescriptionsCommand;
import com.echothree.control.user.core.server.command.GetEntityTypesCommand;
import com.echothree.control.user.core.server.command.GetEventGroupCommand;
import com.echothree.control.user.core.server.command.GetEventGroupStatusChoicesCommand;
import com.echothree.control.user.core.server.command.GetEventGroupsCommand;
import com.echothree.control.user.core.server.command.GetEventsCommand;
import com.echothree.control.user.core.server.command.GetFontStyleChoicesCommand;
import com.echothree.control.user.core.server.command.GetFontStyleCommand;
import com.echothree.control.user.core.server.command.GetFontStyleDescriptionCommand;
import com.echothree.control.user.core.server.command.GetFontStyleDescriptionsCommand;
import com.echothree.control.user.core.server.command.GetFontStylesCommand;
import com.echothree.control.user.core.server.command.GetFontWeightChoicesCommand;
import com.echothree.control.user.core.server.command.GetFontWeightCommand;
import com.echothree.control.user.core.server.command.GetFontWeightDescriptionCommand;
import com.echothree.control.user.core.server.command.GetFontWeightDescriptionsCommand;
import com.echothree.control.user.core.server.command.GetFontWeightsCommand;
import com.echothree.control.user.core.server.command.GetMimeTypeChoicesCommand;
import com.echothree.control.user.core.server.command.GetMimeTypeCommand;
import com.echothree.control.user.core.server.command.GetMimeTypeDescriptionCommand;
import com.echothree.control.user.core.server.command.GetMimeTypeDescriptionsCommand;
import com.echothree.control.user.core.server.command.GetMimeTypeFileExtensionCommand;
import com.echothree.control.user.core.server.command.GetMimeTypeFileExtensionsCommand;
import com.echothree.control.user.core.server.command.GetMimeTypeUsageTypeChoicesCommand;
import com.echothree.control.user.core.server.command.GetMimeTypeUsageTypeCommand;
import com.echothree.control.user.core.server.command.GetMimeTypeUsageTypesCommand;
import com.echothree.control.user.core.server.command.GetMimeTypeUsagesCommand;
import com.echothree.control.user.core.server.command.GetMimeTypesCommand;
import com.echothree.control.user.core.server.command.GetPartyApplicationEditorUseCommand;
import com.echothree.control.user.core.server.command.GetPartyApplicationEditorUsesCommand;
import com.echothree.control.user.core.server.command.GetPartyEntityTypeCommand;
import com.echothree.control.user.core.server.command.GetPartyEntityTypesCommand;
import com.echothree.control.user.core.server.command.GetProtocolChoicesCommand;
import com.echothree.control.user.core.server.command.GetProtocolCommand;
import com.echothree.control.user.core.server.command.GetProtocolDescriptionCommand;
import com.echothree.control.user.core.server.command.GetProtocolDescriptionsCommand;
import com.echothree.control.user.core.server.command.GetProtocolsCommand;
import com.echothree.control.user.core.server.command.GetServerChoicesCommand;
import com.echothree.control.user.core.server.command.GetServerCommand;
import com.echothree.control.user.core.server.command.GetServerDescriptionCommand;
import com.echothree.control.user.core.server.command.GetServerDescriptionsCommand;
import com.echothree.control.user.core.server.command.GetServerServiceCommand;
import com.echothree.control.user.core.server.command.GetServerServicesCommand;
import com.echothree.control.user.core.server.command.GetServersCommand;
import com.echothree.control.user.core.server.command.GetServiceChoicesCommand;
import com.echothree.control.user.core.server.command.GetServiceCommand;
import com.echothree.control.user.core.server.command.GetServiceDescriptionCommand;
import com.echothree.control.user.core.server.command.GetServiceDescriptionsCommand;
import com.echothree.control.user.core.server.command.GetServicesCommand;
import com.echothree.control.user.core.server.command.GetTextDecorationChoicesCommand;
import com.echothree.control.user.core.server.command.GetTextDecorationCommand;
import com.echothree.control.user.core.server.command.GetTextDecorationDescriptionCommand;
import com.echothree.control.user.core.server.command.GetTextDecorationDescriptionsCommand;
import com.echothree.control.user.core.server.command.GetTextDecorationsCommand;
import com.echothree.control.user.core.server.command.GetTextTransformationChoicesCommand;
import com.echothree.control.user.core.server.command.GetTextTransformationCommand;
import com.echothree.control.user.core.server.command.GetTextTransformationDescriptionCommand;
import com.echothree.control.user.core.server.command.GetTextTransformationDescriptionsCommand;
import com.echothree.control.user.core.server.command.GetTextTransformationsCommand;
import com.echothree.control.user.core.server.command.LoadBaseKeysCommand;
import com.echothree.control.user.core.server.command.LockEntityCommand;
import com.echothree.control.user.core.server.command.ProcessQueuedEventsCommand;
import com.echothree.control.user.core.server.command.RemoveCacheEntryCommand;
import com.echothree.control.user.core.server.command.RemovedExpiredEntityLocksCommand;
import com.echothree.control.user.core.server.command.SetBaseEncryptionKeyStatusCommand;
import com.echothree.control.user.core.server.command.SetDefaultAppearanceCommand;
import com.echothree.control.user.core.server.command.SetDefaultApplicationCommand;
import com.echothree.control.user.core.server.command.SetDefaultApplicationEditorCommand;
import com.echothree.control.user.core.server.command.SetDefaultApplicationEditorUseCommand;
import com.echothree.control.user.core.server.command.SetDefaultColorCommand;
import com.echothree.control.user.core.server.command.SetDefaultCommandMessageTypeCommand;
import com.echothree.control.user.core.server.command.SetDefaultEditorCommand;
import com.echothree.control.user.core.server.command.SetDefaultEntityAttributeGroupCommand;
import com.echothree.control.user.core.server.command.SetDefaultEntityIntegerRangeCommand;
import com.echothree.control.user.core.server.command.SetDefaultEntityListItemCommand;
import com.echothree.control.user.core.server.command.SetDefaultEntityLongRangeCommand;
import com.echothree.control.user.core.server.command.SetDefaultFontStyleCommand;
import com.echothree.control.user.core.server.command.SetDefaultFontWeightCommand;
import com.echothree.control.user.core.server.command.SetDefaultMimeTypeCommand;
import com.echothree.control.user.core.server.command.SetDefaultProtocolCommand;
import com.echothree.control.user.core.server.command.SetDefaultServerCommand;
import com.echothree.control.user.core.server.command.SetDefaultServiceCommand;
import com.echothree.control.user.core.server.command.SetDefaultTextDecorationCommand;
import com.echothree.control.user.core.server.command.SetDefaultTextTransformationCommand;
import com.echothree.control.user.core.server.command.SetEventGroupStatusCommand;
import com.echothree.control.user.core.server.command.UnlockEntityCommand;
import com.echothree.control.user.core.server.command.ValidateCommand;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;
import javax.ejb.Stateless;

@Stateless
public class CoreBean
        extends CoreFormsImpl
        implements CoreRemote, CoreLocal {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    @Override
    public String ping() {
        return "CoreBean is alive!";
    }
    
    // -------------------------------------------------------------------------
    //   Base Keys
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult generateBaseKeys(UserVisitPK userVisitPK) {
        return new GenerateBaseKeysCommand(userVisitPK).run();
    }
    
    @Override
    public CommandResult loadBaseKeys(UserVisitPK userVisitPK, LoadBaseKeysForm form) {
        return new LoadBaseKeysCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult changeBaseKeys(UserVisitPK userVisitPK, ChangeBaseKeysForm form) {
        return new ChangeBaseKeysCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Base Encryption Keys
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult getBaseEncryptionKey(UserVisitPK userVisitPK, GetBaseEncryptionKeyForm form) {
        return new GetBaseEncryptionKeyCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getBaseEncryptionKeys(UserVisitPK userVisitPK, GetBaseEncryptionKeysForm form) {
        return new GetBaseEncryptionKeysCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getBaseEncryptionKeyStatusChoices(UserVisitPK userVisitPK, GetBaseEncryptionKeyStatusChoicesForm form) {
        return new GetBaseEncryptionKeyStatusChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setBaseEncryptionKeyStatus(UserVisitPK userVisitPK, SetBaseEncryptionKeyStatusForm form) {
        return new SetBaseEncryptionKeyStatusCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Entity Locks
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult lockEntity(UserVisitPK userVisitPK, LockEntityForm form) {
        return new LockEntityCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult unlockEntity(UserVisitPK userVisitPK, UnlockEntityForm form) {
        return new UnlockEntityCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult removedExpiredEntityLocks(UserVisitPK userVisitPK) {
        return new RemovedExpiredEntityLocksCommand(userVisitPK).run();
    }
    
    // -------------------------------------------------------------------------
    //   Component Vendors
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createComponentVendor(UserVisitPK userVisitPK, CreateComponentVendorForm form) {
        return new CreateComponentVendorCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getComponentVendor(UserVisitPK userVisitPK, GetComponentVendorForm form) {
        return new GetComponentVendorCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getComponentVendors(UserVisitPK userVisitPK, GetComponentVendorsForm form) {
        return new GetComponentVendorsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editComponentVendor(UserVisitPK userVisitPK, EditComponentVendorForm form) {
        return new EditComponentVendorCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteComponentVendor(UserVisitPK userVisitPK, DeleteComponentVendorForm form) {
        return new DeleteComponentVendorCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Entity Types
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createEntityType(UserVisitPK userVisitPK, CreateEntityTypeForm form) {
        return new CreateEntityTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getEntityType(UserVisitPK userVisitPK, GetEntityTypeForm form) {
        return new GetEntityTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getEntityTypes(UserVisitPK userVisitPK, GetEntityTypesForm form) {
        return new GetEntityTypesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editEntityType(UserVisitPK userVisitPK, EditEntityTypeForm form) {
        return new EditEntityTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteEntityType(UserVisitPK userVisitPK, DeleteEntityTypeForm form) {
        return new DeleteEntityTypeCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Commands
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createCommand(UserVisitPK userVisitPK, CreateCommandForm form) {
        return new CreateCommandCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getCommand(UserVisitPK userVisitPK, GetCommandForm form) {
        return new GetCommandCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getCommands(UserVisitPK userVisitPK, GetCommandsForm form) {
        return new GetCommandsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editCommand(UserVisitPK userVisitPK, EditCommandForm form) {
        return new EditCommandCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteCommand(UserVisitPK userVisitPK, DeleteCommandForm form) {
        return new DeleteCommandCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Command Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createCommandDescription(UserVisitPK userVisitPK, CreateCommandDescriptionForm form) {
        return new CreateCommandDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getCommandDescription(UserVisitPK userVisitPK, GetCommandDescriptionForm form) {
        return new GetCommandDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getCommandDescriptions(UserVisitPK userVisitPK, GetCommandDescriptionsForm form) {
        return new GetCommandDescriptionsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editCommandDescription(UserVisitPK userVisitPK, EditCommandDescriptionForm form) {
        return new EditCommandDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteCommandDescription(UserVisitPK userVisitPK, DeleteCommandDescriptionForm form) {
        return new DeleteCommandDescriptionCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Command Message Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createCommandMessageType(UserVisitPK userVisitPK, CreateCommandMessageTypeForm form) {
        return new CreateCommandMessageTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getCommandMessageTypeChoices(UserVisitPK userVisitPK, GetCommandMessageTypeChoicesForm form) {
        return new GetCommandMessageTypeChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getCommandMessageType(UserVisitPK userVisitPK, GetCommandMessageTypeForm form) {
        return new GetCommandMessageTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getCommandMessageTypes(UserVisitPK userVisitPK, GetCommandMessageTypesForm form) {
        return new GetCommandMessageTypesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setDefaultCommandMessageType(UserVisitPK userVisitPK, SetDefaultCommandMessageTypeForm form) {
        return new SetDefaultCommandMessageTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editCommandMessageType(UserVisitPK userVisitPK, EditCommandMessageTypeForm form) {
        return new EditCommandMessageTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteCommandMessageType(UserVisitPK userVisitPK, DeleteCommandMessageTypeForm form) {
        return new DeleteCommandMessageTypeCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Command Message Type Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createCommandMessageTypeDescription(UserVisitPK userVisitPK, CreateCommandMessageTypeDescriptionForm form) {
        return new CreateCommandMessageTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getCommandMessageTypeDescription(UserVisitPK userVisitPK, GetCommandMessageTypeDescriptionForm form) {
        return new GetCommandMessageTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getCommandMessageTypeDescriptions(UserVisitPK userVisitPK, GetCommandMessageTypeDescriptionsForm form) {
        return new GetCommandMessageTypeDescriptionsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editCommandMessageTypeDescription(UserVisitPK userVisitPK, EditCommandMessageTypeDescriptionForm form) {
        return new EditCommandMessageTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteCommandMessageTypeDescription(UserVisitPK userVisitPK, DeleteCommandMessageTypeDescriptionForm form) {
        return new DeleteCommandMessageTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Command Messages
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createCommandMessage(UserVisitPK userVisitPK, CreateCommandMessageForm form) {
        return new CreateCommandMessageCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getCommandMessage(UserVisitPK userVisitPK, GetCommandMessageForm form) {
        return new GetCommandMessageCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getCommandMessages(UserVisitPK userVisitPK, GetCommandMessagesForm form) {
        return new GetCommandMessagesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editCommandMessage(UserVisitPK userVisitPK, EditCommandMessageForm form) {
        return new EditCommandMessageCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteCommandMessage(UserVisitPK userVisitPK, DeleteCommandMessageForm form) {
        return new DeleteCommandMessageCommand(userVisitPK, form).run();
    }

    // -------------------------------------------------------------------------
    //   Command Message Translations
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createCommandMessageTranslation(UserVisitPK userVisitPK, CreateCommandMessageTranslationForm form) {
        return new CreateCommandMessageTranslationCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getCommandMessageTranslation(UserVisitPK userVisitPK, GetCommandMessageTranslationForm form) {
        return new GetCommandMessageTranslationCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getCommandMessageTranslations(UserVisitPK userVisitPK, GetCommandMessageTranslationsForm form) {
        return new GetCommandMessageTranslationsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editCommandMessageTranslation(UserVisitPK userVisitPK, EditCommandMessageTranslationForm form) {
        return new EditCommandMessageTranslationCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteCommandMessageTranslation(UserVisitPK userVisitPK, DeleteCommandMessageTranslationForm form) {
        return new DeleteCommandMessageTranslationCommand(userVisitPK, form).run();
    }

    // -------------------------------------------------------------------------
    //   Entity Instances
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createEntityInstance(UserVisitPK userVisitPK, CreateEntityInstanceForm form) {
        return new CreateEntityInstanceCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getEntityInstance(UserVisitPK userVisitPK, GetEntityInstanceForm form) {
        return new GetEntityInstanceCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getEntityInstances(UserVisitPK userVisitPK, GetEntityInstancesForm form) {
        return new GetEntityInstancesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult generateKey(UserVisitPK userVisitPK, GenerateKeyForm form) {
        return new GenerateKeyCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult generateGuid(UserVisitPK userVisitPK, GenerateGuidForm form) {
        return new GenerateGuidCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult generateUlid(UserVisitPK userVisitPK, GenerateUlidForm form) {
        return new GenerateUlidCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Event Types
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createEventType(UserVisitPK userVisitPK, CreateEventTypeForm form) {
        return new CreateEventTypeCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Event Type Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createEventTypeDescription(UserVisitPK userVisitPK, CreateEventTypeDescriptionForm form) {
        return new CreateEventTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Event Groups
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult getEventGroup(UserVisitPK userVisitPK, GetEventGroupForm form) {
        return new GetEventGroupCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getEventGroups(UserVisitPK userVisitPK, GetEventGroupsForm form) {
        return new GetEventGroupsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getEventGroupStatusChoices(UserVisitPK userVisitPK, GetEventGroupStatusChoicesForm form) {
        return new GetEventGroupStatusChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setEventGroupStatus(UserVisitPK userVisitPK, SetEventGroupStatusForm form) {
        return new SetEventGroupStatusCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Events
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult getEvents(UserVisitPK userVisitPK, GetEventsForm form) {
        return new GetEventsCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Queued Events
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult processQueuedEvents(UserVisitPK userVisitPK) {
        return new ProcessQueuedEventsCommand(userVisitPK).run();
    }
    
    // -------------------------------------------------------------------------
    //   Components
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createComponent(UserVisitPK userVisitPK, CreateComponentForm form) {
        return new CreateComponentCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Component Stages
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createComponentStage(UserVisitPK userVisitPK, CreateComponentStageForm form) {
        return new CreateComponentStageCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Component Versions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createComponentVersion(UserVisitPK userVisitPK, CreateComponentVersionForm form) {
        return new CreateComponentVersionCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Entity Attribute Group Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createEntityAttributeGroupDescription(UserVisitPK userVisitPK, CreateEntityAttributeGroupDescriptionForm form) {
        return new CreateEntityAttributeGroupDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getEntityAttributeGroupDescription(UserVisitPK userVisitPK, GetEntityAttributeGroupDescriptionForm form) {
        return new GetEntityAttributeGroupDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getEntityAttributeGroupDescriptions(UserVisitPK userVisitPK, GetEntityAttributeGroupDescriptionsForm form) {
        return new GetEntityAttributeGroupDescriptionsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editEntityAttributeGroupDescription(UserVisitPK userVisitPK, EditEntityAttributeGroupDescriptionForm form) {
        return new EditEntityAttributeGroupDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteEntityAttributeGroupDescription(UserVisitPK userVisitPK, DeleteEntityAttributeGroupDescriptionForm form) {
        return new DeleteEntityAttributeGroupDescriptionCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Entity Attribute Groups
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createEntityAttributeGroup(UserVisitPK userVisitPK, CreateEntityAttributeGroupForm form) {
        return new CreateEntityAttributeGroupCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getEntityAttributeGroup(UserVisitPK userVisitPK, GetEntityAttributeGroupForm form) {
        return new GetEntityAttributeGroupCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getEntityAttributeGroups(UserVisitPK userVisitPK, GetEntityAttributeGroupsForm form) {
        return new GetEntityAttributeGroupsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getEntityAttributeGroupChoices(UserVisitPK userVisitPK, GetEntityAttributeGroupChoicesForm form) {
        return new GetEntityAttributeGroupChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setDefaultEntityAttributeGroup(UserVisitPK userVisitPK, SetDefaultEntityAttributeGroupForm form) {
        return new SetDefaultEntityAttributeGroupCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editEntityAttributeGroup(UserVisitPK userVisitPK, EditEntityAttributeGroupForm form) {
        return new EditEntityAttributeGroupCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteEntityAttributeGroup(UserVisitPK userVisitPK, DeleteEntityAttributeGroupForm form) {
        return new DeleteEntityAttributeGroupCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Entity Attribute Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createEntityAttributeDescription(UserVisitPK userVisitPK, CreateEntityAttributeDescriptionForm form) {
        return new CreateEntityAttributeDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getEntityAttributeDescription(UserVisitPK userVisitPK, GetEntityAttributeDescriptionForm form) {
        return new GetEntityAttributeDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getEntityAttributeDescriptions(UserVisitPK userVisitPK, GetEntityAttributeDescriptionsForm form) {
        return new GetEntityAttributeDescriptionsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editEntityAttributeDescription(UserVisitPK userVisitPK, EditEntityAttributeDescriptionForm form) {
        return new EditEntityAttributeDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteEntityAttributeDescription(UserVisitPK userVisitPK, DeleteEntityAttributeDescriptionForm form) {
        return new DeleteEntityAttributeDescriptionCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Entity Attributes
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createEntityAttribute(UserVisitPK userVisitPK, CreateEntityAttributeForm form) {
        return new CreateEntityAttributeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getEntityAttribute(UserVisitPK userVisitPK, GetEntityAttributeForm form) {
        return new GetEntityAttributeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getEntityAttributes(UserVisitPK userVisitPK, GetEntityAttributesForm form) {
        return new GetEntityAttributesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editEntityAttribute(UserVisitPK userVisitPK, EditEntityAttributeForm form) {
        return new EditEntityAttributeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteEntityAttribute(UserVisitPK userVisitPK, DeleteEntityAttributeForm form) {
        return new DeleteEntityAttributeCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Entity Attribute Entity Attribute Groups
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createEntityAttributeEntityAttributeGroup(UserVisitPK userVisitPK, CreateEntityAttributeEntityAttributeGroupForm form) {
        return new CreateEntityAttributeEntityAttributeGroupCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getEntityAttributeEntityAttributeGroup(UserVisitPK userVisitPK, GetEntityAttributeEntityAttributeGroupForm form) {
        return new GetEntityAttributeEntityAttributeGroupCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getEntityAttributeEntityAttributeGroups(UserVisitPK userVisitPK, GetEntityAttributeEntityAttributeGroupsForm form) {
        return new GetEntityAttributeEntityAttributeGroupsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editEntityAttributeEntityAttributeGroup(UserVisitPK userVisitPK, EditEntityAttributeEntityAttributeGroupForm form) {
        return new EditEntityAttributeEntityAttributeGroupCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteEntityAttributeEntityAttributeGroup(UserVisitPK userVisitPK, DeleteEntityAttributeEntityAttributeGroupForm form) {
        return new DeleteEntityAttributeEntityAttributeGroupCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Entity Attribute Type Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createEntityAttributeTypeDescription(UserVisitPK userVisitPK, CreateEntityAttributeTypeDescriptionForm form) {
        return new CreateEntityAttributeTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Entity Attribute Types
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createEntityAttributeType(UserVisitPK userVisitPK, CreateEntityAttributeTypeForm form) {
        return new CreateEntityAttributeTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getEntityAttributeType(UserVisitPK userVisitPK, GetEntityAttributeTypeForm form) {
        return new GetEntityAttributeTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getEntityAttributeTypes(UserVisitPK userVisitPK, GetEntityAttributeTypesForm form) {
        return new GetEntityAttributeTypesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getEntityAttributeTypeChoices(UserVisitPK userVisitPK, GetEntityAttributeTypeChoicesForm form) {
        return new GetEntityAttributeTypeChoicesCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Entity List Item Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createEntityListItemDescription(UserVisitPK userVisitPK, CreateEntityListItemDescriptionForm form) {
        return new CreateEntityListItemDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getEntityListItemDescription(UserVisitPK userVisitPK, GetEntityListItemDescriptionForm form) {
        return new GetEntityListItemDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getEntityListItemDescriptions(UserVisitPK userVisitPK, GetEntityListItemDescriptionsForm form) {
        return new GetEntityListItemDescriptionsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editEntityListItemDescription(UserVisitPK userVisitPK, EditEntityListItemDescriptionForm form) {
        return new EditEntityListItemDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteEntityListItemDescription(UserVisitPK userVisitPK, DeleteEntityListItemDescriptionForm form) {
        return new DeleteEntityListItemDescriptionCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Entity List Items
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createEntityListItem(UserVisitPK userVisitPK, CreateEntityListItemForm form) {
        return new CreateEntityListItemCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getEntityListItem(UserVisitPK userVisitPK, GetEntityListItemForm form) {
        return new GetEntityListItemCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getEntityListItems(UserVisitPK userVisitPK, GetEntityListItemsForm form) {
        return new GetEntityListItemsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getEntityListItemChoices(UserVisitPK userVisitPK, GetEntityListItemChoicesForm form) {
        return new GetEntityListItemChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setDefaultEntityListItem(UserVisitPK userVisitPK, SetDefaultEntityListItemForm form) {
        return new SetDefaultEntityListItemCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editEntityListItem(UserVisitPK userVisitPK, EditEntityListItemForm form) {
        return new EditEntityListItemCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteEntityListItem(UserVisitPK userVisitPK, DeleteEntityListItemForm form) {
        return new DeleteEntityListItemCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Entity Integer Range Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createEntityIntegerRangeDescription(UserVisitPK userVisitPK, CreateEntityIntegerRangeDescriptionForm form) {
        return new CreateEntityIntegerRangeDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getEntityIntegerRangeDescription(UserVisitPK userVisitPK, GetEntityIntegerRangeDescriptionForm form) {
        return new GetEntityIntegerRangeDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getEntityIntegerRangeDescriptions(UserVisitPK userVisitPK, GetEntityIntegerRangeDescriptionsForm form) {
        return new GetEntityIntegerRangeDescriptionsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editEntityIntegerRangeDescription(UserVisitPK userVisitPK, EditEntityIntegerRangeDescriptionForm form) {
        return new EditEntityIntegerRangeDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteEntityIntegerRangeDescription(UserVisitPK userVisitPK, DeleteEntityIntegerRangeDescriptionForm form) {
        return new DeleteEntityIntegerRangeDescriptionCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Entity Integer Ranges
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createEntityIntegerRange(UserVisitPK userVisitPK, CreateEntityIntegerRangeForm form) {
        return new CreateEntityIntegerRangeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getEntityIntegerRange(UserVisitPK userVisitPK, GetEntityIntegerRangeForm form) {
        return new GetEntityIntegerRangeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getEntityIntegerRanges(UserVisitPK userVisitPK, GetEntityIntegerRangesForm form) {
        return new GetEntityIntegerRangesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getEntityIntegerRangeChoices(UserVisitPK userVisitPK, GetEntityIntegerRangeChoicesForm form) {
        return new GetEntityIntegerRangeChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setDefaultEntityIntegerRange(UserVisitPK userVisitPK, SetDefaultEntityIntegerRangeForm form) {
        return new SetDefaultEntityIntegerRangeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editEntityIntegerRange(UserVisitPK userVisitPK, EditEntityIntegerRangeForm form) {
        return new EditEntityIntegerRangeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteEntityIntegerRange(UserVisitPK userVisitPK, DeleteEntityIntegerRangeForm form) {
        return new DeleteEntityIntegerRangeCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Entity Long Range Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createEntityLongRangeDescription(UserVisitPK userVisitPK, CreateEntityLongRangeDescriptionForm form) {
        return new CreateEntityLongRangeDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getEntityLongRangeDescription(UserVisitPK userVisitPK, GetEntityLongRangeDescriptionForm form) {
        return new GetEntityLongRangeDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getEntityLongRangeDescriptions(UserVisitPK userVisitPK, GetEntityLongRangeDescriptionsForm form) {
        return new GetEntityLongRangeDescriptionsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editEntityLongRangeDescription(UserVisitPK userVisitPK, EditEntityLongRangeDescriptionForm form) {
        return new EditEntityLongRangeDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteEntityLongRangeDescription(UserVisitPK userVisitPK, DeleteEntityLongRangeDescriptionForm form) {
        return new DeleteEntityLongRangeDescriptionCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Entity Long Ranges
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createEntityLongRange(UserVisitPK userVisitPK, CreateEntityLongRangeForm form) {
        return new CreateEntityLongRangeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getEntityLongRange(UserVisitPK userVisitPK, GetEntityLongRangeForm form) {
        return new GetEntityLongRangeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getEntityLongRanges(UserVisitPK userVisitPK, GetEntityLongRangesForm form) {
        return new GetEntityLongRangesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getEntityLongRangeChoices(UserVisitPK userVisitPK, GetEntityLongRangeChoicesForm form) {
        return new GetEntityLongRangeChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setDefaultEntityLongRange(UserVisitPK userVisitPK, SetDefaultEntityLongRangeForm form) {
        return new SetDefaultEntityLongRangeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editEntityLongRange(UserVisitPK userVisitPK, EditEntityLongRangeForm form) {
        return new EditEntityLongRangeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteEntityLongRange(UserVisitPK userVisitPK, DeleteEntityLongRangeForm form) {
        return new DeleteEntityLongRangeCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Entity Type Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createEntityTypeDescription(UserVisitPK userVisitPK, CreateEntityTypeDescriptionForm form) {
        return new CreateEntityTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getEntityTypeDescription(UserVisitPK userVisitPK, GetEntityTypeDescriptionForm form) {
        return new GetEntityTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getEntityTypeDescriptions(UserVisitPK userVisitPK, GetEntityTypeDescriptionsForm form) {
        return new GetEntityTypeDescriptionsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editEntityTypeDescription(UserVisitPK userVisitPK, EditEntityTypeDescriptionForm form) {
        return new EditEntityTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteEntityTypeDescription(UserVisitPK userVisitPK, DeleteEntityTypeDescriptionForm form) {
        return new DeleteEntityTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Mime Type Usage Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createMimeTypeUsageType(UserVisitPK userVisitPK, CreateMimeTypeUsageTypeForm form) {
        return new CreateMimeTypeUsageTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getMimeTypeUsageType(UserVisitPK userVisitPK, GetMimeTypeUsageTypeForm form) {
        return new GetMimeTypeUsageTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getMimeTypeUsageTypes(UserVisitPK userVisitPK, GetMimeTypeUsageTypesForm form) {
        return new GetMimeTypeUsageTypesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getMimeTypeUsageTypeChoices(UserVisitPK userVisitPK, GetMimeTypeUsageTypeChoicesForm form) {
        return new GetMimeTypeUsageTypeChoicesCommand(userVisitPK, form).run();
    }

    // --------------------------------------------------------------------------------
    //   Mime Type Usage Type Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createMimeTypeUsageTypeDescription(UserVisitPK userVisitPK, CreateMimeTypeUsageTypeDescriptionForm form) {
        return new CreateMimeTypeUsageTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Mime Types
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createMimeType(UserVisitPK userVisitPK, CreateMimeTypeForm form) {
        return new CreateMimeTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getMimeTypes(UserVisitPK userVisitPK, GetMimeTypesForm form) {
        return new GetMimeTypesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getMimeType(UserVisitPK userVisitPK, GetMimeTypeForm form) {
        return new GetMimeTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getMimeTypeChoices(UserVisitPK userVisitPK, GetMimeTypeChoicesForm form) {
        return new GetMimeTypeChoicesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult setDefaultMimeType(UserVisitPK userVisitPK, SetDefaultMimeTypeForm form) {
        return new SetDefaultMimeTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editMimeType(UserVisitPK userVisitPK, EditMimeTypeForm form) {
        return new EditMimeTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteMimeType(UserVisitPK userVisitPK, DeleteMimeTypeForm form) {
        return new DeleteMimeTypeCommand(userVisitPK, form).run();
    }

    // -------------------------------------------------------------------------
    //   Mime Type Descriptions
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createMimeTypeDescription(UserVisitPK userVisitPK, CreateMimeTypeDescriptionForm form) {
        return new CreateMimeTypeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getMimeTypeDescriptions(UserVisitPK userVisitPK, GetMimeTypeDescriptionsForm form) {
        return new GetMimeTypeDescriptionsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getMimeTypeDescription(UserVisitPK userVisitPK, GetMimeTypeDescriptionForm form) {
        return new GetMimeTypeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editMimeTypeDescription(UserVisitPK userVisitPK, EditMimeTypeDescriptionForm form) {
        return new EditMimeTypeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteMimeTypeDescription(UserVisitPK userVisitPK, DeleteMimeTypeDescriptionForm form) {
        return new DeleteMimeTypeDescriptionCommand(userVisitPK, form).run();
    }

    // --------------------------------------------------------------------------------
    //   Mime Type Usages
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createMimeTypeUsage(UserVisitPK userVisitPK, CreateMimeTypeUsageForm form) {
        return new CreateMimeTypeUsageCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getMimeTypeUsages(UserVisitPK userVisitPK, GetMimeTypeUsagesForm form) {
        return new GetMimeTypeUsagesCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Mime Type File Extensions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createMimeTypeFileExtension(UserVisitPK userVisitPK, CreateMimeTypeFileExtensionForm form) {
        return new CreateMimeTypeFileExtensionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getMimeTypeFileExtension(UserVisitPK userVisitPK, GetMimeTypeFileExtensionForm form) {
        return new GetMimeTypeFileExtensionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getMimeTypeFileExtensions(UserVisitPK userVisitPK, GetMimeTypeFileExtensionsForm form) {
        return new GetMimeTypeFileExtensionsCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Protocols
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createProtocol(UserVisitPK userVisitPK, CreateProtocolForm form) {
        return new CreateProtocolCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getProtocolChoices(UserVisitPK userVisitPK, GetProtocolChoicesForm form) {
        return new GetProtocolChoicesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getProtocol(UserVisitPK userVisitPK, GetProtocolForm form) {
        return new GetProtocolCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getProtocols(UserVisitPK userVisitPK, GetProtocolsForm form) {
        return new GetProtocolsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult setDefaultProtocol(UserVisitPK userVisitPK, SetDefaultProtocolForm form) {
        return new SetDefaultProtocolCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editProtocol(UserVisitPK userVisitPK, EditProtocolForm form) {
        return new EditProtocolCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteProtocol(UserVisitPK userVisitPK, DeleteProtocolForm form) {
        return new DeleteProtocolCommand(userVisitPK, form).run();
    }

    // --------------------------------------------------------------------------------
    //   Protocol Descriptions
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createProtocolDescription(UserVisitPK userVisitPK, CreateProtocolDescriptionForm form) {
        return new CreateProtocolDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getProtocolDescription(UserVisitPK userVisitPK, GetProtocolDescriptionForm form) {
        return new GetProtocolDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getProtocolDescriptions(UserVisitPK userVisitPK, GetProtocolDescriptionsForm form) {
        return new GetProtocolDescriptionsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editProtocolDescription(UserVisitPK userVisitPK, EditProtocolDescriptionForm form) {
        return new EditProtocolDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteProtocolDescription(UserVisitPK userVisitPK, DeleteProtocolDescriptionForm form) {
        return new DeleteProtocolDescriptionCommand(userVisitPK, form).run();
    }

    // --------------------------------------------------------------------------------
    //   Services
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createService(UserVisitPK userVisitPK, CreateServiceForm form) {
        return new CreateServiceCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getServiceChoices(UserVisitPK userVisitPK, GetServiceChoicesForm form) {
        return new GetServiceChoicesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getService(UserVisitPK userVisitPK, GetServiceForm form) {
        return new GetServiceCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getServices(UserVisitPK userVisitPK, GetServicesForm form) {
        return new GetServicesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult setDefaultService(UserVisitPK userVisitPK, SetDefaultServiceForm form) {
        return new SetDefaultServiceCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editService(UserVisitPK userVisitPK, EditServiceForm form) {
        return new EditServiceCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteService(UserVisitPK userVisitPK, DeleteServiceForm form) {
        return new DeleteServiceCommand(userVisitPK, form).run();
    }

    // --------------------------------------------------------------------------------
    //   Service Descriptions
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createServiceDescription(UserVisitPK userVisitPK, CreateServiceDescriptionForm form) {
        return new CreateServiceDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getServiceDescription(UserVisitPK userVisitPK, GetServiceDescriptionForm form) {
        return new GetServiceDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getServiceDescriptions(UserVisitPK userVisitPK, GetServiceDescriptionsForm form) {
        return new GetServiceDescriptionsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editServiceDescription(UserVisitPK userVisitPK, EditServiceDescriptionForm form) {
        return new EditServiceDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteServiceDescription(UserVisitPK userVisitPK, DeleteServiceDescriptionForm form) {
        return new DeleteServiceDescriptionCommand(userVisitPK, form).run();
    }

    // --------------------------------------------------------------------------------
    //   Servers
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createServer(UserVisitPK userVisitPK, CreateServerForm form) {
        return new CreateServerCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getServerChoices(UserVisitPK userVisitPK, GetServerChoicesForm form) {
        return new GetServerChoicesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getServer(UserVisitPK userVisitPK, GetServerForm form) {
        return new GetServerCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getServers(UserVisitPK userVisitPK, GetServersForm form) {
        return new GetServersCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult setDefaultServer(UserVisitPK userVisitPK, SetDefaultServerForm form) {
        return new SetDefaultServerCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editServer(UserVisitPK userVisitPK, EditServerForm form) {
        return new EditServerCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteServer(UserVisitPK userVisitPK, DeleteServerForm form) {
        return new DeleteServerCommand(userVisitPK, form).run();
    }

    // --------------------------------------------------------------------------------
    //   Server Descriptions
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createServerDescription(UserVisitPK userVisitPK, CreateServerDescriptionForm form) {
        return new CreateServerDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getServerDescription(UserVisitPK userVisitPK, GetServerDescriptionForm form) {
        return new GetServerDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getServerDescriptions(UserVisitPK userVisitPK, GetServerDescriptionsForm form) {
        return new GetServerDescriptionsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editServerDescription(UserVisitPK userVisitPK, EditServerDescriptionForm form) {
        return new EditServerDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteServerDescription(UserVisitPK userVisitPK, DeleteServerDescriptionForm form) {
        return new DeleteServerDescriptionCommand(userVisitPK, form).run();
    }

    // --------------------------------------------------------------------------------
    //   Server Services
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createServerService(UserVisitPK userVisitPK, CreateServerServiceForm form) {
        return new CreateServerServiceCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getServerService(UserVisitPK userVisitPK, GetServerServiceForm form) {
        return new GetServerServiceCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getServerServices(UserVisitPK userVisitPK, GetServerServicesForm form) {
        return new GetServerServicesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteServerService(UserVisitPK userVisitPK, DeleteServerServiceForm form) {
        return new DeleteServerServiceCommand(userVisitPK, form).run();
    }

    // --------------------------------------------------------------------------------
    //   Entity Boolean Attributes
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createEntityBooleanAttribute(UserVisitPK userVisitPK, CreateEntityBooleanAttributeForm form) {
        return new CreateEntityBooleanAttributeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editEntityBooleanAttribute(UserVisitPK userVisitPK, EditEntityBooleanAttributeForm form) {
        return new EditEntityBooleanAttributeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteEntityBooleanAttribute(UserVisitPK userVisitPK, DeleteEntityBooleanAttributeForm form) {
        return new DeleteEntityBooleanAttributeCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Entity Integer Attributes
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createEntityIntegerAttribute(UserVisitPK userVisitPK, CreateEntityIntegerAttributeForm form) {
        return new CreateEntityIntegerAttributeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editEntityIntegerAttribute(UserVisitPK userVisitPK, EditEntityIntegerAttributeForm form) {
        return new EditEntityIntegerAttributeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteEntityIntegerAttribute(UserVisitPK userVisitPK, DeleteEntityIntegerAttributeForm form) {
        return new DeleteEntityIntegerAttributeCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Entity List Item Attributes
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createEntityListItemAttribute(UserVisitPK userVisitPK, CreateEntityListItemAttributeForm form) {
        return new CreateEntityListItemAttributeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editEntityListItemAttribute(UserVisitPK userVisitPK, EditEntityListItemAttributeForm form) {
        return new EditEntityListItemAttributeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteEntityListItemAttribute(UserVisitPK userVisitPK, DeleteEntityListItemAttributeForm form) {
        return new DeleteEntityListItemAttributeCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Entity Long Attributes
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createEntityLongAttribute(UserVisitPK userVisitPK, CreateEntityLongAttributeForm form) {
        return new CreateEntityLongAttributeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editEntityLongAttribute(UserVisitPK userVisitPK, EditEntityLongAttributeForm form) {
        return new EditEntityLongAttributeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteEntityLongAttribute(UserVisitPK userVisitPK, DeleteEntityLongAttributeForm form) {
        return new DeleteEntityLongAttributeCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Entity Multiple List Item Attributes
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createEntityMultipleListItemAttribute(UserVisitPK userVisitPK, CreateEntityMultipleListItemAttributeForm form) {
        return new CreateEntityMultipleListItemAttributeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteEntityMultipleListItemAttribute(UserVisitPK userVisitPK, DeleteEntityMultipleListItemAttributeForm form) {
        return new DeleteEntityMultipleListItemAttributeCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Entity Name Attributes
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createEntityNameAttribute(UserVisitPK userVisitPK, CreateEntityNameAttributeForm form) {
        return new CreateEntityNameAttributeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editEntityNameAttribute(UserVisitPK userVisitPK, EditEntityNameAttributeForm form) {
        return new EditEntityNameAttributeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteEntityNameAttribute(UserVisitPK userVisitPK, DeleteEntityNameAttributeForm form) {
        return new DeleteEntityNameAttributeCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Entity String Attributes
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createEntityStringAttribute(UserVisitPK userVisitPK, CreateEntityStringAttributeForm form) {
        return new CreateEntityStringAttributeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editEntityStringAttribute(UserVisitPK userVisitPK, EditEntityStringAttributeForm form) {
        return new EditEntityStringAttributeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteEntityStringAttribute(UserVisitPK userVisitPK, DeleteEntityStringAttributeForm form) {
        return new DeleteEntityStringAttributeCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Entity Geo Point Attributes
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createEntityGeoPointAttribute(UserVisitPK userVisitPK, CreateEntityGeoPointAttributeForm form) {
        return new CreateEntityGeoPointAttributeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editEntityGeoPointAttribute(UserVisitPK userVisitPK, EditEntityGeoPointAttributeForm form) {
        return new EditEntityGeoPointAttributeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteEntityGeoPointAttribute(UserVisitPK userVisitPK, DeleteEntityGeoPointAttributeForm form) {
        return new DeleteEntityGeoPointAttributeCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Entity Blob Attributes
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createEntityBlobAttribute(UserVisitPK userVisitPK, CreateEntityBlobAttributeForm form) {
        return new CreateEntityBlobAttributeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editEntityBlobAttribute(UserVisitPK userVisitPK, EditEntityBlobAttributeForm form) {
        return new EditEntityBlobAttributeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getEntityBlobAttribute(UserVisitPK userVisitPK, GetEntityBlobAttributeForm form) {
        return new GetEntityBlobAttributeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteEntityBlobAttribute(UserVisitPK userVisitPK, DeleteEntityBlobAttributeForm form) {
        return new DeleteEntityBlobAttributeCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Entity Clob Attributes
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createEntityClobAttribute(UserVisitPK userVisitPK, CreateEntityClobAttributeForm form) {
        return new CreateEntityClobAttributeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editEntityClobAttribute(UserVisitPK userVisitPK, EditEntityClobAttributeForm form) {
        return new EditEntityClobAttributeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getEntityClobAttribute(UserVisitPK userVisitPK, GetEntityClobAttributeForm form) {
        return new GetEntityClobAttributeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteEntityClobAttribute(UserVisitPK userVisitPK, DeleteEntityClobAttributeForm form) {
        return new DeleteEntityClobAttributeCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Entity Time Attributes
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createEntityTimeAttribute(UserVisitPK userVisitPK, CreateEntityTimeAttributeForm form) {
        return new CreateEntityTimeAttributeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editEntityTimeAttribute(UserVisitPK userVisitPK, EditEntityTimeAttributeForm form) {
        return new EditEntityTimeAttributeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteEntityTimeAttribute(UserVisitPK userVisitPK, DeleteEntityTimeAttributeForm form) {
        return new DeleteEntityTimeAttributeCommand(userVisitPK, form).run();
    }

    // --------------------------------------------------------------------------------
    //   Entity Date Attributes
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createEntityDateAttribute(UserVisitPK userVisitPK, CreateEntityDateAttributeForm form) {
        return new CreateEntityDateAttributeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editEntityDateAttribute(UserVisitPK userVisitPK, EditEntityDateAttributeForm form) {
        return new EditEntityDateAttributeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteEntityDateAttribute(UserVisitPK userVisitPK, DeleteEntityDateAttributeForm form) {
        return new DeleteEntityDateAttributeCommand(userVisitPK, form).run();
    }

    // --------------------------------------------------------------------------------
    //   Entity Attribute Entity Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createEntityAttributeEntityType(UserVisitPK userVisitPK, CreateEntityAttributeEntityTypeForm form) {
        return new CreateEntityAttributeEntityTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getEntityAttributeEntityType(UserVisitPK userVisitPK, GetEntityAttributeEntityTypeForm form) {
        return new GetEntityAttributeEntityTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getEntityAttributeEntityTypes(UserVisitPK userVisitPK, GetEntityAttributeEntityTypesForm form) {
        return new GetEntityAttributeEntityTypesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteEntityAttributeEntityType(UserVisitPK userVisitPK, DeleteEntityAttributeEntityTypeForm form) {
        return new DeleteEntityAttributeEntityTypeCommand(userVisitPK, form).run();
    }

    // --------------------------------------------------------------------------------
    //   Entity Entity Attributes
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createEntityEntityAttribute(UserVisitPK userVisitPK, CreateEntityEntityAttributeForm form) {
        return new CreateEntityEntityAttributeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editEntityEntityAttribute(UserVisitPK userVisitPK, EditEntityEntityAttributeForm form) {
        return new EditEntityEntityAttributeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteEntityEntityAttribute(UserVisitPK userVisitPK, DeleteEntityEntityAttributeForm form) {
        return new DeleteEntityEntityAttributeCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Entity Collection Attributes
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createEntityCollectionAttribute(UserVisitPK userVisitPK, CreateEntityCollectionAttributeForm form) {
        return new CreateEntityCollectionAttributeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteEntityCollectionAttribute(UserVisitPK userVisitPK, DeleteEntityCollectionAttributeForm form) {
        return new DeleteEntityCollectionAttributeCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Party Entity Types
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createPartyEntityType(UserVisitPK userVisitPK, CreatePartyEntityTypeForm form) {
        return new CreatePartyEntityTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editPartyEntityType(UserVisitPK userVisitPK, EditPartyEntityTypeForm form) {
        return new EditPartyEntityTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getPartyEntityType(UserVisitPK userVisitPK, GetPartyEntityTypeForm form) {
        return new GetPartyEntityTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getPartyEntityTypes(UserVisitPK userVisitPK, GetPartyEntityTypesForm form) {
        return new GetPartyEntityTypesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deletePartyEntityType(UserVisitPK userVisitPK, DeletePartyEntityTypeForm form) {
        return new DeletePartyEntityTypeCommand(userVisitPK, form).run();
    }

    // -------------------------------------------------------------------------
    //   Cache Entries
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createCacheEntry(UserVisitPK userVisitPK, CreateCacheEntryForm form) {
        return new CreateCacheEntryCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getCacheEntry(UserVisitPK userVisitPK, GetCacheEntryForm form) {
        return new GetCacheEntryCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getCacheEntries(UserVisitPK userVisitPK, GetCacheEntriesForm form) {
        return new GetCacheEntriesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult removeCacheEntry(UserVisitPK userVisitPK, RemoveCacheEntryForm form) {
        return new RemoveCacheEntryCommand(userVisitPK, form).run();
    }

    // -------------------------------------------------------------------------
    //   Cache Entry Dependencies
    // -------------------------------------------------------------------------

    @Override
    public CommandResult getCacheEntryDependencies(UserVisitPK userVisitPK, GetCacheEntryDependenciesForm form) {
        return new GetCacheEntryDependenciesCommand(userVisitPK, form).run();
    }

    // --------------------------------------------------------------------------------
    //   Applications
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createApplication(UserVisitPK userVisitPK, CreateApplicationForm form) {
        return new CreateApplicationCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getApplicationChoices(UserVisitPK userVisitPK, GetApplicationChoicesForm form) {
        return new GetApplicationChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getApplication(UserVisitPK userVisitPK, GetApplicationForm form) {
        return new GetApplicationCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getApplications(UserVisitPK userVisitPK, GetApplicationsForm form) {
        return new GetApplicationsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setDefaultApplication(UserVisitPK userVisitPK, SetDefaultApplicationForm form) {
        return new SetDefaultApplicationCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editApplication(UserVisitPK userVisitPK, EditApplicationForm form) {
        return new EditApplicationCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteApplication(UserVisitPK userVisitPK, DeleteApplicationForm form) {
        return new DeleteApplicationCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Applications Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createApplicationDescription(UserVisitPK userVisitPK, CreateApplicationDescriptionForm form) {
        return new CreateApplicationDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getApplicationDescription(UserVisitPK userVisitPK, GetApplicationDescriptionForm form) {
        return new GetApplicationDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getApplicationDescriptions(UserVisitPK userVisitPK, GetApplicationDescriptionsForm form) {
        return new GetApplicationDescriptionsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editApplicationDescription(UserVisitPK userVisitPK, EditApplicationDescriptionForm form) {
        return new EditApplicationDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteApplicationDescription(UserVisitPK userVisitPK, DeleteApplicationDescriptionForm form) {
        return new DeleteApplicationDescriptionCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Editors
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createEditor(UserVisitPK userVisitPK, CreateEditorForm form) {
        return new CreateEditorCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getEditorChoices(UserVisitPK userVisitPK, GetEditorChoicesForm form) {
        return new GetEditorChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getEditor(UserVisitPK userVisitPK, GetEditorForm form) {
        return new GetEditorCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getEditors(UserVisitPK userVisitPK, GetEditorsForm form) {
        return new GetEditorsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setDefaultEditor(UserVisitPK userVisitPK, SetDefaultEditorForm form) {
        return new SetDefaultEditorCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editEditor(UserVisitPK userVisitPK, EditEditorForm form) {
        return new EditEditorCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteEditor(UserVisitPK userVisitPK, DeleteEditorForm form) {
        return new DeleteEditorCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Editors Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createEditorDescription(UserVisitPK userVisitPK, CreateEditorDescriptionForm form) {
        return new CreateEditorDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getEditorDescription(UserVisitPK userVisitPK, GetEditorDescriptionForm form) {
        return new GetEditorDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getEditorDescriptions(UserVisitPK userVisitPK, GetEditorDescriptionsForm form) {
        return new GetEditorDescriptionsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editEditorDescription(UserVisitPK userVisitPK, EditEditorDescriptionForm form) {
        return new EditEditorDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteEditorDescription(UserVisitPK userVisitPK, DeleteEditorDescriptionForm form) {
        return new DeleteEditorDescriptionCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   ApplicationEditors
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createApplicationEditor(UserVisitPK userVisitPK, CreateApplicationEditorForm form) {
        return new CreateApplicationEditorCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getApplicationEditorChoices(UserVisitPK userVisitPK, GetApplicationEditorChoicesForm form) {
        return new GetApplicationEditorChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getApplicationEditor(UserVisitPK userVisitPK, GetApplicationEditorForm form) {
        return new GetApplicationEditorCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getApplicationEditors(UserVisitPK userVisitPK, GetApplicationEditorsForm form) {
        return new GetApplicationEditorsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setDefaultApplicationEditor(UserVisitPK userVisitPK, SetDefaultApplicationEditorForm form) {
        return new SetDefaultApplicationEditorCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editApplicationEditor(UserVisitPK userVisitPK, EditApplicationEditorForm form) {
        return new EditApplicationEditorCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteApplicationEditor(UserVisitPK userVisitPK, DeleteApplicationEditorForm form) {
        return new DeleteApplicationEditorCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Application Editor Uses
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createApplicationEditorUse(UserVisitPK userVisitPK, CreateApplicationEditorUseForm form) {
        return new CreateApplicationEditorUseCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getApplicationEditorUseChoices(UserVisitPK userVisitPK, GetApplicationEditorUseChoicesForm form) {
        return new GetApplicationEditorUseChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getApplicationEditorUse(UserVisitPK userVisitPK, GetApplicationEditorUseForm form) {
        return new GetApplicationEditorUseCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getApplicationEditorUses(UserVisitPK userVisitPK, GetApplicationEditorUsesForm form) {
        return new GetApplicationEditorUsesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setDefaultApplicationEditorUse(UserVisitPK userVisitPK, SetDefaultApplicationEditorUseForm form) {
        return new SetDefaultApplicationEditorUseCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editApplicationEditorUse(UserVisitPK userVisitPK, EditApplicationEditorUseForm form) {
        return new EditApplicationEditorUseCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteApplicationEditorUse(UserVisitPK userVisitPK, DeleteApplicationEditorUseForm form) {
        return new DeleteApplicationEditorUseCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Application Editor Use Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createApplicationEditorUseDescription(UserVisitPK userVisitPK, CreateApplicationEditorUseDescriptionForm form) {
        return new CreateApplicationEditorUseDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getApplicationEditorUseDescription(UserVisitPK userVisitPK, GetApplicationEditorUseDescriptionForm form) {
        return new GetApplicationEditorUseDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getApplicationEditorUseDescriptions(UserVisitPK userVisitPK, GetApplicationEditorUseDescriptionsForm form) {
        return new GetApplicationEditorUseDescriptionsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editApplicationEditorUseDescription(UserVisitPK userVisitPK, EditApplicationEditorUseDescriptionForm form) {
        return new EditApplicationEditorUseDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteApplicationEditorUseDescription(UserVisitPK userVisitPK, DeleteApplicationEditorUseDescriptionForm form) {
        return new DeleteApplicationEditorUseDescriptionCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Party Application Editor Uses
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createPartyApplicationEditorUse(UserVisitPK userVisitPK, CreatePartyApplicationEditorUseForm form) {
        return new CreatePartyApplicationEditorUseCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getPartyApplicationEditorUse(UserVisitPK userVisitPK, GetPartyApplicationEditorUseForm form) {
        return new GetPartyApplicationEditorUseCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getPartyApplicationEditorUses(UserVisitPK userVisitPK, GetPartyApplicationEditorUsesForm form) {
        return new GetPartyApplicationEditorUsesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editPartyApplicationEditorUse(UserVisitPK userVisitPK, EditPartyApplicationEditorUseForm form) {
        return new EditPartyApplicationEditorUseCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deletePartyApplicationEditorUse(UserVisitPK userVisitPK, DeletePartyApplicationEditorUseForm form) {
        return new DeletePartyApplicationEditorUseCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Appearances
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createAppearance(UserVisitPK userVisitPK, CreateAppearanceForm form) {
        return new CreateAppearanceCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getAppearanceChoices(UserVisitPK userVisitPK, GetAppearanceChoicesForm form) {
        return new GetAppearanceChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getAppearance(UserVisitPK userVisitPK, GetAppearanceForm form) {
        return new GetAppearanceCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getAppearances(UserVisitPK userVisitPK, GetAppearancesForm form) {
        return new GetAppearancesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setDefaultAppearance(UserVisitPK userVisitPK, SetDefaultAppearanceForm form) {
        return new SetDefaultAppearanceCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editAppearance(UserVisitPK userVisitPK, EditAppearanceForm form) {
        return new EditAppearanceCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteAppearance(UserVisitPK userVisitPK, DeleteAppearanceForm form) {
        return new DeleteAppearanceCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Appearances Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createAppearanceDescription(UserVisitPK userVisitPK, CreateAppearanceDescriptionForm form) {
        return new CreateAppearanceDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getAppearanceDescription(UserVisitPK userVisitPK, GetAppearanceDescriptionForm form) {
        return new GetAppearanceDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getAppearanceDescriptions(UserVisitPK userVisitPK, GetAppearanceDescriptionsForm form) {
        return new GetAppearanceDescriptionsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editAppearanceDescription(UserVisitPK userVisitPK, EditAppearanceDescriptionForm form) {
        return new EditAppearanceDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteAppearanceDescription(UserVisitPK userVisitPK, DeleteAppearanceDescriptionForm form) {
        return new DeleteAppearanceDescriptionCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Appearances Text Decorations
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createAppearanceTextDecoration(UserVisitPK userVisitPK, CreateAppearanceTextDecorationForm form) {
        return new CreateAppearanceTextDecorationCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getAppearanceTextDecoration(UserVisitPK userVisitPK, GetAppearanceTextDecorationForm form) {
        return new GetAppearanceTextDecorationCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getAppearanceTextDecorations(UserVisitPK userVisitPK, GetAppearanceTextDecorationsForm form) {
        return new GetAppearanceTextDecorationsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteAppearanceTextDecoration(UserVisitPK userVisitPK, DeleteAppearanceTextDecorationForm form) {
        return new DeleteAppearanceTextDecorationCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Appearances Text Transformations
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createAppearanceTextTransformation(UserVisitPK userVisitPK, CreateAppearanceTextTransformationForm form) {
        return new CreateAppearanceTextTransformationCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getAppearanceTextTransformation(UserVisitPK userVisitPK, GetAppearanceTextTransformationForm form) {
        return new GetAppearanceTextTransformationCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getAppearanceTextTransformations(UserVisitPK userVisitPK, GetAppearanceTextTransformationsForm form) {
        return new GetAppearanceTextTransformationsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteAppearanceTextTransformation(UserVisitPK userVisitPK, DeleteAppearanceTextTransformationForm form) {
        return new DeleteAppearanceTextTransformationCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Colors
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createColor(UserVisitPK userVisitPK, CreateColorForm form) {
        return new CreateColorCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getColorChoices(UserVisitPK userVisitPK, GetColorChoicesForm form) {
        return new GetColorChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getColor(UserVisitPK userVisitPK, GetColorForm form) {
        return new GetColorCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getColors(UserVisitPK userVisitPK, GetColorsForm form) {
        return new GetColorsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setDefaultColor(UserVisitPK userVisitPK, SetDefaultColorForm form) {
        return new SetDefaultColorCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editColor(UserVisitPK userVisitPK, EditColorForm form) {
        return new EditColorCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteColor(UserVisitPK userVisitPK, DeleteColorForm form) {
        return new DeleteColorCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Color Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createColorDescription(UserVisitPK userVisitPK, CreateColorDescriptionForm form) {
        return new CreateColorDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getColorDescription(UserVisitPK userVisitPK, GetColorDescriptionForm form) {
        return new GetColorDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getColorDescriptions(UserVisitPK userVisitPK, GetColorDescriptionsForm form) {
        return new GetColorDescriptionsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editColorDescription(UserVisitPK userVisitPK, EditColorDescriptionForm form) {
        return new EditColorDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteColorDescription(UserVisitPK userVisitPK, DeleteColorDescriptionForm form) {
        return new DeleteColorDescriptionCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Font Styles
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createFontStyle(UserVisitPK userVisitPK, CreateFontStyleForm form) {
        return new CreateFontStyleCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getFontStyleChoices(UserVisitPK userVisitPK, GetFontStyleChoicesForm form) {
        return new GetFontStyleChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getFontStyle(UserVisitPK userVisitPK, GetFontStyleForm form) {
        return new GetFontStyleCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getFontStyles(UserVisitPK userVisitPK, GetFontStylesForm form) {
        return new GetFontStylesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setDefaultFontStyle(UserVisitPK userVisitPK, SetDefaultFontStyleForm form) {
        return new SetDefaultFontStyleCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editFontStyle(UserVisitPK userVisitPK, EditFontStyleForm form) {
        return new EditFontStyleCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteFontStyle(UserVisitPK userVisitPK, DeleteFontStyleForm form) {
        return new DeleteFontStyleCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Font Style Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createFontStyleDescription(UserVisitPK userVisitPK, CreateFontStyleDescriptionForm form) {
        return new CreateFontStyleDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getFontStyleDescription(UserVisitPK userVisitPK, GetFontStyleDescriptionForm form) {
        return new GetFontStyleDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getFontStyleDescriptions(UserVisitPK userVisitPK, GetFontStyleDescriptionsForm form) {
        return new GetFontStyleDescriptionsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editFontStyleDescription(UserVisitPK userVisitPK, EditFontStyleDescriptionForm form) {
        return new EditFontStyleDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteFontStyleDescription(UserVisitPK userVisitPK, DeleteFontStyleDescriptionForm form) {
        return new DeleteFontStyleDescriptionCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Font Weights
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createFontWeight(UserVisitPK userVisitPK, CreateFontWeightForm form) {
        return new CreateFontWeightCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getFontWeightChoices(UserVisitPK userVisitPK, GetFontWeightChoicesForm form) {
        return new GetFontWeightChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getFontWeight(UserVisitPK userVisitPK, GetFontWeightForm form) {
        return new GetFontWeightCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getFontWeights(UserVisitPK userVisitPK, GetFontWeightsForm form) {
        return new GetFontWeightsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setDefaultFontWeight(UserVisitPK userVisitPK, SetDefaultFontWeightForm form) {
        return new SetDefaultFontWeightCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editFontWeight(UserVisitPK userVisitPK, EditFontWeightForm form) {
        return new EditFontWeightCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteFontWeight(UserVisitPK userVisitPK, DeleteFontWeightForm form) {
        return new DeleteFontWeightCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Font Weight Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createFontWeightDescription(UserVisitPK userVisitPK, CreateFontWeightDescriptionForm form) {
        return new CreateFontWeightDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getFontWeightDescription(UserVisitPK userVisitPK, GetFontWeightDescriptionForm form) {
        return new GetFontWeightDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getFontWeightDescriptions(UserVisitPK userVisitPK, GetFontWeightDescriptionsForm form) {
        return new GetFontWeightDescriptionsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editFontWeightDescription(UserVisitPK userVisitPK, EditFontWeightDescriptionForm form) {
        return new EditFontWeightDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteFontWeightDescription(UserVisitPK userVisitPK, DeleteFontWeightDescriptionForm form) {
        return new DeleteFontWeightDescriptionCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Text Decorations
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createTextDecoration(UserVisitPK userVisitPK, CreateTextDecorationForm form) {
        return new CreateTextDecorationCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getTextDecorationChoices(UserVisitPK userVisitPK, GetTextDecorationChoicesForm form) {
        return new GetTextDecorationChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getTextDecoration(UserVisitPK userVisitPK, GetTextDecorationForm form) {
        return new GetTextDecorationCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getTextDecorations(UserVisitPK userVisitPK, GetTextDecorationsForm form) {
        return new GetTextDecorationsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setDefaultTextDecoration(UserVisitPK userVisitPK, SetDefaultTextDecorationForm form) {
        return new SetDefaultTextDecorationCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editTextDecoration(UserVisitPK userVisitPK, EditTextDecorationForm form) {
        return new EditTextDecorationCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteTextDecoration(UserVisitPK userVisitPK, DeleteTextDecorationForm form) {
        return new DeleteTextDecorationCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Text Decoration Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createTextDecorationDescription(UserVisitPK userVisitPK, CreateTextDecorationDescriptionForm form) {
        return new CreateTextDecorationDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getTextDecorationDescription(UserVisitPK userVisitPK, GetTextDecorationDescriptionForm form) {
        return new GetTextDecorationDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getTextDecorationDescriptions(UserVisitPK userVisitPK, GetTextDecorationDescriptionsForm form) {
        return new GetTextDecorationDescriptionsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editTextDecorationDescription(UserVisitPK userVisitPK, EditTextDecorationDescriptionForm form) {
        return new EditTextDecorationDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteTextDecorationDescription(UserVisitPK userVisitPK, DeleteTextDecorationDescriptionForm form) {
        return new DeleteTextDecorationDescriptionCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Text Transformations
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createTextTransformation(UserVisitPK userVisitPK, CreateTextTransformationForm form) {
        return new CreateTextTransformationCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getTextTransformationChoices(UserVisitPK userVisitPK, GetTextTransformationChoicesForm form) {
        return new GetTextTransformationChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getTextTransformation(UserVisitPK userVisitPK, GetTextTransformationForm form) {
        return new GetTextTransformationCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getTextTransformations(UserVisitPK userVisitPK, GetTextTransformationsForm form) {
        return new GetTextTransformationsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setDefaultTextTransformation(UserVisitPK userVisitPK, SetDefaultTextTransformationForm form) {
        return new SetDefaultTextTransformationCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editTextTransformation(UserVisitPK userVisitPK, EditTextTransformationForm form) {
        return new EditTextTransformationCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteTextTransformation(UserVisitPK userVisitPK, DeleteTextTransformationForm form) {
        return new DeleteTextTransformationCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Text Transformation Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createTextTransformationDescription(UserVisitPK userVisitPK, CreateTextTransformationDescriptionForm form) {
        return new CreateTextTransformationDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getTextTransformationDescription(UserVisitPK userVisitPK, GetTextTransformationDescriptionForm form) {
        return new GetTextTransformationDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getTextTransformationDescriptions(UserVisitPK userVisitPK, GetTextTransformationDescriptionsForm form) {
        return new GetTextTransformationDescriptionsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editTextTransformationDescription(UserVisitPK userVisitPK, EditTextTransformationDescriptionForm form) {
        return new EditTextTransformationDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteTextTransformationDescription(UserVisitPK userVisitPK, DeleteTextTransformationDescriptionForm form) {
        return new DeleteTextTransformationDescriptionCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Entity Appearances
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createEntityAppearance(UserVisitPK userVisitPK, CreateEntityAppearanceForm form) {
        return new CreateEntityAppearanceCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getEntityAppearance(UserVisitPK userVisitPK, GetEntityAppearanceForm form) {
        return new GetEntityAppearanceCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editEntityAppearance(UserVisitPK userVisitPK, EditEntityAppearanceForm form) {
        return new EditEntityAppearanceCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteEntityAppearance(UserVisitPK userVisitPK, DeleteEntityAppearanceForm form) {
        return new DeleteEntityAppearanceCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Utilities
    // -------------------------------------------------------------------------

    @Override
    public CommandResult encrypt(UserVisitPK userVisitPK, EncryptForm form) {
        return new EncryptCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult decrypt(UserVisitPK userVisitPK, DecryptForm form) {
        return new DecryptCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult validate(UserVisitPK userVisitPK, ValidateForm form) {
        return new ValidateCommand(userVisitPK, form).run();
    }
}
