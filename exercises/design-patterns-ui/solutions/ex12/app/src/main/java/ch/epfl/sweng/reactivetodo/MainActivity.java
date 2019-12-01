package ch.epfl.sweng.reactivetodo;

import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import ch.epfl.sweng.reactivetodo.models.DataStore;
import ch.epfl.sweng.reactivetodo.models.TodoItem;
import trikita.anvil.RenderableView;

import static trikita.anvil.BaseDSL.onTextChanged;
import static trikita.anvil.DSL.MATCH;
import static trikita.anvil.DSL.WRAP;
import static trikita.anvil.DSL.button;
import static trikita.anvil.DSL.checkBox;
import static trikita.anvil.DSL.checked;
import static trikita.anvil.DSL.dip;
import static trikita.anvil.DSL.editText;
import static trikita.anvil.DSL.hint;
import static trikita.anvil.DSL.linearLayout;
import static trikita.anvil.DSL.onCheckedChange;
import static trikita.anvil.DSL.onClick;
import static trikita.anvil.DSL.orientation;
import static trikita.anvil.DSL.padding;
import static trikita.anvil.DSL.scrollView;
import static trikita.anvil.DSL.size;
import static trikita.anvil.DSL.text;
import static trikita.anvil.DSL.textView;
import static trikita.anvil.DSL.weight;
import static trikita.anvil.DSL.weightSum;

public class MainActivity extends AppCompatActivity {

    // Our application state is a datastore
    private DataStore state = new DataStore();

    // Value of the new item field as user modifies it
    private String newItemCurrentInput = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new RenderableView(this) {
            @Override
            public void view() {
                linearLayout(() -> {
                    size(MATCH, MATCH);
                    padding(dip(20));
                    weightSum(1);
                    orientation(LinearLayout.VERTICAL);

                    newItemInput();

                    itemsList();
                });
            }
        });
    }

    /**
     * Renders the EditText field for adding a new item and its button
     */
    private void newItemInput() {
        linearLayout(() -> {
            size(MATCH, WRAP);
            weightSum(1);
            orientation(LinearLayout.HORIZONTAL);

            editText(() -> {
                size(0, WRAP);
                weight(1);
                hint("New item...");

                onTextChanged(s -> newItemCurrentInput = s.toString());
            });

            button(() -> {
                size(WRAP, WRAP);
                text("Add");

                onClick((view) -> {
                    TodoItem newItem = new TodoItem(newItemCurrentInput, TodoItem.Status.CURRENT);
                    state.items.add(newItem);
                    // Here Anvil.render() is automatically called to trigger a view update
                });
            });
        });
    }

    /**
     * Renders the list of existing items
     */
    private void itemsList() {
        scrollView(() -> {
            size(MATCH, 0);
            weight(1);

            linearLayout(() -> {
                size(MATCH, WRAP);
                orientation(LinearLayout.VERTICAL);

                for (final TodoItem item : state.items) {
                    itemDisplay(item);
                }
            });
        });
    }

    /**
     * Renders an item row
     *
     * @param item
     */
    private void itemDisplay(final TodoItem item) {
        linearLayout(() -> {
            size(MATCH, WRAP);
            orientation(LinearLayout.HORIZONTAL);
            weightSum(1);

            checkBox(() -> {
                size(WRAP, WRAP);
                if (item.getStatus() == TodoItem.Status.DONE) checked(true);

                onCheckedChange((CompoundButton.OnCheckedChangeListener) (buttonView, isChecked) -> {
                    if (isChecked) item.setStatus(TodoItem.Status.DONE);
                    else item.setStatus(TodoItem.Status.CURRENT);
                    // Here Anvil.render() is automatically called to trigger a view update
                });
            });

            textView(() -> {
                size(0, WRAP);
                weight(1);
                text(item.name);
            });

            button(() -> {
                size(WRAP, WRAP);
                text("X");

                onClick((view) -> {
                    state.items.remove(item);
                    // no need for ugly retrieval of corresponding UI item ;)
                    // Here Anvil.render() is automatically called to trigger a view update
                });
            });
        });
    }
}
