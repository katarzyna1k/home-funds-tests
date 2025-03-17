package utils;

public class AppManager {

    public static void startApp() {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("cmd", "/c", "npm run start");
            processBuilder.directory(new java.io.File("../home-funds"));
            processBuilder.start();
            Thread.sleep(10000);
        } catch (Exception e) {
            throw new RuntimeException("Failed to start the app", e);
        }
    }

    public static void stopApp() {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("cmd", "/c", "taskkill /F /IM node.exe");
            processBuilder.start();
        } catch (Exception e) {
            System.out.println("Failed to stop the app" + e.getMessage());
        }
    }
}
