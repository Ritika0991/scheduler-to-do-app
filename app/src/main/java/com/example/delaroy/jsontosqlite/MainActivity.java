package com.example.delaroy.jsontosqlite;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.delaroy.jsontosqlite.data.DbContract;
import com.example.delaroy.jsontosqlite.data.DbHelper;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final String TAG = "MainActivity";

    RecyclerView recyclerView;
    MenuListAdapter adapter;
    private DbHelper mHelper;
    private ArrayAdapter<String> mAdapter;


    /*public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder>{

        private LayoutInflater inflater;
        private Context context;
        private String mAdapt[];

        public CustomAdapter(Context context,String mAdapt[]) {
            inflater = LayoutInflater.from(context);
            this.context = context;
            this.mAdapt=mAdapt;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = inflater.inflate(R.layout.custom_layout, parent, false);
            MyViewHolder holder = new MyViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.serial_number.setText(mAdapt[position]);
        }

        @Override
        public int getItemCount() {
            return <size of your string array list>;
        }

        class MyViewHolder extends RecyclerView.ViewHolder
        {
            TextView serial_number;

            public MyViewHolder(View itemView) {
                super(itemView);
                serial_number = (TextView)itemView.findViewById(R.id.serialNo_CL);
            }
        }
    }*/
    /*public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.RestaurantViewHolder> {
        private ArrayList<String> mRestaurants = new ArrayList<>();
        private Context mContext;

        public CustomAdapter(Context context, ArrayList<String> restaurants) {
            mContext = context;
            mRestaurants = restaurants;
        }

        @Override
        public CustomAdapter.RestaurantViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_item, parent, false);
            RestaurantViewHolder viewHolder = new RestaurantViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(CustomAdapter.RestaurantViewHolder holder, int position) {
            holder.bindRestaurant(mRestaurants.get(position));
        }

        @Override
        public int getItemCount() {
            return mRestaurants.size();
        }

        class RestaurantViewHolder extends RecyclerView.ViewHolder
        {
            TextView serial_number;

            public RestaurantViewHolder(View itemView) {
                super(itemView);
                serial_number = (TextView)itemView.findViewById(R.id.menu_item_name);
            }


            public void bindRestaurant(String restaurant) {
                serial_number.setText(restaurant);
                //mCategoryTextView.setText(restaurant.getCategories().get(0));
                //mRatingTextView.setText("Rating: " + restaurant.getRating() + "/5");
            }
        }
    }*/



    private static final String[] PROJECTION = new String[]{
            DbContract.MenuEntry._ID,
            DbContract.MenuEntry.COLUMN_NAME,
            DbContract.MenuEntry.COLUMN_DESCRIPTION,
            DbContract.MenuEntry.COLUMN_SCHEDULED,
            //DbContract.MenuEntry.TASK_ID,
//            DbContract.MenuEntry.COLUMN_PHOTO
    };
    /*private void updateUI() {
        ArrayList<String> taskList = new ArrayList<>();
        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor cursor = db.query(DbContract.MenuEntry.TABLE1,
                new String[]{DbContract.MenuEntry._ID, DbContract.MenuEntry.COLUMN_NAME,DbContract.MenuEntry.COLUMN_DESCRIPTION,DbContract.MenuEntry.COLUMN_SCHEDULED},
                null, null, null, null, null);
        while (cursor.moveToNext()) {
            int idx = cursor.getColumnIndex(DbContract.MenuEntry.COLUMN_NAME);
            taskList.add(cursor.getString(idx));
        }

        if (mAdapter == null) {
            mAdapter = new ArrayAdapter<>(this,
                    R.layout.menu_item,
                    R.id.menu_item_name,
                    taskList);
            recyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.clear();
            mAdapter.addAll(taskList);
            mAdapter.notifyDataSetChanged();
        }

        cursor.close();
        db.close();
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mHelper = new DbHelper(this);
        SQLiteDatabase db = mHelper.getReadableDatabase();
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        adapter = new MenuListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        getSupportLoaderManager().initLoader(0, null, this);
        Cursor cursor = db.query(DbContract.MenuEntry.TABLE1,
                new String[]{DbContract.MenuEntry._ID, DbContract.MenuEntry.COLUMN_NAME,DbContract.MenuEntry.COLUMN_DESCRIPTION,DbContract.MenuEntry.COLUMN_SCHEDULED,},
                null, null, null, null, null);
        /*while(cursor.moveToNext()) {
            int idx = cursor.getColumnIndex(DbContract.MenuEntry.COLUMN_NAME);
            Log.d(TAG, "Task: " + cursor.getString(idx));
        }*/
        cursor.close();
        db.close();

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        String queryUri = DbContract.MenuEntry.CONTENT_URI.toString();
        return new CursorLoader(this, Uri.parse(queryUri), PROJECTION, null, null, null);

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        adapter.setData(data);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

        adapter.setData(null);

    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_search:
                //onSearchRequested();
                return true;
            default:
                return false;
        }
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_task:
                //Log.d(TAG, "Add a new task");

                //taskEditText = (EditText) findViewById(R.id.taskEditText);
                AlertDialog.Builder dialog = new AlertDialog.Builder(this)
                        .setTitle("Title");
                LinearLayout layout = new LinearLayout(MainActivity.this);
                layout.setOrientation(LinearLayout.VERTICAL);
                final EditText taskEditText_title = new EditText(this);
                layout.addView(taskEditText_title);
                final EditText taskEditText_des = new EditText(this);
                layout.addView(taskEditText_des);
                final EditText taskEditText_date = new EditText(this);
                layout.addView(taskEditText_date);
                dialog.setView(layout);//.setMessage("What do you want to do next?")
                        //.setHint("Title")
                        //.setView(taskEditText_title)
//                        .setTitle("Description")
//                        .setView(taskEditText_des)
//                        .setTitle("Date")
//                        .setView(taskEditText_date)
                       dialog.setPositiveButton("Save", new DialogInterface. OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String title = String.valueOf(taskEditText_title.getText());
                                String des= String.valueOf(taskEditText_des.getText());
                                String date= String.valueOf(taskEditText_date.getText());
                                SQLiteDatabase db = mHelper.getWritableDatabase();
                                Log.d(TAG, "Task to add: " + title);
                                //db.execSQL("INSERT INTO tasks ("+DbContract.MenuEntry._ID+",name,description,scheduled)"+"VALUES (7,"+ title+","+ des+","+ date+");");
                              //  Log.d(TAG, "Task to add: " + title);
                                //SQLiteDatabase db = mHelper.getWritableDatabase();
                                ContentValues values = new ContentValues();
                                values.put(DbContract.MenuEntry._ID, 5);
                                values.put(DbContract.MenuEntry.COLUMN_NAME, title);
                                values.put(DbContract.MenuEntry.COLUMN_DESCRIPTION, des);
                                values.put(DbContract.MenuEntry.COLUMN_SCHEDULED, date);


                               db.insertWithOnConflict(DbContract.MenuEntry.TABLE1,
                                        null,
                                        values,
                                        SQLiteDatabase.CONFLICT_REPLACE);
                                db.close();
                            }
                        });
                       // dialog.setNegativeButton("Cancel", null);
                        dialog.create();
                dialog.show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
