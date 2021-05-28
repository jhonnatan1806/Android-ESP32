package com.jhaner.esp32.model;

public class ModelModule {

    private String shield_id;
    private String module_id;
    private String creation_date;
    private String status;
    private String cycles;
    private String cycles_completed;
    private String time_on;
    private String time_off;

    public ModelModule() { }

    public void setShield_id(String shield_id) { this.shield_id = shield_id; }

    public void setModule_id(String module_id) { this.module_id = module_id; }

    public void setCreation_date(String creation_date) { this.creation_date = creation_date; }

    public void setStatus(String status) { this.status = status; }

    public void setCycles(String cycles) { this.cycles = cycles; }

    public void setCycles_completed(String cycles_completed) { this.cycles_completed = cycles_completed; }

    public void setTime_on(String time_on) { this.time_on = time_on; }

    public void setTime_off(String time_off) { this.time_off = time_off; }

    public String getShield_id() { return shield_id; }

    public String getModule_id() { return module_id; }

    public String getCreation_date() { return creation_date; }

    public String getStatus() { return status; }

    public String getCycles() { return cycles; }

    public String getCycles_completed() { return cycles_completed; }

    public String getTime_on() { return time_on; }

    public String getTime_off() { return time_off; }
}
