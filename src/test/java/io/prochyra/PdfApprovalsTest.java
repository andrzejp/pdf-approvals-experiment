package io.prochyra;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.approvaltests.Approvals;
import org.approvaltests.awt.AwtApprovals;
import org.approvaltests.core.Options;
import org.approvaltests.reporters.ImageReporter;
import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

class PdfApprovalsTest {

    @Test
    void visually_approve_pdf() throws IOException {
        PDDocument pdDocument = Loader.loadPDF(new File("src/test/resources/test-pdf.pdf"));
        verifyPages(pdDocument);
    }

    private void verifyPages(PDDocument pdDocument) throws IOException {
        for (int pageNumber = 0; pageNumber < pdDocument.getNumberOfPages(); pageNumber++) {
            verifyPage(pdDocument, pageNumber);
        }
    }

    private void verifyPage(PDDocument pdDocument, int page) throws IOException {
        Options options = Approvals.NAMES.withParameters("page-" + page)
                .withReporter(new ImageReporter());
        BufferedImage renderedImage = new PDFRenderer(pdDocument)
                .renderImage(page);
        AwtApprovals.verify(renderedImage, options);
    }
}
