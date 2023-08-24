package com.adobe.aem.guides.wknd.core.servlets;


import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletPaths;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;


@Component(service = Servlet.class)
@SlingServletPaths(
        value = {"/bin/titles"}
)
public class Day18ServletExercisePathServlet extends SlingAllMethodsServlet {
    private static final Logger LOG = LoggerFactory.getLogger(Day18ServletExercisePathServlet.class);

    @Override
    protected void doGet(final SlingHttpServletRequest req, final SlingHttpServletResponse resp) throws ServletException, IOException {
        final ResourceResolver resourceResolver = req.getResourceResolver();
        Page page = resourceResolver.adaptTo(PageManager.class).getPage("/content/academy-johnson-wknd/us/en");
        JSONArray pagesArray = new JSONArray();
        try {
            Iterator<Page> childPages = page.listChildren();
            while (childPages.hasNext()) {
                Page childPage = childPages.next();
                JSONObject pageObject = new JSONObject();
                pageObject.put("title", childPage.getTitle());
                pageObject.put("value", childPage.getPath());
                pagesArray.put(pageObject);
            }
        } catch (JSONException e) {
            LOG.info("\n ERROR {} ", e.getMessage());
        }

        resp.setContentType("application/json");
        resp.getWriter().write(pagesArray.toString());
    }

}