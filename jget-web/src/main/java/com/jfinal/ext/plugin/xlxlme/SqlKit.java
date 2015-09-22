package com.jfinal.ext.plugin.xlxlme;

import com.jfinal.log.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.File;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * sql 工具类
 * @author iaceob (iaceob@gmail.com)
 *
 */
public class SqlKit {

    protected static final Logger log = Logger.getLogger(SqlKit.class);
    
    private static Map<String, String> sqlMap;


    
    /**
     * 遍历xml节点将sql写入到map中
     * @param allNode 所有sql节点
     */
     private static void setSqlMap(NodeList allNode, String containerMark, String sqlMark) {
         Element element;
         // 对符合条件的所有节点进行遍历
         for (int i=allNode.getLength(); (i--)>0;) {
             // 获得一个元素
             element = (Element) allNode.item(i);
             // 此元素有子节点，获取所有子节点
             NodeList nl = element.getChildNodes();
             // 遍历所有子节点
             for (int j=nl.getLength(); (j--)>0;) {
                 // 若子节点的名称不为#text，则输出，#text为反/标签
                 if (!nl.item(j).getNodeName().equals("#text")) {
                     sqlMap.put(
                                 element.getAttribute(containerMark) + "." + 
                                 nl.item(j).getAttributes().getNamedItem(sqlMark).getTextContent(), 
                                 nl.item(j).getTextContent()
                                 );
                 }
             }
         }
     }
     

     
     private static String getPlaceholder(Integer number) {
         String place = "";
         for (;(number--)>0;) {
             place += "?" + (number>0 ? "," : "");
         }
         return place;
     }
    
     /**
      * 初始化sql
      * @param folderPath sql xml 文件目录地址
      * @param fileSuffix 文件后缀
      * @param expression xml 合并节点名
      * @param container sql 容器
      * @param containerMark sql 容器标识
      * @param sqlMark sql 标识
      * @return
      */
    public static boolean init(String folderPath, String fileSuffix, String expression, String container, String containerMark, String sqlMark) {
        sqlMap = new HashMap<String, String>();
        File[] files = SqlXmlMerge.getFileList(folderPath, fileSuffix); 
        if (files.length == 0) {
            log.debug("在目录 " + folderPath + " 中未找到文件名末尾为" + fileSuffix + "的文件");
            return false;
        }
        Document doc = SqlXmlMerge.merge("/" + expression, files);
        NodeList nl = doc.getElementsByTagName(container);
        setSqlMap(nl, containerMark, sqlMark);

        return sqlMap != null||!sqlMap.isEmpty();
    }
    
    
    /**
     * 获取sql 
     * @param key sql 容器节点name . sql节点id
     * @return
     */
    public static String getSql(String key) {
        try {
            return sqlMap.get(key);   
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 获取 sql 格式化sql中预定义字符以 {0} {1} {2} 格式
     * @param key sql 容器节点name . sql节点id
     * @param arguments 格式化字符
     * @return
     */
    public static String getSql(String key, Object ... arguments) {
        try {
            return MessageFormat.format(sqlMap.get(key), arguments);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 这里默认将标识符表示为 #
     * @see com.jfinal.ext.plugin.xlxlme.SqlKit#getSqls(String, String)
     * @param key
     * @return
     */
    public static String[] getSqls(String key) {
        return getSqls("#", key);
    }
    
    /**
     * 为适应jfinal分页sql分离
     * mark 未分割字符串
     * 返回string数组
     * 通常情况下 sqls[0] 为 查询字段
     * sqls[1] 为表信息
     * @param mark
     * @param key
     * @return
     */
    public static String[] getSqls(String mark, String key) {
        return sqlMap.get(key).split(mark);
    }
    
    /**
     * 替换in条件
     * @param key
     * @param formatNumber
     * @return
     */
    public static String getSqlIn(String key, Integer formatNumber) {
        return getSqlIn(key, formatNumber, "##");
    }
    
    public static String getSqlIn(String key, Integer formatNumber, String mark) {
        try {
            String sql = getSql(key);
            sql = sql.replaceAll(mark, "in(" + getPlaceholder(formatNumber) + ")");
            return sql;
        } catch(Exception e) {
            e.printStackTrace();
            return "";
        }
    }
    
    public static String getSqlEquals(String key) {
        return getSqlEquals(key, "##");
    }
    
    public static String getSqlEquals(String key, String mark) {
        try {
            return getSql(key).replaceAll(mark, "=? ");
        } catch (Exception e) {
            return "";
        }
    }
    

    /**
     * 
     */
    public static void clear() {
        sqlMap.clear();
    }

    public static void main(String[] args) {
        // String sql = "select top ${top} id, name, create_time from st_user order by create_time desc";
        // System.out.println(formatTop(sql, 10));
    }

    
    
}
