import java.io.*;

public class StoredData {

    /**
     * Κατασκευαστής της κλάσης StoredData.
     */
    public StoredData() {

    }

    /**
     * Μέθοδος για να αποθηκεύει δεδομένα του παιχνιδιού
     * @param names και scores πινακες για τα ονόματα των παικτών και σκορ τους.
     * @throws IOException
     */
    public void saveData(String[] names,int[] scores) throws IOException {

        File f = new File("storedData.txt");

        try {
            PrintWriter writer = new PrintWriter(new FileWriter("storedData.txt",true));

            for(int i = 0; (i < names.length && i<scores.length); i++){
               // writer.println("Game" + " " + i);
               writer.println(names[i] + "," + scores[i]);
            }
            writer.close();

        }catch(FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
