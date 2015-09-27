package net.bushong.simpletodo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
  ArrayList<String> items;
  ArrayAdapter<String> itemsAdapter;
  ListView lvItems;
  EditText etNewItem;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    lvItems = (ListView) findViewById(R.id.lvItems);
    readItems();
    itemsAdapter = new ArrayAdapter<>(this,
            android.R.layout.simple_list_item_1, items);
    lvItems.setAdapter(itemsAdapter);
    etNewItem = (EditText) findViewById(R.id.etNewItem);
    setupListViewListener();
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  public void onAddItem(View view) {
    itemsAdapter.add(etNewItem.getText().toString());
    etNewItem.setText("");
    writeItems();
  }

  private void setupListViewListener() {
    lvItems.setOnItemLongClickListener(
            new AdapterView.OnItemLongClickListener() {
              @Override
              public boolean onItemLongClick(AdapterView<?> adapter,
                                             View item, int pos, long id) {
                items.remove(pos);
                itemsAdapter.notifyDataSetChanged();
                writeItems();
                return true;
              }
            }
    );
  }

  private void readItems() {
    File filesDir = getFilesDir();
    File todoFile = new File(filesDir, "todo.txt");
    try {
      items = new ArrayList<String>(FileUtils.readLines(todoFile));
    } catch (IOException e) {
      items = new ArrayList<String>();
    }
  }

  private void writeItems() {
    File filesDir = getFilesDir();
    File todoFile = new File(filesDir, "todo.txt");
    try {
      FileUtils.writeLines(todoFile, items);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
