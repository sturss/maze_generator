// Author of idea Christian Schudt
// Modified for just number input by Sviatoslav Skotar

package sviatoslav.ui;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

public class NumberInput extends TextField {
    private int maxNumberLength = -1;

    public NumberInput() {
        textProperty().addListener((observableValue, oldValue, newValue) -> {
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
        });
    }

    public int getMaxNumberLength() {
        return this.maxNumberLength;
    }

    public void setMaxNumberLength(int max) {
        this.maxNumberLength = max;
    }
}