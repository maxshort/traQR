package com.cerner.intern.traqr.util;

import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;

import java.io.IOException;
import java.io.Reader;

/**
 * Created on 7/17/15.
 */
public class CustomConfigs {

    private static Configuration tempConfig;
    static {
        tempConfig = new Configuration(Configuration.VERSION_2_3_22);
        tempConfig.setClassForTemplateLoading(CustomConfigs.class, "/");
        tempConfig.setDefaultEncoding("UTF-8");
        tempConfig.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        //inspired by http://techdiary.peterbecker.de/2009/02/defending-against-xss-attacks-in.html
        TemplateLoader loader = new ClassTemplateLoader(CustomConfigs.class, "/"){
            @Override
            public Reader getReader(Object templateSource, String encoding) throws IOException {
                return new WrappingReader(super.getReader(templateSource, encoding), "<#escape x as x?html>", "</#escape>");
            }
        };
        tempConfig.setTemplateLoader(loader);
    }

    public static final Configuration XSS_SAFE_CONFIG = tempConfig;

}
