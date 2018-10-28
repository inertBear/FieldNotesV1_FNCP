package com.devhunter.fncp.mvc.controller.billing.statemachine;

public class BillingState {
    
    public String mState;

    public BillingState(String state){
        this.mState = state;
    }

    public String getState(){
        return this.mState;
    }
}
