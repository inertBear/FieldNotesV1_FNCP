package com.devhunter.fncp.mvc.controller.sql.billing.statemachine;

public class BillingState {
    
    public String mState;

    public BillingState(String state){
        this.mState = state;
    }

    public String getState(){
        return this.mState;
    }
}
