package demos;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.yaml.snakeyaml.Yaml;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class YamlDemo {

    private final static String RESOURCE_PATH = "E:\\code\\TrackerSpider\\src\\main\\resources\\";

    @Test
    public void dump() {
        Map<String, List<String>> map = new HashMap<>();
        List<String> list = new ArrayList<>();
        list.add("https://raw.githubusercontent.com/ngosang/trackerslist/master/trackers_all.txt");
        map.put("a", list);
        Yaml yaml = new Yaml();
        String dump = yaml.dump(map);
        try (FileWriter fw = new FileWriter(RESOURCE_PATH + "target.yml")) {
            fw.write(dump);
            fw.flush();
        } catch (Exception e) {
            log.error("", e);
        }
    }

    @Test
    public void load() {
        Yaml yaml = new Yaml();
        String path = this.getClass().getResource("/target.yml").getPath();
        try (FileReader fr = new FileReader(path);) {
            Map<String, List<String>> map = yaml.load(fr);
            System.out.println();
        } catch (Exception e) {
            log.error("", e);
        }
    }
}
