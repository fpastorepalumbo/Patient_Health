package unisa.diem.fhir;

import ca.uhn.fhir.rest.client.api.IGenericClient;
import org.hl7.fhir.r4.model.*;

// Utility class with shared fields and methods for executing FHIR requests.
public class FhirService {

    // Return the FHIR client from the global instance of the FHIR context.
    public IGenericClient getClient() {
        return FhirHandler.getClient();
    }

    // Resolve the resource by its ID.
    public Resource resolveId(Class<? extends Resource> type, String id) {
        return FhirHandler.getClient().read().resource(type).withId(id).execute();
    }

    // Get the Claim resource related to the given Encounter.
    public Claim getClaim(Encounter res) {
        return getClient().search().forResource(Claim.class)
                .where(Claim.ENCOUNTER.hasId("Encounter/" + res.getIdElement().getIdPart()))
                .returnBundle(org.hl7.fhir.r4.model.Bundle.class)
                .execute().getEntry().stream()
                .map(org.hl7.fhir.r4.model.Bundle.BundleEntryComponent::getResource)
                .map(Claim.class::cast)
                .findFirst().orElse(null);
    }

    // Get the Claim related to the given MedicationRequest.
    public Claim getClaim(MedicationRequest res) {
        return getClient().search().forResource(Claim.class)
                .where(Claim.ENCOUNTER.hasId(res.getEncounter().getReference()))
                .returnBundle(org.hl7.fhir.r4.model.Bundle.class)
                .execute().getEntry().stream()
                .map(org.hl7.fhir.r4.model.Bundle.BundleEntryComponent::getResource)
                .map(Claim.class::cast)
                .toList().get(1);
    }

    // Get the ExplanationOfBenefit related to the given Encounter.
    public ExplanationOfBenefit getEOB(Encounter res) {
        return getClient().search().forResource(ExplanationOfBenefit.class)
                .where(ExplanationOfBenefit.CLAIM.hasId("Claim/" + getClaim(res).getIdElement().getIdPart()))
                .returnBundle(org.hl7.fhir.r4.model.Bundle.class)
                .execute().getEntry().stream()
                .map(org.hl7.fhir.r4.model.Bundle.BundleEntryComponent::getResource)
                .map(ExplanationOfBenefit.class::cast)
                .findFirst().orElse(null);
    }

    // Get the ExplanationOfBenefit related to the given MedicationRequest.
    public ExplanationOfBenefit getEOB(MedicationRequest res) {
        return getClient().search().forResource(ExplanationOfBenefit.class)
                .where(ExplanationOfBenefit.CLAIM.hasId("Claim/" + getClaim(res).getIdElement().getIdPart()))
                .returnBundle(org.hl7.fhir.r4.model.Bundle.class)
                .execute().getEntry().stream()
                .map(org.hl7.fhir.r4.model.Bundle.BundleEntryComponent::getResource)
                .map(ExplanationOfBenefit.class::cast)
                .findFirst().orElse(null);
    }
}
