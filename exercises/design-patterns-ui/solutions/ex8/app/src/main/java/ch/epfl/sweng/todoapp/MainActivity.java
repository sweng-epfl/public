package ch.epfl.sweng.todoapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import ch.epfl.sweng.todoapp.models.DataStore;
import ch.epfl.sweng.todoapp.models.TodoItem;

public class MainActivity extends AppCompatActivity {

    // Model references
    private DataStore dataStore = new DataStore();

    // UI references
    Button addButton;
    EditText newItemField;
    LinearLayout itemList;

    // Callbacks
    View.OnClickListener onAddButtonPressed = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String text = newItemField.getText().toString();
            TodoItem item = new TodoItem(text, TodoItem.Status.CURRENT);
            // update model state
            dataStore.items.add(item);
            // also update UI state
            addItemToUI(item);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // find UI elements
        addButton = findViewById(R.id.add_button);
        newItemField = findViewById(R.id.new_item_field);
        itemList = findViewById(R.id.item_list);

        addButton.setOnClickListener(onAddButtonPressed);
    }

    /**
     * Updates the display with a new TodoItem
     *
     * @param item
     */
    private void addItemToUI(final TodoItem item) {
        View v = getLayoutInflater().inflate(R.layout.item_viewholder, null);

        TextView text = v.findViewById(R.id.item_text);
        text.setText(item.name);

        final CheckBox check = v.findViewById(R.id.item_checkbox);
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check.isChecked()) item.setStatus(TodoItem.Status.DONE);
                else item.setStatus(TodoItem.Status.CURRENT);
            }
        });

        final Button button = v.findViewById(R.id.item_delete_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int idx = dataStore.items.indexOf(item);
                itemList.removeViewAt(idx);
                dataStore.items.remove(item);
            }
        });

        // clean: delete current text in field
        newItemField.setText("");

        itemList.addView(v);
    }
}
