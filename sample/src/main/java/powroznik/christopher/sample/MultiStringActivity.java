package powroznik.christopher.sample;


import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import powroznik.christopher.save_to_activity.SaveToActivity;

public class MultiStringActivity extends AppCompatActivity {

    SaveToActivity saveToActivity = new SaveToActivity();
    HashMap<String, String> hashMap = saveToActivity.startHashMap();

    public void saveInformation(HashMap<String, String> newHashMap) {
        hashMap = newHashMap;
    }

    public HashMap<String, String> loadInformation() {
        return hashMap;
    }

    List<String> putKeys = new ArrayList<>();
    List<String> putStrings = new ArrayList<>();
    List<String> getKeys = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.multi_string_activity);
        final EditText putKey = (EditText) findViewById(R.id.putKey);
        final EditText putString = (EditText) findViewById(R.id.putString);
        final EditText getKey = (EditText) findViewById(R.id.getKey);
        final TextView getString = (TextView) findViewById(R.id.getValue);
        final TextView putArray = (TextView) findViewById(R.id.putArray);
        final TextView getArray = (TextView) findViewById(R.id.getArray);
        Button putButton = (Button) findViewById(R.id.put);
        Button getButton = (Button) findViewById(R.id.get);
        Button openFragment = (Button) findViewById(R.id.openFragment);
        Button putAddToArray = (Button) findViewById(R.id.putAddToArray);
        Button getAddToArray = (Button) findViewById(R.id.getAddToArray);

        //Put add to array
        if (putAddToArray != null && putKey != null && putString != null && putArray != null) {
            putAddToArray.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String key = putKey.getText().toString();
                    String string = putString.getText().toString();

                    if (!key.isEmpty() && !string.isEmpty()) {

                        String display = "";

                        putKeys.add(key);
                        putStrings.add(string);

                        //Display for UX
                        for (int i = 0; i < putKeys.size(); i++) {
                            display += putKeys.get(i) + ": " + putStrings.get(i) + " \n";
                        }

                        putArray.setText(display);

                    }

                }
            });
        }

        //put
        if (putButton != null && putArray != null) {
            putButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //convert to String[]
                    String[] keysArr = putKeys.toArray(new String[putKeys.size()]);
                    String[] stringsArr = putStrings.toArray(new String[putStrings.size()]);
                    try {
                        saveToActivity.putStrings(MultiStringActivity.this, keysArr, stringsArr);

                        putKeys.clear();
                        putStrings.clear();
                        putArray.setText("");

                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        //Put add to array
        if (getAddToArray != null && getKey != null && getArray != null) {
            getAddToArray.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String key = getKey.getText().toString();

                    if (!key.isEmpty()) {

                        String display = "";

                        getKeys.add(key);

                        //Display for UX
                        for (int i = 0; i < getKeys.size(); i++) {
                            display += getKeys.get(i) + " \n";
                        }

                        getArray.setText(display);

                    }

                }
            });
        }

        //get
        if (getButton != null && getArray != null && getString != null) {
            getButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //convert to String[]
                    String[] keysArr = getKeys.toArray(new String[getKeys.size()]);

                    try {
                        String[] result = saveToActivity.getStrings(MultiStringActivity.this, keysArr);

                        String display = "";

                        for (String s : result) {
                            display += s + " \n";
                        }

                        getString.setText(display);

                        getKeys.clear();
                        getArray.setText("");

                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }

                }
            });
        }

        //Open Fragment
        if (openFragment != null) {
            openFragment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MultiStringFragment multiStringFragment = new MultiStringFragment();
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.parent, multiStringFragment);
                    fragmentTransaction.commit();
                }
            });
        }

    }

}
