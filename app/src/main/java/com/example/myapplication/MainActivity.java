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
    public static String sURL = "http://www.mocky.io/v2/5ddcd3673400005800eae483";
    public static String data;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        downloadData connect = new downloadData();
        Thread downloadThread = new Thread(connect);
        downloadThread.start();
        try {
            downloadThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        TreeMap<String, String> mapWithPhone = new TreeMap<>();
        TreeMap<String, String> mapWithSkills = new TreeMap<>();
        try {
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(data);
            JSONObject company = (JSONObject) jsonObject.get("company");
            JSONArray emp = (JSONArray) company.get("employees");
            JSONObject end;
            JSONArray skills;
            for (int i = 0; i < emp.size(); i++) {
                end = (JSONObject) emp.get(i);
                skills = (JSONArray) end.get("skills");
                String skill ="";
                String name = (String) end.get("name");
                if (name == null) name = "имя отсутствует" + " #" + i;
                String phone_number = (String) end.get("phone_number");
                if (phone_number == null) phone_number = "телефон отсутствует";
                mapWithPhone.put(name, phone_number);
                for (int c = 0; c < skills.size(); c++) {
                    if(skill.equals("")){skill = ""+skills.get(c);}
                    else skill = skill+", "+skills.get(c);
                    mapWithSkills.put(name, skill);
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

        ArrayList<Employee> employes = new ArrayList<>();
        Iterator<Map.Entry<String, String>> iterator = mapWithPhone.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> pair = iterator.next();
            String key = pair.getKey();
            String value = pair.getValue();
            String sk = mapWithSkills.get(pair.getKey());
            if(sk ==null) sk="ничего не умеет";
            employes.add(new Employee(key,"Телефон: "+value,"Навыки: "+ sk));
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

    public static class downloadData implements Runnable{
        public void run(){
            BufferedReader reader = null;
            try {
                URL url = new URL(sURL);
                reader = new BufferedReader(new InputStreamReader(url.openStream()));
                StringBuilder buffer = new StringBuilder();
                int read;
                char[] chars = new char[1024];
                while ((read = reader.read(chars)) != -1)
                    buffer.append(chars, 0, read);
                data = buffer.toString();
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
        }
    }
}

