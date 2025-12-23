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

package com.echothree.ui.web.main.action.item.itemdescriptiontype.add;

import com.echothree.control.user.core.common.CoreUtil;
import com.echothree.control.user.core.common.result.GetMimeTypeChoicesResult;
import com.echothree.control.user.item.common.ItemUtil;
import com.echothree.control.user.item.common.result.GetItemDescriptionTypeChoicesResult;
import com.echothree.model.control.core.common.choice.MimeTypeChoicesBean;
import com.echothree.model.control.item.common.choice.ItemDescriptionTypeChoicesBean;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="ItemDescriptionTypeAddStep2")
public class Step2ActionForm
        extends BaseActionForm {

    private ItemDescriptionTypeChoicesBean parentItemDescriptionTypeChoices;
    private MimeTypeChoicesBean preferredMimeTypeChoices;

    private String itemDescriptionTypeName;
    private String parentItemDescriptionTypeChoice;
    private Boolean useParentIfMissing;
    private String mimeTypeUsageTypeName;
    private Boolean checkContentWebAddress;
    private Boolean includeInIndex;
    private Boolean indexDefault;
    private Boolean isDefault;
    private String sortOrder;
    private String description;
    private String minimumHeight;
    private String minimumWidth;
    private String maximumHeight;
    private String maximumWidth;
    private String preferredHeight;
    private String preferredWidth;
    private String preferredMimeTypeChoice;
    private String quality;
    private Boolean scaleFromParent;

    private void setupParentItemDescriptionTypeChoices()
            throws NamingException {
        if(parentItemDescriptionTypeChoices == null) {
            var form = ItemUtil.getHome().getGetItemDescriptionTypeChoicesForm();

            form.setDefaultItemDescriptionTypeChoice(parentItemDescriptionTypeChoice);
            form.setAllowNullChoice(String.valueOf(true));

            var commandResult = ItemUtil.getHome().getItemDescriptionTypeChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var getItemDescriptionTypeChoicesResult = (GetItemDescriptionTypeChoicesResult)executionResult.getResult();
            parentItemDescriptionTypeChoices = getItemDescriptionTypeChoicesResult.getItemDescriptionTypeChoices();

            if(parentItemDescriptionTypeChoice == null) {
                parentItemDescriptionTypeChoice = parentItemDescriptionTypeChoices.getDefaultValue();
            }
        }
    }

    public void setupPreferredMimeTypeChoices()
            throws NamingException {
        if(preferredMimeTypeChoices == null) {
            var form = CoreUtil.getHome().getGetMimeTypeChoicesForm();

            form.setMimeTypeUsageTypeName(mimeTypeUsageTypeName);
            form.setDefaultMimeTypeChoice(preferredMimeTypeChoice);
            form.setAllowNullChoice(String.valueOf(true));

            var commandResult = CoreUtil.getHome().getMimeTypeChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var result = (GetMimeTypeChoicesResult)executionResult.getResult();
            preferredMimeTypeChoices = result.getMimeTypeChoices();

            if(preferredMimeTypeChoice == null) {
                preferredMimeTypeChoice = preferredMimeTypeChoices.getDefaultValue();
            }
        }
    }

    public void setItemDescriptionTypeName(String itemDescriptionTypeName) {
        this.itemDescriptionTypeName = itemDescriptionTypeName;
    }

    public String getItemDescriptionTypeName() {
        return itemDescriptionTypeName;
    }

    public List<LabelValueBean> getParentItemDescriptionTypeChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;

        setupParentItemDescriptionTypeChoices();
        if(parentItemDescriptionTypeChoices != null) {
            choices = convertChoices(parentItemDescriptionTypeChoices);
        }

        return choices;
    }

    public String getParentItemDescriptionTypeChoice()
            throws NamingException {
        setupParentItemDescriptionTypeChoices();
        return parentItemDescriptionTypeChoice;
    }

    public void setParentItemDescriptionTypeChoice(String parentItemDescriptionTypeChoice) {
        this.parentItemDescriptionTypeChoice = parentItemDescriptionTypeChoice;
    }

    /**
     * Returns the useParentIfMissing.
     * @return the useParentIfMissing
     */
    public Boolean getUseParentIfMissing() {
        return useParentIfMissing;
    }

    /**
     * Sets the useParentIfMissing.
     * @param useParentIfMissing the useParentIfMissing to set
     */
    public void setUseParentIfMissing(Boolean useParentIfMissing) {
        this.useParentIfMissing = useParentIfMissing;
    }

    public String getMimeTypeUsageTypeName() {
        return mimeTypeUsageTypeName;
    }

    public void setMimeTypeUsageTypeName(String mimeTypeUsageTypeName) {
        this.mimeTypeUsageTypeName = mimeTypeUsageTypeName;
    }

    /**
     * Returns the checkContentWebAddress.
     * @return the checkContentWebAddress
     */
    public Boolean getCheckContentWebAddress() {
        return checkContentWebAddress;
    }

    /**
     * Sets the checkContentWebAddress.
     * @param checkContentWebAddress the checkContentWebAddress to set
     */
    public void setCheckContentWebAddress(Boolean checkContentWebAddress) {
        this.checkContentWebAddress = checkContentWebAddress;
    }

    /**
     * Returns the includeInIndex.
     * @return the includeInIndex
     */
    public Boolean getIncludeInIndex() {
        return includeInIndex;
    }

    /**
     * Sets the includeInIndex.
     * @param includeInIndex the includeInIndex to set
     */
    public void setIncludeInIndex(Boolean includeInIndex) {
        this.includeInIndex = includeInIndex;
    }

    /**
     * Returns the indexDefault.
     * @return the indexDefault
     */
    public Boolean getIndexDefault() {
        return indexDefault;
    }

    /**
     * Sets the indexDefault.
     * @param indexDefault the indexDefault to set
     */
    public void setIndexDefault(Boolean indexDefault) {
        this.indexDefault = indexDefault;
    }

    public Boolean getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    /**
     * Returns the minimumHeight.
     * @return the minimumHeight
     */
    public String getMinimumHeight() {
        return minimumHeight;
    }

    /**
     * Sets the minimumHeight.
     * @param minimumHeight the minimumHeight to set
     */
    public void setMinimumHeight(String minimumHeight) {
        this.minimumHeight = minimumHeight;
    }

    /**
     * Returns the minimumWidth.
     * @return the minimumWidth
     */
    public String getMinimumWidth() {
        return minimumWidth;
    }

    /**
     * Sets the minimumWidth.
     * @param minimumWidth the minimumWidth to set
     */
    public void setMinimumWidth(String minimumWidth) {
        this.minimumWidth = minimumWidth;
    }

    /**
     * Returns the maximumHeight.
     * @return the maximumHeight
     */
    public String getMaximumHeight() {
        return maximumHeight;
    }

    /**
     * Sets the maximumHeight.
     * @param maximumHeight the maximumHeight to set
     */
    public void setMaximumHeight(String maximumHeight) {
        this.maximumHeight = maximumHeight;
    }

    /**
     * Returns the maximumWidth.
     * @return the maximumWidth
     */
    public String getMaximumWidth() {
        return maximumWidth;
    }

    /**
     * Sets the maximumWidth.
     * @param maximumWidth the maximumWidth to set
     */
    public void setMaximumWidth(String maximumWidth) {
        this.maximumWidth = maximumWidth;
    }

    /**
     * Returns the preferredHeight.
     * @return the preferredHeight
     */
    public String getPreferredHeight() {
        return preferredHeight;
    }

    /**
     * Sets the preferredHeight.
     * @param preferredHeight the preferredHeight to set
     */
    public void setPreferredHeight(String preferredHeight) {
        this.preferredHeight = preferredHeight;
    }

    /**
     * Returns the preferredWidth.
     * @return the preferredWidth
     */
    public String getPreferredWidth() {
        return preferredWidth;
    }

    /**
     * Sets the preferredWidth.
     * @param preferredWidth the preferredWidth to set
     */
    public void setPreferredWidth(String preferredWidth) {
        this.preferredWidth = preferredWidth;
    }

    public List<LabelValueBean> getPreferredMimeTypeChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;

        setupPreferredMimeTypeChoices();
        if(preferredMimeTypeChoices != null)
            choices = convertChoices(preferredMimeTypeChoices);

        return choices;
    }

    public void setPreferredMimeTypeChoice(String preferredMimeTypeChoice) {
        this.preferredMimeTypeChoice = preferredMimeTypeChoice;
    }

    public String getPreferredMimeTypeChoice()
            throws NamingException {
        setupPreferredMimeTypeChoices();
        return preferredMimeTypeChoice;
    }

    /**
     * Returns the quality.
     * @return the quality
     */
    public String getQuality() {
        return quality;
    }

    /**
     * Sets the quality.
     * @param quality the quality to set
     */
    public void setQuality(String quality) {
        this.quality = quality;
    }

    /**
     * Returns the scaleFromParent.
     * @return the scaleFromParent
     */
    public Boolean getScaleFromParent() {
        return scaleFromParent;
    }

    /**
     * Sets the scaleFromParent.
     * @param scaleFromParent the scaleFromParent to set
     */
    public void setScaleFromParent(Boolean scaleFromParent) {
        this.scaleFromParent = scaleFromParent;
    }

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);

        checkContentWebAddress = false;
        useParentIfMissing = false;
        includeInIndex = false;
        indexDefault = false;
        isDefault = false;
        scaleFromParent = false;
    }

}
