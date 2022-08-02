package api.user;

import com.academy.javabootcamp.exception.ResourceNotFound;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.nio.file.Files;

public class JsonResourcesReader {

    public String readFile(String fileName) {
        try {
            File file = ResourceUtils.getFile(fileName);
            String user = new String(Files.readAllBytes(file.toPath()));
            return user;
        } catch (Exception e) {
            throw new ResourceNotFound("This file does not exist");
        }
    }
}
