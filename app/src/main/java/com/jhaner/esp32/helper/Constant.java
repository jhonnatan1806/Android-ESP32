package com.jhaner.esp32.helper;

public class Constant {

    public static final String SERVER_URL = "http://192.241.140.103";
    public static final String  METHOD_SHOWSHIELDS = "/script.php?method=showshields";
    public static final String  METHOD_SHOWMODULES= "/script.php?method=showmodules";
    public static final String  METHOD_SHOWDATA = "/script.php?method=showdata";
    public static final String  METHOD_MODIFYDATA = "/script.php?method=modifydata";

    public static final String  ARG_SHIELDID = "&shield_id=";
    public static final String  ARG_MODULEID = "&module_id=";
    public static final String  ARG_STATUS = "&status=";
    public static final String  ARG_CREATIONDATE = "&creation_date=";
    public static final String  ARG_CYCLES = "&cycles=";
    public static final String  ARG_CYCLESCOMPLETED = "&cycles_completed=";
    public static final String  ARG_TIMEON = "&time_on=";
    public static final String  ARG_TIMEOFF = "&time_off=";

}
