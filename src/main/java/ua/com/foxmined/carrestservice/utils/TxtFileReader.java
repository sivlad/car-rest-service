package ua.com.foxmined.carrestservice.utils;

import lombok.extern.log4j.Log4j2;
import ua.com.foxmined.carrestservice.exception.FileException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Log4j2
public class TxtFileReader {

    private final String filename;

    public TxtFileReader(String filename) {
        this.filename = filename;
    }

    public List<String> readFile() throws FileException {
        Properties property =  PropertyFactory.getInstance().getProperty();

        try (Stream<String> lineStream = Files.lines(Paths.get(property.getProperty(filename)))) {
            return lineStream.collect(Collectors.toList());
        } catch (IOException exception) {
            log.error("Error open file" + exception.getMessage());
            throw new FileException("Error with file");
        }
    }

}
