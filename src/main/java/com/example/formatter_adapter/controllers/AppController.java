package com.example.formatter_adapter.controllers;

import com.example.formatter_adapter.entities.Document;
import com.example.formatter_adapter.repositories.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.Date;
import java.util.List;


@Controller
public class AppController {

    @Autowired
    private DocumentRepository repo;


    @GetMapping("/")
    public String viewHomePage(Model model){
        List<Document> all = repo.findAll();
        model.addAttribute("listOfDocuments", all);
        return "home";
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("document")MultipartFile multipartFile,
                             RedirectAttributes redirectAttributes) throws IOException {

        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

        Document document = new Document();
        document.setName(fileName);
        document.setContent(multipartFile.getBytes());
        document.setSize(multipartFile.getSize());
        document.setUploadTime(new Date());

        repo.save(document);

        redirectAttributes.addFlashAttribute("message", "File upload successfully");

        return "redirect:/";

    }
}
