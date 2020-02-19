package system.model.filter;

import org.apache.log4j.Logger;
import system.model.managers.ConfigurationManager;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter("/*")
public class EncodingFilter implements Filter {
    final static Logger logger = Logger.getLogger(ConfigurationManager.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        System.out.println("Фильтр отработал...");
        chain.doFilter(req,resp);
    }

    @Override
    public void destroy() {

    }
}
