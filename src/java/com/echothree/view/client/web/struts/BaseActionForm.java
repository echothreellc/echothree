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

package com.echothree.view.client.web.struts;

import com.echothree.control.user.core.common.CoreUtil;
import com.echothree.control.user.core.common.form.ValidateForm;
import com.echothree.control.user.core.common.result.ValidateResult;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.form.BaseForm;
import com.echothree.util.common.form.BaseFormFactory;
import com.echothree.view.client.web.WebConstants;
import com.echothree.view.client.web.taglib.TagConstants;
import com.echothree.view.common.BaseChoicesBean;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

public class BaseActionForm
        extends ActionForm {
    
    protected UserVisitPK userVisitPK;
    private String dtIdAttribute;
    private String dtSortParameter;
    private String dtPageParameter;
    private String dtOrderParameter;
    private String submitButton;
    
    /** Creates a new instance of BaseActionForm */
    public BaseActionForm() {
        super();
        
        userVisitPK = null;
    }
    
    public List<LabelValueBean> convertChoices(BaseChoicesBean choices) {
        var labels = choices.getLabels();
        var values = choices.getValues();
        var labelsSize = labels.size();
        var valuesSize = values.size();
        List<LabelValueBean> result = null;
        
        if(labelsSize == valuesSize) {
            result = new ArrayList<>(labelsSize);
            var labelIterator = labels.iterator();
            var valueIterator = values.iterator();
            
            while(labelIterator.hasNext() && valueIterator.hasNext()) {
                var label = labelIterator.next();
                var value = valueIterator.next();
                
                if(label == null)
                    label = "";
                
                result.add(new LabelValueBean(label, value));
            }
        }
        
        return result;
    }
    
    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);

        var httpSession = request.getSession(true);
        
        userVisitPK = (UserVisitPK)httpSession.getAttribute(WebConstants.Session_USER_VISIT);
    }

    public String getDtIdAttribute() {
        return dtIdAttribute;
    }

    public void setDtIdAttribute(String dtIdAttribute) {
        this.dtIdAttribute = dtIdAttribute;
    }

    public String getDtSortParameter() {
        return dtSortParameter;
    }

    public void setDtSortParameter(String dtSortParameter) {
        this.dtSortParameter = dtSortParameter;
    }

    public String getDtPageParameter() {
        return dtPageParameter;
    }

    public void setDtPageParameter(String dtPageParameter) {
        this.dtPageParameter = dtPageParameter;
    }

    public String getDtOrderParameter() {
        return dtOrderParameter;
    }

    public void setDtOrderParameter(String dtOrderParameter) {
        this.dtOrderParameter = dtOrderParameter;
    }
    
    public void saveDtParameters(HttpServletRequest request) {
        dtIdAttribute = request.getParameter(WebConstants.Parameter_DT_ID_ATTRIBUTE);
        dtSortParameter = request.getParameter(WebConstants.Parameter_DT_SORT_PARAMETER);
        dtPageParameter = request.getParameter(WebConstants.Parameter_DT_PAGE_PARAMETER);
        dtOrderParameter = request.getParameter(WebConstants.Parameter_DT_ORDER_PARAMETER);
    }

    public String getSubmitButton() {
        return submitButton;
    }

    public void setSubmitButton(String submitButton) {
        this.submitButton = submitButton;
    }

    private ValidateForm getValidateForm(final List<FieldDefinition> fieldDefinitions, final Map<String, Class> returnTypes)
            throws NamingException {
        var validateForm = CoreUtil.getHome().getValidateForm();
        var baseForm = BaseFormFactory.createForm(BaseForm.class);
        Class<?> clazz = this.getClass();

        for(var fieldDefinition : fieldDefinitions) {
            var fieldName = fieldDefinition.getFieldName();
            
            try {
                var method = clazz.getMethod("get" + fieldName);
                var fieldValue = method.invoke(this);

                returnTypes.put(fieldName, method.getReturnType());

                if(fieldValue != null) {
                    baseForm.set(fieldName, fieldValue.toString());
                }
            } catch(NoSuchMethodException nsme) {
                throw new RuntimeException(nsme);
            } catch(IllegalAccessException iae) {
                throw new RuntimeException(iae);
            } catch(IllegalArgumentException iea) {
                throw new RuntimeException(iea);
            } catch(InvocationTargetException ite) {
                throw new RuntimeException(ite);
            }
        }

        validateForm.setFormFieldDefinitions(fieldDefinitions);
        validateForm.setBaseForm(baseForm);

        return validateForm;
    }

    private void setFormValidateResult(final ValidateResult validateResult, final Map<String, Class> returnTypes) {
        var baseForm = validateResult.getBaseForm();
        var map = baseForm.get();
        Class<?> clazz = this.getClass();

        for(var entry : map.entrySet()) {
            var key = entry.getKey();
            Class<?> returnType = returnTypes.get(key);

            // If getValidateForm didn't add a map entry, returnType will be null. Ignore those
            // values, as they tend to be from functions like getFomName() which don't exist on
            // the ActionForm.
            if(returnType != null) {
                var methodName = "set" + key;
                var value = entry.getValue();

                if(returnType.equals(Boolean.class) && value instanceof String) {
                    value = Boolean.valueOf((String)value);
                } else if(returnType.equals(Integer.class) && value instanceof String) {
                    value = Integer.valueOf((String)value);
                } else if(returnType.equals(Long.class) && value instanceof String) {
                    value = Long.valueOf((String)value);
                }

                try {
                    clazz.getMethod(methodName, returnType).invoke(this, value);
                } catch(NoSuchMethodException nsme) {
                    throw new RuntimeException(nsme);
                } catch(IllegalAccessException iae) {
                    throw new RuntimeException(iae);
                } catch(IllegalArgumentException iea) {
                    throw new RuntimeException(iea);
                } catch(InvocationTargetException ite) {
                    throw new RuntimeException(ite);
                }
            }
        }
    }

    protected boolean validate(final HttpServletRequest request, final List<FieldDefinition> fieldDefinitions)
            throws NamingException {
        Map<String, Class> returnTypes = new HashMap<>(fieldDefinitions.size());
        var commandForm = getValidateForm(fieldDefinitions, returnTypes);

        var commandResult = CoreUtil.getHome().validate(BaseAction.getUserVisitPK(request), commandForm);
        if(!commandResult.hasErrors()) {
            var executionResult = commandResult.getExecutionResult();

            setFormValidateResult((ValidateResult)executionResult.getResult(), returnTypes);
        }

        request.setAttribute(TagConstants.CommandResultName, commandResult);

        return !commandResult.hasErrors();
    }
    
}
