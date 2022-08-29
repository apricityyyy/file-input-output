import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileOperations{
    
    public static File createFolder(String pathname) {
        File dest_folder = new File(pathname);
        dest_folder.mkdir();

        return dest_folder;
    }

    public static void createFile(File dest) {
        try {
            dest.createNewFile();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            System.out.println("An exception: " + e.toString());
            System.exit(0);
        };
    }

    public static void copyFile(File source, File dest) {
        try(FileInputStream fis = new FileInputStream(source);
            FileOutputStream fos = new FileOutputStream(dest)) {
            int res = 0;
            
            while((res = fis.read()) != -1) {
                fos.write(res);
            }
        } catch (IOException e) {
            System.out.println("An exception: " + e.toString());
            System.exit(0);
        };
    }

    public static String getSize(File source) {
        double bytes = (double) source.length();
        int cnt = 0;

        while(bytes > 1024) {
            bytes /= 1024.0;
            cnt++;
        }

        long res = Math.round(Math.ceil(bytes));
        

        return res + (cnt == 0 ? "B" : (cnt == 1 ? "KB" : "MB" ) );
    }

    public static boolean contains(File source, File dest) {
        String[] names = dest.list();

        for(String file : names) {
            File dest_file = new File(dest, file);

            if(file.equals(source.getName() ) && source.length() == dest_file.length() ) return true;
        }

        return false;
    }
}
