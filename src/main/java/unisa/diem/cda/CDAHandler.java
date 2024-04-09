package unisa.diem.cda;

import ca.uhn.fhir.rest.server.exceptions.ResourceNotFoundException;
import lombok.SneakyThrows;
import org.hl7.fhir.r4.model.*;
import org.openhealthtools.mdht.uml.cda.ClinicalDocument;
import org.openhealthtools.mdht.uml.cda.Encounter;
import org.openhealthtools.mdht.uml.cda.Section;
import unisa.diem.fhir.FhirService;

import java.text.SimpleDateFormat;
import java.util.List;

// Class responsible for creating C-CDA document from encounter and saving them as FHIR DocumentReference resource.
public class CDAHandler {
    private final SimpleDateFormat DATETIME_FMT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    private final CDAService cdaService;
    private final FhirService fhirService;

    public CDAHandler() {
        this.cdaService = new CDAService();
        this.fhirService = new FhirService();
    }

    // Create C-CDA document from encounter and save it as FHIR DocumentReference resource.
    public void saveCDAToFhir(String encounterId) throws ResourceNotFoundException {
        DocumentReference doc = convert(cdaService.getClinicalDocument(encounterId));
        fhirService.getClient().update().resource(doc).execute();
    }

    /**
     * Convert ClinicalDocument to DocumentReference.
     * Take, as input, a ClinicalDocument object produced by the MDHT library and convert it to a FHIR DocumentReference
     */
    @SneakyThrows
    private DocumentReference convert(ClinicalDocument cda) {
        Section body = cda.getSections().get(0);
        Encounter encounter = body.getEncounters().get(0);
        String encounterId = encounter.getIds().get(0).getRoot();

        DocumentReference document = new DocumentReference();
        document.setId(encounterId);
        document.setStatus(Enumerations.DocumentReferenceStatus.CURRENT);
        document.setType(new CodeableConcept().addCoding(
                new Coding()
                        .setSystem("http://loinc.org")
                        .setCode("34133-9")
                        .setDisplay("Summarization of episode note")));

        document.setSubject(new Reference("Patient/" + cda.getPatients().get(0).getId().getRoot()));
        document.setContext(new DocumentReference.DocumentReferenceContextComponent()
                .setEncounter(List.of(new Reference("Encounter/" + encounterId)))
                .setPeriod(new Period()
                        .setStartElement(new DateTimeType(encounter.getEffectiveTime().getLow().getValue()))
                        .setEndElement(new DateTimeType(encounter.getEffectiveTime().getHigh().getValue()))));
        document.setAuthor(cda.getParticipants().stream()
                .map(author -> new Reference("Practitioner/" + author.getAssociatedEntity().getIds().get(0).getRoot()))
                .toList());
        document.setDate(DATETIME_FMT.parse(cda.getEffectiveTime().getValue()));
        document.setDescription("Report of Encounter " + encounterId);

        document.setContent(List.of(new DocumentReference.DocumentReferenceContentComponent()
                .setFormat(new Coding()
                        .setSystem("http://hl7.org/fhir/R4/valueset-formatcodes.html")
                        .setCode("urn:hl7-org:sdwg:ccda-structuredBody:2.1")
                        .setDisplay("C-CDA Structured Body"))
                .setAttachment(new Attachment()
                        .setContentType("application/xml")
                        .setLanguage("en")
                        .setData(cdaService.generateCD(encounterId).getBytes()))));

        return document;
    }
}
