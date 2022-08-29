import java.io.File;
import java.io.FileNotFoundException;

public class Medium {

    public static void main(String[] args) throws FileNotFoundException {

        if(args.length == 0) {
            System.out.println("Please enter the file(s) or directori(es) that will be copied.");
            System.exit(0);
        }

        if(args.length == 1) {
            System.out.println("At least two inputs were expected: (1) a list of directories; (2) the name of destination folder.");
            System.exit(0);
        }

        // last entered input should be the name of destination folder
        String name = args[args.length - 1];

        
        //creating destination folder
        File dest = FileOperations.createFolder(name);

        for(int i = 0; i < args.length - 1; i++) {
            File source = new File(args[i]);

            // handling errors
            if(!source.exists() ) throw new FileNotFoundException("\"" + args[i] + "\"" + " does not exist. Please enter an existing file or directory.");

            if(!source.canRead() ) throw new FileNotFoundException("\"" + args[i] + "\"" + "is not readable.");

            if(!source.canWrite() ) throw new FileNotFoundException("\"" + args[i] + "\"" + "cannot be modified.");

            if(!(source.isDirectory() || source.isFile() ) ) throw new FileNotFoundException("\"" + args[i] + "\"" + " is neither a normal file nor directory. Please enter a valid input.");

        
            // creating the folder for calling next function
            File dest_folder = new File(dest, source.getName() );
            
            // calling recursive function
            copy(source, dest_folder, "");
        }
    }

    private static void copy(File source, File dest, String tabs) {

        if(source.isDirectory() ) {
            // create folders with parent directories
            dest.mkdirs();


            System.out.println(tabs + ">Started: " + source + "...");


            // analyzing the structure of the directory
            String[] sub_content_name = source.list();

            for(String file : sub_content_name) {
                // inputs for next recursive call
                File source_file = new File(source, file); 
                File dest_file = new File(dest, file);

                copy(source_file, dest_file, tabs + "\t"); // each time one indentation will be added for printing
            }

            System.out.println(tabs + ">Finished FOLDER: " + source + "\n");
        } else {
            // checking destination folder to see whether there is a file with the same name or not
            File f = new File (dest.getParent() );

            if(FileOperations.contains(source, f) ) {
                System.out.println(tabs + "There is already a copied file with the name " + "\"" + source.getName() + "\"" + ". Please enter files with different names.");
                return;
            }


            if(tabs == "") { // just an exceptional case if the input is not directory and 
                             // from the beginning is a file(applying basic.java's concept here)

                System.out.println(">Started: " + source + "...");

                // creating entered file in the destination folder
                String fileName = source.getName();
                File dest_file = new File(dest, fileName);    
                FileOperations.createFile(dest_file);


                System.out.println(">Finished FILE: " + source);


                // calculating the size of the file
                String size = FileOperations.getSize(source);

                System.out.println(">Total " + size + " were copied!");
            } else { // if the file is obtained from input entered as directory, 
                     // then we should do the indentation according to folders

                System.out.println(tabs + "\t" + ">Started: " + source + "...");

                FileOperations.copyFile(source, dest);

                System.out.println(tabs + "\t" + ">Finished FILE: " + source);


                // calculating the size of the file
                String size = FileOperations.getSize(source);

                System.out.println(tabs + "\t" + ">Total " + size + " were copied!");
            }

        }

    }

}