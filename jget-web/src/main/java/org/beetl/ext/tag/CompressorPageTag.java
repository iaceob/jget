package org.beetl.ext.tag;

import name.iaceob.jget.web.kit.html.HtmlCompressor;
import org.beetl.core.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by cox on 2015/9/14.
 */
public class CompressorPageTag extends Tag {

    private static final Logger log = LoggerFactory.getLogger(CompressorPageTag.class);



    private String compressor(String html) {
        try {
            return HtmlCompressor.compress(html);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            StringBuilder sb = new StringBuilder(html.length());
            for (String s : html.split("\n"))
                sb.append(s.trim());
            return sb.toString();
        }
    }


    @Override
    public void render() {
        String content = this.getBodyContent().toString();
        try {
            this.bw.writeString(this.compressor(content));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}
