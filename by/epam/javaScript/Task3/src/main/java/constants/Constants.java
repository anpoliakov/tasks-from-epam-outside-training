package constants;

public class Constants {
    //Часть вообще не используется, но сделано на бьудущее, даст гибкость в пониманиии ответа от сервера
    public static final int DEFAULT_NUMBER = 0;

    public static final int ADDED_NEW_USER = 1; //пользователь добавлен
    public static final int USER_EXIST = -1;  //пользователь существует

    public static final int USER_FOUND = 2; //пользователь был найден
    public static final int USER_NOT_FOUND = -2; // пользователя в БД не существует
}
