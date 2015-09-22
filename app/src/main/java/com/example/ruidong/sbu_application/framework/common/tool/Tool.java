package com.example.ruidong.sbu_application.framework.common.tool;

import android.content.Context;
import android.os.Environment;

import com.example.ruidong.sbu_application.event.service.CSVReader;
import com.example.ruidong.sbu_application.framework.POI;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Ruidong on 9/21/2015.
 */
public class Tool {

    public Tool(){

    }

    public static ArrayList readCSV(){
        String next[] = {};
        ArrayList<String[]> list = new ArrayList<String[]>();

        try {
            File file = new File(Environment.getExternalStorageDirectory(),
                    "events.csv");
            InputStream is = new FileInputStream(file);
            CSVReader reader = new CSVReader(new InputStreamReader(is));
            while(true) {
                next = reader.readNext();
                if(next != null) {
                    list.add(next);
                } else {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<POI> removeDuplicateWithOrder(ArrayList<POI> list) {
        Set<POI> set = new HashSet<POI>(list.size());
        set.addAll(list);
        ArrayList<POI> newList = new ArrayList<POI>(set.size());
        newList.addAll(set);
        return newList;
    }
    public static void write(Context context, Object nameOfClass,String filename) {
        File directory = new File(context.getFilesDir().getAbsolutePath()
                + File.separator + "serlization");
        if (!directory.exists()) {
            directory.mkdirs();
        }

        String fileName = filename;
        ObjectOutput out = null;

        try {
            out = new ObjectOutputStream(new FileOutputStream(directory
                    + File.separator + fileName));
            out.writeObject(nameOfClass);
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<String> read(Context context,String filename) {

        ObjectInputStream input = null;
        ArrayList<String> ReturnClass = null;
        String fileName = filename;
        File directory = new File(context.getFilesDir().getAbsolutePath()
                + File.separator + "serlization");
        try {

            input = new ObjectInputStream(new FileInputStream(directory
                    + File.separator + fileName));
            ReturnClass = (ArrayList<String>) input.readObject();
            input.close();

        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return ReturnClass;
    }
}
