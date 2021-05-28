package com.jhaner.esp32.model;

public class ModelShield {

    private String shield_id;
    private String name;
    private String model;
    private String mac;

    public ModelShield() { }

    public void setShield_id(String shield_id) { this.shield_id = shield_id; }
    public void setName(String name) { this.name = name; }
    public void setModel(String model) { this.model = model; }
    public void setMac(String mac) { this.mac = mac; }

    public String getShield_id() { return shield_id; }
    public String getName() { return name; }
    public String getModel() { return model; }
    public String getMac() { return mac; }

}
