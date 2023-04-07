package com.wyn.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Yaml读取工具类
 * @author Yining Wang
 * @date 2023年4月7日10:41:11
 * @since <pre>2023/04/07</pre>
 */
@Slf4j
public class YamlReader {

    public static Map<String, Map<String, Object>> properties;

    private Yaml yaml;

    public void loadProperties(String path) {
        File file = new File(path);
        this.loadProperties(file);
    }

    public void loadProperties(File file) {
        yaml = new Yaml();
        try (InputStream inputStream = new FileInputStream(file)) {
            properties = new HashMap<>();
            properties = yaml.loadAs(inputStream, HashMap.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Object get(String key) {
        if (yaml == null) {
            throw new RuntimeException("Please load yaml by using function: loadProperties(String path) first!");
        }
        String separator = ".";
        String[] separatorKeys = null;
        if (key.contains(separator)) {
            separatorKeys = key.split("\\.");
        } else {
            return properties.get(key);
        }
        Map<String, Map<String, Object>> finalValue = new HashMap<>();
        for (int i = 0; i < separatorKeys.length - 1; i++) {
            if (i == 0) {
                finalValue = (Map) properties.get(separatorKeys[i]);
                continue;
            }
            if (finalValue == null) {
                break;
            }
            finalValue = (Map) finalValue.get(separatorKeys[i]);
        }
        return finalValue == null ? null : finalValue.get(separatorKeys[separatorKeys.length - 1]);
    }

}
