package com.bridgelabz.addressbook;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class AddressBookCSV {

    private static final String CSV_FILE_PATH = "src/main/resources/AddressBookContact.csv";

    public void writeDataToCSV(Map<String, ArrayList<Contact>> addressBook) {
        try (
                Writer writer = Files.newBufferedWriter(Paths.get(CSV_FILE_PATH))
        ) {
            StatefulBeanToCsvBuilder builder = new StatefulBeanToCsvBuilder<>(writer);
            StatefulBeanToCsv beanWriter = builder.build();

            List<Contact> myContactList = addressBook.values().stream().flatMap(Collection::stream).collect(Collectors.toList());
            beanWriter.write(myContactList);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CsvRequiredFieldEmptyException e) {
            throw new RuntimeException(e);
        } catch (CsvDataTypeMismatchException e) {
            throw new RuntimeException(e);
        }
    }

    public void readDataFromCSV() {
        try (
                Reader reader = Files.newBufferedReader(Paths.get(CSV_FILE_PATH))
        ) {
            CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(1).build();
            String[] nextRecord;
            while ((nextRecord = csvReader.readNext()) != null) {
                System.out.print(nextRecord[3] + " ");
                System.out.print(nextRecord[4] + " ");
                System.out.print(nextRecord[0] + " ");
                System.out.print(nextRecord[1] + " ");
                System.out.print(nextRecord[6] + " ");
                System.out.print(nextRecord[7] + " ");
                System.out.print(nextRecord[5] + " ");
                System.out.print(nextRecord[2] + " ");
                System.out.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
