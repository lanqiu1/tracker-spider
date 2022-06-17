package processor;

import constant.Constant;
import lombok.extern.slf4j.Slf4j;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.io.BufferedWriter;
import java.io.FileWriter;

@Slf4j
public class TextTrackersProcessor implements PageProcessor {

    private Site site = Site.me().setRetryTimes(3).setSleepTime(100);

    @Override
    public void process(Page page) {
        String rawText = page.getRawText();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(Constant.PATH + "result.txt", true))) {
            bw.write(rawText);
            bw.newLine();
        } catch (Exception e) {
            log.error(page.getUrl().toString() + "解析失败：", e);
        }
    }

    @Override
    public Site getSite() {
        return site;
    }
}
