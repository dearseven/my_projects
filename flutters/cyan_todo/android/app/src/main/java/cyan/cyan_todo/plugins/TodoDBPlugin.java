package cyan.cyan_todo.plugins;

import android.content.Context;

import java.util.List;
import java.util.Map;

import cyansql.DBClient;
import cyansql.IDBClient;
import cyansql.tables.TodoItem;

public class TodoDBPlugin {
    public static String FLUTTER_CALL_NAME = "TodoSavePlugin_save_todo";

    public static boolean saveTodo(Context ctx, String date, String time, int repeatMode, String title, String detail) {
        IDBClient client = DBClient.getInstance(ctx);
        client.insert(TodoItem.TABLE_NAME,
                new String[]{TodoItem.col_title,
                        TodoItem.col_date,
                        TodoItem.col_time,
                        TodoItem.col_detail},
                new String[]{title, date, time, detail});
        return false;
    }

    public static String FLUTTER_CALL_NAME_getTodos = "TodoSavePlugin_todo_getTodos";

    public static List<Map<String, String>> getTodos(Context ctx) {
        IDBClient client = DBClient.getInstance(ctx);
        List<Map<String, String>> list =client.retrieve(TodoItem.TABLE_NAME,null,null,null,null,null,null,null);
        return list;
    }
}

