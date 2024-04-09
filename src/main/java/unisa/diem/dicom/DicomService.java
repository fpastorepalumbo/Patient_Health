package unisa.diem.dicom;

import com.pixelmed.dicom.DicomException;
import com.pixelmed.dicom.TagFromName;
import unisa.diem.fhir.FhirHandler;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.hl7.fhir.r4.model.ImagingStudy;
import org.hl7.fhir.r4.model.Patient;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

// Service Class with method for working with DICOM files.
public class DicomService {

    // Return the filename part of a DICOM file.
    private String getFilenamePart(ImagingStudy img) {
        Patient patient = FhirHandler.getClient().read().resource(Patient.class)
                .withId(img.getSubject().getReference()).execute();

        String givenName = patient.getNameFirstRep().getGivenAsSingleString(),
                surname = patient.getNameFirstRep().getFamily(),
                patientId = patient.getIdElement().getIdPart();

        return givenName + "_" + surname + "_" + patientId;
    }

    /**
     * Return the DICOM file object from the Coherent dataset.
     * Read all files whose name contains the given filename part and returns the one whose StudyInstanceUID matches.
     */
    public File getFileObject(String filename) {
        Path root = Path.of("src/main/resources/coherent-11-07-2022/dicom");

        try (var stream = Files.walk(root)) {
            List<Path> paths = stream
                    .filter(Files::isRegularFile)
                    .filter(f -> f.getFileName().toString()
                            .contains(filename))
                    .toList();

            if (paths.isEmpty())
                return null;

            for (Path path : paths) {
                DicomHandler dh = new DicomHandler(path.toFile());
                String studyUid = dh.getString(TagFromName.StudyInstanceUID);
                if (path.toString().contains(studyUid))
                    return path.toFile();
            }

            return null;
        } catch (IOException | DicomException e) {
            return null;
        }
    }

    /**
     * Return the DICOM Handle object from the Coherent dataset.
     * Read all files whose name contains the given filename part and returns the one whose StudyInstanceUID matches.
     */
    public DicomHandler getDicomFile(String filename) {
        Path root = Path.of("src/main/resources/coherent-11-07-2022/dicom");

        try (var stream = Files.walk(root)) {
            List<Path> paths = stream
                    .filter(Files::isRegularFile)
                    .filter(f -> f.getFileName().toString()
                            .contains(filename))
                    .toList();

            if (paths.isEmpty())
                return null;

            for (Path path : paths) {
                DicomHandler dh = new DicomHandler(path.toFile());
                String studyUid = dh.getString(TagFromName.StudyInstanceUID);
                if (path.toString().contains(studyUid))
                    return dh;
            }

            return null;
        } catch (IOException | DicomException e) {
            return null;
        }
    }

    // Return the DICOM file corresponding to the given ImagingStudy resource.
    public DicomHandler getDicomFile(ImagingStudy img) {
        return getDicomFile(getFilenamePart(img));
    }

    // Get the DICOM file corresponding to the given ImagingStudy resource and encodes it in Base64.
    public String getBase64Binary(ImagingStudy img) {
        try {
            File file = getFileObject(getFilenamePart(img));
            byte[] encoded = Base64.encodeBase64(FileUtils.readFileToByteArray(file));
            return new String(encoded, StandardCharsets.US_ASCII);
        } catch (IOException e) {
            return null;
        }
    }
}
