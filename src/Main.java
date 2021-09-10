import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException; 
import java.util.Scanner; 

public class Main {
    public static void main(String[] args) throws Exception {
        String txt = "";

        try {
            File myObj = new File("xml.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                txt = txt + validarLinha(myReader.nextLine());
            }

            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Ocorreu um erro na leitura");
            e.printStackTrace();
        }

        try {
            File myObj = new File("json.txt");
            myObj.createNewFile();
            FileWriter myWriter = new FileWriter("json.txt");
            myWriter.write(txt);
            myWriter.close();

        } catch (IOException e) {
            System.out.println("Ocorreu um erro na escrita");
            e.printStackTrace();
        }
    }

    public static String validarLinha(String x) {
        String text = "";

        if (x.matches("^<[A-Z]+>$")) {
            text = x.replace("<", "{\"");

            text = x.contains("CATALOG") ? text.replace(">", "\":[\n") : text.replace(">", "\":{\n");
        }

        if (x.matches("<[A-Z]+>.*<\\/[A-Z]+>")) {
            text = x.replaceFirst("<", "\"").replaceFirst(">", "\":\"").replaceFirst("<\\/[A-Z]+>", "\",\n");
        }

        if (x.matches("<\\/[A-Z]+>")) {
            text = x.contains("/CATALOG") ? x.replaceFirst("<\\/[A-Z]+>", "]\n}")
                    : x.replaceFirst("<\\/[A-Z]+>", "},\n},\n");
        }
        return text;
    }
}
