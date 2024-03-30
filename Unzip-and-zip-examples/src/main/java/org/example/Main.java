package org.example;

import org.example.service.ZipperService;
import org.example.service.impl.ZipperServiceImpl;

public class Main {
    public static void main(String[] args) {
        System.out.println("Working Execution...");

        ZipperService zipperService = new ZipperServiceImpl();

        zipperService.decompressZip("src/main/resources/example2.zip", "src/main/resources/unzips");

        zipperService.unzipWithPassword("src/main/resources/example-cero.zip", "src/main/resources/unzips", "0012");

        zipperService.unzipDecryptPassWithDigits("src/main/resources/example3.zip", "src/main/resources/unzips", 9999, 4);

        zipperService.unzipDecryptPassWithDigits("src/main/resources/example-one.zip", "src/main/resources/unzips", 9999, 4);
    }
}