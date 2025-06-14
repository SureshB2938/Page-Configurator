package com.example.pdfselector.controller;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.Base64;

@Controller
@SessionAttributes("pdfBytes")
public class PdfController {

    @GetMapping("/")
    public String uploadPage() {
        return "upload";
    }

    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, Model model) throws IOException {
        if (file.isEmpty()) {
            model.addAttribute("message", "Please select a PDF file to upload.");
            return "upload";
        }

        byte[] pdfBytes = file.getBytes();
        model.addAttribute("pdfBytes", pdfBytes); // Save to session

        List<String> base64Images = renderPdfToBase64Images(pdfBytes);
        model.addAttribute("images", base64Images);
        return "preview";
    }

    @GetMapping("/select-pages")
    public String showPageSelection(@ModelAttribute("pdfBytes") byte[] pdfBytes, Model model) throws IOException {
        if (pdfBytes == null) {
            model.addAttribute("message", "No PDF found in memory. Please upload again.");
            return "upload";
        }

        List<String> base64Images = renderPdfToBase64Images(pdfBytes);
        model.addAttribute("images", base64Images);
        return "preview";
    }

    @PostMapping("/submit-selection")
    public String handlePageSelection(
            @RequestParam List<Integer> pageNumbers,
            @RequestParam Map<String, String> pageModes,
            Model model,
            @ModelAttribute("pdfBytes") byte[] pdfBytes
    ) throws IOException {
        Map<String, List<Integer>> groupedPages = new HashMap<>();
        groupedPages.put("Color", new ArrayList<>());
        groupedPages.put("Black & White", new ArrayList<>());

        for (Integer i : pageNumbers) {
            String mode = pageModes.get("pageModes[" + i + "]");
            groupedPages.getOrDefault(mode, new ArrayList<>()).add(i + 1); // 1-indexed
        }

        Map<String, String> summaryByType = new HashMap<>();
        for (String type : groupedPages.keySet()) {
            List<Integer> pages = groupedPages.get(type);
            if (!pages.isEmpty()) {
                summaryByType.put(type, pages.stream().map(Object::toString).collect(Collectors.joining(", ")));
            }
        }

        List<String> base64Images = renderPdfToBase64Images(pdfBytes);
        model.addAttribute("images", base64Images);
        model.addAttribute("summaryByType", summaryByType);
        return "preview";
    }

    // Helper method to render PDF pages to Base64 images
    private List<String> renderPdfToBase64Images(byte[] pdfBytes) throws IOException {
        List<String> base64Images = new ArrayList<>();
        try (PDDocument document = PDDocument.load(pdfBytes)) {
            PDFRenderer pdfRenderer = new PDFRenderer(document);
            for (int page = 0; page < document.getNumberOfPages(); ++page) {
                BufferedImage bim = pdfRenderer.renderImageWithDPI(page, 60); // thumbnail
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(bim, "png", baos);
                String base64 = Base64.getEncoder().encodeToString(baos.toByteArray());
                base64Images.add("data:image/png;base64," + base64);
            }
        }
        return base64Images;
    }
}
