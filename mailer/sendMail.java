package mailer;

import java.io.IOException;
import io.restassured.RestAssured;
import io.restassured.config.SSLConfig;
import io.restassured.response.Response;
import utility.extentReports;

public class sendMail {

    public static void sendMailToUser(String toMail) throws IOException {
        runBatchFile();
        String BASE_URL = "http://localhost:3000/mail";
        RestAssured.config = RestAssured.config().sslConfig(
                new SSLConfig().relaxedHTTPSValidation());
        String reportPath = System.getProperty("user.dir") + "/Reports/" + extentReports.mailfileName + "_"
                + extentReports.mailformattedDateTime + ".html";
        System.out.println("reportPath>>>>>>" + reportPath);

        String jsonBody = "{"
                + "\"to\": \"" + toMail + "\","
                + "\"subject\": \"Selenium Test Complete\","
                + "\"text\": \"The Selenium test has completed successfully.\","
                + "\"attachments\": [{"
                + "\"path\": \"" + reportPath.replace("\\", "\\\\") + "\""
                + "}]"
                + "}";

        Response response = RestAssured.given()
                .header("Content-Type", "application/json")
                .body(jsonBody)
                .when()
                .post(BASE_URL)
                .then()
                .extract()
                .response();
        String responseBody = response.asString();
        System.out.println("Response Code: " + response.getStatusCode());
        System.out.println("Response Body: " + responseBody);
    }

    private static void runBatchFile() {
        try {
            String batchFilePath = "C:\\Users\\Dhinesh.P\\eclipse\\Eclipse\\Dv_Mobile\\src\\test\\java\\mailer\\node_runner.bat";
            ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/c", batchFilePath);
            processBuilder.inheritIO();
            processBuilder.start();
            System.out.println("Batch file executed");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
