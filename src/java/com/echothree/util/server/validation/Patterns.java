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

package com.echothree.util.server.validation;

import java.util.regex.Pattern;

public interface Patterns {
    
    Pattern Option = Pattern.compile("^([a-z]|[A-Z]|[0-9]|-|_)+$");
    Pattern TableNameSingular = Pattern.compile("^([a-z]|[A-Z]|[0-9]|-|_)+$");
    
    Pattern Id = Pattern.compile("^[0-9]+$");
    Pattern Boolean = Pattern.compile("^(true|false)$");
    Pattern EntityName = Pattern.compile("^([a-z]|[A-Z]|[0-9]|-|_)+$");
    Pattern EntityName2 = Pattern.compile("^([a-z]|[A-Z]|[0-9]|-|_)+:([a-z]|[A-Z]|[0-9]|-|_)+$");
    Pattern EntityNames = Pattern.compile("^(|([a-z]|[A-Z]|[0-9]|-|_)+:)*([a-z]|[A-Z]|[0-9]|-|_)+$");
    Pattern EntityRef = Pattern.compile("^([a-z]|[A-Z]|[0-9]|-|_|\\.)+\\.([a-z]|[A-Z]|[0-9]|-|_|\\.)+\\.([0-9])+$");
    Pattern EmailAddress = Pattern.compile("^.+@.+\\.(com|net|int|org|mil|edu|gov|[a-z][a-z]|aero|biz|coop|info|museum|name|pro)$");
    Pattern Year = Pattern.compile("^(19|20)[0-9][0-9]$");
    Pattern SignedNumbers = Pattern.compile("^((|-)[0-9]+|MAX_VALUE|MIN_VALUE)$");
    Pattern UnsignedNumbers = Pattern.compile("^([0-9]+|MAX_VALUE)$");
    Pattern PhoneNumber = Pattern.compile("^(\\(|)[0-9][0-9][0-9]((\\)( |)|)|([-\\.]|))[0-9][0-9][0-9]([ -\\.]|)[0-9][0-9][0-9][0-9]$");
    Pattern Inet4Address = Pattern.compile("^([0-9]|[0-9][0-9]|[0-9][0-9][0-9])\\.([0-9]|[0-9][0-9]|[0-9][0-9][0-9])\\.([0-9]|[0-9][0-9]|[0-9][0-9][0-9])\\.([0-9]|[0-9][0-9]|[0-9][0-9][0-9])$");
    Pattern InternationalPhoneNumber = Pattern.compile("^[0-9\\-\\.\\ \\(\\)]+$");
    Pattern InternationalPhoneCountry = Pattern.compile("^[0-9][0-9]([0-9]|)$");
    Pattern State = Pattern.compile("^[a-zA-Z][a-zA-Z]$");
    Pattern YOrN = Pattern.compile("^[yYnN]$");
    Pattern TrueOrFalse = Pattern.compile("^([tT][rR][uU][eE]|[fF][aA][lL][sS][eE])$");
    Pattern Precent = Pattern.compile("^(|1)(|[0-9])[0-9](|%)$");
    Pattern FractionalPercent = Pattern.compile("^(|1)(|[0-9])[0-9](|\\.(|[0-9])(|[0-9])(|[0-9]))(|%)$");
    Pattern HostName = Pattern.compile("^([a-z]|[A-Z]|[0-9]|-|\\.)+$");
    Pattern DomainName = Pattern.compile("^([0-9a-z\\-])+\\.(com|net|int|org|mil|edu|gov|[a-z][a-z]|aero|biz|coop|info|museum|name|pro)$");
    Pattern DomainSuffix = Pattern.compile("^\\.(com|net|int|org|mil|edu|gov|[a-z][a-z]|aero|biz|coop|info|museum|name|pro)$");
    Pattern CreditCardMonth = Pattern.compile("^((|0)[1-9]|1[0-2])$");
    Pattern CreditCardYear = Pattern.compile("^(20[0-9][0-9]|([0-9][0-9]|[0-9]))$");
    Pattern UpperLetter2 = Pattern.compile("^[A-Z][A-Z]$");
    Pattern UpperLetter3 = Pattern.compile("^[A-Z][A-Z][A-Z]$");
    Pattern Number3 = Pattern.compile("^[0-9][0-9][0-9]$");
    Pattern Numbers = Pattern.compile("^[0-9]+$");
    Pattern SequenceMask = Pattern.compile("^[AZ9]+$");
    Pattern Tag = Pattern.compile("^([a-z]|[0-9]|-|_| )+$");
    Pattern TimeZoneName = Pattern.compile("^([a-z]|[A-Z]|[0-9]|-|_|\\.|/)+$");
    Pattern MimeType = Pattern.compile("^([a-z]|[A-Z]|[0-9]|-|_|\\.\\+)+/([a-z]|[A-Z]|[0-9]|-|_|\\.|\\+)+$");
    Pattern Key = Pattern.compile("^([a-z]|[A-Z]|[0-9]|-|_)+$");
    Pattern HarmonizedTariffScheduleCode = Pattern.compile("^[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]$");
    
}
