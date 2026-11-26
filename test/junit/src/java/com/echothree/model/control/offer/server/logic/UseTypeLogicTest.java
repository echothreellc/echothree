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

package com.echothree.model.control.offer.server.logic;

import com.echothree.control.user.offer.common.spec.OfferSpecFactory;
import com.echothree.model.control.offer.common.exception.UnknownUseTypeDescriptionException;
import com.echothree.model.control.offer.common.exception.UnknownUseTypeNameException;
import com.echothree.model.control.offer.server.control.UseTypeControl;
import com.echothree.model.control.party.common.exception.UnknownLanguageIsoNameException;
import com.echothree.model.control.party.server.logic.LanguageLogic;
import com.echothree.model.data.offer.common.pk.UseTypeDescriptionPK;
import com.echothree.model.data.offer.common.pk.UseTypePK;
import com.echothree.model.data.offer.server.entity.UseType;
import com.echothree.model.data.offer.server.entity.UseTypeDescription;
import com.echothree.model.data.offer.server.value.UseTypeDescriptionValue;
import com.echothree.model.data.offer.server.value.UseTypeValue;
import com.echothree.model.data.party.common.pk.LanguagePK;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.value.LanguageValue;
import com.echothree.util.common.exception.BaseException;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.message.Messages;
import com.echothree.util.server.message.DummyExecutionErrorAccumulator;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("UnnecessaryAssignment") // Supply CDI dependencies without a container.
public class UseTypeLogicTest {

    private static class TestLogic extends UseTypeLogic {
        private final UseType useType = new UseType(new UseTypeValue(new UseTypePK(1L), null, null), EntityPermission.READ_ONLY);
        private final Language language = new Language(new LanguageValue(new LanguagePK(2L), "en", false, 0), EntityPermission.READ_ONLY);
        private final UseTypeDescription description = new UseTypeDescription(new UseTypeDescriptionValue(
                new UseTypeDescriptionPK(3L), useType.getPrimaryKey(), language.getPrimaryKey(), "Original", 0L, Long.MAX_VALUE),
                EntityPermission.READ_WRITE);
        private int languageLookups;
        private int descriptionLookups;
        private EntityPermission descriptionPermission;

        TestLogic(String missingEntity) {
            useTypeControl = new UseTypeControl() {
                @Override
                public UseType getUseTypeByName(String useTypeName, EntityPermission entityPermission) {
                    assertEquals("TYPE", useTypeName);
                    assertEquals(EntityPermission.READ_ONLY, entityPermission);
                    return "type".equals(missingEntity) ? null : useType;
                }

                @Override
                public UseTypeDescription getUseTypeDescription(UseType resolvedUseType, Language resolvedLanguage,
                        EntityPermission entityPermission) {
                    assertSame(useType, resolvedUseType);
                    assertSame(language, resolvedLanguage);
                    descriptionLookups++;
                    descriptionPermission = entityPermission;
                    return "description".equals(missingEntity) ? null : description;
                }
            };
            languageLogic = new LanguageLogic() {
                @Override
                public Language getLanguageByName(ExecutionErrorAccumulator eea, String languageIsoName) {
                    assertEquals("en", languageIsoName);
                    languageLookups++;
                    if("language".equals(missingEntity)) {
                        TestLogic.this.handleExecutionError(UnknownLanguageIsoNameException.class, eea,
                                ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
                    }
                    return "language".equals(missingEntity) ? null : language;
                }
            };
        }

        @Override
        public void handleExecutionError(Class<? extends BaseException> exceptionClass, ExecutionErrorAccumulator eea,
                String key, Object... values) {
            if(eea == null) {
                // Avoid server-backed message localization while checking the chosen exception and null-accumulator path.
                BaseException exception;
                try {
                    exception = exceptionClass.getConstructor(String.class).newInstance(key);
                } catch(ReflectiveOperationException reflectionException) {
                    throw new LinkageError(reflectionException.getMessage(), reflectionException);
                }
                throw exception;
            } else {
                super.handleExecutionError(exceptionClass, eea, key, values);
            }
        }
    }

    @ParameterizedTest
    @EnumSource(EntityPermission.class)
    public void resolvesDescriptionWithRequestedPermissionAndReadOnlyParents(EntityPermission permission) {
        var logic = new TestLogic(null);
        var spec = OfferSpecFactory.getUseTypeDescriptionSpec();
        spec.setUseTypeName("TYPE");
        spec.setLanguageIsoName("en");
        var errors = new DummyExecutionErrorAccumulator();

        assertSame(logic.description, logic.getUseTypeDescriptionBySpec(errors, spec, permission));
        assertEquals(permission, logic.descriptionPermission);
        assertFalse(errors.hasExecutionErrors());
    }

    @ParameterizedTest
    @ValueSource(strings = { "type", "language", "description" })
    public void missingEntitiesAccumulateCompleteKeysAndStopDependentLookups(String missingEntity) {
        var logic = new TestLogic(missingEntity);
        var spec = OfferSpecFactory.getUseTypeDescriptionSpec();
        spec.setUseTypeName("TYPE");
        spec.setLanguageIsoName("en");
        var errors = new DummyExecutionErrorAccumulator();

        assertNull(logic.getUseTypeDescriptionBySpec(errors, spec, EntityPermission.READ_WRITE));
        var error = errors.getExecutionErrors().get(Messages.EXECUTION_ERROR).next();
        assertEquals(switch(missingEntity) {
            case "type" -> "UnknownUseTypeName";
            case "language" -> "UnknownLanguageIsoName";
            default -> "UnknownUseTypeDescription";
        }, error.getKey());
        assertArrayEquals(switch(missingEntity) {
            case "type" -> new Object[] { "TYPE" };
            case "language" -> new Object[] { "en" };
            default -> new Object[] { "TYPE", "en" };
        }, error.getValues());
        assertEquals("type".equals(missingEntity) ? 0 : 1, logic.languageLookups);
        assertEquals("description".equals(missingEntity) ? 1 : 0, logic.descriptionLookups);
    }

    @ParameterizedTest
    @ValueSource(strings = { "type", "language", "description", "none" })
    public void nullAccumulatorThrowsSpecificExceptionsOrReturnsDescription(String missingEntity) {
        var logic = new TestLogic(missingEntity);
        var spec = OfferSpecFactory.getUseTypeDescriptionSpec();
        spec.setUseTypeName("TYPE");
        spec.setLanguageIsoName("en");

        if("none".equals(missingEntity)) {
            assertSame(logic.description, logic.getUseTypeDescriptionBySpec(null, spec, EntityPermission.READ_ONLY));
        } else {
            Class<? extends RuntimeException> exceptionClass = switch(missingEntity) {
                case "type" -> UnknownUseTypeNameException.class;
                case "language" -> UnknownLanguageIsoNameException.class;
                default -> UnknownUseTypeDescriptionException.class;
            };
            assertThrows(exceptionClass, () -> logic.getUseTypeDescriptionBySpec(null, spec, EntityPermission.READ_ONLY));
        }
    }

}
