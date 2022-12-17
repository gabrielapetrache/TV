import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import input.Input;
import platform.Platform;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class Main {

    private Main(){
    }

    /**
     * Calls the checker
     * @param args from command line
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void main(String[] args) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Input input = objectMapper.readValue(new File(args[0]), Input.class);

        //System.out.println(input.getUsers());

        ArrayNode output = objectMapper.createArrayNode();

        Platform platform = new Platform();
        platform.start(input, output);

        ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
        objectWriter.writeValue(new File(args[1]), output);
        objectWriter.writeValue(new File(args[0].replace("/in", "/out")), output);
    }

}
