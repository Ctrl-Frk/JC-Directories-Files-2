import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


class Main {
    public static void main(String[] args) {
        GameProgress v1 = new GameProgress(100, 1, 1, 0.0, "D://Games/savegames/save1.dat");
        GameProgress v2 = new GameProgress(75, 2, 2, 400.43, "D://Games/savegames/save2.dat");
        GameProgress v3 = new GameProgress(25, 6, 4, 782.95, "D://Games/savegames/save3.dat");
        List<String> pathFiles = Arrays.asList(v1.getPath(), v2.getPath(), v3.getPath());

        saveGame(v1.getPath(), v1);
        saveGame(v2.getPath(), v2);
        saveGame(v3.getPath(), v3);

        zipFiles("D://Games/savegames/saves.zip", pathFiles);
        deleteFiles();
    }
    private static void saveGame(String path, GameProgress version) {
        try(FileOutputStream fos = new FileOutputStream(path);
            ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(version);
        } catch(Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static void zipFiles(String path, List<String> pathFiles) {
        try(ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(path))) {
            for (String pathFile : pathFiles) {
                FileInputStream fis = new FileInputStream(pathFile);
                String fileName = pathFile.substring(pathFile.lastIndexOf('/') + 1);
                ZipEntry entry = new ZipEntry(fileName);
                zout.putNextEntry(entry);
                byte[] buffer = new byte[fis.available()];
                fis.read(buffer);
                zout.write(buffer);
                zout.closeEntry();
                fis.close();
            }
        } catch(Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static void deleteFiles() {
        File dir = new File("D://Games/savegames");
        if(dir.isDirectory()){
            for(File file : dir.listFiles()) {
                if (file.getName().endsWith(".dat")){
                    file.delete();
                }
            }
        }
    }
}