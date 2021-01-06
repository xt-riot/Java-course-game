import java.io.*;


public class StoredData {

    /**
     * Κατασκευαστής της κλάσης StoredData.
     */
    public StoredData() {

    }

    /**
     * Μέθοδος για να αποθηκεύει δεδομένα του παιχνιδιού.
     * @param data πίνακας απο String.
     * @throws IOException
     */
    public void saveData(String[] data) throws IOException {

        File f = new File("storedData.csv");
        //f.createNewFile();

        if(f.exists() && f.isFile()){
            //ama to arxeio uparxei
            try {
                PrintWriter writer = new PrintWriter(new FileWriter("storedData.csv",true));

                int i = 0;
                while (i < data.length) {
                    writer.println(data[i]);
                    i++;
                }
                writer.close();

            } catch (FileNotFoundException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }else
            continue;// ti continue re mpaglama den eisai sthn python :)

    }
}
