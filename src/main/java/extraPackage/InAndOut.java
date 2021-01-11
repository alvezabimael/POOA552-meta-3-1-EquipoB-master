package extraPackage;

import flujodetrabajo.FlujoDeTrabajo;

import java.util.*;
import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;

public class InAndOut {
    public static void serialize (FlujoDeTrabajo flujoDeTrabajo)
    {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("data.puga"))){
            oos.writeObject(flujoDeTrabajo);
        }catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public static FlujoDeTrabajo deserialize ()
    {
        FlujoDeTrabajo flujoDeTrabajo = null;
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream("data.puga")))
        {
            flujoDeTrabajo = (FlujoDeTrabajo) ois.readObject();

        }catch (ClassNotFoundException | IOException e)
        {
            e.printStackTrace();
        }
        return flujoDeTrabajo;
    }
}
