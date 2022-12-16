package pages;

import com.fasterxml.jackson.databind.node.ArrayNode;
import printer.OutputPrinter;

public interface PageActions {
    void execute(String page, String todo, OutputPrinter printer);
}
