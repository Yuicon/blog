package wang.penglei.apigateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Yuicon
 */
public class LoginFilter extends ZuulFilter {

    private final String authName = "token";

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext context = RequestContext.getCurrentContext();
        return !context.getRequest().getRequestURI().contains("public");
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();
        if (request.getHeader(authName) == null) {
            context.setSendZuulResponse(false);
            context.setResponseStatusCode(401);
        }
        return null;
    }
}
