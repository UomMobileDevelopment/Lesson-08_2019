package gr.uom.android.lesson_07_2018_19;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         db = getBaseContext().openOrCreateDatabase(
                "teo-db",
                Context.MODE_PRIVATE,
                null);


        Button button = findViewById(R.id.btnAddData);

        button.setOnClickListener(btnListener);

    }

    private View.OnClickListener btnListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            TextView txtName = findViewById(R.id.txtName);
            TextView txtAge = findViewById(R.id.txtAge);
            String name = txtName.getText().toString();
            String age = txtAge.getText().toString();

            db.execSQL("insert into Person(name,age) " +
                    "values('"+name+"','"+age+"')");

            readDB();
        }
    };


    private void readDB(){
        Cursor cursor = db.rawQuery("select * from Person",
                null);

        StringBuilder sb = new StringBuilder();

        while(cursor.moveToNext()){
            int id = cursor.getInt(0);
            String name = cursor.getString(1);

            String resultRow = "ROW = ID: "+id+" name: "+name;
            sb.append(resultRow).append("\n");
            Log.d("teo", resultRow);
        }
        cursor.close();

        EditText txtDbOut = findViewById(R.id.txtDbOut);

        txtDbOut.setText(sb.toString());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        db.close();
    }
}
