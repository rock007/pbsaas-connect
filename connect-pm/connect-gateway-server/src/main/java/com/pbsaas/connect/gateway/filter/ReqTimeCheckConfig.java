/**
* sam@here 2019年10月23日
**/
package com.pbsaas.connect.gateway.filter;

public  class ReqTimeCheckConfig {

    private boolean withParams;
    
    public ReqTimeCheckConfig(boolean is) {
    	
    	withParams=is;
    }
    
    public boolean isWithParams() {
        return withParams;
    }

    public void setWithParams(boolean withParams) {
        this.withParams = withParams;
    }

}

