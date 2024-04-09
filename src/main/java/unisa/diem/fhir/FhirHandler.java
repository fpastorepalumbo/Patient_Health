package unisa.diem.fhir;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;


public class FhirHandler {

    private static final String baseUrl = "http://localhost:8080/fhir";
    private static FhirHandler instance;
    private final FhirContext ctx;

    private FhirHandler() {
        ctx = FhirContext.forR4();
    }

    /**
     * Returns the global instance of the FHIR context.
     * If the instance doesn't exist yet, it is created.
     */
    public static FhirContext getContext() {
        if (instance == null) {
            instance = new FhirHandler();
        }
        return instance.ctx;
    }

    // Return a copy of the FHIR client from the global instance of the FHIR context.
    public static IGenericClient getClient() {
        getContext().getRestfulClientFactory().setSocketTimeout(60 * 1000);
        return getContext().newRestfulGenericClient(baseUrl);
    }
}
