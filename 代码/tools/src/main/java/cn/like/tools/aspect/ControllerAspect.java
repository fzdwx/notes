package cn.like.tools.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Map;

/**
 * aspect 工具类
 */
@Aspect
@Component
public class ControllerAspect {

    private static final Logger logger = LoggerFactory.getLogger(ControllerAspect.class);

    private static final String PLATFORM_NAME = "卡券管理后台";
    /**
     * 统计时间
     */
    ThreadLocal<Long> startTime = new ThreadLocal<>();

    @Pointcut("execution(public * cn.like.tools..*.*(..))")
    public void controllerLog() {}

    @Before("controllerLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        startTime.set(System.currentTimeMillis());

        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        //打印请求参数
        Map<String, String[]> paramMap = request.getParameterMap();
        if (paramMap != null && paramMap.size() > 0) {
            StringBuffer paramSbf = new StringBuffer();
            for (String mapKey : paramMap.keySet()) {
                String[] mapValue = paramMap.get(mapKey);

                //添加判断
                if (mapValue != null && mapValue.length > 0) {
                    for (String paramStr : mapValue) {
                        // TODO: pan duan
                       /* if (StringUtils.isNotBlank(paramStr)) {
                            paramSbf.append("参数" + mapKey + "=");
                            paramSbf.append(paramStr);
                            paramSbf.append(";");
                        }*/
                    }

                }//END if

            }//END for

            //打印日志参数
            logger.info(PLATFORM_NAME + "-->request请求参数PARAM : " + paramSbf);
        }//END if
        // 记录下请求内容
        logger.info(PLATFORM_NAME + "-->request请求URL : " + request.getRequestURL().toString());
        logger.info(PLATFORM_NAME + "-->request请求方法HTTP_METHOD : " + request.getMethod());
        logger.info(PLATFORM_NAME + "-->request请求方法IP : " + getIP(request));
        logger.info(PLATFORM_NAME + "-->request请求类方法CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        logger.info(PLATFORM_NAME + "-->request请求ARGS : " + Arrays.toString(joinPoint.getArgs()));
    }

    @AfterReturning(returning = "ret", pointcut = "controllerLog()")
    public void doAfterReturning(Object ret) throws Throwable {
        // 处理完请求，返回内容
        logger.info(PLATFORM_NAME + "-->response请求响应结果RESULT: " + ret);
        logger.info(PLATFORM_NAME + "-->response请求响应时间= 【" + (System.currentTimeMillis() - startTime.get()) + "】毫秒");
    }

    /**
     *
     * @param request
     * @return
     */
    private static String getIP(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

   /* @AfterReturning( pointcut = "controllerLog()" )
    public void addLog( JoinPoint joinPoint ){

        // 从切面织入点通过反射获取织入点的方法
        MethodSignature  methodSignature = (MethodSignature)joinPoint.getSignature();
        // 获取切入点的方法
        Method method = methodSignature.getMethod();
        RequestMapping mapping = method.getAnnotation(RequestMapping.class);
        String methodMapping = mapping.value()[0];

        // 获取请求的类映射路径
        String className = joinPoint.getTarget().getClass().getAnnotation(RequestMapping.class).value()[0];

        // 业务操作
    }*/
}