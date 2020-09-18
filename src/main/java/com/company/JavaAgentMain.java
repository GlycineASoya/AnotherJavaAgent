package com.company;

import java.lang.instrument.Instrumentation;

public class JavaAgentMain {
    private static FileUtils fu = new FileUtils();

    public static void premain(String agentArgs, Instrumentation inst) {
        fu.setPathToFile("/Users/ilevinsk/Work/working_log");
        System.out.println("premain is loaded");
        fu.appendToFile("premain is loaded");
        inst.addTransformer(new JavaAgentClassTransformer());
    }
}
