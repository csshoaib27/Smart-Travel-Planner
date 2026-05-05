package com.smarttravel.service;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import com.lowagie.text.pdf.draw.LineSeparator;
import com.smarttravel.dto.ItineraryPdfRequest;
import com.smarttravel.dto.ItineraryPlanPdfRequest;
import org.springframework.stereotype.Service;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class PdfService {

    private static final Color BRAND_BLUE  = new Color(37, 99, 235);
    private static final Color LIGHT_GRAY  = new Color(243, 244, 246);
    private static final Color DARK_TEXT   = new Color(17, 24, 39);
    private static final Color MUTED_TEXT  = new Color(107, 114, 128);
    private static final Color TOTAL_GREEN = new Color(22, 163, 74);

    public byte[] generateItineraryPdf(ItineraryPdfRequest req) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            String sym = currencySymbol(req.getCurrency());
            Document doc = new Document(PageSize.A4, 50, 50, 60, 60);
            PdfWriter.getInstance(doc, out);
            doc.open();

            addHeader(doc, req);
            doc.add(Chunk.NEWLINE);
            addTripDetails(doc, req, sym);
            doc.add(Chunk.NEWLINE);
            addCostBreakdown(doc, req, sym);
            doc.add(Chunk.NEWLINE);
            addTotals(doc, req, sym);
            doc.add(Chunk.NEWLINE);
            addFooter(doc);

            doc.close();
            return out.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate PDF", e);
        }
    }

    // ── Header ──────────────────────────────────────────────────────────────

    private void addHeader(Document doc, ItineraryPdfRequest req) throws DocumentException {
        Font appFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, MUTED_TEXT);
        Paragraph appName = new Paragraph("SMART TRAVEL PLANNER", appFont);
        appName.setAlignment(Element.ALIGN_CENTER);
        doc.add(appName);
        doc.add(new Chunk("\n"));

        String title = req.getTripTitle() != null && !req.getTripTitle().isBlank()
                ? req.getTripTitle() : "My Trip Itinerary";
        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 22, BRAND_BLUE);
        Paragraph titlePara = new Paragraph(title, titleFont);
        titlePara.setAlignment(Element.ALIGN_CENTER);
        doc.add(titlePara);

        if (req.getDestinationName() != null && !req.getDestinationName().isBlank()) {
            Font destFont = FontFactory.getFont(FontFactory.HELVETICA, 13, MUTED_TEXT);
            Paragraph dest = new Paragraph(req.getDestinationName(), destFont);
            dest.setAlignment(Element.ALIGN_CENTER);
            doc.add(dest);
        }

        doc.add(new Chunk("\n"));
        doc.add(new LineSeparator(1.5f, 100, BRAND_BLUE, Element.ALIGN_CENTER, -2));
    }

    // ── Trip overview table ──────────────────────────────────────────────────

    private void addTripDetails(Document doc, ItineraryPdfRequest req, String sym) throws DocumentException {
        Font sectionFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, DARK_TEXT);
        Paragraph heading = new Paragraph("Trip Overview", sectionFont);
        heading.setSpacingBefore(12);
        heading.setSpacingAfter(8);
        doc.add(heading);

        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);
        table.setWidths(new float[]{1, 1});

        addDetailRow(table, "Currency",         req.getCurrency() != null ? req.getCurrency() : "INR");
        addDetailRow(table, "Total Days",        req.getNumberOfDays() + " day(s)");
        addDetailRow(table, "Number of People",  req.getNumberOfPeople() + " person(s)");
        addDetailRow(table, "Number of Rooms",   req.getNumberOfRooms() + " room(s)");

        if (notBlank(req.getStartDate())) addDetailRow(table, "Start Date", formatDate(req.getStartDate()));
        if (notBlank(req.getEndDate()))   addDetailRow(table, "End Date",   formatDate(req.getEndDate()));

        doc.add(table);
    }

    private void addDetailRow(PdfPTable table, String label, String value) {
        Font lf = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, MUTED_TEXT);
        Font vf = FontFactory.getFont(FontFactory.HELVETICA, 10, DARK_TEXT);

        PdfPCell lc = new PdfPCell(new Phrase(label, lf));
        lc.setBorder(Rectangle.NO_BORDER);
        lc.setBackgroundColor(LIGHT_GRAY);
        lc.setPadding(8);

        PdfPCell vc = new PdfPCell(new Phrase(value, vf));
        vc.setBorder(Rectangle.NO_BORDER);
        vc.setBackgroundColor(LIGHT_GRAY);
        vc.setPadding(8);

        table.addCell(lc);
        table.addCell(vc);
    }

    // ── Cost breakdown table ─────────────────────────────────────────────────

    private void addCostBreakdown(Document doc, ItineraryPdfRequest req, String sym) throws DocumentException {
        Font sectionFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, DARK_TEXT);
        Paragraph heading = new Paragraph("Cost Breakdown", sectionFont);
        heading.setSpacingBefore(14);
        heading.setSpacingAfter(8);
        doc.add(heading);

        int people = safeInt(req.getNumberOfPeople(), 1);
        int days   = safeInt(req.getNumberOfDays(), 1);
        int rooms  = safeInt(req.getNumberOfRooms(), 1);

        BigDecimal accommodation = orZero(req.getHotelPricePerNight()).multiply(bd(days)).multiply(bd(rooms));
        BigDecimal food          = orZero(req.getFoodCostPerDay()).multiply(bd(days)).multiply(bd(people));
        BigDecimal transport     = orZero(req.getTransportationCost());
        BigDecimal activities    = orZero(req.getActivitiesCost());
        BigDecimal other         = orZero(req.getOtherCosts());

        PdfPTable table = new PdfPTable(3);
        table.setWidthPercentage(100);
        table.setWidths(new float[]{3, 5, 2});

        addTableHeader(table, "Category", "Calculation", "Amount");
        addCostRow(table, sym, "Accommodation",
                sym + fmt(req.getHotelPricePerNight()) + "/night × " + days + " nights × " + rooms + " room(s)", accommodation);
        addCostRow(table, sym, "Transportation", "Fixed cost", transport);
        addCostRow(table, sym, "Food",
                sym + fmt(req.getFoodCostPerDay()) + "/day × " + days + " days × " + people + " person(s)", food);
        addCostRow(table, sym, "Activities",  "Fixed cost", activities);
        addCostRow(table, sym, "Other",       "Fixed cost", other);

        doc.add(table);
    }

    private void addTableHeader(PdfPTable table, String... headers) {
        Font hf = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, Color.WHITE);
        for (String h : headers) {
            PdfPCell cell = new PdfPCell(new Phrase(h, hf));
            cell.setBackgroundColor(BRAND_BLUE);
            cell.setPadding(8);
            cell.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell);
        }
    }

    private void addCostRow(PdfPTable table, String sym, String category, String calc, BigDecimal amount) {
        Font catFont  = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, DARK_TEXT);
        Font calcFont = FontFactory.getFont(FontFactory.HELVETICA, 9, MUTED_TEXT);
        Font amtFont  = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, DARK_TEXT);

        PdfPCell catCell = new PdfPCell(new Phrase(category, catFont));
        catCell.setPadding(7);
        catCell.setBorderColor(LIGHT_GRAY);

        PdfPCell calcCell = new PdfPCell(new Phrase(calc, calcFont));
        calcCell.setPadding(7);
        calcCell.setBorderColor(LIGHT_GRAY);

        PdfPCell amtCell = new PdfPCell(new Phrase(sym + fmt(amount), amtFont));
        amtCell.setPadding(7);
        amtCell.setBorderColor(LIGHT_GRAY);
        amtCell.setHorizontalAlignment(Element.ALIGN_RIGHT);

        table.addCell(catCell);
        table.addCell(calcCell);
        table.addCell(amtCell);
    }

    // ── Totals ───────────────────────────────────────────────────────────────

    private void addTotals(Document doc, ItineraryPdfRequest req, String sym) throws DocumentException {
        int people = safeInt(req.getNumberOfPeople(), 1);
        int days   = safeInt(req.getNumberOfDays(), 1);
        int rooms  = safeInt(req.getNumberOfRooms(), 1);

        BigDecimal accommodation = orZero(req.getHotelPricePerNight()).multiply(bd(days)).multiply(bd(rooms));
        BigDecimal food          = orZero(req.getFoodCostPerDay()).multiply(bd(days)).multiply(bd(people));
        BigDecimal total = accommodation
                .add(orZero(req.getTransportationCost()))
                .add(food)
                .add(orZero(req.getActivitiesCost()))
                .add(orZero(req.getOtherCosts()));
        BigDecimal perPerson = total.divide(bd(people), 2, RoundingMode.HALF_UP);

        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(60);
        table.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.setWidths(new float[]{3, 2});

        Font totalLabel = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, DARK_TEXT);
        Font totalAmt   = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, TOTAL_GREEN);
        Font ppLabel    = FontFactory.getFont(FontFactory.HELVETICA, 10, MUTED_TEXT);
        Font ppAmt      = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, MUTED_TEXT);

        PdfPCell tlCell = new PdfPCell(new Phrase("Total Trip Cost", totalLabel));
        tlCell.setBackgroundColor(LIGHT_GRAY);
        tlCell.setPadding(10);
        tlCell.setBorder(Rectangle.NO_BORDER);

        PdfPCell taCell = new PdfPCell(new Phrase(sym + fmt(total), totalAmt));
        taCell.setBackgroundColor(LIGHT_GRAY);
        taCell.setPadding(10);
        taCell.setBorder(Rectangle.NO_BORDER);
        taCell.setHorizontalAlignment(Element.ALIGN_RIGHT);

        PdfPCell plCell = new PdfPCell(new Phrase("Per Person", ppLabel));
        plCell.setPadding(8);
        plCell.setBorder(Rectangle.NO_BORDER);

        PdfPCell paCell = new PdfPCell(new Phrase(sym + fmt(perPerson), ppAmt));
        paCell.setPadding(8);
        paCell.setBorder(Rectangle.NO_BORDER);
        paCell.setHorizontalAlignment(Element.ALIGN_RIGHT);

        table.addCell(tlCell);
        table.addCell(taCell);
        table.addCell(plCell);
        table.addCell(paCell);

        doc.add(table);
    }

    // ── Footer ───────────────────────────────────────────────────────────────

    private void addFooter(Document doc) throws DocumentException {
        doc.add(new Chunk("\n"));
        doc.add(new LineSeparator(0.5f, 100, MUTED_TEXT, Element.ALIGN_CENTER, -2));
        doc.add(new Chunk("\n"));

        Font footerFont = FontFactory.getFont(FontFactory.HELVETICA, 8, MUTED_TEXT);
        Paragraph footer = new Paragraph(
                "Generated by Smart Travel Planner  •  " +
                LocalDate.now().format(DateTimeFormatter.ofPattern("MMMM d, yyyy")), footerFont);
        footer.setAlignment(Element.ALIGN_CENTER);
        doc.add(footer);
    }

    // ── Helpers ──────────────────────────────────────────────────────────────

    private String currencySymbol(String currency) {
        if (currency == null) return "₹";
        return switch (currency.toUpperCase()) {
            case "USD" -> "$";
            case "EUR" -> "€";
            case "GBP" -> "£";
            case "JPY" -> "¥";
            case "AED" -> "AED ";
            case "SGD" -> "S$";
            case "AUD" -> "A$";
            default    -> "₹";
        };
    }

    private String fmt(BigDecimal v) {
        return orZero(v).setScale(2, RoundingMode.HALF_UP).toPlainString();
    }

    private String formatDate(String raw) {
        try { return LocalDate.parse(raw).format(DateTimeFormatter.ofPattern("MMM d, yyyy")); }
        catch (Exception e) { return raw; }
    }

    private boolean notBlank(String s) { return s != null && !s.isBlank(); }
    private BigDecimal orZero(BigDecimal v) { return v != null ? v : BigDecimal.ZERO; }
    private BigDecimal bd(int v) { return BigDecimal.valueOf(v); }
    private int safeInt(Integer v, int def) { return (v != null && v > 0) ? v : def; }

    // ═══════════════════════════════════════════════════════════════════════
    //  ITINERARY PLAN PDF  (day-by-day plan with activities)
    // ═══════════════════════════════════════════════════════════════════════

    public byte[] generateItineraryPlanPdf(ItineraryPlanPdfRequest req) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            String sym = currencySymbol(req.getCurrency());
            Document doc = new Document(PageSize.A4, 45, 45, 55, 55);
            PdfWriter.getInstance(doc, out);
            doc.open();

            addPlanHeader(doc, req, sym);
            doc.add(Chunk.NEWLINE);
            addPlanOverview(doc, req, sym);
            doc.add(Chunk.NEWLINE);
            addDayCards(doc, req, sym);
            doc.add(Chunk.NEWLINE);
            addHotelsSection(doc, req, sym);
            doc.add(Chunk.NEWLINE);
            addPlanFooter(doc);

            doc.close();
            return out.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate itinerary plan PDF", e);
        }
    }

    private void addPlanHeader(Document doc, ItineraryPlanPdfRequest req, String sym) throws DocumentException {
        Font appFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 9, MUTED_TEXT);
        Paragraph app = new Paragraph("SMART TRAVEL PLANNER — ITINERARY", appFont);
        app.setAlignment(Element.ALIGN_CENTER);
        doc.add(app);
        doc.add(new Chunk("\n"));

        String title = notBlank(req.getTitle()) ? req.getTitle() : "My Itinerary";
        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 22, BRAND_BLUE);
        Paragraph tp = new Paragraph(title, titleFont);
        tp.setAlignment(Element.ALIGN_CENTER);
        doc.add(tp);

        if (notBlank(req.getDestinationName())) {
            Font df = FontFactory.getFont(FontFactory.HELVETICA, 13, MUTED_TEXT);
            Paragraph dp = new Paragraph("📍 " + req.getDestinationName(), df);
            dp.setAlignment(Element.ALIGN_CENTER);
            doc.add(dp);
        }
        doc.add(new Chunk("\n"));
        doc.add(new LineSeparator(1.5f, 100, BRAND_BLUE, Element.ALIGN_CENTER, -2));
    }

    private void addPlanOverview(Document doc, ItineraryPlanPdfRequest req, String sym) throws DocumentException {
        Font sh = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, DARK_TEXT);
        Paragraph h = new Paragraph("Trip Overview", sh);
        h.setSpacingBefore(10); h.setSpacingAfter(8);
        doc.add(h);

        PdfPTable t = new PdfPTable(4);
        t.setWidthPercentage(100);
        t.setWidths(new float[]{2, 2, 2, 2});

        addPlanCell(t, "Package", req.getPackageType() != null ? req.getPackageType() : "-");
        addPlanCell(t, "Budget Tier", req.getBudgetTier() != null ? req.getBudgetTier() : "-");
        addPlanCell(t, "Currency", req.getCurrency() != null ? req.getCurrency() : "INR");
        addPlanCell(t, "Total Days", (req.getNumberOfDays() != null ? req.getNumberOfDays() : 0) + " day(s)");

        if (notBlank(req.getStartDate())) addPlanCell(t, "Start", formatDate(req.getStartDate()));
        if (notBlank(req.getEndDate()))   addPlanCell(t, "End",   formatDate(req.getEndDate()));

        if (req.getTotalCost() != null) {
            addPlanCell(t, "Est. Total", sym + fmt(req.getTotalCost()));
        }
        doc.add(t);
    }

    private void addPlanCell(PdfPTable t, String label, String value) {
        Font lf = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 9, MUTED_TEXT);
        Font vf = FontFactory.getFont(FontFactory.HELVETICA, 10, DARK_TEXT);

        PdfPCell lc = new PdfPCell();
        lc.setBorder(Rectangle.NO_BORDER);
        lc.setBackgroundColor(LIGHT_GRAY);
        lc.setPadding(7);
        Paragraph lp = new Paragraph(label + "\n", lf);
        lp.add(new Phrase(value, vf));
        lc.addElement(lp);
        t.addCell(lc);
    }

    private void addDayCards(Document doc, ItineraryPlanPdfRequest req, String sym) throws DocumentException {
        if (req.getDays() == null || req.getDays().isEmpty()) return;

        Font sh = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, DARK_TEXT);
        Paragraph h = new Paragraph("Day-by-Day Plan", sh);
        h.setSpacingBefore(10); h.setSpacingAfter(8);
        doc.add(h);

        for (ItineraryPlanPdfRequest.DayPlan day : req.getDays()) {
            PdfPTable card = new PdfPTable(1);
            card.setWidthPercentage(100);
            card.setSpacingAfter(8);

            // Day header
            Font dhf = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11, Color.WHITE);
            String header = "Day " + day.getDayNumber();
            if (day.getBudget() != null) header += "   |   Budget: " + sym + fmt(day.getBudget());
            PdfPCell hc = new PdfPCell(new Phrase(header, dhf));
            hc.setBackgroundColor(BRAND_BLUE);
            hc.setPadding(8);
            hc.setBorder(Rectangle.NO_BORDER);
            card.addCell(hc);

            // Activities — split on " | " into separate lines
            if (notBlank(day.getActivities())) {
                String[] parts = day.getActivities().split(" \\| ");
                StringBuilder sb = new StringBuilder();
                for (String p : parts) sb.append(p.trim()).append("\n");

                Font af = FontFactory.getFont(FontFactory.HELVETICA, 10, DARK_TEXT);
                PdfPCell ac = new PdfPCell(new Phrase(sb.toString().trim(), af));
                ac.setPadding(9);
                ac.setBorderColor(LIGHT_GRAY);
                ac.setBackgroundColor(Color.WHITE);
                card.addCell(ac);
            }

            // Tip/note
            if (notBlank(day.getNote())) {
                Font nf = FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 9, MUTED_TEXT);
                PdfPCell nc = new PdfPCell(new Phrase("💡 " + day.getNote(), nf));
                nc.setPadding(7);
                nc.setBorderColor(LIGHT_GRAY);
                nc.setBackgroundColor(new Color(254, 252, 232));
                card.addCell(nc);
            }

            doc.add(card);
        }
    }

    private void addHotelsSection(Document doc, ItineraryPlanPdfRequest req, String sym) throws DocumentException {
        if (req.getHotels() == null || req.getHotels().isEmpty()) return;

        Font sh = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, DARK_TEXT);
        Paragraph h = new Paragraph("Recommended Hotels", sh);
        h.setSpacingBefore(10); h.setSpacingAfter(8);
        doc.add(h);

        PdfPTable table = new PdfPTable(3);
        table.setWidthPercentage(100);
        table.setWidths(new float[]{5, 2, 4});
        addTableHeader(table, "Hotel", "Rating", "Price Range");

        for (ItineraryPlanPdfRequest.HotelInfo hotel : req.getHotels()) {
            Font nf = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, DARK_TEXT);
            Font vf = FontFactory.getFont(FontFactory.HELVETICA, 10, DARK_TEXT);

            PdfPCell nameCell = new PdfPCell(new Phrase(hotel.getName() != null ? hotel.getName() : "-", nf));
            nameCell.setPadding(7); nameCell.setBorderColor(LIGHT_GRAY);

            String ratingText = hotel.getStarRating() != null
                    ? hotel.getStarRating() + " / 5"
                      + (hotel.getReviewCount() != null ? "  (" + hotel.getReviewCount() + " reviews)" : "")
                    : "-";
            PdfPCell ratingCell = new PdfPCell(new Phrase(ratingText, vf));
            ratingCell.setPadding(7); ratingCell.setBorderColor(LIGHT_GRAY);

            String priceText = hotel.getPriceRange() != null ? hotel.getPriceRange() : "-";
            PdfPCell priceCell = new PdfPCell(new Phrase(priceText, vf));
            priceCell.setPadding(7); priceCell.setBorderColor(LIGHT_GRAY);
            priceCell.setHorizontalAlignment(Element.ALIGN_RIGHT);

            table.addCell(nameCell);
            table.addCell(ratingCell);
            table.addCell(priceCell);
        }
        doc.add(table);
    }

    private void addPlanFooter(Document doc) throws DocumentException {
        doc.add(new LineSeparator(0.5f, 100, MUTED_TEXT, Element.ALIGN_CENTER, -2));
        doc.add(new Chunk("\n"));
        Font ff = FontFactory.getFont(FontFactory.HELVETICA, 8, MUTED_TEXT);
        Paragraph fp = new Paragraph(
                "Generated by Smart Travel Planner  •  " +
                LocalDate.now().format(DateTimeFormatter.ofPattern("MMMM d, yyyy")), ff);
        fp.setAlignment(Element.ALIGN_CENTER);
        doc.add(fp);
    }
}
