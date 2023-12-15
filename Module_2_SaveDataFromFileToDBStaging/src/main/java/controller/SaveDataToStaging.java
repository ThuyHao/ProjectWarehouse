package controller;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import context.DBConnect;
import dao.*;
import model.Files;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import static context.DBConnect.getConnectionDBControl;
import static util.SendEmail.sendMail;

public class SaveDataToStaging {

    public static void GetDataFromFile() {
        try {
            /**
             * 1. Connect to DBController
             */
            Connection connectionControl = DBConnect.getConnectionDBControl();

            /**
            * 2. Check the controls table:
            * status='RN' and
            * date(created_at) = current_date
            * order by created_at desc
            * LIMIT 1;
            */
            if (ControlDAO.checkStatusControl(connectionControl)) {
                System.out.println("Control status is 'RN'. Save is aborted.");
            } else {
                /**
                 * 3. Check the files table:
                 * status='SC' and
                 * date(created_at) = current_date
                 * order by created_at desc
                 * LIMIT 1;
                 */
                if (!FileDAO.checkStatusFile(connectionControl)) {
                    System.out.println("File status is not 'SC'. Save is aborted.");
                } else {

                    /**
                     * 4. Get the link file GetDataWeb_yyyyMMdd_HHmmss.csv from the Files table using Paths.get(...)
                     * and use removeBackslashBeforeDot(...) to correct the link format
                     */
                    // Load data from CSV to Staging table

                    Files desiredFile = FileDAO.getFileCSV();
                    String latestFilePath = Paths.get(desiredFile.getName(), desiredFile.getDataFormat()).toString();
                    String cleanedFilePath = removeBackslashBeforeDot(latestFilePath);
                    /**
                     * 5. Check Get file GetDataWeb_yyyyMMdd_HHmmss.csv successfully
                     */
                    if (cleanedFilePath != null) {
                        System.out.println("file csv: " + cleanedFilePath);
                        /**
                         * 6. Insert 1 row into the controls table: id, name,
                         * description, code, status, created_at,
                         * updated_at, created_by, update_by.
                         * With status = 'RN', name = 'Save data to DBStaging' ,
                         * description = 'Save data from file GetDataWeb_yyyyMMdd_HHmmss.csv to DBStaging '
                         */
                        ControlDAO.insertRowControl(connectionControl);
                        /**
                         * 7. Insert into the Logs table:event_name, event_type, status,
                         * location, created_at with status='SC'
                         */
                        LogDAO.insertLogs(getConnectionDBControl(), "Insert a Row from controls table", "Success insert a row into table controls with status ='RN' and AND DATE(created_at) = CURRENT_DATE ",
                    "SC", "Module 2");

                        /**
                         * 8. Connect to DBStaging
                         */
                        Connection connectionStaging = DBConnect.getConnectionDBStaging();
                        StagingDAO stagingDAO = new StagingDAO();
                        /**
                         * 9. Truncate table Staging
                         */
                        // Truncate Staging table in DBStaging
                        stagingDAO.truncateStagingTable(connectionStaging);
                        loadDataFromCSV(connectionStaging, cleanedFilePath, stagingDAO);
                    } else {

                        System.out.println("Không tìm thấy file CSV trong thư mục.");
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Database connection error: " + e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void loadDataFromCSV(Connection connection, String csvFilePath, StagingDAO stagingDAO) throws IOException {
        /**
         * 10. Get data from file  GetDataWeb_yyyyMMdd_HHmmss.csv
         */
        CSVReader csvReader = new CSVReader(new InputStreamReader(new FileInputStream(csvFilePath), StandardCharsets.UTF_8));
        csvReader.skip(1); // Skip the first line to avoid header

        String insertSQL = "INSERT INTO staging (title, description, author_name, timeUp, url, " +
                "category_name, image, content, source_name, status, created_at) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (
                PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
            String[] data;
            while ((data = csvReader.readNext()) != null) {
                Timestamp timestamp = StagingDAO.convertToTimestamp(data[3]);

                preparedStatement.setString(1, data[0]); // title
                preparedStatement.setString(2, data[1]); // description
                preparedStatement.setString(3, data[2]); // author_name
                preparedStatement.setTimestamp(4, timestamp); // timeUp
                preparedStatement.setString(5, data[4]); // url
                preparedStatement.setString(6, data[5]); // category_name
                preparedStatement.setString(7, data[6]); // image
                preparedStatement.setString(8, data[7]); // content
                preparedStatement.setString(9, "Riot News"); // source_name
                preparedStatement.setString(10, "SC"); // status
                preparedStatement.setTimestamp(11, new Timestamp(System.currentTimeMillis())); // created_at

                preparedStatement.addBatch();

            }
            try {
                /**
                 * 11. Insert data from  file  GetDataWeb_yyyyMMdd_HHmmss.csv to the Staging table:
                 * INSERT INTO staging (title, description, author_name, timeUp, url, category_name, image, content,source_name, status, created_at)
                 */

                preparedStatement.executeBatch();

                /**
                 * 12.Check if insert data from file  GetDataWeb_yyyyMMdd_HHmmss.csv to the Staging table success
                 */
                int[] updateCounts = preparedStatement.executeBatch();
                boolean isInsertSuccessful = Arrays.stream(updateCounts).allMatch(count -> count >= 0);

                if (isInsertSuccessful) {
                    /**
                     *  13. Insert into the Logs table: event_name, event_type, status, location, created_at with status='SC'
                     *  event_name: Insert data from files csv into DBStaging
                     *  event_type: Success inserting data from file GetDataWeb_yyyyMMdd_HHmmss.csv into DBStaging
                     */
                    LogDAO.insertLogs(getConnectionDBControl(), "Insert data from files csv into DBStaging",
                            "Success inserting data from file GetDataWeb_yyyyMMdd_HHmmss.csv into DBStaging",
                            "SC", "Module 2");

                    /**
                     * 14. Update in file table has status = 'done'
                     * 15. Check if successful update in file table has status = 'done'
                     */
                    if (FileDAO.updateStatusFile(getConnectionDBControl())) {

                        /**
                         *  16. Insert into the Logs table: event_name, event_type, status, location, created_at with status='SC'
                         *  event_name: Update status from files table
                         *  event_type: Success update a row from table files with status ='done' and AND DATE(created_at) = CURRENT_DATE
                         */
                        LogDAO.insertLogs(getConnectionDBControl(), "Update status from files table", "Success update a row from table files with status ='done' and AND DATE(created_at) = CURRENT_DATE ",
                                "SC", "Module 2");

                        /**
                         *  17. Update in controls table has status='SC'
                         *  18. Check if successful update in controls table has status = 'SC'
                         */
                        if (ControlDAO.updateStatusControl(getConnectionDBControl())) {
                            /**
                             * 19. Insert into the Logs table: event_name, event_type, status, location, created_at with status='SC'
                             * event_name:Update status from controls table
                             * event_type:Success update a row from table controls with status ='done' and AND DATE(created_at) = CURRENT_DATE
                             */
                            LogDAO.insertLogs(getConnectionDBControl(), "Update status from controls table", "Success update a row from table controls with status ='done' and AND DATE(created_at) = CURRENT_DATE ",
                                    "SC", "Module 2");
                        } else {
                            /**
                             *   18.1. Insert into the Logs table: event_name, event_type, status, location, created_at with status='EI'
                             *   event_name: Update status from controls table
                             *   event_type: Error update a row from table controls with status ='done' and AND DATE(created_at) = CURRENT_DATE
                             */
                            LogDAO.insertLogs(getConnectionDBControl(), "Update status from controls table", "Error update a row from table controls with status ='done' and AND DATE(created_at) = CURRENT_DATE ",
                                    "ER", "Module 2");
                        }
                    } else {
                        /**
                         *  15.1. Insert into the Logs table: event_name, event_type, status, location, created_at with status='EI'
                         *  event_name: Update status from files table
                         *  event_type: Error update a row from table files with status ='done' and AND DATE(created_at) = CURRENT_DATE
                         */
                        LogDAO.insertLogs(getConnectionDBControl(), "Update status from files table", "Error update a row from table files with status ='done' and AND DATE(created_at) = CURRENT_DATE ",
                                "ER", "Module 2");
                    }
                } else {
                    /**
                     *12.1 Delete row from controls table with status = 'RN' and DATE(created_at) = CURRENT_DATE
                     */
                    ControlDAO.deleteRowControl(getConnectionDBControl());
                    /**
                     *   12.2. Insert into the Logs table: event_name, event_type, status, location, created_at with status='EI'
                     *   event_name: Delete a Row from controls table
                     *   event_type: Success delete a row from table controls with status ='RN' and AND DATE(created_at) = CURRENT_DATE
                     */
                    LogDAO.insertLogs(getConnectionDBControl(), "Delete a Row from controls table", "Success delete a row from table controls with status ='RN' and AND DATE(created_at) = CURRENT_DATE ",
                            "EI", "Module 2");
                    /**
                     *  12.3. Send email to newsofgame2023@gmail.com with content: "Save data from files to DBStaging error at" + error time.
                     */

                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
                    String timestamp = dateFormat.format(new Date());
                    SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                    String errorMessage = "Save data from files to DBStaging error " + outputFormat.format(dateFormat.parse(timestamp));

                    sendMail(ConfigDAO.getEmailReceiveFromDB(), "Error", errorMessage);
                    throw new RuntimeException();
                }

            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        } catch (CsvValidationException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * kiểm tra đường dẫn file csv được lấy ra từ câu lệnh Paths.get(...) và chỉnh sửa cho đúng định dạng
     * @param latestFilePath
     * @return
     */
    public static String removeBackslashBeforeDot(String latestFilePath) {
        // Kiểm tra xem chuỗi filePath có chứa dấu chấm không
        if (latestFilePath.contains("\\")) {
            // Loại bỏ dấu gạch chéo ngược trước dấu chấm
            latestFilePath = latestFilePath.replaceAll("\\\\\\.csv", ".csv");
        }

        return latestFilePath;
    }

    public static void main(String[] args)  {
            GetDataFromFile();
    }
}