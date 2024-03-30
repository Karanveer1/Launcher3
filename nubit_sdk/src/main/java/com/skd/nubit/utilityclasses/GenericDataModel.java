package com.skd.nubit.utilityclasses;

public class GenericDataModel<T> implements YourDataModelInterface {
    private T data;
    private String type; // Type identifier for the data model

    public GenericDataModel(T data, String type) {
        this.data = data;
        this.type = type;
    }

    @Override
    public String getType() {
        return type;
    }

    public T getData() {
        return data;
    }

    // Add more methods as needed
}
