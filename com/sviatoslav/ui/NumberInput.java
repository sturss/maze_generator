// Author of idea Christian Schudt
// Modified for just number input by Sviatoslav Skotar

package sviatoslav.ui;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

public class NumberInput extends TextField {
    private int min_value = -1;
    private int max_value = -1;
    private int maxLength = -1;

    public NumberInput() {
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

    public int getMaxLength() {
        return this.maxLength;
    }

    public void setMaxLength(int max) {
        this.maxLength = maxLength;
    }

    public int getMin_value() {
        return this.min_value;
    }

    public void setMaxValue(int max){
        this.max_value = max;
    }

    public int getMax_value(){
        return this.max_value;
    }

    public void setMinValue(int min){
        this.max_value = min;
    }
}