package com.jhaner.esp32.model;

public class ModelShield {

    private String id;
    private String name;
    private String model;
    private String mac;

    public ModelShield() { }

    public void setId(String id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setModel(String model) { this.model = model; }
    public void setMac(String mac) { this.mac = mac; }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getModel() { return model; }
    public String getMac() { return mac; }

}
