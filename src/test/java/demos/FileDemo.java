package demos;

import com.google.common.collect.Sets;
import constant.Constant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashSet;
import java.util.Set;

@Slf4j
public class FileDemo {

    @Test
    public void file() throws Exception {
        Set<String> set = Sets.newHashSet();

        BufferedReader br = new BufferedReader(new FileReader(Constant.PATH + "result.txt"));
        String line;
        while ((line = br.readLine()) != null) {
            if (StringUtils.isNotBlank(line)) {
                set.add(line);
            }
        }
        br.close();
        BufferedWriter bw = new BufferedWriter(new FileWriter(Constant.PATH + "test.txt"));
        for (String s : set) {
            bw.write(s);
            bw.newLine();
        }
        bw.close();
    }
}
