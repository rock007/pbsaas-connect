package com.pbsaas.connect.framework.dao.sql.template;

import com.pbsaas.connect.framework.dao.sql.template.engines.FreeMarkerSqlTemplateEngine;
import com.pbsaas.connect.framework.dao.sql.template.engines.GroovySqlTemplateEngine;
import org.apache.commons.io.IOUtils;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cl on 2017/6/5.
 * SQL模板管理器
 */
public class SqlTemplateManager {

    private final static Logger logger = LoggerFactory.getLogger(SqlTemplateManager.class);

    /* 默认的模板类型 */
    private static String defaultTemplateType;

    /* SQL配置文件路径 */
    private static String sqlPath;

    /* 模板容器 */
    private static Map<String, SqlTemplate> templateMap = new HashMap<String, SqlTemplate>();

    /* SQL模板引擎容器 */
    private static Map<String, SqlTemplateEngine> engineMap = new HashMap<String, SqlTemplateEngine>();

    private SqlTemplateManager() {
    }

    /**
     * 初始化
     */
    public static void init(String path, String templateType) {
        defaultTemplateType = templateType;
        sqlPath = path;

        load();
    }

    /**
     * 加载sql配置文件
     * 一般为classpath:xxx/xx/*.xml这样格式的路径；多模块多classpath时，格式如：classpath*:xxx/xx/*.xml；
     */
    public static void load() {
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        SAXReader saxReader = new SAXReader();
        // 待关闭的流列表
        List<InputStream> streamList = new ArrayList<InputStream>();

        try {
            Resource[] resources = resolver.getResources(sqlPath);
            for (Resource r : resources) {
                InputStream in = r.getInputStream();
                streamList.add(in);
                Document doc = saxReader.read(in);
                loadConfig(doc);
            }
        } catch (Exception e) {
            logger.error("加载" + sqlPath + "下的配置文件失败\n" + e.getLocalizedMessage(), e);
        } finally {
            // 关闭所有的流
            for (InputStream in : streamList) {
                IOUtils.closeQuietly(in);
            }
        }
    }

    /**
     * 重新加载sql配置文件
     */
    public static void reload() {
        for (SqlTemplateEngine engine : engineMap.values()) {
            engine.clearCache();
        }

        load();
    }

    /**
     * 根据sqlId获取sql模板处理结果
     */
    public static SqlResult getSqlResult(String sqlId, Map<String, Object> params) {
        SqlTemplate template = templateMap.get(sqlId);
        if (null == template) {
            throw new RuntimeException("找不到sqlId：" + sqlId + "对应的sql配置");
        }

        // 调用对应的SQL模板引擎执行，创建SQL
        String type = template.getType();
        SqlTemplateEngine engine = engineMap.get(type);
        return engine.make(template, params);
    }

    /**
     * 加载sql模板配置，xml配置文件格式如下：
     * <sqls>
     * <sql id="TestTab.query" type="groovy">
     * <![CDATA[
     * select * from test_tab
     * ]]>
     * </sql>
     * </sqls>
     */
    private static void loadConfig(Document doc) {
        List<?> list = doc.getRootElement().elements();
        for (Object o : list) {
            Element e = (Element) o;
            String id = e.attributeValue("id");
            String type = e.attributeValue("type");
            String tpl = e.getText();

            SqlTemplate template = new SqlTemplate();
            template.setId(id);
            // 没有指定模板类型时使用默认类型
            template.setType(null != type ? type : defaultTemplateType);
            template.setTpl(tpl);

            templateMap.put(id, template);
        }
    }

    static {
        // 注册SQL模板引擎
        engineMap.put("groovy", new GroovySqlTemplateEngine());
        engineMap.put("freemarker", new FreeMarkerSqlTemplateEngine());
    }

}
