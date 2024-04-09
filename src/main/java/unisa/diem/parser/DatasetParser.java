package unisa.diem.parser;

public class DatasetParser implements Parser {
    private final Parser[] parsers;

    // Register all unitary parsers
    public DatasetParser(DatasetService datasetService) {
        this.parsers = new Parser[]{
                new PatientsParser(datasetService),
                new OrganizationsParser(datasetService),
                new PractitionersParser(datasetService),
                new PayersParser(datasetService),
                new EncountersParser(datasetService),
                new ConditionsParser(datasetService),
                new ObservationsParser(datasetService),
                new AllergiesParser(datasetService),
                new CarePlansParser(datasetService),
                new ImmunizationsParser(datasetService),
                new MedicationRequestsParser(datasetService),
                new DevicesParser(datasetService),
                new ImagingStudiesParser(datasetService),
                new ProceduresParser(datasetService)
        };
    }

    // Call, in order, the parse method of each unitary loader
    public void parse() {
        for (Parser parser : parsers)
            parser.parse();
    }
}
