package wang.penglei.apigateway.filter;

import com.google.gson.Gson;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import io.jsonwebtoken.Claims;
import model.Account;
import utils.JwtUtils;

import javax.servlet.http.HttpServletRequest;
import java.net.URLEncoder;

/**
 * @author Yuicon
 */
public class LoginFilter extends ZuulFilter {

    private final String authName = "token";
    private final Gson gson = new Gson();

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
        String token = request.getHeader(authName);
        if (token == null) {
            context.setSendZuulResponse(false);
            context.setResponseStatusCode(401);
        }
        try {
            Claims claims = JwtUtils.parseToken(token).getBody();
            Account user = new Account();
            user.setId(claims.get("id", Integer.class));
            user.setEmail(claims.get("email", String.class));
            user.setUsername(claims.getSubject());
            context.addZuulRequestHeader("user", URLEncoder.encode(gson.toJson(user),"UTF-8"));
        } catch (Exception e) {
            context.setSendZuulResponse(false);
            context.setResponseStatusCode(401);
        }
        return null;
    }
}
