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

import com.echothree.model.control.core.common.MimeTypes;
import com.google.common.html.HtmlEscapers;
import com.ibm.icu.text.Transliterator;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Locale;

public class StringUtils {
    
    private StringUtils() {
        super();
    }
    
    private static class StringUtilsHolder {
        static StringUtils instance = new StringUtils();
    }
    
    public static StringUtils getInstance() {
        return StringUtilsHolder.instance;
    }

    private Transliterator transliterator = Transliterator.getInstance("Any-Latin; Latin-ASCII; NFKD; [:Nonspacing Mark:] Remove; NFKC", Transliterator.FORWARD);

    public String toAscii7(String text) {
        return transliterator.transliterate(text);
    }

    public String getIndent(int indentAmount, int indentCount) {
        return String.format("%" + indentAmount * indentCount + "s", "");
    }

    /** Take a string and trim all leading and trailing spaces from it.
     * If the resulting length is zero, return null.
     */
    public String trimToNull(String string) {
        String result = null;

        if(string != null) {
            result = string.trim();

            if(result.length() == 0) {
                result = null;
            }
        }

        return result;
    }

    /** 
     * null-safe String comparison.
     */
    public int nullSafeCompareTo(final String s1, final String s2) {
        if (s1 == null ^ s2 == null) {
            return (s1 == null) ? -1 : 1;
        }

        if (s1 == null && s2 == null) {
            return 0;
        }

        return s1.compareTo(s2);
    }

    public String left(String str, int len) {
        return str == null ? null : str.length() <= len ? str : str.substring(0, len);
    }

    public String right(String str, int len) {
        return str == null ? null : str.length() <= len ? str : str.substring(str.length() - len);
    }

    public String cleanStringToName(final String s) {
        var a = s.toCharArray();
        var l = a.length;
        var r = new StringBuilder(l);
        
        for(var i = 0; i < l; i++) {
            var t = a[i];
            
            if(!Character.isLetterOrDigit(t) && t != '-' && t != '_') {
                t = '_';
            }
            
            r.append(t);
            
        }
        
        return r.toString();
    }

    public int countSpaces(final String str) {
        var count = 0;

        for(int codePoint : codePoints(str)) {
            if(Character.isSpaceChar(codePoint)) {
                count++;
            }
        }

        return count;
    }
    
    // Look for the first Space character in a String, and construct a new String that contains it.
    public String getSpace(final String str) {
        String result = null;
        
        for(int codePoint : codePoints(str)) {
            if(Character.isSpaceChar(codePoint)) {
                result = new String(new int[] { codePoint }, 0, 1);
                break;
            }
        }
        
        return result;
    }

    public String cleanString(final String str, final boolean includeDigits, final boolean includeLetters, final boolean includeSpaces) {
        var sb = new StringBuilder();

        for(int codePoint : codePoints(str)) {
            var appended = false;

            if(includeDigits && includeLetters) {
                if(Character.isLetterOrDigit(codePoint)) {
                    sb.appendCodePoint(codePoint);
                    appended = true;
                }
            } else {
                if(includeDigits && Character.isDigit(codePoint)) {
                    sb.appendCodePoint(codePoint);
                    appended = true;
                }

                if(!appended && includeLetters && Character.isLetter(codePoint)) {
                    sb.appendCodePoint(codePoint);
                    appended = true;
                }
            }

            if(!appended && includeSpaces && Character.isSpaceChar(codePoint)) {
                sb.appendCodePoint(codePoint);
            }
        }

        return sb.toString();
    }

    public String cleanStringToLettersOrDigits(final String str) {
        return cleanString(str, true, true, false);
    }
    
    public String upperCaseFirstCharacter(final String str) {
        return str == null ? null : str.length() == 0 ? str : str.substring(0, 1).toUpperCase(Locale.getDefault()) + str.substring(1);
    }

    public String lowerCaseFirstCharacter(final String str) {
        return str == null ? null : str.length() == 0 ? str : str.substring(0, 1).toLowerCase(Locale.getDefault()) + str.substring(1);
    }

    public String normalizeCase(final String str) {
        return str == null ? null : str.length() == 0 ? str : str.substring(0, 1).toUpperCase(Locale.getDefault()) + str.substring(1).toLowerCase(Locale.getDefault());
    }

    // Similar to http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=5003547
    public Iterable<Integer> codePoints(final String str) {
        return () -> new Iterator<Integer>() {
            int nextIndex = 0;
            int len = str.length();
            
            @Override
            public boolean hasNext() {
                return nextIndex < len;
            }
            
            @Override
            public Integer next() {
                var result = str.codePointAt(nextIndex);
                nextIndex += Character.charCount(result);
                return result;
            }
            
            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }
    
    public String convertToHtml(final String value, final String mimeTypeName) {
        if(mimeTypeName.equals(MimeTypes.TEXT_HTML.mimeTypeName())) {
            return value;
        } else if(mimeTypeName.equals(MimeTypes.TEXT_PLAIN.mimeTypeName())) {
            return "<pre>" + HtmlEscapers.htmlEscaper().escape(value) + "</pre>";
        } else if(mimeTypeName.equals(MimeTypes.TEXT_X_MARKUP.mimeTypeName())) {
            return MarkupUtils.getInstance().filter(value);
        } else if(mimeTypeName.equals(MimeTypes.TEXT_X_TEXTILE.mimeTypeName())) {
            return TextileUtils.getInstance().filter(value);
        } else {
            return "Unsupported mimeTypeName.";
        }
    }

    public String mask(final String value, final char mask, final int unmasked) {
        var len = value.length();
        String result;

        // Make sure there's enough String to mask anything at all
        if(len - unmasked > 0) {
            char maskChars[] = new char[len - unmasked];

            Arrays.fill(maskChars, mask);

            result = String.valueOf(maskChars) + value.substring(len - unmasked, len);
        } else {
            result = value;
        }

        return result;
    }

    public String mask(final String value, final char mask) {
        char maskChars[] = new char[value.length()];

        Arrays.fill(maskChars, mask);

        return new String(maskChars);
    }

    public String repeatingStringFromChar(final char val, final int length) {
        var data = new char[length];

        Arrays.fill(data, val);

        return String.valueOf(data);
    }

    public boolean isAllUpperCase(String str) {
        return str == null ? false : str.equals(str.toUpperCase(Locale.getDefault()));
    }

    public boolean isAllLowerCase(String str) {
        return str == null ? false : str.equals(str.toLowerCase(Locale.getDefault()));
    }

    public boolean isAllSameCase(String str) {
        return str == null ? false : isAllUpperCase(str) || isAllLowerCase(str);
    }

}
