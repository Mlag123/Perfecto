package ru.mlagsoft;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Main {

    private File[] file_array_list = null;
    private String desktopPath = System.getProperty("user.home") + File.separator + "Desktop";
    private File _file = new File(desktopPath);
    private Configuration configuration = new Configuration();
    private ArrayList<String> r_name_List;
    private ArrayList<String> name_folder_List;
    private HashMap<String, String> hash_Names;


    public boolean isWindows() {
        return System.getProperty("os.name").toLowerCase().startsWith("win");
    }

    public static boolean isLinux() {
        String os = System.getProperty("os.name").toLowerCase();
        return os.contains("nux") || os.contains("nix");
    }

    public Main() throws IOException {
        if (isWindows()) {
            try {
                r_name_List = configuration.getRList();
                name_folder_List = configuration.getNameList();
                hash_Names = configuration.getConfigMap();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            try {
                replaceFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else if (isLinux()) {
            System.out.println("Linux is not supported");
        } else {
            System.out.println("Your system is not supported");
        }


    }


    public ArrayList<File> getFileNameR(String name) {
        ArrayList<File> file_array = new ArrayList<>();
        file_array_list = _file.listFiles();
        for (File file : file_array_list) {
            if (file.isFile()) {
                String file_name = file.getName().toLowerCase();
                if (file_name.endsWith(name)) {

                    file_array.add(file);
                }


            }
        }
        return file_array;
    }


    public void replaceFile() throws IOException {
        ArrayList<File> _inputArrayFiles;
        ArrayList<File> _outpuyArrayFiles;
        File renameFile;
        File file;
        File folderFile;
        Path path;
        Path _path1;
        for (int i = 0; i < r_name_List.size(); i++) {
            String r = r_name_List.get(i);
            if (!(getFileNameR(r) == null)) {
                _inputArrayFiles = getFileNameR(r);
                String folder_name = hash_Names.get(r);
                folderFile = createFolder(folder_name);

                for (File _file : _inputArrayFiles) {

                   // path = Path.of(_file.getPath()+"/");
                 //  _path1 = Path.of(folderFile.getPath()+"/");

                 //   System.out.println(folderFile.getPath());

                    String moveFile = folderFile.getPath()+"\\"+_file.getName();
                    System.out.println(moveFile);
                    _file.renameTo(new File(moveFile));

                  //  Files.move(Paths.get(_file.getPath()),Paths.get(folderFile.getPath()));
                }
            }


        }
    }

    public File createFolder(String name) {
        File folder = new File(desktopPath + "/" + name);
        if (!folder.isDirectory()) {
            folder.mkdirs();
            return folder;
        }
        return null;
    }


    public static void main(String[] args) throws IOException {

        new Window();

    }
}


class Configuration {
    private String flp_name = ".flp";
    private String mp3_name = ".mp3";
    private String wav_name = ".wav";
    private String lnk_name = ".lnk";
    private String seven_zip_anme = ".7z";
    private String rar_name = ".rar";
    private String url_name = ".url";
    private String jpg_name = ".jpg";
    private String png_name = ".png";
    private String webm_name = ".webm";
    private String txt_name = ".txt";
    private String blend_name = ".blend";
    private String blend_one_name = ".blend1";


    private String _name_FLP = "FLPojects";
    private String _name_TXT = "TXT Folder";
    private String _name_MUSIC = "Music Folder";
    private String _name_BLEND = "Blender Projects";
    private String _name_PICTURE = "Picture Folder";
    private String _name_RAR = "Rar Folder";
    private String _name_URL = "Url Folder";
    private String _name_LNK = "Lnk Folder";


    private String config_path = "./config.txt";
    private File file_configure = new File(config_path);
    private HashMap<String, String> config = new HashMap<>();
    private BufferedWriter bw;
    private BufferedReader br;


    public void writingDefaultConfig() throws IOException {
        config.put(flp_name, _name_FLP);
        config.put(wav_name, _name_MUSIC);
        config.put(mp3_name, _name_MUSIC);
        config.put(lnk_name, _name_LNK);
        config.put(png_name, _name_PICTURE);
        config.put(webm_name, _name_PICTURE);
        config.put(jpg_name, _name_PICTURE);
        config.put(txt_name, _name_TXT);
        config.put(blend_name, _name_BLEND);
        config.put(blend_one_name, _name_BLEND);
        config.put(rar_name, _name_RAR);
        config.put(seven_zip_anme, _name_RAR);
        config.put(url_name, _name_URL);
        //если файла нет, то создай и запиши туда чо хош, если такого нет, то досвидация


        if (!file_configure.isFile()) {
            file_configure.createNewFile();
            bw = new BufferedWriter(new FileWriter(file_configure));
            for (Map.Entry<String, String> entry : config.entrySet()) {

                bw.write(entry.getKey() + ":" + entry.getValue());
                bw.newLine();
            }
            bw.flush();
            bw.close();

        }


    }

    public HashMap<String, String> getConfigMap() throws IOException {
        HashMap<String, String> hashMap = new HashMap<>();
        br = new BufferedReader(new FileReader(file_configure));
        String line;
        while ((line = br.readLine()) != null) {
            String[] parts = line.split(":", 2);
            if (parts.length >= 2) {
                String key = parts[0];
                String value = parts[1];
                hashMap.put(key, value);

            }
        }
        return hashMap;


    }
    public Configuration() throws IOException {
        writingDefaultConfig();
    }


    @Deprecated
    public HashMap<String, String> getDefaultConfig() {
        return config;
    }


    public ArrayList<String> getRList() throws IOException {
        ArrayList<String> list_key = new ArrayList<>();
        for (Map.Entry<String, String> e : getConfigMap().entrySet()) {
            list_key.add(e.getKey());
        }
        return list_key;
    }

    public ArrayList<String> getNameList() throws IOException {
        ArrayList<String> list_name = new ArrayList<>();
        for (Map.Entry<String, String> e : getConfigMap().entrySet()) {
            list_name.add(e.getValue());
        }
        return list_name;
    }

    public void createFolder() {
        if (file_configure.isFile()) {
            try {
                file_configure.createNewFile();
            } catch (IOException e) {

            }
        }
    }


}