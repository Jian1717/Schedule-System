package Com.Helper;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
/**This helper class contain useful static methods that helps log user login event in this application. */
public class LogHelper {
    private static Logger loginLogger;

    //this static method initialize the fileHandler for loginLogger so that login attempt will be recorded on login_activity.txt file.
    static {
        try {
            FileHandler fileHandler = new FileHandler("src/Com/Com/login_activity.txt", true);
            loginLogger= Logger.getLogger("login-Log");
            loginLogger.addHandler(fileHandler);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**This method log the message into logging logger*/
    public void writeLoginLog(String message) throws IOException {
        loginLogger.log(Level.INFO,message);
    }

}
