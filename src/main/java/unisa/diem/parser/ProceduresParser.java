package unisa.diem.parser;

import ca.uhn.fhir.util.BundleBuilder;
import unisa.diem.fhir.FhirHandler;
import org.apache.commons.csv.CSVRecord;
import org.hl7.fhir.r4.model.CodeableConcept;
import org.hl7.fhir.r4.model.Coding;
import org.hl7.fhir.r4.model.Procedure;
import org.hl7.fhir.r4.model.Reference;

import java.util.ArrayList;
import java.util.List;

public class ProceduresParser extends BaseParser {

    ProceduresParser(DatasetService datasetService) {
        super(datasetService, "procedures");
    }

    @Override
    public void parse() {
        int count = 0;
        List<Procedure> buffer = new ArrayList<>();

        for (CSVRecord record : records) {
            Reference pat = new Reference("Patient/" + record.get("PATIENT"));
            Reference enc = new Reference("Encounter/" + record.get("ENCOUNTER"));

            Procedure proc = new Procedure();
            proc.getPerformedDateTimeType().setValueAsString(record.get("DATE"));
            proc.setSubject(pat);
            proc.setEncounter(enc);

            proc.setCode(new CodeableConcept()
                    .addCoding(new Coding()
                            .setSystem("http://snomed.info/sct")
                            .setCode(record.get("CODE"))
                            .setDisplay(record.get("DESCRIPTION"))
                    )
            );

            proc.addReasonCode(new CodeableConcept()
                    .addCoding(new Coding()
                            .setSystem("http://snomed.info/sct")
                            .setCode(record.get("REASONCODE"))
                            .setDisplay(record.get("REASONDESCRIPTION"))
                    )
            );

            count++;
            buffer.add(proc);

            if (count % 100 == 0 || count == records.size()) {
                BundleBuilder bb = new BundleBuilder(FhirHandler.getContext());
                buffer.forEach(bb::addTransactionCreateEntry);
                FhirHandler.getClient().transaction().withBundle(bb.getBundle()).execute();
                if (count % 1000 == 0)
                    datasetService.logInfo("Parsed %d procedures", count);
                buffer.clear();
            }
        }
        datasetService.logInfo("Parsed ALL procedures");
    }
}
