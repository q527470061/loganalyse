package com.yozo.loganalyse.commons.cons;

import java.util.ArrayList;

public interface CommonConfig {

    String SSO1_LOG_PATH="F:\\log\\auth3.log";
    String SSO2_LOG_PATH="F:\\log\\auth3.log";

    /**
     * 所有文件读取
     */
    String[] LOG_PATHS={SSO1_LOG_PATH,SSO2_LOG_PATH};
}
