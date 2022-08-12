import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Settings {
    public String sortType = "-a";
    public String dataType;
    public String outputFile;
    public List<String> inputFiles = new ArrayList<String>();

    public Settings(String[] args){
        if(args.length < 3) throw new IllegalArgumentException("too few arguments");

        int argNum = 0;
        if(Objects.equals(args[argNum], "-a") || Objects.equals(args[argNum], "-d")) {
            this.sortType = args[argNum];
            argNum++;
        }
        if(Objects.equals(args[argNum], "-s") || Objects.equals(args[argNum], "-i")) {
            this.dataType = args[argNum];
            argNum++;
        }
        if(this.dataType == null) throw new IllegalArgumentException("wrong arguments");
        this.outputFile = args[argNum];
        argNum++;
        this.inputFiles.addAll(Arrays.asList(args).subList(argNum, args.length));
        if (this.inputFiles.size() == 1){
            System.out.println("Error: minimum 2 files required");
            System.exit(0);
        }
    }
}
