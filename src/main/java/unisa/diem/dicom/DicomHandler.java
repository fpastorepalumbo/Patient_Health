package unisa.diem.dicom;

import com.pixelmed.dicom.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;
import java.util.logging.Logger;

// Class for handling DICOM files and their metadata.
public class DicomHandler {

    private static final Logger logger = Logger.getLogger(DicomHandler.class.getName());
    private final AttributeList attrs;
    private int numRows;
    private int numCols;
    private boolean isWord;
    private boolean isRGB;
    private short[] shortPixelData;
    private byte[] bytePixelData;

    public DicomHandler(File file) throws IOException, DicomException {
        attrs = read(file.getAbsolutePath());
        loadEssentialData();
    }

    // Read a DICOM file and returns its attribute list
    private AttributeList read(String filePath) throws IOException, DicomException {
        File dicomFile = new File(filePath);
        DicomInputStream dicomInputStream;
        if (!dicomFile.exists())
            throw new FileNotFoundException("File not found: " + filePath);

        dicomInputStream = new DicomInputStream(dicomFile);
        AttributeList attributeList = new AttributeList();
        attributeList.read(dicomInputStream);
        dicomInputStream.close();
        return attributeList;
    }

    // Load essential and frequently-used data from the attribute list
    private void loadEssentialData() throws DicomException {
        assert attrs != null;

        numRows = attrs.get(TagFromName.Rows).getIntegerValues()[0];
        numCols = attrs.get(TagFromName.Columns).getIntegerValues()[0];

        Attribute photometricAttr = attrs.get(TagFromName.PhotometricInterpretation);
        isRGB = photometricAttr.getSingleStringValueOrEmptyString().equals("RGB");

        Attribute pixelDataAttr = attrs.get(TagFromName.PixelData);
        String pixelDataVR = pixelDataAttr.getVRAsString();
        isWord = pixelDataVR.equals("OW");

        if (isWord)
            shortPixelData = pixelDataAttr.getShortValues();
        else
            bytePixelData = pixelDataAttr.getByteValues();
    }

    // Return a string representation of the attribute list
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        assert attrs != null;
        attrs.forEach((key, value) -> {
            DicomDictionary dict = AttributeList.getDictionary();
            sb.append(key);
            sb.append(" ");
            sb.append(dict.getNameFromTag(key));
            sb.append(": [");
            sb.append(value.getVRAsString());
            sb.append(", ");
            sb.append(value.getVL());
            sb.append("] ");
            sb.append(value.getSingleStringValueOrEmptyString());
            sb.append("\n");
        });

        return sb.toString();
    }

    /**
     * Return values for a given attribute tag as a string array
     * If the attribute is not present, or if it is not a string attribute, return null
     */
    public String[] getStrings(AttributeTag tag) {
        assert attrs != null;
        try {
            Attribute attr = attrs.get(tag);
            if (attr == null)
                return null;
            return attr.getStringValues();
        } catch (DicomException e) {
            logger.severe("Error reading DICOM attribute " + e.getMessage());
            return null;
        }
    }

    // Return the first value for a given attribute tag as a string
    public String getString(AttributeTag tag) {
        String[] strings = getStrings(tag);
        if (strings == null)
            return null;
        return strings[0];
    }

    /**
     * Return values for a given attribute tag as an integer array
     * If the attribute is not present, or if it is not an integer attribute, return null
     */
    public int[] getInts(AttributeTag tag) {
        assert attrs != null;
        try {
            return attrs.get(tag).getIntegerValues();
        } catch (DicomException e) {
            logger.severe("Error reading DICOM attribute " + e.getMessage());
            return null;
        }
    }

    // Return the first value for a given attribute tag as an integer
    public int getInt(AttributeTag tag) {
        return getInts(tag)[0];
    }

    // Verify whether the attribute list contains a given attribute tag
    public boolean hasAttr(AttributeTag tag) {
        assert attrs != null;
        return attrs.get(tag) != null;
    }

    // Return the frame at the given index as a DicomFrame object
    private DicomFrame getFrame(int index) {
        try {
            int start, end;
            Object pxs;

            if (isRGB) {
                start = index * numRows * numCols * 3;
                end = start + numRows * numCols * 3;
            } else {
                start = index * numRows * numCols;
                end = start + numRows * numCols;
            }

            if (isWord)
                pxs = Arrays.copyOfRange(shortPixelData, start, end);
            else
                pxs = Arrays.copyOfRange(bytePixelData, start, end);

            return new DicomFrame(numCols, numRows, isWord, pxs);
        } catch (IOException e) {
            logger.severe("Error reading DICOM attribute list");
            return null;
        }
    }

    // Return the frame at the given index as a base64-encoded string
    public String serveFrame(int index) {
        DicomFrame frame = getFrame(index);
        assert frame != null;
        return frame.toBase64();
    }

    // Return a list of base64-encoded strings for the frames in the given range
    public ObservableList<String> serveFrames(int start, int end) {
        ObservableList<String> frames = FXCollections.observableArrayList();
        for (int i = start; i < end; i++) {
            frames.add(serveFrame(i));
        }
        return frames;
    }

    // Return a list of base64-encoded strings for all the frames
    public ObservableList<String> serveAllFrames() {
        int numFrames = getInt(TagFromName.NumberOfFrames);
        return serveFrames(0, numFrames);
    }

    // Hidden Class for handling DICOM frames
    private static class DicomFrame {
        byte[] data;

        /**
         * Create a DicomFrame object from either a byte array or a short array of PixelData
         * The array's element size is determined by the value representation (VR) of the PixelData attribute
         */
        public DicomFrame(int w, int h, boolean isWord, Object pxs) throws IOException {
            if (!isWord) {
                if (!(pxs instanceof byte[] bytes))
                    throw new IllegalArgumentException("Expected byte[] for byte data, got " + pxs.getClass().getName());

                BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_BYTE_GRAY);
                image.getRaster().setDataElements(0, 0, w, h, bytes);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(image, "png", baos);
                data = baos.toByteArray();
            } else {
                if (!(pxs instanceof short[] shorts))
                    throw new IllegalArgumentException("Expected short[] for word data, got " + pxs.getClass().getName());

                BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_USHORT_GRAY);
                image.getRaster().setDataElements(0, 0, w, h, shorts);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(image, "png", baos);
                data = baos.toByteArray();
            }
        }

        // Return the frame as a base64-encoded string
        public String toBase64() {
            return Base64.getEncoder().encodeToString(data);
        }
    }
}
