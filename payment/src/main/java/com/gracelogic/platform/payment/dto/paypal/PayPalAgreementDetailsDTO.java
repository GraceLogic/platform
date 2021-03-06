package com.gracelogic.platform.payment.dto.paypal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include= JsonSerialize.Inclusion.ALWAYS)
public class PayPalAgreementDetailsDTO {
    private PayPalCurrencyDTO outstanding_balance;
    private Integer cycles_remaining;
    private Integer cycles_completed;
    private String next_billing_date;
    private String last_payment_date;
    private String last_payment_amount;
    private String final_payment_date;
    private Integer failed_payment_count;

    public PayPalCurrencyDTO getOutstanding_balance() {
        return outstanding_balance;
    }

    public void setOutstanding_balance(PayPalCurrencyDTO outstanding_balance) {
        this.outstanding_balance = outstanding_balance;
    }

    public Integer getCycles_remaining() {
        return cycles_remaining;
    }

    public void setCycles_remaining(Integer cycles_remaining) {
        this.cycles_remaining = cycles_remaining;
    }

    public Integer getCycles_completed() {
        return cycles_completed;
    }

    public void setCycles_completed(Integer cycles_completed) {
        this.cycles_completed = cycles_completed;
    }

    public String getNext_billing_date() {
        return next_billing_date;
    }

    public void setNext_billing_date(String next_billing_date) {
        this.next_billing_date = next_billing_date;
    }

    public String getLast_payment_date() {
        return last_payment_date;
    }

    public void setLast_payment_date(String last_payment_date) {
        this.last_payment_date = last_payment_date;
    }

    public String getLast_payment_amount() {
        return last_payment_amount;
    }

    public void setLast_payment_amount(String last_payment_amount) {
        this.last_payment_amount = last_payment_amount;
    }

    public String getFinal_payment_date() {
        return final_payment_date;
    }

    public void setFinal_payment_date(String final_payment_date) {
        this.final_payment_date = final_payment_date;
    }

    public Integer getFailed_payment_count() {
        return failed_payment_count;
    }

    public void setFailed_payment_count(Integer failed_payment_count) {
        this.failed_payment_count = failed_payment_count;
    }
}
