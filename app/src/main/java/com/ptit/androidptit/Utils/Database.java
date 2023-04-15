package com.ptit.androidptit.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ptit.androidptit.model.Item;
import com.ptit.androidptit.repository.IItemRepository;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteOpenHelper implements IItemRepository {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "local.db";

    private Database(Context ctx) {
        super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static Database of(Context ctx) {
        return new Database(ctx);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        try {
            sqLiteDatabase.execSQL(String.format("CREATE TABLE IF NOT EXISTS %s (id INTEGER PRIMARY KEY, name TEXT, description TEXT, deadline TEXT, status TEXT, party BOOLEAN);", Item.TABLE_NAME));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Item.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    @Override
    public List<Item> getAllItems() {
        List<Item> items = new ArrayList<>();
        Cursor cursor = getReadableDatabase().rawQuery("SELECT i.id, i.name, i.description, i.deadline, i.status, i.party FROM " + Item.TABLE_NAME + " i", null);
        while (cursor.moveToNext()) {
            Item item = new Item();
            item.setId(cursor.getInt(0));
            item.setName(cursor.getString(1));
            item.setDescription(cursor.getString(2));
            item.setDeadline(cursor.getString(3));
            item.setStatus(cursor.getString(4));
            item.setParty(cursor.getInt(5) == 0);
            items.add(item);
        }
        cursor.close();
        return items;
    }

    @Override
    public Item getItemById(int id) {
        String[] selectionArgs = {String.valueOf(id)};

        Cursor cursor = getReadableDatabase().rawQuery("SELECT i.id, i.name, i.description, i.deadline, i.status, i.party FROM " + Item.TABLE_NAME + " i WHERE id = ?", selectionArgs);
        if (cursor.moveToNext()) {
            Item item = new Item();
            item.setId(cursor.getInt(0));
            item.setName(cursor.getString(1));
            item.setDescription(cursor.getString(2));
            item.setDeadline(cursor.getString(3));
            item.setStatus(cursor.getString(4));
            item.setParty(cursor.getInt(5) > 0);
            cursor.close();
            return item;
        }
        return null;
    }

    @Override
    public int addItem(Item item) {
        ContentValues values = new ContentValues();
        values.put("name", item.getName());
        values.put("description", item.getDescription());
        values.put("deadline", item.getDeadline());
        values.put("status", item.getStatus());
        values.put("party", item.getParty() ? 1 : 0);
        return (int) getWritableDatabase().insert(Item.TABLE_NAME, null, values);
    }

    @Override
    public void updateItem(Item item) {
        ContentValues values = new ContentValues();
        values.put("name", item.getName());
        values.put("description", item.getDescription());
        values.put("deadline", item.getDeadline());
        values.put("status", item.getStatus());
        values.put("party", item.getParty() ? 1 : 0);
        String[] selectionArgs = {String.valueOf(item.getId())};
        getWritableDatabase().update(Item.TABLE_NAME, values, "id = ?", selectionArgs);
    }

    @Override
    public void deleteItem(int id) {
        String[] selectionArgs = {String.valueOf(id)};
        getWritableDatabase().delete(Item.TABLE_NAME, "id = ?", selectionArgs);
    }
}
