package cn.like.code.testCase.hash;

import cn.hutool.core.util.StrUtil;
import com.google.common.base.Preconditions;
import org.slf4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static cn.like.code.testCase.Redis.reactive;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * .____    .__ __
 * |    |   |__|  | __ ____
 * |    |   |  |  |/ // __ \
 * |    |___|  |    <\  ___/
 * |_______ \__|__|_ \\___  >
 * \/       \/    \/
 *
 * desc
 *
 * @author like
 */
public class 基于令牌的用户登录会话机制_10 {
    public static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    private final static Logger log = getLogger(基于令牌的用户登录会话机制_10.class);
    private static final String KEY_SESSIONS = "sessions:token";
    private static final String KEY_SESSIONS_EXPIRE_TIME = "sessions:expire_time";
    /** session 过期时间 30min */
    public static final long expire = 60 * 30 * 1000;

    public static String FIELD_SESSION_TOKEN(String token) {
        return "session:" + token;
    }

    public static void main(String[] args) {
        基于令牌的用户登录会话机制_10 test = new 基于令牌的用户登录会话机制_10();
        String token = "33333";
        String userId = "123";
        test.initSession(userId, token);

        System.out.println(test.isSessionValid(token));
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(test.isSessionValid(token));
    }

    /**
     * 判断session是否有效
     *
     * @param token 令牌
     * @return boolean
     */
    public boolean isSessionValid(String token) {
        Preconditions.checkNotNull(token);

        String session;
        String expireTimeStr = null;
        Date expireTimeDate;
        try {
            // 判断token对应的session是否存在
            session = reactive().hget(KEY_SESSIONS, FIELD_SESSION_TOKEN(token)).block();
            if (StrUtil.isBlank(session)) return false;

            // 判断token对应的过期时间是否存在
            expireTimeStr = reactive().hget(KEY_SESSIONS_EXPIRE_TIME, FIELD_SESSION_TOKEN(token)).block();
            if (StrUtil.isBlank(expireTimeStr)) return false;

            // 判断是否过期
            expireTimeDate = sdf.parse(expireTimeStr);
            if (new Date().after(expireTimeDate)) return false;
        } catch (ParseException e) {
            log.error("[isSessionValid][ sdf.parse(expireTimeStr)类型转换错误( token:{},expireTimeStr: {} )]", token, expireTimeStr);
        }
        return true;
    }

    /**
     * 初始化会话
     *
     * @param userId 用户id
     * @param token  令牌
     */
    public void initSession(String userId, String token) {
        long expireTimeLong = new Date().getTime() + expire;
        reactive().hset(KEY_SESSIONS, FIELD_SESSION_TOKEN(token), userId).subscribe();
        reactive().hset(KEY_SESSIONS_EXPIRE_TIME, FIELD_SESSION_TOKEN(token), sdf.format(expireTimeLong)).subscribe();
    }
}
