// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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

package com.echothree.ui.cli.dataloader.util.hts;

import com.echothree.control.user.geo.common.GeoService;
import com.echothree.control.user.item.common.ItemService;
import com.echothree.control.user.item.common.edit.HarmonizedTariffScheduleCodeEdit;
import com.echothree.control.user.item.common.form.CreateHarmonizedTariffScheduleCodeForm;
import com.echothree.control.user.item.common.form.CreateHarmonizedTariffScheduleCodeUseForm;
import com.echothree.control.user.item.common.form.DeleteHarmonizedTariffScheduleCodeUseForm;
import com.echothree.control.user.item.common.form.EditHarmonizedTariffScheduleCodeForm;
import com.echothree.control.user.item.common.form.EditHarmonizedTariffScheduleCodeTranslationForm;
import com.echothree.control.user.item.common.form.GetHarmonizedTariffScheduleCodeTranslationForm;
import com.echothree.control.user.item.common.form.GetHarmonizedTariffScheduleCodeUsesForm;
import com.echothree.control.user.item.common.form.ItemFormFactory;
import com.echothree.control.user.item.common.result.EditHarmonizedTariffScheduleCodeResult;
import com.echothree.control.user.item.common.result.EditHarmonizedTariffScheduleCodeTranslationResult;
import com.echothree.control.user.item.common.result.GetHarmonizedTariffScheduleCodeTranslationResult;
import com.echothree.control.user.item.common.result.GetHarmonizedTariffScheduleCodeUsesResult;
import com.echothree.control.user.item.common.result.GetHarmonizedTariffScheduleCodesResult;
import com.echothree.control.user.item.common.spec.HarmonizedTariffScheduleCodeSpec;
import com.echothree.control.user.item.common.spec.ItemSpecFactory;
import com.echothree.model.control.geo.common.GeoConstants;
import com.echothree.model.control.geo.common.transfer.CountryTransfer;
import com.echothree.model.control.item.common.ItemOptions;
import com.echothree.model.control.item.common.transfer.HarmonizedTariffScheduleCodeTransfer;
import com.echothree.model.control.item.common.HarmonizedTariffScheduleCodeUseTypes;
import com.echothree.model.control.party.common.Languages;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.string.StringUtils;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class HtsCountryParser<H> {
    
    protected UserVisitPK userVisitPK;
    protected GeoService geoService;
    protected ItemService itemService;
    protected CountryTransfer country;
    protected String countryName;
    
    Map<String, HarmonizedTariffScheduleCodeTransfer> existingCodes;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public void execute(UserVisitPK userVisitPK, GeoService geoService, ItemService itemService, CountryTransfer country)
            throws IOException {
        this.userVisitPK = userVisitPK;
        this.geoService = geoService;
        this.itemService = itemService;
        this.country = country;
        
        countryName = country.getGeoCodeAliases().getMap().get(GeoConstants.GeoCodeAliasType_COUNTRY_NAME).getAlias();
    }
    
    public Map<String, HarmonizedTariffScheduleCodeTransfer> convertHtsListToMap(List<HarmonizedTariffScheduleCodeTransfer> harmonizedTariffScheduleCodes) {
        Map<String, HarmonizedTariffScheduleCodeTransfer> htsMap = new HashMap<>(harmonizedTariffScheduleCodes.size());
        
        harmonizedTariffScheduleCodes.forEach((harmonizedTariffScheduleCode) -> {
            htsMap.put(harmonizedTariffScheduleCode.getHarmonizedTariffScheduleCodeName(), harmonizedTariffScheduleCode);
        });
        
        return htsMap;
    }
    
    public List<HarmonizedTariffScheduleCodeTransfer> getHarmonizedTariffScheduleCodes(String countryName) {
        var commandForm = ItemFormFactory.getGetHarmonizedTariffScheduleCodesForm();
        List<HarmonizedTariffScheduleCodeTransfer> harmonizedTariffScheduleCodes = null;
        
        commandForm.setCountryName(countryName);
        
        Set<String> options = new HashSet<>();
        options.add(ItemOptions.HarmonizedTariffScheduleCodeIncludeHarmonizedTariffScheduleCodeUses);
        commandForm.setOptions(options);

        var commandResult = itemService.getHarmonizedTariffScheduleCodes(userVisitPK, commandForm);
        if(!commandResult.hasErrors()) {
            var executionResult = commandResult.getExecutionResult();
            var result = (GetHarmonizedTariffScheduleCodesResult)executionResult.getResult();
            
            harmonizedTariffScheduleCodes = result.getHarmonizedTariffScheduleCodes();
        } else {
            logger.error(commandResult.toString());
        }
        
        return harmonizedTariffScheduleCodes;
    }
    
    public void determineAndApplyChanges(Map<String, H> importCodes, Map<String, H> exportCodes) {
        Map<String, H> uniqueCodes = new HashMap<>();
        uniqueCodes.putAll(importCodes);
        uniqueCodes.putAll(exportCodes);
        logger.info("{} unique code{}", uniqueCodes.size(), uniqueCodes.size() == 1 ? "" : "s");
        
        existingCodes = convertHtsListToMap(getHarmonizedTariffScheduleCodes(countryName));
        logger.info("{} existing code{}", existingCodes.size(), existingCodes.size() == 1 ? "" : "s");
        
        Set<String> codesToCreate = new HashSet<>();
        codesToCreate.addAll(uniqueCodes.keySet());
        codesToCreate.removeAll(existingCodes.keySet());
        logger.info("{} code{} to create", codesToCreate.size(), codesToCreate.size() == 1 ? "" : "s");
        
        Set<String> codesToDelete = new HashSet<>();
        codesToDelete.addAll(existingCodes.keySet());
        codesToDelete.removeAll(uniqueCodes.keySet());
        logger.info("{} code{} to delete", codesToDelete.size(), codesToDelete.size() == 1 ? "" : "s");
        
        Set<String> codesToCheck = new HashSet<>();
        codesToCheck.addAll(uniqueCodes.keySet());
        codesToCheck.retainAll(existingCodes.keySet());
        logger.info("{} code{} to check", codesToCheck.size(), codesToCheck.size() == 1 ? "" : "s");
        
        createCodes(codesToCreate, importCodes, exportCodes, uniqueCodes);
        deleteCodes(codesToDelete);
        checkCodes(codesToCheck, importCodes, exportCodes, uniqueCodes);
    }
    
    public void transferToEdit(HarmonizedTariffScheduleCodeEdit edit, H htsc) {
        edit.setHarmonizedTariffScheduleCodeName(getHarmonizedTariffScheduleCodeName(htsc));
        edit.setFirstHarmonizedTariffScheduleCodeUnitName(getFirstHarmonizedTariffScheduleCodeUnitName(htsc));
        edit.setSecondHarmonizedTariffScheduleCodeUnitName(getSecondHarmonizedTariffScheduleCodeUnitName(htsc));
        edit.setIsDefault(String.valueOf(false));
        edit.setSortOrder("1");
        edit.setDescription(getDescription(htsc));
        edit.setOverviewMimeTypeName(getOverviewMimeTypeName(htsc));
        edit.setOverview(getOverview(htsc));
    }

    
    public abstract Set<String> getHarmonizedTariffScheduleCodeUses(Map<String, H> importCodes, Map<String, H> exportCodes, H htsc);

    private void createUses(CreateHarmonizedTariffScheduleCodeForm createHarmonizedTariffScheduleCodeForm, Set<String> harmonizedTariffScheduleCodeUseNames) {
        harmonizedTariffScheduleCodeUseNames.stream().map((harmonizedTariffScheduleCodeUseName) -> {
            var commandForm = ItemFormFactory.getCreateHarmonizedTariffScheduleCodeUseForm();
            commandForm.setCountryName(createHarmonizedTariffScheduleCodeForm.getCountryName());
            commandForm.setHarmonizedTariffScheduleCodeName(createHarmonizedTariffScheduleCodeForm.getHarmonizedTariffScheduleCodeName());
            commandForm.setHarmonizedTariffScheduleCodeUseTypeName(harmonizedTariffScheduleCodeUseName);
            return commandForm;
        }).map((commandForm) -> itemService.createHarmonizedTariffScheduleCodeUse(userVisitPK, commandForm)).filter((commandResult) -> commandResult.hasErrors()).forEach((commandResult) -> {
            logger.error(commandResult.toString());
        });
    }
    
    public void createCodes(Set<String> codesToCreate, Map<String, H> importCodes, Map<String, H> exportCodes, Map<String, H> uniqueCodes) {
        var commandForm = ItemFormFactory.getCreateHarmonizedTariffScheduleCodeForm();

        codesToCreate.stream().map((harmonizedTariffScheduleCode) -> uniqueCodes.get(harmonizedTariffScheduleCode)).map((htsc) -> {
            commandForm.reset();
            commandForm.setCountryName(countryName);
            transferToEdit(commandForm, htsc);
            return htsc;            
        }).forEach((htsc) -> {
            var commandResult = itemService.createHarmonizedTariffScheduleCode(userVisitPK, commandForm);

            if(commandResult.hasErrors()) {
                logger.error(commandResult.toString());
            } else {
                createUses(commandForm, getHarmonizedTariffScheduleCodeUses(importCodes, exportCodes, htsc));
            }
        });
    }
    
    public void deleteCodes(Set<String> codesToDelete) {
        var commandForm = ItemFormFactory.getDeleteHarmonizedTariffScheduleCodeForm();
        
        commandForm.setCountryName(countryName);

        codesToDelete.stream().map((harmonizedTariffScheduleCodeName) -> {
            commandForm.setHarmonizedTariffScheduleCodeName(harmonizedTariffScheduleCodeName);
            return harmonizedTariffScheduleCodeName;
        }).map((_item) -> itemService.deleteHarmonizedTariffScheduleCode(userVisitPK, commandForm)).filter((commandResult) -> commandResult.hasErrors()).forEach((commandResult) -> {
            logger.error(commandResult.toString());
        });
    }
    
    // Must return a String, null is not permitted.
    public abstract String getHarmonizedTariffScheduleCodeName(H htsc);
    
    // Must return a String, null is not permitted.
    public abstract String getFirstHarmonizedTariffScheduleCodeUnitName(H htsc);
    
    // Must return a String, null is not permitted.
    public abstract String getSecondHarmonizedTariffScheduleCodeUnitName(H htsc);
    
    // Must return a String, null is not permitted.
    public abstract String getDescription(H htsc);
    
    // Must return a String, null is not permitted.
    public abstract String getOverviewMimeTypeName(H htsc);
    
    // Must return a String, null is not permitted.
    public abstract String getOverview(H htsc);
    
    private void checkCodeTranslation(GetHarmonizedTariffScheduleCodeTranslationForm getCodeTranslationForm, String code, Map<String, H> uniqueCodes,
            EditHarmonizedTariffScheduleCodeTranslationForm editCodeTranslationForm) {
        getCodeTranslationForm.reset();
        getCodeTranslationForm.setCountryName(countryName);
        getCodeTranslationForm.setHarmonizedTariffScheduleCodeName(code);
        getCodeTranslationForm.setLanguageIsoName(Languages.en.name());

        var commandResult = itemService.getHarmonizedTariffScheduleCodeTranslation(userVisitPK, getCodeTranslationForm);
        if(commandResult.hasErrors()) {
            logger.error(commandResult.toString());
        } else {
            var executionResult = commandResult.getExecutionResult();
            var editResult = (GetHarmonizedTariffScheduleCodeTranslationResult)executionResult.getResult();
            var harmonizedTariffScheduleCodeTranslation = editResult.getHarmonizedTariffScheduleCodeTranslation();
            var htsc = uniqueCodes.get(code);
            var description = getDescription(htsc);
            var overview = getOverview(htsc);
            var overviewMimeTypeName = getOverviewMimeTypeName(htsc);
            var translationDifferent = !description.equals(harmonizedTariffScheduleCodeTranslation.getDescription())
                    || !overview.equals(harmonizedTariffScheduleCodeTranslation.getOverview())
                    || !overviewMimeTypeName.equals(harmonizedTariffScheduleCodeTranslation.getOverviewMimeType().getMimeTypeName());
            
            if(translationDifferent) {
                editCodeTranslationForm.reset();
                editCodeTranslationForm.setEditMode(EditMode.LOCK);
                editCodeTranslationForm.setSpec(getCodeTranslationForm);
                
                commandResult = itemService.editHarmonizedTariffScheduleCodeTranslation(userVisitPK, editCodeTranslationForm);

                if(!commandResult.hasErrors()) {
                    executionResult = commandResult.getExecutionResult();
                    var editTranslationResult = (EditHarmonizedTariffScheduleCodeTranslationResult)executionResult.getResult();
                    var edit = editTranslationResult.getEdit();

                    edit.setDescription(StringUtils.getInstance().left(description, 80));
                    edit.setOverview(overview);
                    edit.setOverviewMimeTypeName(overviewMimeTypeName);
                    
                    editCodeTranslationForm.setEditMode(EditMode.UPDATE);
                    editCodeTranslationForm.setEdit(edit);

                    commandResult = itemService.editHarmonizedTariffScheduleCodeTranslation(userVisitPK, editCodeTranslationForm);

                    if(commandResult.hasErrors()) {
                        logger.error("  editHarmonizedTariffScheduleCodeTranslation update failed for {}: {}", code, commandResult);
                    }
                } else {
                    logger.error("  editHarmonizedTariffScheduleCodeTranslation lock failed for {}: {}", code, commandResult);
                }
            }
        }
    }
    
    private void createOrDeleteCodeUse(String code, Map<String, H> importCodes, boolean isCurrentlyUsed, String harmonizedTariffScheduleCodeUseTypeName,
            CreateHarmonizedTariffScheduleCodeUseForm createCodeUseForm, DeleteHarmonizedTariffScheduleCodeUseForm deleteCodeUseForm) {
        if(importCodes.containsKey(code)) {
            if(!isCurrentlyUsed) {
                createCodeUseForm.reset();
                createCodeUseForm.setCountryName(countryName);
                createCodeUseForm.setHarmonizedTariffScheduleCodeName(code);
                createCodeUseForm.setHarmonizedTariffScheduleCodeUseTypeName(harmonizedTariffScheduleCodeUseTypeName);

                var commandResult = itemService.createHarmonizedTariffScheduleCodeUse(userVisitPK, createCodeUseForm);
                if(commandResult.hasErrors()) {
                    logger.error(commandResult.toString());
                }
            }
        } else if(isCurrentlyUsed) {
            deleteCodeUseForm.reset();
            deleteCodeUseForm.setCountryName(countryName);
            deleteCodeUseForm.setHarmonizedTariffScheduleCodeName(code);
            deleteCodeUseForm.setHarmonizedTariffScheduleCodeUseTypeName(harmonizedTariffScheduleCodeUseTypeName);

            var commandResult = itemService.deleteHarmonizedTariffScheduleCodeUse(userVisitPK, deleteCodeUseForm);
            if(commandResult.hasErrors()) {
                logger.error(commandResult.toString());
            }
        }
    }

    private void checkCodeUses(GetHarmonizedTariffScheduleCodeUsesForm getCodeUsesForm, String code, Map<String, H> importCodes,
            CreateHarmonizedTariffScheduleCodeUseForm createCodeUseForm, DeleteHarmonizedTariffScheduleCodeUseForm deleteCodeUseForm,
            Map<String, H> exportCodes) {
        getCodeUsesForm.reset();
        getCodeUsesForm.setCountryName(countryName);
        getCodeUsesForm.setHarmonizedTariffScheduleCodeName(code);

        var commandResult = itemService.getHarmonizedTariffScheduleCodeUses(userVisitPK, getCodeUsesForm);
        if(commandResult.hasErrors()) {
            logger.error(commandResult.toString());
        } else {
            var executionResult = commandResult.getExecutionResult();
            var editResult = (GetHarmonizedTariffScheduleCodeUsesResult)executionResult.getResult();
            var harmonizedTariffScheduleCodeUses = editResult.getHarmonizedTariffScheduleCodeUses();
            var isCurrentlyUsedForImport = false;
            var isCurrentlyUsedForExport = false;
            
            for(var harmonizedTariffScheduleCodeUse : harmonizedTariffScheduleCodeUses) {
                if(harmonizedTariffScheduleCodeUse.getHarmonizedTariffScheduleCodeUseType().getHarmonizedTariffScheduleCodeUseTypeName().equals(HarmonizedTariffScheduleCodeUseTypes.IMPORT.name())) {
                    isCurrentlyUsedForImport = true;
                } else if(harmonizedTariffScheduleCodeUse.getHarmonizedTariffScheduleCodeUseType().getHarmonizedTariffScheduleCodeUseTypeName().equals(HarmonizedTariffScheduleCodeUseTypes.EXPORT.name())) {
                    isCurrentlyUsedForExport = true;
                }
            }
            
            createOrDeleteCodeUse(code, importCodes, isCurrentlyUsedForImport, HarmonizedTariffScheduleCodeUseTypes.IMPORT.name(), createCodeUseForm, deleteCodeUseForm);
            createOrDeleteCodeUse(code, exportCodes, isCurrentlyUsedForExport, HarmonizedTariffScheduleCodeUseTypes.EXPORT.name(), createCodeUseForm, deleteCodeUseForm);
        }
    }
    
    private void checkCode(HarmonizedTariffScheduleCodeSpec harmonizedTariffScheduleCodeSpec, EditHarmonizedTariffScheduleCodeForm editCodeForm,
            GetHarmonizedTariffScheduleCodeUsesForm getCodeUsesForm, String code, Map<String, H> importCodes,
            Map<String, H> exportCodes, Map<String, H> uniqueCodes, CreateHarmonizedTariffScheduleCodeUseForm createCodeUseForm,
            DeleteHarmonizedTariffScheduleCodeUseForm deleteCodeUseForm) {
        var harmonizedTariffScheduleCode = existingCodes.get(code);
        var htsc = uniqueCodes.get(code);
        var firstHarmonizedTariffScheduleCodeUnitName = getFirstHarmonizedTariffScheduleCodeUnitName(htsc);
        var secondHarmonizedTariffScheduleCodeUnitName = getSecondHarmonizedTariffScheduleCodeUnitName(htsc);
        var currentFirstHarmonizedTariffScheduleCodeUnit = harmonizedTariffScheduleCode.getFirstHarmonizedTariffScheduleCodeUnit();
        var currentFirstHarmonizedTariffScheduleCodeUnitName = currentFirstHarmonizedTariffScheduleCodeUnit == null ? null
                : currentFirstHarmonizedTariffScheduleCodeUnit.getHarmonizedTariffScheduleCodeUnitName();
        var currentSecondHarmonizedTariffScheduleCodeUnit = harmonizedTariffScheduleCode.getSecondHarmonizedTariffScheduleCodeUnit();
        var currentSecondHarmonizedTariffScheduleCodeUnitName = currentSecondHarmonizedTariffScheduleCodeUnit == null ? null
                : currentSecondHarmonizedTariffScheduleCodeUnit.getHarmonizedTariffScheduleCodeUnitName();

        var codeDifferent = StringUtils.getInstance().nullSafeCompareTo(firstHarmonizedTariffScheduleCodeUnitName, currentFirstHarmonizedTariffScheduleCodeUnitName) != 0
                || StringUtils.getInstance().nullSafeCompareTo(secondHarmonizedTariffScheduleCodeUnitName, currentSecondHarmonizedTariffScheduleCodeUnitName) != 0;

        if(codeDifferent) {
            harmonizedTariffScheduleCodeSpec.reset();
            harmonizedTariffScheduleCodeSpec.setCountryName(countryName);
            harmonizedTariffScheduleCodeSpec.setHarmonizedTariffScheduleCodeName(code);
            
            editCodeForm.reset();
            editCodeForm.setEditMode(EditMode.LOCK);
            editCodeForm.setSpec(harmonizedTariffScheduleCodeSpec);

            var commandResult = itemService.editHarmonizedTariffScheduleCode(userVisitPK, editCodeForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var editTranslationResult = (EditHarmonizedTariffScheduleCodeResult)executionResult.getResult();
                var edit = editTranslationResult.getEdit();

                edit.setFirstHarmonizedTariffScheduleCodeUnitName(firstHarmonizedTariffScheduleCodeUnitName);
                edit.setSecondHarmonizedTariffScheduleCodeUnitName(secondHarmonizedTariffScheduleCodeUnitName);

                editCodeForm.setEditMode(EditMode.UPDATE);
                editCodeForm.setEdit(edit);

                commandResult = itemService.editHarmonizedTariffScheduleCode(userVisitPK, editCodeForm);

                if(commandResult.hasErrors()) {
                    logger.error("  editHarmonizedTariffScheduleCode update failed for {}: {}", code, commandResult);
                }
            } else {
                logger.error("  editHarmonizedTariffScheduleCode lock failed for {}: {}", code, commandResult);
            }
        }
        
        checkCodeUses(getCodeUsesForm, code, importCodes, createCodeUseForm, deleteCodeUseForm, exportCodes);
    }
    
    public void checkCodes(Set<String> codesToCheck, Map<String, H> importCodes, Map<String, H> exportCodes, Map<String, H> uniqueCodes) {
        var harmonizedTariffScheduleCodeSpec = ItemSpecFactory.getHarmonizedTariffScheduleCodeSpec();
        var editCodeForm = ItemFormFactory.getEditHarmonizedTariffScheduleCodeForm();
        var getCodeUsesForm = ItemFormFactory.getGetHarmonizedTariffScheduleCodeUsesForm();
        var createCodeUseForm = ItemFormFactory.getCreateHarmonizedTariffScheduleCodeUseForm();
        var deleteCodeUseForm = ItemFormFactory.getDeleteHarmonizedTariffScheduleCodeUseForm();
        var getCodeTranslationForm = ItemFormFactory.getGetHarmonizedTariffScheduleCodeTranslationForm();
        var editCodeTranslationForm = ItemFormFactory.getEditHarmonizedTariffScheduleCodeTranslationForm();
        
        codesToCheck.stream().map((code) -> {
            checkCode(harmonizedTariffScheduleCodeSpec, editCodeForm, getCodeUsesForm, code, importCodes, exportCodes, uniqueCodes, createCodeUseForm,
                    deleteCodeUseForm);        
            return code;
        }).forEach((code) -> {
            checkCodeTranslation(getCodeTranslationForm, code, uniqueCodes, editCodeTranslationForm);
        });
    }

}
