import java.io.File;
import java.io.FileNotFoundException;

public class Basic {

    public static void main(String[] args) throws FileNotFoundException {
        
        if(args.length == 0) {
            System.out.println("Please enter the file or files that will be copied.");
            System.exit(0);
        }

        // creating destination folder
        File dest = FileOperations.createFolder("dest");


        for(int i = 0; i < args.length; i++) {
            File source = new File(args[i]);

            // handling errors
            if(source.isDirectory() ) {
                System.out.println("\"" + args[i] + "\"" + " is a directory. A file name was expected.");
                continue;
            }

            if(!source.exists() ) throw new FileNotFoundException("\"" + args[i] + "\"" + " does not exist. Please enter an existing file");

            if(!source.canRead() ) throw new FileNotFoundException("\"" + args[i] + "\"" + "is not readable.");

            if(!source.canWrite() ) throw new FileNotFoundException("\"" + args[i] + "\"" + "cannot be modified.");

            if(!source.isFile() ) throw new FileNotFoundException("\"" + args[i] + "\"" + "is not a normal file.");

            
            // checking destination folder to see whether there is a file with the same name or not
            if(FileOperations.contains(source, dest)) {
                System.out.println("There is already a copied file with the name " + "\"" + source.getName() + "\"" + ". Please enter files with different names.");
                continue;
            }
            

            // creating entered file in the destination folder
            String fileName = source.getName();
            File dest_file = new File(dest, fileName);    
            FileOperations.createFile(dest_file);


            System.out.println(">Started: " + source + "...");
           

            // copying given file into new created file
            FileOperations.copyFile(source, dest_file);


            System.out.println(">Finished FILE: " + source);


            // calculating the size of the file
            String size = FileOperations.getSize(source);

            System.out.println(">Total " + size + " were copied!");
        }
    }
    
}
