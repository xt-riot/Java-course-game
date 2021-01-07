import java.io.*;


public class StoredData {

    /**
     * Κατασκευαστής της κλάσης StoredData.
     */
    public StoredData() {

    }

    /**
     * Μέθοδος για να αποθηκεύει δεδομένα του παιχνιδιού.Με Delimiter το ","
     * @param data πίνακας απο String.
     * @throws IOException
     */
    public void saveData(String[] data) throws IOException {

        File f = new File("storedData.csv");
        //f.createNewFile();

        try {
            PrintWriter writer = new PrintWriter(new FileWriter("storedData.csv",true));

            int i = 0;
            while (i < data.length) {
                writer.print(data[i] + ",");  //ta grafei me delimeter (,)
                i++;
            }
            writer.close();

        }catch(FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
