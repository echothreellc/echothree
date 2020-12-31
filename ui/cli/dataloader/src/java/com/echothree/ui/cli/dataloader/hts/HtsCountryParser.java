// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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

package com.echothree.ui.cli.dataloader.hts;

import com.echothree.control.user.geo.common.GeoService;
import com.echothree.control.user.item.common.ItemService;
import com.echothree.control.user.item.common.edit.HarmonizedTariffScheduleCodeEdit;
import com.echothree.control.user.item.common.edit.HarmonizedTariffScheduleCodeTranslationEdit;
import com.echothree.control.user.item.common.form.CreateHarmonizedTariffScheduleCodeForm;
import com.echothree.control.user.item.common.form.CreateHarmonizedTariffScheduleCodeUseForm;
import com.echothree.control.user.item.common.form.DeleteHarmonizedTariffScheduleCodeForm;
import com.echothree.control.user.item.common.form.DeleteHarmonizedTariffScheduleCodeUseForm;
import com.echothree.control.user.item.common.form.EditHarmonizedTariffScheduleCodeForm;
import com.echothree.control.user.item.common.form.EditHarmonizedTariffScheduleCodeTranslationForm;
import com.echothree.control.user.item.common.form.GetHarmonizedTariffScheduleCodeTranslationForm;
import com.echothree.control.user.item.common.form.GetHarmonizedTariffScheduleCodeUsesForm;
import com.echothree.control.user.item.common.form.GetHarmonizedTariffScheduleCodesForm;
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
import com.echothree.model.control.item.common.ItemConstants;
import com.echothree.model.control.item.common.ItemOptions;
import com.echothree.model.control.item.common.transfer.HarmonizedTariffScheduleCodeTransfer;
import com.echothree.model.control.item.common.transfer.HarmonizedTariffScheduleCodeTranslationTransfer;
import com.echothree.model.control.item.common.transfer.HarmonizedTariffScheduleCodeUnitTransfer;
import com.echothree.model.control.item.common.transfer.HarmonizedTariffScheduleCodeUseTransfer;
import com.echothree.model.control.party.common.Languages;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.command.ExecutionResult;
import com.echothree.util.common.string.StringUtils;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class HtsCountryParser<H extends Object> {
    
    protected UserVisitPK userVisitPK;
    protected GeoService geoService;
    protected ItemService itemService;
    protected File htsDirectory;
    protected CountryTransfer country;
    protected String countryName;
    
    Map<String, HarmonizedTariffScheduleCodeTransfer> existingCodes;
    
    private Log log = LogFactory.getLog(this.getClass());
    
    public void execute(UserVisitPK userVisitPK, GeoService geoService, ItemService itemService, File htsDirectory, CountryTransfer country)
            throws IOException {
        this.userVisitPK = userVisitPK;
        this.geoService = geoService;
        this.itemService = itemService;
        this.htsDirectory = htsDirectory;
        this.country = country;
        
        countryName = country.getGeoCodeAliases().getMap().get(GeoConstants.GeoCodeAliasType_COUNTRY_NAME).getAlias();
    }
    
    public Map<String, HarmonizedTariffScheduleCodeTransfer> convertHtsListToMap(List<HarmonizedTariffScheduleCodeTransfer> harmonizedTariffScheduleCodes) {
        Map<String, HarmonizedTariffScheduleCodeTransfer> htsMap = new HashMap<>(harmonizedTariffScheduleCodes.size());
        
        harmonizedTariffScheduleCodes.stream().forEach((harmonizedTariffScheduleCode) -> {
            htsMap.put(harmonizedTariffScheduleCode.getHarmonizedTariffScheduleCodeName(), harmonizedTariffScheduleCode);
        });
        
        return htsMap;
    }
    
    public List<HarmonizedTariffScheduleCodeTransfer> getHarmonizedTariffScheduleCodes(String countryName) {
        GetHarmonizedTariffScheduleCodesForm commandForm = ItemFormFactory.getGetHarmonizedTariffScheduleCodesForm();
        List<HarmonizedTariffScheduleCodeTransfer> harmonizedTariffScheduleCodes = null;
        
        commandForm.setCountryName(countryName);
        
        Set<String> options = new HashSet<>();
        options.add(ItemOptions.HarmonizedTariffScheduleCodeIncludeHarmonizedTariffScheduleCodeUses);
        commandForm.setOptions(options);

        CommandResult commandResult = itemService.getHarmonizedTariffScheduleCodes(userVisitPK, commandForm);
        if(!commandResult.hasErrors()) {
            ExecutionResult executionResult = commandResult.getExecutionResult();
            GetHarmonizedTariffScheduleCodesResult result = (GetHarmonizedTariffScheduleCodesResult)executionResult.getResult();
            
            harmonizedTariffScheduleCodes = result.getHarmonizedTariffScheduleCodes();
        } else {
            log.error(commandResult);
        }
        
        return harmonizedTariffScheduleCodes;
    }
    
    public void determineAndApplyChanges(Map<String, H> importCodes, Map<String, H> exportCodes) {
        Map<String, H> uniqueCodes = new HashMap<>();
        uniqueCodes.putAll(importCodes);
        uniqueCodes.putAll(exportCodes);
        log.info(uniqueCodes.size() + " unique code" + (uniqueCodes.size() == 1 ? "" : "s"));
        
        existingCodes = convertHtsListToMap(getHarmonizedTariffScheduleCodes(countryName));
        log.info(existingCodes.size() + " existing code" + (existingCodes.size() == 1 ? "" : "s"));
        
        Set<String> codesToCreate = new HashSet<>();
        codesToCreate.addAll(uniqueCodes.keySet());
        codesToCreate.removeAll(existingCodes.keySet());
        log.info(codesToCreate.size() + " code" + (codesToCreate.size() == 1 ? "" : "s") + " to create");
        
        Set<String> codesToDelete = new HashSet<>();
        codesToDelete.addAll(existingCodes.keySet());
        codesToDelete.removeAll(uniqueCodes.keySet());
        log.info(codesToDelete.size() + " code" + (codesToDelete.size() == 1 ? "" : "s") + " to delete");
        
        Set<String> codesToCheck = new HashSet<>();
        codesToCheck.addAll(uniqueCodes.keySet());
        codesToCheck.retainAll(existingCodes.keySet());
        log.info(codesToCheck.size() + " code" + (codesToCheck.size() == 1 ? "" : "s") + " to check");
        
        createCodes(codesToCreate, importCodes, exportCodes, uniqueCodes);
        deleteCodes(codesToDelete);
        checkCodes(codesToCheck, importCodes, exportCodes, uniqueCodes);
    }
    
    public void transferToEdit(HarmonizedTariffScheduleCodeEdit edit, H htsc) {
        edit.setHarmonizedTariffScheduleCodeName(getHarmonizedTariffScheduleCodeName(htsc));
        edit.setFirstHarmonizedTariffScheduleCodeUnitName(getFirstHarmonizedTariffScheduleCodeUnitName(htsc));
        edit.setSecondHarmonizedTariffScheduleCodeUnitName(getSecondHarmonizedTariffScheduleCodeUnitName(htsc));
        edit.setIsDefault(Boolean.FALSE.toString());
        edit.setSortOrder("1");
        edit.setDescription(getDescription(htsc));
        edit.setOverviewMimeTypeName(getOverviewMimeTypeName(htsc));
        edit.setOverview(getOverview(htsc));
    }

    
    public abstract Set<String> getHarmonizedTariffScheduleCodeUses(Map<String, H> importCodes, Map<String, H> exportCodes, H htsc);

    private void createUses(CreateHarmonizedTariffScheduleCodeForm createHarmonizedTariffScheduleCodeForm, Set<String> harmonizedTariffScheduleCodeUseNames) {
        harmonizedTariffScheduleCodeUseNames.stream().map((harmonizedTariffScheduleCodeUseName) -> {
            CreateHarmonizedTariffScheduleCodeUseForm commandForm = ItemFormFactory.getCreateHarmonizedTariffScheduleCodeUseForm();
            commandForm.setCountryName(createHarmonizedTariffScheduleCodeForm.getCountryName());
            commandForm.setHarmonizedTariffScheduleCodeName(createHarmonizedTariffScheduleCodeForm.getHarmonizedTariffScheduleCodeName());
            commandForm.setHarmonizedTariffScheduleCodeUseTypeName(harmonizedTariffScheduleCodeUseName);
            return commandForm;
        }).map((commandForm) -> itemService.createHarmonizedTariffScheduleCodeUse(userVisitPK, commandForm)).filter((commandResult) -> commandResult.hasErrors()).forEach((commandResult) -> {
            log.error(commandResult);
        });
    }
    
    public void createCodes(Set<String> codesToCreate, Map<String, H> importCodes, Map<String, H> exportCodes, Map<String, H> uniqueCodes) {
        CreateHarmonizedTariffScheduleCodeForm commandForm = ItemFormFactory.getCreateHarmonizedTariffScheduleCodeForm();

        codesToCreate.stream().map((harmonizedTariffScheduleCode) -> uniqueCodes.get(harmonizedTariffScheduleCode)).map((htsc) -> {
            commandForm.reset();
            commandForm.setCountryName(countryName);
            transferToEdit(commandForm, htsc);
            return htsc;            
        }).forEach((htsc) -> {
            CommandResult commandResult = itemService.createHarmonizedTariffScheduleCode(userVisitPK, commandForm);

            if(commandResult.hasErrors()) {
                log.error(commandResult);
            } else {
                createUses(commandForm, getHarmonizedTariffScheduleCodeUses(importCodes, exportCodes, htsc));
            }
        });
    }
    
    public void deleteCodes(Set<String> codesToDelete) {
        DeleteHarmonizedTariffScheduleCodeForm commandForm = ItemFormFactory.getDeleteHarmonizedTariffScheduleCodeForm();
        
        commandForm.setCountryName(countryName);

        codesToDelete.stream().map((harmonizedTariffScheduleCodeName) -> {
            commandForm.setHarmonizedTariffScheduleCodeName(harmonizedTariffScheduleCodeName);
            return harmonizedTariffScheduleCodeName;
        }).map((_item) -> itemService.deleteHarmonizedTariffScheduleCode(userVisitPK, commandForm)).filter((commandResult) -> commandResult.hasErrors()).forEach((commandResult) -> {
            log.error(commandResult);
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
        
        CommandResult commandResult = itemService.getHarmonizedTariffScheduleCodeTranslation(userVisitPK, getCodeTranslationForm);
        if(commandResult.hasErrors()) {
            log.error(commandResult);
        } else {
            ExecutionResult executionResult = commandResult.getExecutionResult();
            GetHarmonizedTariffScheduleCodeTranslationResult editResult = (GetHarmonizedTariffScheduleCodeTranslationResult)executionResult.getResult();
            HarmonizedTariffScheduleCodeTranslationTransfer harmonizedTariffScheduleCodeTranslation = editResult.getHarmonizedTariffScheduleCodeTranslation();
            H htsc = uniqueCodes.get(code);
            String description = getDescription(htsc);
            String overview = getOverview(htsc);
            String overviewMimeTypeName = getOverviewMimeTypeName(htsc);
            boolean translationDifferent = !description.equals(harmonizedTariffScheduleCodeTranslation.getDescription())
                    || !overview.equals(harmonizedTariffScheduleCodeTranslation.getOverview())
                    || !overviewMimeTypeName.equals(harmonizedTariffScheduleCodeTranslation.getOverviewMimeType().getMimeTypeName());
            
            if(translationDifferent) {
                editCodeTranslationForm.reset();
                editCodeTranslationForm.setEditMode(EditMode.LOCK);
                editCodeTranslationForm.setSpec(getCodeTranslationForm);
                
                commandResult = itemService.editHarmonizedTariffScheduleCodeTranslation(userVisitPK, editCodeTranslationForm);

                if(!commandResult.hasErrors()) {
                    executionResult = commandResult.getExecutionResult();
                    EditHarmonizedTariffScheduleCodeTranslationResult editTranslationResult = (EditHarmonizedTariffScheduleCodeTranslationResult)executionResult.getResult();
                    HarmonizedTariffScheduleCodeTranslationEdit edit = editTranslationResult.getEdit();

                    edit.setDescription(StringUtils.getInstance().left(description, 80));
                    edit.setOverview(overview);
                    edit.setOverviewMimeTypeName(overviewMimeTypeName);
                    
                    editCodeTranslationForm.setEditMode(EditMode.UPDATE);
                    editCodeTranslationForm.setEdit(edit);

                    commandResult = itemService.editHarmonizedTariffScheduleCodeTranslation(userVisitPK, editCodeTranslationForm);

                    if(commandResult.hasErrors()) {
                        log.error("  editHarmonizedTariffScheduleCodeTranslation update failed for " + code + ": " + commandResult);
                    }
                } else {
                    log.error("  editHarmonizedTariffScheduleCodeTranslation lock failed for " + code + ": " + commandResult);
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

                CommandResult commandResult = itemService.createHarmonizedTariffScheduleCodeUse(userVisitPK, createCodeUseForm);
                if(commandResult.hasErrors()) {
                    log.error(commandResult);
                }
            }
        } else if(isCurrentlyUsed) {
            deleteCodeUseForm.reset();
            deleteCodeUseForm.setCountryName(countryName);
            deleteCodeUseForm.setHarmonizedTariffScheduleCodeName(code);
            deleteCodeUseForm.setHarmonizedTariffScheduleCodeUseTypeName(harmonizedTariffScheduleCodeUseTypeName);
            
            CommandResult commandResult = itemService.deleteHarmonizedTariffScheduleCodeUse(userVisitPK, deleteCodeUseForm);
            if(commandResult.hasErrors()) {
                log.error(commandResult);
            }
        }
    }

    private void checkCodeUses(GetHarmonizedTariffScheduleCodeUsesForm getCodeUsesForm, String code, Map<String, H> importCodes,
            CreateHarmonizedTariffScheduleCodeUseForm createCodeUseForm, DeleteHarmonizedTariffScheduleCodeUseForm deleteCodeUseForm,
            Map<String, H> exportCodes) {
        getCodeUsesForm.reset();
        getCodeUsesForm.setCountryName(countryName);
        getCodeUsesForm.setHarmonizedTariffScheduleCodeName(code);
    
        CommandResult commandResult = itemService.getHarmonizedTariffScheduleCodeUses(userVisitPK, getCodeUsesForm);
        if(commandResult.hasErrors()) {
            log.error(commandResult);
        } else {
            ExecutionResult executionResult = commandResult.getExecutionResult();
            GetHarmonizedTariffScheduleCodeUsesResult editResult = (GetHarmonizedTariffScheduleCodeUsesResult)executionResult.getResult();
            List<HarmonizedTariffScheduleCodeUseTransfer> harmonizedTariffScheduleCodeUses = editResult.getHarmonizedTariffScheduleCodeUses();
            boolean isCurrentlyUsedForImport = false;
            boolean isCurrentlyUsedForExport = false;
            
            for(HarmonizedTariffScheduleCodeUseTransfer harmonizedTariffScheduleCodeUse : harmonizedTariffScheduleCodeUses) {
                if(harmonizedTariffScheduleCodeUse.getHarmonizedTariffScheduleCodeUseType().getHarmonizedTariffScheduleCodeUseTypeName().equals(ItemConstants.HarmonizedTariffScheduleCodeUseType_IMPORT)) {
                    isCurrentlyUsedForImport = true;
                } else if(harmonizedTariffScheduleCodeUse.getHarmonizedTariffScheduleCodeUseType().getHarmonizedTariffScheduleCodeUseTypeName().equals(ItemConstants.HarmonizedTariffScheduleCodeUseType_EXPORT)) {
                    isCurrentlyUsedForExport = true;
                }
            }
            
            createOrDeleteCodeUse(code, importCodes, isCurrentlyUsedForImport, ItemConstants.HarmonizedTariffScheduleCodeUseType_IMPORT, createCodeUseForm, deleteCodeUseForm);
            createOrDeleteCodeUse(code, exportCodes, isCurrentlyUsedForExport, ItemConstants.HarmonizedTariffScheduleCodeUseType_EXPORT, createCodeUseForm, deleteCodeUseForm);
        }
    }
    
    private void checkCode(HarmonizedTariffScheduleCodeSpec harmonizedTariffScheduleCodeSpec, EditHarmonizedTariffScheduleCodeForm editCodeForm,
            GetHarmonizedTariffScheduleCodeUsesForm getCodeUsesForm, String code, Map<String, H> importCodes,
            Map<String, H> exportCodes, Map<String, H> uniqueCodes, CreateHarmonizedTariffScheduleCodeUseForm createCodeUseForm,
            DeleteHarmonizedTariffScheduleCodeUseForm deleteCodeUseForm) {
        HarmonizedTariffScheduleCodeTransfer harmonizedTariffScheduleCode = existingCodes.get(code);
        H htsc = uniqueCodes.get(code);
        String firstHarmonizedTariffScheduleCodeUnitName = getFirstHarmonizedTariffScheduleCodeUnitName(htsc);
        String secondHarmonizedTariffScheduleCodeUnitName = getSecondHarmonizedTariffScheduleCodeUnitName(htsc);
        HarmonizedTariffScheduleCodeUnitTransfer currentFirstHarmonizedTariffScheduleCodeUnit = harmonizedTariffScheduleCode.getFirstHarmonizedTariffScheduleCodeUnit();
        String currentFirstHarmonizedTariffScheduleCodeUnitName = currentFirstHarmonizedTariffScheduleCodeUnit == null ? null
                : currentFirstHarmonizedTariffScheduleCodeUnit.getHarmonizedTariffScheduleCodeUnitName();
        HarmonizedTariffScheduleCodeUnitTransfer currentSecondHarmonizedTariffScheduleCodeUnit = harmonizedTariffScheduleCode.getSecondHarmonizedTariffScheduleCodeUnit();
        String currentSecondHarmonizedTariffScheduleCodeUnitName = currentSecondHarmonizedTariffScheduleCodeUnit == null ? null
                : currentSecondHarmonizedTariffScheduleCodeUnit.getHarmonizedTariffScheduleCodeUnitName();

        boolean codeDifferent = StringUtils.getInstance().nullSafeCompareTo(firstHarmonizedTariffScheduleCodeUnitName, currentFirstHarmonizedTariffScheduleCodeUnitName) != 0
                || StringUtils.getInstance().nullSafeCompareTo(secondHarmonizedTariffScheduleCodeUnitName, currentSecondHarmonizedTariffScheduleCodeUnitName) != 0;

        if(codeDifferent) {
            harmonizedTariffScheduleCodeSpec.reset();
            harmonizedTariffScheduleCodeSpec.setCountryName(countryName);
            harmonizedTariffScheduleCodeSpec.setHarmonizedTariffScheduleCodeName(code);
            
            editCodeForm.reset();
            editCodeForm.setEditMode(EditMode.LOCK);
            editCodeForm.setSpec(harmonizedTariffScheduleCodeSpec);

            CommandResult commandResult = itemService.editHarmonizedTariffScheduleCode(userVisitPK, editCodeForm);

            if(!commandResult.hasErrors()) {
                ExecutionResult executionResult = commandResult.getExecutionResult();
                EditHarmonizedTariffScheduleCodeResult editTranslationResult = (EditHarmonizedTariffScheduleCodeResult)executionResult.getResult();
                HarmonizedTariffScheduleCodeEdit edit = editTranslationResult.getEdit();

                edit.setFirstHarmonizedTariffScheduleCodeUnitName(firstHarmonizedTariffScheduleCodeUnitName);
                edit.setSecondHarmonizedTariffScheduleCodeUnitName(secondHarmonizedTariffScheduleCodeUnitName);

                editCodeForm.setEditMode(EditMode.UPDATE);
                editCodeForm.setEdit(edit);

                commandResult = itemService.editHarmonizedTariffScheduleCode(userVisitPK, editCodeForm);

                if(commandResult.hasErrors()) {
                    log.error("  editHarmonizedTariffScheduleCode update failed for " + code + ": " + commandResult);
                }
            } else {
                log.error("  editHarmonizedTariffScheduleCode lock failed for " + code + ": " + commandResult);
            }
        }
        
        checkCodeUses(getCodeUsesForm, code, importCodes, createCodeUseForm, deleteCodeUseForm, exportCodes);
    }
    
    public void checkCodes(Set<String> codesToCheck, Map<String, H> importCodes, Map<String, H> exportCodes, Map<String, H> uniqueCodes) {
        HarmonizedTariffScheduleCodeSpec harmonizedTariffScheduleCodeSpec = ItemSpecFactory.getHarmonizedTariffScheduleCodeSpec();
        EditHarmonizedTariffScheduleCodeForm editCodeForm = ItemFormFactory.getEditHarmonizedTariffScheduleCodeForm();
        GetHarmonizedTariffScheduleCodeUsesForm getCodeUsesForm = ItemFormFactory.getGetHarmonizedTariffScheduleCodeUsesForm();
        CreateHarmonizedTariffScheduleCodeUseForm createCodeUseForm = ItemFormFactory.getCreateHarmonizedTariffScheduleCodeUseForm();
        DeleteHarmonizedTariffScheduleCodeUseForm deleteCodeUseForm = ItemFormFactory.getDeleteHarmonizedTariffScheduleCodeUseForm();
        GetHarmonizedTariffScheduleCodeTranslationForm getCodeTranslationForm = ItemFormFactory.getGetHarmonizedTariffScheduleCodeTranslationForm();
        EditHarmonizedTariffScheduleCodeTranslationForm editCodeTranslationForm = ItemFormFactory.getEditHarmonizedTariffScheduleCodeTranslationForm();
        
        codesToCheck.stream().map((code) -> {
            checkCode(harmonizedTariffScheduleCodeSpec, editCodeForm, getCodeUsesForm, code, importCodes, exportCodes, uniqueCodes, createCodeUseForm,
                    deleteCodeUseForm);        
            return code;
        }).forEach((code) -> {
            checkCodeTranslation(getCodeTranslationForm, code, uniqueCodes, editCodeTranslationForm);
        });
    }

}
