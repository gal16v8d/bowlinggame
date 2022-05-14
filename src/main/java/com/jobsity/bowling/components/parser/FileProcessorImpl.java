package com.jobsity.bowling.components.parser;

import com.jobsity.bowling.exception.BadBowlingInputException;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class FileProcessorImpl implements FileProcessor {

  @Override
  public List<String> getFileLines(String filePath) {
    try {
      File textFile = new File(filePath);
      return FileUtils.readLines(textFile, StandardCharsets.UTF_8.name());
    } catch (Exception e) {
      log.error("Couldn't read file on {}", filePath, e);
      throw new BadBowlingInputException("Couldn't read file on " + filePath);
    }
  }
}
