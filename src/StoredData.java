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

        //prwta na ele3xw ama uparxei arxeio

        BufferedReader br = new BufferedReader(new FileReader("storedData.csv"));
        if (br.readLine() == null) {
            System.out.println("Error");
        }

        //diaforetika dhmiourgw to arxeio storedData!
        try {
            PrintWriter writer = new PrintWriter(new File("storedData.csv"));

            int i = 0;
            while (i < data.length) {
                writer.println(data[i]);
                i++;
            }
            writer.close();
        }catch (FileNotFoundException e) {
                System.out.println("Error: " + e.getMessage());
            }

    }
}
