package com.example.myapplication;

import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class MainActivity extends AppCompatActivity {
    String sURL = "http://www.mocky.io/v2/5ddcd3673400005800eae483";
    empParsing m = new empParsing();
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        m.execute(sURL);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        TreeMap<String, String> map = new TreeMap<>();
        TreeMap<String, String> map1 = new TreeMap<>();
        try {
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(m.get());
            JSONObject company = (JSONObject) jsonObject.get("company");
            JSONArray emp = (JSONArray) company.get("employees");
            JSONObject end = new JSONObject();
            JSONArray skills;
            for (int i = 0; i < emp.size(); i++) {
                end = (JSONObject) emp.get(i);
                skills = (JSONArray) end.get("skills");
                String skill ="";
                String name = (String) end.get("name");
                if (name == null) name = "имя отсутствует" + i;
                String phone_number = (String) end.get("phone_number");
                if (phone_number == null) phone_number = "телефон отсутствует";
                map.put(name, phone_number);
                for (int c = 0; c < skills.size(); c++) {
                    skill = skill+" "+skills.get(c);
                    map1.put(name, skill);
                }
            }
        }
        catch (ParseException ex) {
            ex.printStackTrace();
        }
        catch (NullPointerException ex) {
            ex.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        ArrayList<Employe> employes = new ArrayList<>();
        Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            //получение  элементов
            Map.Entry<String, String> pair = iterator.next();
            String key = pair.getKey();
            String value = pair.getValue();
            String sk = map1.get(pair.getKey());
            if(sk ==null) sk="ничего не умеет";
            employes.add(new Employe(key,"Телефон: "+value,"Навыки: "+ sk));
        }
        EmpAdapter empAdapter = new EmpAdapter(employes);
        recyclerView.setAdapter(empAdapter);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    class empParsing extends AsyncTask<String, Void, String> {
        String j="";
        protected String doInBackground(String... strings) {
            BufferedReader reader = null;
            try {
                URL url = new URL(sURL);
                reader = new BufferedReader(new InputStreamReader(url.openStream()));
                StringBuffer buffer = new StringBuffer();
                int read;
                char[] chars = new char[1024];
                while ((read = reader.read(chars)) != -1)
                    buffer.append(chars, 0, read);
                j = buffer.toString();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return j;
        }
    }
}

