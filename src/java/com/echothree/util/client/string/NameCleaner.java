// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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

package com.echothree.util.client.string;

import com.echothree.control.user.party.client.helper.NameSuffixesHelper;
import com.echothree.control.user.party.client.helper.PersonalTitlesHelper;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.string.BaseNameCleaner;
import javax.naming.NamingException;

public final class NameCleaner
        extends BaseNameCleaner {

    /** Creates a new instance of NameCleaner */
    public NameCleaner(UserVisitPK userVisitPK)
            throws NamingException {
        loadPersonalTitles(userVisitPK);
        loadNameSuffixes(userVisitPK);
    }

    protected final void loadPersonalTitles(UserVisitPK userVisitPK)
            throws NamingException {
        var personalTitleChoices = PersonalTitlesHelper.getInstance().getPersonalTitleChoices(userVisitPK, Boolean.FALSE);
        var valueIter = personalTitleChoices.getValues().iterator();
        var labelIter = personalTitleChoices.getLabels().iterator();

        while(valueIter.hasNext()) {
            var originalLabel = labelIter.next();
            var label = cleanStringForTitleOrSuffix(originalLabel);
            var value = valueIter.next();
            var spaceCount = stringUtils.countSpaces(label);

            personalTitles.put(label, value);
            personalTitlesOriginal.put(value, originalLabel);

            if(spaceCount > maxPersonalTitleSpaces) {
                maxPersonalTitleSpaces = spaceCount;
            }
        }
    }

    protected void loadNameSuffixes(UserVisitPK userVisitPK)
            throws NamingException {
        var nameSuffixChoices = NameSuffixesHelper.getInstance().getNameSuffixChoices(userVisitPK, Boolean.FALSE);
        var valueIter = nameSuffixChoices.getValues().iterator();
        var labelIter = nameSuffixChoices.getLabels().iterator();

        while(valueIter.hasNext()) {
            var originalLabel = labelIter.next();
            var label = cleanStringForTitleOrSuffix(originalLabel);
            var value = valueIter.next();
            var spaceCount = stringUtils.countSpaces(label);

            nameSuffixes.put(label, value);
            nameSuffixesOriginal.put(value, originalLabel);

            if(spaceCount > maxNameSuffixSpaces) {
                maxNameSuffixSpaces = spaceCount;
            }
        }
    }

}
