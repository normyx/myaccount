package org.mgoulene;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.Locale;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import org.mgoulene.service.dto.OperationDTO;

import io.jsonwebtoken.io.IOException;

public class Test {
    public static void main(String[] args) throws ParseException, FileNotFoundException {
        /*
         * Locale locale = new Locale("fr-FR");
         * 
         * DecimalFormat numberFormat = (DecimalFormat)NumberFormat.getInstance(locale);
         * //numberFormat.set numberFormat.applyLocalizedPattern("###.##");
         * System.out.println(": " + numberFormat.toPattern()); Number parse =
         * numberFormat.parse("246,32");
         * 
         * System.out.println(parse.floatValue());
         */
        /*String path = "/home/vagrant/Downloads/opérations.csv";
        try {

            // CSVParser parser = new
            // CSVParserBuilder().withSeparator('\t').withIgnoreLeadingWhiteSpace(true).build();
            // CSVReader csvReader = new CSVReaderBuilder(new
            // FileReader(path)).withSkipLines(1).withCSVParser(parser).withKeepCarriageReturn(true).build();
            CsvToBean<OperationDTO> csvToBean = new CsvToBeanBuilder(new FileReader(path)).withType(OperationDTO.class)
                    .withIgnoreLeadingWhiteSpace(true).withSeparator('\t').withSkipLines(1).withKeepCarriageReturn(true)
                    .build();
            // CSVReader csvReader = new CSVReader(new FileReader(path), '\t');
            Iterator<OperationDTO> csvIterator = csvToBean.iterator();

            while (csvIterator.hasNext()) {

                System.out.println(csvIterator.next());
            }
        } catch (IOException ioe) {
            System.out.println(ioe);
        }*/
        System.out.println(java.time.LocalDate.parse( "05/09/2018", DateTimeFormatter.ofPattern( "dd/MM/yyyy" ) ));

    }
}
