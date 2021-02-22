package com.example.formatter_adapter;

import com.example.formatter_adapter.entities.Document;
import com.example.formatter_adapter.repositories.DocumentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class FormatterAdapterApplicationTests {

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    @Rollback(value = false)
    void testInsertDocument() throws IOException {
        File file = new File("C:\\program.doc");
        Document document = new Document();
        document.setName(file.getName());

        byte[] bytes = Files.readAllBytes(file.toPath());
        document.setContent(bytes);
        long fileSize = bytes.length;
        document.setSize(fileSize);
        document.setUploadTime(new Date());

        Document saveDoc = documentRepository.save(document);

        Document exists = entityManager.find(Document.class, saveDoc.getId());

        assertThat(exists.getSize()).isEqualTo(fileSize);
    }

}
