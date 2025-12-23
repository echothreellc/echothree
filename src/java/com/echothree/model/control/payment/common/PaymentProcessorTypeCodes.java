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

package com.echothree.model.control.payment.common;

public enum PaymentProcessorTypeCodes {

    ACCOUNT_CLOSED,
    ACCTERROR,
    ACCT_ADMINTRANSNOTALLOWED,
    ACCT_DISABLED,
    ACCT_INVALIDTRANS,
    ACCT_OBSCUREREQUIRED,
    ACCT_PASSEXPIRED,
    ACCT_SSLCERT,
    ACCT_TOOMANYATTEMPTS,
    ACCT_TRANSNOTALLOWED,
    ACCT_USERTRANSNOTALLOWED,
    ALREADY_ACTIVE,
    ALREADY_REVERSED,
    AUTHFAILED,
    BAD_MERCH_ID,
    BAD_PIN,
    BALANCE_MISMATCH,
    CALL,
    CARD_EXPIRED,
    CASHBACK_EXCEEDED,
    CASHBACK_NOAVAIL,
    CID_ERROR,
    CONN_MAXATTEMPTS,
    CONN_MAXSENDS,
    CONN_TOREVERSAL,
    DATA_ABAROUTE,
    DATA_ACCOUNT,
    DATA_AMOUNT,
    DATA_BADMICR,
    DATA_BADTRANS,
    DATA_BATCHLOCKED,
    DATA_EXPDATE,
    DATA_INVALIDMOD,
    DATA_NOOPENBATCHES,
    DATA_RECORDNOTFOUND,
    DATA_TRACKDATA,
    DATE_ERROR,
    DB_FAIL,
    DONOTHONOR,
    DUPLICATE_BATCH,
    ENCRYPTION_ERROR,
    EXCEED_ACTIVITY_LIMIT,
    EXCEED_WITHDRAWAL_LIMIT,
    FRAUDAUTODENY,
    GENERICFAIL,
    ID_ERROR,
    INELIGIBLE_CONV,
    INSUFFICIENT_FUNDS,
    INT_GENERICFAIL,
    INT_SUCCESS,
    INVALID_ACCOUNT_TYPE,
    INVALID_SERVICE_CODE,
    LIC_CARDTYPE,
    LIC_TRANEXCEED,
    LIC_USERS,
    MANAGER_NEEDED,
    NOREPLY,
    NOT_ACTIVE,
    NOT_PERMITTED_CARD,
    NOT_PERMITTED_TRAN,
    PICKUP_FRAUD,
    PICKUP_LOST,
    PICKUP_NOFRAUD,
    PICKUP_STOLEN,
    RECURRING_CANCEL,
    REENTER,
    REJECTED_BATCH,
    REPRESENTED,
    RETRY,
    RETRY_FORCE_INSERT,
    SECURITY_VIOLATION,
    SETUP_CARDTYPE,
    SETUP_DATA,
    SETUP_SCHED,
    SETUP_TRANTYPE,
    SNF,
    SUCCESS,
    SYSTEM_ERROR,
    SYS_MAINTENANCE,
    SYS_SHUTDOWN,
    UNKNOWN,
    VIOLATION,

}
