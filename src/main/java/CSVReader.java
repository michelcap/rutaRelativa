import TADs.Hash.*;
import TADs.Heap.*;
import TADs.Lista.*;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.Reader;
import java.io.IOException;

public class CSVReader {

    public static void leerCSV(String[] args) {
        try {
            Reader in = new FileReader("/Users/coru/IdeaProjects/AAObligatorio/src/main/resources/f1_dataset_test.csv");
            Iterable<CSVRecord> records = CSVFormat.EXCEL.withFirstRecordAsHeader().parse(in);
            for (CSVRecord record : records) {
                String user_name = record.get("user_name");
                String user_location = record.get("user_location");
                String user_description = record.get("user_description");
                String user_created = record.get("user_created");
                String user_followers = record.get("user_followers");
                String user_friends = record.get("user_friends");
                String user_favourites = record.get("user_favourites");
                String user_verified = record.get("user_verified");
                String date = record.get("date");
                String text = record.get("text");
                String hashtags = record.get("hashtags");
                String source = record.get("source");
                String is_retweet = record.get("is_retweet");

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public static void topPilotos(int mesInput, int anoInput){
        // Crear tu tabla hash para llevar la cuenta de las menciones de cada piloto
        LinearProbingHashTable<String, Integer> contadorPilotos = new LinearProbingHashTable<>();

        String[] pilotos = {"Verstappen", "Pérez", "Leclerc", "Sainz", "Hamilton", "Russell", "Alonso", "Stroll", "Norris",
                "Piastri", "Gasly", "Ocon", "Vries", "Tsunoda", "Albon", "Sargeant", "Zhou", "Bottas", "Hülkenberg", "Magnussen"};

        // Inicializar los contadores de todos los pilotos a 0
        for (String piloto : pilotos) {
            contadorPilotos.put(piloto, 0);
        }

        try {
            Reader in = new FileReader("/Users/coru/IdeaProjects/AAObligatorio/src/main/resources/f1_dataset_test.csv");
            Iterable<CSVRecord> records = CSVFormat.EXCEL.withFirstRecordAsHeader().parse(in);

            for (CSVRecord record : records){
                //recorro cada tupla
                String text = record.get("text");
                String date = record.get("date");

                //hago el split de la fecha por - para sacar dia mes y el año con la hora
                String[] parts = date.split("-");

                if (parts.length != 3) {
                    System.out.println("Fecha mal formada: " + date);
                    continue;
                }

                //saco las partes q me interesan
                String ano = parts[0];
                String mes = parts[1];

                int anoInt = Integer.parseInt(ano);
                int mesInt = Integer.parseInt(mes);

                // Comprueba cada piloto
                for (String piloto : pilotos) {
                    if (text.contains(piloto) && anoInt == anoInput && mesInt == mesInput){
                        // Incrementa el contador para este piloto
                        contadorPilotos.put(piloto, contadorPilotos.get(piloto) + 1);
                    }
                }
            }

            Heap<String,Integer> heapPilotos = new Heap<>();

            // Imprimir los contadores
            for (String piloto : pilotos) {
                System.out.println(piloto + ": " + contadorPilotos.get(piloto));
                heapPilotos.insert(piloto,contadorPilotos.get(piloto));
            }


            LL<String> pilotosOrdenados = new LL<>();

// Extrae los pilotos del montón hasta que esté vacío
            while (!heapPilotos.isEmpty()) {
//                pilotosOrdenados.add(heapPilotos.delete());
            }

//            heapPilotos.preOrder().imprimir();
//            heapPilotos.inOrder().imprimir();
//            heapPilotos.postOrder().imprimir();

        }catch (IOException e) {
            e.printStackTrace();
        }
    }

}
