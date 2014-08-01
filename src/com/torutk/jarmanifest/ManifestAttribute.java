/*
 *  Copyright © 2014 TAKAHASHI,Toru
 */
package com.torutk.jarmanifest;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author TAKAHASHI,Toru
 */
public class ManifestAttribute {

    private final StringProperty header;
    private final StringProperty value;

    public ManifestAttribute(String header, String value) {
        this.header = new SimpleStringProperty(header);
        this.value = new SimpleStringProperty(value);
    }

    public String getHeader() {
        return header.get();
    }

    public void setHeader(String header) {
        this.header.set(header);
    }

    public StringProperty headerProperty() {
        return header;
    }

    public String getValue() {
        return value.get();
    }

    public void setValue(String value) {
        this.value.set(value);
    }

    public StringProperty valueProperty() {
        return value;
    }

}
