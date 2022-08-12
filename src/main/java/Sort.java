import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Sort {
    public static void main(String[] args){
        Settings settings = new Settings(args); // processing of command line arguments
        List<BufferedReader> inputFiles = new LinkedList<BufferedReader>(); // list for input files readers
        for(String inputFileName: settings.inputFiles){
            try {
                inputFiles.add(new BufferedReader(new FileReader(inputFileName)));
            } catch (IOException e) {
                System.out.println("Error: incorrect input file with name:" + inputFileName);
                e.printStackTrace();
                System.exit(0);
            }
        }

        int step = 1;
        List<File> tmpFiles = new ArrayList<File>(); // list of all created temporary files
        List<BufferedReader> allReaders = new ArrayList<BufferedReader>(); // list of readers of this files
        try {
        while(inputFiles.size() != 1){
            List<BufferedReader> tmpInputFiles = new ArrayList<BufferedReader>(); // list of temporary files in current stage
            for(int i=0; i<inputFiles.size()-1; i=i+2){
                File tmpFile = new File(step + "_" + i / 2);
                tmpFiles.add(tmpFile);
                BufferedWriter tmpFileWriter = new BufferedWriter(new FileWriter(tmpFile));
                BufferedReader tmpFileReader = new BufferedReader(new FileReader(tmpFile));
                allReaders.add(tmpFileReader);
                String line1 = inputFiles.get(i).readLine();
                String line2 = inputFiles.get(i+1).readLine();
                String tmpLine1 = null, tmpLine2 = null;
                while(line1 != null || line2 !=null){
                    if(compareElements(line1, tmpLine1, settings.sortType, settings.dataType) && line1 != null && tmpLine1 != null){
                        System.out.println("file has elements with incorrect order, skipped: " + line1);
                        line1 = inputFiles.get(i).readLine();
                        continue;
                    }
                    else if(compareElements(line2, tmpLine2, settings.sortType, settings.dataType) && line2 != null && tmpLine2 != null){
                        System.out.println("file has elements with incorrect order, skipped: " + line2);
                        line2 = inputFiles.get(i+1).readLine();
                        continue;
                    }

                    if(compareElements(line1, line2, settings.sortType, settings.dataType)) { // comparing elements from two files
                        tmpFileWriter.write(line1); // writing down desired element to tmp file
                        tmpLine1 = line1;
                        tmpFileWriter.newLine();
                        tmpFileWriter.flush();
                        line1 = inputFiles.get(i).readLine();
                    }
                    else{
                        tmpFileWriter.write(line2);
                        tmpLine2 = line2;
                        tmpFileWriter.newLine();
                        tmpFileWriter.flush();
                        line2 = inputFiles.get(i+1).readLine();
                    }
                }
                tmpFileWriter.close();
                tmpInputFiles.add(tmpFileReader);
                if(inputFiles.size() % 2 == 1) tmpInputFiles.add(inputFiles.get(inputFiles.size()-1)); // case of odd number of input files
            }

            inputFiles.clear();
            inputFiles = tmpInputFiles;
            step+=1;
        }
        inputFiles.get(0).close();
        for(int i=0; i<tmpFiles.size()-1; i++){ // cleaning temporary files
            allReaders.get(i).close();
            tmpFiles.get(i).delete();
        }
        new File(settings.outputFile).delete(); // cleaning old output file
        if (tmpFiles.get(tmpFiles.size()-1).renameTo(new File(settings.outputFile))) // renaming last stage to output name
            System.out.println("operation completed successfully");
        else
            System.out.println("Error: cant save final result in " + settings.outputFile);

        } catch (IOException e){
            System.out.println("Error occurred while operating with filesystem:");
            e.printStackTrace();
        }
    }

    public static boolean compareElements(String line1, String line2, String order, String dataType){ // function for comparing elements according to console args
        boolean result = true;
        if (line1 == null || line2 == null) {
            if (line1 == null) result = false;
            if (line2 == null) result = true;
        }
        else {
            if (Objects.equals(dataType, "-i"))
                try {
                    result = Integer.parseInt(line1) < Integer.parseInt(line2);
                } catch (NumberFormatException e){
                    System.out.println("Error: wrong file format");
                    System.exit(0);
                }
            if (Objects.equals(dataType, "-s"))
                result = line1.compareTo(line2) < 0;
            if (!Objects.equals(order, "-a")) return !result;
        }
        return result;
    }
}