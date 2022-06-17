import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import constant.Constant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.yaml.snakeyaml.Yaml;
import processor.AcgTrackerProcessor;
import processor.TextTrackersProcessor;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.proxy.Proxy;
import us.codecraft.webmagic.proxy.SimpleProxyProvider;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
public class Demo {

    public static void main(String[] args) {
        InputStream is = Demo.class.getClass().getResourceAsStream("/target.yml");
        Yaml yaml = new Yaml();
        Map<String, List<String>> map = yaml.load(is);

        HttpClientDownloader httpClientDownloader = new HttpClientDownloader();
        //代理
//        httpClientDownloader.setProxyProvider(SimpleProxyProvider.from(new Proxy("127.0.0.1", 1081)));

        Spider trackersListSpider = Spider.create(new TextTrackersProcessor());
        List<String> list = Lists.newArrayList();
        list.addAll(map.get("trackerslist"));
        list.addAll(map.get("animeTrackerList"));
        list.addAll(map.get("ngosangTrackerslist"));
        list.addAll(map.get("newtrackon"));
        for (String url : list) {
            trackersListSpider.addUrl(url);
        }
        trackersListSpider.setDownloader(httpClientDownloader).thread(4).run();

        trackersListSpider = Spider.create(new AcgTrackerProcessor());
        list = Lists.newArrayList();
        list.addAll(map.get("acgtracker"));
        for (String url : list) {
            trackersListSpider.addUrl(url);
        }
        trackersListSpider.setDownloader(httpClientDownloader).thread(1).run();

        distinctFile();
    }

    private static void distinctFile() {
        Set<String> set = Sets.newHashSet();
        try (BufferedReader br = new BufferedReader(new FileReader(Constant.PATH + "result.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (StringUtils.isNotBlank(line)) {
                    set.add(line);
                }
            }
        } catch (Exception e) {
            log.error("文件读取错误,结束", e);
            return;
        }
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(Constant.PATH + "distinctResult.txt"))) {
            for (String s : set) {
                bw.write(s);
                bw.newLine();
            }
        } catch (Exception e) {
            log.error("保存去重结果错误", e);
        }
    }
}
