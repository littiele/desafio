package org.example;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class FileProcessorCodeReview {
    private static final List<String> lines = new CopyOnWriteArrayList<>();

    private static Logger log = LoggerFactory.getLogger(FileProcessorCodeReview.class);
    public static void main(String[] args) throws IOException, InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(5);

        /*
        * Mudei para usar try-with-resources para garantir que o BufferedReader seja fechado corretamente.
        * Usei CopyOnWriteArrayList para evitar problemas de concorrência ao adicionar linhas.
        * Isso é mais eficiente do que sincronizar manualmente uma lista comum.
        */
        try(BufferedReader br = new BufferedReader(new FileReader("data.txt"))) {
            String line;
            /*
            * O for disparava 10 threads, mas cada uma lia o arquivo inteiro do zero (data.txt),
            * resultando na leitura do arquivo  10 vezes criando redundancia,
            * Acaba utilizando meoria e CPU desnecessario.
            */
            while ((line = br.readLine()) != null) {
                final String processedLine = line;
                executor.submit(() -> lines.add(processedLine.toUpperCase()));
            }

            executor.shutdown();
            /*
            * Adicionei esse if para evitar que a aplicação fique presa infinitamente
            *  se alguma task travar.
            */
            if (!executor.awaitTermination(1, TimeUnit.MINUTES)) {
                log.warn("Forcing shutdown of executor");
                executor.shutdownNow();
            }
            log.info("Lines processed={}, ", lines.size());
        } catch (IOException | InterruptedException e) {
            log.error("Error processing file", e.getMessage());
            Thread.currentThread().interrupt();
            throw e;
        }
    }
}
