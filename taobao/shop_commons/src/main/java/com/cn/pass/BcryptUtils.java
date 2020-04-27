package com.cn.pass;

import org.mindrot.jbcrypt.BCrypt;

/**
 * 哈希+盐的方式避免彩虹表破解和暴力破解
 * @version 1.0
 * @user 灬焰
 * @date 2020/4/26 12:15
 */
public class BcryptUtils {
    /**
     * 将记录到数据库中的密码加密成密文
     * @param password
     * @return
     */
    public static String hashPassword(String password){
        //hashpw()中的第一个参数是密码明文，第二个参数是自动生成的盐值
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    /**
     * 登录时密码验证
     * @param password
     * @param hashpw
     * @return
     */
    public static boolean checkPassword(String password, String hashpw) {
        //checkpw()中的第一个参数是用户输入的密码，第二个参数是数据库中存的密文密码
        //该方法可以将数据库中的密文密码按照自己的规则获得盐值然后按照自己的规则拼接明文密码后加密进行比较，
        // 使用该规则的前提是数据库中的密文密码也是用该规则生成的，不然无法使用
        //比较方式采用慢哈希比较字节这样可以避免暴力破解
        return BCrypt.checkpw(password,hashpw);
    }
}
