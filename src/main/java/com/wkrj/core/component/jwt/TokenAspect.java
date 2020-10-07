package com.wkrj.core.component.jwt;

import com.wkrj.core.component.log.WkrjOperateLogAop;
import com.wkrj.core.systemnew.user.bean.WkrjUserNew;
import com.wkrj.core.utils.LayuiJson;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;


/**
 * @author ziro
 */
@Aspect
@Component
public class TokenAspect {
    /*@Autowired
    private RedisUtils redisUtils;*/
    @Autowired
    private WkrjOperateLogAop wkrjOperateLogAop;

    /**
     * 设置切入点
     */
    /*@Pointcut("execution(* com.wkrj..controller.*.*(..)) " +
            "&& !execution(* com.wkrj.core.systemnew.wkrjloginnew..controller.*.*(..)) " +
            "&& !execution(* com.wkrj.module.demo..controller.*.*(..)) ")*/
    public void TokenAspect() {

    }

    /**
     * 执行用户是否登录
     *
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    /*@Around("TokenAspect()")*/
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        LayuiJson j = new LayuiJson();
        try {
            // 从切点上获取目标方法
            MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
            Method method = methodSignature.getMethod();

            // 若目标方法忽略token检查，则直接调用目标方法
            IgnoreToken ignoreTokenAnnotation = method.getAnnotation(IgnoreToken.class);
            if (ignoreTokenAnnotation != null) {
                return joinPoint.proceed();
            }
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String accessToken = request.getParameter("access_token");
            //System.out.println("##"+accessToken);
            if (accessToken == null || "".equals(accessToken)) {
                j.setCode(1001);
                j.setSuccess(false);
                j.setMsg("请登录，未发现访问系统所需的Token");
                return j;
            }
            //JwtToken.verifyToken(accessToken);
	        /*if(accessToken == null || "".equals(accessToken)){
	        	j.setCode(1001);
	        	j.setSuccess(false);
	        	j.setMsg("请登录，未发现访问系统所需的Token");
	        	return j;
	        }*/
            Token token = JwtToken.parseToken(accessToken);
            if (token.getUserId() == null || token.getUserId().length() <= 0) {
                j.setCode(1001);
                j.setSuccess(false);
                j.setMsg("请登录,系统获取不到当前用户的ID");
                return j;
            }

            //String perms = request.getSession().getAttribute("userPermission")+"";
            WkrjUserNew user = (WkrjUserNew) request.getSession().getAttribute("usernew");
            if (user != null) {
                if ((token.getUserId()).equals(user.getUserId())) {
                    j.setCode(0);
                    j.setSuccess(true);
                    j.setMsg("Token验证成功");
                    return joinPoint.proceed();//如果token验证成功则执行相应的方法
                } else {
                    j.setCode(1001);
                    j.setSuccess(true);
                    j.setMsg("Token验证失败");
                    return j;//joinPoint.proceed();//如果token验证成功则执行相应的方法
                }

            } else {
                j.setCode(1001);
                j.setSuccess(false);
                j.setMsg("请登录,用户登录信息过期！");
                return j;
            }
	        /*if(jedisUtil.existsKey(token.getUserId())){
	        	String tokenStr = jedisUtil.getString(token.getUserId());
		        if(tokenStr != null && accessToken.equals(tokenStr)){
		        	j.setCode(0);
		        	j.setSuccess(true);
		        	j.setMsg("Token验证成功");
		        	//更新redis过期时间
		        	jedisUtil.expire(token.getUserId());
		        	return joinPoint.proceed();//如果token验证成功则执行相应的方法
		        }else{
		        	j.setCode(1001);
		        	j.setSuccess(false);
		        	j.setMsg("请登录,用户信息不正确！");
		        	return j;
		        }
	        }else{
	        	j.setCode(1001);
	        	j.setSuccess(false);
	        	j.setMsg("请登录,未查到有效Token！");
	        	return j;
	        }*/
        } catch (Exception e) {
            e.printStackTrace();
            j.setCode(1001);
            j.setSuccess(false);
            j.setMsg(e.getMessage());
            return j;//如果token验证失败返回相应的json数据
        } finally {

        }
        //return j;
    }
}
