// --------------------------------------------------------------------------------
// Copyright 2002-2023 Echo Three, LLC
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

package com.echothree.model.control.item.server.transfer;

import com.echothree.model.control.core.common.transfer.MimeTypeTransfer;
import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.control.item.common.transfer.HarmonizedTariffScheduleCodeTransfer;
import com.echothree.model.control.item.common.transfer.HarmonizedTariffScheduleCodeTranslationTransfer;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.party.common.transfer.LanguageTransfer;
import com.echothree.model.data.core.server.entity.MimeType;
import com.echothree.model.data.item.server.entity.HarmonizedTariffScheduleCodeTranslation;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class HarmonizedTariffScheduleCodeTranslationTransferCache
        extends BaseItemDescriptionTransferCache<HarmonizedTariffScheduleCodeTranslation, HarmonizedTariffScheduleCodeTranslationTransfer> {
    
    CoreControl coreControl = Session.getModelController(CoreControl.class);
    
    /** Creates a new instance of HarmonizedTariffScheduleCodeTranslationTransferCache */
    public HarmonizedTariffScheduleCodeTranslationTransferCache(UserVisit userVisit, ItemControl itemControl) {
        super(userVisit, itemControl);
    }
    
    @Override
    public HarmonizedTariffScheduleCodeTranslationTransfer getTransfer(HarmonizedTariffScheduleCodeTranslation harmonizedTariffScheduleCodeTranslation) {
        HarmonizedTariffScheduleCodeTranslationTransfer harmonizedTariffScheduleCodeTranslationTransfer = get(harmonizedTariffScheduleCodeTranslation);
        
        if(harmonizedTariffScheduleCodeTranslationTransfer == null) {
            HarmonizedTariffScheduleCodeTransfer harmonizedTariffScheduleCodeTransfer = itemControl.getHarmonizedTariffScheduleCodeTransfer(userVisit, harmonizedTariffScheduleCodeTranslation.getHarmonizedTariffScheduleCode());
            LanguageTransfer languageTransfer = partyControl.getLanguageTransfer(userVisit, harmonizedTariffScheduleCodeTranslation.getLanguage());
            String description = harmonizedTariffScheduleCodeTranslation.getDescription();
            MimeType overviewMimeType = harmonizedTariffScheduleCodeTranslation.getOverviewMimeType();
            MimeTypeTransfer overviewMimeTypeTransfer = overviewMimeType == null? null: coreControl.getMimeTypeTransfer(userVisit, overviewMimeType);
            String overview = harmonizedTariffScheduleCodeTranslation.getOverview();
            
            harmonizedTariffScheduleCodeTranslationTransfer = new HarmonizedTariffScheduleCodeTranslationTransfer(languageTransfer,
                    harmonizedTariffScheduleCodeTransfer, description, overviewMimeTypeTransfer, overview);
            put(harmonizedTariffScheduleCodeTranslation, harmonizedTariffScheduleCodeTranslationTransfer);
        }
        
        return harmonizedTariffScheduleCodeTranslationTransfer;
    }
    
}
