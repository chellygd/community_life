package com.wkrj.core.component.log;

import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.json.JSONUtil;
import com.wkrj.core.systemnew.user.bean.WkrjUserNew;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

/**
 * 日志操作切面
 *
 * @author ziro
 * @date 2019/8/7 11:30
 */
@Aspect
@Component
public class WkrjOperateLogAop {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final static Logger logger = LoggerFactory.getLogger(WkrjOperateLogAop.class);

    /**
     * 切点
     */
    @Pointcut("@annotation(com.wkrj.core.component.log.WkrjLogInfo)")
    public void logInfoPointCut() {

    }

    /**
     * 切入操作
     *
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("logInfoPointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            // 新建异步线程，执行日志操作
            ThreadUtil.execAsync(new Runnable() {
                @Override
                public void run() {
                    try {
                        // 根据WkrjLog注解切入，记录操作日志
                        logToDatabase(joinPoint, request);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } finally {
            return joinPoint.proceed();
        }
    }

    public void logToDatabase(ProceedingJoinPoint point, HttpServletRequest request) throws Exception {
        // 日志实体对象
        WkrjOperateLog log = new WkrjOperateLog();
        //获取操作的模块和方法信息
        WkrjLogInfo wlog = getModules(point);
        if (null != wlog) {
            try {
                log.setLogmethod(wlog.logmethod());
                log.setLogmsg(wlog.logmsg());
                // 从session获取用户名
                WkrjUserNew user = (WkrjUserNew) request.getSession().getAttribute("usernew");
                if (null != user) {
                    log.setUserId(user.getUserId());
                    log.setUserName(user.getUserName());
                }
                // 获取请求路径
                String actionUrl = request.getRequestURI();
                log.setActionurl(actionUrl);
                // 获取访问真实IP
                log.setIp(getIpAddress(request));
                // 获取接受客户端数据的方法
                log.setContent(JSONUtil.toJsonStr(getMethodMap(request)));
                // commite=1为执行成功
                log.setCommite((byte) 1);
                // 添加到数据库
                addToLogData(log);
            } catch (Throwable e) {
                // MapperUtil.toJsonStr为自定义的转换工具类
                log.setContent(JSONUtil.toJsonStr(getMethodMap(request)));
                // commite=1为执行失败
                log.setCommite((byte) 2);
                // 添加到数据库
                addToLogData(log);
            }
        }
    }

    /**
     * 把日志插入到数据库中
     *
     * @param log
     */
    private void addToLogData(WkrjOperateLog log) {
        jdbcTemplate.update("insert into z_wkrj_operate_log " +
                        "(id,user_id,user_name,logmethod,logmsg,content,actionurl,ip,addtime,commite) " +
                        "values (?,?,?,?,?,?,?,?,now(),?)",
                new Object[]{log.getId(), log.getUserId(), log.getUserName(), log.getLogmethod(), log.getLogmsg(), log.getContent(), log.getActionurl(), log.getIp(), log.getCommite()});
    }

    /**
     * 获取方法路径和说明信息
     *
     * @param joinPoint
     * @return
     * @throws Exception
     */
    private WkrjLogInfo getModules(ProceedingJoinPoint joinPoint) throws Exception {
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
        Class targetClass = Class.forName(targetName);
        Method[] method = targetClass.getMethods();
        for (Method m : method) {
            if (m.getName().equals(methodName)) {
                Class[] tmpCs = m.getParameterTypes();
                if (tmpCs.length == arguments.length) {
                    WkrjLogInfo methodCache = m.getAnnotation(WkrjLogInfo.class);
                    return methodCache;
                }
            }
        }
        return null;
    }

    /**
     * 获取访问真实IP
     *
     * @return
     */
    private String getIpAddress(HttpServletRequest request) {
        String ipAddress = request.getHeader("x-forwarded-for");
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            if (ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")) {
                // 根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                ipAddress = inet.getHostAddress();
            }
        }
        // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if (ipAddress != null && ipAddress.length() > 15) { //"***.***.***.***".length() = 15
            if (ipAddress.indexOf(",") > 0) {
                ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
            }
        }
        return ipAddress;
    }

    /**
     * 获取接受客户端数据的方法
     *
     * @param request
     * @return
     * @throws UnsupportedEncodingException
     */
    private Map<String, String[]> getMethodMap(HttpServletRequest request) throws UnsupportedEncodingException {
        // 接受客户端的数据
        Map<String, String[]> map = request.getParameterMap();
        // 解决获取参数乱码
        Map<String, String[]> newmap = new HashMap<String, String[]>();
        for (Map.Entry<String, String[]> entry : map.entrySet()) {
            String name = entry.getKey();
            String values[] = entry.getValue();
            if (values == null) {
                newmap.put(name, new String[]{});
                continue;
            }
            String newvalues[] = new String[values.length];
            for (int i = 0; i < values.length; i++) {
                String value = values[i];
                value = new String(value.getBytes("iso8859-1"), request.getCharacterEncoding());
                newvalues[i] = value; //解决乱码后封装到Map中
            }
            newmap.put(name, newvalues);
        }
        return newmap;
    }

}
