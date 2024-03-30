package org.example.service.impl;

import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.model.FileHeader;
import org.example.service.ZipperService;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ZipperServiceImpl implements ZipperService {
    @Override
    public void decompressZip(String zipFilePath, String destDir) {
        File dir = new File( destDir ) ;
        // creating an output directory if it doesn't exist already
        if( !dir.exists( ) ) dir.mkdirs( ) ;

        FileInputStream FiS ;
        // buffer to read and write data in the file
        byte[ ] buffer = new byte[ 1024 ] ;
        try {
            FiS = new FileInputStream( zipFilePath ) ;
            ZipInputStream zis = new ZipInputStream( FiS ) ;
            ZipEntry ZE = zis.getNextEntry( ) ;
            while( ZE != null ) {
                String fileName = ZE.getName( ) ;
                File newFile = new File( destDir + File.separator + fileName ) ;
                System.out.println( " Unzipping to " + newFile.getAbsolutePath( ) ) ;
                // create directories for sub directories in zip
                new File( newFile.getParent( ) ).mkdirs( ) ;
                FileOutputStream fos = new FileOutputStream( newFile ) ;
                int len ;
                while ( ( len = zis.read( buffer ) )  > 0 ) {
                    fos.write( buffer, 0, len ) ;
                }
                fos.close( ) ;
                // close this ZipEntry
                zis.closeEntry( ) ;
                ZE = zis.getNextEntry( ) ;
            }
            // close last ZipEntry
            zis.closeEntry( ) ;
            zis.close( ) ;
            FiS.close( ) ;
        } catch ( IOException e ) {
            e.printStackTrace( ) ;
        }
    }

    @Override
    public void unzipWithPassword(String zipFilePath, String destDir, String password) {
        final FileNameExtensionFilter extensionFilter = new FileNameExtensionFilter("N/A", "zip");
        //Folder where zip file is present
        File file = new File(zipFilePath);
        try (ZipFile zipFile = new ZipFile(file)) {
            if (extensionFilter.accept(file)) {
                if (zipFile.isEncrypted()) {
                    //Your ZIP password
                    zipFile.setPassword(password.toCharArray());
                }
                List<FileHeader> fileHeaderList = zipFile.getFileHeaders();

                for (FileHeader fileHeader : fileHeaderList) {
                    //Path where you want to Extract
                    zipFile.extractFile(fileHeader, destDir);
                    System.out.println("Extracted");
                }
            }
        } catch (Exception e) {
            System.out.println("Please Try Again");
        }
    }

    //Use digits to decompress example for 4 digits 9999
    //Only use with numeric keys whose digits you know.
    //This method uses recursion
    @Override
    public void unzipDecryptPassWithDigits(String zipFilePath, String destDir, Integer digits, int maxDigits) {
        final FileNameExtensionFilter extensionFilter = new FileNameExtensionFilter("N/A", "zip");
        //Folder where zip file is present
        File file = new File(zipFilePath);
        if (digits !=0 || digits ==null){
            StringBuilder pass = new StringBuilder(digits.toString());
            while (pass.length() < maxDigits) pass.insert(0, "0");

            try (ZipFile zipFile = new ZipFile(file)) {
                if (extensionFilter.accept(file)) {
                    if (zipFile.isEncrypted()) {
                        //Your ZIP password
                        zipFile.setPassword(pass.toString().toCharArray());
                    }
                    List<FileHeader> fileHeaderList = zipFile.getFileHeaders();

                    for (FileHeader fileHeader : fileHeaderList) {
                        //Path where you want to Extract
                        zipFile.extractFile(fileHeader, destDir);
                        System.out.println("Extracted");
                    }
                    System.out.println("This is the key : " + pass +": \n");
                }
            } catch (Exception e) {
                unzipDecryptPassWithDigits(zipFilePath, destDir, digits-1, maxDigits);
            }
        } else {
            System.out.println("Invalid Digits");
        }
    }
}
