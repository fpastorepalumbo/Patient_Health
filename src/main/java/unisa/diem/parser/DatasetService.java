package unisa.diem.parser;

import unisa.diem.dicom.DicomService;
import lombok.Getter;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;

 // Service class with utility methods for dealing with CSV records.
public class DatasetService {

    private final DateFormat onlyDateFmt = new SimpleDateFormat("yyyy-MM-dd");
    private final DateFormat datetimeFmt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
    private final Logger logger = Logger.getLogger("DatasetParser");

    @Getter
    private final DicomService dicomService;

    public DatasetService() {
        dicomService = new DicomService();
    }

    // Logs a message with the INFO level
    public void logInfo(String fmt, Object... args) {
        logger.setLevel(Level.INFO);
        logger.info(String.format(fmt, args));
    }

    // Logs a message with the SEVERE level
    public void logSevere(String fmt, Object... args) {
        logger.severe(String.format(fmt, args));
    }

     // Return the value of the given property from the given record.
    public boolean hasProp(CSVRecord record, String prop) {
        return (record.isMapped(prop) &&
                record.get(prop) != null &&
                !record.get(prop).isEmpty() &&
                !record.get(prop).equals("false"));
    }

    // Parse the CSV file with the given name and return its records.
    public List<CSVRecord> parse(String subject) {
        CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
                .setHeader()
                .setSkipHeaderRecord(true)
                .build();

        try {
            String basePath = "src/main/resources/coherent-11-07-2022";
            String filePath = basePath + "/csv/" + subject + ".csv";
            return csvFormat.parse(new FileReader(filePath)).getRecords();
        } catch (IOException e) {
            logger.severe(e.getMessage());
            return null;
        }
    }

    // Parse a date string in the format yyyy-MM-dd and return a Date object.
    public Date parseDate(String date) throws ParseException {
        return onlyDateFmt.parse(date);
    }

    // Parse a datetime string in the format yyyy-MM-dd'T'HH:mm:ss'Z' and return a Date object.
    public Date parseDatetime(String datetime) throws ParseException {
        return datetimeFmt.parse(datetime);
    }

    // Check for the presence of the file dataset_loaded in the current directory, and if it doesn't exist, parse the dataset and create it
    public void loadDataset() throws IOException {
        Path path = Paths.get("dataset_loaded");
        if (!Files.exists(path)) {
            new DatasetParser(this).parse();
            Files.createFile(path);
        }
    }
}
