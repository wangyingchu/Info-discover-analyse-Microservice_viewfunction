package com.infoDiscover.infoAnalyse.service.util;

import com.viewfunction.infoDiscoverAnalyseService.util.SpringRuntimeEnvironmentHandler;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by wangychu on 2/21/17.
 */
public class InfoAnalyseServicePropertyHandler {
    private static Properties _properties;
    public static String META_CONFIG_DISCOVERSPACE="META_CONFIG_DISCOVERSPACE";

    public static String getPropertyValue(String resourceFileName){
        _properties=new Properties();
        try {
            _properties.load(new FileInputStream(SpringRuntimeEnvironmentHandler.getApplicationRootPath()+"/config/InfoAnalyseServiceCfg.properties"));
        } catch (FileNotFoundException e) {

        } catch (IOException e) {
        }
        return _properties.getProperty(resourceFileName);
    }
}
