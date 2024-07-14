package it.florentino.dark.timeplanapp.utils;

public class DaoSetter {

    private static String daoTypes;

    private DaoSetter(){}

    public static void setDao(String daoTypes){
        DaoSetter.daoTypes = daoTypes;
    }

    public static String getDao(){
        return DaoSetter.daoTypes;
    }
}
