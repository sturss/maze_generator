// Author Christian Schudt
// Modified for just number input by Sviatoslav Skotar

package sample;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

public class NumberInput extends TextField {

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

    /**
     * Gets the max length of the text field.
     * @return The max length.
     */
    public int getMaxLength() {
        return this.maxLength;
    }

    /**
     * Sets the max length of the text field.
     * @param maxLength The max length.
     */
    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }
}