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

package com.echothree.model.control.index.server.indexer.sortabledescriptionproducer;

import com.echothree.model.control.party.common.Languages;
import com.echothree.model.data.party.server.entity.Language;

public class SortableDescriptionProducerFactory {

    private SortableDescriptionProducerFactory() {
        super();
    }

    private static class SortableDescriptionProducerFactoryHolder {
        static SortableDescriptionProducerFactory instance = new SortableDescriptionProducerFactory();
    }

    public static SortableDescriptionProducerFactory getInstance() {
        return SortableDescriptionProducerFactoryHolder.instance;
    }

    public SortableDescriptionProducer getSortableDescriptionProducer(Language language) {
        var languageIsoName = language.getLanguageIsoName();

        if(languageIsoName.equals(Languages.en.name())) {
            return new EnglishSortableDescriptionProducer();
        } else {
            return new UniversalSortableDescriptionProducer();
        }
    }

}
