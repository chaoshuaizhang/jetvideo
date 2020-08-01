package com.example.libnavcomplier;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.libnavannotation.ActivityDestination;
import com.example.libnavannotation.FragmentDestination;
import com.google.auto.service.AutoService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.annotation.Annotation;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.FileObject;
import javax.tools.StandardLocation;

@AutoService(Processor.class)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedAnnotationTypes({"com.example.libnavannotation.ActivityDestination",
        "com.example.libnavannotation.FragmentDestination"})
public class NavigationProcessor extends AbstractProcessor {
    private Messager messager;
    private Filer filer;
    private static final String OUTPUT_FILE_NAME = "routers.json";
    Map<String, JSONObject> map = new HashMap<>();

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        //日志打印,在java环境下不能使用android.util.log.e()
        messager = processingEnv.getMessager();
        //文件处理工具
        filer = processingEnv.getFiler();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Set<? extends Element> activityElements = roundEnv.getElementsAnnotatedWith(ActivityDestination.class);
        Set<? extends Element> fragElements = roundEnv.getElementsAnnotatedWith(FragmentDestination.class);
        printInfo("frag大小 " + fragElements.size());
        handleDestination(activityElements, ActivityDestination.class);
        handleDestination(fragElements, FragmentDestination.class);
        // 生成json文件
        createRouterFile(JSON.toJSONString(map));
        return false;
    }

    private void handleDestination(Set<? extends Element> elements, Class<? extends Annotation> clazz) {
        Optional.ofNullable(elements).orElse(new HashSet<>()).forEach(e -> {
            TypeElement tmp = (TypeElement) e;
            Annotation annotation = tmp.getAnnotation(clazz);
            JSONObject o = new JSONObject();
            if (annotation instanceof ActivityDestination) {
                ActivityDestination activityDestination = (ActivityDestination) annotation;
                // 页面路由
                String routerUrl = activityDestination.routerUrl();
                if (!map.containsKey(routerUrl)) {
                    o.put("id", Math.abs(routerUrl.hashCode()));
                    o.put("routerUrl", routerUrl);
                    o.put("className", tmp.getQualifiedName().toString());
                    o.put("asStarter", activityDestination.asStarter());
                    o.put("needLogin", activityDestination.needLogin());
                    o.put("isActivity", true);
                    map.put(routerUrl, o);
                }
            } else if (annotation instanceof FragmentDestination) {
                FragmentDestination fragmentDestination = (FragmentDestination) annotation;
                // 页面路由
                String routerUrl = fragmentDestination.routerUrl();
                if (!map.containsKey(routerUrl)) {
                    o.put("id", Math.abs(routerUrl.hashCode()));
                    o.put("routerUrl", routerUrl);
                    o.put("className", tmp.getQualifiedName().toString());
                    o.put("asStarter", fragmentDestination.asStarter());
                    o.put("needLogin", fragmentDestination.needLogin());
                    o.put("isActivity", false);
                    map.put(routerUrl, o);
                }
            }
        });
    }

    private void createRouterFile(String s) {
        FileObject resource;
        FileOutputStream fos = null;
        OutputStreamWriter osw = null;
        try {
            resource = filer.createResource(StandardLocation.CLASS_OUTPUT, "", OUTPUT_FILE_NAME);
            String path = resource.toUri().getPath();
            printInfo("resourcePath:" + path);
            String appPath = path.substring(0, path.indexOf("app") + 4);
            String assertPath = appPath + "src/main/assets/";
            printInfo("resource = " + path);
            printInfo("appPath = " + appPath);
            printInfo("assertPath = " + assertPath);
            File assetsFile = new File(assertPath);
            if (!assetsFile.exists() && !assetsFile.mkdirs()) {
                printError("创建 assets 文件夹失败");
            } else {
                File routerFile = new File(assetsFile, OUTPUT_FILE_NAME);
                fos = new FileOutputStream(routerFile);
                osw = new OutputStreamWriter(fos, "UTF-8");
                osw.write(s);
                osw.flush();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (osw != null) {
                    osw.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void printInfo(String s) {
        messager.printMessage(Diagnostic.Kind.NOTE, s);
    }

    private void printError(String s) {
        messager.printMessage(Diagnostic.Kind.ERROR, s);
    }

}