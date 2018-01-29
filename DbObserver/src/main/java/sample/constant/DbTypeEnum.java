package sample.constant;

/**
 * Created by fanzhijie on 2017/5/27.
 */
public enum  DbTypeEnum {
    DB_ACCESS("MS ACCESS", "jdbc:odbc:driver={Microsoft Access Driver (*.mdb, *.accdb)};DBQ="),
    DB_SQLITE("SQLITE", "jdbc:sqlite://");

    private String name;

    private String dbUrl;

    DbTypeEnum(String name, String dbUrl) {
        this.name = name;
        this.dbUrl = dbUrl;
    }

    @Override
    public String toString() {
        return "DbTypeEnum{" +
                "name='" + name + '\'' +
                ", dbUrl='" + dbUrl + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public String getDbUrl() {
        return dbUrl;
    }

    public static String getUrlByName(String name){
        if(DB_ACCESS.getName().equals(name)){
            return DB_ACCESS.getDbUrl();
        }else if(DB_SQLITE.getName().equals(name)){
            return DB_SQLITE.getDbUrl();
        }

        return null;
    }
}
