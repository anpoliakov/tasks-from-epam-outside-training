package system.model.constants;

/**
 * This class is a wrapper for static,final variables. Used in different parts of the program.
 *
 * @author anpoliakov
 * @version 1.0.0
 *
 * */
public class Constants {
    //вызов статического инициализатора производится только перед созданием самого первого объекта класса - благодаря этому
    //не будут сразу созданы все статические переменные при запуске программы.
    static{
        INDEX_FIRST_PARAMETER = 1;
        INDEX_SECOND_PARAMETER = 2;
        INDEX_THIRD_PARAMETER = 3;
        INDEX_FOURTH_PARAMETER = 4;
        INDEX_FIFTH_PARAMETER = 5;

        RESULT_OPERATION_NEGATIVE = -1;
        RESULT_OPERATION_NEUTRAL = 0;
        RESULT_OPERATION_POSITIVE = 1;

    }

    // These variables set the order in PreparedStatement
    public static final int INDEX_FIRST_PARAMETER;
    public static final int INDEX_SECOND_PARAMETER;
    public static final int INDEX_THIRD_PARAMETER;
    public static final int INDEX_FOURTH_PARAMETER;
    public static final int INDEX_FIFTH_PARAMETER;

    // These variables return the result of the method.
    public static final int RESULT_OPERATION_NEGATIVE;
    public static final int RESULT_OPERATION_NEUTRAL;
    public static final int RESULT_OPERATION_POSITIVE;

}
