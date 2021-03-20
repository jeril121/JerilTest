import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;

public class Main {

    public static String readFileAsString(String fileName)throws Exception
    {
        String data = "";
        data = new String(Files.readAllBytes(Paths.get(fileName)));
        return data;
    }

    public static void main(String[] args) throws Exception
    {
        int numberOfEmployees, startPos;
        ArrayList<Goodies> goodies = new ArrayList<>();
        String finalData;

        //change the file location if needed
        String data = readFileAsString("C:\\Users\\jeril\\Desktop\\Test\\sample_input.txt");

        //splitting data to get the number of employee from file
        String[] employeeNum = data.split("employees: ");
        numberOfEmployees = Integer.parseInt(employeeNum[1].charAt(0)+"");
        System.out.println("numberOfEmployees = " + numberOfEmployees);


        //splitting data to read name and price of goodies
        String[] lines = data.split("\\r?\\n");
        for (int i = 0 ; i< lines.length;i++){
            if (i>=4){
                String[] goodiesData = lines[i].split(": ");
                goodies.add(new Goodies(goodiesData[0],Integer.parseInt(goodiesData[1])));
            }
        }

        if (numberOfEmployees<=0||numberOfEmployees> goodies.size()){
            System.out.println("Invalid Entry");
            return;
        }

        //sorting array to ascending order
        Collections.sort(goodies);


        //startPos is minimum position of the goodies
        startPos = 0;

        //finding position of lowest priced goody
        for (int j = 0;j<goodies.size()-numberOfEmployees;j++){
            if ((goodies.get(startPos+(numberOfEmployees-1)).getPrice()-goodies.get(startPos).getPrice())>(goodies.get(j+numberOfEmployees).getPrice()-goodies.get(j+1).getPrice())){
                //changing startPos if other minimum is found
                startPos=j+1;
            }
        }

        //writing goodies name to final data
        finalData = "The goodies selected for distribution are:\n\n";
        for (int k=startPos;k<(startPos+numberOfEmployees);k++) {
            finalData =finalData+goodies.get(k).getName() + ":" + goodies.get(k).getPrice() + "\n";
        }
        finalData =finalData+"\nAnd the difference between the chosen goodie with highest price and the lowest price is "+(goodies.get(startPos+numberOfEmployees-1).getPrice()-goodies.get(startPos).getPrice());

        System.out.print(finalData);


        //writing to file
        try (PrintWriter out = new PrintWriter("C:\\Users\\jeril\\Desktop\\Test\\sample_output.txt")) {
            out.println(finalData);
        }


    }

    //goodies class to store price and name
    public static class Goodies implements Comparable{
        private int price;
        private String name;

        public Goodies(String name,int price) {
            this.price = price;
            this.name = name;
        }

        public int getPrice() {
            return price;
        }

        public String getName() {
            return name;
        }


        //used for sorting class with price
        @Override
        public int compareTo(Object o) {
            int comparePrice=((Goodies)o).getPrice();
            /* For Ascending order*/
            return this.price-comparePrice;
        }
    }
}
