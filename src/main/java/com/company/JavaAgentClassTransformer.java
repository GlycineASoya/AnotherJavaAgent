package com.company;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

public class JavaAgentClassTransformer implements ClassFileTransformer {

    private FileUtils fu = new FileUtils();

    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        // set initial params
        fu.setPathToFile("/Users/ilevinsk/Work/working_log");

        System.out.println("transformer LOADER is loaded for " + className);
        fu.appendToFile("transformer LOADER is loaded for " + className);

        // put the bytecode of a class into byte array to make a manipulations
        byte[] bytecode = classfileBuffer;

        if (className.contains("com/company")) {
            // initialize pool of classes
            ClassPool classPool = ClassPool.getDefault();
            System.out.println("Class name is " + className);
            fu.appendToFile("Class name is " + className);
            // use class pool to built a bytecode for the target class
            try {
                CtClass ctClass = classPool.makeClass(new ByteArrayInputStream(bytecode));
                // log the class which we manipulate with
                System.out.println("Class is loaded: " + className);
                fu.appendToFile("Class is loaded: " + className);

                // change the bytecode
                // create a method to print getClass().getName() with run time to file and to sout
                CtMethod[] methods = ctClass.getDeclaredMethods();
                for (CtMethod method : methods) {
                    method.addLocalVariable("startTime", CtClass.longType);
                    method.insertBefore("startTime = System.nanoTime();");
                    method.insertAfter("System.out.println("
                            + "\"Execution time (nano sec): \" +"
                            + "(System.nanoTime() - startTime));");

                    // log the method which we manipulate with
                    System.out.println("Method is loaded: " + method.getName());
                    fu.appendToFile("Method is loaded: " + method.getName());
                }

                // write it to
                bytecode = ctClass.toBytecode();

                // free memory
                ctClass.detach();
            } catch (IOException | CannotCompileException e) {
                e.printStackTrace();
            }
        }

        // return corrected bytecode back to JVM
        return bytecode;
    }
}
