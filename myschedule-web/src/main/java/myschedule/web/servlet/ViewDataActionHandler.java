package myschedule.web.servlet;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * A simple ActionHandler implementation that will hide the Http request and response objects, and let subclass
 * handle the {@link ViewData} instead. Any data added into the ViewData will be available in the View rendering.
 * 
 * <p>
 * This handler will check viewName for empty string, and if found, it will use the {@link #getDefaultViewName()} value
 * instead. It default to '/index'.
 * 
 * <p>
 * This class will auto create and populate a {@link ViewData} instance so subclass may further process it. By default,
 * it will copy all request's attributes and parameters into the data map! You may use the 
 * 'addRequestAttributesToViewData', 'addRequestParametersToViewData', or 'useRequestKeys' to control how these are 
 * transfered. Or subclass may just override the createViewData() to provide any customization.
 * 
 * <p>
 * For simple handler that just need a view name returned without data map, then creating a instance of this class with
 * the viewName parameter is sufficient.
 * 
 * @author Zemian Deng <saltnlight5@gmail.com>
 *
 */
public class ViewDataActionHandler implements ActionHandler {
	
	public static final String DEFAULT_VIEW_NAME = "/index";

	private static final String REQUEST_ATTRS = "attrs";

	private static final String REQUEST_PARAMS = "params";
	
	protected String defaultViewName = DEFAULT_VIEW_NAME;
	
	protected boolean addRequestAttributesToViewData = true;

	protected boolean addRequestParametersToViewData = true;
	
	protected boolean useRequestKeys = false;
	
	public ViewDataActionHandler() {
	}
	
	public ViewDataActionHandler(String defaultViewName) {
		this.defaultViewName = defaultViewName;
	}
	
	public String getDefaultViewName() {
		return defaultViewName;
	}
	
	public void setAddRequestAttributesToViewData(boolean addRequestAttributesToViewData) {
		this.addRequestAttributesToViewData = addRequestAttributesToViewData;
	}
	
	public void setAddRequestParametersToViewData(boolean addRequestParametersToViewData) {
		this.addRequestParametersToViewData = addRequestParametersToViewData;
	}

	@Override
	public ViewData handleAction(String actionPath, HttpServletRequest req, HttpServletResponse resp) throws Exception {
		String viewName = actionPath;
		if (viewName.equals("")) {
			viewName = getDefaultViewName();
		}
		ViewData viewData = createViewData(viewName, req);
		handleViewData(viewData);
		return viewData;
	}
	
	private ViewData createViewData(String viewName, HttpServletRequest req) {
		ViewData viewData = ViewData.view(viewName);
		if (addRequestAttributesToViewData) {
			Map<String, Object> reqAttrs = new HashMap<String, Object>();
			Enumeration<?> names = req.getAttributeNames();
			while (names.hasMoreElements()) {
				String key = (String)names.nextElement();
				reqAttrs.put(key, req.getAttribute(key));
			}
			if (useRequestKeys) {
				viewData.addData(REQUEST_ATTRS, reqAttrs);
			} else {
				viewData.addMap(reqAttrs);
			}
		}
		if (addRequestParametersToViewData) {
			Map<String, Object> reqParams = new HashMap<String, Object>();
			Map<?, ?> paramMap = req.getParameterMap();
			for (Map.Entry<?, ?> entry : paramMap.entrySet()){
				String key = (String)entry.getKey();
				reqParams.put(key, entry.getValue());
			}
			if (useRequestKeys) {
				viewData.addData(REQUEST_PARAMS, reqParams);
			} else {
				viewData.addMap(reqParams);
			}
		}
		return viewData;
	}

	/** Default to empty impl. Subclass can override and handle their view data processing. */
	protected void handleViewData(ViewData viewData) {
	}
}