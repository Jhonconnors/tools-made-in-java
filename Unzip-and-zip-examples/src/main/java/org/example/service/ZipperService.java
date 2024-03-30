package org.example.service;

public interface ZipperService {

    void decompressZip(String zipFilePath, String destDir);

    void unzipWithPassword(String zipFilePath, String destDir, String password);

    void unzipDecryptPassWithDigits(String zipFilePath, String destDir, Integer digits);
}
