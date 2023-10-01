package currencyConverter;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class JTextFieldLimit extends PlainDocument {
    private int limit;
    private boolean toUppercase = false;

    // Constructor for JTextFieldLimit, which sets the character limit
    JTextFieldLimit(int limit) {
        super(); // Constructor for JTextFieldLimit, which sets the character limit
        this.limit = limit; // Set the character limit for this document
    }
    // Override the insertString method to limit the text input and optionally convert to uppercase
    @Override
    public void insertString
            (int offset, String  str, AttributeSet attr)
            throws BadLocationException {
        if (str == null) return;

        if ((getLength() + str.length()) <= limit) {
            if (toUppercase) str = str.toUpperCase();
            super.insertString(offset, str, attr);
        }
    }
}

