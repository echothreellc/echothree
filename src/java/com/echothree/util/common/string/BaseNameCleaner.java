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

package com.echothree.util.common.string;

import com.echothree.model.control.party.common.choice.NameSuffixChoicesBean;
import com.echothree.model.control.party.common.choice.PersonalTitleChoicesBean;
import com.google.common.base.Splitter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class BaseNameCleaner {

    private StringUtils stringUtils = StringUtils.getInstance();

    private final Map<String, String> personalTitles = new HashMap<>();
    private final Map<String, String> personalTitlesOriginal = new HashMap<>();
    private int maxPersonalTitleSpaces = 0;

    protected String cleanStringForTitleOrSuffix(String str) {
        return stringUtils.cleanString(str, true, true, true).toLowerCase(Locale.getDefault());
    }

    public Map<String, String> getPersonalTitles() {
        return personalTitles;
    }

    public Map<String, String> getPersonalTitlesOriginal() {
        return personalTitlesOriginal;
    }

    private final Map<String, String> nameSuffixes = new HashMap<>();
    private final Map<String, String> nameSuffixesOriginal = new HashMap<>();
    private int maxNameSuffixSpaces = 0;

    public Map<String, String> getNameSuffixes() {
        return nameSuffixes;
    }

    public Map<String, String> getNameSuffixesOriginal() {
        return nameSuffixesOriginal;
    }

    protected void setupPersonalTitles(PersonalTitleChoicesBean personalTitleChoices) {
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

    protected void setupNameSuffixes(NameSuffixChoicesBean nameSuffixChoices) {
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

    private List<String> iterableToList(Iterable<String> pieces) {
        var list = new ArrayList<String>();

        for(var str : pieces) {
            list.add(str);
        }

        return list;
    }

    private static final Splitter SpaceSplitter = Splitter.on(' ')
            .trimResults()
            .omitEmptyStrings();

    private static final int MaximumFirstNameLength = 20;
    private static final int MaximumMiddleNameLength = 20;
    private static final int MaximumLastNameLength = 20;

    public NameResult getCleansedName(final String str) {
        String personalTitleChoice = null;
        int personalTitlePieces;
        String firstName = null;
        String middleName = null;
        String lastName = null;
        String nameSuffixChoice = null;
        int nameSuffixPieces;

        // 1) Break apart str into a List at any space character.
        var pieces = iterableToList(SpaceSplitter.split(str));
        var piecesSize = pieces.size();
        var startingIndex = 0;
        var endingIndex = piecesSize - 1;

        // 2) Find the longest (in words) personal title.
        for(var i = Math.min(maxPersonalTitleSpaces, endingIndex - startingIndex) ; i >= startingIndex ; i--) {
            var personalTitle = new StringBuilder();

            for(var j = 0; j <= i ; j++) {
                if(j > 0) {
                    personalTitle.append(' ');
                }

                personalTitle.append(pieces.get(startingIndex + j));
            }

            personalTitleChoice = personalTitles.get(cleanStringForTitleOrSuffix(personalTitle.toString()));

            if(personalTitleChoice != null) {
                personalTitlePieces = i - startingIndex + 1;
                startingIndex += personalTitlePieces;
                break;
            }
        }

        // 3) Find the longest (in words) name suffix.
        if(startingIndex <= endingIndex) {
            for(var i = Math.min(endingIndex == 0 ? 0 : endingIndex - maxNameSuffixSpaces, endingIndex - startingIndex) ; i <= endingIndex ; i++) {
                var nameSuffix = new StringBuilder();

                for(var j = i; j <= endingIndex ; j++) {
                    if(j > i) {
                        nameSuffix.append(' ');
                    }

                    nameSuffix.append(pieces.get(j));
                }

                nameSuffixChoice = nameSuffixes.get(cleanStringForTitleOrSuffix(nameSuffix.toString()));

                if(nameSuffixChoice != null) {
                    nameSuffixPieces = endingIndex - i + 1;
                    endingIndex -= nameSuffixPieces;
                    break;
                }
            }
        }

        // 4) Deal with commas and flipped names ("Harms, Richard").
        if(startingIndex <= endingIndex) {
            var firstPiece = pieces.get(startingIndex);

            if(firstPiece.endsWith(",") && firstPiece.length() > 1) {
                // If the first piece ends with a comma, assume they put their last name first, and flip things around.
                for(var i = startingIndex + 1 ; i <= endingIndex ; i++) {
                    pieces.set(i - 1, pieces.get(i));
                }

                pieces.set(endingIndex, firstPiece);
            }

            // Trim all trailing commas, and remove any new empty elements.
            for(var i = startingIndex ; i <= endingIndex ; i++) {
                var element = pieces.get(i);

                if(element.endsWith(",")) {
                    element = element.substring(0, element.length() - 1);

                    if(element.length() == 0) {
                        pieces.remove(i);
                        endingIndex--;

                        if(pieces.isEmpty()) {
                            break;
                        } else {
                            i--;
                        }
                    } else {
                        pieces.set(i, element);
                    }
                }
            }
        }

        // 5) Pick out bits of a name.
        if(startingIndex <= endingIndex) {
            var done = false;

            if(personalTitleChoice != null && startingIndex - endingIndex == 0) {
                // Mr. Harms
                lastName = pieces.get(startingIndex);
                done = true;
            } else {
                if(nameSuffixChoice != null && startingIndex - endingIndex == 0) {
                    // Richard Jr.
                    firstName = pieces.get(startingIndex);
                    done = true;
                } else {
                    // Richard
                    if(personalTitleChoice == null && nameSuffixChoice == null && piecesSize == 1) {
                        firstName = pieces.get(startingIndex);
                        done = true;
                    }
                }
            }

            if(!done) {
                firstName = pieces.get(startingIndex);
                startingIndex++;

                if(startingIndex == endingIndex) {
                    lastName = pieces.get(startingIndex);
                } else {
                    var sb = new StringBuilder();

                    for(var i = startingIndex; i < endingIndex ; i++) {
                        var toAppend = pieces.get(i);

                        if(i != startingIndex) {
                            sb.append(' ');
                        }

                        sb.append(stringUtils.isAllSameCase(toAppend) ? stringUtils.normalizeCase(toAppend) : toAppend);
                    }

                    middleName = sb.toString();
                    lastName = pieces.get(endingIndex);
                }
            }
        }

        // X) If any pieces of the name are all upper-case, or are all lower-case, fix them.
        if(stringUtils.isAllSameCase(firstName)) {
            firstName = stringUtils.normalizeCase(firstName);
        }

        if(stringUtils.isAllSameCase(lastName)) {
            lastName = stringUtils.normalizeCase(lastName);
        }

        return new NameResult(personalTitleChoice,
                stringUtils.left(firstName, MaximumFirstNameLength),
                stringUtils.left(middleName, MaximumMiddleNameLength),
                stringUtils.left(lastName, MaximumLastNameLength),
                nameSuffixChoice);
    }

}
