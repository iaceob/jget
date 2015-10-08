package org.beetl.ext.fn;

import com.jfinal.kit.StrKit;
import name.iaceob.jget.web.kit.Tool;
import org.beetl.core.Context;
import org.beetl.core.Function;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by cot on 2015/6/3.
 */
public class PaginationFunction implements Function {



    private String parsePage(Integer totalRow, Integer totalPage, Integer pageNumber,
                             Integer pageSize, String url, String getParam, String pageParam){
        StringBuilder ph = new StringBuilder();
        ph.append("<div class=\"pagination\">");
        if (pageNumber>1) {
            ph.append("<a class=\"item\" href=\"").append(joinPageUrl(1, url, getParam, pageParam)).append("\">首页</a>");
            ph.append("<a class=\"item previous\" data-page=\"").append(pageNumber-1).append("\" href=\"")
                    .append(joinPageUrl(pageNumber - 1, url, getParam, pageParam)).append("\">上一页</a>");
        } else {
            ph.append("<a class=\"item\">首页</a>");
            ph.append("<a class=\"item\">上一页</a>");
        }
        Integer prevp = pageNumber-4;
        for(Integer i=0; i<4; i++) {
            Integer prevpj = prevp++;
            if (prevpj>0)
                ph.append("<a class=\"item\" data-page=\"").append(prevpj).append("\" href=\"")
                        .append(joinPageUrl(prevpj, url, getParam, pageParam)).append("\">").append(prevpj).append("</a>");
        }
        ph.append("<a class=\"item current\" data-page=\"").append(pageNumber).append("\">").append(pageNumber).append("</a>");
        Integer nexp = pageNumber;
        for(Integer i=0; i<5; i++) {
            Integer nexpj = ++nexp;
            if (nexpj<pageNumber+5&&nexpj<=totalPage)
                ph.append("<a class=\"item\" data-page=\"").append(nexpj).append("\" href=\"")
                .append(joinPageUrl(nexpj, url, getParam, pageParam)).append("\">").append(nexpj).append("</a>");
        }
        if (pageNumber<totalPage) {
            ph.append("<a class=\"item next\" data-page=\"").append(pageNumber + 1).append("\" href=\"")
                    .append(joinPageUrl(pageNumber + 1, url, getParam, pageParam)).append("\">下一页</a>");
            ph.append("<a class=\"item\" data-page=\"").append(totalPage).append("\" href=\"")
                    .append(joinPageUrl(totalPage, url, getParam, pageParam)).append("\">尾页</a>");
        } else {
            ph.append("<a class=\"item next\">下一页</a>");
            ph.append("<a class=\"item\">尾页</a>");
        }
        ph.append("<span class=\"item\">").append(pageNumber).append("/").append(totalPage).append("</span>");
        // ph.append("<input class=\"item page-jump\" type=\"text\" style=\"padding: 0px; height: 25px; width: 37px;\" autocomplete=\"off\" placeholder=\"跳轉\" data-page=\"").append(pageNumber).append("\" data-total=\"").append(totalPage).append("\" data-url=\"").append(joinPageUrl(999999999, url, getParam, pageParam)).append("\" onkeyup=\"!(function(window,jump,e,undefined){'use strict';if(e.keyCode!=13)return;var url=jump.getAttribute('data-url');var totalPage=jump.getAttribute('data-total');var jumpPage=window.parseInt(jump.value)||1;if(!jumpPage)return false;if(jumpPage<=0)jumpPage=1;if(jumpPage>totalPage)jumpPage=totalPage;window.location.href=url.replace(/9{9}/,jumpPage);})(window,this,event);\"/>");
        ph.append("<input class=\"item page-jump\" type=\"text\" style=\"padding: 0px; height: 25px; width: 37px;\" autocomplete=\"off\" placeholder=\"跳轉\" data-page=\"").append(pageNumber).append("\" data-total=\"").append(totalPage).append("\" data-url=\"").append(joinPageUrl(999999999, url, getParam, pageParam)).append("\" onkeyup=\"!function(a,b,c){var d,e,f;if(13==c.keyCode){if(d=b.getAttribute('data-url'),e=b.getAttribute('data-total'),f=a.parseInt(b.value)||1,!f)return!1;0>=f&&(f=1),f>e&&(f=e),a.location.href=d.replace(/9{9}/,f)}}(window,this,event);\"/>");
        ph.append("</div>");
        return ph.toString();
    }


    private String joinPageUrl(Integer pageNumber, String url, String getParam, String pageParam) {
        StringBuilder jpu = new StringBuilder();
        if (StrKit.isBlank(getParam)) {
            jpu.append(url).append("?").append(pageParam).append("=").append(pageNumber);
            return jpu.toString();
        }
        String[] gpm = getParam.split("&");
        String[] gpma;
        jpu.append(url).append("?");
        for(Integer i=gpm.length; i-->0;) {
            gpma = gpm[i].split("=");
            if (!pageParam.equals(gpma[0])) jpu.append(gpm[i]).append("&");
        }
        jpu.append(pageParam).append("=").append(pageNumber);
        return jpu.toString();
    }




    @Override
    public Object call(Object[] objects, Context context) {
        if (objects==null||objects.length<4) return "";

        try {
            HttpServletRequest request = (HttpServletRequest) objects[0];
            Integer totalRow = Tool.strToInt(objects[1].toString());
            Integer totalPage = Tool.strToInt(objects[2].toString());
            Integer pageNumber = Tool.strToInt(objects[3].toString(), 1);
            Integer pageSize = objects.length>=5 ? Tool.strToInt(objects[4].toString(), 50) : 50;
            String pageParam = objects.length>=6 ? objects[5].toString() : "page";


            // String url = request.getRequestURL().toString();
            String url = request.getContextPath() + request.getRequestURI();
            String getParam = request.getQueryString();

            return parsePage(totalRow, totalPage, pageNumber, pageSize, url, getParam, pageParam);

        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

}
