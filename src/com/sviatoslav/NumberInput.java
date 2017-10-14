// Author Christian Schudt
// Modified for just number input by Sviatoslav Skotar

package com.sviatoslav;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

class NumberInput extends TextField {
    private int min_value = -1;
    private int max_value = -1;
    private int maxLength = -1;

    NumberInput() {
        textProperty().addListener(new ChangeListener<String>() {
            private boolean ignore;

            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                if(newValue.length() == 0 || maxLength == 0 || newValue.equals("0")) {
                    setText("");
                    return;
                }

                if(newValue.charAt(0) == '0') {
                    if (maxLength >= newValue.length()) {
                        setText(newValue.substring(1, newValue.length()));
                    }
                    else {
                        setText(newValue.substring(1, maxLength));
                    }
                }

                for(int i = 0; i < newValue.length(); i++)
                    if(newValue.charAt(i) < '0' || newValue.charAt(i) > '9') {
                        setText(oldValue);
                        return;
                    }

                if (maxLength > 0 && newValue.length() > maxLength) {
                    setText(oldValue);
                }
            }
        });
    }

    int getMaxLength() {
        return this.maxLength;
    }

    void setMaxLength(int max) {
        this.maxLength = maxLength;
    }

    int getMin_value() {
        return this.min_value;
    }

    void setMaxValue(int max){
        this.max_value = max;
    }

    int getMax_value(){
        return this.max_value;
    }

    void setMinValue(int min){
        this.max_value = min;
    }
}