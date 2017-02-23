package hr.fer.android.jmbag1191227025.mathland;

import java.util.Locale;

import hr.fer.android.jmbag1191227025.mathland.DisplayActivity;
import hr.fer.android.jmbag1191227025.mathland.R;

/**
 * Class implementing operations provided in {@link hr.fer.android.jmbag1191227025.mathland.CalculusActivity}.
 */
public class Calculus {
    /**Plus sign.*/
    public static final String PLUSSIGN = "+";
    /**Minus sign.*/
    public static final String MINUSSIGN = "-";
    /**Multiplication sign.*/
    public static final String MULSIGN = "*";
    /**Division sign.*/
    public static final String DIVSIGN = "/";
    /**Format used in formatting the result using {@link String#format(String, Object...)}.*/
    private static final String FORMAT = "%,.4f";

    /**
     * Method returning sign specific for the operation provided
     * by its id in {@link R}.
     *
     * @param calculusID id of operation whose SIGN is required
     * @return sign, if {@link R} has provided id and it is one of the
     *          calculus provided in
     *          {@link hr.fer.android.jmbag1191227025.mathland.CalculusActivity},
     *          {@link DisplayActivity#ERROR} otherwise.
     * */
    public static String getCaluclusSign(int calculusID){
        switch (calculusID) {
            case R.id.plus:
                return PLUSSIGN;
            case R.id.minus:
                return MINUSSIGN;
            case R.id.div:
                return DIVSIGN;
            case R.id.mul:
                return MULSIGN;
        }

        return DisplayActivity.ERROR;
    }

    /**
     * Method returning the string representation of the result of the
     * calculus required by its id in {@link R}.
     *
     * @param calculusID id of operation whose SIGN is required
     * @param firstNumber first operand of the operation
     * @param secondNumber second operand of the operation
     * @param activity activity calling from calculation.
     * @return sign, if {@link R} has provided id and it is one of the
     *          calculus provided in
     *          {@link hr.fer.android.jmbag1191227025.mathland.CalculusActivity},
     *          {@link DisplayActivity#ERROR} otherwise.
     * */
    public static String calculate(int calculusID,double firstNumber,double secondNumber,
                                   CalculusActivity activity){
        double result;
        try {
            switch (calculusID) {
                case R.id.plus:
                    result = firstNumber + secondNumber;
                    return String.format(FORMAT,result);

                case R.id.minus:
                    result = firstNumber-secondNumber;
                    return String.format(FORMAT,result);

                case R.id.div:
                    if(secondNumber == 0){
                        throw new IllegalArgumentException("Dividing with 0 is not allowed.");
                    }
                    result = firstNumber / secondNumber;
                    return String.format(FORMAT,result);

                case R.id.mul:
                    result = firstNumber*secondNumber;
                    return String.format(FORMAT,result);

            }
        }catch (Exception e){
            activity.setError(true);
            return e.getMessage();
        }

        return "No legible operation provided.";
    }


}
