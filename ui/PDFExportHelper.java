package ui;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class PDFExportHelper {

    private static final float PAGE_MARGIN = 50f;
    private static final float TITLE_FONT_SIZE = 20f;
    private static final float META_FONT_SIZE = 10f;
    private static final float SECTION_FONT_SIZE = 13f;
    private static final float BODY_FONT_SIZE = 10.5f;
    private static final float LINE_HEIGHT = 14f;

    private static final PDType1Font TITLE_FONT = new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD);
    private static final PDType1Font META_FONT = new PDType1Font(Standard14Fonts.FontName.HELVETICA_OBLIQUE);
    private static final PDType1Font SECTION_FONT = new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD);
    private static final PDType1Font BODY_FONT = new PDType1Font(Standard14Fonts.FontName.COURIER);

    private PDFExportHelper() {
    }

    public static void exportTextToPDF(String text, File file) throws IOException {
        PDDocument document = new PDDocument();

        try {
            List<StyledLine> lines = buildStyledLines(text);

            int currentLineIndex = 0;
            int pageNumber = 1;

            while (currentLineIndex < lines.size()) {
                PDPage page = new PDPage(PDRectangle.LETTER);
                document.addPage(page);

                PDPageContentStream content = new PDPageContentStream(document, page);

                float pageWidth = page.getMediaBox().getWidth();
                float pageHeight = page.getMediaBox().getHeight();
                float usableWidth = pageWidth - (2 * PAGE_MARGIN);
                float y = pageHeight - PAGE_MARGIN;

                if (pageNumber == 1) {
                    y = drawHeader(content, pageWidth, y);
                } else {
                    y -= 10;
                }

                while (currentLineIndex < lines.size()) {
                    StyledLine styledLine = lines.get(currentLineIndex);

                    if (y < PAGE_MARGIN + 30) {
                        break;
                    }

                    drawLine(content, styledLine, PAGE_MARGIN, y);
                    y -= styledLine.lineHeight;
                    currentLineIndex++;
                }

                drawFooter(content, pageWidth, pageNumber);
                content.close();

                pageNumber++;
            }

            document.save(file);
        } finally {
            document.close();
        }
    }

    private static float drawHeader(PDPageContentStream content, float pageWidth, float y) throws IOException {
        String title = "SWYM NATION SYSTEM REPORT";
        String subtitle = "Generated from live database records";
        String timestamp = "Generated on: " + LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        float titleWidth = TITLE_FONT.getStringWidth(title) / 1000 * TITLE_FONT_SIZE;
        float subtitleWidth = META_FONT.getStringWidth(subtitle) / 1000 * 12f;
        float timestampWidth = META_FONT.getStringWidth(timestamp) / 1000 * META_FONT_SIZE;

        content.beginText();
        content.setFont(TITLE_FONT, TITLE_FONT_SIZE);
        content.newLineAtOffset((pageWidth - titleWidth) / 2, y);
        content.showText(title);
        content.endText();

        y -= 24;

        content.beginText();
        content.setFont(META_FONT, 12f);
        content.newLineAtOffset((pageWidth - subtitleWidth) / 2, y);
        content.showText(subtitle);
        content.endText();

        y -= 18;

        content.beginText();
        content.setFont(META_FONT, META_FONT_SIZE);
        content.newLineAtOffset((pageWidth - timestampWidth) / 2, y);
        content.showText(timestamp);
        content.endText();

        y -= 22;

        content.setLineWidth(1f);
        content.moveTo(PAGE_MARGIN, y);
        content.lineTo(pageWidth - PAGE_MARGIN, y);
        content.stroke();

        y -= 18;
        return y;
    }

    private static void drawFooter(PDPageContentStream content, float pageWidth, int pageNumber) throws IOException {
        String footer = "Page " + pageNumber;
        float footerWidth = META_FONT.getStringWidth(footer) / 1000 * META_FONT_SIZE;

        content.setLineWidth(0.6f);
        content.moveTo(PAGE_MARGIN, 35);
        content.lineTo(pageWidth - PAGE_MARGIN, 35);
        content.stroke();

        content.beginText();
        content.setFont(META_FONT, META_FONT_SIZE);
        content.newLineAtOffset((pageWidth - footerWidth) / 2, 20);
        content.showText(footer);
        content.endText();
    }

    private static void drawLine(PDPageContentStream content, StyledLine styledLine, float x, float y)
            throws IOException {
        if (styledLine.text == null || styledLine.text.isEmpty()) {
            return;
        }

        content.beginText();
        content.setFont(styledLine.font, styledLine.fontSize);
        content.newLineAtOffset(x, y);
        content.showText(sanitize(styledLine.text));
        content.endText();
    }

    private static List<StyledLine> buildStyledLines(String text) throws IOException {
        List<StyledLine> output = new ArrayList<StyledLine>();
        String[] rawLines = text.split("\\r?\\n");

        for (int i = 0; i < rawLines.length; i++) {
            String line = rawLines[i] == null ? "" : rawLines[i].trim();

            if (line.isEmpty()) {
                output.add(new StyledLine("", BODY_FONT, BODY_FONT_SIZE, 10f));
                continue;
            }

            if (line.startsWith("===") && line.endsWith("===")) {
                String sectionTitle = line.replace("=", "").trim().toUpperCase();

                output.add(new StyledLine("", BODY_FONT, BODY_FONT_SIZE, 6f));
                output.add(new StyledLine(sectionTitle, SECTION_FONT, SECTION_FONT_SIZE, 18f));
                output.add(new StyledLine(repeat("-", 78), BODY_FONT, BODY_FONT_SIZE, 14f));
                continue;
            }

            List<String> wrapped = wrapText(line, 90);
            for (int j = 0; j < wrapped.size(); j++) {
                output.add(new StyledLine(wrapped.get(j), BODY_FONT, BODY_FONT_SIZE, LINE_HEIGHT));
            }
        }

        return output;
    }

    private static List<String> wrapText(String text, int maxChars) {
        List<String> lines = new ArrayList<String>();

        if (text == null || text.isEmpty()) {
            lines.add("");
            return lines;
        }

        String remaining = text;

        while (remaining.length() > maxChars) {
            int breakPoint = remaining.lastIndexOf(' ', maxChars);
            if (breakPoint <= 0) {
                breakPoint = maxChars;
            }

            lines.add(remaining.substring(0, breakPoint).trim());
            remaining = remaining.substring(breakPoint).trim();
        }

        if (!remaining.isEmpty()) {
            lines.add(remaining);
        }

        return lines;
    }

    private static String repeat(String s, int count) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < count; i++) {
            builder.append(s);
        }
        return builder.toString();
    }

    private static String sanitize(String text) {
        return text
                .replace("\t", "    ")
                .replace("\r", "")
                .replace("\n", " ");
    }

    private static class StyledLine {
        private final String text;
        private final PDType1Font font;
        private final float fontSize;
        private final float lineHeight;

        private StyledLine(String text, PDType1Font font, float fontSize, float lineHeight) {
            this.text = text;
            this.font = font;
            this.fontSize = fontSize;
            this.lineHeight = lineHeight;
        }
    }
}