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

package com.echothree.model.control.customer.common.workflow;

public interface CustomerCreditCardContactMechanismConstants {
    
    String Workflow_CUSTOMER_CREDIT_CARD_CONTACT_MECHANISM = "CUSTOMER_CREDIT_CARD_CONTACT_MECHANISM";
    
    String WorkflowStep_AVS_SECURITY_CODE_TEST = "AVS_SECURITY_CODE_TEST";
    String WorkflowStep_SECURITY_CODE_TEST = "SECURITY_CODE_TEST";
    String WorkflowStep_VERIFICATION = "VERIFICATION";
    String WorkflowStep_FRAUD_REVIEW = "FRAUD_REVIEW";
    String WorkflowStep_FRAUDULENT = "FRAUDULENT";
    String WorkflowStep_VERIFIED = "VERIFIED";
    
    String WorkflowDestination_AVS_SECURITY_CODE_TEST_TO_VERIFIED = "AVS_SECURITY_CODE_TEST_TO_VERIFIED";
    String WorkflowDestination_AVS_SECURITY_CODE_TEST_TO_FRAUD_REVIEW = "AVS_SECURITY_CODE_TEST_TO_FRAUD_REVIEW";
    String WorkflowDestination_SECURITY_CODE_TEST_TO_VERIFIED = "SECURITY_CODE_TEST_TO_VERIFIED";
    String WorkflowDestination_SECURITY_CODE_TEST_TO_FRAUD_REVIEW = "SECURITY_CODE_TEST_TO_FRAUD_REVIEW";
    String WorkflowDestination_FRAUD_REVIEW_TO_FRAUDULENT = "FRAUD_REVIEW_TO_FRAUDULENT";
    String WorkflowDestination_FRAUDULENT_TO_FRAUD_REVIEW = "FRAUDULENT_TO_FRAUD_REVIEW";
    String WorkflowDestination_FRAUD_REVIEW_TO_VERIFICATION = "FRAUD_REVIEW_TO_VERIFICATION";
    String WorkflowDestination_VERIFICATION_TO_FRAUD_REVIEW = "VERIFICATION_TO_FRAUD_REVIEW";
    String WorkflowDestination_FRAUD_REVIEW_TO_AVS_SECURITY_CODE_TEST = "FRAUD_REVIEW_TO_AVS_SECURITY_CODE_TEST";
    String WorkflowDestination_FRAUD_REVIEW_TO_SECURITY_CODE_TEST = "FRAUD_REVIEW_TO_SECURITY_CODE_TEST";
    
    String WorkflowEntrance_NEW_AVS_SECURITY_CODE_TEST = "NEW_AVS_SECURITY_CODE_TEST";
    String WorkflowEntrance_NEW_SECURITY_CODE_TEST = "NEW_SECURITY_CODE_TEST";
    String WorkflowEntrance_NEW_VERIFIED = "NEW_VERIFIED";
    
}
