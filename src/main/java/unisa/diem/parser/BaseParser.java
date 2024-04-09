package unisa.diem.parser;

import org.apache.commons.csv.CSVRecord;

import java.util.List;

// Abstract class for parsers
public abstract class BaseParser implements Parser {

    protected final String subject;
    protected final DatasetService datasetService;
    protected List<CSVRecord> records;

    // Initialize the parser by parsing the assigned CSV file and storing its records
    protected BaseParser(DatasetService datasetService, String subject) {
        this.subject = subject;
        this.datasetService = datasetService;
        this.records = datasetService.parse(subject);
        if (records == null)
            throw new RuntimeException("Failed to parse " + subject);
    }

    // Create and store the FHIR resources from the parsed CSV records
    @Override
    public abstract void parse();
}
