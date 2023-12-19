package dao;

import com.opencsv.CSVWriter;
import util.Util;
import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;

import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.io.*;
import java.util.*;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;


public class GetDataFromSource {
    Util util = new Util();

    SimpleDateFormat dateFormat = new SimpleDateFormat(util.timeFormat);
    String timestamp = dateFormat.format(new Date());
    String csvFileName = util.inputFolderPath + util.nameFile + timestamp + util.fileFormat;

    /**
     * This method retrieves data from a web source and saves it in a CSV file.
     *
     * @throws IOException if an I/O error occurs
     */
    public boolean fetchDataAndWriteToCSV() throws IOException {
        boolean res = false;
        try (CSVWriter csvWriter = new CSVWriter(new OutputStreamWriter(
                new FileOutputStream(csvFileName), StandardCharsets.UTF_8))) {

            // Write CSV header
            csvWriter.writeNext(new String[]{util.titleText, util.descriptionText, util.authorText, util.timeText, util.urlNewText, util.categoryText, util.imageText, util.contentText, util.sourceText});

            String url = util.urlRiotSource;
            Document document = Jsoup.connect(url).get();
            Elements elements = document.select(util.elements);

            for (Element element : elements) {
                String title = element.select(util.title).text();
                String description = element.select(util.description).text();
                String author = element.select(util.author).text();
                String time = element.select(util.time).attr(util.timeAttr);
                String link = element.attr(util.links);
                if (!link.startsWith(util.linkType)) {
                    link = util.linkSource + link;
                }
                Document articleDocument = Jsoup.connect(link).get();
                String articleTitle = element.select(util.articleTitle).text();
                String image = element.select(util.image).attr(util.imageArr);
                String articleContent = articleDocument.select(util.articleContent).text();
                if (articleContent.isEmpty()) {
                    articleContent = articleDocument.select(util.articleContent2).text();
                }

                csvWriter.writeNext(new String[]{title, description, author, time, link, articleTitle, image, articleContent, util.nameSource});
                res = true;

            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return res;
    }

    /**
     * Move the previous file from the output folder to the data_archive folder.
     *
     * @throws IOException if an I/O error occurs
     */
    public void moveFileToFolder() throws IOException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(util.timeFormat);
        Path csvPath = Paths.get(csvFileName);
        StringTokenizer token = new StringTokenizer(csvPath.toString(), "_.");
        String a = token.nextToken();
        String b = token.nextToken();
        String c = token.nextToken();
        String fileTimestamp = b + "_" + c;

        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get(util.inputFolderPath))) {
            for (Path filePath : directoryStream) {
                StringTokenizer tokenInput = new StringTokenizer(filePath.toString(), "_.");
                String a1 = tokenInput.nextToken();
                String b1 = tokenInput.nextToken();
                String c1 = tokenInput.nextToken();

                if (!(b1 + "_" + c1).equals(fileTimestamp)) {
                    Files.move(filePath, Paths.get(util.outputFolderPath + util.nameFile + b1 + "_" + c1 + util.fileFormat));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}



