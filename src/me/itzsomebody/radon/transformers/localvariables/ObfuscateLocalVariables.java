package me.itzsomebody.radon.transformers.localvariables;

import me.itzsomebody.radon.transformers.AbstractTransformer;
import me.itzsomebody.radon.utils.LoggerUtils;
import me.itzsomebody.radon.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Transformer that applies a local variable obfuscation by changing the names.
 *
 * @author ItzSomebody
 */
public class ObfuscateLocalVariables extends AbstractTransformer {
    /**
     * Applies obfuscation.
     */
    public void obfuscate() {
        logStrings.add(LoggerUtils.stdOut("------------------------------------------------"));
        logStrings.add(LoggerUtils.stdOut("Starting local variable obfuscation transformer"));
        AtomicInteger counter = new AtomicInteger();
        long current = System.currentTimeMillis();
        classNodes().stream().filter(classNode -> !classExempted(classNode.name)).forEach(classNode -> {
            classNode.methods.stream().filter(methodNode -> !methodExempted(classNode.name + '.' + methodNode.name + methodNode.desc))
                    .filter(methodNode -> methodNode.localVariables != null).forEach(methodNode -> {
                methodNode.localVariables.forEach(localVariableNode -> {
                    localVariableNode.name = StringUtils.crazyString();
                    counter.incrementAndGet();
                });
            });
        });
        logStrings.add(LoggerUtils.stdOut("Obfuscated " + counter + " local variables."));
        logStrings.add(LoggerUtils.stdOut("Finished. [" + tookThisLong(current) + "ms]"));
    }
}
