package gr.teicm.ieee.quizandroidclient.logic;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Owner: IEEE Student Branch - TEI of Central Macedonia
 * Developer: Jordan Kostelidis
 * Date: 25/9/2017
 * License: MIT License
 */
public class FileHandler {

    public void saveFile(Object objectForSave, FileOutputStream fos) throws IOException {
        ObjectOutputStream oos;
        oos = new ObjectOutputStream(fos);
        oos.writeObject(objectForSave);
        oos.close();
        fos.close();
    }

    public Object readFile(FileInputStream fis) throws IOException, ClassNotFoundException {

        Object result;

        ObjectInputStream is = new ObjectInputStream(fis);
        result = is.readObject();
        is.close();
        fis.close();

        return result;
    }

}