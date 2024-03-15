package nl.c2c.ac.osgi;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class ParsedCommand {

    private Map<String, String> argumentsDParameters = new HashMap<>();

    private Map<String, String> arguments = new HashMap<>();

    private String input = "";

    public Map<String, String> getArgumentsDParameters() {
        return argumentsDParameters;
    }

    public void setArgumentsDParameters(final Map<String, String> argumentsDParameters) {
        this.argumentsDParameters = Collections.unmodifiableMap(argumentsDParameters);
    }

    public Map<String, String> getArguments() {
        return arguments;
    }

    public void setArguments(final Map<String, String> arguments) {
        this.arguments = Collections.unmodifiableMap(arguments);
    }

    public String getArgument1() {
        return arguments.get("arg1");
    }

    public String getArgument2() {
        return arguments.get("arg2");
    }

    public String getArgument3() {
        return arguments.get("arg3");
    }

    public String getArgument4() {
        return arguments.get("arg4");
    }

    public String getInput() {
        return input;
    }

    public void setInput(final String input) {
        this.input = input;
    }

    @Override
    public String toString() {
        return "ParsedCommand{" +
          "argumentsDParameters=" + argumentsDParameters.entrySet()
          .stream()
          .map(stringStringEntry -> stringStringEntry.getKey() + ":" + stringStringEntry.getValue())
          .collect(Collectors.joining(" ")) +
          ", arguments=" + arguments.entrySet()
          .stream()
          .map(stringStringEntry -> stringStringEntry.getKey() + ":" + stringStringEntry.getValue())
          .collect(Collectors.joining(" ")) +
          ", input='" + input + '\'' +
          '}';
    }
}
