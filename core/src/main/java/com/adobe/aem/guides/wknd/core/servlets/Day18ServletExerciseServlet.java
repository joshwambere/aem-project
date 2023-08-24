package com.adobe.aem.guides.wknd.core.servlets;

import com.adobe.granite.ui.components.ds.DataSource;
import com.adobe.granite.ui.components.ds.SimpleDataSource;
import com.adobe.granite.ui.components.ds.ValueMapResource;
import com.day.crx.JcrConstants;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceMetadata;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.api.wrappers.ValueMapDecorator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.servlet.Servlet;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Component(service = Servlet.class, property = { Constants.SERVICE_DESCRIPTION + "= Json Data in dynamic Dropdown",
        "sling.servlet.paths=" + "/bin/academy/johnson/dynamicFields", "sling.servlet.methods=" + HttpConstants.METHOD_GET })
public class Day18ServletExerciseServlet extends SlingSafeMethodsServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day18ServletExerciseServlet.class);
    final transient ThreadLocal<ResourceResolver> resourceResolver = new ThreadLocal<ResourceResolver>();
    transient Resource pathResource;
    transient ValueMap valueMap;
    transient List<Resource> resourceList;
    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) {
        resourceResolver.set(request.getResourceResolver());
        pathResource = request.getResource();
        resourceList = new ArrayList<>();
        try {
            /* Getting AEM Tags Path given on datasource Node */
            String jsonDataPath = Objects.requireNonNull(pathResource.getChild("datasource")).getValueMap()
                    .get("dynamic", String.class);
            assert jsonDataPath != null;
            // Getting Tag Resource using JsonData
            Resource jsonResource = request.getResourceResolver()
                    .getResource(jsonDataPath + "/" + JcrConstants.JCR_CONTENT);
            assert jsonResource != null;
            // Getting Node from jsonRzesource
            Node jsonNode = jsonResource.adaptTo(Node.class);
            assert jsonNode != null;
            // Converting input stream to JSON Object
            InputStream inputStream = jsonNode.getProperty("jcr:data").getBinary().getStream();
            StringBuilder stringBuilder = new StringBuilder();
            String eachLine;
            assert inputStream != null;
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            while ((eachLine = bufferedReader.readLine()) != null) {
                stringBuilder.append(eachLine);
            }
            JSONArray dataArray = new JSONArray(stringBuilder.toString());
            for (int n = 0; n < dataArray.length(); n++) {
                JSONObject jsonObject = dataArray.getJSONObject(n);
                String text = jsonObject.getString("text");
                String value = jsonObject.getString("value");
                valueMap = new ValueMapDecorator(new HashMap<>());
                valueMap.put("text", text);
                valueMap.put("value", value);
                resourceList.add(
                        new ValueMapResource(resourceResolver.get(), new ResourceMetadata(), "nt:unstructured", valueMap));
            }
            /* Create a DataSource that is used to populate the drop-down control */
            DataSource dataSource = new SimpleDataSource(
                    resourceList.iterator());
            request.setAttribute(DataSource.class.getName(), dataSource);
        } catch (JSONException | IOException | RepositoryException e) {
            LOGGER.error("Error in Json Data Exporting : {}", e.getMessage());
        }
    }
}