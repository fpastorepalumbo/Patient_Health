package unisa.diem.parser;

import ca.uhn.fhir.util.BundleBuilder;
import org.hl7.fhir.r4.model.*;
import unisa.diem.fhir.FhirHandler;
import lombok.SneakyThrows;
import org.apache.commons.csv.CSVRecord;

import java.util.ArrayList;
import java.util.List;

public class ImmunizationsParser extends BaseParser {

    ImmunizationsParser(DatasetService datasetService) {
        super(datasetService, "immunizations");
    }

    @Override
    @SneakyThrows
    public void parse() {
        int count = 0;
        List<Immunization> buffer = new ArrayList<>();

        for (CSVRecord rec : records) {
            Reference pat = new Reference("Patient/" + rec.get("PATIENT"));
            Reference enc = new Reference("Encounter/" + rec.get("ENCOUNTER"));

            Immunization imm = new Immunization();

            imm.setRecorded(datasetService.parseDatetime(rec.get("DATE")));
            imm.setPatient(pat);
            imm.setEncounter(enc);
            imm.setVaccineCode(new CodeableConcept()
                    .addCoding(new Coding()
                            .setSystem("http://hl7.org/fhir/sid/cvx")
                            .setCode(rec.get("CODE"))
                            .setDisplay(rec.get("DESCRIPTION"))
                    )
            );

            count++;
            buffer.add(imm);

            if (count % 100 == 0 || count == records.size()) {
                BundleBuilder bb = new BundleBuilder(FhirHandler.getContext());
                buffer.forEach(bb::addTransactionCreateEntry);
                FhirHandler.getClient().transaction().withBundle(bb.getBundle()).execute();
                if (count % 1000 == 0)
                    datasetService.logInfo("Parsed %d immunizations", count);
                buffer.clear();
            }
        }
        datasetService.logInfo("Parsed ALL immunizations");
    }
}
