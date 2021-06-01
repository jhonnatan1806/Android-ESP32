package com.jhaner.esp32.helper;

public class Constants {

    public static final String SERVER_URL = "http://192.241.140.103";
    public static final String METHOD_SHOWSHIELDS = "/script.php?method=showshields";
    public static final String METHOD_SHOWMODULES= "/script.php?method=showmodules";
    public static final String METHOD_SHOWDATA = "/script.php?method=showdata";
    public static final String METHOD_MODIFYDATA = "/script.php?method=modifydata";

    public static final String ARG_SHIELDID = "&shield_id=";
    public static final String ARG_MODULEID = "&module_id=";
    public static final String ARG_STATUS = "&status=";
    public static final String ARG_CREATIONDATE = "&creation_date=";
    public static final String ARG_CYCLES = "&cycles=";
    public static final String ARG_CYCLESCOMPLETED = "&cycles_completed=";
    public static final String ARG_TIMEON = "&time_on=";
    public static final String ARG_TIMEOFF = "&time_off=";

    public static final String DEFAULT_SHIELDID = "10000000";
    public static final String DEFAULT_MODULEID = "20000000";
    public static final String DEFAULT_STATUS = "0";
    public static final String DEFAULT_CREATIONDATE = "2021-01-01+00:00:00";
    public static final String DEFAULT_CYCLES = "0";
    public static final String DEFAULT_CYCLESCOMPLETED = "0";
    public static final String DEFAULT_TIMEON = "0";
    public static final String DEFAULT_TIMEOFF = "0";

    public static final String KEY_SHIELDID = "SHIELD_ID";
    public static final String KEY_MODULEID = "MODULE_ID";
    public static final String KEY_STATUS = "STATUS";
    public static final String KEY_CREATIONDATE = "CREATION_DATE";
    public static final String KEY_CYCLES = "CYCLES";
    public static final String KEY_CYCLESCOMPLETED = "CYCLES_COMPLETED";
    public static final String KEY_TIMEON = "TIME_ON";
    public static final String KEY_TIMEOFF = "TIME_OFF";

    public static final String DATE_FORMAT = "yyyy-MM-dd+HH:mm:ss";
    public static final String DATE_TIMEZONE = "America/Lima";

    public static final String HTML_METHOD = "GET";
    public static final String HTML_KEY = "HTML";
    public static final String HTML_CACHECONTROL = "Cache-Control";
    public static final String HTML_NOCACHE = "no-cache";

}
