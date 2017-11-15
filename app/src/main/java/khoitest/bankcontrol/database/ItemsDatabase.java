package khoitest.bankcontrol.database;


public class ItemsDatabase {
    public static final String TABLE_ITEMS = "items";
    public static final String COLUMN_ID = "itemId";
    public static final String COLUMN_DATE = "itemDate";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_AMOUNT = "amount";
    public static final String COLUMN_SIGN = "sign";

    public static final String[] ALL_COLUMNS =
            {COLUMN_ID, COLUMN_DATE, COLUMN_DESCRIPTION,
                    COLUMN_AMOUNT, COLUMN_SIGN};

    public static final String SQL_CREATE =
            "CREATE TABLE " + TABLE_ITEMS + "(" +
                    COLUMN_ID + " TEXT PRIMARY KEY," +
                    COLUMN_DATE + " TEXT," +
                    COLUMN_DESCRIPTION + " TEXT," +
                    COLUMN_AMOUNT + " REAL," +
                    COLUMN_SIGN + " INTEGER" + ");";

    public static final String SQL_DELETE =
            "DROP TABLE " + TABLE_ITEMS;
}
