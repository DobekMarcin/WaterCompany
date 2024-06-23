package md.program.utils;

import javafx.scene.control.TextFormatter;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.Month;
import java.time.Year;
import java.time.YearMonth;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

public class Utils {

    public static ResourceBundle getResourceBundle(){
        return ResourceBundle.getBundle("BUNDLES.messages");
    }


    public static UnaryOperator<TextFormatter.Change> doubleFilter = t -> {
        if (t.isReplaced())
            if(t.getText().matches("[^0-9]"))
                t.setText(t.getControlText().substring(t.getRangeStart(), t.getRangeEnd()));

        if (t.isAdded()) {
            if (t.getControlText().contains(".")) {
                if (t.getText().matches("[^0-9]")) {
                    t.setText("");
                }
            } else if (t.getText().matches("[^0-9.]")) {
                t.setText("");
            }
        }
        return t;
    };

    public static UnaryOperator<TextFormatter.Change> integerFilter = t -> {
        if (t.isReplaced())
            if(t.getText().matches("[^0-9]"))
                t.setText(t.getControlText().substring(t.getRangeStart(), t.getRangeEnd()));

        if (t.isAdded()) {
            if (t.getControlText().contains(",")) {
                if (t.getText().matches("[^0-9]")) {
                    t.setText("");
                }
            } else if (t.getText().matches("[^0-9]")) {
                t.setText("");
            }
        }
        return t;
    };

    public static DecimalFormat getDecimalFormatWithTwoPlaces(){
        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
        DecimalFormat df;
        decimalFormatSymbols.setDecimalSeparator('.');
        df = new DecimalFormat("0.00",decimalFormatSymbols);
        return df;
    }

    public static Integer getYear(){
        int year = Year.now().getValue();
        return year;
    }

    public static Integer getMonth(){
        int month = YearMonth.now().getMonth().getValue();
        return month;
    }
}
