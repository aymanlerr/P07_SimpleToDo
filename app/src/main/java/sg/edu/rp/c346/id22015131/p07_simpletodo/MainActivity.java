package sg.edu.rp.c346.id22015131.p07_simpletodo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    EditText etTask;
    Button btnAdd, btnClear, btnDelete;
    ListView lvTask;
    Spinner spinnerTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etTask = findViewById(R.id.editTextInput);
        btnAdd = findViewById(R.id.buttonAdd);
        btnClear = findViewById(R.id.buttonClear);
        btnDelete = findViewById(R.id.buttonDelete);
        lvTask = findViewById(R.id.listViewTask);
        spinnerTask = findViewById(R.id.spinner);

        ArrayList<String> alTask = new ArrayList<>();
        ArrayAdapter aaTask = new ArrayAdapter(this, android.R.layout.simple_list_item_1, alTask);
        lvTask.setAdapter(aaTask);

        btnAdd.setOnClickListener(v -> {
            if (etTask.getText().toString().isEmpty()) {
                etTask.setError("Enter task");
                etTask.getText().clear();
            } else {
                SimpleDateFormat currentDate = new SimpleDateFormat("dd/MM/yyyy");
                Date todayDate = new Date();
                String thisDate = currentDate.format(todayDate);

                String newTask = etTask.getText().toString() + "\n~ " + thisDate;
                alTask.add(newTask);
                etTask.getText().clear();
                aaTask.notifyDataSetChanged();
            }


        });

        btnClear.setOnClickListener(v -> {
            alTask.clear();
            aaTask.notifyDataSetChanged();
        });

        btnDelete.setOnClickListener(v -> {
            if (etTask.getText().toString().isEmpty()) {
                etTask.setError("Enter item number");
            } else {
                int pos = Integer.parseInt(etTask.getText().toString());
                if (validateArr(alTask, pos)) {
                    alTask.remove(pos - 1);
                    etTask.getText().clear();
                    aaTask.notifyDataSetChanged();
                }
            }
        });

        spinnerTask.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                CharSequence hintDelete = "Type in the index of that task to be removed";
                CharSequence hintAdd = "Type in a new task here";
                if (pos == 0) {
                    btnDelete.setEnabled(false);
                    btnAdd.setEnabled(true);
                    etTask.setHint(hintAdd);
                    etTask.setInputType(InputType.TYPE_CLASS_TEXT);
                } else if (pos == 1) {
                    btnAdd.setEnabled(false);
                    btnDelete.setEnabled(true);
                    etTask.setHint(hintDelete);
                    etTask.setInputType(InputType.TYPE_CLASS_NUMBER);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private boolean validateArr(ArrayList<String> alTask, int pos) {
        if (alTask.isEmpty()) {
            etTask.getText().clear();
            Toast.makeText(getApplicationContext(), "You don't have any task to remove", Toast.LENGTH_SHORT).show();
            return false;
        } else if (pos > alTask.size() || pos == 0) {
            Toast.makeText(getApplicationContext(), "Wrong index number", Toast.LENGTH_SHORT).show();
            etTask.getText().clear();
            return false;
        } else {
            return true;
        }
    }
}

