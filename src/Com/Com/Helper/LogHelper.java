package Com.Helper;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LogHelper {
    private static Logger loginLogger;
    private static FileHandler fileHandler;

    static {
        try {
            fileHandler = new FileHandler("src/Com/Com/login_activity.txt",true);
            loginLogger= Logger.getLogger("login-Log");
            loginLogger.addHandler(fileHandler);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeLoginLog(String message) throws IOException {
        loginLogger.log(Level.INFO,message);
    }

}
