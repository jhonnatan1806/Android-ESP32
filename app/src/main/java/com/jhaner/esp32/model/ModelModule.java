package com.jhaner.esp32.model;

public class ModelModule {

    private String module_id;
    private String shield_id;
    private String name;
    private String type;
    private String description;

    public ModelModule() { }

    public void setModule_id(String module_id) { this.module_id = module_id; }

    public void setShield_id(String shield_id) { this.shield_id = shield_id; }

    public void setName(String name) { this.name = name; }

    public void setType(String type) { this.type = type; }

    public void setDescription(String description) { this.description = description; }

    public String getModule_id() { return module_id; }

    public String getShield_id() { return shield_id; }

    public String getName() { return name; }

    public String getType() { return type; }

    public String getDescription() { return description; }
}
