import org.jruby.embed.LocalVariableBehavior;
import org.jruby.embed.ScriptingContainer;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class RubyRunner {
    private Process process;
    private BufferedReader processOutput;

    private final static String RUBY_PATH = "C:\\Ruby24-x64\\bin\\ruby.exe";
    private final static String RUBY_SCRIPT_PATH = "hello_world.rb";

    private String outputText = "";
    private ScriptingContainer rubyContainer;

    public String runRuby() {
        Object result;
        try {
//            String line;
//            process = Runtime.getRuntime().exec(RUBY_PATH + " " + RUBY_SCRIPT_PATH);
//            process.waitFor();

           rubyContainer = new ScriptingContainer(LocalVariableBehavior.PERSISTENT);
           rubyContainer.put("main", this);
           result = rubyContainer.runScriptlet("main.hello_world");
           return result.toString();


//           processOutput = new BufferedReader(new InputStreamReader(process.getInputStream()));


//           while ((line = processOutput.readLine()) != null) {
//               outputText += line;
//           }
        } catch (Exception e) {
           e.printStackTrace();
        }
        return "";
    }

   public String getHelloWorld() {
        return "Hello, world from Ruby";
    }

}
