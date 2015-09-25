package name.iaceob.jget.download.kit;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Record;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created by iaceob on 2015/9/13.
 */
public class JsonKit extends com.jfinal.kit.JsonKit {

    private static final Logger log = LoggerFactory.getLogger(JsonKit.class);

    private static List toList(String json) throws JSONException {
        if (StrKit.isBlank(json))
            throw new JSONException("No Json Array");
        JSONArray ja = JSON.parseArray(json);
        List list = new ArrayList();
        for (Integer i = 0; i<ja.size(); i++) {
            Object obj = ja.get(i);
            if (obj instanceof JSONArray) {
                list.add(JsonKit.toList(ja.getJSONArray(i).toJSONString()));
                continue;
            }
            if (obj instanceof JSONObject) {
                list.add(JsonKit.toRecord(ja.getJSONObject(i).toJSONString()));
                continue;
            }
            if (obj instanceof String) {
                list.add(ja.getString(i));
                continue;
            }
            if (obj instanceof Integer) {
                list.add(ja.getInteger(i));
                continue;
            }
            if (obj instanceof Double) {
                list.add(ja.getDouble(i));
                continue;
            }
            if (obj instanceof Long) {
                list.add(ja.getLong(i));
                continue;
            }
            if (obj instanceof Date) {
                list.add(ja.getDate(i));
                continue;
            }
            if (obj instanceof Float) {
                list.add(ja.getFloat(i));
                continue;
            }
            if (obj instanceof Boolean) {
                list.add(ja.getBoolean(i));
                continue;
            }
            list.add(ja.get(i));
        }

        return list;
    }

    private static Record toRecord(String json) throws JSONException{
        if (StrKit.isBlank(json))
            throw new JSONException("No Json Object");
        JSONObject jo = JSON.parseObject(json);
        Set<String> set = jo.keySet();
        Iterator it = set.iterator();
        Record record = new Record();
        while (it.hasNext()) {
            String key = it.next().toString();
            Object val = jo.get(key);
            if (val instanceof JSONObject) {
                record.set(key, JsonKit.toRecord(jo.getJSONObject(key).toJSONString()));
                continue;
            }
            if (val instanceof JSONArray) {
                record.set(key, JsonKit.toList(jo.getJSONArray(key).toJSONString()));
                continue;
            }
            if (val instanceof String) {
                record.set(key, jo.getString(key));
                continue;
            }
            if (val instanceof Integer) {
                record.set(key, jo.getInteger(key));
                continue;
            }
            if (val instanceof Double) {
                record.set(key, jo.getDouble(key));
                continue;
            }
            if (val instanceof Long) {
                record.set(key, jo.getLong(key));
                continue;
            }
            if (val instanceof Date) {
                record.set(key, jo.getDate(key));
                continue;
            }
            if (val instanceof Float) {
                record.set(key, jo.getFloat(key));
                continue;
            }
            if (val instanceof Boolean) {
                record.set(key, jo.getBoolean(key));
                continue;
            }
            record.set(key, jo.get(key));
        }
        return record;
    }



    public static <T extends Record> T fromJson(String json, Class<? extends Record> clazz) { // , Class<? extends Record> clazz
        try {
            if (StrKit.isBlank(json))
                throw new JSONException("Json Is Null");
            T target = (T) clazz.newInstance();
            Object obj = JSON.parse(json);
            if (obj instanceof JSONObject) {
                Record r = JsonKit.toRecord(json);
                String[] keys = r.getColumnNames();
                for (String key : keys)
                    target.set(key, r.get(key));
                return target;
            }
            throw new JSONException("Fail Json");
        } catch (InstantiationException e) {
            log.error(e.getMessage(), e);
        } catch (IllegalAccessException e) {
            log.error(e.getMessage(), e);
        } catch (JSONException e) {
            log.error(e.getMessage(), e);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /*
    public static <T extends List> T fromJson(String json, Class<? extends List> clazz) {
        try {
            if (StrKit.isBlank(json))
                throw new JSONException("Json Is Null");
            T target = (T) clazz.newInstance();
            Object obj = JSON.parse(json);
            if (obj instanceof JSONArray) {
                List list = JsonKit.toList(json);
                for (Integer i=0; i<list.size(); i++)
                    target.add(list.add(i));
                return target;
            }
            throw new JSONException("Fail Json");
        } catch (InstantiationException e) {
            log.error(e.getMessage(), e);
        } catch (IllegalAccessException e) {
            log.error(e.getMessage(), e);
        } catch (JSONException e) {
            log.error(e.getMessage(), e);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }
    */


}