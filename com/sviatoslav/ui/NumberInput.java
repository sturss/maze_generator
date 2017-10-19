// Author of idea Christian Schudt
// Modified for just number input by Sviatoslav Skotar

package sviatoslav.ui;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

public class NumberInput extends TextField {
    private int minValue = -1;
    private int maxValue = -1;
    private int maxNumberLength = -1;

    public NumberInput() {
        textProperty().addListener(new ChangeListener<String>() {
            private boolean ignore;

            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                if(newValue.length() == 0 || maxNumberLength == 0 || newValue.equals("0")) {
                    setText("");
                    return;
                }

                if(newValue.charAt(0) == '0') {
                    if (maxNumberLength >= newValue.length()) {
                        setText(newValue.substring(1, newValue.length()));
                    }
                    else {
                        setText(newValue.substring(1, maxNumberLength));
                    }
                }

                for(int i = 0; i < newValue.length(); i++)
                    if(newValue.charAt(i) < '0' || newValue.charAt(i) > '9') {
                        setText(oldValue);
                    }

                if (maxNumberLength > 0 && newValue.length() > maxNumberLength) {
                    setText(oldValue);
                }
            }
        });
    }

    public int getMaxNumberLength() {
        return this.maxNumberLength;
    }

    public void setMaxNumberLength(int max) {
        this.maxNumberLength = max;
    }

    public int getMinValue() {
        return this.minValue;
    }

    public void setMaxValue(int max){
        this.maxValue = max;
    }

    public int getMaxValue(){
        return this.maxValue;
    }

    public void setMinValue(int min){
        this.maxValue = min;
    }
}