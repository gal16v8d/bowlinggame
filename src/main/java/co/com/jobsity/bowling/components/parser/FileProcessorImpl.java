package co.com.jobsity.bowling.components.parser;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;

import co.com.jobsity.bowling.exception.BadBowlingInputException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class FileProcessorImpl implements FileProcessor {

    @Override
    public List<String> getFileLines(String filePath) {
        try {
            File textFile = new File(filePath);
            return FileUtils.readLines(textFile, StandardCharsets.UTF_8.name());
        } catch (Exception e) {
            log.error("Couldn't read file on {}", filePath);
            throw new BadBowlingInputException("Couldn't read file on " + filePath);
        }
    }

}
