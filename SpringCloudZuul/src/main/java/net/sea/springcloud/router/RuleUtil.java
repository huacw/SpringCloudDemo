package net.sea.springcloud.router;

import org.apache.log4j.Logger;

import javax.script.*;
import java.util.Map;

/**
 * Created by sea on 2019/5/9.
 */
public class RuleUtil {
    private static final Logger logger = Logger.getLogger(RuleUtil.class);

    private static Compilable compilable;

    private static Bindings bindings;

    static {
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("javascript");
        compilable = (Compilable) engine;
        bindings = engine.createBindings(); // Local级别的Binding
    }

    public static String eval(String str, Map<String, Object> params) {
        try {
            long start = System.currentTimeMillis();
            // 定义函数并调用
            String script = "function rule(obj){return " + str + "} rule(obj)";
            // 解析编译脚本函数
            CompiledScript jsFunction = compilable.compile(script);
            // 通过Bindings加入参数
            bindings.put("obj", params);
            // 调用缓存着的脚本函数对象，Bindings作为参数容器传入
            Object result = jsFunction.eval(bindings);
            logger.info("rule eval result:" + result);
            logger.info("rule time millis:" + (System.currentTimeMillis() - start) + "ms");
            return (String) result;
        } catch (Exception e) {
            logger.error("eval rule exception", e);
        }
        return null;
    }

}
