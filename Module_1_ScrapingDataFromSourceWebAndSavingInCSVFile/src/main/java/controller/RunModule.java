package controller;

import util.*;
import dao.*;

import java.io.IOException;
import java.nio.file.*;
import java.sql.Timestamp;
import java.text.*;
import java.util.*;

public class RunModule {

    public static void main(String[] args) throws IOException {
        Util util = new Util();
        ControlDAO controlDAO = new ControlDAO();
        ConfigDAO configDAO = new ConfigDAO();
        FileDAO fileDAO = new FileDAO();
        LogDAO logDAO = new LogDAO();
        GetDataFromSource dataWeb = new GetDataFromSource();
        SendEmail sendEmail = new SendEmail();

        //3 Check the controls table : status = "RN"
        if (!controlDAO.isStatusRunning()){

            //4 Check the files table: status = "done" limit 1
            if(fileDAO.isCheckFile()){

                //5 Load data from the configs table
                configDAO.loadConfigs();

                //6 Insert 1 row in the controls table: id, name, description, status, config_id, created_at, updated_at. With status = 'RN'
                controlDAO.insertControls("Get Data from web","Get data from source and save in CSV file", "RN", 15);

                //7 Insert 1 row in the files table: id, config_id, name, column_name, data_format, file_timestamp, destination, dir_save, dir_archive, note, status, created_at, updated_at, created_by, updated_by. With status = "RN"
                fileDAO.insertFile(15, util.nameFile, util.titleText + ", " + util.descriptionText + ", " + util.authorText + ", " + util.timeText + ", " +
                        util.urlNewText + ", " + util.categoryText + ", " + util.imageText + ", " + util.contentText + ", " + util.sourceText, ".csv", "module 1","output", "data_archive", "", "RN", util.memberCreate, util.memberUpdate);

                //8 Scraping data and saving in Temporary Storage: output/GetDataWeb_yyyyMMdd_HHmmss.csv.
                boolean scrapData = dataWeb.fetchDataAndWriteToCSV();


                //9 Check if scraping and saving success
                if(!scrapData){

                    //9.1 Delete 1 row in the controls table on step 6: id, name, description, status, config_id, created_at, updated_at. With status = 'RN'
                    controlDAO.deleteControls("RN");

                    //9.2 Delete 1 row in the files table on step 7: id, config_id, name, column_name, data_format, file_timestamp, destination, dir_save, dir_archive, note, status, created_at, updated_at, created_by, updated_by. With status = "RN"
                    fileDAO.deleteFiles("RN");

                    //9.3 Insert 1 row in the logs table: id, event_name, event_type, status, location, created_at. With status = 'ES'
                    logDAO.insertLogs("Get data from web", "Scraping data", "ES", "Module1/src/dao/getDataFromSource");

                    //9.4 Send email to newsofgame2023@gmail.com with title is "Scraping error" and content: "Scraping web is error at" + error time.
                    try {SimpleDateFormat dateFormat = new SimpleDateFormat(util.timeFormat);
                    String timestamp = dateFormat.format(new Date());
                    SimpleDateFormat inputFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
                    SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                        sendEmail.sendMail(util.emailReceive,"Scraping error", "Scraping web is error at " + outputFormat.format(inputFormat.parse(timestamp)));
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                }
                //10 move previous file in the ouput folder to data_archive
                dataWeb.moveFileToFolder();

                //11 Update 1 row in the files table on step 7: id, config_id, name, column_name, data_format, file_timestamp, destination, dir_save, dir_archive, note, status, created_at, updated_at, created_by, updated_by. With status = "SC" and name = previous name + time at create
                DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get(util.inputFolderPath));
                    for (Path filePath : directoryStream) {
                        try {StringTokenizer tokenInput = new StringTokenizer(filePath.toString(), "_.");
                        String a1 = tokenInput.nextToken();
                        String b1 = tokenInput.nextToken();
                        String c1 = tokenInput.nextToken();
                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd HHmmss");
                            fileDAO.updateFilesStatus(a1 + "_" + b1 + "_" + c1 , new Timestamp((dateFormat.parse(b1 + " " + c1)).getTime()), "RN", "SC");
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }

                //12 Update in the controls tables on step 6:id, name, description, status, config_id, created_at, updated_at. With status = 'SC'.
                controlDAO.updateControlsStatus("RN", "SC");

                //13 Insert 1 row in the logs table: id, event_name, event_type, status, location, created_at. With status = 'SC'
                logDAO.insertLogs("Get Data from web", "get data from source", "SC","Module 1");
            }
        }
    }
}
