package unisa.diem.parser;

import ca.uhn.fhir.util.BundleBuilder;
import unisa.diem.fhir.FhirWrapper;
import lombok.SneakyThrows;
import org.apache.commons.csv.CSVRecord;
import org.hl7.fhir.r4.model.*;
import org.hl7.fhir.r4.model.Enumerations.AdministrativeGender;

import java.util.ArrayList;
import java.util.List;

public class PractitionersLoader extends BaseLoader {

    PractitionersLoader(DatasetService datasetService) {
        super(datasetService, "practitioners");
    }

    @Override
    @SneakyThrows
    public void load() {
        List<Practitioner> buffer = new ArrayList<>();
        int count = 0;
        for (CSVRecord rec : records) {
            Reference org = new Reference("Organization/" + rec.get("ORGANIZATION"));

            Practitioner practitioner = new Practitioner();
            practitioner.setId(rec.get("Id"));
            practitioner.addIdentifier()
                    .setType(new CodeableConcept()
                            .addCoding(new Coding()
                                    .setSystem("http://terminology.hl7.org/CodeSystem/v2-0203")
                                    .setCode("ANON")
                                    .setDisplay("Anonymous ID")
                            )
                    )
                    .setSystem("urn:ietf:rfc:3986")
                    .setValue(rec.get("Id"));

            practitioner.addName()
                    .setText(rec.get("NAME"));

            practitioner.addAddress()
                    .addLine(rec.get("ADDRESS"))
                    .setCity(rec.get("CITY"))
                    .setState(rec.get("STATE"))
                    .setPostalCode(rec.get("ZIP"));

            practitioner.setGender(
                    rec.get("GENDER").equals("M") ? AdministrativeGender.MALE : AdministrativeGender.FEMALE
            );

            practitioner.addQualification()
                    .setIssuer(org)
                    .setCode(new CodeableConcept()
                            .addCoding(new Coding()
                                    .setSystem("http://snomed.info/sct")
                                    .setDisplay(rec.get("SPECIALITY"))
                            )
                    );

            practitioner.addExtension()
                    .setUrl("http://hl7.org/fhir/StructureDefinition/geolocation")
                    .setValue(new Address()
                            .addExtension()
                            .setUrl("latitude")
                            .setValue(new DecimalType(rec.get("LAT")))
                            .addExtension()
                            .setUrl("longitude")
                            .setValue(new DecimalType(rec.get("LON")))
                    );

            count++;
            buffer.add(practitioner);

            if (count % 100 == 0 || count == records.size()) {
                BundleBuilder bb = new BundleBuilder(FhirWrapper.getContext());
                buffer.forEach(bb::addTransactionUpdateEntry);
                FhirWrapper.getClient().transaction().withBundle(bb.getBundle()).execute();

                if (count % 1000 == 0)
                    datasetService.logInfo("Loaded %d practitioners".formatted(count));

                buffer.clear();
            }
        }
        datasetService.logInfo("Loaded ALL practitioners");
    }
}
