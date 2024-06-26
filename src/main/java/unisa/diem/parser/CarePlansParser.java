package unisa.diem.parser;

import ca.uhn.fhir.util.BundleBuilder;
import unisa.diem.fhir.FhirHandler;
import lombok.SneakyThrows;
import org.apache.commons.csv.CSVRecord;
import org.hl7.fhir.r4.model.*;

import java.util.ArrayList;
import java.util.List;

public class CarePlansParser extends BaseParser {

    CarePlansParser(DatasetService datasetService) {
        super(datasetService, "careplans");
    }

    @Override
    @SneakyThrows
    public void parse() {
        int count = 0;
        List<CarePlan> buffer = new ArrayList<>();

        for (CSVRecord rec : records) {

            Reference pat = new Reference("Patient/" + rec.get("PATIENT"));
            Reference enc = new Reference("Encounter/" + rec.get("ENCOUNTER"));

            CarePlan cp = new CarePlan();
            cp.setId(rec.get("Id"));

            Period period = new Period();
            period.setStart(datasetService.parseDate(rec.get("START")));
            if (datasetService.hasProp(rec, "STOP"))
                period.setEnd(datasetService.parseDate(rec.get("STOP")));
            cp.setPeriod(period);

            cp.setSubject(pat);
            cp.setEncounter(enc);

            cp.addCategory(new CodeableConcept()
                    .addCoding(new Coding()
                            .setSystem("http://snomed.info/sct")
                            .setCode(rec.get("CODE"))
                            .setDisplay(rec.get("DESCRIPTION"))
                    )
            );

            cp.addActivity()
                    .getDetail()
                    .addReasonCode(new CodeableConcept()
                            .addCoding(new Coding()
                                    .setSystem("http://snomed.info/sct")
                                    .setCode(rec.get("REASONCODE"))
                                    .setDisplay(rec.get("REASONDESCRIPTION"))
                            )
                    );

            cp.addIdentifier()
                    .setType(new CodeableConcept()
                            .addCoding(new Coding()
                                    .setSystem("http://terminology.hl7.org/CodeSystem/v2-0203")
                                    .setCode("ANON")
                                    .setDisplay("Anonymous ID")
                            )
                    )
                    .setValue(rec.get("Id"))
                    .setSystem("urn:ietf:rfc:3986");

            count++;
            buffer.add(cp);

            if (count % 100 == 0 || count == records.size()) {
                BundleBuilder bb = new BundleBuilder(FhirHandler.getContext());
                buffer.forEach(bb::addTransactionUpdateEntry);
                FhirHandler.getClient().transaction().withBundle(bb.getBundle()).execute();
                if (count % 1000 == 0)
                    datasetService.logInfo("Parsed %d care plans", count);
                buffer.clear();
            }
        }
        datasetService.logInfo("Parsed ALL care plans");
    }
}
