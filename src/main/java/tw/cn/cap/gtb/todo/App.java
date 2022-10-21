package tw.cn.cap.gtb.todo;

import java.io.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class App {
    static final String HOME = System.getProperty("user.home");
    static final String TASK_DIR = HOME + "/.todo/";
    static final String TASK_FILE = TASK_DIR + "tasks";

    public void init() throws IOException {
        File taskDir = new File(TASK_DIR);
        if (!taskDir.exists()) taskDir.mkdirs();
        File taskFile = new File(TASK_FILE);
        if (!taskFile.exists()) taskFile.createNewFile();
        System.out.println("Initialized successfully.");
    }

    public void list() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(TASK_FILE));
        List<String> tbdList = new ArrayList<>();
        List<String> doneList = new ArrayList<>();
        String line = null;
        while ((line = br.readLine()) != null) {
            if (line.charAt(0) >= '1' && line.charAt(0) <= '9') tbdList.add(line);
            if (line.charAt(0) == 'x') doneList.add(line.substring(1));
        }
        if (!tbdList.isEmpty() || !doneList.isEmpty()) {
            System.out.println("# To be done");
            if (!tbdList.isEmpty()) tbdList.forEach(System.out::println);
            else System.out.println("Empty");
            System.out.println("# Completed");
            if (!doneList.isEmpty()) doneList.forEach(System.out::println);
            else System.out.println("Empty");
        }
    }

    public void add(String title) throws IOException {
        // get last task number
        LineNumberReader reader = new LineNumberReader(new FileReader(TASK_FILE));
        reader.skip(Long.MAX_VALUE);
        int lastNumber = reader.getLineNumber();
        int curNumber = lastNumber + 1;
        BufferedWriter bw = new BufferedWriter(new FileWriter(TASK_FILE, true)); // append mode
        bw.write(curNumber + " " + title);
        bw.newLine();
        bw.close();
        list();
    }

    public static void main(String[] args) throws IOException {
        App app = new App();
        String cmd = args[0];
        if (cmd.equals("init")) {
            app.init();
        } else {
            File taskFile = new File(TASK_FILE);
            if (!taskFile.exists()) {
                System.out.printf("Please run 'todo init' before running '%s' command.", cmd);
                return;
            }
            if (cmd.equals("list")) {
                app.list();
            }else if (cmd.equals("add")) {
                String title = Arrays.stream(args).skip(1).collect(Collectors.joining(" "));
                app.add(title);
            }
        }
    }
}
