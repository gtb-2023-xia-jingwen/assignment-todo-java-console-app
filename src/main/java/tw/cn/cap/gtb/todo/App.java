package tw.cn.cap.gtb.todo;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;

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
        }
    }
}
