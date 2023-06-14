import Entidades.HashTag;
import Entidades.Piloto;
import Entidades.Tweet;
import Entidades.User;
import TADs.Hash.*;
import TADs.Heap.MyHeap;
import TADs.Heap.MyHeapImpl;
import TADs.Heap.NodoTreeBin;
import TADs.Lista.LL;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CSVReader {
    static FileManager fileManager = new FileManager();
    static HashTabla<Integer, String> tweets = new HashTablaImpl<>();
    private static String DATA_SET = fileManager.getFilePath_DataSet();
    private static String DRIVERS = fileManager.getFilePath_Drivers();

    public static void leerCSV() {
        System.out.println("Iniciando Lectura");
        try {
            Reader in = new FileReader(DATA_SET);
            Iterable<CSVRecord> records = CSVFormat.EXCEL.withFirstRecordAsHeader().parse(in);
            for (CSVRecord record : records) {
                Integer id = Integer.parseInt(record.get(""));
                System.out.println(id);
//                String user_name = record.get("user_name");
//                String user_location = record.get("user_location");
//                String user_description = record.get("user_description");
//                String user_created = record.get("user_created");
//                String user_followers = record.get("user_followers");
//                String user_friends = record.get("user_friends");
//                String user_favourites = record.get("user_favourites");
//                String user_verified = record.get("user_verified");
//                String date = record.get("date");
                String text = record.get("text").replaceAll("\\s", "");
//                String hashtags = record.get("hashtags");
//                String source = record.get("source");
//                String is_retweet = record.get("is_retweet");

                tweets.put(id, text);

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


    // ----------  ----------  Primer funcion ----------  ----------
    public static void topPilotos(int mesInput, int anoInput) {

        // ----------  ----------  esto es para leer los pilotos del .txt  ----------  ----------
        // Crear tu tabla hash para llevar la cuenta de las menciones de cada piloto
        LinearProbingHashTable<String, Integer> contadorPilotos = new LinearProbingHashTable<>();

        String archivo = DRIVERS;
        List<String> listaPilotos = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(Paths.get(archivo))) {
            String line;
            while ((line = reader.readLine()) != null) {
                listaPilotos.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Convierte la lista de pilotos a un array
        String[] pilotos = listaPilotos.toArray(new String[0]);


        // Inicializar los contadores de todos los pilotos a 0
        for (String piloto : pilotos) {
            contadorPilotos.put(piloto, 0);
        }

        try {
            Reader in = new FileReader(DATA_SET);
            Iterable<CSVRecord> records = CSVFormat.EXCEL.withFirstRecordAsHeader().parse(in);
            int contadorTweets = 0;
            int conRecord = 0;

            for (CSVRecord record : records) {

                conRecord++;


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

                if (anoInt == anoInput && mesInt == mesInput) contadorTweets++;

                // Comprueba cada piloto
                for (String piloto : pilotos) {

                    String[] partesPiloto = piloto.split(" ");
                    String nombrePiloto = partesPiloto[0];
                    String apePiloto = partesPiloto[1];


                    if (text.contains(nombrePiloto) || text.contains(apePiloto) && anoInt == anoInput && mesInt == mesInput) {
                        // Incrementa el contador para este piloto
                        contadorPilotos.put(piloto, contadorPilotos.get(piloto) + 1);
                    }
                }

            }
            System.out.println("tweets en ese mes: " + contadorTweets);
            System.out.println("tuplas leidas: " + conRecord);

            LL<Piloto> pilotosOrdenados = new LL<>();


// Imprimir los contadores
            for (String piloto : pilotos) {
//                System.out.println(piloto + ": " + contadorPilotos.get(piloto));
                int contPiloto = contadorPilotos.get(piloto);
                Piloto tempPiloto = new Piloto(piloto, contPiloto);
                pilotosOrdenados.add(tempPiloto);
            }

            pilotosOrdenados.sort();

            for (int i = 0; i < 10; i++) {
                System.out.println(i + 1 + "." + pilotosOrdenados.get(i));
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //    // ----------  ----------  Segunda funcion ----------  ----------
    public static void topUsuariosTweets() {
        try {
            Reader in = new FileReader(DATA_SET);
            Iterable<CSVRecord> records = CSVFormat.EXCEL.withFirstRecordAsHeader().parse(in);
            int contUsuarios = 0;

            HashTable<User, Integer> usuariosReg = new LinearProbingHashTable<>();

            for (CSVRecord record : records) {
                //recorro cada tupla y obtengo las datos q necesito

                String column = record.get("");
                Integer id = Integer.parseInt(column); // id del tweet

                String user_name = record.get("user_name"); //nombre

                String user_verified = record.get("user_verified"); /// verificado

                //creo el usuario y el tweet temporal
                User tempUsuario = new User(user_name, user_verified);
                Tweet tempTweet = new Tweet(id, user_name);
                tempUsuario.addTweet(tempTweet);

                if (!usuariosReg.contains(tempUsuario)) {
                    usuariosReg.put(tempUsuario, 1);
                    contUsuarios++;
                } else {
                    usuariosReg.put(tempUsuario, usuariosReg.get(tempUsuario) + 1);
                }


            }

            MyHeap<Integer, User> heapPrueba = new MyHeapImpl<>("maximo");


            for (Entry<User, Integer> entry : ((LinearProbingHashTable<User, Integer>) usuariosReg).getEntries()) {
                User user = entry.getKey();
                int tweets = entry.getValue();
                heapPrueba.insert(tweets, user);

//                System.out.println("Usuario: " + user.getName() + ", Cantidad de Tweets: " + tweets + ", Verificado: " + user.getVerificado() );
            }

            NodoTreeBin<Integer, User> user = new NodoTreeBin<>();
            HashTable<String, Integer> usuariosYaRecorrido = new LinearProbingHashTable<>();
            // devuelvo el usuario y sus favoritos
            int i = 1;
            while (i <= 15) {
                user = heapPrueba.delete();
                User user_name = user.getData(); // nombre usuario
                Integer tweets = user.getKey(); //cantidad de favoritos
                if (!usuariosYaRecorrido.contains(user_name.getName())) {
                    usuariosYaRecorrido.put(user_name.getName(), 0);
                    System.out.println(i + ") " + user_name + " " + tweets);
                    ++i;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // ----------  ----------  Tercera funcion ----------  ----------
    public static void hashtagDistintos(String fecha) {
        try {
            Reader in = new FileReader(DATA_SET);
            Iterable<CSVRecord> records = CSVFormat.EXCEL.withFirstRecordAsHeader().parse(in);

            LinearProbingHashTable<HashTag, Integer> hashTagsReg = new LinearProbingHashTable<>();

            for (CSVRecord record : records) {
                String date = record.get("date");
                String hashtags = record.get("hashtags");

                String[] hashtagsParts = hashtags.split(",");

                for (String parteHashtag : hashtagsParts) {
                    String parteHashSinCorcheteIzq = parteHashtag.replace("[", "");
                    String parteHashSinCorcheteDer = parteHashSinCorcheteIzq.replace("]", "");

                    HashTag hashTagIndividual = new HashTag(parteHashSinCorcheteDer);

                    if (!hashTagsReg.contains(hashTagIndividual) && date.contains(fecha)) { //en el caso que no este registrado
                        hashTagsReg.put(hashTagIndividual, 1);
                    } else if (hashTagsReg.contains(hashTagIndividual) && date.contains(fecha)) { //en el caso q ya este registrado
                        hashTagsReg.put(hashTagIndividual, hashTagsReg.get(hashTagIndividual) + 1);
                    }
                }
            }

            int contHashtags = 0;

            for (Entry<HashTag, Integer> entry : hashTagsReg.getEntries()) {
                contHashtags++;
                System.out.println(entry.getKey().getText());
            }

            System.out.println("Cantidad de hashtags: " + contHashtags);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ----------  ----------  Cuarta funcion ----------  ---------- *Michel*
    public static void hashtagMasUsado(String fecha) {
        try {
            Reader in = new FileReader(DATA_SET);
            Iterable<CSVRecord> records = CSVFormat.EXCEL.withFirstRecordAsHeader().parse(in);

            LinearProbingHashTable<String, Integer> hashTagsReg = new LinearProbingHashTable<>();

            for (CSVRecord record : records) {
                String date = record.get("date");
                String hashtags = record.get("hashtags");
                // limpia la data eliminando corchetes
                String parteHashSinCorcheteIzq = hashtags.replace("[", "");
                String parteHashSinCorcheteDer = parteHashSinCorcheteIzq.replace("]", "");
                // independisa los hashtags de la lista del dataset
                String[] hashtagsSplit = parteHashSinCorcheteDer.split(",");

                // chequea la fecha
                if (date.contains(fecha)) {
                    for (String parteHashtag : hashtagsSplit) {
                        // elimina espacios y comillas para procesar y chequear los hashtagas
                        String value = parteHashtag.replaceAll("[\\s'']", "");
                        if (!hashTagsReg.contains(value)) { //en el caso que no este registrado
                            hashTagsReg.put(value, 1);
                        } else if (hashTagsReg.contains(value)) { //en el caso q ya este registrado
                            hashTagsReg.put(value, hashTagsReg.get(value) + 1);
                        }
                    }
                }
            }

            int contHashtags = 0;
            String hashtag = " ";

            for (Entry<String, Integer> entry : hashTagsReg.getEntries()) {
                // controla en quedarce con aquel hashtag queaparece con mayor frecuencia y que nosea F1 o f1
                if (entry.getValue() > contHashtags
                        && !entry.getKey().equals("F1")
                        && !entry.getKey().equals("f1")) {
                    hashtag = entry.getKey();
                    contHashtags = entry.getValue();
                }
            }
            // imprime por salida estandar el hashtag de mas aparece para la fecha ingresada
            //en caso que no exista alguno para la fecha ingresada se advierte que no hay Hashtag para ese dia
            if (!hashtag.equals(" ")) {
                System.out.println("Hashtags más usado el " + fecha + ": " + hashtag);
            } else {
                System.out.println("No hay Hashtags para el día " + fecha);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ----------  ----------  Quinta funcion ----------  ---------- *Michel*
    public static void topSiteCuentas() {
        try {
            Reader in = new FileReader(DATA_SET);
            Iterable<CSVRecord> records = CSVFormat.EXCEL.withFirstRecordAsHeader().parse(in);

            MyHeap<Integer, String> cuentas = new MyHeapImpl<>("maximo");

            for (CSVRecord record : records) {
                // limpia los espacios y recolecta nombre y favoritos
                String user_name = record.get("user_name").replaceAll("\\s", ""); //nombre usuario
                String user_favourites = record.get("user_favourites"); //cantidad de favoritos
                Integer user_favouritesInt = 0;

                boolean bandera = false;
                // limpia los datos de la columna favoritos y los lleva a valores puros Integer
                if (user_favourites.contains(".") && !user_favourites.contains("-") && !user_favourites.contains(":")) {
                    double doubleValue = Double.parseDouble(user_favourites);
                    user_favouritesInt = (int) doubleValue;
                    bandera = true;
                } else if (!user_favourites.contains("-") && !user_favourites.contains(":")) {
                    user_favouritesInt = Integer.parseInt(user_favourites);
                    bandera = true;
                }
                // en una estructura Heap carga los datos de cantidad de favoritos segun cada usuario
                // se decide esta estrucura ya que implementa un MAXheap organizado desde mayor a menor
                // usuado la cantidad de favoritos como clave
                if (bandera) {
                    cuentas.insert(user_favouritesInt, user_name);
                }
            }

            // como la estrucura Maxheap ya tiene en su nodo raiz el mayor usuario con favoritos
            // resta ir retirando las raizes en caso que este repetido el usuario se tomara
            // el primero ya que la estrucura MAXheap lo organizo estando primero el de mayor al momento de favoritos
            NodoTreeBin<Integer, String> user = new NodoTreeBin<>();
            HashTable<String, Integer> usuariosYaRecorrido = new LinearProbingHashTable<>();
            // devuelvo el usuario y sus favoritos
            int i = 1;
            while (i <= 7) {
                user = cuentas.delete();
                String user_name = user.getData(); // nombre usuario
                Integer user_favourites = user.getKey(); //cantidad de favoritos
                if (!usuariosYaRecorrido.contains(user_name)) {
                    usuariosYaRecorrido.put(user_name, 0);
                    System.out.println(i + ") " + user_name + " " + user_favourites);
                    ++i;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // ----------  ----------  Sexta funcion ----------  ---------- *Michel*
    public static void tweetsFrase(String frase) throws Exception {
        try {
//            Reader in = new FileReader(DATA_SET);
//            Iterable<CSVRecord> records = CSVFormat.EXCEL.withFirstRecordAsHeader().parse(in);
            int count = 0;
            String fraseValue = frase.replaceAll("\\s", "");
            for (NodoHash<Integer, String> entry : tweets.getTabla()) {
                //limpia los datos y chequea tweet a tweet la existencia de la palabra o frase solicitada
                if (entry != null && entry.getData().contains(fraseValue)) {
                    count++;
                }
            }
            // retorna por salida estandar la frecuencia de la palabra o frase ingresada por consola
            if (count != 0) {
                System.out.println("Existen " + count + " tweets con la frase/palabra " + "'" + frase + "'");
            } else {
                System.out.println("No hay tweets con la frase/palabra " + frase);
            }
        } catch (Exception e) {
            throw new Exception("Error en metodo tweetsFrase");
        }
    }
}
